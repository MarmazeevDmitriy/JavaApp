package com.example.projectforjava.alerts;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectforjava.adapter.PersonalChallengeAdapter;
import com.example.projectforjava.R;
import com.example.projectforjava.database.dao.PersonalChallengeDao;
import com.example.projectforjava.database.model.PersonalChallenge;
import com.example.projectforjava.utils.DateUtil;

public class PersonalChallengeConfirmationAlert {

    public static void showConfirmationDialog(Context context, View itemView, CheckBox checkBox, PersonalChallenge currentPersonalChallenge, int position, PersonalChallengeDao personalChallengeDao, PersonalChallengeAdapter adapter) {
        // Инфлейтим макет алерта
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_challenge, null);

        // Находим кнопки в макете алерта
        Button buttonNo = dialogView.findViewById(R.id.exit_button_no);
        Button buttonYes = dialogView.findViewById(R.id.exit_button_yes);

        // Создаем диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView); // Устанавливаем макет в диалог

        // Создаем AlertDialog
        AlertDialog alertDialog = builder.create();

        // Устанавливаем слушатели для кнопок
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(false);
                alertDialog.dismiss(); // Закрываем алерт
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View v) {
                String today = DateUtil.getCurrentDayOfWeek(); // Получаем сегодняшний день недели (например, "Monday")
                if (!currentPersonalChallenge.getDays().contains(today)) {
                    Toast.makeText(context, "You shouldn't complete this challenge today", Toast.LENGTH_SHORT).show();
                    checkBox.setChecked(false);
                }
                else {
                    // Если пользователь подтвердил выполнение челленджа, меняем цвет фона челленджа
                    LinearLayout ItemChallengeLayout = itemView.findViewById(R.id.item_challenge);
                    ItemChallengeLayout.setBackgroundResource(R.drawable.background_challenge_item_checked);
                    // Блокируем чекбокс, чтобы он не мог быть изменен
                    checkBox.setEnabled(false);

                    currentPersonalChallenge.setCompletedToday(true);
                    if(currentPersonalChallenge.getRepetitions() > 0)
                        currentPersonalChallenge.setRepetitions(currentPersonalChallenge.getRepetitions() - 1);
                    // Обновляем запись в базе данных

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            if(currentPersonalChallenge.getRepetitions() == 0)
                                personalChallengeDao.delete(currentPersonalChallenge);
                            // Обновляем запись в базе данных
                            else
                                personalChallengeDao.update(currentPersonalChallenge);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            if(currentPersonalChallenge.getRepetitions() == 0){
                                Log.d("Deleted", "challenge");
                                adapter.removeFromChallengeList(currentPersonalChallenge, position);
                            }
                            else
                                adapter.sortOnChallengeCompleted(currentPersonalChallenge, position);
                        }
                    }.execute();
                }

                alertDialog.dismiss(); // Закрываем алерт
            }
        });

        alertDialog.setCancelable(false);
        // Показываем алерт
        alertDialog.show();
    }
}