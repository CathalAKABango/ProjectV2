package android.projectv2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.NotificationCompat;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CropWalk extends AppCompatActivity {

    private DatabaseReference mFirebaseDatabase;
    private String myFirebaseUser;
    private String queryword = "";
    private FirebaseAuth mFirebaseAuth;
    Button search, save,btnSpeak, homebtn;
    String crp, spry, commen,dasparayed,daplanted,locatio,yeardown;
    EditText fieldnamein, yearin, commentsin;

    
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_walk);

        search = (Button)findViewById(R.id.searchforcropwalk);
        save = (Button)findViewById(R.id.updateCropWalk);
        fieldnamein = (EditText)findViewById(R.id.CropWalkField);
        yearin = (EditText)findViewById(R.id.CropWalkYear);
        commentsin = (EditText)findViewById(R.id.CropWalkComments);
        btnSpeak = (Button)findViewById(R.id.btnSpeak);
        homebtn = (Button)findViewById(R.id.homebtn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseUser = mFirebaseAuth.getCurrentUser().getDisplayName();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String year = yearin.getText().toString();
                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
                String name = mFirebaseAuth.getCurrentUser().getDisplayName();
                final String fieldnametoquery = fieldnamein.getText().toString();
                queryword = fieldnametoquery+year;
                DatabaseReference fileds = myRef1.child("users").child(name);
                final Query filedQuery = fileds.orderByKey().startAt(queryword).endAt(queryword + "\uf8ff");

                filedQuery.addChildEventListener(new ChildEventListener()  {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Map<String, Object> pulldown = (Map<String, Object>) dataSnapshot.getValue();
                        if (pulldown.containsValue(year)){
                             crp = pulldown.get("Crop").toString();
                             spry = pulldown.get("Spray applied").toString();
                             commen = pulldown.get("Comments").toString();
                             dasparayed = pulldown.get("Date Of Spraying").toString();
                            daplanted = pulldown.get("Date Plented").toString();
                             locatio = pulldown.get("Location").toString();
                            yeardown = pulldown.get("Year").toString();
                            Toast.makeText(CropWalk.this, pulldown+"add comment", Toast.LENGTH_SHORT).show();}
                        else {
                            Toast.makeText(CropWalk.this, "Sorry no results for that year", Toast.LENGTH_SHORT).show();
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
                String username = mFirebaseAuth.getCurrentUser().getDisplayName();
                final HashMap<String, String> map1 = new HashMap<>();
                map1.put("Crop", crp);
                map1.put("Spray applied", spry);
                map1.put("Date Of Spraying", dasparayed);
                map1.put("Date Plented", daplanted);
                map1.put("Location", locatio);
                map1.put("Comments", commentsin.getText().toString());
                map1.put("Year", yeardown);
                mFirebaseDatabase.child("users").child(username).child(queryword).setValue(map1);
                addNotification();


            }
        });
btnSpeak.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        promptSpeechInput();
    }
});
        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CropWalk.this, HomeMenu.class));
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Talk About The Crop");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(CropWalk.this, "Sorry device not supported", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    commentsin.setText(result.get(0)+commen);
                }
                break;
            }

        }

    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.tractor)
                        .setContentTitle("Crop Tracker ")
                        .setContentText("A crop walk has just being completed");

        Intent notificationIntent = new Intent(this, CropWalk.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}