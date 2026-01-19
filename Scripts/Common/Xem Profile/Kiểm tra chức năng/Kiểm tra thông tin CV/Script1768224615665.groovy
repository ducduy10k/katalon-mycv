import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType

import groovy.json.JsonSlurper as JsonSlurper

def userId = CustomKeywords.'mycv.MyCVKeywords.getCurrentUserId'()

println("UserId: $userId")

// 3. Call API get profile
def profileResponse = WS.sendRequest(findTestObject('APIs/get_profile', [('id') : userId]))

// 4. Parse JSON response
def profileJson = new JsonSlurper().parseText(profileResponse.getResponseText())

def phoneFromAPI = profileJson.phoneNumber

def emailFromAPI = profileJson.email

def birthDayFromAPI = profileJson.birthDay // timestamp (ms)

// 5. Convert birthday giống FE (dd/MM/yyyy)
def birthdayText = new Date(birthDayFromAPI).format('dd/MM/yyyy')

// Debug log
println("Phone API    : $phoneFromAPI")

println("Email API    : $emailFromAPI")

println("Birthday API : $birthdayText")


// 2. Call api lấy thông tin skill
def skillResponse = WS.sendRequest(findTestObject('APIs/get_skills', [('id') : userId]))

def skillJson = new JsonSlurper().parseText(skillResponse.getResponseText())

def apiSkills = skillJson.Items

assert apiSkills.size() > 0 : 'API trả về danh sách skill rỗng'
 
println("Verify data: ")

WebUI.waitForElementVisible(findTestObject('Viewer/ProfileView/phoneValue'), 0)

// 6. Verify Profile data 
WebUI.verifyElementText(findTestObject('Viewer/ProfileView/phoneValue'), phoneFromAPI)

WebUI.verifyElementText(findTestObject('Viewer/ProfileView/emailValue'), emailFromAPI)

WebUI.verifyElementText(findTestObject('Viewer/ProfileView/birthdayValue'), birthdayText)

WebUI.waitForElementVisible(findTestObject('Viewer/EducationSection/educationGpaValue'), 15)


// Verify education info 
WebUI.verifyElementText(findTestObject('Viewer/EducationSection/educationUniversityName'), university)

WebUI.verifyElementText(findTestObject('Viewer/EducationSection/educationMajorValue'), major)

WebUI.verifyElementText(findTestObject('Viewer/EducationSection/educationPeriodValue'), period)

WebUI.verifyElementText(findTestObject('Viewer/EducationSection/educationGpaValue'), gpa)

println("UserId: $userId")


WebUI.waitForElementVisible(findTestObject('Viewer/SkillsSection/lblSkillPercent'), 15)

// =====================
// 2. BUILD EXPECTED LIST (API → SORT DESC → TOP 10)
// =====================
def expectedSkills = apiSkills.sort({ def a, def b ->
            ((b['percentage']) as Integer) <=> ((a['percentage']) as Integer)
    }).take(10)

println('Expected skills (API sorted DESC, max 10):')

expectedSkills.each({ 
        println("$it.name - $it.percentage%")
    })

// =====================
// 4. GET UI PERCENTAGES (ORDER TOP → BOTTOM)
// =====================
def percentElements = WebUI.findWebElements(findTestObject('Viewer/SkillsSection/lblSkillPercent'), 10)

int uiSkillCount = percentElements.size()

println('UI skill count: ' + uiSkillCount)

// =====================
// 5. VERIFY MAX 10 RECORDS
// =====================
assert uiSkillCount <= 10 : 'UI hiển thị quá 10 skills'

// =====================
// 6. VERIFY UI COUNT = EXPECTED COUNT
// =====================
WebUI.verifyEqual(uiSkillCount, expectedSkills.size())

// =====================
// 7. VERIFY SORT DESC + VALUE MATCH API
// =====================
for (int i = 0; i < expectedSkills.size(); i++) {
    String expectedName = expectedSkills[i].name

    String expectedPercentText = expectedSkills[i].percentage + '%'

    // Dynamic XPath theo skill name
    TestObject percentObj = new TestObject('skillPercent_' + expectedName)

    percentObj.addProperty('xpath', ConditionType.EQUALS, ((('//section[@id=\'skills\']//div[contains(@class,\'skill-card\')]' + 
        '[.//div[normalize-space()=\'') + expectedName) + '\']]') + '//div[contains(@class,\'skill-card__dial-value\')]')

    WebUI.waitForElementVisible(percentObj, 10)

    String actualPercentText = WebUI.getText(percentObj)

    println("Verify UI: $expectedName | Expected: $expectedPercentText | Actual: $actualPercentText")

    // Verify % đúng API
    WebUI.verifyEqual(actualPercentText, expectedPercentText)
}

// =====================
// 8. VERIFY SKILL THỨ 11 KHÔNG HIỂN THỊ (NẾU CÓ)
// =====================
if (apiSkills.size() > 10) {
    String skill11Name = (apiSkills..sort({ def a, def b ->
                ((b['percentage']) as Integer) <=> ((a['percentage']) as Integer)
        }).get(10).name)

    WebUI.verifyTextNotPresent(skill11Name, false)
}

println('✅ VERIFY SKILLS: SORT DESC + MAX 10 + MATCH API — PASSED')

