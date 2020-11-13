package com.rkgit.voter;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageButton;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
                setContentView(R.layout.admin_loggedin);
                admin_loggedin();

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
        ImageButton cross_button;
        cross_button = findViewById(R.id.cross_button);

        cross_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.admin_layout);
                admin_layout();

            }
        });
    }
}