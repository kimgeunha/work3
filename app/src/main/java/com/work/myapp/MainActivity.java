package com.work.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.POIItemEventListener {

    MapView mapView;
    RelativeLayout mapViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapViewContainer = (RelativeLayout) findViewById(R.id.map_view);
        mapView = new MapView(this);
        mapView.setPOIItemEventListener(this);
        mapViewContainer.addView(mapView);

        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(255, 255, 255, 255)); // Polyline 컬러 지정.

// Polyline 좌표 지정.

        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.545024,127.03923));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.527896,127.036245));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.537229, 127.005515));
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(37.541889,127.095388));

// Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

// 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));


    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

        MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
        MapPoint currentMapPoint = MapPoint.mapPointWithGeoCoord(mapPointGeo.latitude, mapPointGeo.longitude);
        mapView.setMapCenterPoint(currentMapPoint, true);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }
}