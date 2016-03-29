package com.makeathon.hinglish.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makeathon.hinglish.R;
import com.makeathon.hinglish.Utilities.SharedPreferenceMethods;

import org.w3c.dom.Text;

public class SinglePlayer_Game extends AppCompatActivity {

    Button bt_test_yes, bt_test_no, bt_test_nextQuestion;
    TextView tv_test_question;

    int questionCount;
    int correctAnswers;
    String temp;

    String currentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        bt_test_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("yes");
            }
        });

        bt_test_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("no");
            }
        });



        bt_test_nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuestion();
            }
        });

    }

    void checkAnswer(String userAnswer) {

        if (userAnswer.equals(currentAnswer)){
            bt_test_nextQuestion.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            Toast.makeText(SinglePlayer_Game.this, "Correct Answer!", Toast.LENGTH_SHORT).show();
            correctAnswers++;
        }
        else {
            Toast.makeText(SinglePlayer_Game.this, "Oops! Wrong Answer", Toast.LENGTH_SHORT).show();
            bt_test_nextQuestion.setBackgroundColor(getResources().getColor(R.color.colorRed));
        }

        bt_test_nextQuestion.setVisibility(View.VISIBLE);
    }

    void setQuestion() {

        bt_test_nextQuestion.setVisibility(View.INVISIBLE);
        questionCount++;

        if (questionCount == 1) {
            temp = "Ram studies in an University. Correct statement?";
            currentAnswer = "no";
        }
        else if (questionCount == 2) {
            temp = "Antonym for difficult is easy";
            currentAnswer = "yes";
        }
        else if (questionCount == 3) {
            temp = "Entreprenurship - Spelling correct? ";
            currentAnswer = "no";
        }
        else if (questionCount == 4) {
            temp = "Meaning of ominous is threatening. Correct?";
            currentAnswer = "yes";
        }
        else if (questionCount == 5) {
            temp = "Cantankerous is a synonym of crotchety. Correct?";
            currentAnswer = "yes";
        }
        else if (questionCount == 6) {

            String badge;

            if (correctAnswers == 5){
                badge = "Professional";
            }
            else if (correctAnswers == 3 || correctAnswers == 4 ){
                badge = "Mediocre";
            }
            else {
                badge = "Beginner";
            }
            temp = "Congratulations! Your score is " + correctAnswers + ". You get a " + badge + " badge.";

            displayResult(badge);

        }
        tv_test_question.setText(temp);
    }

    void displayResult(final String badge) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SinglePlayer_Game.this);
        alertDialog.setTitle("Your Score");
        alertDialog.setMessage("Congratulations! Your score is " + correctAnswers + ". You get a " + badge + " badge.");
        alertDialog.setPositiveButton("Play On!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Saving badge!!!
                SharedPreferenceMethods.setString(getApplicationContext(), SharedPreferenceMethods.SINGLE_PLAYER_BADGE, badge);

                Intent i = new Intent(SinglePlayer_Game.this, PlayOn.class);
                startActivity(i);

            }
        });
        alertDialog.show();

    }

    void init() {
        setContentView(R.layout.singleplayer_game);
        bt_test_yes = (Button)findViewById(R.id.bt_singleGame_yes);
        bt_test_no = (Button)findViewById(R.id.bt_singleGame_no);
        bt_test_nextQuestion = (Button)findViewById(R.id.bt_single_nextQuestion);
        tv_test_question = (TextView)findViewById(R.id.tv_singlePlayer_question);
        questionCount = 0;
        correctAnswers = 0;

        setQuestion();
    }


}
