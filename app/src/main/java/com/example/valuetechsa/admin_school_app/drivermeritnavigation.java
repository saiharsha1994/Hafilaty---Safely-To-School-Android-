package com.example.valuetechsa.admin_school_app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.DB.DatabaseAdapter;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class drivermeritnavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String name =" ",driverImage="",route="",mobileno="",speedlimit="",rashdriving="",timemaintaince="",URL="";
    public static final String[] STARNAME = new String[]{
            "fivestar","fourstar","threestar","twostar"
    };
    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    private EditText etPickADate;
    private Calendar mCalendar;
    static int check=0;
    double speedlimitintvalue,rashdrivingintvalue,timemaintainceintvalue;
    public static final int imageID=0;
    //public static final int trying=Integer.parseInt("halfstar");
    ArrayList<String> namedriver=new ArrayList<String>();
    ArrayList<String> driverImagepng=new ArrayList<String>();
    ArrayList<String> routedriver=new ArrayList<String>();
    ArrayList<String> mobilenodriver=new ArrayList<String>();
    ArrayList<String> speedlimitdriver=new ArrayList<String>();
    ArrayList<String> rashdrivingdriver=new ArrayList<String>();
    ArrayList<String> timemaintaincedriver=new ArrayList<String>();
    String []dodriver=new String[1000];
    String []dodriverimage=new String[1000];
    public static String DriverUploads=Config.image+"uploads/driver_image/";
    ImageView imageView,imageViewtester;
    TextView drivername,driverphoneno;
    GridView grid;
    static int selectedmonth=0,selecteddate=0,selectedyear=0;
    String selectedmonthstring;
    public static int count,changecount=0;
    private static Toast myToast;
    Typeface tfRobo,tfAdam;
    ProgressDialog progressDialog;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    public static final String[] SPEEDLIMIT=new String[100];
    public static final String[] RASHDRIVING=new String[100];
    public static final String[] TIMEMAINTAINANCE= new String[100];
    public static final String[] DRIVERNAMEARRAY = new String[100];
    public static final String[] IMAGES = new String[100];
    ProgressDialog progressDialog2;
    PopupWindow pwindo;
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
        setContentView(R.layout.activity_drivermeritnavigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //copy paste
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
        //
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.drivermeritheader);
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

        myToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);

        TextView TextViewNewFont = new TextView(drivermeritnavigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_driver_rating));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(drivermeritnavigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(drivermeritnavigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);

        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);

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

        fontcanchange();
       /* action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp; Driver Rating</big></font>"));*/
        drivername= (TextView) findViewById(R.id.drivernametext);
        EditText DateEdit =  (EditText) findViewById(R.id.selectroutespinner);
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTruitonDatePickerDialog(v);
            }
        });


    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
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
            EditText date = (EditText)getActivity().findViewById(R.id.selectroutespinner);
            selectedmonth= month+1;
            selecteddate=day;
            selectedyear=year;
            date.setText(day + "/" + (month + 1) + "/" + year);


        }
    }


    public void fontcanchange(){
        TextView sel=(TextView)findViewById(R.id.selectcriteria);
        TextView sele=(TextView)findViewById(R.id.selectroute);
        EditText month=(EditText)findViewById(R.id.selectroutespinner);
        Button app=(Button)findViewById(R.id.applyattenden);
        Button preapp=(Button)findViewById(R.id.previousmonth);
        Button apppre=(Button)findViewById(R.id.nextmonth);
        sel.setTypeface(tfRobo);
        sele.setTypeface(tfRobo);
        month.setTypeface(tfRobo);
        app.setTypeface(tfAdam);
        preapp.setTypeface(tfAdam);
        apppre.setTypeface(tfAdam);
    }


    public void apply(View view){
        EditText checkdate= (EditText)findViewById(R.id.selectroutespinner);
        String checkdatestring=checkdate.getText().toString();
        if (checkdatestring.isEmpty()){
         Log.e("+++++++++++","empty++++++++++++++++++++++++++++++++");
            myToast.setText(getResources().getString(R.string.sj_please_select_date));
            myToast.show();
            /*Toast.makeText(drivermeritnavigation.this, "Please Select A Date",
                    Toast.LENGTH_SHORT).show();*/
        }
        else {
            Log.e("+++++++++++","full++++++++++++++++++++++++++++++++");
            Log.e("selected month",selectedmonth+"");
            if(selectedmonth==1){
                selectedmonthstring="January";
            }
            else if(selectedmonth==2){
                selectedmonthstring="February";
            }
            else if(selectedmonth==3){
                selectedmonthstring="March";
            }
            else if(selectedmonth==4){
                selectedmonthstring="April";
            }
            else if(selectedmonth==5){
                selectedmonthstring="May";
            }
            else if(selectedmonth==6){
                selectedmonthstring="June";
            }
            else if(selectedmonth==7){
                selectedmonthstring="July";
            }
            else if(selectedmonth==8){
                selectedmonthstring="August";
            }
            else if(selectedmonth==9){
                selectedmonthstring="September";
            }
            else if(selectedmonth==10){
                selectedmonthstring="October";
            }
            else if(selectedmonth==11){
                selectedmonthstring="November";
            }
            else if(selectedmonth==12){
                selectedmonthstring="December";
            }
            new getDriverMerit().execute();
            grid = (GridView)findViewById(R.id.grid);

        }

    }

    public void previousmonthmethod(View view){
        if(selectedmonth!=0){
            selectedmonth=selectedmonth-1;
            if(selectedmonth==0) {
                selectedmonth = 12;
                selectedyear=selectedyear-1;
            }
            EditText date = (EditText)findViewById(R.id.selectroutespinner);
            date.setText(selecteddate + "/" + (selectedmonth) + "/" + selectedyear);
            if(selectedmonth==1){
                selectedmonthstring="January";
            }
            else if(selectedmonth==2){
                selectedmonthstring="February";
            }
            else if(selectedmonth==3){
                selectedmonthstring="March";
            }
            else if(selectedmonth==4){
                selectedmonthstring="April";
            }
            else if(selectedmonth==5){
                selectedmonthstring="May";
            }
            else if(selectedmonth==6){
                selectedmonthstring="June";
            }
            else if(selectedmonth==7){
                selectedmonthstring="July";
            }
            else if(selectedmonth==8){
                selectedmonthstring="August";
            }
            else if(selectedmonth==9){
                selectedmonthstring="September";
            }
            else if(selectedmonth==10){
                selectedmonthstring="October";
            }
            else if(selectedmonth==11){
                selectedmonthstring="November";
            }
            else if(selectedmonth==12){
                selectedmonthstring="December";
            }
            new getDriverMerit().execute();
            grid = (GridView)findViewById(R.id.grid);
        }
        else{
            myToast.setText(getResources().getString(R.string.sj_please_select_date));
            myToast.show();
            /*Toast.makeText(drivermeritnavigation.this, "Please Select A Date",
                    Toast.LENGTH_SHORT).show();*/
        }

    }

    public void nextmonthmethod(View view){
        if(selectedmonth!=0){
            selectedmonth=selectedmonth+1;
            if(selectedmonth==13) {
                selectedmonth = 1;
                selectedyear=selectedyear+1;
            }
            EditText date = (EditText)findViewById(R.id.selectroutespinner);
            date.setText(selecteddate + "/" + (selectedmonth) + "/" + selectedyear);
            if(selectedmonth==1){
                selectedmonthstring="January";
            }
            else if(selectedmonth==2){
                selectedmonthstring="February";
            }
            else if(selectedmonth==3){
                selectedmonthstring="March";
            }
            else if(selectedmonth==4){
                selectedmonthstring="April";
            }
            else if(selectedmonth==5){
                selectedmonthstring="May";
            }
            else if(selectedmonth==6){
                selectedmonthstring="June";
            }
            else if(selectedmonth==7){
                selectedmonthstring="July";
            }
            else if(selectedmonth==8){
                selectedmonthstring="August";
            }
            else if(selectedmonth==9){
                selectedmonthstring="September";
            }
            else if(selectedmonth==10){
                selectedmonthstring="October";
            }
            else if(selectedmonth==11){
                selectedmonthstring="November";
            }
            else if(selectedmonth==12){
                selectedmonthstring="December";
            }
            new getDriverMerit().execute();
            grid = (GridView)findViewById(R.id.grid);
        }

        else{
            myToast.setText(getResources().getString(R.string.sj_please_select_date));
            myToast.show();
            /*Toast.makeText(drivermeritnavigation.this, "Please Select A Date",
                    Toast.LENGTH_SHORT).show();*/
        }

    }

    class getDriverMerit extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(drivermeritnavigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);
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
                    Log.e("url", Config.ip+"DriverList_api/driverMeritSystem/month/"+selectedmonthstring);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"DriverList_api/driverMeritSystem/month/"+selectedmonthstring,Jsonfunctions.GET);

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
                                    check=1;
                                    JSONObject obj = jsonArray.getJSONObject(j);
                                    name=obj.getString("name");
                                    Log.e("+++",obj.getString("name"));
                                    DRIVERNAMEARRAY[j]=name;
                                    namedriver.add(name);
                                    route=obj.getString("assigned_bus");
                                    Log.e("+++",obj.getString("assigned_bus"));
                                    routedriver.add(route);
                                    mobileno=obj.getString("mobile");
                                    Log.e("+++",obj.getString("mobile"));
                                    mobilenodriver.add(mobileno);
                                    speedlimit=obj.getString("speed_limit");
                                    Log.e("+++",obj.getString("speed_limit"));
                                    speedlimitdriver.add(speedlimit);
                                    rashdriving=obj.getString("rash_driving");
                                    Log.e("+++",obj.getString("rash_driving"));
                                    rashdrivingdriver.add(rashdriving);
                                    timemaintaince=obj.getString("time_maitanance");
                                    Log.e("+++",obj.getString("time_maitanance"));
                                    timemaintaincedriver.add(timemaintaince);
                                    driverImage=obj.getString("photo");
                                    Log.e("+++",obj.getString("photo"));
                                    if(driverImage.equalsIgnoreCase("null"))
                                        URL= DriverUploads+"employees1.png";
                                    else
                                        URL= DriverUploads+driverImage;
                                    Log.e("+++",URL);
                                    driverImagepng.add(URL);
                                    IMAGES[j]=URL;
                                    count=j;
                                    speedlimitintvalue=obj.getDouble("speed_limit");
                                    rashdrivingintvalue=obj.getDouble("rash_driving");
                                    timemaintainceintvalue=obj.getDouble("time_maitanance");
                                    //Start of if loop speed
                                    if(speedlimitintvalue==5){
                                        SPEEDLIMIT[j]="fivestar";
                                    }
                                    else if (speedlimitintvalue<5 && speedlimitintvalue>=4.5){
                                        SPEEDLIMIT[j]="fourhalfstar";
                                    }
                                    else if(speedlimitintvalue<4.5 && speedlimitintvalue>=4){
                                        SPEEDLIMIT[j]="fourstar";
                                    }
                                    else if(speedlimitintvalue<4 && speedlimitintvalue>=3.5){
                                        SPEEDLIMIT[j]="threehalfstar";
                                    }
                                    else if(speedlimitintvalue<3.5 && speedlimitintvalue>=3){
                                        SPEEDLIMIT[j]="threestar";
                                    }
                                    else if(speedlimitintvalue<3 && speedlimitintvalue>=2.5){
                                        SPEEDLIMIT[j]="twohalfstar";
                                    }
                                    else if(speedlimitintvalue<2.5 && speedlimitintvalue>=2){
                                        SPEEDLIMIT[j]="twostar";
                                    }
                                    else if(speedlimitintvalue<2 && speedlimitintvalue>=1.5){
                                        SPEEDLIMIT[j]="onehalfstar";
                                    }
                                    else if(speedlimitintvalue<1.5 && speedlimitintvalue>=1){
                                        SPEEDLIMIT[j]="onestar";
                                    }
                                    else if(speedlimitintvalue<1 && speedlimitintvalue>=0.5){
                                        SPEEDLIMIT[j]="halfstar";
                                    }
                                    else if(speedlimitintvalue<0.5 && speedlimitintvalue>=0){
                                        SPEEDLIMIT[j]="zerostar";
                                    }
                                    //end of if loop speed
                                    //start of rash loop
                                    if(rashdrivingintvalue==5){
                                        RASHDRIVING[j]="fivestar";
                                    }
                                    else if (rashdrivingintvalue<5 && rashdrivingintvalue>=4.5){
                                        RASHDRIVING[j]="fourhalfstar";
                                    }
                                    else if(rashdrivingintvalue<4.5 && rashdrivingintvalue>=4){
                                        RASHDRIVING[j]="fourstar";
                                    }
                                    else if(rashdrivingintvalue<4 && rashdrivingintvalue>=3.5){
                                        RASHDRIVING[j]="threehalfstar";
                                    }
                                    else if(rashdrivingintvalue<3.5 && rashdrivingintvalue>=3){
                                        RASHDRIVING[j]="threestar";
                                    }
                                    else if(rashdrivingintvalue<3 && rashdrivingintvalue>=2.5){
                                        RASHDRIVING[j]="twohalfstar";
                                    }
                                    else if(rashdrivingintvalue<2.5 && rashdrivingintvalue>=2){
                                        RASHDRIVING[j]="twostar";
                                    }
                                    else if(rashdrivingintvalue<2 && rashdrivingintvalue>=1.5){
                                        RASHDRIVING[j]="onehalfstar";
                                    }
                                    else if(rashdrivingintvalue<1.5 && rashdrivingintvalue>=1){
                                        RASHDRIVING[j]="onestar";
                                    }
                                    else if(rashdrivingintvalue<1 && rashdrivingintvalue>=0.5){
                                        RASHDRIVING[j]="halfstar";
                                    }
                                    else if(rashdrivingintvalue<0.5 && rashdrivingintvalue>=0){
                                        RASHDRIVING[j]="zerostar";
                                    }
                                    //end of rash loop
                                    //start of time loop
                                    if(timemaintainceintvalue==5){
                                        TIMEMAINTAINANCE[j]="fivestar";
                                    }
                                    else if (timemaintainceintvalue<5 && timemaintainceintvalue>=4.5){
                                        TIMEMAINTAINANCE[j]="fourhalfstar";
                                    }
                                    else if(timemaintainceintvalue<4.5 && timemaintainceintvalue>=4){
                                        TIMEMAINTAINANCE[j]="fourstar";
                                    }
                                    else if(timemaintainceintvalue<4 && timemaintainceintvalue>=3.5){
                                        TIMEMAINTAINANCE[j]="threehalfstar";
                                    }
                                    else if(timemaintainceintvalue<3.5 && timemaintainceintvalue>=3){
                                        TIMEMAINTAINANCE[j]="threestar";
                                    }
                                    else if(timemaintainceintvalue<3 && timemaintainceintvalue>=2.5){
                                        TIMEMAINTAINANCE[j]="twohalfstar";
                                    }
                                    else if(timemaintainceintvalue<2.5 && timemaintainceintvalue>=2){
                                        TIMEMAINTAINANCE[j]="twostar";
                                    }
                                    else if(timemaintainceintvalue<2 && timemaintainceintvalue>=1.5){
                                        TIMEMAINTAINANCE[j]="onehalfstar";
                                    }
                                    else if(timemaintainceintvalue<1.5 && timemaintainceintvalue>=1){
                                        TIMEMAINTAINANCE[j]="onestar";
                                    }
                                    else if(timemaintainceintvalue<1 && timemaintainceintvalue>=0.5){
                                        TIMEMAINTAINANCE[j]="halfstar";
                                    }
                                    else if(timemaintainceintvalue<0.5 && timemaintainceintvalue>=0){
                                        TIMEMAINTAINANCE[j]="zerostar";
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
                imagedisplay();
            progressDialog.dismiss();
        }
    }

    public void imagedisplay(){
        if(check==1) {
            grid.setAdapter(new ImageAdapter(this));
            check=0;
        }
        else{
            grid.setAdapter(new ImageAdapterdummy(this));
        }
            /* Picasso.with(this)
                .load("http://al-amaanah.com/Tifly_Pro/uploads/driver_image/employees1.png")
                .resize(400,400)
                .into(imageView);
        imageViewtester.setImageDrawable(imageView.getDrawable());
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/

//        Log.e("name",namedriver.get(1));
//        Log.e("Phnoe no",mobilenodriver.get(1));
//        drivername.setText(namedriver.get(1));
//        drivername.setText(mobilenodriver.get(1));

    }

    private static class ImageAdapter extends BaseAdapter {


        private static final String[] IMAGE_URLS = IMAGES;

        private LayoutInflater inflater;

        Context c;

        ImageAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            c = context;

        }

        @Override
        public int getCount() {
            return count+1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                final ViewHolder holder;
                View view = convertView;
                if (view == null) {
                    view = inflater.inflate(R.layout.item_grid_image, null, true);
                    holder = new ViewHolder();
                    assert view != null;

                    holder.imageView = (ImageView) view.findViewById(R.id.image);
                    holder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    //holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);

                    view.setTag(holder);
                } else {
                    holder = (ViewHolder) view.getTag();
                }
                Picasso.with(c)
                        .load(IMAGE_URLS[position])
                        .resize(400, 400).into(holder.imageView);
                TextView drivername1 = (TextView) view.findViewById(R.id.drivernametext);
                drivername1.setText(DRIVERNAMEARRAY[position]);
                ImageView myImageView = (ImageView) view.findViewById(R.id.image1);
                ImageView myImageView1 = (ImageView) view.findViewById(R.id.image2);
                ImageView myImageView2 = (ImageView) view.findViewById(R.id.image3);
                myImageView.setImageResource(getImageId(view.getContext(), SPEEDLIMIT[position]));
                myImageView1.setImageResource(getImageId(view.getContext(), RASHDRIVING[position]));
                myImageView2.setImageResource(getImageId(view.getContext(), TIMEMAINTAINANCE[position]));
//                    .into(holder.imageView, new Callback() {
//
//                        @Override
//                        public void onSuccess() {
//                            holder.imageView.setVisibility(View.VISIBLE);
//                            holder.progressBar.setVisibility(View.INVISIBLE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            holder.progressBar.setVisibility(View.VISIBLE);
//                            holder.imageView.setVisibility(View.INVISIBLE);
//                        }
//                    });
                return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" +imageName , null, context.getPackageName());
    }

    private static class ImageAdapterdummy extends BaseAdapter {


        private static final String[] IMAGE_URLS = IMAGES;

        private LayoutInflater inflater;

        Context c;

        ImageAdapterdummy(Context context) {
            inflater = LayoutInflater.from(context);
            c = context;

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.emptylayoutfordrivermerit, null, true);
                Toast.makeText(parent.getContext(),parent.getResources().getString(R.string.sj_no_rating_for_this_month),Toast.LENGTH_SHORT).show();
                /*myToast.setText("Please Select A Date 11111");
                myToast.show();*/
                holder = new ViewHolder();
                assert view != null;

            } else {

            }

