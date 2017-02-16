package com.example.valuetechsa.admin_school_app;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.valuetechsa.admin_school_app.DB.DatabaseAdapter;
import com.example.valuetechsa.admin_school_app.commonclass.CommonClass;
import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;


public class ForgotPassword extends Activity {
    SQLiteDatabase db;
    DatabaseAdapter dbh;
    CommonClass cc;
    EditText edtEmail,edtCode,edtPswd;
    Button btnSubmit,btnPswdSubmit,btnBack;
    String Email,respons,Code,newPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        dbh = new DatabaseAdapter(this);
        db = dbh.getWritableDatabase();
        cc=new CommonClass(this);


        Typeface tfRobo = Typeface.createFromAsset(ForgotPassword.this.getAssets(), "fonts/ROBOTO-LIGHT.TTF");

        edtEmail=(EditText)findViewById(R.id.edtEmailInFP);
        edtCode=(EditText)findViewById(R.id.edtCodeInFP);
        edtPswd=(EditText)findViewById(R.id.edtPasswordInFP);

        edtEmail.setTypeface(tfRobo);
        edtEmail.setTextSize(20);
        edtCode.setTypeface(tfRobo);
        edtCode.setTextSize(20);
        edtPswd.setTypeface(tfRobo);
        edtPswd.setTextSize(20);

        btnSubmit=(Button)findViewById(R.id.btnSubmitInFP);
        btnPswdSubmit=(Button)findViewById(R.id.btnSubmitNewPasswordInFP);

        btnSubmit.setTypeface(tfRobo);
        btnSubmit.setTextSize(25);
        btnPswdSubmit.setTypeface(tfRobo);
        btnPswdSubmit.setTextSize(25);

        check();

        btnSubmit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Email=edtEmail.getText().toString();
                if(Email.length()!=0 )
                {
                    String emailPattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    if(!Email.matches(emailPattern))
                    {
                        Toast.makeText(ForgotPassword.this, "Email is invalid",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(cc.isOnline())
                        {
                            new sendDataToServer().execute();
                        }
                        else
                        {
                            cc.showAlertForInternet();
                        }
                    }
                }
            }
        });

        btnPswdSubmit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Email=edtEmail.getText().toString();
                Code=edtCode.getText().toString();
                newPassword=edtPswd.getText().toString();

                if(Email.length()!=0 )
                {
                    String emailPattern="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    if(!Email.matches(emailPattern))
                    {
                        Toast.makeText(ForgotPassword.this, "Email is invalid",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(Code.length()!=0 && newPassword.length()!=0)
                        {

                            if(cc.isOnline())
                            {
                                new RequestNewPasswordServer().execute();
                            }
                            else
                            {
                                cc.showAlertForInternet();
                            }
                        }
                    }
                }
            }
        });
    }

    void check()
    {
        Cursor cur=db.rawQuery("SELECT * FROM ForgotPassword WHERE Status='Requested'", null);
        if(cur.getCount()!=0)
        {
            btnSubmit.setVisibility(View.GONE);
            edtEmail.setVisibility(View.VISIBLE);
            edtCode.setVisibility(View.VISIBLE);
            edtPswd.setVisibility(View.VISIBLE);
            btnPswdSubmit.setVisibility(View.VISIBLE);
            cc.showAlertWithTitle("Instruction","Paste the code here which we sent to your e-mail");
        }
        else
        {
            btnSubmit.setVisibility(View.VISIBLE);
            edtEmail.setVisibility(View.VISIBLE);
            edtCode.setVisibility(View.GONE);
            edtPswd.setVisibility(View.GONE);
            btnPswdSubmit.setVisibility(View.GONE);
        }
    }
    private class sendDataToServer extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog authProgressDialog;
        @Override
        protected void onPreExecute()
        {
            authProgressDialog = ProgressDialog.show(ForgotPassword.this, "","Please Wait...", true, false);
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
                    cc.showAlertWithTitle("Thank You!", "We have sent you the password reset code to your e-mail. Kindly paste that code here to change your password");
                    check();
                }
                else
                {
                    Toast.makeText(ForgotPassword.this,"InValid Email or not registered yet", Toast.LENGTH_LONG).show();
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
            Jsonfunctions sh = new Jsonfunctions();
            Log.e("url", Config.ip+"Forgot_Password_api/forgotTransportPassword/Email/"+Email);
            String jsonStr = sh.makeServiceCall(Config.ip+"Forgot_Password_api/forgotTransportPassword/Email/"+Email, Jsonfunctions.GET);
            if (jsonStr != null)
            {
                try
                {
                    JSONObject Jobj = new JSONObject(jsonStr);
                    respons=Jobj.getString("responsecode");

                    if(respons.equals("1"))
                    {
                        db.delete("ForgotPassword", null, null);

                        ContentValues values = new ContentValues();
                        values.put("Email", Email);
                        values.put("Status", "Requested");

                        db.insert("ForgotPassword", null, values);
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

    private class RequestNewPasswordServer extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog authProgressDialog;
        @Override
        protected void onPreExecute()
        {
            authProgressDialog = ProgressDialog.show(ForgotPassword.this, "","Please Wait...", true, false);
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                postNewPassword();
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
                    Toast.makeText(ForgotPassword.this,"Password has been changed", Toast.LENGTH_LONG).show();

                    Intent i=new Intent(ForgotPassword.this,Home_Admin.class);
                    i.putExtra("FROM", "ForgotPassword");
                    startActivity(i);
                    finish();
                    ForgotPassword.this.overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
                }
                else
                {
                    Toast.makeText(ForgotPassword.this,"InValid Email or not registered yet", Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void postNewPassword() throws JSONException
    {
        try
        {
            Jsonfunctions sh = new Jsonfunctions();
            Log.e("url", Config.ip+"Forgot_Password_api/changePasswordTransport/Email/"+Email+"/Auth_Code/"+Code+"/New_Password/"+newPassword);
            String jsonStr = sh.makeServiceCall(Config.ip+"Forgot_Password_api/changePasswordTransport/Email/"+Email+"/Auth_Code/"+Code+"/New_Password/"+newPassword, Jsonfunctions.GET);
            if (jsonStr != null)
            {
                try
                {
                    JSONObject Jobj = new JSONObject(jsonStr);
                    respons=Jobj.getString("responsecode");

                    if(respons.equals("1"))
                    {
                        db.delete("ForgotPassword", null, null);
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


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i=new Intent(ForgotPassword.this,LoginAdmin.class);
        i.putExtra("FROM", "ForgotPassword");
        startActivity(i);
        finish();
        ForgotPassword.this.overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
    }

}

