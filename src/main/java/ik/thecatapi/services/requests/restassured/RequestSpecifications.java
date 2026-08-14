package ik.thecatapi.services.requests.restassured;

import ik.thecatapi.config.ApiProperties;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.nio.charset.StandardCharsets;
import java.util.Set;

public final class RequestSpecifications {

    private static final LogConfig logConfig = LogConfig.logConfig()
            .blacklistHeaders(Set.of(ApiProperties.getAuthHeaderName()));
    private static final RestAssuredConfig assuredConfig = RestAssured.config()
            .logConfig(logConfig);

    private static RequestSpecifications instance;

    private RequestSpecifications() {
    }

    public static synchronized RequestSpecifications getInstance() {
        if (instance == null) {
            instance = new RequestSpecifications();
        }
        return instance;
    }

    public RequestSpecification getGetRequestSpecification(String baseUri, String basePath) {
        return new RequestSpecBuilder()
                .setConfig(assuredConfig)
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL)
                .build();
    }

    public RequestSpecification getPostRequestSpecification(String baseUri, String basePath) {
        return new RequestSpecBuilder()
                .setConfig(assuredConfig)
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8))
                .build();
    }

    public RequestSpecification getDeleteRequestSpecification(String baseUri, String basePath) {
        return new RequestSpecBuilder()
                .setConfig(assuredConfig)
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .log(LogDetail.ALL)
                .setContentType(ContentType.JSON.withCharset(StandardCharsets.UTF_8))
                .build();
    }
}
