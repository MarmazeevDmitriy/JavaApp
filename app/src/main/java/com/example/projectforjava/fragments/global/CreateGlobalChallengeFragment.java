package com.example.projectforjava.fragments.global;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectforjava.R;
import com.example.projectforjava.adapters.global.GlobalChallengeAdapter;
import com.example.projectforjava.templates.global.GlobalChallenge;

import java.util.List;

public class CreateGlobalChallengeFragment extends Fragment {
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

        adapter = new GlobalChallengeAdapter(getContext(), globalChallengeList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}