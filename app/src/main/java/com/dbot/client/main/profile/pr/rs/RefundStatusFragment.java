package com.dbot.client.main.profile.pr.rs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.profile.pr.PrFragment;
import com.dbot.client.main.profile.pr.rs.model.RefundResponse;

public class RefundStatusFragment extends Fragment {

    private RefundStatusViewModel mViewModel;
    ExpandableListView elv_refund;

    public static RefundStatusFragment newInstance() {
        return new RefundStatusFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_refund_status, container, false);
        elv_refund = root.findViewById(R.id.elv_refund);
        ImageView iv_back_rs = root.findViewById(R.id.iv_back_rs);
        iv_back_rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrFragment prFragment = new PrFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, prFragment);
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RefundStatusViewModel.class);
        mViewModel.getRefundDataList(MainActivity.sessionManager.getClientId());
        mViewModel.getRefundResponseResult().observe(getViewLifecycleOwner(), new Observer<RefundResponse>() {
            @Override
            public void onChanged(RefundResponse refundResponse) {
                if (refundResponse != null) {
                    CustomExpandableListAdapter customExpandableListAdapter = new CustomExpandableListAdapter(getContext(), refundResponse.getRefundData());
                    elv_refund.setAdapter(customExpandableListAdapter);
                }
            }
        });
        // TODO: Use the ViewModel
    }
}