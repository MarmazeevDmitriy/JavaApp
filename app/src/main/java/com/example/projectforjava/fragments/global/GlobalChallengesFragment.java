package com.example.projectforjava.fragments.global;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.projectforjava.R;
import com.example.projectforjava.activities.EditFriendChallengeActivity;
import com.example.projectforjava.activities.GlobalChallengeActivity;
import com.example.projectforjava.adapters.friends.FriendsChallengesAdapter;
import com.example.projectforjava.adapters.global.GlobalChallengeAdapter;
import com.example.projectforjava.templates.friends.FriendsChallenge;
import com.example.projectforjava.templates.global.GlobalChallenge;

import java.util.ArrayList;
import java.util.List;

public class GlobalChallengesFragment extends Fragment {
    private static final int OPEN_GLOBAL_CHALLENGE = 7;
    private RecyclerView recyclerView;
    private GlobalChallengeAdapter adapter;
    private List<GlobalChallenge> globalChallengeList;

    public GlobalChallengesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_global_challenges, container, false);

        recyclerView = view.findViewById(R.id.globalChallengesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация списка данных (в реальном приложении данные можно получать из сети или базы данных)
        globalChallengeList = GlobalChallengeDataGenerator.generateGlobalChallenges(getContext(), 20);
        // Добавьте ваши элементы в globalChallengeList

        adapter = new GlobalChallengeAdapter(getContext(), globalChallengeList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new GlobalChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, GlobalChallenge globalChallenge) {
                openGlobalChallengeActivity(globalChallenge);
            }
        });

        EditText editTextSearch = view.findViewById(R.id.editTextSearchGlobalChallenges);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    public void openGlobalChallengeActivity(GlobalChallenge globalChallenge) {
        Intent intent = new Intent(getActivity(), GlobalChallengeActivity.class);
        if(globalChallenge != null){
            intent.putExtra("globalChallenge", globalChallenge);
            startActivityForResult(intent, OPEN_GLOBAL_CHALLENGE);
        }
    }
}