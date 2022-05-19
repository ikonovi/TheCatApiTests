package ik.thecatapi.requests;

import ik.thecatapi.config.EndpointUriPath;
import ik.thecatapi.requests.common.TheCatApiRequest;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchRequest;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import io.qameta.allure.Attachment;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class BreedsSearchRequests extends TheCatApiRequest {

    @Attachment("Response Object")
    public GetBreedsSearchResponse get(GetBreedsSearchRequest request) {
        Map<String, Object> queryParams = transformRequestQueryParamsToMapObject(request.getQueryParams());

        // TODO-FIX Rest Assured dependency
        Header authorizationHeader = new Header(request.getAuthorizationHeader().getName(), request.getAuthorizationHeader().getValue());
        Headers headers = new Headers(authorizationHeader);
        Response restResponse = httpClient.get(EndpointUriPath.BREEDS_SEARCH.getValue(), queryParams, headers);

        GetBreedsSearchResponse response = new GetBreedsSearchResponse(restResponse.getStatusCode());
        ResponseBodyBreed[] bodyBreeds = restResponse.as(ResponseBodyBreed[].class);
        response.setBody(Arrays.asList(bodyBreeds));
        log.info("{}", response);
        return response;
    }
}
