package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Класс описывающий веб страницу создания проекта.
 *
 * @author Andrey Savelev
 */
public class CreateProjectPage extends CreateBasePage {
    private static final String PROJECT_SHOW_MODE = "createProjectMenu";

    private SelenideElement projectNameInput = $("#projectName");
    public SelenideElement projectNameWarrning = $("span[id*='error_projectName']");

    public static CreateProjectPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE), CreateProjectPage.class);
    }

    /**
     * Метод открывает форму создания проекта.
     *
     * @param url - адрес репозитория для проекта.
     * @return форму создания проекта.
     */
    public CreateProjectPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    /**
     * Метод устанавливает указанные имя проекта и билдтайп вместо сгенерированных автомтаически самой TeamCity.
     *
     * @param projectName имя проекта.
     * @param buildTypeName имя билд тайпа.
     * @return страницу со списком проектов.
     */
    public CreateProjectPage setupProject(String projectName, String buildTypeName) {
        projectNameInput.val(projectName);
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        return this;
    }
}
