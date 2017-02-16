package com.example.valuetechsa.admin_school_app;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomBaseRouteAdapter extends BaseAdapter {
    private static ArrayList<SearchResultRoute> searchArrayList;
    Typeface tfRobo;
    private LayoutInflater mInflater;

    public MyCustomBaseRouteAdapter(Context context, ArrayList<SearchResultRoute> results) {
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

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view_route, null);
            holder = new ViewHolder();
            holder.txtroutename = (TextView) convertView.findViewById(R.id.routenamecreation);
            holder.txtroutename.setTypeface(tfRobo);
            holder.txttime = (TextView) convertView.findViewById(R.id.timecreation);
            holder.txttime.setTypeface(tfRobo);
            holder.txtnoofstops = (TextView) convertView.findViewById(R.id.noofstopscreation);
            holder.txtnoofstops.setTypeface(tfRobo);
            holder.txtdrivername= (TextView) convertView.findViewById(R.id.drivernamecreation);
            holder.txtdrivername.setTypeface(tfRobo);
            holder.txtbusno= (TextView) convertView.findViewById(R.id.busnocreation);
            holder.txtbusno.setTypeface(tfRobo);
            //holder.phone3= (TextView) convertView.findViewById(R.id.phone4);
            holder.txtselectedimage = (ImageView) convertView.findViewById(R.id.clickonimage);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtroutename.setText(searchArrayList.get(position).getRoutenameresult());
        Log.e("position",position+"");
        Log.e("value",searchArrayList.get(position).getRoutenameresult());
        holder.txttime.setText(searchArrayList.get(position).getTimeresult());
        holder.txtnoofstops.setText(searchArrayList.get(position).getNoofstopsresult());
        holder.txtdrivername.setText(searchArrayList.get(position).getDrivernameresult());
        holder.txtbusno.setText(searchArrayList.get(position).getBusnoresult());
        //holder.phone3.setText("comming soon");
        holder.txtselectedimage.setTag(position);



        return convertView;
    }

    static class ViewHolder {
        TextView txtroutename;
        TextView txttime;
        TextView txtnoofstops;
        TextView txtdrivername;
        TextView txtbusno;
        ImageView txtselectedimage;
        //TextView phone3;


    }
}