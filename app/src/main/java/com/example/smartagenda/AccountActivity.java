package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void onChangePasswordBtn_Clicked(View caller)
    {
        NewPasswordFragment dialogFragment = new NewPasswordFragment();
        dialogFragment.show(getSupportFragmentManager(), "My Fragment");
    }

    public void onLogOutBtn_Clicked(View caller)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}