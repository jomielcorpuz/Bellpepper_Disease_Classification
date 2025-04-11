package com.example.bellpepper_classify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class About extends AppCompatActivity {

    ImageButton HButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        HButton = findViewById(R.id.HButton);

        HButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { MainActivity();}

        });

    }

    private  void MainActivity(){
        Intent intent = new Intent(About.this,MainActivity.class);
        startActivity(intent);
    }

}