package com.example.projectforjava.fragments.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectforjava.R;
import com.example.projectforjava.activities.EditPersonalChallengeActivity;
import com.example.projectforjava.activities.MainActivity;
import com.example.projectforjava.activities.SettingsActivity;
import com.example.projectforjava.adapters.personal.PersonalChallengeAdapter;
import com.example.projectforjava.database.dao.PersonalChallengeDao;
import com.example.projectforjava.database.db.PersonalChallengeDatabase;
import com.example.projectforjava.database.model.PersonalChallenge;
import com.example.projectforjava.preferences.AuthPreferencesManager;
import com.example.projectforjava.preferences.DatePreferences;
import com.example.projectforjava.preferences.PreferencesManager;
import com.example.projectforjava.utils.ImgUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageView profileImageView;
    private PersonalChallengeAdapter adapter;
    private ArrayList<PersonalChallenge> personalChallengeList;

    private static final int EDIT_PERSONAL_CHALLENGE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    PreferencesManager preferencesManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        recyclerView = view.findViewById(R.id.personalChallengesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        profileImageView = view.findViewById(R.id.profileImageView);

        // Проверяем, был ли уже сегодня первый вход
        if (DatePreferences.isFirstLoginToday(requireContext())) {
            // Если это первый вход в этот день, обновляем базу данных
            updateDatabase();
            // Устанавливаем флаг о первом входе в этот день
            DatePreferences.setLastLoginDate(requireContext());
        }

        new LoadChallengesTask(this).execute();

        view.findViewById(R.id.buttonAddChallenge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditChallengeActivity(-1);
            }
        });

        view.findViewById(R.id.achievements).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Achievements", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });

        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthPreferencesManager authPreferencesManager = new AuthPreferencesManager(requireContext());
                authPreferencesManager.setIsLogined(false);
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.startAuth();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        preferencesManager = new PreferencesManager(requireContext());
        String name = preferencesManager.getProfileName();
        if(name != null){
            TextView textView = view.findViewById(R.id.usersName);
            textView.setText(name);
        }
        Bitmap img = preferencesManager.getProfileIcon();
        if(img != null){
            profileImageView.setImageBitmap(img);
        }

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void updateDatabase() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                // Получаем экземпляр базы данных
                PersonalChallengeDatabase personalChallengeDatabase = PersonalChallengeDatabase.getInstance(requireContext());

                // Получаем экземпляр дао
                PersonalChallengeDao personalChallengeDao = personalChallengeDatabase.challengeDao();

                // Получаем все челленджи из базы данных
                List<PersonalChallenge> personalChallenges = personalChallengeDao.getAllChallenges();

                // Обновляем значение isCompletedToday для каждого челленджа
                for (PersonalChallenge personalChallenge : personalChallenges) {
                    personalChallenge.setCompletedToday(false);
                    personalChallengeDao.update(personalChallenge);
                }
                return null;
            }
        }.execute();
    }

    public void startEditChallengeActivity(int position) {
        Intent intent = new Intent(getActivity(), EditPersonalChallengeActivity.class);
        intent.putExtra("position", position);
        startActivityForResult(intent, EDIT_PERSONAL_CHALLENGE);
    }

    public void setOnItemClickListener(PersonalChallengeAdapter.OnItemClickListener listener) {
        adapter.setOnItemClickListener(listener);
    }

    private static class LoadChallengesTask extends AsyncTask<Void, Void, List<PersonalChallenge>> {
        private WeakReference<HomeFragment> fragmentReference;

        LoadChallengesTask(HomeFragment fragment) {
            fragmentReference = new WeakReference<>(fragment);
        }

        @Override
        protected List<PersonalChallenge> doInBackground(Void... voids) {
            // Здесь происходит загрузка челленджей из базы данных
            return PersonalChallengeDatabase.getInstance(fragmentReference.get().getContext())
                    .challengeDao().getAllChallenges();
        }

        @Override
        protected void onPostExecute(List<PersonalChallenge> personalChallenges) {
            super.onPostExecute(personalChallenges);
            HomeFragment fragment = fragmentReference.get();
            if (fragment != null) {
                fragment.personalChallengeList = new ArrayList<>(personalChallenges);
                fragment.adapter = new PersonalChallengeAdapter(PersonalChallenge.sortChallenges(fragment.personalChallengeList), fragment.getActivity(), PersonalChallengeDatabase.getInstance(fragmentReference.get().getContext()).challengeDao(), fragmentReference);
                fragment.recyclerView.setAdapter(fragment.adapter);
                fragment.setOnItemClickListener(new PersonalChallengeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Действия при нажатии на элемент списка
                        // Например, открытие экрана редактирования челленджа
                        fragment.startEditChallengeActivity(position);
                    }
                });
            }
        }
    }

    public void updateRecyclerView(ArrayList<PersonalChallenge> personalChallengeList){

        PersonalChallengeAdapter newAdapter = new PersonalChallengeAdapter(personalChallengeList, getActivity(), PersonalChallengeDatabase.getInstance(requireContext()).challengeDao(), new WeakReference<>(this));
        recyclerView.setAdapter(newAdapter);

        newAdapter.setOnItemClickListener(new PersonalChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startEditChallengeActivity(position);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PERSONAL_CHALLENGE) {
            if (resultCode == Activity.RESULT_OK) {
                // Здесь вызывайте обновление адаптера, например, загрузку челленджей из базы данных
                new LoadChallengesTask(this).execute();
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Определите ExecutorService
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Handler mainHandler = new Handler(Looper.getMainLooper());

            // Создайте Callable для обработки изображения
            Callable<Bitmap> imageProcessingTask = new Callable<Bitmap>() {
                @Override
                public Bitmap call() throws Exception {
                    Bitmap imageBitmap;
                    // Теперь у вас есть URI выбранного изображения. Вы можете использовать его для отображения или сохранения.
                    try {
                        imageBitmap = ImgUtils.getBitmapFromUri(requireActivity(), imageUri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    imageBitmap = ImgUtils.scaleSquareBitmap(imageBitmap, 128);
                    return ImgUtils.getRoundedSquareBitmap(imageBitmap, 128, 20);
                }
            };

            // Выполните задачу и получите результат
            Future<Bitmap> future = executorService.submit(imageProcessingTask);

            // Обновите UI на главном потоке
            executorService.execute(() -> {
                try {
                    Bitmap processedBitmap = future.get();

                    // Возвращаемся на главный поток для обновления UI
                    mainHandler.post(() -> {
                        preferencesManager.setProfileIcon(processedBitmap);
                        profileImageView.setImageBitmap(processedBitmap);
                    });

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });

            // Закройте ExecutorService после завершения всех задач
            executorService.shutdown();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}