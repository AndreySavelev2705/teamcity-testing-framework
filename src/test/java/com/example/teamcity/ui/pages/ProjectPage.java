package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Класс, который описывает веб страницу проекта.
 *
 * @author Andrey Savelev
 */
public class ProjectPage extends BasePage {
    private static final String PROJECT_URL = "/project/%s";

    public SelenideElement title = $("span[class*='ProjectPageHeader']");

    /**
     * Метод открывает страницу с данными проекта.
     *
     * @param projectId - id проекта, чьи данные будут отображаться на странице.
     * @return страница проекта.
     */
    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }
}