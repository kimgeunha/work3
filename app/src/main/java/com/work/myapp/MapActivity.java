package com.work.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MapActivity extends AppCompatActivity{
    private static final String LOG_TAG = "MainActivity";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int ACCESS_FINE_LOCATION = 1000;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    MapView mapView;
    ImageButton home;
    RelativeLayout mapViewContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<locationdata> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private CustomApdater cadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapViewContainer = (RelativeLayout) findViewById(R.id.map_view);
        recyclerView = findViewById(R.id.rec);
        recyclerView.setHasFixedSize(true);
        home = (ImageButton) findViewById(R.id.home_button);
        mapView = new MapView(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("locationdata");
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529), true);
        mapViewContainer.addView(mapView);

        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(255, 255, 0, 255)); // Polyline 컬러 지정.

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터 받아오는곳
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    locationdata loc = dataSnapshot.getValue(locationdata.class);
                    arrayList.add(loc);
                }
                cadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cadapter = new CustomApdater(arrayList);
        recyclerView.setAdapter(cadapter);
        cadapter.setOnItemClickListener(new CustomApdater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int pos = position;
                Toast.makeText(getApplicationContext(), position+"번 지도입니다.", Toast.LENGTH_SHORT).show();
                switch (pos){
                    case 0:
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                        mapView.addPolyline(polyline);
                    case 1:
                        mapView.removePolyline(polyline);
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893554356, 127.16783719768529));
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893534356, 127.16783719168529));
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893574356, 127.16783719268529));
                        mapView.addPolyline(polyline);

                }


            }
        });

        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.446695087647534, 127.16732221361669);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재위치");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);



    }


    /*private void permissioncheck(){
        SharedPreferences prefer = getPreferences(MODE_PRIVATE);
        boolean firstcheck = prefer.getBoolean("FirstPermissoncheck",true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Object dialog;
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 권한 거절 (다시 한 번 물어봄)
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.");
                builder.setPositiveButton("확인") { dialog, which ->
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (firstcheck) {
                    // 최초 권한 요청
                    prefer.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") {
                        AtomicReference<Intent> intent = null;
                        dialog, which ->
                                intent.set(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")))
                        startActivity(intent.get());
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    };
                    builder.show();
                }
            }
        } else {
            // 권한이 있는 상태
            startTracking()
        }
    }

    private String[] arrayOf(String accessFineLocation) {
    }*/
}






