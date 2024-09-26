package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;

/**
 * Класс, который хранит в себе все Rest Assured спецификации.
 *
 * @author Andrey Savelev
 */
public class Specifications {
    private static Specifications spec;
    private final String host = Config.getProperty("host");
    private final String port = Config.getProperty("port");

    private Specifications() {}

    public static Specifications getSpec() {
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        return requestBuilder.addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()));
    }

    /**
     * Метод создает и возвращает спецификацию без авторизации.
     *
     * @return спецификация
     */
    public RequestSpecification unauthSpec() {
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
    public RequestSpecification authSpec(User user) {
        return reqBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setBaseUri(String.format("http://%s:%s@%s:%s/", user.getUsername(), user.getPassword(), host, port))
                .build();
    }
}
