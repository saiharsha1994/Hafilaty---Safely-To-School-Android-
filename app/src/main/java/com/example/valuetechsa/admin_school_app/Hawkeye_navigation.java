package com.example.valuetechsa.admin_school_app;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.DB.DatabaseAdapter;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class Hawkeye_navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,GoogleMap.OnMarkerClickListener, DirectionFinderListener {
    private GoogleMap mMap;
    private Circle mCircle;
    private Marker mMarker;
    double xl,yl;
    double schoollat,schoollong;
    int prevcolor;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    private List<Marker> busallMarkers = new ArrayList<>();
    ArrayList<String> buslatlist=new ArrayList<String>();
    ArrayList<String> buslat=new ArrayList<String>();
    ArrayList<String> buslong=new ArrayList<String>();
    ArrayList<String> busvechileno=new ArrayList<String>();
    ArrayList<String> busroutename=new ArrayList<String>();
    ArrayList<String> busdrivername=new ArrayList<String>();
    ArrayList<String> busdriverno=new ArrayList<String>();
    ArrayList<String> drivernamefromserverhawkeye=new ArrayList<String>();
    ArrayList<String> driveridfromserverhawkeye=new ArrayList<String>();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //String globalbusroutename,globalbusrouteno,globaldrivername,globaldriverno;
    int globalcountall=0,globalresponse=0;
    ArrayList<String> globalbusroutename=new ArrayList<String>();
    ArrayList<String> globalbusrouteno=new ArrayList<String>();
    ArrayList<String> globaldrivername=new ArrayList<String>();
    ArrayList<String> globaldriverno=new ArrayList<String>();
    ProgressDialog progressDialog1;
    private PopupWindow pwindo;
    String sheadertop;
    String clickdrivername,clickdriverno;
    boolean clickschool=false;
    int checkinttag=1000000;
    int rout=0;
    int markerclick=0,turn=0;
    String alert_title="",alert_message="",alert_driver="",alert_bus="",alert_timestamp="";
    int speedlimit=35;
    int check=0;
    AsyncTask<Void,Void,Void> getBus;

    /*ArrayList<String> testbuslatlist=new ArrayList<String>();
    ArrayList<String> testbuslat=new ArrayList<String>();
    ArrayList<String> testbuslong=new ArrayList<String>();
    ArrayList<String> testbusvechileno=new ArrayList<String>();
    ArrayList<String> testbusdrivername=new ArrayList<String>();*/
    ArrayList<String> routelatlist=new ArrayList<String>();
    ArrayList<String> routelat=new ArrayList<String>();
    ArrayList<String> routelong=new ArrayList<String>();
    ArrayList<String> routestops=new ArrayList<String>();
    static double coo=0;
    Thread fivemins;
    boolean threadloop=true;
    int[] colorchangearray=new int[100000];
    int[] colorchangebusarray=new int[100000];
    int[] bordercrossed=new int[100000];
    int[] speedcrossed=new int[100000];
    int colorhangecount;
    String nameuser,passuser,adminid,adminname,schoolcordinates;
    String[] schoolcordarray=new String[100000];
    int zoom=0;
    Typeface tfRobo;
    int stop=0;
    Button btnClosePopup,btnAlertPopup;
    TextView topRow,drivernamehawkeyetextbox,drivercontactnohawkeyetextbox,alertredtextbox;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    Handler mHandler;
    Runnable mHandlerTask;
    int alertShown=0;
    int oldsize=0;
    FrameLayout layout_MainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbh = new DatabaseAdapter(this);
        db = dbh.getWritableDatabase();
         Cursor cur1=db.rawQuery("SELECT * FROM language", null);
        cur1.moveToFirst();
        if(cur1.getCount()!=0){
            String language=cur1.getString(2);
            Log.e("language","^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+language);
            change_language(language);

        }
        setContentView(R.layout.activity_hawkeye_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* Cursor cur1=db.rawQuery("SELECT * FROM language", null);
        cur1.moveToFirst();
        if(cur1.getCount()!=0){
            String language=cur1.getString(2);
            String languageToLoad  = language; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            Hawkeye_navigation.this.getResources().updateConfiguration(config,
                    Hawkeye_navigation.this.getResources().getDisplayMetrics());
            Intent intent = new Intent(Hawkeye_navigation.this, Hawkeye_navigation.class);
            startActivity(intent);
            finish();
        }*/

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.menu, this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.dashboardheader);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(navUsername.getText());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        navUsername.setText(mNewTitle);
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }

        layout_MainMenu = (FrameLayout) findViewById( R.id.outerlayout);
        layout_MainMenu.getForeground().setAlpha( 0);

        TextView TextViewNewFont = new TextView(Hawkeye_navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_dashboard));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Hawkeye_navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        TextViewNewFont.setTypeface(tfRobo);

        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
        fontdoitfirst();
        /*action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp; DASHBOARD</big></font>"));*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        try {
            Cursor cur=db.rawQuery("SELECT * FROM OneTimeLogin", null);
            cur.moveToFirst();
            if(cur.getCount()!=0){
                nameuser=cur.getString(1);
                passuser=cur.getString(2);
                adminid=cur.getString(3);
                adminname=cur.getString(4);
                schoolcordinates=cur.getString(5);
                Log.e("DATABASE",nameuser);
                Log.e("DATABASE",passuser);
                Log.e("DATABASE",adminid);
                Log.e("DATABASE",adminname);
                Log.e("DATABASE",schoolcordinates);
                String schoolcordarray1[]=schoolcordinates.split("\\s*,\\s*");
                schoolcordarray[0]=schoolcordarray1[0];
                schoolcordarray[1]=schoolcordarray1[1];
                Log.e("DATABASE",schoolcordarray[0]);
                Log.e("DATABASE",schoolcordarray[1]);
                schoollat=Double.parseDouble(schoolcordarray[0]);
                schoollong=Double.parseDouble(schoolcordarray[1]);

            }
        }
        catch (Exception e){

        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String title = intent.getStringExtra("title");
                    String message = intent.getStringExtra("message");
                    String driver_name = intent.getStringExtra("driver_name");
                    String bus_name = intent.getStringExtra("bus_name");
                    String timestamp = intent.getStringExtra("timestamp");
                    initiatePopupWindowhawkeyeAlert(title,driver_name,bus_name,timestamp);

                    //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        speedlimit = Integer.parseInt(preferences.getString("speed_limit",""));
        Log.e("speed",speedlimit+"");

        Bundle extras = getIntent().getExtras();
        Log.e("","extras");
        if(extras !=null) {
            alert_title = extras.getString("title");
            alert_message = extras.getString("message");
            alert_driver= extras.getString("driver_name");
            alert_bus= extras.getString("bus_name");
            alert_timestamp=extras.getString("timestamp");

        }

        new getDriverListFromServer().execute();
        new getdelay().execute();
        /*fivemins=new Thread(new Runnable() {

            @Override
            public void run() {
                Log.e("is it working","let us see"+threadloop);
                while (threadloop)
                    try
                    {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {

                            @Override
                            public void run()
                            {
                                if(threadloop){
                                    Log.e("check every 5 sec","++++++++++++++++++++++++++++++++++++++++++");
                                    //coo=coo+0.005;
                                    clearall();
                                    new getMapDataToServerAllBuses().execute();// this action have to be in UI thread
                                }
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        });
        fivemins.start();*/


        /*final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1 * 10 * 1000); // every 2 minutes
                new getMapDataToServerAllBuses().execute();

            }
        }, 0);*/
        //Log.e("test","test");






    }

    public void clearall()
    {
        busroutename.clear();
        busvechileno.clear();
        busdrivername.clear();
        busdriverno.clear();
        buslatlist.clear();
        buslat.clear();
        buslong.clear();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void fontdoitfirst(){
        TextView buslist=(TextView)findViewById(R.id.selectedstudent);
        TextView livemap=(TextView)findViewById(R.id.selectstudentlayout);
        buslist.setTypeface(tfRobo);
        livemap.setTypeface(tfRobo);
    }

    private void drawMarkerWithCircle(LatLng position){
        double radiusInMeters = 50000.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ffffff; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position).radius(radiusInMeters).strokeColor(strokeColor).strokeWidth(4);
        mCircle = mMap.addCircle(circleOptions);

        /* CircleOptions circleOptions = new CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);

        MarkerOptions markerOptions = new MarkerOptions().position(position);
        mMarker = mMap.addMarker(markerOptions);*/
    }

    public static double haversine(
            double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }

    public void leftside(){
        ArrayList<SearchResults> searchResults = GetSearchResults();
        final ListView lv1 = (ListView) findViewById(R.id.studentlist);
        //lv1.setAdapter(null);
        lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));
    }



    private ArrayList<SearchResults> GetSearchResults(){
        ArrayList<SearchResults> results = new ArrayList<SearchResults>();
        for(int i=0;i<busvechileno.size();i++){
            SearchResults sr1 = new SearchResults();

            sr1.setRoutename(busroutename.get(i));
            sr1.setPlateno(busvechileno.get(i));
            sr1.setDrivername(busdrivername.get(i));
            sr1.setColorchanege(colorchangearray[i]);
            results.add(sr1);
        }
        return results;
    }


    class getDriverListFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"DriverList_api/listDrivers");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"DriverList_api/listDrivers",Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                JSONArray jsonArray = Jobj.getJSONArray("result_arr");

                                for (int j = 0; j < jsonArray.length(); j++){

                                    JSONObject obj = jsonArray.getJSONObject(j);
                                    Log.e("+++",obj.getString("name"));
                                    String drivername=obj.getString("name");
                                    drivernamefromserverhawkeye.add(drivername);
                                    Log.e("+++",obj.getString("driver_id"));
                                    String driverId=obj.getString("driver_id");
                                    driveridfromserverhawkeye.add(driverId);
//                                    Log.e("+++",obj.getString("student_id"));
//                                    Log.e("+++",obj.getString("att_date"));
//                                    Log.e("+++",obj.getString("att_month"));

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
        protected void onPostExecute(Void result){

        }
    }

    class getdelay extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {
            progressDialog1 = ProgressDialog.show(Hawkeye_navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            if ((progressDialog1 != null) && progressDialog1.isShowing()) {
                progressDialog1.dismiss();
            }
            if(!(alert_title.isEmpty()))
                initiatePopupWindowhawkeyeAlert(alert_title,alert_driver,alert_bus,alert_timestamp);
        }
    }

    class getMapDataToServerAllBuses extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {
            check=1;
            busroutename.clear();
            busvechileno.clear();
            busdrivername.clear();
            busdriverno.clear();
            buslatlist.clear();
            buslat.clear();
            buslong.clear();

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
                                    globalresponse=jsonArray.length();
                                    Log.e("^^^","^^^^^^^^^^^^^^^^^^^^response^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+globalresponse);
                                    String Latitude=obj.getString("latitude");
                                    Log.e("+++",obj.getString("latitude"));
                                    String Langitude=obj.getString("langitude");
                                    Double www=Double.parseDouble(Latitude);
                                   /* www=www+coo;
                                    Latitude=www+"";*/
                                    Double wwww=Double.parseDouble(Langitude);
                                    /*wwww=wwww+coo;
                                    Langitude=wwww+"";*/
                                    /*Double ttypass=Double.parseDouble(obj.getString("langitude"))+rout;
                                    String Langitude=ttypass+"";*/
                                    Log.e("+++",obj.getString("langitude"));
                                    String Latlong=(Latitude+","+Langitude);
                                    Log.e("LatLong.......",Latlong);
                                    String numberplate=obj.getString("plate_number");
                                    Log.e("+++",obj.getString("plate_number"));
                                    String drivername=obj.getString("driver_name");
                                    Log.e("+++",obj.getString("driver_name"));
                                    String routename=obj.getString("pickup_route");
                                    Log.e("+++",obj.getString("pickup_route"));
                                    String busspeed=obj.getString("cur_speed");
                                    Log.e("+++",obj.getString("cur_speed"));
                                    String driverno=obj.getString("driver_mobile");
                                    Log.e("+++",obj.getString("driver_mobile"));
                                    busroutename.add(routename);
                                    globalbusroutename.add(routename);
                                    busvechileno.add(numberplate);
                                    globalbusrouteno.add(numberplate);
                                    busdrivername.add(drivername);
                                    globaldrivername.add(drivername);
                                    busdriverno.add(driverno);
                                    globaldriverno.add(driverno);
                                    buslatlist.add(Latlong);
                                    buslat.add(Latitude);
                                    buslong.add(Langitude);
                                    colorchangearray[j]=0;
                                    colorchangebusarray[j]=0;
                                    bordercrossed[j]=0;
                                    speedcrossed[j]=0;
                                    colorhangecount=j;
                                    globalcountall=globalcountall+1;
                                    Log.e("^^^","^^^^^^^^^^^^^^^^^^count^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+globalcountall);
                                    Double dl=Double.parseDouble(Latitude);
                                    Double dlo=Double.parseDouble(Langitude);
                                    Log.e("before","sine");
                                    Double km = haversine(schoollat,schoollong,dl,dlo);
                                    Log.e("after","sine");
                                    Double doublebusspeed=Double.parseDouble(busspeed);
                                    Log.e("DISTANCE",km+"");
                                    if(km>50){
                                        colorchangearray[j]=1;
                                        colorchangebusarray[j]=1;
                                        bordercrossed[j]=1;
                                    }
                                    if(doublebusspeed>speedlimit){
                                        colorchangearray[j]=1;
                                        colorchangebusarray[j]=1;
                                        speedcrossed[j]=1;
                                    }
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
            check=0;
            plotCoordinates();


           /* for(int tt=0;tt<colorhangecount;tt++){
                busvechileno.add(testbusvechileno.get(tt));
                busdrivername.add(testbusdrivername.get(tt));
                buslatlist.add(testbuslatlist.get(tt));
                buslat.add(testbuslat.get(tt));
                buslong.add(testbuslong.get(tt));
            }*/

            /*if(markerclick==0&&turn==0){
                Log.e("$$$",markerclick+"&&&&&&&&&&&&&&&&&&&&&marker$$$$$$$$$$4444"+turn);
                rout=rout+10;
                if(checkinttag<1000000){
                    colorchangearray[checkinttag]=0;
                    colorchangebusarray[checkinttag]=0;
                }
                leftside();
                if(zoom==0){
                    cordinateszoom();
                }
                else if(zoom==1){
                    cordinates();
                }
            }
            else if(markerclick==1&&turn==1){
                Log.e("$$$",markerclick+"&&&&&&&&&&&&&&&&&&&&&marker$$$$$$$$$$4444"+turn);
                rout=rout+10;
                if(checkinttag<1000000){
                    colorchangearray[checkinttag]=1;
                    colorchangebusarray[checkinttag]=1;
                }
                leftside();
                if(zoom==0){
                    cordinateszoom();
                }
                else if(zoom==1){
                    cordinates();
                }

            }
            else if(markerclick==0){
                Log.e("$$$",markerclick+"&&&&&&&&&&&&&&&&&&&&&marker$$$$$$$$$$4444"+turn);
                turn=0;
            }*/
        }
    }

    public void plotCoordinates()
    {
        leftside();
        if(zoom==0){
            cordinateszoom();
        }
        else if(zoom==1){
            cordinates();
        }
    }

    class getMapDataToServerSingle extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(Hawkeye_navigation.this, "Please wait.",
                    "Finding direction..!", true);
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
           /* try
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
            }*/
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            leftside();
            //sendRequest();
            progressDialog.dismiss();
        }
    }

    public void cordinates(){
        //mMap.clear();
        LatLng latLng = new LatLng(schoollat, schoollong);
        Marker m= null;
        removeMarkers();
        for(int i=0;i<buslatlist.size();i++) {
            xl = Double.parseDouble(buslat.get(i));
            yl = Double.parseDouble(buslong.get(i));
            LatLng hcmus = new LatLng(xl, yl);
            Log.e(xl + "Latitudes in loop", buslat.get(i));
            Log.e(yl + "Longitudes in loop", buslong.get(i));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));
            if (colorchangebusarray[i] == 0) {
                m=(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_bus))
                        .title("test stop")
                        .position(hcmus)));
                m.setTag(i);
                busallMarkers.add(m);
            }
            else{
                m=(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_bus))
                        .title("test stop")
                        .position(hcmus)));
                m.setTag(i);
                busallMarkers.add(m);

            }

            /*busallMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title("test stop")
                    .position(hcmus)));*/
        }
        zoom=1;
        //LatLng latLng = new LatLng(schoollat, schoollong);
        if(buslatlist.size()!=oldsize&&buslatlist.size()>=1) {
            oldsize=buslatlist.size();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : busallMarkers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int padding = 100; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            if(buslatlist.size()==1) {
                CameraUpdate cu2 = CameraUpdateFactory.newLatLngZoom(m.getPosition(), 15F);
                mMap.animateCamera(cu2);
            }
            else
                mMap.animateCamera(cu);
        }
    }

    public void cordinateszoom(){
        mMap.clear();
        busallMarkers.clear();
        LatLng latLng = new LatLng(schoollat, schoollong);
        Marker m=null;
        for(int i=0;i<buslatlist.size();i++) {
            xl = Double.parseDouble(buslat.get(i));
            yl = Double.parseDouble(buslong.get(i));
            LatLng hcmus = new LatLng(xl, yl);
            Log.e(xl + "Latitudes in loop", buslat.get(i));
            Log.e(yl + "Longitudes in loop", buslong.get(i));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus,8));
            if (colorchangebusarray[i] == 0) {
                m=(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_bus))
                        .title("test stop")
                        .position(hcmus)));
                m.setTag(i);
                busallMarkers.add(m);
            }
            else{
                m=(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.red_bus))
                        .title("test stop")
                        .position(hcmus)));
                m.setTag(i);
                busallMarkers.add(m);
            }



            /*busallMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title("test stop")
                    .position(hcmus)));*/
        }
        zoom=1;

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
        /*Marker m=(mMap.addMarker(new MarkerOptions()
                .title("International Indian School Dammam")
                .position(latLng)));
        m.setTag(99999);
        m.showInfoWindow();
        clickschool=true;
        drawMarkerWithCircle(latLng);*/
        oldsize=buslatlist.size();
        if(buslatlist.size()==0)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15F));
        if(buslatlist.size()>=1) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : busallMarkers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int padding = 100; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

            if(oldsize==1) {
                CameraUpdate cu2 = CameraUpdateFactory.newLatLngZoom(m.getPosition(), 15F);
                mMap.animateCamera(cu2);
            }
            else
                mMap.animateCamera(cu);
        }
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%clicked %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%55");


            /*for(int iii=0;iii<colorhangecount;iii++){
                colorchangearray[iii]=0;
            }*/
            //threadloop=false;
            checkinttag=(int)marker.getTag();
            if(checkinttag==99999){
                marker.showInfoWindow();
            }
        else {
                Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%"+checkinttag);
                //colorchangearray[checkinttag]=1;
                /*while(busroutename.isEmpty());
                sheadertop=busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")";
                clickdrivername=busdrivername.get(checkinttag);
                clickdriverno=busdriverno.get(checkinttag);*/
                int id=globalcountall-(globalresponse);
                int countid=id+checkinttag;
                sheadertop=globalbusroutename.get(countid)+"("+globalbusrouteno.get(countid)+")";
                clickdrivername=globaldrivername.get(countid);
                clickdriverno=globaldriverno.get(countid);
                //markerclick=1;
                //turn=1;
                /*sheadertop="111";
                clickdrivername="222222222";
                clickdriverno="3333333333333333";*/
                //clearall();
                //new getMapDataToServerAllBuses().execute();
                //new getMapDataToServerSingle().execute();
                /*ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(fivemins);*/
                initiatePopupWindowhawkeye();
            }
        return true;
    }

    private void removeMarkers() {
        for (Marker marker : busallMarkers) {
            marker.remove();
        }
        busallMarkers.clear();
    }
    private void sendRequest() {
        for (int i=0;i<routestops.size()-1;i++) {

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
            mMap.setOnMarkerClickListener(this);
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

    private void initiatePopupWindowhawkeye() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater1 = (LayoutInflater) Hawkeye_navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            //final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            //fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);
            LayoutInflater inflater = (LayoutInflater) Hawkeye_navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_hawkeye,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 500,380, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            layout_MainMenu.getForeground().setAlpha(200);
            //Log.e("Lets Check",busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")");
            topRow=(TextView)layout.findViewById(R.id.blueStudentnametexthawkeye);
            drivernamehawkeyetextbox=(TextView)layout.findViewById(R.id.txtViewdrivername);
            drivercontactnohawkeyetextbox=(TextView)layout.findViewById(R.id.txtViewdrivercontact);
            alertredtextbox=(TextView)layout.findViewById(R.id.txtalerttext);
            drivernamehawkeyetextbox.setText(getResources().getString(R.string.stv_driver_name)+": "+clickdrivername);
            drivercontactnohawkeyetextbox.setText(getResources().getString(R.string.stv_contact_no)+": "+clickdriverno);

            topRow.setText(sheadertop);
            if(bordercrossed[checkinttag]==1 && speedcrossed[checkinttag]==1){
                alertredtextbox.setText(getResources().getString(R.string.sj_bus_crossing_boundary_and_speeding));
            }
            else if(bordercrossed[checkinttag]==1){
                alertredtextbox.setText(getResources().getString(R.string.sj_bus_crossing_boundary));
            }
            else if(speedcrossed[checkinttag]==1){
                alertredtextbox.setText(getResources().getString(R.string.sj_speeding_alert));
            }
            btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_hawkeye);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout_MainMenu.getForeground().setAlpha(0);
                    pwindo.dismiss();
                    //fadePopup.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initiatePopupWindowhawkeyeAlert(String title,String driver, String bus, String timestamp) {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater1 = (LayoutInflater) Hawkeye_navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            /*final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/

            LayoutInflater inflater = (LayoutInflater) Hawkeye_navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.screen_popup_alert,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, 500,380, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            layout_MainMenu.getForeground().setAlpha(200);
            //Log.e("Lets Check",busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")");
            topRow=(TextView)layout.findViewById(R.id.blueStudentnametexthawkeye);
            drivernamehawkeyetextbox=(TextView)layout.findViewById(R.id.txtViewdrivername);
            drivercontactnohawkeyetextbox=(TextView)layout.findViewById(R.id.txtViewdrivercontact);
            alertredtextbox=(TextView)layout.findViewById(R.id.txtViewtimestamp);
            if(driver.isEmpty())
                drivernamehawkeyetextbox.setVisibility(View.INVISIBLE);
            else
                drivernamehawkeyetextbox.setText("Driver Name: "+driver);
            drivercontactnohawkeyetextbox.setText("Bus Name: "+bus);
            alertredtextbox.setText("Timestamp: "+timestamp);
            topRow.setText(title);

            btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_hawkeye);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout_MainMenu.getForeground().setAlpha(0);
                    pwindo.dismiss();
                    //fadePopup.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            threadloop=true;
            *//*startActivity(new Intent(Hawkeye_navigation.this, LoginAdmin.class));
            finish();*//*
        }*/
        /*Log.e("Back pressed","_________________________________");
        threadloop=true;
        checkinttag=1000000;
        busvechileno.clear();
        busdrivername.clear();
        buslatlist.clear();
        buslat.clear();
        buslong.clear();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(fivemins);
        busvechileno.clear();
        busdrivername.clear();
        buslatlist.clear();
        buslat.clear();
        buslong.clear();
        Log.e(" after Back pressed","_________________________________");
        new getdelay().execute();*/
        Log.e("Back pressed","_________________________________");
        threadloop=true;
        checkinttag=1000000;
        busvechileno.clear();
        busdrivername.clear();
        buslatlist.clear();
        buslat.clear();
        buslong.clear();
        new getdelay().execute();

    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Hawkeye_navigation.this);

        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Which Language would you like to use?");

        alertDialog.setPositiveButton("English", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                db.delete("language",null,null);

                ContentValues val=new ContentValues();
                val.put("selected_language","english");
                val.put("val","en");
                db.insert("language",null,val);

                change_language("en");
                dialog.cancel();
                Intent intent = new Intent(Hawkeye_navigation.this, Hawkeye_navigation.class);
                startActivity(intent);
                finish();

            }
        });

        alertDialog.setNegativeButton("العربية", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                db.delete("language",null,null);

                ContentValues val=new ContentValues();
                val.put("selected_language","arabic");
                val.put("val","ar");
                db.insert("language",null,val);

                change_language("ar");
                dialog.cancel();
                Intent intent = new Intent(Hawkeye_navigation.this, Hawkeye_navigation.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.show();
    }


    void change_language(String language){
        String languageToLoad  = language; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Hawkeye_navigation.this.getResources().updateConfiguration(config,
                Hawkeye_navigation.this.getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hawkeye_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();
        } else if (id == R.id.routes) {
            threadloop=false;
            Intent intent = new Intent(this, Student_Route_Creation_Navigation.class);
            startActivity(intent);
        } else if (id == R.id.attendence) {
            threadloop=false;
            Intent intent = new Intent(this, Student_Attendence_Navigation.class);
            startActivity(intent);
        } else if (id == R.id.transfer) {
            threadloop=false;
            Intent intent = new Intent(this, Transfer_Student_Navigation.class);
            startActivity(intent);
        } else if (id == R.id.driverrating) {
            threadloop=false;
            Intent intent = new Intent(this, drivermeritnavigation.class);
            startActivity(intent);

        } else if (id == R.id.messaging) {
            threadloop=false;
            Intent intent = new Intent(this, Messaging_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.contractcreation) {
            threadloop=false;
            Intent intent = new Intent(this, Contract_Creation_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.buscreationclick) {
            threadloop=false;
            Intent intent = new Intent(this, Bus_Creation_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.drivercreationclick) {
            threadloop=false;
            Intent intent = new Intent(this, Driver_Create_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.managefuel) {
            threadloop=false;
            Intent intent = new Intent(this, Manage_Fuel_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.studentmisbehaviour) {
            threadloop=false;
            Intent intent = new Intent(this, Student_Misbehaviour_Navigation.class);
            startActivity(intent);

        }
        else if (id == R.id.accident) {
            threadloop=false;
            Intent intent = new Intent(this, Accident_Navigation.class);
            startActivity(intent);

        }
        else if (id == R.id.reportsfour) {
            threadloop=false;
            Intent intent = new Intent(this, Reports_Navigation.class);
            startActivity(intent);

        }
        else if (id == R.id.changelanguage) {
            showAlertForLanguage();
        }
        else if (id == R.id.leave) {
            threadloop=false;
            Intent intent = new Intent(this, Leave_Navigation.class);
            startActivity(intent);

        }
        else if (id == R.id.studentlistnew) {
            threadloop=false;
            Intent intent = new Intent(this, Student_List_Navigation.class);
            startActivity(intent);

        }
        else if (id == R.id.breakdowns) {
            threadloop=false;
            Intent intent = new Intent(this, Breakdowns_Navigation.class);
            startActivity(intent);

        }
        else if (id == R.id.logout) {
            threadloop=false;
            db.delete("OneTimeLogin", null, null);
            Intent intent = new Intent(this, LoginAdmin.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();

        //mHandler.removeCallbacks(mHandlerTask);
        mHandler.removeCallbacksAndMessages(null);

        if ((progressDialog1 != null) && progressDialog1.isShowing())
            progressDialog1.dismiss();
        progressDialog1 = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(alertShown==0)
            showAlert();


        mHandler = new Handler();

        mHandlerTask = new Runnable(){
            @Override
            public void run() {
                /*final getMapDataToServerAllBuses n=new getMapDataToServerAllBuses();
                n.execute();
                Log.e("","Async Task");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        Log.e("","Thread2");
                        if ( n.getStatus() == AsyncTask.Status.RUNNING )
                            n.cancel(true);
                    }
                }, 5000 );*/
                if(check==0) {
                    Log.e("run","yes");
                    new getMapDataToServerAllBuses().execute();
                }
                else
                    Log.e("run","no");
                mHandler.postDelayed(this,1 * 10 * 1000);
            }
        };
        mHandlerTask.run();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    protected void onNewIntent( Intent intent ) {
        Log.d( "", "onNewIntent(), intent = " + intent );
        super.onNewIntent( intent );
        alertShown=0;
        setIntent( intent );
    }

    public void showAlert()
    {
        Bundle extras = getIntent().getExtras();
        Log.e("","extras");
        if(extras !=null) {
            alert_title = extras.getString("title");
            alert_message = extras.getString("message");
            alert_driver= extras.getString("driver_name");
            alert_bus= extras.getString("bus_name");
            alert_timestamp=extras.getString("timestamp");

        }
        if(!(alert_title.isEmpty()))
            initiatePopupWindowhawkeyeAlert(alert_title,alert_driver,alert_bus,alert_timestamp);
        alertShown=1;
    }
}
