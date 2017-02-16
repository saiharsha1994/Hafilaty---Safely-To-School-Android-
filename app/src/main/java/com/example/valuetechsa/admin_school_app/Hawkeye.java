package com.example.valuetechsa.admin_school_app;

import android.*;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class Hawkeye extends FragmentActivity implements OnMapReadyCallback,OnMarkerClickListener, DirectionFinderListener {

    private GoogleMap mMap;
    double xl,yl;
    private List<Marker> busallMarkers = new ArrayList<>();
    ArrayList<String> buslatlist=new ArrayList<String>();
    ArrayList<String> buslat=new ArrayList<String>();
    ArrayList<String> buslong=new ArrayList<String>();
    ArrayList<String> routelatlist=new ArrayList<String>();
    ArrayList<String> routelat=new ArrayList<String>();
    ArrayList<String> routelong=new ArrayList<String>();
    ArrayList<String> routestops=new ArrayList<String>();
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hawkeye);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        new getMapDataToServerAllBuses().execute();
        ArrayList<SearchResults> searchResults = GetSearchResults();

        final ListView lv1 = (ListView) findViewById(R.id.studentlist);
        lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));
        //new getMapDataToServerSingle().execute();
    }
    private ArrayList<SearchResults> GetSearchResults(){
        ArrayList<SearchResults> results = new ArrayList<SearchResults>();

       /* SearchResults sr1 = new SearchResults();
        sr1.setName("John Smith");
        sr1.setCityState("Dallas, TX");
        sr1.setPhone("214-555-1234");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Jane Doe");
        sr1.setCityState("Atlanta, GA");
        sr1.setPhone("469-555-2587");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Steve Young");
        sr1.setCityState("Miami, FL");
        sr1.setPhone("305-555-7895");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);

        sr1 = new SearchResults();
        sr1.setName("Fred Jones");
        sr1.setCityState("Las Vegas, NV");
        sr1.setPhone("612-555-8214");
        results.add(sr1);*/

        int x=1;
        int x2=3;
        return results;
    }
    class getMapDataToServerAllBuses extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"BusCoordinates_api/getAllBusCoordinates");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"BusCoordinates_api/getAllBusCoordinates",Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                JSONArray jsonArray = Jobj.getJSONArray("result_arr");

                                for (int j = 0; j < jsonArray.length(); j++)
                                {
                                    JSONObject obj = jsonArray.getJSONObject(j);
                                      String Latitude=obj.getString("latitude");
                                      Log.e("+++",obj.getString("latitude"));
                                      String Langitude=obj.getString("langitude");
                                      Log.e("+++",obj.getString("langitude"));
                                      String Latlong=(Latitude+","+Langitude);
                                      Log.e("LatLong.......",Latlong);
                                      Log.e("LatLong.......",Latlong);
                                      Log.e("LatLong.......",Latlong);
                                      /*buslatlist.add(Latlong);
                                      *//*buslat.add(Latitude);
                                      buslong.add(Langitude);*/
                                }


                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Log.e("ServiceHandler", "Couldn't get any data from the url");
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            cordinates();
        }
    }
    class getMapDataToServerSingle extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(Hawkeye.this, "Please wait.",
                    "Finding direction..!", true);
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"GetRouteDetails_api/getRouteForAdmin/route_id/1");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"GetRouteDetails_api/getRouteForAdmin/route_id/1",Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                JSONArray jsonArray = Jobj.getJSONArray("result_arr");

                                for (int j = 0; j < jsonArray.length(); j++)
                                {
                                    JSONObject obj = jsonArray.getJSONObject(j);
                                      String stop=obj.getString("stop_name");
                                      Log.e("+++",obj.getString("stop_name"));
                                      String Latitude=obj.getString("latitude");
                                      Log.e("+++",obj.getString("latitude"));
                                      String Langitude=obj.getString("langitude");
                                      Log.e("+++",obj.getString("langitude"));
                                      String Latlong=(Latitude+","+Langitude);
                                      Log.e("LatLong.......",Latlong);
                                      routelatlist.add(Latlong);
                                      routelat.add(Latitude);
                                      routelong.add(Langitude);
                                      routestops.add(stop);
                                }


                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Log.e("ServiceHandler", "Couldn't get any data from the url");
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            sendRequest();
            progressDialog.dismiss();
        }
    }

    public void cordinates(){
        for(int i=0;i<buslatlist.size();i++) {
            xl = Double.parseDouble(buslat.get(i));
            yl = Double.parseDouble(buslong.get(i));
            LatLng hcmus = new LatLng(xl, yl);
            Log.e(xl + "Latitudes in loop", buslat.get(i));
            Log.e(yl + "Longitudes in loop", buslong.get(i));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));
            busallMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title("test stop")
                    .position(hcmus)));
        }
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        new getMapDataToServerSingle().execute();
        return true;
    }

    private void sendRequest() {
        for (int i=0;i<2;i++) {

            String xll=routelatlist.get(i);
            String yll=routelatlist.get(i+1);
            if(yll.isEmpty())
                break;
            String origin = xll;
            String destination = yll;
            try {
                new DirectionFinder(this, origin, destination).execute();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onDirectionFinderStart() {
        //progressDialog = ProgressDialog.show(this, "Please wait.",
        //      "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        //progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 14));

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

}
