package com.example.a16022596.lifespeechkidzo;

public class Question {
    private String question;
    private int questionId;
    private  int quizId;
    private String[] choice = new String[4];
    private String answer;
    private String image;

    public Question (){

    }
    public Question(String question, String[] choices, String answer,String image){
        this.question = question;
        this.choice[0] = choices[0];
        this.choice[1] = choices[1];
        this.choice[2] = choices[2];
        this.choice[3] = choices[3];
        this.answer = answer;
        this.image = image;
    }
    public String getQuestion(){
        return question;
    }

    public String getChoice(int i){
        return choice[i];
    }
    public  String getAnswer(){
        return answer;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }
    public void setChoice(int i, String choice){
        this.choice[i] = choice;
    }
    public void setQuestion(String question){
        this.question = question;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
