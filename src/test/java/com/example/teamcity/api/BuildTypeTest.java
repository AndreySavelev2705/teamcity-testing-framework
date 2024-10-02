package com.example.teamcity.api;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.buildType.BuildType;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build Type.", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckedRequest.<BuildType>getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckedRequest.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId().toString());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct.");
    }

    @Test(description = "User should not be able to create two build type with the same id.", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckedRequest.<BuildType>getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));;
    }

    @Test(description = "Project admin should be able to create build type for their project.", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        step("Step 1. Create user");
        step("Step 2. Create project by user");
        step("Step 3. Grand user PROJECT_ADMIN role in project");

        step("Step 4. Create build type #1 for project by user (PROJECT_ADMIN)");
        step("Step 5. Check build type was created successfully");
    }

    @Test(description = "Project admin should not be able to create build type for not their project.", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        step("Step 1. Create user #1");
        step("Step 2. Create project #1 by user #1");
        step("Step 3. Grand user #1 PROJECT_ADMIN role in project #1");

        step("Step 4. Create user #2");
        step("Step 5. Create project #2 by user #2");
        step("Step 6. Grand user #2 PROJECT_ADMIN role in project #2");

        step("Step 7. Create build type #1 for project #1 by user #2");
        step("Step6. Check build type #1 was not created with bad forbidden code");
    }
}
