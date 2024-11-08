package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.testData.TestDataStorage;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

/**
 * Класс, который описывает CRUD эндпоинт для проверяемых запросов
 *
 * @param <T> Наследник базовой модели, которая используется в запросах.
 *
 * @author Andrey Savelev
 */
@SuppressWarnings("unchecked")
public final class CheckedBase<T extends BaseModel> extends Request implements CrudInterface {
    private final UncheckedBase uncheckedBase;

    public CheckedBase(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
        this.uncheckedBase = new UncheckedBase(spec, endpoint);
    }

    /**
     * Метод создающий HTTP запрос на создание модели.
     *
     * @param model объект модели, на основе которого будет создано тело запроса.
     * @return созданная модель.
     */
    @Override
    public T create(BaseModel model) {
        var createdModel = (T) uncheckedBase
                .create(model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());

        TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
        return createdModel;
    }

    /**
     * Метод создающий HTTP запрос на получение модели.
     *
     * @param locator локатор модели.
     * @return полученная модель.
     */
    @Override
    public T read(String locator) {
        return (T) uncheckedBase
                .read(locator)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    /**
     * Метод создающий HTTP запрос на изменение модели.
     *
     * @param locator локатор модели.
     * @param model модель на которую нужно обновить существующую модель.
     * @return обновленная модель.
     */
    @Override
    public T update(String locator, BaseModel model) {
        return (T) uncheckedBase
                .update(locator, model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    /**
     * Метод создающий HTTP запрос на удаление модели
     *
     * @param locator локатор модели.
     * @return ответ от сервера.
     */
    @Override
    public String delete(String locator) {
        return uncheckedBase
                .delete(locator)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
