package com.example.yossi.ap2_ex4;

import android.content.Intent;
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
        //radio bar
        final RadioButton panda = (RadioButton)findViewById(R.id.panda);
        panda.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            }
        });
        final RadioButton rednose = (RadioButton)findViewById(R.id.rednose);
        rednose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            }
        });
        final RadioButton redface = (RadioButton)findViewById(R.id.redface);
        redface.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            }
        });


        final Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText username = (EditText)findViewById(R.id.txtUsername);
                EditText password = (EditText)findViewById(R.id.txtPassword);
                EditText name = (EditText)findViewById(R.id.txtName);
                EditText email = (EditText)findViewById(R.id.txtEmail);

                if(username.getText().toString().equals("") || password.getText().toString().equals("") ||
                        name.getText().toString().equals("") || email.getText().toString().equals("")
                        || (!(redface.isChecked()) && !(rednose.isChecked()) && !(panda.isChecked()))){
                    Toast.makeText(getApplicationContext(), "You did not enter in all your info", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }

            }
        });
        final Button btnResend = (Button) findViewById(R.id.btnReset);
        btnResend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, SignupActivity.class));
            }
        });
    }
}
