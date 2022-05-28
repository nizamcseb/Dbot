package com.dbot.client.main.newrequest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.newrequest.model.BookSlot;
import com.dbot.client.main.newrequest.model.BookSlotResponse;
import com.dbot.client.main.newrequest.model.PackageData;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Request3Fragment extends Fragment implements View.OnClickListener {

    private Request3ViewModel mViewModel;
    View root;
    LinearLayout ll_essential, ll_plus;
    RadioButton rb_essentials, rb_plus;
    TextView tv_service_title_1, tv_service_title_2, tv_es_package_desc, tv_pl_package_desc, tv_es_price, tv_pl_price, tv_bill_service, tv_bill_service_price, tv_coupon_discount_price, tv_bill_total_price;
    Button btn_req3_prev, btn_req3_pay;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    List<PackageData> packageDataList;

    public static Request3Fragment newInstance() {
        return new Request3Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_request3, container, false);
        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll_essential = root.findViewById(R.id.ll_essential);
        ll_plus = root.findViewById(R.id.ll_plus);
        rb_essentials = root.findViewById(R.id.rb_essentials);
        rb_plus = root.findViewById(R.id.rb_plus);
        tv_service_title_1 = root.findViewById(R.id.tv_service_title_1);
        tv_service_title_2 = root.findViewById(R.id.tv_service_title_2);
        tv_es_package_desc = root.findViewById(R.id.tv_es_package_desc);
        tv_pl_package_desc = root.findViewById(R.id.tv_pl_package_desc);
        tv_es_price = root.findViewById(R.id.tv_es_price);
        tv_pl_price = root.findViewById(R.id.tv_pl_price);
        tv_bill_service = root.findViewById(R.id.tv_bill_service);
        tv_bill_service_price = root.findViewById(R.id.tv_bill_service_price);
        tv_coupon_discount_price = root.findViewById(R.id.tv_coupon_discount_price);
        tv_bill_total_price = root.findViewById(R.id.tv_bill_total_price);

        tv_bill_service.setText("");
        tv_bill_service_price.setText("");
        tv_coupon_discount_price.setText(getString(R.string.symbol_rupee) + " " + "0");
        tv_bill_total_price.setText(getString(R.string.symbol_rupee) + " " + "0");


        ll_essential.setOnClickListener(this::onClick);
        ll_plus.setOnClickListener(this::onClick);
        rb_essentials.setOnClickListener(this::onRadioButtonClicked);
        rb_plus.setOnClickListener(this::onRadioButtonClicked);
        btn_req3_prev = root.findViewById(R.id.btn_req3_prev);
        btn_req3_pay = root.findViewById(R.id.btn_req3_pay);
        btn_req3_prev.setOnClickListener(this::onClick);
        btn_req3_pay.setOnClickListener(this::onClick);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request3ViewModel.class);
        mViewModel.getBookSlotResult().observe(this, new Observer<BookSlotResponse>() {
            @Override
            public void onChanged(BookSlotResponse bookSlotResponse) {
                if (bookSlotResponse.getStatus().getCode() == 1031) {
                    RequestCompletedFragment requestCompletedFragment = new RequestCompletedFragment(bookSlotResponse.getData().getRequestId());
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, requestCompletedFragment);
                    fragmentTransaction.commit();
                }
            }
        });
        mViewModel.getPackages();
        mViewModel.getPackagesResult().observe(this, new Observer<List<PackageData>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<PackageData> packageData) {
                if (packageData != null) {
                    packageDataList = packageData;
                    tv_service_title_1.setText(packageData.get(0).getPackageName());
                    tv_es_package_desc.setText(packageData.get(0).getDescription());
                    tv_es_price.setText(getString(R.string.symbol_rupee) + " " + packageData.get(0).getPrice());

                    tv_service_title_2.setText(packageData.get(1).getPackageName());
                    tv_pl_package_desc.setText(packageData.get(1).getDescription());
                    tv_pl_price.setText(getString(R.string.symbol_rupee) + " " + packageData.get(1).getPrice());
                }
            }
        });
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_req3_prev:
                Request2Fragment request2Fragment = new Request2Fragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request2Fragment);
                fragmentTransaction.commit();
                break;
            case R.id.btn_req3_pay:
                if (MainActivity.package_id != 9) {
                    bookSlot();

                }
                break;
            case R.id.ll_essential:
                rb_essentials.setChecked(true);
                rb_plus.setChecked(false);
                tv_bill_service.setText(packageDataList.get(0).getPackageName() + " " + "Service");
                tv_bill_service_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(0).getPrice());
                tv_bill_total_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(0).getPrice());
                MainActivity.package_id = Integer.parseInt(packageDataList.get(0).getPackageId());
                MainActivity.package_amount = Integer.parseInt(packageDataList.get(0).getPrice());
                MainActivity.amount_paid = Integer.parseInt(packageDataList.get(0).getPrice());

                break;
            case R.id.ll_plus:
                rb_essentials.setChecked(false);
                rb_plus.setChecked(true);
                tv_bill_service.setText(packageDataList.get(1).getPackageName() + " " + "Service");
                tv_bill_service_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(1).getPrice());
                tv_bill_total_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(1).getPrice());
                MainActivity.package_id = Integer.parseInt(packageDataList.get(1).getPackageId());
                MainActivity.package_amount = Integer.parseInt(packageDataList.get(1).getPrice());
                MainActivity.amount_paid = Integer.parseInt(packageDataList.get(1).getPrice());
                break;


        }
    }

    private void bookSlot() {
        BookSlot bookSlot = new BookSlot();
        bookSlot.setClientId(MainActivity.sessionManager.getClientId());
        bookSlot.setBookDate(MainActivity.book_date);
        bookSlot.setSlotTimeId(MainActivity.slot_time_id);
        bookSlot.setCity(MainActivity.city);
        bookSlot.setMapLocation(MainActivity.map_location);
        bookSlot.setDoorNumber(MainActivity.door_number);
        bookSlot.setBuildingName(MainActivity.building_name);
        bookSlot.setLandmark(MainActivity.landmark);
        bookSlot.setProjectName(MainActivity.project_name);
        bookSlot.setContactPersonName(MainActivity.contact_person_name);
        bookSlot.setContactPersonPhone(MainActivity.contact_person_phone);
        bookSlot.setPropertySize(MainActivity.property_size + 1);
        bookSlot.setProjectType(MainActivity.project_type);
        bookSlot.setScope(MainActivity.scope);
        bookSlot.setPackage(MainActivity.package_id);
        bookSlot.setCoupenCode(MainActivity.coupen_code);

        bookSlot.setPackageAmount(MainActivity.package_amount);
        bookSlot.setDiscount(MainActivity.discount);

        bookSlot.setAmountPaid(MainActivity.amount_paid);
        bookSlot.setPaymentStatus(MainActivity.payment_status);

        Log.d("bookslotdata", new GsonBuilder().setPrettyPrinting().create().toJson(bookSlot));
        mViewModel.bookSlot(bookSlot);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_essentials:
                if (checked) {
                    rb_plus.setChecked(false);
                    tv_bill_service.setText(packageDataList.get(0).getPackageName() + " " + "Service");
                    tv_bill_service_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(0).getPrice());
                    tv_bill_total_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(0).getPrice());
                    MainActivity.package_id = Integer.parseInt(packageDataList.get(0).getPackageId());
                    MainActivity.package_amount = Integer.parseInt(packageDataList.get(0).getPrice());
                    MainActivity.amount_paid = Integer.parseInt(packageDataList.get(0).getPrice());
                }
                break;
            case R.id.rb_plus:
                if (checked) {
                    rb_essentials.setChecked(false);
                    tv_bill_service.setText(packageDataList.get(1).getPackageName() + " " + "Service");
                    tv_bill_service_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(1).getPrice());
                    tv_bill_total_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(1).getPrice());
                    MainActivity.package_id = Integer.parseInt(packageDataList.get(1).getPackageId());
                    MainActivity.package_amount = Integer.parseInt(packageDataList.get(1).getPrice());
                    MainActivity.amount_paid = Integer.parseInt(packageDataList.get(1).getPrice());
                }
                break;
        }
    }
}
