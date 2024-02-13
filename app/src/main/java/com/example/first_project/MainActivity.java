package com.example.first_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewName, textViewEmail, textViewAge;
    private Data_base data_base;

    AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.appCompatButton);
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewAge = findViewById(R.id.textViewAge);

        data_base = new Data_base(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Login_ac.class);
                startActivity(intent);
                finish();

            }
        });
        SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String mobileNumber = preferences.getString("mobileNumber", "");


//        String userMobileNumber = getIntent().getStringExtra("mobileNumber");
        Cursor cursor = data_base.getData(mobileNumber);

        if (cursor != null && cursor.moveToFirst()) {
            // Get the column indices of the data
            int nameIndex = cursor.getColumnIndex(Data_base.col);
            int emailIndex = cursor.getColumnIndex(Data_base.col2);
            int ageIndex = cursor.getColumnIndex(Data_base.col3);

            // Extract data from the cursor
            String name = cursor.getString(nameIndex);
            String email = cursor.getString(emailIndex);
            int age = cursor.getInt(ageIndex);

            // Set data to TextViews
            textViewName.setText("Name="+name);
            textViewEmail.setText("Email="+email);
            textViewAge.setText(String.valueOf("Age="+age));

            // Close the cursor after use
            cursor.close();
        } else {
            // Handle case where no user data is found
        }



    }



}