package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.user.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;

/**
 * Класс, который хранит в себе все Rest Assured спецификации связанные с авторизацией.
 *
 * @author Andrey Savelev
 */
public class Specifications {
    private static Specifications spec;
    private final static String HOST = Config.getProperty("host");
    private final static String PORT = Config.getProperty("port");
    private final static String SUPER_USER_TOKEN = Config.getProperty("superUserToken");

    private static RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        return requestBuilder.addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()));
    }

    public static RequestSpecification superUserSpec() {
        return reqBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBaseUri(String.format("http://%s:%s@%s:%s/", "", SUPER_USER_TOKEN, HOST, PORT))
                .build();
    }

    /**
     * Метод создает и возвращает спецификацию без авторизации.
     *
     * @return спецификация
     */
    public static RequestSpecification unauthSpec() {
        return reqBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    /**
     * Метод создает и возвращает спецификацию с авторизацией.
     *
     * @param user пользователь, которого нужно авторизовать.
     * @return спецификация
     */
    public static RequestSpecification authSpec(User user) {
        return reqBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBaseUri(String.format("http://%s:%s@%s:%s/", user.getUsername(), user.getPassword(), HOST, PORT))
                .build();
    }
}
