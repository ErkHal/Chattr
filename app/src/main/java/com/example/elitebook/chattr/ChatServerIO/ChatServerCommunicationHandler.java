package com.example.elitebook.chattr.ChatServerIO;

import com.example.elitebook.chattr.MainActivity;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by ErkHal on 25/09/2017.
 * Uses basic Socket class for connecting to chat server
 */

public class ChatServerCommunicationHandler implements Runnable {

    private final String SERVER_IP = "192.168.43.234";
    private final int SERVER_PORT = 1337;

    //Socket for connecting to the server
    private Socket clientSocket;

    // I/O for the server connection
    private ConversationUpdater reader;
    private MessageSender sender;

    MainActivity mainActivity;

    boolean setupDone;

    public ChatServerCommunicationHandler(MainActivity main) {

        this.setupDone = false;
        this.mainActivity = main;

    }

    public void run() {

        while(true) {

            if(!setupDone) {
                connectToServer();
                setupStreams();

                Thread senderThread = new Thread(sender);
                Thread updaterThread = new Thread(reader);

                senderThread.start();
                updaterThread.start();

                setupDone = true;
            }
        }
    }

    /**
     * Sends the message to MessageSender blocking Queue
     * @param msg
     */
    public void sendMessageToQueue(String msg) {

        sender.addMessage(msg);
    }

    /**
     * Connects to the chat server
     * @return
     */
    private boolean connectToServer(){

        try {

            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            return true;

        } catch (IOException io) {
            return false;
        }
    }

    /**
     * Sets up the streams for I/O over the network
     */
    private void setupStreams() {

        try {
            reader = new ConversationUpdater(clientSocket.getInputStream(), mainActivity);
            sender = new MessageSender(new PrintStream(clientSocket.getOutputStream(),true));
        } catch(IOException io) {
            io.printStackTrace();
        }
    }
}
