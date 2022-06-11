package com.dbot.client.main.profile.pr.rs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dbot.client.R;
import com.dbot.client.main.profile.pr.rs.model.RefundData;

import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<RefundData> refundDataList;

    public CustomExpandableListAdapter(Context context, List<RefundData> refundDataList) {
        this.context = context;
        this.refundDataList = refundDataList;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.refundDataList.get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        //final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_refund_status_expand, null);
        }
        TextView tv_rs_booking_id = convertView.findViewById(R.id.tv_rs_booking_id);
        TextView tv_rs_refund_amount = convertView.findViewById(R.id.tv_rs_refund_amount);
        TextView tv_rs_status = convertView.findViewById(R.id.tv_rs_status);
        ImageView iv_rs_initiated = convertView.findViewById(R.id.iv_rs_initiated);
        ImageView iv_rs_processed = convertView.findViewById(R.id.iv_rs_processed);
        ImageView iv_rs_credited = convertView.findViewById(R.id.iv_rs_credited);
        View v_rs_processed = convertView.findViewById(R.id.v_rs_processed);
        View v_rs_credited = convertView.findViewById(R.id.v_rs_credited);
        TextView tv_rs_initiated_date = convertView.findViewById(R.id.tv_rs_initiated_date);
        TextView tv_rs_processed_date = convertView.findViewById(R.id.tv_rs_processed_date);
        TextView tv_rs_credited_date = convertView.findViewById(R.id.tv_rs_credited_date);
        tv_rs_booking_id.setText(refundDataList.get(listPosition).getBookingId());
        tv_rs_refund_amount.setText(context.getString(R.string.symbol_rupee)+" "+refundDataList.get(listPosition).getRefundAmount());
        tv_rs_status.setText(refundDataList.get(listPosition).getRefundStatus().getStatus());
       if(refundDataList.get(listPosition).getRefundStatus().getId().equals("1")) {
           tv_rs_status.setTextColor(context.getColor(R.color.primary_varient));
           iv_rs_initiated.setImageDrawable(context.getDrawable(R.drawable.ic_refund_status_done));

       }
       if(refundDataList.get(listPosition).getRefundStatus().getId().equals("2")) {
           tv_rs_status.setTextColor(context.getColor(R.color.purple_profile));
           iv_rs_initiated.setImageDrawable(context.getDrawable(R.drawable.ic_refund_status_done));
           iv_rs_processed.setImageDrawable(context.getDrawable(R.drawable.ic_refund_status_done));
           v_rs_processed.setBackgroundColor(context.getColor(R.color.refund_status_filled_color));

       }
       if(refundDataList.get(listPosition).getRefundStatus().getId().equals("3")) {
           tv_rs_status.setTextColor(context.getColor(R.color.green));
           iv_rs_initiated.setImageDrawable(context.getDrawable(R.drawable.ic_refund_status_done));
           iv_rs_processed.setImageDrawable(context.getDrawable(R.drawable.ic_refund_status_done));
           v_rs_processed.setBackgroundColor(context.getColor(R.color.refund_status_filled_color));
           iv_rs_credited.setImageDrawable(context.getDrawable(R.drawable.ic_refund_status_done));
           v_rs_credited.setBackgroundColor(context.getColor(R.color.refund_status_filled_color));

       }
        if(refundDataList.get(listPosition).getRefundInitiatedOn() != null)
            tv_rs_initiated_date.setText(refundDataList.get(listPosition).getRefundInitiatedOn());
        if(refundDataList.get(listPosition).getRefundProcessedOn() != null)
            tv_rs_processed_date.setText(refundDataList.get(listPosition).getRefundProcessedOn());
        if(refundDataList.get(listPosition).getRefundCreditedOn() != null)
            tv_rs_credited_date.setText(refundDataList.get(listPosition).getRefundCreditedOn());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.refundDataList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.refundDataList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_refund_status_group, null);
        }
        TextView tv_rs_booking_id =convertView.findViewById(R.id.tv_rs_booking_id);
        TextView tv_rs_refund_amount =convertView.findViewById(R.id.tv_rs_refund_amount);
        TextView tv_rs_status =convertView.findViewById(R.id.tv_rs_status);

        tv_rs_booking_id.setText(refundDataList.get(listPosition).getBookingId());
        tv_rs_refund_amount.setText(context.getString(R.string.symbol_rupee)+" "+refundDataList.get(listPosition).getRefundAmount());
        tv_rs_status.setText(refundDataList.get(listPosition).getRefundStatus().getStatus());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
