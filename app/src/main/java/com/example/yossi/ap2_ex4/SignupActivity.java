package com.example.yossi.ap2_ex4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText username = (EditText)findViewById(R.id.txtUsername);
                EditText password = (EditText)findViewById(R.id.txtPassword);
                EditText name = (EditText)findViewById(R.id.txtName);
                EditText email = (EditText)findViewById(R.id.txtEmail);

                if(username.getText().toString().equals("") || password.getText().toString().equals("") ||
                        name.getText().toString().equals("") || email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "You did not enter in all your info", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(SignupActivity.this, MessageActivity.class));

            }
        });
    }
}
