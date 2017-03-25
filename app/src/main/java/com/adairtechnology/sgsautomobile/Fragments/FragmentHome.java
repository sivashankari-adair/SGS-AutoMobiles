package com.adairtechnology.sgsautomobile.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.adairtechnology.sgsautomobile.R;


/**
 * Created by Android-Team1 on 2/14/2017.
 */

public class FragmentHome extends Fragment {

    private FragmentTabHost mTabHost;
    public static TextView tvTitle;
    ImageView image_back;
    SearchView editsearch;
    public static String search_text;

    public FragmentHome() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_home, container, false);

        tvTitle = (TextView) rootView.findViewById(R.id.tv_title_FRAGMENT);
        tvTitle.setText(" Items");

        image_back = (ImageView) rootView.findViewById(R.id.img_BACK);
        image_back.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) rootView.findViewById(R.id.img_SEARCH);
        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search_text = newText;
                Toast.makeText(getContext(),search_text,Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("fragmentAll").setIndicator("ALL"),
                Fragment_Items_One.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentFavorite").setIndicator("LIMIT1"),
                Fragment_Items_Two.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentOffers").setIndicator("LIMIT2"),
                Fragment_Items_Three.class, null);

        mTabHost.getTabWidget().setDividerDrawable(null); // android:showDividers="none" - in xml

        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
        {
            if(i==0){
                TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#ffffff"));
            }else{
                TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#7dffffff"));
            }

        }

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#7dffffff"));
                }

                TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#ffffff"));

            }
        });

        return rootView;
    }

    public void onBackPressed()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }

}