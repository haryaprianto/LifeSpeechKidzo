package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    ArrayList<String>diffList = new ArrayList<String>();
    Spinner spnDifficulty;
    Button btnStartQuiz;
    Integer quizId;
    String difflevel;
    String subCatId2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quiz_difficulty);
        spnDifficulty = findViewById(R.id.spinnerQuizDifficulty);
        btnStartQuiz = findViewById(R.id.buttonStartQuiz);
        retrieveDifficultyLevel();


        spnDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quizId = quizIdList.get(position);
                difflevel = diffList.get(position);
                Log.i("QUIZID",String.valueOf(quizId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SelectedQuizId",String.valueOf(quizId));
                if (difflevel.equalsIgnoreCase("easy")){
                    Intent intent = new Intent(SelectQuizDifficulty.this, QuizActivity.class);
                    intent.putExtra("selectedQuizId", quizId);
                    startActivity(intent);
                }
                else if (difflevel.equalsIgnoreCase("medium")){
                    Intent intent = new Intent(SelectQuizDifficulty.this, MediumQuizPage.class);
                    intent.putExtra("selectedQuizId", quizId);
                    startActivity(intent);
                }
            }
        });


    }
    private void retrieveDifficultyLevel(){
        Intent IDRecieve = getIntent();
        subCatId2 = IDRecieve.getStringExtra("subCatId");
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
            String diff;
            for (int i= 0;i<jsonArray.length();i++){
                diffcultyName = jsonArray.getJSONObject(i).getString("quiz_name");
                Log.i("Quiz Name ", diffcultyName);
                quizId = jsonArray.getJSONObject(i).getInt("quiz_id");
                diff = jsonArray.getJSONObject(i).getString("difficulty_name");
                difficultyLevelName.add(diffcultyName);
                quizIdList.add(quizId);
                diffList.add(diff);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return difficultyLevelName;
    }
}
