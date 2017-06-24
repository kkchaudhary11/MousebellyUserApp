package com.mousebelly.app.userapp.home;



import android.widget.RelativeLayout;

import com.mousebelly.app.userapp.EmptyMessage;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class GetFoodData {

    public static HashMap<String, FoodUnit> foodItems = new HashMap<>();


    public void loadData(String url){

        String resp = Server.s.get(url);

        try {
            JSONArray jsonArray = new JSONArray(resp);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject foodJSON = jsonArray.getJSONObject(i);
                FoodUnit foodUnit = new FoodUnit();
                foodUnit.set_id(foodJSON.getString("_id"));
                foodUnit.setProd_name(foodJSON.getString("Prod_name"));
                foodUnit.setDescription(foodJSON.getString("Description"));
                foodUnit.setProd_category(foodJSON.getString("Prod_category"));
                foodUnit.setPrice(foodJSON.getString("Price"));
                foodUnit.setProd_id(foodJSON.getString("Prod_id"));
                foodUnit.setImage(foodJSON.getString("Image"));
                foodUnit.setStarsize(foodJSON.getString("starsize"));
                try {
                    foodUnit.setSalePrice(foodJSON.getString("salePrice"));
                    foodUnit.setType(foodJSON.getString("Type"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject HWFDetails = foodJSON.getJSONObject("HWFEmail_id");
                foodUnit.setHwfName(HWFDetails.getString("HWF_Name"));

                foodItems.put(foodUnit.getProd_id(),foodUnit);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void showData() {
        if (foodItems.isEmpty()) {
            RelativeLayout rl = EmptyMessage.show(Home.homeContext, "Sorry! \n No Meal Available for Today");
            Home.FoodLayout.addView(rl);
            return;
        }

        for (FoodUnit foodUnit : foodItems.values()) {
            System.out.println(foodUnit);
            FoodUnit p = new FoodUnit();
            Home.FoodLayout.addView(p.draw(foodUnit));
        }
    }

}
