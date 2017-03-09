package android.projectv2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CreateNewField extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseUser myFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    Button save, view;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_field);

        save = (Button)findViewById(R.id.Savebutton);
        view = (Button)findViewById(R.id.Viewbutton);
        output = (TextView)findViewById(R.id.Comments);
//        myFirebaseUser = mFirebaseAuth.getCurrentUser();



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("myFirebaseUser");
                myRef.setValue("Hello World");


            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                //DatabaseReference ref = database.getReference("myFirebaseUser").get;
                Query ref = database.getReference().orderByChild("myFirebaseUser");
                String retString;
                retString = ref.equalTo("myFirebaseUser").toString();
                output.setText(retString);

            }
        });
    }
}