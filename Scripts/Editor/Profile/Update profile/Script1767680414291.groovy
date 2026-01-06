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

def data = TestDataFactory.findTestData('Profile/ProfileData')

// Lấy dòng 1
String firstName = data.getValue('firstName', 1)
String lastName  = data.getValue('lastName', 1)
String phone     = data.getValue('phone', 1)
String birthDay  = data.getValue('birthDay', 1)
String city      = data.getValue('city', 1)
String address   = data.getValue('address', 1)
String slogan    = data.getValue('slogan', 1)

// DEBUG
println "firstName = " + firstName

// ===== PRE-CONDITION =====
CustomKeywords.'mycv.MyCVKeywords.LoginIfUserdataNotExist'('', '')

WebUI.waitForElementVisible(findTestObject('Editor/ProfileScreen/inputFirstName'), 30)

// ===== INPUT DATA =====
WebUI.clearText(findTestObject('Editor/ProfileScreen/inputFirstName'))

WebUI.setText(findTestObject('Editor/ProfileScreen/inputFirstName'), firstName)

WebUI.clearText(findTestObject('Editor/ProfileScreen/inputLastName'))

WebUI.setText(findTestObject('Editor/ProfileScreen/inputLastName'), lastName)

WebUI.clearText(findTestObject('Editor/ProfileScreen/inputPhone'))

WebUI.setText(findTestObject('Editor/ProfileScreen/inputPhone'), phone)

WebUI.clearText(findTestObject('Editor/ProfileScreen/inputBirthDay'))

WebUI.setText(findTestObject('Editor/ProfileScreen/inputBirthDay'), birthDay)

WebUI.sendKeys(findTestObject('Editor/ProfileScreen/inputBirthDay'), Keys.chord(Keys.ENTER))

WebUI.clearText(findTestObject('Editor/ProfileScreen/inputCity'))

WebUI.setText(findTestObject('Editor/ProfileScreen/inputCity'), city)

WebUI.clearText(findTestObject('Editor/ProfileScreen/inputAddress'))

WebUI.setText(findTestObject('Editor/ProfileScreen/inputAddress'), address)

// ===== SLOGAN (EDITOR) =====
WebUI.click(findTestObject('Editor/ProfileScreen/editorSlogan'))

WebUI.sendKeys(findTestObject('Editor/ProfileScreen/editorSlogan'), Keys.chord(Keys.CONTROL, 'a'))

WebUI.sendKeys(findTestObject('Editor/ProfileScreen/editorSlogan'), Keys.chord(Keys.BACK_SPACE))

WebUI.sendKeys(findTestObject('Editor/ProfileScreen/editorSlogan'), slogan)

// ===== CLICK UPDATE =====
WebUI.click(findTestObject('Editor/ProfileScreen/btnUpdate'))

// ===== VERIFY =====
WebUI.delay(2)

// verify dữ liệu còn tồn tại sau update
WebUI.verifyElementAttributeValue(findTestObject('Editor/ProfileScreen/inputFirstName'), 'value', firstName, 10)

