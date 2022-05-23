package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class GroupsActivity extends AppCompatActivity {
    public static ArrayList<String> groupNames = new ArrayList<>();
    public ArrayList<String> memberNames = new ArrayList<>();
    private RequestQueue requestQueue;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        if(groupNames.isEmpty()){
            getGroups();
        }
        setGroupView();
    }

    private void setGroupView()
    {
        RecyclerView groupRecyclerView = findViewById(R.id.groupRecyclerView);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupRecyclerView.setAdapter(new GroupAdapter(getApplicationContext(),groupNames));
    }

    private void getGroups() {

        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        username = login.getString("username", "");
        requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt308/groups_per_user/"+username+"/"+username;


        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";
                for (int i=0; i<response.length(); ++i)
                {
                    JSONObject o = null;
                    try {
                        o = response.getJSONObject(i);
                        groupNames.add(o.get("team_name").toString());

                        if(i == response.length()-1){
                            setGroupView();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GroupsActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);
    }



    public void onNewGroup_Clicked (View caller)
    {
        NewGroupFragment dialogFragment = new NewGroupFragment();
        dialogFragment.show(getSupportFragmentManager(), "My Fragment");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AgendaScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}