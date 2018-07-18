package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {
    //sidebar start
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mToggle;
    //sidebar end
    private ImageView imgtwitter,imgfb;

    //map
    SupportMapFragment mapFragment;
    //end map


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        imgfb =(ImageView)findViewById(R.id.imgfb);
        imgtwitter = (ImageView)findViewById(R.id.imgtwitter);
        imgfb.setImageResource(R.drawable.facebookpng);
        imgtwitter.setImageResource(R.drawable.twitterpng);

        imgfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fbIntent = new Intent(Intent.ACTION_VIEW);
                // Set the URL to be used.
                fbIntent.setData(Uri.parse("https://www.facebook.com/lifespeech"));
                startActivity(fbIntent);
            }
        });
        imgtwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent twitIntent = new Intent(Intent.ACTION_VIEW);
                // Set the URL to be used.
                twitIntent.setData(Uri.parse("https://twitter.com/LifeSpeechSG"));
                startActivity(twitIntent);
            }
        });

        //sidebar start
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemTextColor(null);
        navigationView.setItemTextAppearance(R.style.MenuTextStyle);
        //sidebar end

        //map
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.287988,103.843140), 15));
            }
        });
        //end map
    }



    //map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng( 1.287988,103.843140))
                .title("Life Speech")
                .snippet("20 Havelock Road, #02-59, CENTRAL SQUARE, Singapore 059765"));
//        end map
    }



    @Override
    protected void onResume() {
        super.onResume();
        navigationdrawer();

    }



    //sidebar start
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent mainActivityIntent = new Intent(ContactPage.this, MainActivity.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_Login) {

            Intent mainActivityIntent = new Intent(ContactPage.this, LoginPage.class);
            startActivity(mainActivityIntent);
        } else if (id == R.id.nav_CreateAccount) {
            Intent mainActivityIntent = new Intent(ContactPage.this, RegisterPage.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_contact) {

            Intent mainActivityIntent = new Intent(ContactPage.this, ContactPage.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_about) {

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }else if (id == R.id.nav_accHome) {
            Intent mainActivityIntent = new Intent(ContactPage.this, MainActivity.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accProfile) {
//            Toast.makeText(Acc_ProfilePage.this,"Profile",Toast.LENGTH_LONG).show();
            Intent mainActivityIntent = new Intent(ContactPage.this, Acc_ProfilePage.class);
            startActivity(mainActivityIntent);
        } else if (id == R.id.nav_navGameHistory) {
            Toast.makeText(ContactPage.this,"Game History",Toast.LENGTH_LONG).show();
//            Intent mainActivityIntent = new Intent(Acc_HomePage.this, RegisterPage.class);
//            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accLogout) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ContactPage.this);
            boolean Islogin;
            prefs.edit().putBoolean("Islogin", false).commit();
            prefs.edit().putString("LoginUsername", "").commit();
            prefs.edit().putString("AccountType", "").commit();
            LoginManager.getInstance().logOut();

            //need public static void to use function from another activity
            navigationdrawer();

            Intent mainActivityIntent = new Intent(ContactPage.this, MainActivity.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accContact) {

            Intent mainActivityIntent = new Intent(ContactPage.this, ContactPage.class);
            startActivity(mainActivityIntent);

        } else if (id == R.id.nav_accAbout) {

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }
        return false;
    }

    public void navigationdrawer(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ContactPage.this);
        boolean islogin = prefs.getBoolean("Islogin",false);
        String isfb = prefs.getString("AccountType","");
        Log.i("tttpref",islogin+":"+isfb);
        if(islogin == true){
            if(isfb.equals("normal")){
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.navigation_loginmenu);
            }else{
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.navigation_facebookloginmenu);
            }

        }else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_menu);
        }


    }

    //sidebar end
}
