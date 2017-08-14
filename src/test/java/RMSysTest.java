import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
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
        $(".sign-out-span>a").shouldBe(visible);
    }

    @Test
    public void explicitCondTest() {
        open("https://192.168.100.26/");
        $("#Username").setValue("EugenBorisik").shouldBe(visible);
        $("#Password").setValue("qwerty12345").shouldBe(visible);
        $("#SubmitButton").click();
        $("#officeMenu").shouldBe(visible).click();
        $(".sign-out-span>a").shouldBe(visible);
    }

    @Test(dataProvider = "IncorrectDataSets", dataProviderClass = DataAndPropertyProvider.class)
    public void InvalidCredsDDTTest(String username, String password) {
        open("https://192.168.100.26/");
        $("#Username").setValue(username);
        $("#Password").setValue(password);
        $("#SubmitButton").click();
        $(".validation-summary-errors li").shouldHave(text("*Invalid credentials."));
    }

    @Test(dataProvider = "IncorrectTestUsers", dataProviderClass = DataAndPropertyProvider.class)
    public void PasswordRequiredDDTTest(String username, String password) {
        open("https://192.168.100.26/");
        $("#Username").setValue(username);
        $("#Password").setValue(password);
        $("#SubmitButton").click();
        $("#password-box-validation>span").shouldHave(text("Password is required"));
    }

    @Test(dataProvider = "CorrectTestUser", dataProviderClass = DataAndPropertyProvider.class)
    public void CorrectLoginDDTTest(String username, String password) {
        open("https://192.168.100.26/");
        $("#Username").setValue(username);
        $("#Password").setValue(password);
        $("#SubmitButton").click();
        $(".sign-out-span>a").shouldBe(visible);
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
        $("#tinymce").shouldHave(text("Hello \uFEFFworld!"));
    }

    @Test
    public void AlertAcceptTest() {
        open("https://the-internet.herokuapp.com/javascript_alerts");
        $(byXpath("//*[contains(text(),'Click for JS Confirm')]")).click();
        dismiss();
        $("#result").shouldHave(text("You clicked: Cancel"));
    }

    @Test
    public void AlertClickPromptTest() {
        open("https://the-internet.herokuapp.com/javascript_alerts");
        $(byXpath("//*[contains(text(),'Click for JS Prompt')]")).click();
        actions().sendKeys("test");
        confirm();
        $("#result").shouldHave(text("You entered: test"));
    }
}
