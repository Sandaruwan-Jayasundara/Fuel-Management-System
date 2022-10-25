package com.fuel.fuelapplication;

import static com.fuel.fuelapplication.AdminHome.ARRIVALS;
import static com.fuel.fuelapplication.AdminHome.CLOSE;
import static com.fuel.fuelapplication.MainActivity.PHONE;
import static com.fuel.fuelapplication.MainActivity.USNAME;
import static com.fuel.fuelapplication.MainActivity.UTYPE;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import Database.RetrofitClient;

import Models.Methods;
import Models.Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Admin use this class to add Time data to the system
 */
public class AdminDetailsAdd  extends AppCompatActivity {

    TimePickerDialog _timePickerDialog;
    public  String ARRIVALTIME=null, DEPARTURETIME=null;
    private String open=null, close=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the action bar and the header
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.admin_detals);

        //Assign global data to local variables
        open=ARRIVALS;
        close=CLOSE;

        //Declare time remove button
        Button remove = findViewById(R.id.departureTimesRev);
        remove.setOnClickListener(v -> {
                //Check times before remove them
                if(open == null && close == null){
                    Toast.makeText(AdminDetailsAdd.this, "No Time Data", Toast.LENGTH_SHORT).show();
                }
            //  Aremove data from the database:: MONGODB
            Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
            String StationNumber = PHONE;
            Call<Model> call = methods.remove(StationNumber);
            call.enqueue(new Callback<Model>() {
                /*
                    If the process success, then receive onResponse:code::200
                    Navigate to the Admin Panel
                 */
                @Override
                public void onResponse(Call<Model> call, Response<Model> response) {
                    Toast.makeText(AdminDetailsAdd.this, "Remove Data", Toast.LENGTH_SHORT).show();
                    open = null;
                    close=null;
                    suNavigation();
                }
                /*
                   If the process Unsuccessful, then receive onResponse:code::400
                   Display Try Again Message
                 */
                @Override
                public void onFailure(Call<Model> call, Throwable t) {
                    Toast.makeText(AdminDetailsAdd.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        });

        //Declare add time button
        TextView add = findViewById(R.id.addDetails);
        add.setOnClickListener(v -> {

            /*
                Check arrival time and the departure time
                If values are false,then display Set time message
             */
            if (ARRIVALTIME == null && DEPARTURETIME == null){
                Toast.makeText(AdminDetailsAdd.this, "Set Time", Toast.LENGTH_SHORT).show();
            }

            //Start to work add time function
            else{
                //Assign primary values to the variables
                String StationName = USNAME;
                String StationNumber = PHONE;
                String StationLocation = UTYPE;

                //  Add data to the bakcend server
                Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Model> call = methods.AddDetail(ARRIVALTIME, DEPARTURETIME, StationNumber,
                        StationName, StationLocation);

                call.enqueue(new Callback<Model>() {
                    /*
                        If the process success, then receive onResponse:code::200
                        Navigate to the Admin Panel
                     */
                    @Override
                    public void onResponse(Call<Model> call, Response<Model> response) {

                        Toast.makeText(AdminDetailsAdd.this, "Upload Data", Toast.LENGTH_SHORT).show();
                        suNavigation();
                    }
                    /*
                        If the process Unsuccessful, then receive onResponse:code::400
                        Display Try Again Message
                     */
                    @Override
                    public void onFailure(Call<Model> call, Throwable t) {
                        Toast.makeText(AdminDetailsAdd.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Arrival Time Button
     * @param view
     */
    public void onButtonSelectArrivalTimeClick(View view) {
        arrival_TimePickerDialog();
    }

    /**
     * Time picker function
     * Add time to the variables
     */
    private void arrival_TimePickerDialog(){
        int hourOfDay=2;
        int minute=2;
        boolean is24HourView=true;
        //add time picker style
        _timePickerDialog=new TimePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            //add TIMEPICKER time to the ARRIVALTIME variable
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                ARRIVALTIME= String.valueOf(hr) +" : "+ String.valueOf(min);
            }
        },hourOfDay,minute,is24HourView);
        //Assign values to the time picker
        _timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        _timePickerDialog.setTitle("Set Start Time");
        _timePickerDialog.show();
    }
    /**
     * Departure Time Button
     * @param view
     */
    public void onButtonSelectDepartureTimeClick(View view) {
        departure_TimePickerDialog();
    }

    /**
     * Time picker function
     * Add time to the variables
     */
    private void departure_TimePickerDialog(){
        int hourOfDay=2;
        int minute=2;
        boolean is24HourView=true;
        //add time picker style
        _timePickerDialog=new TimePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            //add TIMEPICKER time to the ARRIVALTIME variable
            public void onTimeSet(TimePicker timePicker, int hr, int min) {
                DEPARTURETIME=  String.valueOf(hr) +" : "+ String.valueOf(min);
            }
        },hourOfDay,minute,is24HourView);
        //Assign values to the time picker
        _timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        _timePickerDialog.setTitle("Set Close Time");
        _timePickerDialog.show();
    }

    /**
     * NAVIGATE TO THE ADMIN HOME PAGE
     */
    private void suNavigation(){
        Intent intent = new Intent(this, AdminHome.class);
        startActivity(intent);
    }
}