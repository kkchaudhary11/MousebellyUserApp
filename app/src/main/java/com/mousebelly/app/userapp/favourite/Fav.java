package com.mousebelly.app.userapp.favourite;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.mousebelly.app.userapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fav extends Fragment {

    public static LinearLayout favItem;
    ProgressBar favProgressBar;


    public Fav() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_fav, container, false);

        favItem = (LinearLayout) v.findViewById(R.id.favItem);

        favProgressBar = (ProgressBar) v.findViewById(R.id.fav_progress_bar);

        if(GetFavItems.favouriteItems.isEmpty()) {
            new favUnit().execute();
        }else {
            GetFavItems getFavItems = new GetFavItems();
            getFavItems.showData();
        }
        return v;
    }

    class favUnit extends AsyncTask<Void, Void, Void> {

        GetFavItems getFavItems;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            favProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {

            getFavItems = new GetFavItems();
            getFavItems.getData();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            favProgressBar.setVisibility(View.INVISIBLE);

            getFavItems.showData();
        }
    }

}
