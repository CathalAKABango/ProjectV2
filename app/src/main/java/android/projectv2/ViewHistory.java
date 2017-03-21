package android.projectv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewHistory extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private String myFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    Button search;
    EditText location, searchquery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        search = (Button)findViewById(R.id.Viewbutton);
        location = (EditText)findViewById(R.id.location);
        searchquery = (EditText)findViewById(R.id.search);

        mFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseUser = mFirebaseAuth.getCurrentUser().getEmail();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = mFirebaseAuth.getCurrentUser().getUid();
                Toast.makeText(ViewHistory.this, "" + uid, Toast.LENGTH_SHORT).show();
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
                String query = searchquery.getText().toString();
//                String query = "big one";
                DatabaseReference fileds = myRef1.child("users").child(uid);
                Query filedQuery = fileds.orderByKey().startAt(query).endAt(query + "\uf8ff");
                filedQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> fileds = new ArrayList<String>();

                        for (DataSnapshot postSanpShot : dataSnapshot.getChildren()) {
                            fileds.add(postSanpShot.getValue().toString());
                        }
                        location.setText(fileds.toString());
                        Toast.makeText(ViewHistory.this, "" + fileds.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
