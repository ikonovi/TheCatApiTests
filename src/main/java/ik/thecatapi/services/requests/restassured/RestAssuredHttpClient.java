package ik.thecatapi.services.requests.restassured;

import ik.thecatapi.config.ApiProperties;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredHttpClient {
    private final RequestSpecification getReqSpec;
    private final RequestSpecification postReqSpec;
    private final RequestSpecification deleteReqSpec;

    public RestAssuredHttpClient() {
        String baseUri = ApiProperties.getTheCatApiBaseUri();
        String basePath = ApiProperties.getTheCatApiBasePath();
        this.getReqSpec = RequestSpecifications.getInstance().getGetRequestSpecification(baseUri, basePath);
        this.postReqSpec = RequestSpecifications.getInstance().getPostRequestSpecification(baseUri, basePath);
        this.deleteReqSpec = RequestSpecifications.getInstance().getDeleteRequestSpecification(baseUri, basePath);
    }

    @Step("HTTP GET {uriPath}")
    public Response get(String uriPath, @Param(mode = Parameter.Mode.MASKED) Headers headers) {
        return given()
                .spec(getReqSpec)
                .headers(headers)
                .when()
                .get(uriPath);
    }

    @Step("HTTP GET {uriPath}")
    public Response get(String uriPath, Map<String, ?> queryParams, @Param(mode = Parameter.Mode.MASKED) Headers headers) {
        return given()
                    .spec(getReqSpec)
                    .queryParams(queryParams)
                    .headers(headers)
                .when()
                    .get(uriPath);
    }

    @Step("HTTP POST {uriPath}")
    public Response post(String uriPath, @Param(mode = Parameter.Mode.MASKED) Headers headers, Object body) {
        return given()
                    .spec(postReqSpec)
                    .headers(headers)
                    .body(body)
                .when()
                    .post(uriPath);
    }

    @Step("HTTP DELETE {uriPath}")
    public Response delete(String uriPath, @Param(mode = Parameter.Mode.MASKED) Headers headers) {
        return given()
                    .spec(deleteReqSpec)
                    .headers(headers)
                .when()
                    .delete(uriPath);
    }
}
