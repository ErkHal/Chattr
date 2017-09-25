package com.example.elitebook.chattr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.elitebook.chattr.ChatServerIO.ConversationUpdater;
import com.example.elitebook.chattr.ChatServerIO.MessageSender;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    //Streams and socket
    PrintStream clientOutput = null;
    InputStream clientInput = null;
    Socket clientSocket;

    //Chat box, message box and button
    TextView convoWindow;
    EditText messageBox;
    Button sendMessageButton;

    //I/O handlers
    ConversationUpdater convoUpdater;
    MessageSender messageSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tie layout elements into code
        convoWindow = (TextView) findViewById(R.id.convoWindow);
        messageBox = (EditText) findViewById(R.id.messageBox);
        sendMessageButton = (Button) findViewById(R.id.sendButton);


    }
}
