package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccountActivity extends AppCompatActivity {
    

    private TextView usernameTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        usernameTxt=findViewById(R.id.savedUsernameTxt);
        passwordTxt=findViewById(R.id.hashPasswordTxt);


        SharedPreferences login = getSharedPreferences("UserInfo", 0);
        usernameTxt.setText(login.getString("username", ""));

        String pass="";

        for (int i = 0; i < login.getString("password", "").length(); i++)
        {
            pass=pass+"*";
        }

        passwordTxt.setText(pass);




    }

    public void onChangePasswordBtn_Clicked(View caller)
    {

        NewPasswordFragment dialogFragment = new NewPasswordFragment();
        dialogFragment.show(getSupportFragmentManager(), "My Fragment");
        /*


        Intent intent = new Intent(this, NewPasswordActivity.class);
        startActivity(intent);*/
    }

    public void onLogOutBtn_Clicked(View caller)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}