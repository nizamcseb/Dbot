package com.dbot.client.main.projects;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.main.profile.faq.model.FAQsData;
import com.dbot.client.main.projects.model.ClientProjectData;
import com.dbot.client.main.projects.model.ClientProjectResponse;
import com.dbot.client.main.projects.model.ProjectTrackingResponse;
import com.dbot.client.main.projects.model.RefundAmountResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectFullDetailsViewlModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ClientProjectData> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ProjectTrackingResponse> projectTrackingResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<RefundAmountResponse> refundAmountResponseMutableLiveData = new MutableLiveData<>();


    public LiveData<ClientProjectData> getProject(ClientProjectData clientProjectData) {
        Log.d("projectDataList", new GsonBuilder().setPrettyPrinting().create().toJson(clientProjectData.getProjectName()));
        listMutableLiveData.setValue(clientProjectData);

        return listMutableLiveData;
    }

    public void getProjectTracking(String booking_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ProjectTrackingResponse> call = apiInterface.getProjectTracking(booking_id);
        call.enqueue(new Callback<ProjectTrackingResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ProjectTrackingResponse> call, Response<ProjectTrackingResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("ProjectTrackingResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response.body() !=null)
                        projectTrackingResponseMutableLiveData.setValue(response.body());

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ProjectTrackingResponse> call, Throwable t) {
                Log.e("ProjectTrackingResponse error",t.getMessage());
                projectTrackingResponseMutableLiveData.setValue(null);
            }
        });
    }
    public void getRefundAmound(String booking_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RefundAmountResponse> call = apiInterface.getRefundAmount(booking_id);
        call.enqueue(new Callback<RefundAmountResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<RefundAmountResponse> call, Response<RefundAmountResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("RefundAmountResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response.body() !=null)
                        refundAmountResponseMutableLiveData.setValue(response.body());

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<RefundAmountResponse> call, Throwable t) {
                Log.e("RefundAmountResponse error",t.getMessage());
                refundAmountResponseMutableLiveData.setValue(null);
            }
        });
    }
    public LiveData<ClientProjectData> getProjectResult() {
        return listMutableLiveData;
    }
    public LiveData<ProjectTrackingResponse> getProjectTrackingResult() {
        return projectTrackingResponseMutableLiveData;
    }
    public LiveData<RefundAmountResponse> getRefundAmoundResult() {
        return refundAmountResponseMutableLiveData;
    }
}
