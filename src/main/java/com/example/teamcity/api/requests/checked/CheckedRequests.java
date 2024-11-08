package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import io.restassured.specification.RequestSpecification;

import java.util.EnumMap;

/**
 * Класс-фасад для проверяемых запросов.
 *
 * @author Andrey Savelev
 */
public class CheckedRequests {
    private final EnumMap<Endpoint, CheckedBase<?>> requests = new EnumMap<>(Endpoint.class);

    public CheckedRequests(RequestSpecification spec) {
        for (var endpoint: Endpoint.values()) {
            requests.put(endpoint, new CheckedBase<>(spec, endpoint));
        }
    }

    /**
     * Метод возвращает проверяемый запрос по эндпоинту.
     *
     * @param endpoint ключ по которому ищется запрос.
     * @return проверяемый запрос.
     * @param <T> тип модели.
     */
    public <T extends BaseModel> CheckedBase<T> getRequest(Endpoint endpoint) {
        return (CheckedBase<T>) requests.get(endpoint);
    }
}
