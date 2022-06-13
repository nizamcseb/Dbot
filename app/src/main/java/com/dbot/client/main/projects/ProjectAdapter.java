package com.dbot.client.main.projects;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.dbot.client.R;
import com.dbot.client.main.projects.model.ClientProjectData;

import java.util.List;

public class ProjectAdapter extends BaseAdapter implements Filterable {

    Activity activity;
    Context context;
    public List<ClientProjectData> clientProjectDataList;
    LayoutInflater inflater;

    List<ClientProjectData> filterList;
    ProjectFilter filter;

    public ProjectAdapter(Activity activity, Context context, List<ClientProjectData> projectDataList) {
        this.activity = activity;
        this.context = context;
        this.clientProjectDataList = projectDataList;
        this.filterList = projectDataList;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return clientProjectDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientProjectDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View root;
        if (convertView == null) {
            //inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_projects, null);
        }
        TextView tv_project_item_project_name = convertView.findViewById(R.id.tv_project_item_project_name);
        TextView tv_project_item_booking_id = convertView.findViewById(R.id.tv_project_item_booking_id);
        TextView tv_project_item_project_status = convertView.findViewById(R.id.tv_project_item_project_status);
        TextView tv_project_item_project_service = convertView.findViewById(R.id.tv_project_item_project_service);
        TextView tv_project_item_project_booked_date = convertView.findViewById(R.id.tv_project_item_project_booked_date);

        tv_project_item_project_name.setText(clientProjectDataList.get(position).getProjectName());
        tv_project_item_booking_id.setText(clientProjectDataList.get(position).getBookingId());
        if(clientProjectDataList.get(position).getProjectStatus().getStatusValue().equals("Active"))
            tv_project_item_project_status.setTextColor(activity.getColor(R.color.status_active));
        else if(clientProjectDataList.get(position).getProjectStatus().getStatusValue().equals("Completed"))
            tv_project_item_project_status.setTextColor(activity.getColor(R.color.status_completed));
        else if(clientProjectDataList.get(position).getProjectStatus().getStatusValue().equals("Cancelled"))
            tv_project_item_project_status.setTextColor(activity.getColor(R.color.status_cancelled));
        tv_project_item_project_status.setText(clientProjectDataList.get(position).getProjectStatus().getStatusValue());
        tv_project_item_project_service.setText(clientProjectDataList.get(position).getPackage().getPackageName());
        tv_project_item_project_booked_date.setText(clientProjectDataList.get(position).getBookDate());



        // Return the completed view to render on screen
        return convertView;
    }



    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ProjectFilter(filterList, this);
        }

        return filter;
    }




}
