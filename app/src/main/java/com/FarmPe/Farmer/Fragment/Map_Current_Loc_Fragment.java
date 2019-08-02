package com.FarmPe.Farmer.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.FarmPe.Farmer.Activity.GPSTracker;
import com.FarmPe.Farmer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;


public class Map_Current_Loc_Fragment extends Fragment implements OnMapReadyCallback {

    LocationManager manager;
    boolean permission_navigated = false, gps_navigated = false, got_latlong = false;
    int attempts = 0, permission_attempt = 0, gps_attempt = 0;
    Fragment selectedFragment = null;
    public static double latitude, longitude;
    TextView current_address;
    FloatingActionButton fab_icon;
    Button choose_loc;
    public static String LAT_LONG;
    ImageView left_arrw;
    boolean loc_frst_set;



    SupportMapFragment mapFragment;
    String text_address;
    GoogleMap mMap;
    double currentLat, currentLng;



    public static Map_Current_Loc_Fragment newInstance() {
        Map_Current_Loc_Fragment fragment = new Map_Current_Loc_Fragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.get_lat_long_map_layout, container, false);
       // nomap = view.findViewById(R.id.nomap);
        current_address = view.findViewById(R.id.curr_address);

       // choose_loc = view.findViewById(R.id.choose_location);
        fab_icon = view.findViewById(R.id.fab);
        left_arrw = view.findViewById(R.id.left_arrw);


        fab_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkLocationPermission();
                System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkk"+currentLat);
              //  mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude) ,15) );
          mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLat,currentLng), 12.0f));

            }
        });


        // mapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager()
        //  .findFragmentById(R.id.map);


//
//        if (!Places.isInitialized()) {
//            Places.initialize(getActivity(), "AIzaSyB8Gd1ZioSS4JTVWWaGs2z52uEHrZ1gY70");
//     }

//        if (!Places.isInitialized()) {
//            Places.initialize(getActivity(), "AIzaSyCb3WfGFD2eA5dp0BKZKcMyOfAy7A_H738");
//        }
//
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
////        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
////                getActivity().getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
//        autocompleteFragment.setCountry("IN");
//        autocompleteFragment.setHint("Enter your Location");
//
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG));
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//
//                System.out.println("dddd = " + place.getLatLng().longitude);
//
//                latitude = place.getLatLng().latitude;
//
//                longitude = place.getLatLng().longitude;
//
//                mapFragment.getMapAsync(Sell_Map_Fragment.this);
//
//                loc_frst_set = false;
//
//                got_latlong = true;
//
//                //   getLatLongfromPlaceId(place.getId()); to get place ID pass "Place.Field.ID" in setPlaceFields
//
//            }
//
//            @Override
//            public void onError(Status status) {
//
//                Toast.makeText(getActivity().getBaseContext(), status.toString(), Toast.LENGTH_SHORT).show();
//
//                Log.i("", "An error occurred: " + status);
//            }
//        });
//


        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map); //intialize map(fragment - give as getChildFragmentMAnager)

        manager = (LocationManager) getActivity().getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);//inbuilt class - to get Gps(Location Manabger object created)

        checkLocationPermission();///method
      //  mapFragment.getMapAsync(Map_Current_Loc_Fragment.this);//onmap ready excecute

        return view;
    }



    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            //to check whether permissio is in manifest

            //ask for permission pop up
            //  askForPermission();                                   //pop up to check permissions in manifest


        } else {

            if (permission_navigated) permission_navigated = false;

            //check if gps is turned on
            checkGPSTurnedOn();

        }
    }

    @SuppressLint("MissingPermission")
    private void checkGPSTurnedOn() // else to check gps- turn on - pop up
    {
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) { // if gps enable or not
            buildAlertMessageNoGps();

        } else {

            if (gps_navigated)
                gps_navigated = false;//gps already enabled(gps turned off in between)



            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Fetching your Current loaction. Please wait...");
            final int[] count = {5};



            getActivity().runOnUiThread(new Runnable() {// function running in background
                @Override
                public void run() {
                    final GPSTracker gpsTracker = new GPSTracker(getActivity().getBaseContext());
                    attempts++;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            count[0]--;

                            if (gpsTracker.longitude > 0) {

                                got_latlong = true;

                                latitude = gpsTracker.latitude;
                                longitude = gpsTracker.longitude;
                                currentLat = gpsTracker.latitude;
                                currentLng = gpsTracker.longitude;

                                loc_frst_set = false;
                                mapFragment.getMapAsync(Map_Current_Loc_Fragment.this);//onmap ready excecute

                                // mapFragment.getMapAsync((OnMapReadyCallback) getActivity());

                            }

                            if (got_latlong | count[0] == 0) {
                                progressDialog.dismiss();
                                handler.removeCallbacksAndMessages(null);

                            } else
                                handler.postDelayed(this, 500);
                        }
                    }, 1000);  //the time is in miliseconds

                }
            });

        }
    }

    {

    }

    private void buildAlertMessageNoGps() { // alert dialog box
        gps_attempt++; // increment in count (alert box)

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);


        alertDialogBuilder.setTitle("Turn on GPS?");
        alertDialogBuilder.setMessage("Your GPS seems to be disabled, do you want to enable it?");
        alertDialogBuilder.setPositiveButton("Turn On", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                gps_navigated = true;
                //will be navigated to turn on gps
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));//if clicked on turn on gps

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

