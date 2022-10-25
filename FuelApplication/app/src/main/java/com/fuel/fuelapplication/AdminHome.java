package com.fuel.fuelapplication;

import static com.fuel.fuelapplication.MainActivity.PHONE;
import static com.fuel.fuelapplication.MainActivity.USNAME;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import Database.RetrofitClient;
import Models.Methods;
import Models.Model;
import Models.Time;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This AdminHome class helps to display admin panel data to fuel station owners
 */
public class AdminHome  extends AppCompatActivity {
    private static final String TAG = "AdminHome";
    private Model model1;
    private Model model2;
    public static String ARRIVALS=null,CLOSE=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove the Top navigation bar and header
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.admin_home);

        //Declare logout button
        ImageButton logout =findViewById(R.id.logout);
        logout.setOnClickListener(v -> {
            //Redirect to the login activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

         // Navigate to the time details activity
        Button detailsAdd =findViewById(R.id.detailss);
        detailsAdd.setOnClickListener(v -> {
                Intent intent = new Intent(this, AdminDetailsAdd.class);
                startActivity(intent);
        });

        //Declare shed name and assign the shed name
        TextView stationName = (TextView)findViewById(R.id.stationname);
        stationName.setText(USNAME);

        //Declare Shed start time and close time
        TextView StartTime = (TextView)findViewById(R.id.StartTimeField);
        TextView EndTime = (TextView)findViewById(R.id.EndTimeField);

        //Navigate to the add patrol activity
        Button petrolAdd =findViewById(R.id.petroladd);
        petrolAdd.setOnClickListener(v -> {

            //Check the model data to add for the intent
            if(model1 != null) {
                Intent intent = new Intent(this, AdminPatrolAdd.class);
                intent.putExtra("LITERS", model1.getLiters());
                intent.putExtra("ARRIVAL", model1.getArrival());
                intent.putExtra("DEPARTURE", model1.getDeparture());
                intent.putExtra("QUEUE", model1.getQueue());
                startActivity(intent);
            }
            //Assign null values to the intent to send
            else{
                Intent intent = new Intent(this, AdminPatrolAdd.class);
                intent.putExtra("LITERS", "");
                intent.putExtra("ARRIVAL", "");
                intent.putExtra("DEPARTURE","");
                intent.putExtra("QUEUE", "");
                startActivity(intent);
            }
        });

        //Navigation to the diesel activity
        Button Deseladd =findViewById(R.id.dieseladd);
        Deseladd.setOnClickListener(v -> {
            //Check the model data to add to the intent
            if(model2 != null) {
                Intent intent = new Intent(this, AdminDeselAdd.class);
                intent.putExtra("LITERS", model2.getLiters());
                intent.putExtra("ARRIVAL", model2.getArrival());
                intent.putExtra("DEPARTURE", model2.getDeparture());
                intent.putExtra("QUEUE", model2.getQueue());
                startActivity(intent);
            }
            //Assign null values for intent data
            else{
                Intent intent = new Intent(this, AdminDeselAdd.class);
                intent.putExtra("LITERS","");
                intent.putExtra("ARRIVAL", "");
                intent.putExtra("DEPARTURE", "");
                intent.putExtra("QUEUE", "");
                startActivity(intent);
            }
        });

        //Get and assign Primary phone number to get time details
        String StationNumber = PHONE;
         Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Time> call= methods.getTime(StationNumber);
                call.enqueue(new Callback<Time>() {
                    /*
                        If the process success, then receive onResponse:code::200
                        Navigate to the Admin Panel
                     */
                    @Override
                    public void onResponse(Call<Time> call, Response<Time> response) {
                        Log.e(TAG, "onResponse:code Time:"+ response.code());
                        Log.e(TAG, "onResponse:Body Time:"+ response.body());
                        Time time = response.body();

                         //check and set ARRIVAL TIME
                        if( time.getArrival() == null)
                            ARRIVALS = null;
                        else
                            ARRIVALS = time.getArrival().toString();
                        //Check and set DEPARTURE TIME
                        if( time.getDeparture()== null)
                            CLOSE = null;
                        else
                            CLOSE = time.getDeparture().toString();

                        //Assign values for the Text views
                        StartTime.setText(time.getArrival()+" "+"p.m");
                        EndTime.setText(time.getDeparture()+" "+"p.m");
                    }

                    // If the process success, then receive onResponse:code::400
                    @Override
                    public void onFailure(Call<Time> call, Throwable t) {
                        Log.e(TAG, "onFailure Time:"+ t.getMessage());
                    }
              });
        //Declare PatrolLiters and Patrol Queue
        TextView PtLiters = (TextView)findViewById(R.id.patrolLiters);
        TextView ptQueue = (TextView)findViewById(R.id.patrolQueue);

        //Get and assign Primary phone number to the local variable
        String StationNumberDesel = PHONE;
        Methods method1 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call1= method1.getPatrol(StationNumberDesel);
        call1.enqueue(new Callback<Model>() {
            /*
             If the process success, then receive onResponse:code::200
            Navigate to the Admin Panel
          */
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Log.e(TAG, "onResponse:code Patrol:"+ response.code());
                Log.e(TAG, "onResponse:Body Patrol:"+ response.body());
                Model model = response.body();

                model1 = model;

                Log.e(TAG, "onResponse:Body:"+ model.getDeparture());
                Log.e(TAG, "onResponse:Body:"+ model.getQueue());
                Log.e(TAG, "onResponse:Body:"+ model.getLiters());

                //ASSIGN VALUES FOR VIEWS
                    if(model.getLiters() !=null){
                        PtLiters.setText(model.getLiters().toString());
                        ptQueue.setText(model.getQueue().toString());
                    }
            }
            // If the process success, then receive onResponse:code::400
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onFailure Patrol:"+ t.getMessage());
            }
        });

        //Declare the Diesel Liters and Diesel Queue
        TextView DtLiters = (TextView)findViewById(R.id.DeselLiters);
        TextView DtQueue = (TextView)findViewById(R.id.DeselQueue);

        //Send data to the backend server to get Diesel data
        Methods method2 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call2= method2.getDesel(StationNumber);
        call2.enqueue(new Callback<Model>() {
            /*
             If the process success, then receive onResponse:code::200
            Navigate to the Admin Panel
            */
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                //CHECK SHED STATUS AND BODY DATA
                Log.e(TAG, "onResponse:code Desel:"+ response.code());
                Log.e(TAG, "onResponse:Body Desel:"+ response.body());
                Model model1 = response.body();

                model2= model1;
                //Assign values for text views
                if(model1.getLiters() !=null){
                    DtLiters.setText(model1.getLiters().toString());
                    DtQueue.setText(model1.getQueue().toString());
                }
            }
            // If the process success, then receive onResponse:code::400
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "onFailure Desel:"+ t.getMessage());
            }
        });
    }
}
