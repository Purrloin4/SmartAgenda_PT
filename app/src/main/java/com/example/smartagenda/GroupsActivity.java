package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

    }

    public void onNewGroup_Clicked (View caller)
    {
        NewGroupFragment dialogFragment = new NewGroupFragment();
        dialogFragment.show(getSupportFragmentManager(), "My Fragment");
    }
}