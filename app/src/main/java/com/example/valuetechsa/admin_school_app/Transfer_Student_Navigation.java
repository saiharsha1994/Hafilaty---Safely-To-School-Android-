package com.example.valuetechsa.admin_school_app;

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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
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

public class Transfer_Student_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> routeidfromserver=new ArrayList<String>();
    ArrayList<String> routenamefromserver=new ArrayList<String>();
    ArrayList<String> StudentStopName=new ArrayList<String>();
    ArrayList<String> StudentLatitude=new ArrayList<String>();
    ArrayList<String> StudentLongitude=new ArrayList<String>();
    ArrayList<String> StudentLatitudeLongitude=new ArrayList<String>();
    ArrayList<String> StudentAssignTo=new ArrayList<String>();
    ArrayList<String> StudentAssignToId=new ArrayList<String>();
    ArrayList<String> StudentAssignToType=new ArrayList<String>();
    ArrayList<String> StudentContactNoList=new ArrayList<String>();
    ArrayList<String> StudentStopTiming=new ArrayList<String>();
    ArrayList<String> SelectedStudentName=new ArrayList<String>();
    ArrayList<String> SelectedStopTiming=new ArrayList<String>();
    ArrayList<String> SelectedLatitude=new ArrayList<String>();
    ArrayList<String> SelectedLongitude=new ArrayList<String>();
    ArrayList<String> SelectedNumaricalOrder=new ArrayList<String>();
    ArrayList<String> SelectedRouteId=new ArrayList<String>();
    ArrayList<String> SelectedAssignedTo=new ArrayList<String>();
    ArrayList<String> RouteDriverId=new ArrayList<String>();
    ArrayList<String> RouteBusId=new ArrayList<String>();
    ArrayList<String> StudentTOStopName=new ArrayList<String>();
    ArrayList<String> StudentTOLatitude=new ArrayList<String>();
    ArrayList<String> StudentTOLongitude=new ArrayList<String>();
    ArrayList<String> StudentTOLatitudeLongitude=new ArrayList<String>();
    ArrayList<String> StudentTOAssignTo=new ArrayList<String>();
    ArrayList<String> StudentTOAssignToId=new ArrayList<String>();
    ArrayList<String> StudentTOAssignToType=new ArrayList<String>();
    ArrayList<String> StudentTOContactNoList=new ArrayList<String>();
    ArrayList<String> StudentTOStopTiming=new ArrayList<String>();
    RelativeLayout tv,liststuednts,buttontransfer;
    ProgressDialog progressDialogtransfer;
    String response="",url="";
    Jsonfunctions sh;
    ProgressDialog progressDialog;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    int globalselectedStudentCount=0;
    int[] arrayroutechecklist=new int[100000];
    Spinner routeFromSpinner,routeToSpinner,studentTeacherTransferSpinner;
    String routeFromselected="",routeToselected="",routeFromIdselected="",routeToIdselected="",studentTeacherTransferSelected="",studentTeacherTransferIdSelected="";
     int count=0;
     int globalcount=0;
    String driveridcheck,busidcheck;
    int busanddriverid;
    Boolean isScrolling;
    ListView lv1;
    Typeface tfRobo,tfAdam;
    MyCustomBaseTransferAdapter myCustomBaseTransferAdapter;

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
        setContentView(R.layout.activity_transfer__student__navigation);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.transferheader);
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

        TextView TextViewNewFont = new TextView(Transfer_Student_Navigation.this);
        TextViewNewFont.setText("Transfer");
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Transfer_Student_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Transfer_Student_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);

        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
       /* action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp;Transfer</big></font>"));*/

        tv = (RelativeLayout) findViewById(R.id.transferstudentmainview);
        liststuednts=(RelativeLayout) findViewById(R.id.listofstudents);
        buttontransfer=(RelativeLayout) findViewById(R.id.buttontransfer);
        setLayoutInvisible();
        changefont();
        new getRoutesFromServer().execute();
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
        if (liststuednts.getVisibility() == View.VISIBLE) {
            liststuednts.setVisibility(View.GONE);
        }
        if (buttontransfer.getVisibility() == View.VISIBLE) {
            buttontransfer.setVisibility(View.GONE);
        }
    }
    public void setLayoutVisible() {
        if (tv.getVisibility() == View.GONE) {
            tv.setVisibility(View.VISIBLE);
        }
        if (liststuednts.getVisibility() == View.GONE) {
            liststuednts.setVisibility(View.VISIBLE);
        }
        if (buttontransfer.getVisibility() == View.GONE) {
            buttontransfer.setVisibility(View.VISIBLE);
        }
    }

    public void changefont(){
        TextView selectcr=(TextView)findViewById(R.id.selectcriteria);
        TextView trfrr=(TextView)findViewById(R.id.transferfromroute);
        TextView trtto=(TextView)findViewById(R.id.transfertoroute);
        TextView teastu=(TextView)findViewById(R.id.selectdate);
        Button selec=(Button)findViewById(R.id.managetransfer);
        Button selec2=(Button)findViewById(R.id.sendmessage);
        TextView managetr=(TextView)findViewById(R.id.transfermanage);
        TextView typ=(TextView)findViewById(R.id.typetextbox);
        TextView nam=(TextView)findViewById(R.id.studentidtextbox);
        TextView idd=(TextView)findViewById(R.id.idtextbox);
        TextView locc=(TextView)findViewById(R.id.locationtextbox);
        TextView cond=(TextView)findViewById(R.id.contactdetailstextbox);
        selectcr.setTypeface(tfRobo);
        trfrr.setTypeface(tfRobo);
        trtto.setTypeface(tfRobo);
        teastu.setTypeface(tfRobo);
        selec.setTypeface(tfAdam);
        managetr.setTypeface(tfRobo);
        typ.setTypeface(tfRobo);
        nam.setTypeface(tfRobo);
        idd.setTypeface(tfRobo);
        locc.setTypeface(tfRobo);
        cond.setTypeface(tfRobo);
        selec2.setTypeface(tfAdam);



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
                                    routeidfromserver.add(id);
                                    Log.e("++++++++++++++",id);
                                    String nameroute=obj.getString("route_name");
                                    Log.e("++++++++++++++",nameroute);
                                    routenamefromserver.add(nameroute);
                                    String driverid=obj.getString("driver_id");
                                    Log.e("driver Id",driverid);
                                    RouteDriverId.add(driverid);
                                    String busid=obj.getString("bus_id");
                                    Log.e("bus id",busid);
                                    RouteBusId.add(busid);

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

            getDropDown();
        }
    }


    public void getDropDown(){
        routeFromSpinner=(Spinner)findViewById(R.id.routefromspinner);
        routeToSpinner=(Spinner)findViewById(R.id.routetospinner);
        studentTeacherTransferSpinner=(Spinner)findViewById(R.id.teacherstudenttransferspinner);
        final String[] routefrom=new String[routenamefromserver.size()+1];
        routefrom[0]="Select Route";
        for(int i=1;i<=routenamefromserver.size();i++){
            routefrom[i]=routenamefromserver.get(i-1);
        }
        String[] routeto=new String[routenamefromserver.size()+1];
        routeto[0]="Select Route";
        for(int i=1;i<=routenamefromserver.size();i++){
            routeto[i]=routenamefromserver.get(i-1);
        }

        String[] studenttechertransfer=new String[3];
        studenttechertransfer[0]="Select";
        studenttechertransfer[1]="Student";
        studenttechertransfer[2]="Teacher";

        ArrayAdapter<String> adapterroutefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routefrom) {
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
        /*ArrayAdapter<String> adapterroutefrom = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, routefrom);*/
        adapterroutefrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterrouteto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routeto) {
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
        /*ArrayAdapter<String> adapterrouteto = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, routeto);*/
        adapterrouteto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterstudentteachertransfer = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, studenttechertransfer) {
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
        /*ArrayAdapter<String> adapterstudentteachertransfer = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, studenttechertransfer);*/
        adapterstudentteachertransfer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        routeFromSpinner.setAdapter(adapterroutefrom);
        routeToSpinner.setAdapter(adapterrouteto);
        studentTeacherTransferSpinner.setAdapter(adapterstudentteachertransfer);

        routeFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routedrivernamenavigation=item;
                routeFromselected=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        routeToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routebusnonavigation=item;
                routeToselected=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        studentTeacherTransferSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routedrivernamenavigation=item;
                studentTeacherTransferSelected=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void selectedRoute(View view){

        if(routeFromselected.equals("Select Route")){
            Toast.makeText(Transfer_Student_Navigation.this, "Please Select FROM Route",
                    Toast.LENGTH_LONG).show();
        }
        else if(routeToselected.equals("Select Route")){
            Toast.makeText(Transfer_Student_Navigation.this, "Please Select TO Route",
                    Toast.LENGTH_LONG).show();
        }
        else if(studentTeacherTransferSelected.equals("Select")){
            Toast.makeText(Transfer_Student_Navigation.this, "Please Select Student Or Teacher",
                    Toast.LENGTH_LONG).show();
        }
        else{
            for(int i=0;i<routenamefromserver.size();i++){
                if(routeFromselected.equals(routenamefromserver.get(i))){
                    routeFromIdselected=routeidfromserver.get(i);
                }
            }
            for(int j=0;j<routenamefromserver.size();j++){
                if(routeToselected.equals(routenamefromserver.get(j))){
                    routeToIdselected=routeidfromserver.get(j);
                }
            }
            if(studentTeacherTransferSelected.equals("Student")){
                studentTeacherTransferIdSelected="1";
            }
            if(studentTeacherTransferIdSelected.equals("Teacher")){
                studentTeacherTransferIdSelected="2";
            }
            Log.e("&&&&&&&&&&&&&&&&",routeFromIdselected);
            Log.e("&&&&&&&&&&&&&&&&",routeToIdselected);
            Log.e("&&&&&&&&&&&&&&&&",studentTeacherTransferIdSelected);
            if(routeFromIdselected.equals(routeToIdselected)){
                Toast.makeText(Transfer_Student_Navigation.this, "Two Routes Cannot Be Same",
                        Toast.LENGTH_LONG).show();
            }
            else{
                CheckBox checkBoxmain = (CheckBox)findViewById(R.id.radiobuttunmaintransfer);
                checkBoxmain.setChecked(false);
                StudentStopName.clear();
                StudentLatitude.clear();
                StudentLongitude.clear();
                StudentLatitudeLongitude.clear();
                StudentAssignTo.clear();
                StudentAssignToId.clear();
                StudentAssignToType.clear();
                StudentStopTiming.clear();
                StudentContactNoList.clear();
                count=0;
                new getStudentsFromServer().execute();
            }
        }
    }

    class getStudentsFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(Transfer_Student_Navigation.this, "Please wait.",
                    "Finding!", true);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"GetRouteDetails_api/StopsListByRoute/route_id/"+routeFromIdselected);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"GetRouteDetails_api/StopsListByRoute/route_id/"+routeFromIdselected,Jsonfunctions.GET);

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
                                    if(studentTeacherTransferSelected.equalsIgnoreCase(assignto.substring(2,assignto.length()))){
                                        Log.e("@@@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                                        StudentStopName.add(Stopnames);
                                        StudentLatitude.add(lati);
                                        StudentLongitude.add(longi);
                                        StudentLatitudeLongitude.add(latilongi);
                                        StudentAssignTo.add(assignto);
                                        StudentAssignToId.add(assignto.substring(0,1));
                                        StudentAssignToType.add(assignto.substring(2,assignto.length()));
                                        StudentStopTiming.add(stoptiming);
                                        StudentContactNoList.add(contactno);
                                        count=count+1;
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
            if(count>0){
                setLayoutVisible();
            }
            else{
                Toast.makeText(Transfer_Student_Navigation.this, "No Students or Teachers Exists",
                        Toast.LENGTH_LONG).show();
                setLayoutInvisible();
            }

            globalcount=count;
            for(int ii=0;ii<globalcount;ii++){
                arrayroutechecklist[ii]=0;

            }
            liststudentgenerate();
            progressDialog.dismiss();
            //listgenerate();

        }
    }
    public void liststudentgenerate(){
        ArrayList<SearchResultTransfer> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.transferlist);
        myCustomBaseTransferAdapter=new MyCustomBaseTransferAdapter(this, searchResults);
        lv1.setAdapter(myCustomBaseTransferAdapter);
        //lv1.setItemsCanFocus(false);
        //lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
                    if(arrayroutechecklist[pos]==0){
                        arrayroutechecklist[pos]=1;
                    }
                    else{
                        arrayroutechecklist[pos]=0;
                    }
                    CheckBox checkBox = (CheckBox)view.findViewById(R.id.checkboxrowtransfer);
                    checkBox.setChecked(!checkBox.isChecked());
                    CheckBox checkBoxmain = (CheckBox)findViewById(R.id.radiobuttunmaintransfer);
                    checkBoxmain.setChecked(false);
                }

                    //checkBox.setChecked(!checkBox.isChecked());

                //Here pos is the position of row clicked

            }
        });
    }

    public void checkallmainmethod(View view){
        Log.e("))))",")))"+lv1.getCount());
        int counttrueorfalse=0;
        for(int tt=0;tt<globalcount;tt++){
            if(arrayroutechecklist[tt]==1){
                counttrueorfalse=counttrueorfalse+1;
            }
        }

        if (counttrueorfalse == globalcount) {
            for(int i=0; i<=lv1.getLastVisiblePosition() -lv1.getFirstVisiblePosition();i++)
            {
                View view1=lv1.getChildAt(i);
                CheckBox cb;
                cb = (CheckBox)view1.findViewById(R.id.checkboxrowtransfer);
                cb.setChecked(false);
            }
            for(int i=0; i<globalcount;i++)
            {
                View view1=myCustomBaseTransferAdapter.getView(i, null, lv1);
                CheckBox cb;
                cb = (CheckBox)view1.findViewById(R.id.checkboxrowtransfer);
                cb.setChecked(false);
            }
            for(int tt=0;tt<globalcount;tt++){
                arrayroutechecklist[tt]=0;
            }
            CheckBox checkBoxmain = (CheckBox)findViewById(R.id.radiobuttunmaintransfer);
            checkBoxmain.setChecked(false);
        }
        else{
            for(int i=0; i<=lv1.getLastVisiblePosition() -lv1.getFirstVisiblePosition();i++)
            {
                View view1=lv1.getChildAt(i);
                CheckBox cb;
                cb = (CheckBox)view1.findViewById(R.id.checkboxrowtransfer);
                cb.setChecked(true);
            }
            for(int i=0; i<globalcount;i++)
            {
                View view1=myCustomBaseTransferAdapter.getView(i, null, lv1);
                CheckBox cb;
                cb = (CheckBox)view1.findViewById(R.id.checkboxrowtransfer);
                cb.setChecked(true);
            }
            for(int tt=0;tt<globalcount;tt++){
                arrayroutechecklist[tt]=1;
            }

        }
    }

    public void radiobuttonclicktransfer(View view){
        int ii=(Integer) view.getTag();
        Log.e("((((((","(((((((((((((((((((((("+ii);
        if(arrayroutechecklist[ii]==0){
            arrayroutechecklist[ii]=1;
        }
        else{
            arrayroutechecklist[ii]=0;
        }
    }


    private ArrayList<SearchResultTransfer> GetSearchResults(){
        ArrayList<SearchResultTransfer> results = new ArrayList<SearchResultTransfer>();


        for(int reg=0;reg<count;reg++){
            SearchResultTransfer sr1 = new SearchResultTransfer();
            sr1.setType(StudentAssignToType.get(reg));
            sr1.setName(StudentStopName.get(reg));
            sr1.setId(StudentAssignToId.get(reg));
            sr1.setLocation(StudentLatitudeLongitude.get(reg));
            sr1.setContact(StudentContactNoList.get(reg));
            results.add(sr1);
        }
        count=0;
        return results;
    }

    public void transferstudentmethod(View view){
        new getTOStudentsFromServer().execute();
    }

    class getTOStudentsFromServer extends AsyncTask<Void,Void,Void>{
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
                    Log.e("url", Config.ip+"GetRouteDetails_api/StopsListByRoute/route_id/"+routeToIdselected);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"GetRouteDetails_api/StopsListByRoute/route_id/"+routeToIdselected,Jsonfunctions.GET);

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
                                    StudentTOStopName.add(Stopnames);
                                    String lati=obj.getString("latitude");
                                    StudentTOLatitude.add(lati);
                                    String longi=obj.getString("langitude");
                                    StudentTOLongitude.add(longi);
                                    String latilongi=lati+","+longi;
                                    Log.e("latitudelongitude",latilongi);
                                    StudentTOLatitudeLongitude.add(latilongi);
                                    String assignto=obj.getString("assigned_to");
                                    Log.e("assign to",obj.getString("assigned_to"));
                                    StudentTOAssignTo.add(assignto);
                                    StudentTOAssignToId.add(assignto.substring(0,1));
                                    StudentTOAssignToType.add(assignto.substring(2,assignto.length()));
                                    Log.e("id",assignto.substring(0,1));
                                    Log.e("type",assignto.substring(2,assignto.length()));
                                    String contactno=obj.getString("contact_num");
                                    Log.e("contact no",obj.getString("contact_num"));
                                    String stoptiming=obj.getString("stope_time");
                                    Log.e("stop timing",stoptiming);
                                    StudentTOStopTiming.add(stoptiming);
                                    StudentTOContactNoList.add(contactno);
                                    count=count+1;
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
            transferStudent2method();
            //listgenerate();

        }
    }

    public void transferStudent2method(){
        for(int i=0;i<globalcount;i++){
            Log.e("array value",arrayroutechecklist[i]+"");
        }
        for(int i=0;i<routeidfromserver.size();i++){
            if(routeToIdselected.equals(routeidfromserver.get(i))){
                driveridcheck=RouteDriverId.get(i);
                busidcheck=RouteBusId.get(i);
                Log.e("driver id to",driveridcheck);
                Log.e("Bus id to",busidcheck);
            }
        }
        int j=0;
        for(int i=0;i<globalcount;i++){
            if(arrayroutechecklist[i]==1){
                SelectedStudentName.add(StudentStopName.get(i));
                /*Log.e("Student++++",StudentStopName.get(0));
                Log.e("Student++++",StudentStopName.get(1));
                Log.e("Student++++",StudentStopName.get(2))*/;
                SelectedLatitude.add(StudentLatitude.get(i));
                SelectedLongitude.add(StudentLongitude.get(i));
                SelectedAssignedTo.add(StudentAssignTo.get(i));
                SelectedNumaricalOrder.add(j+"");
                SelectedStopTiming.add(StudentStopTiming.get(i));
                SelectedRouteId.add(routeToIdselected);
                j=j+1;
                globalselectedStudentCount=globalselectedStudentCount+1;
            }
        }
        for(int ii=0;ii<StudentTOStopName.size();ii++){
            SelectedStudentName.add(StudentTOStopName.get(ii));
            SelectedLatitude.add(StudentTOLatitude.get(ii));
            SelectedLongitude.add(StudentTOLongitude.get(ii));
            SelectedAssignedTo.add(StudentTOAssignTo.get(ii));
            SelectedNumaricalOrder.add(ii+"");
            SelectedStopTiming.add(StudentTOStopTiming.get(ii));
            SelectedRouteId.add(routeToIdselected);
            j=j+1;
            globalselectedStudentCount=globalselectedStudentCount+1;
        }
        new addstopstoservertransfer().execute();
    }

    class addstopstoservertransfer extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){
            progressDialogtransfer = ProgressDialog.show(Transfer_Student_Navigation.this, "Please wait.",
                    "Fetching Information!", true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

               /* Log.e("before apply",routenamenavigation);
                Log.e("before apply",routetypenavigation);
                Log.e("before apply",routestarttymnavgation);
                Log.e("before apply",routeendtymnavigation);
                Log.e("before apply",routedrivernamenavigation);
                Log.e("before apply",routebusnonavigation);*/

                Log.e("___________","__________________________entered");
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


                //Log.e("size of route name",routenamefromserver.size()+"");
                int counti=globalselectedStudentCount;
                while(counti>0){
                    Stop_Name.append(SelectedStudentName.get(counti-1));
                    Stop_Name.append(",");
                    Stop_Latitude.append(SelectedLatitude.get(counti-1));
                    Stop_Latitude.append(",");
                    Stop_Longitude.append(SelectedLongitude.get(counti-1));
                    Stop_Longitude.append(",");
                    Stop_NumericOrder.append(counti);
                    Stop_NumericOrder.append(",");
                    Stop_RouteId.append(SelectedRouteId.get(counti-1));
                    Stop_RouteId.append(",");
                    Stop_Timing.append(SelectedStopTiming.get(counti-1));
                    Stop_Timing.append(",");
                    Stop_StudentId.append(SelectedAssignedTo.get(counti-1));
                    Stop_StudentId.append(",");
                    counti=counti-1;
                }

                String strStop_Name="",strStop_Latitude="",strStop_Longitude="",strStop_Timing="",strStop_StudentId="",strStop_Driverid="",strStop_Busid="",strStop_NumericOrder="",strStop_RouteId="";

                strStop_Name=Stop_Name.substring(0,Stop_Name.length()-1);
                strStop_Latitude=Stop_Latitude.substring(0,Stop_Latitude.length()-1);
                strStop_Longitude=Stop_Longitude.substring(0,Stop_Longitude.length()-1);
                strStop_NumericOrder=Stop_NumericOrder.substring(0,Stop_NumericOrder.length()-1);
                strStop_RouteId=Stop_RouteId.substring(0,Stop_RouteId.length()-1);
                strStop_Driverid=driveridcheck;
                strStop_Busid=busidcheck;
                strStop_Timing=Stop_Timing.substring(0,Stop_Timing.length()-1);
                strStop_StudentId=Stop_StudentId.substring(0,Stop_StudentId.length()-1);


                Log.e("after apply",strStop_Name);
                Log.e("after apply",strStop_Latitude);
                Log.e("after apply",strStop_Longitude);

                sh = new Jsonfunctions();

                url= Config.ip+"GetRouteDetails_api/createRouteStops" +
                        "/stop_name/"+ URLEncoder.encode(strStop_Name,"UTF-8")+"/stop_time/"+URLEncoder.encode(strStop_Timing,"UTF-8")+"/latitude/"+URLEncoder.encode(strStop_Latitude,"UTF-8")+
                        "/langitude/"+URLEncoder.encode(strStop_Longitude,"UTF-8")+"/numeric_order/"+URLEncoder.encode(strStop_NumericOrder,"UTF-8")+
                        "/route_id/"+URLEncoder.encode(strStop_RouteId,"UTF-8")+"/assigned_to/"+URLEncoder.encode(strStop_StudentId,"UTF-8")+"/driver_id/"
                        +URLEncoder.encode(strStop_Driverid,"UTF-8")+"/bus_id/"+URLEncoder.encode(strStop_Busid,"UTF-8")+"/for/transfer";
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
            progressDialogtransfer.dismiss();
            Toast.makeText(Transfer_Student_Navigation.this, "Transfer Completed",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Transfer_Student_Navigation.this, Student_Route_Creation_Navigation.class);
            startActivity(intent);
            finish();
        }
    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Transfer_Student_Navigation.this);

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
                Intent intent = new Intent(Transfer_Student_Navigation.this, Transfer_Student_Navigation.class);
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
                Intent intent = new Intent(Transfer_Student_Navigation.this, Transfer_Student_Navigation.class);
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
        Transfer_Student_Navigation.this.getResources().updateConfiguration(config,
                Transfer_Student_Navigation.this.getResources().getDisplayMetrics());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Transfer_Student_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transfer__student__navigation, menu);
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
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();

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
