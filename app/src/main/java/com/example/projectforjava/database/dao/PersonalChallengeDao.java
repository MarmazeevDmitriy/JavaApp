package com.example.projectforjava.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectforjava.database.model.PersonalChallenge;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PersonalChallengeDao {
    @Insert
    void insert(PersonalChallenge personalChallenge);

    @Query("SELECT * FROM PersonalChallenge")
    List<PersonalChallenge> getAllChallenges();

    @Query("UPDATE PersonalChallenge SET title = :newTitle, description = :newDescription, repetitions = :newRepetitions, isCompletedToday = :isCompletedToday, days = :newDays WHERE title = :oldTitle")
    void updateChallengeByTitle(String oldTitle, String newTitle, String newDescription, int newRepetitions, ArrayList<String> newDays, boolean isCompletedToday);

    @Query("DELETE FROM PersonalChallenge WHERE title = :title")
    void deleteChallengeByTitle(String title);

    @Update
    void update(PersonalChallenge personalChallenge);

    @Delete
    void delete(PersonalChallenge personalChallenge);

    @Query("SELECT title FROM PersonalChallenge")
    List<String> getAllChallengeTitles();

    @Query("SELECT title FROM PersonalChallenge WHERE isCompletedToday = :isCompletedToday")
    List<String> getUncomletedChallengeTitles(boolean isCompletedToday);

    @Query("SELECT * FROM PersonalChallenge WHERE title = :title")
    PersonalChallenge getChallengeByTitle(String title);
}
