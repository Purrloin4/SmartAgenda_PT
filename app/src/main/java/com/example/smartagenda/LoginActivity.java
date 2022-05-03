package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button logIn;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logIn = (Button) findViewById(R.id.logIn2Btn);
        username = (EditText) findViewById(R.id.username2In);
        password = (EditText) findViewById(R.id.password3In);
    }

    public void onLogIn2Btn_Clicked(View caller)
    {
        if (!username.getText().toString().matches("")
                &&!password.getText().toString().matches(""))
        {
            Intent intent = new Intent(this, AgendaScreen.class);
            startActivity(intent);
        }
        else
        {
            if (username.getText().toString().matches("")
                    || password.getText().toString().matches(""))
            {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }

        }
    }

}