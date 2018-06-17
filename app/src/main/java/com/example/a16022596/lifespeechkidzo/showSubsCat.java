package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class showSubsCat extends AppCompatActivity {
    ListView lvSubsCategory;


    ArrayList<String>subsCategoryNameList = new ArrayList<String>();
    ArrayList<Integer>subCategoryIdList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subs_cat);
        lvSubsCategory = findViewById(R.id.listViewSubs);

        lvSubsCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View lView, final int pos, long id) {
                Intent intent = new Intent(showSubsCat.this, LessonActivity.class);
                int subCategoryId = subCategoryIdList.get(pos);
                String strSubCategoryId = String.valueOf(subCategoryId);
                intent.putExtra("subCatId", strSubCategoryId);
                startActivity(intent);
            }
        });

        retrieveSubsCategory();
    }
    private void retrieveSubsCategory(){
        Intent IDRecieve = getIntent();
        String catId = IDRecieve.getStringExtra("catId");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://fypandroiddmsd.000webhostapp.com/getSubCategoriesByCategory.php?category_id=" + catId + "";
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

        try{
            JSONArray jsonArray = new JSONArray(response);

            String subsCategory;
            int subCategoryId;
            for (int i= 0;i<jsonArray.length();i++){
                subsCategory = jsonArray.getJSONObject(i).getString("subcategory_name");
                subCategoryId = jsonArray.getJSONObject(i).getInt("subcategory_id");
                subsCategoryNameList.add(subsCategory);
                subCategoryIdList.add(subCategoryId);
//                Log.i("info", String.valueOf(subsCategory));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return subsCategoryNameList;
    }
}


