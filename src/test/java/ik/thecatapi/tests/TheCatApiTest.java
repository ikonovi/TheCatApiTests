package ik.thecatapi.tests;

import ik.thecatapi.models.requests.base.AuthorizationHeader;
import ik.thecatapi.models.requests.breed_search.BreedsSearchRequest;
import ik.thecatapi.models.requests.breed_search.BreedsSearchRequestQueryParams;
import ik.thecatapi.models.requests.breed_search.BreedsSearchResponse;
import ik.thecatapi.models.requests.breed_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.favourites.FavouritesRequest;
import ik.thecatapi.models.requests.favourites.FavouritesRequestQueryParams;
import ik.thecatapi.models.requests.favourites.FavouritesResponse;
import ik.thecatapi.models.requests.favourites.ResponseBodyFavourite;
import ik.thecatapi.models.requests.images_search.ImagesSearchRequest;
import ik.thecatapi.models.requests.images_search.ImagesSearchRequestQueryParams;
import ik.thecatapi.models.requests.images_search.ImagesSearchResponse;
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
        BreedsSearchRequest breedsSearchRequest = BreedsSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(
                        BreedsSearchRequestQueryParams.builder()
                                .q("Scottish Fold") // TODO
                                .build())
                .build();
        BreedsSearchResponse breedsSearchResponse = apiRequests.requestGetBreedsSearch(breedsSearchRequest);
        List<ResponseBodyBreed> breedsSearchResponseBody = breedsSearchResponse.getBody();
        ResponseBodyBreed responseBodyBreed = breedsSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed ID was not found."));
        String breedId = responseBodyBreed.getId();

        // #2
        ImagesSearchRequest imagesSearchRequest = ImagesSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(ImagesSearchRequestQueryParams.builder().breedId(breedId).build())
                .build();
        ImagesSearchResponse imagesSearchResponse = apiRequests.requestGetImagesSearch(imagesSearchRequest);
        List<ResponseBodyImage> imagesSearchResponseBody = imagesSearchResponse.getBody();
        ResponseBodyImage responseBodyImage = imagesSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Image was not found."));
        ResponseBodyBreed responseBodyImageBreed = responseBodyImage.getBreeds().stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed of Image was not found."));
        Assert.assertEquals(responseBodyImageBreed.getId(), breedId,"найдено изображение с указанным breed_id");

        // #3


        long favouriteId = 111;

        // #4
        FavouritesRequest favouritesRequest = FavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(FavouritesRequestQueryParams.builder().build())
                .build();
        FavouritesResponse favouritesResponse = apiRequests.requestGetFavourites(favouritesRequest);
        List<ResponseBodyFavourite> favouritesResponseBody = favouritesResponse.getBody();
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
