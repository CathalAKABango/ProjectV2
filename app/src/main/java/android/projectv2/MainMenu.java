package android.projectv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity {

    Button signout, map, createNewField, histroy;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser myFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        signout = (Button)findViewById(R.id.signoutButton);
        map = (Button)findViewById(R.id.mapLaunch);
        createNewField =(Button)findViewById(R.id.CreateField);
        mFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseUser = mFirebaseAuth.getCurrentUser();

//        Toast.makeText(MainMenu.this, ""+ myFirebaseUser, Toast.LENGTH_LONG).show();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                startActivity(new Intent(MainMenu.this, MapActivity.class));
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainMenu.this, WelcomeActivity.class));

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, MapActivity.class));
            }
        });
        createNewField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, CreateNewField.class));
            }
        });
//        histroy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainMenu.this, ViewHistory.class));
//            }
//        });
    }
}
