package com.example.projectforjava.fragments.global;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectforjava.R;
import com.example.projectforjava.activities.EditGlobalChallengeActivity;
import com.example.projectforjava.adapters.global.GlobalChallengeAdapter;
import com.example.projectforjava.templates.global.GlobalChallenge;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateGlobalChallengeFragment extends Fragment {
    private static final int EDIT_GLOBAL_CHALLENGE = 8;
    private RecyclerView recyclerView;
    private GlobalChallengeAdapter adapter;
    private List<GlobalChallenge> globalChallengeList;

    public CreateGlobalChallengeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_global_challenge, container, false);

        recyclerView = view.findViewById(R.id.createGlobalChallengeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация списка данных (в реальном приложении данные можно получать из сети или базы данных)
        globalChallengeList = GlobalChallengeDataGenerator.generateGlobalChallenges(getContext(), 20);
        // Добавьте ваши элементы в globalChallengeList

        adapter = new GlobalChallengeAdapter(getContext(), globalChallengeList, new WeakReference<>(this), true);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new GlobalChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, GlobalChallenge globalChallenge) {
                // Действия при нажатии на элемент списка
                // Например, открытие экрана редактирования челленджа
                startEditChallengeActivity(position, globalChallenge);
            }
        });

        view.findViewById(R.id.buttonAddGlobalChallenge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditChallengeActivity(-1, null);
            }
        });

        return view;
    }

    public void startEditChallengeActivity(int position, @Nullable GlobalChallenge globalChallenge) {
        Intent intent = new Intent(getActivity(), EditGlobalChallengeActivity.class);
        intent.putExtra("position", position);
        globalChallengeList = adapter.getGlobalChallengeListFull();
        if(!globalChallengeList.isEmpty()){
            ArrayList<String> titles = new ArrayList<>();
            for(GlobalChallenge friendsChallenge: globalChallengeList){
                titles.add(friendsChallenge.getTitle());
            }
            intent.putStringArrayListExtra("Titles", titles);
        }
        if(position != -1){
            intent.putExtra("GlobalChallenge", globalChallenge);
        }
        startActivityForResult(intent, EDIT_GLOBAL_CHALLENGE);
    }

    public void updateRecyclerView(ArrayList<GlobalChallenge> globalChallengeList){

        adapter = new GlobalChallengeAdapter(getContext(), globalChallengeList, new WeakReference<>(this), true);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new GlobalChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, GlobalChallenge globalChallenge) {
                // Действия при нажатии на элемент списка
                // Например, открытие экрана редактирования челленджа
                startEditChallengeActivity(position, globalChallenge);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_GLOBAL_CHALLENGE) {
            if (resultCode == Activity.RESULT_OK) {
                switch (Objects.requireNonNull(data.getStringExtra("Action"))){
                    case "Add":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            adapter.addItem(data.getParcelableExtra("challenge", GlobalChallenge.class));
                        }
                        break;
                    case "Update":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            globalChallengeList.add(data.getParcelableExtra("challenge", GlobalChallenge.class));
                            adapter.updateItem(data.getIntExtra("position", -1));
                        }
                        break;
                    case "Delete":
                        adapter.deleteItem(data.getIntExtra("position", -1));
                        break;
                }
            }
        }
    }
}