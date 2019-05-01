package com.example.xiu.newkp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ModeSelect extends AppCompatActivity {


    ImageView imgF,imgN;
    TextView txvAb;
    boolean CheckClickAbout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        // Các thủ tục ánh xạ bên layout
        imgF = (ImageView) findViewById(R.id.imgFar);
        imgN = (ImageView) findViewById(R.id.imgNear);
        txvAb = (TextView) findViewById(R.id.txvAbout);

        txvAb.setText("Về chúng tôi"); // Hiển thị thông tin ở cuối màn hình



        // Hiển thị hai hình để chọn Mode gần hoặc xa
        // Mỗi lần ấn vào hình thì checkwifi để chắc chắn người dùng đang connect wifi
        imgF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global g = (Global)getApplication();// Khởi tạo Object g để gọi hàm check wifi

                if(g.CheckWIFI(ModeSelect.this) == true)
                {
                    // Nếu ấn chọn mode xa thì di chuyển đến đăng nhập (MainActivity Activity)
                    Intent intent = new Intent(ModeSelect.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        imgN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global g = (Global)getApplication();// Khởi tạo Object g để gọi hàm check wifi
                if(g.CheckWIFI(ModeSelect.this) == true)
                {
                    // Nếu ấn chọn mode xa thì di chuyển đến xem dữ liệu ở khoảng cách gần (NearShowData Activity)
                    Intent intent = new Intent(ModeSelect.this,NearShowData.class);
                    startActivity(intent);
                }
            }
        });

        // Nếu có nhấn vào thông tin thì sẽ hiển thị toggle thông tin
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
