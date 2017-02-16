package com.example.valuetechsa.admin_school_app;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class Student_attendence extends AppCompatActivity {
TableRow[] listofrows=new TableRow[10000];
    int count=0;
    TableRow row;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendence);
        Spinner dropdown = (Spinner)findViewById(R.id.selectroutespinner);
        String[] items = new String[]{"Morning","Evening"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        ArrayList<SearchResultAttendence> searchResults = GetSearchResults();

        final ListView lv1 = (ListView) findViewById(R.id.attendencelist);
        lv1.setAdapter(new MyCustomBaseAttendenceAdapter(this, searchResults));
    }

    private ArrayList<SearchResultAttendence> GetSearchResults(){
        ArrayList<SearchResultAttendence> results = new ArrayList<SearchResultAttendence>();

        SearchResultAttendence sr1 = new SearchResultAttendence();

        return results;
    }


}
