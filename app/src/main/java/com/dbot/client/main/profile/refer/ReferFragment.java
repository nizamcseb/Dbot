package com.dbot.client.main.profile.refer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.databinding.FragmentAccountInfoBinding;
import com.dbot.client.databinding.FragmentReferEarnBinding;
import com.dbot.client.main.profile.ProfileFragment;
import com.dbot.client.main.requests.RequestsFragment;
import com.dbot.client.main.requests.RequestsViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ReferFragment extends Fragment {

    private ReferViewModel mViewModel;
    FragmentReferEarnBinding binding;

    public static ReferFragment newInstance() {
        return new ReferFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReferEarnBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.btnCopyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.tvReferCode.getText().toString();
                Snackbar.make(binding.btnCopyCode,"Code Copied",Snackbar.LENGTH_SHORT).show();
            }
        });
        binding.ivBackRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, profileFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReferViewModel.class);
        // TODO: Use the ViewModel

    }

}
