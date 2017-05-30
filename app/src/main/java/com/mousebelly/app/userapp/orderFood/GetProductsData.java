package com.mousebelly.app.userapp.orderFood;


import android.widget.RelativeLayout;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.EmptyMessage;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


public class GetProductsData {

    public static HashMap<String, ProductUnit> productUnits = new HashMap<>();

    public  void convertDataToArrayList(String data) {

        //clear hashmap before reload
        productUnits.clear();

        if (data != null) {


            try {
                DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy:HHmm");
                Date getCurrentDate = new Date();
                String currentDate = dateFormat.format(getCurrentDate);

                String[] dateAndTime = currentDate.split(":");

                JSONArray hwfArray = new JSONArray(data);
                for (int i = 0; i < hwfArray.length(); i++) {
                    String hwf = hwfArray.getString(i);
                    System.out.println(hwf);

                    String mealplan = Server.s.get(APIs.mealplan_mealplan + hwf);
                    JSONArray mealPlanArray = new JSONArray(mealplan);
                    for (int j = 0; j < mealPlanArray.length(); j++) {
                        JSONObject jsonObject = mealPlanArray.getJSONObject(j);
                        System.out.println("Result is " + jsonObject);
                        String hwid = jsonObject.getString("housewife_id");
                        JSONObject product_plan = jsonObject.getJSONObject("product_plan").getJSONObject("f");
                        Iterator it = product_plan.keys();

                        while (it.hasNext()) {
                            String pro_id = (String) it.next();

                            JSONArray products = product_plan.getJSONArray(pro_id);
                            for (int k = 0; k < products.length(); k++) {
                                JSONObject item = products.getJSONObject(k);
                                String stime = item.getString("Start_Time");
                                String etime = item.getString("End_Time");
                                String date = item.getString("Date");




                                //check current products


                                if (dateAndTime[0].equals(date)) {
                                    if (Integer.parseInt(dateAndTime[1]) <= Integer.parseInt(etime)) {

                                        JSONObject product = item.getJSONObject("Food_Object");
                                        ProductUnit productUnit = new ProductUnit();

                                        JSONObject jsonObject1 = product.getJSONObject("HWFEmail_id");
                                        productUnit.setHWF_Name(jsonObject1.getString("HWF_Name"));
                                        productUnit.setHWFEmail_id(jsonObject1.getString("_id"));
                                        productUnit.setPhone_no(jsonObject1.getString("Phone_no"));

                                        productUnit.setImage(product.getString("Image"));
                                        productUnit.setPrice(product.getString("Price"));
                                        productUnit.set__v(product.getString("__v"));
                                        productUnit.set_id(product.getString("_id"));
                                        productUnit.setCountrate(product.getString("countrate"));
                                        productUnit.setDescription(product.getString("Description"));
                                        productUnit.setFeedback(product.getString("feedback"));
                                        productUnit.setIsApproved(product.getString("isApproved"));
                                        productUnit.setIsRejected(product.getString("isRejected"));
                                        productUnit.setProd_id(product.getString("Prod_id"));
                                        productUnit.setProd_category(product.getString("Prod_category"));
                                        productUnit.setProd_name(product.getString("Prod_name"));
                                        productUnit.setStarsize(product.getString("starsize"));

                                        try {

                                        productUnit.setStock_Value(item.getString("Stock_Value"));
                                        productUnit.setStock_Value_User(item.getString("Stock_Value_User"));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        GetProductsData.productUnits.put(productUnit.getProd_id(), productUnit);

                                    }
                                }
                            }
                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }



        } else {
            System.out.println("Internet Connection : [LOST]");
        }
    }

    public void loadData() {

        //remove views before reloading
        Products.productsLayout.removeAllViews();


   /*     Iterator it = productUnits.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next().toString();
             ProductUnit productUnit= new ProductUnit();
            ProductUnit p = productUnits.get(key);


            Products.productsLayout.addView(productUnit.draw(p));
        }*/


        if (productUnits.isEmpty()) {
            RelativeLayout rl = EmptyMessage.show(MainActivity.context, "Sorry! \n No Meal Available for Today");
            Products.productsLayout.addView(rl);
            return;
        }


        for (ProductUnit productUnit : GetProductsData.productUnits.values()) {
            System.out.println(productUnit);
            Products.productsLayout.addView(productUnit.draw());
        }


    }

}
