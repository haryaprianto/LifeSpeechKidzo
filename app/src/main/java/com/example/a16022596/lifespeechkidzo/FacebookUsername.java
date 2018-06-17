package com.example.a16022596.lifespeechkidzo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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

public class FacebookUsername extends AppCompatActivity {
    Button fbbtn;
    EditText fbusername;
    String fbfirstname;
    String fblastname;
    String fbemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_username);
        Intent i = getIntent();
        fbfirstname = i.getStringExtra("fbfirstname");
        fblastname = i.getStringExtra("fblastname");
        fbemail = i.getStringExtra("fbemail");

        Log.i("FacbookUsername",fbfirstname+fblastname+fbemail);
        fbbtn = (Button)findViewById(R.id.fbBtn);
        fbusername = (EditText)findViewById(R.id.fbUsername);


        fbbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

    }




    private void check() {

        final String username = fbusername.getText().toString().trim();


        if (username.isEmpty()) {
            fbusername.setError("Username is required");
            fbusername.requestFocus();
            return;
        }
        if (!username.matches("^\\S*$")) {
            fbusername.setError("Space is not allowed");
            fbusername.requestFocus();
            return;
        }
        else {


            Toast.makeText(getApplicationContext(),
                    "Please wait",
                    Toast.LENGTH_SHORT).show();

            String url = "https://fypandroiddmsd.000webhostapp.com/AdoCreateFacebookAccount.php?firstName=" + fbfirstname + "&lastName=" + fblastname + "&email=" + fbemail + "&password=null&username="+fbusername.getText().toString()+"&user_accountype=facebook";


            Log.i("ttturl",url.replaceAll(" ", "%20"));
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url.replaceAll(" ", "%20"), new AsyncHttpResponseHandler() {

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
                        String username = resultObject.getString("userExists");
//                        Toast.makeText(getApplicationContext(),
//                                username,
//                                Toast.LENGTH_LONG).show();
                        if (username.equals("1")) {
                            Toast.makeText(getApplicationContext(),
                                    "Sorry user name has been taken",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Log.i("tttfbexist","0");
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FacebookUsername.this);
                            prefs.edit().putBoolean("Islogin", true).commit();
                            prefs.edit().putString("LoginUsername",fbusername.getText().toString()).commit();

                            Intent login = new Intent(FacebookUsername.this,MainActivity.class);
                            startActivity(login);

                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            });


        }
    }
}
