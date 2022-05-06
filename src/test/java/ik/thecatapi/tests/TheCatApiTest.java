package ik.thecatapi.tests;

import ik.thecatapi.models.requests.base.AuthorizationHeader;
import ik.thecatapi.models.requests.breed_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.favourites.DeleteFavouritesResponseBody;
import ik.thecatapi.models.requests.favourites.PostFavouritesResponseBody;
import ik.thecatapi.models.requests.favourites.ResponseBodyFavourite;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import ik.thecatapi.services.requests.Steps;
import ik.thecatapi.services.requests.TheCatApiRequests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class TheCatApiTest {
    TheCatApiRequests apiRequests;
    AuthorizationHeader authHeader;
    Steps steps;

    @BeforeClass
    public void setUp() {
        final String AUTHORIZATION_HEADER_NAME = "x-api-key";
        String apiKey = System.getProperty("api_key");
        apiRequests = new TheCatApiRequests();
        authHeader = AuthorizationHeader.builder()
                .name(AUTHORIZATION_HEADER_NAME)
                .value(apiKey)
                .build();
        steps = new Steps(apiRequests, authHeader);
    }

    @Test(description = "Тест кэйс - c шагами")
    @Parameters({"breedName"})
    public void testCase1(String breedName) {
        ResponseBodyBreed responseBodyBreed = steps.doStep1(breedName);
        String breedId = responseBodyBreed.getId();

        ResponseBodyImage responseBodyImage = steps.doStep2(breedId);
        ResponseBodyBreed responseBodyImageBreed = responseBodyImage.getBreeds().stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed of Image was not found."));
        Assert.assertEquals(responseBodyImageBreed.getId(), breedId,"найдено изображение с указанным breed_id");
        String imageId = responseBodyImage.getId();
        String imageUrl = responseBodyImage.getUrl();

        PostFavouritesResponseBody postFavouritesResponseBody = steps.doStep3(imageId);
        Assert.assertEquals(postFavouritesResponseBody.getMessage(), "SUCCESS", "добавили изображение в избранное");
        long favouriteId = postFavouritesResponseBody.getId();

        ResponseBodyFavourite responseBodyFavourite = steps.doStep4();
        ResponseBodyFavourite expectedResponseBodyFavourite = ResponseBodyFavourite.builder()
                .id(favouriteId)
                .imageId(imageId)
                .build();
        Assert.assertEquals(responseBodyFavourite, expectedResponseBodyFavourite, "в ответе присутствует " +
                "ключ \"id\" (избранного) со значением, полученным в шаге 3, а также ключ \"image_id\" со значением, " +
                "полученным на шаге 2");

        DeleteFavouritesResponseBody deleteFavouritesResponseBody = steps.doStep5(favouriteId);
        Assert.assertEquals(deleteFavouritesResponseBody.getMessage(), "SUCCESS",
                "присутствует ключ \"message\" со значением \"SUCCESS\"");

        List<ResponseBodyFavourite> favouritesResponseBody2 = steps.doStep6();
        Assert.assertTrue(favouritesResponseBody2.isEmpty(), "в ответе отсутствует ключ \"id\" (избранного) со значением, " +
                "полученным в шаге 3 (т.е. что изображение действительно было удалено из избранного)");
    }

    @Test(description = "Тест кэйс Бонусное задание - В ответе на запрос к /categories присутствует ключ \"name\" со значением \"boxes\"")
    public void testCategories() {

    }
}
