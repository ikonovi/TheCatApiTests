package ik.thecatapi.tests;

import ik.thecatapi.checks.CategoriesChecks;
import ik.thecatapi.checks.FavouritesChecks;
import ik.thecatapi.checks.ImagesSearchChecks;
import ik.thecatapi.data.DataProviders;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class TheCatApiTest {
    CategoriesService categoriesService;
    CategoriesChecks categoriesChecks;
    BreedsSearchService breedsSearchService;
    ImagesSearchService imagesSearchService;
    ImagesSearchChecks imagesSearchChecks;
    FavouritesService favouritesService;
    FavouritesChecks favouritesChecks;

    @BeforeClass
    public void setUp() {
        String apiKey = System.getProperty("api_key");
        categoriesService = new CategoriesService(apiKey);
        categoriesChecks = new CategoriesChecks();
        breedsSearchService = new BreedsSearchService(apiKey);
        imagesSearchService = new ImagesSearchService(apiKey);
        imagesSearchChecks = new ImagesSearchChecks(imagesSearchService);
        favouritesService = new FavouritesService(apiKey);
        favouritesChecks = new FavouritesChecks(favouritesService);
    }

    @Test(description = "Тест кейс c шагами")
    @Parameters({"breedName"})
    public void testCase1(String breedName) {
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

    @Test(description = "В ответе на запрос к /categories присутствует ключ \"name\" с заданным значением",
            dataProvider = "Category names",
            dataProviderClass = DataProviders.class)
    public void testCategoryName(String name) {
        GetCategoriesResponse getCategoriesResponse = categoriesService.requestGetCategories();
        categoriesChecks.checkCategoryName(getCategoriesResponse, name);
    }

    private void attachBreedInfoToReport(String fileContent) {
        Allure.addAttachment("О кошачей породе", "text/plain",
                new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)),".txt");
    }
}
