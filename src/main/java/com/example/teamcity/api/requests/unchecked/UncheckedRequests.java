package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

import java.util.EnumMap;

/**
 * Класс-фасад для непроверяемых запросов.
 *
 * @author Andrey Savelev
 */
public class UncheckedRequests {
    private final EnumMap<Endpoint, UncheckedBase> requests = new EnumMap<>(Endpoint.class);

    public UncheckedRequests(RequestSpecification spec) {
        for (var endpoint: Endpoint.values()) {
            requests.put(endpoint, new UncheckedBase(spec, endpoint));
        }
    }

    /**
     * Метод возвращает непроверяемый запрос по эндпоинту.
     *
     * @param endpoint ключ по которому ищется запрос.
     * @return непроверяемый запрос.
     */
    public UncheckedBase getRequest(Endpoint endpoint) {
        return requests.get(endpoint);
    }
}
