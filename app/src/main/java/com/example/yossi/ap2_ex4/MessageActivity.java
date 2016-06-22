package com.example.yossi.ap2_ex4;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MessageActivity extends AppCompatActivity {
    private String TAG = "MessageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final Button btnSend = (Button)findViewById(R.id.btnSendMessage);
        assert btnSend != null;
        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Communicator communicator = new Communicator();
                String msg = ((EditText)findViewById(R.id.txtMessage)).getText().toString();

                communicator.newMessagePost("Joe", msg, new Callback<ResultResponse>() {
                    @Override
                    public void success(ResultResponse resultResponse, Response response) {
                        if(resultResponse.getResult().equals("1")){
                            Toast.makeText(getApplicationContext(),
                                    R.string.sendSuccess, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Done perfectly");
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });
        ((EditText)findViewById(R.id.txtMessage)).setText(null);

        /**
        final Button btn = (Button) findViewById(R.id.btnSendMessage);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communicator communicator = new Communicator();
                communicator.getMessagePost("2", new Callback<GetMessageResponse>() {
                    @Override
                    public void success(GetMessageResponse getMessageResponse, Response response) {
                        Log.d(TAG, getMessageResponse.toString());
                    }
                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }
        });
         **/
    }
}
