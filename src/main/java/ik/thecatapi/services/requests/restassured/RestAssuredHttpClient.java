package ik.thecatapi.services.requests.restassured;

import io.qameta.allure.Step;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredHttpClient {
    private final RequestSpecification getReqSpec;
    private final RequestSpecification postReqSpec;

    public RestAssuredHttpClient() {
        String baseUri = "https://api.thecatapi.com"; // TODO: impl config
        String basePath = "/v1";
        this.getReqSpec = RequestSpecifications.getInstance().getGetRequestSpecification(baseUri, basePath);
        this.postReqSpec = RequestSpecifications.getInstance().getPostRequestSpecification(baseUri, basePath);
    }

    @Step("HTTP GET {uriPath}")
    public Response get(String uriPath, Map<String, ?> queryParams, Headers headers) {
        return given()
                    .spec(getReqSpec)
                    .queryParams(queryParams)
                    .headers(headers)
                .when()
                    .get(uriPath);
    }

    @Step("HTTP POST {uriPath}")
    public Response post(String uriPath, Headers headers, Object body) {
        return given()
                    .spec(postReqSpec)
                    .headers(headers)
                    .body(body)
                .when()
                    .post(uriPath);
    }
}
