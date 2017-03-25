package com.adairtechnology.sgsautomobile.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.adairtechnology.sgsautomobile.ActivityClasses.Activity_Inward_Entry_Screen;
import com.adairtechnology.sgsautomobile.Models.ListItem;
import com.adairtechnology.sgsautomobile.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by AndroidTeam2 on 2/13/2017.
 */

public class MyCustomAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater mLayoutInflater;
    private ArrayList<ListItem> arraylist;
    private List<ListItem> itemList;//arrayList.addAll(itemlist)
    private Context mContext;
    private String code;
    private String name;
    private String qty;
    private String rate;
    private String dist;
    private String purName;
    private String purcode;
    private String purQuty;
    private String purrate;
    private static ArrayList<String> testItem;

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

    final ViewHolder holder;
    System.out.println("The Entry open");
    if (view == null) {
        holder = new ViewHolder();

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        view = inflater.inflate(R.layout.activity_item,viewGroup,false);
        holder.itemname = (TextView) view.findViewById(R.id.txtname);
        holder.itemcode = (TextView) view.findViewById(R.id.txtCode);
        holder.itemqut = (TextView) view.findViewById(R.id.txtcountry);
        holder.itemdis = (TextView) view.findViewById(R.id.txtcou);
        holder.itemrate = (TextView) view.findViewById(R.id.txtcoun);
        holder.img_delete = (ImageView)view.findViewById(R.id.check_id);

        holder.img_delete.setTag(position);
        System.out.println(" The Position :" +position);

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = (Integer)v.getTag();
                System.out.println("INTger Valu " + integer);
                Integer integer1 = Activity_Inward_Entry_Screen.RowCount - integer;
                System.out.println("INTger Valu " + integer1);
                itemList.remove(position);

                notifyDataSetChanged();
            }
        });


        view.setTag(holder);
        System.out.println("The adapter if condtion ");

    }
    else {
        holder = (ViewHolder)view.getTag();
        System.out.println("The adapter else condion");
    }

            holder.itemcode.setText(itemList.get(position).getItemCode());
            holder.itemname.setText(itemList.get(position).getItemName());
            holder.itemqut.setText(itemList.get(position).getIteQuty());
            holder.itemrate.setText(itemList.get(position).getItemRate());
            holder.itemdis.setText(itemList.get(position).getItemDisc());

            code = holder.itemcode.getText().toString();
            name = holder.itemname.getText().toString();
            qty  = holder.itemqut.getText().toString();
            rate = holder.itemrate.getText().toString();
            dist = holder.itemdis.getText().toString();

            System.out.println("The jsonobject :" +code);
            System.out.println("The jsonobject :" +name);
            System.out.println("The jsonobject :" +qty);
            System.out.println("The jsonobject :" +rate);
            System.out.println("The jsonobject :" +dist);

            if(!code.equals("") && !name.equals("")){
                System.out.println("The have value fom if condition");
                purName =  "pro_name =" + name + ";";
                purcode =  "pro_code =" + code +  ";";
                purQuty =  "pro_qty ="  + qty +";";
                purrate =  "pro_rate =" + rate +";";
                String purdisc = "pro_disc =" + dist;

                testItem = new ArrayList<String>();
                all_details = "{"+purName + purcode + purQuty+ purrate+ purdisc +"}";
                System.out.println("the sen value is :" +all_details);
                testItem.add(all_details);

//                arrayList.addAll(itemlist);

                pref = PreferenceManager.getDefaultSharedPreferences(mContext);
                editor = pref.edit();
                editor.putString("Value", String.valueOf(testItem));
                System.out.println("The mycustom adapter is :" +testItem);
                editor.commit();

//                testItem.clear();
//                testItem.clear();
            }
            else
            {
                System.out.println("The dont have a vale else condtion");

            }
    System.out.println("The Entry close");
//    testItem.clear();
    editor.clear();

    return view;
    }

    protected class ViewHolder {

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

