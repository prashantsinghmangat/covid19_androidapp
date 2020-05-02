package com.example.covid19_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginPage extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    private ProgressBar spinner;

    final String url_Login = "http://68.183.246.238:8080/appserver/covidAppServer/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((etEmail.getText().toString().trim().equals("")))
                {
                    Toast.makeText(getApplicationContext(), "User name is empty", Toast.LENGTH_SHORT).show();

                }else if ((etPassword.getText().toString().trim().equals("")))
                {
                    Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT).show();

                }else {
                    spinner.setVisibility(View.VISIBLE);
                    String Email = etEmail.getText().toString();
                    String Password = etPassword.getText().toString();

                    new LoginUser().execute(Email, Password);
                }
            }
        });
    }

    public class LoginUser extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... strings) {

            String Email = strings[0];
            String Password = strings[1];
            String organisation = "CovidCDSSMaster ";
            String tenant = "Covidcdss";

            OkHttpClient okHttpClient = new OkHttpClient();

            final MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            JSONObject jsonObjectResult = null;
            JSONObject jsonObject = new JSONObject();

            try{
                jsonObject.put("username", Email);
                jsonObject.put("password", Password);
                jsonObject.put("organisation", "CovidCDSSMaster");
                jsonObject.put("tenant","Covidcdss");
            }catch (Exception e){
                e.printStackTrace();
            }

            RequestBody formBody = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url_Login)
                    .post(formBody)
                    .addHeader("Content-Type", "application/json")
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

                    JSONObject loginToken = new JSONObject(result);
                    JSONObject sessionInfo = loginToken.getJSONObject("sessionInfo");
                    SharedPreferences sharedPreferences = getSharedPreferences("LOGIN_PREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    GlobalValues.sessionToken = sessionInfo.getString("token");
                    GlobalValues.username = loginToken.getString("username");
                    GlobalValues.userUUID = loginToken.getString("uuid");
                    GlobalValues.personID = loginToken.getString("personId");
                    GlobalValues.role = loginToken.getString("role");
//                    editor.putString("username", loginToken.getString("username"));
//                    editor.putString("userUUID", loginToken.getString("uuid"));
//                    editor.putString("personId", loginToken.getString("personId"));
//                    editor.putString("role", loginToken.getString("role"));
//                    editor.putString("sessionToken", sessionInfo.getString("token"));

                    System.out.println("Response = " + result);
                    System.out.println("done code");
                    Intent i = new Intent(LoginPage.this,
                            Homepage_nav.class);
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
                Toast.makeText(LoginPage.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });spinner.setVisibility(View.GONE);
    }
}