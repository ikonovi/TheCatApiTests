package ik.thecatapi.services.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import ik.thecatapi.config.EndpointUriPath;
import ik.thecatapi.models.requests.base.AuthorizationHeader;
import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchRequest;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.categories.GetCategoriesRequest;
import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.categories.ResponseBodyCategory;
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
    public DeleteFavouritesResponse requestDeleteFavourites(DeleteFavouritesRequest request) {
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        String uriPath = EndpointUriPath.FAVOURITES.getValue()
                + EndpointUriPath.DELIMITER.getValue()
                + request.getFavouriteId();
        Response restResponse = httpClient.delete(uriPath, headers);
        DeleteFavouritesResponse response = new DeleteFavouritesResponse(restResponse.getStatusCode());
        response.setBody(restResponse.as(DeleteFavouritesResponseBody.class));
        log.info("{}", response);
        return response;
    }

    @Attachment("Response Object")
    public GetBreedsSearchResponse requestGetBreedsSearch(GetBreedsSearchRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointUriPath.BREEDS_SEARCH.getValue(), queryParams, headers);
        GetBreedsSearchResponse response = new GetBreedsSearchResponse(restResponse.getStatusCode());
        ResponseBodyBreed[] bodyBreeds = restResponse.as(ResponseBodyBreed[].class);
        response.setBody(Arrays.asList(bodyBreeds));
        log.info("{}", response);
        return response;
    }

    @Attachment("Response Object")
    public GetCategoriesResponse requestGetCategories(GetCategoriesRequest request) {
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

    @Attachment("Response Object")
    public GetImagesSearchResponse requestGetImagesSearch(GetImagesSearchRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointUriPath.IMAGES_SEARCH.getValue(), queryParams, headers);
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
        Response restResponse = httpClient.get(EndpointUriPath.FAVOURITES.getValue(), queryParams, headers);
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
        Response restResponse = httpClient.post(EndpointUriPath.FAVOURITES.getValue(), headers, body);
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
