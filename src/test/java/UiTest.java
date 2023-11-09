import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UiTest {

    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/ValidApprovedCard.csv")
    public void validTestsWithApprovedCard(String purchaseMethod, String cardNumber, String month, String year, String name, String cvc, String cssText, String cssSelector, String resultText) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Номер карты")).parent().$("input.input__control").setValue(cardNumber);
        $(byText("Месяц")).parent().$("input.input__control").setValue(month);
        $(byText("Год")).parent().$("input.input__control").setValue(year);
        $(byText("Владелец")).parent().$("input.input__control").setValue(name);
        $(byText("CVC/CVV")).parent().$("input.input__control").setValue(cvc);
        $(byText("Продолжить")).click();
        $(byText(cssText)).parent().$(cssSelector).shouldBe(Condition.visible, Duration.ofSeconds(20)).shouldHave(Condition.exactText(resultText));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/ValidDeclinedCard.csv")
    public void validTestsWithDeclinedCard(String purchaseMethod, String cardNumber, String month, String year, String name, String cvc, String cssText, String cssSelector, String resultText) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Номер карты")).parent().$("input.input__control").setValue(cardNumber);
        $(byText("Месяц")).parent().$("input.input__control").setValue(month);
        $(byText("Год")).parent().$("input.input__control").setValue(year);
        $(byText("Владелец")).parent().$("input.input__control").setValue(name);
        $(byText("CVC/CVV")).parent().$("input.input__control").setValue(cvc);
        $(byText("Продолжить")).click();
        $(byText(cssText)).parent().$(cssSelector).shouldBe(Condition.visible, Duration.ofSeconds(20)).shouldHave(Condition.exactText(resultText));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/EmptyForm.csv")
    public void emptyFormTest(String purchaseMethod) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Продолжить")).click();
        $(byText("Номер карты")).parent().$("span.input__sub").shouldHave(Condition.exactText("Неверный формат"));
        $(byText("Месяц")).parent().$("span.input__sub").shouldHave(Condition.exactText("Неверный формат"));
        $(byText("Год")).parent().$("span.input__sub").shouldHave(Condition.exactText("Неверный формат"));
        $(byText("CVC/CVV")).parent().$("span.input__sub").shouldHave(Condition.exactText("Неверный формат"));
        $(byText("Владелец")).parent().$("span.input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/FirstInvalidCardNumber.csv")
    public void firstInvalidCardNumberTest(String purchaseMethod, String cardNumber) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Номер карты")).parent().$("input.input__control").setValue(cardNumber);
        $(byText("Месяц")).parent().$("input.input__control").setValue("12");
        $(byText("Год")).parent().$("input.input__control").setValue("25");
        $(byText("Владелец")).parent().$("input.input__control").setValue("Dmitry Ivanov");
        $(byText("CVC/CVV")).parent().$("input.input__control").setValue("335");
        $(byText("Продолжить")).click();
        $(byText("Номер карты")).parent().$("span.input__sub").shouldHave(Condition.exactText("Неверный формат"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/SecondInvalidCardNumber.csv")
    public void secondInvalidCardNumberTest(String purchaseMethod, String cardNumber) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Номер карты")).parent().$("input.input__control").setValue(cardNumber);
        $(byText("Месяц")).parent().$("input.input__control").setValue("12");
        $(byText("Год")).parent().$("input.input__control").setValue("25");
        $(byText("Владелец")).parent().$("input.input__control").setValue("Dmitry Ivanov");
        $(byText("CVC/CVV")).parent().$("input.input__control").setValue("335");
        $(byText("Продолжить")).click();
        $(byText("Ошибка")).parent().$("div.notification__content").shouldBe(Condition.visible, Duration.ofSeconds(20)).shouldHave(Condition.exactText("Ошибка! Банк отказал в проведении операции."));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/InvalidMonth.csv")
    public void invalidMonthTest(String purchaseMethod, String month, String exactText) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Номер карты")).parent().$("input.input__control").setValue("4444444444444441");
        $(byText("Месяц")).parent().$("input.input__control").setValue(month);
        $(byText("Год")).parent().$("input.input__control").setValue("25");
        $(byText("Владелец")).parent().$("input.input__control").setValue("Dmitry Ivanov");
        $(byText("CVC/CVV")).parent().$("input.input__control").setValue("335");
        $(byText("Продолжить")).click();
        $(byText("Месяц")).parent().$("span.input__sub").shouldHave(Condition.exactText(exactText));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/InvalidYear.csv")
    public void invalidYearTest(String purchaseMethod, String year, String exactText) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Номер карты")).parent().$("input.input__control").setValue("4444444444444441");
        $(byText("Месяц")).parent().$("input.input__control").setValue("12");
        $(byText("Год")).parent().$("input.input__control").setValue(year);
        $(byText("Владелец")).parent().$("input.input__control").setValue("Dmitry Ivanov");
        $(byText("CVC/CVV")).parent().$("input.input__control").setValue("335");
        $(byText("Продолжить")).click();
        $(byText("Год")).parent().$("span.input__sub").shouldHave(Condition.exactText(exactText));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/InvalidCVC.csv")
    public void invalidCvcTest(String purchaseMethod, String cvc) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Номер карты")).parent().$("input.input__control").setValue("4444444444444441");
        $(byText("Месяц")).parent().$("input.input__control").setValue("12");
        $(byText("Год")).parent().$("input.input__control").setValue("23");
        $(byText("Владелец")).parent().$("input.input__control").setValue("Dmitry Ivanov");
        $(byText("CVC/CVV")).parent().$("input.input__control").setValue(cvc);
        $(byText("Продолжить")).click();
        $(byText("CVC/CVV")).parent().$("span.input__sub").shouldHave(Condition.exactText("Неверный формат"));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/InvalidName.csv")
    public void invalidNameTest(String purchaseMethod, String name) {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080/");
        $(byText(purchaseMethod)).click();
        $(byText("Номер карты")).parent().$("input.input__control").setValue("4444444444444441");
        $(byText("Месяц")).parent().$("input.input__control").setValue("12");
        $(byText("Год")).parent().$("input.input__control").setValue("23");
        $(byText("Владелец")).parent().$("input.input__control").setValue(name);
        $(byText("CVC/CVV")).parent().$("input.input__control").setValue("999");
        $(byText("Продолжить")).click();
        $(byText("CVC/CVV")).parent().$("span.input__sub").shouldHave(Condition.exactText("Неверный формат"));
    }


}