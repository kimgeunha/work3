package com.work.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends AppCompatActivity {
    private static final String TAG = "19994";
    MapView mapView;
    RelativeLayout mapViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapViewContainer = (RelativeLayout) findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
        mapViewContainer.addView(mapView);
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(255, 255, 0, 255)); // Polyline 컬러 지정.

        Bundle extras = getIntent().getExtras();
        int i = extras.getInt("거리");
//        switch (getInt()){
//            case
//        }


        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44677049061589, 127.16760515299688));

        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44677049061589, 128.16760515299688));


        // Polyline 지도에 올리기.
        mapView.addPolyline(polyline);
        MapPOIItem marker = new MapPOIItem();

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.44601, 127.17393);
        marker.setItemName("황송공원");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);
        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

    }
}