package com.example.bellpepper_classify;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDataFetcher extends AsyncTask<Void, Void, Void> {
    private Context context;
    private DataManager dataManager;
    private ArrayList<String> recommendsList;

    public FirebaseDataFetcher(Context context) {
        this.context = context;
        this.dataManager = new DataManager(context);
        this.recommendsList = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Fetch data from Firebase
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Recommends");
        databaseReference.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Iterate through the data and add to the ArrayList
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String recommendValue = snapshot.getValue(String.class);
                        recommendsList.add(recommendValue);
                    }

                    // Open SQLite database and insert records
                    dataManager.open();
                    for (String value : recommendsList) {
                        // Check if the record already exists in SQLite
                        if (!dataManager.isRecordExist(value)) {
                            // Insert the record into SQLite
                            dataManager.insertRecord("Recommends", value);
                            Log.d("FirebaseDataFetcher","Value:" + value);
                        }
                    }
                    dataManager.close();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
        return null;
    }
}
