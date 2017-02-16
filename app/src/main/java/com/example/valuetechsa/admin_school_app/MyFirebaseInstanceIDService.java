package com.example.valuetechsa.admin_school_app;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.commonclass.ApiCrypter;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fasihuddin on 09-Feb-17.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private static String fcmToken="";
    SQLiteDatabase db;
    String nameuser="",passuser="";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //calling the method store token and passing token
        storeToken(refreshedToken);
    }

    public static String getToken() {

        fcmToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Old token: " + fcmToken);

        return fcmToken;
    }

    private void storeToken(String token) {
        //we will save the token in sharedpreferences later
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
        try {
            Cursor cur = db.rawQuery("SELECT * FROM OneTimeLogin", null);
            cur.moveToFirst();
            if (cur.getCount() != 0) {
                nameuser = cur.getString(1);
                passuser = cur.getString(2);
            }
        }
        catch (Exception e){

        }
        if(!(nameuser.isEmpty())&&!(passuser.isEmpty()))
        {
            new sendDataToServer().execute();
        }
    }

    private class sendDataToServer extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute()
        {

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

        }
    }

    public void postData() throws JSONException
    {
        try
        {
            ApiCrypter objEnc=new ApiCrypter();
            Jsonfunctions sh = new Jsonfunctions();
            String TOKEN=SharedPrefManager.getInstance(this).getDeviceToken();

            Log.e("url", Config.ip+"Transport_Admin_Login_api/Login/Email/"+nameuser+"/Password/"+passuser+"/GCM_RegId/"+TOKEN);
            String jsonStr = sh.makeServiceCall(Config.ip+"Transport_Admin_Login_api/Login/Email/"+nameuser+"/Password/"+passuser+"/GCM_RegId/"+TOKEN, Jsonfunctions.GET);

            if (jsonStr != null)
            {
                try
                {
                    JSONObject Jobj = new JSONObject(jsonStr);
                    String respons=Jobj.getString("responsecode");
                    Log.e("rescode is : +",respons);

                    if(Jobj.getString("responsecode").equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"Token updated",Toast.LENGTH_SHORT).show();


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
