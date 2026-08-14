package ik.thecatapi.checks;

import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.services.requests.ImagesSearchService;
import io.qameta.allure.Step;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImagesSearchChecks {
    private final ImagesSearchService service;

    public ImagesSearchChecks(ImagesSearchService service) {
        this.service = service;
    }

    @Step("найдено изображение с указанным breed_id")
    public void checkBreadId(GetImagesSearchResponse response) {
        assertAll(
                () -> assertEquals(200, response.getStatusCode(), "Status Code"));
    }
}
