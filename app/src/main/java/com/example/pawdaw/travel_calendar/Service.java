package com.example.pawdaw.travel_calendar;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;


/**
 * Created by pawdaw on 17/04/16.
 */
public class Service {



    ArrayList<Travel> travels = new ArrayList<Travel>();

    private boolean InitUsed = false;


    // ---------------Singleton pattern--------------------------
    /**
     * Singleton pattern - It is used when you want to eliminate the option of instantiating more than one object !!! We can use in STORAGE, SERVICE
     */

    private static Service instance = new Service();

    Service() {
        //
    }

    public static Service getInstance() {

        return instance;
    }

//    ------------------------------------


    public ArrayList<Travel> getTravels() {

        return this.getInstance().travels;
    }

    public Travel createTravel(String title, String description, String address, String date, String visitAgain, String imageURL) {
        Travel t = new Travel(title, description, address, date, visitAgain, imageURL);
        travels.add(t);
        return t;
    }

    public Travel updateTravel(Travel travel, String title, String description, String address, String date, String visitAgain, String imageURL) {
        travel.setTitle(title);
        travel.setDescription(description);
        travel.setAddress(address);
        travel.setDate(date);
        travel.setImageURL(imageURL);
        travel.setVisitAgain(visitAgain);

        return travel;
    }

    public Travel removeTravel(Travel travel) {

        travels.remove(travel);
        return travel;
    }


    public void initStorage() {

        if (InitUsed == false) {

            InitUsed = true;

            Travel t1 = new Travel("Mallorca","Majorca is popular all year round, thanks to its beautiful beaches and Mediterranean climate.","DES PARK DE LA MAR 6-8","2015", "yes", "http://turystyka.lublin.pl/files/file/majorka.jpg");
            Travel t2 = new Travel("Portugal","Portugal is a popular destination for summer holidays and is a great choice any time of year","Rua Do Oceano Atlantico Vilamoura","2016", "yes", "http://www.travelshops.pl/images/relacje/Majorka-001/Majorka-05d.jpg");
            Travel t3 = new Travel("Dubai","Miles of stunning coastline, the beautiful Arabian Desert, and year-round sunshine make Dubai the destination","The Palm Jumeirah East Crescent Plot C , Dubai","2014", "yes", "http://www.travelshops.pl/images/relacje/Majorka-001/Majorka-03d.jpg");
            Travel t4 = new Travel("Cyprus","Beautiful Cyprus is the alleged birthplace of Aphrodite, goddess of beauty and love and it’s easy to see why.","Larnaca","2014", "yes", "http://i.wp.pl/a/f/jpeg/32345/800_kyrenia2_greenacre8.jpeg");

            travels.add(t1);
            travels.add(t2);
            travels.add(t3);
            travels.add(t4);
        }
    }

    // ----- Save JSON data to file  -----
    public void saveData(Context context) {

        String filename = "JSON.js";
        File directory = new File(context.getFilesDir().getAbsolutePath() + File.separator);

        try {

            OutputStream os = new FileOutputStream(directory + File.separator + filename);

            //  method below to write JSON
            writeJsonStream(os,travels);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("............ JSON SAVED ........... ");
    }


    // ----- read JSON data from file  -----
    public void readFile(Context context) {

        String filename = "JSON.js";
        File directory = new File(context.getFilesDir().getAbsolutePath() + File.separator);

        try {

            InputStream in = new FileInputStream(directory + File.separator + filename);

            //  method below to read JSON
            readJSONStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("............ JSON Read ............ ");
    }



    //  --------- write JSON ---------

    public void writeJsonStream(OutputStream out, ArrayList<Travel> travels) throws IOException{

        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent(" ");

        //method below
        writeTravelArray(writer, travels);

        writer.close();

    }

    public void writeTravelArray(JsonWriter writer, ArrayList<Travel> travels) throws IOException{

        // write array
        writer.beginArray();

        for(Travel t: travels){

            // method below
            writeTravel(writer,t);

        }
        writer.endArray();
    }

    public void writeTravel(JsonWriter writer, Travel travel) throws IOException{

        // write Object
        writer.beginObject();

        writer.name("title").value(travel.getTitle());
        writer.name("description").value(travel.getDescription());
        writer.name("address").value(travel.getAddress());
        writer.name("date").value(travel.getDate());
        writer.name("image").value(travel.getImageURL());
        writer.name("visitAgain").value(travel.getVisitAgain());

        writer.endObject();
    }



    //  ---------- read JSON ------------

    public ArrayList<Travel> readJSONStream (InputStream in) throws IOException{

        JsonReader reader = new JsonReader(new InputStreamReader(in,"UTF-8"));
        try{
            //method below
            return readTravelArray(reader);
        }finally {
            reader.close();
        }
    }

    private ArrayList<Travel> readTravelArray(JsonReader reader) throws IOException {

        reader.beginArray();

        while (reader.hasNext()){
            travels.add(readTravel(reader));
        }
        reader.endArray();
        return travels;
    }
    // convert JSON object into a TRAVEL class instance
    private Travel readTravel(JsonReader reader) throws IOException {

        String title = null;
        String description = null;
        String address = null;
        String date = null;
        String image = null;
        String visitAgain = null;

        reader.beginObject();

        while (reader.hasNext()){

            String name = reader.nextName();

            if(name.equals("title")){
                title = reader.nextString();
            }else if(name.equals("description")){
                description = reader.nextString();
            }else if(name.equals("address")){
                address = reader.nextString();
            }else if(name.equals("date")){
                date = reader.nextString();
            }else if(name.equals("image")){
                image = reader.nextString();
            }else if(name.equals("visitAgain")){
                visitAgain = reader.nextString();
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Travel(title,description,address,date,visitAgain,image);
    }

    // ------------------------------

}
