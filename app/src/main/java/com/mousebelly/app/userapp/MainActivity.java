package com.mousebelly.app.userapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.favourite.Fav;
import com.mousebelly.app.userapp.logout.Logout;
import com.mousebelly.app.userapp.mealPlanner.MealPlan;
import com.mousebelly.app.userapp.orderFood.Products;
import com.mousebelly.app.userapp.orderStack.OrderStack;
import com.mousebelly.app.userapp.preOrderStack.PreOrder;
import com.mousebelly.app.userapp.wallet.Wallet;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;
import static com.mousebelly.app.userapp.Login.LoginActivity.USERNAME;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context context;
    public static NavigationView navigationView;
    public static int displayWidth;
    public static int displayHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        calDisplay();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.user_email_id);
        TextView nav_username = (TextView) hView.findViewById(R.id.user_name);

        nav_user.setText(USERID);
        nav_username.setText(USERNAME);


        navigationView.getMenu().getItem(0).setChecked(true);

        //displaying wallet amount on navigation drawer
        navigationView.getMenu().getItem(4).setTitle("₹"+ WalletHandler.WalletAmount);

        //load food
        Products products = new Products();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.relative_layout_fragment,products, products.getTag()).commit();

        customActionBar();


        fab.setOnClickListener(new View.OnClickListener() {
           /* public Context getActivity() {
                return context;
            }*/

            @Override
            public void onClick(View view) {
                Products.Productcart();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Do You Want to Exit")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

            Products products = new Products();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment,products, products.getTag()).commit();


        } else if (id == R.id.fav) {
            Fav fav= new Fav();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment,fav ,fav.getTag()).commit();

        } else if (id == R.id.meal_planner) {
            MealPlan mealPlan= new MealPlan();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment,mealPlan,mealPlan.getTag()).commit();


        } else if (id == R.id.offers) {

        } else if (id == R.id.wallet) {
            Wallet wallet= new Wallet();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment,wallet,wallet.getTag()).commit();


        }else if(id == R.id.order_stack){
            OrderStack orderStack = new OrderStack();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment,orderStack,orderStack.getTag()).commit();

        }else if(id == R.id.pre_order_stack){
            PreOrder preOrder = new PreOrder();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relative_layout_fragment,preOrder,preOrder.getTag()).commit();
        }

        else if (id == R.id.log_out) {

            new Logout().execute();


        } else if (id == R.id.setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void customActionBar(){
        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ActionBar.LayoutParams titleLayout = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);

        Typeface trumpitFaceHeader = Typeface.createFromAsset(getAssets(), this.getResources().getString(R.string.font_face));

        LinearLayout mainTitleLayout = new LinearLayout(MainActivity.this);
        mainTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mainTitleLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout maintitle = new LinearLayout(MainActivity.this);
        maintitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maintitle.setClipToOutline(true);
        }
        maintitle.setBackgroundResource(R.drawable.customborder);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maintitle.setElevation(2.0f);
        }
        mainTitleLayout.addView(maintitle);


        TextView actionBarText = new TextView(MainActivity.this);
        actionBarText.setTextColor(ContextCompat.getColor(MainActivity.context,R.color.Tamarillo));
        actionBarText.setPadding(30, 2, 0, 2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBarText.setElevation(4.0f);
        }
        actionBarText.setText("Mouse");
        //actionBarText.setBackgroundColor(Color.parseColor("#ffffff"));
        actionBarText.setTextSize(25);
        actionBarText.setTypeface(trumpitFaceHeader);


        actionBarText.setLayoutParams(titleLayout);
        maintitle.addView(actionBarText);

        TextView actionBarText1 = new TextView(MainActivity.this);
        actionBarText1.setTextColor(ContextCompat.getColor(MainActivity.context,R.color.Yellow_Orange));
        actionBarText1.setPadding(0, 2, 30, 2);
        actionBarText1.setText(" - Belly");
        actionBarText1.setTypeface(trumpitFaceHeader);
        //actionBarText1.setBackgroundColor(Color.parseColor("#ffffff"));
        actionBarText1.setTextSize(25);
        maintitle.addView(actionBarText1);
        actionBar.setTitle("");
        actionBar.setCustomView(mainTitleLayout);

        //END OF CUSTOM ACTION BAR
        /////////////////////////


        maintitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Products products = new Products();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layout_fragment,products, products.getTag()).commit();
            }
        });

    }


    public void calDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;

    }



}
