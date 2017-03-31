package com.example.valuetechsa.admin_school_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ValueTechSA on 07-03-2017.
 */
public class OverSpeedingReports extends Fragment {

    Spinner speedlist;
    String selectedspinneritem;
    static int iii=0;
    static String sss;
    static TextView txtfromdate,txttodate;
    static String dateprintgiven="empty";
    int count=0;
    String fromdateserver,todateserver;
    Typeface tfRobo,tfAdam;
    ProgressDialog progressDialog2=null;
    MyCustomBasedOverSpeedingAdaper myCustomBasedOverSpeedingAdaper;
    RelativeLayout manage,listmanage;
    private static Toast myToast;
    Boolean isScrolling;
    ListView lv1;

    ArrayList<String> idfromserver=new ArrayList<String>();
    ArrayList<String> datefromserver=new ArrayList<String>();
    ArrayList<String> busidfromserver=new ArrayList<String>();
    ArrayList<String> driveridfromserver=new ArrayList<String>();
    ArrayList<String> latitudefromserver=new ArrayList<String>();
    ArrayList<String> longitudefromserver=new ArrayList<String>();
    ArrayList<String> currentspeedfromserver=new ArrayList<String>();
    ArrayList<String> distancefromserver=new ArrayList<String>();
    ArrayList<String> triptypefromserver=new ArrayList<String>();
    ArrayList<String> busnamefromserver=new ArrayList<String>();
    ArrayList<String> drivernamefromserver=new ArrayList<String>();

