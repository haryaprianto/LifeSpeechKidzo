package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //sidebar start
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private EditText etUsername,etPassword;
    private Button btnLogin;
    private LoginButton login_button;
    private CallbackManager callbackManager;
    String first_name,last_name,email,id;

    //sidebar end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);





        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.a16022596.lifespeechkidzo",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("tttKeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }









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

        Log.i("","");

        etUsername = (EditText)findViewById(R.id.editTextLogInEmail);
        etPassword = (EditText)findViewById(R.id.editTextLoginPassword);
        //non-fb
        btnLogin = (Button)findViewById(R.id.logLogin);

        //fb login button
        login_button = (LoginButton)findViewById(R.id.login_button);
        login_button.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userid = loginResult.getAccessToken().getUserId();
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("tttfbobject",object+"");
                        Log.i("tttfresponse",response+"");
                        displayUserInfo(object);

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields","first_name,last_name,email,id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
                Intent intent = new Intent(LoginPage.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });






        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        Log.i("tttlogin",isLoggedIn+"");
        if(isLoggedIn == true) {
            Toast.makeText(getApplicationContext(),
                    "Login in successful",
                    Toast.LENGTH_LONG).show();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.this);
            prefs.edit().putBoolean("Islogin", true).commit();
            prefs.edit().putString("LoginUsername", etUsername.getText().toString()).commit();
            prefs.edit().putString("AccountType", "facebook").commit();
            Intent home = new Intent(LoginPage.this, MainActivity.class);
            startActivity(home);
        }






        TextView textViewForgetPassword = (TextView)findViewById(R.id.textViewForgetPassword);
        textViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(LoginPage.this,ForgetPasswordPage.class);
                startActivity(login);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!etUsername.getText().toString().equals("") &&!etPassword.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(),
                            "Login...",
                            Toast.LENGTH_SHORT).show();

                    String url = "https://fypandroiddmsd.000webhostapp.com/AdoLoginAndroid.php?username="+etUsername.getText().toString()+"&password="+etPassword.getText().toString()+"";
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
                            Log.i("tttrespond",response);
                            try {


                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject resultObject = jsonObj.getJSONObject("result");
                                String exist = resultObject.getString("userExist");
                                String emailverify = resultObject.getString("emailVerify");
                                String passwordExist = resultObject.getString("passwordExist");

                                Log.i("tttexist",response+resultObject+"\n"+exist+"\n"+emailverify);

//                              Toast.makeText(getApplicationContext(), exist, Toast.LENGTH_LONG).show();
//                              Toast.makeText(getApplicationContext(), emailverify, Toast.LENGTH_LONG).show();

                                if (exist.equals("0")){
                                    Toast.makeText(getApplicationContext(),
                                            "Account does not exist",
                                            Toast.LENGTH_LONG).show();

                                }else if(emailverify.equals("0")){
                                        Toast.makeText(getApplicationContext(),
                                                "Please verify your email",Toast.LENGTH_LONG).show();


                                }else if(passwordExist.equals("0")) {
                                    Toast.makeText(getApplicationContext(),
                                            "Invalid Password",Toast.LENGTH_LONG).show();
                                }else {
                                        Toast.makeText(getApplicationContext(),
                                                "Login in successful",
                                                Toast.LENGTH_LONG).show();

                                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.this);
                                                prefs.edit().putBoolean("Islogin", true).commit();
                                                prefs.edit().putString("LoginUsername",etUsername.getText().toString()).commit();
                                                prefs.edit().putString("AccountType", "normal").commit();
                                                Intent home = new Intent(LoginPage.this,MainActivity.class);
                                                startActivity(home);
                                    }

                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the form",
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


    public void  displayUserInfo(JSONObject object){

        first_name = "";
        last_name = "";
        email = "";
        id = "";
        Log.i("tttfbobject2",object+"");
        try {
            first_name = object.getString("first_name");
            last_name =  object.getString("last_name");
            email =  object.getString("email");
            id =  object.getString("id");




            Toast.makeText(getApplicationContext(),
                    "Please wait",
                    Toast.LENGTH_SHORT).show();

            String url = "https://fypandroiddmsd.000webhostapp.com/AdoCheckFacebookWithEmail.php?email="+email+"";
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
                        Log.i("tttrp",response);
                        JSONObject jsonObj = new JSONObject(response);
                        JSONObject resultObject = jsonObj.getJSONObject("result");
                        String exist = resultObject.getString("exist");
                        String username = resultObject.getString("userName");


                        if (exist.equals("1")) {


                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginPage.this);
                            prefs.edit().putBoolean("Islogin", true).commit();
                            prefs.edit().putString("LoginUsername",username).commit();
                            prefs.edit().putString("AccountType", "facebook").commit();

                            Intent i = new Intent(LoginPage.this,MainActivity.class);
                            startActivity(i);


                        }else if (exist.equals("0")){
                            Intent i  = new Intent(LoginPage.this,FacebookUsername.class);
                            i.putExtra("fbfirstname",first_name);
                            i.putExtra("fblastname",last_name);
                            i.putExtra("fbemail",email);
                            startActivity(i);

                        }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("ttt",first_name+":"+last_name+":"+email+":"+id+"");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }
}
