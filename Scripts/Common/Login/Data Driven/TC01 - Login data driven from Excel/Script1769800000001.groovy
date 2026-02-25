import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.reader.ExcelFactory
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

String projectDir = RunConfiguration.getProjectDir()
String inputPath = "${projectDir}/Data Files/LoginData.xlsx"
File inputFile = new File(inputPath)

if (!inputFile.exists()) {
	KeywordUtil.markFailedAndStop("Missing input Excel file: ${inputPath}")
}

TestData loginData
try {
	// Katalon 10.x signature: (filePath, sheetName, containsHeader)
	loginData = ExcelFactory.getExcelDataWithDefaultSheet(inputPath, 'LoginData', true)
} catch (MissingMethodException ignored) {
	// Fallback for older Katalon signatures.
	loginData = ExcelFactory.getExcelData(inputPath, true)
}

int totalRows = loginData.getRowNumbers()
if (totalRows == 0) {
	KeywordUtil.markFailedAndStop("Input Excel file has no data rows: ${inputPath}")
}

Set<String> availableColumns = ((loginData.getColumnNames() ?: [] as String[])
	.collect { it?.trim() }
	.findAll { it }) as Set<String>

List<String> requiredColumns = [
	'case_id',
	'email',
	'password',
	'is_encrypted',
	'expected_result',
	'note',
]

List<String> missingColumns = requiredColumns.findAll { !availableColumns.contains(it) }
if (!missingColumns.isEmpty()) {
	KeywordUtil.markFailedAndStop(
		"Input Excel is missing required column(s): ${missingColumns.join(', ')}"
	)
}

String outputDirPath = "${projectDir}/Reports/LoginDataDriven"
File outputDir = new File(outputDirPath)
outputDir.mkdirs()

// Keep execution green and use CSV as the source of truth for pass/fail rows.
boolean failExecutionWhenAnyRowFailed = false

String runTimestamp = new Date().format('yyyyMMdd_HHmmss')
File resultCsvFile = new File(outputDir, "login_results_${runTimestamp}.csv")
File summaryFile = new File(outputDir, "login_summary_${runTimestamp}.txt")

def safeGet = { String column, int row ->
	try {
		String value = loginData.getValue(column, row)
		return (value ?: '').trim()
	} catch (Exception e) {
		KeywordUtil.logInfo("safeGet warning | column=${column}, row=${row}, error=${e.message}")
		return ''
	}
}

def toBooleanFlag = { String value ->
	String normalized = value?.trim()?.toLowerCase()
	return ['1', 'true', 'yes', 'y'].contains(normalized)
}

def normalizeExpected = { String expected ->
	String normalized = expected?.trim()?.toUpperCase()
	return normalized == 'SUCCESS' ? 'SUCCESS' : 'FAIL'
}

def csvEscape = { String value ->
	String text = value ?: ''
	String escaped = text.replace('"', '""')
	return "\"${escaped}\""
}

List<Map<String, String>> rowResults = []

