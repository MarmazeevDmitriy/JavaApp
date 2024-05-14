package com.example.projectforjava.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectforjava.R;
import com.example.projectforjava.customElements.CustomStatusBar;
import com.example.projectforjava.database.asyncTasks.DeleteChallengeAsyncTask;
import com.example.projectforjava.database.asyncTasks.InsertChallengeAsyncTask;
import com.example.projectforjava.database.asyncTasks.UpdateChallengeAsyncTask;
import com.example.projectforjava.database.dao.PersonalChallengeDao;
import com.example.projectforjava.database.db.PersonalChallengeDatabase;
import com.example.projectforjava.database.model.PersonalChallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditChallengeActivity extends AppCompatActivity {
    private PersonalChallenge personalChallengeToEdit;

    private Button btEditChallenge;

    private EditText etTitle;
    private EditText etDescription;
    private EditText etRepetitions;

    private String title;
    private String description;
    private int repetitions;

    private ArrayList<String> chosenDays = new ArrayList<>();
    private ArrayList<String> days;
    private ArrayList<PersonalChallenge> personalChallengeList;

    private RadioGroup chooseDays;
    private RadioButton btUntilDeletion;
    private RadioButton btEveryday;

    private PersonalChallengeDatabase personalChallengeDatabase;
    private PersonalChallengeDao personalChallengeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.changeStatusBar(this);
        setContentView(R.layout.activity_edit_challenge);

        etTitle = findViewById(R.id.editTextTitle);
        etDescription = findViewById(R.id.editTextDescription);
        etRepetitions = findViewById(R.id.editTextRepetitions);

        btEditChallenge = findViewById(R.id.buttonEditChallenge);
        Button btDeleteChallenge = findViewById(R.id.buttonDeleteChallenge);
        Button btAddNewChallenge = findViewById(R.id.buttonAddNewChallenge);

        RadioGroup chooseRepetitions = findViewById(R.id.chooseRepetitions);
        chooseDays = findViewById(R.id.chooseDays);

        btUntilDeletion = findViewById(R.id.chooseRepetitions1);
        btEveryday = findViewById(R.id.chooseDays1);

        personalChallengeDatabase = PersonalChallengeDatabase.getInstance(this);
        personalChallengeDao = personalChallengeDatabase.challengeDao();

        // Получаем данные о челлендже для редактирования
        int position = getIntent().getIntExtra("position", -1);
        if (position != -1) {
            // Получаем челлендж из базы данных по позиции
            // Обратите внимание, что вам может потребоваться выполнить это асинхронно
            new Thread(new Runnable() {
                @Override
                public void run() {
                    personalChallengeList = (ArrayList<PersonalChallenge>) personalChallengeDao.getAllChallenges();
                    if (position < personalChallengeList.size()) {
                        personalChallengeToEdit = personalChallengeList.get(position);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateFields(personalChallengeToEdit);
                            }
                        });
                    }
                }
            }).start();
        } else {
            btEditChallenge.setVisibility(View.GONE);
            btDeleteChallenge.setVisibility(View.GONE);
            btAddNewChallenge.setVisibility(View.VISIBLE);
            btUntilDeletion.setChecked(true);
            btEveryday.setChecked(true);
        }

        btAddNewChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewChallenge();
            }
        });

        btEditChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editChallenge();
            }
        });

        btDeleteChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChallenge();
            }
        });

        // Установка слушателей для радиогруппы выбора повторений
        chooseRepetitions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Получите LayoutParams для вашей кнопки
                LinearLayout chooseDaysLayout = findViewById(R.id.chooseDaysLayout);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        chooseDaysLayout.getLayoutParams();

                // В этом методе вы можете обрабатывать изменения состояния радиокнопок
                // Например, вы можете получить идентификатор выбранной радиокнопки и выполнить соответствующие действия
                if (checkedId == R.id.chooseRepetitions1) {
                    etRepetitions.setVisibility(View.GONE);
                    // Установите новое правило расположения, например, layout_above
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.chooseRepetitionsLayout);
                } else if (checkedId == R.id.chooseRepetitions2) {
                    etRepetitions.setVisibility(View.VISIBLE);
                    // Установите новое правило расположения, например, layout_above
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.editTextRepetitions);
                }

                // Примените новые параметры макета к вашей кнопке
                chooseDaysLayout.setLayoutParams(layoutParams);
            }
        });

        // Установка слушателей для радиогруппы выбора дней
        chooseDays.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Получите LayoutParams для вашей кнопки
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        btEditChallenge.getLayoutParams();
                LinearLayout daysToChoose = findViewById(R.id.daysToChoose);
                // В этом методе вы можете обрабатывать изменения состояния радиокнопок
                // Например, вы можете получить идентификатор выбранной радиокнопки и выполнить соответствующие действия
                if (checkedId == R.id.chooseDays1) {
                    daysToChoose.setVisibility(View.GONE);
                    // Установите новое правило расположения, например, layout_above
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.chooseDaysLayout);
                } else if (checkedId == R.id.chooseDays2) {
                    daysToChoose.setVisibility(View.VISIBLE);
                    // Установите новое правило расположения, например, layout_above
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.daysToChoose);
                }

                // Примените новые параметры макета к вашей кнопке
                btEditChallenge.setLayoutParams(layoutParams);
            }
        });

        // Обработчик для чекбоксов
        CompoundButton.OnCheckedChangeListener checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String day = null;
                String checkBoxId = getResources().getResourceEntryName(buttonView.getId());
                switch (checkBoxId) {
                    case "checkbox_monday":
                        day = "Monday";
                        break;
                    case "checkbox_tuesday":
                        day = "Tuesday";
                        break;
                    case "checkbox_wednesday":
                        day = "Wednesday";
                        break;
                    case "checkbox_thursday":
                        day = "Thursday";
                        break;
                    case "checkbox_friday":
                        day = "Friday";
                        break;
                    case "checkbox_saturday":
                        day = "Saturday";
                        break;
                    case "checkbox_sunday":
                        day = "Sunday";
                        break;
                }
                if (isChecked) {
                    chosenDays.add(day);
                } else {
                    // Если чекбокс снят, удаляем день из списка chosenDays
                    chosenDays.remove(day);
                }
            }
        };

        for(String day : new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"}){
            String id = "checkbox_" + day;
            int resID = getResources().getIdentifier(id, "id", getPackageName());
            CheckBox checkBox = findViewById(resID);
            checkBox.setOnCheckedChangeListener(checkBoxListener);
        }
    }

    @SuppressLint("StaticFieldLeak")
    boolean isChallengeTitleUnique(String newTitle) {
        try {
            // Получаем список всех названий челленджей асинхронно
            List<String> challengeTitles = new AsyncTask<Void, Void, List<String>>() {
                @Override
                protected List<String> doInBackground(Void... voids) {
                    return personalChallengeDao.getAllChallengeTitles();
                }
            }.execute().get(); // Ждем завершения выполнения AsyncTask и получаем результат

            // Проверяем уникальность нового названия
            if (challengeTitles != null) {
                for (String title : challengeTitles) {
                    if (title.equalsIgnoreCase(newTitle)) {
                        // Если название уже существует, вернуть false
                        return false;
                    }
                }
            }
            // Если название уникально, вернуть true
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // В случае ошибки также считаем, что название уникально
            return true;
        }
    }

    private boolean approveNewChallengeInfo(){
        title = etTitle.getText().toString().trim();
        description = etDescription.getText().toString().trim();
        String repetitionsStr = etRepetitions.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка уникальности названия челленджа
        if ((personalChallengeToEdit == null || !personalChallengeToEdit.getTitle().equalsIgnoreCase(title)) && !isChallengeTitleUnique(title)) {
            Toast.makeText(this, "PersonalChallenge title must be unique", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка на длину описания челленджа
        if (description.length() > 50) {
            int excessChars = description.length() - 50;
            Toast.makeText(this, "Description exceeds limit by " + excessChars + " characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(btUntilDeletion.isChecked()){
            repetitions = -1;
        }
        else if (!repetitionsStr.isEmpty()) {
            repetitions = Integer.parseInt(repetitionsStr);
            if(repetitions < 1) {
                Toast.makeText(this, "Repetitions must be greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "Repetitions must be an integer", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(btEveryday.isChecked()){
            days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        }
        else if (!chosenDays.isEmpty()) {
            days = chosenDays;
        }
        else {
            Toast.makeText(this, "You should choose some days", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void addNewChallenge() {
        if(approveNewChallengeInfo()) {
            // Создание нового объекта PersonalChallenge
            PersonalChallenge newPersonalChallenge = new PersonalChallenge(title, description, repetitions, days, false);

            // Создание и запуск AsyncTask для добавления челленджа в базу данных
            new InsertChallengeAsyncTask(personalChallengeDao).execute(newPersonalChallenge);
            setResult(RESULT_OK);
            finish();
        }
    }

    private void editChallenge() {
        if(approveNewChallengeInfo()) {
            String oldTitle = personalChallengeToEdit.getTitle();

            // Обновление данных челленджа
            personalChallengeToEdit.setTitle(title);
            personalChallengeToEdit.setDescription(description);
            personalChallengeToEdit.setRepetitions(repetitions);
            personalChallengeToEdit.setDays(days);

            // Создание и запуск AsyncTask для обновления челленджа в базе данных
            new UpdateChallengeAsyncTask(personalChallengeDao).execute(personalChallengeToEdit, oldTitle);
            setResult(RESULT_OK);
            finish();
        }
    }

    private void deleteChallenge() {
        String title = personalChallengeToEdit.getTitle();

        // Создание и запуск AsyncTask для удаления челленджа из базы данных
        new DeleteChallengeAsyncTask(personalChallengeDao).execute(title);

        setResult(RESULT_OK);
        finish();
    }

    private void populateFields(PersonalChallenge personalChallengeToEdit) {
        if (personalChallengeToEdit != null) {
            etTitle.setText(personalChallengeToEdit.getTitle());
            etDescription.setText(personalChallengeToEdit.getDescription());
            days = personalChallengeToEdit.getDays();
            SetRepetitionFieldActive();
            SetChosenDaysCheckboxesActive();
        }
    }

    private void SetRepetitionFieldActive() {
        if(personalChallengeToEdit.getRepetitions() == -1)
            btUntilDeletion.setChecked(true);
        else{
            RadioButton btChooseRepetitions = findViewById(R.id.chooseRepetitions2);
            btChooseRepetitions.setChecked(true);
            if(personalChallengeToEdit.getRepetitions() != -1)
                etRepetitions.setText(String.valueOf(personalChallengeToEdit.getRepetitions()));
            etRepetitions.setVisibility(View.VISIBLE);
            LinearLayout chooseDaysLayout = findViewById(R.id.chooseDaysLayout);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    chooseDaysLayout.getLayoutParams();
            layoutParams.addRule(RelativeLayout.BELOW, R.id.editTextRepetitions);
            chooseDaysLayout.setLayoutParams(layoutParams);
        }
    }

    private void SetChosenDaysCheckboxesActive() {
        if (days != null && days.size() < 7) {
            RadioButton btChosenDays = findViewById(R.id.chooseDays2);
            btChosenDays.setChecked(true);
            CheckBox checkBox = null;
            for (String day : days) {
                switch (day) {
                    case "Monday":
                        checkBox = findViewById(R.id.checkbox_monday);
                        break;
                    case "Tuesday":
                        checkBox = findViewById(R.id.checkbox_tuesday);
                        break;
                    case "Wednesday":
                        checkBox = findViewById(R.id.checkbox_wednesday);
                        break;
                    case "Thursday":
                        checkBox = findViewById(R.id.checkbox_thursday);
                        break;
                    case "Friday":
                        checkBox = findViewById(R.id.checkbox_friday);
                        break;
                    case "Saturday":
                        checkBox = findViewById(R.id.checkbox_saturday);
                        break;
                    case "Sunday":
                        checkBox = findViewById(R.id.checkbox_sunday);
                        break;
                }
                if (checkBox != null) {
                    checkBox.setChecked(true);
                }
            }
            chosenDays = days;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    btEditChallenge.getLayoutParams();
            LinearLayout daysToChoose = findViewById(R.id.daysToChoose);
            daysToChoose.setVisibility(View.VISIBLE);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.daysToChoose);
            btEditChallenge.setLayoutParams(layoutParams);
        }
        else {
            btEveryday.setChecked(true);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
