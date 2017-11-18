package com.example.alfarih.kateangapp;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alfarih.kateangapp.adapter.MainAdapter;
import com.example.alfarih.kateangapp.model.explore.Explore;
import com.example.alfarih.kateangapp.model.explore.Item_;
import com.example.alfarih.kateangapp.model.photos.ItemPhoto;
import com.example.alfarih.kateangapp.rest.ApiClient;
import com.example.alfarih.kateangapp.rest.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecomenFragment extends Fragment implements LocationListener{


    public RecomenFragment() {
        // Required empty public constructor
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private LocationManager locationManager;
    private Location location;
    private double lat, lng;
    private List<Item_> venue = new ArrayList<Item_>();
    private RecyclerView recyclerView;
    private String currentLocality = null;
    private Context context;
    private View myFragment;

    private final static String CLIENT_ID = "ARUNIBKHB0IGULOKIJXE41EQO3NRUH4LXJMVUJNS1XW4UNGG";
    private final static String CLIENT_SECRET = "ERLAFWMBY3E1TFLHN4QXRB2ZXRALP5ZIVEVH0YQIO4C0IGCP";
    private String apiVersion = "20171112";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_recomen, container, false);

        context = this.getActivity();

        //recycler
        recyclerView = (RecyclerView) myFragment.findViewById(R.id.recyclerviewRecomen);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        getLocation();
        getCurrentAddress();
        getVenueNearby();

        return myFragment;
    }

    public void getVenueNearby(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lat = location.getLatitude();
            lng = location.getLongitude();
        }

        String latlng = String.valueOf(lat)+","+String.valueOf(lng);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Explore> callExplore = apiService.getRecomendationNear(CLIENT_ID, CLIENT_SECRET, apiVersion, latlng, true);
        callExplore.enqueue(new Callback<Explore>() {
            @Override
            public void onResponse(Call<Explore> call, Response<Explore> response) {
                venue = response.body().getResponse().getGroups().get(0).getItems();
                recyclerView.setAdapter(new MainAdapter(context, venue));
            }

            @Override
            public void onFailure(Call<Explore> call, Throwable t) {
                Log.d("Call", "Failure");
            }
        });
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentAddress() {
        Location location;
        String currentLastLocality="";

        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latitude = location.getLatitude();
                double longtitude = location.getLongitude();
                List<Address> addresses;
                String locality;

                Geocoder geocoder = new Geocoder(this.getActivity(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude, longtitude, 5);
                    locality = addresses.get(0).getLocality();

                } catch (IOException e) {
                    locality = "---";
                }

                currentLastLocality = locality;
            }
        }
        return currentLastLocality;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(currentLocality==null){
            currentLocality = getCurrentAddress();
            Log.d("Locality change", currentLocality);
        } else {
            if(currentLocality.equals(getCurrentAddress())){
                Log.d("Locality", "Not Change");
            } else {
                Log.d("Locality", "Change");
                getLocation();
                getCurrentAddress();
                getVenueNearby();
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
