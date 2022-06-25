package com.dbot.client.main.newrequest;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.common.CommonFunctions;
import com.dbot.client.common.Tags;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.newrequest.model.ApplyCouponResponse;
import com.dbot.client.main.newrequest.model.AvailableCoupon;
import com.dbot.client.main.newrequest.model.BookSlot;
import com.dbot.client.main.newrequest.model.BookSlotResponse;
import com.dbot.client.main.newrequest.model.PackageData;
import com.dbot.client.main.payu.PayUActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Request3Fragment extends Fragment implements View.OnClickListener {

    private Request3ViewModel mViewModel;
    View root;
    LinearLayout ll_essential, ll_plus;
    RadioButton rb_essentials, rb_plus;
    TextView tv_service_title_1, tv_service_title_2, tv_es_package_desc, tv_pl_package_desc, tv_es_price, tv_pl_price, tv_bill_service, tv_bill_service_price, tv_coupon_discount_price, tv_bill_total_price, tv_coupon_apply, tv_available_coupon;
    Button btn_req3_prev, btn_req3_pay;
    EditText et_coupon_code;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    List<PackageData> packageDataList;
    int discount_amount = 0, service_amount = 0, total_amount = 0;

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
        et_coupon_code = root.findViewById(R.id.et_coupon_code);
        tv_coupon_apply = root.findViewById(R.id.tv_coupon_apply);
        tv_service_title_1 = root.findViewById(R.id.tv_service_title_1);
        tv_service_title_2 = root.findViewById(R.id.tv_service_title_2);
        tv_available_coupon = root.findViewById(R.id.tv_available_coupon);
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
        tv_coupon_discount_price.setText(getString(R.string.symbol_rupee) + " " + discount_amount);
        tv_bill_total_price.setText(getString(R.string.symbol_rupee) + " " + total_amount);


        tv_available_coupon.setOnClickListener(this::onClick);
        ll_essential.setOnClickListener(this::onClick);
        ll_plus.setOnClickListener(this::onClick);
        rb_essentials.setOnClickListener(this::onRadioButtonClicked);
        rb_plus.setOnClickListener(this::onRadioButtonClicked);
        btn_req3_prev = root.findViewById(R.id.btn_req3_prev);
        btn_req3_pay = root.findViewById(R.id.btn_req3_pay);
        btn_req3_prev.setOnClickListener(this::onClick);
        btn_req3_pay.setOnClickListener(this::onClick);
        tv_coupon_apply.setOnClickListener(this::onClick);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request3ViewModel.class);
        mViewModel.getBookSlotResult().observe(getViewLifecycleOwner(), new Observer<BookSlotResponse>() {
            @Override
            public void onChanged(BookSlotResponse bookSlotResponse) {
                if (bookSlotResponse.getStatus().getCode() == 1031) {
                    /*RequestCompletedFragment requestCompletedFragment = new RequestCompletedFragment(bookSlotResponse.getData().getRequestId());
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, requestCompletedFragment);
                    fragmentTransaction.commit();*/
                }
            }
        });
        mViewModel.getPackages();
        mViewModel.getPackagesResult().observe(getViewLifecycleOwner(), new Observer<List<PackageData>>() {
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
        mViewModel.getAvailableCouponResult().observe(getViewLifecycleOwner(), new Observer<List<AvailableCoupon>>() {
            @Override
            public void onChanged(List<AvailableCoupon> availableCouponList) {
                if (availableCouponList != null)
                    selectAvailableCouponDialog(availableCouponList);
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
            case R.id.tv_available_coupon:
                mViewModel.getAvailableCoupons(MainActivity.sessionManager.getClientId(), MainActivity.package_id);
                break;
            case R.id.ll_essential:
                selectService(0);
                break;
            case R.id.ll_plus:
                selectService(1);
                break;
            case R.id.tv_coupon_apply:
                if (!et_coupon_code.getText().toString().equals("")) {
                    mViewModel.applyCoupon(MainActivity.sessionManager.getClientId(), et_coupon_code.getText().toString(), MainActivity.package_id);
                    mViewModel.getApplyCouponResult().observe(this, new Observer<ApplyCouponResponse>() {
                        @Override
                        public void onChanged(ApplyCouponResponse applyCouponResponse) {
                            if (applyCouponResponse != null) {
                                if (applyCouponResponse.getStatus().getCode() == 1062) {
                                   applyCoupon(applyCouponResponse.getCoupon().getDiscountAmount());

                                } else if (applyCouponResponse.getStatus().getCode() == 1061) {

                                }
                                Snackbar.make(getView(), applyCouponResponse.getStatus().getMessage(), Snackbar.LENGTH_SHORT);
                            }
                        }
                    });
                } else {
                    et_coupon_code.setError("Required");
                }
                break;


        }
    }

    private void applyCoupon(Integer discountAmount) {
        discount_amount = discountAmount;
        tv_coupon_discount_price.setText(getString(R.string.symbol_rupee) + " " + discount_amount);
        calculateTotal();
    }


    private void selectService(int i) {
        if (i == 0) {
            rb_essentials.setChecked(true);
            rb_plus.setChecked(false);
        } else if (i == 1) {
            rb_essentials.setChecked(false);
            rb_plus.setChecked(true);
        }
        tv_bill_service.setText(packageDataList.get(i).getPackageName() + " " + "Service");
        tv_bill_service_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(i).getPrice());
        //tv_bill_total_price.setText(getString(R.string.symbol_rupee) + " " + packageDataList.get(i).getPrice());
        MainActivity.package_id = Integer.parseInt(packageDataList.get(i).getPackageId());
        MainActivity.package_amount = Integer.parseInt(packageDataList.get(i).getPrice());
        MainActivity.amount_paid = Integer.parseInt(packageDataList.get(i).getPrice());
        service_amount = Integer.parseInt(packageDataList.get(i).getPrice());
        et_coupon_code.setText("");
        tv_available_coupon.setText(getString(R.string.select_available_coupon));
        tv_available_coupon.setTextColor(getActivity().getColor(R.color.cool_grey));
        et_coupon_code.setEnabled(true);
        tv_available_coupon.setEnabled(true);
        discount_amount = 0;
        applyCoupon(0);
        calculateTotal();
    }


    private void calculateTotal() {
        total_amount = service_amount - discount_amount;
        tv_bill_total_price.setText(getString(R.string.symbol_rupee) + " " + total_amount);
    }

    private void bookSlot() {


        MainActivity.discount = discount_amount;
        MainActivity.amount_paid = total_amount;
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

        if(MainActivity.amount_paid == 0)
        {
            bookSlot.setTransactionId("");
            bookSlot.setPaymentStatus(1);
            CommonFunctions.postPaymentPurchaseDetails(bookSlot, getContext(), getActivity(),
                    "Success",
                    "Your Slot booked successfully",
                    "success","");
        }else {
            Log.d("bookslotdata", new GsonBuilder().setPrettyPrinting().create().toJson(bookSlot));
            Intent paymentIntent = new Intent(getActivity(), PayUActivity.class);
            paymentIntent.putExtra(Tags.TAG_BOOK_SLOT_DATA, bookSlot);
            getActivity().startActivity(paymentIntent);
        }
        //mViewModel.bookSlot(bookSlot);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_essentials:
                if (checked) {
                    selectService(0);
                }
                break;
            case R.id.rb_plus:
                if (checked) {
                   selectService(1);
                }
                break;
        }
    }

    private void selectAvailableCouponDialog(List<AvailableCoupon> availableCouponList) {
        // Initialize dialog
        Dialog dialog = new Dialog(getContext());

        // set custom dialog
        dialog.setContentView(R.layout.dialog_available_coupons);

        // set custom height and width
        //dialog.getWindow().setLayout(650, 800);

        // set transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCanceledOnTouchOutside(true);

        // show dialog
        dialog.show();

        // Initialize and assign variable

        ListView listView = dialog.findViewById(R.id.list_view);

        AvailableCouponAdapter availableCouponAdapter = new AvailableCouponAdapter(getContext(), availableCouponList);

        listView.setAdapter(availableCouponAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_available_coupon.setText("1 Coupon Applied");
                tv_available_coupon.setTextColor(getActivity().getColor(R.color.green));
                applyCoupon(availableCouponList.get(position).getDiscountAmount());
                // Dismiss dialog
                dialog.dismiss();
            }
        });
    }
}
