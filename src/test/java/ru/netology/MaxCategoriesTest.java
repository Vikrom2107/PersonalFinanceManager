package ru.netology;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MaxCategoriesTest {
    MaxCategories max = new MaxCategories();
    @Test
    void readRequest() {
        HashMap<String,CategoryProducts> categoryProducts = new HashMap<>();
        categoryProducts.put("другое", new CategoryProducts("другое"));
        categoryProducts.put("еда", new CategoryProducts("еда"));
        categoryProducts.get("еда").setOneProduct("булка");

        JSONObject product = new JSONObject();
        product.put("title", "молоко");
        product.put("date", "2022.02.08");
        product.put("sum", 1000);

        String expect = "молоко";
        Assertions.assertEquals(expect, product.get("title").toString());

        String[] expect2 = {"2022","02","08"};
        String[] date = product.get("date").toString().split("\\.");
        Assertions.assertArrayEquals(expect2,date);

        String expect3 = "другое";
        JSONObject answer = max.readRequest(product, categoryProducts);
        JSONObject maxCategory = (JSONObject) answer.get("maxCategories");
        Assertions.assertEquals(expect3,maxCategory.get("category"));
    }
}