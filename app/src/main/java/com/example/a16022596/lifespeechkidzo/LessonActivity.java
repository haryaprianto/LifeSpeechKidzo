package com.example.a16022596.lifespeechkidzo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity {
    ArrayList<Lesson> lessonList;
    ListView lvLesson;
    Button btnSelectCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        retrieveContent();
        lvLesson = (ListView)findViewById(R.id.listVIewLesson);
        btnSelectCategory = (Button)findViewById(R.id.buttonSelectDiff);


        lvLesson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View lView, final int pos, long id) {
                Lesson selectedLesson = lessonList.get(pos);
                String urlAudio = selectedLesson.getContentAudio();
//                Log.i("Audio",urlAudio);
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(urlAudio);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });

        lvLesson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {

                Lesson selectedLesson = lessonList.get(index);
                String urlSpellAudio = selectedLesson.getAudioSpellLink();
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(urlSpellAudio);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                return true;
            }
        });

        btnSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonActivity.this, SelectQuizDifficulty.class);
                Intent IDRecieve2 = getIntent();
                String subCatId2 = IDRecieve2.getStringExtra("subCatId");
                intent.putExtra("subCatId", subCatId2);
                startActivity(intent);

            }
        });
    }

    private void retrieveContent() {
        Intent IDRecieve = getIntent();
        String subCatId = IDRecieve.getStringExtra("subCatId");

        String url = "https://fypandroiddmsd.000webhostapp.com/getLessonBySubCategoryId.php?subcategory_id=" + subCatId + "";
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
                    Lesson(LessonObjectJSON(new String(response)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Lesson(ArrayList<String> lesson){
        LessonAdapter aa = new LessonAdapter(this,R.layout.row_lesson,lessonList);
        lvLesson.setAdapter(aa);
    }

    public ArrayList<String> LessonObjectJSON(String response){
        ArrayList<String>LessonListName = new ArrayList<String>();
        lessonList = new ArrayList<Lesson>();
        try{
            JSONArray jsonArray = new JSONArray(response);
            String lessonImageUrl ;
            String contentName;
            int contentId;
            int subCatId;
            String contentAudio;
            String audioSpell;
            for (int i= 0;i<jsonArray.length();i++){
                lessonImageUrl = jsonArray.getJSONObject(i).getString("content_image");
                contentName = jsonArray.getJSONObject(i).getString("content_name");
                contentId = jsonArray.getJSONObject(i).getInt("content_id");
                subCatId = jsonArray.getJSONObject(i).getInt("subcategory_id");
                contentAudio = jsonArray.getJSONObject(i).getString("content_audio");
                audioSpell = jsonArray.getJSONObject(i).getString("audio_spell");
                LessonListName.add(contentName);
                lessonList.add(new Lesson(contentId,lessonImageUrl,contentAudio,contentName,subCatId,audioSpell));
//                Log.i("info", String.valueOf(contentName));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return LessonListName;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LessonActivity.this);
//        boolean islogin = prefs.getBoolean("Islogin",false);
//        Log.i("tttpref",islogin+"");
//        if(islogin == true){
//            Intent i = new Intent(LessonActivity.this,MainActivity.class);
//            startActivity(i);
//        }
//    }
}
