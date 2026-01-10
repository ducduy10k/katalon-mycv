import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import groovy.json.JsonSlurper as JsonSlurper
import internal.GlobalVariable as GlobalVariable

def userId = CustomKeywords.'mycv.MyCVKeywords.getCurrentUserId'()

println("UserId: $userId")

// 2. Get current userId (dynamic)

def response = WS.sendRequest(findTestObject('APIs/get_skills', [('id') : userId]))

def json = new JsonSlurper().parseText(response.getResponseText())

def apiSkills = json.Items

assert apiSkills.size() > 0 : 'API trả về danh sách skill rỗng'

WebUI.waitForElementVisible(findTestObject('Viewer/SkillsSection/lblSkillPercent'), 15)

// =====================
// 2. BUILD EXPECTED LIST (API → SORT DESC → TOP 10)
// =====================
def expectedSkills = apiSkills.sort({ def a, def b ->
            ((b['percentage']) as Integer) <=> ((a['percentage']) as Integer)
    }).take(10)

println('Expected skills (API sorted DESC, max 10):')

expectedSkills.each({ 
        println("$it.name - $it.percentage%")
    })

// =====================
// 4. GET UI PERCENTAGES (ORDER TOP → BOTTOM)
// =====================
def percentElements = WebUI.findWebElements(findTestObject('Viewer/SkillsSection/lblSkillPercent'), 10)

int uiSkillCount = percentElements.size()

println('UI skill count: ' + uiSkillCount)

// =====================
// 5. VERIFY MAX 10 RECORDS
// =====================
assert uiSkillCount <= 10 : 'UI hiển thị quá 10 skills'

// =====================
// 6. VERIFY UI COUNT = EXPECTED COUNT
// =====================
WebUI.verifyEqual(uiSkillCount, expectedSkills.size())

// =====================
// 7. VERIFY SORT DESC + VALUE MATCH API
// =====================
for (int i = 0; i < expectedSkills.size(); i++) {
    String expectedName = expectedSkills[i].name

    String expectedPercentText = expectedSkills[i].percentage + '%'

    // Dynamic XPath theo skill name
    TestObject percentObj = new TestObject('skillPercent_' + expectedName)

    percentObj.addProperty('xpath', ConditionType.EQUALS, ((('//section[@id=\'skills\']//div[contains(@class,\'skill-card\')]' + 
        '[.//div[normalize-space()=\'') + expectedName) + '\']]') + '//div[contains(@class,\'skill-card__dial-value\')]')

    WebUI.waitForElementVisible(percentObj, 10)

    String actualPercentText = WebUI.getText(percentObj)

    println("Verify UI: $expectedName | Expected: $expectedPercentText | Actual: $actualPercentText")

    // Verify % đúng API
    WebUI.verifyEqual(actualPercentText, expectedPercentText)
}

// =====================
// 8. VERIFY SKILL THỨ 11 KHÔNG HIỂN THỊ (NẾU CÓ)
// =====================
if (apiSkills.size() > 10) {
    String skill11Name = (apiSkills..sort({ def a, def b ->
                ((b['percentage']) as Integer) <=> ((a['percentage']) as Integer)
        }).get(10).name)

    WebUI.verifyTextNotPresent(skill11Name, false)
}

println('✅ VERIFY SKILLS: SORT DESC + MAX 10 + MATCH API — PASSED')

