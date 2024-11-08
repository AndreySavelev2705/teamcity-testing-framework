package com.example.teamcity.api.models.buildType.steps;

import com.example.teamcity.api.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс описывающий сущность шаг.
 *
 * @author Andrey Savelev
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Step extends BaseModel {
    private String id;
    private String name;

    @Builder.Default
    private String type = "simpleRunner";
}
