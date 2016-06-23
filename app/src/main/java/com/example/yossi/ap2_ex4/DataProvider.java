package com.example.yossi.ap2_ex4;

/*
    The class is in charge if the chat message object
 */
public class DataProvider {
    public boolean position;
    public String message;
    public String username;
    public String date;

    /************************************************************************
     * Constructor
     * @param position - position of the method
     * @param message - message itself
     * @param username - user who sent the message
     * @param date - date of send
     ************************************************************************/
    public DataProvider(boolean position, String message, String username,String date) {
        super();
        this.position = position;
        this.message = message;
        this.username = username;
        this.date = date;
    }
}