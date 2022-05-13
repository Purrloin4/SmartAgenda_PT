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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewPasswordActivity extends AppCompatActivity {


    private EditText newPassword;
    private EditText confirmNew;
    private Button update;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        newPassword = (EditText) findViewById(R.id.newPasswordIn);
        confirmNew = (EditText) findViewById(R.id.newPasswordIn2);
        update = (Button) findViewById(R.id.updatePasswordBtn);

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                {
                    if (newPassword.getText().toString().matches("")
                            || confirmNew.getText().toString().matches("")) {

                        Toast.makeText(NewPasswordActivity.this, "Fill in all fields before saving.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!newPassword.getText().toString().equals(confirmNew.getText().toString())) {
                            Toast.makeText(NewPasswordActivity.this, "Passwords are different.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (newPassword.getText().toString().length() < 10) {
                                Toast.makeText(NewPasswordActivity.this, "Password too short.", Toast.LENGTH_SHORT).show();
                            } else {

                                SharedPreferences login = getSharedPreferences("UserInfo", 0);
                                String username = login.getString("username", "");


                                String requestURL = "https://studev.groept.be/api/a21pt308/new_password/" + newPassword.getText().toString() + "/" + username;
                                JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        String info = "";
                                        for (int i = 0; i < response.length(); ++i) {
                                            JSONObject o = null;
                                            try {
                                                o = response.getJSONObject(i);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(NewPasswordActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                                    }
                                });

                                requestQueue.add(submitRequest);


                                Intent intent = new Intent(NewPasswordActivity.this, AccountActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }

        });
    }
}