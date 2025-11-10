import com.kms.katalon.core.webui.driver.DriverFactory
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

class CommonListener {
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		println "==> Bắt đầu test case: ${testCaseContext.getTestCaseId()}"
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		println "<== Kết thúc test case: ${testCaseContext.getTestCaseId()}"
		println "Kết quả: ${testCaseContext.getTestCaseStatus()}"
		if (testCaseContext.getTestCaseStatus() == 'FAILED') {
	        try {
	            if (DriverFactory.getWebDriver() != null) {
					println "takeScreenshot"
	                WebUI.takeScreenshot("Screenshots/${testCaseContext.getTestCaseId()}_FAILED.png")
	            } else {
	                println "⚠️ Browser đã đóng — bỏ qua bước chụp ảnh."
	            }
	        } catch (Exception e) {
	            println "❌ Không thể chụp ảnh lỗi: ${e.message}"
	        }
		}
	}

	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		println "=== Bắt đầu suite: ${testSuiteContext.getTestSuiteId()} ==="
	}

	@AfterTestSuite
	def afterTestSuite(TestSuiteContext testSuiteContext) {
		println "=== Kết thúc suite: ${testSuiteContext.getTestSuiteId()} ==="
	}
}