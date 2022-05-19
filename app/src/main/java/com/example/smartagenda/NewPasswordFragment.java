package com.example.smartagenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

public class NewPasswordFragment extends DialogFragment {

    private EditText newPassword;
    private EditText confirmNew;
    private Button update;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_new_password, container, false);


        newPassword = (EditText) rootView.findViewById(R.id.newPasswordIn);
        confirmNew = (EditText) rootView.findViewById(R.id.newPasswordIn2);
        update = (Button) rootView.findViewById(R.id.updatePasswordBtn);

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                {
                    if (newPassword.getText().toString().matches("")
                            || confirmNew.getText().toString().matches(""))
                    {

                        Toast.makeText(rootView.getContext(),  "Fill in all fields before saving.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (!newPassword.getText().toString().equals(confirmNew.getText().toString()))
                        {
                            Toast.makeText(rootView.getContext(),  "Passwords are different.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (newPassword.getText().toString().length()<10)
                            {
                                Toast.makeText(rootView.getContext(),  "Password too short.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {

                                SharedPreferences login = rootView.getContext().getSharedPreferences("UserInfo", 0);
                                String username = login.getString("username", "").toString();


                                requestQueue = Volley.newRequestQueue(rootView.getContext());
                                String requestURL = "https://studev.groept.be/api/a21pt308/new_password/"+newPassword.getText().toString()+"/"+username;
                                JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        String info = "";
                                        for (int i=0; i<response.length(); ++i) {
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
                                        Toast.makeText(rootView.getContext(), "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                                    }
                                });

                                requestQueue.add(submitRequest);


                                SharedPreferences.Editor editor = login.edit();
                                editor.putString("password",newPassword.getText().toString());
                                editor.commit();

                                Toast.makeText(rootView.getContext(), "Password successfully changed", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), AccountActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });



        return rootView;
    }

    /*
    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(750, 800);
        window.setGravity(Gravity.CENTER);
    }*/

}