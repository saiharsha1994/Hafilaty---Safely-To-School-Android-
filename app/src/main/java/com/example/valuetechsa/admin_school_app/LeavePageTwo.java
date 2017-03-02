package com.example.valuetechsa.admin_school_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

public class LeavePageTwo extends Fragment {

    ArrayList<String> busnamelistfromserver=new ArrayList<String>();
    ArrayList<String> busidlistfromserver=new ArrayList<String>();
    ArrayList<String> studentnametfromserver=new ArrayList<String>();
    ArrayList<String> leaveidfromserver=new ArrayList<String>();
    ArrayList<String> fromdatefromserver=new ArrayList<String>();
    ArrayList<String> todatefromserver=new ArrayList<String>();
    ArrayList<String> reasonfromserver=new ArrayList<String>();
    ArrayList<String> statusfromserver=new ArrayList<String>();
    ArrayList<String> numberofdaysfromserver=new ArrayList<String>();
    ArrayList<String> appliedonfromserver=new ArrayList<String>();
    RelativeLayout manage,listmanage;
    String globalstatus;
    int globalbusid,globalnumberinlist=0,globalselect=0,globalcount=0;
    ProgressDialog progressDialog2=null;
    Jsonfunctions sh;
    String response="",url="";
    Spinner buslistspinner;
    ListView lv1;
    Boolean isScrolling;
    Typeface tfRobo;
    MyCustomBasedLeaveTwoAdaper myCustomBasedLeaveTwoAdaper;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_leave_page_two,container,false);
        tfRobo = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        TextView studentlist=(TextView)v.findViewById(R.id.leavetwolisttextbox);
        TextView studentlistname=(TextView)v.findViewById(R.id.leavetwostudentnametextbox);
        TextView studentlistfromdate=(TextView)v.findViewById(R.id.leavetwofromdatetextbox);
        TextView studentlisttodate=(TextView)v.findViewById(R.id.leavetwotodatetextbox);
        TextView studentlistreason=(TextView)v.findViewById(R.id.leavetworeasontextbox);
        TextView studentlisstatus=(TextView)v.findViewById(R.id.leavetwostatustextbox);

        studentlist.setTypeface(tfRobo);
        studentlistname.setTypeface(tfRobo);
        studentlistfromdate.setTypeface(tfRobo);
        studentlisttodate.setTypeface(tfRobo);
        studentlistreason.setTypeface(tfRobo);
        studentlisstatus.setTypeface(tfRobo);

        new getBusListFromServer().execute();
        manage=(RelativeLayout) v.findViewById(R.id.relativelayoutleavetwocreeation);
        listmanage=(RelativeLayout) v.findViewById(R.id.leavetwolist);
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
                                    Log.e("bus name",obj.getString("name"));
                                    String busname=obj.getString("name");
                                    busnamelistfromserver.add(busname);
                                    Log.e("bus id",obj.getString("bus_Id"));
                                    String busId=obj.getString("bus_Id");
                                    busidlistfromserver.add(busId);

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
            fillbuslistspinner();
            //listgenerate();
        }
    }

    class getStudentsListFromServer extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute(){
            studentnametfromserver.clear();
            fromdatefromserver.clear();
            todatefromserver.clear();
            reasonfromserver.clear();
            statusfromserver.clear();
            leaveidfromserver.clear();
            numberofdaysfromserver.clear();
            appliedonfromserver.clear();
            globalcount=0;
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
                    Log.e("url", Config.ip+"Attendance_api/LeaveListForTransportAdmin/bus_id/"+globalbusid);
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"Attendance_api/LeaveListForTransportAdmin/bus_id/"+globalbusid,Jsonfunctions.GET);

                    if (jsonStr1 != null)
                    {
                        try
                        {
                            JSONObject Jobj = new JSONObject(jsonStr1);

                            if(Jobj.getString("responsecode").equals("1"))
                            {
                                JSONArray jsonArray = Jobj.getJSONArray("result_arr");
                                globalnumberinlist=jsonArray.length();
                                for (int j = 0; j < jsonArray.length(); j++){

                                    JSONObject obj = jsonArray.getJSONObject(j);
                                    if(obj.getString("status").equalsIgnoreCase("1")){

                                    }
                                    else{
                                        Log.e("student_name",obj.getString("student_name"));
                                        String student_name=obj.getString("student_name");
                                        studentnametfromserver.add(student_name);
                                        Log.e("from_date id",obj.getString("from_date"));
                                        String from_date=obj.getString("from_date");
                                        fromdatefromserver.add(from_date);
                                        Log.e("to_date",obj.getString("to_date"));
                                        String to_date=obj.getString("to_date");
                                        todatefromserver.add(to_date);
                                        Log.e("reason id",obj.getString("reason"));
                                        String reason=obj.getString("reason");
                                        reasonfromserver.add(reason);
                                        Log.e("status",obj.getString("status"));
                                        String status=obj.getString("status");
                                        statusfromserver.add(status);
                                        Log.e("applied_on",obj.getString("applied_on"));
                                        String applied_on=obj.getString("applied_on");
                                        appliedonfromserver.add(applied_on);
                                        Log.e("no_of_days",obj.getString("no_of_days"));
                                        String no_of_days=obj.getString("no_of_days");
                                        numberofdaysfromserver.add(no_of_days);
                                        Log.e("id",obj.getString("id"));
                                        String id=obj.getString("id");
                                        leaveidfromserver.add(id);
                                        globalcount++;
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
            progressDialog2.dismiss();
            if(globalnumberinlist>0){
                globalnumberinlist=0;
                if(globalcount==0){
                    setLayoutInvisible();
                    Toast.makeText(getActivity(), getResources().getString(R.string.sj_no_student_leave_requests),
                            Toast.LENGTH_LONG).show();
                }
                else {
                    setLayoutVisible();
                    listgenerate();
                }
            }
            else {
                setLayoutInvisible();
                    Toast.makeText(getActivity(), getResources().getString(R.string.sj_no_student_leave_requests),
                            Toast.LENGTH_LONG).show();
            }

        }
    }

    public void fillbuslistspinner(){

        buslistspinner=(Spinner) getActivity().findViewById(R.id.leavetwospinneroption);
        String[] busitems=new String[busnamelistfromserver.size()+1];
        busitems[0]=getResources().getString(R.string.sj_select_bus);
        for(int i=1;i<=busnamelistfromserver.size();i++){
            busitems[i]=busnamelistfromserver.get(i-1);
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
        buslistspinner.setAdapter(adapterbus);
        buslistspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routebusnonavigation=item;
                //selectedspinneritemintoserver=item;
                /*selectedspinnerbusitem=item;
                for(int i=0;i<busnamelistfromserver.size();i++){
                    if(busnamelistfromserver.get(i).equalsIgnoreCase(item)){
                        selectbusidintoserver=busidlistfromserver.get(i);
                    }
                }*/
                if(item.equalsIgnoreCase(getResources().getString(R.string.sj_select_bus))){
                    setLayoutInvisible();
                }
                else {
                    for(int i=0;i<busidlistfromserver.size();i++){
                        if(item.equalsIgnoreCase(busnamelistfromserver.get(i))){
                            globalbusid=Integer.parseInt(busidlistfromserver.get(i));
                        }
                    }
                    new getStudentsListFromServer().execute();
                }
                Log.e("spinner selected",item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void listgenerate(){
        ArrayList<SearchResultsLeaveTwo> searchResults = GetSearchResults();
        lv1 = (ListView) getActivity().findViewById(R.id.leavetwoselectedlist);
        myCustomBasedLeaveTwoAdaper=new MyCustomBasedLeaveTwoAdaper(getActivity(), searchResults);
        lv1.setAdapter(myCustomBasedLeaveTwoAdaper);
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

    private ArrayList<SearchResultsLeaveTwo> GetSearchResults(){
        ArrayList<SearchResultsLeaveTwo> results = new ArrayList<SearchResultsLeaveTwo>();
        for (int i=0;i<studentnametfromserver.size();i++){
            SearchResultsLeaveTwo sr1 = new SearchResultsLeaveTwo();
            sr1.setLeaveTwoName(studentnametfromserver.get(i));
            sr1.setLeaveTwoFromDate(fromdatefromserver.get(i));
            sr1.setLeaveTwoToDate(todatefromserver.get(i));
            sr1.setLeaveTwoReason(reasonfromserver.get(i));
            if(statusfromserver.get(i).equalsIgnoreCase("2")){
                globalstatus="Approved";
            }
            else{
                globalstatus="Rejected";
            }
            sr1.setLeaveTwoStatus(globalstatus);
            results.add(sr1);
        }
        return results;
    }

    public class MyCustomBasedLeaveTwoAdaper extends BaseAdapter {
        private ArrayList<SearchResultsLeaveTwo> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedLeaveTwoAdaper(Context context, ArrayList<SearchResultsLeaveTwo> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_leave_two, null);
                holder = new ViewHolder();
                holder.txtname = (TextView) convertView.findViewById(R.id.customleavetwostudentname);
                holder.txtname.setTypeface(tfRobo);
                holder.txtfromdate = (TextView) convertView.findViewById(R.id.customleavetwofromdate);
                holder.txtfromdate.setTypeface(tfRobo);
                holder.txttodate = (TextView) convertView.findViewById(R.id.customleavetwotodate);
                holder.txttodate.setTypeface(tfRobo);
                holder.txtreason = (TextView) convertView.findViewById(R.id.customleavetworeason);
                holder.txtreason.setTypeface(tfRobo);
                holder.txtstatus = (TextView) convertView.findViewById(R.id.customleavetwostatus);
                holder.txtstatus.setTypeface(tfRobo);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtname.setText(searchArrayList.get(position).getLeaveTwoName());
            holder.txtfromdate.setText(searchArrayList.get(position).getLeaveTwoFromDate());
            holder.txttodate.setText(searchArrayList.get(position).getLeaveTwoToDate());
            holder.txtreason.setText(searchArrayList.get(position).getLeaveTwoReason());
            holder.txtstatus.setText(searchArrayList.get(position).getLeaveTwoStatus());
            if(searchArrayList.get(position).getLeaveTwoStatus().equalsIgnoreCase("Approved")){
                holder.txtstatus.setTextColor(Color.GREEN);
            }
            else {
                holder.txtstatus.setTextColor(Color.RED);
            }
            return convertView;
        }

        class ViewHolder {
            TextView txtname;
            TextView txtfromdate;
            TextView txttodate;
            TextView txtreason;
            TextView txtstatus;
        }
    }
}
