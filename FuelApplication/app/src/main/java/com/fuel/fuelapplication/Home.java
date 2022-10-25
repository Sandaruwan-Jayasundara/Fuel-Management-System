package com.fuel.fuelapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.Adapter;
import Database.DBHelper;
import Models.ModelClass;
import Models.RegisterUserModel;

/**
 * Main home class
 * display all registered sheds
 * available shed search feature
 */
public class Home extends AppCompatActivity {

    RecyclerView mrecyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass>userList;
    Adapter adapter;
    private DBHelper dbHandler;
    SearchView searchView;
    private static final String TAG = "Home";
    public static String hKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Remove the title bar and the navigation bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        //Declare shed search field
        searchView= findViewById(R.id.searchShed);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        //Back to the Login activity
        ImageButton logoutHome = findViewById(R.id.logoutHome);
        logoutHome.setOnClickListener(v -> {
            Intent logOut = new Intent(this, MainActivity.class);
            startActivity(logOut);
        });

        dbHandler = new DBHelper(Home.this);
        initData();
        initRecyclerView();
    }

    //implement init Recyvle view function
    private void initRecyclerView() {

        //Assign values to the Recycle view
        mrecyclerView=findViewById(R.id.RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mrecyclerView.setLayoutManager(layoutManager);

        //Assign values for recycle view adapter
        adapter=new Adapter(userList,new Adapter.ItemClickListner() {

            //Navigate to the shed activity when clicks the sheds
            @Override
            public void onItemClick(ModelClass modelClass) {
                Toast.makeText(Home.this, modelClass.getPhone(), Toast.LENGTH_SHORT).show();
                hKey =modelClass.getPhone();
                navigation();
            }
        });
        mrecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Navigation to the shed activity
    private void navigation(){
        Intent fuelData = new Intent(this, Shed.class);
        startActivity(fuelData);
    }

    //Get shed data from the database
    private void initData() {
        userList = new ArrayList<>();

        int size =0;
        //Call to the SQLITE database to get shed data
        List<RegisterUserModel> info=  dbHandler.getAll();

        //Assign values for the recycle view
        while (info.size() > size){
                        if(info.get(size).getType().equalsIgnoreCase("1")){
                userList.add(new ModelClass(R.drawable.elecorg,info.get(size).getUtype(),
                        info.get(size).getName(),"RESULT",info.get(size).getEmail()));
            }
            size++;
        }
    }
    //Implement Filter function for the search feature
    private void filterList(String newText) {
        List<ModelClass> list = new ArrayList<>();
        for(ModelClass modelClass: userList){
            if(modelClass.getTextview2().toLowerCase().contains(newText.toLowerCase())){
                list.add(modelClass);
            }
        }
        //If the list is empty, then display the no data message to the user
        if(list.isEmpty()){
            Toast.makeText(Home.this, "No Data Found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(list);
        }
    }
}