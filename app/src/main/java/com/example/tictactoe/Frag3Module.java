package com.example.tictactoe;

public class Frag3Module
{
    String date,description,post_image,time,uid,username;
    //Model class for Fragment3

    public Frag3Module() {
    }

    public Frag3Module(String date, String description, String post_image, String time, String uid, String username) {
        this.date = date;
        this.description = description;
        this.post_image = post_image;
        this.time = time;
        this.uid = uid;
        this.username = username;
    }

    public  String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public  String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
