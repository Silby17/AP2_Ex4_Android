package com.example.yossi.ap2_ex4;

import android.app.Application;
import java.net.CookieHandler;
import java.net.CookieManager;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CookieHandler.setDefault(new CookieManager());
    }
}