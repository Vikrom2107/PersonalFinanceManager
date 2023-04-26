package ru.netology;

import com.google.gson.JsonObject;

import java.util.*;

public class MaxCategories {
    public JsonObject readRequest(JsonObject product, HashMap<String,CategoryProducts> categoryProducts) {
        String category = null;
        boolean findProduct = false;
        GregorianCalendar calendar = null;
        try {
            int sum = product.get("sum").getAsInt();
            String[] date = product.get("date").getAsString().split("\\.");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]) - 1;
            int day = Integer.parseInt(date[2]);
            calendar = new GregorianCalendar(year,month,day);

            for (Map.Entry<String, CategoryProducts> entry : categoryProducts.entrySet()) {
                if (entry.getValue().getProducts().contains(product.get("title").getAsString())) {
                    int result = entry.getValue().getSumDate().lastKey() + sum;
                    entry.getValue().setOneSumDate(result, calendar);
                    findProduct = true;
                    category = entry.getKey();
                }
            }
            if (!findProduct) {
                categoryProducts.get("другое").setOneProduct(product.get("title").getAsString());
                int result = categoryProducts.get("другое").getSumDate().lastKey() + sum;
                categoryProducts.get("другое").setOneSumDate(result,calendar);
                category = "другое";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JsonObject answer = new JsonObject();
        JsonObject maxCategories = new JsonObject();
        maxCategories.addProperty("category", category);
        maxCategories.addProperty("sum", categoryProducts.get(category).getSumDate().lastKey());
        answer.add("maxCategories", maxCategories);

        // Добавляем maxCategories для года, месяца и дня
        answer.add("maxYearCategory", getMaxYearCategory(categoryProducts, calendar));
        answer.add("maxMonthCategory", getMaxMonthCategory(categoryProducts, calendar));
        answer.add("maxDayCategory", getMaxDayCategory(categoryProducts, calendar));

        return answer;
    }
    public JsonObject getMaxYearCategory (HashMap<String,CategoryProducts> categoryProducts,
                                          GregorianCalendar calendar) {
        int maxSum = 0;
        String nameCateg = null;
        GregorianCalendar calendar1 = (GregorianCalendar) calendar.clone();
        calendar1.add(Calendar.YEAR, -1);
        GregorianCalendar calendar2 = (GregorianCalendar) calendar.clone();
        calendar2.add(Calendar.DATE, 1);
        for (Map.Entry<String, CategoryProducts> entry : categoryProducts.entrySet()) {
            for (Map.Entry<Integer, GregorianCalendar> entry2 : entry.getValue().getSumDate().entrySet()) {
                if (entry2.getValue().after(calendar1) && entry2.getValue().before(calendar2)) {
                    if (entry2.getKey() > maxSum) {
                        maxSum = entry2.getKey();
                        nameCateg = entry.getKey();
                    }
                }
            }
        }
        JsonObject maxYearCategory = new JsonObject();
        maxYearCategory.addProperty("category", nameCateg);
        maxYearCategory.addProperty("sum", maxSum);
        return maxYearCategory;
    }
    public JsonObject getMaxMonthCategory (HashMap<String,CategoryProducts> categoryProducts,
                                          GregorianCalendar calendar) {
        int maxSum = 0;
        String nameCateg = null;
        GregorianCalendar calendar1 = (GregorianCalendar) calendar.clone();
        calendar1.add(Calendar.MONTH, -1);
        GregorianCalendar calendar2 = (GregorianCalendar) calendar.clone();
        calendar2.add(Calendar.DATE, 1);
        for (Map.Entry<String, CategoryProducts> entry : categoryProducts.entrySet()) {
            for (Map.Entry<Integer, GregorianCalendar> entry2 : entry.getValue().getSumDate().entrySet()) {
                if (entry2.getValue().after(calendar1) && entry2.getValue().before(calendar2)) {
                    if (entry2.getKey() > maxSum) {
                        maxSum = entry2.getKey();
                        nameCateg = entry.getKey();
                    }
                }
            }
        }
        JsonObject maxMonthCategory = new JsonObject();
        maxMonthCategory.addProperty("category", nameCateg);
        maxMonthCategory.addProperty("sum", maxSum);
        return maxMonthCategory;
    }
    public JsonObject getMaxDayCategory (HashMap<String,CategoryProducts> categoryProducts,
                                           GregorianCalendar calendar) {
        int maxSum = 0;
        String nameCateg = null;
        GregorianCalendar calendar1 = (GregorianCalendar) calendar.clone();
        calendar1.add(Calendar.DATE, -1);
        GregorianCalendar calendar2 = (GregorianCalendar) calendar.clone();
        calendar2.add(Calendar.DATE, 1);
        for (Map.Entry<String, CategoryProducts> entry : categoryProducts.entrySet()) {
            for (Map.Entry<Integer, GregorianCalendar> entry2 : entry.getValue().getSumDate().entrySet()) {
                if (entry2.getValue().after(calendar1) && entry2.getValue().before(calendar2)) {
                    if (entry2.getKey() > maxSum) {
                        maxSum = entry2.getKey();
                        nameCateg = entry.getKey();
                    }
                }
            }
        }
        JsonObject maxDayCategory = new JsonObject();
        maxDayCategory.addProperty("category", nameCateg);
        maxDayCategory.addProperty("sum", maxSum);
        return maxDayCategory;
    }
}

