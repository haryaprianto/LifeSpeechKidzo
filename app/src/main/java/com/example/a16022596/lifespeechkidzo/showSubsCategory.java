package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class showSubsCategory extends AppCompatActivity {
    ListView lvSubsCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lvSubsCategory = (ListView)findViewById(R.id.listViewSubsCat);
        retrieveSubsCategory();
    }
    private void retrieveSubsCategory(){
        Intent IDRecieve = getIntent();
        int ID = IDRecieve.getIntExtra("id", 0);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://fypandroiddmsd.000webhostapp.com/getSubCategoriesByCategory.php?category_id=1";
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
                    SubsCategory(subsCategoryObjectJSON(new String(response)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void SubsCategory(ArrayList<String>Subs){
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Subs);
        lvSubsCategory.setAdapter(adapter);
    }


    public ArrayList<String> subsCategoryObjectJSON(String response){
        ArrayList<String>subsCategoryNameList = new ArrayList<String>();


        try{
            JSONArray jsonArray = new JSONArray(response);
            Log.i("info", String.valueOf(jsonArray));
            String subsCategory;
            for (int i= 0;i<jsonArray.length();i++){
                subsCategory = jsonArray.getJSONObject(i).getString("subcategory_name");
                subsCategoryNameList.add(subsCategory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return subsCategoryNameList;
    }
}

