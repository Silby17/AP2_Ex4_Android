package com.example.yossi.ap2_ex4;


import java.sql.Timestamp;
import java.util.Date;

/*
    The class is in charge if the chat message object
 */
public class DataProvider {
    public boolean position;
    public String message;
    public String username;
    public String date;

    /*
        constructor
        position - the position of the message
        message - the text that was sent
     */
    public DataProvider(boolean position, String message, String username,String date) {
        super();
        this.position = position;
        this.message = message;
        this.username = username;
        this.date = date;
    }
}

