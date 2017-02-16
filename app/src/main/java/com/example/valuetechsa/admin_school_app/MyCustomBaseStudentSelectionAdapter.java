package com.example.valuetechsa.admin_school_app;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomBaseStudentSelectionAdapter extends BaseAdapter {
    private static ArrayList<SearchResultStudentSelection> searchArrayList;
    Typeface tfRobo;
    private LayoutInflater mInflater;

    public MyCustomBaseStudentSelectionAdapter(Context context, ArrayList<SearchResultStudentSelection> results) {
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
            convertView = mInflater.inflate(R.layout.custom_row_view_student_selection, null);
            holder = new ViewHolder();
            holder.txtselectedstudentname = (TextView) convertView.findViewById(R.id.selectedstudentnametextview);
            holder.txtselectedstudentname.setTypeface(tfRobo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtselectedstudentname.setText(searchArrayList.get(position).getStudentnameresult());
        Log.e("in side adaptop check",searchArrayList.get(position).getStudentnameresult());


        return convertView;
    }

    static class ViewHolder {
        TextView txtselectedstudentname;
        ;
    }
}