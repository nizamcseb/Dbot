package com.dbot.client.main;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dbot.client.R;
import com.dbot.client.common.SessionManager;
import com.dbot.client.databinding.ActivityMainBinding;
import com.dbot.client.login.model.CityData;
import com.dbot.client.main.projects.model.ClientProjectData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static SessionManager sessionManager;
    public static String book_date, map_location, door_number, building_name, landmark, project_name, contact_person_name, contact_person_phone, coupen_code = "";
    public static List<Integer> scope = null;
    public static int city=0, slot_time_id=0, property_size = 9, project_type = 1, package_id = 9, package_amount = 0, discount = 0, amount_paid = 0, payment_status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public static void clearBookSlotData() {
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