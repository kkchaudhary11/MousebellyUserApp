package com.mousebelly.app.userapp.mealPlanner;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.IdManager;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealPlan extends Fragment {

    public static TextView totalAmount;
    public static LinearLayout staticCartLinearLayout;
    public static Dialog MealPlanCartdialog;
    public static MenuItem walletMenu;
    static LinearLayout l;
    static LinearLayout productDataLayout;
    static Context context;
    static ProgressBar mealPlannerProgressBar;

    LinearLayout linearLayout;
    LinearLayout melplanlayout;

    View v;


    public MealPlan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        v = inflater.inflate(R.layout.fragment_meal_plan, container, false);


        mealPlannerProgressBar = (ProgressBar) v.findViewById(R.id.mealplanner_progress_bar);

        melplanlayout = (LinearLayout) v.findViewById(R.id.MealPlanLinearLayout);

       linearLayout = (LinearLayout) v.findViewById(R.id.ProductsDataLayout);

        new LoadProducts().execute();

        return v;
    }



    public class LoadProducts extends AsyncTask<Void, Void, Void> {


        String respMealData;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mealPlannerProgressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected Void doInBackground(Void... voids) {


            respMealData = Server.s.get("https://mousebelly.herokuapp.com/mealplan/mealplan/");

            System.out.println("Meal Plan:");
            System.out.println(respMealData);

            productDataLayout = linearLayout;


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            MealPlanGetData mealPlan = new MealPlanGetData(MainActivity.context);

            mealPlan.loadData(respMealData);


            LoadMealPlan loadMealPlan = new LoadMealPlan();
            loadMealPlan.execute();

        }

    }


    public class LoadMealPlan extends AsyncTask<Void, Void, Void> {

        String resp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mealPlannerProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {



            MealPlan.l = melplanlayout;

            resp = Server.s.get("https://mousebelly.herokuapp.com/preorder/preorderbymail/" + LoginActivity.USERID);

            System.out.println("Order Plan:");
            System.out.println(resp);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Date dt = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMMM yyyy");


            for (int k = 0; k < 7; k++) {
                final RelativeLayout dateRelativeLayout = new RelativeLayout(MainActivity.context);

                ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class

                // get paint
                Paint paint = rectShapeDrawable.getPaint();

                // set border color, stroke and stroke width
                paint.setColor(ContextCompat.getColor(MainActivity.context, R.color.Amulet));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5); // you can change the value of 5
                paint.setShadowLayer(10, 10, 10, Color.BLACK);

                dateRelativeLayout.setBackgroundDrawable(rectShapeDrawable);

                final TextView dateValue = new TextView(MainActivity.context);

                IdManager.addId("DateTextView" + k);
                dateValue.setId((int) IdManager.stringToIdMap.get("DateTextView" + k));


                //cal.add(Calendar.DATE,k);

                if (k == 0)
                    cal.add(Calendar.DATE, 0);
                else
                    cal.add(Calendar.DATE, 1);

                dt = cal.getTime();
                //dateValue.setTag(formatter.format(dt).toString());

                dateValue.setText(formatter.format(dt).toString());
                dateValue.setPadding(20, 50, 20, 50);
                dateValue.setWidth(250);
                dateValue.setTextColor(Color.BLACK);


                dateRelativeLayout.addView(dateValue);
                melplanlayout.addView(dateRelativeLayout);
                //dateLayout.put(dateValue.getTag().toString(),melplanlayout);
                //System.out.println("Adding Date");
                //System.out.println(formatter.format(dt).toString());
                DateLayout.dateLayout.put(formatter.format(dt).toString(), dateRelativeLayout);
            }

            System.out.println("Meal Plan Layout : " + DateLayout.dateLayout);

            OrderPlan.loadData(resp);
            // GetProductsData.loadData();
//            pg.setVisibility(View.GONE);

            //this method will be running on UI thread

            // pdLoading.dismiss();

            mealPlannerProgressBar.setVisibility(View.GONE);
        }

    }

}
