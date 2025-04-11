package com.example.bellpepper_classify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Editrecommendation extends AppCompatActivity {

    Button newyellowleaf, newleafcurl, newpowderymildew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editrecommendation);

        init();

        newyellowleaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Editrecommendation.this, New.class);
                intent.putExtra("id", "yellowleaf");
                startActivity(intent);
            }
        });

        newleafcurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Editrecommendation.this, New.class);
                intent1.putExtra("id", "leafcurl");
                startActivity(intent1);
            }
        });

        newpowderymildew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Editrecommendation.this, New.class);
                intent2.putExtra("id", "powderymildew");
                startActivity(intent2);
            }
        });
    }


    private void init(){
        newyellowleaf = findViewById(R.id.newYellowLeaf);
        newleafcurl = findViewById(R.id.newleafcurl);
        newpowderymildew = findViewById(R.id.newpowderymildew);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Editrecommendation.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}