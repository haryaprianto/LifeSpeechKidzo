package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class Acc_ProfilePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //sidebar start
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    //sidebar end

    private EditText etLastname, etFirstname, etPassword, etEmail, etUsername, etPassword2;
    private Button btnUpdate;
    TextView tvpw1,tvpw2,tvfn,tvln,tvemail,tvun;
    String id;
    String acctype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc__profile_page);

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

        etFirstname = (EditText) findViewById(R.id.udEtFirstName);
        etLastname = (EditText) findViewById(R.id.udEtLastName);
        etUsername = (EditText) findViewById(R.id.udEtUserName);
        etEmail = (EditText) findViewById(R.id.udEtEmail);
        etPassword = (EditText) findViewById(R.id.udEtPwd1);
        etPassword2 = (EditText) findViewById(R.id.udEtPwd2);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        tvpw1 = (TextView)findViewById(R.id.tvpw1);
        tvpw2 = (TextView)findViewById(R.id.tvpw2);
        tvfn = (TextView)findViewById(R.id.tvfn);
        tvln = (TextView)findViewById(R.id.tvln);
        tvemail = (TextView)findViewById(R.id.tvemail);
        tvun = (TextView)findViewById(R.id.tvun);


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
                        acctype = resultObject.getString("accounttype");


//                        Toast.makeText(getApplicationContext(),name+email+firstname+lastname , Toast.LENGTH_LONG).show();

                        if (acctype.equals("facebook")){
                            etFirstname.setVisibility(View.INVISIBLE);
                            etLastname.setVisibility(View.INVISIBLE);
                            etEmail.setVisibility(View.INVISIBLE);
                            etPassword.setVisibility(View.INVISIBLE);
                            etPassword2.setVisibility(View.INVISIBLE);
                            tvpw1.setVisibility(View.INVISIBLE);
                            tvpw2.setVisibility(View.INVISIBLE);
                            tvfn.setVisibility(View.INVISIBLE);
                            tvln.setVisibility(View.INVISIBLE);
                            tvemail.setVisibility(View.INVISIBLE);
                            final LinearLayout.LayoutParams layoutparams = (LinearLayout.LayoutParams)btnUpdate.getLayoutParams();
                            layoutparams.setMargins(0,-900,0,0);
                            btnUpdate.setLayoutParams(layoutparams);

                            final LinearLayout.LayoutParams layoutparams2 = (LinearLayout.LayoutParams)tvun.getLayoutParams();
                            layoutparams2.setMargins(0,-500,0,0);
                            tvun.setLayoutParams(layoutparams2);

                            final LinearLayout.LayoutParams layoutparams3 = (LinearLayout.LayoutParams)etUsername.getLayoutParams();
                            layoutparams3.setMargins(0,0,0,0);
                            etUsername.setLayoutParams(layoutparams3);

                        }

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
                checkUpdate(acctype);
            }
        });




    }

    private void checkUpdate(String isfb) {

        final String firstname = etFirstname.getText().toString().trim();
        final String lastname = etLastname.getText().toString().trim();
        final String username = etUsername.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String firstpassword = etPassword.getText().toString().trim();
        final String secondpassword = etPassword2.getText().toString().trim();


        if (isfb.equals("facebook")) {















        }else{
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
            } else {


                Toast.makeText(getApplicationContext(),
                        "Please wait",
                        Toast.LENGTH_SHORT).show();

                String url = "https://fypandroiddmsd.000webhostapp.com/AdoUpdateProfileUpdate.php?username=" + username + "&firstname=" + firstname + "&lastname=" + lastname + "&password=" + firstpassword + "&email=" + etEmail.getText().toString() + "&id=" + id + "";
                Log.i("ttturl", url + "");
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
                            Log.i("tttrp", response);
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
            Intent mainActivityIntent = new Intent(Acc_ProfilePage.this, MainActivity.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_Login) {

            Intent mainActivityIntent = new Intent(Acc_ProfilePage.this, LoginPage.class);
            startActivity(mainActivityIntent);
        } else if (id == R.id.nav_CreateAccount) {
            Intent mainActivityIntent = new Intent(Acc_ProfilePage.this, RegisterPage.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_contact) {

            Intent mainActivityIntent = new Intent(Acc_ProfilePage.this, ContactPage.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_about) {

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }else if (id == R.id.nav_accHome) {
            Intent mainActivityIntent = new Intent(Acc_ProfilePage.this, MainActivity.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accProfile) {
//            Toast.makeText(Acc_ProfilePage.this,"Profile",Toast.LENGTH_LONG).show();
            Intent mainActivityIntent = new Intent(Acc_ProfilePage.this, Acc_ProfilePage.class);
            startActivity(mainActivityIntent);
        } else if (id == R.id.nav_navGameHistory) {
            Toast.makeText(Acc_ProfilePage.this,"Game History",Toast.LENGTH_LONG).show();
//            Intent mainActivityIntent = new Intent(Acc_HomePage.this, RegisterPage.class);
//            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accLogout) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Acc_ProfilePage.this);
            boolean Islogin;
            prefs.edit().putBoolean("Islogin", false).commit();
            prefs.edit().putString("LoginUsername", "").commit();
            prefs.edit().putString("AccountType", "").commit();
            LoginManager.getInstance().logOut();

            //need public static void to use function from another activity
            navigationdrawer();

            Intent mainActivityIntent = new Intent(Acc_ProfilePage.this, MainActivity.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accContact) {

            Intent mainActivityIntent = new Intent(Acc_ProfilePage.this, ContactPage.class);
            startActivity(mainActivityIntent);

        } else if (id == R.id.nav_accAbout) {

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }
        return false;
    }

    public void navigationdrawer(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Acc_ProfilePage.this);
        boolean islogin = prefs.getBoolean("Islogin",false);
        String isfb = prefs.getString("AccountType","");
        Log.i("tttpref",islogin+":"+isfb);
        if(islogin == true){
            if(isfb.equals("normal")){
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.navigation_loginmenu);
            }else{
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.navigation_facebookloginmenu);
            }

        }else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_menu);
        }


    }

    //sidebar end
}
