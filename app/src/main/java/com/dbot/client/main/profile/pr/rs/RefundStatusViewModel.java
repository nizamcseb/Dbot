package com.dbot.client.main.profile.pr.rs;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.main.profile.pr.rs.model.RefundResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefundStatusViewModel extends ViewModel {
    MutableLiveData<RefundResponse> refundResponseMutableLiveData = new MutableLiveData<>();

    public void getRefundDataList(String client_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RefundResponse> call = apiInterface.getRefundList(client_id);
        call.enqueue(new Callback<RefundResponse>() {
            @Override
            public void onResponse(Call<RefundResponse> call, Response<RefundResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("getRefundResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    refundResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RefundResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<RefundResponse> getRefundResponseResult() {
        return refundResponseMutableLiveData;
    }
}
