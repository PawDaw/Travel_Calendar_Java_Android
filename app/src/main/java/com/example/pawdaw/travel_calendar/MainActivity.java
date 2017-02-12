package com.example.pawdaw.travel_calendar;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    List_Fragment listFra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  check if JSON file exist if not intStorage and create JSON file
        String filename = "JSON.js";
        File directory = new File(this.getFilesDir().getAbsolutePath()+filename + File.separator);
        if(!directory.exists()){

            Service.getInstance().initStorage();
            Service.getInstance().saveData(this);

            System.out.println("............ File created ........... ");

        }

        // Read the JSON file, when Travels array is empty
        if(Service.getInstance().getTravels().isEmpty()){
            Service.getInstance().readFile(this);
        }

        setContentView(R.layout.activity_main);
        listFra = new List_Fragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.side_panel, listFra);
        ft.commit();



    }

    // addToBackStack() so your previous state will be added to the backstack allowing you to go back with the back button.
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0){
            getFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }

    //    There is no guarantee, that onDestroy() is called, so call saveData() in onStop()!
    //  save to JSON before the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Service.getInstance().saveData(this);
    }

    //  save to JSON on STOP Application
    @Override
    protected void onStop() {
        super.onStop();
        Service.getInstance().saveData(this);
    }


    //   Address click LINK to google Website
    public void OnCLickAddres(View view) {

//        --------------  Google SEARCH ----------

//        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
//        search.putExtra(SearchManager.QUERY, Service.getInstance().getTravels().get(listFra.getmCurCheckPosition()).getAddress());
//        startActivity(search);

//       --------------  Google MAPS  ----------
//        https://developers.google.com/maps/documentation/android-api/intents#intent_requests


        // Create a Uri from an intent string. Use the result to create an Intent.
//         Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");

         // OR
//        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");

        // OR
//        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4192?q=" + Uri.encode("1st & Pike, Seattle"));

        // OR
        // Search for restaurants nearby
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants");

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

         // Make the Intent explicit by setting the Google Maps package
         mapIntent.setPackage("com.google.android.apps.maps");

         // Attempt to start an activity that can handle the Intent
         startActivity(mapIntent);

    }
}
