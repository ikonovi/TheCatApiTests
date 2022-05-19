package ik.thecatapi.requests.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import ik.thecatapi.services.requests.restassured.RestAssuredHttpClient;

import java.util.Map;

public abstract class TheCatApiRequest {
    protected final RestAssuredHttpClient httpClient;

    protected TheCatApiRequest() {
        this.httpClient = new RestAssuredHttpClient();
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> transformRequestQueryParamsToMapObject(Object queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(queryParams, Map.class);
    }
}
