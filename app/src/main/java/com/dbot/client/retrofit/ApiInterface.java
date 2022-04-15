package com.dbot.client.retrofit;


import com.dbot.client.login.model.Client;
import com.dbot.client.login.model.Login;
import com.dbot.client.login.model.SignUp;
import com.dbot.client.login.model.User;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ApiInterface {

    //get otp for login
    @GET("client/getotp/{mobilenumber}")
    Call<Login> getOtp(@Path("mobilenumber") String mobilenumber);

    //Signup
    @Headers("Content-Type: application/json")
    @POST("client/registerclient")
    Call<SignUp> registerClient(@Body User user);

}