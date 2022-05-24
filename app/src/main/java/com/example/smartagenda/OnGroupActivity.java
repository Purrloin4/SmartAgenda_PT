package com.example.smartagenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private Button add;
    private TextView title;
    private ArrayList<String> membersAL = new ArrayList<>();
    private String allMembersExceptCreator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_group_click);

        member1TV = findViewById(R.id.member1TV);
        membersTV = findViewById(R.id.membersTV);
        leaveGroup = findViewById(R.id.leaveButton);
        deleteGroup = findViewById(R.id.deleteButton);
        add = findViewById(R.id.addBtn);
        title=findViewById(R.id.groupMembersTxt);

        allMembersExceptCreator="";

        deleteGroup.setVisibility(View.INVISIBLE);
        add.setVisibility(View.INVISIBLE);
        leaveGroup.setVisibility(View.INVISIBLE);


        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        username = login.getString("username", "");


        if (getIntent().hasExtra("groupPosition")){
            myGroup = getIntent().getStringExtra("groupPosition");
        }
        getMembers(myGroup);
        getMember1(myGroup);
        setMemberView();

        title.setText(myGroup);


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
                                leaveGroup.setVisibility(View.VISIBLE);
                            }
                            else {
                                deleteGroup.setVisibility(View.VISIBLE);
                                add.setVisibility(View.VISIBLE);
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
                                allMembersExceptCreator=allMembersExceptCreator+o.get("members").toString();
                                j++;
                            }
                            else {
                                membersS = membersS + o.get("members").toString();
                                allMembersExceptCreator=allMembersExceptCreator+", "+o.get("members").toString();
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


    public void onLeaveGroupClicked(View view)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to leave this group?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        getMembers(myGroup);
                        SharedPreferences login = getSharedPreferences("UserInfo", 0);
                        username = login.getString("username", "");

                        requestQueue = Volley.newRequestQueue(OnGroupActivity.this);
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

                        Intent intent = new Intent(OnGroupActivity.this, GroupsActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void onDeleteGroupClicked(View view)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this group ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
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

                        Intent intent = new Intent(OnGroupActivity.this, GroupsActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GroupsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onAddMemberBtn_Clicked(View caller)
    {

        AddMemberFragment dialogFragment = new AddMemberFragment(myGroup, allMembersExceptCreator, memberOne);
        dialogFragment.show(getSupportFragmentManager(), "My Fragment");

    }
}
