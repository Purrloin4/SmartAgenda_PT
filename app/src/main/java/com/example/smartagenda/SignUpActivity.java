package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.usernameIn2);
        password = (EditText) findViewById(R.id.passwordIn);
        confirmPassword = (EditText) findViewById(R.id.password2In);

        save = (Button) findViewById(R.id.saveBtn1);

    }

    public void onSave1Btn_Clicked(View caller)
    {
        if (username.getText().toString().matches("")
                ||password.getText().toString().matches("")
                ||confirmPassword.getText().toString().matches(""))
        {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (!password.getText().toString().equals(confirmPassword.getText().toString()))
            {
                Toast.makeText(this, "Passwords are different", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (password.getText().toString().length()<10)
                {
                    Toast.makeText(this, "Passwords too short", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(this, AgendaScreenActivity.class);
                    startActivity(intent);
                }
            }

        }

        /*
        if (!username.getText().toString().matches("")
                &&!password.getText().toString().matches("")
                &&!confirmPassword.getText().toString().matches("")
                && password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            Intent intent = new Intent(this, AgendaScreenActivity.class);
            startActivity(intent);
        }
        else
        {
            if (username.getText().toString().matches("")
                    || password.getText().toString().matches("")
                    || confirmPassword.getText().toString().matches(""))
            {
                Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }
            if (!password.getText().toString().equals(confirmPassword.getText().toString()))
            {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }

        }*/


    }
}