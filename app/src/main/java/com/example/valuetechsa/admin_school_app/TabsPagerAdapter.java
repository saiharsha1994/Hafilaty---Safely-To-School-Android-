package com.example.valuetechsa.admin_school_app;

/**
 * Created by ValueTechSA on 21-02-2017.
 */

        import com.example.valuetechsa.admin_school_app.LeavePageOne;
        import  com.example.valuetechsa.admin_school_app.LeavePageTwo;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new LeavePageOne();
            case 1:
                // Games fragment activity
                return new LeavePageTwo();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}
