package ik.thecatapi.services.requests.restassured;

import io.qameta.allure.Step;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredHttpClient {
    private final RequestSpecification requestSpecification;

    public RestAssuredHttpClient() {
        String baseUri = "https://api.thecatapi.com"; // TODO: impl config
        String basePath = "/v1";
        this.requestSpecification = RequestSpecifications.getInstance().getSpecification(baseUri, basePath);
    }

    @Step("HTTP GET {uriPath}")
    public Response get(String uriPath, Map<String, ?> queryParams, Headers headers) {
        return given()
                    .spec(requestSpecification)
                    .queryParams(queryParams)
                    .headers(headers)
                .when()
                    .get(uriPath);
    }
}
