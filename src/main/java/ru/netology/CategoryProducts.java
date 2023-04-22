package ru.netology;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CategoryProducts {
    private String name;
    private List<String> products = new ArrayList<>();
    private TreeMap<Integer, GregorianCalendar> sumDate= new TreeMap<>();

    public CategoryProducts(String name) {
        this.name = name;
        sumDate.put(0, new GregorianCalendar(2022,Calendar.JANUARY,1));
    }
    public String getName() {
        return name;
    }

    public List<String> getProducts() {
        return products;
    }

    public TreeMap<Integer, GregorianCalendar> getSumDate() {
        return sumDate;
    }

    public void setOneProduct(String product) {
        this.products.add(product);
    }
    public void setOneSumDate(int sum, GregorianCalendar cal) {
        sumDate.put(sum, cal);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public void setSumDate(TreeMap<Integer, GregorianCalendar> sumDate) {
        this.sumDate = sumDate;
    }

    public String toString() {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        Date today = sumDate.get(sumDate.lastKey()).getTime();
        return name + " " + products + " " + sumDate.lastKey() + " " + df.format(today);
    }
}
