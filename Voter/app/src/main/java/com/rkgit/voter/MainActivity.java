package com.rkgit.voter;

import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private static String[] candidates = new String[20];
    private static String[] slogans = new String[20];
    private static int[] votes = new int[20];
    private static byte no=0;
    private static int vote_casted=0;
    private static boolean election_start=false;
    private static Date start_time;

    private static  String password;
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
        if (!election_start){
            voter.setVisibility(View.GONE);
        }
        else {
            Date timer_s=new Date();
            voter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Date timer_e = new Date();
                    long pause = timer_e.getTime()-timer_s.getTime();
                    if(pause>10000) {
                        setContentView(R.layout.voter_view);
                        voter();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Wait for 10 Seconds to caste next vote",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public void admin_layout(){

        Button admin_login,cancel;
        TextView login_text;
        login_text=findViewById(R.id.login_text);
        admin_login =  findViewById(R.id.admin_login_button);
        cancel =  findViewById(R.id.admin_cancel_button);

        if(election_start)
            login_text.setText("Login");

        else
            login_text.setText("Set");

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText password_field;
                String passwd="";

                password_field = findViewById(R.id.Password);

                if (!election_start) {
                    password = password_field.getText().toString();

                    if (password.length()<8)
                        Toast.makeText(MainActivity.this,"The password must be atleast of 8 characters !",Toast.LENGTH_SHORT).show();
                    else
                        passwd=password;
                }

                else{
                    passwd=password_field.getText().toString();
                }

                if ( passwd.equals(password) ){
                    if (!election_start) {
                        setContentView(R.layout.admin_loggedin);
                        admin_loggedin();
                    }
                    else{
                        setContentView(R.layout.stop_election);
                        stop_election();
                    }
                }
                else if (passwd.isEmpty() && election_start)
                {
                    Toast.makeText(MainActivity.this,"Please enter the password !",Toast.LENGTH_SHORT).show();

                }
                else if (passwd.length()<8 && election_start) {
                    Toast.makeText(MainActivity.this,"Password is too short !",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(election_start)
                        Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
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
        if (!election_start) {
            Button add_button, start_election;
            Button[] candidates_button = new Button[20];

            ViewGroup layout_button = findViewById(R.id.layout_container_buttons);
            for (byte i = 0; i < 20; i++) {
                candidates_button[i] = findViewById(getResources().getIdentifier("button" + i, "id", this.getPackageName()));
                View child = layout_button.getChildAt(i);
                if (child instanceof Button) {
                    Button button = (Button) child;
                    if (i < no) {
                        button.setText(candidates[i]);
                    } else {
                        button.setVisibility(View.GONE);
                    }
                }
            }
            add_button = findViewById(R.id.add_candidate);
            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.add_candidate);
                    add_candidate();
                }
            });

            start_election = findViewById(R.id.start_election);
            start_election.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (no < 2)
                        Toast.makeText(MainActivity.this, "There should be atleast 2 candidates !", Toast.LENGTH_SHORT).show();

                    else
                        start_election();

                }
            });
        }
        else {
            setContentView(R.layout.stop_election);
            stop_election();
        }
    }
    public void add_candidate(){

        Button add_details = findViewById(R.id.add_details);
        add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _slogan,candidate_name;
                _slogan = findViewById(R.id._slogan);
                candidate_name = findViewById((R.id.candidate_name));
                if(no<=20) {
                    candidates[no] = candidate_name.getText().toString();
                    slogans[no] = _slogan.getText().toString();
                    if (!slogans[no].isEmpty()) {
                        no++;
                        setContentView(R.layout.admin_loggedin);
                        admin_loggedin();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"No. of candidates has reached its limit\ncannot add any more !",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void stop_election(){
        Button back,stop_election;
        TextView _votecasted,no_;
        _votecasted=findViewById(R.id._votecasted);
        _votecasted.setText("Vote Casted : "+vote_casted);
        no_=findViewById(R.id.no_);
        no_.setText("No. of candidates : "+(no));

        back=findViewById(R.id.stop_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                activity_main();
            }
        });

        stop_election=findViewById(R.id.stop_eletion_button);
        stop_election.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                election_start=false;
                setContentView(R.layout.result_layout);
                result();

            }
        });

    }

    public void start_election(){
        start_time = new Date();
        setContentView(R.layout.stop_election);
        election_start=true;
        stop_election();
    }

    public void voter(){

        Button[] voter_button = new Button[20];

        ViewGroup layout_button = findViewById(R.id.voter_layout_container_buttons);
        for (byte i = 0; i < 20; i++) {
            voter_button[i] = findViewById(getResources().getIdentifier("candidate_button" + i, "id", this.getPackageName()));
            View child = layout_button.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                if (i < no) {
                    button.setText(candidates[i]);// +"\n" );//+ slogans[i]);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            byte j=0;
                            for (String i : candidates){
                                j++;
                                if(i.equals(button.getText().toString()))
                                    break;
                            }
                            votes[j-1]++;
                            vote_casted++;
                            setContentView(R.layout.activity_main);
                            activity_main();

                        }
                    });

                } else {
                    button.setVisibility(View.GONE);
                }
            }
        }

    }

    public void result(){

        Button[] candidate_result = new Button[20];
        Button result_back;
        Date stop_time = new Date();
        TextView total_vote_casted,max_vote_candidate,total_time;
        int max=0;
        String max_candidate="Candidated with Max votes : "+candidates[0]+" ("+votes[0]+")";

        result_back=findViewById(R.id.result_back);
        total_vote_casted=findViewById(R.id.total_vote_casted);
        total_time=findViewById(R.id.total_time);
        max_vote_candidate=findViewById(R.id.Max_vote_candidate);

        total_vote_casted.setText("Total Vote Casted : "+vote_casted);

        long difference_In_Time = stop_time.getTime() - start_time.getTime();

        long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;

        long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;

        long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;

        total_time.setText("Total Time :"+difference_In_Hours+":"+difference_In_Minutes+":"+difference_In_Seconds);

        for (byte i = 0; i<no;i++){
            if (votes[i]>max) {
                max = votes[i];
                max_candidate="Candidated with Max votes : "+candidates[i]+" ("+votes[i]+")";
            }

        }

        max_vote_candidate.setText(max_candidate);

        ViewGroup layout_button = findViewById(R.id.result_layout_container_buttons);
        for (byte i = 0; i < 20; i++) {
            candidate_result[i] = findViewById(getResources().getIdentifier("result_button" + i, "id", this.getPackageName()));
            View child = layout_button.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                if (i < no) {
                    button.setText(candidates[i]+"\nVotes : "+votes[i]);
                } else {
                    button.setVisibility(View.GONE);
                }
            }
        }

        result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Press again to go back !",Toast.LENGTH_SHORT).show();
                result_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_main);
                        activity_main();
                    }
                });
            }
        });

    }
}