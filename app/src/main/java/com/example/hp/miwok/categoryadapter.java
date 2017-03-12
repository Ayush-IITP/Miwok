package com.example.hp.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Hp on 12-03-2017.
 */

public class categoryadapter extends FragmentPagerAdapter {
    private Context mContext;
    public categoryadapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "Numbers";
        else if(position == 1)
            return "Colors";
        else if(position == 2)
            return  "Family";
        else
            return  "Phrase";
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new NumbersFragment();
        else if(position == 1)
            return new colors_fragment();
        else if(position == 2)
            return new family_fragment();
        else
            return new phrase_fragment();

    }

    @Override
    public int getCount() {
        return 4;
    }
}
