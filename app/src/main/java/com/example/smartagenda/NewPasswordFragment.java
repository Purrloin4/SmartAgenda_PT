package com.example.smartagenda;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewPasswordFragment extends DialogFragment {

    private EditText newPassword;
    private EditText confirmNew;
    private Button update;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_new_password, container, false);


        newPassword = (EditText) rootView.findViewById(R.id.newPasswordIn);
        confirmNew = (EditText) rootView.findViewById(R.id.newPasswordIn2);
        update = (Button) rootView.findViewById(R.id.updatePasswordBtn);

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                {
                    if (newPassword.getText().toString().matches("")
                            || confirmNew.getText().toString().matches(""))
                    {

                        Toast.makeText(rootView.getContext(),  "Fill in all fields before saving.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (!newPassword.getText().toString().equals(confirmNew.getText().toString()))
                        {
                            Toast.makeText(rootView.getContext(),  "Passwords are different.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (newPassword.getText().toString().length()<10)
                            {
                                Toast.makeText(rootView.getContext(),  "Password too short.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent intent = new Intent(getActivity(), AccountActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });



        return rootView;
    }

    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(750, 800);
        window.setGravity(Gravity.CENTER);
    }

}