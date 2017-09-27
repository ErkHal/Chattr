package com.example.elitebook.chattr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.elitebook.chattr.ChatServerIO.ChatServerCommunicationHandler;

import org.apache.commons.lang3.StringEscapeUtils;

public class MainActivity extends AppCompatActivity {

    //Chat box, message box and button
    TextView convoWindow;
    EditText messageBox;
    Button sendMessageButton;
    ChatServerCommunicationHandler chatHandler;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent serverConnectIntent = new Intent(this, selectServerActivity.class);
        startActivityForResult(serverConnectIntent, 0);

    }

    /**
     * Resumes the connection setup after address propmting
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Intent inputIntent = getIntent();
        Log.d("address", inputIntent.toString());
        String ipAddress = inputIntent.getStringExtra("IP_ADDRESS");
        int portNumber = inputIntent.getIntExtra("PORT_NUMBER", 1337);
        Log.d("address", "ip: " + ipAddress + " port: " + portNumber);

        //Initialize the I/O handler and pass it this activity as parameter
        chatHandler = new ChatServerCommunicationHandler(this, ipAddress, portNumber);

        //Tie layout elements into code
        convoWindow = (TextView) findViewById(R.id.convoWindow);
        convoWindow.setMovementMethod(new ScrollingMovementMethod());
        messageBox = (EditText) findViewById(R.id.messageBox);
        sendMessageButton = (Button) findViewById(R.id.sendButton);
        scrollView = (ScrollView) findViewById(R.id.scrollViewForConversation);

        //onClick listener for the SEND button
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message = messageBox.getText().toString();
                String converted = StringEscapeUtils.escapeJava(message);
                sendMessageToHandler(converted);

                //Clears the box after sending the message
                messageBox.getText().clear();
            }
        });

        //Initialize Handler thread for setting up I/O and server connection
        Thread handlerThread = new Thread(chatHandler);
        handlerThread.start();

    }

    /**
     * Adds new message to the textview
     * @param newMessage message to be added into the conversation
     */
    public void appendMessageToConversation(String newMessage) {

        String converted = StringEscapeUtils.unescapeJava(newMessage);
        convoWindow.append(converted);

        scrollDown();
    }

    /**
     * Sends the message to chat handler thread
     * @param msg
     */
    public void sendMessageToHandler(String msg) {

        if(msg.equals("!quit")) {
            onDestroy();
        }

        chatHandler.sendMessageToQueue(msg);

    }

    /**
     * Toggle send button disabled before connection to the server is established
     * @param state
     */
    public void toggleAllUiElements(boolean state) {

        //TODO: Disable elements before connection is made, and then enable again.

    }

    private void scrollDown() {

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(scrollView.getScrollY(), scrollView.getScrollY()
                        + scrollView.getHeight());
            }
        });
    }
}
