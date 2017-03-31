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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valuetechsa.admin_school_app.commonclass.CommonClass;
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
public class DistanceByVechileReport extends Fragment {
    static String dateprintgiven="empty";
    static TextView txtfromdate,txttodate;
    Button getlistbutton;
    String fromdateserver,todateserver;
    Spinner buslist;
    static int iii=0;
    static String sss;
    Jsonfunctions sh;
    String response="",url="";
    int busid;
    ListView lv1;
    MyCustomBasedDistanceByVechileAdaper myCustomBasedDistanceByVechileAdaper;
    Boolean isScrolling;
    RelativeLayout manage,listmanage;
    private static Toast myToast;
    int serverResponseCode = 0;
    int count=0;
    CommonClass cc;
    String selectedspinneritem;
    ArrayList<String> busnamefromserver=new ArrayList<String>();
    ArrayList<String> busidfromserver=new ArrayList<String>();
    Typeface tfRobo,tfAdam;
    ArrayList<String> busidfromdistanceserver=new ArrayList<String>();
    ArrayList<String> busnamefromdistanceserver=new ArrayList<String>();
    ArrayList<String> datefromdistanceserver=new ArrayList<String>();
    ArrayList<String> platenofromdistanceserver=new ArrayList<String>();
    ArrayList<String> busdistancefromdistanceserver=new ArrayList<String>();
    ProgressDialog progressDialog2=null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_distance_by_vehicle_reports,container,false);
        tfRobo = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextView selectcrietria=(TextView)v.findViewById(R.id.selectcriteriavechiledistance);
        TextView selectbus=(TextView)v.findViewById(R.id.selectbusdistancebyvechile);
        TextView fromdate=(TextView)v.findViewById(R.id.fromdatedistancebyvechile);
        TextView todate=(TextView)v.findViewById(R.id.todatedistancebyvechile);
        TextView individualselection=(TextView)v.findViewById(R.id.individualselectiontextbox);
        TextView date=(TextView)v.findViewById(R.id.datedistancebyvechiletextbox);
        TextView busid=(TextView)v.findViewById(R.id.busiddistancebyvechiletextbox);
        TextView busname=(TextView)v.findViewById(R.id.busnamedistancebyvechiletextbox);
        TextView plateno=(TextView)v.findViewById(R.id.platenodistancebyvechiletextbox);
        TextView busdistance=(TextView)v.findViewById(R.id.busdistancedistancebyvechiletextbox);
        EditText fromdateedit=(EditText) v.findViewById(R.id.fromedatedistancebyvechilebox);
        EditText todateedit=(EditText) v.findViewById(R.id.todatedistancebyvechilebox);
        Button getlistbutton=(Button) v.findViewById(R.id.getlistdistancebyvechile);
        selectcrietria.setTypeface(tfRobo);
        selectbus.setTypeface(tfRobo);
        fromdate.setTypeface(tfRobo);
        todate.setTypeface(tfRobo);
        individualselection.setTypeface(tfRobo);
        date.setTypeface(tfRobo);
        busid.setTypeface(tfRobo);
        busname.setTypeface(tfRobo);
        plateno.setTypeface(tfRobo);
        busdistance.setTypeface(tfRobo);
        fromdateedit.setTypeface(tfRobo);
        todateedit.setTypeface(tfRobo);
        getlistbutton.setTypeface(tfAdam);

        getlistbutton=(Button)v.findViewById(R.id.getlistdistancebyvechile);
        getlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getListmethod();
            }
        });
        buslist=(Spinner) v.findViewById(R.id.selectbusdistancebyvechiletypespinner);
        txtfromdate=(EditText)v.findViewById(R.id.fromedatedistancebyvechilebox);
        txtfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iii=1;
                showTruitonDatePickerDialog(v);
            }
        });
        txttodate=(EditText)v.findViewById(R.id.todatedistancebyvechilebox);
        txttodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iii=2;
                showTruitonDatePickerDialog(v);
            }
        });
        new getBusListFromServer().execute();
        myToast = Toast.makeText(getActivity().getApplicationContext(), null, Toast.LENGTH_SHORT);
        manage=(RelativeLayout) v.findViewById(R.id.relativelayoutroutedistancecreeation);
        listmanage=(RelativeLayout) v.findViewById(R.id.relativelayoutlistroutedistance);
        setLayoutInvisible();
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
        int sum1=busnamefromserver.size();
        sum1=sum1+1;
        String[] busitems=new String[sum1];
        busitems[0]=getResources().getString(R.string.sj_select_bus);
        for(int j=1;j<=busnamefromserver.size();j++){
            busitems[j]=busnamefromserver.get(j-1);
        }
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
        buslist.setAdapter(adapterbus);
        buslist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routebusnonavigation=item;
                selectedspinneritem=item;
                Log.e("spinner selected",item);
                for(int i=0;i<busidfromserver.size();i++){
                    if(busnamefromserver.get(i).equalsIgnoreCase(item)){
                        busid=Integer.parseInt(busidfromserver.get(i));
                        Log.e("spinner selected",busid+"");
                        break;
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getListmethod(){
        fromdateserver=txtfromdate.getText().toString();
        todateserver=txttodate.getText().toString();
        if(selectedspinneritem.equalsIgnoreCase("Select Bus")){
            myToast.setText(getResources().getString(R.string.sj_please_select_bus));
            myToast.show();
        }
        else if(fromdateserver.isEmpty()){
            myToast.setText(getResources().getString(R.string.sj_please_select_from_date));
            myToast.show();
        }
        else if(todateserver.isEmpty()){
            myToast.setText(getResources().getString(R.string.sj_please_select_to_date));
            myToast.show();
        }
        else {
            new getdistancebyvechileFromServer().execute();
        }

    }
    class getBusListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
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
            listspinner();
        }
    }

    class getdistancebyvechileFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            datefromdistanceserver.clear();
            busidfromdistanceserver.clear();
            busnamefromdistanceserver.clear();
            platenofromdistanceserver.clear();
            busdistancefromdistanceserver.clear();
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
                    Log.e("url", Config.ip+"BusList_api/VechicleDistanceList/bus_id/"+busid+"/from_date/"+fromdateserver+"/to_date/"+todateserver);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"BusList_api/VechicleDistanceList/bus_id/"+busid+"/from_date/"+fromdateserver+"/to_date/"+todateserver,Jsonfunctions.GET);

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
                                    Log.e("bus_id",obj.getString("bus_id"));
                                    String bus_id=obj.getString("bus_id");
                                    busidfromdistanceserver.add(bus_id);
                                    Log.e("name",obj.getString("name"));
                                    String name=obj.getString("name");
                                    busnamefromdistanceserver.add(name);
                                    Log.e("plate_number",obj.getString("plate_number"));
                                    String plate_number=obj.getString("plate_number");
                                    platenofromdistanceserver.add(plate_number);
                                    Log.e("date",obj.getString("date"));
                                    String date=obj.getString("date");
                                    datefromdistanceserver.add(date);
                                    Log.e("bus_distance",obj.getString("bus_distance"));
                                    String bus_distance=obj.getString("bus_distance");
                                    busdistancefromdistanceserver.add(bus_distance);
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
                listgenerate();
            }
            else {
                myToast.setText(getResources().getString(R.string.sj_no_data_exists));
                myToast.show();
                setLayoutInvisible();
            }
            progressDialog2.dismiss();
        }
    }

    public void listgenerate(){
        ArrayList<SearchReultsDistanceByVechile> searchResults = GetSearchResults();
        lv1 = (ListView) getActivity().findViewById(R.id.routecreateddistancebyvechilelist);
        myCustomBasedDistanceByVechileAdaper=new MyCustomBasedDistanceByVechileAdaper(getActivity(), searchResults);
        lv1.setAdapter(myCustomBasedDistanceByVechileAdaper);
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

    private ArrayList<SearchReultsDistanceByVechile> GetSearchResults(){
        ArrayList<SearchReultsDistanceByVechile> results = new ArrayList<SearchReultsDistanceByVechile>();
        for (int i=0;i<datefromdistanceserver.size();i++){
            SearchReultsDistanceByVechile sr1 = new SearchReultsDistanceByVechile();
            sr1.setDistancebyvechiledate(datefromdistanceserver.get(i));
            sr1.setDistancebyvechilebusid(busidfromdistanceserver.get(i));
            sr1.setDistancebyvechilebusname(busnamefromdistanceserver.get(i));
            if(platenofromdistanceserver.get(i).equalsIgnoreCase("null")){
                sr1.setDistancebyvechileplateno("--");
            }
            else {
                sr1.setDistancebyvechileplateno(platenofromdistanceserver.get(i));
            }
            if(busdistancefromdistanceserver.get(i).equalsIgnoreCase("null")){
                sr1.setDistancebyvechilebusdistance("--");
            }
            else {
                sr1.setDistancebyvechilebusdistance(busdistancefromdistanceserver.get(i));
            }
            Log.e("inside list","#######################");
            results.add(sr1);
        }
        return results;
    }

    public class MyCustomBasedDistanceByVechileAdaper extends BaseAdapter {
        private ArrayList<SearchReultsDistanceByVechile> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedDistanceByVechileAdaper(Context context, ArrayList<SearchReultsDistanceByVechile> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_distance_by_vechile, null);
                holder = new ViewHolder();
                Log.e("inside adaptor","#######################2");
                holder.txtdate = (TextView) convertView.findViewById(R.id.customdistancebyvechiledate);
                holder.txtdate.setTypeface(tfRobo);
                holder.txtbusid = (TextView) convertView.findViewById(R.id.customdistancebyvechilebusid);
                holder.txtbusid.setTypeface(tfRobo);
                holder.txtbusname = (TextView) convertView.findViewById(R.id.customdistancebyvechilebusname);
                holder.txtbusname.setTypeface(tfRobo);
                holder.txtplateno = (TextView) convertView.findViewById(R.id.customdistancebyvechileplateno);
                holder.txtplateno.setTypeface(tfRobo);
                holder.txtbusdistance = (TextView) convertView.findViewById(R.id.customdistancebyvechilebusdistance);
                holder.txtbusdistance.setTypeface(tfRobo);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtdate.setText(searchArrayList.get(position).getDistancebyvechiledate());
            holder.txtbusid.setText(searchArrayList.get(position).getDistancebyvechilebusid());
            holder.txtbusname.setText(searchArrayList.get(position).getDistancebyvechilebusname());
            holder.txtplateno.setText(searchArrayList.get(position).getDistancebyvechileplateno());
            holder.txtbusdistance.setText(searchArrayList.get(position).getDistancebyvechilebusdistance());
            return convertView;
        }

        class ViewHolder {
            TextView txtdate;
            TextView txtbusid;
            TextView txtbusname;
            TextView txtplateno;
            TextView txtbusdistance;
        }
    }

}
