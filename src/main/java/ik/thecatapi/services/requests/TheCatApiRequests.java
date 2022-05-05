package ik.thecatapi.services.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import ik.thecatapi.config.EndpointPath;
import ik.thecatapi.models.requests.breed_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.breed_search.GetBreedsSearchRequest;
import ik.thecatapi.models.requests.breed_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.favourites.*;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequest;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
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
    public GetBreedsSearchResponse requestGetBreedsSearch(GetBreedsSearchRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointPath.BREEDS_SEARCH.getUriPath(), queryParams, headers);
        GetBreedsSearchResponse response = new GetBreedsSearchResponse(restResponse.getStatusCode());
        ResponseBodyBreed[] bodyBreeds = restResponse.as(ResponseBodyBreed[].class);
        response.setBody(Arrays.asList(bodyBreeds));
        log.info("{}", response);
        return response;
    }

    @Attachment("Response Object")
    public GetImagesSearchResponse requestGetImagesSearch(GetImagesSearchRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointPath.IMAGES_SEARCH.getUriPath(), queryParams, headers);
        GetImagesSearchResponse response = new GetImagesSearchResponse(restResponse.getStatusCode());
        ResponseBodyImage[] bodyImages = restResponse.as(ResponseBodyImage[].class);
        response.setBody(Arrays.asList(bodyImages));
        log.info("{}", response);
        return response;
    }

    @Attachment("Response Object")
    public GetFavouritesResponse requestGetFavourites(GetFavouritesRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointPath.FAVOURITES.getUriPath(), queryParams, headers);
        GetFavouritesResponse response = new GetFavouritesResponse(restResponse.getStatusCode());
        ResponseBodyFavourite[] bodyImages = restResponse.as(ResponseBodyFavourite[].class);
        response.setBody(Arrays.asList(bodyImages));
        log.info("{}", response);
        return response;
    }

    @Attachment("Response Object")
    public PostFavouritesResponse requestPostFavourites(PostFavouritesRequest request) {
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        PostFavouritesRequestBody body = request.getBody();
        Response restResponse = httpClient.post(EndpointPath.FAVOURITES.getUriPath(), headers, body);
        PostFavouritesResponse response = new PostFavouritesResponse(restResponse.getStatusCode());
        response.setBody(restResponse.as(PostFavouritesResponseBody.class));
        log.info("{}", response);
        return response;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> transformRequestQueryParamsToMapObject(Object queryParams) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(queryParams, Map.class);
    }
}