    ArrayList<String> datetolist=new ArrayList<String>();
    ArrayList<String> busnametolist=new ArrayList<String>();
    ArrayList<String> drivernametolist=new ArrayList<String>();
    ArrayList<String> currentspeedtolist=new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_over_speeding_reports,container,false);
        tfRobo = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextView selectcrietria=(TextView)v.findViewById(R.id.selectcriteriaoverspeeding);
        TextView selectspeed=(TextView)v.findViewById(R.id.selectspeedoverspeeding);
        TextView fromdate=(TextView)v.findViewById(R.id.fromdateoverspeeding);
        TextView todate=(TextView)v.findViewById(R.id.todateoverspeeding);
        TextView individualselection=(TextView)v.findViewById(R.id.individualselectiontextbox);
        TextView date=(TextView)v.findViewById(R.id.dateoverspeedingtextbox);
        TextView drivername=(TextView)v.findViewById(R.id.drivernameoverspeedingtextbox);
        TextView busname=(TextView)v.findViewById(R.id.busnameoverspeedingtextbox);
        TextView speedachived=(TextView)v.findViewById(R.id.speedachivedoverspeedingtextbox);
        EditText fromdateedit=(EditText) v.findViewById(R.id.fromedateoverspeedingbox);
        EditText todateedit=(EditText) v.findViewById(R.id.todateoverspeedingbox);
        Button getlistbutton=(Button) v.findViewById(R.id.getlistoverspeeding);
        selectcrietria.setTypeface(tfRobo);
        selectspeed.setTypeface(tfRobo);
        fromdate.setTypeface(tfRobo);
        todate.setTypeface(tfRobo);
        individualselection.setTypeface(tfRobo);
        date.setTypeface(tfRobo);
        drivername.setTypeface(tfRobo);
        busname.setTypeface(tfRobo);
        speedachived.setTypeface(tfRobo);
        fromdateedit.setTypeface(tfRobo);
        todateedit.setTypeface(tfRobo);
        getlistbutton.setTypeface(tfAdam);

        speedlist=(Spinner) v.findViewById(R.id.selectspeedoverspeedingtypespinner);
        txtfromdate=(EditText)v.findViewById(R.id.fromedateoverspeedingbox);
        txtfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iii=1;
                showTruitonDatePickerDialog(v);
            }
        });
        txttodate=(EditText)v.findViewById(R.id.todateoverspeedingbox);
        txttodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iii=2;
                showTruitonDatePickerDialog(v);
            }
        });
        getlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListmethod();
            }
        });
        myToast = Toast.makeText(getActivity().getApplicationContext(), null, Toast.LENGTH_SHORT);
        manage=(RelativeLayout) v.findViewById(R.id.relativelayoutoverspeedingcreeation);
        listmanage=(RelativeLayout) v.findViewById(R.id.relativelayoutlistoverspeeding);
        setLayoutInvisible();
        listspinner();
        return v;
    }

    public void setLayoutInvisible() {
        if (manage.getVisibility() == View.VISIBLE) {
            manage.setVisibility(View.GONE);
        }
        if (listmanage.getVisibility() == View.VISIBLE) {
            listmanage.setVisibility(View.GONE);
        }
    }
    public void setLayoutVisible() {
        if (manage.getVisibility() == View.GONE) {
            manage.setVisibility(View.VISIBLE);
        }
        if (listmanage.getVisibility() == View.GONE) {
            listmanage.setVisibility(View.VISIBLE);
        }
    }

    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
            if(iii==1){
                txtfromdate.setText(dateprintgiven);
            }
            if(iii==2){
                txttodate.setText(dateprintgiven);
            }
            //dateusergiven = year+"-"+(month+1)+"-"+day;
        }
    }

    public void listspinner(){
        String[] busitems=new String[12];
        busitems[0]=getResources().getString(R.string.sj_select_speed);
        busitems[1]=getResources().getString(R.string.sj_select_all);
        busitems[2]=getResources().getString(R.string.sj_10);
        busitems[3]=getResources().getString(R.string.sj_20);
        busitems[4]=getResources().getString(R.string.sj_30);
        busitems[5]=getResources().getString(R.string.sj_40);
        busitems[6]=getResources().getString(R.string.sj_50);
        busitems[7]=getResources().getString(R.string.sj_60);
        busitems[8]=getResources().getString(R.string.sj_70);
        busitems[9]=getResources().getString(R.string.sj_80);
        busitems[10]=getResources().getString(R.string.sj_90);
        busitems[11]=getResources().getString(R.string.sj_100);

        ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, busitems) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextSize(20);
                ((TextView) v).setMinHeight(70);
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                Typeface externalFont=Typeface.createFromAsset(getActivity().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextSize(20);
                ((TextView) v).setMinHeight(70);
                return v;
            }
        };
        //ArrayAdapter<String> adapterbus = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, busitems);
        adapterbus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speedlist.setAdapter(adapterbus);
        speedlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routebusnonavigation=item;
                selectedspinneritem=item;
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getListmethod(){
        fromdateserver=txtfromdate.getText().toString();
        todateserver=txttodate.getText().toString();
        if(fromdateserver.isEmpty()){
            myToast.setText(getResources().getString(R.string.sj_please_select_from_date));
            myToast.show();
        }
        else if(todateserver.isEmpty()){
            myToast.setText(getResources().getString(R.string.sj_please_select_to_date));
            myToast.show();
        }
        else if(selectedspinneritem.equalsIgnoreCase("Select Speed")){
            myToast.setText(getResources().getString(R.string.sj_please_select_speed));
            myToast.show();
        }
        else {
            new getBusListFromServer().execute();
        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    class getBusListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            count=0;
            progressDialog2 = ProgressDialog.show(getActivity(), getResources().getString(R.string.sj_please_wait),
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
                    Log.e("url", Config.ip+"BusCoordinates_api/getCoordinatesByDate/From_Date/"+fromdateserver+"/To_Date/"+todateserver);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"BusCoordinates_api/getCoordinatesByDate/From_Date/"+fromdateserver+"/To_Date/"+todateserver,Jsonfunctions.GET);

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
                                    Log.e("id",obj.getString("id"));
                                    String id=obj.getString("id");
                                    idfromserver.add(id);
                                    Log.e("date",obj.getString("date"));
                                    String date=obj.getString("date");
                                    datefromserver.add(date);
                                    Log.e("bus_id",obj.getString("bus_id"));
                                    String bus_id=obj.getString("bus_id");
                                    busidfromserver.add(bus_id);
                                    Log.e("driver_id",obj.getString("driver_id"));
                                    String driver_id=obj.getString("driver_id");
                                    driveridfromserver.add(driver_id);
                                    Log.e("latitude",obj.getString("latitude"));
                                    String latitude=obj.getString("latitude");
                                    latitudefromserver.add(latitude);
                                    Log.e("langitude",obj.getString("langitude"));
                                    String langitude=obj.getString("langitude");
                                    longitudefromserver.add(langitude);
                                    Log.e("cur_speed",obj.getString("cur_speed"));
                                    String cur_speed=obj.getString("cur_speed");
                                    currentspeedfromserver.add(cur_speed);
                                    Log.e("distance",obj.getString("distance"));
                                    String distance=obj.getString("distance");
                                    distancefromserver.add(distance);
                                    Log.e("trip_type",obj.getString("trip_type"));
                                    String trip_type=obj.getString("trip_type");
                                    triptypefromserver.add(trip_type);
                                    Log.e("bus_name",obj.getString("bus_name"));
                                    String bus_name=obj.getString("bus_name");
                                    busnamefromserver.add(bus_name);
                                    Log.e("+driver_name++",obj.getString("driver_name"));
                                    String driver_name=obj.getString("driver_name");
                                    drivernamefromserver.add(driver_name);
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
            if(count>0){
                setLayoutVisible();
                speedlist();
            }
            else {
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            progressDialog2.dismiss();
        }
    }

    public void speedlist(){
        if(selectedspinneritem.equalsIgnoreCase("Select All")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                datetolist.add(datefromserver.get(i));
                busnametolist.add(busnamefromserver.get(i));
                drivernametolist.add(drivernamefromserver.get(i));
                currentspeedtolist.add(currentspeedfromserver.get(i));
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("10 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=0&&speedcheck<=10){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("20 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=11&&speedcheck<=20){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("30 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=21&&speedcheck<=30){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("40 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=31&&speedcheck<=40){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("50 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=41&&speedcheck<=50){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }

        }
        else if(selectedspinneritem.equalsIgnoreCase("60 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=51&&speedcheck<=60){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("70 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=61&&speedcheck<=70){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("80 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=71&&speedcheck<=80){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("90 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=81&&speedcheck<=90){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
        else if(selectedspinneritem.equalsIgnoreCase("100 Km/Hr")){
            datetolist.clear();
            busnametolist.clear();
            drivernametolist.clear();
            currentspeedtolist.clear();
            for(int i=0;i<datefromserver.size();i++){
                int speedcheck=(int)Double.parseDouble(currentspeedfromserver.get(i));
                if(speedcheck>=91&&speedcheck<=100){
                    datetolist.add(datefromserver.get(i));
                    busnametolist.add(busnamefromserver.get(i));
                    drivernametolist.add(drivernamefromserver.get(i));
                    currentspeedtolist.add(currentspeedfromserver.get(i));
                }
            }
            if(datetolist.isEmpty()){
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            else {
                listgenerate();
            }
        }
    }

    public void listgenerate(){
        ArrayList<SearchResultsOverSpeeding> searchResults = GetSearchResults();
        lv1 = (ListView) getActivity().findViewById(R.id.routecreatedoverspeedinglist);
        myCustomBasedOverSpeedingAdaper=new MyCustomBasedOverSpeedingAdaper(getActivity(), searchResults);
        lv1.setAdapter(myCustomBasedOverSpeedingAdaper);
        //setListViewHeightBasedOnChildren(lv1);
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

            }
        });
    }

    private ArrayList<SearchResultsOverSpeeding> GetSearchResults(){
        ArrayList<SearchResultsOverSpeeding> results = new ArrayList<SearchResultsOverSpeeding>();
        for (int i=0;i<busnametolist.size();i++){
            SearchResultsOverSpeeding sr1 = new SearchResultsOverSpeeding();
            sr1.setDate(datetolist.get(i));
            sr1.setDrivername(drivernametolist.get(i));
            sr1.setBusname(busnametolist.get(i));
            sr1.setSpeedachived(currentspeedtolist.get(i));
            Log.e("inside list","#######################");
            results.add(sr1);
        }
        return results;
    }

    public class MyCustomBasedOverSpeedingAdaper extends BaseAdapter {
        private ArrayList<SearchResultsOverSpeeding> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedOverSpeedingAdaper(Context context, ArrayList<SearchResultsOverSpeeding> results) {
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
                Log.e("inside adaptor","#######################1");
                convertView = mInflater.inflate(R.layout.custom_row_view_over_speeding, null);
                holder = new ViewHolder();
                Log.e("inside adaptor","#######################2");
                holder.txtdate = (TextView) convertView.findViewById(R.id.customoverspeedingdate);
                holder.txtdate.setTypeface(tfRobo);
                holder.txtbusname = (TextView) convertView.findViewById(R.id.customoverspeedingbusname);
                holder.txtbusname.setTypeface(tfRobo);
                holder.txtdrivername = (TextView) convertView.findViewById(R.id.customoverspeedingdrivername);
                holder.txtdrivername.setTypeface(tfRobo);
                holder.txtspeedachived = (TextView) convertView.findViewById(R.id.customoverspeedingspeedachived);
                holder.txtspeedachived.setTypeface(tfRobo);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtdate.setText(searchArrayList.get(position).getDate());
            holder.txtbusname.setText(searchArrayList.get(position).getBusname());
            holder.txtdrivername.setText(searchArrayList.get(position).getDrivername());
            holder.txtspeedachived.setText(searchArrayList.get(position).getSpeedachived());
            return convertView;
        }

        class ViewHolder {
            TextView txtdate;
            TextView txtbusname;
            TextView txtdrivername;
            TextView txtspeedachived;
        }
    }

}
