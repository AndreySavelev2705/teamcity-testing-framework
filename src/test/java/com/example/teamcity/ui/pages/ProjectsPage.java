package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.ProjectElement;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Класс, который описывает веб страницу со списком проектов.
 *
 * @author Andrey Savelev
 */
public class ProjectsPage extends BasePage {
    private static final String PROJECTS_URL = "/favorite/projects";

    private ElementsCollection favoriteProjectElements = $$("div[class*='Subproject__container']");

    private ElementsCollection projectTreeElements = $$("div[data-test-itemtype='project']");

    private SelenideElement projectBuildTypeElement = $("div[data-test-itemtype='buildType']");

    private SelenideElement spanFavoriteProjects = $("span[class='ProjectPageHeader__title--ih']");

    private SelenideElement header = $(".MainPanel__router--gF > div");

    // ElementCollection -> List<ProjectElement>
    // UI elements -> List<Object>
    // В итоге нам надо ElementCollection -> List<BasePageElement>

    /**
     * Метод открывает страницу со списком проектов.
     *
     * @return страница со списком проектов.
     */
    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    public ProjectsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    /**
     * Метод возвращает список веб элементов-проектов.
     *
     * @return список веб элементов-проектов
     */
    public List<ProjectElement> getFavoriteProjects() {
        return generatePageElements(favoriteProjectElements, ProjectElement::new);
    }

    /**
     * Метод возвращает список веб элементов-проектов.
     *
     * @return список веб элементов-проектов
     */
    public List<ProjectElement> getProjects() {
        return generatePageElements(projectTreeElements, ProjectElement::new);
    }

    public void clickProject(String projectName) {
        var element = projectTreeElements.findBy(text(projectName));
        element.click();
    }

    public void clickProjectBuildType() {
//        var element = projectTrebeElements.findBy(text(projectBuildTypeName));
//        element.click();
        projectBuildTypeElement.click();
    }
}