package com.example.yossi.ap2_ex4;

import android.app.Application;
import android.util.Log;

public class MyApp extends Application {
    private String myState;

    public MyApp(){
        Log.d("MyApp", "my app i am here");
    }

    public void runAPrintMate(){
        Log.d("MyApp", "running a print!");
    }
}
