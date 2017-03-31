package com.example.valuetechsa.admin_school_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.DB.DatabaseAdapter;
import com.example.valuetechsa.admin_school_app.commonclass.CommonClass;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Student_Attendence_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TableRow[] listofrows=new TableRow[10000];
    int count=0;
    TableRow row;
    RelativeLayout tv;
    Typeface tfRobo,tfAdam;
    ProgressDialog progressDialog;
    static int countstudents=0,applybutton=0;
    static int staticday=0,staticmonth=0,staticyear=0;
    static String dateusergiven="empty";
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    ArrayList<String> studentnamelist  = new ArrayList<String>();
    ArrayList<String> studentidlist  = new ArrayList<String>();
    ArrayList<String> parentnamelist  = new ArrayList<String>();
    ArrayList<String> parentcontactnolist  = new ArrayList<String>();
    ArrayList<String> checkinlist  = new ArrayList<String>();
    ArrayList<String> checkoutlist  = new ArrayList<String>();
    ArrayList<String> routeidfromserverattendence=new ArrayList<String>();
    ArrayList<String> routenamefromserverattendence=new ArrayList<String>();
    Spinner attendeceRouteList,attendenceTripType;
    String routeattendenceselected,triptypeselected;
    String routeattendenceIdselected,triptypeIdselected;

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
        setContentView(R.layout.activity_student__attendence__navigation);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.attendenceheader);
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

        TextView TextViewNewFont = new TextView(Student_Attendence_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_attendance));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Student_Attendence_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Student_Attendence_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);


        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
        /*action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp;Attendence</big></font>"));*/

       /* Spinner dropdown = (Spinner)findViewById(R.id.selectroutespinner);
        String[] items = new String[]{"Morning","Evening"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);*/

        EditText DateEdit =  (EditText) findViewById(R.id.selectdatespinner);
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }
        });

        new getRoutesFromAttendenceServer().execute();

        tv = (RelativeLayout) findViewById(R.id.attendencereativelayoutnotlist);
        setLayoutInvisible();
        changescreen();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void setLayoutInvisible() {
        if (tv.getVisibility() == View.VISIBLE) {
            tv.setVisibility(View.GONE);
        }
    }
    public void setLayoutVisible() {
        if (tv.getVisibility() == View.GONE) {
            tv.setVisibility(View.VISIBLE);
        }
    }

    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText date = (EditText)getActivity().findViewById(R.id.selectdatespinner);
            date.setText(day + "/" + (month + 1) + "/" + year);
            String dateuser = year+"-"+(month+1)+"-"+day;
            Date DBdate=null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
            try
            {
                DBdate = format.parse(dateuser);
                System.out.println(DBdate);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            CommonClass cc=new CommonClass(getActivity());
            String postDate=cc.formatDateToString(DBdate, "yyyy-MM-dd", null);
            dateusergiven=postDate;

            if(staticday==0 && staticmonth==0 && staticyear==0){
                staticyear=year;
                staticmonth=month+1;
                staticday=day;
                applybutton=0;
            }
            else if(staticday!=day || staticmonth!=(month+1) || staticyear!=year){
                staticyear=year;
                staticmonth=month+1;
                staticday=day;
                applybutton=0;
            }

        }
    }

    public void changescreen(){
        TextView studentcre=(TextView)findViewById(R.id.selectcriteriaatt);
        TextView selectroute=(TextView)findViewById(R.id.selectroute);
        TextView selecttrip=(TextView)findViewById(R.id.selecttriptype);
        TextView selectdate=(TextView)findViewById(R.id.selectdate);
        EditText datepicke=(EditText)findViewById(R.id.selectdatespinner);
        Button applybutton=(Button)findViewById(R.id.applyattendence);
        TextView attreport=(TextView)findViewById(R.id.attendancereport);
        TextView stuname=(TextView)findViewById(R.id.studentnametextbox);
        TextView stuid=(TextView)findViewById(R.id.studentidtextbox);
        TextView stupar=(TextView)findViewById(R.id.parentnametextbox);
        TextView stucon=(TextView)findViewById(R.id.contactdetailstextbox);
        TextView stuckin=(TextView)findViewById(R.id.checkin);
        TextView stuckout=(TextView)findViewById(R.id.checkout);
        studentcre.setTypeface(tfRobo);
        selectroute.setTypeface(tfRobo);
        selecttrip.setTypeface(tfRobo);
        selectdate.setTypeface(tfRobo);
        datepicke.setTypeface(tfRobo);
        applybutton.setTypeface(tfAdam);
        attreport.setTypeface(tfRobo);
        stuname.setTypeface(tfRobo);
        stuid.setTypeface(tfRobo);
        stupar.setTypeface(tfRobo);
        stucon.setTypeface(tfRobo);
        stuckin.setTypeface(tfRobo);
        stuckout.setTypeface(tfRobo);


    }

    public void applyAttendence(View view){
        if(applybutton==0){
            if(routeattendenceselected.equals(getResources().getString(R.string.sj_select_route))){
                Toast.makeText(Student_Attendence_Navigation.this, getResources().getString(R.string.sj_please_select_route),
                        Toast.LENGTH_LONG).show();
            }
            else if(triptypeselected.equals(getResources().getString(R.string.sj_select_trip))){
                Toast.makeText(Student_Attendence_Navigation.this, getResources().getString(R.string.sj_please_select_trip),
                        Toast.LENGTH_LONG).show();
            }
            else if(dateusergiven.equals("empty")){
                Toast.makeText(Student_Attendence_Navigation.this, getResources().getString(R.string.sj_please_select_date),
                        Toast.LENGTH_LONG).show();
            }
            else{
                for(int i=0;i<routenamefromserverattendence.size();i++){
                    if(routeattendenceselected.equals(routenamefromserverattendence.get(i))){
                        routeattendenceIdselected=routeidfromserverattendence.get(i);
                    }
                }
                for(int i=0;i<3;i++){
                    if(triptypeselected.equals(getResources().getString(R.string.sj_pick_up))){
                        triptypeIdselected="1";
                    }
                    else if(triptypeselected.equals(getResources().getString(R.string.sj_drop))){
                        triptypeIdselected="2";
                    }
                }
                Log.e("&&&&&&&&&&&&&&&&",routeattendenceIdselected);
                Log.e("&&&&&&&&&&&&&&&&",triptypeIdselected);
                new getAttendenceFromServer().execute();
            }
        }
    }

    class getAttendenceFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Student_Attendence_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);
            countstudents=0;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"Attendance_api/dailyReportByBus/Route_Id/"+routeattendenceIdselected+"/Date/"+dateusergiven+"/trip_type/"+triptypeIdselected);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Attendance_api/dailyReportByBus/Route_Id/"+routeattendenceIdselected+"/Date/"+dateusergiven+"/trip_type/"+triptypeIdselected,Jsonfunctions.GET);

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
                                    Log.e("+++",obj.getString("student_name"));
                                    studentnamelist.add(obj.getString("student_name"));
                                    Log.e("+++",obj.getString("student_id"));
                                    studentidlist.add(obj.getString("student_id"));
                                    Log.e("+++",obj.getString("parent_name"));
                                    parentnamelist.add(obj.getString("parent_name"));
                                    Log.e("+++",obj.getString("parent_contact"));
                                    parentcontactnolist.add(obj.getString("parent_contact"));
                                    String checkindetails=obj.getString("in_status");
                                    Log.e("+++",obj.getString("in_status"));
                                    String checkoutdetails=obj.getString("out_status");
                                    Log.e("+++",obj.getString("out_status"));
                                    if(checkindetails.equalsIgnoreCase("1")){
                                        checkinlist.add("Yes");
                                    }
                                    else{
                                        checkinlist.add("No");
                                    }
                                    if(checkoutdetails.equalsIgnoreCase("1")){
                                        checkoutlist.add("Yes");
                                    }
                                    else{
                                        checkoutlist.add("No");
                                    }
                                    countstudents=countstudents+1;
                                    applybutton=1;
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
            if(countstudents>0){
                setLayoutVisible();
            }
            else{
                Toast.makeText(Student_Attendence_Navigation.this, getResources().getString(R.string.sj_no_attendance_records_for_this_day),
                        Toast.LENGTH_LONG).show();
                setLayoutInvisible();
            }
            listgenerate();
            progressDialog.dismiss();
        }
    }

    public void listgenerate(){
        ArrayList<SearchResultAttendence> searchResults = GetSearchResults();
        final ListView lv1 = (ListView) findViewById(R.id.attendencelist);
        lv1.setAdapter(new MyCustomBaseAttendenceAdapter(this, searchResults));
    }

    private ArrayList<SearchResultAttendence> GetSearchResults(){
        ArrayList<SearchResultAttendence> results = new ArrayList<SearchResultAttendence>();



        for (int i=0;i<countstudents;i++){
            SearchResultAttendence sr1 = new SearchResultAttendence();
            sr1.setStudentnameresult(studentnamelist.get(i));
            sr1.setStudentidresult(studentidlist.get(i));
            sr1.setParentidresult(parentnamelist.get(i));
            sr1.setParentcontactnoresult(parentcontactnolist.get(i));
            sr1.setCheckinresult(checkinlist.get(i));
            sr1.setCheckoutresult(checkoutlist.get(i));
            results.add(sr1);
        }

        return results;
    }

    class getRoutesFromAttendenceServer extends AsyncTask<Void,Void,Void> {
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
                                    routeidfromserverattendence.add(id);
                                    Log.e("++++++++++++++",id);
                                    String nameroute=obj.getString("route_name");
                                    Log.e("++++++++++++++",nameroute);
                                    routenamefromserverattendence.add(nameroute);
                                   /* String driverid=obj.getString("driver_id");
                                    Log.e("driver Id",driverid);
                                    RouteDriverId.add(driverid);
                                    String busid=obj.getString("bus_id");
                                    Log.e("bus id",busid);
                                    RouteBusId.add(busid);*/

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
            getDropDownAttendence();
        }
    }

    public void getDropDownAttendence(){
        attendeceRouteList=(Spinner)findViewById(R.id.selectroutespinnerattendence);
        attendenceTripType=(Spinner)findViewById(R.id.selecttriptypespinnerattendence);

        final String[] routeattendencefrom=new String[routenamefromserverattendence.size()+1];
        routeattendencefrom[0]=getResources().getString(R.string.sj_select_route);
        for(int i=1;i<=routenamefromserverattendence.size();i++){
            routeattendencefrom[i]=routenamefromserverattendence.get(i-1);
        }
        final String[] triptypeattendence=new String[3];
        triptypeattendence[0]=getResources().getString(R.string.sj_select_trip);
        triptypeattendence[1]=getResources().getString(R.string.sj_pick_up);
        triptypeattendence[2]=getResources().getString(R.string.sj_drop);

        ArrayAdapter<String> adapterrouteattendencefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routeattendencefrom) {
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
        /*ArrayAdapter<String> adapterrouteattendencefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, routeattendencefrom);*/
        adapterrouteattendencefrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attendeceRouteList.setAdapter(adapterrouteattendencefrom);

        ArrayAdapter<String> adaptertriptypeattendencefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, triptypeattendence) {
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
       /* ArrayAdapter<String> adaptertriptypeattendencefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, triptypeattendence);*/
        adaptertriptypeattendencefrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attendenceTripType.setAdapter(adaptertriptypeattendencefrom);

        attendeceRouteList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routedrivernamenavigation=item;
                routeattendenceselected=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        attendenceTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routedrivernamenavigation=item;
                triptypeselected=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Student_Attendence_Navigation.this);

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
                Intent intent = new Intent(Student_Attendence_Navigation.this, Student_Attendence_Navigation.class);
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
                Intent intent = new Intent(Student_Attendence_Navigation.this, Student_Attendence_Navigation.class);
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
        Student_Attendence_Navigation.this.getResources().updateConfiguration(config,
                Student_Attendence_Navigation.this.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Student_Attendence_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student__attendence__navigation, menu);
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
            Intent intent = new Intent(this, Student_Route_Creation_Navigation.class);
            startActivity(intent);
        } else if (id == R.id.attendence) {
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();
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
