package com.example.projectforjava.fragments.friends;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;

import com.example.projectforjava.R;
import com.example.projectforjava.adapters.friends.FriendsChallengesAdapter;
import com.example.projectforjava.alerts.alertDialogs.FriendChallengeAlert;
import com.example.projectforjava.alerts.interfaces.FriendChallengeDialogListener;
import com.example.projectforjava.templates.friends.FriendsChallenge;

import java.util.ArrayList;
import java.util.Objects;

public class ChallengesFromFriendsFragment extends Fragment implements FriendChallengeDialogListener {
    private static final int OPEN_ACTIONS_DIALOG = 4;

    FriendsChallengesAdapter adapter;
    private RecyclerView recyclerView;

    EditText editTextSearch;

    public ChallengesFromFriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges_from_friends, container, false);

        recyclerView = view.findViewById(R.id.fromFriendsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<FriendsChallenge> cList = new ArrayList<>();

        Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.random);
        ArrayList<String> friendsNames = new ArrayList<>();
        friendsNames.add("Friend 1");
        friendsNames.add("Friend 2");
        friendsNames.add("Friend 3");
        // Заполните friendList вашими данными
        cList.add(new FriendsChallenge(drawable, "ChallengeFromFriends1", "Description 1", friendsNames, "John Doe"));
        cList.add(new FriendsChallenge(drawable, "ChallengeFromFriends2", "Description 2", friendsNames, "Steel wolf"));
        cList.add(new FriendsChallenge(drawable, "ChallengeFromFriends3", "Description 3", friendsNames, "Soup lover"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FriendsChallengesAdapter(cList, false);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FriendsChallengesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Действия при нажатии на элемент списка
                // Например, открытие экрана редактирования челленджа
                editTextSearch.setEnabled(false);
                FriendChallengeAlert.showFriendChallengeDialog(getContext(), position, ChallengesFromFriendsFragment.this);
            }
        });

        editTextSearch = view.findViewById(R.id.editTextSearchFromFriends);
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

    @Override
    public void onReject(int position) {
        View itemView = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView;
        itemView.setBackgroundColor(getResources().getColor(R.color.red, requireActivity().getTheme()));
        vanishAndPopItem(itemView, position);
    }

    @Override
    public void onComplete(int position) {
        View itemView = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView;
        itemView.setBackgroundColor(getResources().getColor(R.color.green, requireActivity().getTheme()));
        vanishAndPopItem(itemView, position);
    }

    @Override
    public void onCancel() {
        editTextSearch.setEnabled(true);
    }

    public void vanishAndPopItem(final View view, final int position){
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                view.setAlpha(alpha);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Ничего не делаем
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Удаляем элемент из списка и уведомляем адаптер
                adapter.deleteItem(position);
                editTextSearch.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Ничего не делаем
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Ничего не делаем
            }
        });
        animator.start();
    }
}