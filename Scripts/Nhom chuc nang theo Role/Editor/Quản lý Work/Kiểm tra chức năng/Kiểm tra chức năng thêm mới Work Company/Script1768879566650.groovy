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
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory


def data = TestDataFactory.findTestData('WorkData')

String companyName = data.getValue('companyName', 1)
String companyFrom = data.getValue('companyFrom', 1)
String companyTo = data.getValue('companyTo', 1)
String companyPosition = data.getValue('companyPosition', 1)
String companyDisplayOrder = data.getValue('companyDisplayOrder', 1)

WebUI.waitForElementClickable(findTestObject('Editor/Work/btnNewCompany'), 10)
WebUI.click(findTestObject('Editor/Work/btnNewCompany'))

WebUI.waitForElementVisible(findTestObject('Editor/Work/CompanyForm/inputCompanyName'), 10)

def setInputValue = { TestObject obj, String value, boolean pressEnter = false ->
    WebUI.click(obj)
    WebUI.sendKeys(obj, Keys.chord(Keys.CONTROL, 'a'))
    WebUI.sendKeys(obj, Keys.chord(Keys.BACK_SPACE))
    WebUI.setText(obj, value)
    if (pressEnter) {
        WebUI.sendKeys(obj, Keys.chord(Keys.ENTER))
    }
}

setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyName'), companyName)
setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyFrom'), companyFrom, true)
setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyTo'), companyTo, true)

WebUI.click(findTestObject('Editor/Work/CompanyForm/btnPositionByName', [('position') : companyPosition]))

setInputValue(findTestObject('Editor/Work/CompanyForm/inputDisplayOrder'), companyDisplayOrder)

WebUI.click(findTestObject('Editor/Work/CompanyForm/btnSubmitCompany'))

WebUI.delay(1)

setInputValue(findTestObject('Editor/Work/inputSearch'), companyName)

WebUI.delay(1)

WebUI.verifyElementText(findTestObject('Editor/Work/CompanyList/labelCompanyNameByIndex', [('index') : 0]), companyName)
