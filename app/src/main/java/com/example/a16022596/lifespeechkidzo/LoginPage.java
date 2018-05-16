package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //sidebar start
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private EditText etUsername,etPassword;
    private Button btnLogin;
    //sidebar end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //sidebar start
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemTextColor(null);
        navigationView.setItemTextAppearance(R.style.MenuTextStyle);


        //sidebar end


        etUsername = (EditText)findViewById(R.id.editTextLogInEmail);
        etPassword = (EditText)findViewById(R.id.editTextLoginPassword);
        btnLogin = (Button)findViewById(R.id.logLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!etUsername.getText().toString().equals("") &&!etPassword.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(),
                            "Please wait",
                            Toast.LENGTH_SHORT).show();

                    String url = "https://lifespeechsample3.000webhostapp.com/doLogin.php?username="+etUsername.getText().toString()+"&password="+etPassword.getText().toString()+"";
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
                                String exist = resultObject.getString("userExist");
                                String emailverify = resultObject.getString("emailVerify");
                                Toast.makeText(getApplicationContext(),
                                        exist,
                                        Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),
                                        emailverify,
                                        Toast.LENGTH_LONG).show();
                                if (exist.equals("0")){
                                    Toast.makeText(getApplicationContext(),
                                            "Account does exist",
                                            Toast.LENGTH_LONG).show();

                                }else if(emailverify.equals("0")){
                                        Toast.makeText(getApplicationContext(),
                                                "Please verify your email",
                                                Toast.LENGTH_LONG).show();

                                }else{
                                        Toast.makeText(getApplicationContext(),
                                                "Login in successful",
                                                Toast.LENGTH_LONG).show();
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
    //sidebar start

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent mainActivityIntent = new Intent(LoginPage.this, MainActivity.class);
            startActivity(mainActivityIntent);

        }else if(id == R.id.nav_Login) {

            Intent mainActivityIntent = new Intent(LoginPage.this, LoginPage.class);
            startActivity(mainActivityIntent);
        }

        else if (id == R.id.nav_CreateAccount){
            Intent mainActivityIntent = new Intent(LoginPage.this, RegisterPage.class);
            startActivity(mainActivityIntent);


        }
        else if (id == R.id.nav_contact){

            Intent mainActivityIntent = new Intent(LoginPage.this, ContactPage.class);
            startActivity(mainActivityIntent);

        }else if(id == R.id.nav_about){

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }
        return false;
    }

    //sidebar end
}
