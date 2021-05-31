package com.work.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton main1, main2, main3, main4;
    View.OnClickListener cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_main);

        main1 = (ImageButton) findViewById(R.id.mainicon_1);
        main2 = (ImageButton) findViewById(R.id.mainicon_2);
        main3 = (ImageButton) findViewById(R.id.mainicon_3);
        main4 = (ImageButton) findViewById(R.id.mainicon_4);

        String url = "daummaps://route? sp="+37.455855+","+126.637208+"&ep="+37.4487734296325+","+127.16770845396576+"&by=FOOT";

        cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ( v.getId() ) {
                    case R.id.mainicon_1: {
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case R.id.mainicon_2: {
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent2);
                    }
                    break;
                    case R.id.mainicon_3: {
                        Toast.makeText(getApplicationContext(), "미구현 기능입니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case R.id.mainicon_4: {
                        Toast.makeText(getApplicationContext(), "미구현 기능입니다", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        };
        main1.setOnClickListener(cl);
        main2.setOnClickListener(cl);
        main3.setOnClickListener(cl);
        main4.setOnClickListener(cl);
    }
}