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

public class Bus_Creation_Navigation extends AppCompatActivity
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
    ArrayList<String> busidfromserver=new ArrayList<String>();
    ArrayList<String> busnamefromserver=new ArrayList<String>();
    ArrayList<String> bustypefromserver=new ArrayList<String>();
    ArrayList<String> chassisnumberfromserver=new ArrayList<String>();
    ArrayList<String> platenumberfromserver=new ArrayList<String>();
    ArrayList<String> fahasfromserver=new ArrayList<String>();
    ArrayList<String> fromserver=new ArrayList<String>();
    ArrayList<String> busfromfromserver=new ArrayList<String>();
    ArrayList<String> bustofromserver=new ArrayList<String>();
    ArrayList<String> buspickuprouteidfromserver=new ArrayList<String>();
    ArrayList<String> busdroprouteidfromserver=new ArrayList<String>();
    ArrayList<String> buslicensefromserver=new ArrayList<String>();
    ArrayList<String> buslicenseexpiredatefromserver=new ArrayList<String>();
    ArrayList<String> buslicenserenewaldatefromserver=new ArrayList<String>();
    ArrayList<String> mvpiuploadfromserver=new ArrayList<String>();
    ArrayList<String> mvpiexpiredatefromserver=new ArrayList<String>();
    ArrayList<String> licenseexpiredatenumber=new ArrayList<String>();
    ArrayList<String> licenserenewaldatenumber=new ArrayList<String>();
    ArrayList<String> mvpiexpiredatenumber=new ArrayList<String>();
    ArrayList<Integer> licenseexpirechecknumber=new ArrayList<Integer>();
    ArrayList<Integer> licenserenewalchecknumber=new ArrayList<Integer>();
    ArrayList<Integer> mvpiexpirechecknumber=new ArrayList<Integer>();
    static int countstudents=0,applybutton=0;
    static int staticday=0,staticmonth=0,staticyear=0;
    static String dateusergiven="empty";
    static String dateprintgiven="empty";
    static EditText txtLicenseExpire,txtLicenseRenewal,txtMvpiExpire;
    static int iii=0;
    private static final int REQUEST_PATH = 1;
    static Button txtuploadlicensebusbutton,txtuploadmvpibutton;
    int[] a=new int[2];
    static TextView txtuploadlicencetxt,txtuploadmvpitxt;
    ProgressDialog dialog = null;
    int serverResponseCode = 0;
    CommonClass cc;
    int[] checkwhichchoose=new int[2];
    String licensebusintoserver,mvpiintoserver;
    String busnameintoserver,chassisnumberintoserver,platenumberintoserver,fahasintoserver,licenseexpiredateintoserver,licenserenewaldateintoserver,mvpiexpiredateintoserver;
    EditText busnameedit,chassisnumberedit,platenumberedit,fahasedit;
    Spinner bustypelistspinner;
    String selectedspinneritemintoserver;
    static Button btnAddDrivers;
    int editbusid=0,deletebusid=0;
    String toastmessage="";
    MyCustomBasedBusAdaper myCustomBasedBusAdaper;
    FrameLayout layout_MainMenu;


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
        setContentView(R.layout.activity_bus__creation__navigation);
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
        TextView navUsername = (TextView) headerView.findViewById(R.id.busclickheader);
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

        TextView TextViewNewFont = new TextView(Bus_Creation_Navigation.this);
        TextViewNewFont.setText(getResources().getString(R.string.sj_manage_bus));
        TextViewNewFont.setTextSize(32);
        tfRobo = Typeface.createFromAsset(Bus_Creation_Navigation.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(Bus_Creation_Navigation.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextViewNewFont.setTypeface(tfRobo);


        android.support.v7.app.ActionBar action=getSupportActionBar();
        action.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        action.setCustomView(TextViewNewFont);

        changeFont();
        new getBusListFromServer().execute();
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void changeFont(){
        TextView buslist=(TextView)findViewById(R.id.buslisttextbox);
        TextView busid=(TextView)findViewById(R.id.busidtextbox);
        TextView busname=(TextView)findViewById(R.id.busroutenametextbox);
        TextView buschassisnumber=(TextView)findViewById(R.id.chassisnumbertextbox);
        TextView busplatenumber=(TextView)findViewById(R.id.platenumbertextbox);
        TextView busFahas=(TextView)findViewById(R.id.fahastextbox);
        TextView editroute=(TextView)findViewById(R.id.editroutetextbox);
        Button addbus=(Button)findViewById(R.id.addbus);

        buslist.setTypeface(tfRobo);
        busid.setTypeface(tfRobo);
        busname.setTypeface(tfRobo);
        buschassisnumber.setTypeface(tfRobo);
        busplatenumber.setTypeface(tfRobo);
        busFahas.setTypeface(tfRobo);
        editroute.setTypeface(tfRobo);
        addbus.setTypeface(tfAdam);
    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Bus_Creation_Navigation.this);

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
                Intent intent = new Intent(Bus_Creation_Navigation.this, Bus_Creation_Navigation.class);
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
                Intent intent = new Intent(Bus_Creation_Navigation.this, Bus_Creation_Navigation.class);
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
        Bus_Creation_Navigation.this.getResources().updateConfiguration(config,
                Bus_Creation_Navigation.this.getResources().getDisplayMetrics());
    }
    public void listgenerate(){
        ArrayList<SearchResultsBus> searchResults = GetSearchResults();
        lv1 = (ListView) findViewById(R.id.buscreatedlist);
        myCustomBasedBusAdaper=new MyCustomBasedBusAdaper(this, searchResults);
        lv1.setAdapter(myCustomBasedBusAdaper);
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
                if(licenseexpirechecknumber.get(pos)==1){
                    toastmessage=toastmessage+getResources().getString(R.string.sj_license_has_expired);
                }
                if(licenserenewalchecknumber.get(pos)==1){
                    toastmessage= toastmessage+getResources().getString(R.string.sj_license_renewal_has_expired);
                }
                if(mvpiexpirechecknumber.get(pos)==1){
                    toastmessage=toastmessage+getResources().getString(R.string.sj_mvpi_has_expired);
                }
                if(toastmessage.isEmpty()){

                }
                else {
                    Toast.makeText(Bus_Creation_Navigation.this, ""+toastmessage ,
                            Toast.LENGTH_SHORT).show();
                }
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
            /*EditText date = (EditText)getActivity().findViewById(R.id.iqamaexpireedit);

            date.setText(day + "/" + (month + 1) + "/" + year);*/


            //dateprintgiven=day + "/" + (month + 1) + "/" + year;
            dateprintgiven=year + "-" + (month + 1) + "-" + day;
            if(iii==0){
                txtLicenseExpire.setText(dateprintgiven);
            }
            if(iii==1){
                txtLicenseRenewal.setText(dateprintgiven);
            }
            if(iii==2){
                txtMvpiExpire.setText(dateprintgiven);
            }
            dateusergiven = year+"-"+(month+1)+"-"+day;
            //dateselected[iii]=dateusergiven;
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

    private ArrayList<SearchResultsBus> GetSearchResults(){
        ArrayList<SearchResultsBus> results = new ArrayList<SearchResultsBus>();
        for (int i=0;i<busidfromserver.size();i++){
            SearchResultsBus sr1 = new SearchResultsBus();
            /*sr1.setBusId("1234");
            sr1.setRouteName("HYD-KOL");
            sr1.setChassisNumber("1233456");
            sr1.setPlateNumber("AP 10 AS 1234");
            sr1.setFahas("AP123");
            sr1.setPositionsdrop(i);*/
            sr1.setBusId(busidfromserver.get(i));
            sr1.setRouteName(busnamefromserver.get(i));
            if(chassisnumberfromserver.get(i).equalsIgnoreCase("null")){
                sr1.setChassisNumber("-");
            }
            else{
                sr1.setChassisNumber(chassisnumberfromserver.get(i));
            }
            sr1.setPlateNumber(platenumberfromserver.get(i));
            if(fahasfromserver.get(i).equalsIgnoreCase("null")){
                sr1.setFahas("-");
            }
            else{
                sr1.setFahas(fahasfromserver.get(i));
            }
            sr1.setPositionsdrop(i);
            results.add(sr1);
        }

        return results;
    }

    public void getpopup(View view){
        initiatePopupWindowbus();
    }

    private void initiatePopupWindowbus() {
        try {
            busnameintoserver="null";
            chassisnumberintoserver="null";
            platenumberintoserver="null";
            fahasintoserver="null";
            selectedspinneritemintoserver="null";
            licensebusintoserver="null";
            mvpiintoserver="null";
            licenseexpiredateintoserver="null";
            licenserenewaldateintoserver="null";
            mvpiexpiredateintoserver="null";

// We need to get the instance of the LayoutInflater
           /* LayoutInflater inflater1 = (LayoutInflater) Bus_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/
            LayoutInflater inflater = (LayoutInflater) Bus_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_bus,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            layout_MainMenu.getForeground().setAlpha( 200);

            busnameedit=(EditText)layout.findViewById(R.id.busnameedit);
            chassisnumberedit=(EditText)layout.findViewById(R.id.chassisnoedit);
            platenumberedit=(EditText)layout.findViewById(R.id.platenameedit);
            fahasedit=(EditText)layout.findViewById(R.id.fahasedit);

            txtLicenseExpire=(EditText)layout.findViewById(R.id.licenseexpirebusedit);
            txtLicenseExpire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=0;
                        showTruitonDatePickerDialog(v);
                }
            });
            txtLicenseRenewal=(EditText)layout.findViewById(R.id.licenserenewalbusedit);
            txtLicenseRenewal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=1;
                    showTruitonDatePickerDialog(v);
                }
            });
            txtMvpiExpire=(EditText)layout.findViewById(R.id.mvpiexpireedit);
            txtMvpiExpire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=2;
                    showTruitonDatePickerDialog(v);
                }
            });

            bustypelistspinner=(Spinner) layout.findViewById(R.id.selectbustypespinnerdriver);
            String[] busitems=new String[4];
            busitems[0]=getResources().getString(R.string.sj_select_bus_type);
            busitems[1]="10 Seater";
            busitems[2]="20 Seater";
            busitems[3]="30 Seater";
            ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(Bus_Creation_Navigation.this, android.R.layout.simple_spinner_item, busitems) {
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
            bustypelistspinner.setAdapter(adapterbus);
            bustypelistspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //routebusnonavigation=item;
                    selectedspinneritemintoserver=item;
                    Log.e("spinner selected",item);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            btnAddDrivers=(Button)layout.findViewById(R.id.btn_add_popup_bus);
            btnAddDrivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addBusMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_bus);
            btnClosePopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layout_MainMenu.getForeground().setAlpha( 0);
                    pwindo.dismiss();
                }
            });
            //Log.e("Lets Check",busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")");
            txtuploadlicencetxt=(TextView)layout.findViewById(R.id.uploadlicensebustext);
            txtuploadmvpitxt=(TextView)layout.findViewById(R.id.uploadnamemvpitext);

            txtuploadlicensebusbutton=(Button)layout.findViewById(R.id.btn_uploadlicensebus);
            txtuploadlicensebusbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=1;
                    a[1]=0;
                    uploadfirst();
                }
            });
            txtuploadmvpibutton=(Button)layout.findViewById(R.id.btn_uploadmvpi);
            txtuploadmvpibutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=0;
                    a[1]=1;
                    uploadfirst();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initiateeditPopupWindowbus(final int rownumber) {
        try {
            busnameintoserver="null";
            chassisnumberintoserver="null";
            platenumberintoserver="null";
            fahasintoserver="null";
            selectedspinneritemintoserver="null";
            licensebusintoserver="null";
            mvpiintoserver="null";
            licenseexpiredateintoserver="null";
            licenserenewaldateintoserver="null";
            mvpiexpiredateintoserver="null";

// We need to get the instance of the LayoutInflater
           /* LayoutInflater inflater1 = (LayoutInflater) Bus_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout1 = inflater1.inflate(R.layout.fadepopup,
                    (ViewGroup) findViewById(R.id.fadePopup));
            final PopupWindow fadePopup = new PopupWindow(layout1, Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels, false);
            fadePopup.showAtLocation(layout1, Gravity.NO_GRAVITY, 0, 0);*/
            LayoutInflater inflater = (LayoutInflater) Bus_Creation_Navigation.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.screen_popup_bus,
                    (ViewGroup) findViewById(R.id.popup_element));
            pwindo = new PopupWindow(layout, Resources.getSystem().getDisplayMetrics().widthPixels-150, WindowManager.LayoutParams.WRAP_CONTENT, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            pwindo.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            layout_MainMenu.getForeground().setAlpha( 200);

            busnameedit=(EditText)layout.findViewById(R.id.busnameedit);
            chassisnumberedit=(EditText)layout.findViewById(R.id.chassisnoedit);
            platenumberedit=(EditText)layout.findViewById(R.id.platenameedit);
            fahasedit=(EditText)layout.findViewById(R.id.fahasedit);

            if(!chassisnumberfromserver.get(rownumber).equalsIgnoreCase("null")){
                chassisnumberedit.setText(chassisnumberfromserver.get(rownumber));
            }
            if(!fahasfromserver.get(rownumber).equalsIgnoreCase("null")){
                fahasedit.setText(fahasfromserver.get(rownumber));
            }
            busnameedit.setText(busnamefromserver.get(rownumber));
            platenumberedit.setText(platenumberfromserver.get(rownumber));

            txtLicenseExpire=(EditText)layout.findViewById(R.id.licenseexpirebusedit);
            if(!buslicenseexpiredatefromserver.get(rownumber).equalsIgnoreCase("null")){
                txtLicenseExpire.setText(buslicenseexpiredatefromserver.get(rownumber));
            }
            txtLicenseExpire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=0;
                    showTruitonDatePickerDialog(v);
                }
            });
            txtLicenseRenewal=(EditText)layout.findViewById(R.id.licenserenewalbusedit);
            if(!buslicenserenewaldatefromserver.get(rownumber).equalsIgnoreCase("null")){
                txtLicenseRenewal.setText(buslicenserenewaldatefromserver.get(rownumber));
            }
            txtLicenseRenewal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=1;
                    showTruitonDatePickerDialog(v);
                }
            });
            txtMvpiExpire=(EditText)layout.findViewById(R.id.mvpiexpireedit);
            if(!mvpiexpiredatefromserver.get(rownumber).equalsIgnoreCase("null")){
                txtMvpiExpire.setText(mvpiexpiredatefromserver.get(rownumber));
            }
            txtMvpiExpire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iii=2;
                    showTruitonDatePickerDialog(v);
                }
            });

            bustypelistspinner=(Spinner) layout.findViewById(R.id.selectbustypespinnerdriver);
            String[] busitems=new String[4];
            busitems[0]=getResources().getString(R.string.sj_select_bus_type);
            busitems[1]="10 Seater";
            busitems[2]="20 Seater";
            busitems[3]="30 Seater";
            ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(Bus_Creation_Navigation.this, android.R.layout.simple_spinner_item, busitems) {
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
            bustypelistspinner.setAdapter(adapterbus);
            bustypelistspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //routebusnonavigation=item;
                    selectedspinneritemintoserver=item;
                    Log.e("spinner selected",item);
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if(bustypefromserver.get(rownumber).equalsIgnoreCase("null")){

            }
            else{
                int spinnerrow=0;
                if(bustypefromserver.get(rownumber).equalsIgnoreCase("10 Seater")){
                    spinnerrow=1;
                }
                else if(bustypefromserver.get(rownumber).equalsIgnoreCase("20 Seater")){
                    spinnerrow=2;
                }
                else if(bustypefromserver.get(rownumber).equalsIgnoreCase("30 Seater")){
                    spinnerrow=3;
                }
                bustypelistspinner.setSelection(spinnerrow);
            }

            btnAddDrivers=(Button)layout.findViewById(R.id.btn_add_popup_bus);
            btnAddDrivers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editbusid=Integer.parseInt(busidfromserver.get(rownumber));
                    addBusMethod();
                }
            });
            Button btnClosePopup=(Button)layout.findViewById(R.id.btn_close_popup_bus);
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
            //Log.e("Lets Check",busroutename.get(checkinttag)+"("+busvechileno.get(checkinttag)+")");

            txtuploadlicencetxt=(TextView)layout.findViewById(R.id.uploadlicensebustext);
            txtuploadmvpitxt=(TextView)layout.findViewById(R.id.uploadnamemvpitext);
            if(!buslicensefromserver.get(rownumber).equalsIgnoreCase("null")){
                txtuploadlicencetxt.setText(buslicensefromserver.get(rownumber));
                licensebusintoserver=buslicensefromserver.get(rownumber);
            }
            if(!mvpiuploadfromserver.get(rownumber).equalsIgnoreCase("null")){
                txtuploadmvpitxt.setText(mvpiuploadfromserver.get(rownumber));
                mvpiintoserver=mvpiuploadfromserver.get(rownumber);
            }


            txtuploadlicensebusbutton=(Button)layout.findViewById(R.id.btn_uploadlicensebus);
            txtuploadlicensebusbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=1;
                    a[1]=0;
                    uploadfirst();
                }
            });
            txtuploadmvpibutton=(Button)layout.findViewById(R.id.btn_uploadmvpi);
            txtuploadmvpibutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("+++","yes clicked");
                    a[0]=0;
                    a[1]=1;
                    uploadfirst();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addBusMethod(){
        busnameintoserver=busnameedit.getText().toString();
        chassisnumberintoserver=chassisnumberedit.getText().toString();
        platenumberintoserver=platenumberedit.getText().toString();
        fahasintoserver=fahasedit.getText().toString();
        licenseexpiredateintoserver=txtLicenseExpire.getText().toString();
        licenserenewaldateintoserver=txtLicenseRenewal.getText().toString();
        mvpiexpiredateintoserver=txtMvpiExpire.getText().toString();
        if(busnameintoserver.isEmpty()){
            Toast.makeText(Bus_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_bus_name),
                    Toast.LENGTH_LONG).show();
        }
        else if(selectedspinneritemintoserver.equalsIgnoreCase(getResources().getString(R.string.sj_select_bus_type))){
            Toast.makeText(Bus_Creation_Navigation.this, getResources().getString(R.string.sj_please_select_the_bus_type),
                    Toast.LENGTH_LONG).show();
        }
        else if(platenumberintoserver.isEmpty()){
            Toast.makeText(Bus_Creation_Navigation.this, getResources().getString(R.string.sj_please_enter_the_plate_number),
                    Toast.LENGTH_LONG).show();
        }
        else{
            if(chassisnumberintoserver.isEmpty()){
                chassisnumberintoserver="null";
            }
            if(fahasintoserver.isEmpty()){
                fahasintoserver="null";
            }
            if(chassisnumberintoserver.isEmpty()){
                chassisnumberintoserver="null";
            }
            if(licenseexpiredateintoserver.isEmpty()){
                licenseexpiredateintoserver="null";
            }
            if(licenserenewaldateintoserver.isEmpty()){
                licenserenewaldateintoserver="null";
            }
            if(mvpiexpiredateintoserver.isEmpty()){
                mvpiexpiredateintoserver="null";
            }
            Log.e("busname: ",""+busnameintoserver);
            Log.e("spinner: ",""+selectedspinneritemintoserver);
            Log.e("chassis: ",""+chassisnumberintoserver);
            Log.e("plate: ",""+platenumberintoserver);
            Log.e("fahas: ",""+fahasintoserver);
            Log.e("licenseup: ",""+licensebusintoserver);
            Log.e("licenseexpire: ",""+licenseexpiredateintoserver);
            Log.e("licenserenewal: ",""+licenserenewaldateintoserver);
            Log.e("mvipupload/; ",""+mvpiintoserver);
            Log.e("mvipexpire: ",""+mvpiexpiredateintoserver);
            if(editbusid==0){
                new addbusdetailstoserver().execute();
            }
            else {
                new addeditbusdetailstoserver().execute();
            }


        }


    }


    class addbusdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Bus_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"BusList_api/addBuses/bus_name/"+ URLEncoder.encode(busnameintoserver,"UTF-8")+"/bus_type/"+URLEncoder.encode(selectedspinneritemintoserver,"UTF-8")+
                        "/chassis_number/"+URLEncoder.encode(chassisnumberintoserver,"UTF-8")+"/plate_number/"+URLEncoder.encode(platenumberintoserver,"UTF-8")+"/fahas/"+URLEncoder.encode(fahasintoserver,"UTF-8")+
                        "/bus_license/"+URLEncoder.encode(licensebusintoserver,"UTF-8")+"/license_expiry/"+URLEncoder.encode(licenseexpiredateintoserver,"UTF-8")+"/license_renewal_date/"+URLEncoder.encode(licenserenewaldateintoserver,"UTF-8")+"/MVPI/"+URLEncoder.encode(mvpiintoserver,"UTF-8")+
                        "/MVPI_expiry/"+URLEncoder.encode(mvpiexpiredateintoserver,"UTF-8");

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
                                //Toast.makeText(Bus_Creation_Navigation.this, "Driver Added Successfully", Toast.LENGTH_SHORT).show();
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


    class addeditbusdetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Bus_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();

                url= Config.ip+"BusList_api/editBuses/bus_name/"+ URLEncoder.encode(busnameintoserver,"UTF-8")+"/bus_type/"+URLEncoder.encode(selectedspinneritemintoserver,"UTF-8")+
                        "/chassis_number/"+URLEncoder.encode(chassisnumberintoserver,"UTF-8")+"/plate_number/"+URLEncoder.encode(platenumberintoserver,"UTF-8")+"/fahas/"+URLEncoder.encode(fahasintoserver,"UTF-8")+
                        "/bus_license/"+URLEncoder.encode(licensebusintoserver,"UTF-8")+"/license_expiry/"+URLEncoder.encode(licenseexpiredateintoserver,"UTF-8")+"/license_renewal_date/"+URLEncoder.encode(licenserenewaldateintoserver,"UTF-8")+"/MVPI/"+URLEncoder.encode(mvpiintoserver,"UTF-8")+
                        "/MVPI_expiry/"+URLEncoder.encode(mvpiexpiredateintoserver,"UTF-8")+"/bus_id/"+editbusid;

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
                                //Toast.makeText(Bus_Creation_Navigation.this, "Driver Added Successfully", Toast.LENGTH_SHORT).show();
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

    class getBusDeleteServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Bus_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"BusList_api/deleteBus/bus_id/"+deletebusid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"BusList_api/deleteBus/bus_id/"+deletebusid,Jsonfunctions.GET);

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
                    if(a[0]==1){
                        Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        String curFileName = data.getStringExtra("GetFileName");
                        String curFileName1 = data.getStringExtra("GetPath");
                        txtuploadlicencetxt.setText(curFileName);
                        String iqamavaluepath=curFileName1+"/"+curFileName;
                        a[0]=0;
                        a[1]=0;
                        checkwhichchoose[0]=1;
                        checkwhichchoose[1]=0;
                        uploadIqamaTheard(iqamavaluepath);
                    }
                    else if(a[1]==1){
                        Log.e("+++",data.getStringExtra("GetFileName")+"+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        String curFileName = data.getStringExtra("GetFileName");
                        String curFileName1 = data.getStringExtra("GetPath");
                        txtuploadmvpitxt.setText(curFileName);
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
        dialog = ProgressDialog.show(Bus_Creation_Navigation.this, "", getResources().getString(R.string.sj_uploading_file), true);

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
                cc=new CommonClass(Bus_Creation_Navigation.this);
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
                                licensebusintoserver=ResObj.getString("file_name");
                                Log.e("into",""+licensebusintoserver);
                            }
                            else if(checkwhichchoose[1]==1){
                                mvpiintoserver=ResObj.getString("file_name");
                                Log.e("into",""+mvpiintoserver);
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

                            Toast.makeText(Bus_Creation_Navigation.this, getResources().getString(R.string.sj_upload_complete), Toast.LENGTH_SHORT).show();
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

    class getBusListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(Bus_Creation_Navigation.this, getResources().getString(R.string.sj_please_wait),
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
                                    Log.e("+++",obj.getString("bus_type"));
                                    String busType=obj.getString("bus_type");
                                    bustypefromserver.add(busType);
                                    Log.e("+++",obj.getString("chassis_number"));
                                    String busChassis=obj.getString("chassis_number");
                                    chassisnumberfromserver.add(busChassis);
                                    Log.e("+++",obj.getString("plate_number"));
                                    String busPlatenuber=obj.getString("plate_number");
                                    platenumberfromserver.add(busPlatenuber);
                                    Log.e("+++",obj.getString("fahas"));
                                    String busFahas=obj.getString("fahas");
                                    fahasfromserver.add(busFahas);
                                    Log.e("+++",obj.getString("bus_from"));
                                    String busFrom=obj.getString("bus_from");
                                    busfromfromserver.add(busFrom);
                                    Log.e("+++",obj.getString("bus_to"));
                                    String busTo=obj.getString("bus_to");
                                    bustofromserver.add(busTo);
                                    Log.e("+++",obj.getString("pickup_route_id"));
                                    String busPickup=obj.getString("pickup_route_id");
                                    buspickuprouteidfromserver.add(busPickup);
                                    Log.e("+++",obj.getString("drop_route_id"));
                                    String busDrop=obj.getString("drop_route_id");
                                    busdroprouteidfromserver.add(busDrop);
                                    Log.e("+++",obj.getString("bus_license_upload"));
                                    String busLicense=obj.getString("bus_license_upload");
                                    buslicensefromserver.add(busLicense);
                                    Log.e("+++",obj.getString("license_expiry_date"));
                                    String busLicenseexpiredate=obj.getString("license_expiry_date");
                                    buslicenseexpiredatefromserver.add(busLicenseexpiredate);
                                    Log.e("+++",obj.getString("license_renewal_date"));
                                    String busLicenseRenewaldate=obj.getString("license_renewal_date");
                                    buslicenserenewaldatefromserver.add(busLicenseRenewaldate);
                                    Log.e("+++",obj.getString("MVPI_upload"));
                                    String busMvpi=obj.getString("MVPI_upload");
                                    mvpiuploadfromserver.add(busMvpi);
                                    Log.e("+++",obj.getString("MVPI_expiry_date"));
                                    String busMvpiexpireadate=obj.getString("MVPI_expiry_date");
                                    mvpiexpiredatefromserver.add(busMvpiexpireadate);

                                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                                            "yyyy-MM-dd");
                                    try {
                                        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1");
                                        if(busLicenseexpiredate.equalsIgnoreCase("null")||busLicenseexpiredate.isEmpty()){
                                            licenseexpiredatenumber.add("%%%%%");
                                            licenseexpirechecknumber.add(0);
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@11");
                                        }
                                        else{
                                            Date oldDate = dateFormat.parse(busLicenseexpiredate);
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
                                            licenseexpiredatenumber.add(days+"");
                                            if(days>=-2){
                                                licenseexpirechecknumber.add(1);
                                            }
                                            else{
                                                licenseexpirechecknumber.add(0);
                                            }
                                        }
                                    } catch (ParseException e) {

                                        e.printStackTrace();
                                    }

                                    try {
                                        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1");
                                        if(busLicenseRenewaldate.equalsIgnoreCase("null")||busLicenseRenewaldate.isEmpty()){
                                            licenserenewaldatenumber.add("%%%%%");
                                            licenserenewalchecknumber.add(0);
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@11");
                                        }
                                        else{
                                            Date oldDate = dateFormat.parse(busLicenseRenewaldate);
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
                                            licenserenewaldatenumber.add(days+"");
                                            if(days>=-2){
                                                licenserenewalchecknumber.add(1);
                                            }
                                            else{
                                                licenserenewalchecknumber.add(0);
                                            }
                                        }
                                    } catch (ParseException e) {

                                        e.printStackTrace();
                                    }

                                    try {
                                        Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1");
                                        if(busMvpiexpireadate.equalsIgnoreCase("null")||busMvpiexpireadate.isEmpty()){
                                            mvpiexpiredatenumber.add("%%%%%");
                                            mvpiexpirechecknumber.add(0);
                                            Log.e("@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@11");
                                        }
                                        else{
                                            Date oldDate = dateFormat.parse(busMvpiexpireadate);
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
                                            mvpiexpiredatenumber.add(days+"");
                                            if(days>=-2){
                                                mvpiexpirechecknumber.add(1);
                                            }
                                            else{
                                                mvpiexpirechecknumber.add(0);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(Bus_Creation_Navigation.this, Hawkeye_navigation.class));
            finish();
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


    public class MyCustomBasedBusAdaper extends BaseAdapter {
        private ArrayList<SearchResultsBus> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedBusAdaper(Context context, ArrayList<SearchResultsBus> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_bus, null);
                holder = new ViewHolder();
                holder.txtbusid = (TextView) convertView.findViewById(R.id.busidtext);
                holder.txtbusid.setTypeface(tfRobo);
                holder.txtroutename = (TextView) convertView.findViewById(R.id.routenamebustext);
                holder.txtroutename.setTypeface(tfRobo);
                holder.txtchassisno = (TextView) convertView.findViewById(R.id.chassisnumbertext);
                holder.txtchassisno.setTypeface(tfRobo);
                holder.txtplateno = (TextView) convertView.findViewById(R.id.platenotext);
                holder.txtplateno.setTypeface(tfRobo);
                holder.txtfasas = (TextView) convertView.findViewById(R.id.fahastext);
                holder.txtfasas.setTypeface(tfRobo);
                holder.txtbusoptions=(Spinner)convertView.findViewById(R.id.busoption);

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
                holder.txtbusoptions.setAdapter(adapterdriverdropown);

            /*holder.txtdriiveroptions = (TextView) convertView.findViewById(R.id.driveroption);
            holder.txtdriiveroptions.setTypeface(tfRobo);*/
                //holder.txtcheckout = (ImageView) convertView.findViewById(R.id.attenddencecheckoubox);
                //holder.txtcheckout.setTypeface(tfRobo);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtbusoptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    String item = parent.getItemAtPosition(position1).toString();
                    //routedrivernamenavigation=item;
                    //routeattendenceselected=item;
                    Log.e("spinner selected",item);
                    Log.e("Row",""+searchArrayList.get(position).getPositionsdrop());
                    if(item.equalsIgnoreCase("Action")){

                    }
                    else if(item.equalsIgnoreCase("Edit")){
                        initiateeditPopupWindowbus(position);
                    }
                    if(item.equalsIgnoreCase("Delete")){
                        deletebusid=Integer.parseInt(busidfromserver.get(position));
                        new getBusDeleteServer().execute();
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtroutename.setText(searchArrayList.get(position).getRouteName());
            holder.txtbusid.setText(searchArrayList.get(position).getBusId());
            holder.txtchassisno.setText(searchArrayList.get(position).getChassisNumber());
            holder.txtplateno.setText(searchArrayList.get(position).getPlateNumber());
            holder.txtfasas.setText(searchArrayList.get(position).getFahas());

            int neutral=0,notneutral=0;

            if(licenseexpiredatenumber.get(position).equalsIgnoreCase("%%%%%")){
                neutral=neutral+1;
            }
            else if(Integer.parseInt(licenseexpiredatenumber.get(position))>=-2){
                notneutral=notneutral+1;
            }

            if(licenserenewaldatenumber.get(position).equalsIgnoreCase("%%%%%")){
                neutral=neutral+1;
            }
            else if(Integer.parseInt(licenserenewaldatenumber.get(position))>=-2){
                notneutral=notneutral+1;
            }

            if(mvpiexpiredatenumber.get(position).equalsIgnoreCase("%%%%%")){
                neutral=neutral+1;
            }
            else if(Integer.parseInt(mvpiexpiredatenumber.get(position))>=-2){
                notneutral=notneutral+1;
            }

            if(neutral==3){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchangebus);
                layoutrow.setBackgroundResource(R.drawable.spinnerborder);
            }
            else if(notneutral==1||notneutral==2||notneutral==3){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchangebus);
                layoutrow.setBackgroundResource(R.drawable.tablecolorbigred);
                LinearLayout firstview =(LinearLayout)convertView.findViewById(R.id.firstlinebus);
                firstview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout secondview =(LinearLayout)convertView.findViewById(R.id.secondlinebus);
                secondview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout thirdview =(LinearLayout)convertView.findViewById(R.id.thirdlinebus);
                thirdview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout fourthview =(LinearLayout)convertView.findViewById(R.id.fourthlinebus);
                fourthview.setBackgroundResource(R.drawable.tablerowcolorred);
                LinearLayout fifthview =(LinearLayout)convertView.findViewById(R.id.fifthlinebus);
                fifthview.setBackgroundResource(R.drawable.tablerowcolorred);
            }
            else if(notneutral==0){
                LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.colorlistchangebus);
                layoutrow.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout firstview =(LinearLayout)convertView.findViewById(R.id.firstlinebus);
                firstview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout secondview =(LinearLayout)convertView.findViewById(R.id.secondlinebus);
                secondview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout thirdview =(LinearLayout)convertView.findViewById(R.id.thirdlinebus);
                thirdview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout fourthview =(LinearLayout)convertView.findViewById(R.id.fourthlinebus);
                fourthview.setBackgroundResource(R.drawable.spinnerborder);
                LinearLayout fifthview =(LinearLayout)convertView.findViewById(R.id.fifthlinebus);
                fifthview.setBackgroundResource(R.drawable.spinnerborder);
            }




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
            TextView txtbusid;
            TextView txtroutename;
            TextView txtchassisno;
            TextView txtplateno;
            TextView txtfasas;
            Spinner txtbusoptions;
        }
    }

}
