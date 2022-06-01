package com.dbot.client.main.profile.refer;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.main.profile.refer.model.RcAndRhResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferViewModel extends ViewModel {
    private MutableLiveData<RcAndRhResponse> rcAndRhResponseMutableLiveData = new MutableLiveData<>();

    public void geRcAndRhCData(String client_id){
        Log.d("geRcAndRhCData ", client_id);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RcAndRhResponse> call = apiInterface.getRcAndRhResponse(client_id);
        call.enqueue(new Callback<RcAndRhResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<RcAndRhResponse> call, Response<RcAndRhResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("getRcAndRhResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    rcAndRhResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RcAndRhResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }
    public LiveData<RcAndRhResponse> getRcAndRhResponseResult() {
        return rcAndRhResponseMutableLiveData;
    }
}
