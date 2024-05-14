package com.example.projectforjava.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.projectforjava.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Entity(tableName = "personalChallenge")
public class PersonalChallenge {
    @PrimaryKey
    @NonNull
    private String title;
    private String description;
    private int repetitions;
    private ArrayList<String> days;
    private boolean isCompletedToday;

    // Конструктор с передачей списка дней
    public PersonalChallenge(String title, String description, int repetitions, ArrayList<String> days, boolean isCompletedToday) {
        this.title = title;
        this.description = description;
        this.repetitions = repetitions;
        this.days = days;
        this.isCompletedToday = isCompletedToday;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public boolean getCompletedToday() {
        return isCompletedToday;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public void setCompletedToday(boolean isCompletedToday) {
        this.isCompletedToday = isCompletedToday;
    }

    @NonNull
    @Override
    public String toString() {
        return "PersonalChallenge{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", repetitions=" + repetitions +
                ", days=" + days +
                ", isCompletedToday=" + isCompletedToday +
                '}';
    }

    // Сортирует челленджи сначала по isCompletedToday (false вперед), затем по дням недели
    public static void sortChallenges(ArrayList<PersonalChallenge> personalChallengeList) {
        // Сортировка по полю isCompletedToday
        Collections.sort(personalChallengeList, new Comparator<PersonalChallenge>() {
            @Override
            public int compare(PersonalChallenge personalChallenge1, PersonalChallenge personalChallenge2) {
                // Сначала сравниваем по isCompletedToday (false считается большим)
                if (personalChallenge1.isCompletedToday && !personalChallenge2.isCompletedToday) {
                    return 1;
                } else if (!personalChallenge1.isCompletedToday && personalChallenge2.isCompletedToday) {
                    return -1;
                } else {
                    return sortByDaysOfWeek(personalChallenge1, personalChallenge2);
                }
            }
        });
    }

    // Вспомогательный метод для сортировки по дням недели
    private static int sortByDaysOfWeek(PersonalChallenge c1, PersonalChallenge c2) {
        // Получаем текущий день недели
        String currentDayOfWeek = DateUtil.getCurrentDayOfWeek();
        // Проверяем, содержат ли челленджи текущий день недели в списке дней
        boolean c1ContainsDay = c1.getDays().contains(currentDayOfWeek);
        boolean c2ContainsDay = c2.getDays().contains(currentDayOfWeek);
        // Сортируем челленджи: те, у которых текущий день недели в списке дней, идут вперед
        if (c1ContainsDay && !c2ContainsDay) {
            return -1;
        } else if (!c1ContainsDay && c2ContainsDay) {
            return 1;
        } else {
            return 0;
        }
    }
}