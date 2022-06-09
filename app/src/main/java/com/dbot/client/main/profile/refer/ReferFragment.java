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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.databinding.FragmentReferEarnBinding;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.profile.ProfileFragment;
import com.dbot.client.main.profile.refer.model.RcAndRhResponse;
import com.dbot.client.retrofit.ApiClient;
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
                Snackbar.make(binding.btnCopyCode, "Code Copied", Snackbar.LENGTH_SHORT).show();
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
        mViewModel = new ViewModelProvider(this).get(ReferViewModel.class);
        //if (ApiClient.isTest)
            mViewModel.geRcAndRhCData(MainActivity.sessionManager.getClientId());
        //else  mViewModel.geRcAndRhCData("DBOT1775828299");
        //mViewModel.geRcAndRhCData(MainActivity.sessionManager.getClientId());
        mViewModel.getRcAndRhResponseResult().observe(this, new Observer<RcAndRhResponse>() {
            @Override
            public void onChanged(RcAndRhResponse rcAndRhResponse) {
                binding.tvReferCode.setText(rcAndRhResponse.getRcAndRhData().getMyReferralCode());
                binding.tvRhClaims.setText("Claims : "+rcAndRhResponse.getRcAndRhData().getClaimed()+"/"+rcAndRhResponse.getRcAndRhData().getTotal());
                ReferalHistoryAdapter referalHistoryAdapter = new ReferalHistoryAdapter(getContext(),rcAndRhResponse.getRcAndRhData().getReferalHistory());
                binding.lvRh.setAdapter(referalHistoryAdapter);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel

    }

}
