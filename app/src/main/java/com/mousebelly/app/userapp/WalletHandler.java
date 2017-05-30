package com.mousebelly.app.userapp;


import org.json.JSONArray;
import org.json.JSONObject;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;


/**
 * Created by Kamal Kant on 22-04-2017.
 */

public class WalletHandler {


    public static int WalletAmount;

    public static void  setWalletTotalAmount() {

        String resp = Server.s.get(APIs.sign_sign + USERID);

        try {

            JSONArray jsonArray = new JSONArray(resp);

            JSONObject jsonObject = jsonArray.getJSONObject(0);

            WalletAmount = jsonObject.getInt("wallet");

            System.out.println("Total Wallet1 Amount: " + WalletAmount);

        } catch (Exception e) {
            System.out.println("Exception in Loading Total Wallet1 Amount");
        }
    }

    public static void cutFromWallet(int amount) {

        Server.s.put(APIs.sign_walletRemainbal + USERID + "/" + amount);

        setWalletTotalAmount();
    }

    public static void addToWallet(int amount) {

        Server.s.put(APIs.walletmoneyupdate + USERID + "/" + amount);

        setWalletTotalAmount();

    }


}