package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class SignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Button save;
    private RequestQueue requestQueue, requestQueue2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.usernameIn2);
        password = (EditText) findViewById(R.id.passwordIn);
        confirmPassword = (EditText) findViewById(R.id.password2In);

        save = (Button) findViewById(R.id.saveBtn1);

    }

    public void onSave1Btn_Clicked(View caller)
    {
        if (username.getText().toString().matches("")
                ||password.getText().toString().matches("")
                ||confirmPassword.getText().toString().matches(""))
        {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (!password.getText().toString().equals(confirmPassword.getText().toString()))
            {
                Toast.makeText(this, "Passwords are different", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (password.getText().toString().length()<10)
                {
                    Toast.makeText(this, "Passwords too short", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    requestQueue = Volley.newRequestQueue(this);
                    String requestURL = "https://studev.groept.be/api/a21pt308/usernames";

                    JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            String info = "";
                            int counter = 0;
                            for (int i=0; i<response.length(); ++i) {
                                JSONObject o = null;
                                try {
                                    o = response.getJSONObject(i);
                                    if (o.get("username").equals(username.getText().toString()))
                                    {
                                        counter++;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (counter==1)
                            {
                                Toast.makeText(SignUpActivity.this, "This username is taken.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                //enter new user in database

                                String requestURL2 = "https://studev.groept.be/api/a21pt308/new_user/"+username.getText().toString()+"/"+password.getText().toString();
                                JsonArrayRequest submitRequest2 = new JsonArrayRequest(Request.Method.GET,requestURL2,null,new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(SignUpActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                                    }
                                });

                                requestQueue.add(submitRequest2);

                                SharedPreferences login = getSharedPreferences("UserInfo", 0);
                                SharedPreferences.Editor editor = login.edit();
                                editor.putString("username",username.getText().toString());
                                editor.putString("password",password.getText().toString());
                                editor.commit();

                                Toast.makeText(SignUpActivity.this, "Your are now logged in with your new account.", Toast.LENGTH_LONG).show();


                                Intent intent = new Intent(SignUpActivity.this, AgendaScreenActivity.class);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignUpActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                        }
                    });

                    requestQueue.add(submitRequest);

                }
            }

        }


    }
}