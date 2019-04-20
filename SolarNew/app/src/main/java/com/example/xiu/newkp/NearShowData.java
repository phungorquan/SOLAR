package com.example.xiu.newkp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NearShowData extends AppCompatActivity {

    TextView txvNDT, txvND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_show_data);

        txvNDT = (TextView) findViewById(R.id.txvNearDataTittle);
        txvND = (TextView)  findViewById(R.id.txvNearData);

        getSupportActionBar().setTitle("Data");

        Global g = (Global) getApplication();
        if(g.CheckWIFI(NearShowData.this) == true)
        {
            txvNDT.setText("Lượng điện thu được hiện tại");

            txvND.setText("Chưa có dữ liệu");
        }





    }
}
