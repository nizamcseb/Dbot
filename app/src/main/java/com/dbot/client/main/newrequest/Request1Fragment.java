package com.dbot.client.main.newrequest;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dbot.client.R;

public class Request1Fragment extends Fragment {

    private Request1ViewModel mViewModel;

    public static Request1Fragment newInstance() {
        return new Request1Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_request1, container, false);
        Button btn_req1_next = root.findViewById(R.id.btn_req1_next);
        btn_req1_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request2Fragment request2Fragment = new Request2Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request2Fragment);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request1ViewModel.class);
        // TODO: Use the ViewModel
    }

}