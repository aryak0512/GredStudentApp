package com.example.studentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME= "student.db";
    public static String TABLE1_NAME= "registrations";
    public static String TABLE2_NAME= "student_records";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE1_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE "+TABLE2_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, score INTEGER, total INTEGER, grade TEXT,  student_id INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2_NAME);
        onCreate(db);
    }

    public long addStudent(String name,String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("username",username);
        values.put("password",password);
        long response =db.insert(TABLE1_NAME,null,values);
        db.close();
        return response;
    }

    public long addSubject(Subject subject,Student student){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",subject.getName());
        values.put("score",subject.getScore());
        values.put("total",subject.getTotal());
        values.put("grade",subject.getGrade());
        values.put("student_id",student.getId());
        long response =db.insert(TABLE2_NAME,null,values);
        System.out.println("IN add subject: ---"+response);
        db.close();
        return response;
    }

    public Student checkUser(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        String sql = "select * from registrations where username=? and password=?";
        //Cursor cursor = db.rawQuery( "select * from "+TABLE1_NAME+" WHERE username =? AND password =?", );
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE1_NAME+" WHERE username=? AND password=?",new String[]{username,password} );
        int count=cursor.getCount();
        System.out.println("count=========================>"+count);
        if(count==0){
            return null;
        }
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String name= cursor.getString(1);
        String stuUsername= cursor.getString(2);
        String stuPassword= cursor.getString(3);
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setUsername(stuUsername);
        student.setPassword(stuPassword);
        //System.out.println(student);
        return student;
    }

    public int updatePassword(Student student,String newPassword){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",newPassword);
        int res= db.update(TABLE1_NAME,values,"id=?",new String[]{Integer.toString(student.getId())});
        db.close();
        return res;
    }
    public int deleteUser(Student student){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        int res= db.delete(TABLE1_NAME,"id=?",new String[]{Integer.toString(student.getId())});
        db.close();
        return res;
    }

    public List<Subject> getRecords(Student s){
        SQLiteDatabase db = getReadableDatabase();
        List < Subject> list=new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE2_NAME+" WHERE student_id=?",new String[]{Integer.toString(s.getId())} );
        int count=cursor.getCount();
        System.out.println("count=========================>"+count);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            int id = cursor.getInt(0);
            String name= cursor.getString(1);
            int score= cursor.getInt(2);
            int total= cursor.getInt(3);
            String grade= cursor.getString(4);
            Subject subject = new Subject();
            subject.setId(id);
            subject.setName(name);
            subject.setTotal(total);
            subject.setScore(score);
            subject.setGrade(grade);
            subject.setStudent_id(s.getId());
            list.add(subject);
            cursor.moveToNext();
        }
        db.close();
        return list;
    }

    public int deleteSubject(int index) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        int res= db.delete(TABLE2_NAME,"id=?",new String[]{Integer.toString(index)});
        System.out.println(res);
        db.close();
        return res;
    }


    public int checkIfUsernameExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        List < Subject> list=new ArrayList();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE1_NAME+" WHERE username=?",new String[]{username} );
        int count=cursor.getCount();
        System.out.println("AVAILABILITY: "+count);
        return count;
    }
}
