package com.example.bellpepper_classify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageButton editButton, cameraButton, aboutButton;
    String leafCurlValue, yellowLeafValue,powderMildValue;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        retrieveLeafCurlRecomm();
//        retrieveYellowLeafRecomm();
//        retrievePowderMildewRecomm();
//        insertNewLeafCurl();
//        insertNewYellowLeaf();
//        insertNewPowderyMildew();
        context = this;
        if (isNetworkAvailable()) {
            new FirebaseDataFetcher(getApplicationContext()).execute();
        } else {
            // Handle case when network is not available
        }
        editButton = findViewById(R.id.editButton);
        cameraButton = findViewById(R.id.cameraButton);
        aboutButton = findViewById(R.id.aboutButton);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Edit();}

        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { OpenCamera();}

        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { About();}

        });

    }

    private  void Edit(){
        Intent intent = new Intent(MainActivity.this,Editrecommendation.class);
        startActivity(intent);
    }
    private  void About(){
        Intent intent = new Intent(MainActivity.this,About.class);
        startActivity(intent);
    }

    private  void OpenCamera(){
        Intent intent = new Intent(MainActivity.this,OpenCamera.class);
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }





    private void retrieveYellowLeafRecomm() {
        DatabaseReference yellowLeafRef = FirebaseDatabase.getInstance().getReference("Recommendations").child("YellowLeafRecommendation");

        yellowLeafRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // The node "YellowLeafRecommendation" exists and has a value
                     yellowLeafValue = dataSnapshot.getValue(String.class);
                    // Now you can use the yellowLeafValue as needed


                } else {
                    // The node "YellowLeafRecommendation" does not exist or has no value
                    // Handle this case accordingly

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
                     leafCurlValue = dataSnapshot.getValue(String.class);
                    // Now you can use the yellowLeafValue as needed

                } else {
                    // The node "YellowLeafRecommendation" does not exist or has no value
                    // Handle this case accordingly

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
                     powderMildValue = dataSnapshot.getValue(String.class);
                    // Now you can use the yellowLeafValue as needed


                } else {
                    // The node "YellowLeafRecommendation" does not exist or has no value
                    // Handle this case accordingly

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors or interruptions in the data retrieval process
                // Log or display the error message
            }
        });
    }


    private void insertNewYellowLeaf(){

        DataManager dataManager = new DataManager(this);

        //String updateRecommendations = newrecommendation.getText().toString();

        dataManager.open();
        if (yellowLeafValue!=null){
            if (!dataManager.isRecordExist("newyellowleaves")) {

                long newyellowleavesID = dataManager.insertRecord("newyellowleaves", yellowLeafValue);

                Log.d("Set","Update: "+yellowLeafValue);
                if (newyellowleavesID != -1) {
                    // Record inserted successfully
                    Toast.makeText(this, "New Yellow Leaves Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // There was an error inserting the record
                    Toast.makeText(this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                updateyellowleaf();
            }

        }else {
            //newrecommendation.setError("Please input new recommendations");
        }
    }

    private void updateyellowleaf(){

        //String updateRecommendations = newrecommendation.getText().toString();
        //String updateChem = chemedtxt.getText().toString();

        if (!yellowLeafValue.isEmpty() ){
            DataManager dataManager = new DataManager(this);
            try {
                // Open the database for writing
                dataManager.open();

                // Retrieve the "blightRecommendation" record
                Cursor cursor = dataManager.getAllRecords();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int ID = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                        int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                        int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                        //int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);

                        if (ID >= 0 && nameIndex >= 0 && valueIndex >= 0){
                            int recId = cursor.getInt(ID);
                            String name = cursor.getString(nameIndex);
                            String value = cursor.getString(valueIndex);
                            //String chemicalRecommendation = cursor.getString(chemValueIndex);

                            // Check if the columns are present in the cursor
                            if ("newyellowleaves".equals(name)) {
                                // Update the "blightRecommendation" record
                                int rowsAffected = dataManager.updateRecord(recId, name, yellowLeafValue);

                                Log.d("Set","Update: "+yellowLeafValue);
                                if (rowsAffected > 0) {
                                    // Record updated successfully
                                    Toast.makeText(this, "Yellow leaf updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
                                    Toast.makeText(this, "Error updating blightRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        }
                        else {
                            Log.d("Set","Update: No data");
                        }
                    } while (cursor.moveToNext());

                    // Close the cursor
                    cursor.close();
                }


            } finally {
                // Close the database
                dataManager.close();
            }
        }else {
        }


    }

    private void insertNewPowderyMildew(){

        DataManager dataManager = new DataManager(this);

       // String updateRecommendations = newrecommendation.getText().toString();

        dataManager.open();
        if (powderMildValue!=null){
            if (!dataManager.isRecordExist("newpowderymildew")) {

                long newpowderymildewID = dataManager.insertRecord("newpowderymildew", powderMildValue);

                if (newpowderymildewID != -1) {
                    // Record inserted successfully
                    Toast.makeText(this, "New Yellow Leaves Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // There was an error inserting the record
                    Toast.makeText(this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                updatepowderymildew();
            }

        }else {
           // newrecommendation.setError("Please input new recommendations");
        }
    }

    private void updatepowderymildew(){

       // String updateRecommendations = newrecommendation.getText().toString();
        //String updateChem = chemedtxt.getText().toString();

        if (powderMildValue!=null){
            DataManager dataManager = new DataManager(this);
            try {
                // Open the database for writing
                dataManager.open();

                // Retrieve the "blightRecommendation" record
                Cursor cursor = dataManager.getAllRecords();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int ID = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                        int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                        int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                        //int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);

                        if (ID >= 0 && nameIndex >= 0 && valueIndex >= 0){
                            int recId = cursor.getInt(ID);
                            String name = cursor.getString(nameIndex);
                            String value = cursor.getString(valueIndex);
                            //String chemicalRecommendation = cursor.getString(chemValueIndex);

                            // Check if the columns are present in the cursor
                            if ("newpowderymildew".equals(name)) {
                                // Update the "blightRecommendation" record
                                int rowsAffected = dataManager.updateRecord(recId, name, powderMildValue);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
                                    Toast.makeText(this, "Yellow leaf updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
                                    Toast.makeText(this, "Error updating blightRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        }
                        else {
                            Log.d("Set","Update: No data");
                        }
                    } while (cursor.moveToNext());

                    // Close the cursor
                    cursor.close();
                }


            } finally {
                // Close the database
                dataManager.close();
            }
        }else {
        }


    }

    private void insertNewLeafCurl(){

        DataManager dataManager = new DataManager(this);

        //String updateRecommendations = newrecommendation.getText().toString();

        dataManager.open();
        if (leafCurlValue!=null){
            if (!dataManager.isRecordExist("newleafcurl")) {

                long newleafcurlID = dataManager.insertRecord("newleafcurl", leafCurlValue);

                if (newleafcurlID != -1) {
                    // Record inserted successfully
                    Toast.makeText(this, "New Yellow Leaves Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // There was an error inserting the record
                    Toast.makeText(this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                updateleafcurl();
            }

        }else {
            //newrecommendation.setError("Please input new recommendations");
        }
    }

    private void updateleafcurl(){

       // String updateRecommendations = newrecommendation.getText().toString();
        //String updateChem = chemedtxt.getText().toString();

        if (leafCurlValue!=null){
            DataManager dataManager = new DataManager(this);
            try {
                // Open the database for writing
                dataManager.open();

                // Retrieve the "blightRecommendation" record
                Cursor cursor = dataManager.getAllRecords();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int ID = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                        int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                        int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                        //int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);

                        if (ID >= 0 && nameIndex >= 0 && valueIndex >= 0){
                            int recId = cursor.getInt(ID);
                            String name = cursor.getString(nameIndex);
                            String value = cursor.getString(valueIndex);
                            //String chemicalRecommendation = cursor.getString(chemValueIndex);

                            // Check if the columns are present in the cursor
                            if ("newleafcurl".equals(name)) {
                                // Update the "blightRecommendation" record
                                int rowsAffected = dataManager.updateRecord(recId, name, leafCurlValue);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
                                    Toast.makeText(this, "Yellow leaf updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
                                    Toast.makeText(this, "Error updating blightRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                        }
                        else {
                            Log.d("Set","Update: No data");
                        }
                    } while (cursor.moveToNext());

                    // Close the cursor
                    cursor.close();
                }


            } finally {
                // Close the database
                dataManager.close();
            }
        }else {
        }


    }

    //-----NOT USED--------
   /* private void initializeRecommendation() {
        DataManager dataManager = new DataManager(this);

        //this is for default values recommendation initialization not for new recommendation
        String newyellowleaves = getResources().getString(R.string.Yellow_Leaves_recommendation);
        String newleafcurl = getResources().getString(R.string.Leaf_Curl_Virus_recommendation);
        String newpowderymildew = getResources().getString(R.string.Powdery_Mildew_recommendation);

        try {
            // Open the database for reading
            dataManager.open();

            // Check if "newyellowleavesRecommendation" already exists in the database
            if (!dataManager.isRecordExist("newyellowleaves")) {
                // If it doesn't exist, insert the record
                long newyellowleavesID = dataManager.insertRecord("newyellowleaves", newleafcurl);

                if (newyellowleavesID != -1) {
                    // Record inserted successfully
                    // Toast.makeText(this, "New Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // There was an error inserting the record
                    // Toast.makeText(this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                }
            } else {
                // The record already exists
                //Toast.makeText(this, "New Recommendation already exists", Toast.LENGTH_SHORT).show();
            }

            // Check if "newleafcurlRecommendation" already exists in the database
            if (!dataManager.isRecordExist("newleafcurl")) {
                // If it doesn't exist, insert the record
                long newleafcurlID = dataManager.insertRecord("newleafcurl", newyellowleaves);

                if (newleafcurlID != -1) {
                    // Record inserted successfully
                    // Toast.makeText(this, "New Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // There was an error inserting the record
                    // Toast.makeText(this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                }
            } else {
                // The record already exists
                //Toast.makeText(this, "New Recommendation already exists", Toast.LENGTH_SHORT).show();
            }

            // Check if "newpowderymildewRecommendation" already exists in the database
            if (!dataManager.isRecordExist("newpowderymildew")) {
                // If it doesn't exist, insert the record
                long newpowderymildewID = dataManager.insertRecord("newpowderymildew", newpowderymildew );

                if (newpowderymildewID != -1) {
                    // Record inserted successfully
                    // Toast.makeText(this, "New Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // There was an error inserting the record
                    // Toast.makeText(this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                }
            } else {
                // The record already exists
                //Toast.makeText(this, "New Recommendation already exists", Toast.LENGTH_SHORT).show();
            }
        } finally {
            // Close the database
            dataManager.close();
        }


    }

   private void updateRecommendation() {
        // Needed a method or condition for updating recommendations after opening the app so that it wont update everytime when opening or launching the app
        DataManager dataManager = new DataManager(this);

        String newyellowleaves = getResources().getString(R.string.Yellow_Leaves_recommendation);
        String newleafcurl = getResources().getString(R.string.Leaf_Curl_Virus_recommendation);
        String newpowderymildew = getResources().getString(R.string.Powdery_Mildew_recommendation);

        try {
            // Open the database for writing
            dataManager.open();

            // Retrieve the "newRecommendation" record
            Cursor cursor = dataManager.getAllRecords();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int ID = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                    int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                   // int newRecommendationIndex = cursor.getColumnIndex(DBHelper.COLUMN_NEW_RECOMMENDATION);

                    if (ID >= 0 && nameIndex >= 0 && valueIndex >= 0 ){
                        int recId = cursor.getInt(ID);
                        String name = cursor.getString(nameIndex);
                        String value = cursor.getString(valueIndex);
                        //String newRecommendation = cursor.getString(newRecommendationIndex);

                        // Check if the columns are present in the cursor
                        if ("newyellowleafrecommendation".equals(name)) {
                            if (value.equals(newyellowleaves)) {
                                // Update the "newyellowleaves" record
                                int rowsAffected = dataManager.updateRecord(recId, name, newyellowleaves);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
//                            Toast.makeText(this, "newyellowleaves updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
//                            Toast.makeText(this, "Error updating newyellowleavesRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            else {

                            }
                        }
                        if ("newleafcurlrecommendation".equals(name)) {
                            if (value.equals(newleafcurl) ) {
                                // Update the "newleafcurl" record
                                int rowsAffected = dataManager.updateRecord(recId, name, newleafcurl);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
//                            Toast.makeText(this, "newleafcurl updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
//                            Toast.makeText(this, "Error updating newleafcurlRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            else {

                            }
                        }
                        if ("newpowderymildew".equals(name)) {
                            if (value.equals(newpowderymildew)) {
                                // Update the "newpowderymildew" record
                                int rowsAffected = dataManager.updateRecord(recId, name, newpowderymildew);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
//                            Toast.makeText(this, "newpowderymildew updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
//                            Toast.makeText(this, "Error updating newpowderymildewRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            else {

                            }
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
    */
}

