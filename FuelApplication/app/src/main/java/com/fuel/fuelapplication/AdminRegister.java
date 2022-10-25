package com.fuel.fuelapplication;

import android.content.Intent;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import Database.DBHelper;

/**
 * fuel station owners registration class
 */
public class AdminRegister extends AppCompatActivity {

    private DBHelper dbHandler;
    private EditText aLocation, aStation, aPhone, aPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to remove title bar and action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.admin_register);

        dbHandler = new DBHelper(AdminRegister.this);

        //Declare register button
        Button button = findViewById(R.id.AdregisterUser);
        button.setOnClickListener(v -> {

            //Get input data from the register activity
            aLocation = findViewById(R.id.AdFullName);
            aStation = findViewById(R.id.AdStationName);
            aPhone = findViewById(R.id.Adphone);
            aPassword = findViewById(R.id.Adpassword);

            //Assign values to the String variables
            String auLocation = aLocation.getText().toString();
            String auStation = aStation.getText().toString();
            String auPhone = aPhone.getText().toString();
            String auPassword = aPassword.getText().toString();
            String utype = "1";

            // validate registtration text fields
            if (auLocation.isEmpty() && auStation.isEmpty() && auPhone.isEmpty() && auPassword.isEmpty()) {
                Toast.makeText(AdminRegister.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                return;
            }
            //send data to the sqlite data base
            dbHandler.registerUser(auPhone,auLocation,auStation,auPassword,utype);

            //Display registered message after completing the process
            Toast.makeText(AdminRegister.this, "Registered.", Toast.LENGTH_SHORT).show();

            //Navigate to the Login activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}