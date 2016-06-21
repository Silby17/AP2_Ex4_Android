package com.example.yossi.ap2_ex4;

import android.database.CursorJoiner;
import android.util.Log;
import com.squareup.otto.Produce;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Communicator {
    private static  final String TAG = "Communicator";
    private static final String SERVER_URL = "http://192.168.1.21:8080";
    //private static final String SERVER_URL = "http://172.18.6.116:8080";


    public void loginPost(String username, String password, Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        Interface communicatorInterface = restAdapter.create(Interface.class);
        communicatorInterface.postData("login", username, password, callback);
    }

    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }
}