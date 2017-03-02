package com.example.valuetechsa.admin_school_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Accident_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;
    DatabaseAdapter dbh;
    Typeface tfRobo,tfAdam;
    ListView lv1;
    Boolean isScrolling;
    ProgressDialog progressDialog2=null;
    private PopupWindow pwindo;
    static EditText txtincidentdate,txtincidentdetails,txtincidentfineamount,txtincidentstatus;
    Spinner buslistspinner,driverlistspinner;
    static String dateusergiven="empty";
    static String dateprintgiven="empty";
    static String sss="empty";
    static Button txtuploadrecordbutton,txtuploaddocumentbutton;
    static TextView txtuploadrecordtxt,txtuploaddocumenttxt;
    int[] a=new int[2];
    int[] checkwhichchoose=new int[2];
    ProgressDialog dialog = null;
    int serverResponseCode = 0;
    CommonClass cc;
    private static final int REQUEST_PATH = 1;
    Jsonfunctions sh;
    String response="",url="";
    String dateintoserver,detailsintoserver,uploadreportsintoserver,selectbusintoserver,selectbusidintoserver,selectdriverintoserver,selectdriveridintoserver,fineamountintoserver,statusintoserver,uploaddocumentintoserver;
    String globaluploadreportname,globaluploaddocumentname,selectedspinnerbusitem,selectedspinnerdriveritem;
    String globalincidentid,editviewornot="0";
    int deleteincidentid;
    int driveridintintoserver,busidintintoserver,fineamountintintoserver,statusintintoserver;
    MyCustomBasedAccidentAdaper myCustomBasedAccidentAdaper;
    ArrayList<String> incidentididfromserver=new ArrayList<String>();
    ArrayList<String> datefromserver=new ArrayList<String>();
    ArrayList<String> detailsfromserver=new ArrayList<String>();
    ArrayList<String> reportuploadsfromserver=new ArrayList<String>();
    ArrayList<String> busidfromserver=new ArrayList<String>();
    ArrayList<String> driveridfromserver=new ArrayList<String>();
    ArrayList<String> fineamountpaidfromserver=new ArrayList<String>();
    ArrayList<String> statusfromserver=new ArrayList<String>();
    ArrayList<String> documentuploadfromserver=new ArrayList<String>();
    ArrayList<String> drivernamefromserver=new ArrayList<String>();
    ArrayList<String> busnamefromserver=new ArrayList<String>();

    ArrayList<String> drivernamelistfromserver=new ArrayList<String>();
    ArrayList<String> busnamelistfromserver=new ArrayList<String>();
    ArrayList<String> busidlistfromserver=new ArrayList<String>();
    ArrayList<String> driveridlistfromserver=new ArrayList<String>();
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
        setContentView(R.layout.activity_accident__navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.accidentheader);
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

        TextView TextViewNewFont = new TextView(Accident_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_accident));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Accident_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Accident_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);

        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
        changeFont();
        new getAccidentListFromServer().execute();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void changeFont(){
        TextView accidentlist=(TextView)findViewById(R.id.accidentlisttextbox);
        TextView accidentdate=(TextView)findViewById(R.id.accidentdatetextbox);
        TextView accidentdrivername=(TextView)findViewById(R.id.accidentdrivernametextbox);
        TextView accidentdetails=(TextView)findViewById(R.id.accidentdetailstextbox);
        TextView accidentamountandstatus=(TextView)findViewById(R.id.accidentfinetextbox);
        TextView accidentoptions=(TextView)findViewById(R.id.accidentoptionstextbox);
        Button addaccident=(Button)findViewById(R.id.addaccident);

        accidentlist.setTypeface(tfRobo);
        accidentdate.setTypeface(tfRobo);
        accidentdrivername.setTypeface(tfRobo);
        accidentdetails.setTypeface(tfRobo);
        accidentamountandstatus.setTypeface(tfRobo);
        accidentoptions.setTypeface(tfRobo);
        addaccident.setTypeface(tfAdam);

    }

    public void listgenerate(){
        ArrayList<SearchResultsAccidents> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.accidentselectedlist);
        myCustomBasedAccidentAdaper=new MyCustomBasedAccidentAdaper(this, searchResults);
        lv1.setAdapter(myCustomBasedAccidentAdaper);
        lv1.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == SCROLL_STATE_IDLE) {
                    isScrolling = false;
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                isScrolling = true;
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {
                Log.e("position-------", "-------------"+pos);

            }
        });
    }

    private ArrayList<SearchResultsAccidents> GetSearchResults(){
        ArrayList<SearchResultsAccidents> results = new ArrayList<SearchResultsAccidents>();
        for (int i=0;i<incidentididfromserver.size();i++){
            SearchResultsAccidents sr1 = new SearchResultsAccidents();
            /*sr1.setDate("2017-02-17");
            sr1.setDriverName("Avinash");
            sr1.setDetailsAccident("Small Accident");
            sr1.setFineandstatus("100Rs-Paid");
            sr1.setPositionsdrop(i);*/
            sr1.setDate(datefromserver.get(i));
            sr1.setDriverName(drivernamefromserver.get(i));
            sr1.setDetailsAccident(detailsfromserver.get(i));
            String status;
            if(statusfromserver.get(i).equalsIgnoreCase("1")){
                status="Paid";
            }
            else {
                status="Unpaid";
            }
            sr1.setFineandstatus(fineamountpaidfromserver.get(i)+"-"+status);
            sr1.setPositionsdrop(i);
            results.add(sr1);
        }
        return results;
    }

    class getAccidentListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Accident_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"Incidents_api/listIncident");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Incidents_api/listIncident",Jsonfunctions.GET);

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
                                    Log.e("Incident id",obj.getString("incident_id"));
                                    String incident_id=obj.getString("incident_id");
                                    incidentididfromserver.add(incident_id);
                                    Log.e("date",obj.getString("date"));
                                    String dateacc=obj.getString("date");
                                    datefromserver.add(dateacc);
                                    Log.e("details",obj.getString("details"));
                                    String details=obj.getString("details");
                                    detailsfromserver.add(details);
                                    Log.e("report_upload",obj.getString("report_upload"));
                                    String report_upload=obj.getString("report_upload");
                                    reportuploadsfromserver.add(report_upload);
                                    Log.e("bus_id",obj.getString("bus_id"));
                                    String bus_id=obj.getString("bus_id");
                                    busidfromserver.add(bus_id);
                                    Log.e("driver_id",obj.getString("driver_id"));
                                    String driver_id=obj.getString("driver_id");
                                    driveridfromserver.add(driver_id);
                                    Log.e("fine_amount",obj.getString("fine_amount"));
                                    String fine_amount=obj.getString("fine_amount");
                                    fineamountpaidfromserver.add(fine_amount);
                                    Log.e("status",obj.getString("status"));
                                    String status=obj.getString("status");
                                    statusfromserver.add(status);
                                    Log.e("document_upload",obj.getString("document_upload"));
                                    String document_upload=obj.getString("document_upload");
                                    documentuploadfromserver.add(document_upload);
                                    Log.e("driver_name",obj.getString("driver_name"));
                                    String driver_name=obj.getString("driver_name");
                                    drivernamefromserver.add(driver_name);
                                    Log.e("bus_name",obj.getString("bus_name"));
                                    String bus_name=obj.getString("bus_name");
                                    busnamefromserver.add(bus_name);
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
            //progressDialog2.dismiss();
            new getDriverListFromServer().execute();
        }
    }

    class getDriverListFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            //progressDialog2 = ProgressDialog.show(Accident_Navigation.this, "Please wait.",
                   // "Fetching Information!", true);

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
                                    Log.e("driver name",obj.getString("name"));
                                    String drivername=obj.getString("name");
                                    drivernamelistfromserver.add(drivername);
                                    Log.e("driver id",obj.getString("driver_id"));
                                    String driverId=obj.getString("driver_id");
                                    driveridlistfromserver.add(driverId);
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
            //progressDialog2.dismiss();
            new getBusListFromServer().execute();
        }
    }

    class getBusListFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            //progressDialog2 = ProgressDialog.show(Accident_Navigation.this, "Please wait.",
                    //"Fetching Information!", true);

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
                                    Log.e("bus name",obj.getString("name"));
                                    String busname=obj.getString("name");
                                    busnamelistfromserver.add(busname);
                                    Log.e("bus id",obj.getString("bus_Id"));
                                    String busId=obj.getString("bus_Id");
                                    busidlistfromserver.add(busId);
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
            listgenerate();
            progressDialog2.dismiss();
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
            if(month<9){
                month=month+1;
                sss=""+ (month);
                sss="0"+sss;
            }
            else {
                month=month+1;
                sss=""+ (month);
            }
            dateprintgiven=year + "-" + (sss) + "-" + day;
            dateusergiven = year+"-"+(month+1)+"-"+day;
            txtincidentdate.setText(dateprintgiven);

        }
    }

    public void getpopupaccident(View view){
        Log.e("+++","yes");
        initiatePopupWindowaccident();
    }

    private void initiatePopupWindowaccident() {
        try {

            dateintoserver="null";
            detailsintoserver="null";
            uploadreportsintoserver="null";
            selectbusintoserver="null";
            selectbusidintoserver="null";
            selectdriverintoserver="null";
            selectdriveridintoserver="null";
            fineamountintoserver="null";
            statusintoserver="null";
            uploaddocumentintoserver="null";
            globaluploaddocumentname="null";
            globaluploadreportname="null";
// We need to get the instance of the LayoutInflater
           /* LayoutInflater inflater1 = (LayoutInflater) Bus_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/
            LayoutInflater inflater = (LayoutInflater) Accident_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_accidents,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            txtincidentdetails=(EditText)layout.findViewById(R.id.incidentdetailsedit);
            txtuploadrecordtxt=(TextView)layout.findViewById(R.id.uploadnamereporttext);
            txtuploaddocumenttxt=(TextView)layout.findViewById(R.id.uploadnamedocumenttext);
            txtincidentfineamount=(EditText)layout.findViewById(R.id.fineamountedit);
            txtincidentstatus=(EditText)layout.findViewById(R.id.finestatusedit);

            txtincidentdate=(EditText)layout.findViewById(R.id.incidentdateedit);
            txtincidentdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //iii=0;
                    showTruitonDatePickerDialog(v);
                }
            });

            buslistspinner=(Spinner) layout.findViewById(R.id.selectbuslistspinner);
            String[] busitems=new String[busnamelistfromserver.size()+1];
            busitems[0]=getResources().getString(R.string.sj_select_bus);
            for(int i=1;i<=busnamelistfromserver.size();i++){
                busitems[i]=busnamelistfromserver.get(i-1);
            }
            ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(Accident_Navigation.this, android.R.layout.simple_spinner_item, busitems) {
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
            //ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, busitems);
            adapterbus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            buslistspinner.setAdapter(adapterbus);
            buslistspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //routebusnonavigation=item;
                    //selectedspinneritemintoserver=item;
                    selectedspinnerbusitem=item;
                    for(int i=0;i<busnamelistfromserver.size();i++){
                        if(busnamelistfromserver.get(i).equalsIgnoreCase(item)){
                            selectbusidintoserver=busidlistfromserver.get(i);
                        }
                    }
                    Log.e("spinner selected",item);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            driverlistspinner=(Spinner) layout.findViewById(R.id.selectdriverlistspinner);
            String[] driveritems=new String[drivernamelistfromserver.size()+1];
            driveritems[0]=getResources().getString(R.string.sj_select_driver);
            for(int i=1;i<=drivernamelistfromserver.size();i++){
                driveritems[i]=drivernamelistfromserver.get(i-1);
            }
            ArrayAdapter<String> adapterdriver = new ArrayAdapter<String>(Accident_Navigation.this, android.R.layout.simple_spinner_item, driveritems) {
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
            //ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, busitems);
            adapterdriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            driverlistspinner.setAdapter(adapterdriver);
            driverlistspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //routebusnonavigation=item;
                    //selectedspinneritemintoserver=item;
                    selectedspinnerdriveritem=item;
                    for(int i=0;i<drivernamelistfromserver.size();i++){
                        if(drivernamelistfromserver.get(i).equalsIgnoreCase(item)){
                            selectdriveridintoserver=driveridlistfromserver.get(i);
                        }
                    }
                    Log.e("spinner selected",item);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            txtuploadrecordbutton=(Button)layout.findViewById(R.id.btn_uploadreport);
            txtuploadrecordbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=1;
                    a[1]=0;
                    uploadfirst();
                }
            });
            txtuploaddocumentbutton=(Button)layout.findViewById(R.id.btn_uploaddocument);
            txtuploaddocumentbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=0;
                    a[1]=1;
                    uploadfirst();
                }
            });
            Button btnAddDrivers=(Button)layout.findViewById(R.id.btn_add_popup_accident);
            btnAddDrivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAccidentMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_accident);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initiateeditPopupWindowAccident(int rownumber){
        try {

            dateintoserver="null";
            detailsintoserver="null";
            uploadreportsintoserver="null";
            selectbusintoserver="null";
            selectbusidintoserver="null";
            selectdriverintoserver="null";
            selectdriveridintoserver="null";
            fineamountintoserver="null";
            statusintoserver="null";
            uploaddocumentintoserver="null";
            globaluploaddocumentname="null";
            globaluploadreportname="null";
// We need to get the instance of the LayoutInflater
           /* LayoutInflater inflater1 = (LayoutInflater) Bus_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/
            LayoutInflater inflater = (LayoutInflater) Accident_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_accidents,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            globalincidentid=incidentididfromserver.get(rownumber);
            editviewornot="1";

            txtincidentdetails=(EditText)layout.findViewById(R.id.incidentdetailsedit);
            if(!detailsfromserver.get(rownumber).equalsIgnoreCase("null")){
                txtincidentdetails.setText(detailsfromserver.get(rownumber));
            }
            txtuploadrecordtxt=(TextView)layout.findViewById(R.id.uploadnamereporttext);
            if(!reportuploadsfromserver.get(rownumber).equalsIgnoreCase("null")){
                Log.e("edit report",""+reportuploadsfromserver.get(rownumber));
                txtuploadrecordtxt.setText(reportuploadsfromserver.get(rownumber));
                globaluploadreportname=reportuploadsfromserver.get(rownumber);
            }
            txtuploaddocumenttxt=(TextView)layout.findViewById(R.id.uploadnamedocumenttext);
            if(!documentuploadfromserver.get(rownumber).equalsIgnoreCase("null")){
                Log.e("edit report",""+documentuploadfromserver.get(rownumber));
                txtuploaddocumenttxt.setText(documentuploadfromserver.get(rownumber));
                globaluploaddocumentname=documentuploadfromserver.get(rownumber);
            }
            txtincidentfineamount=(EditText)layout.findViewById(R.id.fineamountedit);
            if(!fineamountpaidfromserver.get(rownumber).equalsIgnoreCase("null")){
                txtincidentfineamount.setText(fineamountpaidfromserver.get(rownumber));
            }
            txtincidentstatus=(EditText)layout.findViewById(R.id.finestatusedit);
            if(!statusfromserver.get(rownumber).equalsIgnoreCase("null")){
                if(statusfromserver.get(rownumber).equalsIgnoreCase("1")){
                    txtincidentstatus.setText("Paid");
                }
                else{
                    txtincidentstatus.setText("Unpaid");
                }

            }

            txtincidentdate=(EditText)layout.findViewById(R.id.incidentdateedit);
            if(!datefromserver.get(rownumber).equalsIgnoreCase("null")){
                txtincidentdate.setText(datefromserver.get(rownumber));
            }
            txtincidentdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //iii=0;
                    showTruitonDatePickerDialog(v);
                }
            });

            buslistspinner=(Spinner) layout.findViewById(R.id.selectbuslistspinner);
            String[] busitems=new String[busnamelistfromserver.size()+1];
            busitems[0]=getResources().getString(R.string.sj_select_bus);
            for(int i=1;i<=busnamelistfromserver.size();i++){
                busitems[i]=busnamelistfromserver.get(i-1);
            }
            ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(Accident_Navigation.this, android.R.layout.simple_spinner_item, busitems) {
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

            //ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, busitems);
            adapterbus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            buslistspinner.setAdapter(adapterbus);
            buslistspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //routebusnonavigation=item;
                    //selectedspinneritemintoserver=item;
                    selectedspinnerbusitem=item;
                    for(int i=0;i<busnamelistfromserver.size();i++){
                        if(busnamelistfromserver.get(i).equalsIgnoreCase(item)){
                            selectbusidintoserver=busidlistfromserver.get(i);
                        }
                    }
                    Log.e("spinner selected",item);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            if(busnamefromserver.get(rownumber).equalsIgnoreCase("null")){

            }
            else {
                int selectedrow=0;
                for(int i=0;i<busnamelistfromserver.size();i++){
                    if(busnamefromserver.get(rownumber).equalsIgnoreCase(busnamelistfromserver.get(i))){
                        selectedrow=i+1;
                    }
                }
                buslistspinner.setSelection(selectedrow);

            }

            driverlistspinner=(Spinner) layout.findViewById(R.id.selectdriverlistspinner);
            String[] driveritems=new String[drivernamelistfromserver.size()+1];
            driveritems[0]=getResources().getString(R.string.sj_select_driver);
            for(int i=1;i<=drivernamelistfromserver.size();i++){
                driveritems[i]=drivernamelistfromserver.get(i-1);
            }
            ArrayAdapter<String> adapterdriver = new ArrayAdapter<String>(Accident_Navigation.this, android.R.layout.simple_spinner_item, driveritems) {
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
            //ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, busitems);
            adapterdriver.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            driverlistspinner.setAdapter(adapterdriver);
            driverlistspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //routebusnonavigation=item;
                    //selectedspinneritemintoserver=item;
                    selectedspinnerdriveritem=item;
                    for(int i=0;i<drivernamelistfromserver.size();i++){
                        if(drivernamelistfromserver.get(i).equalsIgnoreCase(item)){
                            selectdriveridintoserver=driveridlistfromserver.get(i);
                        }
                    }
                    Log.e("spinner selected",item);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if(drivernamefromserver.get(rownumber).equalsIgnoreCase("null")){

            }
            else{
                int selecteddriverrow=0;
                for(int i=0;i<drivernamelistfromserver.size();i++){
                    if(drivernamefromserver.get(rownumber).equalsIgnoreCase(drivernamelistfromserver.get(i))){
                        selecteddriverrow=i+1;
                    }
                }
                driverlistspinner.setSelection(selecteddriverrow);
            }

            txtuploadrecordbutton=(Button)layout.findViewById(R.id.btn_uploadreport);
            txtuploadrecordbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=1;
                    a[1]=0;
                    uploadfirst();
                }
            });
            txtuploaddocumentbutton=(Button)layout.findViewById(R.id.btn_uploaddocument);
            txtuploaddocumentbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=0;
                    a[1]=1;
                    uploadfirst();
                }
            });
            Button btnAddDrivers=(Button)layout.findViewById(R.id.btn_add_popup_accident);
            btnAddDrivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAccidentMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_accident);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAccidentMethod(){
        dateintoserver=txtincidentdate.getText().toString();
        detailsintoserver=txtincidentdetails.getText().toString();
        /*uploadreportsintoserver=globaluploadreportname;
        uploaddocumentintoserver=globaluploaddocumentname;*/
        selectbusintoserver=selectedspinnerbusitem;
        selectdriverintoserver=selectedspinnerdriveritem;
        fineamountintoserver=txtincidentfineamount.getText().toString();
        statusintoserver=txtincidentstatus.getText().toString();
        if(dateintoserver.isEmpty()){
            Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_please_enter_date),
                    Toast.LENGTH_SHORT).show();
        }
        else if(detailsintoserver.isEmpty()){
            Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_please_enter_the_details),
                    Toast.LENGTH_SHORT).show();
        }
        /*else if(uploadreportsintoserver.equalsIgnoreCase("null")){
            Toast.makeText(Accident_Navigation.this, "Please Upload Report",
                    Toast.LENGTH_SHORT).show();
        }
        else if(uploaddocumentintoserver.equalsIgnoreCase("null")){
            Toast.makeText(Accident_Navigation.this, "Please Upload Document",
                    Toast.LENGTH_SHORT).show();
        }*/
        else if(selectbusintoserver.equalsIgnoreCase(getResources().getString(R.string.sj_select_bus))){
            Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_please_select_bus),
                    Toast.LENGTH_SHORT).show();
        }
        else if(selectdriverintoserver.equalsIgnoreCase(getResources().getString(R.string.sj_select_driver))){
            Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_please_select_driver),
                    Toast.LENGTH_SHORT).show();
        }
        else if(fineamountintoserver.isEmpty()){
            Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_please_enter_the_fine_amount),
                    Toast.LENGTH_SHORT).show();
        }
        else if(statusintoserver.isEmpty()){
            Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_please_enter_the_status),
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Log.e("date",""+dateintoserver);
            Log.e("details",""+detailsintoserver);
            Log.e("report",""+globaluploadreportname);
            Log.e("document",""+globaluploaddocumentname);
            Log.e("bus",""+selectbusintoserver);
            Log.e("driver",""+selectdriverintoserver);
            Log.e("bus id",""+selectbusidintoserver);
            /*driveridintintoserver=Integer.parseInt(selectdriveridintoserver);
            busidintintoserver= Integer.parseInt(selectbusidintoserver);*/
            Log.e("driver id",""+selectdriveridintoserver);
            Log.e("fine paid",""+fineamountintoserver);
            /*fineamountintintoserver=Integer.parseInt(fineamountintoserver);*/
            Log.e("status",""+statusintoserver);
            if(!(statusintoserver.equalsIgnoreCase("paid")||statusintoserver.equalsIgnoreCase("Unpaid"))){
                Toast.makeText(Accident_Navigation.this, "The Status Must Be Either Paid Or Unpaid!",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                if(statusintoserver.equalsIgnoreCase("paid")){
                    statusintintoserver=1;
                }
                else {
                    statusintintoserver=0;
                }

                if(editviewornot.equalsIgnoreCase("1")){
                    new addeditaccidentdetailstoserver().execute();
                }
                else {
                    new addaccidentdetailstoserver().execute();
                }
            }
        }
    }

    class getIncidentDeleteServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Accident_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    "", true);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"Incidents_api/deleteIncident/incident_id/"+deleteincidentid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Incidents_api/deleteIncident/incident_id/"+deleteincidentid,Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                JSONArray jsonArray = Jobj.getJSONArray("result_arr");


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
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            progressDialog2.dismiss();
        }
    }

    class addaccidentdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Accident_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();
                Log.e("@@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                /*Log.e("report",""+uploadreportsintoserver);
                Log.e("document",""+uploaddocumentintoserver);
                uploadreportsintoserver="null";
                uploaddocumentintoserver="null";*/
                /*url= Config.ip+"Incidents_api/addIncident/date/"+ URLEncoder.encode(dateintoserver,"UTF-8")+"/driver_id/"+driveridintintoserver+
                        "/driver_name/"+URLEncoder.encode(selectdriverintoserver,"UTF-8")+"/bus_id/"+busidintintoserver+"/bus_name/"+URLEncoder.encode(selectbusintoserver,"UTF-8")+
                        "/details/"+URLEncoder.encode(detailsintoserver,"UTF-8")+"/report/"+URLEncoder.encode(uploadreportsintoserver,"UTF-8")+"/fine_amt/"+fineamountintintoserver+"/status/"+statusintintoserver+
                        "/document/"+URLEncoder.encode(uploaddocumentintoserver,"UTF-8");*/

                url= Config.ip+"Incidents_api/addIncident/date/"+ URLEncoder.encode(dateintoserver,"UTF-8")+"/driver_id/"+URLEncoder.encode(selectdriveridintoserver,"UTF-8")+"/driver_name/"+URLEncoder.encode(selectdriverintoserver,"UTF-8")+"/bus_id/"+URLEncoder.encode(selectbusidintoserver,"UTF-8")+"/bus_name/"+URLEncoder.encode(selectbusintoserver,"UTF-8")+"/details/"+URLEncoder.encode(detailsintoserver,"UTF-8")+"/report/"+ URLEncoder.encode(globaluploadreportname,"UTF-8")+"/fine_amt/"+URLEncoder.encode(fineamountintoserver,"UTF-8")+"/status/"+statusintintoserver+"/document/"+URLEncoder.encode(globaluploaddocumentname,"UTF-8");

                /*url= Config.ip+"GetRouteDetails_api/createRoute" +
                        "/route_name/"+ URLEncoder.encode(strRoute_name,"UTF-8")+"/trip_name/"+URLEncoder.encode(strRoute_Type,"UTF-8")+
                        "/start_time/"+URLEncoder.encode(strRoute_Starttime,"UTF-8")+"/end_time/"+URLEncoder.encode(strRoute_Endtime,"UTF-8");*/
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
                                Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_accident_added_succesfully), Toast.LENGTH_SHORT).show();
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
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    class addeditaccidentdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Accident_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();
                Log.e("@@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                /*Log.e("report",""+uploadreportsintoserver);
                Log.e("document",""+uploaddocumentintoserver);
                uploadreportsintoserver="null";
                uploaddocumentintoserver="null";*/
                /*url= Config.ip+"Incidents_api/addIncident/date/"+ URLEncoder.encode(dateintoserver,"UTF-8")+"/driver_id/"+driveridintintoserver+
                        "/driver_name/"+URLEncoder.encode(selectdriverintoserver,"UTF-8")+"/bus_id/"+busidintintoserver+"/bus_name/"+URLEncoder.encode(selectbusintoserver,"UTF-8")+
                        "/details/"+URLEncoder.encode(detailsintoserver,"UTF-8")+"/report/"+URLEncoder.encode(uploadreportsintoserver,"UTF-8")+"/fine_amt/"+fineamountintintoserver+"/status/"+statusintintoserver+
                        "/document/"+URLEncoder.encode(uploaddocumentintoserver,"UTF-8");*/

                url= Config.ip+"Incidents_api/editIncident/incident_id/"+ URLEncoder.encode(globalincidentid,"UTF-8")+"/date/"+ URLEncoder.encode(dateintoserver,"UTF-8")+"/driver_id/"+URLEncoder.encode(selectdriveridintoserver,"UTF-8")+"/driver_name/"+URLEncoder.encode(selectdriverintoserver,"UTF-8")+"/bus_id/"+URLEncoder.encode(selectbusidintoserver,"UTF-8")+"/bus_name/"+URLEncoder.encode(selectbusintoserver,"UTF-8")+"/details/"+URLEncoder.encode(detailsintoserver,"UTF-8")+"/report/"+ URLEncoder.encode(globaluploadreportname,"UTF-8")+"/fine_amt/"+URLEncoder.encode(fineamountintoserver,"UTF-8")+"/status/"+statusintintoserver+"/document/"+URLEncoder.encode(globaluploaddocumentname,"UTF-8");

                /*url= Config.ip+"GetRouteDetails_api/createRoute" +
                        "/route_name/"+ URLEncoder.encode(strRoute_name,"UTF-8")+"/trip_name/"+URLEncoder.encode(strRoute_Type,"UTF-8")+
                        "/start_time/"+URLEncoder.encode(strRoute_Starttime,"UTF-8")+"/end_time/"+URLEncoder.encode(strRoute_Endtime,"UTF-8");*/
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
                                Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_updated_succesfully), Toast.LENGTH_SHORT).show();
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
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    public void uploadfirst(){
        Intent intent1 = new Intent(this, FileChooser.class);
        startActivityForResult(intent1,REQUEST_PATH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {
                if(a[0]==1){
                    Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    Log.e("+++",data.getStringExtra("GetPath")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    String curFileName = data.getStringExtra("GetFileName");
                    String curFileName1 = data.getStringExtra("GetPath");
                    txtuploadrecordtxt.setText(curFileName);
                    String iqamavaluepath=curFileName1+"/"+curFileName;
                    a[0]=0;
                    a[1]=0;
                    checkwhichchoose[0]=1;
                    checkwhichchoose[1]=0;
                    uploadIqamaTheard(iqamavaluepath);
                }
                else if(a[1]==1){
                    Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    Log.e("+++",data.getStringExtra("GetPath")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    String curFileName = data.getStringExtra("GetFileName");
                    String curFileName1 = data.getStringExtra("GetPath");
                    txtuploaddocumenttxt.setText(curFileName);
                    String licensename=curFileName1+"/"+curFileName;
                    a[0]=0;
                    a[1]=0;
                    checkwhichchoose[0]=0;
                    checkwhichchoose[1]=1;
                    uploadIqamaTheard(licensename);

                }
            }
        }
    }

    void uploadIqamaTheard(final String srcPath)
    {
        dialog = ProgressDialog.show(Accident_Navigation.this, "", getResources().getString(R.string.sj_uploading_file), true);

        new Thread(new Runnable() {
            public void run() {
                uploadIqamaFile(srcPath);
            }
        }).start();
    }

    public int uploadIqamaFile(String sourceFileUri)
    {
        String fileName = sourceFileUri.replaceAll(" ", "_");

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        String urlServer = Config.ip+"Upload/uploadsAccidentReport";
        // Config.ip+"Upload/uploadsAccidentReport";

        Log.e("+++",urlServer);
        Log.e("+++",sourceFileUri);
        Log.e("+++",fileName);
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            dialog.dismiss();
            return 0;
        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(urlServer);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("image", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename="+ fileName + "" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    Log.e("+++","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11");
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                Log.e("+++","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!221");
                serverResponseCode = conn.getResponseCode();
                Log.e("+++","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!222"+String.valueOf(conn.getResponseCode()));
                String serverResponseMessage = conn.getResponseMessage();
                Log.e("+++","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!223");
                cc=new CommonClass(Accident_Navigation.this);
                String result=cc.readInputStreamToString(conn);
                Log.e("+++","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!224");
                if (result != null)
                {
                    try
                    {
                        JSONObject Jobj = new JSONObject(result);
                        Log.e("+++","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!33");
                        if(Jobj.getString("responsecode").equals("1"))
                        {
                            JSONObject jsonObj = Jobj.getJSONObject("result_arr");

                            JSONObject ResObj = jsonObj.getJSONObject("upload_data");

                            Log.e("file_name","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+ResObj.getString("file_name"));
                            if(checkwhichchoose[0]==1){
                                //licensebusintoserver=ResObj.getString("file_name");
                                Log.e("into",""+ResObj.getString("file_name"));
                                globaluploadreportname=ResObj.getString("file_name");
                            }
                            else if(checkwhichchoose[1]==1){
                                //mvpiintoserver=ResObj.getString("file_name");
                                Log.e("into",""+ResObj.getString("file_name"));
                                globaluploaddocumentname=ResObj.getString("file_name");
                            }
                            //ImagePath=Config.Uploadsip+"Image/"+ResObj.getString("file_name");
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


                Log.e("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(Accident_Navigation.this, getResources().getString(R.string.sj_upload_complete), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
            }
            catch(MalformedURLException mex)
            {
                dialog.dismiss();
                mex.printStackTrace();
                Log.e("Upload file to server", "error: " + mex.getMessage(), mex);
            }
            catch(Exception ex)
            {
                dialog.dismiss();
                ex.printStackTrace();

                Log.e("Exception : " + ex.getMessage(), ex+"");
            }
            dialog.dismiss();
            return serverResponseCode;
        } // End else bloc
    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Accident_Navigation.this);

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
                Intent intent = new Intent(Accident_Navigation.this, Accident_Navigation.class);
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
                Intent intent = new Intent(Accident_Navigation.this, Accident_Navigation.class);
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
        Accident_Navigation.this.getResources().updateConfiguration(config,
                Accident_Navigation.this.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accident__navigation, menu);
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

    public class MyCustomBasedAccidentAdaper extends BaseAdapter {
        private ArrayList<SearchResultsAccidents> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedAccidentAdaper(Context context, ArrayList<SearchResultsAccidents> results) {
            searchArrayList = results;
            tfRobo = Typeface.createFromAsset(context.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return searchArrayList.size();
        }

        public Object getItem(int position) {
            return searchArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                final int rowposition=position;
                convertView = mInflater.inflate(R.layout.custom_row_view_accidents, null);
                holder = new ViewHolder();
                holder.txtaccidentdate = (TextView) convertView.findViewById(R.id.accidentdate);
                holder.txtaccidentdate.setTypeface(tfRobo);
                holder.txtaccidentdrivername = (TextView) convertView.findViewById(R.id.accidentdrivername);
                holder.txtaccidentdrivername.setTypeface(tfRobo);
                holder.txtaccidentdetails = (TextView) convertView.findViewById(R.id.accidentdetails);
                holder.txtaccidentdetails.setTypeface(tfRobo);
                holder.txtaccidentfineandstatus = (TextView) convertView.findViewById(R.id.accidentfine);
                holder.txtaccidentfineandstatus.setTypeface(tfRobo);
                holder.txtaccidentoptions=(Spinner)convertView.findViewById(R.id.accidentoption);

                final String[] triptypeattendence=new String[3];
                triptypeattendence[0]="Action";
                triptypeattendence[1]="Edit";
                triptypeattendence[2]="Delete";

                ArrayAdapter<String> adapterdriverdropown = new ArrayAdapter<String>(convertView.getContext(), android.R.layout.simple_spinner_item, triptypeattendence) {
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View v = super.getView(position, convertView, parent);
                        Typeface externalFont=Typeface.createFromAsset(getContext().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                        ((TextView) v).setTypeface(externalFont);
                        ((TextView) v).setTextSize(20);
                        ((TextView) v).setMinHeight(70);
                        return v;
                    }
                    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                        View v =super.getDropDownView(position, convertView, parent);
                        Typeface externalFont=Typeface.createFromAsset(getContext().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                        ((TextView) v).setTypeface(externalFont);
                        ((TextView) v).setTextSize(20);
                        ((TextView) v).setMinHeight(70);
                        return v;
                    }
                };

                adapterdriverdropown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                holder.txtaccidentoptions.setAdapter(adapterdriverdropown);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtaccidentoptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    String item = parent.getItemAtPosition(position1).toString();
                    //routedrivernamenavigation=item;
                    //routeattendenceselected=item;
                    Log.e("spinner selected",item);
                    Log.e("Row",""+searchArrayList.get(position).getPositionsdrop());
                    if(item.equalsIgnoreCase("Action")){

                    }
                    else if(item.equalsIgnoreCase("Edit")){
                        initiateeditPopupWindowAccident(position);
                    }
                    if(item.equalsIgnoreCase("Delete")){
                        deleteincidentid=Integer.parseInt(incidentididfromserver.get(position));
                        new getIncidentDeleteServer().execute();
                        //deletebusid=Integer.parseInt(busidfromserver.get(position));
                        //new getBusDeleteServer().execute();
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtaccidentdate.setText(searchArrayList.get(position).getDate());
            holder.txtaccidentdrivername.setText(searchArrayList.get(position).getDriverName());
            holder.txtaccidentdetails.setText(searchArrayList.get(position).getDetailsAccident());
            holder.txtaccidentfineandstatus.setText(searchArrayList.get(position).getFineandstatus());
            return convertView;
        }

        class ViewHolder {
            TextView txtaccidentdate;
            TextView txtaccidentdrivername;
            TextView txtaccidentdetails;
            TextView txtaccidentfineandstatus;
            Spinner txtaccidentoptions;
        }
    }
}
