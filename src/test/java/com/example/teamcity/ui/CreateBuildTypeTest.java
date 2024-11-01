package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.buildType.BuildType;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.BuildTypePage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUiTest {
    @Test(description = "User should be able to create a projects buildType", groups = {"Positive"})
    public void userCreatesAndStartProjectsBuildType() {
        // Step 1. Login as user
        loginAs(testData.getUser());
        var userCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Step 2. Create project by user
        var project = userCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Step 3. Open Create BuildType Page` (http://localhost:8111/admin/createObjectMenu.html)
        CreateBuildTypePage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                    .setupBuildType(testData.getBuildType().getName());

        // Step 4. Check that entity(build type) was successfully created with correct data on API level
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);

        // Step 5. Check that buildType is created
        BuildTypePage.open(project.getId(), testData.getBuildType().getName())
                .title.shouldHave(Condition.text("Auto-detected Build Steps"));
    }

    @Test(description = "User should not be able to create a projects buildType without name", groups = {"Positive"})
    public void userCantCreatesAndStartProjectsBuildType() {
        // Step 1. Login as user
        loginAs(testData.getUser());
        var userCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Step 2. Create project by user
        userCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Step 3. Open Create BuildType Page` (http://localhost:8111/admin/createObjectMenu.html)
        var buildTypePage = CreateBuildTypePage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                    .setupBuildType("");

        // Step 4. Check that entity(build type) was not created with on API level
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES).read(testData.getBuildType().getName())
                .then().assertThat()
                .statusCode(SC_NOT_FOUND);

        // Step 5. Check that there is warring on create BuildType page.
        buildTypePage.buildTypeNameWarrning.shouldBe(Condition.text("Build configuration name must not be empty"));
    }
}