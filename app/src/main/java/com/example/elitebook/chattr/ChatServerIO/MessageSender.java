package com.example.elitebook.chattr.ChatServerIO;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.elitebook.chattr.MainActivity;

import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;

import static android.content.ContentValues.TAG;

/**
 * Created by ErkHal on 25/09/2017.
 * This class is responsible for sending messages to Chat server. Has a Blocking Queue array structure, which
 * is used to store messages to be sent to the server.
 */

public class MessageSender implements Runnable {

    private ArrayBlockingQueue<String> messageQueue;
    private EditText messageBox;
    private Button sendButton;
    private PrintStream output;
    private boolean messageWasSent;
    private MainActivity mainActivity;

    public MessageSender(PrintStream outputStream) {

        this.output = outputStream;
        this.messageWasSent = false;
        this.messageQueue = new ArrayBlockingQueue<>(10);

    }

    /**
     * Adds the message into the message queue
     * @param newMessage
     */
    public void addMessage(String newMessage) {

        this.messageQueue.add(newMessage);
    }

    /**
     * Takes the newest message from the queue and sends it to the server
     */
    public void run() {

        try {
            String newestMessage = this.messageQueue.take();
            sendMessage(newestMessage);

        } catch(InterruptedException i) {
            Log.d(TAG, "ERROR WHILE ACCESSING MESSAGE BLOCKING QUEUE");
            i.printStackTrace();
        }
    }

    /**
     * sends message down the stream for the server
     */
    private void sendMessage(String msg) {

        String message = msg;
        output.println(message);

    }

}
