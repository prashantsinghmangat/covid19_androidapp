package com.example.covid19_androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button sign_in_register;
    private RequestQueue requestQueue;
    private static final String URL = "http://68.183.246.238:8080/appserver/covidAppServer/login";
    private StringRequest request;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        sign_in_register = (Button) findViewById(R.id.sign_in_register);

        requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, URL, new JSONObject(new LoginActivity().getParams()), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){
                                Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Welcome.class));
                            }else {
                                Toast.makeText(getApplicationContext(), "Error" +jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("username",email.getText().toString());
                        hashMap.put("password",password.getText().toString());
                        hashMap.put("organisation","CovidCDSSMaster");
                        hashMap.put("tenant","Covidcdss");

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });
    }
}


