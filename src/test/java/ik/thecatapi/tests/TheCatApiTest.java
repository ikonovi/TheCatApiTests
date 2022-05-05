package ik.thecatapi.tests;

import ik.thecatapi.models.requests.base.AuthorizationHeader;
import ik.thecatapi.models.requests.breed_search.GetBreedsSearchRequest;
import ik.thecatapi.models.requests.breed_search.GetBreedsSearchRequestQueryParams;
import ik.thecatapi.models.requests.breed_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.breed_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.favourites.*;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequest;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequestQueryParams;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import ik.thecatapi.services.requests.TheCatApiRequests;
import org.testng.Assert;
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

        // #1
        GetBreedsSearchRequest getBreedsSearchRequest = GetBreedsSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(
                        GetBreedsSearchRequestQueryParams.builder()
                                .q("Scottish Fold") // TODO
                                .build())
                .build();
        GetBreedsSearchResponse getBreedsSearchResponse = apiRequests.requestGetBreedsSearch(getBreedsSearchRequest);
        List<ResponseBodyBreed> breedsSearchResponseBody = getBreedsSearchResponse.getBody();
        ResponseBodyBreed responseBodyBreed = breedsSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed ID was not found."));
        String breedId = responseBodyBreed.getId();

        // #2
        GetImagesSearchRequest getImagesSearchRequest = GetImagesSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(GetImagesSearchRequestQueryParams.builder().breedId(breedId).build())
                .build();
        GetImagesSearchResponse getImagesSearchResponse = apiRequests.requestGetImagesSearch(getImagesSearchRequest);
        List<ResponseBodyImage> imagesSearchResponseBody = getImagesSearchResponse.getBody();
        ResponseBodyImage responseBodyImage = imagesSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Image was not found."));
        ResponseBodyBreed responseBodyImageBreed = responseBodyImage.getBreeds().stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed of Image was not found."));
        Assert.assertEquals(responseBodyImageBreed.getId(), breedId,"найдено изображение с указанным breed_id");

        // #3
        PostFavouritesRequest postFavouritesRequest = PostFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .body(PostFavouritesRequestBody.builder().imageId(responseBodyImage.getId()).build())
                .build();
        PostFavouritesResponse postFavouritesResponse = apiRequests.requestPostFavourites(postFavouritesRequest);
        PostFavouritesResponseBody postFavouritesResponseBody = postFavouritesResponse.getBody();
        Assert.assertEquals(postFavouritesResponseBody.getMessage(), "SUCCESS", "добавили изображение в избранное");
        long favouriteId = postFavouritesResponseBody.getId();

        // #4
        GetFavouritesRequest getFavouritesRequest = GetFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(GetFavouritesRequestQueryParams.builder().build())
                .build();
        GetFavouritesResponse getFavouritesResponse = apiRequests.requestGetFavourites(getFavouritesRequest);
        List<ResponseBodyFavourite> favouritesResponseBody = getFavouritesResponse.getBody();
        ResponseBodyFavourite responseBodyFavourite = favouritesResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Favorite Image was not found."));
        ResponseBodyFavourite expectedResponseBodyFavourite = ResponseBodyFavourite.builder()
                .id(favouriteId)
                .imageId(responseBodyImage.getId())
                .build();
        Assert.assertEquals(responseBodyFavourite, expectedResponseBodyFavourite, "в ответе присутствует " +
                "ключ \"id\" (избранного) со значением, полученным в шаге 3, а также ключ \"image_id\" со значением, " +
                "полученным на шаге 2");
    }
}
