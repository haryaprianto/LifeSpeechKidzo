package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordVerifyPage extends AppCompatActivity {




    private Button verifyBtn;
    private EditText editTextCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_verify_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editTextCode = (EditText)findViewById(R.id.editTextCode);

        verifyBtn = (Button)findViewById(R.id.verifybtn);





        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkcode();


            }
        });

    }


    private void checkcode(){
        if (!editTextCode.getText().toString().equals("")) {

            Toast.makeText(getApplicationContext(),
                    "Please wait",
                    Toast.LENGTH_SHORT).show();

            String url = "https://fypandroiddmsd.000webhostapp.com/AdoForgetPassword2.php?token=" + editTextCode.getText().toString() + "";
            Log.i("ttturltoken", url);

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {

                @Override
                public void onFailure(Throwable arg0, String arg1) {
                    Toast.makeText(getApplicationContext(),
                            "Please connect to internet and try again",
                            Toast.LENGTH_LONG).show();


                }

                @Override
                public void onFinish() {

                    super.onFinish();
                }

                @Override
                public void onStart() {

                    super.onStart();
                }

                @Override
                public void onSuccess(String response) {
                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        JSONObject resultObject = jsonObj.getJSONObject("result");
                        String exist = resultObject.getString("tokenExist");
                        if (exist.equals("0")) {
                            Toast.makeText(getApplicationContext(),
                                    "Wrong Code",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Verify successful",
                                    Toast.LENGTH_LONG).show();
                            Intent changepassword = new Intent(ForgetPasswordVerifyPage.this,ForgetPasswordChangePasswordPage.class);
                            changepassword.putExtra("token",editTextCode.getText().toString().trim());
                            startActivity(changepassword);
                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please complete form!",
                    Toast.LENGTH_LONG).show();
        }
    }




}
