package com.fuel.fuelapplication;

import static com.fuel.fuelapplication.Home.hKey;
import static com.fuel.fuelapplication.MainActivity.PHONE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.RetrofitClient;
import Models.Methods;
import Models.Model;
import Models.Time;
import Models.UserQueue;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Display selected shed details
 * fuel types and available times
 */
public class Shed  extends AppCompatActivity {
    private static final String TAG = "Shed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove the title bar and the header action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_shed);

        //Declare Image Views
        ImageView userPatrolbt = findViewById(R.id.userPatrolbt);
        ImageView userDeselbt = findViewById(R.id.userDeselbt);

        Bundle data = getIntent().getExtras();

        //Check Shed Primary key
        if( hKey==null){
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

        //Declare logout button
        ImageButton logout =findViewById(R.id.logout3);
        logout.setOnClickListener(v -> {
            //Redirect to the Home activity
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        });

        //pass primary key to other methods
        deselData(hKey);
        TimeData(hKey);
        patrolData(hKey);
        UserData(PHONE);

        //Patrol Navigation button
        userPatrolbt.setOnClickListener(v -> {
            pNavigation(hKey);
        });

        //Diesel Navigation button
        userDeselbt.setOnClickListener(v -> {
            dNavigation(hKey);
        });
    }

    /**
     * Get shed time data from the backend database MongoDB
     * @param data
     */
    public void UserData(String data){

        //Declare Text Views
        TextView arrivTimetoQue = findViewById(R.id.arrivTimetoQue);
        TextView departureTimetoQue = (TextView)findViewById(R.id.departureTimetoQue);
        TextView fuelTypeShed = (TextView)findViewById(R.id.fuelTypeShed);


        //send a reques to the backend server to get time data
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<UserQueue> call = methods.UserData(data);

        call.enqueue(new Callback<UserQueue>() {
            /*
                If the process success, then receive onResponse:code::200
                Assign retrieved values to the Text Views
             */
            @Override
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
                Log.e(TAG, "onResponse:code Patrol:"+ response.code());
                Log.e(TAG, "onResponse:Body Patrol:"+ response.body());

                UserQueue queue = response.body();

                //Check the User queue object
                if(queue ==null){
                    arrivTimetoQue.setText("N/A");
                    departureTimetoQue.setText("N/A");
                }
                //If user queue object is not empty, then start to assign retrieved data to the text views
                else{
                    arrivTimetoQue.setText(queue.getArrival());
                    departureTimetoQue.setText(queue.getDeparture());
                    fuelTypeShed.setText(queue.getFuel());
                }
            }
            /*
               If the process success, then receive onResponse:code::400
             */
            @Override
            public void onFailure(Call<UserQueue> call, Throwable t) {
                Log.e(TAG, "onFailure Patrol:"+ t.getMessage());
            }
        });
    }

    /**
     * Get shed patrol data from the backend database MongoDB
     * @param data
     */
    public void patrolData(String data){

        //Declare Text Views
        TextView ptliters = (TextView)findViewById(R.id.ptliters);
        TextView ptavalable = (TextView)findViewById(R.id.ptavalable);
        ImageView userPatrolbt = findViewById(R.id.userPatrolbt);

        //send a request to the backend server to get patrol data::MONGODB
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.shedPatrol(data);

        call.enqueue(new Callback<Model>() {
            /*
                If the process success, then receive onResponse:code::200
                Assign retrieved values to the Text Views
             */
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.e(TAG, "onResponse:code Patrol:"+ response.code());
                Log.e(TAG, "onResponse:Body Patrol:"+ response.body());

                Model model1 = response.body();
                ptliters.setText(model1.getLiters());

                //Check the model1.getLiters() data
                if(model1.getLiters() == null){
                    userPatrolbt.setEnabled(false);
                    ptavalable.setText("Unavailable");
                }else{
                    ptavalable.setText("Available");
                }
            }
            /*
                If the process Unsuccessful, then receive onResponse:code::400
                If patrol data is unavailable, then disable the patrol button
                and display unavailable message to the user
              */
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onFailure Patrol:"+ t.getMessage());
                userPatrolbt.setEnabled(false);
                ptavalable.setText("Unavailable");
            }
        });
    }
    /**
     * Get shed Diesel data from the backend database::MONGODB
     * @param data
     */
    public void deselData(String data){
    //Declare Text Views
    TextView dsliters = (TextView)findViewById(R.id.dsliters);
    TextView dsavailable = (TextView)findViewById(R.id.dsavailable);
    ImageView userDeselbt = findViewById(R.id.userDeselbt);

    //Get diesel data from the backend database::MONGODB
    Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
    Call<Model> call = methods.shedDesel(data);

    call.enqueue(new Callback<Model>() {
        /*
           If the process success, then receive onResponse:code::200
           Assign retrieved values to the Text Views
       */
        @Override
        public void onResponse(Call<Model> call, Response<Model> response) {
            Log.e(TAG, "onResponse:code Desel:"+ response.code());
            Log.e(TAG, "onResponse:Body Desel:"+ response.body());
            Model model = response.body();
            dsliters.setText(model.getLiters());

            //Check the model1.getLiters() data
            if(model.getLiters() ==null){
                userDeselbt.setEnabled(false);
                dsavailable.setText("UnAvailable");
            }else{
                dsavailable.setText("Available");
            }
        }
        /*
            If the process Unsuccessful, then receive onResponse:code::400
            If Diesel data is unavailable, then disable the Diesel button
            and display unavailable message to the user
          */
        @Override
        public void onFailure(Call<Model> call, Throwable t) {
            Log.e(TAG, "onFailure Desel:"+ t.getMessage());
            userDeselbt.setEnabled(false);
            dsavailable.setText("UnAvailable");
        }
    });
    }
    /**
     * Get shed Time data from the backend database::MONGODB
     * @param data
     */
    public void TimeData(String data){

        //Declare Text Views
        TextView timeClose = (TextView)findViewById(R.id.timeClose);
        TextView timeOpen = (TextView)findViewById(R.id.timeOPen);

        //Send a request to the backend to get time data:: MONGODB
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Time> call= methods.getTime(data);
        call.enqueue(new Callback<Time>() {
            /*
               If the process success, then receive onResponse:code::200
               Assign retrieved values to the Text Views
            */
            @Override
            public void onResponse(Call<Time> call, Response<Time> response) {
                Log.e(TAG, "onResponse:code Time:"+ response.code());
                Log.e(TAG, "onResponse:Body Time:"+ response.body());
                Time time = response.body();

                //Check the time.getArrival() data
                if( time.getArrival() == null)
                    timeOpen.setText("");
                else
                    timeOpen.setText(time.getArrival()+"  " + "p.m");

                if( time.getDeparture()== null)
                    timeClose.setText("");
                else
                    timeClose.setText(time.getDeparture()+"  "+ "p.m");
            }
            /*
                If the process Unsuccessful, then receive onResponse:code::400
            */
            @Override
            public void onFailure(Call<Time> call, Throwable t) {
                Log.e(TAG, "onFailure Time:"+ t.getMessage());
            }
        });
    }

    //Navigation to the Patrol Shed activity
    public void pNavigation(String pKey){
        Intent fuelData = new Intent(this, ShedPatrol.class);
        fuelData.putExtra("pKey",pKey);
        startActivity(fuelData);
    }

    //Navigation to the Shed Diesel actitivty
    public void dNavigation(String pKey){
        Intent fuelData = new Intent(this, ShedDesel.class);
        fuelData.putExtra("pKey",pKey);
        startActivity(fuelData);
    }
}