package ik.thecatapi.checks;

import ik.thecatapi.models.requests.favourites.DeleteFavouritesResponse;
import ik.thecatapi.models.requests.favourites.GetFavouritesResponse;
import ik.thecatapi.models.requests.favourites.PostFavouritesResponse;
import ik.thecatapi.services.requests.FavouritesService;
import io.qameta.allure.Step;
import org.awaitility.Awaitility;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FavouritesChecks {
    private final FavouritesService service;

    public FavouritesChecks(FavouritesService service) {
        this.service = service;
    }

    @Step("Проверить, что в ответе присутствует ключ message со значением SUCCESS")
    public void checkPost(PostFavouritesResponse postRes) {
        assertAll(
                () -> assertEquals(200, postRes.getStatusCode(), "Status Code"),
                () -> assertEquals("SUCCESS", postRes.getBody().getMessage(),
                        "В ответе присутствует ключ message со значением SUCCESS"));

        long favouriteId = postRes.getBody().getId();
        checkFavouritesHasItem(favouriteId);
    }

    @Step("Проверить, что в ответе на запрос /favourites присутствует ключ id (избранного) со значением, " +
            "полученным в шаге 3, а также ключ image_id со значением, полученным на шаге 2.")
    private void checkFavouritesHasItem(long favouriteId) {
        Callable<GetFavouritesResponse> callable = service::requestGetFavourites;
        Predicate<GetFavouritesResponse> hasItem = response ->
                response.getBody().stream().anyMatch(f -> f.getId() == favouriteId);
        Predicate<GetFavouritesResponse> hasStatusCode = response -> response.getStatusCode() == 200;
        Awaitility
                .waitAtMost(Duration.ofSeconds(3))
                .pollDelay(Duration.ofMillis(500))
                .await(favouriteId + " присутствует в результате запроса GET /favourite")
                .until(callable, hasItem.and(hasStatusCode));
    }

    @Step("Проверить, что присутствует ключ \"message\" со значением SUCCESS")
    public void checkDelete(DeleteFavouritesResponse response, long favouriteId) {
        assertAll(
                () -> assertEquals(200, response.getStatusCode(), "Status Code"),
                () -> assertEquals("SUCCESS", response.getBody().getMessage(),
                        "присутствует ключ message со значением SUCCESS"),
                () -> assertFavouritesNoItem(favouriteId));
    }

    @Step("Проверить, что теперь в ответе отсутствует ключ id (избранного) со значением, полученным в шаге 3 " +
            "(т.е. что изображение действительно было удалено из избранного)")
    private void assertFavouritesNoItem(long favouriteId) {
        Callable<GetFavouritesResponse> callable = service::requestGetFavourites;
        Predicate<GetFavouritesResponse> predicateNoItem =
                response -> response.getBody().stream().noneMatch(
                        f -> f.getId() == favouriteId);
        Predicate<GetFavouritesResponse> predicateStatusCode = response -> response.getStatusCode() == 200;
        Awaitility
                .waitAtMost(Duration.ofSeconds(3))
                .pollDelay(Duration.ofMillis(500))
                .await(favouriteId + " отсутствует в результате запроса GET /favourite")
                .until(callable, predicateNoItem.and(predicateStatusCode));
    }
}
