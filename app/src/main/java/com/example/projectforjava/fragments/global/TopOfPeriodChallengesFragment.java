package com.example.projectforjava.fragments.global;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projectforjava.R;

public class TopOfPeriodChallengesFragment extends Fragment {
    TextView title;
    TextView description;
    TextView approximateTime;

    View layout;

    public TopOfPeriodChallengesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_of_period_challenges, container, false);
        layout = view.findViewById(R.id.topOfDay);
        layout.findViewById(R.id.topOfPeriodGlobalChallengeImage).setBackgroundResource(R.drawable.chickens);
        title = layout.findViewById(R.id.topOfPeriodGlobalChallengeTitle);
        title.setText("КУРОЧКИ");
        description = layout.findViewById(R.id.topOfPeriodGlobalChallengeDescription);
        description.setText("Убейти 10 курочек в майнкрафте ВО ВРЕМЯ ПАДЕНИЯ С ВЫСОТЫ 200+ блоков и зажарьте их мясо");
        approximateTime = layout.findViewById(R.id.topOfPeriodGlobalChallengeApproximateTime);
        approximateTime.setText("~15 мин");

        layout = view.findViewById(R.id.topOfWeek);
        layout.findViewById(R.id.topOfPeriodGlobalChallengeImage).setBackgroundResource(R.drawable.my_friend);
        title = layout.findViewById(R.id.topOfPeriodGlobalChallengeTitle);
        title.setText("Бег на скорость");
        description = layout.findViewById(R.id.topOfPeriodGlobalChallengeDescription);
        description.setText("Пробегите 20 км за 50 минут");
        approximateTime = layout.findViewById(R.id.topOfPeriodGlobalChallengeApproximateTime);
        approximateTime.setText("~60 мин");

        layout = view.findViewById(R.id.topOfMonth);
        layout.findViewById(R.id.topOfPeriodGlobalChallengeImage).setBackgroundResource(R.drawable.random);
        title = layout.findViewById(R.id.topOfPeriodGlobalChallengeTitle);
        title.setText("Озеленение");
        description = layout.findViewById(R.id.topOfPeriodGlobalChallengeDescription);
        description.setText("Посадите 100 саженцев любого дерева у себя на даче или где-то в лесу");
        approximateTime = layout.findViewById(R.id.topOfPeriodGlobalChallengeApproximateTime);
        approximateTime.setText("~15 дней");

        return view;
    }
}