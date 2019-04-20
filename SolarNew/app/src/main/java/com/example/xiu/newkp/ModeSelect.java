package com.example.xiu.newkp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ModeSelect extends AppCompatActivity {


    ImageView imgF,imgN;
    TextView txvAb;
    boolean CheckClickAbout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        imgF = (ImageView) findViewById(R.id.imgFar);
        imgN = (ImageView) findViewById(R.id.imgNear);
        txvAb = (TextView) findViewById(R.id.txvAbout);

        txvAb.setText("Về chúng tôi");

        final Global g = (Global)getApplication();
        imgF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(g.CheckWIFI(ModeSelect.this) == true)
                {
                    Intent intent = new Intent(ModeSelect.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        imgN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(g.CheckWIFI(ModeSelect.this) == true)
                {
                    Intent intent = new Intent(ModeSelect.this,NearShowData.class);
                    startActivity(intent);
                }
            }
        });

        txvAb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckClickAbout == true)
                {
                    txvAb.setText("UIT - Khoa Kỹ Thuật Máy Tính");
                    CheckClickAbout = false;
                }
                else
                {
                    txvAb.setText("Về chúng tôi");
                    CheckClickAbout = true;
                }

            }
        });


    }
}
