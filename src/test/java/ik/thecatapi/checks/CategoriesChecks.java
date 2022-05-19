package ik.thecatapi.checks;

import ik.thecatapi.models.requests.categories.GetCategoriesResponse;
import ik.thecatapi.models.requests.categories.ResponseBodyCategory;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.List;

public class CategoriesChecks {
    @Step("присутствует ключ \"name\" со значением {expectedName}")
    public void checkCategoryName(GetCategoriesResponse response, String expectedName) {
        List<ResponseBodyCategory> categories = response.getBody();
        boolean anyMatch = categories.stream().anyMatch(category -> category.getName().equals(expectedName));
        Assert.assertEquals(response.getStatusCode(), 200, "Status Code");
        Assert.assertTrue(anyMatch, "присутствует ключ \"name\" со значением " + expectedName);
    }
}
