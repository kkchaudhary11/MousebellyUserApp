package com.mousebelly.app.userapp.payment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mousebelly.app.userapp.GenerateOrderId;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.orderFood.Cart;
import com.mousebelly.app.userapp.orderFood.ProductUnit;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class PaymentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_payment);

        Intent intent = getIntent();

        final String ActivityName = intent.getStringExtra("Activity");

        System.out.println("ActivityName  : " + ActivityName);

        if(ActivityName.equals("OrderPayment")) {
            OrderPayment orderPayment = new OrderPayment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.payment_layout, orderPayment, orderPayment.getTag()).commit();
        }
        if(ActivityName.equals("AddToWalletPayment")) {
            AddToWalletPayment addToWalletPayment = new AddToWalletPayment();
            FragmentManager manager2 = getSupportFragmentManager();
            manager2.beginTransaction().replace(R.id.payment_layout, addToWalletPayment, addToWalletPayment.getTag()).commit();
        }

    }
}