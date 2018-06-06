package com.example.a16022596.lifespeechkidzo;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView tvScore;   // view for current total score
    private TextView tvQuestion;  //current question to answer
    private RadioGroup radioGroup;
    private RadioButton optionOne;
    private RadioButton optionTwo;
    private RadioButton optionThree;
    private RadioButton optionFour;
    private RadioButton rb;
    private Button btnNext;
    private Button btnPrevious;
    private CountDownTimer cDtimer;
    private long remainingTime = 60000 * 30;
    private boolean timeRunning;
    private int currentQuizQuestion;
    private int quizCount;
    private Question firstQuestion;
    private List<Question> parsedObject;
    TextView tvTimer;

    private String correctAnswer;  // correct answer for question in mQuestionView
    private int marks = 0;  // current total score
    private int questionNumber = 0; // current question number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvScore = (TextView)findViewById(R.id.textViewScore);
        tvQuestion = (TextView)findViewById(R.id.textViewQuestion);

        radioGroup = (RadioGroup)findViewById(R.id.rg);

        optionOne = (RadioButton)findViewById(R.id.radioOption1);

        optionTwo = (RadioButton)findViewById(R.id.radioOption2);

        optionThree = (RadioButton)findViewById(R.id.radioOption3);

        optionFour = (RadioButton)findViewById(R.id.radioOption4);
        btnNext = (Button)findViewById(R.id.buttonNext);
        btnPrevious = (Button)findViewById(R.id.buttonPrevious);
        tvTimer = (TextView)findViewById(R.id.textViewTimer);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioSelected = radioGroup.getCheckedRadioButtonId();
                rb = (RadioButton)findViewById(radioSelected);
                String selectedAnswer = rb.getText().toString();
                String correctAnswerForQuestion = firstQuestion.getQuestionAnswer();
                if(selectedAnswer == correctAnswerForQuestion){
                    // correct answer
                    Toast.makeText(QuizActivity.this, "You got the answer correct", Toast.LENGTH_LONG).show();
                    currentQuizQuestion++;
                    if(currentQuizQuestion >= quizCount){
                        Toast.makeText(QuizActivity.this, "End of the Quiz Questions", Toast.LENGTH_LONG).show();

                        return;
                    }

                    else{

                        firstQuestion = parsedObject.get(currentQuizQuestion);

                        tvQuestion.setText(firstQuestion.getQuestion());

                        String[] possibleAnswers = firstQuestion.getQuestionAnswer().split(",");
                        uncheckedRadioButton();
                        optionOne.setText(possibleAnswers[0]);
                        optionTwo.setText(possibleAnswers[1]);
                        optionThree.setText(possibleAnswers[2]);
                        optionFour.setText(possibleAnswers[3]);
                    }
                }

                else{

// failed question

                    Toast.makeText(QuizActivity.this, "You chose the wrong answer", Toast.LENGTH_LONG).show();

                    return;

                }

            }

        });



    }

    private int getSelectedAnswer(int radioSelected){

        int answerSelected = 0;

        if(radioSelected == R.id.radioOption1){

            answerSelected = 1;

        }

        if(radioSelected == R.id.radioOption2){

            answerSelected = 2;

        }

        if(radioSelected == R.id.radioOption3){

            answerSelected = 3;

        }

        if(radioSelected == R.id.radioOption4){

            answerSelected = 4;

        }

        return answerSelected;

    }
    private void uncheckedRadioButton(){

        optionOne.setChecked(false);

        optionTwo.setChecked(false);

        optionThree.setChecked(false);

        optionFour.setChecked(false);

    }
}
