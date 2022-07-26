package com.dbot.client.main.newrequest;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dbot.client.R;
import com.dbot.client.common.PlacesSearchActivity;
import com.dbot.client.main.MainActivity;
import com.dbot.client.main.home.HomeFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Request1Fragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private Request1ViewModel mViewModel;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    GoogleMap mGoogleMap;
    MapView mapView;
    View root;
    GpsTracker gpsTracker;
    TextView tv_location_address;
    ImageView iv_my_location;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayout bottom_sheet, ll_bottom_sheet_expand;
    Location nowLocation;
    Button btn_req1_next, btn_req1_prev;
    EditText et_door_no, et_building_name, et_landmark;

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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) root.findViewById(R.id.mapView);
        getLocationPermission();
        tv_location_address = root.findViewById(R.id.tv_location_address);
        ll_bottom_sheet_expand = root.findViewById(R.id.ll_bottom_sheet_expand);
        bottom_sheet = root.findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);

        et_door_no = root.findViewById(R.id.et_door_no);
        et_building_name = root.findViewById(R.id.et_building_name);
        et_landmark = root.findViewById(R.id.et_landmark);
        setData();
        iv_my_location = root.findViewById(R.id.iv_my_location);
        iv_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
        btn_req1_next = root.findViewById(R.id.btn_req1_next);
        btn_req1_prev = root.findViewById(R.id.btn_req1_prev);
        btn_req1_next.setOnClickListener(this::onClick);
        btn_req1_prev.setOnClickListener(this::onClick);
        tv_location_address.setOnClickListener(this::onClick);
        ll_bottom_sheet_expand.setOnClickListener(this::onClick);

        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        ll_bottom_sheet_expand.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        ll_bottom_sheet_expand.setVisibility(View.GONE);
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


    }

    private void setData() {
        if (MainActivity.door_number != null)
            et_door_no.setText(MainActivity.door_number);
        if (MainActivity.building_name != null)
            et_building_name.setText(MainActivity.building_name);
        if (MainActivity.landmark != null)
            et_landmark.setText(MainActivity.landmark);

    }

    private boolean checkManditoryFields() {
        if (et_door_no.getText().toString().equals("")) {
            et_door_no.setError("Required");
            return false;
        }
        if (et_building_name.getText().toString().equals("")) {
            et_building_name.setError("Required");
            return false;
        }
        /*if(MainActivity.map_location == null) {
            Snackbar.make(btn_req1_next,"Location needed",Snackbar.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            //userCurrentLocation = new Location(String.valueOf(new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude())));
            startMap(latitude, longitude);
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
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
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
                                }
                            }
                        });
                        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {
                                LatLng midLatLng = googleMap.getCameraPosition().target;
                                nowLocation = new Location(String.valueOf(midLatLng));
                                if (getAddressText(midLatLng) != null)
                                    tv_location_address.setText(getAddressText(midLatLng));
                            }
                        });
                    }
                });
            }
        }
    }
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Request1ViewModel.class);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        // TODO: Use the ViewModel
    }

    private String getAddressText(LatLng location) {
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            if (geocoder != null)
                addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert addresses != null;
        if (addresses.size() != 0)
            return addresses.get(0).getAddressLine(0);
        else return null;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_req1_prev:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, homeFragment);
                fragmentTransaction.commit();
                break;
            case R.id.btn_req1_next:
                if (checkManditoryFields()) {
                    MainActivity.door_number = et_door_no.getText().toString();
                    MainActivity.building_name = et_building_name.getText().toString();
                    MainActivity.landmark = et_landmark.getText().toString();
                    MainActivity.map_location = nowLocation.toString();
                    Request2Fragment request2Fragment = new Request2Fragment();
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, request2Fragment);
                    fragmentTransaction.commit();
                }
                break;
            case R.id.ll_bottom_sheet_expand:
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.tv_location_address:
                Intent placeIntent = new Intent(getActivity(),PlacesSearchActivity.class);
                //assert getParentFragment() != null;
                startActivityForResult(placeIntent,111);
                //getActivity().startActivity(new Intent(getActivity(), PlacesSearchActivity.class));
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult resultCode",String.valueOf(resultCode));
        Log.d("onActivityResult requestCode",String.valueOf(requestCode));
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                String returnedResult = data.getData().toString();
                Log.d("returnedResult",returnedResult);
                //getAddressText(new LatLng())
                tv_location_address.setText(returnedResult);
                // OR
                // String returnedResult = data.getDataString();
            }
        }
    }
}