package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MediumQuizPage extends AppCompatActivity {
    List<MediumQuestion> list = new ArrayList<>();
    Button btnNext;
    EditText etAnswer;
    TextView tvQuestion;
    TextView tvTimer;
    TextView tvScore;
    ImageView imgView;
    String imageLink;
    int ia = -1;
    private MediaPlayer mediaPlayer;

    //time
    private CountDownTimer cDtimer;
    private long remainingTime = 60000 * 30;
    private boolean timeRunning;

    private String correctAnswer;  // correct answer for question in mQuestionView
    int marks = 0;  // current total score
    private int questionNumber = 0; // current question number


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium_quiz_page);

        btnNext = (Button)findViewById(R.id.buttonNextQuestion);
        etAnswer = (EditText)findViewById(R.id.editTextAnswer);
        tvQuestion = (TextView)findViewById(R.id.textViewQuestion);
        tvTimer = (TextView)findViewById(R.id.textViewTimer);
        tvScore = (TextView)findViewById(R.id.textViewScore);
        imgView = (ImageView)findViewById(R.id.imageView);


        retrieveQuestions();
        updateQuestion();
        startTimer();



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
            String answer;
            int quizId2;
            int questionId;
            ia=1;
            for (int i= 0;i<jsonArray.length();i++){
                question = jsonArray.getJSONObject(i).getString("question");
                answer = jsonArray.getJSONObject(i).getString("question_answer");
                imageLink = "https://fypdmsd.000webhostapp.com/ws/images/"+jsonArray.getJSONObject(i).getString("question_image");
                quizId2 = jsonArray.getJSONObject(i).getInt("quiz_id");
                questionId = jsonArray.getJSONObject(i).getInt("question_id");
                list.add(new MediumQuestion(question,questionId,quizId2,answer,imageLink));
                questionList.add(question);

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        tvQuestion.setText(list.get(questionNumber).getQuestion());
        correctAnswer = list.get(questionNumber).getAnswer();
        if (!imageLink.isEmpty()){
            Log.d("tag", "questionObjectJSON: "+ list.get(questionNumber).getImage());
            Picasso.get().load(list.get(questionNumber).getImage()).fit().centerCrop().into(imgView);
        }


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equalsIgnoreCase(etAnswer.getText().toString())) {
                    questionNumber++;
                    updateQuestion();
                }
                else{
                    Toast.makeText(MediumQuizPage.this,"Your answer is wrong",Toast.LENGTH_LONG).show();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                    questionNumber++;
                    updateQuestion();


                }
            }
        });

        return questionList;

    }
    private void updateScore(int point) {
        tvScore.setText("Total Marks: "+marks+"/"+ list.size());
    }



    private void updateQuestion(){
        Log.i("info","question num "+questionNumber);
        Log.i("info","list size "+list.size());

            if (list.size() > questionNumber) {
//                marks++;
//                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.applause);
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.start();
                if (correctAnswer.equalsIgnoreCase(etAnswer.getText().toString().trim())){
                    marks=marks+1;
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.applause);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
                    updateScore(marks);
//                    Toast.makeText(MediumQuizPage.this, "It was the last question", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getBaseContext(),Result.class);
                    int correctAnswer = list.size() - marks;
                    int wrongAnswer = marks;
//                    intent.putExtra("correct",correctAnswer);
//                    intent.putExtra("wrong", wrongAnswer);
//                    startActivity(intent);
                    Log.i("mark",marks+"");
                }
                else{
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.start();
//                    Toast.makeText(MediumQuizPage.this, "It was the last question", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getBaseContext(),Result.class);
                    int correctAnswer = list.size() - marks;
                    int wrongAnswer = marks;
//                    intent.putExtra("correct",correctAnswer);
//                    intent.putExtra("wrong", wrongAnswer);
//                    startActivity(intent);
                }
                updateScore(marks);
                etAnswer.setText("");
                if (!imageLink.isEmpty()) {
                    Picasso.get().load(list.get(questionNumber).getImage()).fit().centerCrop().into(imgView);
                }
                Log.i("mark",marks+"");
                correctAnswer = list.get(questionNumber).getAnswer();
            } else {
                if(ia==-1){
                }else{
                    if (correctAnswer.equalsIgnoreCase(etAnswer.getText().toString().trim())){
                        marks=marks+1;
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.applause);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();
                        updateScore(marks);
                        Toast.makeText(MediumQuizPage.this, "It was the last question", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(),Result.class);
                        int correctAnswerMarks = list.size() - marks;
                        int wrongAnswermarks = marks;
                        intent.putExtra("correct",correctAnswerMarks);
                        intent.putExtra("wrong", wrongAnswermarks);
                        startActivity(intent);
//                        Log.i("mark",marks+"");
                    }
                    else{
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.start();
                        Toast.makeText(MediumQuizPage.this, "It was the last question", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(),Result.class);
                        int correctAnswer = list.size() - marks;
                        int wrongAnswer = marks;
                        intent.putExtra("correct",correctAnswer);
                        intent.putExtra("wrong", wrongAnswer);
                        startActivity(intent);
                    }

                }

            }

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
                Intent intent = new Intent(getBaseContext(),Result.class);
                int correctAnswer = list.size() - marks;
                int wrongAnswer = marks;
                intent.putExtra("correct",correctAnswer);
                intent.putExtra("wrong", wrongAnswer);
                startActivity(intent);
            }
        }.start();
        timeRunning = true;
    }

}
