package com.fuel.fuelapplication;

import static com.fuel.fuelapplication.MainActivity.USNAME;
import static com.fuel.fuelapplication.MainActivity.PHONE;
import static com.fuel.fuelapplication.MainActivity.UTYPE;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DBHelper;
import Database.RetrofitClient;
import Models.Methods;
import Models.Model;
import Models.RegisterUserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Admin use this class to add Patrol data to the system
 */
public class AdminPatrolAdd  extends AppCompatActivity {

    private EditText PLiters, PArrival, PDeparture, PQueue;
    private static final String TAG = "AdminPatrolAdd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // REMOVE THE TITLE BAR AND THE ACTION BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.admin_patrol);

        //Declare Edite Text
        PLiters = (EditText)findViewById(R.id.Patrolliters);
        PArrival = (EditText)findViewById(R.id.PatrolarrivalTime);
        PDeparture = (EditText)findViewById(R.id.PatroldepartureTime);
        PQueue = (EditText)findViewById(R.id.PatrolqueueSize);

        Bundle data = getIntent().getExtras();

        //Check LITERS values
        if(data.getString("LITERS")== null)
            PLiters.setText("");
        else
            PLiters.setText(data.getString("LITERS"));
        //Check ARRIVAL values
        if(data.getString("ARRIVAL")== null)
            PArrival.setText("");
        else
            PArrival.setText(data.getString("ARRIVAL"));
        //Check DEPARTURE values
        if(data.getString("DEPARTURE")== null)
            PDeparture.setText("");
        else
            PDeparture.setText(data.getString("DEPARTURE"));
        //Check QUEUE values
        if(data.getString("QUEUE")== null)
            PQueue.setText("");
        else
            PQueue.setText(data.getString("QUEUE"));

        //Declare add patrol button
        Button patrolunavil = findViewById(R.id.patrolunavil);
        patrolunavil.setOnClickListener(v -> {

            String StationNumber = PHONE;

            //  ADD DATA TO THE BACKEND SERVER
            Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
            Call<Model> call = methods.deletePatrol(StationNumber);

            call.enqueue(new Callback<Model>() {
                /*
                   If the process success, then receive onResponse:code::200
                   Navigate to the Admin Panel
                */
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    Toast.makeText(AdminPatrolAdd.this, "Update Data", Toast.LENGTH_SHORT).show();
                    suNavigation();
                }
                /*
                    If the process unsuccessful, then receive onResponse:code::400
                    Display Try Again message to the user
                 */
                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Log.e(TAG, "onFailure:" + t.getMessage());
                    Toast.makeText(AdminPatrolAdd.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        });

        //Declare add Patrol button
        TextView add = findViewById(R.id.PatrolAdd);
        add.setOnClickListener(v -> {

            //Assign inout data to String variables
            String Liters = PLiters.getText().toString();
            String Arrival = PArrival.getText().toString();
            String Departure = PDeparture.getText().toString();
            String Queue = PQueue.getText().toString();

            //Validating the LITERS field
            if(Liters.isEmpty()) {
                Toast.makeText(AdminPatrolAdd.this, "Enter Liters data.", Toast.LENGTH_SHORT).show();
                return;
            }
            //Validating the ARRIVAL field
            if(Arrival.isEmpty()) {
                Toast.makeText(AdminPatrolAdd.this, "Enter Arrival data.", Toast.LENGTH_SHORT).show();
                return;
            }
            //Validating the QUEUE field
            if(Queue.isEmpty()) {
                Toast.makeText(AdminPatrolAdd.this, "Enter Queue data.", Toast.LENGTH_SHORT).show();
                return;
            }
            /*
                Check the LITERS,ARRIVAL,QUEUE fields
                if all fields are true, then start to work add diesel function
             */
            if(
                 data.getString("LITERS")== null && data.getString("ARRIVAL")== null
                && data.getString("QUEUE")== null
            ) {
                String StationName = USNAME;
                String StationNumber = PHONE;
                String StationLocation = UTYPE;

                //  Send data to the backend server to add data
                Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Model> call = methods.addPatrol(Liters, Arrival, Departure, Queue, StationNumber,
                        StationName, StationLocation);

                call.enqueue(new Callback<Model>() {
                    /*
                       If the process success, then receive onResponse:code::200
                       Navigate to the Admin Panel
                      */
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {

                        Toast.makeText(AdminPatrolAdd.this, "Upload Data", Toast.LENGTH_SHORT).show();
                        suNavigation();
                    }
                    /*
                          If the process unsuccessful, then receive onResponse:code::400
                          Display Try Again message to the user
                       */
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.e(TAG, "onFailure:" + t.getMessage());
                        Toast.makeText(AdminPatrolAdd.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
             /*
                Checked LITERS,ARRIVAL,QUEUE fields are false
                then start to work update diesel details function
             */
            else{

                String StationName = USNAME;
                String StationNumber = PHONE;
                String StationLocation = UTYPE;

                //  Add data to backend server to update data
                Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Model> call = methods.updatePatrol(Liters, Arrival, Departure, Queue, StationNumber,
                        StationName, StationLocation);

                call.enqueue(new Callback<Model>() {
                    /*
                        If the process success, then receive onResponse:code::200
                        Navigate to the Admin Panel
                    */
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Toast.makeText(AdminPatrolAdd.this, "Update Data", Toast.LENGTH_SHORT).show();
                        suNavigation();
                    }
                    /*
                        If the process unsuccessful, then receive onResponse:code::400
                        Display Try Again message to the user
                     */
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.e(TAG, "onFailure:" + t.getMessage());
                        Toast.makeText(AdminPatrolAdd.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    /**
     * navigation to the Admin Panel
     */
    private void suNavigation(){
        Intent intent = new Intent(this, AdminHome.class);
        startActivity(intent);
    }
}