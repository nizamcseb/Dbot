package com.dbot.client.main.projects.details;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.main.projects.details.model.FileRequestResponse;
import com.dbot.client.main.projects.details.model.ProjectTrackingResponse;
import com.dbot.client.main.projects.details.model.ResheduleResponse;
import com.dbot.client.main.projects.details.model.UpdateProject;
import com.dbot.client.main.projects.details.model.UpdateProjectResponse;
import com.dbot.client.main.projects.model.ClientProjectData;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectFullDetailsViewlModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ClientProjectData> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ProjectTrackingResponse> projectTrackingResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UpdateProjectResponse> updateProjectResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<FileRequestResponse> fileRequestResponseMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResheduleResponse> resheduleResponseMutableLiveData = new MutableLiveData<>();


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
                    if (response.body() != null)
                        projectTrackingResponseMutableLiveData.setValue(response.body());

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ProjectTrackingResponse> call, Throwable t) {
                Log.e("ProjectTrackingResponse error", t.getMessage());
                projectTrackingResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void updateProject(UpdateProject updateProject) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UpdateProjectResponse> call = apiInterface.updateProject(updateProject);
        call.enqueue(new Callback<UpdateProjectResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<UpdateProjectResponse> call, Response<UpdateProjectResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("UpdateProjectResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response.body() != null)
                        updateProjectResponseMutableLiveData.setValue(response.body());

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<UpdateProjectResponse> call, Throwable t) {
                Log.e("updateProject error", t.getMessage());
                updateProjectResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void sendFileRequest(String booking_id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FileRequestResponse> call = apiInterface.sendFileRequest(booking_id);
        call.enqueue(new Callback<FileRequestResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<FileRequestResponse> call, Response<FileRequestResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("FileRequestResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response.body() != null)
                        fileRequestResponseMutableLiveData.setValue(response.body());

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<FileRequestResponse> call, Throwable t) {
                Log.e("ProjectTrackingResponse error", t.getMessage());
                fileRequestResponseMutableLiveData.setValue(null);
            }
        });
    }

    public void resheduleSlot(String booking_id, String client_id, String book_date, Integer slot_time_id) {
        Log.d("booking_id", booking_id);
        Log.d("client_id", client_id);
        Log.d("book_date", book_date);
        Log.d("slot_time_id", String.valueOf(slot_time_id));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResheduleResponse> call = apiInterface.sendReshedule(booking_id, client_id, book_date, slot_time_id);
        call.enqueue(new Callback<ResheduleResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ResheduleResponse> call, Response<ResheduleResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("ResheduleResponseResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    resheduleResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResheduleResponse> call, Throwable t) {
                resheduleResponseMutableLiveData.setValue(null);
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public LiveData<ClientProjectData> getProjectResult() {
        return listMutableLiveData;
    }

    public LiveData<ProjectTrackingResponse> getProjectTrackingResult() {
        return projectTrackingResponseMutableLiveData;
    }

    public LiveData<UpdateProjectResponse> getUpdateProjectResult() {
        return updateProjectResponseMutableLiveData;
    }

    public LiveData<FileRequestResponse> getFileRequestResult() {
        return fileRequestResponseMutableLiveData;
    }

    public LiveData<ResheduleResponse> getResheduleResponseResult() {
        return resheduleResponseMutableLiveData;
    }
}
