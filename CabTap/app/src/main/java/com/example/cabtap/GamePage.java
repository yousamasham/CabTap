package com.example.cabtap;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

public class GamePage extends AppCompatActivity {

    String username;
    Random random = new Random();

    ImageButton btnRock, btnPaper, btnScissors, btnBack;
    ImageView comPlay, playerPlay;
    TextView comScoreText, playerScoreText;

    int comScore, playerScore, rewardPoints;

    enum Choice {ROCK, PAPER, SCISSORS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        btnRock = (ImageButton) findViewById(R.id.btn_rock);
        btnPaper = (ImageButton) findViewById(R.id.btn_paper);
        btnScissors = (ImageButton) findViewById(R.id.btn_scissors);
        btnScissors = (ImageButton) findViewById(R.id.btn_back);

        comPlay =(ImageView) findViewById(R.id.img_comPlay);
        playerPlay =(ImageView) findViewById(R.id.img_playerPlay);

        comScoreText = (TextView) findViewById(R.id.tv_compScore);
        playerScoreText = (TextView) findViewById(R.id.tv_playerScore);

        comScore = 0;
        playerScore = 0;

        username = getIntent().getStringExtra("username");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rewardPoints = playerScore*5;
                try {
                    updateRewards( username, rewardPoints);
                }catch (Exception E){}
                finish();
            }
        });

        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame(Choice.ROCK);
            }
        });

        btnPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame(Choice.PAPER);
            }
        });

        btnScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame(Choice.SCISSORS);
            }
        });

    }

    private void playGame(Choice playerMove) {

        int rngMove = random.nextInt(3);
        Choice compMove = Choice.values()[rngMove];

        switch(compMove){
            case ROCK:
                comPlay.setImageDrawable(getResources().getDrawable(R.drawable.fist, getApplicationContext().getTheme()));
                break;
            case PAPER:
                comPlay.setImageDrawable(getResources().getDrawable(R.drawable.hand, getApplicationContext().getTheme()));
                break;
            case SCISSORS:
                comPlay.setImageDrawable(getResources().getDrawable(R.drawable.scissor, getApplicationContext().getTheme()));
                break;
        }

        switch (playerMove) {
            case ROCK:
                playerPlay.setImageDrawable(getResources().getDrawable(R.drawable.fist, getApplicationContext().getTheme()));
                break;
            case PAPER:
                playerPlay.setImageDrawable(getResources().getDrawable(R.drawable.hand, getApplicationContext().getTheme()));
                break;
            case SCISSORS:
                playerPlay.setImageDrawable(getResources().getDrawable(R.drawable.scissor, getApplicationContext().getTheme()));
                break;
        }

        if (playerMove == compMove){
            comScore++;
            playerScore++;
        }
        else if (playerMove == Choice.ROCK && compMove == Choice.PAPER){
            comScore++;
        }
        else if (playerMove == Choice.ROCK && compMove == Choice.SCISSORS){
            playerScore++;
        }
        else if (playerMove == Choice.PAPER && compMove == Choice.ROCK){
            playerScore++;
        }
        else if (playerMove == Choice.PAPER && compMove == Choice.SCISSORS){
            comScore++;
        }
        else if (playerMove == Choice.SCISSORS && compMove == Choice.ROCK){
            comScore++;
        }
        else if (playerMove == Choice.SCISSORS && compMove == Choice.PAPER){
            playerScore++;
        }
        comScoreText.setText(String.valueOf(comScore));
        playerScoreText.setText(String.valueOf(playerScore));

        if(comScore + playerScore >= 5){
            rewardPoints = playerScore*5;
            try {
                updateRewards( username, rewardPoints);
            }catch (Exception E){}
            finish();
        }
    }

    public void updateRewards(String username, int amount) throws  Exception{
        ProfileField fieldToPass = ProfileField.REWARDSBAL;
        try{
            ProfileDatabase db = new ProfileDatabase();
            ArrayList<String> profile = db.RetrieveProfile(username);
            db.ModifyProfile(username, fieldToPass, profile.get(ProfileField.REWARDSBAL.ordinal()) +amount);
        }
        catch (Exception E){
            throw E;
        }
    }


}