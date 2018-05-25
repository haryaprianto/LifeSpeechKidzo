package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class Acc_ContactPage extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {

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
        setContentView(R.layout.activity_acc__contact_page);

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






    //sidebar start

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_accHome) {
            Intent mainActivityIntent = new Intent(Acc_ContactPage.this, Acc_HomePage.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accProfile) {
            Toast.makeText(Acc_ContactPage.this,"Profile",Toast.LENGTH_LONG).show();
//            Intent mainActivityIntent = new Intent(Acc_HomePage.this, LoginPage.class);
//            startActivity(mainActivityIntent);
        } else if (id == R.id.nav_navGameHistory) {
            Toast.makeText(Acc_ContactPage.this,"Game History",Toast.LENGTH_LONG).show();
//            Intent mainActivityIntent = new Intent(Acc_HomePage.this, RegisterPage.class);
//            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accLogout) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Acc_ContactPage.this);
            boolean Islogin;
            prefs.edit().putBoolean("Islogin", false).commit();
            Intent mainActivityIntent = new Intent(Acc_ContactPage.this, MainActivity.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_accContact) {

            Intent mainActivityIntent = new Intent(Acc_ContactPage.this, Acc_ContactPage.class);
            startActivity(mainActivityIntent);

        } else if (id == R.id.nav_accAbout) {

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }
        return false;
    }
    //sidebar end
}
