package com.dbot.client.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dbot.client.R;
import com.dbot.client.common.SessionManager;
import com.dbot.client.databinding.ActivitySignupBinding;
import com.dbot.client.login.model.CityData;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;

import java.util.List;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    LoginViewModel loginViewModel;
    String deiveId, phoneNumber, name, email, companyName, city;
    SessionManager sessionManager;
    List<CityData> cityDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        deiveId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        phoneNumber = getIntent().getStringExtra(getString(R.string.TAG_CLIENT_PHONE));
        sessionManager = new SessionManager(this);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getCityData();
        loginViewModel.getCityResult().observe(this, new Observer<List<CityData>>() {
            @Override
            public void onChanged(List<CityData> cityData) {
                cityDataList = cityData;
                CityAdapter cityAdapter = new CityAdapter(SignupActivity.this, cityDataList);
                binding.spCity.setAdapter(cityAdapter);
            }
        });
        loginViewModel.getSignUpResult().observe(this, new Observer<SignUpResponse>() {
            @Override
            public void onChanged(SignUpResponse signUpResponse) {
                if (signUpResponse != null) {
                    Toast.makeText(SignupActivity.this, signUpResponse.getStatus().getMessage(), Toast.LENGTH_LONG).show();
                    if (signUpResponse.getStatus().getCode() == 1012)
                        sessionManager.setLogedInClient(signUpResponse.getClientData().getClientId(),
                                signUpResponse.getClientData().getFullname(),
                                signUpResponse.getClientData().getClientPhone(),
                                signUpResponse.getClientData().getClientEmail(),
                                signUpResponse.getClientData().getCompanyName());
                }

            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = binding.etFullName.getText().toString();
                email = binding.etEmail.getText().toString();
                companyName = binding.etCompanyName.getText().toString();
                city = "Chennai";
                User user = new User(name, phoneNumber, email, companyName, city, 0, deiveId, "ssss", 1, Build.MODEL);
                loginViewModel.signUp(user);
            }
        });
    }
}