package com.mousebelly.app.userapp.home;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kamal Kant on 11-06-2017.
 */

public class GetHouseWife {

    String resp;
    ArrayList<String> HwfId = new ArrayList<>();

    ArrayList<String> loadData(){
        resp = Server.s.get(APIs.housewifesign_housewifesign );


        try {
            JSONArray jsonArray = new JSONArray(resp);

            for(int i = 0 ; i<jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String hwfname = jsonObject.getString("HWFEmail_id");

                HwfId.add(hwfname);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return HwfId;


    }
}
