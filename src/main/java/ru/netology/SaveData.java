package ru.netology;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SaveData {

    public static void saveDataBIN(File file, HashMap<String, CategoryProducts> categories) {
        try (FileOutputStream fOut = new FileOutputStream(file);
             DataOutputStream dOut = new DataOutputStream(fOut)) {
            dOut.writeInt(categories.size());
            for (Map.Entry<String, CategoryProducts> entry : categories.entrySet()) {
                dOut.writeUTF(entry.getKey());
                dOut.writeUTF(entry.getValue().getName());
                dOut.writeInt(entry.getValue().getProducts().size());
                for (String prod : entry.getValue().getProducts()) {
                    dOut.writeUTF(prod);
                }
                dOut.writeInt(entry.getValue().getSumDate().size());
                for(Map.Entry<Integer, GregorianCalendar> entry2 : entry.getValue().getSumDate().entrySet()) {
                    dOut.writeInt(entry2.getKey());
                    DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
                    Date today = entry2.getValue().getTime();
                    dOut.writeUTF(df.format(today));
                }
            }

        } catch (IOException ex)  {
            ex.printStackTrace();
        }
    }
    public static HashMap<String, CategoryProducts> loadDataBIN (File file) {
        HashMap<String, CategoryProducts> categories= new HashMap<>();
        try (FileInputStream fis2 = new FileInputStream(file);
             DataInputStream dis2 = new DataInputStream(fis2)) {
            int sizeCateg = dis2.readInt();
            for (int i = 0; i < sizeCateg; i++) {
                String keyCateg = dis2.readUTF();
                String nameCateg = dis2.readUTF();
                categories.put(keyCateg,new CategoryProducts(nameCateg));
                int sizeProd = dis2.readInt();
                for (int j = 0; j < sizeProd; j++) {
                    String product = dis2.readUTF();
                    categories.get(keyCateg).setOneProduct(product);
                }
                int sizeSumDate = dis2.readInt();
                for (int k = 0; k < sizeSumDate; k++) {
                    int sum = dis2.readInt();
                    String [] date = dis2.readUTF().split("\\.");
                    int year = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]) - 1;
                    int day = Integer.parseInt(date[2]);
                    categories.get(keyCateg).setOneSumDate(sum, new GregorianCalendar(year,month,day));
                }
            }
        } catch (IOException ex)  {
            ex.printStackTrace();
        }
        return categories;
    }

}
