package com.mousebelly.app.userapp.payment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.orderFood.Cart;
import com.mousebelly.app.userapp.orderFood.Products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PaymentStatus extends Fragment {

    List<String> OrderIDArray;
    List<String> OrderUserArray;
    List<String> OrderQuantityArray;
    List<String> OrderHwfNameArray;
    List<String> OrderProductNameArray;
    List<String> OrderProductIDArray;
    List<String> OrderStatusArray;
    List<String> OrderTotalBillArray;
    List<String> OrderPaymentStatusArray;
    List<String> OrderOrderDateArray;
    List<String> OrderSystemIDArray;

    String OrderID;

    String OrderPaymentStatus;
    String OrderTotalBill;

    ProgressBar pb;
    String jsonstr;
    TextView payorderid, paystatus, paybill, thankyouTextView;
    ImageView paymentStatusImage;
    Button cont;
    RelativeLayout OrderStatusDetailsLayout;


    public PaymentStatus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_payment_status, container, false);


        payorderid = (TextView) v.findViewById(R.id.orderidofpaiditem);
        paystatus = (TextView) v.findViewById(R.id.statusofpaiditem);
        paybill = (TextView) v.findViewById(R.id.totalbillofpaiditem);
        thankyouTextView = (TextView) v.findViewById(R.id.thankyou);
        paymentStatusImage = (ImageView) v.findViewById(R.id.status_imageview);
        pb = (ProgressBar) v.findViewById(R.id.order_progress);
        cont = (Button) v.findViewById(R.id.cont);
        OrderStatusDetailsLayout = (RelativeLayout)v.findViewById(R.id.order_status_details_layout);

        //TODO uncomment to go to order stack
      /*  cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Successfull.this, OrderStackMainActivity.class);
                startActivity(intent);
            }
        });*/

        new PaySuc().execute();
        // ORDERID = cart.getOrderId();
        System.out.println("This is order id of User : " + OrderPayment.orderID);

        // Inflate the layout for this fragment
        return v;
    }


    public class PaySuc extends AsyncTask<Void, Void, Void> {


        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
            OrderStatusDetailsLayout.setVisibility(View.INVISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            jsonstr = Server.s.get(APIs.order_order3 + OrderPayment.orderID);
            if (jsonstr != null) {
                try {
                    JSONArray jaar = new JSONArray(jsonstr);

                    OrderIDArray = new ArrayList<>();
                    OrderUserArray = new ArrayList<>();
                    OrderQuantityArray = new ArrayList<>();
                    OrderHwfNameArray = new ArrayList<>();
                    OrderProductNameArray = new ArrayList<>();
                    OrderProductIDArray = new ArrayList<>();
                    OrderStatusArray = new ArrayList<>();
                    OrderTotalBillArray = new ArrayList<>();
                    OrderPaymentStatusArray = new ArrayList<>();
                    OrderOrderDateArray = new ArrayList<>();
                    OrderSystemIDArray = new ArrayList<>();


                    for (int i = 0; i < jaar.length(); i++) {
                        JSONObject jsonObject = jaar.getJSONObject(i);
                        String OrderSystemID = jsonObject.getString("_id");
                        String OrderUserEmail = jsonObject.getString("UserEmail_id");
                        OrderTotalBill = jsonObject.getString("Total_bill");
                        OrderPaymentStatus = jsonObject.getString("Payment_status");
                        String OrderOrderDate = jsonObject.getString("order_date");
                        String Order_ID = jsonObject.getString("order_Id");

                        JSONObject object = new JSONObject(jsonObject.getString("Prod_details"));
                        Iterator it = object.keys();
                        while (it.hasNext()) {
                            String f = it.next().toString();
                            JSONObject object1 = new JSONObject(object.getString(f));
                            OrderID = object1.getString("orderid");
                            String OrderQuantity = object1.getString("Qty");
                            String OrderHWFName = object1.getString("HWID");
                            String OrderProductName = object1.getString("Product_Name");
                            String OrderProductID = object1.getString("PID");
                            String OrderStatus = object1.getString("status");

                            /*if((OrderStatus.equals("delivered") || OrderStatus.equals("paid")) && (showHistoryStatus==false)) {
                                // do not include paid and delivered items in list until show History is true

                            }else{*/
                            OrderUserArray.add(OrderUserEmail);
                            OrderIDArray.add(OrderID);
                            OrderQuantityArray.add(OrderQuantity);
                            OrderHwfNameArray.add(OrderHWFName);
                            OrderProductNameArray.add(OrderProductName);
                            OrderProductIDArray.add(OrderProductID);
                            OrderStatusArray.add(OrderStatus);
                            OrderSystemIDArray.add(OrderSystemID);
                            OrderTotalBillArray.add(OrderTotalBill);
                            OrderPaymentStatusArray.add(OrderPaymentStatus);
                            OrderOrderDateArray.add(OrderOrderDate);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            pb.setVisibility(View.GONE);
            OrderStatusDetailsLayout.setVisibility(View.VISIBLE);

            try {
                payorderid.setText(OrderID);
                paystatus.setText(OrderPaymentStatus);
                paybill.setText("₹" + OrderTotalBill);

            }catch (Exception e){
                e.printStackTrace();
                paymentStatusImage.setImageResource(R.drawable.unsucces);
                thankyouTextView.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.INVISIBLE);
            }

            if (OrderPaymentStatus.equals("Success")) {
                paymentStatusImage.setImageResource(R.drawable.success);
                thankyouTextView.setVisibility(View.VISIBLE);

                //empty the cart
                Cart.cartItems.clear();

            } else {
                paymentStatusImage.setImageResource(R.drawable.unsucces);
                thankyouTextView.setVisibility(View.INVISIBLE);
                cont.setVisibility(View.INVISIBLE);
            }


            if (jsonstr == null) {
                CustomToast.Toast(getActivity(),"Something went wrong");
                return;
            }
        }
    }



}
