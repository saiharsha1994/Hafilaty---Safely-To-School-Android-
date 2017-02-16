package com.example.valuetechsa.admin_school_app;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DriverMerit extends AppCompatActivity {
    String name =" ",driverImage="",route="",mobileno="",speedlimit="",rashdriving="",timemaintaince="",URL="";
    public static final String[] STARNAME = new String[]{
            "fivestar","fourstar","threestar","twostar"
    };

    double speedlimitintvalue,rashdrivingintvalue,timemaintainceintvalue;
    public static final int imageID=0;
    //public static final int trying=Integer.parseInt("halfstar");
    ArrayList<String> namedriver=new ArrayList<String>();
    ArrayList<String> driverImagepng=new ArrayList<String>();
    ArrayList<String> routedriver=new ArrayList<String>();
    ArrayList<String> mobilenodriver=new ArrayList<String>();
    ArrayList<String> speedlimitdriver=new ArrayList<String>();
    ArrayList<String> rashdrivingdriver=new ArrayList<String>();
    ArrayList<String> timemaintaincedriver=new ArrayList<String>();
    String []dodriver=new String[1000];
    String []dodriverimage=new String[1000];
    public static String DriverUploads="http://al-amaanah.com/Tifly_Pro/uploads/driver_image/";
    ImageView imageView,imageViewtester;
    TextView drivername,driverphoneno;
    GridView grid;
    public static int count,changecount=0;
    ProgressDialog progressDialog;
    public static final String[] SPEEDLIMIT=new String[100];
    public static final String[] RASHDRIVING=new String[100];
    public static final String[] TIMEMAINTAINANCE= new String[100];
    public static final String[] DRIVERNAMEARRAY = new String[100];
    public static final String[] IMAGES = new String[100];
    public static final String[] IMAGES1 = new String[] {
// Heavy images
            "http://mobworld.co.in/BestWallpaper/3D/image1.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image2.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image3.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image4.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image5.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image6.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image7.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image8.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image9.jpg",
            "http://mobworld.co.in/BestWallpaper/3D/image10.jpg",


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_merit);
//        imageView = (ImageView) findViewById(R.id.imageView);
//        driverphoneno = (TextView) findViewById(R.id.phonenoofthedriver);
        drivername= (TextView) findViewById(R.id.drivernametext);
        android.support.v7.app.ActionBar action=getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.menu);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        action.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //action.setHomeAsUpIndicator(R.drawable.menu);
        action.setTitle(Html.fromHtml("<font color='#000000'><big>&nbsp;&nbsp;&nbsp; Driver Rating</big></font>"));
    }
    public void apply(View view){
        new getDriverMerit().execute();
        grid = (GridView)findViewById(R.id.grid);
    }
    class getDriverMerit extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(DriverMerit.this, "Please wait.",
                    "Fetching Drivers Informatin!", true);
        }
        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                try
                {
                    Jsonfunctions sh = new Jsonfunctions();
                    ServiceModel reg=new ServiceModel();
                    Log.e("url", Config.ip+"DriverList_api/driverMeritSystem");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"DriverList_api/driverMeritSystem",Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                JSONArray jsonArray = Jobj.getJSONArray("result_arr");

                                for (int j = 0; j < jsonArray.length(); j++)
                                {
                                    JSONObject obj = jsonArray.getJSONObject(j);
                                    name=obj.getString("name");
                                    Log.e("+++",obj.getString("name"));
                                    DRIVERNAMEARRAY[j]=name;
                                    namedriver.add(name);
                                    route=obj.getString("assigned_bus");
                                    Log.e("+++",obj.getString("assigned_bus"));
                                    routedriver.add(route);
                                    mobileno=obj.getString("mobile");
                                    Log.e("+++",obj.getString("mobile"));
                                    mobilenodriver.add(mobileno);
                                    speedlimit=obj.getString("speed_limit");
                                    Log.e("+++",obj.getString("speed_limit"));
                                    speedlimitdriver.add(speedlimit);
                                    rashdriving=obj.getString("rash_driving");
                                    Log.e("+++",obj.getString("rash_driving"));
                                    rashdrivingdriver.add(rashdriving);
                                    timemaintaince=obj.getString("time_maitanance");
                                    Log.e("+++",obj.getString("time_maitanance"));
                                    timemaintaincedriver.add(timemaintaince);
                                    driverImage=obj.getString("photo");
                                    Log.e("+++",obj.getString("photo"));
                                    if(driverImage.equalsIgnoreCase("null"))
                                        URL= DriverUploads+"employees1.png";
                                    else
                                        URL= DriverUploads+driverImage;
                                    Log.e("+++",URL);
                                    driverImagepng.add(URL);
                                    IMAGES[j]=URL;
                                    count=j;
                                    speedlimitintvalue=obj.getDouble("speed_limit");
                                    rashdrivingintvalue=obj.getDouble("rash_driving");
                                    timemaintainceintvalue=obj.getDouble("time_maitanance");
                                    //Start of if loop speed
                                    if(speedlimitintvalue==5){
                                        SPEEDLIMIT[j]="fivestar";
                                    }
                                    else if (speedlimitintvalue<5 && speedlimitintvalue>=4.5){
                                        SPEEDLIMIT[j]="fourhalfstar";
                                    }
                                    else if(speedlimitintvalue<4.5 && speedlimitintvalue>=4){
                                        SPEEDLIMIT[j]="fourstar";
                                    }
                                    else if(speedlimitintvalue<4 && speedlimitintvalue>=3.5){
                                        SPEEDLIMIT[j]="threehalfstar";
                                    }
                                    else if(speedlimitintvalue<3.5 && speedlimitintvalue>=3){
                                        SPEEDLIMIT[j]="threestar";
                                    }
                                    else if(speedlimitintvalue<3 && speedlimitintvalue>=2.5){
                                        SPEEDLIMIT[j]="twohalfstar";
                                    }
                                    else if(speedlimitintvalue<2.5 && speedlimitintvalue>=2){
                                        SPEEDLIMIT[j]="twostar";
                                    }
                                    else if(speedlimitintvalue<2 && speedlimitintvalue>=1.5){
                                        SPEEDLIMIT[j]="onehalfstar";
                                    }
                                    else if(speedlimitintvalue<1.5 && speedlimitintvalue>=1){
                                        SPEEDLIMIT[j]="onestar";
                                    }
                                    else if(speedlimitintvalue<1 && speedlimitintvalue>=0.5){
                                        SPEEDLIMIT[j]="halfstar";
                                    }
                                    else if(speedlimitintvalue<0.5 && speedlimitintvalue>=0){
                                        SPEEDLIMIT[j]="zerostar";
                                    }
                                    //end of if loop speed
                                    //start of rash loop
                                    if(rashdrivingintvalue==5){
                                        RASHDRIVING[j]="fivestar";
                                    }
                                    else if (rashdrivingintvalue<5 && rashdrivingintvalue>=4.5){
                                        RASHDRIVING[j]="fourhalfstar";
                                    }
                                    else if(rashdrivingintvalue<4.5 && rashdrivingintvalue>=4){
                                        RASHDRIVING[j]="fourstar";
                                    }
                                    else if(rashdrivingintvalue<4 && rashdrivingintvalue>=3.5){
                                        RASHDRIVING[j]="threehalfstar";
                                    }
                                    else if(rashdrivingintvalue<3.5 && rashdrivingintvalue>=3){
                                        RASHDRIVING[j]="threestar";
                                    }
                                    else if(rashdrivingintvalue<3 && rashdrivingintvalue>=2.5){
                                        RASHDRIVING[j]="twohalfstar";
                                    }
                                    else if(rashdrivingintvalue<2.5 && rashdrivingintvalue>=2){
                                        RASHDRIVING[j]="twostar";
                                    }
                                    else if(rashdrivingintvalue<2 && rashdrivingintvalue>=1.5){
                                        RASHDRIVING[j]="onehalfstar";
                                    }
                                    else if(rashdrivingintvalue<1.5 && rashdrivingintvalue>=1){
                                        RASHDRIVING[j]="onestar";
                                    }
                                    else if(rashdrivingintvalue<1 && rashdrivingintvalue>=0.5){
                                        RASHDRIVING[j]="halfstar";
                                    }
                                    else if(rashdrivingintvalue<0.5 && rashdrivingintvalue>=0){
                                        RASHDRIVING[j]="zerostar";
                                    }
                                    //end of rash loop
                                    //start of time loop
                                    if(timemaintainceintvalue==5){
                                        TIMEMAINTAINANCE[j]="fivestar";
                                    }
                                    else if (timemaintainceintvalue<5 && timemaintainceintvalue>=4.5){
                                        TIMEMAINTAINANCE[j]="fourhalfstar";
                                    }
                                    else if(timemaintainceintvalue<4.5 && timemaintainceintvalue>=4){
                                        TIMEMAINTAINANCE[j]="fourstar";
                                    }
                                    else if(timemaintainceintvalue<4 && timemaintainceintvalue>=3.5){
                                        TIMEMAINTAINANCE[j]="threehalfstar";
                                    }
                                    else if(timemaintainceintvalue<3.5 && timemaintainceintvalue>=3){
                                        TIMEMAINTAINANCE[j]="threestar";
                                    }
                                    else if(timemaintainceintvalue<3 && timemaintainceintvalue>=2.5){
                                        TIMEMAINTAINANCE[j]="twohalfstar";
                                    }
                                    else if(timemaintainceintvalue<2.5 && timemaintainceintvalue>=2){
                                        TIMEMAINTAINANCE[j]="twostar";
                                    }
                                    else if(timemaintainceintvalue<2 && timemaintainceintvalue>=1.5){
                                        TIMEMAINTAINANCE[j]="onehalfstar";
                                    }
                                    else if(timemaintainceintvalue<1.5 && timemaintainceintvalue>=1){
                                        TIMEMAINTAINANCE[j]="onestar";
                                    }
                                    else if(timemaintainceintvalue<1 && timemaintainceintvalue>=0.5){
                                        TIMEMAINTAINANCE[j]="halfstar";
                                    }
                                    else if(timemaintainceintvalue<0.5 && timemaintainceintvalue>=0){
                                        TIMEMAINTAINANCE[j]="zerostar";
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
        protected void onPostExecute(Void result)
        {
            imagedisplay();
            progressDialog.dismiss();
        }
    }

    public void imagedisplay(){
        grid.setAdapter(new ImageAdapter(this));
       /* Picasso.with(this)
                .load("http://al-amaanah.com/Tifly_Pro/uploads/driver_image/employees1.png")
                .resize(400,400)
                .into(imageView);
        imageViewtester.setImageDrawable(imageView.getDrawable());
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/

//        Log.e("name",namedriver.get(1));
//        Log.e("Phnoe no",mobilenodriver.get(1));
//        drivername.setText(namedriver.get(1));
//        drivername.setText(mobilenodriver.get(1));

    }

    private static class ImageAdapter extends BaseAdapter {


        private static final String[] IMAGE_URLS = IMAGES;

        private LayoutInflater inflater;

        Context c;

        ImageAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            c = context;

        }

        @Override
        public int getCount() {
            return count+1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.item_grid_image,null, true);
                holder = new ViewHolder();
                assert view != null;

                holder.imageView = (ImageView) view.findViewById(R.id.image);
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                //holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Picasso.with(c)
                    .load(IMAGE_URLS[position])
                    .resize(400,400).into(holder.imageView);
            TextView drivername1= (TextView) view.findViewById(R.id.drivernametext);
            drivername1.setText(DRIVERNAMEARRAY[position]);
            ImageView myImageView = (ImageView)view.findViewById(R.id.image1);
            ImageView myImageView1 = (ImageView)view.findViewById(R.id.image2);
            ImageView myImageView2 = (ImageView)view.findViewById(R.id.image3);
            myImageView.setImageResource(getImageId(view.getContext(),SPEEDLIMIT[position]));
            myImageView1.setImageResource(getImageId(view.getContext(),RASHDRIVING[position]));
            myImageView2.setImageResource(getImageId(view.getContext(),TIMEMAINTAINANCE[position]));
//                    .into(holder.imageView, new Callback() {
//
//                        @Override
//                        public void onSuccess() {
//                            holder.imageView.setVisibility(View.VISIBLE);
//                            holder.progressBar.setVisibility(View.INVISIBLE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            holder.progressBar.setVisibility(View.VISIBLE);
//                            holder.imageView.setVisibility(View.INVISIBLE);
//                        }
//                    });

            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
    public static int getImageId(Context context, String imageName) {
        return context.getResources().getIdentifier("drawable/" +imageName , null, context.getPackageName());
    }
}

