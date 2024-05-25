package com.example.projectforjava.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectforjava.R;
import com.example.projectforjava.customElements.CustomStatusBar;
import com.example.projectforjava.templates.global.GlobalChallenge;

public class GlobalChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.changeStatusBar(this);
        setContentView(R.layout.activity_global_challenge);

        ImageView drawable = findViewById(R.id.globalChallengeViewImage);
        TextView title = findViewById(R.id.globalChallengeViewTitle);
        TextView description = findViewById(R.id.globalChallengeViewDescription);
        Button button = findViewById(R.id.buttonAcceptGlobalChallenge);

        GlobalChallenge globalChallenge = getIntent().getParcelableExtra("globalChallenge");
        if(globalChallenge != null){
            drawable.setImageDrawable(globalChallenge.getImage());
            title.setText(globalChallenge.getTitle());
            description.setText(globalChallenge.getDescription());
            if(globalChallenge.isAccepted()){
                button.setBackgroundResource(R.color.green);
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setBackgroundResource(R.color.green);
            }
        });
    }
}