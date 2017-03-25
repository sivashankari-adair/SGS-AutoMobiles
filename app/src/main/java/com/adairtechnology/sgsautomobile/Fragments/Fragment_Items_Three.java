package com.adairtechnology.sgsautomobile.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.adairtechnology.sgsautomobile.ActivityClasses.Activity_Inward_Entry_Screen;
import com.adairtechnology.sgsautomobile.Adapters.ItemCartAdapter;
import com.adairtechnology.sgsautomobile.Db.DBController;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.R;

import java.util.ArrayList;


/**
 * Created by Android-Team1 on 2/17/2017.
 */

public class Fragment_Items_Three  extends Fragment {
    private Context mContext;
    private CoordinatorLayout layout_main;
    private FloatingActionButton floatingActionButton;
    private ItemCartAdapter itemshopAdapter;
    public ArrayList<Item> itemNameArrayList = new ArrayList<>();
    private RecyclerView shopRcyclerView;
    private ProgressBar itemProgressBar;
    Bundle mBundle = new Bundle();
    ArrayList<Item> employeeList;
    public Fragment_Items_Three() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getContext();

        View rootView = inflater.inflate(R.layout.layout_all_items_recycler, container, false);
        layout_main = (CoordinatorLayout) rootView.findViewById(R.id.layout_main);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        shopRcyclerView = (RecyclerView) rootView.findViewById(R.id.itemRecyclerView);
        shopRcyclerView.setHasFixedSize(true);

        itemshopAdapter = new ItemCartAdapter(mContext, itemNameArrayList);
        itemProgressBar = (ProgressBar) rootView.findViewById(R.id.itemProgressBar);

        DBController databaseHelper = new DBController(getContext());
        employeeList = new ArrayList<Item>();

        employeeList = databaseHelper.getAllEmployee();
        itemshopAdapter = new ItemCartAdapter(mContext, employeeList);
        shopRcyclerView.setAdapter(itemshopAdapter);

        databaseHelper = new DBController(getContext());
        employeeList = new ArrayList<Item>();

        employeeList = databaseHelper.getAllEmployee();
        itemshopAdapter = new ItemCartAdapter(mContext, employeeList);
        shopRcyclerView.setAdapter(itemshopAdapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        shopRcyclerView.setLayoutManager(mLayoutManager);
        shopRcyclerView.setItemAnimator(new DefaultItemAnimator());
        shopRcyclerView.setAdapter(itemshopAdapter);
        shopRcyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, shopRcyclerView, new Fragment_Items_Three.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String shippingorder = prefs.getString("Orderinfo", "");
                System.out.println("hi"+shippingorder);


                Intent in  = new Intent(getActivity(), Activity_Inward_Entry_Screen.class);
                in.putExtra("Items_List",shippingorder);
                startActivity(in);
                getActivity().finish();


              /*  Intent in  = new Intent(getActivity(), PlacesList.class);
                in.putExtra("Items_List",shippingorder);
                startActivity(in);
                getActivity().finish();*/

            }
        });

        String text_search = FragmentHome.search_text;
        System.out.println("hi"+ text_search);
        return rootView;
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}