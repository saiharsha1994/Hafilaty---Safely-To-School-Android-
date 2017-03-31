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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Contract_Creation_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase db;
    DatabaseAdapter dbh;
    Typeface tfRobo,tfAdam;
    private PopupWindow pwindo;
    ListView lv1;
    Boolean isScrolling;
    int serverResponseCode = 0;
    CommonClass cc;
    Jsonfunctions sh;
    String response="",url="";
    int contractid=0;
    MyCustomBasedContractAdaper myCustomBasedContractAdaper;
    ProgressDialog progressDialog2=null;
    ArrayList<String> vendornamefromserver=new ArrayList<String>();
    ArrayList<String> vendoremailfromserver=new ArrayList<String>();
    ArrayList<String> vendoridfromserver=new ArrayList<String>();
    ArrayList<String> vendorcontractdatefromserver=new ArrayList<String>();
    ArrayList<String> vendorexpiredatefromserver=new ArrayList<String>();
    ArrayList<String> vendormobilefromserver=new ArrayList<String>();
    ArrayList<String> vendorbussesprovidedfromserver=new ArrayList<String>();
    ArrayList<String> vendordriversprovidedfromserver=new ArrayList<String>();
    ArrayList<String> vendoradditionaldetailsfromserver=new ArrayList<String>();
    ArrayList<String> vendordocumentsfromserver=new ArrayList<String>();
    static String dateusergiven="empty";
    static String dateprintgiven="empty";
    static int iii=0;
    int globaldeletecontractid=0;
    String contractdateintosserver,expiredateintoserver;
    static EditText contractdateedit,expiredateedit;
    Button uploadbutton;
    EditText vendornameedit,vendoremailedit,vendormoblienoedit,busprovidededit,driverprovidededit,additionaldetailsedit;
    TextView uploadfiletext;
    String vendornametxtintoserver,vendoremailtxtintoserver,vendormobilenotxtintoserver,busprovidedtxtintoserver,driverprovidedtxtintoserver,additionaldetailstxtintoserver,uploadfiletxtintoserver;
    ArrayList<String> vendorexpiredatenumber=new ArrayList<String>();
    ArrayList<String> vendorexpiredatechecknumber=new ArrayList<String>();
    private static final int REQUEST_PATH = 1;
    String toastmessage;
    ProgressDialog dialog = null;
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
        setContentView(R.layout.activity_contract__creation__navigation);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.contractheader);
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


        TextView TextViewNewFont = new TextView(Contract_Creation_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_manage_contract));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Contract_Creation_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Contract_Creation_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);


        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);
        new getContractListFromServer().execute();
        changeFont();
        new getSpareBusFromServer().execute();
        //listgenerate();
    }

    public void changeFont(){
        TextView contractlist=(TextView)findViewById(R.id.buslisttextbox);
        TextView contractname=(TextView)findViewById(R.id.vendornametextbox);
        TextView contractemail=(TextView)findViewById(R.id.vendoremailtextbox);
        TextView busdriverprovided=(TextView)findViewById(R.id.busdrivertextbox);
        TextView expirydate=(TextView)findViewById(R.id.expiredatetextbox);
        TextView editroute=(TextView)findViewById(R.id.editroutetextbox);
        Button addcontract=(Button)findViewById(R.id.addcontract);

        contractlist.setTypeface(tfRobo);
        contractname.setTypeface(tfRobo);
        contractemail.setTypeface(tfRobo);
        busdriverprovided.setTypeface(tfRobo);
        expirydate.setTypeface(tfRobo);
        editroute.setTypeface(tfRobo);
        addcontract.setTypeface(tfAdam);
    }

    public void listgenerate(){
        ArrayList<SearchResultsContract> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.conrtactedcreatedlist);
        myCustomBasedContractAdaper=new MyCustomBasedContractAdaper(this, searchResults);
        //lv1.setLongClickable(true);
        lv1.setAdapter(myCustomBasedContractAdaper);
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
                toastmessage="";
                Log.e("position-------", "-------------"+pos);
                if(vendorexpiredatechecknumber.get(pos).equalsIgnoreCase("1")){
                    toastmessage=toastmessage+getResources().getString(R.string.sj_license_has_expired);
                }
                if(toastmessage.isEmpty()){

                }
                else {
                    Toast.makeText(Contract_Creation_Navigation.this, ""+toastmessage ,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private ArrayList<SearchResultsContract> GetSearchResults(){
        ArrayList<SearchResultsContract> results = new ArrayList<SearchResultsContract>();
        /*for (int i=0;i<2;i++){
            SearchResultsContract sr1 = new SearchResultsContract();
            sr1.setVendorname("Avinash");
            sr1.setVendoremail("Avinash@gmail.com");
            sr1.setBusdrivernumber("21-23");
            sr1.setExpiredate("2017-01-20");
            sr1.setContractrowposition(i);
            results.add(sr1);
        }*/
        for (int i=0;i<vendoridfromserver.size();i++){
            SearchResultsContract sr1 = new SearchResultsContract();
            sr1.setVendorname(vendornamefromserver.get(i));
            sr1.setVendoremail(vendoremailfromserver.get(i));
            sr1.setBusdrivernumber(vendorbussesprovidedfromserver.get(i)+"-"+vendordriversprovidedfromserver.get(i));
            sr1.setExpiredate(vendorexpiredatefromserver.get(i));
            sr1.setContractrowposition(i);
            results.add(sr1);
        }

        return results;
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
            /*EditText date = (EditText)getActivity().findViewById(R.id.iqamaexpireedit);

            date.setText(day + "/" + (month + 1) + "/" + year);*/


            //dateprintgiven=day + "/" + (month + 1) + "/" + year;
            dateprintgiven=year + "-" + (month + 1) + "-" + day;
            if(iii==0){
                contractdateedit.setText(dateprintgiven);
            }
            if(iii==1){
                expiredateedit.setText(dateprintgiven);
            }
            dateusergiven = year+"-"+(month+1)+"-"+day;
            //dateselected[iii]=dateusergiven;
        }
    }

    public void getpopupcontract(View view){
        initiatePopupWindowcontract();
    }

    private void initiatePopupWindowcontract(){
        try {
            vendornametxtintoserver="null";
            vendoremailtxtintoserver="null";
            vendormobilenotxtintoserver="null";
            busprovidedtxtintoserver="null";
            driverprovidedtxtintoserver="null";
            additionaldetailstxtintoserver="null";
            uploadfiletxtintoserver="null";
            contractdateintosserver="null";
            expiredateintoserver="null";
// We need to get the instance of the LayoutInflater
            /*LayoutInflater inflater1 = (LayoutInflater) Driver_Create_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, true);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/
            LayoutInflater inflater = (LayoutInflater) Contract_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_contracts,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            layout_MainMenu.getForeground().setAlpha( 200);

            vendornameedit=(EditText)layout.findViewById(R.id.vendornameedit);
            vendoremailedit=(EditText)layout.findViewById(R.id.vendoremailedit);
            vendormoblienoedit=(EditText)layout.findViewById(R.id.vendormobileedit);
            busprovidededit=(EditText)layout.findViewById(R.id.bussesprovidededit);
            driverprovidededit=(EditText)layout.findViewById(R.id.driverprovidededit);
            additionaldetailsedit=(EditText)layout.findViewById(R.id.additionaldetailsedit);

            uploadfiletext=(TextView) layout.findViewById(R.id.uploadcontractfilenametext);

            contractdateedit=(EditText)layout.findViewById(R.id.contractdateedit);
            contractdateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=0;
                    showTruitonDatePickerDialog(v);
                }
            });

            expiredateedit=(EditText)layout.findViewById(R.id.expiredateedit);
            expiredateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=1;
                    showTruitonDatePickerDialog(v);
                }
            });

            uploadbutton=(Button)layout.findViewById(R.id.btn_uploadcontract);
            uploadbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    uploadfirst();
                }
            });


            Button btnAddDrivers=(Button)layout.findViewById(R.id.btn_add_popup_contracts);
            btnAddDrivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addContract();
                    //addDriverMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_contracts);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout_MainMenu.getForeground().setAlpha( 0);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    pwindo.dismiss();
                    //fadePopup.dismiss();

                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initiateeditPopupWindowcontract(final int rownumber){
        try {
            vendornametxtintoserver="null";
            vendoremailtxtintoserver="null";
            vendormobilenotxtintoserver="null";
            busprovidedtxtintoserver="null";
            driverprovidedtxtintoserver="null";
            additionaldetailstxtintoserver="null";
            uploadfiletxtintoserver="null";
            contractdateintosserver="null";
            expiredateintoserver="null";
// We need to get the instance of the LayoutInflater
            /*LayoutInflater inflater1 = (LayoutInflater) Driver_Create_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, true);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/
            LayoutInflater inflater = (LayoutInflater) Contract_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_contracts,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            layout_MainMenu.getForeground().setAlpha( 200);

            contractid=Integer.parseInt(vendoridfromserver.get(rownumber));
            vendornameedit=(EditText)layout.findViewById(R.id.vendornameedit);
            vendornameedit.setText(vendornamefromserver.get(rownumber));
            vendoremailedit=(EditText)layout.findViewById(R.id.vendoremailedit);
            vendoremailedit.setText(vendoremailfromserver.get(rownumber));
            vendormoblienoedit=(EditText)layout.findViewById(R.id.vendormobileedit);
            vendormoblienoedit.setText(vendormobilefromserver.get(rownumber));
            busprovidededit=(EditText)layout.findViewById(R.id.bussesprovidededit);
            busprovidededit.setText(vendorbussesprovidedfromserver.get(rownumber));
            driverprovidededit=(EditText)layout.findViewById(R.id.driverprovidededit);
            driverprovidededit.setText(vendordriversprovidedfromserver.get(rownumber));

            additionaldetailsedit=(EditText)layout.findViewById(R.id.additionaldetailsedit);
            if(!vendoradditionaldetailsfromserver.get(rownumber).equalsIgnoreCase("null")){
                additionaldetailsedit.setText(vendoradditionaldetailsfromserver.get(rownumber));
            }
            uploadfiletext=(TextView) layout.findViewById(R.id.uploadcontractfilenametext);
            if(!vendordocumentsfromserver.get(rownumber).equalsIgnoreCase("null")){
                uploadfiletext.setText(vendordocumentsfromserver.get(rownumber));
            }
            contractdateedit=(EditText)layout.findViewById(R.id.contractdateedit);
            contractdateedit.setText(vendorcontractdatefromserver.get(rownumber));
            contractdateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=0;
                    showTruitonDatePickerDialog(v);
                }
            });

            expiredateedit=(EditText)layout.findViewById(R.id.expiredateedit);
            expiredateedit.setText(vendorexpiredatefromserver.get(rownumber));
            expiredateedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=1;
                    showTruitonDatePickerDialog(v);
                }
            });

            uploadbutton=(Button)layout.findViewById(R.id.btn_uploadcontract);
            uploadbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    uploadfirst();
                }
            });


            Button btnAddDrivers=(Button)layout.findViewById(R.id.btn_add_popup_contracts);
            btnAddDrivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addContract();
                    //addDriverMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_contracts);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout_MainMenu.getForeground().setAlpha( 0);
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    pwindo.dismiss();
                    //fadePopup.dismiss();

                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addContract(){
        vendornametxtintoserver=vendornameedit.getText().toString();
        vendoremailtxtintoserver=vendoremailedit.getText().toString();
        vendormobilenotxtintoserver=vendormoblienoedit.getText().toString();
        busprovidedtxtintoserver=busprovidededit.getText().toString();
        driverprovidedtxtintoserver=driverprovidededit.getText().toString();
        additionaldetailstxtintoserver=additionaldetailsedit.getText().toString();
        contractdateintosserver=contractdateedit.getText().toString();
        expiredateintoserver=expiredateedit.getText().toString();


        if(contractdateintosserver.isEmpty()){
            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_the_contract_date),
                    Toast.LENGTH_LONG).show();
        }
        else if(vendornametxtintoserver.isEmpty()){
            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_the_vendor_name),
                    Toast.LENGTH_LONG).show();
        }
        else if(vendoremailtxtintoserver.isEmpty()){
            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_vendor_email),
                    Toast.LENGTH_LONG).show();
        }
        else if(vendormobilenotxtintoserver.isEmpty()){
            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_vendor_mobile_number),
                    Toast.LENGTH_LONG).show();
        }
        else if(busprovidedtxtintoserver.isEmpty()){
            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_the_bus_provided),
                    Toast.LENGTH_LONG).show();
        }
        else if(driverprovidedtxtintoserver.isEmpty()){
            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_the_driver_provided),
                    Toast.LENGTH_LONG).show();
        }
        else if(expiredateintoserver.isEmpty()){
            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_the_expiry_date),
                    Toast.LENGTH_LONG).show();
        }
        else{
            if(additionaldetailstxtintoserver.isEmpty()){
                additionaldetailstxtintoserver="null";
            }
            if(uploadfiletxtintoserver.isEmpty()){
                uploadfiletxtintoserver="null";
            }


            Log.e("Contract Date: ",""+contractdateintosserver);
            Log.e("Vendor Name: ",""+vendornametxtintoserver);
            Log.e("Vendor Email: ",""+vendoremailtxtintoserver);
            Log.e("Vendor Mobile: ",""+vendormobilenotxtintoserver);
            Log.e("Bus Provided: ",""+busprovidedtxtintoserver);
            Log.e("Driver Provided: ",""+driverprovidedtxtintoserver);
            Log.e("Expire Date: ",""+expiredateintoserver);
            Log.e("Additional Details: ",""+additionaldetailstxtintoserver);
            Log.e("Upload File Name: ",""+uploadfiletxtintoserver);
            if(contractid==0){
                new addcontractdetailstoserver().execute();
            }
            else {
                new addeditcontractdetailstoserver().execute();
            }

        }
    }

    class addcontractdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"Contracts_api/addContract/vendor_name/"+ URLEncoder.encode(vendornametxtintoserver,"UTF-8")+"/vendor_email/"+URLEncoder.encode(vendoremailtxtintoserver,"UTF-8")+
                        "/contract_date/"+URLEncoder.encode(contractdateintosserver,"UTF-8")+"/vendor_mobile/"+URLEncoder.encode(vendormobilenotxtintoserver,"UTF-8")+"/busses_provide/"+URLEncoder.encode(busprovidedtxtintoserver,"UTF-8")+
                        "/driver_provide/"+URLEncoder.encode(driverprovidedtxtintoserver,"UTF-8")+"/expiry_date/"+URLEncoder.encode(expiredateintoserver,"UTF-8")+"/addtional_details/"+URLEncoder.encode(additionaldetailstxtintoserver,"UTF-8")+
                        "/document/"+URLEncoder.encode(uploadfiletxtintoserver,"UTF-8");

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
                                //Toast.makeText(Contract_Creation_Navigation.this, "Contract Added Successfully", Toast.LENGTH_SHORT).show();
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


    class addeditcontractdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"Contracts_api/editContract/vendor_name/"+ URLEncoder.encode(vendornametxtintoserver,"UTF-8")+"/vendor_email/"+URLEncoder.encode(vendoremailtxtintoserver,"UTF-8")+
                        "/contract_date/"+URLEncoder.encode(contractdateintosserver,"UTF-8")+"/vendor_mobile/"+URLEncoder.encode(vendormobilenotxtintoserver,"UTF-8")+"/busses_provide/"+URLEncoder.encode(busprovidedtxtintoserver,"UTF-8")+
                        "/driver_provide/"+URLEncoder.encode(driverprovidedtxtintoserver,"UTF-8")+"/expiry_date/"+URLEncoder.encode(expiredateintoserver,"UTF-8")+"/addtional_details/"+URLEncoder.encode(additionaldetailstxtintoserver,"UTF-8")+
                        "/document/"+URLEncoder.encode(uploadfiletxtintoserver,"UTF-8")+"/contract_id/"+contractid;

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
                                //Toast.makeText(Contract_Creation_Navigation.this, "Contract Added Successfully", Toast.LENGTH_SHORT).show();
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

    class getContractDeleteServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"Contracts_api/deleteContract/contract_id/"+globaldeletecontractid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Contracts_api/deleteContract/contract_id/"+globaldeletecontractid,Jsonfunctions.GET);

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
                    uploadfiletext.setText(curFileName);
                    String iqamavaluepath=curFileName1+"/"+curFileName;
                    /*a[0]=0;
                    a[1]=0;
                    checkwhichchoose[0]=1;
                    checkwhichchoose[1]=0;*/
                    uploadIqamaTheard(iqamavaluepath);
            }
        }
    }

    void uploadIqamaTheard(final String srcPath)
    {
        dialog = ProgressDialog.show(Contract_Creation_Navigation.this, "", getResources().getString(R.string.sj_uploading_file), true);

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

        String urlServer = Config.ip+"Upload/uploadsDriverDocument";


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
                cc=new CommonClass(Contract_Creation_Navigation.this);
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

                            uploadfiletxtintoserver=ResObj.getString("file_name");
                            Log.e("into",""+uploadfiletxtintoserver);
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

                            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_upload_complete), Toast.LENGTH_SHORT).show();
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

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    class getContractListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"Contracts_api/listContract");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Contracts_api/listContract",Jsonfunctions.GET);

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
                                    String vendorId=obj.getString("id");
                                    vendoridfromserver.add(vendorId);
                                    Log.e("+++",obj.getString("vendor_name"));
                                    String vendorName=obj.getString("vendor_name");
                                    vendornamefromserver.add(vendorName);
                                    Log.e("+++",obj.getString("vendor_email"));
                                    String vendorEmail=obj.getString("vendor_email");
                                    vendoremailfromserver.add(vendorEmail);
                                    Log.e("+++",obj.getString("contract_date"));
                                    String vendorContractDate=obj.getString("contract_date");
                                    vendorcontractdatefromserver.add(vendorContractDate);
                                    Log.e("+++",obj.getString("vendor_mobile"));
                                    String vendorMobile=obj.getString("vendor_mobile");
                                    vendormobilefromserver.add(vendorMobile);
                                    Log.e("+++",obj.getString("busses_provide"));
                                    String vendorBussesProvided=obj.getString("busses_provide");
                                    vendorbussesprovidedfromserver.add(vendorBussesProvided);
                                    Log.e("+++",obj.getString("driver_provide"));
                                    String vendorDriversProvided=obj.getString("driver_provide");
                                    vendordriversprovidedfromserver.add(vendorDriversProvided);
                                    Log.e("+++",obj.getString("expiry_date"));
                                    String vendorExpireDate=obj.getString("expiry_date");
                                    vendorexpiredatefromserver.add(vendorExpireDate);
                                    Log.e("+++",obj.getString("addtional_details"));
                                    String vendorAdditionalDetails=obj.getString("addtional_details");
                                    vendoradditionaldetailsfromserver.add(vendorAdditionalDetails);
                                    Log.e("+++",obj.getString("document"));
                                    String vendorDocument=obj.getString("document");
                                    vendordocumentsfromserver.add(vendorDocument);
