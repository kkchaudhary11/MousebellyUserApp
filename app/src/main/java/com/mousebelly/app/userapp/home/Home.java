package com.mousebelly.app.userapp.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    public static LinearLayout FoodLayout;
    ProgressBar pg;
    ArrayList<String> HwfId = new ArrayList<>();
    public static Context homeContext;
    LinearLayout OptionLayout ;
    TextView fetchingProducts;

    Button logInButton,signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeContext = Home.this;

        FoodLayout = (LinearLayout)findViewById(R.id.food_layout);
        pg = (ProgressBar)findViewById(R.id.pg);
        OptionLayout = (LinearLayout)findViewById(R.id.options);

        logInButton = (Button)findViewById(R.id.logIn);
        signUpButton = (Button)findViewById(R.id.signUp);

        fetchingProducts = (TextView) findViewById(R.id.fetching_products);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLogin = new Intent(Home.this, LoginActivity.class);
                startActivity(goToLogin);
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToLogin = new Intent(Home.this, LoginActivity.class);
                startActivity(goToLogin);
                finish();
            }
        });

        new LoadHWF().execute();

    }


    public class LoadHWF extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pg.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            GetHouseWife getHouseWife = new GetHouseWife();
            HwfId = getHouseWife.loadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            System.out.println("HouseWife IDs : " + HwfId);

            if(!HwfId.isEmpty()) {
                new LoadProducts().execute();
            }else{
                CustomToast.Toast(Home.this,"Something went wrong");
            }
        }

    }

    public class LoadProducts extends AsyncTask<Void, Void, Void> {

        GetFoodData getFoodData = new GetFoodData();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for (String hwfid : HwfId) {
                getFoodData.loadData(APIs.prod_approval_prod_hwf + hwfid);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //show the options

            getFoodData.showData();

            pg.setVisibility(View.GONE);
            fetchingProducts.setVisibility(View.GONE);



        }

    }

}
