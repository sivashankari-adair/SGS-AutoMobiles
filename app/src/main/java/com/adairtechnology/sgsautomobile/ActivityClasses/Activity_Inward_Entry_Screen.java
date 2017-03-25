package com.adairtechnology.sgsautomobile.ActivityClasses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adairtechnology.sgsautomobile.Adapters.ItemCartAdapter;
import com.adairtechnology.sgsautomobile.Adapters.MyCustomAdapter;
import com.adairtechnology.sgsautomobile.Db.DBController;
import com.adairtechnology.sgsautomobile.Fragments.FragmentHome;
import com.adairtechnology.sgsautomobile.Models.Item;
import com.adairtechnology.sgsautomobile.Models.ListItem;
import com.adairtechnology.sgsautomobile.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.adairtechnology.sgsautomobile.R.layout.item;

/**
 * Created by Android-Team1 on 2/10/2017.
 */

public class Activity_Inward_Entry_Screen extends AppCompatActivity {

    private static TextView party_name,bill_no,bill_date,godown_name;
    private EditText edt_search,edt_nameVendor,edt_billNo,edt_date,edt_godownName,edt_discountNew,edt_rateNew,edt_qutyNew,edt_itemCodeNew,edt_nameNew;
    String purchaseItemName, purchaseCode, purchQuty, purRate, purDisc, str_addNew, str_vendorInf, arrayAddNew, arrayVendorInf, str_reportName, str_reportBillno, str_reportDate;
    Button btn_save,btn_saveAddNew,btn_update,btn_report;
    public static String pname,pbill,pdate,pgodown,searchItem;
    String fragementValue,text;
    private Toolbar toolbar;
    public static TextView tvTitle;
    ImageView image_back,image_allitems,image_dialodVendor;
    Dialog dialog;
    String val_frag;
    private ListItem item,item1;
    private List<ListItem> listForSearchConcepts = new ArrayList<ListItem>();
    private List<ListItem> listForSearchConcepts1 = new ArrayList<ListItem>();
    public static int test2 = 0;
    int test;
    ListView myList;
    public static int RowCount;
    public static MyCustomAdapter adapter,adapter1;
    public static TextView tex_rowCount, txt_total;
    FloatingActionButton floatingActionButton_add;
    ArrayList<String> finallist = new ArrayList<String>();
    public static String purName,purcode,purQuty,purrate,purdisc,partyname,gname,gdate,pnobill;
    String restoredIp,logininfo,gcodee,gstatus_code;
    LinearLayout linear_purchase;
    RelativeLayout relative_list;
    String res,server_response;
    String customvalue;
    ImageView imag_calendr;
    static final int DATE_PICKER_ID = 1111;
    private int year;
    private int month;
    private int day;
    public static String currentDateandTime;
    private static String selected_date;
    public static String final_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inward_entry);
        myList = (ListView) findViewById(R.id.list_listView);
        tvTitle = (TextView) findViewById(R.id.tv_title1);
        image_back = (ImageView)findViewById(R.id.img);
        image_back.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));
        linear_purchase = (LinearLayout) findViewById(R.id.linear_purchase);
        relative_list= (RelativeLayout)findViewById(R.id.relative_list);
        image_allitems = (ImageView)findViewById(R.id.imgFragme);
        image_dialodVendor=(ImageView)findViewById(R.id.imgDialog);
        tvTitle.setText(" Inward Entry");
        edt_search = (EditText)findViewById(R.id.searchitem);
        party_name = (TextView)findViewById(R.id.txtpartyname);
        bill_no = (TextView)findViewById(R.id.txtbillnumber);
        bill_date = (TextView)findViewById(R.id.txtdate);
        godown_name = (TextView)findViewById(R.id.txtGodownname);

        dialog = new Dialog(Activity_Inward_Entry_Screen.this);
        dialog.setTitle("Vendor Information");
        dialog.setContentView(R.layout.layout_vendor_information);
        edt_nameVendor = (EditText)dialog.findViewById(R.id.pur_party_name);
        edt_billNo = (EditText) dialog.findViewById(R.id.pur_bill_number);
        edt_date = (EditText)dialog.findViewById(R.id.pur_date);
        imag_calendr = (ImageView)dialog.findViewById(R.id.imgCaled);
