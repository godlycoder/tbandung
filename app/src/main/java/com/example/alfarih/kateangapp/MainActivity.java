package com.example.alfarih.kateangapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.alfarih.kateangapp.adapter.MainAdapter;
import com.example.alfarih.kateangapp.adapter.TabAdapter;
import com.example.alfarih.kateangapp.model.MainHead;
import com.example.alfarih.kateangapp.model.explore.Explore;
import com.example.alfarih.kateangapp.model.explore.Item_;
import com.example.alfarih.kateangapp.model.photos.ItemPhoto;
import com.example.alfarih.kateangapp.model.photos.Photos;
import com.example.alfarih.kateangapp.rest.ApiClient;
import com.example.alfarih.kateangapp.rest.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        LocationListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private MainHead head;

    private NotificationManager notificationManager;
    private static final int NOTIF_ID = 0;
    private NotificationReceiver mReceiver = new NotificationReceiver();


    private static final String NOTIFICATION_GUIDE_URL =
            "https://developer.android.com/design/patterns/notifications.html";
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION =
            "com.example.android.notifyme.ACTION_CANCEL_NOTIFICATION";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private TabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        //notification
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);*/

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        registerReceiver(mReceiver, intentFilter);

        //navigation drawer
        /*if (drawerLayout != null) {
            drawerLayout.addDrawerListener(toggle);
        }

        toggle.syncState();*/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

    }



    /*private String getCurrentAddress() {
        Location location;
        String currentLastLocality="";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);

        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latitude = location.getLatitude();
                double longtitude = location.getLongitude();
                List<Address> addresses;
                String locality;

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(latitude, longtitude, 5);
                    locality = addresses.get(0).getLocality();

                } catch (IOException e) {
                    locality = "---";
                }
                head = new MainHead(locality, locality);

                currentLastLocality = locality;
            }
        }
        return currentLastLocality;
    }*/

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.map:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {



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

    private class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ACTION_CANCEL_NOTIFICATION:
                    cancelNotification();
                    break;
                case ACTION_UPDATE_NOTIFICATION:
                    updateNotification();
                    break;
            }
        }
    }

    public void sendNotification(int i){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIF_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast
                (this, NOTIF_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent learnMoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NOTIFICATION_GUIDE_URL));
        PendingIntent learnMorePendingIntent = PendingIntent.getActivity
                (this, NOTIF_ID, learnMoreIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast
                (this, NOTIF_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Ada "+i+" Rekomendasi tempat di dekat anda")
                .setContentText("Klik untuk melihat")
                .setSmallIcon(R.mipmap.notif)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setDeleteIntent(cancelPendingIntent);

        Notification myNotification = notifyBuilder.build();
        notificationManager.notify(NOTIF_ID, myNotification);
    }

    public void updateNotification(){
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.near);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIF_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast
                (this, NOTIF_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent learnMoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NOTIFICATION_GUIDE_URL));
        PendingIntent learnMorePendingIntent = PendingIntent.getActivity
                (this, NOTIF_ID, learnMoreIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("You've been notificated!")
                .setContentText("Hallo")
                .setSmallIcon(R.mipmap.notif)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setDeleteIntent(cancelPendingIntent)
                .addAction(R.drawable.near, "Learn More",
                        learnMorePendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(androidImage)
                        .setBigContentTitle("Notification update!"));

        Notification notification = notifyBuilder.build();
        notificationManager.notify(NOTIF_ID, notification);
    }

    public void cancelNotification(){
        notificationManager.cancel(NOTIF_ID);
    }
}
