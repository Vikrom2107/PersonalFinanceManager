package ru.netology;

import org.json.simple.JSONObject;

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
            JSONObject product = new JSONObject();
            product.put("title", "мыло");
            product.put("date", "2022.11.13");
            product.put("sum", 1500);
//            System.out.println(product.toJSONString());
            out.println(product.toJSONString());
            String answer = in.readLine();
            System.out.println(answer);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
