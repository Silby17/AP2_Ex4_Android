package com.example.yossi.ap2_ex4;

import com.squareup.otto.Produce;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Communicator {
    private static  final String TAG = "Communicator";


    public void loginPost(String username, String password, Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        Interface communicatorInterface = restAdapter.create(Interface.class);
        communicatorInterface.postData("login", username, password, callback);
    }


    //private static final String SERVER_URL = "http://192.168.1.21:8080";
    //private static final String SERVER_URL = "http://192.168.1.15:8080";
    private static final String SERVER_URL = "http://advprog.cs.biu.ac.il:8080";

    public void newUserPost(String username, String pass, String name,
                            String email, String icon, Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        communicatorInterface.postNewUser(username, pass, name, email, icon, callback);
    }

    public void getMessagePost(String id, Callback<GetMessageResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        communicatorInterface.postGetMessage(id, callback);
    }

    public void newMessagePost(String username, String msg, Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        communicatorInterface.postNewMessage(username, msg, callback);
    }

    public void getMessageCount(String count, Callback<ResultResponse> callback){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        communicatorInterface.getMessageCount(count, callback);
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