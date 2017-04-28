package com.adairtechnology.sgsautomobile.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.adairtechnology.sgsautomobile.Db.DBController;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Android-Team1 on 2/10/2017.
 */

public class ItemCartAdapter extends RecyclerView
        .Adapter<ItemCartAdapter
        .DataObjectHolder> implements ListAdapter {
    private Context mContext;
    private static String LOG_TAG = "ItemCartAdapter";
    ArrayList<Item> mDataset;
    List<Item> mDatasets;
    private ArrayList<Item> itemNameArrayList = new ArrayList<>();
    LayoutInflater inflater;
    private static int added_value;
    DBController controller = new DBController(mContext);
 //   public static String all_details;
    public String pro_name,pro_code,pro_qty;
    private List<Item> filteredData;
    List<Item> mOriginalData;

    private List<Item> mValues;
    List<Item> itemlistttt;
    private ArrayList<String> cartItem = new ArrayList<String>();
    String[] Current;
    StringBuilder sb;

    public ItemCartAdapter(Context mContext, List<Item> itemNameArrayList) {

        this.mContext = mContext;
        this.mOriginalData = itemNameArrayList;
        this.mOriginalData = (ArrayList<Item>) itemNameArrayList;
        inflater = LayoutInflater.from(mContext);
        this.mDataset = new ArrayList<Item>();
        this.mDataset.addAll(itemNameArrayList);


    }

    public ItemCartAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mOriginalData.size();
    }

    @Override
    public Object getItem(int position) {

        return mOriginalData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemCount() {
        return mOriginalData.size();
    }



    @Override
    public boolean isEmpty() {
        return false;
    }



    public class DataObjectHolder extends RecyclerView.ViewHolder
            {
        TextView productname,productcode,productMRB;
        ImageView product_detail,increment_value,decrement_value;
                CheckBox chkIos;
                EditText productdiscount;
                String[] Current;
                EditText quantity;



        public DataObjectHolder(View itemView) {
            super(itemView);
            quantity = (EditText) itemView.findViewById(R.id.quantityTV);
            productname = (TextView) itemView.findViewById(R.id.itemNameTV);
            productcode = (TextView) itemView.findViewById(R.id.itemCodeTV);
            productMRB = (TextView) itemView.findViewById(R.id.itemMRBTV);
            chkIos = (CheckBox)itemView.findViewById(R.id.chkIos);
         }
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_all_items_card, parent, false);

        return new DataObjectHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        final DataObjectHolder holderTemp = holder;

        holder.quantity.setText(mOriginalData.get(position).getQty());
        holder.productcode.setText(mOriginalData.get(position).getItemcode());
        holder.productname.setText(mOriginalData.get(position).getName());
        holder.productMRB.setText(mOriginalData.get(position).getMrb());
        holder.quantity.setTag(position);

        holder.quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                        public void onFocusChange(View v, boolean hasFocus) {
                                                            if (!hasFocus) {
                                                                final int position = (Integer) v.getTag();
                                                                final EditText Caption = (EditText) v;
                                                                mOriginalData.get(position).qty = Caption.getText().toString();
                                                            }
                                                        }
                                                    });


        holder.chkIos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {

                    String pro_code = holder.productcode.getText().toString();
                    String pro_name = holder.productname.getText().toString();
                    String pro_rate = holder.productMRB.getText().toString();
                    String pro_qty  = holder.quantity.getText().toString();
                    String all_details = "{"+"pro_code"+":\""+pro_code +"\"," +
                            "pro_name"+":\""+pro_name +"\"," +
                            "pro_rate"+":\""+pro_rate +"\"," +
                            "pro_qty"+":\""+pro_qty +"\"}";
                    cartItem.add(all_details);
                    System.out.println("Cart Item Added" + cartItem);
                }
                else{

                    String pro_code = holder.productcode.getText().toString();
                    String pro_name = holder.productname.getText().toString();
                    String pro_qty  = holder.quantity.getText().toString();
                    String all_details = "{"+"pro_code"+":\""+pro_code +"\"," +
                            "pro_name"+":\""+pro_name +"\"," +
                            "pro_qty"+"\""+pro_qty +"\"}";
                    cartItem.remove(all_details);
                    System.out.println("Cart Item Removed" + cartItem);
                }

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Orderinfo", cartItem+"");
                System.out.println("the Fragment : " +cartItem);
                editor.clear();
                editor.commit();
            }

        });


    }

    private void clicklicener() {

    }



    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        mOriginalData.clear();

        if (charText.length() == 0) {
            mOriginalData.addAll(mDataset);

            System.out.print("response search nothing");

        } else {
            for (Item postDetail : mDataset) {
                System.out.print("response search nothing"+mDataset );
                if (charText.length() != 0 && postDetail.getItemcode().toLowerCase(Locale.getDefault()).contains(charText)
                        || postDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)
                        || postDetail.getQty().toLowerCase(Locale.getDefault()).contains(charText)){
                    mOriginalData.add(postDetail);
                }
            }

            System.out.print("response search hiiiiiiiiiiiiiiiiiiiiiiiiiiii" +charText);
        }
        notifyDataSetChanged();
    }



    }
