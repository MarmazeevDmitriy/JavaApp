package com.example.projectforjava.database.asyncTasks;

import android.os.AsyncTask;

import com.example.projectforjava.database.dao.PersonalChallengeDao;

public class DeleteChallengeAsyncTask extends AsyncTask<String, Void, Void> {
    private PersonalChallengeDao personalChallengeDao;

    public DeleteChallengeAsyncTask(PersonalChallengeDao personalChallengeDao) {
        this.personalChallengeDao = personalChallengeDao;
    }

    @Override
    protected Void doInBackground(String... strings) {
        personalChallengeDao.deleteChallengeByTitle(strings[0]);
        return null;
    }
}