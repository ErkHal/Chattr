package com.example.elitebook.chattr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This activity pops up to prompt the user for the ip and port number of the server.
 */
public class selectServerActivity extends AppCompatActivity {

    Button goButton;
    EditText ipAddressField;
    EditText portNumberField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_server);
        

        goButton = (Button) findViewById(R.id.go_button);
        ipAddressField = (EditText) findViewById(R.id.ip_address);
        portNumberField = (EditText) findViewById(R.id.port_number);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendServerInfoAndQuit();
            }
        });

    }

    @Override
    protected void onStart() {

        Log.d("kissa", "Activity starting again !");
        super.onStart();

        setContentView(R.layout.activity_select_server);


        goButton = (Button) findViewById(R.id.go_button);
        ipAddressField = (EditText) findViewById(R.id.ip_address);
        portNumberField = (EditText) findViewById(R.id.port_number);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendServerInfoAndQuit();
            }
        });


    }

    /**
     * Register extras and close this activity and start the chat Activity
     */
    private void sendServerInfoAndQuit() {

        String ipAddress = String.valueOf(ipAddressField.getText().toString());
        int portNumber = Integer.parseInt(portNumberField.getText().toString());
        Log.d("address", ipAddress + " | " + portNumber);

        Intent backToMainIntent = new Intent(this, MainActivity.class);
        backToMainIntent.putExtra("IP_ADDRESS", ipAddress);
        backToMainIntent.putExtra("PORT_NUMBER", portNumber);
        startActivity(backToMainIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("kissa", "Activity stopping !!!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("kissa", "Activity Resuming !!");
    }
}
