import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class RMSysTest extends TestBase {

    @Test
    public void fillingLoginForm() {
        open("https://192.168.100.26/");
        $("#Username").setValue("EugenBorisik");
        $("#Password").setValue("qwerty12345");
        $("#SubmitButton").click();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(".sign-out-span>a");
    }

    @Test
    public void explicitCondTest() {
        open("https://192.168.100.26/");
        $("#Username").setValue("EugenBorisik").shouldBe(visible);
        $("#Password").setValue("qwerty12345").shouldBe(visible);
        $("#SubmitButton").click();
        $("#officeMenu").shouldBe(visible).click();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(".sign-out-span>a");

    }

    @Test(dataProvider = "IncorrectDataSets", dataProviderClass = DataProvider.class)
    public void InvalidCredsDDTTest(String username, String password) {
        open("https://192.168.100.26/");
        $("#Username").setValue(username);
        $("#Password").setValue(password);
        $("#SubmitButton").click();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals($(".validation-summary-errors li"), "*Invalid credentials.");
    }

    @Test(dataProvider = "IncorrectTestUsers", dataProviderClass = DataProvider.class)
    public void PasswordRequiredDDTTest(String username, String password) {
        open("https://192.168.100.26/");
        $("#Username").setValue(username);
        $("#Password").setValue(password);
        $("#SubmitButton").click();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals($("#password-box-validation>span"), "Password is required");
    }

    @Test(dataProvider = "CorrectTestUser", dataProviderClass = DataProvider.class)
    public void CorrectLoginDDTTest(String username, String password) {
        open("https://192.168.100.26/");
        $("#Username").setValue(username);
        $("#Password").setValue(password);
        $("#SubmitButton").click();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(".sign-out-span>a");
    }

    @Test
    public void iFrameTest() {
        open("https://the-internet.herokuapp.com/iframe");
        switchTo().innerFrame("mce_0_ifr");
        $("#tinymce").click();
        $("#tinymce").clear();
        switchTo().innerFrame("mce_0_ifr");
        $("#tinymce").setValue("Hello ");
        switchTo().defaultContent();
        $("#mceu_3>button").click();
        switchTo().innerFrame("mce_0_ifr");
        $("#tinymce").setValue("world!");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals($("#tinymce").getText(), "Hello \uFEFFworld!");
    }

    @Test
    public void AlertAcceptTest() {
        open("https://the-internet.herokuapp.com/javascript_alerts");
        $(byXpath("//*[contains(text(),'Click for JS Confirm')]")).click();
        dismiss();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals($("#result").getText(), "You clicked: Cancel");
    }

    @Test
    public void AlertClickPromptTest() {
        open("https://the-internet.herokuapp.com/javascript_alerts");
        $(byXpath("//*[contains(text(),'Click for JS Prompt')]")).click();
        actions().sendKeys("test");
        confirm();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals($("#result").getText(), "You entered: test");
    }
}
