package com.dbot.client.main.profile.faq;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.dbot.client.databinding.FragmentFaqsBinding;
import com.dbot.client.main.profile.ProfileFragment;
import com.dbot.client.main.profile.faq.model.FAQsData;

import java.util.List;

public class FAQsFragment extends Fragment {

    private FAQsViewModel mViewModel;
    FragmentFaqsBinding binding;


    public static FAQsFragment newInstance() {
        return new FAQsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFaqsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.ivBackFaq.setOnClickListener(new View.OnClickListener() {
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
        mViewModel = new ViewModelProvider(this).get(FAQsViewModel.class);
        mViewModel.getFAQsData();
        mViewModel.getFAQsDataResult().observe(this, new Observer<List<FAQsData>>() {
            @Override
            public void onChanged(List<FAQsData> faQsDataList) {
                if(faQsDataList != null){
                    FAQsAdapter faQsAdapter = new FAQsAdapter(getActivity(),getContext(),faQsDataList);
                    binding.lvFaqs.setAdapter(faQsAdapter);
                    binding.etFaqSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            Log.d("searchText",charSequence.toString());
                            faQsAdapter.getFilter().filter(charSequence.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }

            }
        });
        // TODO: Use the ViewModel

    }
}
