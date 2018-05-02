package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class RegisterPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //sidebar start
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    //sidebar end
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_register);

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
        if (id == R.id.nav_home){
            Intent mainActivityIntent = new Intent(RegisterPage.this, MainActivity.class);
            startActivity(mainActivityIntent);

        }else if(id == R.id.nav_Login) {

            Intent mainActivityIntent = new Intent(RegisterPage.this, LoginPage.class);
            startActivity(mainActivityIntent);
        }

        else if (id == R.id.nav_CreateAccount){
            Intent mainActivityIntent = new Intent(RegisterPage.this, RegisterPage.class);
            startActivity(mainActivityIntent);


        }
        else if (id == R.id.nav_contact){

            Intent mainActivityIntent = new Intent(RegisterPage.this, ContactPage.class);
            startActivity(mainActivityIntent);

        }else if(id == R.id.nav_about){

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }
        return false;
    }

    //sidebar end
}
