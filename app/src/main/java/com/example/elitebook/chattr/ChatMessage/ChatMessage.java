package com.example.elitebook.chattr.ChatMessage;

/**
 * Created by ErkHal on 02/10/2017.
 * A model class for chat messages that are constructed from the server's stream
 */

public class ChatMessage {

    private String sender;
    private String timeStamp;
    private String message;
    private boolean isOwnMessage;

    public ChatMessage(String sendr, String time, String msg, boolean isOwn) {

        this.sender = sendr;
        this.timeStamp = time;
        this.message = msg;
        this.isOwnMessage = isOwn;

    }

    public String getMessage() {

        return this.message;
    }

    public String getSender() {

        return this.sender;
    }

    public String getTimeStamp() {

        return this.timeStamp;
    }

    public boolean isOwnMessage() {

        return this.isOwnMessage;
    }

    @Override
    public String toString() {

        return this.message;
    }
}
