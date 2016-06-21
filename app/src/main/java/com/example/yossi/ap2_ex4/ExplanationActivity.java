package com.example.yossi.ap2_ex4;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.kassisdion.library.ViewPagerWithIndicator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/****************************************************************************
 * This Class will display the page viewer of the 5 explanation fragments
 ***************************************************************************/
public class ExplanationActivity extends FragmentActivity {
    private ViewPager viewPager;
    private PagerAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explanation);
        //Creates a new Adapter for the pageViewer
        adapter =  new PagerAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        //Creates a PageViewer with an indicator
        ViewPagerWithIndicator wIndicator = (ViewPagerWithIndicator) findViewById(R.id.viewPagerWithIndicator);
        wIndicator.setViewPager(viewPager);

        //This onClick listener will perform the actions for the button
        // on the Explanation pages and will take the user to the Login Screen
        final Button btnSkip = (Button)findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(ExplanationActivity.this,
                        LoginActivity.class));
            }
        });
    }
}