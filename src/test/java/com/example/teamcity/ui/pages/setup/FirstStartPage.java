package com.example.teamcity.ui.pages.setup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

/**
 * Класс, который описывает веб страницу первого старта TeamCity.
 *
 * @author Andrey Savelev
 */
public class FirstStartPage extends BasePage {
    private final SelenideElement restoreButton = $("#restoreButton");
    private final SelenideElement proceedButton = $("#proceedButton");
    private final SelenideElement dbTypeSelect = $("#dbType");
    private final SelenideElement acceptLicenseCheckbox = $("#accept");
    private final SelenideElement submitButton = $("input[type='submit']");

    public FirstStartPage() {
        restoreButton.shouldBe(Condition.visible, LONG_WAITING);
    }

    /**
     * Метод открывает страницу первого старта
     *
     * @return объект страницы первого старта
     */
    public static FirstStartPage open() {
        return Selenide.open("/", FirstStartPage.class);
    }

    /**
     * Метод выполняет шаги для первого старта приложения
     *
     * @return объект страницы первого старта
     */
    public FirstStartPage setupFirstStart() {
        proceedButton.click();
        dbTypeSelect.shouldBe(Condition.visible, LONG_WAITING);
        proceedButton.click();
        acceptLicenseCheckbox.should(Condition.exist, LONG_WAITING).scrollTo().click();
        submitButton.click();
        return this;
    }
}
