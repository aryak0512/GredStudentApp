package com.example.studentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvRegister;
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(MainActivity.this);

        //setting the widgets
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin =findViewById(R.id.btnLogin);

        //login button listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= txtUsername.getText().toString();
                String password= txtPassword.getText().toString();
                Student student=db.checkUser(username,password);
                if(student!=null){
                    Intent toHomePage = new Intent(MainActivity.this, HomeActivity.class);
                    toHomePage.putExtra("student", student);
                    startActivity(toHomePage);
                    //Toast.makeText(MainActivity.this,"You are logged in.",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Invalid credentials.",Toast.LENGTH_LONG).show();
                }

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegisterPage = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(toRegisterPage);
                //Toast.makeText(MainActivity.this,"Please register here.",Toast.LENGTH_LONG).show();
            }
        });

    }
}