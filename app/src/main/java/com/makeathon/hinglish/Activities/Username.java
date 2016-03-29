package com.makeathon.hinglish.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.makeathon.hinglish.R;
import com.makeathon.hinglish.Utilities.SharedPreferenceMethods;

public class Username extends Activity{

    Button bt_enterName, bt_save;
    EditText et_player_1, et_player_2;
    String s_player1, s_player2;

    MediaPlayer buttonPopSound;

    String soundEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundEnabled.equals("true"))
                    buttonPopSound.start();
                if (stringCheck()) {
                    SharedPreferenceMethods.setString(getApplicationContext(), SharedPreferenceMethods.PLAYER_1_NAME, s_player1);
                    SharedPreferenceMethods.setString(getApplicationContext(), SharedPreferenceMethods.PLAYER_2_NAME, s_player2);
                    Intent i = new Intent(Username.this, Reaction.class);
                    startActivity(i);
                }
            }
        });
    }

    void init() {
        setContentView(R.layout.activity_username);
        bt_enterName = (Button)findViewById(R.id.bt_username_entername);
        bt_save = (Button)findViewById(R.id.bt_username_save);
        et_player_1 = (EditText)findViewById(R.id.et_username_player_1);
        et_player_2 = (EditText)findViewById(R.id.et_username_player_2);

        Typeface Mont = Typeface.createFromAsset(getApplication().getAssets(), "Montserrat-Bold.otf");
        bt_enterName.setTypeface(Mont);
        bt_save.setTypeface(Mont);
        et_player_1.setTypeface(Mont);
        et_player_2.setTypeface(Mont);

        buttonPopSound = MediaPlayer.create(Username.this, R.raw.button_pop_sound);

        soundEnabled = SharedPreferenceMethods.getString(getApplicationContext(), SharedPreferenceMethods.SOUND);
    }

    boolean stringCheck() {
        s_player1 = et_player_1.getText().toString();
        s_player2 = et_player_2.getText().toString();

        if (s_player1.equals("")){
            return false;
        }
        else if (s_player2.equals("")) {
            return false;
        }
        else {
            return true;
        }
    }

}
