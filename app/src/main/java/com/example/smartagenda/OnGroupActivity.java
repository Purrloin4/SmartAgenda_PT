package com.example.smartagenda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class OnGroupActivity extends GroupsActivity{
    private RequestQueue requestQueue;
    private String username;
    private String myGroup;
    private String member1;
    private String memberOne;
    private String membersS;
    private TextView member1TV, membersTV;
    private Button leaveGroup;
    private Button deleteGroup;
    private ArrayList<String> membersAL = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_group_click);

        member1TV = findViewById(R.id.member1TV);
        membersTV = findViewById(R.id.membersTV);
        leaveGroup = findViewById(R.id.leaveButton);
        deleteGroup = findViewById(R.id.deleteButton);

        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        username = login.getString("username", "");


        if (getIntent().hasExtra("groupPosition")){
            myGroup = getIntent().getStringExtra("groupPosition");
        }
        getMembers(myGroup);
        getMember1(myGroup);
        setMemberView();




    }

    private void setMemberView()
    {
        member1TV.setText(member1);
        membersTV.setText(membersS);
    }



    private void getMember1(String group) {
        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        username = login.getString("username", "");
        requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt308/get_member1/"+username+"/"+username;


        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for (int i=0; i<response.length(); ++i)
                {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        if(o.get("team_name").equals(group)) {
                            member1 = ("Group creator: " + o.get("member1").toString());
                            memberOne = o.get("member1").toString();
                        }
                        if(i == response.length()-1){
                            setMemberView();

                            if (!memberOne.equals(username)) {
                                deleteGroup.setVisibility(View.INVISIBLE);
                            }
                            else {
                                leaveGroup.setVisibility(View.INVISIBLE);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnGroupActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);


    }

    private void getMembers(String group) {
        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        username = login.getString("username", "");
        requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt308/members_of_group/"+username+"/"+username;




        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                int j =0;
                for (int i=0; i<response.length(); ++i)
                {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        if(o.get("team_name").equals(group)) {
                            if(j == 0){
                                membersS = "Other members: " + o.get("members").toString();
                                j++;
                            }
                            else {
                                membersS = membersS + o.get("members").toString();
                            }
                            membersAL.add(o.get("members").toString());
                        }
                        if(i == response.length()-1){
                            setMemberView();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnGroupActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);


    }


    public void onLeaveGroupClicked(View view) {
        getMembers(myGroup);
        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        username = login.getString("username", "");
        requestQueue = Volley.newRequestQueue(this);
        int i = 0;
        for(String member:membersAL){
            if (member.equals(username)){
                membersAL.remove(i);
            }
            i++;
        }
        for (int j = 0; j<groupNames.size();j++){
            if(groupNames.get(j).equals(myGroup)){
                groupNames.remove(j);
                j--;
            }
        }

        if(username.equals(memberOne)){
            memberOne = null;
        }
        String new_members = String.join(",", membersAL);

        String requestURL = "https://studev.groept.be/api/a21pt308/leave_group/"+new_members+"/"+memberOne+"/"+myGroup;


        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnGroupActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);

        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

    public void onDeleteGroupClicked(View view){


        for (int j = 0; j<groupNames.size();j++){
            if(groupNames.get(j).equals(myGroup)){
                groupNames.remove(j);
                j--;
            }
        }


        String requestURL = "https://studev.groept.be/api/a21pt308/delete_group/"+myGroup;


        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnGroupActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);

        Intent intent = new Intent(this, GroupsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GroupsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
