package ik.thecatapi.services.requests.restassured;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecifications {
    private static RequestSpecifications instance;

    private RequestSpecifications() {
    }

    public synchronized static RequestSpecifications getInstance() {
        if (instance == null) {
            instance = new RequestSpecifications();
        }
        return instance;
    }

    public RequestSpecification getSpecification(String baseUri, String basePath) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL)
                .build();
    }
}
