package ik.thecatapi.services.requests;

import ik.thecatapi.models.requests.base.AuthorizationHeader;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchRequest;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchRequestQueryParams;
import ik.thecatapi.models.requests.breeds_search.GetBreedsSearchResponse;
import ik.thecatapi.models.requests.breeds_search.ResponseBodyBreed;
import ik.thecatapi.models.requests.categories.GetCategoriesRequest;
import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.categories.ResponseBodyCategory;
import ik.thecatapi.models.requests.favourites.*;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequest;
import ik.thecatapi.models.requests.images_search.GetImagesSearchRequestQueryParams;
import ik.thecatapi.models.requests.images_search.GetImagesSearchResponse;
import ik.thecatapi.models.requests.images_search.ResponseBodyImage;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Steps {
    TheCatApiRequests apiRequests;
    AuthorizationHeader authHeader;

    public Steps(TheCatApiRequests apiRequests, AuthorizationHeader authHeader) {
        this.apiRequests = apiRequests;
        this.authHeader = authHeader;
    }

    @Step("1. Выполнить запрос к /breeds/search по названию породы \"Scottish Fold\". В ответе достать id этой породы. ")
    public ResponseBodyBreed doStep1(String breedName) {
        GetBreedsSearchRequest getBreedsSearchRequest = GetBreedsSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(
                        GetBreedsSearchRequestQueryParams.builder()
                                .q(breedName)
                                .build())
                .build();
        GetBreedsSearchResponse getBreedsSearchResponse = apiRequests.requestGetBreedsSearch(getBreedsSearchRequest);
        List<ResponseBodyBreed> breedsSearchResponseBody = getBreedsSearchResponse.getBody();
        return breedsSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Breed ID was not found."));
    }

    @Step("2. Выполнить запрос к /images/search, в теле запроса должен быть указан параметр breed_id с ранее полученным id породы из шага 1." +
            "В ответе проверить, что действительно найдено изображение с указанным breed_id.")
    public ResponseBodyImage doStep2(String breedId) {
        GetImagesSearchRequest getImagesSearchRequest = GetImagesSearchRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(GetImagesSearchRequestQueryParams.builder().breedId(breedId).build())
                .build();
        GetImagesSearchResponse getImagesSearchResponse = apiRequests.requestGetImagesSearch(getImagesSearchRequest);
        List<ResponseBodyImage> imagesSearchResponseBody = getImagesSearchResponse.getBody();
        return imagesSearchResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Image was not found."));
    }

    @Step("3. Выполнить запрос к /favourites, который добавит данное изображение в избранное. \n" +
            "В ответе проверить, что присутствует ключ \"message\" со значением \"SUCCESS\" и получить из него id избранного.")
    public PostFavouritesResponseBody doStep3(String imageId) {
        PostFavouritesRequest postFavouritesRequest = PostFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .body(PostFavouritesRequestBody.builder().imageId(imageId).build())
                .build();
        PostFavouritesResponse postFavouritesResponse = apiRequests.requestPostFavourites(postFavouritesRequest);
        return postFavouritesResponse.getBody();
    }

    @Step("4. Выполнить get запрос к /favourites. \n" +
            "Проверить, что в ответе присутствует ключ \"id\" (избранного) со значением, полученным в шаге 3, " +
            "а также ключ \"image_id\" со значением, полученным на шаге 2.")
    public ResponseBodyFavourite doStep4() {
        GetFavouritesRequest getFavouritesRequest = GetFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(GetFavouritesRequestQueryParams.builder().build())
                .build();
        GetFavouritesResponse getFavouritesResponse = apiRequests.requestGetFavourites(getFavouritesRequest);
        List<ResponseBodyFavourite> favouritesResponseBody = getFavouritesResponse.getBody();
        return favouritesResponseBody.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("Favorite Image was not found."));
    }

    @Step("5. Выполнить delete запрос к /favourites, указав в качестве параметра полученный id избранного из шага 3.\n" +
            "В ответе проверить, что присутствует ключ \"message\" со значением \"SUCCESS\".")
    public DeleteFavouritesResponseBody doStep5(long favouriteId) {
        DeleteFavouritesRequest deleteFavouritesRequest = DeleteFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .favouriteId(favouriteId)
                .build();
        DeleteFavouritesResponse deleteFavouritesResponse = apiRequests.requestDeleteFavourites(deleteFavouritesRequest);
        return deleteFavouritesResponse.getBody();
    }

    @Step("6. Снова выполнить get запрос к /favourites. \n" +
            "Проверить, что теперь в ответе отсутствует ключ \"id\" (избранного) со значением, " +
            "полученным в шаге 3 (т.е. что изображение действительно было удалено из избранного)")
    public List<ResponseBodyFavourite> doStep6() {
        GetFavouritesRequest getFavouritesRequest2 = GetFavouritesRequest.builder()
                .authorizationHeader(authHeader)
                .queryParams(GetFavouritesRequestQueryParams.builder().build())
                .build();
        GetFavouritesResponse getFavouritesResponse2 = apiRequests.requestGetFavourites(getFavouritesRequest2);
        return getFavouritesResponse2.getBody();
    }

    @Step("Выполнить запрос к /categories")
    public List<ResponseBodyCategory> getCategories() {
        GetCategoriesRequest getCategoriesRequest = GetCategoriesRequest.builder()
                .authorizationHeader(authHeader)
                .build();
        GetCategoriesResponse getCategoriesResponse = apiRequests.requestGetCategories(getCategoriesRequest);
        return getCategoriesResponse.getBody();
    }

    public void attachBreedInfoToReport(String fileContent) {
        Allure.addAttachment("О кошачей породе", "text/plain",
                new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8)),".txt");
    }
}
