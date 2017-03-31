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
public class ArivialDepatureReports extends Fragment {
    Typeface tfRobo,tfAdam;
    static TextView txtfromdate,txttodate;
    static String sss;
    static String dateprintgiven="empty";
    static int iii=0;
    Spinner buslist;
    ProgressDialog progressDialog2=null;
    String selectedspinneritem;
    ListView lv1;
    Boolean isScrolling;
    String fromdateserver,todateserver;
    MyCustomBasedArrivalDepatureAdaper myCustomBasedArrivalDepatureAdaper;
    int busid,count=0;
    private static Toast myToast;
    RelativeLayout manage,listmanage;
    ArrayList<String> busnamefromserver=new ArrayList<String>();
    ArrayList<String> busidfromserver=new ArrayList<String>();
    ArrayList<String> datearrivaldepaturefromserver=new ArrayList<String>();
    ArrayList<String> pickupstartarrivaldepaturefromserver=new ArrayList<String>();
    ArrayList<String> pickupendarrivaldepaturefromserver=new ArrayList<String>();
    ArrayList<String> dropstartarrivaldepaturefromserver=new ArrayList<String>();
    ArrayList<String> dropendarrivaldepaturefromserver=new ArrayList<String>();
    ArrayList<String> routeidarrivaldepaturefromserver=new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_arrival_depature_reports,container,false);
        tfRobo = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextView selectcrietria=(TextView)v.findViewById(R.id.selectcriteriaarrivaldepature);
        TextView selectbus=(TextView)v.findViewById(R.id.selectbusarrivaldepature);
        TextView fromdate=(TextView)v.findViewById(R.id.fromdatearrivaldepature);
        TextView todate=(TextView)v.findViewById(R.id.todatearrivaldepature);
        TextView individualselection=(TextView)v.findViewById(R.id.individualselectiontextbox);
        TextView date=(TextView)v.findViewById(R.id.datearrivaldepaturetextbox);
        TextView pickupstart=(TextView)v.findViewById(R.id.pickupstarttimearrivaldepaturetextbox);
        TextView pickupstop=(TextView)v.findViewById(R.id.pickupendtimearrivaldepaturetextbox);
        TextView dropstart=(TextView)v.findViewById(R.id.dropstarttimearrivaldepaturetextbox);
        TextView dropstop=(TextView)v.findViewById(R.id.dropendtimearrivaldepaturetextbox);
        EditText fromdateedit=(EditText) v.findViewById(R.id.fromedatearrivaldepaturebox);
        EditText todateedit=(EditText) v.findViewById(R.id.todatearrivaldepaturebox);
        Button getlistbutton=(Button) v.findViewById(R.id.getlistarrivaldepature);
        selectcrietria.setTypeface(tfRobo);
        selectbus.setTypeface(tfRobo);
        fromdate.setTypeface(tfRobo);
        todate.setTypeface(tfRobo);
        individualselection.setTypeface(tfRobo);
        date.setTypeface(tfRobo);
        pickupstart.setTypeface(tfRobo);
        pickupstop.setTypeface(tfRobo);
        dropstart.setTypeface(tfRobo);
        dropstop.setTypeface(tfRobo);
        fromdateedit.setTypeface(tfRobo);
        todateedit.setTypeface(tfRobo);
        getlistbutton.setTypeface(tfAdam);
        buslist=(Spinner) v.findViewById(R.id.selectbusarrivaldepaturetypespinner);
        txtfromdate=(EditText)v.findViewById(R.id.fromedatearrivaldepaturebox);
        txtfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iii=1;
                showTruitonDatePickerDialog(v);
            }
        });
        txttodate=(EditText)v.findViewById(R.id.todatearrivaldepaturebox);
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
        manage=(RelativeLayout) v.findViewById(R.id.relativelayoutarriveldepature);
        listmanage=(RelativeLayout) v.findViewById(R.id.relativelayoutlistroutedistance);
        setLayoutInvisible();
        new getBusListFromServer().execute();
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

    class getArrivalDepatureDetailsFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            datearrivaldepaturefromserver.clear();
            pickupstartarrivaldepaturefromserver.clear();
            pickupendarrivaldepaturefromserver.clear();
            dropendarrivaldepaturefromserver.clear();
            dropendarrivaldepaturefromserver.clear();
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
                    Log.e("url", Config.ip+"ArrivalDeparture_api/ArrivalDepartureList/bus_id/"+busid+"/from_date/"+fromdateserver+"/to_date/"+todateserver);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"ArrivalDeparture_api/ArrivalDepartureList/bus_id/"+busid+"/from_date/"+fromdateserver+"/to_date/"+todateserver,Jsonfunctions.GET);

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
                                    Log.e("date",obj.getString("date"));
                                    String date=obj.getString("date");
                                    datearrivaldepaturefromserver.add(date);
                                    Log.e("pickup_start_time",obj.getString("pickup_start_time"));
                                    String pickup_start_time=obj.getString("pickup_start_time");
                                    pickupstartarrivaldepaturefromserver.add(pickup_start_time);
                                    Log.e("pickup_end_time",obj.getString("pickup_end_time"));
                                    String pickup_end_time=obj.getString("pickup_end_time");
                                    pickupendarrivaldepaturefromserver.add(pickup_end_time);
                                    Log.e("drop_start_time",obj.getString("drop_start_time"));
                                    String drop_start_time=obj.getString("drop_start_time");
                                    dropstartarrivaldepaturefromserver.add(drop_start_time);
                                    Log.e("drop_end_time",obj.getString("drop_end_time"));
                                    String drop_end_time=obj.getString("drop_end_time");
                                    dropendarrivaldepaturefromserver.add(drop_end_time);
                                    Log.e("route_id",obj.getString("route_id"));
                                    String route_id=obj.getString("route_id");
                                    routeidarrivaldepaturefromserver.add(route_id);
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
            //listgenerate();
            new getArrivalDepatureDetailsFromServer().execute();
        }

    }

    public void listgenerate(){
        ArrayList<SearchResultsArrivalDepature> searchResults = GetSearchResults();
        lv1 = (ListView) getActivity().findViewById(R.id.routecreatedarrivaldepaturelist);
        myCustomBasedArrivalDepatureAdaper=new MyCustomBasedArrivalDepatureAdaper(getActivity(), searchResults);
        lv1.setAdapter(myCustomBasedArrivalDepatureAdaper);
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

    private ArrayList<SearchResultsArrivalDepature> GetSearchResults(){
        ArrayList<SearchResultsArrivalDepature> results = new ArrayList<SearchResultsArrivalDepature>();
        /*for (int i=0;i<2;i++){
            SearchResultsArrivalDepature sr1 = new SearchResultsArrivalDepature();
            sr1.setDate("2017-3-20");
            sr1.setPickupstart("7:00 AM");
            sr1.setPickupend("8:30 AM");
            sr1.setDropstart("4:00 PM");
            sr1.setDropend("5:30 PM");
            Log.e("inside list","#######################");
            results.add(sr1);
        }*/
        for (int i=0;i<datearrivaldepaturefromserver.size();i++){
            SearchResultsArrivalDepature sr1 = new SearchResultsArrivalDepature();
            sr1.setDate(datearrivaldepaturefromserver.get(i));
            if(pickupstartarrivaldepaturefromserver.get(i).equalsIgnoreCase("0")){
                sr1.setPickupstart("--");
            }
            else {
                sr1.setPickupstart(pickupstartarrivaldepaturefromserver.get(i));
            }
            if(pickupendarrivaldepaturefromserver.get(i).equalsIgnoreCase("0")){
                sr1.setPickupend("--");
            }
            else {
                sr1.setPickupend(pickupendarrivaldepaturefromserver.get(i));
            }
            if(dropstartarrivaldepaturefromserver.get(i).equalsIgnoreCase("0")){
                sr1.setDropstart("--");
            }
            else {
                sr1.setDropstart(dropstartarrivaldepaturefromserver.get(i));
            }
            if(dropendarrivaldepaturefromserver.get(i).equalsIgnoreCase("0")){
                sr1.setDropend("--");
            }
            else {
                sr1.setDropend(dropendarrivaldepaturefromserver.get(i));
            }
            Log.e("inside list","#######################");
            results.add(sr1);
        }
        return results;
    }

    public class MyCustomBasedArrivalDepatureAdaper extends BaseAdapter {
        private ArrayList<SearchResultsArrivalDepature> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedArrivalDepatureAdaper(Context context, ArrayList<SearchResultsArrivalDepature> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_arrival_depature, null);
                holder = new ViewHolder();
                Log.e("inside adaptor","#######################2");
                holder.txtdate = (TextView) convertView.findViewById(R.id.customarrivaldepaturedate);
                holder.txtdate.setTypeface(tfRobo);
                holder.txtpickupstart = (TextView) convertView.findViewById(R.id.customarrivaldepaturepickupstart);
                holder.txtpickupstart.setTypeface(tfRobo);
                holder.txtpickupend = (TextView) convertView.findViewById(R.id.customarrivaldepaturepickupend);
                holder.txtpickupend.setTypeface(tfRobo);
                holder.txtdropstart = (TextView) convertView.findViewById(R.id.customarrivaldepaturedropstart);
                holder.txtdropstart.setTypeface(tfRobo);
                holder.txtdropend = (TextView) convertView.findViewById(R.id.customarrivaldepaturedropend);
                holder.txtdropend.setTypeface(tfRobo);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtdate.setText(searchArrayList.get(position).getDate());
            holder.txtpickupstart.setText(searchArrayList.get(position).getPickupstart());
            holder.txtpickupend.setText(searchArrayList.get(position).getPickupend());
            holder.txtdropstart.setText(searchArrayList.get(position).getDropstart());
            holder.txtdropend.setText(searchArrayList.get(position).getDropend());
            return convertView;
        }

        class ViewHolder {
            TextView txtdate;
            TextView txtpickupstart;
            TextView txtpickupend;
            TextView txtdropstart;
            TextView txtdropend;
        }
    }

}
