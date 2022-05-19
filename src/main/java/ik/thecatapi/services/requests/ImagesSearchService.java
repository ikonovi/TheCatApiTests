package ik.thecatapi.services.requests;

import ik.thecatapi.controllers.requests.ImagesSearchRequestController;
import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequest;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequestQueryParams;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import io.qameta.allure.Step;

import java.util.List;

public class ImagesSearchService extends AbstractService {
    private final ImagesSearchRequestController controller;

    public ImagesSearchService(String apiKey) {
        super(apiKey);
        this.controller = new ImagesSearchRequestController();
    }

    @Step("2. Выполнить запрос к /images/search, в теле запроса должен быть указан параметр breed_id с ранее полученным id={breedId} породы из шага 1.")
    public GetImagesSearchResponse getImagesSearch(String breedId) {
        GetImagesSearchRequest getImagesSearchRequest = GetImagesSearchRequest.builder()
                .authorizationHeader(super.getAuthorizationHeader())
                .queryParams(GetImagesSearchRequestQueryParams.builder().breedId(breedId).build())
                .build();
        return controller.get(getImagesSearchRequest);
    }

    @Step("Взять первый Image в результатах поиска.")
    public ResponseBodyImage getResponseBody1stImage(GetImagesSearchResponse response) {
        List<ResponseBodyImage> imagesSearchResponseBody = response.getBody();
        return imagesSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Image was not found."));
    }

    @Step("Взять свойство Breed в объекте Image")
    public ResponseBodyBreed getImageBreed(ResponseBodyImage image) {
        return image.getBreeds().stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed of Image was not found."));
    }
}
