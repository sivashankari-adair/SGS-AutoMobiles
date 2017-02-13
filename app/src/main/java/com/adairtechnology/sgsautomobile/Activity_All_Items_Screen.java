package com.adairtechnology.sgsautomobile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.adairtechnology.sgsautomobile.Adapters.ItemCartAdapter;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.Utils.EndPoints;
import com.adairtechnology.sgsautomobile.Utils.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android-Team1 on 2/10/2017.
 */

public class Activity_All_Items_Screen  extends Fragment {
    private Context mContext;
    private CoordinatorLayout layout_main;
    private FloatingActionButton floatingActionButton;
    private ItemCartAdapter itemshopAdapter;
    public ArrayList<Item> itemNameArrayList = new ArrayList<>();
    private RecyclerView shopRcyclerView;
    private ProgressBar itemProgressBar;
    public Activity_All_Items_Screen() {

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
        //itemProgressBar = (ProgressBar) rootView.findViewById(R.id.itemProgressBar);



        new GetItemList().execute();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        shopRcyclerView.setLayoutManager(mLayoutManager);
        shopRcyclerView.setItemAnimator(new DefaultItemAnimator());
        shopRcyclerView.setAdapter(itemshopAdapter);
        shopRcyclerView.addOnItemTouchListener(new Activity_All_Items_Screen.RecyclerTouchListener(mContext, shopRcyclerView, new Activity_All_Items_Screen.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        return rootView;
    }


    // Download JSON file AsyncTask(list view items)
    private class GetItemList extends AsyncTask<Void, Void, Void> {
        List<Item> listForSearchConcepts = new ArrayList<Item>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
          //  itemProgressBar.setVisibility(View.GONE);
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
        private Activity_All_Items_Screen.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Activity_All_Items_Screen.ClickListener clickListener) {
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
