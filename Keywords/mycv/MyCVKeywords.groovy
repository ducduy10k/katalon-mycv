package mycv

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class MyCVKeywords {

	@Keyword
	def AppLogin () {
		WebUI.navigateToUrl('https://devduynd.web.app/auth/login')

		WebUI.setText(findTestObject('Object Repository/Page_Login/input_Email_email'), 'test@yopmail.com')

		WebUI.setEncryptedText(findTestObject('Object Repository/Page_Login/input_Password_password'), 'RigbBhfdqOBGNlJIWM1ClA==')

		WebUI.click(findTestObject('Object Repository/Page_Login/button_Forgot your password_btn bg-blue-400_bf5a24'))
	}

	@Keyword
	def finalTestCase () {
		print("Congratulations on your success");
	}
}
