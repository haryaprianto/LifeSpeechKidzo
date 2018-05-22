package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class showSubsCat extends AppCompatActivity {
    ListView lvSubsCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subs_cat);
        lvSubsCategory = findViewById(R.id.listViewSubs);
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

    public void SubsCategory(ArrayList<String> Subs){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Subs);
        lvSubsCategory.setAdapter(adapter);
    }


    public ArrayList<String> subsCategoryObjectJSON(String response){
        ArrayList<String>subsCategoryNameList = new ArrayList<String>();


        try{
            JSONArray jsonArray = new JSONArray(response);

            String subsCategory;
            for (int i= 0;i<jsonArray.length();i++){
                subsCategory = jsonArray.getJSONObject(i).getString("subcategory_name");
                subsCategoryNameList.add(subsCategory);

                Log.i("info", String.valueOf(subsCategory));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return subsCategoryNameList;
    }
}


