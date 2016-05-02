package com.example.pawdaw.travel_calendar;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    List_Fragment listFra;

    DescriptionFragment desFra;

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

    //  save to JSON before the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Service.getInstance().saveData(this);
    }

    public void OnCLickAddres(View view) {

        Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
        search.putExtra(SearchManager.QUERY, Service.getInstance().getTravels().get(listFra.getmCurCheckPosition()).getAddress());
        startActivity(search);

    }
}