//        edt_godownName = (EditText) dialog.findViewById(R.id.godown_name);
        btn_save = (Button) dialog.findViewById(R.id.save_btn_dialog);

        edt_nameNew = (EditText) findViewById(R.id.pur_dialog_itemName);
        edt_itemCodeNew = (EditText) findViewById(R.id.pur_dialog_itemCode);
        edt_qutyNew = (EditText) findViewById(R.id.pur_dialog_quty);
        edt_rateNew = (EditText) findViewById(R.id.pur_dialog_rate);
        edt_discountNew = (EditText) findViewById(R.id.purcDialog_disc);
        floatingActionButton_add = (FloatingActionButton) findViewById(R.id.fabAdd);
        btn_saveAddNew = (Button) findViewById(R.id.save_btn_dialogPur);
        btn_update = (Button)findViewById(R.id.update);
        btn_report =(Button) findViewById(R.id.btn_report);

        tex_rowCount = (TextView) findViewById(R.id.txt_rowCount);
        txt_total = (TextView) findViewById(R.id.txt_quty);

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        edt_date.setText(new StringBuilder().append(day)
                .append("-").append(month + 1).append("-").append(year)
                .append(" "));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        currentDateandTime = sdf.format(new Date());
        System.out.println("test time : " +currentDateandTime);



        SharedPreferences prefss = getSharedPreferences("MYPREFF", MODE_PRIVATE);
        logininfo = prefss.getString("loginInfo", null);
        System.out.println("The login inform : " +logininfo);
        try {
            JSONObject jsonObj = new JSONObject(logininfo);
            JSONArray godown_name = jsonObj.getJSONArray("Gcode");
            System.out.println("godown_name : " +godown_name);

            // looping through All Contacts
            for (int i = 0; i < godown_name.length(); i++) {
                JSONObject cc = godown_name.getJSONObject(i);
                gcodee = cc.optString("Code");

            }
            System.out.println("godown name :" +gcodee);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent in = getIntent();
        fragementValue = in.getStringExtra("Items_List");
        System.out.println("The Fragment is :" +fragementValue);
        val_frag = fragementValue;
        test(val_frag);


        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                Intent in = new Intent(Activity_Inward_Entry_Screen.this,HomeScreenActivity.class);
                startActivity(in);
                finish();
            }
        });
        imag_calendr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
//                showDialog(DATE_PICKER_ID);
                    Calendar mcurrentDate=Calendar.getInstance();
                    year=mcurrentDate.get(Calendar.YEAR);
                    month=mcurrentDate.get(Calendar.MONTH);
                    day=mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker=new DatePickerDialog(Activity_Inward_Entry_Screen.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */

                            year  = selectedyear;
                            month = selectedmonth;
                            day   = selectedday;

                            edt_date.setText(new StringBuilder().append(day)
                                    .append("-").append(month + 1).append("-").append(year)
                                    .append(""));

                            selected_date = edt_date.getText().toString();
                            final_date = selected_date;

                            System.out.println("Value Date"+selected_date);
                            Log.e("Test url", selected_date+""  );
                        }
                    },year, month, day);
                    mDatePicker.setTitle("Select date");
                    mDatePicker.show();  }
        });

        image_allitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentHome fragment = new FragmentHome();
                    fragmentTransaction.add(android.R.id.content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

            }

        });

        image_dialodVendor.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.show();
           }
       });


        btn_save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                pname= edt_nameVendor.getText().toString();
                pbill= edt_billNo.getText().toString();
                pdate= edt_date.getText().toString();
                System.out.println("The vendor is :" +pgodown);

                System.out.println("the pname is : " +pname);


                partyname = "Pname :" + pname +",";
                gname = "Gname :" + gcodee +",";
                gdate = "Date :" + pdate +",";
                pnobill = "Pbill :" + pbill;


                party_name.setText("Party : " + pname);
                godown_name.setText("Godown : " + gcodee );
                bill_no.setText("BillNo : " + pbill);
                bill_date.setText("Date : " + pdate);

                str_vendorInf = partyname + gname + gdate + pnobill;

                System.out.println("the party name :"+partyname);
                System.out.println("the gname :"+gname);
                System.out.println("the date :"+gdate);
                System.out.println("the pbill :"+pnobill);
                System.out.println("the vendor :"+str_vendorInf);

                addVendorInformation();

            }

        });


       /* myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
*/
        floatingActionButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_purchase.setVisibility(View.VISIBLE);
                relative_list.setVisibility(View.GONE);
            }
        });

        btn_saveAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                linear_purchase.setVisibility(View.GONE);
                relative_list.setVisibility(View.VISIBLE);
               // addToNew();
                Test();
                edt_nameNew.setText("");
                edt_itemCodeNew.setText("");
                edt_qutyNew.setText("");
                edt_rateNew.setText("");
                edt_discountNew.setText("");

            }
        });

        searchtext();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new addNewTask().execute();
            }
        });
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Inward_Entry_Screen.this,ReportActivity.class);
                startActivity(intent);

            }
        });
