package com.adairtechnology.sgsautomobile.Fragments;

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

import com.adairtechnology.sgsautomobile.Activity_Inward_Entry_Screen;
import com.adairtechnology.sgsautomobile.Adapters.ItemCartAdapter;
import com.adairtechnology.sgsautomobile.DBController;
import com.adairtechnology.sgsautomobile.DBController1;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.PlacesList;
import com.adairtechnology.sgsautomobile.R;
import com.adairtechnology.sgsautomobile.Utils.EndPoints;
import com.adairtechnology.sgsautomobile.Utils.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    Bundle mBundle = new Bundle();
    DBController controller = new DBController(getContext());
  //  String code,name,qty,id;
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

        itemshopAdapter = new ItemCartAdapter(mContext, itemNameArrayList);
        itemProgressBar = (ProgressBar) rootView.findViewById(R.id.itemProgressBar);


        new GetItemList().execute();



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
                System.out.println("hi"+shippingorder);


             /*   Intent in  = new Intent(getActivity(), Activity_Inward_Entry_Screen.class);
                in.putExtra("Items_List",shippingorder);
                startActivity(in);
                getActivity().finish();

              */

                Intent in  = new Intent(getActivity(), PlacesList.class);
                in.putExtra("Items_List",shippingorder);
                startActivity(in);
                getActivity().finish();

            }
        });

        String text_search = FragmentHome.search_text;
        System.out.println("hi"+ text_search);
        return rootView;
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
            HttpHandler sh = new HttpHandler();

            // http://space7cloud.com/sgs_trader/sgs_datas.php?user_godown_id=g001&date=//for testing
            String url = EndPoints.allitems;

            // Making a request to url and getting response
            String jsonStrr = sh.makeServiceCall(url);
            Log.e("Test url2", url);
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
                        item.setId(c.optString("id"));
                        item.setQty(c.optString("qty"));
                        item.setName(c.optString("name"));
                        item.setItemcode(c.optString("itemcode"));
                        item.setFont(c.optString("font"));
                        listForSearchConcepts.add(item);
                        Log.i("myitemlist",listForSearchConcepts.toString());
                        System.out.println("Hi"+listForSearchConcepts.toString());


                        String code = c.optString("itemcode");
                        String name = c.optString("name");
                        String qty = c.optString("qty");
                        String id = c.optString("id");

                        /*controller = new DBController(getContext());
                        SQLiteDatabase db = controller.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("place", code);
                        cv.put("country", name);
                        db.insert("places", null, cv);

                        db.close();


                        System.out.println("Test value in data base  : " + cv);*/

                        controller = new DBController(getContext());
                        SQLiteDatabase db = controller.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put("name", name);
                        cv.put("quantity", qty);
                        cv.put("itemcode", code);
                        db.insert("items", null, cv);

                        db.close();


                        System.out.println("Test value in data base  : " + cv);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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