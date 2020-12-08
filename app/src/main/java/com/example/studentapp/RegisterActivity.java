package com.example.studentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    TextView tvLogin;
    EditText txtName;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirmPassword;
    Button btnRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(RegisterActivity.this);

        //settings the widgets
        txtName=findViewById(R.id.txtName);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnRegister =findViewById(R.id.btnRegister);
        tvLogin=findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean passLength=false;
                boolean passMatch=false;
                boolean isNameValid=false;

                String name= txtName.getText().toString();
                String username= txtUsername.getText().toString();
                String password= txtPassword.getText().toString();
                String confirmPassword= txtConfirmPassword.getText().toString();

                int count = db.checkIfUsernameExists(username);
                if(count==0 && username.length()>2){
                    passLength=password.length()>5;
                    passMatch=password.equals(confirmPassword);
                    isNameValid=(!name.equals("")) && (name != null) && (name.matches("^[a-zA-Z ]*$"));

                    if(isNameValid && name.length()>2){
                        if(passLength){
                            if(passMatch){
                                //insert in db
                                if(db.addStudent(name, username, password)>0){
                                    Intent toLoginPage = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(toLoginPage);
                                    Toast.makeText(RegisterActivity.this,"Registered! Please login now.",Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(RegisterActivity.this,"Passwords do not match.",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"Passwords should be of atleast 6 chars.",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(RegisterActivity.this,"Please enter a valid name.",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this,"This username is already taken or is too short.",Toast.LENGTH_LONG).show();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginPage = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(toLoginPage);
                //Toast.makeText(RegisterActivity.this,"Please login here.",Toast.LENGTH_LONG).show();
            }
        });

    }
}