package com.example.first_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Reg_page extends AppCompatActivity {

    AppCompatButton button;

    TextInputLayout name,email,password,age,mobile;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();


    Data_base data_base;
    TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);
        name=findViewById(R.id.outlinedTextField);
        email=findViewById(R.id.outlinedTextField2);
        password=findViewById(R.id.outlinedTextField5);
        age=findViewById(R.id.outlinedTextField4);
        mobile=findViewById(R.id.outlinedTextField3);
        button=findViewById(R.id.regis);
         data_base=new Data_base(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressText = findViewById(R.id.progressText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertingvalue();


            }
        });

    }

    private void insertingvalue() {

        String nameValue = name.getEditText().getText().toString().trim();
        String emailValue = email.getEditText().getText().toString().trim();
        String passwordValue = password.getEditText().getText().toString().trim();
        String ageValue = age.getEditText().getText().toString().trim();
        String mobileValue = mobile.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(nameValue) || TextUtils.isEmpty(emailValue) || TextUtils.isEmpty(passwordValue) || TextUtils.isEmpty(ageValue) || TextUtils.isEmpty(mobileValue)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        } else if (data_base.checkUserExists(nameValue, emailValue, passwordValue, ageValue, mobileValue)) {
            Toast.makeText(Reg_page.this, "User already exists", Toast.LENGTH_SHORT).show();
            return;
        } else  if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        } else if (!TextUtils.isDigitsOnly(mobileValue)) {
            Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
            return;




    } else {

            startProgressBar();


            // Insert data into the database
            data_base.insertData(nameValue, emailValue, passwordValue, ageValue, mobileValue);


        }





    }


    private void startProgressBar() {
        progressStatus = 0;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        progressText.setText("Creating account...");

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });

                }

                handler.post(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Reg_page.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(Reg_page.this, Login_ac.class);
                        startActivity(intent);
                        finish();

                    }
                });
            }
        }).start();
    }


}