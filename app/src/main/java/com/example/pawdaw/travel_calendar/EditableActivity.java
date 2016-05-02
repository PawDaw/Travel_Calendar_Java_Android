package com.example.pawdaw.travel_calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by pawdaw on 17/04/16.
 */
public class EditableActivity extends AppCompatActivity {
    TextView title, description, address, date, visit,imageURL;
    EditText titleText, descriptionText, addressText, dateText, visitText,imageURLEdit;
    Button submit;

    int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);


        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        address = (TextView) findViewById(R.id.address);
        date = (TextView) findViewById(R.id.date);
        visit = (TextView) findViewById(R.id.visitAgain);
        imageURL = (TextView) findViewById(R.id.ImageText);


        titleText = (EditText) findViewById(R.id.titleText);
        descriptionText = (EditText) findViewById(R.id.descriptionText);
        addressText = (EditText) findViewById(R.id.addressText);
        dateText = (EditText) findViewById(R.id.dateText);
        visitText = (EditText) findViewById(R.id.visitAgainText);
        imageURLEdit = (EditText) findViewById(R.id.setImageEdit);


        final Bundle extras = getIntent().getExtras();
        if (extras != null) {

            itemPosition = extras.getInt("itemPosition");
            titleText.setText(extras.getString("title"));
            descriptionText.setText(extras.getString("description"));
            addressText.setText(extras.getString("address"));
            dateText.setText(extras.getString("date"));
            visitText.setText(extras.getString("visit"));
            imageURLEdit .setText(extras.getString("image"));

        }

        submit = (Button) findViewById(R.id.add);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(titleText.getText().toString().equals("") || descriptionText.getText().toString().equals("") || addressText.getText().toString().equals("") || dateText.getText().toString().equals("") || visitText.getText().toString().equals("") || imageURLEdit.getText().toString().equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditableActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please write some info...");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }else {

                    String getTitle = titleText.getText().toString();
                    String getDescription = descriptionText.getText().toString();
                    String getAddress = addressText.getText().toString();
                    String getDate = dateText.getText().toString();
                    String getVisit = visitText.getText().toString();
                    String getImageURL = imageURLEdit.getText().toString();

                    Intent toTheList = new Intent(EditableActivity.this, MainActivity.class);

                    toTheList.putExtra("title", getTitle);
                    toTheList.putExtra("description", getDescription);
                    toTheList.putExtra("address", getAddress);
                    toTheList.putExtra("date", getDate);
                    toTheList.putExtra("visit", getVisit);
                    toTheList.putExtra("image", getImageURL);

                    Service.getInstance().updateTravel(Service.getInstance().getTravels().get(itemPosition), getTitle, getDescription, getAddress, getDate, getVisit, getImageURL);
                    Service.getInstance().saveData(getApplicationContext());

                    setResult(RESULT_OK, toTheList);
                    finish();


                    Toast.makeText(getApplicationContext(), getTitle, Toast.LENGTH_LONG).show();
//                    System.out.println("4444444....................." + Service.getInstance().getTravels().toString());
                }
            }
        });
    }
}
