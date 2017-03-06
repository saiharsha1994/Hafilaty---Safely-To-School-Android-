package com.example.valuetechsa.admin_school_app;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.DB.DatabaseAdapter;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Student_Route_Creation_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    EditText txtTime,txtTimed;
    private int mHour, mMinute, mHourd, mMinuted;
    Spinner dropdown;
    String routenamenavigation="empty",routetypenavigation,routestarttymnavgation="empty",routeendtymnavigation="empty";
    String response="",url="";
    String timeampm;
    String selectday,selectminite;
    String starttym,endtym,startendtym;
    static int countroutes=0;
    Jsonfunctions sh;
    RelativeLayout seeornot;
    Typeface tfRobo,tfAdam;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    ProgressDialog progressDialog1,progressDialog2;
    ArrayList<String> serverroutelist  = new ArrayList<String>();
    ArrayList<String> serverroutetype=new ArrayList<String>();
    ArrayList<String> servertimelist  = new ArrayList<String>();
    ArrayList<String> serverstopslist  = new ArrayList<String>();
    ArrayList<String> serverdrivernamelist  = new ArrayList<String>();
    ArrayList<String> serverbusnolist  = new ArrayList<String>();
    ArrayList<String> finalserverdrivernamelist  = new ArrayList<String>();
    ArrayList<String> finalserverbusnolist  = new ArrayList<String>();
    ArrayList<String> drivernameroutefromserver=new ArrayList<String>();
    ArrayList<String> busnameroutefromserver=new ArrayList<String>();
    ArrayList<String> driveridroutefromserver=new ArrayList<String>();
    ArrayList<String> busidroutefromserver=new ArrayList<String>();
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
        setContentView(R.layout.activity_student__route__creation__navigation);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.routeheadernav);
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

        TextView TextViewNewFont = new TextView(Student_Route_Creation_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_routes));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Student_Route_Creation_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Student_Route_Creation_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);
        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
       /* action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp;Routes</big></font>"));*/

        dropdown = (Spinner)findViewById(R.id.selecttriptypespinner);
        String[] items = new String[]{getResources().getString(R.string.sj_select_route),getResources().getString(R.string.sj_pick_up),getResources().getString(R.string.sj_drop)};
       /* ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
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
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                routetypenavigation=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        txtTime=(EditText)findViewById(R.id.endtimebox);
        txtTimed=(EditText)findViewById(R.id.starttimebox);
        seeornot=(RelativeLayout)findViewById(R.id.relativelayoutroutecreeation);
        setLayoutInvisible();
        Log.i("harsha","Inside Oncreate");
        System.out.println("Inside Oncreate");

        fontchangestudentroute();
        new getRoutesFromServer().execute();
    }

    public void setLayoutInvisible() {
        if (seeornot.getVisibility() == View.VISIBLE) {
            seeornot.setVisibility(View.GONE);
        }
    }
    public void setLayoutVisible() {
        if (seeornot.getVisibility() == View.GONE) {
            seeornot.setVisibility(View.VISIBLE);
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void fontchangestudentroute(){
        TextView createnewroute=(TextView)findViewById(R.id.selectcriteriaroute);
        createnewroute.setText(getResources().getString(R.string.sj_create_new_route));
        TextView routename=(TextView)findViewById(R.id.routenameroute);
        TextView triptype=(TextView)findViewById(R.id.triptyperoute);
        TextView starttime=(TextView)findViewById(R.id.starttimeroute);
        TextView endtime=(TextView)findViewById(R.id.endtimeroute);
        EditText textroutname=(EditText)findViewById(R.id.routenamebox);
        EditText textstarttime=(EditText)findViewById(R.id.starttimebox);
        EditText textendtime=(EditText)findViewById(R.id.endtimebox);
        Button addro=(Button)findViewById(R.id.addroute);
        TextView routelist=(TextView)findViewById(R.id.routelisttextbox);
        TextView routetrip=(TextView)findViewById(R.id.routenametextbox);
        TextView startendtim=(TextView)findViewById(R.id.Startendtimetextbox);
        TextView nos=(TextView)findViewById(R.id.noofstopstextbox);
        TextView drivernamm=(TextView)findViewById(R.id.drivernametextbox);
        TextView busnooo=(TextView)findViewById(R.id.busnotextbox);
        TextView editroutee=(TextView)findViewById(R.id.editroutetextbox);
        routelist.setTypeface(tfRobo);
        routetrip.setTypeface(tfRobo);
        startendtim.setTypeface(tfRobo);
        nos.setTypeface(tfRobo);
        drivernamm.setTypeface(tfRobo);
        busnooo.setTypeface(tfRobo);
        editroutee.setTypeface(tfRobo);
        createnewroute.setTypeface(tfRobo);
        routename.setTypeface(tfRobo);
        triptype.setTypeface(tfRobo);
        starttime.setTypeface(tfRobo);
        endtime.setTypeface(tfRobo);
        textroutname.setTypeface(tfRobo);
        textstarttime.setTypeface(tfRobo);
        textendtime.setTypeface(tfRobo);
        addro.setTypeface(tfAdam);
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
                                    Log.e("+++",obj.getString("route_name"));
                                    serverroutelist.add(obj.getString("route_name"));
                                    Log.e("+++",obj.getString("start_time"));
                                    starttym=obj.getString("start_time");
                                    Log.e("+++",obj.getString("end_time"));
                                    endtym=obj.getString("end_time");
                                    startendtym=starttym+"-"+endtym;
                                    servertimelist.add(startendtym);
                                    Log.e("+++",obj.getString("stop_count"));
                                    serverstopslist.add(obj.getString("stop_count"));
                                    Log.e("+++",obj.getString("driver_id"));
                                    serverdrivernamelist.add(obj.getString("driver_id"));
                                    Log.e("+++",obj.getString("bus_id"));
                                    serverbusnolist.add(obj.getString("bus_id"));
                                    Log.e("+++",obj.getString("trip_type"));
                                    serverroutetype.add(obj.getString("trip_type"));
                                    countroutes=countroutes+1;

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
            if(!serverroutelist.isEmpty()){
                setLayoutVisible();
            }
            else{
                setLayoutInvisible();
            }
           //listgenerate();
            new getDriverListrouteFromServer().execute();
        }
    }

    public void listgenerate(){
        ArrayList<SearchResultRoute> searchResults = GetSearchResults();
        final ListView lv1 = (ListView) findViewById(R.id.routecreatedlist);
        lv1.setAdapter(new MyCustomBaseRouteAdapter(this, searchResults));
    }


    private ArrayList<SearchResultRoute> GetSearchResults(){
        ArrayList<SearchResultRoute> results = new ArrayList<SearchResultRoute>();


        for (int i=0;i<countroutes;i++){
            SearchResultRoute sr1 = new SearchResultRoute();
            sr1.setRoutenameresult(serverroutelist.get(i));
            Log.e("inside for",serverroutelist.get(i));
            sr1.setTimeresult(servertimelist.get(i));
            sr1.setNoofstopsresult(serverstopslist.get(i));
            sr1.setDrivernameresult(finalserverdrivernamelist.get(i));
            sr1.setBusnoresult(serverbusnolist.get(i));
            results.add(sr1);

        }
        countroutes=0;
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

                            /*txtTimed.setText(hourOfDay + ":" + minute);
                            routestarttymnavgation=hourOfDay + ":" + minute;*/
                            if(hourOfDay>=12) {
                                timeampm = "PM";
                            }
                            else{
                                timeampm = "AM";
                            }
                            if(hourOfDay<10){
                                selectday="0"+hourOfDay;
                            }
                            else
                            {
                                selectday=""+hourOfDay;
                            }
                            if((minute)<9){
                                selectminite="0"+(minute);
                            }
                            else{
                                selectminite=""+(minute);
                            }
                            txtTimed.setText(selectday + ":" + selectminite+" "+timeampm);
                            routestarttymnavgation=selectday + ":" + selectminite+" "+timeampm;
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
                            if(hourOfDay>=12) {
                                 timeampm = "PM";
                            }
                            else{
                                 timeampm = "AM";
                            }
                            if(hourOfDay<10){
                                selectday="0"+hourOfDay;
                            }
                            else
                            {
                                 selectday=""+hourOfDay;
                            }
                            if((minute)<9){
                                selectminite="0"+(minute);
                            }
                            else{
                                selectminite=""+(minute);
                            }
                            txtTime.setText(selectday + ":" + selectminite+" "+timeampm);
                            routeendtymnavigation=selectday  + ":" + selectminite+" "+timeampm;
                        }
                    }, mHourd, mMinuted, false);
            timePickerDialog.show();
        }

    }

    class adddetailstoserver extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){


        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                Log.e("after apply",routenamenavigation);
                Log.e("after apply",routetypenavigation);
                Log.e("after apply",routestarttymnavgation);
                Log.e("after apply",routeendtymnavigation);

                StringBuffer Route_Name=null,Route_Type=null,Route_Starttime=null,Route_Endtime=null;
                Route_Name=new StringBuffer();
                Route_Type=new StringBuffer();
                Route_Starttime=new StringBuffer();
                Route_Endtime=new StringBuffer();

                Route_Name.append(routenamenavigation+"-"+routetypenavigation);
                Route_Name.append(",");
                Route_Type.append(routetypenavigation);
                Route_Type.append(",");
                Route_Starttime.append(routestarttymnavgation);
                Route_Starttime.append(",");
                Route_Endtime.append(routeendtymnavigation);
                Route_Endtime.append(",");

                String strRoute_name="",strRoute_Type="",strRoute_Starttime="",strRoute_Endtime="";

                strRoute_name=Route_Name.substring(0,Route_Name.length()-1);
                //strRoute_Type=Route_Type.substring(0,Route_Type.length()-1);
                Log.e("#####","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2"+Route_Type);
                if(routetypenavigation.equalsIgnoreCase(getResources().getString(R.string.sj_pick_up)))
                    strRoute_Type=1+"";
                else
                    strRoute_Type=2+"";

                strRoute_Starttime=Route_Starttime.substring(0,Route_Starttime.length()-1);
                strRoute_Endtime=Route_Endtime.substring(0,Route_Endtime.length()-1);

                Log.e("after apply",strRoute_name);
                Log.e("after apply",strRoute_Type);
                Log.e("after apply",strRoute_Starttime);
                Log.e("after apply",strRoute_Endtime);

                sh = new Jsonfunctions();

                url= Config.ip+"GetRouteDetails_api/createRoute" +
                        "/route_name/"+ URLEncoder.encode(strRoute_name,"UTF-8")+"/trip_name/"+URLEncoder.encode(strRoute_Type,"UTF-8")+
                        "/start_time/"+URLEncoder.encode(strRoute_Starttime,"UTF-8")+"/end_time/"+URLEncoder.encode(strRoute_Endtime,"UTF-8");
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
            changeactivity();
        }
    }

    public void changeactivity(){
        Intent intent = new Intent(this, Student_Selection_Search_Admin_Navigation.class);
        intent.putExtra("RouteName", routenamenavigation+"-"+routetypenavigation);
        intent.putExtra("TripType", routetypenavigation);
        intent.putExtra("Lineno",0 );
        intent.putExtra("StartTime", routestarttymnavgation);
        intent.putExtra("EndTime", routeendtymnavigation);
        startActivity(intent);
        finish();
    }
    public void gotoselection(View view){
        EditText routename=(EditText)findViewById(R.id.routenamebox);
        routenamenavigation=routename.getText().toString();
        if(routenamenavigation.isEmpty()){
            Toast.makeText(Student_Route_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_route_name),
                    Toast.LENGTH_LONG).show();
        }
        else if(routestarttymnavgation.equals("empty")){
            Toast.makeText(Student_Route_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_start_time),
                    Toast.LENGTH_LONG).show();
        }
        else if(routeendtymnavigation.equals("empty")){
            Toast.makeText(Student_Route_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_end_time),
                    Toast.LENGTH_LONG).show();
        }
        else if(routetypenavigation.equals(getResources().getString(R.string.sj_select_route))){
            Toast.makeText(Student_Route_Creation_Navigation.this, getResources().getString(R.string.sj_please_select_trip_type) ,
                    Toast.LENGTH_LONG).show();
        }
        else{
            //changeactivity();
            new adddetailstoserver().execute();
        }



        /*Intent intent = new Intent(this, dummytestpage.class);
        startActivity(intent);*/
    }

    public void jasonmethod(){

    }

    public void editbuttonclick(View view){

        int lineno= (int)view.getTag();
        /*Toast.makeText(Student_Route_Creation_Navigation.this, "EDIT BUTTON CLICKED "+lineno,
                Toast.LENGTH_LONG).show();*/
        Intent intent = new Intent(this, Student_Selection_Search_Admin_Navigation.class);
        intent.putExtra("Lineno",lineno+1 );
        intent.putExtra("TripType",serverroutetype.get(lineno));
        startActivity(intent);
        finish();



    }



    class getDriverListrouteFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog1 = ProgressDialog.show(Student_Route_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                                    drivernameroutefromserver.add(drivername);
                                    Log.e("+++",obj.getString("driver_id"));
                                    String driverId=obj.getString("driver_id");
                                    driveridroutefromserver.add(driverId);
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
            new getBusListRouteFromServer().execute();
        }
    }

    class getBusListRouteFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Student_Route_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                                    busnameroutefromserver.add(busname);
                                    Log.e("+++",obj.getString("bus_Id"));
                                    String busId=obj.getString("bus_Id");
                                    busidroutefromserver.add(busId);
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
            //dropdownlist();
            driverbusname();
            progressDialog2.dismiss();
            listgenerate();
        }
    }

    public void driverbusname(){
        for(int jj=0;jj<serverdrivernamelist.size();jj++){
            for(int ii=0;ii<drivernameroutefromserver.size();ii++){
                if(serverdrivernamelist.get(jj).equals("0")){
                    finalserverdrivernamelist.add("No Driver");
                    break;
                }
                if(serverdrivernamelist.get(jj).equals(driveridroutefromserver.get(ii))){
                    finalserverdrivernamelist.add(drivernameroutefromserver.get(ii));
                    break;
                }
            }
        }

        for(int jj=0;jj<serverbusnolist.size();jj++){
            for(int ii=0;ii<busnameroutefromserver.size();ii++){
                if(serverbusnolist.get(jj).equals("0")){
                    finalserverbusnolist.add("No Bus");
                    break;
                }
                if(serverbusnolist.get(jj).equals(busidroutefromserver.get(ii))){
                    finalserverbusnolist.add(busnameroutefromserver.get(ii));
                    break;
                }
            }
        }

    }


    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Student_Route_Creation_Navigation.this);

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
                Intent intent = new Intent(Student_Route_Creation_Navigation.this, Student_Route_Creation_Navigation.class);
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
                Intent intent = new Intent(Student_Route_Creation_Navigation.this, Student_Route_Creation_Navigation.class);
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
        Student_Route_Creation_Navigation.this.getResources().updateConfiguration(config,
                Student_Route_Creation_Navigation.this.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Student_Route_Creation_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student__route__creation__navigation, menu);
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
