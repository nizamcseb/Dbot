package com.dbot.client.retrofit;


import com.dbot.client.common.city.CityResponse;
import com.dbot.client.login.model.LoginResponse;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;
import com.dbot.client.main.home.model.AvailableSlotsResponse;
import com.dbot.client.main.home.model.NotifySlotRequestResponse;
import com.dbot.client.main.home.model.QuickMessageResponse;
import com.dbot.client.main.home.model.TermsAndConditionsResponse;
import com.dbot.client.main.newrequest.model.ApplyCouponResponse;
import com.dbot.client.main.newrequest.model.AvailableCouponResponse;
import com.dbot.client.main.newrequest.model.BookSlot;
import com.dbot.client.main.newrequest.model.BookSlotResponse;
import com.dbot.client.main.newrequest.model.PackageResponse;
import com.dbot.client.main.profile.faq.model.FAQsResponse;
import com.dbot.client.main.profile.pr.rs.model.RefundResponse;
import com.dbot.client.main.profile.refer.model.RcAndRhResponse;
import com.dbot.client.main.projects.details.model.CancelRequestResponse;
import com.dbot.client.main.projects.details.model.ResheduleResponse;
import com.dbot.client.main.projects.model.ClientProjectResponse;
import com.dbot.client.main.projects.details.model.FileRequestResponse;
import com.dbot.client.main.projects.details.model.ProjectTrackingResponse;
import com.dbot.client.main.projects.details.model.RefundAmountResponse;
import com.dbot.client.main.projects.details.model.UpdateProject;
import com.dbot.client.main.projects.details.model.UpdateProjectResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    //Update Project
    @Headers("Content-Type: application/json")
    @POST("slotbooking/updateproject")
    Call<UpdateProjectResponse> updateProject(@Body UpdateProject updateProject);

    //Project List
    @GET("slotbooking/getclientprojects/{client_id}")
    Call<ClientProjectResponse> getProjects(@Path("client_id") String client_id);

    //Project Tracking
    @GET("slotbooking/getprojecttracking/{booking_id}")
    Call<ProjectTrackingResponse> getProjectTracking(@Path("booking_id") String booking_id);

    //Send File Request
    @GET("message/sendfile/{booking_id}")
    Call<FileRequestResponse> sendFileRequest(@Path("booking_id") String booking_id);

    //Quickmessage
    @Headers("Content-Type: application/json")
    @POST("message/quickmessage")
    Call<QuickMessageResponse> sendQuickMessage(@Query("client_id") String client_id, @Query("message") String message);

    //TC List
    @GET("message/terms")
    Call<TermsAndConditionsResponse> getTC();

    //RcAndRh List
    @GET("client/getmyreferralcodeandhistory/{client_id}")
    Call<RcAndRhResponse> getRcAndRhResponse(@Path("client_id") String client_id);

    //Get Refund Amound Request
    @GET("package/getrefundamount/{booking_id}")
    Call<RefundAmountResponse> getRefundAmount(@Path("booking_id") String booking_id);

    //Cancel Request
    @GET("slotbooking/cancelrequest/{booking_id}")
    Call<CancelRequestResponse> cancelRequest(@Path("booking_id") String booking_id);

    //FAQ List
    @GET("message/getfaqs")
    Call<FAQsResponse> getFAQs();

    //NotifySlot Request
    @Headers("Content-Type: application/json")
    @POST("slotbooking/notifyslotavailablerequest")
    Call<NotifySlotRequestResponse> sendNotifySlotAvailableRequest(@Query("client_id") String client_id, @Query("book_date") String book_date, @Query("slot_time_id") String slot_time_id);

    //Apply Coupon
    @GET("client/applycouponcode/{client_id}/{coupon_code}")
    Call<ApplyCouponResponse> getApplyCoupon(@Path("client_id") String client_id, @Path("coupon_code") String coupon_code);

    //Get Refund List
    @GET("report/refundstatus/{client_id}")
    Call<RefundResponse> getRefundList(@Path("client_id") String client_id);

    @Headers("Content-Type: application/json")
    @POST("slotbooking/reschedule")
    Call<ResheduleResponse> sendReshedule(@Query("booking_id") String booking_id,@Query("client_id") String client_id, @Query("book_date") String book_date, @Query("slot_time_id") Integer slot_time_id);


    //Get Available Coupons
    @GET("client/getavailablecoupons/{client_id}")
    Call<AvailableCouponResponse> getAvailableCoupons(@Path("client_id") String client_id);

}