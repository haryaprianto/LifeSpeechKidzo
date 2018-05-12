package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
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

public class RegisterPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //sidebar start
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private EditText etLastname,etFirstname,etPassword,etEmail;
    private Button btnSubmit;
    //sidebar end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_register);

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

        etFirstname = (EditText)findViewById(R.id.regEtFirstName);
        etLastname = (EditText)findViewById(R.id.regEtLastName);
        etEmail = (EditText)findViewById(R.id.regEtEmail);
        etPassword = (EditText)findViewById(R.id.regEtPwd);
        btnSubmit = (Button) findViewById(R.id.btnRegister);







        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!etFirstname.getText().toString().equals("") &&!etLastname.getText().toString().equals("") && !etEmail.getText().toString().equals("") && !etPassword.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(),
                            "Please wait",
                            Toast.LENGTH_LONG).show();

                    String url = "https://lifespeechsample3.000webhostapp.com/doRegister.php?firstName="+etFirstname.getText().toString()+"&lastName="+etLastname.getText().toString()+"&email="+etEmail.getText().toString()+"&username="+etLastname.getText().toString()+"&password="+etPassword.getText().toString()+"";
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
                                //JSONObject Object = jsonObj.getJSONObject("success");
                                String success = jsonObj.getString("success");
                                if (success.equals("1")){
                                    Toast.makeText(getApplicationContext(),
                                            "Account create successful",
                                            Toast.LENGTH_LONG).show();

                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "Account create fail",
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
            Intent mainActivityIntent = new Intent(RegisterPage.this, MainActivity.class);
            startActivity(mainActivityIntent);

        }else if(id == R.id.nav_Login) {

            Intent mainActivityIntent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(mainActivityIntent);
        }

        else if (id == R.id.nav_CreateAccount){
            Intent mainActivityIntent = new Intent(RegisterPage.this, RegisterPage.class);
            startActivity(mainActivityIntent);


        }
        else if (id == R.id.nav_contact){

            Intent mainActivityIntent = new Intent(RegisterPage.this, ContactPage.class);
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
