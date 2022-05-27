package ik.thecatapi.checks;

import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.categories.ResponseBodyCategory;
import io.qameta.allure.Step;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoriesChecks {
    @Step("присутствует ключ \"name\" со значением {expectedName}")
    public void checkCategoryName(GetCategoriesResponse response, String expectedName) {
        List<ResponseBodyCategory> categories = response.getBody();
        boolean anyMatch = categories.stream().anyMatch(category -> category.getName().equals(expectedName));
        assertEquals(200, response.getStatusCode(), "Status Code");
        assertTrue(anyMatch, "присутствует ключ \"name\" со значением " + expectedName);
    }
}
