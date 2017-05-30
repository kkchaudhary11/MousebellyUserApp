package com.mousebelly.app.userapp.wallet;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mousebelly.app.userapp.CustomToast;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.payment.PaymentMainActivity;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;



public class Wallet extends Fragment {

    Dialog walletDialog;

    public Wallet() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_wallet, container, false);

        final TextView walletAmount = (TextView) v.findViewById(R.id.wallet_amount);

        Button addToWallet = (Button) v.findViewById(R.id.add_to_wallet);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance("INR"));

        // String result = format.format(1234567.89);
        walletAmount.setText(format.format(WalletHandler.WalletAmount));
        walletAmount.setTextColor(ContextCompat.getColor(MainActivity.context, R.color.Amulet));
        walletAmount.setTypeface(null, Typeface.BOLD);


        addToWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                walletDialog = new Dialog(MainActivity.context);
                walletDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                walletDialog.setContentView(R.layout.add_wallet_amount_dialog_box);

                final EditText amountToAdd = (EditText) walletDialog.findViewById(R.id.amount_to_add);

                Button dialogButtonOK = (Button) walletDialog.findViewById(R.id.dialogButtonOK);

                dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //checks
                        if (amountToAdd.getText().toString().matches("")) {
                            CustomToast.Toast(MainActivity.context, "Enter valid Amount");
                            return;
                        } else if (Integer.parseInt(amountToAdd.getText().toString()) < 10) {
                            CustomToast.Toast(MainActivity.context, "Min amount : 10");
                            return;
                        } else if (Integer.parseInt(amountToAdd.getText().toString()) > 1000) {
                            CustomToast.Toast(MainActivity.context, "Max amount : 10,000");
                            return;
                        }

                        Intent i = new Intent(MainActivity.context, PaymentMainActivity.class);
                        i.putExtra("Activity","AddToWalletPayment");
                        i.putExtra("AmountToAdd", amountToAdd.getText().toString());
                        startActivity(i);

                    }
                });

                walletDialog.show();

            }
        });




        // Inflate the layout for this fragment
        return v;
    }

}
