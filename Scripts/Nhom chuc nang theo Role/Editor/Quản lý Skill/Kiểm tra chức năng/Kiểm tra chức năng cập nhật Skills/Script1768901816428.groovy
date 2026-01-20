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
import com.kms.katalon.core.testobject.ConditionType as ConditionType

def data = TestDataFactory.findTestData('AddSkillData')

String name = data.getValue('name', 1)

String icon = data.getValue('icon', 1)

String updatedName = name + ' Updated'

String updatedPercentage = '80'

String updatedIcon = icon

TestObject item = new TestObject('skillItem')

item.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' + 
    name) + '\']]')

if (!(WebUI.waitForElementVisible(item, 2, FailureHandling.OPTIONAL))) {
    WebUI.comment('Skill not found, create new before update')

    WebUI.callTestCase(findTestCase('Nhom chuc nang theo Role/Editor/Quản lý Skill/Kiểm tra chức năng/Kiểm tra chức năng thêm mới Skills'), 
        [:], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(item)

WebUI.waitForElementVisible(findTestObject('Editor/Skill/AddSkill/inputSkillName'), 10)

WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputSkillName'))

WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputSkillName'), updatedName)

WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputPercentage'))

WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputPercentage'), updatedPercentage)

WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputIcon'))

WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputIcon'), updatedIcon)

WebUI.click(findTestObject('Editor/Skill/AddSkill/btnSubmitNewSkill'))

TestObject updatedNameObj = new TestObject('updatedSkillName')

updatedNameObj.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' + 
    updatedName) + '\']]//p')

TestObject updatedPercentObj = new TestObject('updatedSkillPercent')

updatedPercentObj.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' + 
    updatedName) + '\']]//span[contains(@test_id,\'builder-skill-item-percentage-value-\')]')

WebUI.waitForElementVisible(updatedNameObj, 10)

WebUI.verifyElementText(updatedNameObj, updatedName)

WebUI.verifyElementText(updatedPercentObj, updatedPercentage + '%')

