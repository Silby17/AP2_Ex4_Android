package com.example.yossi.ap2_ex4;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Button;

public class PagerAdapter extends FragmentPagerAdapter{
    private Activity activity;
    public PagerAdapter(FragmentManager fm, Activity activity){
        super(fm);
        this.activity = activity;
    }


    /**********************************************************************
     * This will have all the Fragments called on each swipe
     * @param position
     * @return
     *********************************************************************/
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            case 3:
                return new FragmentFour();
            case 4:
                ((Button)activity.findViewById(R.id.btnSkip)).setText(R.string.Continue);
                return new FragmentFive();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}