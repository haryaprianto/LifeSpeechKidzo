package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SelectQuizDifficulty extends AppCompatActivity {

    ArrayList<String>difficultyLevelName = new ArrayList<String>();
    ArrayList<Integer>quizIdList = new ArrayList<Integer>();
    Spinner spnDifficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quiz_difficulty);
        spnDifficulty = findViewById(R.id.spinnerQuizDifficulty);
        retrieveDifficultyLevel();

        spnDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer quizId = quizIdList.get(position);
//                Log.i("QUIZID",String.valueOf(quizId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void retrieveDifficultyLevel(){
        Intent IDRecieve = getIntent();
        String subCatId2 = IDRecieve.getStringExtra("subCatId");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://fypandroiddmsd.000webhostapp.com/getSelectedDifficulty.php?subcategory_id=" + subCatId2 + "";
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
                    difficultyName(difficultyOptionObjectJSON(new String(response)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void difficultyName(List<String> diff){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,diff);
        spnDifficulty.setAdapter(adapter);
    }


    public ArrayList<String> difficultyOptionObjectJSON(String response){

        try{
            JSONArray jsonArray = new JSONArray(response);

            String diffcultyName;
            int quizId;
            for (int i= 0;i<jsonArray.length();i++){
                diffcultyName = jsonArray.getJSONObject(i).getString("quiz_name");
//                Log.i("Quiz Name ", diffcultyName);
                quizId = jsonArray.getJSONObject(i).getInt("quiz_id");
                difficultyLevelName.add(diffcultyName);
                quizIdList.add(quizId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return difficultyLevelName;
    }
}
