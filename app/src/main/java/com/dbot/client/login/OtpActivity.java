
package com.dbot.client.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dbot.client.R;
import com.dbot.client.common.SessionManager;
import com.dbot.client.databinding.ActivityOtpBinding;
import com.dbot.client.login.model.LoginData;
import com.dbot.client.main.MainActivity;
import com.dbot.client.retrofit.ApiClient;

public class OtpActivity extends AppCompatActivity {
    ActivityOtpBinding binding;
    SessionManager sessionManager;
    Integer Otp;
    String strClientPhone;
    boolean loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);
        loginStatus = getIntent().getBooleanExtra(getString(R.string.TAG_LOGIN_STATUS), false);
        Otp = getIntent().getIntExtra(getString(R.string.TAG_OTP),0);
        strClientPhone = getIntent().getStringExtra(getString(R.string.TAG_CLIENT_PHONE));
        if (ApiClient.isTest)
            binding.etOtp.setText(String.valueOf(Otp));
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etOtp.getText().toString().equals(String.valueOf(Otp)))
                    //Log.d("loginStatus", String.valueOf(loginStatus));
                    if (loginStatus) {
                        LoginData loginData = (LoginData) getIntent().getSerializableExtra(getString(R.string.TAG_CLIENT_DATA));
                        sessionManager.setLogedInClient(loginData.getClientId(),
                                loginData.getFullname(),
                                loginData.getClientPhone(),
                                loginData.getClientEmail(),
                                loginData.getCompanyName());
                        startActivity(new Intent(OtpActivity.this, MainActivity.class));
                    } else {
                        Intent signupIntent = new Intent(OtpActivity.this, SignupActivity.class);
                        signupIntent.putExtra(getString(R.string.TAG_CLIENT_PHONE), strClientPhone);
                        startActivity(signupIntent);
                    }
            }
        });
    }
}