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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
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

import java.util.ArrayList;
import java.util.Locale;

public class Student_List_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Typeface tfRobo;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    ProgressDialog progressDialog2=null;
    Spinner buslistspinner;
    int globalcount=0,globalbusid=0;
    int globalnumberinlist=0;
    ListView lv1;
    Boolean isScrolling;
    ArrayList<String> busnamelistfromserver=new ArrayList<String>();
    ArrayList<String> busidlistfromserver=new ArrayList<String>();
    RelativeLayout manage,listmanage;
    MyCustomBasedStudentListAdaper myCustomBasedStudentListAdaper;
    PopupWindow pwindo;

    ArrayList<String> studentidfromserver=new ArrayList<String>();
    ArrayList<String> studentnamefromserver=new ArrayList<String>();
    ArrayList<String> studentclassidfromserver=new ArrayList<String>();
    ArrayList<String> studentclassnamefromserver=new ArrayList<String>();
    ArrayList<String> studentsectionidfromserver=new ArrayList<String>();
    ArrayList<String> studentsectionnamefromserver=new ArrayList<String>();
    ArrayList<String> studentfathersnofromserver=new ArrayList<String>();
    ArrayList<String> studentmothernumberfromserver=new ArrayList<String>();
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
        setContentView(R.layout.activity_student__list__navigation);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.Studentlistheader);
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

        TextView TextViewNewFont = new TextView(Student_List_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.stv_Student_List));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Student_List_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        TextViewNewFont.setTypeface(tfRobo);

        layout_MainMenu = (FrameLayout) findViewById( R.id.outerlayout);
        layout_MainMenu.getForeground().setAlpha( 0);

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

        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
        new getBusListFromServer().execute();
        manage=(RelativeLayout) findViewById(R.id.relativelayoutstudentlistcreeation);
        listmanage=(RelativeLayout) findViewById(R.id.studentlistlist);
        changeFont();
        setLayoutInvisible();
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
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void changeFont(){
        TextView studentlist=(TextView)findViewById(R.id.studentlistlisttextbox);
        TextView studentlistname=(TextView)findViewById(R.id.studentlistnametextbox);
        TextView studentlistclass=(TextView)findViewById(R.id.studentlistclasstextbox);
        TextView studentlistsection=(TextView)findViewById(R.id.studentlistsectiontextbox);
        TextView studentlistmobileno=(TextView)findViewById(R.id.studentlistmobilenotextbox);

        studentlist.setTypeface(tfRobo);
        studentlistname.setTypeface(tfRobo);
        studentlistclass.setTypeface(tfRobo);
        studentlistsection.setTypeface(tfRobo);
        studentlistmobileno.setTypeface(tfRobo);
    }

    class getStudentsListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            studentidfromserver.clear();
            studentnamefromserver.clear();
            studentclassidfromserver.clear();
            studentclassnamefromserver.clear();
            studentsectionidfromserver.clear();
            studentclassnamefromserver.clear();
            studentfathersnofromserver.clear();
            studentmothernumberfromserver.clear();
            globalcount=0;
            progressDialog2 = ProgressDialog.show(Student_List_Navigation.this, "Please wait.",
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
                    Log.e("url", Config.ip+"Students_api/onlyStudentsByBus/Bus_Id/"+globalbusid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Students_api/onlyStudentsByBus/Bus_Id/"+globalbusid,Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                JSONArray jsonArray = Jobj.getJSONArray("result_arr");
                                globalnumberinlist=jsonArray.length();
                                for (int j = 0; j < jsonArray.length(); j++){

                                    JSONObject obj = jsonArray.getJSONObject(j);

                                        Log.e("student_id",obj.getString("student_id"));
                                        String student_id=obj.getString("student_id");
                                        studentidfromserver.add(student_id);
                                        Log.e("name",obj.getString("name"));
                                        String name=obj.getString("name");
                                        studentnamefromserver.add(name);
                                        Log.e("class_id",obj.getString("class_id"));
                                        String class_id=obj.getString("class_id");
                                        studentclassidfromserver.add(class_id);
                                        Log.e("class_name",obj.getString("class_name"));
                                        String class_name=obj.getString("class_name");
                                        studentclassnamefromserver.add(class_name);
                                        Log.e("section_id id",obj.getString("section_id"));
                                        String section_id=obj.getString("section_id");
                                        studentsectionidfromserver.add(section_id);
                                        Log.e("section_name",obj.getString("section_name"));
                                        String section_name=obj.getString("section_name");
                                        studentsectionnamefromserver.add(section_name);
                                        Log.e("Father_Primary_Mobile",obj.getString("Father_Primary_Mobile"));
                                        String Father_Primary_Mobile=obj.getString("Father_Primary_Mobile");
                                        studentfathersnofromserver.add(Father_Primary_Mobile);
                                        Log.e("Mother_Primary_Mobile",obj.getString("Mother_Primary_Mobile"));
                                        String Mother_Primary_Mobile=obj.getString("Mother_Primary_Mobile");
                                        studentmothernumberfromserver.add(Mother_Primary_Mobile);
                                        globalcount++;

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
            progressDialog2.dismiss();
            if(globalnumberinlist>0){
                globalnumberinlist=0;
                if(globalcount==0){
                    setLayoutInvisible();
                    Toast.makeText(Student_List_Navigation.this, "No Student Exists",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    setLayoutVisible();
                    Log.e("inside else","#######################");
                    listgenerate();
                }
            }
            else {
                setLayoutInvisible();
                Toast.makeText(Student_List_Navigation.this, "No Student Exists",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    class getBusListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Student_List_Navigation.this, "Please wait.",
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
            progressDialog2.dismiss();
            fillbuslistspinner();
        }
    }

    public void listgenerate(){
        ArrayList<SearchResultsStudentList> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.studentlistselectedlist);
        myCustomBasedStudentListAdaper=new MyCustomBasedStudentListAdaper(this, searchResults);
        lv1.setAdapter(myCustomBasedStudentListAdaper);
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

    private ArrayList<SearchResultsStudentList> GetSearchResults(){
        ArrayList<SearchResultsStudentList> results = new ArrayList<SearchResultsStudentList>();
        for (int i=0;i<studentidfromserver.size();i++){
            SearchResultsStudentList sr1 = new SearchResultsStudentList();
            sr1.setSearchstudentname(studentnamefromserver.get(i));
            sr1.setSearchstudentclass(studentclassnamefromserver.get(i));
            sr1.setSearchstudentsection(studentsectionnamefromserver.get(i));
            String no;
            if(studentfathersnofromserver.get(i).isEmpty()||studentfathersnofromserver.get(i).equalsIgnoreCase("null")){
                no="--";
            }
            else{
                no=studentfathersnofromserver.get(i);
            }
            sr1.setSearchstudentphoneno(no);
            Log.e("inside list","#######################");
            results.add(sr1);
        }
        return results;
    }

    public void fillbuslistspinner(){

        buslistspinner=(Spinner) findViewById(R.id.studentlistspinneroption);
        String[] busitems=new String[busnamelistfromserver.size()+1];
        busitems[0]="Select Bus";
        for(int i=1;i<=busnamelistfromserver.size();i++){
            busitems[i]=busnamelistfromserver.get(i-1);
        }
        ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(Student_List_Navigation.this, android.R.layout.simple_spinner_item, busitems) {
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
                if(item.equalsIgnoreCase("Select bus")){
                    setLayoutInvisible();
                }
                else {
                    for(int i=0;i<busidlistfromserver.size();i++){
                        if(item.equalsIgnoreCase(busnamelistfromserver.get(i))){
                            globalbusid=Integer.parseInt(busidlistfromserver.get(i));
                        }
                    }
                    new getStudentsListFromServer().execute();
                }
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Student_List_Navigation.this);

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
                Intent intent = new Intent(Student_List_Navigation.this, Student_List_Navigation.class);
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
                Intent intent = new Intent(Student_List_Navigation.this, Student_List_Navigation.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog.show();
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

            LayoutInflater inflater = (LayoutInflater) Student_List_Navigation.this
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

            LayoutInflater inflater = (LayoutInflater) Student_List_Navigation.this
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
            Toast.makeText(Student_List_Navigation.this, getResources().getString(R.string.sj_please_select_bus),
                    Toast.LENGTH_LONG).show();
        }

        else
            new reassignbustoserver().execute();


    }


    class reassignbustoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Student_List_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                                            Toast.makeText(Student_List_Navigation.this, getResources().getString(R.string.sj_reassigned_successfully), Toast.LENGTH_SHORT).show();
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




    void change_language(String language){
        String languageToLoad  = language; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Student_List_Navigation.this.getResources().updateConfiguration(config,
                Student_List_Navigation.this.getResources().getDisplayMetrics());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Student_List_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student__list__navigation, menu);
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
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();
        }
        else if (id == R.id.reportsfour) {
            Intent intent = new Intent(this, Reports_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.logout) {
            db.delete("OneTimeLogi" +
                    "n", null, null);
            Intent intent = new Intent(this, LoginAdmin.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MyCustomBasedStudentListAdaper extends BaseAdapter {
        private ArrayList<SearchResultsStudentList> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedStudentListAdaper(Context context, ArrayList<SearchResultsStudentList> results) {
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
                Log.e("inside adaptor","#######################1");
                convertView = mInflater.inflate(R.layout.custom_row_view_student_list, null);
                holder = new ViewHolder();
                Log.e("inside adaptor","#######################2");
                holder.txtstudentname = (TextView) convertView.findViewById(R.id.customstudentlistname);
                holder.txtstudentname.setTypeface(tfRobo);
                holder.txtstudentclass = (TextView) convertView.findViewById(R.id.customstudentlistclass);
                holder.txtstudentclass.setTypeface(tfRobo);
                holder.txtstudentsetion = (TextView) convertView.findViewById(R.id.customstudentlistsection);
                holder.txtstudentsetion.setTypeface(tfRobo);
                holder.txtstudentmobileno = (TextView) convertView.findViewById(R.id.customstudentlistmobileno);
                holder.txtstudentmobileno.setTypeface(tfRobo);
                /*holder.txtstatus = (TextView) convertView.findViewById(R.id.customleaveonestatus);
                holder.txtstatus.setTypeface(tfRobo);*/

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtstudentname.setText(searchArrayList.get(position).getSearchstudentname());
            holder.txtstudentclass.setText(searchArrayList.get(position).getSearchstudentclass());
            holder.txtstudentsetion.setText(searchArrayList.get(position).getSearchstudentsection());
            holder.txtstudentmobileno.setText(searchArrayList.get(position).getSearchstudentphoneno());
            //holder.txtstatus.setText(searchArrayList.get(position).getLeaveoneStatus());
            return convertView;
        }

        class ViewHolder {
            TextView txtstudentname;
            TextView txtstudentclass;
            TextView txtstudentsetion;
            TextView txtstudentmobileno;
            Spinner txtstatus;
        }
    }
}
