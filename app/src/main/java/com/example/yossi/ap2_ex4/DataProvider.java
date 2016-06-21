package com.example.yossi.ap2_ex4;


/*
    The class is in charge if the chat message object
 */
public class DataProvider {
    public boolean position;
    public String message;

    /*
        constructor
        position - the position of the message
        message - the text that was sent
     */
    public DataProvider(boolean position, String message) {
        super();
        this.position = position;
        this.message = message;
    }
}

