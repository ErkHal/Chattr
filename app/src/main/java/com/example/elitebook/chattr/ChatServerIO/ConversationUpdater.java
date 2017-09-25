package com.example.elitebook.chattr.ChatServerIO;

import android.widget.TextView;

import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ErkHal on 25/09/2017.
 */

public class ConversationUpdater implements Runnable {

    private boolean updateRunning = true;
    private boolean newMessageReceived = false;

    private TextView conversationWindow;
    //private InputStream connectionToServer;
    private Scanner reader;

    public ConversationUpdater(TextView updateableWindow, InputStream connection) {

        this.conversationWindow = updateableWindow;
        //this.connectionToServer = connection;
        this.reader = new Scanner(connection);
    }

    public void run() {

        while(updateRunning) {

            String possibleMessage = reader.nextLine();
            conversationWindow.append(possibleMessage);
        }
    }
}
