package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //sidebar start
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private EditText etLastname, etFirstname, etPassword, etEmail, etUsername, etPassword2;
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

        etFirstname = (EditText) findViewById(R.id.regEtFirstName);
        etLastname = (EditText) findViewById(R.id.regEtLastName);
        etUsername = (EditText) findViewById(R.id.regEtUserName);
        etEmail = (EditText) findViewById(R.id.regEtEmail);
        etPassword = (EditText) findViewById(R.id.regEtPwd1);
        etPassword2 = (EditText) findViewById(R.id.regEtPwd2);
        btnSubmit = (Button) findViewById(R.id.btnRegister);


        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                check();
            }
        });
    }


    private void check() {

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

            String url = "https://fypandroiddmsd.000webhostapp.com/AdoRegisterAndroid.php?firstName=" + etFirstname.getText().toString() + "&lastName=" + etLastname.getText().toString() + "&email=" + etEmail.getText().toString() + "&username=" + etLastname.getText().toString() + "&password=" + etPassword.getText().toString() + "&username="+etUsername.getText().toString()+"";
//            Log.i("ttturl",url);
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
                        String username = resultObject.getString("userExists");
//                        Toast.makeText(getApplicationContext(),
//                                username,
//                                Toast.LENGTH_LONG).show();
                        if (username.equals("1")) {
                            Toast.makeText(getApplicationContext(),
                                    "Sorry user name has been taken",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(RegisterPage.this,
                                    "Account create successful please verify your email",
                                    Toast.LENGTH_LONG).show();
                                    Intent login = new Intent(RegisterPage.this,LoginPage.class);
                                    startActivity(login);

                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            });


        }
    }


    //sidebar start

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent mainActivityIntent = new Intent(RegisterPage.this, MainActivity.class);
            startActivity(mainActivityIntent);

        } else if (id == R.id.nav_Login) {

            Intent mainActivityIntent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(mainActivityIntent);
        } else if (id == R.id.nav_CreateAccount) {
            Intent mainActivityIntent = new Intent(RegisterPage.this, RegisterPage.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_contact) {

            Intent mainActivityIntent = new Intent(RegisterPage.this, ContactPage.class);
            startActivity(mainActivityIntent);

        } else if (id == R.id.nav_about) {

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }
        return false;
    }

    //sidebar end
}
