package com.example.yossi.ap2_ex4;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //setting name of app in center
        ActionBar ab = getSupportActionBar();
        TextView textview = new TextView(getApplicationContext());
        ActionBar.LayoutParams layoutparams = new LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setGravity(Gravity.CENTER);
        textview.setText(ab.getTitle().toString());
        textview.setTextSize(20);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ab.setCustomView(textview);


        //Radio Group
        final RadioButton panda = (RadioButton)findViewById(R.id.panda);
        assert panda != null;
        panda.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

            }
        });
        final RadioButton rednose = (RadioButton)findViewById(R.id.rednose);
        assert rednose != null;
        rednose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            }
        });
        final RadioButton redface = (RadioButton)findViewById(R.id.redface);
        assert redface != null;
        redface.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            }
        });


        final Button btnSend = (Button)findViewById(R.id.btnSend);
        assert btnSend != null;
        btnSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText username = (EditText)findViewById(R.id.txtUsername);
                EditText password = (EditText)findViewById(R.id.txtPassword);
                EditText name = (EditText)findViewById(R.id.txtName);
                EditText email = (EditText)findViewById(R.id.txtEmail);
                String icon = null;

                if(username.getText().toString().equals("") || password.getText().toString().equals("") ||
                        name.getText().toString().equals("") || email.getText().toString().equals("")
                        || (!(redface.isChecked()) && !(rednose.isChecked()) && !(panda.isChecked()))){
                    Toast.makeText(getApplicationContext(), "You did not enter in all your info", Toast.LENGTH_SHORT).show();
                }
                else {
                    String usernameStr = username.getText().toString();
                    String passStr = password.getText().toString();
                    String nameStr  = name.getText().toString();
                    String emailStr = email.getText().toString();
                    if(redface.isChecked()){
                        icon = "redFace";
                    } else if(rednose.isChecked()){
                        icon = "redNose";
                    }
                    else{
                        icon = "panda";
                    }

                    (new AsyncTask<String, Void, Void>(){
                        @Override
                        protected Void doInBackground(String... params) {
                            Communicator communicator = new Communicator();
                            communicator.newUserPost(params[0], params[1], params[2], params[3], params[4], new Callback<ResultResponse>() {
                                @Override
                                public void success(ResultResponse resultResponse, Response response) {
                                    Intent i = new Intent(SignupActivity.this, MessageActivity.class);
                                    SignupActivity.this.startActivity(i);
                                }
                                @Override
                                public void failure(RetrofitError error) {
                                }
                            });
                            return null;
                        }
                    }).execute(usernameStr, passStr, nameStr, emailStr, icon);
                    //TODO send signup info to server
                    startActivity(new Intent(SignupActivity.this, MessageActivity.class));
                }

            }
        });
        final Button btnResend = (Button) findViewById(R.id.btnReset);
        assert btnResend != null;
        btnResend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, SignupActivity.class));
            }
        });
    }
}
