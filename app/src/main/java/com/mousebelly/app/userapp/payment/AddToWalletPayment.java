package com.mousebelly.app.userapp.payment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.GenerateOrderId;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.wallet.Wallet;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddToWalletPayment extends Fragment {

    public static String orderID;
    TextView TotalBill, CustomerId, OrderId;
    EditText PromoCode;
    Button useWallet, Pay;
    WebView webView;
    LinearLayout walletAddPaymentLayout;
    ProgressBar WebProgresesBar;
    JSONObject jobj;
    String AmountToAdd;

    public AddToWalletPayment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        View v = inflater.inflate(R.layout.fragment_order_payment, container, false);

        OrderId = (TextView) v.findViewById(R.id.order_id);
        CustomerId = (TextView) v.findViewById(R.id.customer_id);
        TotalBill = (TextView) v.findViewById(R.id.total_price);
        PromoCode = (EditText) v.findViewById(R.id.promo_code);
        useWallet = (Button) v.findViewById(R.id.usewallet);
        useWallet.setVisibility(View.INVISIBLE);
        Pay = (Button) v.findViewById(R.id.pay);
        walletAddPaymentLayout = (LinearLayout) v.findViewById(R.id.payment_details_layout);
        WebProgresesBar = (ProgressBar) v.findViewById(R.id.web_progress);

        webView = (WebView) v.findViewById(R.id.WebView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(100);
        webView.getSettings().setDomStorageEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webSettings.setAllowContentAccess(true);
        webView.getProgress();

        Intent intent = getActivity().getIntent();

        AmountToAdd = intent.getStringExtra("AmountToAdd");

        String oid = GenerateOrderId.getOrderId().replaceAll("[^0-9]", "");
        orderID = "wp" + oid;
        OrderId.setText(orderID);

        TotalBill.setText(AmountToAdd);
        CustomerId.setText(LoginActivity.USERID);
        // Order JSON

        jobj = new JSONObject();


        System.out.println("ORDER ID ::::: " + orderID);
        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addTowalletManager();

            }
        });


        return v;
    }



    private void addTowalletManager(){

        walletAddPaymentLayout.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);

        String dataSet[] =
                {
                        "merchant_id", "124827"
                        ,
                        "order_id", orderID
                        ,
                        "currency", "INR"
                        ,
                        "amount", AmountToAdd
                        ,
                        "redirect_url", APIs.DOMAIN
                        ,
                        "cancel_url", APIs.DOMAIN
                        ,
                        "language", "EN"
                        ,
                        "billing_name", LoginActivity.user.getU_name()
                        ,
                        "billing_address", LoginActivity.user.getStreet_name()
                        ,
                        "billing_city", LoginActivity.user.getCity_name()
                        ,
                        "billing_state", LoginActivity.user.getState_name()
                        ,
                        "billing_zip", LoginActivity.user.getZip_code()
                        ,
                        "billing_country", "India"
                        ,
                        "billing_tel", LoginActivity.user.getPhone_No()
                        ,
                        "billing_email", LoginActivity.user.getUserEmail_id()
                        ,
                        "promo_code", ""
                        ,
                        "customer_identifier", LoginActivity.user.getUserEmail_id()
                };

        String data = "<form method='post' action='"+APIs.ccavRequestHandler+"'>";

        for (int i = 0; i < dataSet.length; i += 2) {
            data += "<input type='hidden' name='" + dataSet[i] + "' value='" + dataSet[i + 1] + "' />";
        }

        data += "<center><input type='submit' style=' background: #5cb85c;\n" +
                "margin-top:100px;\n" +

                "  -webkit-border-radius: 60;\n" +
                " \n" +
                "  border-radius: 10px;\n" +
                "  font-family: Arial;\n" +
                "  color: #ffffff;\n" +
                "  font-size: 60px;\n" +
                "  padding: 10px 20px 10px 20px;\n" +
                "  text-decoration: none;' value='CHECK OUT'></center>";
        // data += "<input type='submit' value='CheckOut'>";

        data += "</form>";

        //int status = Server.s.post("http://mousebelly.herokuapp.com/order/order", jobj);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadData(finalData, "text/html; charset=utf-8", "UTF-8");

                System.out.println("URL: " + url);
                if (url.equals(APIs.DOMAIN + "/")) {
                    // WalletHandler.addToWallet(Integer.parseInt(AmountToAdd));
                    CustomToast.Toast(getActivity(), "Amount Added to Wallet");
                    WalletHandler.setWalletTotalAmount();
                    System.out.println("₹"+ WalletHandler.WalletAmount);
                    MainActivity.navigationView.getMenu().getItem(4).setTitle("₹"+ WalletHandler.WalletAmount);

                    Intent webviewIntent = new Intent(getActivity(), MainActivity.class);
                    startActivity(webviewIntent);
                    getActivity().finish();

                } else {

                }

                return false;
            }
        });

        webView.loadData(data, "text/html; charset=utf-8", "UTF-8");

    }

}
