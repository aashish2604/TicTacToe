package com.example.tictactoe;

public class BlogDataHolder {
    String uid;
    String date;
    String time;
    String description;
    String post_image;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public BlogDataHolder(String uid, String date, String time, String description, String post_image) {
        this.uid = uid;
        this.date = date;
        this.time = time;
        this.description = description;
        this.post_image = post_image;
    }

    public BlogDataHolder() {
    }
}
