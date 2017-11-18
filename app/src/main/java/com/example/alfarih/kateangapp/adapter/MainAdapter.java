package com.example.alfarih.kateangapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alfarih.kateangapp.R;
import com.example.alfarih.kateangapp.model.explore.Item_;
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

/**
 * Created by alfarih on 14/11/17.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item_> items;
    private List<ItemPhoto> photos;
    private Context context;

    private final static String CLIENT_ID = "ARUNIBKHB0IGULOKIJXE41EQO3NRUH4LXJMVUJNS1XW4UNGG";
    private final static String CLIENT_SECRET = "ERLAFWMBY3E1TFLHN4QXRB2ZXRALP5ZIVEVH0YQIO4C0IGCP";
    private String apiVersion = "20171112";


    public MainAdapter(Context context, List<Item_> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_header, parent, false);
            return  new HeadViewHolder(v);
        }
        else if(viewType == TYPE_ITEM)
        {*/
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_list, parent, false);
            return new ItemViewHolder(context, v);
        //}
        //return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /*if(holder instanceof HeadViewHolder)
        {
            HeadViewHolder VHHead = (HeadViewHolder) holder;
            VHHead.kota.setText(mainHead.getKota());
            VHHead.jalan.setText(mainHead.getJalan());
        }
        else if(holder instanceof ItemViewHolder)
        {*/
            ItemViewHolder VHitem = (ItemViewHolder) holder;
            String prefix = items.get(position).getVenue().getCategories().get(0).getIcon().getPrefix();
            String suffix = items.get(position).getVenue().getCategories().get(0).getIcon().getSuffix();

            VHitem.title.setText(items.get(position).getVenue().getName());
            VHitem.detail.setText(items.get(position).getVenue().getLocation().getAddress());
            VHitem.jarak.setText(String.valueOf(items.get(position).getVenue().getLocation().getDistance())+" Meter dari tempat anda");
            Picasso.with(context).load(prefix+"bg_64"+suffix).into(VHitem.img);

            Venue currentVenue = items.get(position).getVenue();
            VHitem.bindTo(currentVenue);
            VHitem.setupPhotos(items.get(position).getVenue().getId(), items.get(position).getVenue().getCategories().get(0).getName());
        //}

    }

    /*@Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }*/

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /*public class HeadViewHolder extends RecyclerView.ViewHolder {

        private TextView kota, jalan;

        public HeadViewHolder(View itemView) {
            super(itemView);
            kota = (TextView) itemView.findViewById(R.id.kota);
            jalan = (TextView) itemView.findViewById(R.id.jalan);

        }
    }*/

    public class ItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        private TextView title, detail, jarak;
        private ImageView img, preview;
        private Context context;
        private Venue currentVenue = new Venue();
        private String type;

        private List<ItemPhoto> itemPhotos = new ArrayList<ItemPhoto>();
        String prefix, suffix;



        public ItemViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            title = (TextView) itemView.findViewById(R.id.recomenTitel);
            detail = (TextView) itemView.findViewById(R.id.recomenAddress);
            jarak = (TextView) itemView.findViewById(R.id.recomenDistance);
            img = (ImageView) itemView.findViewById(R.id.recomenImg);
            preview = (ImageView) itemView.findViewById(R.id.imgPreview);

            itemView.setOnClickListener(this);
        }

        public void bindTo(Venue currentVenue){
            this.currentVenue = currentVenue;
        }
        private void setupPhotos(String id, String type) {

            this.type = type;

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<Photos> photosCall = apiService.getPhotos(id
                    ,CLIENT_ID, CLIENT_SECRET, apiVersion);
            photosCall.enqueue(new Callback<Photos>() {
                @Override
                public void onResponse(Call<Photos> call, Response<Photos> response) {
                    itemPhotos = response.body().getResponse().getPhotos().getItems();
                    prefix = itemPhotos.get(0).getPrefix();
                    suffix = itemPhotos.get(0).getSuffix();
                }

                @Override
                public void onFailure(Call<Photos> call, Throwable t) {

                }
            });

            Picasso.with(context).load(prefix+"200x200"+suffix).into(preview);
        }

        @Override
        public void onClick(View view) {

            Intent detailIntent = Venue.starter(context , currentVenue.getId(), title.getText().toString(),
                    currentVenue.getRating(), detail.getText().toString(), jarak.getText().toString(), type);
            context.startActivity(detailIntent);

        }
    }
}
