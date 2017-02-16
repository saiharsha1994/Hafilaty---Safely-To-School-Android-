package com.example.valuetechsa.admin_school_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home_Admin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__admin);
    }
    public void selectStudent(View view) {
        Intent intent = new Intent(this, Student_Route_Creation_Navigation.class);
        startActivity(intent);
    }
    public void hawkeyeclick(View view) {
        Intent intent = new Intent(this, Hawkeye_navigation.class);
        startActivity(intent);
    }
    public void drivermerit(View view) {
        Intent intent = new Intent(this, drivermeritnavigation.class);
        startActivity(intent);
    }
}
