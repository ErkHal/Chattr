package com.example.elitebook.chattr;

/**
 * Created by ErkHal on 27/09/2017.
 */

public class serverConnectionInformation {

    private String serverIP;
    private int portNumber;

    private static final serverConnectionInformation ourInstance = new serverConnectionInformation();

    public static serverConnectionInformation getInstance() {
        return ourInstance;
    }

    private serverConnectionInformation() {

        this.serverIP = "10.0.2.2";
        this.portNumber = 1337;
    }

    public String getServerIp() {

        return this.serverIP;
    }

    public int getServerPort() {

        return this.portNumber;
    }

    public void setPortNumber(int port) {

        this.portNumber = port;
    }

    public void setServerIP(String ipAddr) {

        this.serverIP = ipAddr;
    }
}
