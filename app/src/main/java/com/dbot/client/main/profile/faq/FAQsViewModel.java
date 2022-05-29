package com.dbot.client.main.profile.faq;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.main.home.model.AvailableSlotsData;
import com.dbot.client.main.home.model.TermsAndConditionsResponse;
import com.dbot.client.main.profile.faq.model.FAQsData;
import com.dbot.client.main.profile.faq.model.FAQsResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQsViewModel extends ViewModel {
    private MutableLiveData<List<FAQsData>> listFAQsMutableLiveData = new MutableLiveData<>();
    public void getFAQsData(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FAQsResponse> call = apiInterface.getFAQs();
        call.enqueue(new Callback<FAQsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<FAQsResponse> call, Response<FAQsResponse> response) {
                if(response.isSuccessful()) {
                    Log.d("getFAQsResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    listFAQsMutableLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<FAQsResponse> call, Throwable t) {

            }
        });
    }
    public LiveData<List<FAQsData>> getFAQsDataResult() {
        return listFAQsMutableLiveData;
    }
}
