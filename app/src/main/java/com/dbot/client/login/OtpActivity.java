
package com.dbot.client.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dbot.client.R;
import com.dbot.client.databinding.ActivityOtpBinding;
import com.dbot.client.main.MainActivity;
import com.dbot.client.retrofit.ApiClient;

public class OtpActivity extends AppCompatActivity {
    ActivityOtpBinding binding;
    Integer Otp;
    String strClientPhone;
    boolean loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginStatus = getIntent().getBooleanExtra(getString(R.string.tag_login_stauts), false);
        Otp = getIntent().getIntExtra(getString(R.string.tag_otp),0);
        strClientPhone = getIntent().getStringExtra(getString(R.string.tag_client_phone));
        if (ApiClient.isTest)
            binding.etOtp.setText(String.valueOf(Otp));
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etOtp.getText().toString().equals(String.valueOf(Otp)))
                    //Log.d("loginStatus", String.valueOf(loginStatus));
                    if (loginStatus) {
                        startActivity(new Intent(OtpActivity.this, MainActivity.class));
                    } else {
                        Intent signupIntent = new Intent(OtpActivity.this, SignupActivity.class);
                        signupIntent.putExtra(getString(R.string.tag_client_phone), strClientPhone);
                        startActivity(signupIntent);
                    }
            }
        });
    }
}