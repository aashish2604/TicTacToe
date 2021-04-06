package com.example.tictactoe;

public class ModelFrag1 {

    String button, recieverUid, recieverUsername, senderUid, senderUsername;

    //Model class of fragment 1

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getRecieverUid() {
        return recieverUid;
    }

    public void setRecieverUid(String recieverUid) {
        this.recieverUid = recieverUid;
    }

    public String getRecieverUsername() {
        return recieverUsername;
    }

    public void setRecieverUsername(String recieverUsername) {
        this.recieverUsername = recieverUsername;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public ModelFrag1(String button, String recieverUid, String recieverUsername, String senderUid, String senderUsername) {
        this.button = button;
        this.recieverUid = recieverUid;
        this.recieverUsername = recieverUsername;
        this.senderUid = senderUid;
        this.senderUsername = senderUsername;
    }

    public ModelFrag1() {
    }
}
