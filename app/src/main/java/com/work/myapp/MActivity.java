package com.work.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class MActivity extends AppCompatActivity {
    public static final int sub = 1002;
    EditText ed1,ed2;
    Button btn1,btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);
        Button btn1 = (Button)findViewById(R.id.search1);
        Button btn2 = (Button)findViewById(R.id.search2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra("거리",ed1.getText());
                startActivityForResult(intent,sub);
            }
        });

    }
}