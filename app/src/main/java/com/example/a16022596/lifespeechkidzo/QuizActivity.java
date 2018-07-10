package com.example.a16022596.lifespeechkidzo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    List<Question> list = new ArrayList<>();

    private TextView tvScore;   // view for current total score
    private TextView tvQuestion;  //current question to answer
    private Button btnChoice1; // multiple choice 1 for mQuestionView
    private Button btnChoice2; // multiple choice 2 for mQuestionView
    private Button btnChoice3; // multiple choice 3 for mQuestionView
    private Button btnChoice4; // multiple choice 4 for mQuestionView
    private CountDownTimer cDtimer;
    private long remainingTime = 60000 * 30;
    private boolean timeRunning;
    private TextView tvTimer;

    private String correctAnswer;  // correct answer for question in mQuestionView
    private int marks = 0;  // current total score
    private int questionNumber = 0; // current question number
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        tvScore = (TextView)findViewById(R.id.textViewScore);
        tvQuestion = (TextView)findViewById(R.id.textViewQuestion);
        btnChoice1 = (Button)findViewById(R.id.buttonOption1);
        btnChoice2 = (Button)findViewById(R.id.buttonOption2);
        btnChoice3 = (Button)findViewById(R.id.buttonOption3);
        btnChoice4 = (Button)findViewById(R.id.buttonOption4);
        tvTimer = (TextView)findViewById(R.id.textViewTimer);
        retrieveQuestions();
        updateQuestion();
    }
    private void retrieveQuestions(){
        Intent IDRecieve = getIntent();
        int quizId = IDRecieve.getIntExtra("quizID",0);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://fypandroiddmsd.000webhostapp.com/getQuestionByQuizId.php?quiz_id=" + quizId + "";
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
                    Log.i("info", response);
                    questionObjectJSON(new String(response));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ArrayList<String> questionObjectJSON(String response){
        ArrayList<String>questionList = new ArrayList<String>();


        Log.i("info",response);
        try{
            JSONArray jsonArray = new JSONArray(response);

            String question;
            String option1;
            String option2;
            String option3;
            String option4;
            String imageLink;
            int quizId;
            int questionId;
            for (int i= 0;i<jsonArray.length();i++){
                question = jsonArray.getJSONObject(i).getString("question");
                option1 = jsonArray.getJSONObject(i).getString("question_answer");
                option2 = jsonArray.getJSONObject(i).getString("question_answer1");
                option3 = jsonArray.getJSONObject(i).getString("question_answer2");
                option4 = jsonArray.getJSONObject(i).getString("question_answer3");
                imageLink = jsonArray.getJSONObject(i).getString("question_image");
                quizId = jsonArray.getJSONObject(i).getInt("quiz_id");
                questionId = jsonArray.getJSONObject(i).getInt("question_id");
                list.add(new Question(question,new String[]{option1,option2,option3,option4},option1,imageLink));
                questionList.add(question);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return questionList;
    }

    public int getLength(){
        return list.size();
    }

    public String getQuestion(int i){
        return list.get(i).getQuestion();
    }

    public String getChoice(int index, int num) {
        return list.get(index).getChoice(num - 1);
    }

    public String getCorrectAnswer(int i){
        return list.get(i).getAnswer();
    }
    private void updateQuestion(){
        if (questionNumber < getLength()){
            tvQuestion.setText(getQuestion(questionNumber));
            btnChoice1.setText(getChoice(questionNumber,1));
            btnChoice2.setText(getChoice(questionNumber,2));
            btnChoice3.setText(getChoice(questionNumber,3));
            btnChoice4.setText(getChoice(questionNumber,4));
            correctAnswer = getCorrectAnswer(questionNumber);
            questionNumber++;
        }
        else{
            Toast.makeText(QuizActivity.this, "It was the last question!", Toast.LENGTH_SHORT).show();
            int correctAnswer = getLength() - marks;
            int wrongAnswer = marks;
        }
    }
    private void updateScore(int point){
        tvScore.setText("Total Marks: "+marks+"/"+ getLength());
    }

    public void updateTimer(){
        int minute = (int) remainingTime/60000;
        int second = (int)  remainingTime % 60000 /1000;
        String timeLeft;
        timeLeft = " Remaining Time: \n" ;
        timeLeft += " " + minute + " mins ";
        timeLeft += ": ";
        if (second<10) timeLeft +="0";
        timeLeft += second + " secs";
        tvTimer.setText(timeLeft);


        if (minute<5){
            tvTimer.setTextColor(Color.parseColor("#ff0000"));
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(600); //You can manage the time of the blink with this parameter
            anim.setStartOffset(200);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            tvTimer.startAnimation(anim);
        }

    }

    public void startTimer(){
        cDtimer = new CountDownTimer(remainingTime,1000) {
            @Override
            public void onTick(long l) {
                remainingTime = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
//                Intent intent = new Intent(getBaseContext(),Result.class);
                int correctAnswer = getLength() - marks;
                int wrongAnswer = marks;
//                intent.putExtra("correct",correctAnswer);
//                intent.putExtra("wrong", wrongAnswer);
//                startActivity(intent);
            }
        }.start();
        timeRunning = true;
    }
    public void onClick (View view) {
        //all logic for all answers buttons in one method
        Button answer = (Button)view;
        tvScore.setText(answer.getText().toString());
        // if the answer is correct, increase the score
        if (answer.getText().equals(correctAnswer)){
            marks = marks + 1;
            Toast.makeText(QuizActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(QuizActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
        // show current total score for the user
        updateScore(marks);
        // once user answer the question, we move on to the next one, if any
        updateQuestion();

    }

}
