package com.adairtechnology.sgsautomobile.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adairtechnology.sgsautomobile.Models.ListItem;
import com.adairtechnology.sgsautomobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.app.AlertDialog;
import android.content.DialogInterface;

import static android.content.Context.MODE_PRIVATE;
import static com.adairtechnology.sgsautomobile.ActivityClasses.Activity_Inward_Entry_Screen.parmeter;
import static com.adairtechnology.sgsautomobile.ActivityClasses.Activity_Inward_Entry_Screen.tex_rowCount;
import static com.adairtechnology.sgsautomobile.ActivityClasses.Activity_Inward_Entry_Screen.txt_total;

/**
 * Created by AndroidTeam2 on 2/13/2017.
 */

public class MyCustomAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<ListItem> arraylist;
    private List<ListItem> itemList;//arrayList.addAll(itemlist)
    private Context mContext;
    public int outQuty;
    String code,name,quentity,rate,dist;
    String purName,purcode,purQuty,purrate,purdist;
    int rowBeforeDel;
    public ArrayList<Integer> Quantity;


    public int quantitys;
    public static int delQutya;
//    public static int test;

    ArrayList<String> testItem = new ArrayList<>();
    ArrayList<String> testItems = new ArrayList<>();
    private ArrayList<ListItem> list;

    private String all_details;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public MyCustomAdapter(Context context, int simple_list_item_1, List<ListItem> itemList){

        super(context ,0,itemList);
        this.itemList = itemList;
        mContext = context;
//        mLayoutInflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<ListItem>();
        this.arraylist.addAll(itemList);
      //  System.out.println("the Item list value is :" + itemList);



    }


    @Override
    public ListItem getItem(int position) {
    /*    quantity = Integer.parseInt(itemList.get(position).getIteQuty());
        System.out.println("The total quantity of ADA : " + MyCustomAdapter.quantity);*/


        return itemList.get(position);
    }


    @Override
    public long getItemId(int position) {


        rowBeforeDel = itemList.size();
        System.out.println(" list size is" + rowBeforeDel);



        return position;
    }

    @Override
    public int getCount() {
        rowBeforeDel = itemList.size();
        System.out.println(" list size is" + rowBeforeDel);
        tex_rowCount.setText("TotalCount :" + rowBeforeDel);



     return itemList.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        int test2 = 0;
        ViewHolder holder;
        if (parmeter.equals(" ")) {

            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.activity_main_custom, viewGroup, false);
                holder = new ViewHolder();
                Quantity = new ArrayList<Integer>();
                holder.itemname = (TextView) view.findViewById(R.id.txtnames);
                holder.itemcode = (TextView) view.findViewById(R.id.txtCodes);
                holder.itemqut = (TextView) view.findViewById(R.id.txtQuty);
                holder.itemdis = (TextView) view.findViewById(R.id.txtDisc);
                holder.itemrate = (TextView) view.findViewById(R.id.txtRate);
                holder.img_delete = (ImageView) view.findViewById(R.id.delete);

                code = itemList.get(position).itemCode;
                name = itemList.get(position).itemName;
                quentity = itemList.get(position).iteQuty;
                rate = itemList.get(position).itemRate;
                dist = itemList.get(position).itemDisc;

                purName = "pro_name =" + name + ";";
                purcode = "pro_code =" + code + ";";
                purQuty = "pro_qty =" + quentity + ";";
                purrate = "pro_rate =" + rate + ";";
                purdist = "pro_disc =" + dist;

                String all_details = "{" + purName + purcode + purQuty + purrate + purdist + "}";
                testItem.add(all_details);
                notifyDataSetChanged();
                System.out.println("The if array item : " +testItem);

                SharedPreferences pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ItemListFinalValue", String.valueOf(testItem));
                editor.clear();
                editor.commit();
                rowBeforeDel = itemList.size();
               // tex_rowCount.setText("TotalCount :" + rowBeforeDel);

                for (int i = 0; i < rowBeforeDel; i++) {
                    int val = Integer.parseInt(itemList.get(i).getIteQuty());
                    Quantity.add(val);
                    System.out.println("The total quantity of array: " + Quantity + "  hi");

                }

                for (int j = 0; j < Quantity.size(); j++) {
                    int id = Quantity.get(j);
                    test2 = test2 + id;
                    System.out.println("The total quantity of array tetst: " + test2);
                    txt_total.setText("Total Quantity :"+test2);
                }


                view.setTag(holder);


            } else {
                holder = (ViewHolder) view.getTag();

            }

            holder.itemcode.setText(itemList.get(position).getItemCode());
            holder.itemname.setText(itemList.get(position).getItemName());
            holder.itemqut.setText(itemList.get(position).getIteQuty());
            holder.itemrate.setText(itemList.get(position).getItemRate());
            holder.itemdis.setText(itemList.get(position).getItemDisc());

            holder.img_delete.setTag(position);
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            mContext);

                    alertDialogBuilder.setTitle("Delete");

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    int test = Integer.parseInt(itemList.get(position).getIteQuty());
                                    itemList.remove(position);
                                    notifyDataSetChanged();
                                    txt_total.setText("Total Quantity :");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();
                }

            });
            return view;

        }
        else{

            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.activity_item, viewGroup, false);
                holder = new ViewHolder();
                Quantity = new ArrayList<Integer>();
                holder.itemname = (TextView) view.findViewById(R.id.txtnames);
                holder.itemcode = (TextView) view.findViewById(R.id.txtCodes);
                holder.itemqut = (TextView) view.findViewById(R.id.txtQuty);
                holder.itemdis = (TextView) view.findViewById(R.id.txtDisc);
                holder.itemrate = (TextView) view.findViewById(R.id.txtRate);
                holder.img_delete = (ImageView) view.findViewById(R.id.delete);

                holder.itemrate.setVisibility(View.GONE);
                holder.itemdis.setVisibility(View.GONE);

                code = itemList.get(position).itemCode;
                name = itemList.get(position).itemName;
                quentity = itemList.get(position).iteQuty;
                rate = itemList.get(position).itemRate;
                dist = itemList.get(position).itemDisc;

                purName = "pro_name =" + name + ";";
                purcode = "pro_code =" + code + ";";
                purQuty = "pro_qty =" + quentity + ";";
                purrate = "pro_rate =" + rate + ";";
                purdist = "pro_disc =" + dist;

                String all_details = "{" + purName + purcode + purQuty + purrate + purdist + "}";
                testItems.add(all_details);
                notifyDataSetChanged();

                System.out.println("The else array item : " +testItems);

                SharedPreferences pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ItemListFinalValue", String.valueOf(testItems));
                editor.clear();
                editor.commit();
                rowBeforeDel = itemList.size();
                // tex_rowCount.setText("TotalCount :" + rowBeforeDel);

                for (int i = 0; i < rowBeforeDel; i++) {
                    int val = Integer.parseInt(itemList.get(i).getIteQuty());
                    Quantity.add(val);
                    System.out.println("The total quantity of array: " + Quantity + "  hi");

                }

                for (int j = 0; j < Quantity.size(); j++) {
                    int id = Quantity.get(j);
                    test2 = test2 + id;
                    System.out.println("The total quantity of array tetst: " + test2);
                    txt_total.setText("Total Quantity :" +test2);
                }


                view.setTag(holder);


            } else {
                holder = (ViewHolder) view.getTag();

            }

            holder.itemcode.setText(itemList.get(position).getItemCode());
            holder.itemname.setText(itemList.get(position).getItemName());
            holder.itemqut.setText(itemList.get(position).getIteQuty());
            holder.itemrate.setText(itemList.get(position).getItemRate());
            holder.itemdis.setText(itemList.get(position).getItemDisc());

            holder.img_delete.setTag(position);
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            mContext);

                    alertDialogBuilder.setTitle("Delete");

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    int test = Integer.parseInt(itemList.get(position).getIteQuty());
                                    itemList.remove(position);
                                    notifyDataSetChanged();
                                    txt_total.setText("Total Quantity :");
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

            });
            return view;
        }
    }

    class ViewHolder {

        TextView itemname, itemcode,itemqut,itemdis,itemrate;
        ImageView img_delete;

    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

       /// System.out.print("response arraylist"+arraylist);
        itemList.clear();
        if (charText.length() == 0) {
            itemList.addAll(arraylist);
           // System.out.print("response search nothing"+itemList);

        } else {
            for (ListItem postDetail : arraylist) {
                //System.out.print("response search nothing"+arraylist);
                if (charText.length() != 0 && postDetail.getItemCode().toLowerCase(Locale.getDefault()).contains(charText)
                        || postDetail.getItemName().toLowerCase(Locale.getDefault()).contains(charText)
                        || postDetail.getIteQuty().toLowerCase(Locale.getDefault()).contains(charText)
                        || postDetail.getItemRate().toLowerCase(Locale.getDefault()).contains(charText)
                        || postDetail.getItemDisc().toLowerCase(Locale.getDefault()).contains(charText)) {
                    itemList.add(postDetail);
                }
            }
        }
        notifyDataSetChanged();
    }


}
