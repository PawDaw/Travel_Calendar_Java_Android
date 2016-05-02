package com.example.pawdaw.travel_calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Piotrek J on 2016-04-13.
 */
public class NewNoteActivity extends AppCompatActivity {
    TextView title, description, address, date, visit, imageURL;
    EditText titleText, descriptionText, addressText, dateText, visitText,imageURLEdit;
    Button save;

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


        save = (Button) findViewById(R.id.add);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(titleText.getText().toString().equals("") || descriptionText.getText().toString().equals("") || addressText.getText().toString().equals("") || dateText.getText().toString().equals("") || visitText.getText().toString().equals("") || imageURLEdit.getText().toString().equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewNoteActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please write some info...");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                }
                else {
                    String getTitle = titleText.getText().toString();
                    String getDescription = descriptionText.getText().toString();
                    String getAddress = addressText.getText().toString();
                    String getDate = dateText.getText().toString();
                    String getVisit = visitText.getText().toString();
                    String getImageURL = imageURLEdit.getText().toString();

                    Intent intent = new Intent(NewNoteActivity.this, MainActivity.class);

                    intent.putExtra("title", getTitle);
                    intent.putExtra("description", getDescription);
                    intent.putExtra("address", getAddress);
                    intent.putExtra("date", getDate);
                    intent.putExtra("visit", getVisit);
                    intent.putExtra("image", getImageURL);

                    Service.getInstance().createTravel(getTitle, getDescription, getAddress, getDate, getVisit, getImageURL);
                    Service.getInstance().saveData(getApplicationContext());

                    setResult(RESULT_OK, intent);
                    finish();
                    System.out.print("Added");
                }
            }
        });
    }
}
