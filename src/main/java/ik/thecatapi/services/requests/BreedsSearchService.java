package ik.thecatapi.services.requests;

import ik.thecatapi.controllers.requests.BreedsSearchRequestController;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchRequest;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchRequestQueryParams;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import io.qameta.allure.Step;

import java.util.List;

public class BreedsSearchService extends AbstractService {
    private final BreedsSearchRequestController controller;

    public BreedsSearchService(String apiKey) {
        super(apiKey);
        this.controller = new BreedsSearchRequestController();
    }

    @Step("1. Выполнить запрос к /breeds/search по названию породы {breedName}.")
    public GetBreedsSearchResponse requestGetBreedsSearch(String breedName) {
        GetBreedsSearchRequest getBreedsSearchRequest = GetBreedsSearchRequest.builder()
                .authorizationHeader(super.getAuthorizationHeader())
                .queryParams(
                        GetBreedsSearchRequestQueryParams.builder()
                                .q(breedName)
                                .build())
                .build();
        return controller.get(getBreedsSearchRequest);
    }

    @Step("В ответе достать id породы.")
    public String getBreedId(GetBreedsSearchResponse getBreedsSearchResponse) {
        List<ResponseBodyBreed> breedsSearchResponseBody = getBreedsSearchResponse.getBody();
        ResponseBodyBreed responseBodyBreed = breedsSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed ID was not found.")); // XXX: type of error to throw?
        return responseBodyBreed.getId();
    }
}
