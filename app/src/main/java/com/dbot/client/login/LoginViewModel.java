package com.dbot.client.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbot.client.login.city.CityData;
import com.dbot.client.login.city.CityResponse;
import com.dbot.client.login.model.LoginResponse;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;
import com.dbot.client.retrofit.ApiClient;
import com.dbot.client.retrofit.ApiInterface;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginResponse> loginMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<SignUpResponse> signUpMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CityData>> cityMutableLiveData = new MutableLiveData<>();


    public void  loginGetOtp(String mobileNumber) {
        System.out.println("mobileNumber " + mobileNumber);
        //loginMutableLiveData = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.getOtp(mobileNumber);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               Log.d("getOtpResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
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
    public void updateClientProfile(User user){
        Log.d("SignupInputdata",new GsonBuilder().setPrettyPrinting().create().toJson(user));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SignUpResponse> call = apiInterface.updateClient(user);
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
    public void getCityData(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CityResponse> call = apiInterface.getCities();
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                if(response.isSuccessful()) {
                    //Log.d("getCityResponse", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    cityMutableLiveData.setValue(response.body().getCityListData());
                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<LoginResponse> getLoginResult() {
        return loginMutableLiveData;
    }
    public LiveData<SignUpResponse> getSignUpResult() {
        return signUpMutableLiveData;
    }
    public LiveData<List<CityData>> getCityResult() {
        return cityMutableLiveData;
    }
}
