package com.example.a16022596.lifespeechkidzo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    List<Question> list = new ArrayList<>();




    private TextView tvScore;   // view for current total score
    private TextView tvQuestion;  //current question to answer
    private Button btnChoice1; // multiple choice 1 for mQuestionView
    private Button btnChoice2; // multiple choice 2 for mQuestionView
    private Button btnChoice3; // multiple choice 3 for mQuestionView
    private Button btnChoice4; // multiple choice 4 for mQuestionView
    private ImageView img;
    private MediaPlayer mediaPlayer;

    private String imageLink;



    //time
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
        img = (ImageView)findViewById(R.id.imageView2);

        btnChoice1 = (Button)findViewById(R.id.buttonOption1);
        btnChoice2 = (Button)findViewById(R.id.buttonOption2);
        btnChoice3 = (Button)findViewById(R.id.buttonOption3);
        btnChoice4 = (Button)findViewById(R.id.buttonOption4);
        tvTimer = (TextView)findViewById(R.id.textViewTimer);
        retrieveQuestions();
        startTimer();
        updateScore(marks);


    }
    private void retrieveQuestions(){
        Intent IDRecieve = getIntent();
        int quizId = IDRecieve.getIntExtra("selectedQuizId",0);
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
        final ArrayList<String>questionList = new ArrayList<String>();


        Log.i("info",response);
        try{
            JSONArray jsonArray = new JSONArray(response);

            String question;
            String option1;
            String option2;
            String option3;
            String option4;
            int quizId2;
            int questionId;
            for (int i= 0;i<jsonArray.length();i++){
                question = jsonArray.getJSONObject(i).getString("question");
                option1 = jsonArray.getJSONObject(i).getString("question_answer");
                option2 = jsonArray.getJSONObject(i).getString("question_answer1");
                option3 = jsonArray.getJSONObject(i).getString("question_answer2");
                option4 = jsonArray.getJSONObject(i).getString("question_answer3");
                imageLink = "https://fypdmsd.000webhostapp.com/ws/images/"+jsonArray.getJSONObject(i).getString("question_image");
                quizId2 = jsonArray.getJSONObject(i).getInt("quiz_id");
                questionId = jsonArray.getJSONObject(i).getInt("question_id");
                list.add(new Question(question,new String[]{option1,option2,option3,option4},option1,imageLink));
                questionList.add(question);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

            // set the text for new question,
            // and new 4 alternative to answer on four buttons
            tvQuestion.setText(list.get(questionNumber).getQuestion());
            btnChoice1.setText(list.get(questionNumber).getChoice(0));
            btnChoice2.setText(list.get(questionNumber).getChoice(1));
            btnChoice3.setText(list.get(questionNumber).getChoice(2));
            btnChoice4.setText(list.get(questionNumber).getChoice(3));

            correctAnswer = list.get(questionNumber).getAnswer();

            Log.i("Correct Answer",correctAnswer);

            Log.i("QuestionNumber",String.valueOf(questionNumber));

        if (!imageLink.isEmpty()){
            Log.d("tag", "questionObjectJSON: "+ list.get(questionNumber).getImage());
            Picasso.get().load(list.get(questionNumber).getImage()).fit().centerCrop().into(img);
        }
        questionNumber++;



        btnChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (btnChoice1.getText().toString().equalsIgnoreCase(correctAnswer)) {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.applause);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();
                        marks++;
                        updateScore(marks);
                        tvQuestion.setText(list.get(questionNumber).getQuestion());
                        btnChoice1.setText(list.get(questionNumber).getChoice(0));
                        btnChoice2.setText(list.get(questionNumber).getChoice(1));
                        btnChoice3.setText(list.get(questionNumber).getChoice(2));
                        btnChoice4.setText(list.get(questionNumber).getChoice(3));
                        if (!imageLink.isEmpty()) {
                            Log.d("tag", "questionObjectJSON: " + list.get(questionNumber).getImage());
                            Picasso.get().load(list.get(questionNumber).getImage()).fit().centerCrop().into(img);
                        }
                        correctAnswer = list.get(questionNumber).getAnswer();

                    } else {
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();
                    }
            }
        });
        btnChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnChoice2.getText().toString().equalsIgnoreCase(correctAnswer)){
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.applause);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                    marks++;
                    updateScore(marks);
                    tvQuestion.setText(list.get(questionNumber).getQuestion());
                    btnChoice1.setText(list.get(questionNumber).getChoice(0));
                    btnChoice2.setText(list.get(questionNumber).getChoice(1));
                    btnChoice3.setText(list.get(questionNumber).getChoice(2));
                    btnChoice4.setText(list.get(questionNumber).getChoice(3));
                    if (!imageLink.isEmpty()){
                        Log.d("tag", "questionObjectJSON: "+ list.get(questionNumber).getImage());
                        Picasso.get().load(list.get(questionNumber).getImage()).fit().centerCrop().into(img);
                    }
                    correctAnswer = list.get(questionNumber).getAnswer();
                    if (questionNumber == (list.size() -1)){
                        Toast.makeText(QuizActivity.this,"This is the last question",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                }
            }
        });
        btnChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnChoice3.getText().toString().equalsIgnoreCase(correctAnswer)){
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.applause);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                    marks++;
                    updateScore(marks);
                    tvQuestion.setText(list.get(questionNumber).getQuestion());
                    btnChoice1.setText(list.get(questionNumber).getChoice(0));
                    btnChoice2.setText(list.get(questionNumber).getChoice(1));
                    btnChoice3.setText(list.get(questionNumber).getChoice(2));
                    btnChoice4.setText(list.get(questionNumber).getChoice(3));
                    if (!imageLink.isEmpty()){
                        Log.d("tag", "questionObjectJSON: "+ list.get(questionNumber).getImage());
                        Picasso.get().load(list.get(questionNumber).getImage()).fit().centerCrop().into(img);
                    }
                    correctAnswer = list.get(questionNumber).getAnswer();
                    if (questionNumber == (list.size() -1)){
                        Toast.makeText(QuizActivity.this,"This is the last question",Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                }
            }
        });
        btnChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnChoice4.getText().toString().equalsIgnoreCase(correctAnswer)){
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.applause);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                    marks++;
                    updateScore(marks);
                    tvQuestion.setText(list.get(questionNumber).getQuestion());
                    btnChoice1.setText(list.get(questionNumber).getChoice(0));
                    btnChoice2.setText(list.get(questionNumber).getChoice(1));
                    btnChoice3.setText(list.get(questionNumber).getChoice(2));
                    btnChoice4.setText(list.get(questionNumber).getChoice(3));
                    if (!imageLink.isEmpty()){
                        Log.d("tag", "questionObjectJSON: "+ list.get(questionNumber).getImage());
                        Picasso.get().load(list.get(questionNumber).getImage()).fit().centerCrop().into(img);
                    }
                    correctAnswer = list.get(questionNumber).getAnswer();
                    if (questionNumber == (list.size() -1)){
                        Toast.makeText(QuizActivity.this,"This is the last question",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                }
            }
        });


        return questionList;
    }

    private void updateScore(int point) {
        tvScore.setText("Total Marks: "+marks+"/"+ list.size());
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
//                int correctAnswer = list.size() - marks;
//                int wrongAnswer = marks;
//                intent.putExtra("correct",correctAnswer);
//                intent.putExtra("wrong", wrongAnswer);
//                startActivity(intent);
            }
        }.start();
        timeRunning = true;
    }
}
