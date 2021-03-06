package com.dbot.client.main.profile;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbot.client.R;
import com.dbot.client.common.SessionManager;
import com.dbot.client.databinding.FragmentProfileBinding;
import com.dbot.client.main.profile.pr.PrFragment;
import com.dbot.client.main.profile.account.AccountInfoFragment;
import com.dbot.client.main.profile.faq.FAQsFragment;
import com.dbot.client.main.profile.logout.LogoutFragment;
import com.dbot.client.main.profile.refer.ReferFragment;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    FragmentProfileBinding binding;
    SessionManager sessionManager;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sessionManager = new SessionManager(getContext());
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
        binding.tvUserName.setText("Hi "+sessionManager.getClientFullName());
        binding.llAccountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountInfoFragment accountInfoFragment = new AccountInfoFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, accountInfoFragment);
                fragmentTransaction.commit();
            }
        });
        binding.llFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FAQsFragment FAQsFragment = new FAQsFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, FAQsFragment);
                fragmentTransaction.commit();
            }
        });
        binding.llPaymentRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrFragment prFragment = new PrFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, prFragment);
                fragmentTransaction.commit();
            }
        });
        binding.llReferEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReferFragment referFragment = new ReferFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, referFragment);
                fragmentTransaction.commit();
            }
        });
        binding.llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutFragment logoutFragment = new LogoutFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, logoutFragment);
                fragmentTransaction.commit();
               /* //Uncomment the below code to Set the message and title from the strings.xml file
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                //Setting message manually and performing action on button click
                builder.setMessage(getString(R.string.ad_signout_msg))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SessionManager sessionManager = new SessionManager(getContext());
                                sessionManager.logoutUser();

                                Toast.makeText(getContext(), "Logout Successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LandingActivity.class);
                                startActivity(intent);
                                getActivity().finishAffinity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Alert");
                alert.show();*/

            }
        });
    }

}