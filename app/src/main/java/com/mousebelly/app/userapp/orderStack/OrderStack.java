package com.mousebelly.app.userapp.orderStack;


import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mousebelly.app.userapp.APIs;
import com.mousebelly.app.userapp.EmptyMessage;
import com.mousebelly.app.userapp.Login.LoginActivity;
import com.mousebelly.app.userapp.MainActivity;
import com.mousebelly.app.userapp.R;
import com.mousebelly.app.userapp.Server;
import com.mousebelly.app.userapp.SocketAccess;
import com.mousebelly.app.userapp.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;


public class OrderStack extends Fragment {


    public static ViewPager orderStackViewPager;

    public static List<Fragment> fList = new ArrayList<>();

    MyPageAdapter myPageAdapter;
    View v;

    ProgressBar OrderStackProgressBar;


    public OrderStack() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_order_stack, container, false);


        OrderStackProgressBar = (ProgressBar) v.findViewById(R.id.Order_Stack_Progress_Bar);


        LoadOrders loadOrders = new LoadOrders();
        loadOrders.execute();

        // Inflate the layout for this fragment
        return v;
    }


    public class LoadOrders extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            OrderStackProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... voids) {


            String respOrderStack = Server.s.get(APIs.order_order2 + LoginActivity.USERID);
            System.out.println("Order Stack : ");
            System.out.println(respOrderStack);

            GetOrderStack orderStack = new GetOrderStack();

            orderStack.loadData(respOrderStack);

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

            OrderStackProgressBar.setVisibility(View.GONE);

            System.out.println("FLIST:");
            System.out.println(OrderStack.fList);

            if (GetOrderStack.orderStack.isEmpty()) {
                RelativeLayout rl = EmptyMessage.show(MainActivity.context, "No Orders");
                OrderStack.fList.add(MyFragment.newInstance(rl));
            }

            myPageAdapter = new MyPageAdapter(getActivity().getSupportFragmentManager(), OrderStack.fList);

            orderStackViewPager = (ViewPager) v.findViewById(R.id.OrderStackViewPager);
            orderStackViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            orderStackViewPager.setAdapter(myPageAdapter);

        }
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

    private int getItem(int i) {
        return orderStackViewPager.getCurrentItem() + i;
    }

}
