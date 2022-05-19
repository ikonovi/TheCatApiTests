package ik.thecatapi.checks;

import ik.thecatapi.models.requests.favourites.DeleteFavouritesResponse;
import ik.thecatapi.models.requests.favourites.GetFavouritesResponse;
import ik.thecatapi.models.requests.favourites.PostFavouritesResponse;
import ik.thecatapi.models.requests.favourites.ResponseBodyFavourite;
import ik.thecatapi.services.requests.FavouritesService;
import io.qameta.allure.Step;
import org.awaitility.Awaitility;
import org.testng.Assert;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class FavouritesChecks {
    private final FavouritesService service;

    public FavouritesChecks(FavouritesService service) {
        this.service = service;
    }

    @Step("Проверить, что в ответе присутствует ключ message со значением SUCCESS")
    public void checkPost(PostFavouritesResponse response, String expectedImageId) {
        Assert.assertEquals(response.getStatusCode(), 200 ,"Status Code");
        Assert.assertEquals(response.getBody().getMessage(), "SUCCESS",
                "В ответе присутствует ключ message со значением SUCCESS");

        long favouriteId = response.getBody().getId();
        ResponseBodyFavourite createdFavourite = ResponseBodyFavourite.builder()
                .id(favouriteId)
                .imageId(expectedImageId)
                .build();
        checkFavouritesHasItem(createdFavourite);
    }

    @Step("Проверить, что в ответе на запрос /favourites присутствует ключ id (избранного) со значением, " +
            "полученным в шаге 3, а также ключ image_id со значением, полученным на шаге 2.")
    private void checkFavouritesHasItem(ResponseBodyFavourite favourite) {
        Callable<GetFavouritesResponse> callable = service::requestGetFavourites;
        Predicate<GetFavouritesResponse> hasItem = response -> response.getBody().stream().anyMatch(f -> f.equals(favourite));
        Predicate<GetFavouritesResponse> hasStatusCode = response -> response.getStatusCode() == 200;
        Awaitility
                .waitAtMost(Duration.ofSeconds(3))
                .pollDelay(Duration.ofMillis(500))
                .await(favourite + " присутствует в результате запроса GET /favourite")
                .until(callable, hasItem.and(hasStatusCode));
    }

    @Step("Проверить, что присутствует ключ \"message\" со значением SUCCESS")
    public void checkDelete(DeleteFavouritesResponse response, ResponseBodyFavourite createdFavourite) {
        Assert.assertEquals(response.getStatusCode(), 200 ,"Status Code");
        Assert.assertEquals(response.getBody().getMessage(), "SUCCESS",
                "присутствует ключ message со значением SUCCESS");
        checkFavouritesNoItem(createdFavourite);
    }

    @Step("Проверить, что теперь в ответе отсутствует ключ id (избранного) со значением, полученным в шаге 3 " +
            "(т.е. что изображение действительно было удалено из избранного)")
    private void checkFavouritesNoItem(ResponseBodyFavourite favourite) {
        Callable<GetFavouritesResponse> callable = service::requestGetFavourites;
        Predicate<GetFavouritesResponse> predicateNoItem = response -> response.getBody().stream().noneMatch(f -> f.equals(favourite));
        Predicate<GetFavouritesResponse> predicateStatusCode = response -> response.getStatusCode() == 200;
        Awaitility
                .waitAtMost(Duration.ofSeconds(3))
                .pollDelay(Duration.ofMillis(500))
                .await(favourite + " отсутствует в результате запроса GET /favourite")
                .until(callable, predicateNoItem.and(predicateStatusCode));
    }
}
