package com.example.pawdaw.travel_calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by pawdaw on 17/04/16.
 */
public class rowAdapter extends ArrayAdapter<Travel> {

    Context context;
    int layoutResourceId;

    public rowAdapter(Context context, int layoutResourceId, ArrayList<Travel> travels) {
        super(context, layoutResourceId, travels);
        this.context = context;
        this.layoutResourceId = layoutResourceId;


    }

    // method to reload ViewList
    public void refreshViewList() {

        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        // view lookup cache stored in tag
        ViewHolder holder = null;


        // Get the data item for this position
        final Travel travelNote = getItem(position);



        if ( row == null){

//           Refresh List View
            refreshViewList();

            holder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(layoutResourceId,parent,false);

            holder.title = (TextView) row.findViewById(R.id.textView);
            holder.image = (ImageView) row.findViewById(R.id.icon);

            row.setTag(holder);


        }else{

            holder = (ViewHolder) row.getTag();

        }

        // Populate the data into the template view using the data object
        holder.title.setText(travelNote.getTitle());

        if(travelNote.getImageURL() != "" || travelNote.getImageURL() != null){

            // Picasso, powerful image downloading and caching library
            Picasso.with(context).load(travelNote.getImageURL()).into(holder.image);
        }


        // Return the completed view to render on screen
        return row;
    }


    public class ViewHolder {

        TextView title;
        ImageView image;


    }


}
