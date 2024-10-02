package com.example.teamcity.api.models.testData;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.buildType.BuildType;
import com.example.teamcity.api.models.user.User;
import lombok.Data;

@Data
public class TestData {
    private Project project;
    private User user;
    private BuildType buildType;
}
