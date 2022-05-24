package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button logIn;
    private EditText username;
    private EditText password;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logIn = (Button) findViewById(R.id.logIn2Btn);
        username = (EditText) findViewById(R.id.username2In);
        password = (EditText) findViewById(R.id.password3In);
    }

    public void onLogIn2Btn_Clicked(View caller)
    {

        if (username.getText().toString().matches("")
                || password.getText().toString().matches(""))
        {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        }
        else
        {
            requestQueue = Volley.newRequestQueue(this);
            String requestURL = "https://studev.groept.be/api/a21pt308/logins";

            JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    String info = "";
                    int counter = 0;
                    String passwordFound = "";
                    for (int i=0; i<response.length(); ++i) {
                        JSONObject o = null;
                        try {
                            o = response.getJSONObject(i);
                            if (o.get("username").equals(username.getText().toString()))
                            {
                                counter++;
                                passwordFound= (String) o.get("password");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (counter==0)
                    {
                        Toast.makeText(LoginActivity.this, "There is no account under that username.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (!passwordFound.equals(password.getText().toString()))
                        {
                            Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            SharedPreferences login = getSharedPreferences("UserInfo", 0);
                            SharedPreferences.Editor editor = login.edit();
                            editor.putString("username",username.getText().toString());
                            editor.putString("password",password.getText().toString());
                            editor.commit();

                            Toast.makeText(LoginActivity.this, "You are now logged in.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, AgendaScreenActivity.class);
                            startActivity(intent);
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(submitRequest);
        }

    }

}