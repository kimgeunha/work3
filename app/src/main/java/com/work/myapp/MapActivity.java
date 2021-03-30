package com.work.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

public class MapActivity extends AppCompatActivity {
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

        // Polyline 좌표 지정.


        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44677049061589, 127.16760515299688));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.4456891874656, 127.16765769072074));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.443742161258754, 127.16937249763474));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44229283818044, 127.16863494627388));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.443932474325216, 127.17435096932051));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44394711377182, 127.1745906735128));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44444485325191, 127.17588138839429));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44497186791587, 127.17885003262171));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44072431719216, 127.17513024928122));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.43960198924165, 127.17239153209239));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44300399401924, 127.17035958062968));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.442512992571245, 127.16876935774583));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.443775561213506, 127.16938777775623));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44517839027332, 127.16823928345121));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44665133247796, 127.16744417200928));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.44677049061589, 127.16760515299688));



        // Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));

    }
}