package ik.thecatapi.controllers.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import ik.thecatapi.models.requests.AuthorizationHeader;
import ik.thecatapi.models.requests.CommonRequest;
import ik.thecatapi.services.requests.restassured.RestAssuredHttpClient;
import io.restassured.http.Header;

import java.util.Map;

public abstract class AbstractRequestController {
    protected final RestAssuredHttpClient httpClient;

    protected AbstractRequestController() {
        this.httpClient = new RestAssuredHttpClient();
    }

    protected Header getAuthorizationHeader(CommonRequest request) {
        AuthorizationHeader header = request.getAuthorizationHeader();
        return new Header(header.getName(), header.getValue());
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> transformRequestQueryParamsToMapObject(Object queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(queryParams, Map.class);
    }
}
