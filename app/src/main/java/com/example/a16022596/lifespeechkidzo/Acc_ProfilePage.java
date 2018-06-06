package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class Acc_ProfilePage extends AppCompatActivity {
    private EditText etLastname, etFirstname, etPassword, etEmail, etUsername, etPassword2;
    private Button btnUpdate;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc__profile_page);

        etFirstname = (EditText) findViewById(R.id.udEtFirstName);
        etLastname = (EditText) findViewById(R.id.udEtLastName);
        etUsername = (EditText) findViewById(R.id.udEtUserName);
        etEmail = (EditText) findViewById(R.id.udEtEmail);
        etPassword = (EditText) findViewById(R.id.udEtPwd1);
        etPassword2 = (EditText) findViewById(R.id.udEtPwd2);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Acc_ProfilePage.this);
        boolean islogin = prefs.getBoolean("Islogin",false);
        final String username = prefs.getString("LoginUsername","a");
        Log.i("tttpref",islogin+"");
        Log.i("tttpref",username+"");
        if(islogin == true){
            Toast.makeText(getApplicationContext(),
                    "Please wait",
                    Toast.LENGTH_SHORT).show();

            String url = "https://fypandroiddmsd.000webhostapp.com/AdoUpdateProfile.php?username="+username+"";
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
                    Log.i("tttrespond",response);
                    try {


                        JSONObject jsonObj = new JSONObject(response);
                        JSONObject resultObject = jsonObj.getJSONObject("result");

                        id = resultObject.getString("id");
                        String name = resultObject.getString("name");
                        String email = resultObject.getString("email");
                        String firstname = resultObject.getString("firstname");
                        String lastname = resultObject.getString("lastname");


                        Toast.makeText(getApplicationContext(),name+email+firstname+lastname , Toast.LENGTH_LONG).show();

                        if (!name.equals("0")&&!email.equals("0")&&!firstname.equals("0")&&!lastname.equals("0")){
                            etFirstname.setText(firstname);
                            etLastname.setText(lastname);
                            etUsername.setText(username);
                            etEmail.setText(email);
                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            });
        }




        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUpdate();
            }
        });




    }

    private void checkUpdate() {

        final String firstname = etFirstname.getText().toString().trim();
        final String lastname = etLastname.getText().toString().trim();
        final String username = etUsername.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String firstpassword = etPassword.getText().toString().trim();
        final String secondpassword = etPassword2.getText().toString().trim();



        if (firstname.isEmpty()) {
            etFirstname.setError("First name is required");
            etFirstname.requestFocus();
            return;
        }

        if (!firstname.matches("^\\S*$")) {
            etFirstname.setError("Space is not allowed");
            etFirstname.requestFocus();
            return;
        }
        if (lastname.isEmpty()) {
            etLastname.setError("Last name is required");
            etLastname.requestFocus();
            return;
        }

        if (!lastname.matches("^\\S*$")) {
            etLastname.setError("Space is not allowed");
            etLastname.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            etUsername.requestFocus();
            return;
        }
        if (!username.matches("^\\S*$")) {
            etUsername.setError("Space is not allowed");
            etUsername.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }


        if (!firstpassword.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~()#;'?!@$%.^&*-]).{8,}$")) {
            etPassword.setError("Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.");
            etPassword.requestFocus();
            return;
        }
        if (!firstpassword.matches("^\\S*$")) {
            etPassword.setError("Space is not allowed");
            etPassword.requestFocus();
            return;
        }
        if (firstpassword.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (secondpassword.length() < 8) {
            etPassword2.setError("Minimum length of password should be 8");
            etPassword2.requestFocus();
            return;
        }

        if (secondpassword.isEmpty()) {
            etPassword2.setError("Password is required");
            etPassword2.requestFocus();
            return;
        }
        if (!firstpassword.equals(secondpassword)) {
            etPassword.setError("Password does not match");
            etPassword.requestFocus();
            etPassword2.setError("Password does not match");
            etPassword2.requestFocus();
            return;
        }
        else {


            Toast.makeText(getApplicationContext(),
                    "Please wait",
                    Toast.LENGTH_SHORT).show();

            String url = "https://fypandroiddmsd.000webhostapp.com/AdoUpdateProfileUpdate.php?username="+username+"&firstname="+firstname+"&lastname="+lastname+"&password="+firstpassword+"&email="+etEmail.getText().toString()+"&id="+id+"";
            Log.i("ttturl",url+"");
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(URLEncoder.encode(url), new AsyncHttpResponseHandler() {

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
                        Log.i("tttrp",response);
                        JSONObject jsonObj = new JSONObject(response);
                        JSONObject resultObject = jsonObj.getJSONObject("result");
                        String username = resultObject.getString("update");
                        if (username.equals("0")) {
                            Toast.makeText(getApplicationContext(),
                                    "Update Fail",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(Acc_ProfilePage.this,
                                    "Account Updated",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            });


        }
    }

}
