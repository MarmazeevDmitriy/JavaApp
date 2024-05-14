package com.example.projectforjava.database.converters;

import androidx.room.TypeConverter;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item).append(",");
        }
        return sb.toString();
    }

    @TypeConverter
    public static ArrayList<String> toArrayList(String string) {
        String[] array = string.split(",");
        ArrayList<String> list = new ArrayList<>();
        for (String item : array) {
            list.add(item);
        }
        return list;
    }
}
