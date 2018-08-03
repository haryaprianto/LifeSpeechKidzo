package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    private  String tag = "Result";
    PieChart pcResult;

    int quiz_id;
    String username;
    int correct;
    int wrong;
    //Facebook
    private Button fbshare;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;


    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            if (ShareDialog.canShow(SharePhotoContent.class))
            {
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
//    //Facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        Intent intRecieved = getIntent();
        correct = intRecieved.getIntExtra("correct",0);
         wrong = intRecieved.getIntExtra("wrong",0);
        quiz_id = intRecieved.getIntExtra("quiz_id",0);


        Log.i("quiz_id",quiz_id+"");

        final double doublecorrect = correct+0.0;
        final double doublewrong = wrong+0.0;
        final double total = doublecorrect+doublewrong;
        final double average = total/2.0;

        Log.i(tag+"87",total+":"+average+":"+doublecorrect+":"+doublewrong);


        pcResult = (PieChart)findViewById(R.id.pieChart);
        pcResult.setUsePercentValues(true);
        pcResult.getDescription().setEnabled(false);
        pcResult.setExtraOffsets(5,10,5,5);



        pcResult.setDragDecelerationFrictionCoef(0.95f);
        pcResult.setDrawHoleEnabled(true);
        pcResult.setHoleColor(Color.WHITE);
        pcResult.setTransparentCircleRadius(61f);
        pcResult.setCenterText("Result");
        pcResult.setCenterTextSize(20f);
        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(correct,"Wrong"));
        yValues.add(new PieEntry(wrong,"Correct"));


        PieDataSet dataSet = new PieDataSet(yValues,"Result");
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(3f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(25f);
        data.setValueTextColor(Color.YELLOW);

        pcResult.setData(data);



        //Facebook
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        fbshare = (Button)findViewById(R.id.facebookShare);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Result.this);
        String type = prefs.getString("AccountType","unknown");
        username = prefs.getString("LoginUsername","");
        Log.i("user_name",username);
        Log.i(tag+"145",type);
        if(type.toString().trim().equals("normal")){
            fbshare.setVisibility(View.INVISIBLE);
        }else{
            fbshare.setVisibility(View.VISIBLE);
            fbshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Toast.makeText(Result.this,"Share successfully!",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(Result.this,"Share unsuccessfully!",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onError(FacebookException error) {
                            Toast.makeText(Result.this,error.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });

                    if(total==doublewrong) {
                        Picasso.get()
                                .load("https://fypdmsd.000webhostapp.com/ws/images/ShareExecellent.png")
                                .into(target);
                    }else if(doublewrong>average){
                        Picasso.get()
                                .load("https://fypdmsd.000webhostapp.com/ws/images/ShareWellDone.png")
                                .into(target);
                    }else{
                        Picasso.get()
                                .load("https://fypdmsd.000webhostapp.com/ws/images/ShareWorkHarder.png")
                                .into(target);
                    }


                }
            });

        }
//        Facebook

    }

    @Override
    protected void onResume() {
        super.onResume();


        // Code for step 1 start
        if(!username.equalsIgnoreCase("")){
            HttpRequest request = new HttpRequest
                    ("https://fypdmsd.000webhostapp.com/ws/doUploadScore.php");
            request.setOnHttpResponseListener(mHttpResponseListener);
            request.setMethod("POST");
            request.addData("username",username);
            request.addData("quiz_id", quiz_id+"");
            request.addData("marks", wrong+"");
            request.execute();
            // Code for step 1 end
        }
    }
    // Code for step 2 start
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response) {

                    // process response here
                    try {
                        Log.i("JSON Results: ", response);

                        JSONObject jsonObj = new JSONObject(response);
                        Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
}
