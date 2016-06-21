package com.example.yossi.ap2_ex4;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/*****************************************************************************
 * This class is in charge of the SPLASH screen that opens up
 * at each use of the app
 *****************************************************************************/
public class Splash extends Activity{
    final String welcomeScreenShownPref = "welcomeScreenShown";
    public static final String TAG = "SplashScreen";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**Show Window in full screen without status bar**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        /**First time in the Application**/
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = preferences.getBoolean(welcomeScreenShownPref, false);

        //TODO remove the following additions to the prefs
        preferences.edit().putString("username", "admin").commit();
        preferences.edit().putString("password", "admin").commit();


        /**If This is not First time if app**/
        if (welcomeScreenShown) {
            Log.d(TAG, "Not the First time in this screen");
            //Starts the animation on the Splash Screen
            final ImageView img = (ImageView) findViewById(R.id.imageView);
            final Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
            img.startAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener(){

                @Override
                public void onAnimationStart(Animation animation) {
                    (new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            SharedPreferences pref = PreferenceManager.
                                    getDefaultSharedPreferences(getApplicationContext());

                            /**Get the login details of the user and log them in**/
                            String username = pref.getString("username", "");
                            String pass = pref.getString("password", "");
                            Communicator communicator = new Communicator();
                            /**Login into the Server**/
                            communicator.loginPost(username, pass, new Callback<ResultResponse>() {
                                @Override
                                public void success(ResultResponse serverResponse, Response response2) {
                                    Log.d("splash", "Success");
                                    //If the POST operation was successful we will check the response code
                                    if(serverResponse.getResult().equals("1")){
                                        Intent msg = new Intent(Splash.this, MessageActivity.class);
                                        Splash.this.startActivity(msg);
                                    }
                                }
                                @Override
                                public void failure(RetrofitError error) {
                                    if(error != null ){
                                        Log.e(TAG, error.getMessage());
                                        error.printStackTrace();
                                    }
                                }
                            });
                            return null;
                        }
                    }).execute();
                    /**Displays a Toast to notify the user that he is being connected
                     * To the server **/
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.connect),
                            Toast.LENGTH_LONG).show();
                }
                @Override
                public void onAnimationEnd(Animation animation) {}
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        }
        /**This is the first time in the app**/
        else {
            /**Starts the animation in the splash screen**/
            final ImageView img = (ImageView) findViewById(R.id.imageView);
            final Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
            img.startAnimation(anim);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                //Show the Required Toasts
                public void onAnimationStart(Animation animation) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.welcome),
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.fun),
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.tell),
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.come_back),
                            Toast.LENGTH_LONG).show();

                    //flag checked to mark that not first time in app
                    //figure it out how to come back to this page
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(welcomeScreenShownPref, true);
                    editor.commit(); // Very important to save the preference
                    Intent i = new Intent(Splash.this, ExplanationActivity.class);
                    Splash.this.startActivity(i);
                    finish();
                }

                @Override
                public void onAnimationEnd(Animation animation) {}

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        }
    }
}