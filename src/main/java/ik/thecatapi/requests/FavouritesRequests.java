package ik.thecatapi.requests;

import ik.thecatapi.config.EndpointUriPath;
import ik.thecatapi.models.requests.favourites.*;
import ik.thecatapi.requests.common.TheCatApiRequest;
import io.qameta.allure.Attachment;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class FavouritesRequests extends TheCatApiRequest {

    @Attachment("Response Object")
    public GetFavouritesResponse get(GetFavouritesRequest request) {
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
    public PostFavouritesResponse post(PostFavouritesRequest request) {

        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        PostFavouritesRequestBody body = request.getBody();
        Response restResponse = httpClient.post(EndpointUriPath.FAVOURITES.getValue(), headers, body);

        PostFavouritesResponse response = new PostFavouritesResponse(restResponse.getStatusCode());
        response.setBody(restResponse.as(PostFavouritesResponseBody.class));
        log.info("{}", response);
        return response;
    }

    @Attachment("Response Object")
    public DeleteFavouritesResponse delete(DeleteFavouritesRequest request) {
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
}
