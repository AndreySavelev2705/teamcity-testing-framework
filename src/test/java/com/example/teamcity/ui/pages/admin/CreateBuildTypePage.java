package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Класс описывающий веб страницу создания билд тайпа.
 *
 * @author Andrey Savelev
 */
public class CreateBuildTypePage extends CreateBasePage {
    private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";

    public SelenideElement buildTypeNameWarrning = $("span[id*='error_buildTypeName']");

    public static CreateBuildTypePage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE), CreateBuildTypePage.class);
    }

    /**
     * Метод открывает форму создания билд тайпа.
     *
     * @return форму создания билд тайпа.
     */
    public CreateBuildTypePage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    /**
     * Метод устанавливает указанные имя проекта и билдтайп вместо сгенерированных автомтаически самой TeamCity.
     *
     * @param buildTypeName имя билд тайпа.
     * @return страницу со списком проектов.
     */
    public CreateBuildTypePage setupBuildType(String buildTypeName) {
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        return this;
    }
}
