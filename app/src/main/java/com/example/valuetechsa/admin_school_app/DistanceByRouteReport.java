package com.example.valuetechsa.admin_school_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.valuetechsa.admin_school_app.commonclass.Config;
import com.example.valuetechsa.admin_school_app.libs.Jsonfunctions;
import com.example.valuetechsa.admin_school_app.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ValueTechSA on 07-03-2017.
 */
public class DistanceByRouteReport extends Fragment {

    ProgressDialog progressDialog2=null;
    Spinner routelist;
    String selectedspinneritem;
    ListView lv1;
    Boolean isScrolling;
    int rowcount=0;
    RelativeLayout manage,listmanage;
    Typeface tfRobo,tfAdam;
    MyCustomBasedDistanceByrouteAdaper myCustomBasedDistanceByrouteAdaper;
    ArrayList<String> routeidfromserver=new ArrayList<String>();
    ArrayList<String> routenamefromserver=new ArrayList<String>();
    ArrayList<String> triptypefromserver=new ArrayList<String>();
    ArrayList<String> starttimefromserver=new ArrayList<String>();
    ArrayList<String> endtimefromserver=new ArrayList<String>();
    ArrayList<String> driveridfromserver=new ArrayList<String>();
    ArrayList<String> busidfromserver=new ArrayList<String>();
    ArrayList<String> routedistancefromserver=new ArrayList<String>();
    ArrayList<String> stopcountfromserver=new ArrayList<String>();
    ArrayList<String> busnamefromserver=new ArrayList<String>();

