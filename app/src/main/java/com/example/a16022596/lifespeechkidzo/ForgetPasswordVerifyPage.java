package com.example.a16022596.lifespeechkidzo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForgetPasswordVerifyPage extends AppCompatActivity {
    LinearLayout firstLayout,secondLayout;
    TextView changepwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_verify_page);
        firstLayout = (LinearLayout)findViewById(R.id.firstLinear);
        secondLayout = (LinearLayout)findViewById(R.id.secondLinear);
        secondLayout.setVisibility(View.INVISIBLE);
    }
}
