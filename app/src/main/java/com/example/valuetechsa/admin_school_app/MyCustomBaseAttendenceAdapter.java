package com.example.valuetechsa.admin_school_app;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomBaseAttendenceAdapter extends BaseAdapter {
    private static ArrayList<SearchResultAttendence> searchArrayList;
    Typeface tfRobo;
    private LayoutInflater mInflater;

    public MyCustomBaseAttendenceAdapter(Context context, ArrayList<SearchResultAttendence> results) {
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
            convertView = mInflater.inflate(R.layout.custom_row_view_attendence, null);
            holder = new ViewHolder();
            holder.txtstudentname = (TextView) convertView.findViewById(R.id.attendencestudentname);
            holder.txtstudentname.setTypeface(tfRobo);
            holder.txtstudentid = (TextView) convertView.findViewById(R.id.attendencestudentid);
            holder.txtstudentid.setTypeface(tfRobo);
            holder.txtparentname = (TextView) convertView.findViewById(R.id.attendenceparentname);
            holder.txtparentname.setTypeface(tfRobo);
            holder.txtparentcontactno = (TextView) convertView.findViewById(R.id.attendenceparentcontactno);
            holder.txtparentcontactno.setTypeface(tfRobo);
            holder.txtcheckin = (ImageView) convertView.findViewById(R.id.attendencecheckinbox);
            //holder.txtcheckin.setTypeface(tfRobo);
            holder.txtcheckout = (ImageView) convertView.findViewById(R.id.attenddencecheckoubox);
            //holder.txtcheckout.setTypeface(tfRobo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtstudentname.setText(searchArrayList.get(position).getStudentnameresult());
        holder.txtstudentid.setText(searchArrayList.get(position).getStudentidresult());
        holder.txtparentname.setText(searchArrayList.get(position).getParentidresult());
        holder.txtparentcontactno.setText(searchArrayList.get(position).getParentcontactnoresult());
        if(searchArrayList.get(position).getCheckinresult().equalsIgnoreCase("yes")){
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
        }
        /*holder.txtcheckin.setText(searchArrayList.get(position).getCheckinresult());
        holder.txtcheckout.setText(searchArrayList.get(position).getCheckoutresult());*/

        return convertView;
    }

    static class ViewHolder {
        TextView txtstudentname;
        TextView txtstudentid;
        TextView txtparentname;
        TextView txtparentcontactno;
        /*TextView txtcheckin;
        TextView txtcheckout;*/
        ImageView txtcheckin;
        ImageView txtcheckout;
    }
}