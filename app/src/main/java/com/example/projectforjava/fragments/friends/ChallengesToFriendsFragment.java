package com.example.projectforjava.fragments.friends;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
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
import com.example.projectforjava.adapters.friends.FriendsChallengesAdapter;
import com.example.projectforjava.templates.friends.FriendsChallenge;
import com.example.projectforjava.utils.ImgUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChallengesToFriendsFragment extends Fragment {
    private static final int EDIT_FRIENDS_CHALLENGE = 3;

    FriendsChallengesAdapter adapter;

    EditText editTextSearch;

    ArrayList<FriendsChallenge> challengeList;

    public ChallengesToFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges_to_friends, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.toFriendsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> friendsNames = new ArrayList<>();
        friendsNames.add("John Doe");
        friendsNames.add("Steel wolf");

        challengeList = new ArrayList<>();
        Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.random);
        drawable = ImgUtils.BitmapToDrawable(ImgUtils.scaleRectangleBitmap(ImgUtils.drawableToBitmap(drawable), 256, 128), requireContext());
        challengeList.add(new FriendsChallenge(drawable, "ChallengeToFriends1", "Description 1", friendsNames, "Димастер"));
        challengeList.add(new FriendsChallenge(drawable, "ChallengeToFriends2", "Description 2", friendsNames, "Димастер"));
        challengeList.add(new FriendsChallenge(drawable, "ChallengeToFriends3", "Description 3", friendsNames, "Димастер"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FriendsChallengesAdapter(challengeList, true);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FriendsChallengesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Действия при нажатии на элемент списка
                // Например, открытие экрана редактирования челленджа
                startEditChallengeActivity(position);
            }
        });

        editTextSearch = view.findViewById(R.id.editTextSearchToFriends);
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

        view.findViewById(R.id.buttonAddChallengeForFriends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditChallengeActivity(-1);
            }
        });

        return view;
    }

    public void startEditChallengeActivity(int position) {
        Intent intent = new Intent(getActivity(), EditFriendChallengeActivity.class);
        intent.putExtra("position", position);
        challengeList = (ArrayList<FriendsChallenge>) adapter.getFriendsChallengeListFull();
        if(!challengeList.isEmpty()){
            ArrayList<String> titles = new ArrayList<>();
            for(FriendsChallenge friendsChallenge: challengeList){
                titles.add(friendsChallenge.getTitle());
            }
            intent.putStringArrayListExtra("Titles", titles);
        }
        if(position != -1){
            intent.putExtra("FriendsChallenge", adapter.getItem(position));
        }
        startActivityForResult(intent, EDIT_FRIENDS_CHALLENGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_FRIENDS_CHALLENGE) {
            if (resultCode == Activity.RESULT_OK) {
                switch (Objects.requireNonNull(data.getStringExtra("Action"))){
                    case "Add":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            adapter.addItem(data.getParcelableExtra("challenge", FriendsChallenge.class));
                        }
                        break;
                    case "Update":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            adapter.updateItem(data.getParcelableExtra("challenge", FriendsChallenge.class), data.getIntExtra("position", -1));
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