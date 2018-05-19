package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonChangepwrd = (Button)findViewById(R.id.changepwd);
        final EditText changeemail = (EditText)findViewById(R.id.editTextCPEmail);




        buttonChangepwrd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!changeemail.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(),
                            "Please wait",
                            Toast.LENGTH_SHORT).show();

                    String url = "https://fypandroiddmsd.000webhostapp.com/AdoForgetPassword.php?email="+changeemail.getText().toString()+"";
                    Log.i("ttturl",url);
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
                                String exist = resultObject.getString("userExists");
                                String verify = resultObject.getString("userVerify");
                                String emailsend = resultObject.getString("EmailSuccess");
//                                Toast.makeText(getApplicationContext(), exist, Toast.LENGTH_LONG).show();
//                                Toast.makeText(getApplicationContext(), emailverify, Toast.LENGTH_LONG).show();

                                if (exist.equals("0")){
                                    Toast.makeText(getApplicationContext(),
                                            "Account does exist",
                                            Toast.LENGTH_LONG).show();

                                }else if(verify.equals("0")){
                                    Toast.makeText(getApplicationContext(),
                                            "Please verify your account before changing your password",
                                            Toast.LENGTH_LONG).show();

                                }else{
                                    Toast.makeText(getApplicationContext(), "Please check your email to change your password", Toast.LENGTH_LONG).show();
                                    Intent home = new Intent(ForgetPasswordPage.this,ForgetPasswordVerifyPage.class);
                                    startActivity(home);
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
        });

    }
}
