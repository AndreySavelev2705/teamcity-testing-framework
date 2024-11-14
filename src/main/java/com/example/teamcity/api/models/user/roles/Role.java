package com.example.teamcity.api.models.user.roles;

import com.example.teamcity.api.annotations.Parametrizable;
import com.example.teamcity.api.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, который описывает сущность Роль.
 *
 * @author Andrey Savelev
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends BaseModel {
    @Builder.Default
    @Parametrizable
    private String roleId = "SYSTEM_ADMIN";

    @Builder.Default
    @Parametrizable
    private String scope = "g";
}
