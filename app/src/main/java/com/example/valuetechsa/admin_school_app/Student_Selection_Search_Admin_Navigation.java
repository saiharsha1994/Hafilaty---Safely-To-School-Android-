package com.example.valuetechsa.admin_school_app;

import android.*;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.DB.DatabaseAdapter;
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
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class Student_Selection_Search_Admin_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, DirectionFinderListener,GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    String[] cordinateList=new String[100];
    String x="",y="";
    String globalstop="default";
    Double xl,yl,xlc,ylc;
    Point p;
    LatLng globallatlng;
    ListView listViewstudents ;
    String response="",url="";
    String studentidgot,studentidgotfull;
    int driverlistno,buslistno,routeidno,lineno;
    int editrouteid;
    Jsonfunctions sh;
    int addornotpopup=0;
    int searchclick=0;
    int lastdelete=0;
    Button btnClosePopup,btnAddPopup,btnCloserRemovePopup,btnAddRemovePopup;

    ArrayList<String> list=new ArrayList<String>();
    ArrayList<String> longlist=new ArrayList<String>();
    ArrayList<String> latlist=new ArrayList<String>();
    ArrayList<String> stops=new ArrayList<String>();
    ArrayList<String> colorchangelat=new ArrayList<String>();
    ArrayList<String> colorchangelan=new ArrayList<String>();
    ArrayList<String> drivernamefromserver=new ArrayList<String>();
    ArrayList<String> busnamefromserver=new ArrayList<String>();
    ArrayList<String> driveridfromserver=new ArrayList<String>();
    ArrayList<String> busidfromserver=new ArrayList<String>();
    ArrayList<String> routeidfromserver=new ArrayList<String>();
    ArrayList<String> routetypefromserver=new ArrayList<String>();
    ArrayList<String> routenamefromserver=new ArrayList<String>();
    ArrayList<String> studentidfromserver=new ArrayList<String>();
    ArrayList<String> droprouteidfromserver=new ArrayList<String>();
    ArrayList<String> pickuprouteidfromserver=new ArrayList<String>();
    ArrayList<String> teachermarker=new ArrayList<String>();
    ArrayList<String> studentidselected=new ArrayList<String>();
    ArrayList<String> teacherid=new ArrayList<String>();
    ArrayList<String> teachername=new ArrayList<String>();
    ArrayList<String> teacherlat=new ArrayList<String>();
    ArrayList<String> teacherlong=new ArrayList<String>();
    ArrayList<String> dummydrivername=new ArrayList<String>();
    ArrayList<String> dummydriverid=new ArrayList<String>();
    ArrayList<String> dummybusname=new ArrayList<String>();
    ArrayList<String> dummybusid=new ArrayList<String>();
    ArrayList<String> teacherlatlong=new ArrayList<String>();
    ArrayList<String> EditStopName=new ArrayList<String>();
    ArrayList<String> EditLatitudeLongitude=new ArrayList<String>();
    ArrayList<String> EditLatitude=new ArrayList<String>();
    ArrayList<String> EditLongitude=new ArrayList<String>();
    ArrayList<String> EditAssignTo=new ArrayList<String>();
    ArrayList<String> EditDriverIdFromServer=new ArrayList<String>();
    ArrayList<String> EditBusIdFromServer=new ArrayList<String>();

    ArrayList<String> driverpickupidFromServer=new ArrayList<String>();
    ArrayList<String> driverdropidFromServer=new ArrayList<String>();
    ArrayList<String> buspickupidFromServer=new ArrayList<String>();
    ArrayList<String> busdropidFromServer=new ArrayList<String>();
    ArrayList<String> bustypenumberFromServer=new ArrayList<String>();

    ArrayList<Integer> dummyindex=new ArrayList<Integer>();
    ArrayList<String> dummyselectedstudent=new ArrayList<String>();
    ArrayList<String> dummyselectedstudentname=new ArrayList<String>();
    ArrayList<String> dummycolorchangelat=new ArrayList<String>();
    ArrayList<String> dummycolorchangelan=new ArrayList<String>();
    ArrayList<String> dummystudentid=new ArrayList<String>();

    ArrayList<String> schoollocationfromserver=new ArrayList<String>();
    ArrayList<String> schoolnamefromserver=new ArrayList<String>();

    String globalschoolname,globalschoollat,globalschoollan;

    LinearLayout back_dim_layout;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    String slat="";
    String slon="";
    int countinitialstudents=0;
    double globaldistance=0,globaltime=0;
    double dlat =0;
    double dlon=0;
    ArrayList<Double> dlatlist=new ArrayList<Double>();
    ArrayList<Double> dlonlist=new ArrayList<Double>();
    ArrayList<String> slatlist=new ArrayList<String>();
    ArrayList<String> slonlist=new ArrayList<String>();
    ArrayList<String> globalstoplist=new ArrayList<String>();
    ArrayList<String> studentidlist=new ArrayList<String>();

    private PopupWindow pwindo;
    private EditText searchBox;
    int removeposition;
    String removestudent;
    Typeface tfRobo,tfAdam;
    Spinner busdropdown,driverdropdown;
    String routetypefromboth;
    String routenamenavigation,routetypenavigation,routestarttymnavgation,routeendtymnavigation,routedrivernamenavigation,routebusnonavigation;
    ArrayList<String> selectedstudents=new ArrayList<String>();
    ArrayList<String> selectedstudentsreverse=new ArrayList<String>();
    ArrayList<String> selectedstudentsname=new ArrayList<String>();
    private List<Marker> studentMarkers = new ArrayList<>();
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog1;
    private ProgressDialog progressDialog2;
    private ProgressDialog progressDialog5;
    private ProgressDialog progressDialog123;
    ArrayList<String> selectedstudentdistance=new ArrayList<String>();
    ArrayList<String> selectedstudenttime=new ArrayList<String>();
    ArrayList<String> schoolstudentdistance=new ArrayList<String>();
    ArrayList<String> schoolstudenttime=new ArrayList<String>();
    ArrayList<Double> schoolstudentdistancedummy=new ArrayList<Double>();
    String nameuser,passuser,adminid,adminname,schoolcordinates;
    double schoollat,schoollong;
    String[] schoolcordarray=new String[100000];
    int firsttimezero=0;
    int [][] finaldistance;
    int globalcalculate=0;
    FrameLayout layout_MainMenu;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    ArrayList<String> busnameforreassign=new ArrayList<String>();
    ArrayList<String> busidforreassign=new ArrayList<String>();
    TextView brokendownBus;
    Spinner reassignBusSelect;
    NotificationUtils notificationUtils;
    TextView topRow,drivernamehawkeyetextbox;
    String alert_title="",alert_message="",alert_notification_message="",alert_type="",previous_alert_type="normal",frombusidintoserver="",tobusidintoserver="",tobusnameintoserver="";

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
        setContentView(R.layout.activity_student__selection__search__admin__navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.selectionheader);
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

        TextView TextViewNewFont = new TextView(Student_Selection_Search_Admin_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_student_selection));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Student_Selection_Search_Admin_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Student_Selection_Search_Admin_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);

        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
       /* action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp; Student Selection</big></font>"));*/
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

        Intent intent = getIntent();
        lineno=(intent.getExtras().getInt("Lineno"));
        if(lineno!=0){
            Log.e("intent values",lineno+"");
            routetypenavigation = intent.getExtras().getString("TripType");
            Log.e("intent values", routetypenavigation);
        }
        else {
            routenamenavigation = intent.getExtras().getString("RouteName");
            routetypenavigation = intent.getExtras().getString("TripType");
            routestarttymnavgation = intent.getExtras().getString("StartTime");
            routeendtymnavigation = intent.getExtras().getString("EndTime");
            if(routetypenavigation.equalsIgnoreCase(getResources().getString(R.string.sj_drop))){
                routetypenavigation="2";
            }
            else
            {
                routetypenavigation="1";
            }
            Log.e("intent values", routenamenavigation);
            Log.e("intent values", routetypenavigation);
            Log.e("intent values", routestarttymnavgation);
            Log.e("intent values", routeendtymnavigation);

        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String title = intent.getStringExtra("title");
                    String message = intent.getStringExtra("message");
                    String notification_message = intent.getStringExtra("notification_message");
                    String type = intent.getStringExtra("type");
                    String imageUrl = intent.getStringExtra("image");


                    if(!type.equalsIgnoreCase("normal")){
                        initiatePopupWindowhawkeyeAlert(title,message,notification_message,type);
                    }

                    //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();



                    else {
                        //Display Notification
                        Intent resultIntent = new Intent(getApplicationContext(), Hawkeye_navigation.class);
                        resultIntent.putExtra("title", title);
                        resultIntent.putExtra("message", message);
                        resultIntent.putExtra("notification_message", notification_message);
                        resultIntent.putExtra("type", type);

                        // check for image attachment
                        if (TextUtils.isEmpty(imageUrl)) {
                            showNotificationMessage(getApplicationContext(), title, notification_message, resultIntent);
                        } else {
                            // image is present, show notification with image
                            showNotificationMessageWithBigImage(getApplicationContext(), title, notification_message, resultIntent, imageUrl);
                        }
                    }

                }
            }
        };

        new getSpareBusFromServer().execute();



