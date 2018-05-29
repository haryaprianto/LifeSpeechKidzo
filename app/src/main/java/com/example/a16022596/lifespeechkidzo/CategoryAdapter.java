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

public class CategoryAdapter extends ArrayAdapter<Category> {
    private ArrayList<Category> category;
    private Context context;
    private ImageView imgCategory;
    private TextView tvCategory;

    public CategoryAdapter(Context context, int resource, ArrayList<Category> objects) {
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        category = objects;
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
        View rowView = inflater.inflate(R.layout.categor_view, parent, false);


        tvCategory = (TextView) rowView.findViewById(R.id.textViewCategory);
        imgCategory = (ImageView) rowView.findViewById(R.id.imageViewCategory);

        Category currentCategory = category.get(position);
        tvCategory.setText(currentCategory.getName());
        String url = "https://fypdmsd.000webhostapp.com/ws/images/"+currentCategory.getLinkImage();
        Log.i("ttturl",url);
        if (!url.isEmpty()){
//            Picasso.get().load(url).resize(100,50).into(imgCategory);

            Picasso.get().load(url).fit().centerCrop().into(imgCategory);

        }
        return rowView;
    }
}
