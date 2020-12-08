package com.example.studentapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class AccountActivity extends AppCompatActivity {

    Button deleteAccount;
    Button resetPassword;
    TextView acctPassword;
    TextView acctUsername;
    TextView acctName;
    TextView acctLine;
    Student student;
    DatabaseHelper db;
    SpaceNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        student = (Student) getIntent().getSerializableExtra("student");
        db = new DatabaseHelper(AccountActivity.this);


        //setting widgets
        deleteAccount = findViewById(R.id.deleteAccount);
        resetPassword = findViewById(R.id.resetPassword);
        acctPassword = findViewById(R.id.acctPassword);
        acctUsername = findViewById(R.id.acctUsername);
        acctName = findViewById(R.id.acctName);
        acctLine = findViewById(R.id.acctLine);

        //conceal * to password
        int len = student.getPassword().length();
        String pass = student.getPassword().charAt(0) + "";
        for (int i = 1; i <= len - 1; i++) {
            pass = pass + "*";
        }
        acctName.setText("NAME: " + student.getName());
        acctUsername.setText("USERNAME: " + student.getUsername());
        acctPassword.setText("PASSWORD: " + pass);

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountActivity.this);
                alertDialogBuilder.setTitle("ARE YOU SURE!");
                alertDialogBuilder.setMessage("You are about to deactivate your account.");
                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        db.deleteUser(student);
                        Intent toLoginPage = new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(toLoginPage);
                        Toast.makeText(AccountActivity.this, "Your account was deleted.", Toast.LENGTH_LONG).show();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(AccountActivity.this, "You clicked no button", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toResetPage = new Intent(AccountActivity.this, ResetActivity.class);
                toResetPage.putExtra("student", student);
                startActivity(toResetPage);
                //Toast.makeText(AccountActivity.this, "You are in reset page.", Toast.LENGTH_LONG).show();
            }
        });

        //bottom nav
        navigationView = findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_arrow_back_24));     //0
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_add_box_24));        //1
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_account_circle_24)); //2
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_login_24));          //3
        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent toHomePage = new Intent(AccountActivity.this, HomeActivity.class);
                toHomePage.putExtra("student",student);
                startActivity(toHomePage);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex == 0) {
                    finish();
                }
                if (itemIndex == 2) {

                }
                if (itemIndex == 3) {
                    Intent toLoginPage = new Intent(AccountActivity.this, MainActivity.class);
                    startActivity(toLoginPage);
                    Toast.makeText(AccountActivity.this, "You haved logged out.", Toast.LENGTH_LONG).show();
                }
                if (itemIndex == 1) {
                    Intent toSubjectPage = new Intent(AccountActivity.this, SubjectActivity.class);
                    toSubjectPage.putExtra("student", student);
                    startActivity(toSubjectPage);
                    //Toast.makeText(AccountActivity.this, "You are in subject page.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });


    }
}