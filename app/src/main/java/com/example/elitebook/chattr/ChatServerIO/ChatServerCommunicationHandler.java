package com.example.elitebook.chattr.ChatServerIO;

import com.example.elitebook.chattr.ChatMessage.ChatMessage;
import com.example.elitebook.chattr.MainActivity;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by ErkHal on 25/09/2017.
 * Uses basic Socket class for connecting to chat server
 */

public class ChatServerCommunicationHandler implements Runnable {

    //Socket for connecting to the server
    private Socket clientSocket;

    // I/O for the server connection
    private ConversationUpdater reader;
    private MessageSender sender;

    //Ip address and port number
    private String ipAddress;
    private int portNumber;

    private MainActivity mainActivity;

    private Thread senderThread;
    private Thread updaterThread;

    public ChatServerCommunicationHandler(MainActivity main, String ipAddr, int portNumber) {

        this.mainActivity = main;

        this.ipAddress = ipAddr;
        this.portNumber = portNumber;

    }

    /**
     * Sets up the connections to server. If connecting fails, displays a message in the chat window and doesn't do anything else
     */
    public void run() {

                mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.disableSendButton();
            }
        });

                boolean connection = connectToServer();
                if(connection) {
                    setupStreams();

                    senderThread = new Thread(sender);
                    updaterThread = new Thread(reader);

                    senderThread.start();
                    updaterThread.start();

                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.enableSendButton();
                            mainActivity.setNotConnected(false);
                        }
                    });

                } else {

                    reader = new ConversationUpdater(System.in, mainActivity);
                    ChatMessage warning = new ChatMessage("", "", "Cannot connect to host ! Please try again after a while" +
                            " or try another address and port number !", true);
                    reader.appendMessage(warning);
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
     * Connects to the chat server using the provided ip and port number
     * @return True if connection established, otherwise False
     */
    private boolean connectToServer(){

        try {

            clientSocket = new Socket(this.ipAddress, this.portNumber);
            return true;

        } catch (IOException io) {
            io.printStackTrace();
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

    /**
     * Sends interrupts to I/O Threads
     */
    public void stop() {

        senderThread.interrupt();
        updaterThread.interrupt();
    }
}
