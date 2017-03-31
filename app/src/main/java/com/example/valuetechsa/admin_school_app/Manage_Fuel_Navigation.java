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
import android.widget.FrameLayout;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Manage_Fuel_Navigation extends AppCompatActivity
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
    private static final int REQUEST_PATH = 1;
    ArrayList<String> idfromserver=new ArrayList<String>();
    ArrayList<String> datefromserver=new ArrayList<String>();
    ArrayList<String> driveridfromserver=new ArrayList<String>();
    ArrayList<String> drivernamefromserver=new ArrayList<String>();
    ArrayList<String> amountfromserver=new ArrayList<String>();
    ArrayList<String> amountspentfromserver=new ArrayList<String>();
    ArrayList<String> invoicefromserver=new ArrayList<String>();
    ArrayList<String> balancefromserver=new ArrayList<String>();
    ArrayList<String> drivernamefromserverfordropdown=new ArrayList<String>();
    ArrayList<String> driveridfromserverfordropdown=new ArrayList<String>();

    static String dateusergiven="empty";
    static String dateprintgiven="empty";

    int serverResponseCode = 0;
    CommonClass cc;

    ProgressDialog dialog = null;

    String dateintoserver,driveridintoserver,amountgivenintoserver, drivernameintoserver,drivernameforadd,driveridforadd,amountspentintoserver,balanceintoserver,invoiceintoserver;
    static EditText dateedit,amountedit,amountgivenedit,amountspentedit;
    Spinner drivernamespinner;

    static Button btnAddRecord,btnUpdateRecord,btnUploadInvoice;
    int editfuelid=0,deletefuelid=0;
    static TextView txtUploadInvoice;
    FrameLayout layout_MainMenu;

    MyCustomBasedFuelAdaper myCustomBasedFuelAdaper;
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
        setContentView(R.layout.activity_manage_fuel_navigation);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.managefuelheader);
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

        TextView TextViewNewFont = new TextView(Manage_Fuel_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_manage_fuel));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Manage_Fuel_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Manage_Fuel_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);


        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
        changeFont();
        new getFuelListFromServer().execute();
    }

    public void changeFont(){
        TextView paymentlist=(TextView)findViewById(R.id.paymentlisttextbox);
        TextView date=(TextView)findViewById(R.id.fueldatetextbox);
        TextView drivername=(TextView)findViewById(R.id.drivernametextbox2);
        TextView amount=(TextView)findViewById(R.id.amounttextbox);
        TextView spent=(TextView)findViewById(R.id.amountspenttextbox);
        TextView ballance=(TextView)findViewById(R.id.balancetextbox);
        TextView options=(TextView)findViewById(R.id.editpaymenttextbox);
        Button addpayment=(Button)findViewById(R.id.addpayment);

        paymentlist.setTypeface(tfRobo);
        date.setTypeface(tfRobo);
        drivername.setTypeface(tfRobo);
        amount.setTypeface(tfRobo);
        spent.setTypeface(tfRobo);
        ballance.setTypeface(tfRobo);
        options.setTypeface(tfRobo);
        addpayment.setTypeface(tfAdam);
    }

    class getFuelListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"PettyCash_api/listPettycash/for/fuel");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"PettyCash_api/listPettycash/for/fuel",Jsonfunctions.GET);

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
                                    Log.e("+++",obj.getString("id"));
                                    String id=obj.getString("id");
                                    idfromserver.add(id);
                                    Log.e("+++",obj.getString("date"));
                                    String date=obj.getString("date");
                                    datefromserver.add(date);
                                    Log.e("+++",obj.getString("driver_id"));
                                    String driverid=obj.getString("driver_id");
                                    driveridfromserver.add(driverid);
                                    Log.e("+++",obj.getString("driver_name"));
                                    String drivername=obj.getString("driver_name");
                                    drivernamefromserver.add(drivername);
                                    Log.e("+++",obj.getString("amount_given"));
                                    String amount=obj.getString("amount_given");
                                    amountfromserver.add(amount);
                                    Log.e("+++",obj.getString("amount_spend"));
                                    String amountspent=obj.getString("amount_spend");
                                    if(amountspent.equalsIgnoreCase("null")||amountspent.isEmpty()){
                                        amountspentfromserver.add("--");
                                    }
                                    else
                                        amountspentfromserver.add(amountspent);
                                    Log.e("+++",obj.getString("balance"));
                                    String balance=obj.getString("balance");
                                    if(balance.equalsIgnoreCase("null")||balance.isEmpty()){
                                        balancefromserver.add("--");
                                    }
                                    else
                                        balancefromserver.add(balance);
                                    Log.e("+++",obj.getString("invoice_doc"));
                                    String invoice=obj.getString("invoice_doc");
                                    if(balance.equalsIgnoreCase("null")||balance.isEmpty()){
                                        invoicefromserver.add("--");
                                    }
                                    else
                                        invoicefromserver.add(invoice);




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
            new getDriversFromServer().execute();
            progressDialog2.dismiss();
        }
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Manage_Fuel_Navigation.this);

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
                Intent intent = new Intent(Manage_Fuel_Navigation.this, Manage_Fuel_Navigation.class);
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
                Intent intent = new Intent(Manage_Fuel_Navigation.this, Manage_Fuel_Navigation.class);
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
        Manage_Fuel_Navigation.this.getResources().updateConfiguration(config,
                Manage_Fuel_Navigation.this.getResources().getDisplayMetrics());
    }
    public void listgenerate(){
        ArrayList<SearchResultsFuel> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.paymentcreatedlist);
        myCustomBasedFuelAdaper=new MyCustomBasedFuelAdaper(this, searchResults);
        lv1.setAdapter(myCustomBasedFuelAdaper);
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

            dateedit.setText(dateusergiven);

        }
    }

    private ArrayList<SearchResultsFuel> GetSearchResults(){
        ArrayList<SearchResultsFuel> results = new ArrayList<SearchResultsFuel>();
        for (int i=0;i<idfromserver.size();i++){
            SearchResultsFuel sr1 = new SearchResultsFuel();

            sr1.setId(idfromserver.get(i));
            sr1.setdate(datefromserver.get(i));
            sr1.setdriverId(driveridfromserver.get(i));
            sr1.setdriverName(drivernamefromserver.get(i));
            sr1.setamount(amountfromserver.get(i));
            sr1.setamountSpent(amountspentfromserver.get(i));
            sr1.setbalance(balancefromserver.get(i));
            results.add(sr1);
        }

        return results;
    }

    public void getpopup(View view){
        initiatePopupWindowAdd();
    }

    private void initiatePopupWindowAdd() {
        try {
            dateintoserver="null";
            driveridintoserver="null";
            amountgivenintoserver="null";
            drivernameintoserver="null";


            LayoutInflater inflater = (LayoutInflater) Manage_Fuel_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_fuel,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            layout_MainMenu.getForeground().setAlpha( 200);

            dateedit=(EditText)layout.findViewById(R.id.fueldateedit);
            drivernamespinner=(Spinner)layout.findViewById(R.id.selectdrivernamespinner);
            amountedit=(EditText)layout.findViewById(R.id.amountedit);

            dateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTruitonDatePickerDialog(v);
                }
            });


            if(drivernamefromserverfordropdown.isEmpty())
                new getDriversFromServer().execute();

            final String[] drivernamefrom=new String[drivernamefromserverfordropdown.size()+1];
            drivernamefrom[0]=getResources().getString(R.string.sj_select_driver);
            for(int i=1;i<=drivernamefromserverfordropdown.size();i++){
                drivernamefrom[i]=drivernamefromserverfordropdown.get(i-1);
            }


            ArrayAdapter<String> adapterdrivername = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, drivernamefrom) {
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

            adapterdrivername.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            drivernamespinner.setAdapter(adapterdrivername);


            drivernamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    drivernameforadd=item;

                    Log.e("spinner selected for ad",item);
                    for (int i = 0; i < drivernamefromserverfordropdown.size(); i++) {
                        if (drivernameforadd.equals(drivernamefromserverfordropdown.get(i))) {
                            driveridforadd = driveridfromserverfordropdown.get(i);

                        }
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            btnAddRecord=(Button)layout.findViewById(R.id.btn_add_popup_fuel);
            btnAddRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addNewRecord();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_fuel);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout_MainMenu.getForeground().setAlpha( 0);
                    pwindo.dismiss();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class getDriversFromServer extends AsyncTask<Void,Void,Void> {
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
                                    Log.e("+++",obj.getString("driver_id"));
                                    String id=obj.getString("driver_id");
                                    driveridfromserverfordropdown.add(id);
                                    Log.e("+++",obj.getString("name"));
                                    String name=obj.getString("name");
                                    drivernamefromserverfordropdown.add(name);

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

        }
    }

    private void initiateeditPopupWindow(final int rownumber) {
        try {

            dateintoserver="null";
            driveridintoserver="null";
            amountgivenintoserver="null";
            drivernameintoserver="null";
            amountspentintoserver="null";
            balanceintoserver="null";
            invoiceintoserver="null";


            LayoutInflater inflater = (LayoutInflater) Manage_Fuel_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_fuel_edit,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            layout_MainMenu.getForeground().setAlpha( 200);

            dateedit=(EditText)layout.findViewById(R.id.fueldateedit);
            drivernamespinner=(Spinner)layout.findViewById(R.id.selectdrivernamespinner);
            amountgivenedit=(EditText)layout.findViewById(R.id.amountgivenedit);
            amountspentedit=(EditText)layout.findViewById(R.id.amountspentedit);

            dateedit.setText(datefromserver.get(rownumber));

            amountgivenedit.setText(amountfromserver.get(rownumber));
            if(!amountspentfromserver.get(rownumber).equalsIgnoreCase("--")){
                amountspentedit.setText(amountspentfromserver.get(rownumber));
            }

            Log.e("aaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa    "+amountfromserver.get(rownumber));

            dateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTruitonDatePickerDialog(v);
                }
            });


            final String[] drivernamefrom=new String[drivernamefromserverfordropdown.size()+1];
            drivernamefrom[0]=getResources().getString(R.string.sj_select_driver);
            for(int i=1;i<=drivernamefromserverfordropdown.size();i++){
                drivernamefrom[i]=drivernamefromserverfordropdown.get(i-1);
            }


            ArrayAdapter<String> adapterdrivername = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, drivernamefrom) {
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

            adapterdrivername.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            drivernamespinner.setAdapter(adapterdrivername);


            drivernamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    drivernameforadd=item;

                    Log.e("spinner selected for ad",item);
                    for (int i = 0; i < drivernamefromserverfordropdown.size(); i++) {
                        if (drivernameforadd.equals(drivernamefromserverfordropdown.get(i))) {
                            driveridforadd = driveridfromserverfordropdown.get(i);

                        }
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            String drivername=drivernamefromserver.get(rownumber);
            int pos=0;

            for(int i=1;i<drivernamefrom.length;i++)
            {
                if(drivername.equalsIgnoreCase(drivernamefrom[i]))
                {
                    pos=i;
                }
            }

            drivernamespinner.setSelection(pos);

            btnUpdateRecord=(Button)layout.findViewById(R.id.btn_update_popup_fuel);
            btnUpdateRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editfuelid=Integer.parseInt(idfromserver.get(rownumber));
                    editRowMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_fuel);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout_MainMenu.getForeground().setAlpha( 0);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    pwindo.dismiss();
                }
            });

            txtUploadInvoice=(TextView)layout.findViewById(R.id.uploadinvoicepettycashtext);
            if(!invoicefromserver.get(rownumber).equalsIgnoreCase("--")){
                txtUploadInvoice.setText(invoicefromserver.get(rownumber));
                invoiceintoserver=invoicefromserver.get(rownumber);
            }

            btnUploadInvoice=(Button)layout.findViewById(R.id.btn_uploadinvoicepettycash);
            btnUploadInvoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    uploadfirst();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void editRowMethod(){
        dateintoserver=dateedit.getText().toString();
        driveridintoserver=driveridforadd;
        amountgivenintoserver=amountgivenedit.getText().toString();
        amountspentintoserver=amountspentedit.getText().toString();
        balanceintoserver= (Integer.parseInt(amountgivenintoserver)-(Integer.parseInt(amountspentintoserver)))+"";

        if(dateintoserver.isEmpty()){
            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_select_date),
                    Toast.LENGTH_LONG).show();
        }
        else if(drivernameforadd.equalsIgnoreCase(getResources().getString(R.string.sj_select_driver))){
            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_select_driver),
                    Toast.LENGTH_LONG).show();
        }
        else if(amountgivenintoserver.isEmpty()){
            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_enter_given_amount),
                    Toast.LENGTH_LONG).show();
        }
        else if(amountspentintoserver.isEmpty()){
            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_enter_spent_amount),
                    Toast.LENGTH_LONG).show();
        }
        else
            new updaterecordtoserver().execute();


    }

    public void uploadfirst(){
        Intent intent1 = new Intent(this, FileChooser.class);
        startActivityForResult(intent1,REQUEST_PATH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {
                    Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    String curFileName = data.getStringExtra("GetFileName");
                    String curFileName1 = data.getStringExtra("GetPath");
                    txtUploadInvoice.setText(curFileName);
                    String invoicevaluepath=curFileName1+"/"+curFileName;
                Log.e("path",invoicevaluepath);
                    uploadInvoiceTheard(invoicevaluepath);

            }
        }
    }

    void uploadInvoiceTheard(final String srcPath)
    {
        dialog = ProgressDialog.show(Manage_Fuel_Navigation.this, "", getResources().getString(R.string.sj_uploading_file), true);

        new Thread(new Runnable() {
            public void run() {
                uploadInvoiceFile(srcPath);
            }
        }).start();
    }

    public int uploadInvoiceFile(String sourceFileUri)
    {
        String fileName = sourceFileUri.replaceAll(" ", "_");

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        String urlServer = Config.ip+"Upload/uploadsPettyCash";


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
                cc=new CommonClass(Manage_Fuel_Navigation.this);
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
                                invoiceintoserver=ResObj.getString("file_name");
                                Log.e("into",""+invoiceintoserver);
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

                            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_upload_complete), Toast.LENGTH_SHORT).show();
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

    public void addNewRecord(){
        dateintoserver=dateedit.getText().toString();
        driveridintoserver=driveridforadd;
        amountgivenintoserver=amountedit.getText().toString();

        if(dateintoserver.isEmpty()){
            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_select_date),
                    Toast.LENGTH_LONG).show();
        }
        else if(drivernameforadd.equalsIgnoreCase(getResources().getString(R.string.sj_select_driver))){
            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_select_driver),
                    Toast.LENGTH_LONG).show();
        }
        else if(amountgivenintoserver.isEmpty()){
            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_enter_amount),
                    Toast.LENGTH_LONG).show();
        }
        else
            new addnewrecordtoserver().execute();


    }


    class addnewrecordtoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();
                Log.e("add",dateintoserver+" "+driveridintoserver+" "+amountgivenintoserver);

                url= Config.ip+"PettyCash_api/addPettyCash/date/"+ URLEncoder.encode(dateintoserver,"UTF-8")+"/driver_id/"+URLEncoder.encode(driveridintoserver,"UTF-8")+
                        "/amount_given/"+URLEncoder.encode(amountgivenintoserver,"UTF-8")+"/amount_for/fuel";

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
                                if(Jobj.getString("responsecode").equals("1"))
                                {
                                    //JSONArray jsonArray = Jobj.getJSONArray("result_arr");
                                    runOnUiThread(new Runnable(){

                                        @Override
                                        public void run(){
                                            //update ui here
                                            // display toast here
                                            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_added_successfully), Toast.LENGTH_SHORT).show();
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


    class updaterecordtoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"PettyCash_api/editPettyCash/date/"+ URLEncoder.encode(dateintoserver,"UTF-8")+"/driver_id/"+URLEncoder.encode(driveridintoserver,"UTF-8")+
                        "/amount_given/"+URLEncoder.encode(amountgivenintoserver,"UTF-8")+"/amount_spend/"+URLEncoder.encode(amountspentintoserver,"UTF-8")+"/balance/"+URLEncoder.encode(balanceintoserver,"UTF-8")+"/invoice_doc/"+URLEncoder.encode(invoiceintoserver,"UTF-8")+
                        "/pc_id/"+editfuelid;


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
                                            Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_updated_succesfully), Toast.LENGTH_SHORT).show();
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

    class getFuelDeleteServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"/PettyCash_api/deletePettyCash/pc_id/"+deletefuelid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"/PettyCash_api/deletePettyCash/pc_id/"+deletefuelid,Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                //JSONArray jsonArray = Jobj.getJSONArray("result_arr");
                                runOnUiThread(new Runnable(){

                                    @Override
                                    public void run(){
                                        //update ui here
                                        // display toast here
                                        Toast.makeText(Manage_Fuel_Navigation.this, getResources().getString(R.string.sj_deleted_succesfully), Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(Manage_Fuel_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_fuel_navigation, menu);
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
        else if (id == R.id.managefuel) {
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
        else if (id == R.id.buscreationclick) {
            Intent intent = new Intent(this, Bus_Creation_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.studentmisbehaviour) {
            Intent intent = new Intent(this, Student_Misbehaviour_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.accident) {
            Intent intent = new Intent(this, Accident_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.leave) {
            Intent intent = new Intent(this, Leave_Navigation.class);
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
        else if (id == R.id.changelanguage) {
            showAlertForLanguage();
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

    /*@Override
    public void onPause()
    {
        if ((pwindo != null) && pwindo.isShowing())
            pwindo.dismiss();
    }*/


    public class MyCustomBasedFuelAdaper extends BaseAdapter {
        private ArrayList<SearchResultsFuel> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedFuelAdaper(Context context, ArrayList<SearchResultsFuel> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_manage_fuel, null);
                holder = new ViewHolder();
                holder.txtdate = (TextView) convertView.findViewById(R.id.fueldatelisttext);
                holder.txtdate.setTypeface(tfRobo);
                holder.txtdrivername = (TextView) convertView.findViewById(R.id.drivernamelisttext);
                holder.txtdrivername.setTypeface(tfRobo);
                holder.txtamount = (TextView) convertView.findViewById(R.id.amountlisttext);
                holder.txtamount.setTypeface(tfRobo);
                holder.txtamountspent = (TextView) convertView.findViewById(R.id.amountspentlisttext);
                holder.txtamountspent.setTypeface(tfRobo);
                holder.txtbalance = (TextView) convertView.findViewById(R.id.balancelisttext);
                holder.txtbalance.setTypeface(tfRobo);
                holder.txtfueloptions=(Spinner)convertView.findViewById(R.id.fueloption);

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
                holder.txtfueloptions.setAdapter(adapterdriverdropown);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtfueloptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    String item = parent.getItemAtPosition(position1).toString();
                    //routedrivernamenavigation=item;
                    //routeattendenceselected=item;
                    Log.e("spinner selected",item);
                    if(item.equalsIgnoreCase("Action")){

                    }
                    else if(item.equalsIgnoreCase("Edit")){
                        initiateeditPopupWindow(position);
                    }
                    if(item.equalsIgnoreCase("Delete")){
                        deletefuelid=Integer.parseInt(idfromserver.get(position));
                        new getFuelDeleteServer().execute();
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            holder.txtdate.setText(searchArrayList.get(position).getdate());
            holder.txtdrivername.setText(searchArrayList.get(position).getdriverName());
            holder.txtamount.setText(searchArrayList.get(position).getamount());
            holder.txtamountspent.setText(searchArrayList.get(position).getamountSpent());
            holder.txtbalance.setText(searchArrayList.get(position).getbalance());

            return convertView;
        }

        class ViewHolder {
            TextView txtdate;
            TextView txtdrivername;
            TextView txtamount;
            TextView txtamountspent;
            TextView txtbalance;
            Spinner txtfueloptions;
        }
    }

}
