package com.dbot.client.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.login.model.Client;
import com.dbot.client.login.model.Login;
import com.dbot.client.login.model.SignUp;
import com.dbot.client.login.model.User;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Login> loginMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<SignUp> signUpMutableLiveData = new MutableLiveData<>();


    public void loginGetOtp(String mobileNumber) {
        System.out.println("mobileNumber " + mobileNumber);
        //loginMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Login> call = apiInterface.getOtp(mobileNumber);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
               // Log.d("getOtpResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                loginMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public void signUp(User user){
        Log.d("SignupInputdata",new GsonBuilder().setPrettyPrinting().create().toJson(user));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SignUp> call = apiInterface.registerClient(user);
        call.enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                Log.d("getSignupResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                signUpMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Log.e("response ERROR= ", "" + t.getMessage() + " " + t.getLocalizedMessage());
            }
        });
    }

    public LiveData<Login> getLoginResult() {
        return loginMutableLiveData;
    }
    public LiveData<SignUp> getSignUpResult() {
        return signUpMutableLiveData;
    }
}
