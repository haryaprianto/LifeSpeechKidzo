package com.example.a16022596.lifespeechkidzo;

public class Question {
    private  int  questionId;
    private String question;
    private String questionAnswer;
    private  String questionAnswer1;
    private String questionAnswer2;
    private String questionAnswer3;
    private  String questionImage;
    private int quiz_id;

    public Question(int questionId, String question, String questionAnswer, String questionAnswer1, String questionAnswer2, String questionAnswer3, String questionImage, int quiz_id) {
        this.questionId = questionId;
        this.question = question;
        this.questionAnswer = questionAnswer;
        this.questionAnswer1 = questionAnswer1;
        this.questionAnswer2 = questionAnswer2;
        this.questionAnswer3 = questionAnswer3;
        this.questionImage = questionImage;
        this.quiz_id = quiz_id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionAnswer1() {
        return questionAnswer1;
    }

    public void setQuestionAnswer1(String questionAnswer1) {
        this.questionAnswer1 = questionAnswer1;
    }

    public String getQuestionAnswer2() {
        return questionAnswer2;
    }

    public void setQuestionAnswer2(String questionAnswer2) {
        this.questionAnswer2 = questionAnswer2;
    }

    public String getQuestionAnswer3() {
        return questionAnswer3;
    }

    public void setQuestionAnswer3(String questionAnswer3) {
        this.questionAnswer3 = questionAnswer3;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }
}
