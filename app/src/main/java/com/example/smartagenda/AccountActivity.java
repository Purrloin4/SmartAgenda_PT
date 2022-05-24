package com.example.smartagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
    }

    public void onLogOutBtn_Clicked(View caller)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        finish();
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
}