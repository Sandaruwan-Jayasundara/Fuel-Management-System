package com.fuel.fuelapplication;

import static com.fuel.fuelapplication.MainActivity.PHONE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

import Database.RetrofitClient;
import Models.Methods;
import Models.Model;
import Models.Queue;
import Models.UserQueue;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * user can check the availability and add to te queue
 */
public class ShedPatrol  extends AppCompatActivity {
    private static final String TAG = "ShedPatrol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove the Header bar header action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_patrol);

        //Get data from the Shed class
        Bundle data = getIntent().getExtras();
        String pQueue =  data.getString("pKey");

        //Declare the QueueSize
        TextView pqueueSize= findViewById(R.id.queueSize);
        TextView PestimatedTime= findViewById(R.id.PestimatedTime);

        //Get queue size from the backend database:: MONGODB
        Methods methods1 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Queue> call1= methods1.getQueue(pQueue);

          /*
               If the process success, then receive onResponse:code::200
               Assign retrieved values to the Text Views
            */
        call1.enqueue(new Callback<Queue>() {
            @Override
            public void onResponse(Call<Queue> call, Response<Queue> response) {
                Log.e(TAG, "onResponse:code Time:"+ response.code());
                Log.e(TAG, "onResponse:Body Time:"+ response.body());
                Queue queue = response.body();

                //If retrieved queue size is null then assign queue values as 0
                if(queue ==null){
                    pqueueSize.setText("0");
                    PestimatedTime.setText("0");
                }
                else{
                    //If retrieved queue size is null then assign queue values as 0
                    if(queue.getQueue() ==null){
                        pqueueSize.setText("0");
                        PestimatedTime.setText("0");
                    }
                    //Assign retrieved queue size to the textview
                    else{
                        pqueueSize.setText(queue.getQueue());
                      //  int in2 = new Integer(queue.getQueue());
//                        int in = Integer.valueOf(queue.getQueue());
                        int estimatedTimeInt = Integer.parseInt(queue.getQueue().trim());
                             int time= estimatedTimeInt*5;
                             String Est = String.valueOf(time) +" "+"min";
                        pqueueSize.setText(queue.getQueue());
                        PestimatedTime.setText(Est);
                    }
                }
            }
            /*
                If the process Unsuccessful, then receive onResponse:code::400
                Assign queue value as 0
            */
            @Override
            public void onFailure(Call<Queue> call, Throwable t) {
                Log.e(TAG, "onFailure Time:"+ t.getMessage());
                pqueueSize.setText("0");
            }
        });

//        patrolData(pQueue);

        //Declare add to queue button
        Button addToQueue=findViewById(R.id.addtotheque);
        addToQueue.setOnClickListener(v -> {
            addToQueue(pQueue);
        });

        //Declare fuel complete button
        Button pcomplete=findViewById(R.id.fuelcomplete);
        pcomplete.setOnClickListener(v -> {
            Compete();
        });

        //Declare exit from the queue button
        Button pExite=findViewById(R.id.fuelexites);
        pExite.setOnClickListener(v -> {
            Leave(pQueue);
        });
    }


