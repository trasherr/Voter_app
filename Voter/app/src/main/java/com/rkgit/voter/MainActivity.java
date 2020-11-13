package com.rkgit.voter;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] candidates = new String[20];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main();

    }
    public void activity_main(){
        Button admin,voter;
        admin =  findViewById(R.id.Admin);
        voter =  findViewById(R.id.Voter);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.admin_layout);
                admin_layout();
            }
        });

        voter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.admin_layout);
                admin_layout();
            }
        });

    }

    public void admin_layout(){

        Button admin_login,cancel;
        admin_login =  findViewById(R.id.admin_login_button);
        cancel =  findViewById(R.id.admin_cancel_button);

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id,password;
                String uname,passwd;
                id = findViewById(R.id.uname);
                password = findViewById(R.id.Password);
                uname=id.getText().toString();
                passwd=password.getText().toString();

                if (uname.equals("test") && passwd.equals("password")){
                    setContentView(R.layout.admin_loggedin);
                    admin_loggedin();
                }
                if (uname.isEmpty() || passwd.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please enter credentials !",Toast.LENGTH_SHORT);

                }
                if (uname.length()<3 || passwd.length()<8) {
                    Toast.makeText(MainActivity.this,"ID or Password is too short !",Toast.LENGTH_SHORT);
                }
                else {
                    Toast.makeText(MainActivity.this, "Incorrect ID or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                activity_main();

            }
        });
    }

    public void admin_loggedin(){
        Button add_button;

        add_button = findViewById(R.id.add_candidate);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}