package com.example.smartagenda;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class AddMemberFragment extends DialogFragment {

    private String group, members;
    private Button add;
    private EditText memberToAdd;
    private RequestQueue requestQueue;
    private String username;

    public AddMemberFragment(String group, String members, String username)
    {
        this.group=group;
        this.members=members;
        this.username=username;

    }


    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_add_member, container, false);

        add = rootView.findViewById(R.id.addMemberBtn);
        memberToAdd = rootView.findViewById(R.id.memberIn2);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if (!memberToAdd.getText().toString().matches(""))
                {
                    if (!members.contains(memberToAdd.getText().toString()) && !memberToAdd.getText().toString().equals(username))
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
                                        if (o.get("username").equals(memberToAdd.getText().toString()))
                                        {
                                            counter2++;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (counter2==1)
                                {
                                    members=members+", "+memberToAdd.getText().toString();
                                    String requestURL2 = "https://studev.groept.be/api/a21pt308/insertNewMembers/"+members+"/"+group;
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

                                    Toast.makeText(rootView.getContext(), "A new member was successfully added to your group.", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getActivity(), OnGroupActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(rootView.getContext(), "There is no account under that username.", Toast.LENGTH_SHORT).show();
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
                    else
                    {
                        Toast.makeText(rootView.getContext(), "You can't add a member to a group more than once.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(rootView.getContext(), "Fill in the new member's username before saving.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

}

