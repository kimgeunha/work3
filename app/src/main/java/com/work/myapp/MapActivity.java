package com.work.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
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
import java.util.List;

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
    View.OnClickListener cl;
    Button btn;

    /*List<String> list = new ArrayList<String>(){
        {
            add( 1,"37.446656133395585,127.16731194692203");
            add( 2,"37.44637288569847, 127.16587786696184");
            add( 3,"37.44906448332953, 127.16491763612774");
            add( 4,"37.44935833859561, 127.16639285095282");
            add( 5,"37.44668961248583, 127.16731371115651");
        }
    };*/

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
        btn = (Button)findViewById(R.id.btn);

        //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);




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
        cadapter = new CustomApdater(arrayList,this);
        recyclerView.setAdapter(cadapter);
        cadapter.setOnItemClickListener(new CustomApdater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), position+"번 지도입니다.", Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    MapPolyline polyline1 = new MapPolyline();
                    polyline1.setTag(1000);
                    polyline1.setLineColor(Color.argb(255, 255, 0, 255)); // Polyline 컬러 지정.
                    polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.446656133395585, 127.16731194692203));
                    polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.44637288569847, 127.16587786696184));
                    polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.44906448332953, 127.16491763612774));
                    polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.44935833859561, 127.16639285095282));
                    polyline1.addPoint(MapPoint.mapPointWithGeoCoord(37.44668961248583, 127.16731371115651));
                    mapView.addPolyline(polyline1);
                    MapPointBounds mapPointBounds = new MapPointBounds(polyline1.getMapPoints());
                    int padding = 100; // px
                    mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
                } else if (position == 1) {
                    MapPolyline polyline2 = new MapPolyline();
                    polyline2.setTag(1000);
                    polyline2.setLineColor(Color.argb(255, 255, 0, 255)); // Polyline 컬러 지정.
                    polyline2.addPoint(MapPoint.mapPointWithGeoCoord(37.445178365779945, 127.16836444966167));
                    polyline2.addPoint(MapPoint.mapPointWithGeoCoord(37.44573953272457, 127.16937969947553));
                    polyline2.addPoint(MapPoint.mapPointWithGeoCoord(37.44614765149512, 127.17075478466643));
                    polyline2.addPoint(MapPoint.mapPointWithGeoCoord(37.44612724560947, 127.1723354900728));
                    mapView.addPolyline(polyline2);
                    MapPointBounds mapPointBounds = new MapPointBounds(polyline2.getMapPoints());
                    int padding = 100; // px
                    mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

                }else if (position == 2) {
                    MapPolyline polyline3 = new MapPolyline();
                    polyline3.addPoint(MapPoint.mapPointWithGeoCoord(37.44237246786802, 127.16878854138004));
                    polyline3.addPoint(MapPoint.mapPointWithGeoCoord(37.44262755383964, 127.16916122802057));
                    polyline3.addPoint(MapPoint.mapPointWithGeoCoord(37.444015206292285, 127.17354350886261));
                    polyline3.addPoint(MapPoint.mapPointWithGeoCoord(37.443964190113675, 127.17461016373032));
                    polyline3.addPoint(MapPoint.mapPointWithGeoCoord(37.44432130263341, 127.17547119838255));
                    polyline3.addPoint(MapPoint.mapPointWithGeoCoord(37.4449743039758, 127.17854265035103));
                    polyline3.setTag(1000);
                    polyline3.setLineColor(Color.argb(255, 255, 0, 255)); // Polyline 컬러 지정.
                    mapView.addPolyline(polyline3);
                    MapPointBounds mapPointBounds = new MapPointBounds(polyline3.getMapPoints());
                    int padding = 100; // px
                    mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

                }

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mapView.removeAllPolylines();



                    }
                });
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






