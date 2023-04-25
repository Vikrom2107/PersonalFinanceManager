package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestRequest {

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket("127.0.0.1",8989);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            JsonObject product = new JsonObject();
            product.addProperty("title", "колбаса");
            product.addProperty("date", "2022.12.10");
            product.addProperty("sum", 1500);
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            System.out.println(gson.toJson(product));
            out.println(gson.toJson(product));
            String answer = in.readLine();
            System.out.println(answer);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
