package ik.thecatapi.checks;

import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import ik.thecatapi.services.requests.ImagesSearchService;
import io.qameta.allure.Step;
import org.testng.Assert;

public class ImagesSearchChecks {
    private final ImagesSearchService service;

    public ImagesSearchChecks(ImagesSearchService service) {
        this.service = service;
    }

    @Step("найдено изображение с указанным breed_id")
    public void checkBreadId(GetImagesSearchResponse response, String expectedBreedId) {
        ResponseBodyImage responseBodyImage = service.getResponseBody1stImage(response);
        ResponseBodyBreed responseBodyImageBreed = service.getImageBreed(responseBodyImage);
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code");
        Assert.assertEquals(responseBodyImageBreed.getId(), expectedBreedId, "найдено изображение с указанным breed_id");
    }
}
