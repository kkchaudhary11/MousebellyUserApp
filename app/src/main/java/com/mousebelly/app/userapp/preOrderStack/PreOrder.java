package com.mousebelly.app.userapp.preOrderStack;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.EmptyMessage;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.SocketAccess;
import com.mousebelly.app.userapp.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import static com.mousebelly.app.userapp.Login.LoginActivity.USERID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreOrder extends Fragment {

    View v;
    public static ViewPager orderStackViewPager;

    public static List<Fragment> fList = new ArrayList<>();
    MyPageAdapter myPageAdapter;
    ProgressBar PreOrderProgressBar;




    public PreOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_order_stack, container, false);


        PreOrderProgressBar = (ProgressBar) v.findViewById(R.id.Order_Stack_Progress_Bar);

        LoadPreOrders loadPreOrders = new LoadPreOrders();
        loadPreOrders.execute();

        // Inflate the layout for this fragment
        return v;
    }

    public class LoadPreOrders extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PreOrderProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String respOrderStack = Server.s.get(APIs.preorder_preorderbymail + USERID);
            System.out.println("Order Stack : ");
            System.out.println(respOrderStack);

            GetPreOrderStack preOrderStack = new GetPreOrderStack();

            preOrderStack.loadData(respOrderStack);

            while (SocketAccess.connected == false) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            System.out.println("FLIST:");
            System.out.println(PreOrder.fList);

            if (GetPreOrderStack.preorderStack.isEmpty()) {
                System.out.println("NO DATA");
                RelativeLayout rl2 = EmptyMessage.show(MainActivity.context, "No Pre-Order");
                PreOrder.fList.add(MyFragment.newInstance(rl2));

            }

            myPageAdapter = new MyPageAdapter(getActivity().getSupportFragmentManager(), PreOrder.fList);

            orderStackViewPager = (ViewPager) v.findViewById(R.id.OrderStackViewPager);
            orderStackViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            orderStackViewPager.setAdapter(myPageAdapter);

            PreOrderProgressBar.setVisibility(View.GONE);


        }

    }

    private int getItem(int i) {
        return orderStackViewPager.getCurrentItem() + i;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

}
