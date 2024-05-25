package com.example.projectforjava.adapters.global;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectforjava.R;
import com.example.projectforjava.fragments.global.AcceptedChallengesFragment;
import com.example.projectforjava.fragments.global.CreateGlobalChallengeFragment;
import com.example.projectforjava.templates.global.GlobalChallenge;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GlobalChallengeAdapter extends RecyclerView.Adapter<GlobalChallengeAdapter.GlobalChallengeViewHolder> {
    private List<GlobalChallenge> globalChallengeList;
    private List<GlobalChallenge> filteredList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private WeakReference<AcceptedChallengesFragment> acceptedChallengesFragmentWeakReference;
    private WeakReference<CreateGlobalChallengeFragment> createGlobalChallengeFragmentWeakReference;

    public interface OnItemClickListener {
        void onItemClick(int position, GlobalChallenge item);
    }

    public GlobalChallengeAdapter(Context context, List<GlobalChallenge> globalChallengeList) {
        this.context = context;
        this.globalChallengeList = globalChallengeList;
        this.filteredList = new ArrayList<>(globalChallengeList);
    }

    public GlobalChallengeAdapter(Context context, List<GlobalChallenge> globalChallengeList, WeakReference<AcceptedChallengesFragment> acceptedChallengesFragmentWeakReference) {
        this.context = context;
        this.globalChallengeList = globalChallengeList;
        this.filteredList = new ArrayList<>(globalChallengeList);
        this.acceptedChallengesFragmentWeakReference = acceptedChallengesFragmentWeakReference;
    }

    public GlobalChallengeAdapter(Context context, List<GlobalChallenge> globalChallengeList, WeakReference<CreateGlobalChallengeFragment> createGlobalChallengeFragmentWeakReference, boolean create) {
        this.context = context;
        this.globalChallengeList = globalChallengeList;
        this.filteredList = new ArrayList<>(globalChallengeList);
        this.createGlobalChallengeFragmentWeakReference = createGlobalChallengeFragmentWeakReference;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public GlobalChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_global_challenge, parent, false);
        return new GlobalChallengeViewHolder(view, onItemClickListener, filteredList);
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalChallengeViewHolder holder, int position) {
        GlobalChallenge challenge = filteredList.get(position);

        holder.globalChallengeImage.setImageDrawable(challenge.getImage());
        holder.globalChallengeTitle.setText(challenge.getTitle());
        holder.globalChallengeDescription.setText(challenge.getDescription());
        holder.globalChallengeApproximateTime.setText("Примерное время: " + challenge.getApproximateTimeValue() + " " + challenge.getApproximateTimeMeasure());
        holder.globalChallengeTimesCompleted.setText("Количество выполнений: " + challenge.getCompletions());
        holder.likesAmount.setText(String.valueOf(challenge.getLikes()));

        holder.likeButton.setImageResource(challenge.isLiked() ? R.drawable.like_pressed : R.drawable.like_not_pressed);
        holder.warningButton.setImageResource(challenge.isWarned() ? R.drawable.warning_pressed : R.drawable.warning_not_pressed);

        holder.likeButton.setOnClickListener(v -> {
            challenge.setLiked(!challenge.isLiked());
            challenge.setLikes(challenge.getLikes() + (int)Math.pow(-1, challenge.isLiked() ? 0 : 1));
            notifyItemChanged(position);
        });

        holder.warningButton.setOnClickListener(v -> {
            challenge.setWarned(!challenge.isWarned());
            notifyItemChanged(position);
        });

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position, challenge);
            }
        });

        if(acceptedChallengesFragmentWeakReference != null || createGlobalChallengeFragmentWeakReference != null){
            holder.likeButton.setVisibility(View.GONE);
            holder.warningButton.setVisibility(View.GONE);
            holder.likesAmount.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public List<GlobalChallenge> getGlobalChallengeListFull() {
        return globalChallengeList;
    }

    public void addItem(GlobalChallenge globalChallenge){
        globalChallengeList.add(globalChallenge);
        filteredList.add(globalChallenge);
        notifyItemInserted(filteredList.size() - 1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItem(int position){
        deleteItem(position);
    }

    public void deleteItem(int position){
        if(position != -1){
            globalChallengeList.remove(filteredList.get(position));
            filteredList.remove(position);
        }
        if(acceptedChallengesFragmentWeakReference != null){
            acceptedChallengesFragmentWeakReference.get().updateRecyclerView((ArrayList<GlobalChallenge>) globalChallengeList);
        }
        if(createGlobalChallengeFragmentWeakReference != null){
            createGlobalChallengeFragmentWeakReference.get().updateRecyclerView((ArrayList<GlobalChallenge>) globalChallengeList);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(globalChallengeList);
        } else {
            text = text.toLowerCase();
            for (GlobalChallenge item : globalChallengeList) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class GlobalChallengeViewHolder extends RecyclerView.ViewHolder {
        ImageView globalChallengeImage;
        TextView globalChallengeTitle;
        TextView globalChallengeDescription;
        TextView globalChallengeApproximateTime;
        TextView globalChallengeTimesCompleted;
        ImageButton warningButton;
        ImageButton likeButton;
        TextView likesAmount;

        public GlobalChallengeViewHolder(View itemView, OnItemClickListener onItemClickListener, List<GlobalChallenge> filteredList) {
            super(itemView);
            globalChallengeImage = itemView.findViewById(R.id.globalChallengeImage);
            globalChallengeTitle = itemView.findViewById(R.id.globalChallengeTitle);
            globalChallengeDescription = itemView.findViewById(R.id.globalChallengeDescription);
            globalChallengeApproximateTime = itemView.findViewById(R.id.globalChallengeApproximateTime);
            globalChallengeTimesCompleted = itemView.findViewById(R.id.globalChallengeTimesCompleted);
            warningButton = itemView.findViewById(R.id.warningButton);
            likeButton = itemView.findViewById(R.id.likeButton);
            likesAmount = itemView.findViewById(R.id.likesAmount);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, filteredList.get(position));
                }
            });
        }
    }
}
