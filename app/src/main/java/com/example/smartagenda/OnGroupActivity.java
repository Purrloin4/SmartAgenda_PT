package com.example.smartagenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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


public class OnGroupActivity extends GroupsActivity{
    private RequestQueue requestQueue;
    private String username;
    private String myGroup;
    private String member1;
    private String membersS;
    private TextView member1TV, membersTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_group_click);

        member1TV = findViewById(R.id.member1TV);
        membersTV = findViewById(R.id.membersTV);

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
}
