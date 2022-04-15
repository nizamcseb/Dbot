package com.dbot.client.retrofit;


import com.dbot.client.login.model.LoginResponse;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    //get otp for login
    @GET("client/getotp/{mobilenumber}")
    Call<LoginResponse> getOtp(@Path("mobilenumber") String mobilenumber);

    //Signup
    @Headers("Content-Type: application/json")
    @POST("client/registerclient")
    Call<SignUpResponse> registerClient(@Body User user);

}