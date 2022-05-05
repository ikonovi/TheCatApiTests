package ik.thecatapi.tests;

import ik.thecatapi.models.requests.base.AuthorizationHeader;
import ik.thecatapi.models.requests.breedsearch.BreedSearchRequest;
import ik.thecatapi.models.requests.breedsearch.BreedSearchRequestQueryParams;
import ik.thecatapi.models.requests.breedsearch.BreedSearchResponse;
import ik.thecatapi.models.requests.ResponseBodyBreed;
import ik.thecatapi.services.requests.TheCatApiRequests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class TheCatApiTest {
    TheCatApiRequests apiRequests;
    AuthorizationHeader authHeader;

    @BeforeClass
    public void setUp() {
        apiRequests = new TheCatApiRequests();
        String headerName = "x-api-key"; // TODO conf prop
        String API_KEY = System.getenv("API_KEY"); // TODO env var
        authHeader = AuthorizationHeader.builder()
                .name(headerName)
                .value(API_KEY)
                .build();
    }

    @Test(description = "Задание 2 (Автоматизация API)")
    public void testCase1() {
        BreedSearchRequest breedSearchRequest = BreedSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(
                        BreedSearchRequestQueryParams.builder()
                                .q("Scottish Fold") // TODO
                                .build())
                .build();
        BreedSearchResponse breedSearchResponse = apiRequests.requestGetBreedSearch(breedSearchRequest);
        List<ResponseBodyBreed> breedSearchResponseBody = breedSearchResponse.getBody();
        ResponseBodyBreed responseBodyBreed = breedSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed ID was not found."));

        System.out.println(responseBodyBreed.getId());
    }
}
