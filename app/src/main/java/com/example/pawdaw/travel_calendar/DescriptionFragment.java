package com.example.pawdaw.travel_calendar;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by pawdaw on 18/04/16.
 */
public class DescriptionFragment extends Fragment {

    private static int index;


    public static int getIndex() {

        return  DescriptionFragment.index;
    }

    public static void setIndex(int index) {

        DescriptionFragment.index = index;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(container == null)
            return  null;

        View view = inflater.inflate(R.layout.description_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView title = (TextView) getActivity().findViewById(R.id.title);
        TextView address = (TextView) getActivity().findViewById(R.id.address);
        TextView date_of_visit =  (TextView) getActivity().findViewById(R.id.date_of_visit);
        TextView description = (TextView) getActivity().findViewById(R.id.description);
        TextView visitAgain = (TextView) getActivity().findViewById(R.id.visit_again);
        ImageView image = (ImageView) getActivity().findViewById(R.id.imageView);


        if (title != null || address != null || date_of_visit != null || description != null || visitAgain != null || image != null ){

            title.setText(Service.getInstance().getTravels().get(index).getTitle());
            address.setText(Service.getInstance().getTravels().get(index).getAddress());
            date_of_visit.setText(Service.getInstance().getTravels().get(index).getDate());
            description.setText(Service.getInstance().getTravels().get(index).getDescription());
            visitAgain.setText(Service.getInstance().getTravels().get(index).getVisitAgain());

            // Picasso, powerful image downloading and caching library
            Picasso.with(getActivity()).load(Service.getInstance().getTravels().get(index).getImageURL()).into(image);

            System.out.println("............ url ............ " +Service.getInstance().getTravels().get(index).getImageURL());



        }

    }



}