//        listViewstudents=(ListView) findViewById(R.id.studentlist);
//        String[] students = new String[] { "Student1",
//                "Student2",
//                "Student3",
//                "Student4",
//                "Student5",
//                "Student6",
//                "Student7",
//                "Student8"
//        };
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, students);
//        listViewstudents.setAdapter(adapter2);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        screenchange();
        new getSettingsListFromServer().execute();
        //new getMapDataToServer().execute();
        //new getDriverListFromServer().execute();
        //new getRoutesFromServer().execute();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void screenchange(){
        TextView selectstudentlocation=(TextView)findViewById(R.id.selectstudentlayout);
        EditText searrc=(EditText)findViewById(R.id.editsearchbox);
        TextView selectstudents=(TextView)findViewById(R.id.selectedstudent);
        Button saves=(Button)findViewById(R.id.savebutton);
        Button cancless=(Button)findViewById(R.id.cancelbutton);
        selectstudentlocation.setTypeface(tfRobo);
        searrc.setTypeface(tfRobo);
        selectstudents.setTypeface(tfRobo);
        saves.setTypeface(tfAdam);
        cancless.setTypeface(tfAdam);
    }

    class getSettingsListFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"Settings_api/listSettings");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Settings_api/listSettings",Jsonfunctions.GET);

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
                                    Log.e("school_location",obj.getString("school_location"));
                                    String school_location=obj.getString("school_location");
                                    schoollocationfromserver.add(school_location);
                                    Log.e("school_name",obj.getString("school_name"));
                                    String school_name=obj.getString("school_name");
                                    schoolnamefromserver.add(school_name);
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
            int ri=schoollocationfromserver.get(0).indexOf(",");
            globalschoollat=schoollocationfromserver.get(0).substring(0,ri);
            globalschoollan=schoollocationfromserver.get(0).substring(ri+1);
            Log.e("~~~~~~~~~","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~schoollat: "+globalschoollat);
            Log.e("~~~~~~~~~","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~schoollan: "+globalschoollan);
            new getMapDataToServer().execute();
        }
    }


    class getRoutesFromServer extends AsyncTask<Void,Void,Void>{
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
                    Log.e("url", Config.ip+"GetRouteDetails_api/RoutesListStops");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"GetRouteDetails_api/RoutesListStops",Jsonfunctions.GET);

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
                                    Log.e("inside","+++++++++++++++++++++++++++++++++++++");
                                    String id=obj.getString("route_id");
                                    routeidfromserver.add(id);
                                    Log.e("++++++++++++++",id);
                                    String nameroute=obj.getString("route_name");
                                    Log.e("++++++++++++++",nameroute);
                                    String routetype=obj.getString("trip_type");
                                    routetypefromserver.add(obj.getString("trip_type"));
                                    routenamefromserver.add(nameroute);
                                    String driverid=obj.getString("driver_id");
                                    EditDriverIdFromServer.add(driverid);
                                    String busid=obj.getString("bus_id");
                                    EditBusIdFromServer.add(busid);
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
            new getDriverListFromServer().execute();
            if (lineno!=0) {
                editrouteid=Integer.parseInt(routeidfromserver.get(lineno-1));
                routenamenavigation=routenamefromserver.get(lineno-1);
                Log.e("Route id selected", editrouteid+"");
                routetypefromboth=routetypefromserver.get(lineno-1);
                Log.e("@@@@@@@@",routetypenavigation+"^::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+routetypefromboth);
                new getEditStopsFromServer().execute();
            }

            //listgenerate();

        }
    }

    class getEditStopsFromServer extends AsyncTask<Void,Void,Void>{
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
                    Log.e("url", Config.ip+"GetRouteDetails_api/StopsListByRoute/route_id/"+editrouteid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"GetRouteDetails_api/StopsListByRoute/route_id/"+editrouteid,Jsonfunctions.GET);

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
                                    Log.e("inside","+++++++++++++++++++++++++++++++++++++stops");
                                    String Stopnames=obj.getString("stop_name");
                                    Log.e("stop name",Stopnames);
                                    EditStopName.add(Stopnames);
                                    String lati=obj.getString("latitude");
                                    EditLatitude.add(lati);
                                    String longi=obj.getString("langitude");
                                    EditLongitude.add(longi);
                                    String latilongi=lati+","+longi;
                                    Log.e("latitudelongitude",latilongi);
                                    EditLatitudeLongitude.add(latilongi);
                                    String assignto=obj.getString("assigned_to");
                                    EditAssignTo.add(assignto);
                                    countinitialstudents=countinitialstudents+1;
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
            for(int i=0;i<EditStopName.size();i++){
                selectedstudents.add(EditLatitudeLongitude.get(i));
                selectedstudentsname.add(EditStopName.get(i));
                colorchangelat.add(EditLatitude.get(i));
                colorchangelan.add(EditLongitude.get(i));
                studentidselected.add(EditAssignTo.get(i));
            }
            //distancefromschool();
            new distancebox().execute();
            //new progessdialogbox().execute();
            //listgenerate();

        }
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
                    Log.e("url", Config.ip+"Students_api/allStudents/trip_type/"+routetypenavigation);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Students_api/allStudents/trip_type/"+routetypenavigation,Jsonfunctions.GET);

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
                                    //String stop="student stop";
                                    String stop=obj.getString("name");
                                    //Log.e("+++",obj.getString("stop_name"));
                                    String Latitude=obj.getString("latitude");
                                    Log.e("+++",obj.getString("latitude"));
                                    String Langitude=obj.getString("langitude");
                                    String Latlong=(Latitude+","+Langitude);
                                    String studentidfrom=obj.getString("student_id");
                                    String pickuprouteid=obj.getString("pickup_route_id");
                                    String droprouteid=obj.getString("drop_route_id");
                                    if(routetypenavigation.equalsIgnoreCase("1")){
                                        if(pickuprouteid.equalsIgnoreCase("0")){
                                            Log.e("!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+stop);
                                            studentidfromserver.add(studentidfrom);
                                            teachermarker.add("0");
                                            Log.e("LatLong.......",Latlong);
                                            latlist.add(Latitude);
                                            longlist.add(Langitude);
                                            stops.add(stop);
                                            list.add(Latlong);
                                        }
                                    }
                                    else if(routetypenavigation.equalsIgnoreCase("2")){
                                        if(droprouteid.equalsIgnoreCase("0")){
                                            Log.e("!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+stop);
                                            studentidfromserver.add(studentidfrom);
                                            teachermarker.add("0");
                                            Log.e("LatLong.......",Latlong);
                                            latlist.add(Latitude);
                                            longlist.add(Langitude);
                                            stops.add(stop);
                                            list.add(Latlong);
                                        }
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
            new getMapTeacherDataToServer().execute();
            //cordinates();
        }
    }

    class getMapTeacherDataToServer extends AsyncTask<Void,Void,Void>
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
                    Log.e("url", Config.ip+"Students_api/allTeachers");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Students_api/allTeachers",Jsonfunctions.GET);

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
                                    String teacheridfrom=obj.getString("teacher_id");
                                    Log.e("+++",obj.getString("teacher_id"));
                                    String teachernamee=obj.getString("name");
                                    Log.e("+++",obj.getString("name"));
                                    String Latitude=obj.getString("latitude");
                                    Log.e("+++",obj.getString("latitude"));
                                    String Langitude=obj.getString("langitude");
                                    Log.e("+++",obj.getString("langitude"));
                                    String Latlong=(Latitude+","+Langitude);
                                    Log.e("+++",Latlong);
                                    String pickuprouteid=obj.getString("pickup_route_id");
                                    String droprouteid=obj.getString("drop_route_id");
                                    if(routetypenavigation.equalsIgnoreCase("1")){
                                        if(pickuprouteid.equalsIgnoreCase("0")){
                                            Log.e("!!!!!","!!!!!!!3123122312312!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+teachernamee);
                                            teacherid.add(teacheridfrom);
                                            teachername.add(teachernamee);
                                            teacherlat.add(Latitude);
                                            teacherlong.add(Langitude);
                                            teacherlatlong.add(Latlong);
                                            studentidfromserver.add(teacheridfrom);
                                            teachermarker.add("1");
                                            //Log.e("LatLong.......",Latlong);
                                            latlist.add(Latitude);
                                            longlist.add(Langitude);
                                            stops.add(teachernamee+"-TEACHER");
                                            list.add(Latlong);
                                        }
                                    }
                                    else if(routetypenavigation.equalsIgnoreCase("2")){
                                        if(droprouteid.equalsIgnoreCase("0")){
                                            Log.e("!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+teachernamee);
                                            teacherid.add(teacheridfrom);
                                            teachername.add(teachernamee);
                                            teacherlat.add(Latitude);
                                            teacherlong.add(Langitude);
                                            teacherlatlong.add(Latlong);
                                            studentidfromserver.add(teacheridfrom);
                                            teachermarker.add("1");
                                            //Log.e("LatLong.......",Latlong);
                                            latlist.add(Latitude);
                                            longlist.add(Langitude);
                                            stops.add(teachernamee+"-TEACHER");
                                            list.add(Latlong);
                                        }
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
            calculateDistanceForSingleCoordinate();
            cordinates();
            new getRoutesFromServer().execute();
        }
    }

    class getDriverListFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog1 = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, "Please wait.",
                    "Fetching Information!", true);

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
                                    drivernamefromserver.add(drivername);
                                    Log.e("+++",obj.getString("driver_id"));
                                    String driverId=obj.getString("driver_id");
                                    driveridfromserver.add(driverId);
                                    Log.e("pickup_route_id",obj.getString("pickup_route_id"));
                                    String pickup_route_id=obj.getString("pickup_route_id");
                                    driverpickupidFromServer.add(pickup_route_id);
                                    Log.e("drop_route_id",obj.getString("drop_route_id"));
                                    String drop_route_id=obj.getString("drop_route_id");
                                    driverdropidFromServer.add(drop_route_id);
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
            progressDialog1.dismiss();
            new getBusListFromServer().execute();
        }
    }

    class getBusListFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"BusList_api/listBuses");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"BusList_api/listBuses",Jsonfunctions.GET);

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
                                    String busname=obj.getString("name");
                                    busnamefromserver.add(busname);
                                    Log.e("+++",obj.getString("bus_Id"));
                                    String busId=obj.getString("bus_Id");
                                    busidfromserver.add(busId);
                                    Log.e("pickup_route_id",obj.getString("pickup_route_id"));
                                    String pickup_route_id=obj.getString("pickup_route_id");
                                    buspickupidFromServer.add(pickup_route_id);
                                    Log.e("drop_route_id",obj.getString("drop_route_id"));
                                    String drop_route_id=obj.getString("drop_route_id");
                                    busdropidFromServer.add(drop_route_id);
                                    Log.e("bus_type",obj.getString("bus_type"));
                                    String bus_type=obj.getString("bus_type");
                                    bustypenumberFromServer.add(bus_type);
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
            dropdownlist();
            progressDialog2.dismiss();
        }
    }

    class distancebox extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute()
        {
            distancefromschool();
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {

            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {

            progressDialog123.dismiss();
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

    public void calculateDistanceForSingleCoordinate(){
        Double[ ][ ] distancecoordinate = new Double[list.size()][list.size()];
        finaldistance=new  int[list.size()][list.size()];
        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+latlist.size());
        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+longlist.size());
        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+list.size());
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.size();j++){
                if(i==j){
                    distancecoordinate[i][j]=0.0;
                    finaldistance[i][j]=0;
                    Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+(i+1)+" "+(j+1)+" : "+distancecoordinate[i][j].intValue());
                    Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ final distance: "+finaldistance[i][j]);
                }
                else {
                    distancecoordinate[i][j]=haversine(Double.parseDouble(latlist.get(i)),Double.parseDouble(longlist.get(i)),Double.parseDouble(latlist.get(j)),Double.parseDouble(longlist.get(j)));
                    if(distancecoordinate[i][j]>=0 && distancecoordinate[i][j]<30){
                        finaldistance[i][j]=1;
                    }
                    else {
                        finaldistance[i][j]=0;
                    }
                    Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+(i+1)+" "+(j+1)+" : "+distancecoordinate[i][j].intValue());
                    Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ final distance: "+finaldistance[i][j]);
                }
            }
        }
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
        return d*1000;
    }

    public void dropdownlist(){
        driverdropdown = (Spinner)findViewById(R.id.selectdriverspinner);
        busdropdown = (Spinner)findViewById(R.id.selectbusspinner);
        for(int routeid1=0;routeid1<routenamefromserver.size();routeid1++){
            if(routenamenavigation.equals(routenamefromserver.get(routeid1))){
                routeidno=routeid1;
                Log.e("++++++","+++++++++++++++++++++++++++++++++++++++++++++++++"+routeidfromserver.get(routeidno));
            }
        }
        int sum=drivernamefromserver.size();
        sum=sum+1;
        int sumdriver=1;
        if(routetypenavigation.equalsIgnoreCase("1")){
            for(int ii=0;ii<drivernamefromserver.size();ii++){
                if(Integer.parseInt(driverpickupidFromServer.get(ii))==0||Integer.parseInt(driverpickupidFromServer.get(ii))==Integer.parseInt(routeidfromserver.get(routeidno))){
                    sumdriver=sumdriver+1;
                    Log.e("*****","*********************************************************");
                }
            }
            Log.e("*****","*********************************************************sumdriver"+sumdriver);
        }
        else {
            for(int ii=0;ii<drivernamefromserver.size();ii++){
                if(Integer.parseInt(driverdropidFromServer.get(ii))==0||Integer.parseInt(driverdropidFromServer.get(ii))==Integer.parseInt(routeidfromserver.get(routeidno))){
                    sumdriver=sumdriver+1;
                    Log.e("*****","*********************************************************");
                }
            }
            Log.e("*****","*********************************************************sumdriver"+sumdriver);
        }
        String[] driveritems= new String[sumdriver];
        driveritems[0]=getResources().getString(R.string.sj_select_driver);
        if(routetypenavigation.equalsIgnoreCase("1")){
            int hi=1;
            for(int i=1;i<=drivernamefromserver.size();i++){
                if(Integer.parseInt(driverpickupidFromServer.get(i-1))==0||Integer.parseInt(driverpickupidFromServer.get(i-1))==Integer.parseInt(routeidfromserver.get(routeidno))){
                    driveritems[hi]=drivernamefromserver.get(i-1);
                    dummydrivername.add(drivernamefromserver.get(i-1));
                    dummydriverid.add(driveridfromserver.get(i-1));
                    Log.e("*****","*********************************************************drivername "+driveritems[hi]);
                    hi++;
                }
            }
        }
        else{
            int hi=1;
            for(int i=1;i<=drivernamefromserver.size();i++){
                if(Integer.parseInt(driverdropidFromServer.get(i-1))==0||Integer.parseInt(driverdropidFromServer.get(i-1))==Integer.parseInt(routeidfromserver.get(routeidno))){
                    driveritems[hi]=drivernamefromserver.get(i-1);
                    dummydrivername.add(drivernamefromserver.get(i-1));
                    dummydriverid.add(driveridfromserver.get(i-1));
                    Log.e("*****","*********************************************************drivername "+driveritems[hi]);
                    hi++;
                }
            }
        }
        int sum1=busnamefromserver.size();
        sum1=sum1+1;
        int sumbus=1;
        if(routetypenavigation.equalsIgnoreCase("1")){
            for(int ii=0;ii<busnamefromserver.size();ii++){
                if(Integer.parseInt(buspickupidFromServer.get(ii))==0||Integer.parseInt(buspickupidFromServer.get(ii))==Integer.parseInt(routeidfromserver.get(routeidno))){
                    sumbus=sumbus+1;
                    Log.e("*****","*********************************************************");
                }
            }
            Log.e("*****","*********************************************************sumbus"+sumbus);
        }
        else {
            for(int ii=0;ii<busnamefromserver.size();ii++){
                if(Integer.parseInt(busdropidFromServer.get(ii))==0||Integer.parseInt(busdropidFromServer.get(ii))==Integer.parseInt(routeidfromserver.get(routeidno))){
                    sumbus=sumbus+1;
                    Log.e("*****","*********************************************************");
                }
            }
            Log.e("*****","*********************************************************sumbus"+sumbus);
        }
        String[] busitems=new String[sumbus];
        busitems[0]=getResources().getString(R.string.sj_select_bus);
        if(routetypenavigation.equalsIgnoreCase("1")){
            int hi=1;
            for(int j=1;j<=busnamefromserver.size();j++){
                if(Integer.parseInt(buspickupidFromServer.get(j-1))==0||Integer.parseInt(buspickupidFromServer.get(j-1))==Integer.parseInt(routeidfromserver.get(routeidno))){
                    busitems[hi]=busnamefromserver.get(j-1);
                    dummybusname.add(busnamefromserver.get(j-1));
                    dummybusid.add(busidfromserver.get(j-1));
                    Log.e("*****","*********************************************************busname"+busitems[hi]);
                    hi++;
                }
            }
        }
        else{
            int hi=1;
            for(int j=1;j<=busnamefromserver.size();j++){
                if(Integer.parseInt(busdropidFromServer.get(j-1))==0||Integer.parseInt(busdropidFromServer.get(j-1))==Integer.parseInt(routeidfromserver.get(routeidno))){
                    busitems[hi]=busnamefromserver.get(j-1);
                    dummybusname.add(busnamefromserver.get(j-1));
                    dummybusid.add(busidfromserver.get(j-1));
                    Log.e("*****","*********************************************************busname "+busitems[hi]);
                    hi++;
                }
            }
        }
        try{
            ArrayAdapter<String> adapterdriver = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, driveritems) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                    ((TextView) v).setTypeface(externalFont);
                    ((TextView) v).setTextSize(20);
                    ((TextView) v).setMinHeight(70);
                    return v;
                }
                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                    View v =super.getDropDownView(position, convertView, parent);
                    Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                    ((TextView) v).setTypeface(externalFont);
                    ((TextView) v).setTextSize(20);
                    ((TextView) v).setMinHeight(70);
                    return v;
                }
            };
        /*ArrayAdapter<String> adapterdriver = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, driveritems);*/
            adapterdriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busitems) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                    ((TextView) v).setTypeface(externalFont);
                    ((TextView) v).setTextSize(20);
                    ((TextView) v).setMinHeight(70);
                    return v;
                }
                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                    View v =super.getDropDownView(position, convertView, parent);
                    Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                    ((TextView) v).setTypeface(externalFont);
                    ((TextView) v).setTextSize(20);
                    ((TextView) v).setMinHeight(70);
                    return v;
                }
            };
        /*ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, busitems);*/
            adapterbus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            driverdropdown.setAdapter(adapterdriver);
            busdropdown.setAdapter(adapterbus);
        }
        catch (Exception e){

        }
        if(lineno!=0){
            int rowdriver=0;
            int rowbus=0;
            String driverid=EditDriverIdFromServer.get(lineno-1);
            String busid=EditBusIdFromServer.get(lineno-1);
            for(int r=0;r<dummydriverid.size();r++){
                if(driverid.equals(dummydriverid.get(r))){
                    routedrivernamenavigation=dummydrivername.get(r);
                    rowdriver=r+1;
                }
            }
            for(int t=0;t<dummybusid.size();t++){
                if(busid.equals(dummybusid.get(t))){
                    routebusnonavigation=dummybusname.get(t);
                    rowbus=t+1;
                }
            }
            Log.e("!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! driverid "+driverid);
            Log.e("!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!bus id "+busid);
            Log.e("!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!rowdriver "+rowdriver);
            Log.e("!!!!!!!!!","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!rowbus "+rowbus);
            driverdropdown.setSelection(rowdriver);
            busdropdown.setSelection(rowbus);
        }
        driverdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                routedrivernamenavigation=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        busdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                routebusnonavigation=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

   public void adddetailstoserversavebutton(View view){
       if(routedrivernamenavigation.equals(getResources().getString(R.string.sj_select_driver))){
           Toast.makeText(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_select_driver),
                   Toast.LENGTH_SHORT).show();
       }
       else if(routebusnonavigation.equals(getResources().getString(R.string.sj_select_bus))){
           Toast.makeText(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_select_bus),
                   Toast.LENGTH_SHORT).show();
       }
       else{
           String seatcapacity;
           String ssss="";
           int busassigned=0;
           int totalstudentsselected=0;
           int remainingseats=9999999;
           Log.e("#####","#####################################################routeid: "+routeidfromserver.get(routeidno));
           if(routetypenavigation.equalsIgnoreCase("1")){

               for(int ee=0;ee<busnamefromserver.size();ee++){
                   if(routebusnonavigation.equalsIgnoreCase(busnamefromserver.get(ee))){
                       seatcapacity=bustypenumberFromServer.get(ee);

                       Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(seatcapacity);
                       while (m.find())
                       {
                           double d = Double.parseDouble(m.group(1));
                           ssss=ssss+String.valueOf(d);
                           //System.out.println(d);
                       }
                       Log.e("########","#########################################seatcapacity: "+seatcapacity);
                       Log.e("########","#########################################ssss: "+ssss);
                       break;
                   }
               }
               /*for(int rr=0;rr<bustypenumberFromServer.size();rr++){
                   if(buspickupidFromServer.get(rr).equalsIgnoreCase(routeidfromserver.get(routeidno))){
                       busassigned=1;
                       seatcapacity=bustypenumberFromServer.get(rr);

                       Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(seatcapacity);
                       while (m.find())
                       {
                           double d = Double.parseDouble(m.group(1));
                           ssss=ssss+String.valueOf(d);
                           //System.out.println(d);
                       }
                       Log.e("########","#########################################seatcapacity: "+seatcapacity);
                       Log.e("########","#########################################ssss "+ssss);
                       break;
                   }
               }*/
           }
           else {
               for(int ee=0;ee<busnamefromserver.size();ee++){
                   if(routebusnonavigation.equalsIgnoreCase(busnamefromserver.get(ee))){
                       seatcapacity=bustypenumberFromServer.get(ee);

                       Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(seatcapacity);
                       while (m.find())
                       {
                           double d = Double.parseDouble(m.group(1));
                           ssss=ssss+String.valueOf(d);
                           //System.out.println(d);
                       }
                       Log.e("########","#########################################seatcapacity: "+seatcapacity);
                       Log.e("########","#########################################ssss: "+ssss);
                       break;
                   }
               }
               /*for(int rr=0;rr<bustypenumberFromServer.size();rr++){
                   routebusnonavigation
                   if(busdropidFromServer.get(rr).equalsIgnoreCase(routeidfromserver.get(routeidno))){
                       busassigned=1;
                       seatcapacity=bustypenumberFromServer.get(rr);

                       Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(seatcapacity);
                       while (m.find())
                       {
                           double d = Double.parseDouble(m.group(1));
                           ssss=ssss+String.valueOf(d);
                           //System.out.println(d);
                       }
                       Log.e("########","#########################################seatcapacity: "+seatcapacity);
                       Log.e("########","#########################################ssss: "+ssss);
                       break;
                   }
               }*/
           }
           Log.e("#########","#########################################no of selected students "+selectedstudentsname.size());
           totalstudentsselected=selectedstudentsname.size();
           Double dummy=Double.parseDouble(ssss);
           if(totalstudentsselected>dummy.intValue()){
               Toast.makeText(Student_Selection_Search_Admin_Navigation.this,getResources().getString(R.string.sj_seats_not_avaliable)+", "+getResources().getString(R.string.sj_student_selected)+" "+totalstudentsselected+" , "+getResources().getString(R.string.sj_seats_remaining_in_bus)+" "+dummy.intValue(),
                       Toast.LENGTH_SHORT).show();
           }
           else {
               new addstopstoserver().execute();
           }
       }

    }

   /* class adddetailstoserver extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){


        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                Log.e("before apply",routenamenavigation);
                Log.e("before apply",routetypenavigation);
                Log.e("before apply",routestarttymnavgation);
                Log.e("before apply",routeendtymnavigation);
                Log.e("before apply",routedrivernamenavigation);
                Log.e("before apply",routebusnonavigation);


                StringBuffer Route_Name=null,Route_Type=null,Route_Starttime=null,Route_Endtime=null,Route_DriverName=null,Route_BusNo=null;
                Route_Name=new StringBuffer();
                Route_Type=new StringBuffer();
                Route_Starttime=new StringBuffer();
                Route_Endtime=new StringBuffer();
                Route_DriverName=new StringBuffer();
                Route_BusNo=new StringBuffer();


                Route_Name.append(routenamenavigation);
                Route_Name.append(",");
                Route_Type.append(routetypenavigation);
                Route_Type.append(",");
                Route_Starttime.append(routestarttymnavgation);
                Route_Starttime.append(",");
                Route_Endtime.append(routeendtymnavigation);
                Route_Endtime.append(",");
                Route_DriverName.append(routedrivernamenavigation);
                Route_DriverName.append(",");
                Route_BusNo.append(routebusnonavigation);
                Route_BusNo.append(",");

                String strRoute_name="",strRoute_Type="",strRoute_Starttime="",strRoute_Endtime="",strRoute_DriverName="",strRoute_BusNo="";

                strRoute_name=Route_Name.substring(0,Route_Name.length()-1);
                //strRoute_Type=Route_Type.substring(0,Route_Type.length()-1);
                if(Route_Type.equals("morning"))
                    strRoute_Type=1+"";
                else
                    strRoute_Type=2+"";

                strRoute_Starttime=Route_Starttime.substring(0,Route_Starttime.length()-1);
                strRoute_Endtime=Route_Endtime.substring(0,Route_Endtime.length()-1);
                strRoute_DriverName=Route_DriverName.substring(0,Route_DriverName.length()-1);
                strRoute_BusNo=Route_BusNo.substring(0,Route_BusNo.length()-1);

                Log.e("after apply",strRoute_name);
                Log.e("after apply",strRoute_Type);
                Log.e("after apply",strRoute_Starttime);
                Log.e("after apply",strRoute_Endtime);
                Log.e("after apply",strRoute_DriverName);
                Log.e("after apply",strRoute_BusNo);

                sh = new Jsonfunctions();

                url= Config.ip+"GetRouteDetails_api/createRoute" +
                        "/route_name/"+ URLEncoder.encode(strRoute_name,"UTF-8")+"/trip_name/"+URLEncoder.encode(strRoute_Type,"UTF-8")+
                        "/start_time/"+URLEncoder.encode(strRoute_Starttime,"UTF-8")+"/end_time/"+URLEncoder.encode(strRoute_Endtime,"UTF-8")+
                        "/driver_id/"+URLEncoder.encode(strRoute_DriverName,"UTF-8")+"bus_id"+URLEncoder.encode(strRoute_BusNo,"UTF-8");
                Log.e("url created",url);
                try
                {
                    try
                    {
                        Log.e("url above service call",url);
                        String jsonStr = sh.makeServiceCall(url,Jsonfunctions.GET);
                        Log.e("url below service call",url);
                        if (jsonStr != null) {
                            try {
                                JSONObject Jobj = new JSONObject(jsonStr);
                                response=Jobj.getString("responsecode");
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
    }*/

    class addstopstoserver extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_adding_details), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

               /* Log.e("before apply",routenamenavigation);
                Log.e("before apply",routetypenavigation);
                Log.e("before apply",routestartt   ymnavgation);
                Log.e("before apply",routeendtymnavigation);
                Log.e("before apply",routedrivernamenavigation);
                Log.e("before apply",routebusnonavigation);*/


                StringBuffer Stop_Name=null,Stop_Latitude=null,Stop_Longitude=null,Stop_DriverId=null,Stop_BusId=null,Stop_StudentId=null,Stop_Timing=null,Stop_NumericOrder=null,Stop_RouteId=null,Route_Endtime=null,Route_DriverName=null,Route_BusNo=null;
                Stop_Name=new StringBuffer();
                Stop_Latitude=new StringBuffer();
                Stop_Longitude=new StringBuffer();
                Stop_DriverId=new StringBuffer();
                Stop_BusId=new StringBuffer();
                Stop_NumericOrder=new StringBuffer();
                Stop_RouteId=new StringBuffer();
                Stop_Timing=new StringBuffer();
                Stop_StudentId=new StringBuffer();

                for(int driverlist=0; driverlist<drivernamefromserver.size();driverlist++){
                    if(routedrivernamenavigation.equals(drivernamefromserver.get(driverlist))){
                        driverlistno=driverlist;
                    }
                }
                for(int buslist=0;buslist<busnamefromserver.size();buslist++){
                    if(routebusnonavigation.equals(busnamefromserver.get(buslist))){
                        buslistno=buslist;
                    }

                }
                for(int routeid1=0;routeid1<routenamefromserver.size();routeid1++){
                    if(routenamenavigation.equals(routenamefromserver.get(routeid1))){
                        routeidno=routeid1;
                    }
                }
                //Log.e("size of route name",routenamefromserver.size()+"");
                int counti=selectedstudentsname.size();
                while(counti>0){
                    Stop_Name.append(selectedstudentsname.get(counti-1));
                    Stop_Name.append(",");
                    Stop_Latitude.append(colorchangelat.get(counti-1));
                    Stop_Latitude.append(",");
                    Stop_Longitude.append(colorchangelan.get(counti-1));
                    Stop_Longitude.append(",");
                    Stop_NumericOrder.append(counti);
                    Stop_NumericOrder.append(",");
                    Stop_RouteId.append(routeidfromserver.get(routeidno));
                    Stop_RouteId.append(",");
                    Stop_Timing.append("5:21 AM");
                    Stop_Timing.append(",");
                    Stop_StudentId.append(studentidselected.get(counti-1));
                    Stop_StudentId.append(",");
                    counti=counti-1;
                }

                String strStop_Name="",strStop_Latitude="",strStop_Longitude="",strStop_Timing="",strStop_StudentId="",strStop_Driverid="",strStop_Busid="",strStop_NumericOrder="",strStop_RouteId="";

                strStop_Name=Stop_Name.substring(0,Stop_Name.length()-1);
                strStop_Latitude=Stop_Latitude.substring(0,Stop_Latitude.length()-1);
                strStop_Longitude=Stop_Longitude.substring(0,Stop_Longitude.length()-1);
                strStop_NumericOrder=Stop_NumericOrder.substring(0,Stop_NumericOrder.length()-1);
                strStop_RouteId=Stop_RouteId.substring(0,Stop_RouteId.length()-1);
                strStop_Driverid=driveridfromserver.get(driverlistno);
                strStop_Busid=busidfromserver.get(buslistno);
                strStop_Timing=Stop_Timing.substring(0,Stop_Timing.length()-1);
                strStop_StudentId=Stop_StudentId.substring(0,Stop_StudentId.length()-1);


                Log.e("after apply",strStop_Name);
                Log.e("after apply",strStop_Latitude);
                Log.e("after apply",strStop_Longitude);
                Log.e("after apply",strStop_RouteId);
                Log.e("after apply",routeidno+"");
                Log.e("after apply",routenamenavigation);


                sh = new Jsonfunctions();

                url= Config.ip+"GetRouteDetails_api/createRouteStops" +
                        "/stop_name/"+ URLEncoder.encode(strStop_Name,"UTF-8")+"/stop_time/"+URLEncoder.encode(strStop_Timing,"UTF-8")+"/latitude/"+URLEncoder.encode(strStop_Latitude,"UTF-8")+
                        "/langitude/"+URLEncoder.encode(strStop_Longitude,"UTF-8")+"/numeric_order/"+URLEncoder.encode(strStop_NumericOrder,"UTF-8")+
                        "/route_id/"+URLEncoder.encode(strStop_RouteId,"UTF-8")+"/assigned_to/"+URLEncoder.encode(strStop_StudentId,"UTF-8")+"/driver_id/"
                        +URLEncoder.encode(strStop_Driverid,"UTF-8")+"/bus_id/"+URLEncoder.encode(strStop_Busid,"UTF-8")+"/for/create";
                Log.e("url created",url);
                try
                {
                    try
                    {
                        Log.e("url above service call",url);
                        String jsonStr = sh.makeServiceCall(url,Jsonfunctions.GET);
                        Log.e("url below service call",url);
                        if (jsonStr != null) {
                            try {
                                JSONObject Jobj = new JSONObject(jsonStr);
                                response=Jobj.getString("responsecode");
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


            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            progressDialog2.dismiss();
            Toast.makeText(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_route_created) ,
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Student_Selection_Search_Admin_Navigation.this, Student_Route_Creation_Navigation.class);
            startActivity(intent);
            finish();
        }
    }

    public void distancefromschool(){
        progressDialog123 = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_wait),
                getResources().getString(R.string.sj_finding_direction), true);
        globalcalculate=1;
        Log.e("```````````","`````````````````````````````````````````````````distancefromschool outside");
        Log.e("```````````","`````````````````````````````````````````````````distancefromschool outside: "+globalcalculate);
        if(selectedstudents.size()==0){
            new progessdialogbox().execute();
            globalcalculate=0;
            return;
        }
        for (int i=0;i<selectedstudents.size();i++) {

            String xll=schoollocationfromserver.get(0);
            String yll=selectedstudents.get(i);
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
        Log.e("```````````","`````````````````````````````````````````````````distancefromschool after for");
    }

    public void finaldistance(){
        for(int pp=0;pp<schoolstudentdistance.size();pp++){
            Log.e("```````````","`````````````````````````````````````````````````"+schoolstudentdistance.get(pp)+":"+selectedstudentsname.get(pp));
        }
        if(schoolstudentdistance.size()==selectedstudents.size()){
            globalcalculate=0;
            Log.e("```````````","`````````````````````````````````````````````````distancefromschool after all");

            for(int i=0;i<schoolstudentdistance.size();i++){
                String sss="";
                if(schoolstudentdistance.get(i).contains("km")){
                    Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(schoolstudentdistance.get(i));
                    while (m.find())
                    {
                        double d = Double.parseDouble(m.group(1));
                        sss=sss+String.valueOf(d);
                        //System.out.println(d);
                    }
                }
                else{
                    Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(schoolstudentdistance.get(i));
                    while (m.find())
                    {
                        double d = Double.parseDouble(m.group(1));
                        sss=sss+String.valueOf(d);
                        //System.out.println(d);
                    }
                    Double mt=Double.parseDouble(sss);
                    mt=mt/1000;
                    sss=String.valueOf(mt);
                }
                Double distance=Double.parseDouble(sss);
                schoolstudentdistancedummy.add(distance);
            }
            for(int pp=0;pp<schoolstudentdistancedummy.size();pp++){
                Log.e("```````````","`````````````````````````````````````````````````new: "+schoolstudentdistancedummy.get(pp));
            }
            /*Collections.sort(schoolstudentdistancedummy);
            Collections.reverse(schoolstudentdistancedummy);*/
            ArrayList<StringIntTuple> list123 = new ArrayList<StringIntTuple>();
            for(int i =0; i<schoolstudentdistancedummy.size(); i++){
                list123.add(new StringIntTuple(schoolstudentdistancedummy.get(i),Integer.toString(i)));
            }
            Collections.sort(list123,new StringIntTupleIntComparator());
            for(int pp=0;pp<list123.size();pp++){
                Log.e("```````````","`````````````````````````````````````````````````new sort: "+list123.get(pp));
                String indexstring=list123.get(pp).toString();
                int index=indexstring.indexOf(",");
                int indexback=indexstring.indexOf(")");
                Log.e("```````````","`````````````````````````````````````````````````new sort split: "+indexstring.substring(index+1,indexback));
                dummyindex.add(Integer.parseInt(indexstring.substring(index+1,indexback)));
            }
            if(routetypenavigation.equalsIgnoreCase("1")){
                Collections.reverse(dummyindex);
                for(int rr=0;rr<dummyindex.size();rr++){
                    dummyselectedstudent.add(selectedstudents.get(dummyindex.get(rr)));
                    dummyselectedstudentname.add(selectedstudentsname.get(dummyindex.get(rr)));
                    dummycolorchangelat.add(colorchangelat.get(dummyindex.get(rr)));
                    dummycolorchangelan.add(colorchangelan.get(dummyindex.get(rr)));
                    dummystudentid.add(studentidselected.get(dummyindex.get(rr)));
                    Log.e("Change the list","lllllllllllllllllllllllllllllllllllllllllwwwwwwwwwwpickup");
                }
                selectedstudents.clear();
                selectedstudentsname.clear();
                colorchangelat.clear();
                colorchangelan.clear();
                studentidselected.clear();
                for(int gg=0;gg<dummystudentid.size();gg++){
                    selectedstudents.add(dummyselectedstudent.get(gg));
                    selectedstudentsname.add(dummyselectedstudentname.get(gg));
                    colorchangelat.add(dummycolorchangelat.get(gg));
                    colorchangelan.add(dummycolorchangelan.get(gg));
                    studentidselected.add(dummystudentid.get(gg));
                    Log.e("Change the list","lllllllllllllllllllllllllllllllllllllllll");
                }
                new progessdialogbox().execute();
            }
            else {
                for(int rr=0;rr<dummyindex.size();rr++){
                    dummyselectedstudent.add(selectedstudents.get(dummyindex.get(rr)));
                    dummyselectedstudentname.add(selectedstudentsname.get(dummyindex.get(rr)));
                    dummycolorchangelat.add(colorchangelat.get(dummyindex.get(rr)));
                    dummycolorchangelan.add(colorchangelan.get(dummyindex.get(rr)));
                    dummystudentid.add(studentidselected.get(dummyindex.get(rr)));
                    Log.e("Change the list","lllllllllllllllllllllllllllllllllllllllllwwwwwwwwwwdrop");
                }
                selectedstudents.clear();
                selectedstudentsname.clear();
                colorchangelat.clear();
                colorchangelan.clear();
                studentidselected.clear();
                for(int gg=0;gg<dummystudentid.size();gg++){
                    selectedstudents.add(dummyselectedstudent.get(gg));
                    selectedstudentsname.add(dummyselectedstudentname.get(gg));
                    colorchangelat.add(dummycolorchangelat.get(gg));
                    colorchangelan.add(dummycolorchangelan.get(gg));
                    studentidselected.add(dummystudentid.get(gg));
                    Log.e("Change the list","lllllllllllllllllllllllllllllllllllllllll");
                }
                new progessdialogbox().execute();
            }
        }
    }

    private void sendRequest() {
        mMap.clear();
        Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% only twice");
        selectedstudentdistance.clear();
        selectedstudenttime.clear();
        progressDialog = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_wait),
                getResources().getString(R.string.sj_finding_direction), true);
        /*for(int j=selectedstudents.size()-1;j>=0;j--){
            selectedstudentsreverse.add(selectedstudents.get(j));
        }*/

        if(routetypenavigation.equalsIgnoreCase("1")){
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
                if(i==selectedstudents.size()-2){
                    String origin1 = yll;
                    String destination1 = yll;
                    try {
                        new DirectionFinder(this, origin, destination).execute();

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

            }
            if(selectedstudents.size()>0){
                String comb=globalschoollat+","+globalschoollan;
                String xlll=selectedstudents.get(selectedstudents.size()-1);
                String ylll=comb;
                String originl = xlll;
                String destinationl = ylll;
                try {
                    Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% insidedirection");
                    new DirectionFinder(this, originl, destinationl).execute();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String xllll=comb;
                String yllll=comb;
                String originll = xllll;
                String destinationll = yllll;
                try {
                    Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% insidedirection");
                    new DirectionFinder(this, originll, destinationll).execute();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            //LatLng schoolcoo = new LatLng(Double.parseDouble(globalschoollat), Double.parseDouble(globalschoollan));
            Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% drop");
            if(selectedstudents.size()>0){
                String comb=globalschoollat+","+globalschoollan;
                String xlll=comb;
                String ylll=selectedstudents.get(0);
                String originl = xlll;
                String destinationl = ylll;
                try {
                    Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% insidedirection");
                    new DirectionFinder(this, originl, destinationl).execute();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
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
                if(i==selectedstudents.size()-2){
                    String origin1 = yll;
                    String destination1 = yll;
                    try {
                        new DirectionFinder(this, origin, destination).execute();

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
        Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% final can call");
        //calculatedistancetime();
        selectedstudentsreverse.clear();
        cordinates();

    }

    public void calculatedistancetime(){
        globaldistance=0;
        globaltime=0;
        Double distance=0.0;
        Double time=0.0;

        for(int i=0;i<selectedstudentdistance.size()-1;i++){
            String sss="";
                if(selectedstudentdistance.get(i).contains("km")){
                    Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(selectedstudentdistance.get(i));
                    while (m.find())
                    {
                        double d = Double.parseDouble(m.group(1));
                        sss=sss+String.valueOf(d);
                        //System.out.println(d);
                    }
                }
                else{
                    Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(selectedstudentdistance.get(i));
                    while (m.find())
                    {
                        double d = Double.parseDouble(m.group(1));
                        sss=sss+String.valueOf(d);
                        //System.out.println(d);
                    }
                    Double mt=Double.parseDouble(sss);
                    mt=mt/1000;
                    sss=String.valueOf(mt);
                }
            distance=distance+Double.parseDouble(sss);
            Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% inside distance :"+sss);
        }
        for(int i=0;i<selectedstudenttime.size()-1;i++){
            String sss="";
            if(selectedstudenttime.get(i).contains("hours")){
                String withhours=selectedstudenttime.get(i);
                String hours=withhours.substring(0, withhours.indexOf(" "));
                Double hoursdouble=Double.parseDouble(hours);
                hoursdouble=hoursdouble*60;
                String mins=withhours.replace("hours","");
                mins=mins.replace("mins","");
                mins=mins.substring(mins.indexOf(" "));
                Double minsdouble=Double.parseDouble(mins);
                Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% hours:"+hoursdouble);
                Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% mins :"+mins);
                Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% total:"+(hoursdouble+minsdouble));
                sss=(hoursdouble+minsdouble)+"";
            }
            else{
                Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(selectedstudenttime.get(i));
                while (m.find())
                {
                    double d = Double.parseDouble(m.group(1));
                    sss=sss+String.valueOf(d);
                    //System.out.println(d);
                }
            }
            time=time+Double.parseDouble(sss);
            Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% inside time :"+sss);
        }
        Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Total Distance :"+distance);
        Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Total Time :"+time);
        Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Total No of Stops :"+(selectedstudentdistance.size()));
        Double stoptime=(double)selectedstudentdistance.size()*2;
        //time=time+stoptime;
        DecimalFormat numberFormat = new DecimalFormat("#0.0");
        DecimalFormat numberFormatDistance = new DecimalFormat("#0.00");
        EditText distim=(EditText)findViewById(R.id.editdistancetimebosbox);
        if(time>=60){
            int hours = time.intValue() / 60; //since both are ints, you get an int
            int minutes = time.intValue() % 60;
            distim.setText("Distance: "+numberFormatDistance.format(distance)+" KM, Time: "+hours+" Hours "+minutes+" Mins");
            distim.setTextColor(Color.RED);
        }
        else {
            distim.setText("Distance: "+numberFormatDistance.format(distance)+" KM, Time: "+numberFormat.format(time)+" Mins");
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
    public void cordinates(){
        LatLng schoolcoo = new LatLng(Double.parseDouble(globalschoollat), Double.parseDouble(globalschoollan));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(schoolcoo,9));
        Marker mmmm=mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue1))
                .title("School")
                .position(schoolcoo));
        for(int i=0;i<latlist.size();i++) {
            xl = Double.parseDouble(latlist.get(i));
            yl = Double.parseDouble(longlist.get(i));
            LatLng hcmus = new LatLng(xl, yl);
            Log.e(xl + "Latitudes in loop", latlist.get(i));
            Log.e(yl + "Longitudes in loop", longlist.get(i));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));
            if(teachermarker.get(i).equalsIgnoreCase("0")){
                Marker m=mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .title(stops.get(i))
                        .position(hcmus));
                m.setTag(studentidfromserver.get(i));
                studentMarkers.add(m);
            }
            else
            {
                Marker m=mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.teacher_blue))
                        .title(stops.get(i))
                        .position(hcmus));
                m.setTag(studentidfromserver.get(i));
                studentMarkers.add(m);

            }

           /* studentMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(stops.get(i))
                    .position(hcmus)));*/

        }
//        if(!globalstop.equals("default")) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(globallatlng,15));
//            studentMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
//                    .title(globalstop)
//                    .position(globallatlng)));
//        }


        if(firsttimezero==0){
            LatLng latLng = new LatLng(schoollat, schoollong);
            Marker m=(mMap.addMarker(new MarkerOptions()
                    .visible(false)
                    .position(latLng)));
            firsttimezero=1;
        }
        for(int f=0;f<selectedstudents.size();f++){
            xlc = Double.parseDouble(colorchangelat.get(f));
            ylc = Double.parseDouble(colorchangelan.get(f));
            LatLng hcmus = new LatLng(xlc, ylc);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus));
            //Log.e("yesss","^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^4");
            if(selectedstudentsname.get(f).contains("TEACHER")){
                Log.e("yesss","^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^6");
                Marker m=mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(selectedstudentsname.get(f))
                        .position(hcmus));
                m.setTag(studentidselected.get(f));
            }
            else{
                Marker m=mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(selectedstudentsname.get(f))
                        .position(hcmus));
                m.setTag(studentidselected.get(f));
                studentMarkers.add(m);
            }

            /*studentMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(stops.get(f))
                    .position(hcmus)));*/

        }
        if(!selectedstudentsname.isEmpty()){
            Log.e("inside list generator","+++++++++++++++++++++++++++++++++++++++++++++++");
            listgenerate();
        }
        else if(lastdelete==1){
            Log.e("inside list generator","+++++++++++++++++++++++++++++++++++++++++++++++");
            lastdelete=0;
            listgenerate();
        }
        mMap.setOnMarkerClickListener(this);
    }

    public void listgenerate(){
        ArrayList<SearchResultStudentSelection> searchResults = GetSearchResults();
        final ListView lv1 = (ListView) findViewById(R.id.studentlistselected);
        lv1.setLongClickable(true);
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                Log.e("long clicked","pos: " + pos);
                Log.e("Student Selected",selectedstudentsname.get(pos));
                removestudent=selectedstudentsname.get(pos);
                removeposition=pos;
                if(pos<countinitialstudents){
                    Toast.makeText(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_student_teacher_already_exist),
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    removePopupWindow();
                }
                return true;
            }
        });
        lv1.setAdapter(new MyCustomBaseStudentSelectionAdapter(this, searchResults));
    }

    private ArrayList<SearchResultStudentSelection> GetSearchResults(){
        ArrayList<SearchResultStudentSelection> results = new ArrayList<SearchResultStudentSelection>();
        for(int i=0;i<selectedstudentsname.size();i++) {
            Log.e("++++++++++++++++++++++",selectedstudentsname.get(i));
            SearchResultStudentSelection sr1 = new SearchResultStudentSelection();
            sr1.setStudentnameresult(selectedstudentsname.get(i));
            results.add(sr1);
        }
        return results;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String schoollocation=marker.getTitle();
        if(schoollocation.equalsIgnoreCase("School")){
            schoollocation="empty";
            return true;
        }
        String seatcapacity;
        String ssss="";
        int busassigned=0;
        int totalstudentsselected=0;
        int remainingseats=9999999;
        Log.e("#####","#####################################################routeid: "+routeidfromserver.get(routeidno));
        if(routetypenavigation.equalsIgnoreCase("1")){
            for(int rr=0;rr<bustypenumberFromServer.size();rr++){
                if(buspickupidFromServer.get(rr).equalsIgnoreCase(routeidfromserver.get(routeidno))){
                    busassigned=1;
                    seatcapacity=bustypenumberFromServer.get(rr);

                        Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(seatcapacity);
                        while (m.find())
                        {
                            double d = Double.parseDouble(m.group(1));
                            ssss=ssss+String.valueOf(d);
                            //System.out.println(d);
                        }
                    Log.e("########","#########################################seatcapacity: "+seatcapacity);
                    Log.e("########","#########################################ssss "+ssss);
                    break;
                }
            }
        }
        else {
            for(int rr=0;rr<bustypenumberFromServer.size();rr++){
                if(busdropidFromServer.get(rr).equalsIgnoreCase(routeidfromserver.get(routeidno))){
                    busassigned=1;
                    seatcapacity=bustypenumberFromServer.get(rr);

                    Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(seatcapacity);
                    while (m.find())
                    {
                        double d = Double.parseDouble(m.group(1));
                        ssss=ssss+String.valueOf(d);
                        //System.out.println(d);
                    }
                    Log.e("########","#########################################seatcapacity: "+seatcapacity);
                    Log.e("########","#########################################ssss: "+ssss);
                    break;
                }
            }
        }
        Log.e("#########","#########################################no of selected students "+selectedstudentsname.size());
        if(busassigned==1){
            Double dummy=Double.parseDouble(ssss);
            remainingseats= dummy.intValue()-selectedstudentsname.size();
            Log.e("###########","#########################################remaining seats "+remainingseats);
        }
        dlonlist.clear();
        dlatlist.clear();
        slatlist.clear();
        slonlist.clear();
        globalstoplist.clear();
        studentidlist.clear();
        int[] location = new int[2];
         dlat =marker.getPosition().latitude;
         dlatlist.add(marker.getPosition().latitude);
         dlon =marker.getPosition().longitude;
         dlonlist.add(marker.getPosition().longitude);
         globalstop= marker.getTitle();
         globalstoplist.add(marker.getTitle());
         slat = String.valueOf(dlat);
         slatlist.add(String.valueOf(dlat));
         slon = String.valueOf(dlon);
         slonlist.add(String.valueOf(dlon));
         studentidgot=(String)marker.getTag();
         studentidgotfull=(String)marker.getTag();
         studentidlist.add((String)marker.getTag());
        Log.e("get tag","================================================================"+studentidgot);
        Log.e("get tag","================================================================"+studentidgotfull);
        if(studentidgot.contains("teacher")||studentidgot.contains("student")){
            int num=studentidgot.indexOf("-");
            studentidgot=studentidgot.substring(0,num);
        }
        Log.e("get tag","================================================================"+studentidgot);
        Log.e("get tag","================================================================"+globalstop);
        totalstudentsselected=totalstudentsselected+1;
        String latlong=slat+","+slon;
        for(int ii=0;ii<list.size();ii++){
            if(stops.get(ii).equalsIgnoreCase(globalstop)){
                int rownumber=ii;
                for(int jj=0;jj<list.size();jj++){
                    if(finaldistance[rownumber][jj]==1){
                        Log.e("stop beside ","================================================================"+stops.get(jj));
                        Log.e("stop beside ","================================================================"+jj);
                        int yesorno=studentcheckbesidefromlist(Integer.parseInt(studentidfromserver.get(jj)));
                        if(yesorno==1){
                            Log.e("stop beside ","================================================================EXISTS");
                        }
                        else {
                            Log.e("stop beside ","================================================================DOES NOT EXISTS");
                            totalstudentsselected=totalstudentsselected+1;
                            dlatlist.add(Double.parseDouble(latlist.get(jj)));
                            dlonlist.add(Double.parseDouble(longlist.get(jj)));
                            slatlist.add(latlist.get(jj));
                            slonlist.add(longlist.get(jj));
                            globalstoplist.add(stops.get(jj));
                            studentidlist.add(studentidfromserver.get(jj));
                        }

                    }
                }
            }
        }
        Log.e("+++++++++++++ ","================================================================Total Students"+totalstudentsselected);
        int checkstudentreturnvalue=studentcheckfromlist();
        if(checkstudentreturnvalue==1){
            int studentline=0;
            for(int dd=0;dd<studentidselected.size();dd++){
                Log.e("+++++++++++++ ","=======================================================idnew : "+studentidselected.get(dd));
                int newrow=studentidselected.get(dd).indexOf("-");
                if(studentidgot.equalsIgnoreCase(studentidselected.get(dd).substring(0,newrow))){
                    studentline=dd+1;
                    break;
                }
            }
            Toast.makeText(Student_Selection_Search_Admin_Navigation.this, globalstop+"-"+getResources().getString(R.string.sj_stop_number)+" "+studentline+" : "+getResources().getString(R.string.sj_already_exist_in_the_route),
                    Toast.LENGTH_SHORT).show();
            Log.e("+++++++++++++ ","==========================================================id: "+studentidgot);
            dlonlist.clear();
            dlatlist.clear();
            slatlist.clear();
            slonlist.clear();
            globalstoplist.clear();
            studentidlist.clear();
        }
        else {
            if(totalstudentsselected>remainingseats){
                Toast.makeText(Student_Selection_Search_Admin_Navigation.this,getResources().getString(R.string.sj_seats_not_avaliable)+", "+getResources().getString(R.string.sj_student_selected)+" "+totalstudentsselected+" , "+getResources().getString(R.string.sj_seats_remaining_in_bus)+" "+remainingseats,
                        Toast.LENGTH_SHORT).show();
            }
            else {
                initiatePopupWindow();
            }

            /*if(addornotpopup==2){
                Log.e("Always inside","#################################################");
            }
            else if(addornotpopup==1){
                globallatlng = new LatLng(dlat, dlon);
                selectedstudentsname.add(globalstop);
                selectedstudents.add(latlong);
                colorchangelat.add(slat);
                colorchangelan.add(slon);
                studentidselected.add(studentidgot + "-student");
                Log.e("Stop name :", globalstop);
                Log.e("Latitudes in listener", slat);
                Log.e("Longitudes in listener", slon);
                Log.e("Student Id", studentidgot);
                new progessdialogbox().execute();
            }*/


        }
        //sendRequest();
        return true;
    }

    public int studentcheckfromlist(){
        int returnvalue=0;
        for(int iii=0;iii<studentidselected.size();iii++){
            Log.e("inside","studentlist");
            Log.e("next1111","  "+(studentidgot+"-student")+"  "+studentidselected.get(iii));
            if((studentidgot+"-student").equals(studentidselected.get(iii))&&!globalstop.contains("TEACHER")){
                Log.e("inside","studentlist1");
                returnvalue=1;
                break;
            }
            else if((studentidgot+"-teacher").equals(studentidselected.get(iii))&&globalstop.contains("TEACHER")){
                returnvalue=1;
                break;
            }

        }
        return returnvalue;
    }

    public int studentcheckbesidefromlist(int k){
        int returnvalue=0;
        Log.e("inside","studentlist2");
        for(int iii=0;iii<studentidselected.size();iii++){
            if((k+"-student").equals(studentidselected.get(iii))&&!globalstop.contains("TEACHER")){
                Log.e("inside","studentlist");
                returnvalue=1;
                break;
            }
            else if((k+"-teacher").equals(studentidselected.get(iii))&&globalstop.contains("TEACHER")){
                returnvalue=1;
                break;
            }

        }
        return returnvalue;
    }

    public void findLocation() {
        searchclick=1;
        new searchprogessbox().execute();
    }
    private void sendRequestsearch() {
        progressDialog = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_wait),
                getResources().getString(R.string.sj_finding_location), true);
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
        Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%global count: "+globalcalculate);
        if(globalcalculate==1){
            for (Route route : routes) {
                    Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%school student distance"+route.distance.text);
                    schoolstudentdistance.add(route.distance.text);
                    Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%school student time"+route.duration.text);
                    schoolstudenttime.add(route.duration.text);
                    //calculatedistancetime();
            }
            finaldistance();
        }
        else{
            for (Route route : routes) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
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
                if(searchclick==0){
                    Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+route.distance.text);
                    selectedstudentdistance.add(route.distance.text);
                    Log.e("%%%%%%","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+route.duration.text);
                    selectedstudenttime.add(route.duration.text);
                    calculatedistancetime();
                }
                searchclick=0;
                polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
        }
    }
    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            /*LayoutInflater inflater1 = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            *///fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);
            LayoutInflater inflater = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup,
                    (ViewGroup) findViewById(R.id.popup_element));

            pwindo = new PopupWindow(layout, 500,300, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            layout_MainMenu.getForeground().setAlpha(200);
            TextView studentnamedisply=(TextView)layout.findViewById(R.id.blueStudentnametext);
            final TextView studentlistnamedisply=(TextView)layout.findViewById(R.id.txtstudentlistView);
            btnAddPopup = (Button) layout.findViewById(R.id.btn_add_popup);
            btnClosePopup=(Button) layout.findViewById(R.id.btn_close_popup);
            //studentnamedisply.setText("   "+globalstop);
            studentnamedisply.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            if(globalstoplist.size()>=2){
                String names="Selected Students: ";
                for(int iii=0;iii<globalstoplist.size();iii++){
                    if(iii==0){
                        names=names+globalstoplist.get(iii);
                    }
                    else{
                        names=names+", "+globalstoplist.get(iii);
                    }
                }
                studentlistnamedisply.setText(" "+names);
            }
            else{
                studentlistnamedisply.setText("Selected Students: "+globalstop);
            }
            // btnClosePopup.setOnClickListener(cancel_button_click_listener);
            btnAddPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(globalstoplist.size()>=2){
                        for(int iii=0;iii<globalstoplist.size();iii++){
                            String latlong=slatlist.get(iii)+","+slonlist.get(iii);
                            globallatlng = new LatLng(dlatlist.get(iii), dlonlist.get(iii));
                            selectedstudentsname.add(globalstoplist.get(iii));
                            selectedstudents.add(latlong);
                            colorchangelat.add(slatlist.get(iii));
                            colorchangelan.add(slonlist.get(iii));
                            if(globalstop.contains("TEACHER")){
                                Log.e("&&&&&&&7","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$44");
                                studentidselected.add(studentidlist.get(iii) + "-teacher");
                            }
                            else{
                                studentidselected.add(studentidlist.get(iii) + "-student");
                            }
                            Log.e("Stop name :", globalstoplist.get(iii));
                            Log.e("Latitudes in listener", slatlist.get(iii));
                            Log.e("Longitudes in listener", slonlist.get(iii));
                            Log.e("Student Id", studentidlist.get(iii));
                        }
                        dlonlist.clear();
                        dlatlist.clear();
                        slatlist.clear();
                        slonlist.clear();
                        globalstoplist.clear();
                        studentidlist.clear();
                        Log.e("^^^^^^^^^^^","^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^if two");

                        new progessdialogbox().execute();
                        layout_MainMenu.getForeground().setAlpha( 0);
                        pwindo.dismiss();
                    }
                    else{
                        String latlong=slat+","+slon;
                        globallatlng = new LatLng(dlat, dlon);
                        selectedstudentsname.add(globalstop);
                        selectedstudents.add(latlong);
                        colorchangelat.add(slat);
                        colorchangelan.add(slon);
                        if(globalstop.contains("TEACHER")){
                            Log.e("&&&&&&&7","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$44");
                            studentidselected.add(studentidgot + "-teacher");
                        }
                        else{
                            studentidselected.add(studentidgot + "-student");
                        }
                        Log.e("Stop name :", globalstop);
                        Log.e("Latitudes in listener", slat);
                        Log.e("Longitudes in listener", slon);
                        Log.e("Student Id", studentidgot);
                        new progessdialogbox().execute();
                        layout_MainMenu.getForeground().setAlpha( 0);
                        pwindo.dismiss();
                    }
                }
            });
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dlonlist.clear();
                    dlatlist.clear();
                    slatlist.clear();
                    slonlist.clear();
                    globalstoplist.clear();
                    studentidlist.clear();
                    layout_MainMenu.getForeground().setAlpha( 0);
                    pwindo.dismiss();
                    //fadePopup.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void removePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            /*LayoutInflater inflater1 = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            *///fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);
            LayoutInflater inflater = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_remove,
                    (ViewGroup) findViewById(R.id.popup_element));

            pwindo = new PopupWindow(layout, 500,300, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            layout_MainMenu.getForeground().setAlpha( 200);
            TextView studentnamedisply=(TextView)layout.findViewById(R.id.blueStudentnameremovetext);
            TextView studentnamelistdisply=(TextView)layout.findViewById(R.id.txtremovestudentView);
            btnAddRemovePopup = (Button) layout.findViewById(R.id.btn_remove_popup);
            btnCloserRemovePopup=(Button) layout.findViewById(R.id.btn_closeremove_popup);
            //studentnamedisply.setText("   "+removestudent);
            studentnamelistdisply.setText("Selected Student: "+removestudent);
            studentnamedisply.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            // btnClosePopup.setOnClickListener(cancel_button_click_listener);
            btnAddRemovePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("###",selectedstudentsname.get(removeposition));
                    Log.e("###",selectedstudents.get(removeposition));
                    Log.e("###",colorchangelat.get(removeposition));
                    Log.e("###",colorchangelan.get(removeposition));
                    Log.e("###",studentidselected.get(removeposition));
                    selectedstudentsname.remove(removeposition);
                    selectedstudents.remove(removeposition);
                    colorchangelat.remove(removeposition);
                    colorchangelan.remove(removeposition);
                    studentidselected.remove(removeposition);
                    lastdelete=1;
                    //globallatlng = new LatLng(dlat, dlon);
                    /*selectedstudentsname.add(globalstop);
                    selectedstudents.add(latlong);
                    colorchangelat.add(slat);
                    colorchangelan.add(slon);
                    studentidselected.add(studentidgot + "-student");*/
                    new progessdialogbox().execute();
                    layout_MainMenu.getForeground().setAlpha( 0);
                    pwindo.dismiss();
                    //fadePopup.dismiss();

                }
            });
            btnCloserRemovePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout_MainMenu.getForeground().setAlpha( 0);
                    pwindo.dismiss();
                    //fadePopup.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


   public void canclemainmethod(View view){
       Intent intent = new Intent(this, Student_Route_Creation_Navigation.class);
       startActivity(intent);
       finish();
   }

    private void initiatePopupWindowhawkeyeAlert(String title,String message, String notification_message,final String type) {
        try {
            previous_alert_type=type;
            String busid="";
            String busname="";

            if(!type.equalsIgnoreCase("normal")){
                String[] split= type.split("~");
                busid=split[1];
                busname=split[2];
            }
            //Toast.makeText(Hawkeye_navigation.this,busid+busname,Toast.LENGTH_SHORT).show();
            final String id=busid;
            final String name=busname;

            LayoutInflater inflater = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_alert,(ViewGroup) findViewById(R.id.popup_element));
            if(!type.equalsIgnoreCase("normal"))
                layout = inflater.inflate(R.layout.screen_popup_alert_breakdown,(ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            layout_MainMenu.getForeground().setAlpha(200);
            //Log.e("Lets Check",busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")");
            topRow=(TextView)layout.findViewById(R.id.blueStudentnametexthawkeye);
            drivernamehawkeyetextbox=(TextView)layout.findViewById(R.id.txtViewdrivername);

            if(message.isEmpty())
                drivernamehawkeyetextbox.setVisibility(View.INVISIBLE);
            else
                drivernamehawkeyetextbox.setText(message);
            topRow.setText(title);

            btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_hawkeye);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(type.equalsIgnoreCase("normal")) {
                        layout_MainMenu.getForeground().setAlpha(0);
                        pwindo.dismiss();
                    }
                    else {
                        layout_MainMenu.getForeground().setAlpha(0);
                        pwindo.dismiss();
                        initiatePopupWindowhawkeyeReassign(type,id,name);
                    }
                    //fadePopup.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initiatePopupWindowhawkeyeReassign(String type,String busId, String busName) {
        try {
            frombusidintoserver="";
            tobusnameintoserver="";
            tobusidintoserver="";

            LayoutInflater inflater = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_reassign,(ViewGroup) findViewById(R.id.popup_element));

            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150,WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            layout_MainMenu.getForeground().setAlpha(200);
            //Log.e("Lets Check",busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")");

            brokendownBus=(TextView)layout.findViewById(R.id.brokendownbus);
            reassignBusSelect=(Spinner)layout.findViewById(R.id.selectreassignbusspinner);

            brokendownBus.setText(busName+"");
            frombusidintoserver=busId;

            if(busidforreassign.isEmpty())
                new getSpareBusFromServer().execute();

            //Toast.makeText(Hawkeye_navigation.this,busnameforreassign.size()+"",Toast.LENGTH_SHORT).show();

            final String[] busnamefrom=new String[busnameforreassign.size()+1];
            busnamefrom[0]=getResources().getString(R.string.sj_select_bus);
            for(int i=1;i<=busnameforreassign.size();i++){
                busnamefrom[i]=busnameforreassign.get(i-1);
                //Toast.makeText(Hawkeye_navigation.this,busnamefrom[i],Toast.LENGTH_SHORT).show();
            }


            ArrayAdapter<String> adapterbusname = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busnamefrom) {
                public View getView(int position, View convertView, ViewGroup parent) {
                    View v = super.getView(position, convertView, parent);
                    Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                    ((TextView) v).setTypeface(externalFont);
                    ((TextView) v).setTextSize(20);
                    ((TextView) v).setMinHeight(70);
                    return v;
                }
                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                    View v =super.getDropDownView(position, convertView, parent);
                    Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                    ((TextView) v).setTypeface(externalFont);
                    ((TextView) v).setTextSize(20);
                    ((TextView) v).setMinHeight(70);
                    return v;
                }
            };

            adapterbusname.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            reassignBusSelect.setAdapter(adapterbusname);


            reassignBusSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    tobusnameintoserver=item;

                    Log.e("spinner selected for ad",item);
                    for (int i = 0; i < busnameforreassign.size(); i++) {
                        if (tobusnameintoserver.equals(busnameforreassign.get(i))) {
                            tobusidintoserver = busidforreassign.get(i);

                        }
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            btnClosePopup=(Button)layout.findViewById(R.id.btn_reassign_bus);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        /*layout_MainMenu.getForeground().setAlpha(0);
                        pwindo.dismiss();*/
                    reassignBus();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void reassignBus(){

        //Toast.makeText(Hawkeye_navigation.this,frombusidintoserver+tobusidintoserver+tobusnameintoserver+"",Toast.LENGTH_SHORT).show();
        if(tobusnameintoserver.equalsIgnoreCase(getResources().getString(R.string.sj_select_bus))){
            Toast.makeText(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_select_bus),
                    Toast.LENGTH_LONG).show();
        }

        else
            new reassignbustoserver().execute();


    }


    class reassignbustoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                Jsonfunctions sh = new Jsonfunctions();
                ServiceModel reg = new ServiceModel();

                Log.e("add",frombusidintoserver+" "+tobusidintoserver);

                String url= Config.ip+"BusList_api/reassignBus/from_bus_id/"+frombusidintoserver+"/to_bus_id/"+tobusidintoserver;
                String response;

                try
                {
                    try
                    {
                        Log.e("url above service call",url);
                        String jsonStr = sh.makeServiceCall(url,Jsonfunctions.GET);
                        Log.e("url below service call",url);
                        if (jsonStr != null) {
                            try {
                                JSONObject Jobj = new JSONObject(jsonStr);
                                response=Jobj.getString("responsecode");
                                if(Jobj.getString("responsecode").equals("1"))
                                {
                                    //JSONArray jsonArray = Jobj.getJSONArray("result_arr");
                                    runOnUiThread(new Runnable(){

                                        @Override
                                        public void run(){
                                            //update ui here
                                            // display toast here
                                            Toast.makeText(Student_Selection_Search_Admin_Navigation.this, getResources().getString(R.string.sj_reassigned_successfully), Toast.LENGTH_SHORT).show();
                                        }
                                    });


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


            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            progressDialog2.dismiss();
            layout_MainMenu.getForeground().setAlpha(0);
            if(pwindo!=null&&pwindo.isShowing()) {
                pwindo.dismiss();
            }
        }
    }



    class getSpareBusFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                try {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg = new ServiceModel();
                    Log.e("url", Config.ip + "BusList_api/listSpareBuses");
                    String jsonStr1 = sh.makeServiceCall(Config.ip + "BusList_api/listSpareBuses", Jsonfunctions.GET);

                    if (jsonStr1 != null) {
                        try {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if (Jobj.getString("responsecode").equals("1")) {
                                JSONArray jsonArray = Jobj.getJSONArray("result_arr");

                                for (int j = 0; j < jsonArray.length(); j++) {

                                    JSONObject obj = jsonArray.getJSONObject(j);
                                    Log.e("+++", obj.getString("bus_Id"));
                                    String id = obj.getString("bus_Id");
                                    busidforreassign.add(id);
                                    Log.e("+++", obj.getString("name"));
                                    String name = obj.getString("name");
                                    busnameforreassign.add(name);

                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("ServiceHandler", "Couldn't get any data from the url");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }



    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent, imageUrl);
    }





    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }


    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Student_Selection_Search_Admin_Navigation.this);

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
                Intent intent = new Intent(Student_Selection_Search_Admin_Navigation.this, Student_Selection_Search_Admin_Navigation.class);
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
                Intent intent = new Intent(Student_Selection_Search_Admin_Navigation.this, Student_Selection_Search_Admin_Navigation.class);
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
        Student_Selection_Search_Admin_Navigation.this.getResources().updateConfiguration(config,
                Student_Selection_Search_Admin_Navigation.this.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Student_Selection_Search_Admin_Navigation.this, Student_Route_Creation_Navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student__selection__search__admin__navigation, menu);
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
            Intent intent = new Intent(this, Hawkeye_navigation.class);
            startActivity(intent);
        } else if (id == R.id.routes) {
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();
        } else if (id == R.id.attendence) {
            Intent intent = new Intent(this, Student_Attendence_Navigation.class);
            startActivity(intent);

        } else if (id == R.id.transfer) {
            Intent intent = new Intent(this, Transfer_Student_Navigation.class);
            startActivity(intent);

        } else if (id == R.id.driverrating) {
            Intent intent = new Intent(this, drivermeritnavigation.class);
            startActivity(intent);
        } else if (id == R.id.messaging) {
            Intent intent = new Intent(this, Messaging_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.contractcreation) {
            Intent intent = new Intent(this, Contract_Creation_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.buscreationclick) {
            Intent intent = new Intent(this, Bus_Creation_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.drivercreationclick) {
            Intent intent = new Intent(this, Driver_Create_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.managefuel) {
            Intent intent = new Intent(this, Manage_Fuel_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.studentmisbehaviour) {
            Intent intent = new Intent(this, Student_Misbehaviour_Navigation.class);
            startActivity(intent);

        }
        else if (id == R.id.changelanguage) {
            showAlertForLanguage();
        }
        else if (id == R.id.leave) {
            Intent intent = new Intent(this, Leave_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.accident) {
            Intent intent = new Intent(this, Accident_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.breakdowns) {
            Intent intent = new Intent(this, Breakdowns_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.studentlistnew) {
            Intent intent = new Intent(this, Student_List_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.reportsfour) {
            Intent intent = new Intent(this, Reports_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.logout) {
            db.delete("OneTimeLogin", null, null);
            Intent intent = new Intent(this, LoginAdmin.class);
            startActivity(intent);
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
