package com.example.midterm_sunny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName, etCollege, etDepartment;
    Button btnSaveData, btnNextScreen, btnStartService, btnStopService;

    Intent intent;

    //time service
    BoundService timeService;
    boolean isTimeServiceBound = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etCollege = findViewById(R.id.etCollege);
        etDepartment = findViewById(R.id.etDepartment);
        btnSaveData = findViewById(R.id.btnSaveData);
        btnNextScreen = findViewById(R.id.btnNextScreen);
        btnStartService = findViewById(R.id.btnStartService);
        btnStopService = findViewById(R.id.btnStopService);

        //for database part:
        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etName.getText().toString();
                String college = etCollege.getText().toString();
                String department = etDepartment.getText().toString();
                DbHandler dbHandler = new DbHandler(MainActivity.this);
                dbHandler.insertUserDetails(username, college, department);

/*                Toast.makeText(getApplicationContext(), "Success",
                        Toast.LENGTH_LONG).show();*/
            }
        });

        //for next screen:
        btnNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, secondScreen.class);
                startActivity(intent);
            }
        });

        //to start service: displaying date time
        Intent timeServiceIntent = new Intent(this, BoundService.class);
        bindService(timeServiceIntent, myTimeServiceConnection, Context.BIND_AUTO_CREATE);
        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime(v);
            }
        });

        //to stop service:
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentTime = timeService.getCurrentTime();
                Toast.makeText(getApplicationContext(), "Service stopped at " + currentTime,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //display time using BoundService
    private void showTime(View view) {
        String currentTime = timeService.getCurrentTime();
        Toast.makeText(getApplicationContext(), "Service started at " + currentTime,
                Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Service is mining bitcoins!",
                Toast.LENGTH_LONG).show();

    }

    private ServiceConnection myTimeServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyLocalBinder binder = (BoundService.MyLocalBinder) service;
            timeService = binder.getService();
            isTimeServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isTimeServiceBound = false;

        }
    };
}