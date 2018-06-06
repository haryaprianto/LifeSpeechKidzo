package com.example.a16022596.lifespeechkidzo;

public class Lesson {
    private int contentId;
    private String contentImage;
    private String contentAudio;
    private String contentName;
    private int subsCatId;

    public Lesson(int contentId, String contentImage, String contentAudio, String contentName, int subsCatId) {
        this.contentId = contentId;
        this.contentImage = contentImage;
        this.contentAudio = contentAudio;
        this.contentName = contentName;
        this.subsCatId = subsCatId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getContentAudio() {
        return contentAudio;
    }

    public void setContentAudio(String contentAudio) {
        this.contentAudio = contentAudio;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public int getSubsCatId() {
        return subsCatId;
    }

    public void setSubsCatId(int setSubsCatId) {
        this.subsCatId = subsCatId;
    }
}