    ArrayList<String> routenameforlist=new ArrayList<String>();
    ArrayList<String> triptypeforlist=new ArrayList<String>();
    ArrayList<String> busnameforlist=new ArrayList<String>();
    ArrayList<String> noofstopsforlist=new ArrayList<String>();
    ArrayList<String> routedistanceforlist=new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_distance_by_route_reports,container,false);
        tfRobo = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        tfAdam = Typeface.createFromAsset(getActivity().getAssets(), "fonts/ADAM.CG PRO.OTF");
        TextView selectcrietria=(TextView)v.findViewById(R.id.selectcriteriaroutedistance);
        TextView selectroute=(TextView)v.findViewById(R.id.selectroutedistancebyroute);
        TextView individualselection=(TextView)v.findViewById(R.id.individualselectiontextbox);
        TextView routenamel=(TextView)v.findViewById(R.id.routenamedistancebyroutetextbox);
        TextView triptypel=(TextView)v.findViewById(R.id.triptypedistancebyroutetextbox);
        TextView busnamel=(TextView)v.findViewById(R.id.busnamedistancebyroutetextbox);
        TextView noofstopsl=(TextView)v.findViewById(R.id.noofstopsdistancebyroutetextbox);
        TextView routedistancel=(TextView)v.findViewById(R.id.routedistancedistancebyroutetextbox);
        Button getlistbutton=(Button) v.findViewById(R.id.getlistdistancebyroute);
        selectcrietria.setTypeface(tfRobo);
        selectroute.setTypeface(tfRobo);
        individualselection.setTypeface(tfRobo);
        routenamel.setTypeface(tfRobo);
        triptypel.setTypeface(tfRobo);
        busnamel.setTypeface(tfRobo);
        noofstopsl.setTypeface(tfRobo);
        routedistancel.setTypeface(tfRobo);
        getlistbutton.setTypeface(tfAdam);

        getlistbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                routenameforlist.clear();
                triptypeforlist.clear();
                busnameforlist.clear();
                noofstopsforlist.clear();
                routedistanceforlist.clear();
                if(selectedspinneritem.equalsIgnoreCase("Select Route")){
                    setLayoutInvisible();
                }
                else{
                    setLayoutVisible();
                    for(int i=0;i<routenamefromserver.size();i++){
                        if(routenamefromserver.get(i).equalsIgnoreCase(selectedspinneritem)){
                            rowcount=i;
                            break;
                        }
                    }
                    routenameforlist.add(routenamefromserver.get(rowcount));
                    triptypeforlist.add(triptypefromserver.get(rowcount));
                    busnameforlist.add(busnamefromserver.get(rowcount));
                    noofstopsforlist.add(stopcountfromserver.get(rowcount));
                    routedistanceforlist.add(routedistancefromserver.get(rowcount));
                    listgenerate();
                }
            }
        });

        routelist=(Spinner) v.findViewById(R.id.selectbusdistancebyroutetypespinner);
        manage=(RelativeLayout) v.findViewById(R.id.relativelayoutrouteroutecreeation);
        listmanage=(RelativeLayout) v.findViewById(R.id.relativelayoutlistrouteroute);
        new getRouteListFromServer().execute();
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

    public void listspinner(){
        int sum1=routenamefromserver.size();
        sum1=sum1+1;
        String[] busitems=new String[sum1];
        busitems[0]=getResources().getString(R.string.sj_select_route);
        for(int j=1;j<=routenamefromserver.size();j++){
            busitems[j]=routenamefromserver.get(j-1);
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
        routelist.setAdapter(adapterbus);
        routelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //routebusnonavigation=item;
                selectedspinneritem=item;
                Log.e("spinner selected",item);
                /*routenameforlist.clear();
                triptypeforlist.clear();
                busnameforlist.clear();
                noofstopsforlist.clear();
                routedistanceforlist.clear();
                if(item.equalsIgnoreCase("Select Route")){
                    setLayoutInvisible();
                }
                else{
                    setLayoutVisible();
                    for(int i=0;i<routenamefromserver.size();i++){
                        if(routenamefromserver.get(i).equalsIgnoreCase(item)){
                            rowcount=i;
                            break;
                        }
                    }
                    routenameforlist.add(routenamefromserver.get(rowcount));
                    triptypeforlist.add(triptypefromserver.get(rowcount));
                    busnameforlist.add(busnamefromserver.get(rowcount));
                    noofstopsforlist.add(stopcountfromserver.get(rowcount));
                    routedistanceforlist.add(routedistancefromserver.get(rowcount));
                    listgenerate();
                }*/
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    class getRouteListFromServer extends AsyncTask<Void,Void,Void> {
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
                    Log.e("url", Config.ip+"GetRouteDetails_api/RoutesListStops");
                    String jsonStr1 = sh.makeServiceCall(Config.ip+"GetRouteDetails_api/RoutesListStops",Jsonfunctions.GET);

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
                                    Log.e("route_id",obj.getString("route_id"));
                                    String route_id=obj.getString("route_id");
                                    routeidfromserver.add(route_id);
                                    Log.e("route_name",obj.getString("route_name"));
                                    String route_name=obj.getString("route_name");
                                    routenamefromserver.add(route_name);
                                    Log.e("trip_type",obj.getString("trip_type"));
                                    String trip_type=obj.getString("trip_type");
                                    triptypefromserver.add(trip_type);
                                    Log.e("start_time",obj.getString("start_time"));
                                    String start_time=obj.getString("start_time");
                                    starttimefromserver.add(start_time);
                                    Log.e("end_time",obj.getString("end_time"));
                                    String end_time=obj.getString("end_time");
                                    endtimefromserver.add(end_time);
                                    Log.e("driver_id",obj.getString("driver_id"));
                                    String driver_id=obj.getString("driver_id");
                                    driveridfromserver.add(driver_id);
                                    Log.e("bus_id",obj.getString("bus_id"));
                                    String bus_id=obj.getString("bus_id");
                                    busidfromserver.add(bus_id);
                                    Log.e("route_distance",obj.getString("route_distance"));
                                    String route_distance=obj.getString("route_distance");
                                    routedistancefromserver.add(route_distance);
                                    Log.e("stop_count",obj.getString("stop_count"));
                                    String stop_count=obj.getString("stop_count");
                                    stopcountfromserver.add(stop_count);
                                    Log.e("bus_name",obj.getString("bus_name"));
                                    String bus_name=obj.getString("bus_name");
                                    busnamefromserver.add(bus_name);
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


    public void listgenerate(){
        ArrayList<SearchResultsDistanceByRoute> searchResults = GetSearchResults();
        lv1 = (ListView) getActivity().findViewById(R.id.routecreateddistancebyroutelist);
        myCustomBasedDistanceByrouteAdaper=new MyCustomBasedDistanceByrouteAdaper(getActivity(), searchResults);
        lv1.setAdapter(myCustomBasedDistanceByrouteAdaper);
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

    private ArrayList<SearchResultsDistanceByRoute> GetSearchResults(){
        ArrayList<SearchResultsDistanceByRoute> results = new ArrayList<SearchResultsDistanceByRoute>();
        for (int i=0;i<routenameforlist.size();i++){
            SearchResultsDistanceByRoute sr1 = new SearchResultsDistanceByRoute();

            sr1.setRoutename(routenameforlist.get(i));
            if(triptypeforlist.get(i).equalsIgnoreCase("1")){
                sr1.setTriptype("Pick Up");
            }
            else if(triptypeforlist.get(i).equalsIgnoreCase("2")){
                sr1.setTriptype("Drop");
            }
            if(busnameforlist.get(i).equalsIgnoreCase("null")){
                sr1.setBusname("--");
            }
            else{
                sr1.setBusname(busnameforlist.get(i));
            }
            sr1.setNoofstops(noofstopsforlist.get(i));
            if(routedistanceforlist.get(i).equalsIgnoreCase("null")){
                sr1.setRoutedistance("--");
            }
            else {
                sr1.setRoutedistance(routedistanceforlist.get(i));
            }
            Log.e("inside list","#######################");
            results.add(sr1);
        }
        return results;
    }

    public class MyCustomBasedDistanceByrouteAdaper extends BaseAdapter {
        private ArrayList<SearchResultsDistanceByRoute> searchArrayList;
        Typeface tfRobo;
        private LayoutInflater mInflater;

        public
        MyCustomBasedDistanceByrouteAdaper(Context context, ArrayList<SearchResultsDistanceByRoute> results) {
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
                convertView = mInflater.inflate(R.layout.custom_row_view_distance_by_route, null);
                holder = new ViewHolder();
                Log.e("inside adaptor","#######################2");
                holder.txtroutename = (TextView) convertView.findViewById(R.id.customdistancebyroutename);
                holder.txtroutename.setTypeface(tfRobo);
                holder.txttriptype = (TextView) convertView.findViewById(R.id.customdistancebyroutetriptype);
                holder.txttriptype.setTypeface(tfRobo);
                holder.txtbusname = (TextView) convertView.findViewById(R.id.customdistancebyroutebusname);
                holder.txtbusname.setTypeface(tfRobo);
                holder.txtnoofstops = (TextView) convertView.findViewById(R.id.customdistancebyroutenoofstops);
                holder.txtnoofstops.setTypeface(tfRobo);
                holder.txtroutedistance = (TextView) convertView.findViewById(R.id.customdistancebyrouteroutedistance);
                holder.txtroutedistance.setTypeface(tfRobo);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());
            holder.txtroutename.setText(searchArrayList.get(position).getRoutename());
            holder.txttriptype.setText(searchArrayList.get(position).getTriptype());
            holder.txtbusname.setText(searchArrayList.get(position).getBusname());
            holder.txtnoofstops.setText(searchArrayList.get(position).getNoofstops());
            holder.txtroutedistance.setText(searchArrayList.get(position).getRoutedistance());
            return convertView;
        }

        class ViewHolder {
            TextView txtroutename;
            TextView txttriptype;
            TextView txtbusname;
            TextView txtnoofstops;
            TextView txtroutedistance;
        }
    }
}
