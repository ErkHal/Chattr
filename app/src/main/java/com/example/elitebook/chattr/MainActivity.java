package com.example.elitebook.chattr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elitebook.chattr.ChatMessage.ChatMessage;
import com.example.elitebook.chattr.ChatServerIO.ChatServerCommunicationHandler;

import org.apache.commons.lang3.StringEscapeUtils;

public class MainActivity extends AppCompatActivity {

    //Chat box, message box and button
    private EditText messageBox;
    private Button sendMessageButton;
    private ChatServerCommunicationHandler chatHandler;
    private ScrollView scrollView;

    private boolean exitConfirmationDisplayed;

    private boolean notConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        //Retrieve Extra data passed from the server selection activity
        String ipAddress = intent.getStringExtra("IP_ADDRESS");
        int portNumber = intent.getIntExtra("PORT_NUMBER", 1337);
        Log.d("address", "ip: " + ipAddress + " port: " + portNumber);

        //Initialize the I/O handler and pass it this current activity as parameter
        chatHandler = new ChatServerCommunicationHandler(this, ipAddress, portNumber);

        //Tie UI elements into java
        messageBox = (EditText) findViewById(R.id.messageBox);
        sendMessageButton = (Button) findViewById(R.id.sendButton);
        scrollView = (ScrollView) findViewById(R.id.scrollViewForConversation);

        //onClick listener for the SEND button
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exitConfirmationDisplayed = false;
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

        exitConfirmationDisplayed = false;
        notConnected = true;

    }

    /**
     * Adds new chat bubble to the textview
     * @param msg ChatMessage to be added into the conversation
     */
    public void appendMessageToConversation(ChatMessage msg) {

        final LinearLayout conversation = (LinearLayout) findViewById(R.id.linearConversation);
        TextView chatBubble = new TextView(getApplicationContext());
        String converted = StringEscapeUtils.unescapeJava(msg.getMessage());

        conversation.addView(chatBubble);
        chatBubble.setText(msg.getTimeStamp() + " " + msg.getSender() + "\n" + converted);

        LinearLayout.LayoutParams textViewParams = (LinearLayout.LayoutParams) chatBubble.getLayoutParams();
        textViewParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        textViewParams.width = 400;
        textViewParams.topMargin = 30;

        //Check the owner of the message and adjust style and alignment accordingly
        if(msg.isOwnMessage()) {

            chatBubble.setTextAppearance(R.style.own_chatbubble);
            chatBubble.setBackgroundResource(R.drawable.chat_bubble_own);
            textViewParams.gravity = Gravity.RIGHT;
            chatBubble.setLayoutParams(textViewParams);

        } else {

            chatBubble.setTextAppearance(R.style.other_user_chatbubble);
            chatBubble.setBackgroundResource(R.drawable.chat_bubble_others);
            textViewParams.gravity = Gravity.LEFT;
            chatBubble.setLayoutParams(textViewParams);
        }

        scrollDown();
    }

    /**
     * Sends the message to chat handler thread
     * @param msg
     */
    public void sendMessageToHandler(String msg) {

        if(msg.equals("!quit")) {
            quit();
        }

        chatHandler.sendMessageToQueue(msg);

    }

    /**
     * Scrolls the chat window down when new message is added
     */
    private void scrollDown() {

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(scrollView.getScrollY(), scrollView.getScrollY()
                        + scrollView.getHeight());
            }
        });
    }

    /**
     * Disables the send button when connection isn't acquired
     */
    public void disableSendButton() {

        sendMessageButton.setEnabled(false);
    }

    /**
     * Enables the send button when connection is acquired
     */
    public void enableSendButton() {

        sendMessageButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {

        if(!exitConfirmationDisplayed) {

            Toast.makeText(this, "Press back button again to exit", Toast.LENGTH_SHORT).show();
            exitConfirmationDisplayed = true;

        } else {

            if(notConnected) {

                this.finish();
            } else {

                sendMessageToHandler("!quit");
            }
        }
    }

    /**
     *Stops the threads and finishes this activity, returning to SelectServerActivity
     */
    public void quit() {

        chatHandler.stop();
        this.finish();

    }

    public void setNotConnected(boolean state) {

        this.notConnected = state;
    }
}
