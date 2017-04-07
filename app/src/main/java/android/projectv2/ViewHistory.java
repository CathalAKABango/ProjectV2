package android.projectv2;

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
    Button search;
    EditText location, searchquery, crop, spray, dateplanted, datesparayed, comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        search = (Button)findViewById(R.id.Viewbutton);
        location = (EditText)findViewById(R.id.location);
        searchquery = (EditText)findViewById(R.id.search);
        crop = (EditText)findViewById(R.id.Crop);
        spray = (EditText)findViewById(R.id.spray);
        dateplanted = (EditText)findViewById(R.id.dateCropPlanted);
        datesparayed = (EditText)findViewById(R.id.dateofspraying);
        comments = (EditText)findViewById(R.id.Comments);

        mFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseUser = mFirebaseAuth.getCurrentUser().getDisplayName();

        String administrator = myFirebaseUser.toString();
//
//        if(myFirebaseUser.equals("admin")){
//            search.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
//                    final String query = searchquery.getText().toString();
//                    DatabaseReference fileds = myRef1.child("users");
//                    Query filedQuery = fileds.orderByKey().startAt(query).endAt(query + "\uf8ff");
//                    filedQuery.addChildEventListener(new ChildEventListener()  {
//
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            Map<String, Object> pulldown = (Map<String, Object>) dataSnapshot.getValue();
//                            String crp = pulldown.get("Crop").toString();
//                            crop.setText(crp);
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            });
//
//
//        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String year = crop.getText().toString();
                    String uid = mFirebaseAuth.getCurrentUser().getDisplayName();
                    DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();

                    final String query = searchquery.getText().toString();
                    DatabaseReference fileds = myRef1.child("users").child(uid).child(query);
                    Query filedQuery = fileds.orderByKey().startAt(year).endAt(year + "\uf8ff");
                    filedQuery.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Map<String, Object> pulldown = (Map<String, Object>) dataSnapshot.getValue();
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
                }catch (Exception e){
                    Toast.makeText(ViewHistory.this, "No Records Where Found", Toast.LENGTH_SHORT).show();
                }

//                filedQuery.addChildEventListener(new ChildEventListener()  {
//
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        Map<String, Object> pulldown = (Map<String, Object>) dataSnapshot.getValue();
//                        String crp = pulldown.get("Crop").toString();
//                        String spry = pulldown.get("Spray applied").toString();
//                        String commen = pulldown.get("Comments").toString();
//                        String dasparayed = pulldown.get("Date Of Spraying").toString();
//                        String daplanted = pulldown.get("Date Plented").toString();
//                        String locatio = pulldown.get("Location").toString();
//                        crop.setText(crp);
//                        spray.setText(spry);
//                        dateplanted.setText(daplanted);
//                        datesparayed.setText(dasparayed);
//                        comments.setText(commen);
//                        location.setText(locatio);
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
    }
}
