package com.example.studentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class SubjectActivity extends AppCompatActivity {
    EditText score;
    EditText totalMarks;
    EditText subjectName;
    Button addSubject;
    Student student;
    DatabaseHelper db;
    Service service= new Service();
    SpaceNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        student = (Student) getIntent().getSerializableExtra("student");
        db = new DatabaseHelper(SubjectActivity.this);


        score = findViewById(R.id.score);
        totalMarks = findViewById(R.id.totalMarks);
        subjectName = findViewById(R.id.subjectName);
        addSubject =findViewById(R.id.addSubject);

        navigationView=findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_arrow_back_24));     //0
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_add_box_24));        //1
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_account_circle_24)); //2
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_login_24));          //3


        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent toHomePage = new Intent(SubjectActivity.this, HomeActivity.class);
                toHomePage.putExtra("student",student);
                startActivity(toHomePage);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if(itemIndex==0){
                    finish();
                }
                if(itemIndex==1){

                }
                if(itemIndex==3){
                    Intent toLoginPage = new Intent(SubjectActivity.this, MainActivity.class);
                    startActivity(toLoginPage);
                    Toast.makeText(SubjectActivity.this,"You haved logged out.",Toast.LENGTH_LONG).show();
                }
                if(itemIndex==2){
                    Intent toAccountPage = new Intent(SubjectActivity.this, AccountActivity.class);
                    toAccountPage.putExtra("student",student);
                    startActivity(toAccountPage);
                    Toast.makeText(SubjectActivity.this,"You are in account page.",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjName= subjectName.getText().toString();
                String subjScore= score.getText().toString();
                String subjTotal= totalMarks.getText().toString();

                boolean isNameValid= service.checkNameValid(subjName);
                boolean areMarksInvalid= service.checkMarksValid(subjScore,subjTotal);


                if(isNameValid==false){
                    Toast.makeText(SubjectActivity.this,"Please insert appropriate subject name.",Toast.LENGTH_LONG).show();
                }
                else{
                    if(areMarksInvalid==true){
                        Toast.makeText(SubjectActivity.this,"Please insert appropriate marks.",Toast.LENGTH_LONG).show();
                    }
                    else{
                        //validations of input then create object
                        Subject s = new Subject();
                        s.setName(subjName);
                        s.setScore(Integer.parseInt(subjScore));
                        s.setTotal(Integer.parseInt(subjTotal));
                        s.setGrade(service.getGrade(Integer.parseInt(subjScore),Integer.parseInt(subjTotal)));
                        s.setStudent_id(student.getId());
                        db.addSubject(s,student);
                        Intent toHomePage = new Intent(SubjectActivity.this, HomeActivity.class);
                        toHomePage.putExtra("student", student);
                        startActivity(toHomePage);
                        Toast.makeText(SubjectActivity.this,"Subject was added.",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
}