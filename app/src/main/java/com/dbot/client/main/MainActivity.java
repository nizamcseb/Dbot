package com.dbot.client.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dbot.client.R;
import com.dbot.client.common.SessionManager;
import com.dbot.client.common.Tags;
import com.dbot.client.databinding.ActivityMainBinding;
import com.dbot.client.main.newrequest.RequestCompletedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static SessionManager sessionManager;
    public static String book_date, map_location, door_number, building_name, landmark, project_name, contact_person_name, contact_person_phone, coupen_code = "";
    public static List<Integer> scope = null;
    public static int city = 0;
    public static int slot_time_id = 0;
    public static int property_size = 9;
    public static int project_type = 1;
    public static int package_id = 9;
    public static int package_amount = 0;
    public static int discount = 0;
    public static int amount_paid = 0;
    public static int payment_status = 0;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager = new SessionManager(this);
        scope = new ArrayList<Integer>();

        Log.d("session user", sessionManager.getClientId());
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile, R.id.navigation_projects)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       Log.d("requestCode ",String.valueOf(requestCode));
        Log.d(" resultCode ",String.valueOf(resultCode));
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                //RequestCompletedFragment requestCompletedFragment = new RequestCompletedFragment(data.getStringExtra(Tags.TAG_PAYMENT_TXN_ID));
                Bundle bundle = new Bundle();
                bundle.putString(Tags.TAG_PAYMENT_TXN_ID,data.getStringExtra(Tags.TAG_PAYMENT_TXN_ID));
                navController.navigate(R.id.navigation_new_request_completed,bundle);
            }

        }
    }*/

        public static void clearBookSlotData () {
            book_date = "";
            map_location = "";
            door_number = "";
            building_name = "";
            landmark = "";
            project_name = "";
            contact_person_name = "";
            contact_person_phone = "";
            coupen_code = "";
            scope = null;
            city = 0;
            slot_time_id = 0;
            property_size = 9;
            project_type = 1;
            package_id = 9;
            package_amount = 0;
            discount = 0;
            amount_paid = 0;
            payment_status = 0;
        }

    }