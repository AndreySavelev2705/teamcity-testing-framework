package com.example.teamcity.api.models.buildType;

import com.example.teamcity.api.annotations.Parametrizable;
import com.example.teamcity.api.annotations.Random;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.buildType.steps.Steps;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс описывающий сущность билда (сборки).
 *
 * @author Andrey Savelev
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildType extends BaseModel {
    @Random
    @Parametrizable
    private String id;

    @Random
    private String name;
    private Project project;
    private Steps steps;
}
