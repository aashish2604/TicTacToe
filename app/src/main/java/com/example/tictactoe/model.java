package com.example.tictactoe;

public class model
{
    //model class for Fragment2
    String email,uid,username;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public model(String email, String uid, String username) {
        this.email = email;
        this.uid = uid;
        this.username = username;
    }

    public model() {
    }
}
