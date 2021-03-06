package com.example.valuetechsa.admin_school_app;

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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.util.ArrayList;
import java.util.Locale;

public class Messaging_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<String> routeidfromservermessage=new ArrayList<String>();
    ArrayList<String> routenamefromservermessage=new ArrayList<String>();
    Spinner sendmessageroutelist,studentteacherlist;
    String routeMessageselected,routeMessageIdselected,studentteacherselected,studentteacherIdselected;
    ArrayList<String> MessageStudentStopName=new ArrayList<String>();
    ArrayList<String> MessageStudentLatitude=new ArrayList<String>();
    ArrayList<String> MessageStudentLongitude=new ArrayList<String>();
    ArrayList<String> MessageStudentLatitudeLongitude=new ArrayList<String>();
    ArrayList<String> MessageStudentAssignTo=new ArrayList<String>();
    ArrayList<String> MessageStudentAssignToId=new ArrayList<String>();
    ArrayList<String> MessageStudentAssignToType=new ArrayList<String>();
    ArrayList<String> MessageStudentContactNoList=new ArrayList<String>();
    ArrayList<String> MessageStudentStopTiming=new ArrayList<String>();
    String message;
    int Messagecount=0,Messageglobalcount=0;
    int[] arrayroutemessagechecklist=new int[1000000];
    int checkall=0;
    EditText enterthetextedit;
    String idappend="empty",parentappend="empty";
    String emptymessage="empty";
    Boolean isScrolling;
    String response="",url="";
    Jsonfunctions sh;
    Typeface tfRobo,tfAdam;
    ProgressDialog progressDialog,progressDialogmessage;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    RelativeLayout manage,listmanage;
    LinearLayout messagetypemanage;
    ListView lv1;
    MyCustomBaseMessageAdapter myCustomBaseMessageAdapter;
    FrameLayout layout_MainMenu;
    ProgressDialog progressDialog2;
    PopupWindow pwindo;

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
        setContentView(R.layout.activity_messaging__navigation);

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
        TextView navUsername = (TextView) headerView.findViewById(R.id.messagingheader);
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

        TextView TextViewNewFont = new TextView(Messaging_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_messaging));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Messaging_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Messaging_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);

        layout_MainMenu = (FrameLayout) findViewById( R.id.outerlayout);
        layout_MainMenu.getForeground().setAlpha( 0);

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
            Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_check_internet_connection) ,
                    Toast.LENGTH_LONG).show();
        }

        Log.e("connection","))))))))))))))))))))))))))))))))))))))))))))))))))))))"+connected);

        /*action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp;Messaging</big></font>"));*/
        enterthetextedit=(EditText)findViewById(R.id.enterthetext);
        manage = (RelativeLayout) findViewById(R.id.managelayoutmessage);
        listmanage=(RelativeLayout) findViewById(R.id.listmanage);
        messagetypemanage=(LinearLayout) findViewById(R.id.buttonmessagemanage);
        setLayoutInvisible();
        fontchange();
        new getRoutesFromServer().execute();

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
        if (messagetypemanage.getVisibility() == View.VISIBLE) {
            messagetypemanage.setVisibility(View.GONE);
        }

    }
    public void setLayoutVisible() {
        if (manage.getVisibility() == View.GONE) {
            manage.setVisibility(View.VISIBLE);
        }
        if (listmanage.getVisibility() == View.GONE) {
            listmanage.setVisibility(View.VISIBLE);
        }
        if (messagetypemanage.getVisibility() == View.GONE) {
            messagetypemanage.setVisibility(View.VISIBLE);
        }

    }

    public void fontchange(){
        TextView selcr=(TextView)findViewById(R.id.selectcriteria);
        TextView selro=(TextView)findViewById(R.id.selectroute);
        TextView stutes=(TextView)findViewById(R.id.studentteacherselection);
        Button gtlist=(Button)findViewById(R.id.getlist);
        TextView manatra=(TextView)findViewById(R.id.transfermanage);
        TextView typpp=(TextView)findViewById(R.id.typetextbox);
        TextView mane=(TextView)findViewById(R.id.studentidtextbox);
        TextView iid=(TextView)findViewById(R.id.idtextbox);
        TextView loc=(TextView)findViewById(R.id.locationtextbox);
        TextView conde=(TextView)findViewById(R.id.contactdetailstextbox);
        EditText ypemsg=(EditText)findViewById(R.id.enterthetext) ;
        Button sdmsg=(Button)findViewById(R.id.sendmessage);
        selcr.setTypeface(tfRobo);
        selro.setTypeface(tfRobo);
        stutes.setTypeface(tfRobo);
        gtlist.setTypeface(tfAdam);
        manatra.setTypeface(tfRobo);
        typpp.setTypeface(tfRobo);
        mane.setTypeface(tfRobo);
        iid.setTypeface(tfRobo);
        loc.setTypeface(tfRobo);
        conde.setTypeface(tfRobo);
        ypemsg.setTypeface(tfRobo);
        sdmsg.setTypeface(tfAdam);
    }

    class getRoutesFromServer extends AsyncTask<Void,Void,Void> {
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
                                    routeidfromservermessage.add(id);
                                    Log.e("++++++++++++++",id);
                                    String nameroute=obj.getString("route_name");
                                    Log.e("++++++++++++++",nameroute);
                                    routenamefromservermessage.add(nameroute);
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
            getDropDownmessage();
        }
    }

    public void getDropDownmessage(){
        sendmessageroutelist=(Spinner)findViewById(R.id.studentteacherselectionspinner);
        studentteacherlist=(Spinner)findViewById(R.id.studentteachermessagespinner);

        final String[] routemessagefrom=new String[routenamefromservermessage.size()+1];
        routemessagefrom[0]=getResources().getString(R.string.sj_select_route);
        for(int i=1;i<=routenamefromservermessage.size();i++){
            routemessagefrom[i]=routenamefromservermessage.get(i-1);
        }

        final String[] studentteachermessagein=new String[3];
        studentteachermessagein[0]=getResources().getString(R.string.sj_select);
        studentteachermessagein[1]=getResources().getString(R.string.sj_student);
        studentteachermessagein[2]=getResources().getString(R.string.sj_teacher);

        ArrayAdapter<String> adapterroutemessagefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routemessagefrom) {
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
        adapterroutemessagefrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sendmessageroutelist.setAdapter(adapterroutemessagefrom);

        ArrayAdapter<String> adapterStudentteachermessage = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, studentteachermessagein) {
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
        /*ArrayAdapter<String> adapterStudentteachermessage = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, studentteachermessagein);*/
        adapterStudentteachermessage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentteacherlist.setAdapter(adapterStudentteachermessage);

        sendmessageroutelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routedrivernamenavigation=item;
                routeMessageselected=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        studentteacherlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equalsIgnoreCase(getResources().getString(R.string.sj_student)))
                    studentteacherselected="student";
                if(item.equalsIgnoreCase(getResources().getString(R.string.sj_teacher)))
                    studentteacherselected="teacher";
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void selectedMessageRoute(View view){

        if(routeMessageselected.equals(getResources().getString(R.string.sj_select_route))){
            Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_please_select_route),
                    Toast.LENGTH_LONG).show();
        }
        else  if(studentteacherselected.equals(getResources().getString(R.string.sj_select))){
            Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_please_select_student_or_teacher),
                    Toast.LENGTH_LONG).show();
        }
        else{
            for(int i=0;i<routenamefromservermessage.size();i++){
                if(routeMessageselected.equals(routenamefromservermessage.get(i))){
                    routeMessageIdselected=routeidfromservermessage.get(i);
                }
            }
                if(studentteacherselected.equals("student")){
                    studentteacherIdselected="1";
                }
                if(studentteacherselected.equals("teacher")){
                    studentteacherIdselected="2";
                }

            Log.e("&&&&&&&&&&&&&&&&",routeMessageIdselected);
            Log.e("&&&&&&&&&&&&&&&&",studentteacherIdselected);
            CheckBox checkBoxmain = (CheckBox)findViewById(R.id.radiobuttuntransfer);
            checkBoxmain.setChecked(false);
            MessageStudentStopName.clear();
            MessageStudentLatitude.clear();
            MessageStudentLongitude.clear();
            MessageStudentLatitudeLongitude.clear();
            MessageStudentAssignTo.clear();
            MessageStudentAssignToId.clear();
            MessageStudentAssignToType.clear();
            MessageStudentContactNoList.clear();
            MessageStudentStopTiming.clear();
            Messagecount=0;
            new getStudentsFromMessageServer().execute();
        }
    }

    class getStudentsFromMessageServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Messaging_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"GetRouteDetails_api/StopsListByRoute/route_id/"+routeMessageIdselected);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"GetRouteDetails_api/StopsListByRoute/route_id/"+routeMessageIdselected,Jsonfunctions.GET);

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
                                    String lati=obj.getString("latitude");
                                    String longi=obj.getString("langitude");
                                    String latilongi=lati+","+longi;
                                    Log.e("latitudelongitude",latilongi);
                                    String assignto=obj.getString("assigned_to");
                                    Log.e("assign to",obj.getString("assigned_to"));
                                    Log.e("id",assignto.substring(0,1));
                                    Log.e("type",assignto.substring(2,assignto.length()));
                                    String contactno=obj.getString("contact_num");
                                    Log.e("contact no",obj.getString("contact_num"));
                                    String stoptiming=obj.getString("stope_time");
                                    Log.e("stop timing",stoptiming);
                                    if(studentteacherselected.equalsIgnoreCase(assignto.substring(2,assignto.length()))){
                                        Log.e("@@@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                                        MessageStudentStopName.add(Stopnames);
                                        MessageStudentLatitude.add(lati);
                                        MessageStudentLongitude.add(longi);
                                        MessageStudentLatitudeLongitude.add(latilongi);
                                        MessageStudentAssignTo.add(assignto);
                                        MessageStudentAssignToId.add(assignto.substring(0,1));
                                        MessageStudentAssignToType.add(assignto.substring(2,assignto.length()));
                                        MessageStudentStopTiming.add(stoptiming);
                                        MessageStudentContactNoList.add(contactno);
                                        Messagecount=Messagecount+1;
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
        protected void onPostExecute(Void result){
            if(Messagecount>0){
                setLayoutVisible();
            }
            else{
                Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_no_students_or_teachers_exist),
                        Toast.LENGTH_LONG).show();
                setLayoutInvisible();
            }

           /* messageglobalcount=count;
            for(int ii=0;ii<globalcount;ii++){
                messagearrayroutechecklist[ii]=0;

            }*/
            //liststudentgenerate();
            //listgenerate();

            Messageglobalcount=Messagecount;
            for(int ii=0;ii<Messageglobalcount;ii++){
                arrayroutemessagechecklist[ii]=0;

            }
            messagelist();
            progressDialog.dismiss();
        }
    }

    public void messagelist(){
        ArrayList<SearchResultMessage> searchResults = GetSearchResults();
         lv1 = (ListView) findViewById(R.id.messagelist);
        myCustomBaseMessageAdapter=new MyCustomBaseMessageAdapter(this, searchResults);
        lv1.setAdapter(myCustomBaseMessageAdapter);

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
                if (isScrolling) {
                    Log.e("position-------", "-------------"+pos);
                    if(arrayroutemessagechecklist[pos]==0){
                        arrayroutemessagechecklist[pos]=1;
                    }
                    else{
                        arrayroutemessagechecklist[pos]=0;
                    }
                    CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkboxrowmessage);
                    checkBox.setChecked(!checkBox.isChecked());
                    CheckBox checkBoxmain = (CheckBox)findViewById(R.id.radiobuttuntransfer);
                    checkBoxmain.setChecked(false);
                }
                    }
        });
    }

    public void allclickradio(View view){

        ListView lv =  (ListView) findViewById(R.id.messagelist);
        Log.e("))))",")))"+lv1.getCount());
       /* View view1=lv1.getChildAt(0);
        CheckBox cb;
        cb = (CheckBox)view1.findViewById(R.id.checkboxrowmessage);
        cb.setChecked(!cb.isChecked());
        View view2=lv1.getChildAt(1);
        CheckBox cb2;
        cb2 = (CheckBox)view2.findViewById(R.id.checkboxrowmessage);
        cb2.setChecked(!cb2.isChecked());
        View view3=lv1.getChildAt(2);
        CheckBox cb3;
        cb3 = (CheckBox)view3.findViewById(R.id.checkboxrowmessage);
        cb3.setChecked(!cb3.isChecked());
        View view4=myCustomBaseMessageAdapter.getView(4, null, null);
        *//*View view4=lv1.getChildAt(3);*//*
        CheckBox cb4;
        cb4 = (CheckBox)view4.findViewById(R.id.checkboxrowmessage);
        cb4.setChecked(!cb4.isChecked());*/
        int counttrueorfalse=0;
        for(int tt=0;tt<Messageglobalcount;tt++){
            if(arrayroutemessagechecklist[tt]==1){
                counttrueorfalse=counttrueorfalse+1;
            }
        }

        if (counttrueorfalse == Messageglobalcount) {
            for(int i=0; i<=lv1.getLastVisiblePosition() -lv1.getFirstVisiblePosition();i++)
            {
                View view1=lv1.getChildAt(i);
                CheckBox cb;
                cb = (CheckBox)view1.findViewById(R.id.checkboxrowmessage);
                cb.setChecked(false);
            }
            for(int i=0; i<Messageglobalcount;i++)
            {
                View view1=myCustomBaseMessageAdapter.getView(i, null, lv1);
                CheckBox cb;
                cb = (CheckBox)view1.findViewById(R.id.checkboxrowmessage);
                cb.setChecked(false);
            }
            for(int tt=0;tt<Messageglobalcount;tt++){
                arrayroutemessagechecklist[tt]=0;
            }
            CheckBox checkBoxmain = (CheckBox)findViewById(R.id.radiobuttuntransfer);
            checkBoxmain.setChecked(false);
        }
        else{
            for(int i=0; i<=lv1.getLastVisiblePosition() -lv1.getFirstVisiblePosition();i++)
            {
                View view1=lv1.getChildAt(i);
                CheckBox cb;
                cb = (CheckBox)view1.findViewById(R.id.checkboxrowmessage);
                cb.setChecked(true);
            }
            for(int i=0; i<Messageglobalcount;i++)
            {
                View view1=myCustomBaseMessageAdapter.getView(i, null, lv1);
                CheckBox cb;
                cb = (CheckBox)view1.findViewById(R.id.checkboxrowmessage);
                cb.setChecked(true);
            }
            for(int tt=0;tt<Messageglobalcount;tt++){
                arrayroutemessagechecklist[tt]=1;
            }

        }

    }

    public void clickradio(View view){
        int ii=(Integer) view.getTag();
        Log.e("((((((","(((((((((((((((((((((("+ii);
        if(arrayroutemessagechecklist[ii]==0){
            arrayroutemessagechecklist[ii]=1;
        }
        else{
            arrayroutemessagechecklist[ii]=0;
        }
    }


    private ArrayList<SearchResultMessage> GetSearchResults(){
        ArrayList<SearchResultMessage> results = new ArrayList<SearchResultMessage>();

        for(int reg=0;reg<Messagecount;reg++){
            SearchResultMessage sr1 = new SearchResultMessage();
            sr1.setType(MessageStudentAssignToType.get(reg));
            sr1.setName(MessageStudentStopName.get(reg));
            sr1.setId(MessageStudentAssignToId.get(reg));
            sr1.setLocation(MessageStudentLatitudeLongitude.get(reg));
            sr1.setContact(MessageStudentContactNoList.get(reg));
            results.add(sr1);
        }
        Messagecount=0;

        return results;
    }

    public void selectedmessagelist(View view){
        emptymessage=enterthetextedit.getText().toString();
        if(emptymessage.equalsIgnoreCase("empty")||emptymessage.isEmpty()){
            Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_please_enter_a_message) ,
                    Toast.LENGTH_SHORT).show();
        }
        else{
            checkall=0;
            Log.e("array value","outside loop");
            Log.e("array value",Messageglobalcount+"");
            for(int i=0;i<Messageglobalcount;i++){
                Log.e("array value",arrayroutemessagechecklist[i]+"");
                if(arrayroutemessagechecklist[i]==1){
                    checkall=checkall+1;
                }
            }
            if(studentteacherselected.equalsIgnoreCase("student")){
                Log.e("%%%%%%%%%%%5",checkall+"");
                if(checkall==0){
                    Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_please_select_students) ,
                            Toast.LENGTH_SHORT).show();
                }
                else if(checkall==Messageglobalcount){
                    new sendmessageall().execute();
                }
                else if(checkall!=0&&checkall>0){
                    for(int i=0;i<Messageglobalcount;i++){
                        if(arrayroutemessagechecklist[i]==1){
                            if(idappend.equalsIgnoreCase("empty")){
                                idappend="";
                                idappend=MessageStudentAssignToId.get(i);
                            }
                            else{
                                idappend=idappend+","+MessageStudentAssignToId.get(i);
                            }
                        }
                    }
                    for(int j=0;j<checkall;j++){
                        if(parentappend.equalsIgnoreCase("empty")){
                            parentappend="";
                            parentappend="parent";
                        }
                        else{
                            parentappend=parentappend+",parent";
                        }
                    }
                    Log.e("##########",idappend);
                    Log.e("##########",parentappend);
                    new sendmessagesome().execute();
                }
            }
            else if(studentteacherselected.equalsIgnoreCase("teacher")){
                Log.e("%%%%%%%%%%%5",checkall+"");
                if(checkall==0){
                    Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_please_select_teachers) ,
                            Toast.LENGTH_SHORT).show();
                }
                else if(checkall==Messageglobalcount){
                    new sendmessageallteachers().execute();
                }
                else if(checkall!=0&&checkall>0){
                    for(int i=0;i<Messageglobalcount;i++){
                        if(arrayroutemessagechecklist[i]==1){
                            if(idappend.equalsIgnoreCase("empty")){
                                idappend="";
                                idappend=MessageStudentAssignToId.get(i);
                            }
                            else{
                                idappend=idappend+","+MessageStudentAssignToId.get(i);
                            }
                        }
                    }
                    for(int j=0;j<checkall;j++){
                        if(parentappend.equalsIgnoreCase("empty")){
                            parentappend="";
                            parentappend="teacher";
                        }
                        else{
                            parentappend=parentappend+",teacher";
                        }
                    }
                    Log.e("##########",idappend);
                    Log.e("##########",parentappend);
                    new sendmessagesome().execute();
                }
            }
        }
    }

    class sendmessagesome extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute()
        {
            progressDialogmessage = ProgressDialog.show(Messaging_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_sending_message), true);

        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try {
                sh = new Jsonfunctions();
                url = Config.ip + "SendMessageFromDriver_api/insert/Msg/"+URLEncoder.encode(emptymessage,"UTF-8")+"/MsgTo/"+URLEncoder.encode(idappend,"UTF-8")+"/Type/"+URLEncoder.encode(parentappend,"UTF-8");
                Log.e("url created", url);
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
                                message=Jobj.getString("responsemsg");
                                Log.e("Alllllll",message);
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
            catch (Exception e){
                e.printStackTrace();
            }

            emptymessage="empty";
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            parentappend="empty";
            idappend="empty";
            progressDialogmessage.dismiss();
            if(message.equalsIgnoreCase("Sent Successfully")){
                Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_message_sent_successfully) ,
                        Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        }
    }

    class sendmessageall extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute()
        {
            progressDialogmessage = ProgressDialog.show(Messaging_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_sending_message), true);

        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try {
                sh = new Jsonfunctions();
                url = Config.ip + "SendMessageFromDriver_api/insert/Msg/"+URLEncoder.encode(emptymessage,"UTF-8")+"/MsgTo/all/Type/parent-" + routeMessageIdselected;
                Log.e("url created", url);
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
                                message=Jobj.getString("responsemsg");
                                Log.e("Alllllll",message);
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
            catch (Exception e){
                e.printStackTrace();
            }
            emptymessage="empty";
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            progressDialogmessage.dismiss();
            if(message.equalsIgnoreCase("Sent Successfully")){
                Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_message_sent_successfully) ,
                        Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        }
    }

    class sendmessageallteachers extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute()
        {
            progressDialogmessage = ProgressDialog.show(Messaging_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_sending_message), true);

        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try {
                sh = new Jsonfunctions();
                url = Config.ip + "SendMessageFromDriver_api/insert/Msg/"+URLEncoder.encode(emptymessage,"UTF-8")+"/MsgTo/all/Type/teacher-" + routeMessageIdselected;
                Log.e("url created", url);
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
                                message=Jobj.getString("responsemsg");
                                Log.e("Alllllll",message);
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
            catch (Exception e){
                e.printStackTrace();
            }
            emptymessage="empty";
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            progressDialogmessage.dismiss();
            if(message.equalsIgnoreCase("Sent Successfully")){
                Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_message_sent_successfully) ,
                        Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
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

            LayoutInflater inflater = (LayoutInflater) Messaging_Navigation.this
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

            LayoutInflater inflater = (LayoutInflater) Messaging_Navigation.this
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
            Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_please_select_bus),
                    Toast.LENGTH_LONG).show();
        }

        else
            new reassignbustoserver().execute();


    }


    class reassignbustoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Messaging_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                                            Toast.makeText(Messaging_Navigation.this, getResources().getString(R.string.sj_reassigned_successfully), Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Messaging_Navigation.this);

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
                Intent intent = new Intent(Messaging_Navigation.this, Messaging_Navigation.class);
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
                Intent intent = new Intent(Messaging_Navigation.this, Messaging_Navigation.class);
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
        Messaging_Navigation.this.getResources().updateConfiguration(config,
                Messaging_Navigation.this.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Messaging_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.messaging__navigation, menu);
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

        } else if (id == R.id.messaging) {
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
