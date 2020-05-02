package com.example.covid19_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Feedback extends AppCompatActivity {
    Button button;
    TextView backfromfeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        button = (Button) findViewById(R.id.finalsubmit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Feedback.this, finalsumit.class);
                startActivity(intent);
            }
        });
        backfromfeedback = (TextView) findViewById(R.id.back_from_feedback);
        backfromfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Feedback.this, patient_health_details.class);
                startActivity(intent);
            }
        });
    }
}
