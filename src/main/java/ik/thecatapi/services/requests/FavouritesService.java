package ik.thecatapi.services.requests;

import ik.thecatapi.controllers.requests.FavouritesRequestController;
import ik.thecatapi.models.requests.favourites.*;
import io.qameta.allure.Step;

public class FavouritesService extends AbstractService{
    private final FavouritesRequestController controller;

    public FavouritesService(String apiKey) {
        super(apiKey);
        this.controller = new FavouritesRequestController();
    }

    @Step("Выполнить get запрос к /favourites.")
    public GetFavouritesResponse requestGetFavourites() {
        GetFavouritesRequest getFavouritesRequest = GetFavouritesRequest.builder()
                .authorizationHeader(super.getAuthorizationHeader())
                .queryParams(GetFavouritesRequestQueryParams.builder().build())
                .build();
        return controller.get(getFavouritesRequest);
    }

    @Step("3. Выполнить запрос к /favourites, который добавит данное изображение в избранное.")
    public PostFavouritesResponse requestPostFavourites(String imageId) {
        PostFavouritesRequest postFavouritesRequest = PostFavouritesRequest.builder()
                .authorizationHeader(super.getAuthorizationHeader())
                .body(PostFavouritesRequestBody.builder().imageId(imageId).build())
                .build();
        return controller.post(postFavouritesRequest);
    }

    @Step("5. Выполнить delete запрос к /favourites, указав в качестве параметра полученный id избранного из шага 3.")
    public DeleteFavouritesResponse requestDeleteFavourites(long favouriteId) {
        DeleteFavouritesRequest deleteFavouritesRequest = DeleteFavouritesRequest.builder()
                .authorizationHeader(super.getAuthorizationHeader())
                .favouriteId(favouriteId)
                .build();
        return controller.delete(deleteFavouritesRequest);
    }
}
