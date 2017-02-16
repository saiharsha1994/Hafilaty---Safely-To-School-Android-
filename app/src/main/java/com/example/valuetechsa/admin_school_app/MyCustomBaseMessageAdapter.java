package com.example.valuetechsa.admin_school_app;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyCustomBaseMessageAdapter extends BaseAdapter {
    List<SearchResultMessage> list;
    Typeface tfRobo;
    private static ArrayList<SearchResultMessage> searchArrayList;

    private LayoutInflater mInflater;

    public MyCustomBaseMessageAdapter(Context context, ArrayList<SearchResultMessage> results) {
        searchArrayList = results;
        tfRobo = Typeface.createFromAsset(context.getAssets(), "fonts/ROBOTO-LIGHT.TTF");
        mInflater = LayoutInflater.from(context);
        this.list=results;
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
            convertView = mInflater.inflate(R.layout.custom_row_view_message, null);
            holder = new ViewHolder();
            holder.txtType = (TextView) convertView.findViewById(R.id.messagetype);
            holder.txtType.setTypeface(tfRobo);
            holder.txtName = (TextView) convertView.findViewById(R.id.messagename);
            holder.txtName.setTypeface(tfRobo);
            holder.txtID = (TextView) convertView.findViewById(R.id.messageid);
            holder.txtID.setTypeface(tfRobo);
            holder.txtLoction = (TextView) convertView.findViewById(R.id.messagelocation);
            holder.txtLoction.setTypeface(tfRobo);
            holder.txtContactDetails = (TextView) convertView.findViewById(R.id.messagecontact);
            holder.txtContactDetails.setTypeface(tfRobo);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkboxrowmessage);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    list.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                }
            });


            convertView.setTag(holder);
            convertView.setTag(R.id.checkboxrowmessage, holder.checkBox);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtType.setText(searchArrayList.get(position).getType());
        holder.txtName.setText(searchArrayList.get(position).getName());
        holder.txtID.setText(searchArrayList.get(position).getId());
        holder.txtLoction.setText(searchArrayList.get(position).getLocation());
        holder.txtContactDetails.setText(searchArrayList.get(position).getContact());
        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(list.get(position).isSelected());
        return convertView;
    }

    static class ViewHolder {
        TextView txtType;
        TextView txtName;
        TextView txtID;
        TextView txtLoction;
        TextView txtContactDetails;
        CheckBox checkBox;
    }
}
