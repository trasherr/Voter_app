package com.rkgit.voter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import io.reactivex.rxjava3.annotations.NonNull;


public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "shared_prefs";

    private static boolean election_start;
    private static String password;

    List<MainData> dataList = new ArrayList<>();
    List<Voter> dataList2 = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;
    VoterAdapter adapter2;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        election_start = sharedPreferences.getBoolean("election",false);
        password = sharedPreferences.getString("password","0x0");


        activity_main();
    }

    private int totalVotes(){

        int sum = 0 ;

        for (int i = 0 ; i < dataList.size() ; i++) {
            sum = sum + dataList.get(i).getVotes();
        }
        return sum;
    }

    private int can_max_votes(){

        int max = 0;
        int max_index = 0;

        for (int i = 0 ; i < dataList.size() ; i++) {
            if (dataList.get(i).getVotes() > max)
                max_index = i;
        }
        return max_index;

    }

    public void addVoter(){
        setContentView(R.layout.voter_add);

        EditText editText = findViewById(R.id.editTextTextPersonName);
        Button bt_add,bt_exit,bt_submit;
        bt_add = findViewById(R.id.button23);
        bt_exit = findViewById(R.id.button25);
        bt_submit = findViewById(R.id.bt_submit);

        RecyclerView recyclerView =findViewById(R.id.recycler_view);

        database = RoomDB.getInstance(this);
        dataList2 = database.voterDoa().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter2 = new VoterAdapter(MainActivity.this, dataList2);
        recyclerView.setAdapter(adapter2);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText = editText.getText().toString().trim();

                if (!sText.equals("")){
                    Voter data = new Voter();
                    data.setVoter(sText);
                    data.setVote(true);

                    database.voterDoa().insert(data);

                    editText.setText("");
                    dataList2.clear();
                    dataList2.addAll(database.voterDoa().getAll());
                    adapter2.notifyDataSetChanged();
                }
            }
        });

        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.voterDoa().reset(dataList2);

                dataList2.clear();
                dataList2.addAll(database.voterDoa().getAll());
                adapter2.notifyDataSetChanged();
            }
        });

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_loggedin();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_admin:
                        admin_layout();

                        return true;

                    case R.id.nav_voter:
                        voter();
                        return true;

                    case R.id.nav_results:
                        activity_main();
                        return true;
                }

                return false;
            }
        });

    }

    public void addCandidate(){

        setContentView(R.layout.admin_add2);

        EditText editText = findViewById(R.id.editTextTextPersonName);
        Button bt_add,bt_exit,bt_submit;
        bt_add = findViewById(R.id.button23);
        bt_exit = findViewById(R.id.button25);
        bt_submit = findViewById(R.id.bt_submit);

        RecyclerView recyclerView =findViewById(R.id.recycler_view);

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);

         bt_add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String sText = editText.getText().toString().trim();

                 if (!sText.equals("")){
                     MainData data = new MainData();
                     data.setCandidate(sText);

                     database.mainDao().insert(data);

                     editText.setText("");
                     dataList.clear();
                     dataList.addAll(database.mainDao().getAll());
                     adapter.notifyDataSetChanged();
                 }
             }
         });

         bt_exit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 database.mainDao().reset(dataList);

                 dataList.clear();
                 dataList.addAll(database.mainDao().getAll());
                 adapter.notifyDataSetChanged();
             }
         });
         
         bt_submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 admin_loggedin();
             }
         });

        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_admin:
                        admin_layout();

                        return true;

                    case R.id.nav_voter:
                        voter();
                        return true;

                    case R.id.nav_results:
                        activity_main();
                        return true;
                }

                return false;
            }
        });


    }

    public void activity_main(){




        setContentView(R.layout.activity_main);
        Button admin,voter;
        admin =  findViewById(R.id.Admin);
        voter =  findViewById(R.id.Voter);



        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


                    voter();

                }
            });
        }


        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_admin:

                        admin_layout();

                        return true;

                    case R.id.nav_voter:

                        voter();
                        return true;

                    case R.id.nav_results:

                        activity_main();
                        return true;
                }

                return false;
            }
        });

    }

    public void admin_layout(){


        setContentView(R.layout.admin_layout);
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

                        SharedPreferences sharedPreferences = getSharedPreferences(PREFS,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("password",password);
                        editor.commit();
                        admin_loggedin();

                }

                else {
                    passwd = password_field.getText().toString();
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
                    password = sharedPreferences.getString("password", "0x0");


                    if (passwd.equals(password)) {


                        stop_election();

                    } else if (passwd.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter the password !",
                                Toast.LENGTH_SHORT).show();

                    } else {

                            Toast.makeText(MainActivity.this, "Incorrect Password",
                                    Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                activity_main();

            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_admin:
                        admin_layout();

                        return true;

                    case R.id.nav_voter:
                        voter();
                        return true;

                    case R.id.nav_results:
                        activity_main();
                        return true;
                }

                return false;
            }
        });
    }

    public void admin_loggedin(){
        setContentView(R.layout.admin_loggedin);



        if (!election_start) {
            Button add_button1, start_election,add_button2;
            Button[] candidates_button = new Button[20];


            ViewGroup layout_button = findViewById(R.id.layout_container_buttons);
            for (byte i = 0; i < 20; i++) {
                candidates_button[i] = findViewById(getResources().getIdentifier("button" +
                        i, "id", this.getPackageName()));
                View child = layout_button.getChildAt(i);
                if (child instanceof Button) {
                    Button button = (Button) child;
                    if (i < dataList.size()) {
                        button.setText(dataList.get(i).getCandidate());
                    } else {
                        button.setVisibility(View.GONE);
                    }
                }
            }
            add_button1 = findViewById(R.id.add_candidate);
            add_button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCandidate();
                }
            });

            start_election = findViewById(R.id.start_election);
            start_election.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataList.size() < 2)
                        Toast.makeText(MainActivity.this, "There should be atleast 2 "+
                                "candidates !", Toast.LENGTH_SHORT).show();

                    else
                        start_election();

                }
            });
            add_button2 = findViewById(R.id.add_voter);
            add_button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addVoter();
                }
            });
        }
        else {

            stop_election();
        }


        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_admin:
                        admin_layout();

                        return true;

                    case R.id.nav_voter:
                        voter();
                        return true;

                    case R.id.nav_results:
                        activity_main();
                        return true;
                }

                return false;
            }
        });
    }

    public void stop_election(){

        setContentView(R.layout.stop_election);
        Button back,stop_election;
        TextView _votecasted,no_;

        _votecasted=findViewById(R.id._votecasted);
        _votecasted.setText("Vote Casted : ");
        no_=findViewById(R.id.no_);
        no_.setText("No. of candidates : "+(dataList.size()));

        back=findViewById(R.id.stop_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                activity_main();
            }
        });

        stop_election=findViewById(R.id.stop_eletion_button);
        stop_election.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                election_start=false;
                SharedPreferences sharedPreferences = getSharedPreferences(PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("election",false);
                editor.commit();

                result();

            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_admin:
                        item.setIcon(item.getItemId());
                        admin_layout();

                        return true;

                    case R.id.nav_voter:
                        voter();
                        return true;

                    case R.id.nav_results:
                        activity_main();
                        return true;
                }

                return false;
            }
        });

    }

    public void start_election(){

        election_start=true;
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("election",true);
        editor.commit();
        stop_election();

    }

    public void voter(){

        setContentView(R.layout.voter_login);
        Button cancel,submit;
        EditText id;

        cancel = findViewById(R.id.button21);
        submit = findViewById(R.id.button);
        id = findViewById(R.id.editTextTextPassword);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_main();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0 ; i < dataList2.size() ; i++ ){
                    if(id.getText().toString().equals(dataList2.get(i).getVoter())){

                        if( dataList2.get(i).getVote()){
                            dataList2.get(i).setVote(false);
                            voterLoggedin();

                        }
                        else
                            Toast.makeText(MainActivity.this,"You have already voted !",
                                    Toast.LENGTH_SHORT).show();


                    }
                    else
                    Toast.makeText(MainActivity.this,"Wrong id !",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_admin:
                        admin_layout();

                        return true;

                    case R.id.nav_voter:
                        voter();
                        return true;

                    case R.id.nav_results:
                        activity_main();
                        return true;
                }

                return false;
            }
        });


    }

    public void voterLoggedin(){
        setContentView(R.layout.voter_view);
        Button[] voter_button = new Button[20];

        ViewGroup layout_button = findViewById(R.id.voter_layout_container_buttons);
        for (byte i = 0; i < 20; i++) {
            voter_button[i] = findViewById(getResources().getIdentifier("candidate_button" +
                    i, "id", this.getPackageName()));
            View child = layout_button.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                if (i < dataList.size()) {
                    button.setText(dataList.get(i).getCandidate());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int i;
                            for (i = 0 ; i<dataList.size() ; i++){

                                if(dataList.get(i).getCandidate().equals(button.getText().toString()))
                                    break;
                            }
                            dataList.get(i).setVotes((dataList.get(i).getVotes()+1));

                            activity_main();

                        }
                    });

                } else {
                    button.setVisibility(View.GONE);
                }
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.nav_admin:
                        admin_layout();

                        return true;

                    case R.id.nav_voter:
                        voter();
                        return true;

                    case R.id.nav_results:
                        activity_main();
                        return true;
                }

                return false;
            }
        });

    }

    public void result(){

        setContentView(R.layout.result_layout);

        Button[] candidate_result = new Button[20];
        Button result_back;
        Date stop_time = new Date();
        TextView total_vote_casted,max_vote_candidate,total_time;
        int max=0;
        String max_candidate="Candidated with Max votes : " + dataList.get(can_max_votes()-1).getCandidate() ;

        result_back=findViewById(R.id.result_back);
        total_vote_casted=findViewById(R.id.total_vote_casted);
        total_time=findViewById(R.id.total_time);
        max_vote_candidate=findViewById(R.id.Max_vote_candidate);

        total_vote_casted.setText("Total Vote Casted : " + totalVotes());


        max_vote_candidate.setText(max_candidate);

        ViewGroup layout_button = findViewById(R.id.result_layout_container_buttons);
        for (byte i = 0; i < 20; i++) {
            candidate_result[i] = findViewById(getResources().getIdentifier("result_button" +
                    i, "id", this.getPackageName()));
            View child = layout_button.getChildAt(i);
            if (child instanceof Button) {
                Button button = (Button) child;
                if (i < dataList.size()) {
                    button.setText(dataList.get(i).getCandidate()+"\nVotes : "+dataList.get(i).getVotes());
                } else {
                    button.setVisibility(View.GONE);
                }
            }

            BottomNavigationView bottomNav = findViewById(R.id.nav_bar);

            bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){

                        case R.id.nav_admin:
                            admin_layout();

                            return true;

                        case R.id.nav_voter:
                            voter();
                            return true;

                        case R.id.nav_results:
                            activity_main();
                            return true;
                    }

                    return false;
                }
            });
        }

        result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Press again to go back !",
                        Toast.LENGTH_SHORT).show();
                result_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.mainDao().reset(dataList);
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        adapter.notifyDataSetChanged();
                        
                        activity_main();
                    }
                });
            }
        });

    }
}