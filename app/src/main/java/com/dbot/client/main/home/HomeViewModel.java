package com.dbot.client.main.home;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.common.calendar.AvailableDate;
import com.dbot.client.common.calendar.AvailableDatesResponse;
import com.dbot.client.main.home.model.AvailableSlotsData;
import com.dbot.client.main.home.model.AvailableSlotsResponse;
import com.dbot.client.main.home.model.NotifySlotRequestResponse;
import com.dbot.client.main.home.model.QuickMessageResponse;
import com.dbot.client.main.home.model.TermsAndConditionsResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.dbot.client.retrofit.Status;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<AvailableSlotsData>> availableSlotsData = new MutableLiveData<>();
    private MutableLiveData<List<AvailableDate>> availablDates = new MutableLiveData<>();
    private MutableLiveData<Status> statusMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<TermsAndConditionsResponse> termsAndConditionsResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<NotifySlotRequestResponse> notifySlotRequestResponseMutableLiveData = new MutableLiveData<>();

    public void getAvailableDates(int month, int year) {
        System.out.println("month_year " + String.valueOf(month) + " " + String.valueOf(year));
        //loginMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AvailableDatesResponse> call = apiInterface.getAvailableDates(month, year);
        call.enqueue(new Callback<AvailableDatesResponse>() {
            @Override
            public void onResponse(Call<AvailableDatesResponse> call, Response<AvailableDatesResponse> response) {
                availablDates.setValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<AvailableDatesResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("availablDates response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });

    }

    public void getAvailableSlots(String book_date) {
        System.out.println("book_date " + book_date);
        //loginMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AvailableSlotsResponse> call = apiInterface.getAvailableSlots(book_date);
        call.enqueue(new Callback<AvailableSlotsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<AvailableSlotsResponse> call, Response<AvailableSlotsResponse> response) {
                if (response.isSuccessful()) {

                    availableSlotsData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<AvailableSlotsResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("availableSlotsData response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public void sendMessage(String client_id, String message) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<QuickMessageResponse> call = apiInterface.sendQuickMessage(client_id, message);
        call.enqueue(new Callback<QuickMessageResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<QuickMessageResponse> call, Response<QuickMessageResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("sendMessageResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    statusMutableLiveData.setValue(response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<QuickMessageResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public void getTCData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TermsAndConditionsResponse> call = apiInterface.getTC();
        call.enqueue(new Callback<TermsAndConditionsResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<TermsAndConditionsResponse> call, Response<TermsAndConditionsResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("getTermsAndConditionsResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    termsAndConditionsResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TermsAndConditionsResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public void sendNotifySlotAvailableRequest(String client_id, String book_date, String slot_time_id) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NotifySlotRequestResponse> call = apiInterface.sendNotifySlotAvailableRequest(client_id, book_date, slot_time_id);
        call.enqueue(new Callback<NotifySlotRequestResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<NotifySlotRequestResponse> call, Response<NotifySlotRequestResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("NotifySlotRequestResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    notifySlotRequestResponseMutableLiveData.setValue(response.body());
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<NotifySlotRequestResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("NotifySlotRequestResponse ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public LiveData<List<AvailableDate>> getAvailableDatesResult() {
        return availablDates;
    }

    public LiveData<List<AvailableSlotsData>> getAvailableSlotsResult() {
        return availableSlotsData;
    }

    public LiveData<Status> getQuickMessageResult() {
        return statusMutableLiveData;
    }

    public LiveData<TermsAndConditionsResponse> getTCResult() {
        return termsAndConditionsResponseMutableLiveData;
    }

    public LiveData<NotifySlotRequestResponse> getNotifySlotAvailableRequestResult() {
        return notifySlotRequestResponseMutableLiveData;
    }
}