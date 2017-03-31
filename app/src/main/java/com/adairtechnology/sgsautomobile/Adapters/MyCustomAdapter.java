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
import static com.adairtechnology.sgsautomobile.ActivityClasses.Activity_Inward_Entry_Screen.txt_total;

/**
 * Created by AndroidTeam2 on 2/13/2017.
 */

public class MyCustomAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<ListItem> arraylist;
    private List<ListItem> itemList;//arrayList.addAll(itemlist)
    private Context mContext;
    public static int outQuty;
    public int test2 = 0;
//    public static int test;

     ArrayList<String> testItem = new ArrayList<>();
    private List<String> list;

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
        System.out.println("the Item list value is :" + itemList);


    }
    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public ListItem getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

@Override
public View getView(final int position, View view, ViewGroup viewGroup) {
    String code,name,quentity,rate,dist;
    String purName,purcode,purQuty,purrate,purdist;
    int test;


     final ViewHolder holder;
    System.out.println("The Entry open");
    if (view == null) {
        view = LayoutInflater.from(mContext).inflate(R.layout.activity_item, viewGroup, false);
        holder = new ViewHolder();
       /* holder = new ViewHolder();

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        view = inflater.inflate(R.layout.activity_item,viewGroup,false);
       */ holder.itemname = (TextView) view.findViewById(R.id.txtname);
        holder.itemcode = (TextView) view.findViewById(R.id.txtCode);
        holder.itemqut = (TextView) view.findViewById(R.id.txtcountry);
        holder.itemdis = (TextView) view.findViewById(R.id.txtcou);
        holder.itemrate = (TextView) view.findViewById(R.id.txtcoun);
        holder.img_delete = (ImageView)view.findViewById(R.id.check_id);

           /* code =itemList.get(position).getItemCode();
            name = itemList.get(position).getItemName();
            quentity  = itemList.get(position).getIteQuty();
            rate = itemList.get(position).getItemRate();
            dist = itemList.get(position).getItemDisc();*/

            code =itemList.get(position).itemCode;
            name = itemList.get(position).itemName;
            quentity  = itemList.get(position).iteQuty;
            rate = itemList.get(position).itemRate;
            dist = itemList.get(position).itemDisc;

            purName =  "pro_name =" + name + ";";
            purcode =  "pro_code =" + code +  ";";
            purQuty =  "pro_qty ="  + quentity +";";
            purrate =  "pro_rate =" + rate +";";
            purdist = "pro_disc =" + dist ;

            String all_details = "{"+purName + purcode + purQuty+ purrate+ purdist+"}";
            testItem.add(all_details);
            notifyDataSetChanged();
            System.out.println("The custom finalist : "+testItem);

            SharedPreferences pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("ItemListFinalValue", String.valueOf(testItem));
            editor.clear();
            editor.commit();




        holder.img_delete.setTag(position);
        System.out.println(" The Position :" +position);

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        mContext);

                alertDialogBuilder.setTitle("Delete");

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                itemList.remove(position);
                                notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

            }
        });
        test = Integer.parseInt(itemList.get(position).getIteQuty());
        test2 = test2 + test;
        outQuty =test2;
        txt_total.setText(" Total Quantity : " + outQuty);


        view.setTag(holder);
        System.out.println("The adapter if condtion ");
//        testItem.clear();
    }
    else
    {
        holder = (ViewHolder)view.getTag();
        System.out.println("The adapter else condion");

    }

            holder.itemcode.setText(itemList.get(position).getItemCode());
            holder.itemname.setText(itemList.get(position).getItemName());
            holder.itemqut.setText(itemList.get(position).getIteQuty());
            holder.itemrate.setText(itemList.get(position).getItemRate());
            holder.itemdis.setText(itemList.get(position).getItemDisc());

//            testItem.clear();

            /*name = holder.itemname.getText().toString();
            qty  = holder.itemqut.getText().toString();
            rate = holder.itemrate.getText().toString();
            dist = holder.itemdis.getText().toString();

            purName =  "pro_name =" + name + ";";
            purcode =  "pro_code =" + code +  ";";
            purQuty =  "pro_qty ="  + qty +";";
            purrate =  "pro_rate =" + rate +";";

            String all_details = "{"+purName + purcode + purQuty+ purrate+"}";
            System.out.println("The custom finalist : "+all_details);
            */
            System.out.println("The Entry close");




    return view;
    }

    class ViewHolder {

        TextView itemname, itemcode,itemqut,itemdis,itemrate;
        protected ImageView img_delete;

    }
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        System.out.print("response arraylist"+arraylist);
        itemList.clear();
        if (charText.length() == 0) {
            itemList.addAll(arraylist);
            System.out.print("response search nothing"+itemList);

        } else {
            for (ListItem postDetail : arraylist) {
                System.out.print("response search nothing"+arraylist);
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

