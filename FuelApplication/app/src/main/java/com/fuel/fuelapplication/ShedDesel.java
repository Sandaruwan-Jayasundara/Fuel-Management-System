package com.fuel.fuelapplication;

import static com.fuel.fuelapplication.MainActivity.PHONE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import Database.RetrofitClient;
import Models.Methods;
import Models.Queue;
import Models.UserQueue;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * user can check the availability and add to te queue
 */
public class ShedDesel extends AppCompatActivity {
    private static final String TAG = "ShedPatrol";
    public int queSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove the Header bar header action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_desel);

        //Get data from the Shed class
        Bundle data = getIntent().getExtras();
        String pQueue =  data.getString("pKey");

        //Declare the QueueSize
        TextView dqueueSize= findViewById(R.id.dqueueSize);

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
                    dqueueSize.setText("0");

                }

                else{
                    //If retrieved queue size is null then assign queue values as 0
                    if(queue.getQueue() ==null){
                        dqueueSize.setText("0");
                    }
                    //Assign retrieved queue size to the textview
                    else{
                        dqueueSize.setText(queue.getQueue());

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
                dqueueSize.setText("0");
            }
        });
                //Declare add to queue button
                Button addToQueue=findViewById(R.id.addToQueue);
                addToQueue.setOnClickListener(v -> {
                    addToQueue(pQueue);
                });

                //Declare fuel complete button
                Button pcomplete=findViewById(R.id.pcomplete);
                  pcomplete.setOnClickListener(v -> {
                      Compete(pQueue);
                });

                 //Declare exit from the queue button
                Button pExite=findViewById(R.id.pExite);
                     pExite.setOnClickListener(v -> {
                    Leave(pQueue);
                });
    }

    /**
     * fuel filling is complete then runs the method
     * @param data
     */
    public void Compete(String data){

        //send reques to the backend database to notify whether fuel filling is complete
        Methods methods2 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<UserQueue> call2 = methods2.updateQueueTime(PHONE,"Complete");
          /*
            If the process success, then receive onResponse:code::200
             navigate to the shed page
          */
        call2.enqueue(new Callback<UserQueue>() {
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
                Log.e(TAG, "onResponse:code Desel:"+ response.code());
                Log.e(TAG, "onResponse:Body Desel:"+ response.body());
                System.out.println("THIS IS THE UCOMPELTE"+response.body());

                navigation();
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

    /**
     * fuel filling is uncomplete and exit from the queue
     * @param data
     */
    public void Leave(String data){

        //send request to the backend server to update exit notification
        Methods methods2 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<UserQueue> call2 = methods2.updateQueueTime(PHONE,"Leave");

        call2.enqueue(new Callback<UserQueue>() {
            /*
              If the process success, then receive onResponse:code::200
               navigate to the shed page
            */
            @Override
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
                Log.e(TAG, "onResponse:code Desel:"+ response.code());
                Log.e(TAG, "onResponse:Body Desel:"+ response.body());

                //Declare the queue size text view
                TextView dqueueSize= findViewById(R.id.dqueueSize);

                //send request to decrease the queue size
                Methods methods4 = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<Queue> call4 = methods4.decreaseQueue(data,dqueueSize.getText().toString());

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
                Log.e(TAG, "onFailure Desel:"+ t.getMessage());
            }
        });
    }

    /**
     * when use wants  to add to the queue, then this methods starts to work
     * @param data
     */
    public void addToQueue(String data){

        //Declare the queue size text view
        TextView dqueueSize= findViewById(R.id.dqueueSize);

        //send request to add for the queue
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Queue> call = methods.addToQueue("Desel",dqueueSize.getText().toString(),data);

        call.enqueue(new Callback<Queue>() {
            /*
              If the process success, then receive onResponse:code::200
               navigate to the shed page
            */
            @Override
            public void onResponse(Call<Queue> call, Response<Queue> response) {
                Log.e(TAG, "onResponse:code Desel:"+ response.code());
                Log.e(TAG, "onResponse:Body Desel:"+ response.body());
                response.body();

                navigation();
            }
            /*
                If the process Unsuccessful, then receive onResponse:code::400
            */
            @Override
            public void onFailure(Call<Queue> call, Throwable t) {
                Log.e(TAG, "onFailure Desel:"+ t.getMessage());
            }
        });


        //send request to add queue time to the database
        Methods methods1 = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<UserQueue> call1 = methods1.addQueueTime(PHONE,"Desel");

        call1.enqueue(new Callback<UserQueue>() {
            /*
              If the process success, then receive onResponse:code::200
               navigate to the shed page
            */
            @Override
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
                Log.e(TAG, "onResponse:code Desel:"+ response.code());
                Log.e(TAG, "onResponse:Body Desel:"+ response.body());
                System.out.println("THIS IS THE ADD QUEUE TIME"+response.body());

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