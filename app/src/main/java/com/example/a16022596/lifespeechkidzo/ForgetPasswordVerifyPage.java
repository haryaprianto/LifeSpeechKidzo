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
    LinearLayout firstLayout,secondLayout;
    Button verifyBtn,changepwdBtn;
    EditText editTextCode,editTextChangpw1,editTextChangpw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_verify_page);
        firstLayout = (LinearLayout)findViewById(R.id.firstLinear);
        secondLayout = (LinearLayout)findViewById(R.id.secondLinear);
        secondLayout.setVisibility(View.INVISIBLE);

//        verifyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//                if (!editTextCode.getText().toString().equals("")) {
//
//                    Toast.makeText(getApplicationContext(),
//                            "Please wait",
//                            Toast.LENGTH_SHORT).show();
//
//                    String url = "https://fypandroiddmsd.000webhostapp.com/AdoLoginAndroid.php?token="+editTextCode.getText().toString()+"";
//                    Log.i("ttturl",url);
//
//                    AsyncHttpClient client = new AsyncHttpClient();
//                    client.get(url, new AsyncHttpResponseHandler() {
//
//                        @Override
//                        public void onFailure(Throwable arg0, String arg1) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Please connect to internet and try again",
//                                    Toast.LENGTH_LONG).show();
//
//
//                        }
//
//                        @Override
//                        public void onFinish() {
//
//                            super.onFinish();
//                        }
//
//                        @Override
//                        public void onStart() {
//
//                            super.onStart();
//                        }
//
//                        @Override
//                        public void onSuccess(String response) {
//                            try {
//                                JSONObject jsonObj = new JSONObject(response);
//                                JSONObject resultObject = jsonObj.getJSONObject("result");
//                                String exist = resultObject.getString("tokenExist");
//                                if (exist.equals("0")) {
//                                    Toast.makeText(getApplicationContext(),
//                                            "Wrong Code",
//                                            Toast.LENGTH_LONG).show();
//
//                                }else{
////                                    Toast.makeText(getApplicationContext(),
////                                            "Login in successful",
////                                            Toast.LENGTH_LONG).show();
//                                    firstLayout.setVisibility(View.INVISIBLE);
//                                    secondLayout.setVisibility(View.VISIBLE);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                                    if (!editTextChangpw1.getText().toString().equals("")) {
//
//                                        Toast.makeText(getApplicationContext(),
//                                                "Please wait",
//                                                Toast.LENGTH_SHORT).show();
//
//                                        String url = "https://fypandroiddmsd.000webhostapp.com/AdoLoginAndroid.php?newpassword="+editTextChangpw1.getText().toString()+"";
//                                        Log.i("ttturl",url);
//
//                                        AsyncHttpClient client = new AsyncHttpClient();
//                                        client.get(url, new AsyncHttpResponseHandler() {
//
//
//
//                                            @Override
//                                            public void onFinish() {
//
//                                                super.onFinish();
//                                            }
//
//                                            @Override
//                                            public void onStart() {
//
//                                                super.onStart();
//                                            }
//
//                                            @Override
//                                            public void onSuccess(String response) {
//                                                try {
//                                                    JSONObject jsonObj = new JSONObject(response);
//                                                    JSONObject resultObject = jsonObj.getJSONObject("result");
//                                                    String exist = resultObject.getString("newpassword");
//                                                    if (exist.equals("0")) {
//                                                        Toast.makeText(getApplicationContext(),
//                                                                "Password Not Changed",
//                                                                Toast.LENGTH_LONG).show();
//
//                                                    }else{
//                                                    Toast.makeText(getApplicationContext(),
//                                                            "Password change successful",
//                                                            Toast.LENGTH_LONG).show();
//
//
//
//
//
//
//
//
//                                                    }
//
//                                                } catch (JSONException e) {
//
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        });
//                                    } else {
//                                        Toast.makeText(getApplicationContext(),
//                                                "Please complete form!",
//                                                Toast.LENGTH_LONG).show();
//                                    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                                }
//
//                            } catch (JSONException e) {
//
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "Please complete form!",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//
//    }
//} private void check() {
//
//    final String firstpassword = editTextChangpw1.getText().toString().trim();
//    final String secondpassword = editTextChangpw2.getText().toString().trim();
//
//
//
//
//    if (firstpassword.isEmpty()) {
//        editTextChangpw1.setError("Password is required");
//        editTextChangpw1.requestFocus();
//        return;
//    }
//
//    if (firstpassword.length() < 8) {
//        editTextChangpw2.setError("Minimum length of password should be 8");
//        editTextChangpw2.requestFocus();
//        return;
//    }
//
//    if (secondpassword.isEmpty()) {
//        editTextChangpw2.setError("Password is required");
//        editTextChangpw2.requestFocus();
//        return;
//    }
//    if (!firstpassword.equals(secondpassword)) {
//        editTextChangpw1.setError("Password does not match");
//        editTextChangpw1.requestFocus();
//        return;
//    }
//    else {
//
//
//        Toast.makeText(getApplicationContext(),
//                "Please wait",
//                Toast.LENGTH_SHORT).show();
//
//        String url = "https://fypandroiddmsd.000webhostapp.com/AdoRegisterAndroid.php?firstName=" + etFirstname.getText().toString() + "&lastName=" + etLastname.getText().toString() + "&email=" + etEmail.getText().toString() + "&username=" + etLastname.getText().toString() + "&password=" + etPassword.getText().toString() + "&username="+etUsername.getText().toString()+"";
////            Log.i("ttturl",url);
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, new AsyncHttpResponseHandler() {
//
//            @Override
//            public void onFailure(Throwable arg0, String arg1) {
//                Toast.makeText(getApplicationContext(),
//                        "Please connect to internet and try again",
//                        Toast.LENGTH_LONG).show();
//
//
//            }
//
//            @Override
//            public void onFinish() {
//
//                super.onFinish();
//            }
//
//            @Override
//            public void onStart() {
//
//                super.onStart();
//            }
//
//            @Override
//            public void onSuccess(String response) {
//                try {
//                    JSONObject jsonObj = new JSONObject(response);
//                    JSONObject resultObject = jsonObj.getJSONObject("result");
//                    String username = resultObject.getString("userExists");
////                        Toast.makeText(getApplicationContext(),
////                                username,
////                                Toast.LENGTH_LONG).show();
//                    if (username.equals("1")) {
//                        Toast.makeText(getApplicationContext(),
//                                "Sorry user name has been taken",
//                                Toast.LENGTH_LONG).show();
//
//                    } else {
////                        Toast.makeText(RegisterPage.this,
////                                "Account create successful please verify your email",
////                                Toast.LENGTH_LONG).show();
////                        Intent login = new Intent(RegisterPage.this,LoginPage.class);
////                        startActivity(login);
//
//                    }
//
//                } catch (JSONException e) {
//
//                    e.printStackTrace();
//                }
//            }
//        });
//

    }
}
