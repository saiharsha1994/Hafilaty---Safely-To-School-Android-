package com.example.valuetechsa.admin_school_app;

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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.DB.DatabaseAdapter;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Breakdowns_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> busnamefromserver=new ArrayList<String>();
    ArrayList<String> busidfromserver=new ArrayList<String>();
    Spinner busListSpinner;
    String busNameselected,busIdselected,fromDateSelected,toDateSelected;
    ArrayList<String> BreakdownBusId=new ArrayList<String>();
    ArrayList<String> BreakdownBusName=new ArrayList<String>();
    ArrayList<String> BreakdownStatus=new ArrayList<String>();
    ArrayList<String> BreakdownDate=new ArrayList<String>();
    static String dateusergiven="empty";
    static String dateprintgiven="empty";
    int Breakdowncount=0;
    ProgressDialog progressDialog2=null;
    PopupWindow pwindo;

    Boolean isScrolling;

    Jsonfunctions sh;
    Typeface tfRobo,tfAdam;
    ProgressDialog progressDialog;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    RelativeLayout manage,listmanage;
    ListView lv1;
    MyCustomBasedBreakdownsAdapter myCustomBasedBreakdownAdapter;
    static int datecheck=0;
    static EditText dateFrom;
    static EditText dateTo;
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

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbh = new DatabaseAdapter(this);
        db = dbh.getWritableDatabase();
        Cursor cur1=db.rawQuery("SELECT * FROM language", null);
        cur1.moveToFirst();
        if(cur1.getCount()!=0){
            String language=cur1.getString(2);
            Log.e("language","^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+language);
            change_language(language);

        }
        setContentView(R.layout.activity_breakdowns_navigation);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.breakdownsheader);
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

        TextView TextViewNewFont = new TextView(Breakdowns_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_breakdowns));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Breakdowns_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Breakdowns_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
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



        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        if(connected==false){
            Toast.makeText(Breakdowns_Navigation.this, "Please Check Your Net Connection" ,
                    Toast.LENGTH_LONG).show();
        }

        Log.e("connection","))))))))))))))))))))))))))))))))))))))))))))))))))))))"+connected);

        /*action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp;Messaging</big></font>"));*/
        dateFrom=(EditText)findViewById(R.id.fromdatebox);
        dateTo=(EditText)findViewById(R.id.todatebox);

        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datecheck=1;
                showTruitonDatePickerDialog(view);
            }
        });

        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datecheck=2;
                showTruitonDatePickerDialog(view);
            }
        });

        manage = (RelativeLayout) findViewById(R.id.relativelayoutbreakdowns);
        listmanage=(RelativeLayout) findViewById(R.id.listmanage);
        fontchange();
        setLayoutInvisible();
        new getSpareBusFromServer().execute();
        new getBusesFromServer().execute();

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void setLayoutInvisible() {
        if (manage.getVisibility() == View.VISIBLE) {
            manage.setVisibility(View.GONE);
        }
        if (listmanage.getVisibility() == View.VISIBLE) {
            listmanage.setVisibility(View.GONE);
        }

    }
    public void setLayoutVisible() {
        if (manage.getVisibility() == View.GONE) {
            manage.setVisibility(View.VISIBLE);
        }
        if (listmanage.getVisibility() == View.GONE) {
            listmanage.setVisibility(View.VISIBLE);
        }

    }

    public void fontchange(){
        TextView vaddr=(TextView)findViewById(R.id.selectcriteriabreakdowns);
        TextView selb=(TextView)findViewById(R.id.breakdownbusname);
        TextView selb2=(TextView)findViewById(R.id.breakdowndatefrom);
        TextView selb3=(TextView)findViewById(R.id.breakdowndateto);
        TextView selb4=(TextView)findViewById(R.id.breakdownlisttextbox);
        TextView selb5=(TextView)findViewById(R.id.breakdownbusidtextbox);
        TextView selb6=(TextView)findViewById(R.id.breakdownbusnametextbox);
        TextView selb7=(TextView)findViewById(R.id.breakdownstatustextbox);
        TextView selb8=(TextView)findViewById(R.id.breakdowndatetextbox);
        Button viewr=(Button)findViewById(R.id.viewbreakdown);


        vaddr.setTypeface(tfRobo);
        selb.setTypeface(tfRobo);
        selb2.setTypeface(tfRobo);
        selb3.setTypeface(tfRobo);
        selb4.setTypeface(tfRobo);
        selb5.setTypeface(tfRobo);
        selb6.setTypeface(tfRobo);
        selb7.setTypeface(tfRobo);
        selb8.setTypeface(tfRobo);
        viewr.setTypeface(tfAdam);
        dateFrom.setTypeface(tfRobo);
        dateTo.setTypeface(tfRobo);
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
            dateprintgiven=year + "-" + (month + 1) + "-" + day;
            String s1="",s2="";
            if(month<9){
                month=month+1;
                s1=""+ (month);
                s1="0"+s1;
            }
            else {
                month=month+1;
                s1=""+ (month);
            }

            if(day<10)
            {
                s2="0"+day;
            }
            else
                s2=""+day;

            dateusergiven = year+"-"+(s1)+"-"+s2;
            if (datecheck==1)
                dateFrom.setText(dateusergiven);
            else if(datecheck==2)
                dateTo.setText(dateusergiven);

        }
    }

    class getBusesFromServer extends AsyncTask<Void,Void,Void> {
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
            getDropDownBusList();
        }
    }

    public void getDropDownBusList(){
        busListSpinner=(Spinner)findViewById(R.id.busselectionspinner);

        final String[] busmessagefrom=new String[busnamefromserver.size()+1];
        busmessagefrom[0]=getResources().getString(R.string.sj_select_bus);
        for(int i=1;i<=busnamefromserver.size();i++){
            busmessagefrom[i]=busnamefromserver.get(i-1);
        }


        ArrayAdapter<String> adapterbusmessagefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, busmessagefrom) {
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
        /*ArrayAdapter<String> adapterroutemessagefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, routemessagefrom);*/
        adapterbusmessagefrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        busListSpinner.setAdapter(adapterbusmessagefrom);


        busListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routedrivernamenavigation=item;
                busNameselected=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }



    public void viewRecord(View view){

        if(busNameselected.equalsIgnoreCase(getResources().getString(R.string.sj_select_bus))){
            Toast.makeText(Breakdowns_Navigation.this, getResources().getString(R.string.sj_please_select_bus),
                    Toast.LENGTH_LONG).show();
        }
        else{
            for(int i=0;i<busnamefromserver.size();i++){
                if(busNameselected.equals(busnamefromserver.get(i))){
                    busIdselected=busidfromserver.get(i);
                }
            }

            Log.e("&&&&&&&&&&&&&&&&",busIdselected);

            BreakdownBusId.clear();
            BreakdownBusName.clear();
            BreakdownStatus.clear();
            BreakdownDate.clear();

            Breakdowncount=0;
            fromDateSelected=dateFrom.getText().toString();
            toDateSelected=dateTo.getText().toString();
            if(fromDateSelected.isEmpty())
                Toast.makeText(Breakdowns_Navigation.this,getResources().getString(R.string.sj_please_select_from_date),Toast.LENGTH_SHORT).show();
            if(toDateSelected.isEmpty())
                Toast.makeText(Breakdowns_Navigation.this,getResources().getString(R.string.sj_please_select_to_date),Toast.LENGTH_SHORT).show();
            new getBreakdownRecordsFromServer().execute();
        }
    }

    class getBreakdownRecordsFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Breakdowns_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"BusCoordinates_api/getBreakdownBuses/bus_id/"+busIdselected+"/from_date/"+fromDateSelected+"/to_date/"+toDateSelected);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"BusCoordinates_api/getBreakdownBuses/bus_id/"+busIdselected+"/from_date/"+fromDateSelected+"/to_date/"+toDateSelected,Jsonfunctions.GET);

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
                                    String busid=obj.getString("bus_id");
                                    Log.e("busid",busid);
                                    String busname=obj.getString("name");
                                    Log.e("name",busname);
                                    String status=obj.getString("status");
                                    Log.e("status",status);
                                    String date=obj.getString("last_updated");
                                    Log.e("date",date);
                                    BreakdownBusId.add(busid);
                                    BreakdownBusName.add(busname);
                                    if(status.equalsIgnoreCase("2"))
                                        BreakdownStatus.add("Breakdown");
                                    else
                                        BreakdownStatus.add("Normal");
                                    BreakdownDate.add(date);

                                    Breakdowncount=Breakdowncount+1;
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
            if(Breakdowncount>0){
                setLayoutVisible();
            }
            else{
                Toast.makeText(Breakdowns_Navigation.this, getResources().getString(R.string.sj_no_records),
                        Toast.LENGTH_LONG).show();
                setLayoutInvisible();
            }

           /* messageglobalcount=count;
            for(int ii=0;ii<globalcount;ii++){
                messagearrayroutechecklist[ii]=0;

            }*/
            //liststudentgenerate();
            //listgenerate();

            recordslist();
            progressDialog.dismiss();
        }
    }

    public void recordslist(){
        ArrayList<SearchResultsBreakdowns> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.breakdownlist);
        myCustomBasedBreakdownAdapter=new MyCustomBasedBreakdownsAdapter(this, searchResults);
        lv1.setAdapter(myCustomBasedBreakdownAdapter);

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

    }


    private ArrayList<SearchResultsBreakdowns> GetSearchResults(){
        ArrayList<SearchResultsBreakdowns> results = new ArrayList<SearchResultsBreakdowns>();

        for(int reg=0;reg<Breakdowncount;reg++){
            SearchResultsBreakdowns sr1 = new SearchResultsBreakdowns();
            sr1.setBusId(BreakdownBusId.get(reg));
            sr1.setBusName(BreakdownBusName.get(reg));
            sr1.setStatus(BreakdownStatus.get(reg));
            sr1.setDate(BreakdownDate.get(reg));
            results.add(sr1);
        }
        Breakdowncount=0;

        return results;
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

            LayoutInflater inflater = (LayoutInflater) Breakdowns_Navigation.this
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

            LayoutInflater inflater = (LayoutInflater) Breakdowns_Navigation.this
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

            //Toast.makeText(Breakdowns_Navigation.this,busnameforreassign.size()+"",Toast.LENGTH_SHORT).show();

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
            Toast.makeText(Breakdowns_Navigation.this, getResources().getString(R.string.sj_please_select_bus),
                    Toast.LENGTH_LONG).show();
        }

        else
            new reassignbustoserver().execute();


    }


    class reassignbustoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Breakdowns_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                                            Toast.makeText(Breakdowns_Navigation.this, getResources().getString(R.string.sj_reassigned_successfully), Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Breakdowns_Navigation.this);

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
                Intent intent = new Intent(Breakdowns_Navigation.this, Student_Misbehaviour_Navigation.class);
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
                Intent intent = new Intent(Breakdowns_Navigation.this, Student_Misbehaviour_Navigation.class);
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
        Breakdowns_Navigation.this.getResources().updateConfiguration(config,
                Breakdowns_Navigation.this.getResources().getDisplayMetrics());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Breakdowns_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_misbehaviour_navigation, menu);
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
            Intent intent = new Intent(this, Student_Attendence_Navigation.class);
            startActivity(intent);
        } else if (id == R.id.transfer) {
            Intent intent = new Intent(this, Transfer_Student_Navigation.class);
            startActivity(intent);

        } else if (id == R.id.driverrating) {
            Intent intent = new Intent(this, drivermeritnavigation.class);
            startActivity(intent);

        } else if (id == R.id.breakdowns) {
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();

        }
        else if (id == R.id.managefuel) {
            Intent intent = new Intent(this, Manage_Fuel_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.parents) {
            Intent intent = new Intent(this, Parent_Creation_Navigation.class);
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
        else if (id == R.id.studentmisbehaviour) {
            Intent intent = new Intent(this, Student_Misbehaviour_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.drivercreationclick) {
            Intent intent = new Intent(this, Driver_Create_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.messaging) {
            Intent intent = new Intent(this, Messaging_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.changelanguage) {
            showAlertForLanguage();
        }
        else if (id == R.id.leave) {
            Intent intent = new Intent(this, Leave_Navigation.class);
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


    public class MyCustomBasedBreakdownsAdapter extends BaseAdapter {
        private ArrayList<SearchResultsBreakdowns> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedBreakdownsAdapter(Context context, ArrayList<SearchResultsBreakdowns> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_breakdowns, null);
                holder = new ViewHolder();
                holder.busid = (TextView) convertView.findViewById(R.id.text_busid);
                holder.busid.setTypeface(tfRobo);
                holder.busname = (TextView) convertView.findViewById(R.id.text_busname);
                holder.busname.setTypeface(tfRobo);
                holder.status = (TextView) convertView.findViewById(R.id.text_status);
                holder.status.setTypeface(tfRobo);
                holder.date = (TextView) convertView.findViewById(R.id.text_date);
                holder.date.setTypeface(tfRobo);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.busid.setText(searchArrayList.get(position).getBusId());
            holder.busname.setText(searchArrayList.get(position).getBusName());
            holder.status.setText(searchArrayList.get(position).getStatus());
            holder.date.setText(searchArrayList.get(position).getDate());

            return convertView;
        }

        class ViewHolder {
            TextView busid;
            TextView busname;
            TextView status;
            TextView date;
        }
    }
}
