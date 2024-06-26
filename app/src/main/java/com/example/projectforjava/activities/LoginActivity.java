package com.example.projectforjava.activities;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.projectforjava.models.User;
import com.example.projectforjava.preferences.AuthPreferencesManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private String enteredUsername, enteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.changeStatusBar(this);
        setContentView(R.layout.activity_login);

        AuthPreferencesManager authPreferencesManager = new AuthPreferencesManager(this);
        usernameEditText = findViewById(R.id.enterUsernameEditText);
        passwordEditText = findViewById(R.id.password_edit_text);

        MainActivity mainActivity = new MainActivity();
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredUsername = usernameEditText.getText().toString();
                enteredPassword = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredPassword)) {
                    Toast.makeText(LoginActivity.this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {

                    User user = new User(enteredUsername, enteredPassword);
                    // Вход пользователя
                    ServerAPI.getInstance().loginUser(user, new Callback<Boolean>() {
                        @Override
                        public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                boolean isUserFound = Boolean.TRUE.equals(response.body());
                                if(isUserFound){
                                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    authPreferencesManager.setEmail(enteredUsername);
                                    authPreferencesManager.setPassword(enteredPassword);
                                    setResult(Activity.RESULT_OK, new Intent());
                                    finish();
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Ошибка: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                            Toast.makeText(LoginActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        Button registrationButton = findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivityForResult(registrationIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            setResult(Activity.RESULT_OK, new Intent());
            finish();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        ExitorAlert.showExitDialog(this);
    }
}