//        tex_rowCount.setText("RowCount :" + MyCustomAdapter.RowCount);
      /*  adapter1 = new MyCustomAdapter(Activity_Inward_Entry_Screen.this, android.R.layout.simple_list_item_1, listForSearchConcepts);
        System.out.println("List Size2 :"+listForSearchConcepts.size());
        for (int i = 1; i == listForSearchConcepts.size(); i++) {

            System.out.println("Count_loop :"+listForSearchConcepts.size());
            myList.setAdapter(adapter1);
            //myList.setScrollingCacheEnabled(true);
            adapter1.notifyDataSetChanged();
        }*/



    }

    private void searchtext() {
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text = edt_search.getText().toString().toLowerCase(Locale.getDefault());
                System.out.println("The string value " +text);
                adapter.filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
                }
        });
    }

    private void test(String val_frag) {

        try {

            System.out.println("The json " +val_frag);
            JSONArray itemss = new JSONArray(val_frag);
            System.out.println("The json array " +itemss);
            for (int i = 0; i < itemss.length(); i++) {
                System.out.println("The json array for loop " +i);
                JSONObject c = itemss.getJSONObject(i);
                System.out.println("The json array for loop " +c);
//                finallist.add(String.valueOf(c));
                System.out.println("The fragment list value :" +finallist);
                item1 = new ListItem();
                item1.setItemName(c.optString("pro_name"));
                item1.setItemCode(c.optString("pro_code"));
                item1.setIteQuty(c.optString("pro_qty"));
                item1.setItemRate("0");
                item1.setIteQuty("0");
                System.out.println("The jsonobject :" +c);
                listForSearchConcepts.add(0,item1);
                System.out.println("List item :" + listForSearchConcepts.toString());
            }
            adapter1 = new MyCustomAdapter(Activity_Inward_Entry_Screen.this, android.R.layout.simple_list_item_1, listForSearchConcepts);
                    myList.setAdapter(adapter1);
                    System.out.println("The adapter list :" + String.valueOf(myList));
                    myList.setScrollingCacheEnabled(true);
                    adapter1.notifyDataSetChanged();

            /*RowCount = listForSearchConcepts.size();
            System.out.println("Size of List :" + RowCount);*/
            RowCount = listForSearchConcepts.size();
            System.out.println("Size of List :" + RowCount);
            tex_rowCount.setText("RowCount :" + RowCount);



        }catch (Exception e){
            e.printStackTrace();
        }


    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay)
        {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            edt_date.setText(new StringBuilder().append(day)
                    .append("-").append(month + 1).append("-").append(year)
                    .append(""));

            selected_date = edt_date.getText().toString();
            final_date = selected_date;

            System.out.println("Value Date"+selected_date);
            Log.e("Test url", selected_date+""  );

        }
    };


    private void addVendorInformation() {


               /* pname= edt_nameVendor.getText().toString();
                pbill= edt_billNo.getText().toString();
                pdate= edt_date.getText().toString();
                System.out.println("The vendor is :" +pgodown);


                partyname = "Pname :" + pname +",";
                gname = "Gname :" + gcodee +",";
                gdate = "Date :" + pdate +",";
                pnobill = "Pbill :" + pbill;
*/
              /*  str_vendorInf = partyname + gname + gdate + pnobill;

                System.out.println("the party name :"+partyname);
                System.out.println("the gname :"+gname);
                System.out.println("the date :"+gdate);
                System.out.println("the pbill :"+pnobill);
                System.out.println("the vendor :"+str_vendorInf);


              */  /*party_name.setText("Party : " + pname);
                godown_name.setText("Godown : " + gcodee );
                bill_no.setText("BillNo : " + pbill);
                bill_date.setText("Date : " + pdate);

*/                if(!pname.equals("") && !pbill.equals("")  && !pdate.equals("")){
                    Toast.makeText(Activity_Inward_Entry_Screen.this, "If Vendor Correct", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(Activity_Inward_Entry_Screen.this, "Else Vendor not correct", Toast.LENGTH_SHORT).show();
                    dialog.show();
                }


        }


    private void Test() {
        System.out.println("Test Part Open");
        purchaseItemName = edt_nameNew.getText().toString();
        purchaseCode = edt_itemCodeNew.getText().toString();
        purchQuty = edt_qutyNew.getText().toString();
        purRate = edt_rateNew.getText().toString();
        purDisc = edt_discountNew.getText().toString();

        System.out.println("The final list is :" +String.valueOf(finallist));
        purName =  "pro_name =" + purchaseItemName + ";";
        purcode =  "pro_code =" + purchaseCode +  ";";
        purQuty =  "pro_qty ="  + purchQuty +";";
        purrate =  "pro_rate =" + purRate +";";
        purdisc =  "pro_disc =" + purDisc;

        str_addNew = "{" + purName + purcode +purQuty + purrate +purdisc +"}";
        System.out.println("the value is addingg :" +str_addNew);
        finallist.add(str_addNew);

        if (!purchaseItemName.equals("") && !purchaseCode.equals("") && !purchQuty.equals("") && !purRate.equals("") && !purDisc.equals("")) {

            item = new ListItem();
            item.setItemName(purchaseItemName);
            item.setItemCode(purchaseCode);
            item.setIteQuty(purchQuty);
            item.setItemRate(purRate);
            item.setItemDisc(purDisc);
            listForSearchConcepts.add(0, item);
            System.out.println("List Size1 :"+listForSearchConcepts.size());
            adapter = new MyCustomAdapter(Activity_Inward_Entry_Screen.this, android.R.layout.simple_list_item_1, listForSearchConcepts);
            System.out.println("List Size2 :"+listForSearchConcepts.size());
            for (int i = 0; i < listForSearchConcepts.size(); i++) {

                System.out.println("Count_loop :"+listForSearchConcepts.size());
               myList.setAdapter(adapter);
                //myList.setScrollingCacheEnabled(true);
                adapter.notifyDataSetChanged();
            }

            RowCount = listForSearchConcepts.size();
            System.out.println("Size of List :" + RowCount);
            tex_rowCount.setText("RowCount :" + RowCount);


        } else {

            Toast.makeText(Activity_Inward_Entry_Screen.this, R.string.dialogAdd_new, Toast.LENGTH_SHORT).show();
        }
        System.out.println("Test Part Close");
    }



    private class addNewTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MYPREFERNCE",MODE_PRIVATE);
            restoredIp = prefs.getString("ip", null);
            System.out.println("wedew"+restoredIp);

            SharedPreferences pref = getApplicationContext().getSharedPreferences("value", MODE_PRIVATE);
            String value = pref.getString("Value", "");
            System.out.println("Value Submit for update: "+ ""+value);

            SharedPreferences prefsss = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            customvalue= prefsss.getString("Value", null);
            System.out.println("hi Fragment One"+customvalue);


            // http://space7cloud.com/automobiles/get_data.php
            //http://space7cloud.com/automobiles/get_data.php?page=item_save&party_details={}&item_list={}

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority(restoredIp)//space7cloud.com//2//92.168.1.100//192.168.1.7
                    .appendPath("android")
                    .appendPath("automobiles")//sgs_traders
                    .appendPath("get_data.php")
                    .appendQueryParameter("page", "item_save")
                    .appendQueryParameter("party_details", str_vendorInf)
                    .appendQueryParameter("item_list", customvalue);
            System.out.println("test url : " + customvalue);

            String myUrl = builder.build().toString();
            System.out.println("Value Submit Url  :" + myUrl);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpget = new HttpPost(myUrl);
            System.out.println("The Http " + httpget);
            HttpResponse response = null;
            try {
                response = httpclient.execute(httpget);
                System.out.println("The Http rsponse " + response);
                if (response.getStatusLine().getStatusCode() == 200) {

                    server_response = EntityUtils.toString(response.getEntity());

                    try {
                        JSONObject jsonObjj = new JSONObject(server_response);
                        res = jsonObjj.getString("status");
                        gstatus_code = jsonObjj.getString(gcodee);
                        System.out.println("hfdfdfh" + gstatus_code);
                        System.out.println("hdfh" + res);

                        //Getting JSON ARRAY for Items
                        JSONObject Godown_jsonobjj = jsonObjj.getJSONObject(gcodee);
                        System.out.println("hdfhsdsd" + Godown_jsonobjj);

                        JSONArray Godown_list_items_jsonobjj = Godown_jsonobjj.getJSONArray("items");
                        for (int j = 0; j < Godown_list_items_jsonobjj.length(); j++) {
                            JSONObject c = Godown_list_items_jsonobjj.getJSONObject(j);

                            ListItem itemc = new ListItem();
                            itemc.setIteQuty(c.optString("qty"));
                            itemc.setItemName(c.optString("name"));
                            itemc.setItemCode(c.optString("itemcode"));

//                            listForSearchConcepts.add(0,item);

//                            Log.i("myitemlist", listForSearchConcepts.toString());

                            String itemcode = c.optString("itemcode");
                            String itemname = c.optString("name");
                            String itemfont = c.optString("font");
                            String itemid = c.optString("id");
                            String itemqty = c.optString("qty");

                            DBController controller = new DBController(Activity_Inward_Entry_Screen.this);
                            SQLiteDatabase db = controller.getWritableDatabase();
                            ContentValues cv2 = new ContentValues();

                            cv2.put("name", itemname);
                            cv2.put("itemcode", itemcode);
                            cv2.put("quantity", itemqty);
                            db.insert("items", null, cv2);
                            db.close();

                            System.out.println("test_cv2" + cv2);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("Server response", server_response);
                    Log.i("Server response", myUrl);
                    System.out.println("Value server resonse" + server_response);

                } else {
                    Log.i("Server response", "Failed to get server response");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            Toast.makeText(Activity_Inward_Entry_Screen.this,res,Toast.LENGTH_SHORT).show();

        }
    }

}





