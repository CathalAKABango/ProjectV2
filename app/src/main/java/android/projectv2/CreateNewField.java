package android.projectv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CreateNewField extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private String myFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    Button save, Back;
    TextView output;
    EditText name, crop, datePlanted, dateSpray, sparyApplied, location, year1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_field);

        name = (EditText)findViewById(R.id.Fieldname);
        crop =(EditText)findViewById(R.id.Crop);
        datePlanted =(EditText)findViewById(R.id.dateCropPlanted);
        dateSpray =(EditText)findViewById(R.id.dateofspraying);
        sparyApplied =(EditText)findViewById(R.id.spray);
        save = (Button)findViewById(R.id.Savebutton);
        Back = (Button)findViewById(R.id.Viewbutton);
        output = (TextView)findViewById(R.id.Comments);
        location = (EditText) findViewById(R.id.area);
        year1 = (EditText) findViewById(R.id.Year);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault());
        String date = dateFormat.format(Calendar.getInstance().getTime());
        datePlanted.setText(date);
        dateSpray.setText(date);

        final ArrayList<LatLng> cordinates =  (ArrayList<LatLng>)getIntent().getSerializableExtra("arrayPoints");
        try{
            location.setText(cordinates.toString());
        }
        catch (Exception e){
            location.setText("No Location to fill");
        }
        mFirebaseAuth = FirebaseAuth.getInstance();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
                String username = mFirebaseAuth.getCurrentUser().getDisplayName();
                final HashMap<String, String> map1 = new HashMap<>();
                map1.put("Crop",  crop.getText().toString());
                map1.put("Spray applied", sparyApplied.getText().toString());
                map1.put("Date Of Spraying", dateSpray.getText().toString());
                map1.put("Date Plented", datePlanted.getText().toString());
                map1.put("Location", location.getText().toString());
                map1.put("Comments", output.getText().toString());
                map1.put("Year", year1.getText().toString());
                mFirebaseDatabase.child("users").child(username).child(name.getText().toString()+year1.getText().toString()).setValue(map1);

            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNewField.this, HomeMenu.class));
            }
        });
    }


}
