package com.fuel.fuelapplication;

import static com.fuel.fuelapplication.MainActivity.PHONE;
import static com.fuel.fuelapplication.MainActivity.USNAME;
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
 * Admin use this class to add diesel data to the system
 */
public class AdminDeselAdd  extends AppCompatActivity {

    private EditText DLiters, DArrival, DQueue,DDeparture;
    private static final String TAG = "AdminDeselAdd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the action bar and the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.admin_disel);

        //Get IDs from the admin desel activity
        DLiters = findViewById(R.id.Deselliters);
        DArrival = findViewById(R.id.DeselarrivalTime);
        DDeparture = findViewById(R.id.DeseldepartureTime);
        DQueue = findViewById(R.id.DeselQueueSize);

        Bundle data = getIntent().getExtras();

        //Check LITERS values
        if(data.getString("LITERS")==null)
            DLiters.setText("");
        else
            DLiters.setText(data.getString("LITERS"));
        //Check ARRIVAL values
        if(data.getString("ARRIVAL")==null)
            DArrival.setText("");
        else
            DArrival.setText(data.getString("ARRIVAL"));
        //Check DEPARTURE values
        if(data.getString("DEPARTURE")==null)
            DDeparture.setText("");
        else
            DDeparture.setText(data.getString("DEPARTURE"));
        //Check QUEUE values
        if(data.getString("QUEUE")==null)
            DQueue.setText("");
        else
            DQueue.setText(data.getString("QUEUE"));

        //Declare add diesel button
        TextView add = findViewById(R.id.deseladd);
        add.setOnClickListener(v -> {
            //Assign inout data to String variables
            String Liters = DLiters.getText().toString();
            String Arrival = DArrival.getText().toString();
            String Queue = DQueue.getText().toString();
            String Departure= DDeparture.getText().toString();

            //Validating the LITERS field
            if(Liters.isEmpty()) {
                Toast.makeText(AdminDeselAdd.this, "Enter Liters data.", Toast.LENGTH_SHORT).show();
                return;
            }
            //Validating the ARRIVAL field
            if(Arrival.isEmpty()) {
                Toast.makeText(AdminDeselAdd.this, "Enter Arrival data.", Toast.LENGTH_SHORT).show();
                return;
            }
            //Validating the QUEUE field
            if(Queue.isEmpty()) {
                Toast.makeText(AdminDeselAdd.this, "Enter Queue data.", Toast.LENGTH_SHORT).show();
                return;
            }

            /*
                Check the LITERS,ARRIVAL,QUEUE fields
                if all fields are true, then start to work add diesel function
             */
            if(
                    data.getString("LITERS")== null &&
                            data.getString("ARRIVAL") == null &&
                            data.getString("QUEUE")== null
            ) {
                String StationName = USNAME;
                String StationNumber = PHONE;
                String StationLocation = UTYPE;

                //  Send data to the backend server to add data
                Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Model> call = methods.addDesel(Liters, Arrival, Departure, Queue, StationNumber,
                        StationName, StationLocation);

                call.enqueue(new Callback<Model>() {
                    /*
                        If the process success, then receive onResponse:code::200
                        Navigate to the Admin Panel
                     */
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {

                        Toast.makeText(AdminDeselAdd.this, "Upload Data", Toast.LENGTH_SHORT).show();
                        suNavigation();
                    }
                    /*
                        If the process unsuccessful, then receive onResponse:code::400
                        Display Try Again message to the user
                     */
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.e(TAG, "onFailure:" + t.getMessage());
                        Toast.makeText(AdminDeselAdd.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            /*
                Check the LITERS,ARRIVAL,QUEUE fields are false
                then start to work update diesel details function
             */
            else{
                String StationName = USNAME;
                String StationNumber = PHONE;
                String StationLocation = UTYPE;

                //  Add data to backend server to update data
                Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Model> call = methods.updateDesel(Liters, Arrival, Departure, Queue, StationNumber,
                        StationName, StationLocation);

                call.enqueue(new Callback<Model>() {
                    /*
                    If the process success, then receive onResponse:code::200
                    Navigate to the Admin Panel
                 */
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {
                        Toast.makeText(AdminDeselAdd.this, "Update Data", Toast.LENGTH_SHORT).show();
                        suNavigation();
                    }
                    /*
                        If the process unsuccessful, then receive onResponse:code::400
                        Display Try Again message to the user
                     */
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Log.e(TAG, "onFailure:" + t.getMessage());
                        Toast.makeText(AdminDeselAdd.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Diesel unavailable feature
        TextView deselunavil = findViewById(R.id.deselunavil);
        deselunavil.setOnClickListener(v -> {
            String StationNumber = PHONE;

            //  Remove data from the backend database
            Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
            Call<Model> call = methods.deleteDesel(StationNumber);

            call.enqueue(new Callback<Model>() {
                /*
                     If the process success, then receive onResponse:code::200
                     Navigate to the Admin Panel
                 */
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    Toast.makeText(AdminDeselAdd.this, "Update Data", Toast.LENGTH_SHORT).show();
                    suNavigation();
                }
                /*
                    If the process unsuccessful, then receive onResponse:code::400
                    Display Try Again message to the user
                 */
                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Log.e(TAG, "onFailure:" + t.getMessage());
                    Toast.makeText(AdminDeselAdd.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });

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