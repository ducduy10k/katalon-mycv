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
String percentage = data.getValue('percentage', 1)

TestObject item = new TestObject('skillItem')
item.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' +
    name) + '\']]')

WebUI.waitForElementClickable(findTestObject('Editor/Skill/btnNewSkill'), 10)
WebUI.click(findTestObject('Editor/Skill/btnNewSkill'))

WebUI.waitForElementVisible(findTestObject('Editor/Skill/AddSkill/popupAddSkill'), 10)

WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputSkillName'))
WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputSkillName'), name)

WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputPercentage'))
WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputPercentage'), percentage)

WebUI.clearText(findTestObject('Editor/Skill/AddSkill/inputIcon'))
WebUI.setText(findTestObject('Editor/Skill/AddSkill/inputIcon'), icon)

WebUI.click(findTestObject('Editor/Skill/AddSkill/btnSubmitNewSkill'))

TestObject skillName = new TestObject('skillName')
skillName.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' +
    name) + '\']]//p')

TestObject skillPercent = new TestObject('skillPercent')
skillPercent.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' +
    name) + '\']]//span[contains(@test_id,\'builder-skill-item-percentage-value-\')]')

WebUI.waitForElementVisible(skillName, 10)
WebUI.verifyElementText(skillName, name)
WebUI.verifyElementText(skillPercent, percentage + '%')

WebUI.waitForElementVisible(item, 10)
WebUI.click(item)

WebUI.waitForElementVisible(findTestObject('Editor/Skill/AddSkill/inputSkillName'), 10)

String updatedName = name + ' Updated'
String updatedPercentage = '80'
String updatedIcon = icon

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

String deleteName = updatedName

TestObject deleteItem = new TestObject('skillItemToDelete')
deleteItem.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' +
    deleteName) + '\']]')

TestObject skillItems = new TestObject('skillItems')
skillItems.addProperty('xpath', ConditionType.EQUALS, '//div[starts-with(@test_id,\'builder-skill-item-\') and not(contains(@test_id,\'icon-wrap\'))]')

if (WebUI.waitForElementVisible(deleteItem, 2, FailureHandling.OPTIONAL)) {
    TestObject deleteIcon = new TestObject('skillDelete')
    deleteIcon.addProperty('xpath', ConditionType.EQUALS, ('//div[contains(@test_id,\'builder-skill-item-\')][.//p[normalize-space()=\'' +
        deleteName) + '\']]//i[contains(@test_id,\'builder-skill-item-delete-\')]')

    int beforeCount = WebUI.findWebElements(skillItems, 10).size()

    def safeClick = { TestObject obj ->
        WebUI.waitForElementVisible(obj, 10)
        WebUI.scrollToElement(obj, 5)
        def el = WebUI.findWebElement(obj, 10)
        WebUI.executeJavaScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", [el])
        try {
            WebUI.click(obj)
        } catch (Exception e) {
            WebUI.executeJavaScript("arguments[0].click();", [el])
        }
    }

    safeClick(deleteIcon)

    WebUI.waitForElementVisible(findTestObject('Editor/Skill/DeleteSkill/btnConfirmYes'), 10)
    WebUI.click(findTestObject('Editor/Skill/DeleteSkill/btnConfirmYes'))

    WebUI.delay(2)

    int afterCount = WebUI.findWebElements(skillItems, 10).size()
    WebUI.verifyEqual(afterCount, beforeCount - 1)
} else {
    WebUI.comment('Skill not found, skip delete')
}
