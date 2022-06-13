package com.dbot.client.main.projects;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.main.projects.model.ClientProjectData;
import com.dbot.client.main.projects.model.ClientProjectResponse;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<ClientProjectData>> listMutableLiveData = new MutableLiveData<>();


    public void getProjects(String client_id) {
        listMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ClientProjectResponse> call = apiInterface.getProjects(client_id);
        call.enqueue(new Callback<ClientProjectResponse>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<ClientProjectResponse> call, Response<ClientProjectResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("ClientProjectResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response.body() !=null)
                        listMutableLiveData.setValue(response.body().getData());

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ClientProjectResponse> call, Throwable t) {
                Log.e("ClientProjectResponse error",t.getMessage());
                listMutableLiveData.setValue(null);
            }
        });
    }
    public LiveData<List<ClientProjectData>> getProjectResult() {
        return listMutableLiveData;
    }
}