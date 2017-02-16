package com.example.valuetechsa.admin_school_app;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.valuetechsa.admin_school_app.R;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class Students_Selection_Search_Admin extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener,OnMarkerClickListener {

    private GoogleMap mMap;
    String[] cordinateList=new String[100];
    String x="",y="";
    String globalstop="default";
    Double xl,yl,xlc,ylc;
    Point p;
    LatLng globallatlng;
    ListView listViewstudents ;
    Button btnClosePopup;
    ArrayList<String> list=new ArrayList<String>();
    ArrayList<String> longlist=new ArrayList<String>();
    ArrayList<String> latlist=new ArrayList<String>();
    ArrayList<String> stops=new ArrayList<String>();
    ArrayList<String> colorchangelat=new ArrayList<String>();
    ArrayList<String> colorchangelan=new ArrayList<String>();
    private PopupWindow pwindo;
    private EditText searchBox;
    ArrayList<String> selectedstudents=new ArrayList<String>();
    private List<Marker> studentMarkers = new ArrayList<>();
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students__selection__search__admin);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        searchBox=(EditText) findViewById(R.id.editsearchbox);
        searchBox.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            findLocation();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        listViewstudents=(ListView) findViewById(R.id.studentlist);
        String[] students = new String[] { "Student1",
                "Student2",
                "Student3",
                "Student4",
                "Student5",
                "Student6",
                "Student7",
                "Student8"
        };
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, students);
        listViewstudents.setAdapter(adapter2);
        new getMapDataToServer().execute();
    }

    class getMapDataToServer extends AsyncTask<Void,Void,Void>
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
                    Log.e("url", Config.ip+"Students_api/allStudents");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Students_api/allStudents",Jsonfunctions.GET);

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
                                   // String stop=obj.getString("stop_name");
                                    String stop="student stop";
                                    //Log.e("+++",obj.getString("stop_name"));
                                    String Latitude=obj.getString("Latitude");
                                    Log.e("+++",obj.getString("Latitude"));
                                    String Langitude=obj.getString("Longitude");
                                    String Latlong=(Latitude+","+Langitude);
                                    Log.e("LatLong.......",Latlong);
                                    latlist.add(Latitude);
                                    longlist.add(Langitude);
                                    stops.add(stop);
                                    list.add(Latlong);

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

    class searchprogessbox extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute()
        {
            sendRequestsearch();
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {

            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {

            progressDialog.dismiss();
        }
    }

    class progessdialogbox extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute()
        {
            sendRequest();
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {

            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {

            progressDialog.dismiss();
        }
    }


    private void sendRequest() {
        progressDialog = ProgressDialog.show(Students_Selection_Search_Admin.this, "Please wait.",
                "Finding direction..!", true);
        for (int i=0;i<selectedstudents.size()-1;i++) {

            String xll=selectedstudents.get(i);
            String yll=selectedstudents.get(i+1);
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

        cordinates();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    public void cordinates(){
        for(int i=0;i<latlist.size();i++) {
            xl = Double.parseDouble(latlist.get(i));
            yl = Double.parseDouble(longlist.get(i));
            LatLng hcmus = new LatLng(xl, yl);
            Log.e(xl + "Latitudes in loop", latlist.get(i));
            Log.e(yl + "Longitudes in loop", longlist.get(i));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));
            studentMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(stops.get(i))
                    .position(hcmus)));
        }
//        if(!globalstop.equals("default")) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(globallatlng,15));
//            studentMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
//                    .title(globalstop)
//                    .position(globallatlng)));
//        }
        for(int f=0;f<selectedstudents.size();f++){
            xlc = Double.parseDouble(colorchangelat.get(f));
            ylc = Double.parseDouble(colorchangelan.get(f));
            LatLng hcmus = new LatLng(xlc, ylc);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));
            studentMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(stops.get(f))
                    .position(hcmus)));

        }
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int[] location = new int[2];
        double dlat =marker.getPosition().latitude;
        double dlon =marker.getPosition().longitude;
        globalstop= marker.getTitle();
        String slat = String.valueOf(dlat);
        String slon = String.valueOf(dlon);
        String latlong=slat+","+slon;
        globallatlng= new LatLng(dlat,dlon);
        selectedstudents.add(latlong);
        colorchangelat.add(slat);
        colorchangelan.add(slon);
        Log.e("Stop name :",globalstop);
        Log.e("Latitudes in listener",slat);
        Log.e("Longitudes in listener", slon);
        initiatePopupWindow();
        //sendRequest();
        return true;
    }

    public void findLocation() {
        new searchprogessbox().execute();
    }
    private void sendRequestsearch() {
        progressDialog = ProgressDialog.show(Students_Selection_Search_Admin.this, "Please wait.",
                "Finding direction..!", true);
        String typedlocation = searchBox.getText().toString();
        try {
            new DirectionFinder(this, typedlocation, typedlocation).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
                    .visible(false)
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .visible(false)
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
    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) Students_Selection_Search_Admin.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 500,300, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
           // btnClosePopup.setOnClickListener(cancel_button_click_listener);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    //sendRequest();
                    new progessdialogbox().execute();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

