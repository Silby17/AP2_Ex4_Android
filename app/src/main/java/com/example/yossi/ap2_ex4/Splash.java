package com.example.yossi.ap2_ex4;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        final ImageView img = (ImageView)findViewById(R.id.imageView);
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
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
