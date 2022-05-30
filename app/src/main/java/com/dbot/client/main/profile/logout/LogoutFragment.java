package com.dbot.client.main.profile.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dbot.client.LandingActivity;
import com.dbot.client.R;
import com.dbot.client.common.SessionManager;
import com.dbot.client.main.profile.ProfileFragment;
import com.dbot.client.main.profile.refer.ReferFragment;

public class LogoutFragment extends Fragment {


    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        ImageView iv_back_logout = root.findViewById(R.id.iv_back_logout);
        iv_back_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, profileFragment);
                fragmentTransaction.commit();
            }
        });
        Button btn_logout_back = root.findViewById(R.id.btn_logout_back);
        btn_logout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, profileFragment);
                fragmentTransaction.commit();
            }
        });
        Button btn_logout_confirm = root.findViewById(R.id.btn_logout_confirm);
        btn_logout_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager sessionManager = new SessionManager(getContext());
                sessionManager.logoutUser();

                Toast.makeText(getContext(), "Logout Successfully",
                        Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();*/
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }
}