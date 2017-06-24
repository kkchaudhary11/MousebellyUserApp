package com.mousebelly.app.userapp.userprofile;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

import com.mousebelly.app.userapp.R;

@SuppressWarnings("deprecation")
public class UserprofileMainActivity extends TabActivity {
    static TabHost tabHost;
    /**
     * Called when the activity is first created.
     */
    UserprofileUserBean check = new UserprofileUserBean();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofileactivity_main);

        tabHost = (TabHost) findViewById(android.R.id.tabhost);



        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third tab");


        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("1. Profile");
        tab1.setContent(new Intent(this, UserprofileUserPro.class));

        tab2.setIndicator("2. Address");
        tab2.setContent(new Intent(this, UserprofileMapsActivity.class));


        tab3.setIndicator("3. Upload Picture");
        tab3.setContent(new Intent(this, UserprofileUploadphoto.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(15);
            tabHost.getTabWidget().getChildAt(i).setEnabled(false);
        }


    }

    public void display() {

        tabHost.setCurrentTab(1);
    }

    public void photo() {

        tabHost.setCurrentTab(2);
    }
}