import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import internal.GlobalVariable as GlobalVariable
import groovy.json.JsonSlurper as JsonSlurper
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint


// 1. Open browser
// 2. Get current userId (dynamic)
def userId = CustomKeywords.'mycv.MyCVKeywords.getCurrentUserId'()

println("UserId: $userId")

// 3. Call API get profile
def response = WS.sendRequest(findTestObject('APIs/get_profile', [('id') : userId]))

// 4. Parse JSON response
def json = new JsonSlurper().parseText(response.getResponseText())

def phoneFromAPI = json.phoneNumber

def emailFromAPI = json.email

def birthDayFromAPI = json.birthDay // timestamp (ms)

// 5. Convert birthday giống FE (dd/MM/yyyy)
def birthdayText = new Date(birthDayFromAPI).format('dd/MM/yyyy')

// Debug log
println("Phone API    : $phoneFromAPI")

println("Email API    : $emailFromAPI")

println("Birthday API : $birthdayText")

// 6. Verify UI vs API
WebUI.verifyElementText(findTestObject('Viewer/ProfileView/phoneValue'), phoneFromAPI)

WebUI.verifyElementText(findTestObject('Viewer/ProfileView/emailValue'), emailFromAPI)

WebUI.verifyElementText(findTestObject('Viewer/ProfileView/birthdayValue'), birthdayText)

