package com.example.covid19_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class patient_health_details extends AppCompatActivity {
    Button button;
    TextView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_health_screening);

        button = (Button) findViewById(R.id.nexttopagetwo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(patient_health_details.this, Feedback.class);
                startActivity(intent);
            }
        });

        backbutton = (TextView) findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(patient_health_details.this, Homepage_nav.class);
                startActivity(intent);
            }
        });
    }
}
