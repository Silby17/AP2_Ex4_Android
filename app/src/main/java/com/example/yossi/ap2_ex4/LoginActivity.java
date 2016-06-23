package com.example.yossi.ap2_ex4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/*****************************************************************************
 * This Class will handle all the Login actions
 *****************************************************************************/
public class LoginActivity extends AppCompatActivity {
    public static String cookie = null;
    private String TAG = "LoginActivity";
    private SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        /**This will hide the virtual keyboard**/
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
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

                //Sends the info to Post to the server and creates a new
                //Call back in order to get the server response
                communicator.loginPost(username, password, new Callback<ResultResponse>() {
                    @Override
                    public void success(ResultResponse resultResponse, Response response) {
                        //Checks if the login details are correct by checking
                        // //the server response
                        if(resultResponse.getResult().equals("-1")){
                            Toast.makeText(getApplicationContext(),
                                    R.string.detailsError, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent msg = new Intent(LoginActivity.this, ChatActivity.class);
                            startActivity(msg);
                        }
                    }
                    //If the connection and post the the server fails
                    @Override
                    public void failure(RetrofitError error) {
                        if(error != null) {
                            Log.e(TAG, error.getMessage());
                            error.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(),
                                R.string.errorWithServer, Toast.LENGTH_SHORT).show();
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