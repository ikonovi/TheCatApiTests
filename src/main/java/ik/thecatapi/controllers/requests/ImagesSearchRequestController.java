package ik.thecatapi.controllers.requests;

import ik.thecatapi.config.EndpointUriPath;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequest;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import io.qameta.allure.Attachment;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class ImagesSearchRequestController extends AbstractRequestController {

    @Attachment("Response Object")
    public GetImagesSearchResponse get(GetImagesSearchRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());
        Header authHeader = super.getAuthorizationHeader(request);
        Headers headers = new Headers(authHeader);
        Response restResponse = httpClient.get(EndpointUriPath.IMAGES_SEARCH.getValue(), queryParams, headers);
        GetImagesSearchResponse response = new GetImagesSearchResponse(restResponse.getStatusCode());
        ResponseBodyImage[] bodyImages = restResponse.as(ResponseBodyImage[].class);
        response.setBody(Arrays.asList(bodyImages));
        log.info("{}", response);
        return response;
    }
}
