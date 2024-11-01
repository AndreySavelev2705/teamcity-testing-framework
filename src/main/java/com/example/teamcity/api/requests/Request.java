package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

/**
 * Класс, который описывает меняющиеся параметры запроса:
 *      -спецификация
 *      -эндпоинт (relative URL, model)
 *
 * @author Andrey Savelev
 */
public class Request {
    protected final RequestSpecification spec;
    protected final Endpoint endpoint;

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
