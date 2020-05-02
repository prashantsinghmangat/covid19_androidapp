package com.example.covid19_androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class screen_patient_home extends Fragment {
    Button button;
    Button button1;
    private Spinner spinnerMhe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_patient_home, container, false);

        button = (Button) view.findViewById(R.id.registerpatient);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), Patient_registration.class);
                startActivity(intent);
            }
        });
        /*button1 = (Button) view.findViewById(R.id.withoutregister);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), patient_health_details.class);
                startActivity(intent);
            }
        });*/

        spinnerMhe = view.findViewById(R.id.spinner);

        List<String> categories = new ArrayList<>();
        categories.add(0,"Protocol A");
        categories.add("Protocol K-001");
        categories.add("Protocol K-002");
        categories.add("Protocol K-003");


        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerMhe.setAdapter(dataAdapter);

        spinnerMhe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("MHE/OP*"))
                {
                    //do nothing
                }
                else
                {
                    //on selecting spinner item
                    String item = parent.getItemAtPosition(position).toString();

                    //show spinner selected item
                    Toast.makeText(parent.getContext(), ""  + item, Toast.LENGTH_SHORT).show();
                    if(parent.getItemAtPosition(position).equals("metro"));
                    {
                        //Intent intent = new Intent(Loginform.this, Loginform.class);
                        //startActivity(intent);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
}