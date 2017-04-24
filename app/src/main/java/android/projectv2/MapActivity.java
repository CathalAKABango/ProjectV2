package android.projectv2;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener  {

    private GoogleMap MyMap;
    private ArrayList<LatLng> arrayPoints = null;
    //    PolylineOptions polylineOptions;
    private boolean checkClick = false;
    PolygonOptions polygonOptions;
    Polygon polygon;
    private String TAG;
    protected double myLat, myLong;

    Button back, calculate, create, maptype;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        arrayPoints = new ArrayList<LatLng>();

        SupportMapFragment googleMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap.getMapAsync(this);
        back = (Button)findViewById(R.id.backButton);
        calculate = (Button)findViewById(R.id.CalculateArea);
        maptype = (Button)findViewById(R.id.button);
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
                startActivity(new Intent(MapActivity.this, HomeMenu.class));
            }
        });
        maptype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapTypeSelectorDialog();
            }
        });

    }
    private static final CharSequence[] MAP_TYPE_ITEMS =
            {"Road Map", "Satellite", "Terrain", "Hybrid"};

    private void showMapTypeSelectorDialog() {
        // Prepare the dialog by setting up a Builder.
        final String fDialogTitle = "Select Map Type";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(fDialogTitle);

        // Find the current map type to pre-check the item representing the current state.
        int checkItem = MyMap.getMapType() - 1;

        // Add an OnClickListener to the dialog, so that the selection will be handled.
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEMS,
                checkItem,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        // Locally create a finalised object.

                        // Perform an action depending on which item was selected.
                        switch (item) {
                            case 1:
                                MyMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                MyMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                            case 3:
                                MyMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            default:
                                MyMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                        dialog.dismiss();
                    }
                }
        );

        // Build the dialog and show it.
        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }

    private void calculateAreaFunction() {

        double areamts = SphericalUtil.computeArea(arrayPoints);

        DecimalFormat df = new DecimalFormat("#.##");
        double area = areamts * 0.000247105;
        Toast.makeText(MapActivity.this, "acres " + (df.format(area)), Toast.LENGTH_LONG).show();
    }

    public void onMapReady(GoogleMap gmap) {
        this.MyMap = gmap;


        MyMap.setMyLocationEnabled(true);
        MyMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        double lng = 0;
        double lat = 0;

        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(provider);
            lat = location.getLatitude();
            lng = location.getLongitude();
            LatLng mylocation = new LatLng(lat, lng);
            CameraUpdate newLocation = CameraUpdateFactory.newLatLngZoom(mylocation, 17);
            MyMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
            MyMap.moveCamera(CameraUpdateFactory.zoomTo(10));
//        Toast.makeText(MapActivity.this, "Zoom", Toast.LENGTH_SHORT).show();
            MyMap.setOnMapLongClickListener(this);
            MyMap.animateCamera(newLocation);
            MyMap.setOnMapClickListener(this);
            MyMap.setOnMarkerClickListener(this);
            MyMap.setOnMarkerDragListener(this);
        }catch(Exception e){

        }
        LatLng mylocation = new LatLng(lat, lng);
        CameraUpdate newLocation = CameraUpdateFactory.newLatLngZoom(mylocation, 17);
        MyMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        MyMap.moveCamera(CameraUpdateFactory.zoomTo(10));
//        Toast.makeText(MapActivity.this, "Zoom", Toast.LENGTH_SHORT).show();
        MyMap.setOnMapLongClickListener(this);
        MyMap.animateCamera(newLocation);
        MyMap.setOnMapClickListener(this);
        MyMap.setOnMarkerClickListener(this);
        MyMap.setOnMarkerDragListener(this);



        calculate = (Button)findViewById(R.id.CalculateArea);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAreaFunction();
            }
        });
    }

    protected void onResume(){
        super.onResume();

    }
    public void onMapClick(LatLng point)
    {
        Marker marker = null;

        if (checkClick == false)
        { MarkerOptions markeroptions = new MarkerOptions();

            markeroptions.position(point);
            MyMap.addMarker(new MarkerOptions().position(point).draggable(true).rotation(180));
            arrayPoints.add(point);
        }
    }
    public void onMapLongClick(LatLng point)
    {
        MyMap.clear();
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
            polygon = MyMap.addPolygon(polygonOptions);
        }else{
            if(polygon != null){
                polygon.remove();
                polygon = null;
            }


            polygonOptions = new PolygonOptions().add(marker.getPosition());
            Log.i(TAG, "computeArea " + arrayPoints);
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
