package com.mousebelly.app.userapp.favourite;

import android.widget.RelativeLayout;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.EmptyMessage;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;




/**
 * Created by Kamal Kant on 12-05-2017.
 */

public class GetFavItems {

    public static HashMap<String, favItem> favouriteItems = new HashMap<>();

    public void getData() {

        ArrayList<String> favItems = LoginActivity.user.getFav();
 /*       favItems.add("product66179");
        favItems.add("product41308");
        favItems.add("product51854");
        favItems.add("product84740");
*/
        for (String favItem : favItems) {

            String resp = Server.s.get(APIs.prod_approval_prod_approval11 + favItem);

            try {
                JSONArray jsonArray = new JSONArray(resp);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                com.mousebelly.app.userapp.favourite.favItem favItemobj = new favItem();
                favItemobj.setProd_name(jsonObject.getString("Prod_name"));
                favItemobj.setProd_category(jsonObject.getString("Prod_category"));
                favItemobj.setDescription(jsonObject.getString("Description"));
                favItemobj.setPrice(jsonObject.getString("Price"));
                favItemobj.setProd_id(jsonObject.getString("Prod_id"));
                favItemobj.setImage(jsonObject.getString("Image"));
                favItemobj.setStarsize(jsonObject.getString("starsize"));
                JSONObject hwfname = jsonObject.getJSONObject("HWFEmail_id");
                favItemobj.setHWF_Name(hwfname.getString("HWF_Name"));

                favouriteItems.put(favItemobj.getProd_id(), favItemobj);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public void showData() {

        if (favouriteItems.isEmpty()) {
            RelativeLayout rl = EmptyMessage.show(MainActivity.context, "No Favourite Item");
            Fav.favItem.addView(rl);
            return;
        }

        Iterator it = favouriteItems.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next().toString();
            favItem favItem = new favItem();
            com.mousebelly.app.userapp.favourite.favItem f = favouriteItems.get(key);

            //RelativeLayout rl = favItem.draw(f);

            Fav.favItem.addView(favItem.draw(f));
        }


    }


}
