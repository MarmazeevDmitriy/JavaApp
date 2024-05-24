package com.example.projectforjava.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.projectforjava.R;
import com.example.projectforjava.api.ServerAPI;
import com.example.projectforjava.models.User;
import com.example.projectforjava.preferences.AuthPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        AuthPreferencesManager authPreferencesManager = new AuthPreferencesManager(this);

        findViewById(R.id.deleteAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(authPreferencesManager.getEmail(), authPreferencesManager.getPassword());
                // Удаление пользователя
                ServerAPI.getInstance().deleteUser(user, new Callback<Boolean>() {
                    @Override
                    public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                        if (response.isSuccessful()) {
                            boolean isDeleted = Boolean.TRUE.equals(response.body());
                            if(isDeleted){
                                authPreferencesManager.setEmail("");
                                authPreferencesManager.setPassword("");
                                authPreferencesManager.setIsLogined(false);
                                finishAffinity();
                            }
                            else{
                                Toast.makeText(SettingsActivity.this, "Не удалось удалить аккаунт", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SettingsActivity.this, "Ошибка: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                        Toast.makeText(SettingsActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}