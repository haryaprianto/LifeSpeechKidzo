package com.example.a16022596.lifespeechkidzo;

public class MediumQuestion {
    private String question;
    private int questionId;
    private  int quizId;
    private String answer;
    private String image;

    public MediumQuestion(String question, int questionId, int quizId, String answer, String image) {
        this.question = question;
        this.questionId = questionId;
        this.quizId = quizId;
        this.answer = answer;
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
