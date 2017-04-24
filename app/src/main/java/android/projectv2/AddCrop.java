package android.projectv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddCrop extends AppCompatActivity {
    private DatabaseReference mFirebaseDatabase;
    private String myFirebaseUser;
    private String queryword = "";
    private FirebaseAuth mFirebaseAuth;
    Button search, save,home;
    String crp, spry, commen,dasparayed,daplanted,locatio,yeardown;
    EditText fieldnamein, yearin, commentsin, dateplan, croptoadd;
    Boolean canUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop2);

        search = (Button)findViewById(R.id.searchforcropwalk);
        save = (Button)findViewById(R.id.SavenewCrop);
        home = (Button)findViewById(R.id.BacktoHome);
        fieldnamein = (EditText)findViewById(R.id.CropWalkField);
        yearin = (EditText)findViewById(R.id.CropWalkYear);
        dateplan =(EditText)findViewById(R.id.DatePlantingNewCrop);
        croptoadd = (EditText)findViewById(R.id.cropadded);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyy", Locale.getDefault());
        String date = dateFormat.format(Calendar.getInstance().getTime());
        dateplan.setText(date);

        mFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseUser = mFirebaseAuth.getCurrentUser().getDisplayName();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String year = yearin.getText().toString();
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
                String name = mFirebaseAuth.getCurrentUser().getDisplayName();

                final String fieldinsearch = fieldnamein.getText().toString();
                queryword = fieldinsearch+year;

                DatabaseReference fileds = myRef1.child("users").child(name);
                final Query filedQuery = fileds.orderByKey().startAt(queryword).endAt(queryword + "\uf8ff");

                filedQuery.addChildEventListener(new ChildEventListener()  {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> pulldown = (Map<String, Object>) dataSnapshot.getValue();
                        if (pulldown.containsValue(year)){
                            canUpdate = true;
                            crp = pulldown.get("Crop").toString();
                            spry = pulldown.get("Spray applied").toString();
                            commen = pulldown.get("Comments").toString();
                            dasparayed = pulldown.get("Date Of Spraying").toString();
                            daplanted = pulldown.get("Date Plented").toString();
                            locatio = pulldown.get("Location").toString();
                            yeardown = pulldown.get("Year").toString();
                            Toast.makeText(AddCrop.this,  "Field Found Add Crop", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AddCrop.this, "Sorry no results for that year", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        mFirebaseAuth = FirebaseAuth.getInstance();

            if (canUpdate = true) {
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
                        String username = mFirebaseAuth.getCurrentUser().getDisplayName();
                        final HashMap<String, String> map1 = new HashMap<>();
                        map1.put("Crop", croptoadd.getText().toString() + crp);
                        map1.put("Spray applied", spry);
                        map1.put("Date Of Spraying", dasparayed);
                        map1.put("Date Plented", dateplan.getText().toString() + daplanted);
                        map1.put("Location", locatio);
                        map1.put("Comments", commen);
                        map1.put("Year", yeardown);
                        mFirebaseDatabase.child("users").child(username).child(queryword).setValue(map1);
                        Toast.makeText(AddCrop.this, "Records have been updated", Toast.LENGTH_SHORT).show();

                    }
                });
            } else {
                Toast.makeText(AddCrop.this, "Sorry YOu need TO search for field first", Toast.LENGTH_SHORT).show();
            }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCrop.this, HomeMenu.class));
            }
        });
    }
}
