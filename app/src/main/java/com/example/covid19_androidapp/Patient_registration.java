package com.example.covid19_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Patient_registration extends AppCompatActivity {
    Button button;
    EditText phone_number,address,patient_name, age, gender, state, pincode;
    Button btnLogin;
    TextView Cancel;
    private ProgressBar spinner;

    final String url_Login = "http://68.183.246.238:8080/appserver/covidAppServer/createPatient?useruuid="+GlobalValues.userUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        Cancel = (TextView) findViewById(R.id.Cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Patient_registration.this, Homepage_nav.class);
                startActivity(intent);
            }
        });

        age = (EditText) findViewById(R.id.Age);
        gender= (EditText) findViewById(R.id.gender);
        state= (EditText) findViewById(R.id.State);
        pincode= (EditText) findViewById(R.id.pincode);
        btnLogin = (Button) findViewById(R.id.btn_login);
        patient_name = (EditText) findViewById(R.id.Patient_name);
        phone_number = (EditText) findViewById(R.id.phone_number);
        address = (EditText) findViewById(R.id.Address);

        button = (Button) findViewById(R.id.start_screening);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((age.getText().toString().trim().equals(""))) {
                    age.setError("Field can't be empty");
                    Toast.makeText(getApplicationContext(), "Age is empty", Toast.LENGTH_SHORT).show();

                }
                else if ((gender.getText().toString().trim().equals(""))) {
                    gender.setError("Field can't be empty");
                    Toast.makeText(getApplicationContext(), "Gender is empty", Toast.LENGTH_SHORT).show();

                }else if ((state.getText().toString().trim().equals(""))) {
                    state.setError("Field can't be empty");
                    Toast.makeText(getApplicationContext(), "State is empty", Toast.LENGTH_SHORT).show();

                } else {
                    spinner.setVisibility(View.VISIBLE);
                    String Patient_name = patient_name.getText().toString();
                    String Age = age.getText().toString();
                    String Gender = gender.getText().toString();
                    String Phone_number = phone_number.getText().toString();
                    String Address = address.getText().toString();
                    String State = state.getText().toString();
                    String Pincode = pincode.getText().toString();

                    new LoginUser().execute(Patient_name, Age, Gender, Phone_number, Address, State, Pincode);
                }
            }
        });spinner.setVisibility(View.GONE);



        final CheckBox showPasswordCheckBox = (CheckBox) findViewById(R.id.checkbox);
        showPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPasswordCheckBox.isChecked()){
                    patient_name.setEnabled(false);
                    patient_name.setText("Anonymous");
                }else{
                    patient_name.setVisibility(View.VISIBLE);
                    patient_name.setEnabled(true);
                    patient_name.setText("");
                }
            }
        });
    }

    public class LoginUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String Patient_name = strings[0];
            String Age = strings[1];
            String Gender = strings[2];
            String Phone_number = strings[3];
            String Address = strings[4];
            String State = strings[5];
            String Pincode = strings[6];

            OkHttpClient okHttpClient = new OkHttpClient();

            final MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            JSONObject jsonObjectResult = null;
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("address", Address);
                jsonObject.put("age", Age);
                jsonObject.put("gender", Gender);
                jsonObject.put("name", Patient_name);
                jsonObject.put("phoneNumber", Phone_number);
                jsonObject.put("pincode", Pincode);
                jsonObject.put("state", State);
            }catch (Exception e){
                e.printStackTrace();
            }

            RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

            SharedPreferences sharedPref = getBaseContext().getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
//            SharedPreferences sharedPref = (LoginPage.this);
//            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            String username = GlobalValues.userUUID;
            String role = GlobalValues.role;
            String personID = GlobalValues.personID;
            String sessionToken = GlobalValues.sessionToken;
            String userUUID = sharedPref.getString("userUUID", "ANONYMOUS");
            Request request = new Request.Builder()
                    .url(url_Login)
                    .post(formBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", GlobalValues.sessionToken)
                    .build();

            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                ResponseBody rb = response.body();
                if(response.code() != 200){
                    showToast("Email or Password mismatched!");
                    return "Email or Password mismatched!";
                } else {
                    String result = rb.string();
                    System.out.println("Response = " + result);
                    if(response.code() == 200)
                    {
                        showToast("Patient created successfully.");
                    }

                    System.out.println("done code");
                    Intent i = new Intent(Patient_registration.this,
                            patient_health_details.class);
                    startActivity(i);
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }


    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Patient_registration.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });spinner.setVisibility(View.VISIBLE);
    }
}


 /*passwordEditView = (EditText) findViewById(R.id.Patient_name);
        phone_number = (EditText) findViewById(R.id.phone_number);
        address = (EditText) findViewById(R.id.Address);
        final CheckBox showPasswordCheckBox = (CheckBox) findViewById(R.id.checkbox);
        showPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPasswordCheckBox.isChecked()){
                    passwordEditView.setVisibility(View.GONE);
                    phone_number.setVisibility(View.GONE);
                    address.setVisibility(View.GONE);
                    //passwordEditView.setTransformationMethod(null);
                }else{

                    passwordEditView.setVisibility(View.VISIBLE);
                    phone_number.setVisibility(View.VISIBLE);
                    address.setEnabled(true);
                    //passwordEditView.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });*/