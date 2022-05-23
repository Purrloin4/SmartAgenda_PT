package com.example.smartagenda;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class EventAdapter extends BaseAdapter {

    private Context context;
    private static ArrayList<Event> EventArrayList;
    private static Event  event;

    public EventAdapter(@NonNull Context context, ArrayList<Event> events)
    {
        this.context = context;
        this.EventArrayList = events;
    }

    public void remove(int position) {


        String description = EventArrayList.get(position).getDescription();
        String startTime = EventArrayList.get(position).getStartTime();
        String endTime = EventArrayList.get(position).getEndTime();
        String date = EventArrayList.get(position).getDate();

        SharedPreferences login = context.getSharedPreferences("UserInfo", 0);
        String username = login.getString("username", "");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a21pt308/delete_task/"+username+"/"+description+"/"+startTime+"/"+endTime+"/"+date;

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET,requestURL,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String info = "";

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(submitRequest);

        EventArrayList.remove(position);
        notifyDataSetChanged();

    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getCount() {
        return EventArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return EventArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        String description = EventArrayList.get(position).getDescription();
        String startTime = EventArrayList.get(position).getStartTime();
        String endTime = EventArrayList.get(position).getEndTime();
        String date = EventArrayList.get(position).getDate();
        Boolean isAllDay = EventArrayList.get(position).isAllDay();
        Boolean informativeTask = EventArrayList.get(position).isInformative();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false);
        }

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        String formattedTime;
        if (isAllDay){
            formattedTime = "all day";
        }
        else{
            formattedTime = startTime +"-"+ endTime;
        }
        String eventTitle = description +" \u2022 "+ formattedTime;
        if(informativeTask){
            eventTitle = description;
        }
        eventCellTV.setText(eventTitle);
        return convertView;
    }


}
