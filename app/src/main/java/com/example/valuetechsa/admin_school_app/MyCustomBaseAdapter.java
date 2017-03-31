package com.example.valuetechsa.admin_school_app;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomBaseAdapter extends BaseAdapter {
    private static ArrayList<SearchResults> searchArrayList;
    Typeface tfRobo;
    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<SearchResults> results) {
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
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.txtrouteid = (TextView) convertView.findViewById(R.id.routeidhawkeye);
            holder.txtrouteid.setTypeface(tfRobo);
            holder.txtplateno = (TextView) convertView.findViewById(R.id.platenohawkeye);
            holder.txtplateno.setTypeface(tfRobo);
            holder.txtdrivername = (TextView) convertView.findViewById(R.id.drivernamehawkeye);
            holder.txtdrivername.setTypeface(tfRobo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int icolor=searchArrayList.get(position).getColorchanege();
        SpannableStringBuilder sb = new SpannableStringBuilder(" Vehicle: " + searchArrayList.get(position).getPlateno());
        StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        sb.setSpan(b, 0, 0 + 9, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        SpannableStringBuilder sb1 = new SpannableStringBuilder(" Driver: " + searchArrayList.get(position).getDrivername());
        StyleSpan b1 = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        sb1.setSpan(b1, 0, 0 + 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        holder.txtrouteid.setText(searchArrayList.get(position).getRoutename());
        holder.txtplateno.setText(sb);
        holder.txtdrivername.setText(sb1);
        if(icolor==1){
            LinearLayout layout =(LinearLayout)convertView.findViewById(R.id.changecolor);
            LinearLayout layoutrow =(LinearLayout)convertView.findViewById(R.id.changecolorrow);
            layout.setBackgroundResource(R.drawable.tablecolorred);
            layoutrow.setBackgroundResource(R.drawable.tablerowcolorred);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txtrouteid;
        TextView txtplateno;
        TextView txtdrivername;

    }
}
