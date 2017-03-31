package com.adairtechnology.sgsautomobile.Fragments;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adairtechnology.sgsautomobile.ActivityClasses.Activity_Inward_Entry_Screen;
import com.adairtechnology.sgsautomobile.ActivityClasses.LoginActivity;
import com.adairtechnology.sgsautomobile.ActivityClasses.ReportActivity;
import com.adairtechnology.sgsautomobile.Adapters.ItemCartAdapter;
import com.adairtechnology.sgsautomobile.Db.DBController;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.Models.ListItem;
import com.adairtechnology.sgsautomobile.R;
import com.adairtechnology.sgsautomobile.Utils.EndPoints;
import com.adairtechnology.sgsautomobile.Utils.HttpHandler;
import com.adairtechnology.sgsautomobile.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Android-Team1 on 2/14/2017.
 */

public class Fragment_Items_One  extends Fragment implements Serializable {
    private Context mContext;
    private CoordinatorLayout layout_main;
    private FloatingActionButton floatingActionButton;
    private ItemCartAdapter itemshopAdapter;
    public ArrayList<Item> itemNameArrayList = new ArrayList<>();
    private RecyclerView shopRcyclerView;
    private ProgressBar itemProgressBar;
    Activity_Inward_Entry_Screen activie;
    Bundle mBundle = new Bundle();
    DBController controller = new DBController(getContext());
  //  String code,name,qty,id;
    ArrayList<Item> employeeList;
    private static Dialog dialogs;
    String updateinfo,restoredIp,logininfo,gcodee;

    public Fragment_Items_One() {

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

        SharedPreferences prefss = getContext().getSharedPreferences("MYPREFF", MODE_PRIVATE);
        logininfo = prefss.getString("loginInfo", null);
        System.out.println("The login inform fragmen : " +logininfo);
        try {
            JSONObject jsonObj = new JSONObject(logininfo);
            JSONArray godown_name = jsonObj.getJSONArray("Gcode");
            System.out.println("godown_name : " +godown_name);

            // looping through All Contacts
            for (int i = 0; i < godown_name.length(); i++) {
                JSONObject cc = godown_name.getJSONObject(i);
                gcodee = cc.optString("Code");

            }
            System.out.println("godown name fragment :" +gcodee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        itemshopAdapter = new ItemCartAdapter(mContext, itemNameArrayList);
        itemProgressBar = (ProgressBar) rootView.findViewById(R.id.itemProgressBar);

        ItemListFragm();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        shopRcyclerView.setLayoutManager(mLayoutManager);
        shopRcyclerView.setItemAnimator(new DefaultItemAnimator());
        shopRcyclerView.setAdapter(itemshopAdapter);
        shopRcyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, shopRcyclerView, new Fragment_Items_One.ClickListener() {
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
                System.out.println("hi Fragment One"+shippingorder);

                Toast.makeText(mContext, "Fragment fab is clicked", Toast.LENGTH_SHORT).show();
                Intent in  = new Intent(getActivity(), Activity_Inward_Entry_Screen.class);
                in.putExtra("Items_List",shippingorder);
                startActivity(in);
                getActivity().finish();


            }
        });

        String text_search = FragmentHome.search_text;
        System.out.println("hi Search"+ text_search);
        return rootView;
    }

    private void ItemListFragm() {
        if(!Utils.isNetworkAvailable(mContext)){
            Toast.makeText(mContext, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
        new GetItemList().execute();

    }


    // Download JSON file AsyncTask(list view items)
    private class GetItemList extends AsyncTask<Void, Void, Void> {
        List<Item> listForSearchConcepts = new ArrayList<Item>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            itemProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            // Making a request to url and getting response
            //http://localhost/android/automobiles/get_data.php?page=item_list&Gcode=0M50PB1QA
            HttpHandler httpHandler = new HttpHandler();

            SharedPreferences prefs = getContext().getSharedPreferences("MYPREFERNCE",Context.MODE_PRIVATE);;
            restoredIp = prefs.getString("ip", null);
            System.out.println("test update info ipAddress "+restoredIp);

            String Url = "http://"+ restoredIp +"/android/automobiles/get_data.php?page=item_list&Gcode=0M50PB1QA";
            System.out.println("The fragement url :" +Url);
            String jsonStrr = httpHandler.makeServiceCall(Url);

            Log.e("Test url2", jsonStrr);
            //Log.e("Response from url:", jsonStrr);

            if (jsonStrr != null) {
                try {
                    JSONObject jsonObjj = new JSONObject(jsonStrr);

                    // Getting JSON Array node
                    JSONArray itemss = jsonObjj.getJSONArray("items");

                    // looping through All Contacts
                    for (int i = 0; i < itemss.length(); i++) {

                        JSONObject c = itemss.getJSONObject(i);

                        Item item = new Item();
                        item.setQty(c.optString("qty"));
                        item.setName(c.optString("name"));
                        item.setItemcode(c.optString("itemcode"));

                        listForSearchConcepts.add(item);
                        Log.i("myitemlist",listForSearchConcepts.toString());
                        System.out.println("Hi"+listForSearchConcepts.toString());



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(mContext, "Network Is Not Avialable", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            itemProgressBar.setVisibility(View.GONE);
            itemshopAdapter = new ItemCartAdapter(mContext, (ArrayList<Item>)listForSearchConcepts);
            shopRcyclerView.setAdapter(itemshopAdapter);


        }


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