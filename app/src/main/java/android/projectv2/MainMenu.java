package android.projectv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainMenu extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_nav_drwaer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.creaeField) {
            startActivity(new Intent(MainMenu.this, CreateNewField.class));
        } else if (id == R.id.createwithmap) {
            startActivity(new Intent(MainMenu.this, MapActivity.class));
        } else if (id == R.id.adcrop) {


        } else if (id == R.id.addspray) {

        } else if (id == R.id.addcropwalk) {
            startActivity(new Intent(MainMenu.this, CropWalk.class));
        } else if (id == R.id.search) {
            startActivity(new Intent(MainMenu.this, ViewHistory.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

//        Toast.makeText(MainMenu.this, ""+ myFirebaseUser, Toast.LENGTH_LONG).show();

//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                startActivity(new Intent(MainMenu.this, MapActivity.class));
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(MainMenu.this, WelcomeActivity.class));
//
//            }
//        });
//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainMenu.this, MapActivity.class));
//            }
//        });
//        createNewField.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainMenu.this, CreateNewField.class));
//                String tkn = FirebaseInstanceId.getInstance().getToken();
//                Toast.makeText(MainMenu.this, "Current token ["+tkn+"]",
//                        Toast.LENGTH_LONG).show();
//                System.out.println("Refreshed token: " + tkn);
//
//            }
//        });
//        histroy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainMenu.this, ViewHistory.class));
//            }
//        });
//        maphistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainMenu.this, HomeMenu.class));
//            }
//        });