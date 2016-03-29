package com.makeathon.hinglish.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.makeathon.hinglish.R;
import com.makeathon.hinglish.Utilities.SharedPreferenceMethods;

import java.util.Random;

public class Reaction extends Activity{

    Button player1_Name, player2_Name, player1_yes, player1_no, player2_yes, player2_no, player1_Ques, player2_Ques;
    Boolean beg_1, beg_2;
    String question, s_player1_naaam, s_player2_naaam;
    Boolean answer, questionActive;
    int player1_score, player2_score;
    int questionCount;
    Button nextQuestion, restartGame;
    MediaPlayer correctSound, wrongSound, nextQuestionSound;

    String soundEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        beg_1 = true;
        beg_2 = true;
        questionActive = true;
        questionCount = 0;
        player1_score = 0;
        player2_score = 0;

        player1_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (beg_1){  //player 1 is tapping to get ready!
                    player1_Ques.setText("READY!");
                    beg_1 = false;
                    checkStart();
                }
                else {       // player is answering a question
                    checkAnswer(true, 1);
                }
            }
        });

        player2_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (beg_2){  //player 2 is tapping to get ready!
                    player2_Ques.setText("READY!");
                    beg_2 = false;
                    checkStart();
                }
                else {       // player is answering a question
                    checkAnswer(true, 2);
                }
            }
        });

        player1_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!beg_1 && !beg_2)
                {
                    checkAnswer(false, 1);
                }
            }
        });

        player2_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!beg_1 && !beg_2)
                {
                    checkAnswer(false, 2);
                }
            }
        });

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundEnabled.equals("true"))
                    nextQuestionSound.start();
                nextQuestion.setVisibility(View.INVISIBLE);
                player1_yes.setBackgroundColor(getResources().getColor(R.color.colorgrey));
                player1_no.setBackgroundColor(getResources().getColor(R.color.colorgrey));
                player2_yes.setBackgroundColor(getResources().getColor(R.color.colorgrey));
                player2_no.setBackgroundColor(getResources().getColor(R.color.colorgrey));
                setRandomQuestion();
            }
        });

        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestionSound.start();
                Intent i = new Intent(Reaction.this, Username.class);
                startActivity(i);
            }
        });


    }

    void checkStart() {
        if (!beg_1 && !beg_2) {   // both players have pressed start!
            beginGame();
        }
    }

    void beginGame() {
        setRandomQuestion();
    }

    void checkAnswer(boolean userAns, int player) {

        if (userAns == answer && player == 1 && questionActive) {
            player1_yes.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            player1_no.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            questionActive = false;
            player1_score++;
            refreshScore();
            if (soundEnabled.equals("true"))
                correctSound.start();
            if (questionCount < 10)
                nextQuestion.setVisibility(View.VISIBLE);
            else
                scorePopup();
        }
        else if (userAns == answer && player == 2 && questionActive) {
            player2_yes.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            player2_no.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            questionActive = false;
            player2_score++;
            if (soundEnabled.equals("true"))
                correctSound.start();
            refreshScore();
            if (questionCount < 10)
                nextQuestion.setVisibility(View.VISIBLE);
            else
                scorePopup();
        }
        else if (userAns != answer && player == 1 && questionActive) {
            player1_yes.setBackgroundColor(getResources().getColor(R.color.colorRed));
            player1_no.setBackgroundColor(getResources().getColor(R.color.colorRed));
            questionActive = false;
            player2_score++;
            if (soundEnabled.equals("true"))
                wrongSound.start();
            refreshScore();
            if (questionCount < 10)
                nextQuestion.setVisibility(View.VISIBLE);
            else
                scorePopup();
        }
        else if (userAns != answer && player == 2 && questionActive) {
            player2_yes.setBackgroundColor(getResources().getColor(R.color.colorRed));
            player2_no.setBackgroundColor(getResources().getColor(R.color.colorRed));
            questionActive = false;
            player1_score++;
            if (soundEnabled.equals("true"))
                wrongSound.start();
            refreshScore();
            if (questionCount < 10)
                nextQuestion.setVisibility(View.VISIBLE);
            else
                scorePopup();
        }
    }

    void scorePopup() {
        restartGame.setVisibility(View.VISIBLE);
        if (player1_score > player2_score) {
            player1_Ques.setText("Congratulations " + s_player1_naaam + "! You Won!");
            player1_Ques.setTextColor(getResources().getColor(R.color.colorAccent));
            player2_Ques.setText("Better luck next time " + s_player2_naaam);
            player2_Ques.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (player1_score < player2_score) {
            player2_Ques.setText("Congratulations " + s_player2_naaam + "! You Won!");
            player2_Ques.setTextColor(getResources().getColor(R.color.colorAccent));
            player1_Ques.setText("Better luck next time " + s_player1_naaam);
            player1_Ques.setTextColor(getResources().getColor(R.color.colorRed));
        }
        else if (player1_score == player2_score) {
            player1_Ques.setText("Match Draw!");
            player2_Ques.setText("Match Draw!");
        }
    }

    void setRandomQuestion() {

        questionCount++;
        questionActive = true;

        Random r = new Random();
        int rand = r.nextInt(10 - 0) + 0;

        switch (rand) {
            case 0:
                question = "No. of vowels in english language is more than 6. True?";
                answer = false;
                break;
            case 1:
                question = "Acquit word's meaning is - to declare innocent. True?";
                answer = true;
                break;
            case 2:
                question = "Detour word's meaning is - diversion or deviation from path. True?";
                answer = true;
                break;
            case 3:
                question = "Exuberance refers to: sad. True?";
                answer = false;
                break;
            case 4:
                question = "Euphoria is a state of joy. True?";
                answer = true;
                break;
            case 5:
                question = "Articulate meaning: clear, effective. True?";
                answer = true;
                break;
            case 6:
                question = "Meaning of the word MAVERICK is powerful. True?";
                answer = false;
                break;
            case 7:
                question = "Obscure and hazy are synonym. True?";
                answer = true;
                break;
            case 8:
                question = "Corpulent synonym is thin. True?";
                answer = false;
                break;
            case 9:
                question = "Chubby, plump and tubby have same meaning. True?";
                answer = true;
                break;
            case 10:
                question = "Astonished is not a synonym of surprised. True?";
                answer = false;
                break;
        }

        player1_Ques.setText(questionCount + ". " + question);
        player2_Ques.setText(questionCount + ". " +question);
    }

    void refreshScore() {
        player1_Name.setText(s_player1_naaam + " : " + player1_score);
        player2_Name.setText(s_player2_naaam + " : " + player2_score);
    }

    void init() {
        setContentView(R.layout.activity_reaction);
        player1_Name = (Button)findViewById(R.id.bt_reactions_player1_name);
        player2_Name = (Button)findViewById(R.id.bt_reactions_player2_name);
        player1_yes = (Button)findViewById(R.id.bt_reactions_player1_yes);
        player2_yes = (Button)findViewById(R.id.bt_reactions_player2_yes);
        player1_no = (Button)findViewById(R.id.bt_reactions_player1_no);
        player2_no = (Button)findViewById(R.id.bt_reactions_player2_no);
        player1_Ques = (Button)findViewById(R.id.bt_reaction_player1_ques);
        player2_Ques = (Button)findViewById(R.id.bt_reaction_player2_ques);
        nextQuestion = (Button)findViewById(R.id.bt_reaction_nextQuestion);
        restartGame = (Button)findViewById(R.id.bt_reaction_restart);

        Typeface Mont = Typeface.createFromAsset(getApplication().getAssets(), "Montserrat-Bold.otf");
        player1_Name.setTypeface(Mont);
        player2_Name.setTypeface(Mont);
        player1_yes.setTypeface(Mont);
        player2_yes.setTypeface(Mont);
        player1_no.setTypeface(Mont);
        player2_no.setTypeface(Mont);
        player1_Ques.setTypeface(Mont);
        player2_Ques.setTypeface(Mont);
        nextQuestion.setTypeface(Mont);
        restartGame.setTypeface(Mont);

        s_player1_naaam = SharedPreferenceMethods.getString(getApplicationContext(), SharedPreferenceMethods.PLAYER_1_NAME);
        s_player2_naaam = SharedPreferenceMethods.getString(getApplicationContext(), SharedPreferenceMethods.PLAYER_2_NAME);
        player1_Name.setText(s_player1_naaam + " : 0");
        player2_Name.setText(s_player2_naaam + " : 0");

        soundEnabled = SharedPreferenceMethods.getString(getApplicationContext(), SharedPreferenceMethods.SOUND);

        correctSound = MediaPlayer.create(Reaction.this, R.raw.button_correct_sound);
        wrongSound = MediaPlayer.create(Reaction.this, R.raw.button_wrong_sound);
        nextQuestionSound = MediaPlayer.create(Reaction.this, R.raw.button_pop_sound);
    }



}
