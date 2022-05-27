package ik.thecatapi.tests;

import ik.thecatapi.checks.CategoriesChecks;
import ik.thecatapi.checks.FavouritesChecks;
import ik.thecatapi.checks.ImagesSearchChecks;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.favourites.DeleteFavouritesResponse;
import ik.thecatapi.models.requests.favourites.PostFavouritesResponse;
import ik.thecatapi.models.requests.favourites.ResponseBodyFavourite;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import ik.thecatapi.services.requests.BreedsSearchService;
import ik.thecatapi.services.requests.CategoriesService;
import ik.thecatapi.services.requests.FavouritesService;
import ik.thecatapi.services.requests.ImagesSearchService;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@DisplayName("Задание 2 (Автоматизация API)")
class TheCatApiTest {
    CategoriesService categoriesService;
    CategoriesChecks categoriesChecks;
    BreedsSearchService breedsSearchService;
    ImagesSearchService imagesSearchService;
    ImagesSearchChecks imagesSearchChecks;
    FavouritesService favouritesService;
    FavouritesChecks favouritesChecks;

    @BeforeEach
    void initAll() {
        String apiKey = System.getProperty("api_key");
        categoriesService = new CategoriesService(apiKey);
        categoriesChecks = new CategoriesChecks();
        breedsSearchService = new BreedsSearchService(apiKey);
        imagesSearchService = new ImagesSearchService(apiKey);
        imagesSearchChecks = new ImagesSearchChecks(imagesSearchService);
        favouritesService = new FavouritesService(apiKey);
        favouritesChecks = new FavouritesChecks(favouritesService);
    }

    @DisplayName("Тест кейс c шагами")
    @ParameterizedTest
    @ValueSource(strings = {"Scottish Fold"})
    void testCase1(String breedName) {
        GetBreedsSearchResponse getBreedsSearchResponse = breedsSearchService.requestGetBreedsSearch(breedName);
        String breedId = breedsSearchService.getBreedId(getBreedsSearchResponse);

        GetImagesSearchResponse getImagesSearchResponse = imagesSearchService.getImagesSearch(breedId);
        imagesSearchChecks.checkBreadId(getImagesSearchResponse, breedId);

        ResponseBodyImage responseBodyImage = imagesSearchService.getResponseBody1stImage(getImagesSearchResponse);
        String imageId = responseBodyImage.getId();
        String imageUrl = responseBodyImage.getUrl();

        PostFavouritesResponse postFavouritesResponse = favouritesService.requestPostFavourites(imageId);
        long favouriteId = postFavouritesResponse.getBody().getId();
        ResponseBodyFavourite createdFavourite = ResponseBodyFavourite.builder()
                .id(favouriteId)
                .imageId(imageId)
                .build();
        favouritesChecks.checkPost(postFavouritesResponse, imageId);

        DeleteFavouritesResponse deleteFavouritesResponse = favouritesService.requestDeleteFavourites(favouriteId);
        favouritesChecks.checkDelete(deleteFavouritesResponse, createdFavourite);

        String fileContent = "Название породы: " + breedName +
                "\nid породы: " + breedId +
                "\nURL: " + imageUrl;
        attachBreedInfoToReport(fileContent);
    }

    @DisplayName("В ответе на запрос к /categories присутствует ключ \"name\" с заданным значением")
    @ParameterizedTest
    @ValueSource(strings = {"boxes"})
    void testCategoryName(String name) {
        GetCategoriesResponse getCategoriesResponse = categoriesService.requestGetCategories();
        categoriesChecks.checkCategoryName(getCategoriesResponse, name);
    }

    private void attachBreedInfoToReport(String fileContent) {
        Allure.addAttachment("О кошачей породе", "text/plain",
                new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)), ".txt");
    }
}
