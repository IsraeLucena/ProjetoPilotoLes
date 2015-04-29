
package com.br.les.report;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 2:
                // third week fragment activity
                return new ThirdWeek();
            default:
                return new ThirdWeek();
        }

    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
