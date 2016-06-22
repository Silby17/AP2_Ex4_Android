package com.example.yossi.ap2_ex4;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class GetMessageResponse implements Serializable {

    @SerializedName("username")
    private String username;

    @SerializedName("message")
    private String message;

    @SerializedName("sentTime")
    private Date time;

    public GetMessageResponse(String username, String message, Date date){
        this.username = username;
        this.message = message;
        this.time = date;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
