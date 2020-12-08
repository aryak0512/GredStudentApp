package com.example.studentapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    Student student;
    DatabaseHelper db;
    List<Subject> list;
    Service service=new Service();
    TextView statsMessage;
    TextView greeting;
    SpaceNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new DatabaseHelper(HomeActivity.this);
        student=(Student)getIntent().getSerializableExtra("student");
        list=db.getRecords(student);
        Map <String,Float>map =service.getPercentCGPA(list);


        //connecting widgets
        statsMessage=findViewById(R.id.statsMessage);
        greeting= findViewById(R.id.greeting);
        String pointer = Float.toString(map.get("cgpa"));
        String percentage = Float.toString(map.get("percentage"));
        System.out.println("poitrr: "+pointer);
        System.out.println("percetn: "+percentage);


       if(pointer.length()>3 && percentage.length()>3){
           statsMessage.setText("Your percentage is: "+percentage.substring(0,4)+" and CGPA="+pointer.substring(0,4));
       }
       else{
           statsMessage.setText("Your percentage is: "+percentage+" and CGPA="+pointer);
       }

        greeting.setText("WELCOME, "+student.getUsername()+"!");
        statsMessage.setTextColor(Color.RED);


        navigationView=findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_arrow_back_24));     //0
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_add_box_24));        //1
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_account_circle_24)); //2
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_baseline_login_24));          //3
        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if(itemIndex==0){
                    finish();
                }
                if(itemIndex==1){
                    Intent toSubjectPage = new Intent(HomeActivity.this, SubjectActivity.class);
                    toSubjectPage.putExtra("student",student);
                    startActivity(toSubjectPage);
                    //Toast.makeText(HomeActivity.this,"You are in subject page.",Toast.LENGTH_LONG).show();
                }

                if(itemIndex==3){
                    Intent toLoginPage = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(toLoginPage);
                    Toast.makeText(HomeActivity.this,"You haved logged out.",Toast.LENGTH_LONG).show();
                }
                if(itemIndex==2){
                    Intent toAccountPage = new Intent(HomeActivity.this, AccountActivity.class);
                    toAccountPage.putExtra("student",student);
                    startActivity(toAccountPage);
                    //Toast.makeText(HomeActivity.this,"You are in account page.",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });


        final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for(Subject s: list)
        {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            TextView name = new TextView(this);
            name.setText("                      "+s.getName().toUpperCase().substring(0,3)+"    ");
            name.setTextSize(15);
            ll.addView(name);

            TextView score = new TextView(this);
            score.setText("  "+s.getScore()+"/");
            score.setTextSize(15);
            ll.addView(score);

            TextView total = new TextView(this);
            total.setText(""+s.getTotal()+"   ");
            total.setTextSize(15);
            ll.addView(total);

            TextView grade = new TextView(this);
            grade.setText("       "+s.getGrade()+"       ");
            grade.setTextSize(15);
            ll.addView(grade);


            final Button btnDelete = new Button(this);
            btnDelete.setId(s.getId());
            btnDelete.setText("delete");
            btnDelete.setTextColor(Color.RED);
            btnDelete.setTextSize(15);

            // set the layoutParams on the button
            btnDelete.setLayoutParams(params);

            final int index = s.getId();
            btnDelete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("DELETED :", "index :" + index);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
                    alertDialogBuilder.setTitle("ARE YOU SURE");
                    alertDialogBuilder.setMessage("You want to delete this subject?");
                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            db.deleteSubject(index);
                            Intent toHomePage = new Intent(HomeActivity.this, HomeActivity.class);
                            toHomePage.putExtra("student",student);
                            startActivity(toHomePage);
                            Toast.makeText(HomeActivity.this,"Subject was deleted.",Toast.LENGTH_LONG).show();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(HomeActivity.this,"Deletion aborted.",Toast.LENGTH_LONG).show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });
            //Add button to LinearLayout
            ll.addView(btnDelete);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
        }



    }
}