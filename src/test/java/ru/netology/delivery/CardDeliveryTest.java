package ru.netology.delivery;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    private String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // ПОЗИТИВНЫЕ ТЕСТЫ

    @Test
    void shouldSubmitValidForm() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно!\nВстреча успешно забронирована на " + generateDate(3)));
    }

    @Test
    void shouldSubmitFormWithHyphenInName() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").setValue(generateDate(5));
        $("[data-test-id=name] input").setValue("Анна-Мария Петрова-Иванова");
        $("[data-test-id=phone] input").setValue("+79012345678");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно!\nВстреча успешно забронирована на " + generateDate(5)));
    }

    @Test
    void shouldSubmitFormWithDifferentCity() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Казань");
        $("[data-test-id=date] input").setValue(generateDate(7));
        $("[data-test-id=name] input").setValue("Петр Сидоров");
        $("[data-test-id=phone] input").setValue("+79213456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно!\nВстреча успешно забронирована на " + generateDate(7)));
    }

    // НЕГАТИВНЫЕ ТЕСТЫ

    @Test
    void shouldShowErrorWithEmptyCity() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorWithInvalidCity() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Нью-Йорк");
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=city].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldShowErrorWithEmptyDate() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=date] .input__sub")
                .shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void shouldShowErrorWithPastDate() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue("01.01.2020");
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=date] .input__sub")
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldShowErrorWithLessThanThreeDays() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(generateDate(2));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=date] .input__sub")
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldShowErrorWithEmptyName() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorWithEnglishName() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowErrorWithNumbersInName() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Иван123");
        $("[data-test-id=phone] input").setValue("+79123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowErrorWithEmptyPhone() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorWithInvalidPhone() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+7912345678"); // 10 цифр вместо 11
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldShowErrorWithoutAgreement() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(generateDate(3));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79123456789");
        // Чекбокс не ставим!
        $("button.button").click();

        $("[data-test-id=agreement].input_invalid")
                .shouldBe(Condition.visible);
    }
}