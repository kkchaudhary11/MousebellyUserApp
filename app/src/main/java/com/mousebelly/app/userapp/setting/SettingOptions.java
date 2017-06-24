package com.mousebelly.app.userapp.setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.userprofile.UserprofileMainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingOptions extends Fragment {

    Button button;


    public SettingOptions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting_options, container, false);

        button = (Button) v.findViewById(R.id.myprofile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.context,UserprofileMainActivity.class);
                startActivity(i);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

}
