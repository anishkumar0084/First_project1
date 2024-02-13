package com.example.first_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login_ac extends AppCompatActivity {

    AppCompatButton button;
    TextInputLayout email, password1;

    AppCompatButton login;
    //    Data_base data_base;
    Data_base data_base;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        data_base=new Data_base(this);
        email = findViewById(R.id.outlinedTextFieldt);
        password1 = findViewById(R.id.outlinedTextField51);

        login = findViewById(R.id.lgbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = email.getEditText().getText().toString().trim();
                String password = password1.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login_ac.this, "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (validateLogin(username, password)) {
                    SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("mobileNumber", password);
                    editor.apply();
                    Intent intent = new Intent(Login_ac.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login_ac.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }


        });

        button = findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_ac.this, Reg_page.class);

                startActivity(intent);

            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // Query the database to check if the username and password match
        // Assuming Data_base class has a method to check login credentials
        return data_base.checkLogin(username, password);
    }

}