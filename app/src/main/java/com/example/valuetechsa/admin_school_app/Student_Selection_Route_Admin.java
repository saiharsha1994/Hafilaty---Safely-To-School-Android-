package com.example.valuetechsa.admin_school_app;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;

public class Student_Selection_Route_Admin extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText txtTime,txtTimed;
    private int mHour, mMinute, mHourd, mMinuted;
    ListView listViewroutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__selection__route__admin);
        //Time Picker
        txtTime=(EditText)findViewById(R.id.endtimebox);
        txtTimed=(EditText)findViewById(R.id.starttimebox);
        Log.i("harsha","Inside Oncreate");
        System.out.println("Inside Oncreate");
        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
           //     .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
//        Spinner dropdown = (Spinner)findViewById(R.id.tripbox);
//        String[] items = new String[]{"Morning","Evening"};
//        Spinner dropdowndriver = (Spinner)findViewById(R.id.driverdropdown);
//        String[] itemsdriver = new String[]{"Driver 1","Driver 2","Driver 3","Driver 4"};
//        Spinner dropdownbus = (Spinner)findViewById(R.id.busdropdown);
//        String[] itemsbus = new String[]{"Bus 1","Bus 2","Bus 3","Bus 4"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        ArrayAdapter<String> adapterdriver = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsdriver);
//        ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsbus);
//        dropdown.setAdapter(adapter);
//        dropdowndriver.setAdapter(adapterdriver);
//        dropdownbus.setAdapter(adapterbus);
       //listViewroutes = (ListView) findViewById(R.id.routecreatedlist);
        String[] routes = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View","Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        /*ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, routes);
        listViewroutes.setAdapter(adapter1);
        listViewroutes.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent myIntent = new Intent(view.getContext(),Student_Selection_Search_Admin_Navigation.class);
                        startActivityForResult(myIntent, 0);
                    }
                });*/

        ArrayList<SearchResultRoute> searchResults = GetSearchResults();
        final ListView lv1 = (ListView) findViewById(R.id.routecreatedlist);
        lv1.setAdapter(new MyCustomBaseRouteAdapter(this, searchResults));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private ArrayList<SearchResultRoute> GetSearchResults(){
        ArrayList<SearchResultRoute> results = new ArrayList<SearchResultRoute>();

        SearchResultRoute sr1 = new SearchResultRoute();
       /* sr1.setName("John Smith");
        sr1.setCityState("Dallas, TX");
        sr1.setPhone("214-555-1234");
        results.add(sr1);

        sr1 = new SearchResultRoute();
        sr1.setName("Jane Doe");
        sr1.setCityState("Atlanta, GA");
        sr1.setPhone("469-555-2587");
        results.add(sr1);

        sr1 = new SearchResultRoute();
        sr1.setName("Steve Young");
        sr1.setCityState("Miami, FL");
        sr1.setPhone("305-555-7895");
        results.add(sr1);

        sr1 = new SearchResultRoute();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);

        sr1.setName("John Smith");
        sr1.setCityState("Dallas, TX");
        sr1.setPhone("214-555-1234");
        results.add(sr1);

        sr1 = new SearchResultRoute();
        sr1.setName("Jane Doe");
        sr1.setCityState("Atlanta, GA");
        sr1.setPhone("469-555-2587");
        results.add(sr1);

        sr1 = new SearchResultRoute();
        sr1.setName("Steve Young");
        sr1.setCityState("Miami, FL");
        sr1.setPhone("305-555-7895");
        results.add(sr1);

        sr1 = new SearchResultRoute();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);
*/
        return results;
    }
    public void selectTimer(View v) {
        Log.i("harsha","Inside view");
        System.out.println("Inside View");
        if (1==1) {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

    }

    public void selectTimerd(View v) {
        Log.i("harsha","Inside view");
        System.out.println("Inside View");
        if (1==1) {
            final Calendar c = Calendar.getInstance();
            mHourd = c.get(Calendar.HOUR_OF_DAY);
            mMinuted = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTimed.setText(hourOfDay + ":" + minute);
                        }
                    }, mHourd, mMinuted, false);
            timePickerDialog.show();
        }

    }

    public void selectTimerdend(View v) {
        Log.i("harsha","Inside view");
        System.out.println("Inside View");
        if (1==1) {
            final Calendar c = Calendar.getInstance();
            mHourd = c.get(Calendar.HOUR_OF_DAY);
            mMinuted = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHourd, mMinuted, false);
            timePickerDialog.show();
        }

    }

    public void gotoselection(View view){
        Intent intent = new Intent(this, Student_Selection_Search_Admin_Navigation.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
