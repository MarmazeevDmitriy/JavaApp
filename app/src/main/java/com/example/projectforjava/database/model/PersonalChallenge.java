package com.example.projectforjava.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.projectforjava.utils.DateUtil;

import java.util.ArrayList;

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
    public static ArrayList<PersonalChallenge> sortChallenges(ArrayList<PersonalChallenge> personalChallengeList) {
        ArrayList<PersonalChallenge> completedTodayList = new ArrayList<>();
        ArrayList<PersonalChallenge> notContainsTodayList = new ArrayList<>();
        ArrayList<PersonalChallenge> otherList = new ArrayList<>();

        // Разделение списка на три категории
        for (PersonalChallenge challenge : personalChallengeList) {
            if (challenge.getCompletedToday()) {
                completedTodayList.add(challenge);
            } else if (!challenge.getDays().contains(DateUtil.getCurrentDayOfWeek())) {
                notContainsTodayList.add(challenge);
            } else {
                otherList.add(challenge);
            }
        }

        // Объединение списков в нужном порядке
        personalChallengeList.clear();
        personalChallengeList.addAll(otherList);
        personalChallengeList.addAll(notContainsTodayList);
        personalChallengeList.addAll(completedTodayList);

        return personalChallengeList;
    }
}