package com.example.yossi.ap2_ex4;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.kassisdion.library.ViewPagerWithIndicator;
import android.os.Bundle;


/****************************************************************************
 * This Class will display the page viewer of the 5 explanation fragments
 ***************************************************************************/
public class ExplanationActivity extends FragmentActivity {
    ViewPager viewPager;
    PagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation);
        //Creates a new Adapter for the pageViewer
        adapter =  new PagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        //Creates a PageViewer with an indicator
        ViewPagerWithIndicator wIndicator = (ViewPagerWithIndicator) findViewById(R.id.viewPagerWithIndicator);
        wIndicator.setViewPager(viewPager);
    }
}