package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MaxCategories max = new MaxCategories();
        HashMap<String, CategoryProducts> categories;
        File dataBIN = new File("data.bin");
        if (dataBIN.exists()) {
            categories = SaveData.loadDataBIN(dataBIN);
        } else {
            categories = loadCategories(new File("categories.tsv"));
            categories.put("другое", new CategoryProducts("другое"));
            categories.get("другое").setOneProduct("машина");
        }
        for (Map.Entry<String, CategoryProducts> entry : categories.entrySet()) {
            System.out.println("Категория: " + entry.getValue().toString());
        }

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                        FileWriter file = new FileWriter(new File("answer.json"))
                ) {
                    // обработка одного подключения
                    final String name = in.readLine();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    System.out.println(name);
                    JsonObject product = gson.fromJson(name, JsonObject.class);
                    JsonObject answer = (max.readRequest(product, categories));
                    out.println(gson.toJson(answer));
//                    file.write(gson.toJson(answer));
//                    file.flush();
//                    saveCategories(new File("categ1.tsv"), categories);
                    SaveData.saveDataBIN(dataBIN,categories);
                }
            }
        } catch (Exception e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
    public static HashMap<String, CategoryProducts> loadCategories(File categoriesTSV) {
        HashMap<String, CategoryProducts> categories = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(categoriesTSV))) {
            String string = null;
            while ((string = br.readLine()) != null) {
                String[] value = string.split("\t");
                if(!categories.containsKey(value[1])) {
                    CategoryProducts categoryProducts = new CategoryProducts(value[1]);
                    categoryProducts.setOneProduct(value[0]);
                    categories.put(value[1], categoryProducts);
                } else {
                    categories.get(value[1]).setOneProduct(value[0]);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categories;
    }
    public static void saveCategories(File fileTSV, HashMap<String, CategoryProducts> categories) {
        try (FileWriter writer = new FileWriter(fileTSV);
             BufferedWriter out = new BufferedWriter(writer);){
            for (Map.Entry<String, CategoryProducts> entry : categories.entrySet()) {
                for (String product : entry.getValue().getProducts()) {
                    out.write(product + "\t" + entry.getValue().getName() + "\n");
                    out.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}