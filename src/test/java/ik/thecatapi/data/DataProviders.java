package ik.thecatapi.data;

import org.testng.annotations.DataProvider;

public class DataProviders {
    @DataProvider(name = "Category names")
    public static Object[][] provideCategoryNames() {
        return new Object[][] {
                {"boxes"}
        };
    }
}
