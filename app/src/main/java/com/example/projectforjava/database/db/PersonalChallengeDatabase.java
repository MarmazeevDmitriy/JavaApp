package com.example.projectforjava.database.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.projectforjava.database.dao.PersonalChallengeDao;
import com.example.projectforjava.database.converters.Converters;
import com.example.projectforjava.database.model.PersonalChallenge;

@Database(entities = {PersonalChallenge.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class PersonalChallengeDatabase extends RoomDatabase {
    public abstract PersonalChallengeDao challengeDao();

    private static volatile PersonalChallengeDatabase instance;

    public static synchronized PersonalChallengeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            PersonalChallengeDatabase.class, "challenge_database")
                    .build();
        }
        return instance;
    }
}
