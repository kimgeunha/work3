package com.work.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity{
    private static final String LOG_TAG = "MainActivity";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    MapView mapView;
    ImageButton home;
    RelativeLayout mapViewContainer;
    View.OnClickListener cl;
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
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
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
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));

        cadapter = new CustomApdater(arrayList);
        recyclerView.setAdapter(cadapter);
        cadapter.setOnItemClickListener(new CustomApdater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), position+"번 지도입니다.", Toast.LENGTH_SHORT).show();
                if(position == 0){
                    polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                    polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                    polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                    polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44893524356, 127.16783719768529));
                    mapView.addPolyline(polyline);


                }else if(position == 1){
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


}




