package com.dbot.client.main.newrequest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.login.model.CityData;
import com.dbot.client.login.model.CityResponse;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;
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
    private MutableLiveData<BookSlotResponse> bookSlotResponseMutableLiveData = new MutableLiveData<>();
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
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }
    public LiveData<List<PackageData>> getPackagesResult() {
        return mutableLiveData;
    }
    public LiveData<BookSlotResponse> getBookSlotResult() {
        return bookSlotResponseMutableLiveData;
    }
}
