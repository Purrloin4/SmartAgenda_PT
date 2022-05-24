package com.example.smartagenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class NewGroupFragment extends DialogFragment {


    private Button save;
    private Button add;
    private EditText memberIn;
    private EditText groupName;
    private TextView newMember;
    private int counter;
    private RequestQueue requestQueue;
    private String members, username;


    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_new_group, container, false);

        members= "";
        SharedPreferences login = rootView.getContext().getSharedPreferences("UserInfo", 0);
        username = login.getString("username", "");

        counter = 0;
        groupName=rootView.findViewById(R.id.groupNameIn);
        add = rootView.findViewById(R.id.addMemberBtn);
        memberIn = rootView.findViewById(R.id.memberIn2);
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
                            Toast.makeText(rootView.getContext(), "There is no account under that username.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (!members.contains(memberIn.getText().toString()))
                            {
                                if (!memberIn.getText().toString().equals(username))
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
                                }
                                else
                                {
                                    Toast.makeText(rootView.getContext(), "You don't have to add yourself in the group manually.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(rootView.getContext(), "You can't add the same member twice.", Toast.LENGTH_SHORT).show();
                            }

                            counter++;
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(rootView.getContext(), "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                    }
                });

                requestQueue.add(submitRequest);

            }
        });

        save = (Button) rootView.findViewById(R.id.createGroupBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(rootView.getContext(), "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                                }
                            });

                            requestQueue.add(submitRequest2);

                            GroupsActivity.groupNames.add(groupName.getText().toString());


                            Intent intent = new Intent(getActivity(), GroupsActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(rootView.getContext(), "This group name is already used.", Toast.LENGTH_SHORT).show();
                        }
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

}