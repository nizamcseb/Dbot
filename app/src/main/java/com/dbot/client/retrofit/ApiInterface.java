package com.dbot.client.retrofit;


import com.dbot.client.login.model.CityResponse;
import com.dbot.client.login.model.LoginResponse;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;
import com.dbot.client.main.home.model.AvailableSlotsResponse;
import com.dbot.client.main.newrequest.model.BookSlot;
import com.dbot.client.main.newrequest.model.BookSlotResponse;
import com.dbot.client.main.newrequest.model.PackageResponse;
import com.dbot.client.main.projects.model.ClientProjectResponse;

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

    //Update Client Profile
    @Headers("Content-Type: application/json")
    @POST("client/updateprofile")
    Call<SignUpResponse> updateClient(@Body User user);

    //City List
    @GET("reference/getcities")
    Call<CityResponse> getCities();

    //Package List
    @GET("package/getpackages")
    Call<PackageResponse> getPackages();

    //Get Available Slots
    @GET("slotbooking/availableslots/{book_date}")
    Call<AvailableSlotsResponse> getAvailableSlots(@Path("book_date") String book_date);

    //Book Slot
    @Headers("Content-Type: application/json")
    @POST("slotbooking/bookslot")
    Call<BookSlotResponse> bookSlot(@Body BookSlot bookSlot);

    //Project List
    @GET("slotbooking/getclientprojects/{client_id}")
    Call<ClientProjectResponse> getProjects(@Path("client_id") String client_id);

}