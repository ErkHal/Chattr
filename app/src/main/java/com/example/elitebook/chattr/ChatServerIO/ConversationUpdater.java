package com.example.elitebook.chattr.ChatServerIO;

import com.example.elitebook.chattr.MainActivity;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by ErkHal on 25/09/2017.
 * Reads data from the server and displays it in the conversation window
 */

public class ConversationUpdater implements Runnable {

    private boolean updateRunning = true;
    private Scanner reader;
    private MainActivity mainActivity;

    public ConversationUpdater(InputStream connection, MainActivity main) {

        this.reader = new Scanner(connection);
        this.mainActivity = main;
    }

    /**
     * Reads inputStream for strings, and appends them to the conversation window
     */
    public void run() {

        while(updateRunning) {

            final String msg = reader.nextLine();

            appendMessage(msg);
        }
    }

    /**
     * Appends the message to the conversation window using UI thread
     * @param message
     */
    public void appendMessage(final String message) {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.appendMessageToConversation(message + "\r\n");
            }
        });
    }
}
