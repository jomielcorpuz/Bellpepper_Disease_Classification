package com.example.bellpepper_classify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecommViewer extends AppCompatActivity {

    CardView defaultRecommLayout,updatedRecommLayout;

    TextView titletxtvw,recommTxt, newrecommendation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }
        init();



        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            String titleresult = bundle.getString("title");

            if(titleresult.equals("Yellow Leaves")){

                defaultRecommLayout.setVisibility(View.VISIBLE);
                titletxtvw.setText(titleresult);
                recommTxt.setText(R.string.Yellow_Leaves_recommendation);
                //updatedyellowleafRecommendation();
                retrieveYellowLeafRecomm();
            }
            else if(titleresult.equals("Powdery Mildew")){

                defaultRecommLayout.setVisibility(View.VISIBLE);
                titletxtvw.setText(titleresult);
                recommTxt.setText(R.string.Powdery_Mildew_recommendation);
//                updatepowderymildewRecommendation();
                    retrievePowderMildewRecomm();

            }
            else if(titleresult.equals("Leaf Curl Virus")){

                defaultRecommLayout.setVisibility(View.VISIBLE);
                titletxtvw.setText(titleresult);
                recommTxt.setText(R.string.Leaf_Curl_Virus_recommendation);
//                updatedLeafCurl();
                retrieveLeafCurlRecomm();

            } else if (titleresult.equals("Healthy Leaf")) {
                titletxtvw.setText(titleresult);
                recommTxt.setVisibility(View.GONE);
            } else if (titleresult.equals("Undefined Images")) {
                titletxtvw.setText(titleresult);
                recommTxt.setVisibility(View.GONE);
            } else {
                Toast.makeText(this,"No result found", Toast.LENGTH_SHORT).show();
            }
        }

    }



    private void retrieveYellowLeafRecomm() {
        DatabaseReference yellowLeafRef = FirebaseDatabase.getInstance().getReference("Recommendations").child("YellowLeafRecommendation");

        yellowLeafRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // The node "YellowLeafRecommendation" exists and has a value
                    String yellowLeafValue = dataSnapshot.getValue(String.class);
                    // Now you can use the yellowLeafValue as needed
                    newrecommendation.setText(yellowLeafValue);
                    updatedRecommLayout.setVisibility(View.VISIBLE);
                } else {
                    // The node "YellowLeafRecommendation" does not exist or has no value
                    // Handle this case accordingly
                    updatedRecommLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors or interruptions in the data retrieval process
                // Log or display the error message
            }
        });
    }
    private void retrieveLeafCurlRecomm() {
        DatabaseReference leafcurlRef = FirebaseDatabase.getInstance().getReference("Recommendations").child("LeafCurlRecommendation");

        leafcurlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // The node "YellowLeafRecommendation" exists and has a value
                    String leafCurlValue = dataSnapshot.getValue(String.class);
                    // Now you can use the yellowLeafValue as needed
                    newrecommendation.setText(leafCurlValue);
                    updatedRecommLayout.setVisibility(View.VISIBLE);
                } else {
                    // The node "YellowLeafRecommendation" does not exist or has no value
                    // Handle this case accordingly
                    updatedRecommLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors or interruptions in the data retrieval process
                // Log or display the error message
            }
        });
    }
    private void retrievePowderMildewRecomm() {
        DatabaseReference powderMildRef = FirebaseDatabase.getInstance().getReference("Recommendations").child("PowderyMildewRecommendation");

        powderMildRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // The node "YellowLeafRecommendation" exists and has a value
                    String powderMildValue = dataSnapshot.getValue(String.class);
                    // Now you can use the yellowLeafValue as needed
                    newrecommendation.setText(powderMildValue);
                    updatedRecommLayout.setVisibility(View.VISIBLE);
                } else {
                    // The node "YellowLeafRecommendation" does not exist or has no value
                    // Handle this case accordingly
                    updatedRecommLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors or interruptions in the data retrieval process
                // Log or display the error message
            }
        });
    }


    private void updatedyellowleafRecommendation(){
        DataManager dataManager = new DataManager(this);
        try {

            // Open the database for reading
            dataManager.open();

            // Check if "newyellowleavesRecommendation" already exists in the database
            if (!dataManager.isRecordExist("newyellowleaves")) {
                // If it doesn't exist, do nothing
            } else {
                // The record already exists, display the new recommendation
                try {
                    // Open the database for reading
                    dataManager.open();

                    // Retrieve the "purpleblotchRecommendation" record
                    Cursor cursor = dataManager.getAllRecords();
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                            int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);


                            // Check if the columns are present in the cursor
                            if (nameIndex >= 0 && valueIndex >= 0) {
                                String name = cursor.getString(nameIndex);
                                String value = cursor.getString(valueIndex);


                                if ("newyellowleaves".equals(name)) {
                                    // Found the "newyellowleaves" record
                                    // Store the result in a string or use it as needed
                                    newrecommendation.setText(value);
                                    updatedRecommLayout.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }
                        } while (cursor.moveToNext());

                        // Close the cursor
                        cursor.close();
                    }
                } finally {
                    // Close the database
                    dataManager.close();
                }
            }

        } finally {
            // Close the database
            dataManager.close();
        }

    }

    private void updatepowderymildewRecommendation(){
        DataManager dataManager = new DataManager(this);
        try {

            // Open the database for reading
            dataManager.open();

            // Check if "newyellowleavesRecommendation" already exists in the database
            if (!dataManager.isRecordExist("newpowderymildew")) {
                // If it doesn't exist, do nothing
            } else {
                // The record already exists, display the new recommendation
                try {
                    // Open the database for reading
                    dataManager.open();

                    // Retrieve the "purpleblotchRecommendation" record
                    Cursor cursor = dataManager.getAllRecords();
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                            int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);


                            // Check if the columns are present in the cursor
                            if (nameIndex >= 0 && valueIndex >= 0) {
                                String name = cursor.getString(nameIndex);
                                String value = cursor.getString(valueIndex);


                                if ("newpowderymildew".equals(name)) {
                                    // Found the "newyellowleaves" record
                                    // Store the result in a string or use it as needed
                                    newrecommendation.setText(value);
                                    updatedRecommLayout.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }
                        } while (cursor.moveToNext());

                        // Close the cursor
                        cursor.close();
                    }
                } finally {
                    // Close the database
                    dataManager.close();
                }
            }

        } finally {
            // Close the database
            dataManager.close();
        }

    }

    private void updatedLeafCurl(){
        DataManager dataManager = new DataManager(this);
        try {

            // Open the database for reading
            dataManager.open();

            // Check if "newyellowleavesRecommendation" already exists in the database
            if (!dataManager.isRecordExist("newleafcurl")) {
                // If it doesn't exist, do nothing
            } else {
                // The record already exists, display the new recommendation
                try {
                    // Open the database for reading
                    dataManager.open();

                    // Retrieve the "purpleblotchRecommendation" record
                    Cursor cursor = dataManager.getAllRecords();
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                            int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);


                            // Check if the columns are present in the cursor
                            if (nameIndex >= 0 && valueIndex >= 0) {
                                String name = cursor.getString(nameIndex);
                                String value = cursor.getString(valueIndex);


                                if ("newleafcurl".equals(name)) {
                                    // Found the "newyellowleaves" record
                                    // Store the result in a string or use it as needed
                                    newrecommendation.setText(value);
                                    updatedRecommLayout.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }
                        } while (cursor.moveToNext());

                        // Close the cursor
                        cursor.close();
                    }
                } finally {
                    // Close the database
                    dataManager.close();
                }
            }

        } finally {
            // Close the database
            dataManager.close();
        }
    }



    //TODO


    private void init(){
        titletxtvw = findViewById(R.id.titleDisease);
        recommTxt = findViewById(R.id.recommTxtvw);
        defaultRecommLayout = findViewById(R.id.defaultRecommlayout);
        updatedRecommLayout = findViewById(R.id.updatedRecomlayout);
        newrecommendation = findViewById(R.id.newrecommendation);
    }
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}