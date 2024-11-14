package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.ServerAuthSettings;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

/**
 * Класс описывающий запрос к настройкам сервиса аутентификации
 *
 * @author Andrey Savelev
 */
public class ServerAuthRequest {
    private static final String SERVER_AUTH_SETTINGS_URL = "/app/rest/server/authSettings";
    private RequestSpecification spec;

    public ServerAuthRequest(RequestSpecification spec) {
        this.spec = spec;
    }

    /**
     * Метод описывает запрос на получение настроек
     *
     * @return объект настроек
     */
    public ServerAuthSettings read() {
        return RestAssured.given()
                .spec(spec)
                .get(SERVER_AUTH_SETTINGS_URL)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(ServerAuthSettings.class);

    }

    /**
     * Метод описывает запрос на обновление настроек
     *
     * @param authSettings настройки на которые нужно обновить
     * @return  объект настроек
     */
    public ServerAuthSettings update(ServerAuthSettings authSettings) {
        return RestAssured.given()
                .spec(spec)
                .body(authSettings)
                .put(SERVER_AUTH_SETTINGS_URL)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(ServerAuthSettings.class);
    }
}
