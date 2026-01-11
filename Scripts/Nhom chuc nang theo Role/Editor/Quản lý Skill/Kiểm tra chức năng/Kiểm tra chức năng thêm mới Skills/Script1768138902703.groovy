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

WebUI.waitForElementClickable(findTestObject('Editor/Skill/btnNewSkill'), 10)

WebUI.click(findTestObject('Editor/Skill/btnNewSkill'))

// Wait popup
WebUI.waitForElementVisible(findTestObject('Editor/Skill/AddSkill/popupAddSkill'), 10)

def data = TestDataFactory.findTestData('AddSkillData')

// Lấy dòng 1
String icon = data.getValue('icon', 1)

String name = data.getValue('name', 1)

String percentage = data.getValue('percentage', 1)

// DEBUG
println('icon = ' + icon)

// Name
WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputSkillName'))

WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputSkillName'), name)

// Percentage
WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputPercentage'))

WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputPercentage'), percentage)

// Icon
WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputIcon'))

WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputIcon'), icon)

// Submit
WebUI.click(findTestObject('Editor/Skill/AddSkill/btnSubmitNewSkill'))

