package android.projectv2;

import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener {

    private GoogleMap googleMap;
    private ArrayList<LatLng> arrayPoints = null;
    //    PolylineOptions polylineOptions;
    private boolean checkClick = false;
    PolygonOptions polygonOptions;
    Polygon polygon;
    private String TAG;
    protected double myLat, myLong;

    Button back, calculate, create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        arrayPoints = new ArrayList<LatLng>();

        SupportMapFragment googleMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap.getMapAsync(this);
        back = (Button)findViewById(R.id.backButton);
        calculate = (Button)findViewById(R.id.CalculateArea);
        create = (Button)findViewById(R.id.CreatWithCordi);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, CreateNewField.class);
                intent.putExtra("arrayPoints", arrayPoints);
                startActivity(intent);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, MainMenu.class));
            }
        });

    }

    public void onMapReady(GoogleMap gmap) {
        googleMap = gmap;

       googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        LatLng mylocation = new LatLng(lat, lng);
        CameraUpdate newLocation = CameraUpdateFactory.newLatLngZoom(mylocation, 17);
        googleMap.setOnMapLongClickListener(this);
        googleMap.animateCamera(newLocation);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);

        calculate = (Button)findViewById(R.id.CalculateArea);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAreaFunction();
            }
        });



    }

    private void calculateAreaFunction() {

        double areamts = SphericalUtil.computeArea(arrayPoints);

        DecimalFormat df = new DecimalFormat("#.##");
        double area = areamts * 0.000247105;
        Toast.makeText(MapActivity.this, "acres " + (df.format(area)), Toast.LENGTH_LONG).show();
    }
    // googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(o);




    protected void onResume(){
        super.onResume();

    }
    public void onMapClick(LatLng point)
    {
        Marker marker = null;

        if (checkClick == false)
        { MarkerOptions markeroptions = new MarkerOptions();

            markeroptions.position(point);
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(point));
            googleMap.addMarker(new MarkerOptions().position(point).draggable(true).rotation(180));
            arrayPoints.add(point);
//            polygon = googleMap.addPolygon(new PolygonOptions().add(point).strokeColor(Color.RED).strokeWidth(4));




        }
    }
    public void onMapLongClick(LatLng point)
    {
//        googleMap.addMarker(new MarkerOptions().position(point).draggable(true));
//        checkClick = false;
        googleMap.clear();
        arrayPoints.clear();
        checkClick = false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(checkClick){

            if(polygon != null){
                polygon.remove();
                polygon = null;
            }

            polygonOptions.add(marker.getPosition());
            polygonOptions.strokeColor(Color.RED);
            polygonOptions.strokeWidth(2);
            polygonOptions.fillColor(0x5500ff00);
            polygon = googleMap.addPolygon(polygonOptions);
        }else{
            if(polygon != null){
                polygon.remove();
                polygon = null;
            }


            polygonOptions = new PolygonOptions().add(marker.getPosition());
            Log.i(TAG, "computeArea " + arrayPoints);
//            Toast.makeText(MainActivity.this, " " + arrayPoints, Toast.LENGTH_LONG).show();


            checkClick = true;
        }

        return true;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {


    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng dragPosition = marker.getPosition();

    }
}
