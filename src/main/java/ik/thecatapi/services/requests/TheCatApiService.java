package ik.thecatapi.services.requests;

import ik.thecatapi.models.requests.base.AuthorizationHeader;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchRequest;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchRequestQueryParams;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.categories.GetCategoriesRequest;
import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.favourites.*;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequest;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequestQueryParams;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.awaitility.Awaitility;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class TheCatApiService {
    private final AuthorizationHeader authHeader;
    private final TheCatApiRequests apiRequests;

    public TheCatApiService(String apiKey) {
        this.authHeader = createAuthorizationHeader(apiKey);
        this.apiRequests = new TheCatApiRequests();
    }

    @Step("1. Выполнить запрос к /breeds/search по названию породы {breedName}.")
    public GetBreedsSearchResponse getBreedsSearch(String breedName) {
        GetBreedsSearchRequest getBreedsSearchRequest = GetBreedsSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(
                        GetBreedsSearchRequestQueryParams.builder()
                                .q(breedName)
                                .build())
                .build();
        return apiRequests.getBreedsSearch(getBreedsSearchRequest);
    }

    @Step("В ответе достать id породы.")
    public String getBreedId(GetBreedsSearchResponse getBreedsSearchResponse) {
        List<ResponseBodyBreed> breedsSearchResponseBody = getBreedsSearchResponse.getBody();
        ResponseBodyBreed responseBodyBreed = breedsSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed ID was not found."));
        return responseBodyBreed.getId();
    }

    @Step("2. Выполнить запрос к /images/search, в теле запроса должен быть указан параметр breed_id с ранее полученным id породы из шага 1.")
    public GetImagesSearchResponse getImagesSearch(String breedId) {
        GetImagesSearchRequest getImagesSearchRequest = GetImagesSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(GetImagesSearchRequestQueryParams.builder().breedId(breedId).build())
                .build();
        return apiRequests.getImagesSearch(getImagesSearchRequest);
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

    @Step("3. Выполнить запрос к /favourites, который добавит данное изображение в избранное.")
    public PostFavouritesResponse postFavourites(String imageId) {
        PostFavouritesRequest postFavouritesRequest = PostFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .body(PostFavouritesRequestBody.builder().imageId(imageId).build())
                .build();
        return apiRequests.postFavourites(postFavouritesRequest);
    }

    @Step("Выполнить get запрос к /favourites.")
    public GetFavouritesResponse getFavourites() {
        GetFavouritesRequest getFavouritesRequest = GetFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(GetFavouritesRequestQueryParams.builder().build())
                .build();
        return apiRequests.getFavourites(getFavouritesRequest);
    }


    @Step("5. Выполнить delete запрос к /favourites, указав в качестве параметра полученный id избранного из шага 3.")
    public DeleteFavouritesResponse deleteFavourites(long favouriteId) {
        DeleteFavouritesRequest deleteFavouritesRequest = DeleteFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .favouriteId(favouriteId)
                .build();
        return apiRequests.deleteFavourites(deleteFavouritesRequest);
    }

    @Step("Выполнить get запрос к /categories")
    public GetCategoriesResponse getCategories() {
        GetCategoriesRequest getCategoriesRequest = GetCategoriesRequest.builder()
                .authorizationHeader(authHeader)
                .build();
        return apiRequests.getCategories(getCategoriesRequest);
    }

    @Step("4. Проверить, что в ответе на запрос /favourites присутствует ключ id (избранного) со значением, " +
            "полученным в шаге 3, а также ключ image_id со значением, полученным на шаге 2.")
    public void checkFavouritesHasItem(ResponseBodyFavourite favourite) {
        Callable<GetFavouritesResponse> callable = this::getFavourites;
        Predicate<GetFavouritesResponse> hasItem = response -> response.getBody().stream().anyMatch(f -> f.equals(favourite));
        Predicate<GetFavouritesResponse> hasStatusCode = response -> response.getStatusCode() == 200;
        Awaitility
                .waitAtMost(Duration.ofSeconds(3))
                .pollDelay(Duration.ofMillis(500))
                .await(favourite + " присутствует в результате запроса GET /favourite")
                .until(callable, hasItem.and(hasStatusCode));
    }

    @Step("Проверить, что теперь в ответе отсутствует ключ id (избранного) со значением, полученным в шаге 3 " +
            "(т.е. что изображение действительно было удалено из избранного)")
    public void checkFavouritesNoItem(ResponseBodyFavourite favourite) {
        Callable<GetFavouritesResponse> callable = this::getFavourites;
        Predicate<GetFavouritesResponse> predicateNoItem = response -> response.getBody().stream().noneMatch(f -> f.equals(favourite));
        Predicate<GetFavouritesResponse> predicateStatusCode = response -> response.getStatusCode() == 200;
                Awaitility
                        .waitAtMost(Duration.ofSeconds(3))
                        .pollDelay(Duration.ofMillis(500))
                        .await(favourite + " отсутствует в результате запроса GET /favourite")
                        .until(callable, predicateNoItem.and(predicateStatusCode));
    }

    public void attachBreedInfoToReport(String fileContent) {
        Allure.addAttachment("О кошачей породе", "text/plain",
                new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)),".txt");
    }

    private AuthorizationHeader  createAuthorizationHeader(String apiKey) {
        final String AUTH_HEADER_NAME = "x-api-key";
        return AuthorizationHeader.builder()
                .name(AUTH_HEADER_NAME)
                .value(apiKey)
                .build();
    }
}