for (int row = 1; row <= totalRows; row++) {
	String caseId = safeGet('case_id', row)
	if (!caseId) {
		caseId = "ROW_${row}"
	}

	String email = safeGet('email', row)
	String password = safeGet('password', row)
	boolean encryptedPassword = toBooleanFlag(safeGet('is_encrypted', row))
	String expectedResult = normalizeExpected(safeGet('expected_result', row))
	String note = safeGet('note', row)

	String actualResult = 'ERROR'
	String status = 'FAIL'
	String detail = ''

	try {
		// Start each iteration with a clean auth state.
		WebUI.deleteAllCookies(FailureHandling.OPTIONAL)
		WebUI.navigateToUrl("${GlobalVariable.G_base_url}/auth/login")
		WebUI.waitForElementVisible(findTestObject('Common/Login/titleLogin'), 30)
		WebUI.executeJavaScript('window.localStorage.clear(); window.sessionStorage.clear();', null)
		WebUI.refresh()
		WebUI.waitForElementVisible(findTestObject('Common/Login/titleLogin'), 30)

		WebUI.setText(findTestObject('Common/Login/inputEmail'), email)
		if (encryptedPassword) {
			WebUI.setEncryptedText(findTestObject('Common/Login/inputPassword'), password)
		} else {
			WebUI.setText(findTestObject('Common/Login/inputPassword'), password)
		}

		WebUI.waitForElementClickable(findTestObject('Common/Login/btnSignIn'), 30)
		WebUI.click(findTestObject('Common/Login/btnSignIn'))

		boolean loginSuccess = false
		for (int i = 0; i < 25; i++) {
			boolean hasProfileTitle = WebUI.waitForElementPresent(
				findTestObject('Editor/ProfileScreen/titleProfile'),
				1,
				FailureHandling.OPTIONAL
			)
			String currentUrl = WebUI.getUrl()
			boolean isBuilderUrl = currentUrl?.contains('/builder')
			def userData = WebUI.executeJavaScript(
				"return window.localStorage.getItem('userData');",
				null
			)
			boolean hasUserData = userData != null && userData.toString().trim() != '' && userData.toString() != 'null'

			if (hasProfileTitle || isBuilderUrl || hasUserData) {
				loginSuccess = true
				break
			}
		}

		boolean hasLoginError = WebUI.waitForElementPresent(
			findTestObject('Common/Login/txtLoginError'),
			3,
			FailureHandling.OPTIONAL
		)

		actualResult = loginSuccess ? 'SUCCESS' : 'FAIL'
		if (loginSuccess) {
			detail = 'Redirected to builder/profile screen after sign in.'
		} else if (hasLoginError) {
			String loginErrorText = WebUI.getText(
				findTestObject('Common/Login/txtLoginError'),
				FailureHandling.OPTIONAL
			)
			detail = "Login failed with message: ${loginErrorText ?: 'N/A'} | URL: ${WebUI.getUrl()}"
		} else {
			detail = "No redirect to builder/profile screen after sign in. URL: ${WebUI.getUrl()}"
		}

		status = actualResult == expectedResult ? 'PASS' : 'FAIL'
	} catch (Throwable t) {
		actualResult = 'ERROR'
		status = 'FAIL'
		detail = (t.getMessage() ?: 'Unexpected execution error').take(500)
	}

	if (status == 'FAIL') {
		try {
			String normalizedCaseId = caseId.replaceAll(/[^A-Za-z0-9._-]/, '_')
			WebUI.takeScreenshot("${outputDirPath}/${normalizedCaseId}_${runTimestamp}.png")
		} catch (Exception ignored) {
			// Ignore screenshot write issues and continue collecting results.
		}
	}

	rowResults << [
		timestamp      : runTimestamp,
		row            : String.valueOf(row),
		case_id        : caseId,
		email          : email,
		expected_result: expectedResult,
		actual_result  : actualResult,
		status         : status,
		detail         : detail,
		note           : note,
	]
}

resultCsvFile.withWriter('UTF-8') { writer ->
	writer << 'timestamp,row,case_id,email,expected_result,actual_result,status,detail,note\n'
	rowResults.each { result ->
		writer << [
			result.timestamp,
			result.row,
			result.case_id,
			result.email,
			result.expected_result,
			result.actual_result,
			result.status,
			result.detail,
			result.note,
		].collect { value ->
			csvEscape(value as String)
		}.join(',') + '\n'
	}
}

int passCount = rowResults.count { it.status == 'PASS' }
int failCount = rowResults.size() - passCount

summaryFile.withWriter('UTF-8') { writer ->
	writer << "Input file: ${inputPath}\n"
	writer << "Output CSV: ${resultCsvFile.absolutePath}\n"
	writer << "Total rows: ${rowResults.size()}\n"
	writer << "PASS rows : ${passCount}\n"
	writer << "FAIL rows : ${failCount}\n"
}

KeywordUtil.logInfo("Data-driven login result file: ${resultCsvFile.absolutePath}")
KeywordUtil.logInfo("Data-driven login summary file: ${summaryFile.absolutePath}")

if (failCount > 0 && failExecutionWhenAnyRowFailed) {
	KeywordUtil.markFailed("Data-driven login has ${failCount} failed row(s). Check ${resultCsvFile.absolutePath}")
} else if (failCount > 0) {
	KeywordUtil.markWarning("Data-driven login has ${failCount} failed row(s). Check ${resultCsvFile.absolutePath}")
} else {
	KeywordUtil.markPassed("Data-driven login passed all ${rowResults.size()} row(s).")
}
