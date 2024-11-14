package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.user.User;

import static com.codeborne.selenide.Selenide.$;

/**
 * Класс, который описывает веб страницу авторизации.
 *
 * @author Andrey Savelev
 */
public class LoginPage extends BasePage {
    private static final String LOGIN_URL = "/login.html";

    private SelenideElement inputUsername = $("#username");
    private SelenideElement inputPassword = $("#password");
    private SelenideElement inputSubmitLogin = $(".loginButton");

    public SelenideElement inputSubmitLogin2 = $(".loginButton");

    /**
     * Метод открывает страницу авторизации.
     *
     * @return страницу авторизации.
     */
    public static LoginPage open() {
        return Selenide.open(LOGIN_URL, LoginPage.class);
    }

    /**
     * Метод выполняет авторизацию юзера в системе.
     *
     * @param user юзер под которым производится авторизация
     * @return страницу со списком проектов.
     */
    public ProjectsPage login(User user) {
        // Метод val вместо clear, sendKeys
        inputUsername.val(user.getUsername());
        inputPassword.val(user.getPassword());
        inputSubmitLogin.click();

        return Selenide.page(ProjectsPage.class);
    }
}