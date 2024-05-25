package com.example.projectforjava.fragments.global;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.projectforjava.R;
import com.example.projectforjava.adapters.friends.FriendsChallengesAdapter;
import com.example.projectforjava.adapters.global.GlobalChallengeAdapter;
import com.example.projectforjava.adapters.personal.PersonalChallengeAdapter;
import com.example.projectforjava.alerts.alertDialogs.ChallengeAlert;
import com.example.projectforjava.alerts.interfaces.ChallengeDialogListener;
import com.example.projectforjava.database.db.PersonalChallengeDatabase;
import com.example.projectforjava.database.model.PersonalChallenge;
import com.example.projectforjava.fragments.friends.ChallengesFromFriendsFragment;
import com.example.projectforjava.templates.global.GlobalChallenge;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AcceptedChallengesFragment extends Fragment implements ChallengeDialogListener {
    private RecyclerView recyclerView;
    private GlobalChallengeAdapter adapter;
    private List<GlobalChallenge> globalChallengeList;

    public AcceptedChallengesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accepted_challenges, container, false);

        recyclerView = view.findViewById(R.id.acceptedChallengesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация списка данных (в реальном приложении данные можно получать из сети или базы данных)
        globalChallengeList = GlobalChallengeDataGenerator.generateGlobalChallenges(getContext(), 20);
        // Добавьте ваши элементы в globalChallengeList

        adapter = new GlobalChallengeAdapter(getContext(), globalChallengeList, new WeakReference<>(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new GlobalChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, GlobalChallenge globalChallenge) {
                ChallengeAlert.showFriendChallengeDialog(getContext(), position, AcceptedChallengesFragment.this);
            }
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

    public void updateRecyclerView(ArrayList<GlobalChallenge> globalChallengeList){

        adapter = new GlobalChallengeAdapter(getContext(), globalChallengeList, new WeakReference<>(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new GlobalChallengeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, GlobalChallenge globalChallenge) {
                ChallengeAlert.showFriendChallengeDialog(getContext(), position, AcceptedChallengesFragment.this);
            }
        });
    }
}