package com.example.projectforjava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectforjava.R;
import com.example.projectforjava.adapters.friends.FriendsAdapter;
import com.example.projectforjava.customElements.CustomStatusBar;
import com.example.projectforjava.templates.friends.Friend;
import com.example.projectforjava.templates.friends.FriendsChallenge;
import com.example.projectforjava.utils.ImgUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditFriendChallengeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FriendsAdapter adapter;
    private TextView selectedFriendsTextView;
    private ArrayList<String> names = new ArrayList<>();

    private static final int PICK_IMAGE_REQUEST = 5;

    FriendsChallenge friendsChallenge;
    ArrayList<String> titles;
    String current_title;

    ImageView image;
    EditText etChallengeTitle;
    EditText etChallengeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomStatusBar.changeStatusBar(this);
        setContentView(R.layout.activity_edit_friend_challenge);

        selectedFriendsTextView = findViewById(R.id.selectedFriendsTextView);
        recyclerView = findViewById(R.id.chooseFriendsRecyclerView);

        Button editChallengeButton = findViewById(R.id.buttonEditChallengeFC);
        Button deleteChallengeButton = findViewById(R.id.buttonDeleteChallengeFC);
        Button addChallengeButton = findViewById(R.id.buttonAddNewChallengeFC);

        etChallengeTitle = findViewById(R.id.editTextTitleFC);
        etChallengeDescription = findViewById(R.id.editTextDescriptionFC);

        image = findViewById(R.id.friendChallengeImageInput);

        int position = getIntent().getIntExtra("position", -1);
        titles = getIntent().getStringArrayListExtra("Titles");

        for (int i = 0; i < titles.size(); i++) {
            titles.set(i, titles.get(i).toLowerCase());
        }

        if(position != -1) {
            friendsChallenge = getIntent().getParcelableExtra("FriendsChallenge");
            current_title = friendsChallenge.getTitle();
            etChallengeTitle.setText(current_title);
            etChallengeDescription.setText(friendsChallenge.getDescription());
            image.setImageDrawable(friendsChallenge.getImage());
            names.addAll(friendsChallenge.getReceiversNames());
            selectedFriendsTextView.setText("Получатели: " + String.join(", ", names));
        }
        else {
            editChallengeButton.setVisibility(View.GONE);
            deleteChallengeButton.setVisibility(View.GONE);
            addChallengeButton.setVisibility(View.VISIBLE);
        }

        List<Friend> friends = new ArrayList<>();
        // Заполните friendList вашими данными
        friends.add(new Friend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), this),
                "John Doe", 5, 3));
        friends.add(new Friend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), this),
                "Greatest Dio", 107, 10));
        friends.add(new Friend(ImgUtils.BitmapToDrawable(ImgUtils.getRoundedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.my_friend)), this),
                "Soup lover", 20, 40));

        adapter = new FriendsAdapter(friends, true, names);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FriendsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                updateItemSelection(position);
            }
        });

        editChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editChallenge(position);
            }
        });

        deleteChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChallenge(position);
            }
        });

        addChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChallenge();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private boolean approveNewChallengeInfo(){
        String title = etChallengeTitle.getText().toString().trim();
        String description = etChallengeDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка уникальности названия челленджа
        if ((titles.contains(title.toLowerCase()) && current_title == null) || (current_title != null && !title.equals(current_title) && titles.contains(title.toLowerCase()))) {
            Toast.makeText(this, "Challenge title must be unique", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Проверка на длину описания челленджа
        if (description.length() > 50) {
            int excessChars = description.length() - 50;
            Toast.makeText(this, "Description exceeds limit by " + excessChars + " characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (names.isEmpty()){
            Toast.makeText(this, "Choose some friends", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updateItemSelection(int position){
        Friend friend = adapter.getItem(position);
        View itemView = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView;

        if(!names.contains(friend.getName())){
            itemView.setBackgroundResource(R.drawable.friend_challeng_completed);
            names.add(friend.getName());
        }
        else {
            itemView.setBackgroundResource(R.drawable.background_rounded_rectangle_border_black);
            names.remove(friend.getName());
        }

        selectedFriendsTextView.setText("Получатели: " + String.join(", ", names));
    }

    private void addChallenge(){
        if(approveNewChallengeInfo()){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("Action", "Add");
            resultIntent.putExtra("challenge", new FriendsChallenge(image.getDrawable(), etChallengeTitle.getText().toString().trim(),
                    etChallengeDescription.getText().toString().trim(), names, "Димастер"));
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void editChallenge(int position){
        if(approveNewChallengeInfo()){
            Intent resultIntent = new Intent();
            resultIntent.putExtra("Action", "Update");
            resultIntent.putExtra("challenge", new FriendsChallenge(image.getDrawable(), etChallengeTitle.getText().toString().trim(),
                    etChallengeDescription.getText().toString().trim(), names, "Димастер"));
            resultIntent.putExtra("position", position);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void deleteChallenge(int position){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Action", "Delete");
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Bitmap imageBitmap;
            // Теперь у вас есть URI выбранного изображения. Вы можете использовать его для отображения или сохранения.
            try {
                imageBitmap = ImgUtils.getBitmapFromUri(this, imageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imageBitmap = ImgUtils.scaleSquareBitmap(imageBitmap, 128);
            imageBitmap = ImgUtils.getRoundedSquareBitmap(imageBitmap, 128, 20);
            image.setImageBitmap(imageBitmap);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}