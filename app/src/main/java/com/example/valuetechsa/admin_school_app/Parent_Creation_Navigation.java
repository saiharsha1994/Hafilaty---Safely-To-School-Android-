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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
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
import com.example.valuetechsa.admin_school_app.commonclass.ApiCrypter;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Parent_Creation_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Typeface tfRobo,tfAdam;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    ListView lv1;
    Jsonfunctions sh;
    String response="",url="";
    private PopupWindow pwindo;
    Boolean isScrolling;
    ProgressDialog progressDialog2=null;
    ArrayList<String> parentidfromserver=new ArrayList<String>();
    ArrayList<String> parentnamefromserver=new ArrayList<String>();
    ArrayList<String> parentemailfromserver=new ArrayList<String>();
    ArrayList<String> parentpasswordfromserver=new ArrayList<String>();
    ArrayList<String> parentphonefromserver=new ArrayList<String>();
    ArrayList<String> parentaddressfromserver=new ArrayList<String>();
    ArrayList<String> parentprofessionfromserver=new ArrayList<String>();
    ArrayList<String> parentchildrenfromserver=new ArrayList<String>();

    ProgressDialog dialog = null;

    String parentnameintoserver,parentemailintoserver,parentpasswordintoserver,parentphoneintoserver,parentprofessionintoserver,parentaddressintoserver;
    EditText parentnameedit,parentemailedit,parentpasswordedit,parentphoneedit,parentprofessionedit,parentaddressedit;

    static Button btnAddParent;
    int editparentid=0,deleteparentid=0,edit=0;
    MyCustomBasedParentsAdaper myCustomBasedParentsAdaper;
    ApiCrypter crypt;

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
        setContentView(R.layout.activity_parent_creation_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.menu, this.getTheme());

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.parentclickheader);
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

        TextView TextViewNewFont = new TextView(Parent_Creation_Navigation.this);
        TextViewNewFont.setText("Parents");
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Parent_Creation_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Parent_Creation_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);


        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
        crypt= new ApiCrypter();

        new getParentListFromServer().execute();
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Parent_Creation_Navigation.this);

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
                Intent intent = new Intent(Parent_Creation_Navigation.this, Bus_Creation_Navigation.class);
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
                Intent intent = new Intent(Parent_Creation_Navigation.this, Bus_Creation_Navigation.class);
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
        Parent_Creation_Navigation.this.getResources().updateConfiguration(config,
                Parent_Creation_Navigation.this.getResources().getDisplayMetrics());
    }

    class getParentListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Parent_Creation_Navigation.this, "Please wait.",
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
                    Log.e("url", Config.ip+"Parent_api/listParents");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Parent_api/listParents",Jsonfunctions.GET);

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
                                    String parentname=obj.getString("name");
                                    parentnamefromserver.add(parentname);
                                    Log.e("+++",obj.getString("parent_id"));
                                    String parentId=obj.getString("parent_id");
                                    parentidfromserver.add(parentId);
                                    Log.e("+++",obj.getString("email"));
                                    String email=obj.getString("email");
                                    parentemailfromserver.add(email);

                                    String password=obj.getString("password");
                                    Log.e("+++",password);
                                    String res = new String( crypt.decrypt( password ), "UTF-8" );
                                    res = URLDecoder.decode(res,"UTF-8");
                                    Log.e("+++",res);
                                    parentpasswordfromserver.add(res);
                                    Log.e("+++",obj.getString("phone"));
                                    String phone=obj.getString("phone");
                                    parentphonefromserver.add(phone);
                                    Log.e("+++",obj.getString("address"));
                                    String address=obj.getString("address");
                                    parentaddressfromserver.add(address);
                                    Log.e("+++",obj.getString("profession"));
                                    String profession=obj.getString("profession");
                                    parentprofessionfromserver.add(profession);
                                    Log.e("+++",obj.getString("children"));
                                    String children=obj.getString("children");
                                    if(children.equalsIgnoreCase("null")||children.isEmpty()){
                                        parentchildrenfromserver.add("--");
                                    }
                                    else
                                        parentchildrenfromserver.add(children);

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

    public void listgenerate(){
        ArrayList<SearchResultsParents> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.parentcreatedlist);
        myCustomBasedParentsAdaper=new MyCustomBasedParentsAdaper(this, searchResults);
        lv1.setAdapter(myCustomBasedParentsAdaper);
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


    private ArrayList<SearchResultsParents> GetSearchResults(){
        ArrayList<SearchResultsParents> results = new ArrayList<SearchResultsParents>();
        for (int i=0;i<parentidfromserver.size();i++){
            SearchResultsParents sr1 = new SearchResultsParents();
            sr1.setParentId(parentidfromserver.get(i));
            sr1.setParentName(parentnamefromserver.get(i));
            sr1.setParentEmail(parentemailfromserver.get(i));
            sr1.setParentAddress(parentaddressfromserver.get(i));
            sr1.setParentPassword(parentpasswordfromserver.get(i));
            sr1.setParentPhone(parentphonefromserver.get(i));
            sr1.setParentProfession(parentprofessionfromserver.get(i));
            sr1.setParentChildren(parentchildrenfromserver.get(i));
            results.add(sr1);
        }

        return results;
    }

    public void getpopup(View view){
        initiatePopupWindowParent();
    }

    private void initiatePopupWindowParent() {
        try {
            parentnameintoserver="null";
            parentemailintoserver="null";
            parentpasswordintoserver="null";
            parentphoneintoserver="null";
            parentprofessionintoserver="null";
            parentaddressintoserver="null";


            LayoutInflater inflater = (LayoutInflater) Parent_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_parent,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            parentnameedit=(EditText)layout.findViewById(R.id.parentnameedit);
            parentemailedit=(EditText)layout.findViewById(R.id.parentemailedit);
            parentpasswordedit=(EditText)layout.findViewById(R.id.parentpasswordedit);
            parentphoneedit=(EditText)layout.findViewById(R.id.parentphoneedit);
            parentprofessionedit=(EditText)layout.findViewById(R.id.parentprofessionedit);
            parentaddressedit=(EditText)layout.findViewById(R.id.parentaddressedit);

            btnAddParent=(Button)layout.findViewById(R.id.btn_add_popup_parent);
            btnAddParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit=0;
                    addParentMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_parent);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    Intent intent=getIntent();
                    finish();
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initiateEditPopupWindowParent(final int rownumber) {
        try {
            parentnameintoserver="null";
            parentemailintoserver="null";
            parentpasswordintoserver="null";
            parentphoneintoserver="null";
            parentprofessionintoserver="null";
            parentaddressintoserver="null";


            LayoutInflater inflater = (LayoutInflater) Parent_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_parent_edit,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


            parentnameedit=(EditText)layout.findViewById(R.id.parentnameedit);
            parentnameedit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

            parentemailedit=(EditText)layout.findViewById(R.id.parentemailedit);
            parentemailedit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

            parentpasswordedit=(EditText)layout.findViewById(R.id.parentpasswordedit);
            parentpasswordedit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

            parentphoneedit=(EditText)layout.findViewById(R.id.parentphoneedit);
            parentphoneedit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

            parentprofessionedit=(EditText)layout.findViewById(R.id.parentprofessionedit);
            parentprofessionedit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

            parentaddressedit=(EditText)layout.findViewById(R.id.parentaddressedit);
            parentaddressedit.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


            parentnameedit.setText(parentnamefromserver.get(rownumber));
            parentemailedit.setText(parentemailfromserver.get(rownumber));
            parentpasswordedit.setText(parentpasswordfromserver.get(rownumber));
            parentphoneedit.setText(parentphonefromserver.get(rownumber));
            parentprofessionedit.setText(parentprofessionfromserver.get(rownumber));
            parentaddressedit.setText(parentaddressfromserver.get(rownumber));
            editparentid=Integer.parseInt(parentidfromserver.get(rownumber));

            btnAddParent=(Button)layout.findViewById(R.id.btn_update_popup_parent);
            btnAddParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit=1;
                    addParentMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_parent);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    Intent intent=getIntent();
                    finish();
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addParentMethod(){
        parentnameintoserver=parentnameedit.getText().toString();
        parentemailintoserver=parentemailedit.getText().toString();
        String pass=parentpasswordedit.getText().toString();
        try {
             byte p[] = crypt.encrypt(pass);
            parentpasswordintoserver=crypt.bytesToHex(p);
        }
        catch (Exception e){

        }
        parentaddressintoserver=parentaddressedit.getText().toString();
        parentprofessionintoserver=parentprofessionedit.getText().toString();
        parentphoneintoserver=parentphoneedit.getText().toString();

        if(parentnameintoserver.isEmpty()){
            Toast.makeText(Parent_Creation_Navigation.this, "Please Enter The Parent Name",
                    Toast.LENGTH_LONG).show();
        }
        else if(parentemailintoserver.isEmpty()){
            Toast.makeText(Parent_Creation_Navigation.this, "Please Enter Parent Email",
                    Toast.LENGTH_LONG).show();
        }
        else if(parentpasswordintoserver.isEmpty()){
            Toast.makeText(Parent_Creation_Navigation.this, "Please Enter Parent Password",
                    Toast.LENGTH_LONG).show();
        }
        else if(parentaddressintoserver.isEmpty()){
            Toast.makeText(Parent_Creation_Navigation.this, "Please Enter Parent Address",
                    Toast.LENGTH_LONG).show();
        }
        else if(parentprofessionintoserver.isEmpty()){
            Toast.makeText(Parent_Creation_Navigation.this, "Please Enter Parent Profession",
                    Toast.LENGTH_LONG).show();
        }
        else if(parentphoneintoserver.isEmpty()){
            Toast.makeText(Parent_Creation_Navigation.this, "Please Enter Parent Phone",
                    Toast.LENGTH_LONG).show();
        }
        else {
            if (edit == 0)
                new addparentdetailstoserver().execute();
            else
                new updateparentdetailstoserver().execute();

        }
    }


    class addparentdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Parent_Creation_Navigation.this, "Please wait.",
                    "Fetching Information!", true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"Parent_api/addParent/Name/"+ URLEncoder.encode(parentnameintoserver,"UTF-8")+"/Email/"+URLEncoder.encode(parentemailintoserver,"UTF-8")+
                        "/Password/"+URLEncoder.encode(parentpasswordintoserver,"UTF-8")+"/Phone/"+URLEncoder.encode(parentphoneintoserver,"UTF-8")+"/Profession/"+URLEncoder.encode(parentprofessionintoserver,"UTF-8")+
                        "/Address/"+URLEncoder.encode(parentaddressintoserver,"UTF-8");

                /*url= Config.ip+"GetRouteDetails_api/createRoute" +
                        "/route_name/"+ URLEncoder.encode(strRoute_name,"UTF-8")+"/trip_name/"+URLEncoder.encode(strRoute_Type,"UTF-8")+
                        "/start_time/"+URLEncoder.encode(strRoute_Starttime,"UTF-8")+"/end_time/"+URLEncoder.encode(strRoute_Endtime,"UTF-8");
                Log.e("url created",url);*/
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
                                if(response.equalsIgnoreCase("1")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Parent_Creation_Navigation.this, "Parent Added Successfully", Toast.LENGTH_SHORT).show();
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
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }


    class updateparentdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Parent_Creation_Navigation.this, "Please wait.",
                    "Fetching Information!", true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"Parent_api/editParent/Name/"+ URLEncoder.encode(parentnameintoserver,"UTF-8")+"/Email/"+URLEncoder.encode(parentemailintoserver,"UTF-8")+
                        "/Password/"+URLEncoder.encode(parentpasswordintoserver,"UTF-8")+"/Phone/"+URLEncoder.encode(parentphoneintoserver,"UTF-8")+"/Profession/"+URLEncoder.encode(parentprofessionintoserver,"UTF-8")+
                        "/Address/"+URLEncoder.encode(parentaddressintoserver,"UTF-8")+"/Parent_id/"+editparentid;

                /*url= Config.ip+"GetRouteDetails_api/createRoute" +
                        "/route_name/"+ URLEncoder.encode(strRoute_name,"UTF-8")+"/trip_name/"+URLEncoder.encode(strRoute_Type,"UTF-8")+
                        "/start_time/"+URLEncoder.encode(strRoute_Starttime,"UTF-8")+"/end_time/"+URLEncoder.encode(strRoute_Endtime,"UTF-8");
                Log.e("url created",url);*/
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
                                if(response.equalsIgnoreCase("1")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Parent_Creation_Navigation.this, "Parent Updated Successfully", Toast.LENGTH_SHORT).show();
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
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    class getParentDeleteServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Parent_Creation_Navigation.this, "Please wait.",
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
                    Log.e("url", Config.ip+"Parent_api/deleteParent/Parent_id/"+deleteparentid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Parent_api/deleteParent/Parent_id/"+deleteparentid,Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                //JSONArray jsonArray = Jobj.getJSONArray("result_arr");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Parent_Creation_Navigation.this, "Parent Deleted Successfully", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.bus__creation__navigation, menu);
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
        else if (id == R.id.buscreationclick) {
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();
        }
        else if (id == R.id.contractcreation) {
            Intent intent = new Intent(this, Contract_Creation_Navigation.class);
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


    public class MyCustomBasedParentsAdaper extends BaseAdapter {
        private ArrayList<SearchResultsParents> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedParentsAdaper(Context context, ArrayList<SearchResultsParents> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_parent_creation, null);
                holder = new ViewHolder();
                holder.txtparentname = (TextView) convertView.findViewById(R.id.parentnametext);
                holder.txtparentname.setTypeface(tfRobo);
                holder.txtparentemail = (TextView) convertView.findViewById(R.id.parentemailtext);
                holder.txtparentemail.setTypeface(tfRobo);
                holder.txtparentchildren = (TextView) convertView.findViewById(R.id.parentchildrentext);
                holder.txtparentchildren.setTypeface(tfRobo);

                holder.txtparentoptions=(Spinner)convertView.findViewById(R.id.parentoption);

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
                holder.txtparentoptions.setAdapter(adapterdriverdropown);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtparentoptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    String item = parent.getItemAtPosition(position1).toString();
                    //routedrivernamenavigation=item;
                    //routeattendenceselected=item;
                    Log.e("spinner selected",item);
                    if(item.equalsIgnoreCase("Action")){

                    }
                    else if(item.equalsIgnoreCase("Edit")){
                        initiateEditPopupWindowParent(position);
                    }
                    if(item.equalsIgnoreCase("Delete")){
                        deleteparentid=Integer.parseInt(parentidfromserver.get(position));
                        new getParentDeleteServer().execute();
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtparentname.setText(searchArrayList.get(position).getParentName());
            holder.txtparentemail.setText(searchArrayList.get(position).getParentEmail());
            String c= searchArrayList.get(position).getParentChildren();
            String parts[]=c.split("\\|");
            String children="";
            for(int i=0;i<parts.length;i++)
            {
                children=children+parts[i];
                if(!(i ==parts.length-1))
                    children=children+"\n";
            }
            holder.txtparentchildren.setText(children);
            Log.e("children",children);
            return convertView;
        }

        class ViewHolder {
            TextView txtparentname;
            TextView txtparentemail;
            TextView txtparentphone;
            TextView txtparentchildren;
            Spinner txtparentoptions;
        }
    }

}
