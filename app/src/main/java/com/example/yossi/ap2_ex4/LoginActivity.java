package com.example.yossi.ap2_ex4;
<<<<<<< HEAD
import android.content.DialogInterface;
=======

>>>>>>> origin/yossiB1
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
<<<<<<< HEAD
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
=======
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
>>>>>>> origin/yossiB1

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
<<<<<<< HEAD
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username = (EditText) findViewById(R.id.txtUsernameLogin);
                EditText password = (EditText) findViewById(R.id.txtPasswordLogin);

                if (username.getText().toString().equals("") ||
                        password.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "You did not enter in all your info",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(LoginActivity.this, MessageActivity.class));
=======
        assert btnSubmit != null;
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("LoginActivity", "USER clicked SUBMIT BUTTON");
                EditText username = (EditText) findViewById(R.id.txtUsernameLogin);
                EditText password = (EditText) findViewById(R.id.txtPasswordLogin);

                try {
                    sendToServer();
                } catch (Exception e) {
                    e.printStackTrace();
>>>>>>> origin/yossiB1
                }
            }
        });

        final Button btnResend = (Button) findViewById(R.id.btnLogin);
        btnResend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
<<<<<<< HEAD
       // ActionBar.LayoutParams params = new ActionBar.LayoutParams(Gravity.CENTER);
       // getActionBar().setCustomView(v, params) .getCustomView().setLayoutParams(params);
    }


}

=======
        // ActionBar.LayoutParams params = new ActionBar.LayoutParams(Gravity.CENTER);
        // getActionBar().setCustomView(v, params) .getCustomView().setLayoutParams(params);
    }

    public void sendToServer() throws Exception{
        Log.d("TESTER:", "Inside Send to sevrer");
        EditText username = (EditText) findViewById(R.id.txtUsernameLogin);
        EditText password = (EditText) findViewById(R.id.txtPasswordLogin);

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    URL url = new URL("http://10.0.2.2:8080/login");
                    Map<String, String> params = new LinkedHashMap<>();
                    //params.put("username", username.getText().toString());
                    //params.put("password", password.getText().toString());
                    params.put("password", "admin");
                    params.put("password", "admin");

                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String, String> param : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                    }
                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    Log.d("trying url", "URL Connected");
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(postDataBytes);
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
        });
        thread.start();
    }
}
>>>>>>> origin/yossiB1
