package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgetPasswordChangePasswordPage extends AppCompatActivity {
    private Button changepwdBtn;
    private EditText editTextChangpw1, editTextChangpw2;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_change_password_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changepwdBtn = (Button)findViewById(R.id.changepwdbtn);
        editTextChangpw1 = (EditText)findViewById(R.id.editTextChangpw1);
        editTextChangpw2 = (EditText)findViewById(R.id.editTextChangpw2);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.i("ttttoken",token);

        changepwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();

 

                }



        });
    }
    private void check() {

        final String firstpassword = editTextChangpw1.getText().toString().trim();
        final String secondpassword = editTextChangpw2.getText().toString().trim();


        if (firstpassword.isEmpty()) {
            editTextChangpw1.setError("Password is required");
            editTextChangpw1.requestFocus();
            return;
        }

        if (firstpassword.length() < 8) {
            editTextChangpw2.setError("Minimum length of password should be 8");
            editTextChangpw2.requestFocus();
            return;
        }

        if (secondpassword.isEmpty()) {
            editTextChangpw2.setError("Password is required");
            editTextChangpw2.requestFocus();
            return;
        }
        if (!firstpassword.equals(secondpassword)) {
            editTextChangpw1.setError("Password does not match");
            editTextChangpw1.requestFocus();
            return;
        }else{

            Toast.makeText(getApplicationContext(),
                    "Please wait",
                    Toast.LENGTH_SHORT).show();

            String url = "https://fypandroiddmsd.000webhostapp.com/AdoForgetPassword2.php?newpassword="+editTextChangpw1.getText().toString()+"&token="+token;
//                    Log.i("ttturl",url);
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
                        String passwordChange = resultObject.getString("passwordChange");
                        Toast.makeText(getApplicationContext(), passwordChange, Toast.LENGTH_LONG).show();
                        if (passwordChange.equals("0")){
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Password",
                                    Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Password Change Successful",
                                    Toast.LENGTH_LONG).show();
                            Intent home = new Intent(ForgetPasswordChangePasswordPage.this,LoginPage.class);
                            startActivity(home);
                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            });
        }


    }

}
