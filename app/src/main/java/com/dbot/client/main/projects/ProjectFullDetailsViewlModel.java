package com.dbot.client.main.projects;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.main.profile.faq.model.FAQsData;
import com.dbot.client.main.projects.model.ClientProjectData;
import com.dbot.client.main.projects.model.ClientProjectResponse;
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


    public LiveData<ClientProjectData> getProject(ClientProjectData clientProjectData) {
        Log.d("projectDataList", new GsonBuilder().setPrettyPrinting().create().toJson(clientProjectData.getProjectName()));
        listMutableLiveData.setValue(clientProjectData);

        return listMutableLiveData;
    }
    public LiveData<ClientProjectData> getProjectResult() {
        return listMutableLiveData;
    }
}
