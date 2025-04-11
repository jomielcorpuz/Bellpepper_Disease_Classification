package com.example.bellpepper_classify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class New extends AppCompatActivity {

    private static final String TAG = "Update Recommendation";
    Button updateBtn, cancelBtn;
    EditText newrecommendation;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        init();
        // Initialize Firebase only once
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }
        Bundle extras = getIntent().getExtras();
        String Name = extras.getString("id");


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Name.equals("yellowleaf")){
                    insertNewYellowLeafRecomm();
                    //insertNewYellowLeaf();
                    Toast.makeText(New.this,"Successfully added new treatment for yellow Leaf", Toast.LENGTH_SHORT).show();
                } else if(Name.equals("leafcurl")){
                    //insertNewLeafCurl();
                    insertNewleafcurlRecomm();
                    Toast.makeText(New.this,"Successfully Updated Recommendations for leafcurl", Toast.LENGTH_SHORT).show();
                }else if (Name.equals("powderymildew")){
                    //insertNewPowderyMildew();
                    insertNewpowderymildewRecomm();
                    Toast.makeText(New.this,"Successfully Updated Recommendations for powdery mildew", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(New.this,"Unable to update",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(New.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });


    }

    private void init(){
        updateBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        newrecommendation = findViewById(R.id.newrecommendation);
        //chemedtxt = findViewById(R.id.chemEditxt);
    }
    private void insertNewleafcurlRecomm() {
        try {
            String newLeafCurlRecomm = newrecommendation.getText().toString();
            DatabaseReference leafCurlRef = FirebaseDatabase.getInstance().getReference("Recommendations/LeafCurlRecommendation");

            if (!newLeafCurlRecomm.isEmpty()) {
                // Set the value in the database
                leafCurlRef.setValue(newLeafCurlRecomm)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Value set successfully
                                    Toast.makeText(New.this, "New Leaf Curl Recommendation inserted to database successfully", Toast.LENGTH_SHORT).show();
                                    Log.d("Database", "LeafCurl inserted");
                                } else {
                                    // Handle the error
                                    Exception exception = task.getException();
                                    // Log or display the error message
                                    Toast.makeText(New.this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                newrecommendation.setError("Please input new recommendations");
            }
        } catch (Throwable throwable) {
            // Handle the throwable exception here
            throwable.printStackTrace(); // Log the exception details

            // You can also throw a new exception or handle it based on your requirements
            throw new RuntimeException("Error occurred during database operation", throwable);
        }
    }

    private void insertNewpowderymildewRecomm(){
        DatabaseReference yellowLeafRef = FirebaseDatabase.getInstance().getReference("Recommendations").child("PowderyMildewRecommendation");
        String leafCurlValue = newrecommendation.getText().toString();
        if (!leafCurlValue.isEmpty()){
            // Set the value in the database
            yellowLeafRef.setValue(leafCurlValue)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Value set successfully
                                // You can add any additional actions you want to perform upon success

                                Toast.makeText(New.this, "New Powder Mildew Recommendation inserted to database successfully", Toast.LENGTH_SHORT).show();
                                Log.d("Database","Leafcurl inserted");
                            } else {
                                // Handle the error
                                Exception exception = task.getException();
                                // Log or display the error message

                                Toast.makeText(New.this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            newrecommendation.setError("Please input new recommendations");
        }
    }
    private void insertNewYellowLeafRecomm(){
        DatabaseReference yellowLeafRef = FirebaseDatabase.getInstance().getReference("Recommendations").child("YellowLeafRecommendation");
        String newYellowleafRecomm = newrecommendation.getText().toString();
        if (!newYellowleafRecomm.isEmpty()){
            // Set the value in the database
            yellowLeafRef.setValue(newYellowleafRecomm)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Value set successfully
                                // You can add any additional actions you want to perform upon success
                                Log.d("Database","Leafcurl inserted");
                                Toast.makeText(New.this, "New Yellow Leaves Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle the error
                                Exception exception = task.getException();
                                // Log or display the error message

                                Toast.makeText(New.this, "Error inserting New Recommendation", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            newrecommendation.setError("Please input new recommendations");
        }
    }

    private void insertNewYellowLeaf(){

        DataManager dataManager = new DataManager(this);

        String updateRecommendations = newrecommendation.getText().toString();

        dataManager.open();
        if (!updateRecommendations.isEmpty()){
            if (!dataManager.isRecordExist("newyellowleaves")) {

                long newyellowleavesID = dataManager.insertRecord("newyellowleaves", updateRecommendations);

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
            newrecommendation.setError("Please input new recommendations");
        }
    }

    private void updateyellowleaf(){

        String updateRecommendations = newrecommendation.getText().toString();
        //String updateChem = chemedtxt.getText().toString();

        if (!updateRecommendations.isEmpty() ){
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
                                int rowsAffected = dataManager.updateRecord(recId, name, updateRecommendations);

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
                            Log.d(TAG,"Update: No data");
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

        String updateRecommendations = newrecommendation.getText().toString();

        dataManager.open();
        if (!updateRecommendations.isEmpty()){
            if (!dataManager.isRecordExist("newpowderymildew")) {

                long newpowderymildewID = dataManager.insertRecord("newpowderymildew", updateRecommendations);

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
            newrecommendation.setError("Please input new recommendations");
        }
    }

    private void updatepowderymildew(){

        String updateRecommendations = newrecommendation.getText().toString();
        //String updateChem = chemedtxt.getText().toString();

        if (!updateRecommendations.isEmpty() ){
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
                                int rowsAffected = dataManager.updateRecord(recId, name, updateRecommendations);

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
                            Log.d(TAG,"Update: No data");
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

        String updateRecommendations = newrecommendation.getText().toString();

        dataManager.open();
        if (!updateRecommendations.isEmpty()){
            if (!dataManager.isRecordExist("newleafcurl")) {

                long newleafcurlID = dataManager.insertRecord("newleafcurl", updateRecommendations);

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
            newrecommendation.setError("Please input new recommendations");
        }
    }

    private void updateleafcurl(){

        String updateRecommendations = newrecommendation.getText().toString();
        //String updateChem = chemedtxt.getText().toString();

        if (!updateRecommendations.isEmpty() ){
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
                                int rowsAffected = dataManager.updateRecord(recId, name, updateRecommendations);

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
                            Log.d(TAG,"Update: No data");
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
}