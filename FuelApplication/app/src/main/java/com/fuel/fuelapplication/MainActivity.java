package com.fuel.fuelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import Database.DBHelper;

/**
 * User Login class
 * AUthorization and AUthentication and
 * All validations
 */
public class MainActivity extends AppCompatActivity {

    private EditText uPhone,uPassword;
    private DBHelper dbHelper;
    private ArrayList arrayList;
    public static String USNAME;
    public static String PHONE;
    public static String UTYPE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //to remove title bar and action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        dbHelper=new DBHelper(MainActivity.this);

        //Declare Register button to navigate to the register activity
        TextView register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterUser.class);
            startActivity(intent);
        });

        //Assign Edit text values to the String variables
        uPhone = findViewById(R.id.auemail);
        uPassword = findViewById(R.id.aupassword);

        //Declare the login button
        Button loginbtn = findViewById(R.id.login);
        loginbtn.setOnClickListener(v -> {

            //Get user data according to the given credentials
























































            arrayList= dbHelper.

                    getinfo(uPhone.getText().toString());
            if(!arrayList.isEmpty()) {

                //Assign user data to the String Variables
                String DBID = arrayList.get(0).toString();
                String DBName = arrayList.get(1).toString();
                String DBPhone = arrayList.get(2).toString();
                String Vtype = arrayList.get(3).toString();
                String type = arrayList.get(4).toString();
                String DBPassword = arrayList.get(5).toString();

                //Set Gloable variables
                USNAME = DBName;
                PHONE = DBPhone;
                UTYPE = Vtype;

                //Check password and the phone number for authorization and authentication purpose
                if (DBPassword.equals(uPassword.getText().toString()) && DBPhone.equalsIgnoreCase(uPhone.getText().toString())) {

                    //If the user is a level 2 user, then navigate to the main home page
                    if (type.equalsIgnoreCase("2")) {
                        Intent intent = new Intent(this, Home.class);
                        startActivity(intent);
                    }
                    //If the user is a level 1 user, then navigate to the Admin Panel
                    else {
                        Intent intent = new Intent(this, AdminHome.class);
                        startActivity(intent);
                    }
                }
                //Display try again message
                else{
                    Toast.makeText(MainActivity.this, "Try again.", Toast.LENGTH_SHORT).show();
                }
            }
            //Display try again message
            else{
                Toast.makeText(MainActivity.this, "Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}