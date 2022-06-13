package com.dbot.client.main.profile.pr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dbot.client.R;
import com.dbot.client.main.profile.ProfileFragment;
import com.dbot.client.main.profile.pr.rs.RefundStatusFragment;

public class PrFragment extends Fragment {

    //private ServicesViewModel mViewModel;

    public static PrFragment newInstance() {
        return new PrFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pr, container, false);
        ImageView iv_back_pr = root.findViewById(R.id.iv_back_pr);
        iv_back_pr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, profileFragment);
                fragmentTransaction.commit();
            }
        });
        LinearLayout ll_payment_modes = root.findViewById(R.id.ll_payment_modes);
        ll_payment_modes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        LinearLayout ll_refund_status = root.findViewById(R.id.ll_refund_status);
        ll_refund_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefundStatusFragment refundStatusFragment = new RefundStatusFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, refundStatusFragment);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        // TODO: Use the ViewModel
    }

}
