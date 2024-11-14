package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static io.qameta.allure.Allure.step;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {
    @Test(description = "User should be able to create project", groups = {"Positive"})
    public void userCreatesProject() {
        // подготовка окружения
        step("Login as user");
        loginAs(testData.getUser());

        // взаимодействие с UI
        // Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)
        CreateProjectPage.open("_Root")
                .createForm(REPO_URL)
                    .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        // Check that all entities (project, build type) was successfully created with correct data on API level
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        // проверка состояния UI
        // (корректность считывания данных и отображение данных на UI)
        // Check that project is visible on Projects Page (http://localhost:8111/favorite/projects)
        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));

        var projectExist = ProjectsPage.open().getFavoriteProjects().stream()
                .anyMatch(project -> project.getName().getText().equals(testData.getProject().getName()));
        softy.assertTrue(projectExist);
    }

    @Test(description = "User should not be able to create a project without name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        // подготовка окружения
        //Login as user"
        loginAs(testData.getUser());

        //"Check number of projects"
        var projectsCount = ProjectsPage.open().getProjects().size();

        // взаимодействие с UI
        //Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)
        var createProjectPage = CreateProjectPage.open("_Root")
                .createForm(REPO_URL)
                    .setupProject("", testData.getBuildType().getName());

        // проверка состояния UI
        // (корректность считывания данных и отображение данных на UI)
        // Check that error appears `Project name must not be empty
        createProjectPage.projectNameWarrning.shouldBe(Condition.text("Project name must not be empty"));

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        // Check that number of projects did not change"
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS).read(testData.getProject().getName())
                .then().assertThat()
                .statusCode(SC_NOT_FOUND);

        var actualProjectsCount = ProjectsPage.open().getProjects().size();
        softy.assertTrue(actualProjectsCount == projectsCount);
    }
}