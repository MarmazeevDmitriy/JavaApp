package com.example.projectforjava.database.asyncTasks;

import android.os.AsyncTask;

import com.example.projectforjava.database.dao.PersonalChallengeDao;
import com.example.projectforjava.database.model.PersonalChallenge;

public class UpdateChallengeAsyncTask extends AsyncTask<Object, Void, Void> {
    private PersonalChallengeDao personalChallengeDao;

    public UpdateChallengeAsyncTask(PersonalChallengeDao personalChallengeDao) {
        this.personalChallengeDao = personalChallengeDao;
    }

    @Override
    protected Void doInBackground(Object... objects) {
        PersonalChallenge personalChallenge = (PersonalChallenge) objects[0];
        String oldTitle = (String) objects[1];
        personalChallengeDao.updateChallengeByTitle(oldTitle, personalChallenge.getTitle(), personalChallenge.getDescription(), personalChallenge.getRepetitions(), personalChallenge.getDays(), personalChallenge.getCompletedToday());
        return null;
    }
}
