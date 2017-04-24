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

import java.util.Map;


public class ViewHistory extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private String myFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    Button search, homebtton;
    EditText location, searchquery, crop, spray, dateplanted, datesparayed, comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        search = (Button)findViewById(R.id.Viewbutton);
        homebtton = (Button)findViewById(R.id.HomeBtn2);
        location = (EditText)findViewById(R.id.location);
        searchquery = (EditText)findViewById(R.id.search);
        crop = (EditText)findViewById(R.id.Crop);
        spray = (EditText)findViewById(R.id.spray);
        dateplanted = (EditText)findViewById(R.id.dateCropPlanted);
        datesparayed = (EditText)findViewById(R.id.dateofspraying);
        comments = (EditText)findViewById(R.id.Comments);

        final String commentsin =  (String)getIntent().getSerializableExtra("details");
        try{
            comments.setText(commentsin.toString());
            Toast.makeText(ViewHistory.this, "Please read your comments for details of the crop walk", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
        comments.setText("no comments left from crop walk");
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseUser = mFirebaseAuth.getCurrentUser().getDisplayName();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String year = crop.getText().toString();
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
                String name = mFirebaseAuth.getCurrentUser().getDisplayName();
                final String queryword = searchquery.getText().toString();
                DatabaseReference fileds = myRef1.child("users").child(name);
                final Query filedQuery = fileds.orderByKey().startAt(queryword).endAt(queryword + "\uf8ff");

                filedQuery.addChildEventListener(new ChildEventListener()  {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> pulldown = (Map<String, Object>) dataSnapshot.getValue();
                        if (pulldown.containsValue(year)){
                        String crp = pulldown.get("Crop").toString();
                        String spry = pulldown.get("Spray applied").toString();
                        String commen = pulldown.get("Comments").toString();
                        String dasparayed = pulldown.get("Date Of Spraying").toString();
                        String daplanted = pulldown.get("Date Plented").toString();
                        String locatio = pulldown.get("Location").toString();
                        crop.setText(crp);
                        spray.setText(spry);
                        dateplanted.setText(daplanted);
                        datesparayed.setText(dasparayed);
                        comments.setText(commen);
                        location.setText(locatio);
                        Toast.makeText(ViewHistory.this, "finally here", Toast.LENGTH_SHORT).show();}
                        else {
                            Toast.makeText(ViewHistory.this, "Sorry no results for that year", Toast.LENGTH_SHORT).show();
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
        homebtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewHistory.this, HomeMenu.class));
            }
        });
    }
}