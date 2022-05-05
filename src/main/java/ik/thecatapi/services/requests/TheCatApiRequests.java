package ik.thecatapi.services.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import ik.thecatapi.config.EndpointPath;
import ik.thecatapi.models.requests.ResponseBodyBreed;
import ik.thecatapi.models.requests.breed_search.BreedsSearchRequest;
import ik.thecatapi.models.requests.breed_search.BreedsSearchResponse;
import ik.thecatapi.models.requests.images_search.ImagesSearchRequest;
import ik.thecatapi.models.requests.images_search.ImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import ik.thecatapi.services.requests.restassured.RestAssuredHttpClient;
import io.qameta.allure.Attachment;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class TheCatApiRequests {
    private final RestAssuredHttpClient httpClient;

    public TheCatApiRequests() {
        this.httpClient = new RestAssuredHttpClient();
    }

    @Attachment("Response Object")
    public BreedsSearchResponse requestGetBreedsSearch(BreedsSearchRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointPath.BREEDS_SEARCH.getUriPath(), queryParams, headers);
        BreedsSearchResponse response = new BreedsSearchResponse(restResponse.getStatusCode());
        ResponseBodyBreed[] bodyBreeds = restResponse.as(ResponseBodyBreed[].class);
        response.setBody(Arrays.asList(bodyBreeds));
        log.info("{}", response);
        return response;
    }

    @Attachment("Response Object")
    public ImagesSearchResponse requestGetImagesSearch(ImagesSearchRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointPath.IMAGES_SEARCH.getUriPath(), queryParams, headers);
        ImagesSearchResponse response = new ImagesSearchResponse(restResponse.getStatusCode());
        ResponseBodyImage[] bodyImages = restResponse.as(ResponseBodyImage[].class);
        response.setBody(Arrays.asList(bodyImages));
        log.info("{}", response);
        return response;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> transformRequestQueryParamsToMapObject(Object queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(queryParams, Map.class);
    }
}
