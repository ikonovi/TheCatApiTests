package ik.thecatapi.services.requests;

import ik.thecatapi.controllers.requests.CategoriesRequestController;
import ik.thecatapi.models.requests.categories.GetCategoriesRequest;
import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import io.qameta.allure.Step;

public class CategoriesService extends AbstractService {
    private final CategoriesRequestController controller;

    public CategoriesService(String apiKey) {
        super(apiKey);
        this.controller = new CategoriesRequestController();
    }

    @Step("Выполнить get запрос к /categories")
    public GetCategoriesResponse requestGetCategories() {
        GetCategoriesRequest getCategoriesRequest = GetCategoriesRequest.builder()
                .authorizationHeader(super.getAuthorizationHeader())
                .build();
        return controller.get(getCategoriesRequest);
    }
}
