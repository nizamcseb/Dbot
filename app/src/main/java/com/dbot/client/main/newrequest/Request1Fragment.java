package com.dbot.client.main.newrequest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Request1Fragment extends Fragment implements OnMapReadyCallback {

    private Request1ViewModel mViewModel;
    GoogleMap mGoogleMap;
    MapView mapView;
    View root;
    GpsTracker gpsTracker;
    TextView tv_location_address;
    ImageView iv_my_location;

    Location nowLocation, location;
    Button btn_req1_next, saveLocation;

    public static Request1Fragment newInstance() {
        return new Request1Fragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_request1, container, false);

        // startLocationService();
        tv_location_address = root.findViewById(R.id.tv_location_address);
        iv_my_location = root.findViewById(R.id.iv_my_location);
        iv_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
        btn_req1_next = root.findViewById(R.id.btn_req1_next);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) root.findViewById(R.id.mapView);
        getLocationPermission();


    }

    public void getLocation() {
        gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            //userCurrentLocation = new Location(String.valueOf(new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude())));
            startMap(latitude,longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void getLocationPermission() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                //statMap();
                                getLocation();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                getLocation();
                            } else {
                                // No location access granted.
                            }
                        }
                );

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

    }

    private void startMap(double latitude, double longitude) {

        if (mapView != null) {
            // Initialise the MapView
            if (mapView != null) {
                // Initialise the MapView
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @SuppressLint({"MissingPermission", "PotentialBehaviorOverride"})
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        // storing location to temporary variable
                        LatLng latLng = new LatLng(latitude, longitude); //your lat lng
                        //LatLng latLng = new LatLng(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude()); //your lat lng
                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("marker")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        // Enable GPS marker in Map
                        googleMap.setMyLocationEnabled(true);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.getUiSettings().setZoomControlsEnabled(true);
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);
                        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                            @Override
                            public void onCameraMove() {
                                LatLng midLatLng = googleMap.getCameraPosition().target;
                                if (marker != null) {
                                    marker.setPosition(midLatLng);
                                    nowLocation = new Location(String.valueOf(midLatLng));
                                    tv_location_address.setText(getAddressText(midLatLng));
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request1ViewModel.class);

        // TODO: Use the ViewModel
    }


   /* private void startLocationService() {

        MapsInitializer.initialize(getContext());


        saveLocation = root.findViewById(R.id.saveLocation);
        saveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nowLocation != null) {
                    // if user location is null set the previous location fetched
                    //location = new GeoPoint(nowLocation.latitude, nowLocation.longitude);
                    location = nowLocation;
                } else {
                    //location = new GeoPoint(userCurrentLocation.getLatitude(), userCurrentLocation.getLongitude());
                    location = userCurrentLocation;
                }
            }
        });
    }*/

    private String getAddressText(LatLng location) {
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert addresses != null;
        return addresses.get(0).getAddressLine(0);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;


    }
}