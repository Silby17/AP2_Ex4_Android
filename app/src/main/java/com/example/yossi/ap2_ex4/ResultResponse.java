package com.example.yossi.ap2_ex4;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultResponse implements Serializable {

    @SerializedName("msg")
    private String message;

    @SerializedName("result")
    private  String result;

    public ResultResponse(String msg, String result){
        this.message = msg;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
