
package com.example.valuetechsa.admin_school_app.model;


import android.app.Application;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Sajib on 26-10-2016.
 */

 public class ServiceModel extends Application {

    //private variables

    public String name;
    public String bus_id;
    public String driver_id;
    


//
    // Empty constructor
    public ServiceModel(){

    }
    // constructor
    public ServiceModel( String name, String driver_id,String bus_id){

        this.name = name;
        this.driver_id = driver_id;
        this.bus_id=bus_id;


    }



    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    // setting  first name
    public void setName(String name)
    {
        this.name =name;
    }
    public String getDriver_id() {
        // TODO Auto-generated method stub
        return driver_id;
    }

    public void setDriver_id(String driver_id)
    {
        this.driver_id =driver_id;
    }
    public String getBus_id() {
        // TODO Auto-generated method stub
        return bus_id;
    }
    public void setBus_id(String bus_id)
    {
        this.bus_id =bus_id;
    }

}
