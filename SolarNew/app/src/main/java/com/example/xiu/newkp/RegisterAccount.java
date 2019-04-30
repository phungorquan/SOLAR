package com.example.xiu.newkp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class RegisterAccount extends AppCompatActivity {
    TextView tvReg;
    EditText edtID,edtPASS,edtPASSCON;
    String urlpostdata = "http:192.168.0.113:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtID = (EditText) findViewById(R.id.edtID);
        edtPASS = (EditText)findViewById(R.id.edtPASS);
        edtPASSCON = (EditText)findViewById(R.id.edtPASSCON);
        tvReg = (TextView) findViewById(R.id.txvReg);
        tvReg.setText("ĐĂNG KÝ");

        final Global g = (Global)getApplication();



        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtID.getText().toString().trim();
                String pass = edtPASS.getText().toString().trim();
                String passconfirm = edtPASSCON.getText().toString().trim();

                if(g.CheckWIFI(RegisterAccount.this) == true)
                {
                    if (id.isEmpty() || pass.isEmpty()) {
                        Toast.makeText(RegisterAccount.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (pass.equals(passconfirm))
                            Register(urlpostdata);
                        else
                            Toast.makeText(RegisterAccount.this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }



    public void Register (String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("2")) {
                            Toast.makeText(RegisterAccount.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterAccount.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else if(response.trim().equals("1"))
                        {
                            Toast.makeText(RegisterAccount.this,"Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            //Log.d("AAA","ERROR\n" + response.toString());
                        }

                        else if(response.trim().equals("-1"))
                        {
                            Toast.makeText(RegisterAccount.this,"Hệ thống đang bận, vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if can't connect to database
                        Toast.makeText(RegisterAccount.this,"Hệ thống đang bận, vui lòng thử lại!!!", Toast.LENGTH_SHORT).show();
                        //Log.d("AAA","Lỗi\n" + error.toString());
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ID",edtID.getText().toString().trim());
                params.put("PASS",edtPASS.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
