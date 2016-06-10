package com.example.yossi.ap2_ex4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import me.relex.circleindicator.CircleIndicator;

public class ExplanationActivity extends FragmentActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation);
        viewPager = (ViewPager)findViewById(R.id.pager);
        //CircleIndicator indicator = (CircleIndicator) viewPager.findViewById(R.id.indicator);
        PagerAdapter pAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pAdapter);
        //indicator.setViewPager(viewPager);
    }
}


