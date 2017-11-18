package com.example.alfarih.kateangapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alfarih.kateangapp.adapter.ImageAdapter;
import com.example.alfarih.kateangapp.model.explore.Venue;
import com.example.alfarih.kateangapp.model.photos.ItemPhoto;
import com.example.alfarih.kateangapp.model.photos.Photos;
import com.example.alfarih.kateangapp.rest.ApiClient;
import com.example.alfarih.kateangapp.rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMainActivity extends AppCompatActivity {

    private Gallery gallery;
    private ImageView selctedImage;
    private ImageAdapter imageAdapter;
    private TextView name, street, distance, type, rating;
    private List<ItemPhoto> itemPhotos = new ArrayList<ItemPhoto>();
    private final static String CLIENT_ID = "ARUNIBKHB0IGULOKIJXE41EQO3NRUH4LXJMVUJNS1XW4UNGG";
    private final static String CLIENT_SECRET = "ERLAFWMBY3E1TFLHN4QXRB2ZXRALP5ZIVEVH0YQIO4C0IGCP";
    private String apiVersion = "20171112";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(getIntent().getStringExtra(Venue.NAME));

        gallery = (Gallery) findViewById(R.id.gallery);
        selctedImage = (ImageView) findViewById(R.id.selectedImage);

        type = (TextView) findViewById(R.id.jenis);
        street = (TextView) findViewById(R.id.jalan);
        rating = (TextView) findViewById(R.id.rating);
        distance = (TextView) findViewById(R.id.jarak);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Photos> photosCall = apiService.getPhotos(getIntent().getStringExtra(Venue.ID)
                ,CLIENT_ID, CLIENT_SECRET, apiVersion);
        photosCall.enqueue(new Callback<Photos>() {
            @Override
            public void onResponse(Call<Photos> call, Response<Photos> response) {
                itemPhotos = response.body().getResponse().getPhotos().getItems();
                gallery.setAdapter(new ImageAdapter(getApplicationContext(), itemPhotos));
                gallery.setSpacing(10);


            }

            @Override
            public void onFailure(Call<Photos> call, Throwable t) {

            }
        });

        distance.setText(getIntent().getStringExtra(Venue.DISTANCE));
        type.setText(getIntent().getStringExtra(Venue.TYPE));
        street.setText(getIntent().getStringExtra(Venue.STREET));
        rating.setText(getIntent().getStringExtra(Venue.RATING));


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Picasso.with(getApplicationContext()).load(itemPhotos.get(i).getPrefix()+"200x200"+
                        itemPhotos.get(i).getSuffix()).into(selctedImage);
            }
        });

    }
}
