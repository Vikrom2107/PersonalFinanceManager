package ru.netology;

import org.json.simple.JSONObject;

import java.util.*;

public class MaxCategories {
    public JSONObject readRequest(JSONObject product, HashMap<String,CategoryProducts> categoryProducts) {
        String category = null;
        boolean findProduct = false;
        try {
            int sum = Integer.parseInt(product.get("sum").toString());
            String[] date = product.get("date").toString().split("\\.");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]) - 1;
            int day = Integer.parseInt(date[2]);
            GregorianCalendar calendar = new GregorianCalendar(year,month,day);

            for (Map.Entry<String, CategoryProducts> entry : categoryProducts.entrySet()) {
                if (entry.getValue().getProducts().contains((String) product.get("title"))) {
                    entry.getValue().setOneSumDate(sum, calendar);
                    findProduct = true;
                    category = entry.getKey();
                }
            }
            if (!findProduct) {
                categoryProducts.get("другое").setOneProduct((String) product.get("title"));
                categoryProducts.get("другое").setOneSumDate(sum,calendar);
                category = "другое";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JSONObject answer = new JSONObject();
        JSONObject maxCategories = new JSONObject();
        maxCategories.put("category", category);
        maxCategories.put("sum", categoryProducts.get(category).getSumDate().lastKey());
        answer.put("maxCategories", maxCategories);
        return answer;
    }
}

