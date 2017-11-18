package com.example.alfarih.kateangapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.alfarih.kateangapp.model.photos.ItemPhoto;
import com.example.alfarih.kateangapp.model.photos.Photos_;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfarih on 17/11/17.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<ItemPhoto> photos_list;

    public ImageAdapter(Context context, List<ItemPhoto> photos_list) {
        this.context = context;
        this.photos_list = photos_list;
    }

    @Override
    public int getCount() {
        return photos_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        String prefix, suffix;
        prefix = photos_list.get(position).getPrefix();
        suffix = photos_list.get(position).getSuffix();

        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(prefix+"200x200"+suffix).into(imageView); // set image in ImageView
        imageView.setLayoutParams(new Gallery.LayoutParams(200, 200)); // set ImageView param
        return imageView;
    }
}
