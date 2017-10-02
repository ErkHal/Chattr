package com.example.elitebook.chattr.ChatServerIO;

import com.example.elitebook.chattr.ChatMessage.ChatMessage;
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

        while (updateRunning) {

            final String msg = reader.nextLine();

            char separator = '£';

            ChatMessage chatMessage;
            boolean isOwnMessage = false;

            if (msg.contains("" + separator)) {

                String[] separatedMsg;

                /*int firstSeparator = 0;
                int secondSeparator = 0;

                for (int firstIndex = 0; firstIndex < msg.length(); firstIndex++) {

                    if (msg.charAt(firstIndex) == separator) {

                        firstSeparator = firstIndex;

                        for (int i = firstIndex; i < msg.length(); i++) {

                            if (msg.charAt(i) == separator)
                                secondSeparator = i;
                            break;
                        }
                    }
                } */

                separatedMsg = msg.split("£", 3);

                //Checks if the message sent from the server was originally sent by this user.
                if(separatedMsg[1].equals("OWN_MESSAGE")) {
                    isOwnMessage = true;
                }

                //The message is a user-sent message
                chatMessage = new ChatMessage(separatedMsg[1], separatedMsg[0], separatedMsg[2], isOwnMessage);
                appendMessage(chatMessage);

            } else {

                //The message printed is a system message
                appendMessage(new ChatMessage(null, null, msg, true));
            }
        }
    }

    /**
     * Appends the message to the conversation window using UI thread
     * @param msg The message to be displayed
     */
    public void appendMessage(final ChatMessage msg) {

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.appendMessageToConversation(msg);
            }
        });
    }
}
