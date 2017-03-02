package com.example.valuetechsa.admin_school_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.net.URLEncoder;
import java.util.ArrayList;

public class LeavePageOne extends Fragment {

    ArrayList<String> busnamelistfromserver=new ArrayList<String>();
    ArrayList<String> busidlistfromserver=new ArrayList<String>();
    ArrayList<String> studentnametfromserver=new ArrayList<String>();
    ArrayList<String> fromdatefromserver=new ArrayList<String>();
    ArrayList<String> todatefromserver=new ArrayList<String>();
    ArrayList<String> reasonfromserver=new ArrayList<String>();
    ArrayList<String> statusfromserver=new ArrayList<String>();
    ArrayList<String> numberofdaysfromserver=new ArrayList<String>();
    ArrayList<String> appliedonfromserver=new ArrayList<String>();
    ArrayList<String> leaveidfromserver=new ArrayList<String>();
    int globalbusid,globalnumberinlist=0,globalcount=0;
    int globalposition=0,globalstatus=0;
    RelativeLayout manage,listmanage;
    ProgressDialog progressDialog2=null;
    Jsonfunctions sh;
    String response="",url="";
    Spinner buslistspinner;
    ListView lv1;
    Boolean isScrolling;
    Typeface tfRobo;
    MyCustomBasedLeaveoneAdaper myCustomBasedLeaveoneAdaper;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_leave_page_one,container,false);
        tfRobo = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        TextView studentlist=(TextView)v.findViewById(R.id.leaveonelisttextbox);
        TextView studentlistname=(TextView)v.findViewById(R.id.leaveonestudentnametextbox);
        TextView studentlistfromdate=(TextView)v.findViewById(R.id.leaveonefromdatetextbox);
        TextView studentlisttodate=(TextView)v.findViewById(R.id.leaveonetodatetextbox);
        TextView studentlistreason=(TextView)v.findViewById(R.id.leaveonereasontextbox);
        TextView studentlisstatus=(TextView)v.findViewById(R.id.leaveonestatustextbox);
        studentlist.setTypeface(tfRobo);
        studentlistname.setTypeface(tfRobo);
        studentlistfromdate.setTypeface(tfRobo);
        studentlisttodate.setTypeface(tfRobo);
        studentlistreason.setTypeface(tfRobo);
        studentlisstatus.setTypeface(tfRobo);
        new getBusListFromServer().execute();
        manage=(RelativeLayout) v.findViewById(R.id.relativelayoutleaveonecreeation);
        listmanage=(RelativeLayout) v.findViewById(R.id.leaveonelist);
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
                                    if(obj.getString("status").equalsIgnoreCase("2")||obj.getString("status").equalsIgnoreCase("3")){

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
                    Log.e("inside else","#######################");
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

    class addeditleavedetailstoserver extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            progressDialog2 = ProgressDialog.show(getActivity(), getResources().getString(R.string.sj_please_wait),
                    getResources().getString(R.string.sj_fetching_information), true);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{

                sh = new Jsonfunctions();
                Log.e("@@@@","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                url= Config.ip+"Attendance_api/editLeaveRequest/leave_id/"+globalposition+"/status/"+globalstatus;
                Log.e("url created",url);
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
                                //Toast.makeText(getActivity(), "Driver Added Successfully", Toast.LENGTH_SHORT).show();
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
            Intent intent = getActivity().getIntent();
            getActivity().finish();
            startActivity(intent);
            //new getStudentsListFromServer().execute();
        }
    }

    public void fillbuslistspinner(){

        buslistspinner=(Spinner) getActivity().findViewById(R.id.leaveonespinneroption);
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
        ArrayList<SearchResultsLeaveOne> searchResults = GetSearchResults();
        lv1 = (ListView) getActivity().findViewById(R.id.leaveoneselectedlist);
        myCustomBasedLeaveoneAdaper=new MyCustomBasedLeaveoneAdaper(getActivity(), searchResults);
        lv1.setAdapter(myCustomBasedLeaveoneAdaper);
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

    private ArrayList<SearchResultsLeaveOne> GetSearchResults(){
        ArrayList<SearchResultsLeaveOne> results = new ArrayList<SearchResultsLeaveOne>();
        for (int i=0;i<studentnametfromserver.size();i++){
            SearchResultsLeaveOne sr1 = new SearchResultsLeaveOne();
            sr1.setLeaveoneName(studentnametfromserver.get(i));
            sr1.setLeaveoneFromDate(fromdatefromserver.get(i));
            sr1.setLeaveoneToDate(todatefromserver.get(i));
            sr1.setLeaveoneReason(reasonfromserver.get(i));
            Log.e("inside list","#######################");
            /*if(statusfromserver.get(i).equalsIgnoreCase("2")){
                globalstatus="Approved";
            }
            else{
                globalstatus="Rejected";
            }*/
            //sr1.setLeaveoneStatus(statusfromserver.get(i));
            results.add(sr1);
        }
        return results;
    }

    public class MyCustomBasedLeaveoneAdaper extends BaseAdapter {
        private ArrayList<SearchResultsLeaveOne> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedLeaveoneAdaper(Context context, ArrayList<SearchResultsLeaveOne> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_leave_one, null);
                holder = new ViewHolder();
                Log.e("inside adaptor","#######################2");
                holder.txtname = (TextView) convertView.findViewById(R.id.customleaveonestudentname);
                holder.txtname.setTypeface(tfRobo);
                holder.txtfromdate = (TextView) convertView.findViewById(R.id.customleaveonefromdate);
                holder.txtfromdate.setTypeface(tfRobo);
                holder.txttodate = (TextView) convertView.findViewById(R.id.customleaveonetodate);
                holder.txttodate.setTypeface(tfRobo);
                holder.txtreason = (TextView) convertView.findViewById(R.id.customleaveonereason);
                holder.txtreason.setTypeface(tfRobo);
                /*holder.txtstatus = (TextView) convertView.findViewById(R.id.customleaveonestatus);
                holder.txtstatus.setTypeface(tfRobo);*/
                holder.txtstatus=(Spinner)convertView.findViewById(R.id.customleaveonestatus);

                Log.e("inside adaptor","#######################3");
                final String[] triptypeattendence=new String[3];
                triptypeattendence[0]="Action";
                triptypeattendence[1]="Approve";
                triptypeattendence[2]="Reject";

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
                holder.txtstatus.setAdapter(adapterdriverdropown);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    String item = parent.getItemAtPosition(position1).toString();
                    //routedrivernamenavigation=item;
                    //routeattendenceselected=item;
                    Log.e("spinner selected",item);
                    Log.e("Row",""+position);
                    if(item.equalsIgnoreCase("Action")){

                    }
                    else if(item.equalsIgnoreCase("Approve")){
                        globalposition=Integer.parseInt(leaveidfromserver.get(position));
                        globalstatus=2;
                        new addeditleavedetailstoserver().execute();
                    }
                    if(item.equalsIgnoreCase("Reject")){
                        globalposition=Integer.parseInt(leaveidfromserver.get(position));
                        globalstatus=3;
                        new addeditleavedetailstoserver().execute();
                    }

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtname.setText(searchArrayList.get(position).getLeaveoneName());
            holder.txtfromdate.setText(searchArrayList.get(position).getLeaveoneFromDate());
            holder.txttodate.setText(searchArrayList.get(position).getLeaveoneToDate());
            holder.txtreason.setText(searchArrayList.get(position).getLeaveoneReason());
            //holder.txtstatus.setText(searchArrayList.get(position).getLeaveoneStatus());
            return convertView;
        }

        class ViewHolder {
            TextView txtname;
            TextView txtfromdate;
            TextView txttodate;
            TextView txtreason;
            Spinner txtstatus;
        }
    }
}
