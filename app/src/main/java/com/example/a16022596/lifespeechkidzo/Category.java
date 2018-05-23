package com.example.a16022596.lifespeechkidzo;

public class Category {
    private String name;
    private int id;
    private String linkImage;

    public Category(String name, int id, String linkImage) {
        this.name = name;
        this.id = id;
        this.linkImage = linkImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
