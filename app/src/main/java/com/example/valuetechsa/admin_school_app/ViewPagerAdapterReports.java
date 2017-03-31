package com.example.valuetechsa.admin_school_app;

/**
 * Created by ValueTechSA on 21-02-2017.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapterReports extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterReports(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            DistanceByRouteReport distanceByRouteReport= new DistanceByRouteReport();
            return distanceByRouteReport;
        }
        else if(position == 1) // if the position is 0 we are returning the First tab
        {
            DistanceByVechileReport distanceByVechileReport=new DistanceByVechileReport();
            return distanceByVechileReport;
        }
        else if(position == 2) // if the position is 0 we are returning the First tab
        {
            ArivialDepatureReports arivialDepatureReports=new ArivialDepatureReports();
            return arivialDepatureReports;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            OverSpeedingReports overSpeedingReports=new OverSpeedingReports();
            return overSpeedingReports;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}