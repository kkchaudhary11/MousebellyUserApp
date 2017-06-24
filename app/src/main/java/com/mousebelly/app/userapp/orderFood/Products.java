package com.mousebelly.app.userapp.orderFood;


import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.feedback.LoadOrdersForFeedback;
import com.mousebelly.app.userapp.payment.PaymentMainActivity;

import java.util.ArrayList;
import java.util.Iterator;



/**
 * A simple {@link Fragment} subclass.
 */
public class Products extends Fragment {


    public static LinearLayout productsLayout;

    public static TextView totalAmount;
    public static Dialog ProductCartDialog;
    public static LinearLayout staticCartLinearLayout;
    public LinearLayout Options;
    ProgressBar pg;

    Button changeLocation, showFav;


    public Products() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_products, container, false);

        MainActivity.navigationView.getMenu().getItem(0).setChecked(true);

        Products.productsLayout = (LinearLayout) v.findViewById(R.id.ProductsLayout);
        Options = (LinearLayout) v.findViewById(R.id.options);

        pg = (ProgressBar) v.findViewById(R.id.products_Progress_Bar);

        changeLocation = (Button)v.findViewById(R.id.location_change);


        showFav = (Button)v.findViewById(R.id.show_fav);
            GetProductsData.productUnits.clear();
            LoadProducts loadProducts = new LoadProducts();
            loadProducts.execute();


        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.context, ChangeLocation.class);
                startActivity(i);
            }
        });


        showFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout l = Products.productsLayout;

                if (showFav.getText().equals("All food")) {
                    /*
                    l.removeAllViews();
                    showFav.setText("Available Fav");*/

                    Products products = new Products();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.relative_layout_fragment,products, products.getTag()).commit();

                }

                ArrayList<String> favItems = LoginActivity.user.getFav();
                System.out.println("Fav. Items : " + favItems);

                if (!favItems.isEmpty() && !GetProductsData.productUnits.isEmpty() && favItems != null) {

                    for (String foodId : favItems) {
                        Iterator it = GetProductsData.productUnits.keySet().iterator();
                        while (it.hasNext()) {
                            String key = it.next().toString();

                            if (!key.equals(foodId)) {
                                for (int i = 0; i < l.getChildCount(); i++) {
                                    RelativeLayout rl = (RelativeLayout) l.getChildAt(i);

                                /*System.out.println("Tag : " + rl.getTag());
                                System.out.println("Food : " + foodId);
*/
                                    if (rl.getTag().equals(foodId)) {
                                        System.out.println("removing layout");
                                        l.removeView(rl);
                                        showFav.setText("All food");
                                    } else {

                                    }
                                }
                            }
                        }
                    }
                    if (showFav.getText().equals("Available Fav")) {
                        CustomToast.Toast(MainActivity.context, "Your Favourite Food is not available");
                    }
                } else {
                    CustomToast.Toast(MainActivity.context, "No Favourite Food");
                }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }



    public class LoadProducts extends AsyncTask<Void, Void, Void> {

        String resp;
        GetProductsData getProductsData = new GetProductsData();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pg.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            resp = Server.s.get(APIs.housewifesign_getauthorisedhw + LoginActivity.user.getLat() + "/" + LoginActivity.user.getLongitude());
            getProductsData.convertDataToArrayList(resp);
            System.out.println(resp);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //show the options
            Options.setVisibility(View.VISIBLE);

            getProductsData.loadData();


            //TODO uncomment for advertiesments
           /* AdMainActivity adMainActivity = new AdMainActivity();
            adMainActivity.startAd();*/

            pg.setVisibility(View.GONE);

            checkFav();
            //load pending feedbacks
            new LoadOrdersForFeedback().execute();


        }

    }

    public void checkFav(){
        ArrayList<String> favItems = LoginActivity.user.getFav();
        if (!favItems.isEmpty() && !GetProductsData.productUnits.isEmpty() && favItems != null) {

            for (String foodId : favItems) {

                for(ProductUnit p : GetProductsData.productUnits.values()){

                    if(p.getProd_id().equals(foodId)){
                        System.out.println("fav food found");
                        showFav.setVisibility(View.VISIBLE);
                    }

                }
            }
        } else {
            showFav.setVisibility(View.GONE);
        }
    }


    public static void Productcart(){
        if (Cart.cartItems.isEmpty()) {
            CustomToast.Toast(MainActivity.context, "Cart is Empty");
            return;
        }

        ProductCartDialog = new Dialog(MainActivity.context);
        ProductCartDialog.setTitle("Cart");
        ProductCartDialog.setContentView(R.layout.products_user_cart_dialogbox);

        LinearLayout cartLinearLayout = (LinearLayout) ProductCartDialog.findViewById(R.id.CartLinearLayout);

        Products.staticCartLinearLayout = cartLinearLayout;

        totalAmount = (TextView) ProductCartDialog.findViewById(R.id.totalAmount);
        System.out.println("Cart Amount : " + Cart.cartAmount);
        totalAmount.setText(String.valueOf(Cart.cartAmount));

        Iterator it = Cart.cartItems.keySet().iterator();

        while (it.hasNext()) {
            ProductUnit productUnit = Cart.cartItems.get(it.next());
            ProductUnit p = new ProductUnit();
            RelativeLayout rl = p.showCart(productUnit);
            //RelativeLayout rl = ProductUnit.showCart(productUnit);

            cartLinearLayout.addView(rl);
        }


        Button dialogButtonOK = (Button) ProductCartDialog.findViewById(R.id.dialogButtonOK);


        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movetopayment = new Intent(MainActivity.context, PaymentMainActivity.class);
                movetopayment.putExtra("Activity","OrderPayment");
                MainActivity.context.startActivity(movetopayment);
            }
        });


        Button dialogButtonCancel = (Button) ProductCartDialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductCartDialog.dismiss();
            }
        });

        ProductCartDialog.show();
    }


}
