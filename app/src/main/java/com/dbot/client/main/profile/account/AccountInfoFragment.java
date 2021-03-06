package com.dbot.client.main.profile.account;

import static com.dbot.client.common.CommonFunctions.checkEmptyValidatation;
import static com.dbot.client.common.CommonFunctions.findCityPosition;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.common.SessionManager;
import com.dbot.client.databinding.FragmentAccountInfoBinding;
import com.dbot.client.login.LoginViewModel;
import com.dbot.client.login.OtpEditText;
import com.dbot.client.common.city.CityAdapter;
import com.dbot.client.common.city.CityData;
import com.dbot.client.login.model.LoginResponse;
import com.dbot.client.login.model.SignUpResponse;
import com.dbot.client.login.model.User;
import com.dbot.client.main.profile.ProfileFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AccountInfoFragment extends Fragment {

    private AccountInfoViewModel mViewModel;
    private LoginViewModel loginViewModel;
    FragmentAccountInfoBinding binding;
    SessionManager sessionManager;
    List<CityData> cityDataList;
    String city;
    View root;
    User user;
    boolean isOtpVerified = false;

    public static AccountInfoFragment newInstance() {
        return new AccountInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountInfoBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sessionManager = new SessionManager(getContext());
        mViewModel = new ViewModelProvider(this).get(AccountInfoViewModel.class);
        // TODO: Use the ViewModel
        binding.etProfileFullName.setText(sessionManager.getClientFullName());
        binding.etProfilePhone.setText(sessionManager.getClientPhone());
        binding.etProfileEmail.setText(sessionManager.getClientEmailId());
        binding.etProfileCompanyName.setText(sessionManager.getClientCompanyName());
        if (sessionManager.getClientCompanyPhone() != null)
            binding.etProfileCompanyPhoneNumber.setText(sessionManager.getClientCompanyPhone());
        if (sessionManager.getClientCompanyEmail() != null)
            binding.etProfileCompanyEmailAddress.setText(sessionManager.getClientCompanyEmail());
        if (sessionManager.getFreeLancer() == 1) {
            binding.rgFreelancer.check(binding.rbFreelancerYes.getId());
            stars(true);
        } else {
            binding.rgFreelancer.check(binding.rbFreelancerNo.getId());
        }
        binding.rgFreelancer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) root.findViewById(id);
                if (radioButton.getTag().toString().equals("1"))
                    stars(true);
                if (radioButton.getTag().toString().equals("0"))
                    stars(false);

            }
        });
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getCityData();
        loginViewModel.getCityResult().observe(getViewLifecycleOwner(), new Observer<List<CityData>>() {
            @Override
            public void onChanged(List<CityData> cityData) {
                cityDataList = cityData;
                CityAdapter cityAdapter = new CityAdapter(getActivity(), getContext(), cityDataList);
                binding.spProfileCity.setAdapter(cityAdapter);
                int position = findCityPosition(cityDataList, sessionManager.getCity());
                binding.spProfileCity.setSelection(position);

                binding.spProfileCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        city = String.valueOf(cityDataList.get(i).getId());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });
        binding.btnProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etProfileFullName.getText().toString();
                String phone = binding.etProfilePhone.getText().toString();
                String email = binding.etProfileEmail.getText().toString();
                String cName = binding.etProfileCompanyName.getText().toString();
                String cPhone = binding.etProfileCompanyPhoneNumber.getText().toString();
                String cEmail = binding.etProfileCompanyEmailAddress.getText().toString();
                int selectedId = binding.rgFreelancer.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) root.findViewById(selectedId);
                if (!checkEmptyValidatation(binding.etProfileFullName))
                    binding.etProfileFullName.setError("Required");
                else if (!checkEmptyValidatation(binding.etProfilePhone))
                    binding.etProfilePhone.setError("Required");
                else if (!checkEmptyValidatation(binding.etProfileEmail))
                    binding.etProfileEmail.setError("Required");
                else if (!checkEmptyValidatation(binding.etProfileCompanyName) && radioButton.getTag().toString().equals("0")) {

                    binding.etProfileCompanyName.setError("Required");
                } else if (!checkEmptyValidatation(binding.etProfileCompanyPhoneNumber) && radioButton.getTag().toString().equals("0")) {

                    binding.etProfileCompanyPhoneNumber.setError("Required");
                } else if (!checkEmptyValidatation(binding.etProfileCompanyEmailAddress) && radioButton.getTag().toString().equals("0")) {

                    binding.etProfileCompanyEmailAddress.setError("Required");
                } else {

                    user = new User(sessionManager.getClientId(),
                            name, phone, email, cName, cPhone, cEmail, city, Integer.parseInt(radioButton.getTag().toString()), null, null, 0, null);
                    if (sessionManager.getClientPhone().equals(phone))
                        loginViewModel.updateClientProfile(user);
                    else {
                        loginViewModel.loginGetOtp(phone);
                    }
                }
            }
        });
        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                otpVerificationPopup(getView(), loginResponse);
            }
        });
        loginViewModel.getSignUpResult().observe(getViewLifecycleOwner(), new Observer<SignUpResponse>() {
            @Override
            public void onChanged(SignUpResponse signUpResponse) {
                if (signUpResponse.getStatus().getCode() == 1027) {
                    sessionManager.updateClient(signUpResponse.getClientData().getClientId(),
                            signUpResponse.getClientData().getFullname(),
                            signUpResponse.getClientData().getClientPhone(),
                            signUpResponse.getClientData().getClientEmail(),
                            signUpResponse.getClientData().getCompanyName(),
                            signUpResponse.getClientData().getCompanyPhone(),
                            signUpResponse.getClientData().getCompanyEmail(),
                            signUpResponse.getClientData().getCity(),
                            signUpResponse.getClientData().getFreelancer());
                    Log.d("update ", signUpResponse.getStatus().getMessage());
                    Snackbar.make(binding.btnProfileSave, signUpResponse.getStatus().getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        binding.ivBackAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, profileFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void stars(boolean hide) {
        if (hide) {
            binding.tvStarProfileCompanyName.setVisibility(View.INVISIBLE);
            binding.tvStarProfileCompanyPhoneNumber.setVisibility(View.INVISIBLE);
            binding.tvStarProfileCompanyEmailAddress.setVisibility(View.INVISIBLE);
        } else {
            binding.tvStarProfileCompanyName.setVisibility(View.VISIBLE);
            binding.tvStarProfileCompanyPhoneNumber.setVisibility(View.VISIBLE);
            binding.tvStarProfileCompanyEmailAddress.setVisibility(View.VISIBLE);
        }
    }

    private void otpVerificationPopup(View view, LoginResponse loginResponse) {

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_otp, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        OtpEditText otpEditText = popupView.findViewById(R.id.et_popup_otp);
        Button btn_popup_continue = popupView.findViewById(R.id.btn_popup_continue);
        btn_popup_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpEditText.getText().toString().equals(String.valueOf(loginResponse.getOtp()))) {
                    isOtpVerified = true;
                    loginViewModel.updateClientProfile(user);
                    //Toast.makeText(getContext(), "Otp verified successfully", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }else Toast.makeText(getContext(), "Enter Correct OTP", Toast.LENGTH_SHORT).show();

            }
        });

    }

}