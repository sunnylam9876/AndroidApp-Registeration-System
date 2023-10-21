package com.example.midterm_sunny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class secondScreen extends AppCompatActivity {

    Button btnBack, btnShowData;

    ListView lvUserList;

    ListAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        btnBack = findViewById(R.id.btnBack);
        btnShowData = findViewById(R.id.btnShowData);

        //to back to main screen:
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(secondScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //to show data using Async task:
        btnShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });
    }


 //for Async task
    private class BackgroundTask extends AsyncTask<Void, Void, String> {

     @Override
        protected void onPreExecute() {
         //super.onPreExecute();
         Toast.makeText(getApplicationContext(), "Thread starting",
                 Toast.LENGTH_LONG).show();

         //to delay for 1 second
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

        @Override
        protected String doInBackground(Void... voids) {
            //try {
            //get data from db:
            DbHandler db = new DbHandler(secondScreen.this);
                ArrayList<HashMap<String, String>> userList = db.GetUsers();
                lvUserList = findViewById(R.id.user_list);
                adapter = new SimpleAdapter(secondScreen.this, userList,
                        R.layout.list_row,
                        new String[]{"name", "college", "department"},
                        new int[] {R.id.tvGetName, R.id.tvGetCollege, R.id.tvGetDepartment});

            //} catch (Exception e) {
                //e.printStackTrace();
            //}
            return "Done!";
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(getApplicationContext(), "Thread ended",
                    Toast.LENGTH_LONG).show();

            //update the UI
            lvUserList.setAdapter(adapter);
        }
    }
}

