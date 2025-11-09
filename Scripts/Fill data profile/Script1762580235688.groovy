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

WebUI.openBrowser('')

CustomKeywords.'mycv.MyCVKeywords.AppLogin'()

WebUI.waitForElementPresent(findTestObject('Page_Login/div_Setting_w-100'), 1)

WebUI.setText(findTestObject('Object Repository/Page_Login/input_Profile_p-inputtext p-component p-ele_2ea6b0'), 'Duy')

WebUI.click(findTestObject('Object Repository/Page_Login/input_First name_p-inputtext p-component p-_c00827'))

WebUI.setText(findTestObject('Object Repository/Page_Login/input_First name_p-inputtext p-component p-_c00827'), 'Nguyễn ')

WebUI.click(findTestObject('Object Repository/Page_Login/input_Description_p-inputtext p-component p_f9ed0f'))

WebUI.setText(findTestObject('Object Repository/Page_Login/input_Description_p-inputtext p-component p_f9ed0f'), '<h1 class="home__title">Hi,<br>I\'am&#160;<font color="#d72d2d">Duy</font><br>Full Stack Developer</h1>')

WebUI.setText(findTestObject('Object Repository/Page_Login/input_Slogan_calendar'), '11/10/1999')

WebUI.setText(findTestObject('Object Repository/Page_Login/input_Birth day_p-inputtext p-component p-e_b43973'), '038****706')

WebUI.setText(findTestObject('Object Repository/Page_Login/input_Phone number_p-inputtext p-component _c71f92'), 'Ninh Bình')

WebUI.setText(findTestObject('Object Repository/Page_Login/input_City, Province_p-inputtext p-componen_057006'), 'Kim Sơn')

WebUI.click(findTestObject('Object Repository/Page_Login/div_Setting_main-panel_1'))

CustomKeywords.'mycv.MyCVKeywords.finalTestCase'()