//                if (gps_attempt < 2)
//                    alertDialogBuilder.show();
//                else
//                    Toast.makeText(getActivity().getBaseContext(), "Enter Location Manually", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();

    }

//    private void permissionDeclined()
//    {
//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//        alertDialogBuilder.setTitle("Allow Location Access");
//        alertDialogBuilder.setMessage("You have declined location access permission please turn it on to navigate into the App.");
//        alertDialogBuilder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                permission_navigated=true;
//                //will navigate to permission section to check location
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);
//
//
//
//            }
//        });
//        alertDialogBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                permission_attempt++;
//                dialogInterface.dismiss();
//
//                if(permission_attempt!=2)
//                    alertDialogBuilder.show();
//                Toast.makeText(getActivity().getBaseContext(),"Enter Location Manually",Toast.LENGTH_SHORT).show();
//            }
//        });
//        alertDialogBuilder.setCancelable(false);
//        alertDialogBuilder.show();
//    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap=googleMap;


       // nomap.setVisibility(View.INVISIBLE);

        LatLng india = new LatLng(latitude, longitude);//object latlag variable india - lat long  - gps tracker
              LAT_LONG = getAddressFromLatLong(latitude, longitude);

        current_address.setText(LAT_LONG); // string return - address



      googleMap.addMarker(new MarkerOptions().position(india).title("India"));

      googleMap.moveCamera(CameraUpdateFactory.newLatLng(india));

        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude) ,15) );// with zoom without movement


        // for adding + and - to map to zoom in and out
        // googleMap.getUiSettings().setZoomControlsEnabled(true); //+ - Symbol

      googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(india, 17f)); // zoom in
        // zoom dance madkondu hogathe with peak alli irothe  LatLng coordinate = new LatLng(15, 77); //Store these lat lng values somewhere. These should be constant.



        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
        @Override
        public void onCameraIdle() {

            if (loc_frst_set) {

                LatLng midLatLng = googleMap.getCameraPosition().target;
                LAT_LONG = getAddressFromLatLong(latitude, longitude);


               current_address.setText(LAT_LONG);

                latitude = midLatLng.latitude;
                longitude = midLatLng.longitude;


            } else
                loc_frst_set = true;

        }
    });


}


    private String getAddressFromLatLong(Double Latitude, Double Longitude) {
        Geocoder geocoder; // inbuilt class
        List<Address> addresses;// inbuilt
        geocoder = new Geocoder(getActivity(), Locale.getDefault());// any default location
        System.out.println("jjjjjjjjjjjjjjjjj" + Latitude);
        System.out.println("jjjjjjjjjjjjjjjjj" + Longitude);
        try {
            addresses = geocoder.getFromLocation(Latitude, Longitude, 1);//lat long pass in 1 method(inbuilt)
            String address = addresses.get(0).getAddressLine(0); // -- // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            return address;

        } catch (Exception e) {//if input not clear

            return "Address Not Available for this Location";

        }
       /* catch (ArrayIndexOutOfBoundsException e)//0 if given as 1
        {
            return "Address Not Available for this Location";
        }
    }*/
    }
}
