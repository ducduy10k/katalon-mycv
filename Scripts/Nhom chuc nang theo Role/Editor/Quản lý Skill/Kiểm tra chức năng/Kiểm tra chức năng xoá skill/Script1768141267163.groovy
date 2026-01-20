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

TestObject item = new TestObject('skillItem')

item.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' + 
    name) + '\']]')

TestObject skillItems = new TestObject('skillItems')

skillItems.addProperty('xpath', ConditionType.EQUALS,
    '//div[starts-with(@test_id,\'builder-skill-item-\') and not(contains(@test_id,\'icon-wrap\'))]')

if (!(WebUI.waitForElementVisible(item, 2, FailureHandling.OPTIONAL))) {
    WebUI.comment('Skill not found, create new before delete')

    WebUI.callTestCase(findTestCase('Nhom chuc nang theo Role/Editor/Quản lý Skill/Kiểm tra chức năng/Kiểm tra chức năng thêm mới Skills'), 
        [:], FailureHandling.STOP_ON_FAILURE)
}

TestObject deleteIcon = new TestObject('skillDelete')

deleteIcon.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' + 
    name) + '\']]//i[contains(@test_id,\'builder-skill-item-delete-\')]')

int beforeCount = WebUI.findWebElements(skillItems, 10).size()

WebUI.waitForElementVisible(deleteIcon, 10)

WebUI.click(deleteIcon)

WebUI.waitForElementVisible(findTestObject('Editor/Skill/DeleteSkill/btnConfirmYes'), 10)

WebUI.click(findTestObject('Editor/Skill/DeleteSkill/btnConfirmYes'))

WebUI.delay(2)

int afterCount = WebUI.findWebElements(skillItems, 10).size()

WebUI.verifyEqual(afterCount, beforeCount - 1)
