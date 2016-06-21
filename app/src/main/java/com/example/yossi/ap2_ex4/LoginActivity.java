package com.example.yossi.ap2_ex4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends AppCompatActivity {
    public static String cookie = null;
    private String TAG = "LoginActivity";
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        setContentView(R.layout.activity_login);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        //First time on app - intent to explanationActivity
        if (!welcomeScreenShown) {
            startActivity(new Intent(LoginActivity.this, ExplanationActivity.class));
            //figure it out how to come back to this page
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit(); // Very important to save the preference
        }

        //Second time on app - intent to message
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        assert btnLogin != null;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              sendLoginInfo();
            }
        });


        final Button btnResend = (Button) findViewById(R.id.btnSignUp);
        assert btnResend != null;
        btnResend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }


    /*************************************************************************
     * This method will send the Login details to the WebServer
     * @throws Exception - IOException
     *************************************************************************/
    public void sendLoginInfo(){

        EditText username = (EditText) findViewById(R.id.txtUsernameLogin);
        EditText password = (EditText) findViewById(R.id.txtPasswordLogin);
        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();

        (new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String username = params[0];
                String password = params[1];
                Communicator communicator = new Communicator();
                communicator.loginPost(username, password, new Callback<ResultResponse>() {
                    @Override
                    public void success(ResultResponse resultResponse, Response response) {

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
        }).execute(usernameStr, passwordStr);


    }


    @Override
    public void onResume(){
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}