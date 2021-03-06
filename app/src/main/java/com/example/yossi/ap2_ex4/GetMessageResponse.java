package com.example.yossi.ap2_ex4;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

/***************************************************************************
 * This class is the Response that the App will get from the server
 * after requesting information from the SQL server
 * This is a standard serializable class with getters and setters for all
 * fields of this Object
 ***************************************************************************/
public class GetMessageResponse implements Serializable {

    @SerializedName("username")
    private String username;

    @SerializedName("message")
    private String message;

    @SerializedName("time")
    private String time;

    public GetMessageResponse(String username, String message, String time){
        this.username = username;
        this.message = message;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
