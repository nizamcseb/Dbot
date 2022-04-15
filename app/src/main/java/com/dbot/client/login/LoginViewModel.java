package com.dbot.client.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.login.model.LoginResponse;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> loginMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<SignUpResponse> signUpMutableLiveData = new MutableLiveData<>();


    public void loginGetOtp(String mobileNumber) {
        System.out.println("mobileNumber " + mobileNumber);
        //loginMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.getOtp(mobileNumber);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               // Log.d("getOtpResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                loginMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public void signUp(User user){
        Log.d("SignupInputdata",new GsonBuilder().setPrettyPrinting().create().toJson(user));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SignUpResponse> call = apiInterface.registerClient(user);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.d("getSignupResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                signUpMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public LiveData<LoginResponse> getLoginResult() {
        return loginMutableLiveData;
    }
    public LiveData<SignUpResponse> getSignUpResult() {
        return signUpMutableLiveData;
    }
}
