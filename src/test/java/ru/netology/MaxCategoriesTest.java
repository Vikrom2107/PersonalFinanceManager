package ru.netology;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

class MaxCategoriesTest {
    MaxCategories max = new MaxCategories();
    @Test
    void readRequest() {
        HashMap<String,CategoryProducts> categoryProducts = new HashMap<>();
        categoryProducts.put("другое", new CategoryProducts("другое"));
        categoryProducts.put("еда", new CategoryProducts("еда"));
        categoryProducts.get("еда").setOneProduct("булка");

        JsonObject product = new JsonObject();
        product.addProperty("title", "молоко");
        product.addProperty("date", "2022.02.08");
        product.addProperty("sum", 1000);

        String expect = "молоко";
        Assertions.assertEquals(expect, product.get("title").getAsString());

        String[] expect2 = {"2022","02","08"};
        String[] date = product.get("date").getAsString().split("\\.");
        Assertions.assertArrayEquals(expect2,date);

        String expect3 = "другое";
        JsonObject answer = max.readRequest(product, categoryProducts);
        JsonObject maxCategory = (JsonObject) answer.get("maxCategories");
        Assertions.assertEquals(expect3,maxCategory.get("category").getAsString());
    }
}