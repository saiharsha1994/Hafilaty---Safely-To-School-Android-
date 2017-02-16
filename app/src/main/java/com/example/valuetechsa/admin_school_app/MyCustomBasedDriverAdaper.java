package com.example.valuetechsa.admin_school_app;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomBasedDriverAdaper extends BaseAdapter {
    private static ArrayList<SearchResultDriver> searchArrayList;
    Typeface tfRobo;
    private LayoutInflater mInflater;

    public MyCustomBasedDriverAdaper(Context context, ArrayList<SearchResultDriver> results) {
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
            convertView = mInflater.inflate(R.layout.custom_row_view_driver, null);
            holder = new ViewHolder();
            holder.txtdrivername = (TextView) convertView.findViewById(R.id.driverdrivername);
            holder.txtdrivername.setTypeface(tfRobo);
            holder.txtdrivernationality = (TextView) convertView.findViewById(R.id.drivernationality);
            holder.txtdrivernationality.setTypeface(tfRobo);
            holder.txtdriverassignto = (TextView) convertView.findViewById(R.id.driverassiggnto);
            holder.txtdriverassignto.setTypeface(tfRobo);
            holder.txtdrivercontactno = (TextView) convertView.findViewById(R.id.drivermobileno);
            holder.txtdrivercontactno.setTypeface(tfRobo);
            holder.txtdriveroptionsspinner=(Spinner)convertView.findViewById(R.id.driveroption);

            final String[] triptypeattendence=new String[4];
            triptypeattendence[0]="Action";
            triptypeattendence[1]="Edit";
            triptypeattendence[2]="View";
            triptypeattendence[3]="Delete";
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
            holder.txtdriveroptionsspinner.setAdapter(adapterdriverdropown);
            holder.txtdriveroptionsspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //routedrivernamenavigation=item;
                    //routeattendenceselected=item;
                    Log.e("spinner selected",item);
                    Log.e("Line No",""+view.getTag());
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            /*holder.txtdriiveroptions = (TextView) convertView.findViewById(R.id.driveroption);
            holder.txtdriiveroptions.setTypeface(tfRobo);*/
            //holder.txtcheckout = (ImageView) convertView.findViewById(R.id.attenddencecheckoubox);
            //holder.txtcheckout.setTypeface(tfRobo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtdrivername.setText(searchArrayList.get(position).getDrivername());
        holder.txtdrivernationality.setText(searchArrayList.get(position).getNationality());
        holder.txtdriverassignto.setText(searchArrayList.get(position).getAssignto());
        holder.txtdrivercontactno.setText(searchArrayList.get(position).getMobileno());

        //holder.txtdriiveroptions.setText(searchArrayList.get(position).getOptions());


        /*if(searchArrayList.get(position).getCheckinresult().equalsIgnoreCase("yes")){
            //holder.txtcheckin.setBackgroundResource(R.drawable.present);
            holder.txtcheckin.setImageResource(R.drawable.present);
        }
        else{
            holder.txtcheckin.setImageResource(R.drawable.absent);
        }
        if(searchArrayList.get(position).getCheckoutresult().equalsIgnoreCase("yes")){
            holder.txtcheckout.setImageResource(R.drawable.present);
        }
        else{
            holder.txtcheckout.setImageResource(R.drawable.absent);
        }*/
        /*holder.txtcheckin.setText(searchArrayList.get(position).getCheckinresult());
        holder.txtcheckout.setText(searchArrayList.get(position).getCheckoutresult());*/

        return convertView;
    }

    static class ViewHolder {
        TextView txtdrivername;
        TextView txtdrivernationality;
        TextView txtdriverassignto;
        TextView txtdrivercontactno;
        TextView txtdriiveroptions;
        Spinner txtdriveroptionsspinner;
       /* TextView txtcheckout;
        ImageView txtcheckin;
        ImageView txtcheckout;*/
    }
}