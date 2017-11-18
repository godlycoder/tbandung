package com.example.alfarih.kateangapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alfarih.kateangapp.RecomenFragment;
import com.example.alfarih.kateangapp.TrendingFragment;

/**
 * Created by alfarih on 18/11/17.
 */

public class TabAdapter extends FragmentPagerAdapter{

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RecomenFragment();
            case 1:
                return new TrendingFragment();
            default:
                return new RecomenFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Rekomendasi";
            case 1:
                return "Terpopuler";
            default:
                return "Rekomendasi";
        }
    }
}
