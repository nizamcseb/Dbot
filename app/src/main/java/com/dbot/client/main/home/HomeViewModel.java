package com.dbot.client.main.home;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.login.model.CityData;
import com.dbot.client.login.model.LoginResponse;
import com.dbot.client.main.home.model.AvailableSlotsData;
import com.dbot.client.main.home.model.AvailableSlotsResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<AvailableSlotsData>> availableSlotsData = new MutableLiveData<>();

    public void getAvailableSlots(String book_date) {
        System.out.println("book_date " + book_date);
        //loginMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AvailableSlotsResponse> call = apiInterface.getAvailableSlots(book_date);
        call.enqueue(new Callback<AvailableSlotsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<AvailableSlotsResponse> call, Response<AvailableSlotsResponse> response) {
                if(response.isSuccessful()) {

                    availableSlotsData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<AvailableSlotsResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }
    public LiveData<List<AvailableSlotsData>> getAvailableSlotsResult() {
        return availableSlotsData;
    }
}