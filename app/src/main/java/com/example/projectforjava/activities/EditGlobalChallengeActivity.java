package com.example.projectforjava.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectforjava.R;
import com.example.projectforjava.customElements.CustomStatusBar;
import com.example.projectforjava.templates.friends.Friend;
import com.example.projectforjava.templates.friends.FriendsChallenge;
import com.example.projectforjava.templates.global.GlobalChallenge;
import com.example.projectforjava.utils.ImgUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class EditGlobalChallengeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 5;

    GlobalChallenge globalChallenge;
    ArrayList<String> titles;
    String current_title;

    ImageView image;
    EditText etChallengeTitle;
    EditText etChallengeDescription;
    AutoCompleteTextView editTextTimeMeasureGC;
    EditText etApproximateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.changeStatusBar(this);
        setContentView(R.layout.activity_edit_global_challenge);

        Button editChallengeButton = findViewById(R.id.buttonEditChallengeGC);
        Button deleteChallengeButton = findViewById(R.id.buttonDeleteChallengeGC);
        Button addChallengeButton = findViewById(R.id.buttonAddNewChallengeGC);

        etChallengeTitle = findViewById(R.id.editTextTitleGC);
        etChallengeDescription = findViewById(R.id.editTextDescriptionGC);

        image = findViewById(R.id.globalChallengeImageInput);

        etApproximateTime = findViewById(R.id.editTextTimeGC);
        editTextTimeMeasureGC = findViewById(R.id.editTextTimeMeasureGC);

        int position = getIntent().getIntExtra("position", -1);
        titles = getIntent().getStringArrayListExtra("Titles");

        assert titles != null;
        titles.replaceAll(String::toLowerCase);

        if(position != -1) {
            globalChallenge = getIntent().getParcelableExtra("GlobalChallenge");
            current_title = globalChallenge.getTitle();
            etChallengeTitle.setText(current_title);
            etChallengeDescription.setText(globalChallenge.getDescription());
            image.setImageDrawable(globalChallenge.getImage());
            etApproximateTime.setText(String.valueOf(globalChallenge.getApproximateTimeValue()));
            editTextTimeMeasureGC.setText(globalChallenge.getApproximateTimeMeasure());
        }
        else {
            editChallengeButton.setVisibility(View.GONE);
            deleteChallengeButton.setVisibility(View.GONE);
            addChallengeButton.setVisibility(View.VISIBLE);
        }

        // Создание адаптера с вариантами выбора
        String[] timeMeasures = new String[] {"минуты", "часы", "дни"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, timeMeasures);

        editTextTimeMeasureGC.setAdapter(adapter);

        // Установка кликабельности и предотвращение ввода текста вручную
        editTextTimeMeasureGC.setOnClickListener(v -> {
            editTextTimeMeasureGC.showDropDown();
        });

        editChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editChallenge(position);
            }
        });

        deleteChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChallenge(position);
            }
        });

        addChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChallenge();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private boolean approveNewChallengeInfo(){
        String title = etChallengeTitle.getText().toString().trim();
        String description = etChallengeDescription.getText().toString().trim();
        String approximateTime = etApproximateTime.getText().toString().trim();
        String approximateTimeMeasure = editTextTimeMeasureGC.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || approximateTime.isEmpty() || approximateTimeMeasure.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка уникальности названия челленджа
        if ((titles.contains(title.toLowerCase()) && current_title == null) || (current_title != null && !title.equals(current_title) && titles.contains(title.toLowerCase()))) {
            Toast.makeText(this, "Challenge title must be unique", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка на длину описания челленджа
        if (description.length() > 350) {
            int excessChars = description.length() - 350;
            Toast.makeText(this, "Description exceeds limit by " + excessChars + " characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            if (Integer.parseInt(approximateTime) <= 0){
                Toast.makeText(this, "Enter value greater than 0", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e){
            Toast.makeText(this, "Enter integer value!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void addChallenge(){
        if(approveNewChallengeInfo()){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("Action", "Add");
            resultIntent.putExtra("challenge",
                    new GlobalChallenge(ImgUtils.BitmapToDrawable(ImgUtils.drawableToBitmap(image.getDrawable()), this),
                            etChallengeTitle.getText().toString().trim(),
                            etChallengeDescription.getText().toString().trim(),
                            Integer.parseInt(etApproximateTime.getText().toString().trim()),
                            editTextTimeMeasureGC.getText().toString().trim(),
                            0, 0, 0 ,0, false, false, false));
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void editChallenge(int position){
        if(approveNewChallengeInfo()){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("Action", "Update");
            resultIntent.putExtra("challenge",
                    new GlobalChallenge(image.getDrawable(),
                            etChallengeTitle.getText().toString().trim(),
                            etChallengeDescription.getText().toString().trim(),
                            Integer.parseInt(etApproximateTime.getText().toString().trim()),
                            editTextTimeMeasureGC.getText().toString().trim(),
                            0, 0, 0 ,0, false, false, false));
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void deleteChallenge(int position){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Action", "Delete");
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Bitmap imageBitmap;
            // Теперь у вас есть URI выбранного изображения. Вы можете использовать его для отображения или сохранения.
            try {
                imageBitmap = ImgUtils.getBitmapFromUri(this, imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageBitmap = ImgUtils.scaleSquareBitmap(imageBitmap, 128);
            imageBitmap = ImgUtils.getRoundedSquareBitmap(imageBitmap, 128, 20);
            image.setImageBitmap(imageBitmap);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}