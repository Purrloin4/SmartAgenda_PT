package com.example.smartagenda;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class NewGroupFragment extends DialogFragment {


    private Button save;
    private Button add;
    private EditText memberIn;
    private TextView newMember;
    private int counter;

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_new_group, container, false);

        counter = 0;
        add = rootView.findViewById(R.id.addMemberBtn);
        memberIn = rootView.findViewById(R.id.memberIn);
        newMember = rootView.findViewById(R.id.userTxt);
        newMember.setVisibility(View.INVISIBLE);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if (counter==0)
                {
                    newMember.setText(memberIn.getText().toString());
                    newMember.setVisibility(View.VISIBLE);
                }
                else
                {
                    newMember.setText(newMember.getText().toString()+ ", " +memberIn.getText().toString());
                }
                counter++;
            }
        });

        save = (Button) rootView.findViewById(R.id.createGroupBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), GroupsActivity.class);
                startActivity(intent);
            }
        } );

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