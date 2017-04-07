package android.projectv2;

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

import java.util.ArrayList;
import java.util.HashMap;

public class CreateNewField extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private String myFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    Button save, view;
    TextView output;
    EditText name, crop, datePlanted, dateSpray, sparyApplied, location, year;


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
        view = (Button)findViewById(R.id.Viewbutton);
        output = (TextView)findViewById(R.id.Comments);
        location = (EditText) findViewById(R.id.area);
        year = (EditText) findViewById(R.id.Year);
        final ArrayList<LatLng> cordinates =  (ArrayList<LatLng>)getIntent().getSerializableExtra("arrayPoints");
        try{
            location.setText(cordinates.toString());
        }
        catch (Exception e){
            location.setText("No Location to fill");
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
//        myFirebaseUser = mFirebaseAuth.getCurrentUser().getDisplayName();



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
                mFirebaseDatabase.child("users").child(username).child(name.getText().toString()).child(year.getText().toString()).setValue(map1);



            }
        });
    }

}
