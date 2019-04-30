package com.example.xiu.newkp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity{
    TextView tvLog,tvReg;
    EditText edtID,edtPASS;
    String urlpostdata =  "http://ceecsolarsystem.herokuapp.com/androidLogin";  // URL để gửi thông tin đăng nhập
    ImageView imgLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login");// Hàm hỗ trợ hiển thị tên ở góc trái màn hình và nút Back ( Nút back cần được set sẽ Back về đâu trong file Manifest)

        // Các thủ tục ảnh xạ bên Layout
        final Global g = (Global)getApplication();
        imgLogin =(ImageView)findViewById(R.id.imageView);
        edtID = (EditText) findViewById(R.id.edtID);
        edtPASS = (EditText)findViewById(R.id.edtPASS);

        tvLog = (TextView) findViewById(R.id.txvLog);
        tvReg = (TextView) findViewById(R.id.txvReg);

        tvLog.setText("ĐĂNG NHẬP");
        tvReg.setText("ĐĂNG KÝ");

        // Nếu click đăng nhập sẽ nhảy vào đây
        tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString().trim();      // Get id from edtID
                String pass = edtPASS.getText().toString().trim();  // Get id from edtPASS
                g.setData(id);

                if(g.CheckWIFI(MainActivity.this) == true)
                {
                    if (id.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Login(urlpostdata); // Khi đăng nhập thành công sẽ nhảy vào hàm này
                    }
                }
            }
        });

        // Nếu click đăng ký sẽ nhảy vào đây nhưng hiện tại chưa có chức năng này
        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(g.CheckWIFI(MainActivity.this) == true)
//                {
//                    Intent intent = new Intent(MainActivity.this,RegisterAccount.class);
//                    startActivity(intent);
//                }
            }
        });

    }

        public void Login (String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("FAIL"))
                        {
                            Toast.makeText(MainActivity.this,"Sai tài khoản hoặc mật khẩu, hãy thử lại !!!", Toast.LENGTH_SHORT).show();
                        }
                        else // Nếu đăng nhập thành công thì sẽ lấy thông tin các NodeList gửi về
                        {
                            Global g = (Global) getApplication();
                            g.setResponseJsonNodeList(response);    // Lưu các NodeList vào biến cục bộ để ở Activity sau hiển thị
                            Intent intent = new Intent(MainActivity.this, Node_List.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Nếu có lỗi
                        Toast.makeText(MainActivity.this,"Hệ thống đang bận, vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            // Lấy thông tin người dùng nhập để chuẩn bị gửi đi
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",edtID.getText().toString().trim());
                params.put("password",edtPASS.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest); // Gửi Request đi
    }
}
