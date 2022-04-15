package com.dbot.client.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.dbot.client.R;
import com.dbot.client.databinding.ActivityLoginBinding;
import com.dbot.client.login.model.LoginResponse;
import com.dbot.client.retrofit.ApiClient;
import com.google.gson.GsonBuilder;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;
    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (ApiClient.isTest)
            binding.etMobileNumber.setText("9994471706");
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                Log.d("getOtpResponse", new GsonBuilder().setPrettyPrinting().create().toJson(loginResponse));
                if (loginResponse != null) {

                    Log.d("loginStatus", String.valueOf(loginResponse.getLoginStatus()));
                    Intent otpIntent = new Intent(LoginActivity.this, OtpActivity.class);
                    otpIntent.putExtra(getString(R.string.TAG_CLIENT_PHONE), mobileNumber);
                    otpIntent.putExtra(getString(R.string.TAG_OTP), loginResponse.getOtp());
                    otpIntent.putExtra(getString(R.string.TAG_LOGIN_STATUS), loginResponse.getLoginStatus());
                    if(loginResponse.getLoginStatus())
                        otpIntent.putExtra(getString(R.string.TAG_CLIENT_DATA),loginResponse.getLoginData());
                    startActivity(otpIntent);

                }
            }
        });
        binding.btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileNumber = binding.etMobileNumber.getText().toString();
                loginViewModel.loginGetOtp(mobileNumber);
            }
        });
    }
}