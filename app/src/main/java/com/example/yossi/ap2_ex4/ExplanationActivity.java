package com.example.yossi.ap2_ex4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.kassisdion.library.ViewPagerWithIndicator;


public class ExplanationActivity extends FragmentActivity {
    ViewPager viewPager;
    PagerAdapter adapter;
    Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation);
        adapter =  new PagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        ViewPagerWithIndicator vwith = (ViewPagerWithIndicator) findViewById(R.id.viewPagerWithIndicator);
        vwith.setViewPager(viewPager);
    }
}


