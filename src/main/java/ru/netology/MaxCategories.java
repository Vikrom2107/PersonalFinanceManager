package ru.netology;

import org.json.simple.JSONObject;

import java.util.*;

public class MaxCategories {
    public JSONObject readRequest(JSONObject product, HashMap<String,CategoryProducts> categoryProducts) {
        String category = null;
        boolean findProduct = false;
        GregorianCalendar calendar = null;
        try {
            int sum = Integer.parseInt(product.get("sum").toString());
            String[] date = product.get("date").toString().split("\\.");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]) - 1;
            int day = Integer.parseInt(date[2]);
            calendar = new GregorianCalendar(year,month,day);

            for (Map.Entry<String, CategoryProducts> entry : categoryProducts.entrySet()) {
                if (entry.getValue().getProducts().contains((String) product.get("title"))) {
                    int result = entry.getValue().getSumDate().lastKey() + sum;
                    entry.getValue().setOneSumDate(result, calendar);
                    findProduct = true;
                    category = entry.getKey();
                }
            }
            if (!findProduct) {
                categoryProducts.get("другое").setOneProduct((String) product.get("title"));
                int result = categoryProducts.get("другое").getSumDate().lastKey() + sum;
                categoryProducts.get("другое").setOneSumDate(result,calendar);
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

        // Добавляем maxCategories для года, месяца и дня
        answer.put("maxYearCategory", getMaxYearCategory(categoryProducts, calendar));
        answer.put("maxMonthCategory", getMaxMonthCategory(categoryProducts, calendar));
        answer.put("getMaxDayCategory", getMaxDayCategory(categoryProducts, calendar));

        return answer;
    }
    public JSONObject getMaxYearCategory (HashMap<String,CategoryProducts> categoryProducts,
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
        JSONObject maxYearCategory = new JSONObject();
        maxYearCategory.put("category", nameCateg);
        maxYearCategory.put("sum", maxSum);
        return maxYearCategory;
    }
    public JSONObject getMaxMonthCategory (HashMap<String,CategoryProducts> categoryProducts,
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
        JSONObject maxMonthCategory = new JSONObject();
        maxMonthCategory.put("category", nameCateg);
        maxMonthCategory.put("sum", maxSum);
        return maxMonthCategory;
    }
    public JSONObject getMaxDayCategory (HashMap<String,CategoryProducts> categoryProducts,
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
        JSONObject maxDayCategory = new JSONObject();
        maxDayCategory.put("category", nameCateg);
        maxDayCategory.put("sum", maxSum);
        return maxDayCategory;
    }
}

