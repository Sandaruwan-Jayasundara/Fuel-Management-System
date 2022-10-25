package com.fuel.fuelapplication;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import Database.DBHelper;

/**
 * User registration class allows user to registers for the system
 */
public class RegisterUser extends AppCompatActivity {

   // creating variables for our edittext, button and dbHandler
   private DBHelper dbHandler;
   private EditText uName, uVehicle, uPhone, uPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to remove title bar and action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        dbHandler = new DBHelper(RegisterUser.this);

        //Declare Register user button
        Button button = findViewById(R.id.registerUser);
          button.setOnClickListener(v -> {

            //Get data from thte Edit text fields
            uName = findViewById(R.id.fullname);
            uVehicle = findViewById(R.id.vehicle);
            uPhone = findViewById(R.id.email);
            uPassword = findViewById(R.id.password);

            //Assign values to the String variables
            String usName = uName.getText().toString();
            String usVehicle = uVehicle.getText().toString();
            String usPhone = uPhone.getText().toString();
            String usPassword = uPassword.getText().toString();
            String utype = "2";

              // validate registration text fields
              if (usName.isEmpty() && usVehicle.isEmpty() && usPhone.isEmpty() && usPassword.isEmpty()) {
                  Toast.makeText(RegisterUser.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                  return;
              }

              //send data to the sqlite data base
              dbHandler.registerUser(usPhone,usName,usVehicle,usPassword,utype);

              //Display registered message after completing the process
              Toast.makeText(RegisterUser.this, "Registered.", Toast.LENGTH_SHORT).show();

              //Navigate to the Login activity
              Intent intent = new Intent(this, MainActivity.class);
              startActivity(intent);

        });

        TextView adminRegisterTab = findViewById(R.id.adminRegisterTab);
        adminRegisterTab.setOnClickListener(v -> {

            Intent intent = new Intent(this, AdminRegister.class);
            startActivity(intent);
        });

    }
}