package com.example.projectforjava.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectforjava.alerts.alertDialogs.ExitorAlert;
import com.example.projectforjava.R;
import com.example.projectforjava.api.ServerAPI;
import com.example.projectforjava.customElements.CustomStatusBar;
import com.example.projectforjava.preferences.AuthPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.projectforjava.models.User;

public class RegistrationActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.changeStatusBar(this);
        setContentView(R.layout.activity_registration);

        AuthPreferencesManager authPreferencesManager = new AuthPreferencesManager(this);
        usernameEditText = findViewById(R.id.registerUsername);
        passwordEditText = findViewById(R.id.password_edit_text);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = usernameEditText.getText().toString();
                String newPassword = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!isPasswordValid(newPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                } else {

                    User user = new User(newUsername, newPassword);

                    // Регистрация пользователя
                    ServerAPI.getInstance().registerUser(user, new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.isSuccessful()) {
                                String message = response.body();
                                assert message != null;
                                if(message.equals("Success")){
                                    Toast.makeText(RegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    authPreferencesManager.setEmail(newUsername);
                                    authPreferencesManager.setPassword(newPassword);
                                    setResult(Activity.RESULT_OK, new Intent());
                                    finish();
                                }
                                else{
                                    Toast.makeText(RegistrationActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Ошибка: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Toast.makeText(RegistrationActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8; // Проверка, что пароль содержит 8 или больше символов
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        ExitorAlert.showExitDialog(this);
    }
}