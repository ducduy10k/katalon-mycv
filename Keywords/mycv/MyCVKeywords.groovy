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

import com.kms.katalon.core.util.KeywordUtil


import internal.GlobalVariable

import utils.UrlUtils
import utils.LocalStorageUtils

public class MyCVKeywords {

	@Keyword
	def AppLogin (String userName, String password) {
		WebUI.navigateToUrl('https://devduynd.web.app/auth/login')

		WebUI.waitForPageLoad(20)

		WebUI.setText(findTestObject('Object Repository/Page_Login/input_Email_email'), userName)

		WebUI.setEncryptedText(findTestObject('Object Repository/Page_Login/input_Password_password'), password)

		WebUI.click(findTestObject('Object Repository/Page_Login/button_Forgot your password_btn bg-blue-400_bf5a24'))

		boolean isPresent = WebUI.waitForElementPresent(findTestObject('Page_Login/div_Setting_w-100'), 10)

		if (!isPresent) {
			WebUI.comment("Element div_Setting_w-100 KHÔNG xuất hiện trong 10s")
			KeywordUtil.markFailed("❌ FAIL: Không tìm thấy element Setting trên màn hình Login")
		}
	}

	@Keyword
	def LoginIfUserdataNotExist(String userName, String password) {
		// Lấy giá trị userData từ localStorage
		def userData = WebUI.executeJavaScript(
				"return window.localStorage.getItem('userData');",
				null
				)

		if (userData == null || userData == "") {
			println("❗ LocalStorage không có token → chưa login → thực hiện login")
			AppLogin(GlobalVariable.G_user_name, GlobalVariable.G_password)
		} else {
			println("✔ Đã login (localStorage có token) → bỏ qua login")
		}
	}

	@Keyword
	String getCurrentUserId() {
		return UrlUtils.getQueryParam('id') ?: LocalStorageUtils.getUserIdFromLocalStorage() ?: GlobalVariable.G_userIdDefault
	}

	@Keyword
	def finalTestCase () {
		print("Congratulations on your success");
	}
}
