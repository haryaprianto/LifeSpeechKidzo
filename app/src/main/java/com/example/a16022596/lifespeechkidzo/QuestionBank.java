package com.example.a16022596.lifespeechkidzo;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    List<Question> list = new ArrayList<>();

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

}
