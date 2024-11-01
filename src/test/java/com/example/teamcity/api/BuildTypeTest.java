package com.example.teamcity.api;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.buildType.BuildType;
import com.example.teamcity.api.models.user.User;
import com.example.teamcity.api.models.user.roles.Roles;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build Type.", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        // Step 1. Create user
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Step 2. Create project by user
        userCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Step 3. Create buildType for project by user
        userCheckedRequest.<BuildType>getRequest(BUILD_TYPES).create(testData.getBuildType());
        var createdBuildType = userCheckedRequest.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());

        // Step 4. Check buildType was created successfully with correct data
        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct.");
    }

    @Test(description = "User should not be able to create two build type with the same id.", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        // Step 1. Create user
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Step 2. Create project by user
        userCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Step 3. Create buildType2 with same id as buildType1 for project by user
        userCheckedRequest.<BuildType>getRequest(BUILD_TYPES).create(testData.getBuildType());

        // Step 4. Check buildType2 was not created with bad request code
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create build type for their project.", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        //Step 1. Create user
        var user = superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        var userCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        //Step 2. Create project by user
        userCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Step 3. Grand user PROJECT_ADMIN role in project
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).update(user.getId(), testData.getUser());

        // Step 4. Create build type #1 for project by user (PROJECT_ADMIN)
        var response = new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES).create(testData.getBuildType());

        // Step 5. Check build type was created successfully
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(description = "Project admin should not be able to create build type for not their project.", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        // Step 1. Create user #1
        var firstUser = superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        var firstUserCheckedRequest = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        // Step 2. Create project #1 by user #1
        var firstProject = firstUserCheckedRequest.<Project>getRequest(PROJECTS).create(testData.getProject());

        // Step 3. Grand user #1 PROJECT_ADMIN role in project #1
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).update(firstUser.getId(), testData.getUser());

        // Step 4. Create user #2
        var secondTestData = generate();
        var secondUser = superUserCheckRequests.<User>getRequest(USERS).create(secondTestData.getUser());
        var secondUserCheckedRequest = new CheckedRequests(Specifications.authSpec(secondTestData.getUser()));

        // Step 5. Create project #2 by user #2
        secondUserCheckedRequest.<Project>getRequest(PROJECTS).create(secondTestData.getProject());

        // Step 6. Grand user #2 PROJECT_ADMIN role in project #2
        secondTestData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + secondTestData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).update(secondUser.getId(), secondTestData.getUser());

        // Step 7. Create build type #1 for project #1 by user #2
        secondTestData.getBuildType().setProject(firstProject);
        var response = new UncheckedBase(Specifications.authSpec(secondUser), BUILD_TYPES).create(secondTestData.getBuildType());

        // Step 8. Check build type #1 was not created with bad forbidden code
        response.then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(Matchers.containsString("Incorrect username or password"));
    }
}
