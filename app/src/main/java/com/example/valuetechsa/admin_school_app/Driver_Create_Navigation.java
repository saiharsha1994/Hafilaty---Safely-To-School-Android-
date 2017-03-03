package com.example.valuetechsa.admin_school_app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.support.v7.view.ContextThemeWrapper;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.DB.DatabaseAdapter;
import com.example.valuetechsa.admin_school_app.commonclass.CommonClass;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;
import com.squareup.picasso.Picasso;

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

public class Driver_Create_Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Typeface tfRobo,tfAdam;
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    private PopupWindow pwindo;
    static int countstudents=0,applybutton=0;
    static int staticday=0,staticmonth=0,staticyear=0;
    static String dateusergiven="empty";
    static String dateprintgiven="empty";
    static EditText txtIqamaTimed,txtPassportTimed,txtLicenseTimed;
    static EditText nameedit,nationalityedit,iqamanumberedit,passportnoedit,phonenoedit,licenseedit,passwordedit;
    String nameserver,nationalityserver,iqamanumberserver,iqamaexpireserver,passportnumberserver,passportexpireserver,mobilenoserver;
    String licensenumberserver,licenseexpiredateserver,passwordserver,busiddriverserver;
    static Button btnAddDrivers;
    static TextView txtuploadiqamaname,txtuploadlicensetext,txtuploadpassporttext;
    static Button txtuploadnameiqamabutton,txtuploadlicnsebutton,txtuploadpassportbutton;
    static String[] dateselected=new String[100];
    static int iii=0;
    ProgressDialog dialog = null;
    ProgressDialog progressDialog2=null;
    int deletedriverid=0;
    String selectedspinneritem,selectedbusid;
    ArrayList<String> busnamefromserver=new ArrayList<String>();
    ArrayList<String> busidfromserver=new ArrayList<String>();

    ArrayList<String> drivernamefromserver=new ArrayList<String>();
    ArrayList<String> driveridfromserver=new ArrayList<String>();
    ArrayList<String> drivernationalityfromserver=new ArrayList<String>();
    ArrayList<String> driveriqamanumberfromserver=new ArrayList<String>();
    ArrayList<String> driveriqamaexpiredatefromserver=new ArrayList<String>();
    ArrayList<String> driverpassportnumberfromserver=new ArrayList<String>();
    ArrayList<String> driverpassportexpiredatefromserver=new ArrayList<String>();
    ArrayList<String> drivermobilefromserver=new ArrayList<String>();
    ArrayList<String> driverpasswordfromserver=new ArrayList<String>();
    ArrayList<String> driverphotofromserver=new ArrayList<String>();
    ArrayList<String> driverassignedbusfromserver=new ArrayList<String>();
    ArrayList<String> driveriqamauploadfromserver=new ArrayList<String>();
    ArrayList<String> driverlicenceuploadfromserver=new ArrayList<String>();
    ArrayList<String> driverpassportuploadfromserver=new ArrayList<String>();
    ArrayList<String> driverlicensenumberfromserver=new ArrayList<String>();
    ArrayList<String> driverlicenseexpiredatefromserver=new ArrayList<String>();
    ArrayList<String> driverlicenseexpiredatenumber=new ArrayList<String>();
    ArrayList<String> driverpassportdatenumber=new ArrayList<String>();
    ArrayList<String> driveriqamadatenumber=new ArrayList<String>();
    ArrayList<Integer> driverlicensechecknumber=new ArrayList<Integer>();
    ArrayList<Integer> driverpassportchecknumber=new ArrayList<Integer>();
    ArrayList<Integer> driveriqamachecknumber=new ArrayList<Integer>();
    String toastmessage="";
    Jsonfunctions sh;
    String response="",url="";
    int serverResponseCode = 0;
    CommonClass cc;
    int[] a=new int[3];
    int[] b=new int[2];
    private static final int REQUEST_PATH = 1;
    Spinner cb;
    Spinner buslistspinner;
    ListView lv1;
    View view1;
    int listcount=0;
    int editdriverid=0;
    Boolean isScrolling;
    static ImageView imageView;
    String imagenameserver,iqamafileserver,licenseserver,passportserver;
    int[] checkwhichchoose=new int[3];
    static String sss;
    MyCustomBasedDriverAdaper myCustomBasedDriverAdaper;


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
        setContentView(R.layout.activity_driver__create__navigation);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.driverclickheader);
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

        TextView TextViewNewFont = new TextView(Driver_Create_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_manage_driver));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Driver_Create_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Driver_Create_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);


        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Log.e("todays date","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+date);
        a[0]=0;
        a[1]=0;
        a[2]=0;
        new getDriverListFromServer().execute();
        changeFont();
    }

    public void changeFont(){
        TextView driverlist=(TextView)findViewById(R.id.driverlisttextbox);
        TextView drivername=(TextView)findViewById(R.id.drivernametextbox);
        TextView nationality=(TextView)findViewById(R.id.nationalitytextbox);
        TextView mobileno=(TextView)findViewById(R.id.mobilenumbertextbox);
        TextView assignbus=(TextView)findViewById(R.id.assignedbustextbox);
        TextView options=(TextView)findViewById(R.id.optionstextbox);
        Button addbus=(Button)findViewById(R.id.adddrivers);

        driverlist.setTypeface(tfRobo);
        drivername.setTypeface(tfRobo);
        nationality.setTypeface(tfRobo);
        mobileno.setTypeface(tfRobo);
        assignbus.setTypeface(tfRobo);
        options.setTypeface(tfRobo);
        addbus.setTypeface(tfAdam);
    }

    public void listgenerate(){
        ArrayList<SearchResultDriver> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.driverselectedlist);
        myCustomBasedDriverAdaper=new MyCustomBasedDriverAdaper(this, searchResults);
        //lv1.setLongClickable(true);
        lv1.setAdapter(myCustomBasedDriverAdaper);
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
                    toastmessage="";
                    Log.e("position-------", "-------------"+pos);
                    if(driveriqamachecknumber.get(pos)==1){
                        toastmessage=toastmessage+getResources().getString(R.string.sj_iqama_has_expired);
                    }
                    if(driverpassportchecknumber.get(pos)==1){
                        toastmessage= toastmessage+getResources().getString(R.string.sj_passport_has_expired);
                    }
                    if(driverlicensechecknumber.get(pos)==1){
                        toastmessage=toastmessage+getResources().getString(R.string.sj_license_has_expired);
                    }
                if(toastmessage.isEmpty()){

                }
                else {
                    Toast.makeText(Driver_Create_Navigation.this, ""+toastmessage ,
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private ArrayList<SearchResultDriver> GetSearchResults(){
        ArrayList<SearchResultDriver> results = new ArrayList<SearchResultDriver>();
        /*for (int i=0;i<2;i++){
            SearchResultDriver sr1 = new SearchResultDriver();
            sr1.setDrivername("Avinash");
            sr1.setNationality("Indian");
            sr1.setAssignto("HYD-CHN");
            sr1.setMobileno("9000399003");
            sr1.setOptions("test");
            sr1.setPosition(i);
            results.add(sr1);
        }*/

        for (int i=0;i<drivernamefromserver.size();i++){
            SearchResultDriver sr1 = new SearchResultDriver();
            sr1.setDrivername(drivernamefromserver.get(i));
            if(drivernationalityfromserver.get(i).equalsIgnoreCase("null")){
                sr1.setNationality("-");
            }
            else{
                sr1.setNationality(drivernationalityfromserver.get(i));
            }
            String busname="No Bus Assigned";
            for(int ii=0;ii<busidfromserver.size();ii++){
                if(busidfromserver.get(ii).equalsIgnoreCase(driverassignedbusfromserver.get(i))){
                    busname=busnamefromserver.get(ii);
                    break;
                }
            }
            sr1.setAssignto(busname);
            sr1.setMobileno(drivermobilefromserver.get(i));
            sr1.setOptions("test");
            sr1.setPosition(i);
            //listcount=listcount+1;
            results.add(sr1);
        }

        return results;
    }
    /*public void checkallmainmethod() {
        Log.e("))))", ")))" + lv1.getCount());
        //View view1=lv1.getChildAt(1);
        view1=myCustomBasedDriverAdaper.getView(1, null, lv1);
        cb = (Spinner) view1.findViewById(R.id.driveroption);
        //Log.e("ewewe",""+cb.getSelectedItem().toString());
        TextView a=(TextView)view1.findViewById(R.id.driverdrivername);
        //Log.e("text",a.getText().toString());
        final String[] triptypeattendence=new String[4];
        triptypeattendence[0]="Action";
        triptypeattendence[1]="Edit";
        triptypeattendence[2]="View";
        triptypeattendence[3]="Delete";
        ArrayAdapter<String> adapterdriverdropown = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, triptypeattendence) {
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

        adapterdriverdropown.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cb.setAdapter(adapterdriverdropown);
        cb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routedrivernamenavigation=item;
                //routeattendenceselected=item;
                Log.e("spinner selected123","***************************************************************");
                Log.e("spinner selected123",item);
                Log.e("Line No",""+view.getTag());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }*/


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
            /*EditText date = (EditText)getActivity().findViewById(R.id.iqamaexpireedit);

            date.setText(day + "/" + (month + 1) + "/" + year);*/


            //dateprintgiven=day + "/" + (month + 1) + "/" + year;
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
            if(iii==0){
                txtIqamaTimed.setText(dateprintgiven);
            }
            if(iii==1){
                txtPassportTimed.setText(dateprintgiven);
            }
            if(iii==2){
                txtLicenseTimed.setText(dateprintgiven);
            }
            dateusergiven = year+"-"+(month+1)+"-"+day;
            dateselected[iii]=dateusergiven;
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


    public void getpopupdriver(View view){
        initiatePopupWindowdriver();
    }

    private void initiatePopupWindowdriver() {
        try {
// We need to get the instance of the LayoutInflater
            /*LayoutInflater inflater1 = (LayoutInflater) Driver_Create_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, true);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/
            /*imagenameserver=null;
            iqamafileserver=null;
            licenseserver=null;
            passportserver=null;
            nameserver=null;
            nationalityserver=null;
            iqamanumberserver=null;
            iqamaexpireserver=null;
            passportnumberserver=null;
            passportexpireserver=null;
            licensenumberserver=null;
            licenseexpiredateserver=null;
            passwordserver=null;
            mobilenoserver=null;
            selectedbusid="0";*/

            imagenameserver="null";
            iqamafileserver="null";
            licenseserver="null";
            passportserver="null";
            nameserver="null";
            nationalityserver="null";
            iqamanumberserver="null";
            iqamaexpireserver="null";
            passportnumberserver="null";
            passportexpireserver="null";
            licensenumberserver="null";
            licenseexpiredateserver="null";
            passwordserver="null";
            mobilenoserver="null";
            selectedbusid="0";



            LayoutInflater inflater = (LayoutInflater) Driver_Create_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_drivers,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, Resources.getSystem().getDisplayMetrics().heightPixels-50, true);
            try{
                pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
                /*pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                pwindo.setFocusable(true);*/
            }
            catch (Exception e){

            }


            nameedit=(EditText)layout.findViewById(R.id.nameedit);
            nationalityedit=(EditText)layout.findViewById(R.id.nationalityedit);
            iqamanumberedit=(EditText)layout.findViewById(R.id.iqamanumberedit);
            passportnoedit=(EditText)layout.findViewById(R.id.passportnoedit);
            phonenoedit=(EditText)layout.findViewById(R.id.mobileedit);
            licenseedit=(EditText)layout.findViewById(R.id.licensenoedit);
            passwordedit=(EditText)layout.findViewById(R.id.passwordedit);
            buslistspinner=(Spinner) layout.findViewById(R.id.selectbusspinnerdriver);
            int sum1=busnamefromserver.size();
            sum1=sum1+1;
            String[] busitems=new String[sum1];
            busitems[0]=getResources().getString(R.string.sj_select_bus);
            for(int j=1;j<=busnamefromserver.size();j++){
                busitems[j]=busnamefromserver.get(j-1);
            }
            ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(Driver_Create_Navigation.this, android.R.layout.simple_spinner_item, busitems) {
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
                    selectedspinneritem=item;
                    Log.e("spinner selected",item);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            txtIqamaTimed=(EditText)layout.findViewById(R.id.iqamaexpireedit);
            txtIqamaTimed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=0;
                    showTruitonDatePickerDialog(v);
                }
            });
            txtPassportTimed=(EditText)layout.findViewById(R.id.passportexpireedit);
            txtPassportTimed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=1;
                    showTruitonDatePickerDialog(v);
                }
            });
            txtLicenseTimed=(EditText)layout.findViewById(R.id.licenseexpireedit);
            txtLicenseTimed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=2;
                    showTruitonDatePickerDialog(v);
                }
            });
            btnAddDrivers=(Button)layout.findViewById(R.id.btn_add_popup_driver);
            btnAddDrivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addDriverMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_driver);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    pwindo.dismiss();
                    //fadePopup.dismiss();
                }
            });
            txtuploadnameiqamabutton=(Button)layout.findViewById(R.id.btn_uploadnameiqama);
            txtuploadnameiqamabutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=1;
                    b[0]=1;
                    b[1]=0;
                    uploadfirst();
                }
            });
            txtuploadiqamaname=(TextView)layout.findViewById(R.id.uploadnameiqamatext);

            txtuploadlicnsebutton=(Button)layout.findViewById(R.id.btn_uploadlicense);
            txtuploadlicnsebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[1]=1;
                    b[0]=1;
                    b[1]=0;
                    uploadfirst();
                }
            });
            txtuploadlicensetext=(TextView)layout.findViewById(R.id.uploadlicensetext);

            txtuploadpassportbutton=(Button)layout.findViewById(R.id.btn_uploadpassport);
            txtuploadpassportbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[2]=1;
                    b[0]=1;
                    b[1]=0;
                    uploadfirst();
                }
            });
            txtuploadpassporttext=(TextView)layout.findViewById(R.id.uploadpassporttext);
            //Log.e("Lets Check",busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")");

            final EditText editText=(EditText)layout.findViewById(R.id.mobileedit);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Log.e("@@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                        pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        pwindo.setFocusable(true);
                        editText.requestFocus();
                        pwindo.update();
                    }
                }
            });

            imageView = (ImageView) layout.findViewById(R.id.imgView);
            Button buttonLoadImage = (Button) layout.findViewById(R.id.btn_uploadimage);
            buttonLoadImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    b[0]=0;
                    b[1]=1;
                    /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, REQUEST_PATH);*/
                    /*Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, REQUEST_PATH);*/
                    selectImage();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initiateEditPopupWindowdriver(final int rownumber) {
        try {
// We need to get the instance of the LayoutInflater
            /*LayoutInflater inflater1 = (LayoutInflater) Driver_Create_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, true);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/
            /*imagenameserver=null;
            iqamafileserver=null;
            licenseserver=null;
            passportserver=null;
            nameserver=null;
            nationalityserver=null;
            iqamanumberserver=null;
            iqamaexpireserver=null;
            passportnumberserver=null;
            passportexpireserver=null;
            licensenumberserver=null;
            licenseexpiredateserver=null;
            passwordserver=null;
            mobilenoserver=null;
            selectedbusid="0";*/

            imagenameserver="null";
            iqamafileserver="null";
            licenseserver="null";
            passportserver="null";
            nameserver="null";
            nationalityserver="null";
            iqamanumberserver="null";
            iqamaexpireserver="null";
            passportnumberserver="null";
            passportexpireserver="null";
            licensenumberserver="null";
            licenseexpiredateserver="null";
            passwordserver="null";
            mobilenoserver="null";
            selectedbusid="0";



            LayoutInflater inflater = (LayoutInflater) Driver_Create_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_drivers,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, Resources.getSystem().getDisplayMetrics().heightPixels-50, true);
            try{
                pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
                /*pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                pwindo.setFocusable(true);*/
            }
            catch (Exception e){

            }


            nameedit=(EditText)layout.findViewById(R.id.nameedit);
            nameedit.setText(drivernamefromserver.get(rownumber));
            nationalityedit=(EditText)layout.findViewById(R.id.nationalityedit);
            if(!drivernationalityfromserver.get(rownumber).equalsIgnoreCase("null")){
                nationalityedit.setText(drivernationalityfromserver.get(rownumber));
            }
            iqamanumberedit=(EditText)layout.findViewById(R.id.iqamanumberedit);
            if(!driveriqamanumberfromserver.get(rownumber).equalsIgnoreCase("null")){
                iqamanumberedit.setText(driveriqamanumberfromserver.get(rownumber));
            }
            passportnoedit=(EditText)layout.findViewById(R.id.passportnoedit);
            if(!driverpassportnumberfromserver.get(rownumber).equalsIgnoreCase("null")){
                passportnoedit.setText(driverpassportnumberfromserver.get(rownumber));
            }
            phonenoedit=(EditText)layout.findViewById(R.id.mobileedit);
            phonenoedit.setText(drivermobilefromserver.get(rownumber));
            licenseedit=(EditText)layout.findViewById(R.id.licensenoedit);
            if(!driverlicensenumberfromserver.get(rownumber).equalsIgnoreCase("null")){
                licenseedit.setText(driverlicensenumberfromserver.get(rownumber));
            }
            passwordedit=(EditText)layout.findViewById(R.id.passwordedit);
            passwordedit.setText(driverpasswordfromserver.get(rownumber));
            buslistspinner=(Spinner) layout.findViewById(R.id.selectbusspinnerdriver);
            int sum1=busnamefromserver.size();
            sum1=sum1+1;
            String[] busitems=new String[sum1];
            busitems[0]=getResources().getString(R.string.sj_select_bus);
            for(int j=1;j<=busnamefromserver.size();j++){
                busitems[j]=busnamefromserver.get(j-1);
            }
            ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(Driver_Create_Navigation.this, android.R.layout.simple_spinner_item, busitems) {
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
                    selectedspinneritem=item;
                    Log.e("spinner selected",item);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(driverassignedbusfromserver.get(rownumber).equalsIgnoreCase("null")){

            }
            else {
                int rowdriver=0;
                String busnameid=driverassignedbusfromserver.get(rownumber);
                Log.e("busnameID","###############################################################"+busnameid);
                for(int i=0;i<busidfromserver.size();i++){
                    if(busnameid.equalsIgnoreCase(busidfromserver.get(i))){
                        Log.e("busnameIDinside","###############################################################"+busidfromserver.get(i));
                        rowdriver=i+1;
                        selectedbusid=driveridfromserver.get(i);
                        break;
                    }
                }
                buslistspinner.setSelection(rowdriver);
            }




            txtIqamaTimed=(EditText)layout.findViewById(R.id.iqamaexpireedit);
            if(!driveriqamaexpiredatefromserver.get(rownumber).equalsIgnoreCase("null")){
                txtIqamaTimed.setText(driveriqamaexpiredatefromserver.get(rownumber));
            }
            txtIqamaTimed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=0;
                    showTruitonDatePickerDialog(v);
                }
            });
            txtPassportTimed=(EditText)layout.findViewById(R.id.passportexpireedit);
            if(!driverpassportexpiredatefromserver.get(rownumber).equalsIgnoreCase("null")){
                txtPassportTimed.setText(driverpassportexpiredatefromserver.get(rownumber));
            }
            txtPassportTimed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=1;
                    showTruitonDatePickerDialog(v);
                }
            });
            txtLicenseTimed=(EditText)layout.findViewById(R.id.licenseexpireedit);
            if(!driverlicenseexpiredatefromserver.get(rownumber).equalsIgnoreCase("null")){
                txtLicenseTimed.setText(driverlicenseexpiredatefromserver.get(rownumber));
            }
            txtLicenseTimed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=2;
                    showTruitonDatePickerDialog(v);
                }
            });
            btnAddDrivers=(Button)layout.findViewById(R.id.btn_add_popup_driver);
            btnAddDrivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editdriverid=Integer.parseInt(driveridfromserver.get(rownumber));
                    Log.e("Image name",""+imagenameserver);
                    Log.e("Iqama name",""+iqamafileserver);
                    Log.e("License name",""+licenseserver);
                    Log.e("Passport name",""+passportserver);
                    if(!imagenameserver.equalsIgnoreCase("null")){

                    }
                    else{
                        imagenameserver=driverphotofromserver.get(rownumber);
                    }
                    if(!iqamafileserver.equalsIgnoreCase("null")){

                    }
                    else {
                        iqamafileserver=driveriqamauploadfromserver.get(rownumber);
                    }
                    if(!licenseserver.equalsIgnoreCase("null")){

                    }
                    else {
                        licenseserver=driverlicenceuploadfromserver.get(rownumber);
                    }
                    if(!passportserver.equalsIgnoreCase("null")){

                    }
                    else {
                        passportserver=driverpassportuploadfromserver.get(rownumber);
                    }

                    addDriverMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_driver);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    pwindo.dismiss();
                    //fadePopup.dismiss();
                }
            });
            txtuploadnameiqamabutton=(Button)layout.findViewById(R.id.btn_uploadnameiqama);
            txtuploadnameiqamabutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=1;
                    b[0]=1;
                    b[1]=0;
                    uploadfirst();
                }
            });
            txtuploadiqamaname=(TextView)layout.findViewById(R.id.uploadnameiqamatext);
            if(!driveriqamauploadfromserver.get(rownumber).equalsIgnoreCase("null")){
                txtuploadiqamaname.setText(driveriqamauploadfromserver.get(rownumber));
            }

            txtuploadlicnsebutton=(Button)layout.findViewById(R.id.btn_uploadlicense);
            txtuploadlicnsebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[1]=1;
                    b[0]=1;
                    b[1]=0;
                    uploadfirst();
                }
            });
            txtuploadlicensetext=(TextView)layout.findViewById(R.id.uploadlicensetext);
            if(!driverlicenceuploadfromserver.get(rownumber).equalsIgnoreCase("null")){
                txtuploadlicensetext.setText(driverlicenceuploadfromserver.get(rownumber));
            }


            txtuploadpassportbutton=(Button)layout.findViewById(R.id.btn_uploadpassport);
            txtuploadpassportbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[2]=1;
                    b[0]=1;
                    b[1]=0;
                    uploadfirst();
                }
            });
            txtuploadpassporttext=(TextView)layout.findViewById(R.id.uploadpassporttext);
            if(!driverpassportuploadfromserver.get(rownumber).equalsIgnoreCase("null")){
                txtuploadpassporttext.setText(driverpassportuploadfromserver.get(rownumber));
            }

            //Log.e("Lets Check",busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")");

            final EditText editText=(EditText)layout.findViewById(R.id.mobileedit);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Log.e("@@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                        pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        pwindo.setFocusable(true);
                        editText.requestFocus();
                        pwindo.update();
                    }
                }
            });

            imageView = (ImageView) layout.findViewById(R.id.imgView);
            if(driverphotofromserver.get(rownumber).equalsIgnoreCase("null")){

            }
            else{
                Picasso.with(this)
                        .load(Config.image+"uploads/driver_image/"+driverphotofromserver.get(rownumber))
                        .into(imageView);
                Log.e("+++",Config.image+"uploads/driver_image/"+driverphotofromserver.get(rownumber));
            }


            Button buttonLoadImage = (Button) layout.findViewById(R.id.btn_uploadimage);
            buttonLoadImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    b[0]=0;
                    b[1]=1;
                    /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, REQUEST_PATH);*/
                    /*Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, REQUEST_PATH);*/
                    selectImage();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void addDriverMethod(){

        nameserver=nameedit.getText().toString();
        nationalityserver=nationalityedit.getText().toString();
        iqamanumberserver=iqamanumberedit.getText().toString();
        iqamaexpireserver=txtIqamaTimed.getText().toString();
        passportnumberserver=passportnoedit.getText().toString();
        passportexpireserver=txtPassportTimed.getText().toString();
        licensenumberserver=licenseedit.getText().toString();
        licenseexpiredateserver=txtLicenseTimed.getText().toString();
        passwordserver=passwordedit.getText().toString();
        mobilenoserver=phonenoedit.getText().toString();
        if(nameserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, getResources().getString(R.string.sj_please_enter_the_driver_name),
                    Toast.LENGTH_LONG).show();
        }
        else if(mobilenoserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, getResources().getString(R.string.sj_please_enter_the_mobile_number),
                    Toast.LENGTH_LONG).show();
        }
        else if(passwordserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, getResources().getString(R.string.sj_please_enter_the_password),
                    Toast.LENGTH_LONG).show();
        }
        /*else if(nationalityserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, "Please Enter The Nationality",
                    Toast.LENGTH_LONG).show();
        }
        else if(iqamanumberserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, "Please Enter The Iqama Number",
                    Toast.LENGTH_LONG).show();
        }
        else if(iqamaexpireserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, "Please Enter The Iqama Expire Date",
                    Toast.LENGTH_LONG).show();
        }
        else if(passportnumberserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, "Please Enter The Passport Number",
                    Toast.LENGTH_LONG).show();
        }
        else if(passportexpireserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, "Please Enter The Passport Expire Date",
                    Toast.LENGTH_LONG).show();
        }
        else if(licensenumberserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, "Please Enter The License Number",
                    Toast.LENGTH_LONG).show();
        }
        else if(licenseexpiredateserver.isEmpty()){
            Toast.makeText(Driver_Create_Navigation.this, "Please Enter The License Expire Date",
                    Toast.LENGTH_LONG).show();
        }*/
        else{
            if(nationalityserver.isEmpty()){
                nationalityserver="null";
            }
            if(iqamanumberserver.isEmpty()){
                iqamanumberserver="null";
            }
            if(iqamaexpireserver.isEmpty()){
                iqamaexpireserver="null";
            }
            if(passportnumberserver.isEmpty()){
                passportnumberserver="null";
            }
            if(passportexpireserver.isEmpty()){
                passportexpireserver="null";
            }
            if(licensenumberserver.isEmpty()){
                licensenumberserver="null";
            }
            if(licenseexpiredateserver.isEmpty()){
                licenseexpiredateserver="null";
            }
            Log.e("Image name","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111111111111111");
            if(selectedspinneritem.equalsIgnoreCase(getResources().getString(R.string.sj_select_bus))){
                selectedbusid="0";
            }
            else{
                for(int j=1;j<=busnamefromserver.size();j++){
                    if(selectedspinneritem.equalsIgnoreCase(busnamefromserver.get(j-1))){
                        selectedbusid=busidfromserver.get(j-1);
                        break;
                    }
                }
            }

            Log.e("Image name","222222222222222222222222222222222222222222222222222222222222");
            Log.e("Driver name",""+nameserver);
            Log.e("mobile",""+mobilenoserver);
            Log.e("password",""+passwordserver);
            Log.e("nationality",""+nationalityserver);
            Log.e("Iqama",""+iqamanumberserver);
            Log.e("Iqama date",""+iqamaexpireserver);
            Log.e("Passport",""+passportnumberserver);
            Log.e("Passport date",""+passportexpireserver);
            Log.e("License",""+licensenumberserver);
            Log.e("License date",""+licenseexpiredateserver);
            Log.e("Bus Id",""+selectedbusid);

            Log.e("Image name",""+imagenameserver);
            Log.e("Iqama name",""+iqamafileserver);
            Log.e("License name",""+licenseserver);
            Log.e("Passport name",""+passportserver);
            if(editdriverid==0){
                new adddetailstoserver().execute();
            }
            else {
                new addeditdetailstoserver().execute();
            }

        }

    }

    class adddetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Driver_Create_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"DriverList_api/addDrivers/name/"+URLEncoder.encode(nameserver,"UTF-8")+"/nationality/"+URLEncoder.encode(nationalityserver,"UTF-8")+
                        "/iqama_number/"+URLEncoder.encode(iqamanumberserver,"UTF-8")+"/iqama_expiry_date/"+URLEncoder.encode(iqamaexpireserver,"UTF-8")+"/passport_number/"+URLEncoder.encode(passportnumberserver,"UTF-8")+
                        "/passport_expiry_date/"+URLEncoder.encode(passportexpireserver,"UTF-8")+"/mobile/"+URLEncoder.encode(mobilenoserver,"UTF-8")+"/password/"+URLEncoder.encode(passwordserver,"UTF-8")+"/photo/"+URLEncoder.encode(imagenameserver,"UTF-8")+
                        "/assigned_bus/"+URLEncoder.encode(selectedbusid,"UTF-8")+"/iqama_upload/"+URLEncoder.encode(iqamafileserver,"UTF-8")+"/license_upload/"+URLEncoder.encode(licenseserver,"UTF-8")+
                        "/passport_upload/"+URLEncoder.encode(passportserver,"UTF-8")+"/license_number/"+URLEncoder.encode(licensenumberserver,"UTF-8")+
                        "/license_expiry_date/"+URLEncoder.encode(licenseexpiredateserver,"UTF-8");

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
                                //Toast.makeText(Driver_Create_Navigation.this, "Driver Added Successfully", Toast.LENGTH_SHORT).show();
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


    class addeditdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Driver_Create_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    "", true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"DriverList_api/editDrivers/name/"+URLEncoder.encode(nameserver,"UTF-8")+"/nationality/"+URLEncoder.encode(nationalityserver,"UTF-8")+
                        "/iqama_number/"+URLEncoder.encode(iqamanumberserver,"UTF-8")+"/iqama_expiry_date/"+URLEncoder.encode(iqamaexpireserver,"UTF-8")+"/passport_number/"+URLEncoder.encode(passportnumberserver,"UTF-8")+
                        "/passport_expiry_date/"+URLEncoder.encode(passportexpireserver,"UTF-8")+"/mobile/"+URLEncoder.encode(mobilenoserver,"UTF-8")+"/password/"+URLEncoder.encode(passwordserver,"UTF-8")+"/photo/"+URLEncoder.encode(imagenameserver,"UTF-8")+
                        "/assigned_bus/"+URLEncoder.encode(selectedbusid,"UTF-8")+"/iqama_upload/"+URLEncoder.encode(iqamafileserver,"UTF-8")+"/license_upload/"+URLEncoder.encode(licenseserver,"UTF-8")+
                        "/passport_upload/"+URLEncoder.encode(passportserver,"UTF-8")+"/license_number/"+URLEncoder.encode(licensenumberserver,"UTF-8")+
                        "/license_expiry_date/"+URLEncoder.encode(licenseexpiredateserver,"UTF-8")+"/driver_id/"+editdriverid;

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
                                //Toast.makeText(Driver_Create_Navigation.this, "Driver Edited Successfully", Toast.LENGTH_SHORT).show();
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

    class getDriverListFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Driver_Create_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                                    Log.e("+++name",obj.getString("name"));
                                    String drivername=obj.getString("name");
                                    drivernamefromserver.add(drivername);
                                    Log.e("+++id",obj.getString("driver_id"));
                                    String driverId=obj.getString("driver_id");
                                    driveridfromserver.add(driverId);
                                    Log.e("+++nationality",obj.getString("nationality"));
                                    String driverNationality=obj.getString("nationality");
                                    drivernationalityfromserver.add(driverNationality);
                                    Log.e("+++iqama",obj.getString("iqama_number"));
                                    String driverIqamaNumber=obj.getString("iqama_number");
                                    driveriqamanumberfromserver.add(driverIqamaNumber);
                                    Log.e("+++iqamaex",obj.getString("iqama_expiry_date"));
                                    String driverIqamaExpire=obj.getString("iqama_expiry_date");
                                    driveriqamaexpiredatefromserver.add(driverIqamaExpire);
                                    Log.e("+++passport",obj.getString("passport_number"));
                                    String driverPassport=obj.getString("passport_number");
                                    driverpassportnumberfromserver.add(driverPassport);
                                    Log.e("+++passportex",obj.getString("passport_expiry_date"));
                                    String driverPassportexpire=obj.getString("passport_expiry_date");
                                    driverpassportexpiredatefromserver.add(driverPassportexpire);
                                    Log.e("+++mobile",obj.getString("mobile"));
                                    String driverMobile=obj.getString("mobile");
                                    drivermobilefromserver.add(driverMobile);
                                    Log.e("+++password",obj.getString("password"));
                                    String driverPassword=obj.getString("password");
                                    driverpasswordfromserver.add(driverPassword);
                                    Log.e("+++photo",obj.getString("photo"));
                                    String driverPhoto=obj.getString("photo");
                                    driverphotofromserver.add(driverPhoto);
                                    Log.e("+++assigned",obj.getString("assigned_bus"));
                                    String driverAssigned=obj.getString("assigned_bus");
                                    driverassignedbusfromserver.add(driverAssigned);
                                    Log.e("+++iqamaupload",obj.getString("iqama_upload"));
                                    String driverIqamaUpload=obj.getString("iqama_upload");
                                    driveriqamauploadfromserver.add(driverIqamaUpload);
                                    Log.e("+++licenseupload",obj.getString("license_upload"));
                                    String driverLicenseUpload=obj.getString("license_upload");
                                    driverlicenceuploadfromserver.add(driverLicenseUpload);
                                    Log.e("+++passportupload",obj.getString("passport_upload"));
                                    String driverPassportUpload=obj.getString("passport_upload");
                                    driverpassportuploadfromserver.add(driverPassportUpload);
                                    Log.e("+++license",obj.getString("license_number"));
                                    String driverLicense=obj.getString("license_number");
                                    driverlicensenumberfromserver.add(driverLicense);
                                    Log.e("+++licenseexpire",obj.getString("license_expiry_date"));
                                    String driverLicenseex=obj.getString("license_expiry_date");
                                    driverlicenseexpiredatefromserver.add(driverLicenseex);

                                    //String toyBornTime = "2014-06-18 12:56:50";
                                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                                            "yyyy-MM-dd");

                                    try {
                                        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1");
                                        if(driverLicenseex.equalsIgnoreCase("null")){
                                            driverlicenseexpiredatenumber.add("%%%%%");
                                            driverlicensechecknumber.add(0);
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@11");
                                        }
                                        else{
                                            Date oldDate = dateFormat.parse(driverLicenseex);
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
                                            driverlicenseexpiredatenumber.add(days+"");
                                            if(days>=-2){
                                                driverlicensechecknumber.add(1);
                                            }
                                            else{
                                                driverlicensechecknumber.add(0);
                                            }
                                        /*if (oldDate.before(currentDate)) {

                                            Log.e("oldDate", "is previous date");
                                            Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                                                    + " hours: " + hours + " days: " + days);

                                        }*/

                                            // Log.e("toyBornTime", "" + toyBornTime);
                                        }


                                    } catch (ParseException e) {

                                        e.printStackTrace();
                                    }
                                    try {
                                        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1");
                                        if(driverPassportexpire.equalsIgnoreCase("null")){
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@11");
                                            driverpassportdatenumber.add("%%%%%");
                                            driverpassportchecknumber.add(0);
                                        }
                                        Date oldDate = dateFormat.parse(driverPassportexpire);
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
                                        driverpassportdatenumber.add(""+days);
                                        if(days>=-2){
                                            driverpassportchecknumber.add(1);
                                        }
                                        else{
                                            driverpassportchecknumber.add(0);
                                        }
                                        /*if (oldDate.before(currentDate)) {

                                            Log.e("oldDate", "is previous date");
                                            Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                                                    + " hours: " + hours + " days: " + days);

                                        }*/

                                        // Log.e("toyBornTime", "" + toyBornTime);

                                    } catch (ParseException e) {

                                        e.printStackTrace();
                                    }

                                    try {
                                        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1");
                                        if(driverIqamaExpire.equalsIgnoreCase("null")){
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@11");
                                            driveriqamadatenumber.add("%%%%%");
                                            driveriqamachecknumber.add(0);
                                        }
                                        Date oldDate = dateFormat.parse(driverIqamaExpire);
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
                                        driveriqamadatenumber.add(""+days);
                                        if(days>=-2){
                                            driveriqamachecknumber.add(1);
                                        }
                                        else{
                                            driveriqamachecknumber.add(0);
                                        }
                                        /*if (oldDate.before(currentDate)) {

                                            Log.e("oldDate", "is previous date");
                                            Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                                                    + " hours: " + hours + " days: " + days);

                                        }*/

                                        // Log.e("toyBornTime", "" + toyBornTime);

                                    } catch (ParseException e) {

                                        e.printStackTrace();
                                    }
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
            new getBusListFromServer().execute();
        }
    }


    class getBusListFromServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Driver_Create_Navigation.this, getResources().getString(R.string.sj_please_wait),
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

    class getDeleteServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Driver_Create_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"DriverList_api/deleteDriver/driver_id/"+deletedriverid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"DriverList_api/deleteDriver/driver_id/"+deletedriverid,Jsonfunctions.GET);

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



    /*protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Log.e("+++","#####"+picturePath);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {
                if(a[0]==1){
                    Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    String curFileName = data.getStringExtra("GetFileName");
                    String curFileName1 = data.getStringExtra("GetPath");
                    txtuploadiqamaname.setText(curFileName1);
                    a[0]=0;
                    a[1]=0;
                    a[2]=0;
                }
                else if(a[1]==1){
                    Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    String curFileName = data.getStringExtra("GetFileName");
                    String curFileName1 = data.getStringExtra("GetPath");
                    txtuploadlicensetext.setText(curFileName);
                    a[0]=0;
                    a[1]=0;
                    a[2]=0;
                }
                else if(a[2]==1){
                    Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    String curFileName = data.getStringExtra("GetFileName");
                    String curFileName1 = data.getStringExtra("GetPath");
                    txtuploadpassporttext.setText(curFileName);
                    a[0]=0;
                    a[1]=0;
                    a[2]=0;
                }
            }
        }
    }*/

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Driver_Create_Navigation.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    /*File f = new File("/storage/sdcard0/DCIM/Camera/", "temp.jpg");*/

                    File f = new File(android.os.Environment.getExternalStorageDirectory()+"/DCIM/Camera/", "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(b[1]==1){
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    File f = new File(Environment.getExternalStorageDirectory().toString()+"/DCIM/Camera/");
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    try {
                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                                bitmapOptions);

                        imageView.setImageBitmap(bitmap);

                        /*String path = android.os.Environment
                                .getExternalStorageDirectory()
                                + File.separator
                                + "Phoenix" + File.separator + "default";*/
                        //String path ="/storage/sdcard0/DCIM/Camera/";
                        String path = android.os.Environment
                                .getExternalStorageDirectory()
                                +"/DCIM/Camera/" ;
                        f.delete();
                        OutputStream outFile = null;
                        /*File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");*/
                        File file = new File(path,"temp.jpg");
                        Log.e("#####","@@@@"+path);
                        try {
                            outFile = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                            outFile.flush();
                            outFile.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == 2) {

                    Uri selectedImage = data.getData();
                    String[] filePath = { MediaStore.Images.Media.DATA };
                    Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                    Log.e("path of image.", picturePath+"");
                    imageView.setImageBitmap(thumbnail);
                }
            }
            uploadImageTheard(android.os.Environment
                    .getExternalStorageDirectory()
                    +"/DCIM/Camera/temp.jpg");
        }
        else if(b[0]==1){
            if (requestCode == REQUEST_PATH){
                if (resultCode == RESULT_OK) {
                    if(a[0]==1){
                        Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        String curFileName = data.getStringExtra("GetFileName");
                        String curFileName1 = data.getStringExtra("GetPath");
                        txtuploadiqamaname.setText(curFileName);
                        String iqamavaluepath=curFileName1+"/"+curFileName;
                        a[0]=0;
                        a[1]=0;
                        a[2]=0;
                        checkwhichchoose[0]=1;
                        checkwhichchoose[1]=0;
                        checkwhichchoose[2]=0;
                        uploadIqamaTheard(iqamavaluepath);
                    }
                    else if(a[1]==1){
                        Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        String curFileName = data.getStringExtra("GetFileName");
                        String curFileName1 = data.getStringExtra("GetPath");
                        txtuploadlicensetext.setText(curFileName);
                        String licensename=curFileName1+"/"+curFileName;
                        a[0]=0;
                        a[1]=0;
                        a[2]=0;
                        checkwhichchoose[0]=0;
                        checkwhichchoose[1]=1;
                        checkwhichchoose[2]=0;
                        uploadIqamaTheard(licensename);

                    }
                    else if(a[2]==1){
                        Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        String curFileName = data.getStringExtra("GetFileName");
                        String curFileName1 = data.getStringExtra("GetPath");
                        txtuploadpassporttext.setText(curFileName);
                        String passportname=curFileName1+"/"+curFileName;
                        a[0]=0;
                        a[1]=0;
                        a[2]=0;
                        checkwhichchoose[0]=0;
                        checkwhichchoose[1]=0;
                        checkwhichchoose[2]=1;
                        uploadIqamaTheard(passportname);
                    }
                }
            }
        }
    }


    public void uploadfirst(){
        Intent intent1 = new Intent(this, FileChooser.class);
        startActivityForResult(intent1,REQUEST_PATH);
    }

    void uploadImageTheard(final String srcPath)
    {
        dialog = ProgressDialog.show(Driver_Create_Navigation.this, "", getResources().getString(R.string.sj_uploading_file), true);

        new Thread(new Runnable() {
            public void run() {
                uploadImageFile(srcPath);
            }
        }).start();
    }

    public int uploadImageFile(String sourceFileUri)
    {
        String fileName = sourceFileUri.replaceAll(" ", "_");

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        String urlServer = Config.ip+"Upload/uploadsDriverImage";


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
                Log.e("+++","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!222");
                String serverResponseMessage = conn.getResponseMessage();
                Log.e("+++","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!223");
                cc=new CommonClass(Driver_Create_Navigation.this);
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
                            imagenameserver=ResObj.getString("file_name");
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

                            Toast.makeText(Driver_Create_Navigation.this, getResources().getString(R.string.sj_upload_complete), Toast.LENGTH_SHORT).show();
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


    void uploadIqamaTheard(final String srcPath)
    {
        dialog = ProgressDialog.show(Driver_Create_Navigation.this, "", getResources().getString(R.string.sj_uploading_file), true);

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
                cc=new CommonClass(Driver_Create_Navigation.this);
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
                               iqamafileserver=ResObj.getString("file_name");
                            }
                            else if(checkwhichchoose[1]==1){
                                licenseserver=ResObj.getString("file_name");
                            }
                            else if(checkwhichchoose[2]==1){
                                passportserver=ResObj.getString("file_name");
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

                            Toast.makeText(Driver_Create_Navigation.this, getResources().getString(R.string.sj_upload_complete), Toast.LENGTH_SHORT).show();
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
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(Driver_Create_Navigation.this);

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
                Intent intent = new Intent(Driver_Create_Navigation.this, Driver_Create_Navigation.class);
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
                Intent intent = new Intent(Driver_Create_Navigation.this, Driver_Create_Navigation.class);
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
        Driver_Create_Navigation.this.getResources().updateConfiguration(config,
                Driver_Create_Navigation.this.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Driver_Create_Navigation.this, Hawkeye_navigation.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.driver__create__navigation, menu);
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
        else if (id == R.id.studentmisbehaviour) {
            Intent intent = new Intent(this, Student_Misbehaviour_Navigation.class);
            startActivity(intent);
        }
        else if (id == R.id.drivercreationclick) {
            DrawerLayout mDrawerLayout;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawers();
        }
        else if (id == R.id.managefuel) {
            Intent intent = new Intent(this, Manage_Fuel_Navigation.class);
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
        else if (id == R.id.logout) {
            db.delete("OneTimeLogin", null, null);
            Intent intent = new Intent(this, LoginAdmin.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MyCustomBasedDriverAdaper extends BaseAdapter {
        private ArrayList<SearchResultDriver> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedDriverAdaper(Context context, ArrayList<SearchResultDriver> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_driver, null);
                holder = new ViewHolder();
                holder.txtdrivername = (TextView) convertView.findViewById(R.id.driverdrivername);
                holder.txtdrivername.setTypeface(tfRobo);
                holder.txtdrivernationality = (TextView) convertView.findViewById(R.id.drivernationality);
                holder.txtdrivernationality.setTypeface(tfRobo);
                holder.txtdriverassignto = (TextView) convertView.findViewById(R.id.driverassiggnto);
                holder.txtdriverassignto.setTypeface(tfRobo);
                holder.txtdrivercontactno = (TextView) convertView.findViewById(R.id.drivermobileno);
                holder.txtdrivercontactno.setTypeface(tfRobo);
                holder.txtdriveroptionsspinner=(Spinner)convertView.findViewById(R.id.driveroption);

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
                holder.txtdriveroptionsspinner.setAdapter(adapterdriverdropown);

            /*holder.txtdriiveroptions = (TextView) convertView.findViewById(R.id.driveroption);
            holder.txtdriiveroptions.setTypeface(tfRobo);*/
                //holder.txtcheckout = (ImageView) convertView.findViewById(R.id.attenddencecheckoubox);
                //holder.txtcheckout.setTypeface(tfRobo);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txtdriveroptionsspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    String item = parent.getItemAtPosition(position1).toString();
                    //routedrivernamenavigation=item;
                    //routeattendenceselected=item;
                    Log.e("spinner selected",item);
                    Log.e("Row",""+searchArrayList.get(position).getPosition());
                    Log.e("position",""+position);
                    if(item.equalsIgnoreCase("Action")){

                    }
                    else if(item.equalsIgnoreCase("Edit")){
                        initiateEditPopupWindowdriver(position);
                    }
                    if(item.equalsIgnoreCase("Delete")){
                        deletedriverid=Integer.parseInt(driveridfromserver.get(position));
                        new getDeleteServer().execute();
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            Log.e("Row outside",""+searchArrayList.get(position).getPosition());
            Log.e("position outside",""+position);

            holder.txtdrivername.setText(searchArrayList.get(position).getDrivername());
            holder.txtdrivernationality.setText(searchArrayList.get(position).getNationality());
            holder.txtdriverassignto.setText(searchArrayList.get(position).getAssignto());
            holder.txtdrivercontactno.setText(searchArrayList.get(position).getMobileno());

            int neutral=0,notneutral=0;

            if(driverlicenseexpiredatenumber.get(position).equalsIgnoreCase("%%%%%")){
                neutral=neutral+1;
            }
            else if(Integer.parseInt(driverlicenseexpiredatenumber.get(position))>=-2){
                notneutral=notneutral+1;
            }

            if(driverpassportdatenumber.get(position).equalsIgnoreCase("%%%%%")){
                neutral=neutral+1;
            }
            else if(Integer.parseInt(driverpassportdatenumber.get(position))>=-2){
                notneutral=notneutral+1;
            }

            if(driveriqamadatenumber.get(position).equalsIgnoreCase("%%%%%")){
                neutral=neutral+1;
            }
            else if(Integer.parseInt(driveriqamadatenumber.get(position))>=-2){
                notneutral=notneutral+1;
            }

            if(neutral==3){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchange);
                layoutrow.setBackgroundResource(R.drawable.spinnerborder);
            }
            else if(notneutral==1||notneutral==2||notneutral==3){
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
            else if(notneutral==0){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchange);
                layoutrow.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout firstview =(LinearLayout)convertView.findViewById(R.id.firstline);
                firstview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout secondview =(LinearLayout)convertView.findViewById(R.id.secondline);
                secondview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout thirdview =(LinearLayout)convertView.findViewById(R.id.thirdline);
                thirdview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout fourthview =(LinearLayout)convertView.findViewById(R.id.fourthline);
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
            TextView txtdrivername;
            TextView txtdrivernationality;
            TextView txtdriverassignto;
            TextView txtdrivercontactno;
            TextView txtdriiveroptions;
            Spinner txtdriveroptionsspinner;
       /* TextView txtcheckout;
        ImageView txtcheckin;
        ImageView txtcheckout;*/
        }
    }

}
