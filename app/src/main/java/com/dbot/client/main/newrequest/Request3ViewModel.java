package com.dbot.client.main.newrequest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.main.newrequest.model.ApplyCouponResponse;
import com.dbot.client.main.newrequest.model.AvailableCoupon;
import com.dbot.client.main.newrequest.model.AvailableCouponResponse;
import com.dbot.client.main.newrequest.model.BookSlot;
import com.dbot.client.main.newrequest.model.BookSlotResponse;
import com.dbot.client.main.newrequest.model.PackageData;
import com.dbot.client.main.newrequest.model.PackageResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Request3ViewModel extends ViewModel {
    private MutableLiveData<List<PackageData>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<AvailableCoupon>> availableCouponMutableData = new MutableLiveData<>();
    private MutableLiveData<BookSlotResponse> bookSlotResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ApplyCouponResponse> applyCouponResponseMutableLiveData = new MutableLiveData<>();
    public void getPackages(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<PackageResponse> call = apiInterface.getPackages();
        call.enqueue(new Callback<PackageResponse>() {
            @Override
            public void onResponse(Call<PackageResponse> call, Response<PackageResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("getPackagesResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    mutableLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<PackageResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }
    public void getAvailableCoupons(String client_id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AvailableCouponResponse> call = apiInterface.getAvailableCoupons(client_id);
        call.enqueue(new Callback<AvailableCouponResponse>() {
            @Override
            public void onResponse(Call<AvailableCouponResponse> call, Response<AvailableCouponResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("getAvailableCouponResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    availableCouponMutableData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<AvailableCouponResponse> call, Throwable t) {
                availableCouponMutableData.setValue(null);
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }
    public void bookSlot(BookSlot bookSlot){
        Log.d("bookSlotInputdata",new GsonBuilder().setPrettyPrinting().create().toJson(bookSlot));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<BookSlotResponse> call = apiInterface.bookSlot(bookSlot);
        call.enqueue(new Callback<BookSlotResponse>() {
            @Override
            public void onResponse(Call<BookSlotResponse> call, Response<BookSlotResponse> response) {
                Log.d("BookSlotResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                bookSlotResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<BookSlotResponse> call, Throwable t) {
                bookSlotResponseMutableLiveData.setValue(null);
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }
    public void applyCoupon(String client_id,String coupon_code){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApplyCouponResponse> call = apiInterface.getApplyCoupon(client_id,coupon_code);
        call.enqueue(new Callback<ApplyCouponResponse>() {
            @Override
            public void onResponse(Call<ApplyCouponResponse> call, Response<ApplyCouponResponse> response) {
                Log.d("ApplyCouponResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                applyCouponResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ApplyCouponResponse> call, Throwable t) {
                applyCouponResponseMutableLiveData.setValue(null);
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }
    public LiveData<List<PackageData>> getPackagesResult() {
        return mutableLiveData;
    }
    public LiveData<List<AvailableCoupon>> getAvailableCouponResult() {
        return availableCouponMutableData;
    }
    public LiveData<BookSlotResponse> getBookSlotResult() {
        return bookSlotResponseMutableLiveData;
    }
    public LiveData<ApplyCouponResponse> getApplyCouponResult() {
        return applyCouponResponseMutableLiveData;
    }

}
