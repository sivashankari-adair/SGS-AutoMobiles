package com.adairtechnology.sgsautomobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adairtechnology.sgsautomobile.Fragments.FragmentHome;

/**
 * Created by Android-Team1 on 2/10/2017.
 */

public class Activity_Inward_Entry_Screen extends AppCompatActivity {

    private TextView party_name,bill_no,bill_date,godown_name;
    EditText edt_name,edt_billNo,edt_date,edt_godownName;
    Button btn_save;
    String pname,pbill,pdate,pgodown;
    private Toolbar toolbar;
    public static TextView tvTitle;
    ImageView image_back,image_notification,image_allitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inward_entry);

        Intent in = getIntent();
        String ins = in.getStringExtra("Items_List");
        Toast.makeText(Activity_Inward_Entry_Screen.this,ins
                , Toast.LENGTH_LONG).show();



        tvTitle = (TextView) findViewById(R.id.tv_title1);
        image_back = (ImageView)findViewById(R.id.img);
        image_back.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left));

        image_notification = (ImageView)findViewById(R.id.img2);
        image_allitems = (ImageView)findViewById(R.id.img3);
        tvTitle.setText(" Inward Entry");

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        image_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_Inward_Entry_Screen.this,"noti", Toast.LENGTH_LONG).show();

            }
        });
        image_allitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_Inward_Entry_Screen.this,"items", Toast.LENGTH_LONG).show();

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


                FragmentHome fragment = new FragmentHome();

                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        party_name = (TextView)findViewById(R.id.txtpartyname);
        bill_no = (TextView)findViewById(R.id.txtbillnumber);
        bill_date = (TextView)findViewById(R.id.txtdate);
        godown_name = (TextView)findViewById(R.id.txtGodownname);

        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Vendor Information");
        dialog.setContentView(R.layout.layout_vendor_information);
        edt_name = (EditText)dialog.findViewById(R.id.pur_party_name);
        edt_billNo = (EditText) dialog.findViewById(R.id.pur_bill_number);
        edt_date = (EditText)dialog.findViewById(R.id.pur_date);
        edt_godownName = (EditText) dialog.findViewById(R.id.godown_name);
        btn_save = (Button) dialog.findViewById(R.id.save_btn_dialog);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pname= edt_name.getText().toString();
                pbill= edt_billNo.getText().toString();
                pgodown= edt_godownName.getText().toString();
                pdate= edt_date.getText().toString();

                party_name.setText("Party : " + pname);
                godown_name.setText("Godown : " + pgodown);
                bill_no.setText("BillNo : " + pbill);
                bill_date.setText("Date : " + pdate);
                dialog.dismiss();
            }
        });
        dialog.show();



    }
}

