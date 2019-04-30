package com.example.xiu.newkp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CreateNode extends AppCompatActivity {

    EditText edtNameNode,edtAddr,edtInfo;
    TextView creNl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_node);

        getSupportActionBar().setTitle("Create Node");// Hàm hỗ trợ hiển thị tên ở góc trái màn hình và nút Back ( Nút back cần được set sẽ Back về đâu trong file Manifest)

        // Các thủ tực ánh xạ bên Layout
        edtNameNode = (EditText) findViewById(R.id.edtNameNode);
        edtAddr = (EditText) findViewById(R.id.edtLocation);
        edtInfo = (EditText) findViewById(R.id.edtInfo);

        creNl = (TextView) findViewById(R.id.txvCreNL);

        creNl.setText("Tạo");

        // Hàm này chưa có gì cả ,chưa có chức năng này


        creNl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global g = (Global)getApplication();

                String Namenode = edtNameNode.getText().toString().trim();
                String Addr = edtAddr.getText().toString().trim();
                String Info = edtInfo.getText().toString().trim();

                if(g.CheckWIFI(CreateNode.this) == true)
                {
                    if (Namenode.isEmpty() || Addr.isEmpty() || Info.isEmpty()) {
                        Toast.makeText(CreateNode.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {

                            Toast.makeText(CreateNode.this, "Thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateNode.this,Node_List.class);
                         startActivity(intent);
                    }
                }

            }
        });

    }
}
