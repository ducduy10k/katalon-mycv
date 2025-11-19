import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webui.driver.DriverFactory

class B_LoginChecker {
	@BeforeTestCase
	def checkLogin(TestCaseContext testCaseContext) {
		println("✔ Check login")
		
		  def driver = null
        try {
            driver = DriverFactory.getWebDriver()
        } catch (Exception e) {
            driver = null
        }

        if (driver == null) {
            println("⚠ Browser chưa mở → mở browser")
            WebUI.openBrowser('')
            WebUI.navigateToUrl(GlobalVariable.G_base_url)
			WebUI.waitForPageLoad(20)
        }
		
		CustomKeywords.'mycv.MyCVKeywords.LoginIfUserdataNotExist'(GlobalVariable.G_user_name, GlobalVariable.G_password)
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		
	}

	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		
	}

	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		
	}
}