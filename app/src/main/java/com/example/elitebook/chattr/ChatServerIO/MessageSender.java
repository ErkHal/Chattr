package com.example.elitebook.chattr.ChatServerIO;

import android.util.Log;

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
    private PrintStream output;

    public MessageSender(PrintStream outputStream) {

        this.output = outputStream;
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

        while(true) {

            if (!Thread.interrupted()) {

                try {
                    String newestMessage = this.messageQueue.take();
                    sendMessage(newestMessage);

                } catch (InterruptedException i) {
                    Log.d(TAG, "Queue access error !");
                    i.printStackTrace();
                }
            } else {

                break;
            }
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
