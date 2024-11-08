package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Класс, который описывает веб страницу билда.
 *
 * @author Andrey Savelev
 */
public class BuildTypePage extends BasePage {
    private static final String BUILD_TYPE_URL = "/admin/discoverRunners.html?init=1&id=buildType:%s_%s";

    public SelenideElement title = $("h2[class*='noBorder']");

    /**
     * Метод открывает страницу с данными билда.
     *
     * @param projectName - имя проекта, чей билд смотрим.
     * @param buildTypeName - имя билда, чьи данные будут отображаться на странице.
     * @return страница проекта.
     */
    public static BuildTypePage open(String projectName, String buildTypeName) {
        var formatedBuildTypeName = buildTypeName.replace("_", "");
        return Selenide.open(BUILD_TYPE_URL.formatted(projectName, formatedBuildTypeName), BuildTypePage.class);
    }
}