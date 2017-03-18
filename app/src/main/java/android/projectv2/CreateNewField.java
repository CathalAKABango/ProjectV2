package android.projectv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateNewField extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private String myFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    Button save, view;
    TextView output;
    EditText name, crop, datePlanted, dateSpray, sparyApplied;


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

        mFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseUser = mFirebaseAuth.getCurrentUser().getEmail();



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
                String uid = mFirebaseAuth.getCurrentUser().getUid();
                final HashMap<String, String> map1 = new HashMap<>();
                map1.put("Field Name", name.getText().toString());
                map1.put("Crop",  crop.getText().toString());
                map1.put("Spray applied", sparyApplied.getText().toString());
                map1.put("Date Of Spraying", dateSpray.getText().toString());
                map1.put("Date Plented", datePlanted.getText().toString());
                mFirebaseDatabase.child("users").child(uid).push().setValue(map1);



            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = mFirebaseAuth.getCurrentUser().getUid();
                Toast.makeText(CreateNewField.this, ""+uid, Toast.LENGTH_SHORT).show();
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();

                myRef1.child("users").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        output.setText(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        output.setText(databaseError.toString());
                    }
                });

        }
        });




    }

}
