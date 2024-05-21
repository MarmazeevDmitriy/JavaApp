package com.example.projectforjava.adapters.global;

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
import com.example.projectforjava.templates.global.GlobalChallenge;

import java.util.List;

public class GlobalChallengeAdapter extends RecyclerView.Adapter<GlobalChallengeAdapter.GlobalChallengeViewHolder> {
    private List<GlobalChallenge> globalChallengeList;
    private Context context;

    public GlobalChallengeAdapter(Context context, List<GlobalChallenge> globalChallengeList) {
        this.context = context;
        this.globalChallengeList = globalChallengeList;
    }

    @NonNull
    @Override
    public GlobalChallengeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_global_challenge, parent, false);
        return new GlobalChallengeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalChallengeViewHolder holder, int position) {
        GlobalChallenge challenge = globalChallengeList.get(position);

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
    }

    @Override
    public int getItemCount() {
        return globalChallengeList.size();
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

        public GlobalChallengeViewHolder(View itemView) {
            super(itemView);
            globalChallengeImage = itemView.findViewById(R.id.globalChallengeImage);
            globalChallengeTitle = itemView.findViewById(R.id.globalChallengeTitle);
            globalChallengeDescription = itemView.findViewById(R.id.globalChallengeDescription);
            globalChallengeApproximateTime = itemView.findViewById(R.id.globalChallengeApproximateTime);
            globalChallengeTimesCompleted = itemView.findViewById(R.id.globalChallengeTimesCompleted);
            warningButton = itemView.findViewById(R.id.warningButton);
            likeButton = itemView.findViewById(R.id.likeButton);
            likesAmount = itemView.findViewById(R.id.likesAmount);
        }
    }
}