//                    .into(holder.imageView, new Callback() {
//
//                        @Override
//                        public void onSuccess() {
//                            holder.imageView.setVisibility(View.VISIBLE);
//                            holder.progressBar.setVisibility(View.INVISIBLE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            holder.progressBar.setVisibility(View.VISIBLE);
//                            holder.imageView.setVisibility(View.INVISIBLE);
//                        }
//                    });
            return view;
        }
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

            LayoutInflater inflater = (LayoutInflater) drivermeritnavigation.this
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

            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_hawkeye);
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

            LayoutInflater inflater = (LayoutInflater) drivermeritnavigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_reassign,(ViewGroup) findViewById(R.id.popup_element));

            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
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

            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_reassign_bus);
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
            Toast.makeText(drivermeritnavigation.this, getResources().getString(R.string.sj_please_select_bus),
                    Toast.LENGTH_LONG).show();
        }

        else
            new reassignbustoserver().execute();


    }


    class reassignbustoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(drivermeritnavigation.this, getResources().getString(R.string.sj_please_wait),
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
                                            Toast.makeText(drivermeritnavigation.this, getResources().getString(R.string.sj_reassigned_successfully), Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(drivermeritnavigation.this);

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
                Intent intent = new Intent(drivermeritnavigation.this, drivermeritnavigation.class);
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
                Intent intent = new Intent(drivermeritnavigation.this, drivermeritnavigation.class);
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
        drivermeritnavigation.this.getResources().updateConfiguration(config,
                drivermeritnavigation.this.getResources().getDisplayMetrics());
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(drivermeritnavigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drivermeritnavigation, menu);
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
            selectedmonth=0;
            selecteddate=0;
            selectedyear=0;
        } else if (id == R.id.routes) {
            Intent intent = new Intent(this, Student_Route_Creation_Navigation.class);
            startActivity(intent);
            selectedmonth=0;
            selecteddate=0;
            selectedyear=0;
        } else if (id == R.id.attendence) {
            Intent intent = new Intent(this, Student_Attendence_Navigation.class);
            startActivity(intent);
            selectedmonth=0;
            selecteddate=0;
            selectedyear=0;
        } else if (id == R.id.transfer) {
            Intent intent = new Intent(this, Transfer_Student_Navigation.class);
            startActivity(intent);
            selectedmonth=0;
            selecteddate=0;
            selectedyear=0;
        } else if (id == R.id.driverrating) {
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();

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
        else if (id == R.id.messaging) {
            Intent intent = new Intent(this, Messaging_Navigation.class);
            startActivity(intent);
            selectedmonth=0;
            selecteddate=0;
            selectedyear=0;
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
            selectedmonth=0;
            selecteddate=0;
            selectedyear=0;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
