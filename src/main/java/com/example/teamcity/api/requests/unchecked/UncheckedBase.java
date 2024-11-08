package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Класс, который описывает CRUD эндпоинт для непроверяемых запросов.
 *
 * @author Andrey Savelev
 */
public class UncheckedBase extends Request implements CrudInterface {
    public UncheckedBase(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
    }

    /**
     * Метод создающий HTTP запрос на создание модели.
     *
     * @param model объект модели, на основе которого будет создано тело запроса.
     * @return созданная модель.
     */
    @Override
    public Response create(BaseModel model) {
        return RestAssured
                .given()
                .spec(spec)
                .body(model)
                .post(endpoint.getUrl());
    }

    /**
     * Метод создающий HTTP запрос на получение модели.
     *
     * @param locator локатор модели.
     * @return полученная модель.
     */
    @Override
    public Response read(String locator) {
        return RestAssured
                .given()
                .spec(spec)
                .get(endpoint.getUrl() + "/" + locator);
    }

    /**
     * Метод создающий HTTP запрос на изменение модели.
     *
     * @param locator локатор модели.
     * @param model модель на которую нужно обновить существующую модель.
     * @return обновленная модель.
     */
    @Override
    public Response update(String locator, BaseModel model) {
        return RestAssured
                .given()
                .spec(spec)
                .body(model)
                .put(endpoint.getUrl() + "/id:" + locator);
    }

    /**
     * Метод создающий HTTP запрос на удаление модели
     *
     * @param locator локатор модели.
     * @return ответ от сервера.
     */
    @Override
    public Response delete(String locator) {
        return RestAssured
                .given()
                .spec(spec)
                .delete(endpoint.getUrl() + "/" + locator);
    }
}
