package com.example.yossi.ap2_ex4;

import com.squareup.otto.Produce;
import retrofit.Callback;
import retrofit.RestAdapter;


public class Communicator {
    private static  final String TAG = "Communicator";
    //private static final String SERVER_URL = "http://192.168.1.21:8080";
    private static final String SERVER_URL = "http://192.168.173.1:8080";


    public void loginPost(String username, String password, Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        Interface communicatorInterface = restAdapter.create(Interface.class);
        communicatorInterface.postData("login", username, password, callback);
    }

    public void newUserPost(String username, String pass, String name, String email, String icon, Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        communicatorInterface.postNewUser(username, pass, name, email, icon, callback);
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