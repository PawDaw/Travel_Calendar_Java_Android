package com.example.pawdaw.travel_calendar;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;

/**
 * Created by pawdaw on 17/04/16.
 */
public class List_Fragment extends ListFragment {

    // True or False depending on if we are in horizontal or duel pane mode
    private boolean landscape;
    private static final int REQUEST_CODE = 1;
    private static final int REQUEST_CODE_2 = 2;

    // Currently selected item in the ListView
    int  mCurCheckPosition;
    rowAdapter adapter;

    public int getmCurCheckPosition() {

        return mCurCheckPosition;
    }

    public void setmCurCheckPosition(int mCurCheckPosition) {
        this.mCurCheckPosition = mCurCheckPosition;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ContextMenu functionality
        registerForContextMenu(getListView());
        setHasOptionsMenu(true);


        // If the screen is rotated onSaveInstanceState() below will store the
        // hero most recently selected. Get the value attached to curChoice and
        // store it in mCurCheckPosition
        if (savedInstanceState != null) {
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        adapter = new rowAdapter(getActivity(),R.layout.row,Service.getInstance().getTravels());
        setListAdapter(adapter);

        // CHOICE_MODE_SINGLE allows one item in the ListView to be selected at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Make the currently selected item highlighted
        getListView().setItemChecked(mCurCheckPosition, true);


        View detailFrame = getActivity().findViewById(R.id.description_panel);

        landscape = detailFrame !=null;
        if (landscape) {
            getFragmentManager().popBackStack();
            showDetails(mCurCheckPosition);
        }
    }
    // We save the last item selected in the list here and attach it to the key curChoice
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        showDetails(position);
    }


    void showDetails(int position) {

        DescriptionFragment details = new DescriptionFragment();
        details.setIndex(position);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        // Check if we are in horizontal mode and if yes show the ListView and
        // the Travel data
        if (landscape) {
            // Replace the content of the container
            // ft.replace(R.id.flContainer, new_fragment);
            ft.replace(R.id.description_panel, details);
        } else {
            // Replace whatever is in the fragment_container view with this fragment,   transaction.replace(R.id.fragment_container, newFragment);
            // and add the transaction to the back stack
            ft.replace(R.id.side_panel, details);

            // addToBackStack(null) so your previous state will be added to the backstack allowing you to go back with the back button.
            // MainActivity method onBackPressed() need to be implemented
            ft.addToBackStack("null");

        }

        // Commit the changes
        ft.commit();
    }


    //  Implement the app bar
    //  SearchView widget in the app bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.search_bar).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }

                return true;
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case R.id.add:
                Intent addNewNote = new Intent(getActivity(), NewNoteActivity.class);
                startActivityForResult(addNewNote, REQUEST_CODE);
                Toast.makeText(getActivity(), "Add", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Select the action");
        menu.add("Edit");//groupId, itemId, order, title
        menu.add("Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals("Edit")){

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int itemPosition = info.position;

            String selectedTitle = Service.getInstance().getTravels().get(itemPosition).getTitle();
            String selectedDescription = Service.getInstance().getTravels().get(itemPosition).getDescription();
            String selectedAddress = Service.getInstance().getTravels().get(itemPosition).getAddress();
            String selectedDate = Service.getInstance().getTravels().get(itemPosition).getDate();
            String selectedImage = Service.getInstance().getTravels().get(itemPosition).getImageURL();
            String selectedVisited = Service.getInstance().getTravels().get(itemPosition).getVisitAgain();

            Intent intent = new Intent(getActivity(), EditableActivity.class);

            intent.putExtra("itemPosition", itemPosition);
            intent.putExtra("title", selectedTitle);
            intent.putExtra("description", selectedDescription);
            intent.putExtra("address", selectedAddress);
            intent.putExtra("date", selectedDate);
            intent.putExtra("visit", selectedVisited);
            intent.putExtra("image", selectedImage);

            getActivity().startActivityForResult(intent, REQUEST_CODE_2);

            Toast.makeText(getActivity(), "Edited on position: " + itemPosition, Toast.LENGTH_LONG).show();
            System.out.println("2222222....................." + Service.getInstance().getTravels().toString());
        }
        else if(item.getTitle().equals("Delete")){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int itemPosition = info.position;
            Service.getInstance().removeTravel(Service.getInstance().getTravels().get(itemPosition));
            adapter.notifyDataSetChanged();
            Service.getInstance().saveData(getActivity());

            Toast.makeText(getActivity(),"Deleted on position: " + itemPosition,Toast.LENGTH_LONG).show();
        }
        return true;
    }
    //     Update List view,call every time when the activity stop ( EditableActivity or NewNoteActivity  )
    //    Called when the activity will start interacting with the user. At this point your activity is at the top of the activity stack, with user input going to it.
    //    Always followed by onPause()
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        Service.getInstance().saveData(getActivity());
    }




}
