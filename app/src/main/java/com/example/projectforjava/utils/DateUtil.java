package com.example.projectforjava.utils;

import java.util.Calendar;

public class DateUtil {
    // Метод для получения текущего дня недели
    public static String getCurrentDayOfWeek() {
        // Получаем текущую дату
        Calendar calendar = Calendar.getInstance();
        // Получаем день недели
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Преобразуем номер дня недели в строковое представление
        String[] daysOfWeek = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return daysOfWeek[dayOfWeek - 1];
    }
}
