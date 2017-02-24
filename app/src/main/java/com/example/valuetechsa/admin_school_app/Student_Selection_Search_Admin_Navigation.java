package com.example.valuetechsa.admin_school_app;

import android.*;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    String studentidgot;
    int driverlistno,buslistno,routeidno,lineno;
    int editrouteid;
    Jsonfunctions sh;
    int addornotpopup=0;
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
    ArrayList<String> teacherlatlong=new ArrayList<String>();
    ArrayList<String> EditStopName=new ArrayList<String>();
    ArrayList<String> EditLatitudeLongitude=new ArrayList<String>();
    ArrayList<String> EditLatitude=new ArrayList<String>();
    ArrayList<String> EditLongitude=new ArrayList<String>();
    ArrayList<String> EditAssignTo=new ArrayList<String>();
    ArrayList<String> EditDriverIdFromServer=new ArrayList<String>();
    ArrayList<String> EditBusIdFromServer=new ArrayList<String>();
    LinearLayout back_dim_layout;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    String slat="";
    String slon="";
    int countinitialstudents=0;
    double dlat =0;
    double dlon=0;
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
    String nameuser,passuser,adminid,adminname,schoolcordinates;
    double schoollat,schoollong;
    String[] schoolcordarray=new String[100000];
    int firsttimezero=0;

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

        TextView TextViewNewFont = new TextView(Student_Selection_Search_Admin_Navigation.this);
        TextViewNewFont.setText("Student Selection");
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
            if(routetypenavigation.equalsIgnoreCase("drop")){
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
        new getMapDataToServer().execute();
        new getDriverListFromServer().execute();
        new getRoutesFromServer().execute();
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
            new progessdialogbox().execute();
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

            cordinates();
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
            progressDialog2 = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, "Please wait.",
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

    public void dropdownlist(){
        driverdropdown = (Spinner)findViewById(R.id.selectdriverspinner);
        busdropdown = (Spinner)findViewById(R.id.selectbusspinner);
        int sum=drivernamefromserver.size();
        sum=sum+1;
        String[] driveritems= new String[sum];
        driveritems[0]="Select Driver";
        for(int i=1;i<=drivernamefromserver.size();i++){
            driveritems[i]=drivernamefromserver.get(i-1);
        }
        int sum1=busnamefromserver.size();
        sum1=sum1+1;
        String[] busitems=new String[sum1];
        busitems[0]="Select Bus";
        for(int j=1;j<=busnamefromserver.size();j++){
            busitems[j]=busnamefromserver.get(j-1);
        }
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

        if(lineno!=0){
            int rowdriver=0;
            int rowbus=0;
            String driverid=EditDriverIdFromServer.get(lineno-1);
            String busid=EditBusIdFromServer.get(lineno-1);
            for(int r=0;r<driveridfromserver.size();r++){
                if(driverid.equals(driveridfromserver.get(r))){
                    routedrivernamenavigation=drivernamefromserver.get(r);
                    rowdriver=r+1;
                }

            }
            for(int t=0;t<busidfromserver.size();t++){
                if(busid.equals(busidfromserver.get(t))){
                    routebusnonavigation=busnamefromserver.get(t);
                    rowbus=t+1;
                }
            }
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
       if(routedrivernamenavigation.equals("Select Driver")){
           Toast.makeText(Student_Selection_Search_Admin_Navigation.this, "Please Select Driver",
                   Toast.LENGTH_SHORT).show();
       }
       else if(routebusnonavigation.equals("Select Bus")){
           Toast.makeText(Student_Selection_Search_Admin_Navigation.this, "Please Select Bus",
                   Toast.LENGTH_SHORT).show();
       }
       else{
           new addstopstoserver().execute();
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
            progressDialog2 = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, "Please wait.",
                    "Adding Details", true);

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
            Toast.makeText(Student_Selection_Search_Admin_Navigation.this, "Route Created" ,
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Student_Selection_Search_Admin_Navigation.this, Student_Route_Creation_Navigation.class);
            startActivity(intent);
            finish();
        }
    }


    private void sendRequest() {
        mMap.clear();
        progressDialog = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, "Please wait.",
                "Finding direction..!", true);
        /*for(int j=selectedstudents.size()-1;j>=0;j--){
            selectedstudentsreverse.add(selectedstudents.get(j));
        }*/
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
        selectedstudentsreverse.clear();
        cordinates();

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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,9));
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
                    Toast.makeText(Student_Selection_Search_Admin_Navigation.this, "Student/Teacher Already Exist, Can't Remove, Make Transfer ",
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
        int[] location = new int[2];
         dlat =marker.getPosition().latitude;
         dlon =marker.getPosition().longitude;
        globalstop= marker.getTitle();
         slat = String.valueOf(dlat);
         slon = String.valueOf(dlon);
        studentidgot=(String)marker.getTag();
        Log.e("get tag","================================================================"+studentidgot);
        if(studentidgot.contains("teacher")||studentidgot.contains("student")){
            studentidgot=studentidgot.substring(0,1);
        }
        Log.e("get tag","================================================================"+studentidgot);
        Log.e("get tag","================================================================"+globalstop);
        String latlong=slat+","+slon;
        int checkstudentreturnvalue=studentcheckfromlist();
        if(checkstudentreturnvalue==1){
            Toast.makeText(Student_Selection_Search_Admin_Navigation.this, globalstop+" Already Exist In The Route",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            initiatePopupWindow();

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
            if((studentidgot+"-student").equals(studentidselected.get(iii))&&!globalstop.contains("TEACHER")){
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

    public void findLocation() {
        new searchprogessbox().execute();
    }
    private void sendRequestsearch() {
        progressDialog = ProgressDialog.show(Student_Selection_Search_Admin_Navigation.this, "Please wait.",
                "Finding Location..!", true);
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

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater1 = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);
            LayoutInflater inflater = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup,
                    (ViewGroup) findViewById(R.id.popup_element));

            pwindo = new PopupWindow(layout, 500,400, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            TextView studentnamedisply=(TextView)layout.findViewById(R.id.blueStudentnametext);
            btnAddPopup = (Button) layout.findViewById(R.id.btn_add_popup);
            btnClosePopup=(Button) layout.findViewById(R.id.btn_close_popup);
            studentnamedisply.setText("   "+globalstop);
            studentnamedisply.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            // btnClosePopup.setOnClickListener(cancel_button_click_listener);
            btnAddPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                    pwindo.dismiss();
                    fadePopup.dismiss();

                }
            });
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    fadePopup.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void removePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            LayoutInflater inflater1 = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);
            LayoutInflater inflater = (LayoutInflater) Student_Selection_Search_Admin_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_remove,
                    (ViewGroup) findViewById(R.id.popup_element));

            pwindo = new PopupWindow(layout, 500,400, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            TextView studentnamedisply=(TextView)layout.findViewById(R.id.blueStudentnameremovetext);
            btnAddRemovePopup = (Button) layout.findViewById(R.id.btn_remove_popup);
            btnCloserRemovePopup=(Button) layout.findViewById(R.id.btn_closeremove_popup);
            studentnamedisply.setText("   "+removestudent);
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
                    //globallatlng = new LatLng(dlat, dlon);
                    /*selectedstudentsname.add(globalstop);
                    selectedstudents.add(latlong);
                    colorchangelat.add(slat);
                    colorchangelan.add(slon);
                    studentidselected.add(studentidgot + "-student");*/
                    new progessdialogbox().execute();
                    pwindo.dismiss();
                    fadePopup.dismiss();

                }
            });
            btnCloserRemovePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    fadePopup.dismiss();
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
