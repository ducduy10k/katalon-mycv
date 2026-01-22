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

def data = TestDataFactory.findTestData('WorkData')

String companyName = data.getValue('companyName', 1)

String companyFrom = data.getValue('companyFrom', 1)

String companyTo = data.getValue('companyTo', 1)

String companyPosition = data.getValue('companyPosition', 1)

String companyDisplayOrder = data.getValue('companyDisplayOrder', 1)

WebUI.waitForElementClickable(findTestObject('Editor/Work/btnNewCompany'), 10)

WebUI.click(findTestObject('Editor/Work/btnNewCompany'))

WebUI.waitForElementVisible(findTestObject('Editor/Work/CompanyForm/inputCompanyName'), 10)

def setInputValue = { TestObject obj, String value, boolean pressEnter = false ->
    WebUI.click(obj)

    WebUI.sendKeys(obj, Keys.chord(Keys.CONTROL, 'a'))

    WebUI.sendKeys(obj, Keys.chord(Keys.BACK_SPACE))

    WebUI.setText(obj, value)

    if (pressEnter) {
        WebUI.sendKeys(obj, Keys.chord(Keys.ENTER))
    }
}

setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyName'), companyName)

setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyFrom'), companyFrom, true)

setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyTo'), companyTo, true)

WebUI.click(findTestObject('Editor/Work/CompanyForm/btnPositionByName', [('position') : companyPosition]))

setInputValue(findTestObject('Editor/Work/CompanyForm/inputDisplayOrder'), companyDisplayOrder)

WebUI.click(findTestObject('Editor/Work/CompanyForm/btnSubmitCompany'))

WebUI.delay(1)

setInputValue(findTestObject('Editor/Work/inputSearch'), companyName)

WebUI.delay(1)

WebUI.verifyElementText(findTestObject('Editor/Work/CompanyList/labelCompanyNameByIndex', [('index') : 0]), companyName)

String updatedName = companyName + ' Updated'

String updatedPosition = 'Backend'

String updatedDisplayOrder = '2'

WebUI.waitForElementVisible(findTestObject('Editor/Work/inputSearch'), 10)

setInputValue(findTestObject('Editor/Work/inputSearch'), companyName)

WebUI.delay(1)

def editBtn = findTestObject('Editor/Work/CompanyList/btnEditByIndex', [('index') : 0])

WebUI.waitForElementClickable(editBtn, 10)

WebUI.click(editBtn)

WebUI.waitForElementVisible(findTestObject('Editor/Work/CompanyForm/inputCompanyName'), 10)

setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyName'), updatedName)

setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyFrom'), companyFrom, true)

setInputValue(findTestObject('Editor/Work/CompanyForm/inputCompanyTo'), companyTo, true)

WebUI.click(findTestObject('Editor/Work/CompanyForm/btnPositionByName', [('position') : updatedPosition]))

setInputValue(findTestObject('Editor/Work/CompanyForm/inputDisplayOrder'), updatedDisplayOrder)

WebUI.click(findTestObject('Editor/Work/CompanyForm/btnSubmitCompany'))

WebUI.delay(1)

setInputValue(findTestObject('Editor/Work/inputSearch'), updatedName)

WebUI.delay(1)

WebUI.verifyElementText(findTestObject('Editor/Work/CompanyList/labelCompanyNameByIndex', [('index') : 0]), updatedName)

String projectName = data.getValue('projectName', 1)

String projectFrom = data.getValue('projectFrom', 1)

String projectTo = data.getValue('projectTo', 1)

String projectDescription = data.getValue('projectDescription', 1)

String projectTeamSize = data.getValue('projectTeamSize', 1)

String projectResponsibilities = data.getValue('projectResponsibilities', 1)

String projectLanguages = data.getValue('projectLanguages', 1)

String projectTools = data.getValue('projectTools', 1)

String projectDatabase = data.getValue('projectDatabase', 1)

String projectTechnologies = data.getValue('projectTechnologies', 1)

String projectUrl = data.getValue('projectUrl', 1)

def projectNewBtn = findTestObject('Editor/Work/ProjectList/btnNewProjectByCompanyIndex', [('companyIndex') : 0])

if (!(WebUI.waitForElementVisible(projectNewBtn, 2, FailureHandling.OPTIONAL))) {
    def toggleBtn = findTestObject('Editor/Work/CompanyList/btnToggleByIndex', [('index') : 0])

    WebUI.click(toggleBtn)

    WebUI.waitForElementVisible(projectNewBtn, 5)
}

WebUI.click(projectNewBtn)

WebUI.waitForElementVisible(findTestObject('Editor/Work/ProjectForm/inputProjectName'), 10)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectName'), projectName)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectFrom'), projectFrom, true)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectTo'), projectTo, true)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectDescription'), projectDescription)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectTeamSize'), projectTeamSize)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectResponsibilities'), projectResponsibilities)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectLanguages'), projectLanguages)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectTools'), projectTools)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectDatabase'), projectDatabase)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectTechnologies'), projectTechnologies)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectUrl'), projectUrl)

WebUI.click(findTestObject('Editor/Work/ProjectForm/btnSubmitProject'))

WebUI.delay(1)

WebUI.verifyTextPresent(projectName, false)

String updatedProjectName = projectName + ' Updated'

String updatedProjectDescription = projectDescription + ' Updated'

def projectShowBtn = findTestObject('Editor/Work/ProjectList/btnShowByIndex', [('companyIndex') : 0, ('projectIndex') : 0])

if (!(WebUI.waitForElementVisible(projectShowBtn, 2, FailureHandling.OPTIONAL))) {
    def toggleBtn = findTestObject('Editor/Work/CompanyList/btnToggleByIndex', [('index') : 0])

    WebUI.click(toggleBtn)

    WebUI.waitForElementVisible(projectShowBtn, 5)
}

WebUI.click(projectShowBtn)

WebUI.waitForElementVisible(findTestObject('Editor/Work/ProjectForm/inputProjectName'), 10)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectName'), updatedProjectName)

setInputValue(findTestObject('Editor/Work/ProjectForm/inputProjectDescription'), updatedProjectDescription)

WebUI.click(findTestObject('Editor/Work/ProjectForm/btnSubmitProject'))

WebUI.delay(1)

WebUI.verifyTextPresent(updatedName, false)

def projectDeleteBtn = findTestObject('Editor/Work/ProjectList/btnDeleteByIndex', [('companyIndex') : 0, ('projectIndex') : 0])

if (!(WebUI.waitForElementVisible(projectDeleteBtn, 2, FailureHandling.OPTIONAL))) {
    def toggleBtn = findTestObject('Editor/Work/CompanyList/btnToggleByIndex', [('index') : 0])

    WebUI.click(toggleBtn)

    WebUI.waitForElementVisible(projectDeleteBtn, 5)
}

WebUI.click(projectDeleteBtn)

WebUI.waitForElementVisible(findTestObject('Editor/Work/ConfirmDialog/btnConfirmYes'), 10)

WebUI.click(findTestObject('Editor/Work/ConfirmDialog/btnConfirmYes'))

WebUI.delay(2)

def deleteBtn = findTestObject('Editor/Work/CompanyList/btnDeleteByIndex', [('index') : 0])

WebUI.waitForElementClickable(deleteBtn, 10)

WebUI.click(deleteBtn)

WebUI.waitForElementVisible(findTestObject('Editor/Work/ConfirmDialog/btnConfirmYes'), 10)

WebUI.click(findTestObject('Editor/Work/ConfirmDialog/btnConfirmYes'))

WebUI.delay(2)

