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

// 1. Verify sidebar hiển thị
CustomKeywords.'mycv.MyCVKeywords.LoginIfUserdataNotExist'('', '')

String currentUrl = WebUI.getUrl()

// ===== 3. Load test data =====
def data = TestDataFactory.findTestData('SocialData')

String facebook = data.getValue('facebook', 1)

String youtube = data.getValue('youtube', 1)

String gmail = data.getValue('gmail', 1)

String skype = data.getValue('skype', 1)

println(facebook)

if (!(currentUrl.contains('/builder/social'))) {
    WebUI.navigateToUrl(GlobalVariable.G_base_url + '/builder/social')
}

WebUI.waitForPageLoad(30)

WebUI.delay(20)

// ===== 2. Wait màn Social =====
WebUI.waitForElementVisible(findTestObject('Editor/Social/titleSocial'), 30)

// ===== 4. Nhập dữ liệu =====
WebUI.clearText(findTestObject('Editor/Social/inputFacebook'))

WebUI.setText(findTestObject('Editor/Social/inputFacebook'), facebook)

WebUI.clearText(findTestObject('Editor/Social/inputYoutube'))

WebUI.setText(findTestObject('Editor/Social/inputYoutube'), youtube)

WebUI.clearText(findTestObject('Editor/Social/inputGmail'))

WebUI.setText(findTestObject('Editor/Social/inputGmail'), gmail)

WebUI.clearText(findTestObject('Editor/Social/inputSkype'))

WebUI.setText(findTestObject('Editor/Social/inputSkype'), skype)

// ===== 5. Click Update =====
WebUI.click(findTestObject('Editor/Social/btnUpdate'))

// ===== 6. Verify (basic) =====
WebUI.delay(2)

// Option 1: verify value vẫn giữ
WebUI.verifyElementAttributeValue(findTestObject('Editor/Social/inputSkype'), 'value', skype, 10)

