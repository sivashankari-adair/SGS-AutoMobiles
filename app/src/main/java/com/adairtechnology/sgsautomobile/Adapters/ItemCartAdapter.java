package com.adairtechnology.sgsautomobile.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adairtechnology.sgsautomobile.Activity_All_Items_Screen;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android-Team1 on 2/10/2017.
 */

public class ItemCartAdapter extends RecyclerView
        .Adapter<ItemCartAdapter
        .DataObjectHolder> {
    private Context mContext;
    private static String LOG_TAG = "ItemCartAdapter";
    private ArrayList<Item> mDataset;
    private ArrayList<Item> itemNameArrayList = new ArrayList<>();
    LayoutInflater inflater;
    private static int added_value;
 //   public static String all_details;
    private ArrayList<String> cartItem = new ArrayList<String>();;

    public ItemCartAdapter(Context mContext, ArrayList<Item> itemNameArrayList) {

        this.mContext = mContext;
        this.itemNameArrayList = itemNameArrayList;
        inflater = LayoutInflater.from(mContext);
        this.mDataset = new ArrayList<Item>();
        this.mDataset.addAll(itemNameArrayList);

    }

    public class DataObjectHolder extends RecyclerView.ViewHolder
            {
        TextView quantity,productname,productcode;
        ImageView product_detail,increment_value,decrement_value;
                CheckBox chkIos;

        public DataObjectHolder(View itemView) {
            super(itemView);
            quantity = (TextView) itemView.findViewById(R.id.quantityTV);
            productname = (TextView) itemView.findViewById(R.id.itemNameTV);
            productcode = (TextView) itemView.findViewById(R.id.itemCodeTV);
            product_detail =(ImageView)itemView.findViewById(R.id.displayItemDetailsIV);
            increment_value =(ImageView)itemView.findViewById(R.id.plusIV);
            decrement_value =(ImageView)itemView.findViewById(R.id.minusIV);
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
        holder.quantity.setText(mDataset.get(position).getQty());
        holder.productcode.setText(mDataset.get(position).getItemcode());
        holder.productname.setText(mDataset.get(position).getName());

        holder.chkIos.setOnClickListener(new View.OnClickListener() {

            String pro_code = holder.productcode.getText().toString();
            String pro_name = holder.productname.getText().toString();
            String pro_qty  = holder.quantity.getText().toString();
            String all_details = "{"+"pro_code="+pro_code +"," +
                    "pro_name="+pro_name +"," +
                    "pro_qty="+pro_qty +"}";

            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {

                    cartItem.add(all_details);
                    System.out.println("Cart Item Added" + cartItem);
                }
                else{
                    cartItem.remove(all_details);
                    System.out.println("Cart Item Removed" + cartItem);
                }
            }
        });


        holder.increment_value.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View v) {
                int counter = Integer.parseInt(holder.quantity.getText()+"");
                counter++;

                added_value = counter;
                holder.quantity.setText(added_value+"");

             }
        });


        holder.decrement_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int new_val = Integer.parseInt(holder.quantity.getText()+"");
                new_val--;

                int added_value = new_val;
                holder.quantity.setText(added_value+"");

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemNameArrayList.size();
    }
}