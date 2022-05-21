package com.example.smartagenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.ArrayList;


public class NewGroupFragment extends DialogFragment {


    private Button save;
    private Button add;
    private EditText memberIn;
    private EditText groupName;
    private TextView newMember;
    private int counter;
    private RequestQueue requestQueue;
    private String members;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_new_group, container, false);

        members= "";

        counter = 0;
        groupName=rootView.findViewById(R.id.groupNameIn);
        add = rootView.findViewById(R.id.addMemberBtn);
        memberIn = rootView.findViewById(R.id.memberIn);
        newMember = rootView.findViewById(R.id.userTxt);
        newMember.setVisibility(View.INVISIBLE);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                requestQueue = Volley.newRequestQueue(rootView.getContext());

                String requestURL = "https://studev.groept.be/api/a21pt308/usernames";
                JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int counter2 = 0;
                        for (int i=0; i<response.length(); ++i) {
                            JSONObject o = null;
                            try {
                                o = response.getJSONObject(i);
                                if (o.get("username").equals(memberIn.getText().toString()))
                                {
                                    counter2++;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (counter2==0)
                        {
                            Toast.makeText(rootView.getContext(), "There is no account under that username", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (counter==0)
                            {
                                members=members+memberIn.getText().toString();
                                newMember.setText(members);
                                newMember.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                members=members+ ", " +memberIn.getText().toString();
                                newMember.setText(members);
                            }
                            counter++;
                        }
                        //txtInfo.setText(info);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(rootView.getContext(), "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                    }
                });

                requestQueue.add(submitRequest);

                /*

                if (counter==0)
                {
                    newMember.setText(memberIn.getText().toString());
                    newMember.setVisibility(View.VISIBLE);
                }
                else
                {
                    newMember.setText(newMember.getText().toString()+ ", " +memberIn.getText().toString());
                }
                counter++;*/
            }
        });

        save = (Button) rootView.findViewById(R.id.createGroupBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SharedPreferences login = rootView.getContext().getSharedPreferences("UserInfo", 0);
                String username = login.getString("username", "");
                requestQueue = Volley.newRequestQueue(rootView.getContext());

                String requestURL = "https://studev.groept.be/api/a21pt308/teamnames";
                JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int counter = 0;
                        for (int i=0; i<response.length(); ++i) {
                            JSONObject o = null;
                            try {
                                o = response.getJSONObject(i);
                                if (o.get("team_name").equals(groupName.getText().toString()))
                                {
                                    counter++;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if (counter==0)
                        {
                            String requestURL2 = "https://studev.groept.be/api/a21pt308/add_team/"+groupName.getText().toString()+"/"+username+"/"+members;
                            JsonArrayRequest submitRequest2 = new JsonArrayRequest(Request.Method.GET,requestURL2,null,new Response.Listener<JSONArray>() {
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

                            requestQueue.add(submitRequest2);


                            Intent intent = new Intent(getActivity(), GroupsActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(rootView.getContext(), "This group name is already used.", Toast.LENGTH_SHORT).show();
                        }
                        //txtInfo.setText(info);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(rootView.getContext(), "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                    }
                });

                requestQueue.add(submitRequest);


            }
        } );

        return rootView;
    }
/*
    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(750, 800);
        window.setGravity(Gravity.CENTER);
    }

 */
}