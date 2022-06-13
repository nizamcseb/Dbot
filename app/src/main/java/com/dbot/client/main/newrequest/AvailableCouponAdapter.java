package com.dbot.client.main.newrequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dbot.client.R;
import com.dbot.client.main.newrequest.model.AvailableCoupon;

import java.util.List;

public class AvailableCouponAdapter extends ArrayAdapter<AvailableCoupon> {
    List<AvailableCoupon> availableCouponList;
    public AvailableCouponAdapter(Context context, List<AvailableCoupon> availableCoupon) {
        super(context, 0, availableCoupon);
        availableCouponList = availableCoupon;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //AvailableCoupon availableCoupon = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_available_coupon, parent, false);
        }

        TextView tv_ac_referral_code = convertView.findViewById(R.id.tv_ac_referral_code);
        TextView tv_ac_discount_amount = convertView.findViewById(R.id.tv_ac_discount_amount);
        tv_ac_referral_code.setText(availableCouponList.get(position).getReferralCode());
        tv_ac_discount_amount.setText(getContext().getString(R.string.symbol_rupee)+" "+String.valueOf(availableCouponList.get(position).getDiscountAmount()));

        return convertView;
    }
}