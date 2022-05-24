package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button logInBtn;
    private Button signUpBtn;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }


    public void onSignUpBtn_Clicked(View caller)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onLogInBtn_Clicked(View caller)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}


