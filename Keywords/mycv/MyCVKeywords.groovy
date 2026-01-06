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
		WebUI.navigateToUrl(GlobalVariable.G_base_url + '/auth/login')

		WebUI.waitForElementVisible(findTestObject('Common/Login/titleLogin'), 30)

		// Nhập email
		WebUI.setText(findTestObject('Common/Login/inputEmail'), GlobalVariable.G_user_name)

		// Nhập password
		WebUI.setEncryptedText(findTestObject('Common/Login/inputPassword'), GlobalVariable.G_password)

		// Đợi nút Sign in clickable
		WebUI.waitForElementClickable(findTestObject('Common/Login/btnSignIn'), 30)

		// Click login
		WebUI.click(findTestObject('Common/Login/btnSignIn'))

		boolean isPresent = WebUI.waitForElementPresent(findTestObject('Editor/ProfileScreen/titleProfile'), 300)

		if (!isPresent) {
			WebUI.comment("Title profile KHÔNG xuất hiện trong 300")
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
