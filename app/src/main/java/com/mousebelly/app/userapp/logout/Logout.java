package com.mousebelly.app.userapp.logout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.mousebelly.app.userapp.CustomProgressDialog;
import com.mousebelly.app.userapp.LocalSaveModule;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.WalletHandler;
import com.mousebelly.app.userapp.favourite.Fav;
import com.mousebelly.app.userapp.favourite.GetFavItems;
import com.mousebelly.app.userapp.orderFood.Cart;
import com.mousebelly.app.userapp.wallet.Wallet;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;


/**
 * Created by Kamal Kant on 21-05-2017.
 */

public class Logout extends AsyncTask<Void, Void, Void> {

    ProgressDialog pg = CustomProgressDialog.getDialog(MainActivity.context,"Logging Out");


        // ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pg.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Server.s.put("http://mousebelly.herokuapp.com/sign/LoggedinCheck/" + USERID + "/false");
            Server.s.put("http://mousebelly.herokuapp.com/sign/isConnectedtrue/" + USERID + "/false");
            LocalSaveModule.clearPreferences(MainActivity.context);

            Cart.cartItems.clear();
            GetFavItems.favouriteItems.clear();
            WalletHandler.WalletAmount  = Integer.MIN_VALUE;

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pg.dismiss();

            Intent i = new Intent(MainActivity.context, LoginActivity.class);

            MainActivity.context.startActivity(i);


        }

}
