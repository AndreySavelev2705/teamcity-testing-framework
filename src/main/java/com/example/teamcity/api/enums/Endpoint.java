package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.buildType.BuildType;
import com.example.teamcity.api.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс-перечисление всех существующих в рамках проекта эндпоинтов.
 *
 * @author Andrey Savelev
 */
@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECTS("/app/rest/projects", Project.class),
    USERS("/app/rest/users", User.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass;
}
