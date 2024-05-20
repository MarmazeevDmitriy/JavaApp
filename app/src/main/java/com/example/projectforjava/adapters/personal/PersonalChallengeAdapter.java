package com.example.projectforjava.adapters.personal;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;

import com.example.projectforjava.R;
import com.example.projectforjava.alerts.alertDialogs.PersonalChallengeConfirmationAlert;
import com.example.projectforjava.database.dao.PersonalChallengeDao;
import com.example.projectforjava.database.model.PersonalChallenge;
import com.example.projectforjava.fragments.main.HomeFragment;
import com.example.projectforjava.utils.DateUtil;

public class PersonalChallengeAdapter extends RecyclerView.Adapter<PersonalChallengeAdapter.ChallengeViewHolder> {

    private ArrayList<PersonalChallenge> personalChallengeList;
    private OnItemClickListener listener;
    private PersonalChallengeDao personalChallengeDao;

    Context context;
    private WeakReference<HomeFragment> homeFragmentWeakReference;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PersonalChallengeAdapter(ArrayList<PersonalChallenge> personalChallengeList, Context context, PersonalChallengeDao dao, WeakReference<HomeFragment> weakReference) {
        this.personalChallengeList = personalChallengeList;
        this.context = context;
        this.personalChallengeDao = dao;
        this.homeFragmentWeakReference = weakReference;
    }

    @NonNull
    @Override
    public ChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_challenge, parent, false);
        return new ChallengeViewHolder(itemView, listener, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PersonalChallenge currentPersonalChallenge = personalChallengeList.get(position);
        holder.bind(currentPersonalChallenge, position);

        CheckBox checkBox = holder.itemView.findViewById(R.id.checkbox_challenge);

        // Устанавливаем слушатель событий для чекбокса
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d("Adapter", currentPersonalChallenge.getTitle());
                    PersonalChallengeConfirmationAlert.showConfirmationDialog(context, holder.itemView, checkBox, currentPersonalChallenge, position, personalChallengeDao, holder.adapter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return personalChallengeList.size();
    }

    public void removeFromChallengeList(PersonalChallenge personalChallengeToRemove, int position){
        this.personalChallengeList.remove(personalChallengeToRemove);
        this.notifyItemRemoved(position);
    }

    public void sortOnChallengeCompleted(PersonalChallenge comletedPersonalChallenge, int position){
        this.personalChallengeList.set(position, comletedPersonalChallenge);
        homeFragmentWeakReference.get().updateRecyclerView(PersonalChallenge.sortChallenges(personalChallengeList));
    }

    private ArrayList<PersonalChallenge> getChallengeList() {
        return personalChallengeList;
    }

    public static class ChallengeViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewRepetitions;
        private TextView textViewDays;
        private CheckBox checkBox;
        private View challengeSeparatorLine;
        private LinearLayout itemChallengeLayout;

        private PersonalChallengeAdapter adapter;

        public ChallengeViewHolder(@NonNull View itemView, final OnItemClickListener listener, PersonalChallengeAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            textViewTitle = itemView.findViewById(R.id.textView_challenge_title);
            textViewDescription = itemView.findViewById(R.id.textView_challenge_description);
            textViewRepetitions = itemView.findViewById(R.id.textView_challenge_repetitions);
            textViewDays = itemView.findViewById(R.id.textView_challenge_days);
            checkBox = itemView.findViewById(R.id.checkbox_challenge);
            challengeSeparatorLine = itemView.findViewById(R.id.challenge_separator);
            itemChallengeLayout = itemView.findViewById(R.id.item_challenge);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(PersonalChallenge personalChallenge, int position) {
            ArrayList<PersonalChallenge> personalChallengeList = adapter.getChallengeList();

            textViewTitle.setText(personalChallenge.getTitle());
            textViewDescription.setText(personalChallenge.getDescription());
            if(personalChallenge.getRepetitions() >= 0) {
                textViewRepetitions.setVisibility(View.VISIBLE);
                textViewRepetitions.setText("Repetitions: " + personalChallenge.getRepetitions());
            }
            else
                textViewRepetitions.setVisibility(View.GONE);
            if(personalChallenge.getDays().size() < 7) {
                textViewDays.setVisibility(View.VISIBLE);
                textViewDays.setText(String.join(", ", personalChallenge.getDays()));
            }
            else
                textViewDays.setVisibility(View.GONE);

            if(personalChallenge.getCompletedToday()) {
                itemChallengeLayout.setBackgroundResource(R.drawable.background_personal_challenge_item_checked);
                checkBox.setChecked(true);
                checkBox.setEnabled(false);
            }

            else {
                itemChallengeLayout.setBackgroundResource(R.drawable.background_rounded_rectangle_border_black);
                checkBox.setChecked(false);
                checkBox.setEnabled(true);
            }

            if(position < personalChallengeList.size() - 1)
                if(personalChallengeList.get(position).getDays().contains(DateUtil.getCurrentDayOfWeek()) &&
                        !personalChallengeList.get(position + 1).getDays().contains(DateUtil.getCurrentDayOfWeek()) ||
                        !personalChallengeList.get(position).getCompletedToday() &&
                                personalChallengeList.get(position + 1).getCompletedToday())
                    challengeSeparatorLine.setVisibility(View.VISIBLE);
        }
    }
}