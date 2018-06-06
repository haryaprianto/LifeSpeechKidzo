package com.example.a16022596.lifespeechkidzo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LessonAdapter extends ArrayAdapter<Lesson> {
    private ArrayList<Lesson> lessons;
    private Context context;
    private ImageView imgLesson;
    private TextView tvLesson;

    public LessonAdapter(Context context, int resource, ArrayList<Lesson> objects) {
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        lessons = objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row_lesson, parent, false);


        tvLesson = (TextView) rowView.findViewById(R.id.textViewLesson);
        imgLesson = (ImageView) rowView.findViewById(R.id.imageViewLesson);

        Lesson currentLesson = lessons.get(position);
        tvLesson.setText(currentLesson.getContentName());
        String url = currentLesson.getContentImage();
        Log.i("ttturl",url);
        if (!url.isEmpty()){
//            Picasso.get().load(url).resize(100,50).into(imgCategory);

            Picasso.get().load(url).fit().centerCrop().into(imgLesson);
        }
        return rowView;
    }
}
