package com.dbot.client.login;

import static com.dbot.client.common.CommonFunctions.findCityPosition;
import static com.dbot.client.common.Popup.SearchCity;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.common.Popup;
import com.dbot.client.common.SessionManager;
import com.dbot.client.common.city.SaveCity;
import com.dbot.client.databinding.ActivitySignupBinding;
import com.dbot.client.common.city.CityAdapter;
import com.dbot.client.common.city.CityData;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;
import com.google.gson.GsonBuilder;

import java.util.List;

public class SignupActivity extends AppCompatActivity implements SaveCity {

    ActivitySignupBinding binding;
    LoginViewModel loginViewModel;
    String deiveId, phoneNumber, name, email, companyName, city;
    boolean loginStatus;
    SessionManager sessionManager;
    List<CityData> cityDataList;
    CityAdapter cityAdapter;
    SaveCity saveCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        deiveId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        phoneNumber = getIntent().getStringExtra(getString(R.string.TAG_CLIENT_PHONE));
        loginStatus = getIntent().getBooleanExtra(getString(R.string.TAG_LOGIN_STATUS), false);
        sessionManager = new SessionManager(this);
        if (loginStatus) {
            binding.etFullName.setText(sessionManager.getClientFullName());
            binding.etEmail.setText(sessionManager.getClientEmailId());
            binding.etCompanyName.setText(sessionManager.getClientCompanyName());
            if (sessionManager.getFreeLancer() == 1)
                binding.rgFreelancer.check(binding.rbFreelancerYes.getId());
            else binding.rgFreelancer.check(binding.rbFreelancerNo.getId());

        }

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getCityData();
        loginViewModel.getCityResult().observe(this, new Observer<List<CityData>>() {
            @Override
            public void onChanged(List<CityData> cityData) {
                cityDataList = cityData;

                //binding.spCity.setAdapter(cityAdapter);
                /*if (loginStatus) {
                    int position = findCityPosition(cityDataList,sessionManager.getCity());
                    binding.spCity.setSelection(position);
                }*/
                int position = findCityPosition(cityDataList, sessionManager.getCity());
                binding.tvCitySearch.setText(cityDataList.get(position).getCityName());
                //binding.spCity.setSelection(position);
                // binding.spCity.setOnItemSelectedListener(SignupActivity.this);
            }
        });
        loginViewModel.getSignUpResult().observe(this, new Observer<SignUpResponse>() {
            @Override
            public void onChanged(SignUpResponse signUpResponse) {
                if (signUpResponse != null) {
                    Toast.makeText(SignupActivity.this, signUpResponse.getStatus().getMessage(), Toast.LENGTH_LONG).show();
                    if (signUpResponse.getStatus().getCode() == 1012 || signUpResponse.getStatus().getCode() == 1027)
                        sessionManager.setLogedInClient(signUpResponse.getClientData().getClientId(),
                                signUpResponse.getClientData().getFullname(),
                                signUpResponse.getClientData().getClientPhone(),
                                signUpResponse.getClientData().getClientEmail(),
                                signUpResponse.getClientData().getCompanyName(),
                                signUpResponse.getClientData().getCompanyPhone(),
                                signUpResponse.getClientData().getCompanyEmail(),
                                signUpResponse.getClientData().getCity(),
                                signUpResponse.getClientData().getFreelancer());
                }

            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = binding.etFullName.getText().toString();
                email = binding.etEmail.getText().toString();
                companyName = binding.etCompanyName.getText().toString();
                int selectedId = binding.rgFreelancer.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                User user;
                if (loginStatus) {
                    user = new User(sessionManager.getClientId(), name, phoneNumber, email, companyName, "99999", "test@test.com", city, Integer.parseInt(radioButton.getTag().toString()), null, null, 0, null);
                    loginViewModel.updateClientProfile(user);
                } else {
                    user = new User(null, name, phoneNumber, email, companyName, "", "", city, Integer.parseInt(radioButton.getTag().toString()), deiveId, "ssss", 1, Build.MODEL);
                    loginViewModel.signUp(user);
                }


            }
        });
        binding.tvCitySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCity = SignupActivity.this;
                SearchCity(SignupActivity.this,cityDataList,saveCity);
            }
        });

    }


    @Override
    public void cityData(CityData cityData) {
        //CityData cityData = (CityData) parent.getItemAtPosition(position);
        Log.d("cityData", new GsonBuilder().setPrettyPrinting().create().toJson(cityData));
        if (!cityData.getWorkingCity().equals("1")) {
            Popup popup = new Popup();
            popup.showCityNotAvailablePopupWindow(binding.tvCitySearch);
        }
        //city = String.valueOf(cityData.getId());
        binding.tvCitySearch.setText(cityData.getCityName());
        city = cityData.getId();
    }
}