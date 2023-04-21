package ru.netology;

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
        int i = sumDate.lastKey() + sum;
        sumDate.put(i, cal);
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
        return name + " " + products + " " + sumDate.lastKey();
    }
}
