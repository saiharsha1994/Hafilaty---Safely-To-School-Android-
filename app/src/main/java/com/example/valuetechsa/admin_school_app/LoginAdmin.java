package com.example.valuetechsa.admin_school_app;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Locale;

public class LoginAdmin extends AppCompatActivity {
    private EditText userId;
    private EditText password;
    private Button sign_in,forgot_password;
    String USERID = "", PASSWORD = "",respons="",TOKEN="";
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    CommonClass cc;
    boolean connected = false;
    Thread fivemins;
    boolean threadloop=true;
    String adminid,adminname,schoolcordinate;
    String speedlimit;
    final int MY_PERMISSIONS_REQUEST_Camera=1;
    final int MY_PERMISSIONS_REQUEST_Coarse_Location=2;
    final int MY_PERMISSIONS_REQUEST_Fine_Location=3;
    final int MY_PERMISSIONS_REQUEST_Write_External_Storage=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        dbh = new DatabaseAdapter(this);
        db = dbh.getWritableDatabase();
        cc=new CommonClass(this);

        Cursor cur=db.rawQuery("SELECT * FROM language", null);
        cur.moveToFirst();
        if(cur.getCount()==0){
            showAlertForLanguage();
        }

        fivemins=new Thread(new Runnable() {

            @Override
            public void run() {
                Log.e("is it working","let us see"+threadloop);
                while (threadloop)
                    try
                    {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() // start actions in UI thread
                        {

                            @Override
                            public void run()
                            {
                                if(threadloop){
                                    Log.e("check every 5 sec","++++++++++++++++++++++++++++++++++++++++++");
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

                                    Log.e("connection","))))))))))))))))))))))))))))))))))))))))))))))))))))))"+connected);
                                    /*if(connected==false){
                                        Toast.makeText(LoginAdmin.this, "Please Check Your Net Connection" ,
                                                Toast.LENGTH_LONG).show();
                                    }*/
                                }
                            }
                        });
                    }
                    catch (InterruptedException e)
                    {
                        // ooops
                    }
            }
        });
        fivemins.start();


        ActionBar action=getSupportActionBar();
        Typeface tfRobo = Typeface.createFromAsset(LoginAdmin.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        Typeface tfAdam = Typeface.createFromAsset(LoginAdmin.this.getAssets(), "fonts/ADAM.CG PRO.OTF");
        Button btnLogin=(Button)findViewById(R.id.loginlogin);
        Button btnForgotpassword=(Button)findViewById(R.id.forgotpassword);
        TextView tran=(TextView)findViewById(R.id.Transport);
        TextView admi=(TextView)findViewById(R.id.admin);
        EditText user=(EditText)findViewById(R.id.useridlogin);
        EditText pass=(EditText)findViewById(R.id.passwordlogin);

        btnForgotpassword.setText(R.string.sb_forgot_password);
        btnLogin.setText(R.string.sb_login);
        tran.setText(R.string.stv_transport);
        admi.setText(R.string.stv_admin);
        user.setHint(R.string.stv_username);
        pass.setHint(R.string.stv_password);

        user.setTypeface(tfRobo);
        tran.setTypeface(tfRobo);
        admi.setTypeface(tfRobo);
        pass.setTypeface(tfRobo);
        btnLogin.setTypeface(tfAdam);
        btnForgotpassword.setTypeface(tfAdam);
        action.hide();
        getPermissions();
        init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public void getPermissions(){


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);

            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_Camera);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_Coarse_Location);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_Fine_Location);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_Write_External_Storage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_Camera: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    cc.showAlertWithTitle(getResources().getString(R.string.alert), getResources().getString(R.string.permission_required));
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_Write_External_Storage: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    cc.showAlertWithTitle(getResources().getString(R.string.alert), getResources().getString(R.string.permission_required));
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_Fine_Location: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    cc.showAlertWithTitle(getResources().getString(R.string.alert), getResources().getString(R.string.permission_required));
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_Coarse_Location: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    cc.showAlertWithTitle(getResources().getString(R.string.alert), getResources().getString(R.string.permission_required));
                }
                return;
            }

        }
    }

    public void showAlertForLanguage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginAdmin.this);

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
                Intent intent = new Intent(LoginAdmin.this, LoginAdmin.class);
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
                Intent intent = new Intent(LoginAdmin.this, LoginAdmin.class);
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
        LoginAdmin.this.getResources().updateConfiguration(config,
                LoginAdmin.this.getResources().getDisplayMetrics());
    }


    public void init(){

        MyFirebaseInstanceIDService.getToken();

        try {
            Cursor cur=db.rawQuery("SELECT * FROM OneTimeLogin", null);
            cur.moveToFirst();
            if(cur.getCount()!=0){
                String nameuser=cur.getString(1);
                String passuser=cur.getString(2);
                threadloop=false;
                Intent intent = new Intent(LoginAdmin.this, Hawkeye_navigation.class);
                startActivity(intent);
                finish();

            }
        }
        catch (Exception e){

        }


        userId = (EditText) findViewById(R.id.useridlogin);
        password = (EditText) findViewById(R.id.passwordlogin);

        sign_in = (Button) findViewById(R.id.loginlogin);
        forgot_password=(Button) findViewById(R.id.forgotpassword);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAdmin.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USERID=userId.getText().toString();
                try {
                    PASSWORD = password.getText().toString();
                }catch (Exception e){
                    e.printStackTrace();;
                }


                //new sendDataToServer().execute();
                if(USERID.isEmpty() ){
                    Toast.makeText(LoginAdmin.this,getResources().getString(R.string.sj_username_should_not_be_empty),Toast.LENGTH_LONG).show();

                }
                else if(PASSWORD.isEmpty()){
                    Toast.makeText(LoginAdmin.this,getResources().getString(R.string.sj_password_should_not_be_empty),Toast.LENGTH_LONG).show();
                }
                else if(connected==false){
                    Toast.makeText(LoginAdmin.this,getResources().getString(R.string.sj_check_internet_connection),Toast.LENGTH_LONG).show();
                }
                else{

                    new sendDataToServer().execute();
                }

            }

        });

    }

    private class sendDataToServer extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog authProgressDialog;
        @Override
        protected void onPreExecute()
        {
            authProgressDialog = ProgressDialog.show(LoginAdmin.this, "",getResources().getString(R.string.sj_please_wait), true, false);
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                postData();
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

            authProgressDialog.dismiss();
            try
            {
                if(respons.equals("1"))
                {
                    ContentValues values = new ContentValues();
                    values.put("EmailUser", USERID);
                    values.put("PasswordUser", PASSWORD);
                    values.put("AdminId", adminid);
                    values.put("AdminName", adminname);
                    values.put("SchoolLocation", schoolcordinate);

                    db.insert("OneTimeLogin", null, values);
                    threadloop=false;
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginAdmin.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("speed_limit",speedlimit);
                    editor.apply();

                    Intent i=new Intent(LoginAdmin.this,Hawkeye_navigation.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginAdmin.this,getResources().getString(R.string.sj_invalid_username_or_password)+" "+PASSWORD+" "+USERID, Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void postData() throws JSONException
    {
        try
        {
            ApiCrypter objEnc=new ApiCrypter();
            Jsonfunctions sh = new Jsonfunctions();
            TOKEN=SharedPrefManager.getInstance(this).getDeviceToken();

            Log.e("url", Config.ip+"Transport_Admin_Login_api/Login/Email/"+USERID+"/Password/"+PASSWORD+"/GCM_RegId/"+TOKEN);
            String jsonStr = sh.makeServiceCall(Config.ip+"Transport_Admin_Login_api/Login/Email/"+USERID+"/Password/"+PASSWORD+"/GCM_RegId/"+TOKEN, Jsonfunctions.GET);

            if (jsonStr != null)
            {
                try
                {
                    JSONObject Jobj = new JSONObject(jsonStr);
                    respons=Jobj.getString("responsecode");
                    Log.e("rescode is : +",respons);

                    if(Jobj.getString("responsecode").equals("1"))
                    {
                        JSONArray jsonArray = Jobj.getJSONArray("result_arr");

                        for (int j = 0; j < jsonArray.length(); j++)
                        {
                            JSONObject obj = jsonArray.getJSONObject(j);
                            adminid=obj.getString("trans_admin_id");
                            adminname=obj.getString("name");
                            schoolcordinate=obj.getString("school_location");
                            speedlimit=obj.getString("speed_limit");
                            Log.e("++++",adminid);
                            Log.e("++++",adminname);
                            Log.e("++++",schoolcordinate);
                            Log.e("++++",speedlimit);
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
}