//                                    Log.e("+++",obj.getString("student_id"));
//                                    Log.e("+++",obj.getString("att_date"));
//                                    Log.e("+++",obj.getString("att_month"));

                                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                                            "yyyy-MM-dd");
                                    try {
                                        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1");
                                        if(vendorExpireDate.equalsIgnoreCase("null")){
                                            vendorexpiredatenumber.add("%%%%%");
                                            vendorexpiredatechecknumber.add("0");
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@11");
                                        }
                                        else{
                                            Date oldDate = dateFormat.parse(vendorExpireDate);
                                            System.out.println(oldDate);
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2");

                                            Date currentDate = new Date();

                                            long diff = currentDate.getTime() - oldDate.getTime();
                                            long seconds = diff / 1000;
                                            long minutes = seconds / 60;
                                            long hours = minutes / 60;
                                            long days = hours / 24;
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@3");
                                            Log.e("oldDate", "is previous date");
                                            Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                                                    + " hours: " + hours + " days: " + days);
                                            vendorexpiredatenumber.add(days+"");
                                            if(days>=-2){
                                                vendorexpiredatechecknumber.add("1");
                                            }
                                            else{
                                                vendorexpiredatechecknumber.add("0");
                                            }
                                        }
                                    } catch (ParseException e) {

                                        e.printStackTrace();
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
            listgenerate();
            progressDialog2.dismiss();
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

            LayoutInflater inflater = (LayoutInflater) Contract_Creation_Navigation.this
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

            LayoutInflater inflater = (LayoutInflater) Contract_Creation_Navigation.this
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
            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_select_bus),
                    Toast.LENGTH_LONG).show();
        }

        else
            new reassignbustoserver().execute();


    }


    class reassignbustoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                                            Toast.makeText(Contract_Creation_Navigation.this, getResources().getString(R.string.sj_reassigned_successfully), Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Contract_Creation_Navigation.this);

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
                Intent intent = new Intent(Contract_Creation_Navigation.this, Contract_Creation_Navigation.class);
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
                Intent intent = new Intent(Contract_Creation_Navigation.this, Contract_Creation_Navigation.class);
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
        Contract_Creation_Navigation.this.getResources().updateConfiguration(config,
                Contract_Creation_Navigation.this.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Contract_Creation_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contract__creation__navigation, menu);
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
            Intent intent = new Intent(this, Bus_Creation_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.contractcreation) {
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();
        }
        else if (id == R.id.managefuel) {
            Intent intent = new Intent(this, Manage_Fuel_Navigation.class);
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

    public class MyCustomBasedContractAdaper extends BaseAdapter {
        private ArrayList<SearchResultsContract> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedContractAdaper(Context context, ArrayList<SearchResultsContract> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_contract, null);
                holder = new ViewHolder();
                holder.txtvendorname = (TextView) convertView.findViewById(R.id.vendornametext);
                holder.txtvendorname.setTypeface(tfRobo);
                holder.txtvendoremail = (TextView) convertView.findViewById(R.id.vendoremailtext);
                holder.txtvendoremail.setTypeface(tfRobo);
                holder.txtbusdriver = (TextView) convertView.findViewById(R.id.vendorbusdrivertext);
                holder.txtbusdriver.setTypeface(tfRobo);
                holder.txtexpiredate = (TextView) convertView.findViewById(R.id.vendorexpiredatetext);
                holder.txtexpiredate.setTypeface(tfRobo);
                holder.txtcontractoptionsspinner=(Spinner)convertView.findViewById(R.id.contractoption);

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
                holder.txtcontractoptionsspinner.setAdapter(adapterdriverdropown);

            /*holder.txtdriiveroptions = (TextView) convertView.findViewById(R.id.driveroption);
            holder.txtdriiveroptions.setTypeface(tfRobo);*/
                //holder.txtcheckout = (ImageView) convertView.findViewById(R.id.attenddencecheckoubox);
                //holder.txtcheckout.setTypeface(tfRobo);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txtcontractoptionsspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    String item = parent.getItemAtPosition(position1).toString();
                    //routedrivernamenavigation=item;
                    //routeattendenceselected=item;
                    Log.e("spinner selected",item);
                    //Log.e("Row",""+searchArrayList.get(position).getPosition());
                    Log.e("position",""+position);
                    if(item.equalsIgnoreCase("Action")){
                        Log.e("position",""+position);
                    }
                    else if(item.equalsIgnoreCase("Edit")){
                        //initiateEditPopupWindowdriver(position);
                        initiateeditPopupWindowcontract(position);
                        Log.e("position",""+position);
                    }
                    if(item.equalsIgnoreCase("Delete")){
                        Log.e("position",""+position);
                        globaldeletecontractid=Integer.parseInt(vendoridfromserver.get(position));
                        new getContractDeleteServer().execute();
                        //deletedriverid=Integer.parseInt(driveridfromserver.get(position));
                        //new getDeleteServer().execute();
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            //Log.e("Row outside",""+searchArrayList.get(position).getPosition());
            //Log.e("position outside",""+position);

            holder.txtvendorname.setText(searchArrayList.get(position).getVendorname());
            holder.txtvendoremail.setText(searchArrayList.get(position).getVendoremail());
            holder.txtbusdriver.setText(searchArrayList.get(position).getBusdrivernumber());
            holder.txtexpiredate.setText(searchArrayList.get(position).getExpiredate());


            if(vendorexpiredatenumber.get(position).equalsIgnoreCase("%%%%%")){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchangecontracts);
                layoutrow.setBackgroundResource(R.drawable.spinnerborder);
            }
            else if(vendorexpiredatechecknumber.get(position).equalsIgnoreCase("1")){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchangecontracts);
                layoutrow.setBackgroundResource(R.drawable.tablecolorbigred);
                LinearLayout firstview =(LinearLayout)convertView.findViewById(R.id.firstlinecon);
                firstview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout secondview =(LinearLayout)convertView.findViewById(R.id.secondlinecon);
                secondview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout thirdview =(LinearLayout)convertView.findViewById(R.id.thirdlinecon);
                thirdview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout fourthview =(LinearLayout)convertView.findViewById(R.id.fourthlinecon);
                fourthview.setBackgroundResource(R.drawable.tablerowcolorred);
            }
            else if(vendorexpiredatechecknumber.get(position).equalsIgnoreCase("0")){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchangecontracts);
                layoutrow.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout firstview =(LinearLayout)convertView.findViewById(R.id.firstlinecon);
                firstview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout secondview =(LinearLayout)convertView.findViewById(R.id.secondlinecon);
                secondview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout thirdview =(LinearLayout)convertView.findViewById(R.id.thirdlinecon);
                thirdview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout fourthview =(LinearLayout)convertView.findViewById(R.id.fourthlinecon);
                fourthview.setBackgroundResource(R.drawable.spinnerborder);
            }



            /*if(driverlicenseexpiredatenumber.get(position).equalsIgnoreCase("%%%%%")&&driverpassportdatenumber.get(position).equalsIgnoreCase("%%%%%")&&driveriqamadatenumber.get(position).equalsIgnoreCase("%%%%%")){
                        LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchange);
                        layoutrow.setBackgroundResource(R.drawable.spinnerborder);
            }
            else if(Integer.parseInt(driverlicenseexpiredatenumber.get(position))>-2||Integer.parseInt(driverpassportdatenumber.get(position))>-2||Integer.parseInt(driveriqamadatenumber.get(position))>-2){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchange);
                layoutrow.setBackgroundResource(R.drawable.tablecolorbigred);
                LinearLayout firstview =(LinearLayout)convertView.findViewById(R.id.firstline);
                firstview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout secondview =(LinearLayout)convertView.findViewById(R.id.secondline);
                secondview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout thirdview =(LinearLayout)convertView.findViewById(R.id.thirdline);
                thirdview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout fourthview =(LinearLayout)convertView.findViewById(R.id.fourthline);
                fourthview.setBackgroundResource(R.drawable.tablerowcolorred);
            }
            else{
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchange);
                layoutrow.setBackgroundResource(R.drawable.spinnerborder);
            }*/


            /*LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchange);
            layoutrow.setBackgroundResource(R.drawable.tablecolorred);*/
            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());


        /*if(searchArrayList.get(position).getCheckinresult().equalsIgnoreCase("yes")){
            //holder.txtcheckin.setBackgroundResource(R.drawable.present);
            holder.txtcheckin.setImageResource(R.drawable.present);
        }
        else{
            holder.txtcheckin.setImageResource(R.drawable.absent);
        }
        if(searchArrayList.get(position).getCheckoutresult().equalsIgnoreCase("yes")){
            holder.txtcheckout.setImageResource(R.drawable.present);
        }
        else{
            holder.txtcheckout.setImageResource(R.drawable.absent);
        }*/
        /*holder.txtcheckin.setText(searchArrayList.get(position).getCheckinresult());
        holder.txtcheckout.setText(searchArrayList.get(position).getCheckoutresult());*/

            return convertView;
        }

        class ViewHolder {
            TextView txtvendorname;
            TextView txtvendoremail;
            TextView txtbusdriver;
            TextView txtexpiredate;
            Spinner txtcontractoptionsspinner;
       /* TextView txtcheckout;
        ImageView txtcheckin;
        ImageView txtcheckout;*/
        }
    }
}
