package com.example.yossi.ap2_ex4;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setting name of app in center
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        TextView textview = new TextView(getApplicationContext());
        android.support.v7.app.ActionBar.LayoutParams layoutparams = new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setGravity(Gravity.CENTER);
        textview.setText(ab.getTitle().toString());
        textview.setTextSize(20);
        ab.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(textview);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        //FIRST TIME IN APP CODE
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        // not First time on app - intent to explanationActivity
        if (welcomeScreenShown) {
        //to do : connect to server with user name and password with in local storage
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.connect), Toast.LENGTH_LONG).show();

        }
        else {
            final ImageView img = (ImageView) findViewById(R.id.imageView);
            final Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
            img.startAnimation(anim);

            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.welcome), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fun), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.tell), Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.come_back), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent i = new Intent(Splash.this, ExplanationActivity.class);
                    Splash.this.startActivity(i);
                    //flag done to mark that not first time in app
                    //figure it out how to come back to this page
                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putBoolean(welcomeScreenShownPref, true);
                    editor.commit(); // Very important to save the preference
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }
}
