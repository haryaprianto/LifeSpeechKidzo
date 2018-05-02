package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeLogo extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_logo);

        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivityIntent = new Intent(WelcomeLogo.this, MainActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

}