//    public void patrolData(String data){
//        //Declare the QueueSize
//        TextView queueSize= findViewById(R.id.queueSize);
//
//        //  ADD DATA TO THE BACKEND SERVER
//        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
//        Call<Model> call = methods.shedPatrol(data);
//
//        call.enqueue(new Callback<Model>() {
//            @Override
//            public void onResponse(Call<Model> call, Response<Model> response) {
//                Log.e(TAG, "onResponse:code Patrol:"+ response.code());
//                Log.e(TAG, "onResponse:Body Patrol:"+ response.body());
//
//                Model model1 = response.body();
//                if(model1.getQueue()==null){
//                    queueSize.setText("UNAVAILABLE");
//                }else{
//                    queueSize.setText(model1.getQueue());
//                }
//            }
//            @Override
//            public void onFailure(Call<Model> call, Throwable t) {
//                Log.e(TAG, "onFailure Patrol:"+ t.getMessage());
//                queueSize.setText("UNAVAILABLE");
//            }
//        });
//    }

    /**
     * fuel filling is complete then runs the method
     */
    public void Compete(){

        //send reques to the backend database to notify whether fuel filling is complete
        Methods methods2 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<UserQueue> call2 = methods2.updateQueueTime(PHONE,"Complete");

        call2.enqueue(new Callback<UserQueue>() {
            /*
              If the process success, then receive onResponse:code::200
               navigate to the shed page
            */
            @Override
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
                Log.e(TAG, "onResponse:code Patrol:"+ response.code());
                Log.e(TAG, "onResponse:Body Patrol:"+ response.body());
                navigation();
            }
            /*
                If the process Unsuccessful, then receive onResponse:code::400
            */
            @Override
            public void onFailure(Call<UserQueue> call, Throwable t) {
                Log.e(TAG, "onFailure Patrol:"+ t.getMessage());
            }
        });
    }

    /**
     * fuel filling is uncompleted and exit from the queue
     */
    public void Leave(String data){

        //send request to the backend server to update exit notification
        Methods methods2 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<UserQueue> call2 = methods2.updateQueueTime(PHONE,"Leave");

       /*
              If the process success, then receive onResponse:code::200
               navigate to the shed page
        */
        call2.enqueue(new Callback<UserQueue>() {
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
                Log.e(TAG, "onResponse:code Patrol:"+ response.code());
                Log.e(TAG, "onResponse:Body Patrol:"+ response.body());

                //Declare the queue size text view
                TextView queueSize= findViewById(R.id.queueSize);

                //send request to decrease the queue size
                Methods methods4 = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Queue> call4 = methods4.decreaseQueue(data,queueSize.getText().toString());

                call4.enqueue(new Callback<Queue>() {
                    /*
                      If the process success, then receive onResponse:code::200
                       navigate to the shed page
                    */
                    @Override
                    public void onResponse(Call<Queue> call, Response<Queue> response) {
                        Log.e(TAG, "onResponse:code Patrol:"+ response.code());
                        Log.e(TAG, "onResponse:Body Patrol:"+ response.body());

                        System.out.println("THIS HEREE 222");

                        navigation();
                    }
                    /*
                        If the process Unsuccessful, then receive onResponse:code::400
                    */
                    @Override
                    public void onFailure(Call<Queue> call, Throwable t) {
                        Log.e(TAG, "onFailure Patrol:"+ t.getMessage());
                    }
                });
            }
            /*
                If the process Unsuccessful, then receive onResponse:code::400
            */
            @Override
            public void onFailure(Call<UserQueue> call, Throwable t) {
                Log.e(TAG, "onFailure Patrol:"+ t.getMessage());
            }
        });







    }

    /**
     * when use wants  to add to the queue, then this methods starts to work
     * @param data
     */
    public void addToQueue(String data){
        //Declare the queue size text view
        TextView queueSize= findViewById(R.id.queueSize);

        //send request to add for the queue
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Queue> call = methods.addToQueue("Patrol",queueSize.getText().toString(),data);

        call.enqueue(new Callback<Queue>() {
            /*
              If the process success, then receive onResponse:code::200
               navigate to the shed page
            */
            @Override
            public void onResponse(Call<Queue> call, Response<Queue> response) {
                Log.e(TAG, "onResponse:code Patrol:"+ response.code());
                Log.e(TAG, "onResponse:Body Patrol:"+ response.body());
                Queue queue = response.body();
                navigation();
            }
            /*
                If the process Unsuccessful, then receive onResponse:code::400
            */
            @Override
            public void onFailure(Call<Queue> call, Throwable t) {
                Log.e(TAG, "onFailure Patrol:"+ t.getMessage());
            }
        });

        //send request to add queue time to the database
        Methods methods1 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<UserQueue> call1 = methods1.addQueueTime(PHONE,"Patrol");

        call1.enqueue(new Callback<UserQueue>() {
            /*
            If the process success, then receive onResponse:code::200
             navigate to the shed page
          */
            @Override
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
                Log.e(TAG, "onResponse:code Patrol:"+ response.code());
                Log.e(TAG, "onResponse:Body Patrol:"+ response.body());

            }
            /*
                If the process Unsuccessful, then receive onResponse:code::400
            */
            @Override
            public void onFailure(Call<UserQueue> call, Throwable t) {
                Log.e(TAG, "onFailure Desel:"+ t.getMessage());
            }
        });
    }

    //Navigate to the Shed activity
    public void navigation(){
        Intent intent = new Intent(this, Shed.class);
        startActivity(intent);
    }
}