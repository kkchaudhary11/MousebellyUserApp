package com.mousebelly.app.userapp.userprofile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by adity on 10-Apr-17.
 */

public class GetUserProfileData {
    static UserprofileUserBean userprofileUserBean = new UserprofileUserBean();
    static ArrayList<UserprofileUserBean> userprofileUserBeanArray = new ArrayList<>();
    public Boolean status = false;
    Context mcontext;
    String Email = LoginActivity.USERID;
    String jsonstr;
    String uname;

    GetUserProfileData(Context context) {
        mcontext = context;
    }

    protected void loadData() {


        jsonstr = Server.s.get(APIs.sign_sign + Email);
        System.out.println("This is Json str::::::::::::" + jsonstr);

        if (jsonstr != null) {

            try {
                status = false;
                JSONArray jaar = new JSONArray(jsonstr);
                for (int i = 0; i < jaar.length(); i++) {

                    JSONObject jsonObject = jaar.getJSONObject(i);
                    userprofileUserBean.validateUsername(jsonObject.getString("U_name"));
                    userprofileUserBean.validateEmail(jsonObject.getString("UserEmail_id"));
                    userprofileUserBean.validatePassword(jsonObject.getString("Pwd"));
                    userprofileUserBean.validatePhone(jsonObject.getString("Phone_No"));

                    JSONArray jsonArray = jsonObject.getJSONArray("Address");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    userprofileUserBean.setAddressStreet(jsonObject1.getString("street_name"));
                    userprofileUserBean.setAddressCity(jsonObject1.getString("city_name"));
                    userprofileUserBean.setAddressState(jsonObject1.getString("state_name"));
                    userprofileUserBean.setAddressZipcode(jsonObject1.getString("zip_code"));
                    userprofileUserBean.setAddressCountry(jsonObject1.getString("country"));

                    JSONArray jsonArray2 = jsonObject.getJSONArray("Cordinates");
                    JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                    userprofileUserBean.setLatitude(jsonObject2.getString("lat"));
                    userprofileUserBean.setLongitude(jsonObject2.getString("long"));

                    userprofileUserBeanArray.add(userprofileUserBean);

                    System.out.println("USer detials" + userprofileUserBean);
                }

                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
