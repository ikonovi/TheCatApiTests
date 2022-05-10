package ik.thecatapi.tests;

import ik.thecatapi.data.DataProviders;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.categories.ResponseBodyCategory;
import ik.thecatapi.models.requests.favourites.DeleteFavouritesResponse;
import ik.thecatapi.models.requests.favourites.PostFavouritesResponse;
import ik.thecatapi.models.requests.favourites.ResponseBodyFavourite;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import ik.thecatapi.services.requests.TheCatApiService;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

public class TheCatApiTest {
    TheCatApiService apiService;

    @BeforeClass
    public void setUp() {
        String apiKey = System.getProperty("api_key");
        apiService = new TheCatApiService(apiKey);
    }

    @Test(description = "Тест кейс c шагами")
    @Parameters({"breedName"})
    public void testCase1(String breedName) {
        GetBreedsSearchResponse getBreedsSearchResponse = apiService.getBreedsSearch(breedName);
        String breedId = apiService.getBreedId(getBreedsSearchResponse);

        GetImagesSearchResponse getImagesSearchResponse = apiService.getImagesSearch(breedId);
        ResponseBodyImage responseBodyImage = apiService.getResponseBody1stImage(getImagesSearchResponse);
        ResponseBodyBreed responseBodyImageBreed = apiService.getImageBreed(responseBodyImage);
        Assert.assertEquals(getImagesSearchResponse.getStatusCode(), 200 ,"Status Code");
        Assert.assertEquals(responseBodyImageBreed.getId(), breedId,"найдено изображение с указанным breed_id");
        String imageId = responseBodyImage.getId();
        String imageUrl = responseBodyImage.getUrl();

        PostFavouritesResponse postFavouritesResponse = apiService.postFavourites(imageId);
        Assert.assertEquals(postFavouritesResponse.getStatusCode(), 200 ,"Status Code");
        Assert.assertEquals(postFavouritesResponse.getBody().getMessage(), "SUCCESS",
                "В ответе присутствует ключ message со значением SUCCESS");
        long favouriteId = postFavouritesResponse.getBody().getId();

        ResponseBodyFavourite createdFavourite = ResponseBodyFavourite.builder()
                .id(favouriteId)
                .imageId(imageId)
                .build();
        apiService.checkFavouritesHasItem(createdFavourite);

        DeleteFavouritesResponse deleteFavouritesResponse = apiService.deleteFavourites(favouriteId);
        Assert.assertEquals(deleteFavouritesResponse.getStatusCode(), 200 ,"Status Code");
        Assert.assertEquals(deleteFavouritesResponse.getBody().getMessage(), "SUCCESS",
                "присутствует ключ message со значением SUCCESS");

        apiService.checkFavouritesNoItem(createdFavourite);

        String fileContent = "Название породы: " + breedName +
                "\nid породы: " + breedId +
                "\nURL: " + imageUrl;
        apiService.attachBreedInfoToReport(fileContent);
    }

    @Test(description = "В ответе на запрос к /categories присутствует ключ \"name\" с заданным значением",
            dataProvider = "Category names",
            dataProviderClass = DataProviders.class)
    public void testCategory(String name) {
        GetCategoriesResponse getCategoriesResponse = apiService.getCategories();
        List<ResponseBodyCategory> categories = getCategoriesResponse.getBody();
        Assert.assertEquals(getCategoriesResponse.getStatusCode(), 200 ,"Status Code");
        boolean anyMatch = categories.stream().anyMatch(category -> category.getName().equals(name));
        Assert.assertTrue(anyMatch, "присутствует ключ \"name\" со значением " + name);
    }
}
