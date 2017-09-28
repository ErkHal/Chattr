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
                sendServerInfo();
            }
        });

    }

    private void sendServerInfo() {

        String ipAddress = String.valueOf(ipAddressField.getText().toString());
        int portNumber = Integer.parseInt(portNumberField.getText().toString());
        Log.d("address", ipAddress + " | " + portNumber);

        //Prototype version, trying to pass information via singleton class
        serverConnectionInformation.getInstance().setServerIP(ipAddress);
        serverConnectionInformation.getInstance().setPortNumber(portNumber);

        Intent backToMainIntent = new Intent(this, MainActivity.class);
        backToMainIntent.putExtra("IP_ADDRESS", ipAddress);
        backToMainIntent.putExtra("PORT_NUMBER", portNumber);
        startActivity(backToMainIntent);
    }
}
