package com.example.tictactoe;

public class dataholder {
    String username;
    String email;
    String uid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public dataholder(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    public dataholder() {
    }
}

