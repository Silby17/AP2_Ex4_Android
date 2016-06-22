package com.example.yossi.ap2_ex4;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


/***************************************************************************
 * This Interface will define all the Posting methods that will be used
 * to send data to the web server using the POST method
 ***************************************************************************/
public interface Interface {


    /**********************************************************************
     * This will POST new user data to the server
     * @param username - username
     * @param password - password
     * @param name - users name
     * @param email - users email
     * @param icon - icon of their choosing
     * @param serverResponseCallback - The response result from server
     **********************************************************************/
    @FormUrlEncoded
    @POST("/NewUser")
    void postNewUser(@Field("username") String username,
                     @Field("password") String password,
                     @Field("name") String name,
                     @Field("email") String email,
                     @Field("icon") String icon,
                     Callback<ResultResponse> serverResponseCallback);


    /**********************************************************************
     * This method will POST login data to the Web Server
     * @param method - Method to post to
     * @param username - username
     * @param password - password
     * @param serverResponseCallback login result from the server
     ***********************************************************************/
    @FormUrlEncoded
    @POST("/login")
    void postData(@Field("method") String method,
                  @Field("username") String username,
                  @Field("password") String password,
                  Callback<ResultResponse> serverResponseCallback);


    /**********************************************************************
     * This Method will POST the new Message and the senders username to
     * the Web Server
     * @param username - username
     * @param message - message to send
     * @param serverResponse - Response result from the server
     ***********************************************************************/
    @FormUrlEncoded
    @POST("/messageServlet")
    void postNewMessage(@Field("username") String username,
                        @Field("message") String message,
                        Callback<ResultResponse> serverResponse);


    /**********************************************************************
     * This method will POST a request to the the Server to get a specific
     * message with a given ID
     * @param id - ID of the message to get
     * @param serverResponse - response from server with the msg or with
     *                       the failed report
     ***********************************************************************/
    @FormUrlEncoded
    @POST("/getMessage")
    void postGetMessage(@Field("msgID") String id,
                        Callback<GetMessageResponse> serverResponse);
}