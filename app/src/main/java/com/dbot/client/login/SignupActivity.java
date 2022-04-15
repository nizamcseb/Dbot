package com.dbot.client.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.dbot.client.R;
import com.dbot.client.databinding.ActivitySignupBinding;
import com.dbot.client.login.model.Client;
import com.dbot.client.login.model.SignUp;
import com.dbot.client.login.model.User;
import com.dbot.client.main.MainActivity;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    LoginViewModel loginViewModel;
    String deiveId,phoneNumber,name,email,companyName,city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        deiveId = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        phoneNumber = getIntent().getStringExtra(getString(R.string.tag_client_phone));
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getSignUpResult().observe(this, new Observer<SignUp>() {
            @Override
            public void onChanged(SignUp signUp) {
                if (signUp != null) {
                    Toast.makeText(SignupActivity.this, signUp.getStatus().getMessage(), Toast.LENGTH_LONG).show();
                    if (signUp.getStatus().getCode() == 1012)
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
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
                User user = new User(name,phoneNumber,email,companyName,city,0,deiveId,"ssss",1,Build.MODEL);
               /* Client client = new Client();
                client.setClientPhone(phoneNumber);
                client.setFullname(name);
                client.setClientEmail(email);
                client.setFreelancer(0);
                client.setCompanyName(companyName);
                client.setCity(city);
                client.setDeviceid(deiveId);
                client.setDevice_model(Build.MODEL);
                client.setOs_type(1);
                client.setNotify_token("asdfasdfsdafsdfsd");*/
                loginViewModel.signUp(user);
            }
        });
    }
}