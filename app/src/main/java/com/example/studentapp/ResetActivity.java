package com.example.studentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity {
    EditText txtConfirmPassword;
    EditText txtPassword;
    Button btnReset;
    DatabaseHelper db;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        student=(Student)getIntent().getSerializableExtra("student");
        db = new DatabaseHelper(ResetActivity.this);

        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnReset= findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password= txtPassword.getText().toString();
                String confirmPassword= txtConfirmPassword.getText().toString();

                if(password.length()<=5){
                    Toast.makeText(ResetActivity.this,"Passwords should be of atleast 6 chars.",Toast.LENGTH_LONG).show();
                }
                else{
                    if(password.equals(confirmPassword)){
                        int response=db.updatePassword(student,password);
                        System.out.println("UPDATE RESPONSE: "+response);
                        Intent toLoginPage = new Intent(ResetActivity.this, MainActivity.class);
                        startActivity(toLoginPage);
                        Toast.makeText(ResetActivity.this,"Updated! Please login with new credentials.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(ResetActivity.this,"Your strings do not match.",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }
}