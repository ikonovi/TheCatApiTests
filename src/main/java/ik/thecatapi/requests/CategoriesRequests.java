package ik.thecatapi.requests;

import ik.thecatapi.config.EndpointUriPath;
import ik.thecatapi.models.requests.AuthorizationHeader;
import ik.thecatapi.models.requests.categories.GetCategoriesRequest;
import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.categories.ResponseBodyCategory;
import ik.thecatapi.requests.common.TheCatApiRequest;
import io.qameta.allure.Attachment;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CategoriesRequests extends TheCatApiRequest {

    @Attachment("Response Object")
    public GetCategoriesResponse get(GetCategoriesRequest request) {
        AuthorizationHeader authHeader = request.getAuthorizationHeader();
        Header authorizationHeader = new Header(authHeader.getName(), authHeader.getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointUriPath.CATEGORIES.getValue(), headers);
        GetCategoriesResponse response = new GetCategoriesResponse(restResponse.getStatusCode());
        ResponseBodyCategory[] bodyCategories = restResponse.as(ResponseBodyCategory[].class);
        response.setBody(Arrays.asList(bodyCategories));
        log.info("{}", response);
        return response;
    }
}
