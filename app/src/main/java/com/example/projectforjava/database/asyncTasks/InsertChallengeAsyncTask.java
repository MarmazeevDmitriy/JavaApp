package com.example.projectforjava.database.asyncTasks;

import android.os.AsyncTask;

import com.example.projectforjava.database.dao.PersonalChallengeDao;
import com.example.projectforjava.database.model.PersonalChallenge;

public class InsertChallengeAsyncTask extends AsyncTask<PersonalChallenge, Void, Void> {
    private PersonalChallengeDao personalChallengeDao;

    public InsertChallengeAsyncTask(PersonalChallengeDao personalChallengeDao) {
        this.personalChallengeDao = personalChallengeDao;
    }

    @Override
    protected Void doInBackground(PersonalChallenge... personalChallenges) {
        personalChallengeDao.insert(personalChallenges[0]);
        return null;
    }
}