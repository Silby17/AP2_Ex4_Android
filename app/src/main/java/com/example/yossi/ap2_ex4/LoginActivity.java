package com.example.yossi.ap2_ex4;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        //First time on app - intent to explanationActivity
        if (!welcomeScreenShown) {
            startActivity(new Intent(LoginActivity.this, ExplanationActivity.class));
            //figure it out how to come back to this page
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit(); // Very important to save the preference
        }
        //Secound time on app - intent to message
        final Button btnSubmit = (Button) findViewById(R.id.btnSubmitLogin);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.txtUsernameLogin);
                EditText password = (EditText) findViewById(R.id.txtPasswordLogin);

                if (username.getText().toString().equals("") ||
                        password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "You did not enter in all your info",
                            Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(LoginActivity.this, MessageActivity.class));
            }
        });

        final Button btnResend = (Button) findViewById(R.id.btnLogin);
        btnResend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
            }
        });
    }


}

