package com.dbot.client.login;

import static com.dbot.client.common.CommonFunctions.findCityPosition;

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
import android.widget.RadioButton;
import android.widget.Toast;

import com.dbot.client.R;
import com.dbot.client.common.Popup;
import com.dbot.client.common.SessionManager;
import com.dbot.client.databinding.ActivitySignupBinding;
import com.dbot.client.login.model.CityData;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;

import java.util.List;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivitySignupBinding binding;
    LoginViewModel loginViewModel;
    String deiveId, phoneNumber, name, email, companyName, city;
    boolean loginStatus;
    SessionManager sessionManager;
    List<CityData> cityDataList;

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
                CityAdapter cityAdapter = new CityAdapter(SignupActivity.this, cityDataList);
                binding.spCity.setAdapter(cityAdapter);
                /*if (loginStatus) {
                    int position = findCityPosition(cityDataList,sessionManager.getCity());
                    binding.spCity.setSelection(position);
                }*/
                int position = findCityPosition(cityDataList,sessionManager.getCity());
                binding.spCity.setSelection(position);
                binding.spCity.setOnItemSelectedListener(SignupActivity.this);
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
                    user = new User(sessionManager.getClientId(),name, phoneNumber, email, companyName,"99999","test@test.com", city, Integer.parseInt(radioButton.getTag().toString()), null, null, 0, null);
                    loginViewModel.updateClientProfile(user);
                } else {
                    user = new User(null,name, phoneNumber, email, companyName,"","", city, Integer.parseInt(radioButton.getTag().toString()), deiveId, "ssss", 1, Build.MODEL);
                    loginViewModel.signUp(user);
                }


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(SignupActivity.this,cityDataList.get(i).getId(),Toast.LENGTH_SHORT).show();
        if (cityDataList.get(i).getWorkingCity().equals("1"))
            city = String.valueOf(cityDataList.get(i).getId());
        else {
            Popup popup = new Popup();
            popup.showCityNotAvailablePopupWindow(binding.spCity);
            city = String.valueOf(cityDataList.get(i).getId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}