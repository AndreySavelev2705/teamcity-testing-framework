package com.example.teamcity.api.models.user;

import com.example.teamcity.api.annotations.Random;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.user.roles.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс описывающий сущность пользователя системы.
 *
 * @author Andrey Savelev
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseModel {
    private String id;

    @Random
    private String username;

    @Random
    private String password;

    private Roles roles;
}
