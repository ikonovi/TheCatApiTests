package ik.thecatapi.controllers.requests;

import ik.thecatapi.config.EndpointUriPath;
import ik.thecatapi.models.requests.categories.GetCategoriesRequest;
import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.categories.ResponseBodyCategory;
import io.qameta.allure.Attachment;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CategoriesRequestController extends AbstractRequestController {

    @Attachment("Response Object")
    public GetCategoriesResponse get(GetCategoriesRequest request) {
        Header authHeader = super.getAuthorizationHeader(request);
        Headers headers = new Headers(authHeader);
        Response restResponse = httpClient.get(EndpointUriPath.CATEGORIES.getValue(), headers);
        GetCategoriesResponse response = new GetCategoriesResponse(restResponse.getStatusCode());
        ResponseBodyCategory[] bodyCategories = restResponse.as(ResponseBodyCategory[].class);
        response.setBody(Arrays.asList(bodyCategories));
        log.info("{}", response);
        return response;
    }
}
