package com.example.yossi.ap2_ex4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/*****************************************************************************
 * This class will handle the singing up of the new User.
 * It will extract all the info from the form, check that everything is in
 * the correct format and filled in and then send to the WebServer
 *****************************************************************************/
public class SignupActivity extends AppCompatActivity {
    private String TAG = "SignupActivity";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //setting name of app in center
        ActionBar ab = getSupportActionBar();
        TextView textview = new TextView(getApplicationContext());

        ActionBar.LayoutParams layoutparams = new
                LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);

        textview.setLayoutParams(layoutparams);
        textview.setGravity(Gravity.CENTER);
        textview.setText(ab.getTitle().toString());
        textview.setTextSize(20);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(textview);

        /**The following code is to capture the click on one of the icons**/
        //Radio Group
        final RadioButton panda = (RadioButton) findViewById(R.id.panda);
        assert panda != null;
        panda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        final RadioButton rednose = (RadioButton) findViewById(R.id.rednose);
        assert rednose != null;
        rednose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        final RadioButton redface = (RadioButton) findViewById(R.id.redface);
        assert redface != null;
        redface.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });


        final Button btnSend = (Button) findViewById(R.id.btnSend);
        assert btnSend != null;
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.txtUsername);
                EditText password = (EditText) findViewById(R.id.txtPassword);
                EditText name = (EditText) findViewById(R.id.txtName);
                EditText email = (EditText) findViewById(R.id.txtEmail);
                String icon = null;

                if (username.getText().toString().equals("") ||
                        password.getText().toString().equals("") ||
                        name.getText().toString().equals("") ||
                        email.getText().toString().equals("") ||
                        (!(redface.isChecked()) && !(rednose.isChecked()) &&
                                !(panda.isChecked()))) {
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                } else {
                    String usernameStr = username.getText().toString();
                    String passStr = password.getText().toString();
                    String nameStr = name.getText().toString();
                    String emailStr = email.getText().toString();
                    if (redface.isChecked()) {
                        icon = "redFace";
                    } else if (rednose.isChecked()) {
                        icon = "redNose";
                    } else {
                        icon = "panda";
                    }

                    (new AsyncTask<String, Void, Void>() {
                        @Override
                        protected Void doInBackground(final String... params) {
                            Communicator communicator = new Communicator();
                            communicator.newUserPost(params[0], params[1],
                                    params[2], params[3], params[4],
                                    new Callback<ResultResponse>() {
                                        @Override
                                        public void success(ResultResponse resultResponse, Response response) {
                                            if (resultResponse.getResult().equals("1")) {
                                                preferences.edit().putString("username", params[0]).apply();
                                                preferences.edit().putString("password", params[1]).apply();
                                                Intent i = new Intent(SignupActivity.this,
                                                        ChatActivity.class);
                                                SignupActivity.this.startActivity(i);
                                            } else {
                                                //If there was some error from the server
                                                Toast.makeText(SignupActivity.this,
                                                        R.string.errorConnection, Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        //If there was an error posting the data
                                        @Override
                                        public void failure(RetrofitError error) {
                                            if (error != null) {
                                                Log.e(TAG, error.getMessage());
                                                error.printStackTrace();
                                            }
                                        }
                                    });
                            return null;
                        }
                    }).execute(usernameStr, passStr, nameStr, emailStr, icon);
                }
            }
        });


        /***********************************************************************
         * This button will clear all entered data by simply reloading activity
         **********************************************************************/
        final Button btnReset = (Button) findViewById(R.id.btnReset);
        assert btnReset != null;
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }
}