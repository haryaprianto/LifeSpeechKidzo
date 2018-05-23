package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //sidebar start
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    ArrayList<Category>categoriesList;
    ListView lvCategory;


    //sidebar end
    int id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCategory = (ListView)findViewById(R.id.listViewCategory);
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
        retrieve();

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View lView, final int pos, long id) {
                Intent intent = new Intent(MainActivity.this, showSubsCat.class);
                Category selectedCategory = categoriesList.get(pos);
                int categoryId = selectedCategory.getId();
                String strCategoryId = String.valueOf(categoryId);
                intent.putExtra("catId", strCategoryId);
                startActivity(intent);
            }
        });


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
            Intent mainActivityIntent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_Login) {

            Intent mainActivityIntent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(mainActivityIntent);
        } else if (id == R.id.nav_CreateAccount) {
            Intent mainActivityIntent = new Intent(MainActivity.this, RegisterPage.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_contact) {

            Intent mainActivityIntent = new Intent(MainActivity.this, ContactPage.class);
            startActivity(mainActivityIntent);


        } else if (id == R.id.nav_about) {

            Intent rpIntent = new Intent(Intent.ACTION_VIEW);
            // Set the URL to be used.
            rpIntent.setData(Uri.parse("https://lifespeech.org/about-us/"));
            startActivity(rpIntent);
        }
        return false;
    }
    private void retrieve() {

            String url = "https://fypdmsd.000webhostapp.com/retrieveCategoriesAndroid.php";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {

                @Override
                public void onFailure(Throwable arg0, String arg1) {
                    Toast.makeText(getApplicationContext(),
                            "Please connect to internet and try again",
                            Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFinish() {

                    super.onFinish();
                }
                @Override
                public void onStart() {

                    super.onStart();
                }
                @Override
                public void onSuccess(String response) {
                    try {
                        Category(CategoryObjectJSON(new String(response)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void Category(ArrayList<String> cat){
        CategoryAdapter aa = new CategoryAdapter(this,R.layout.categor_view,categoriesList);
        lvCategory.setAdapter(aa);
    }

    public ArrayList<String> CategoryObjectJSON(String response){
        ArrayList<String>CategoryNameList = new ArrayList<String>();
        categoriesList = new ArrayList<Category>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String categoryImageUrl ;
            String category;
            int categoryId ;
            for (int i= 0;i<jsonArray.length();i++){
                category = jsonArray.getJSONObject(i).getString("category_name");
                categoryId = jsonArray.getJSONObject(i).getInt("category_id");
                categoryImageUrl = jsonArray.getJSONObject(i).getString("image");
                CategoryNameList.add(category);
                categoriesList.add(new Category(category,categoryId,categoryImageUrl));
                Log.i("info", String.valueOf(category));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CategoryNameList;
    }
}






