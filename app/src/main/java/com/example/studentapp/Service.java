package com.example.studentapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    public String getGrade(int score, int total) {
        double s = (double) score;
        double t = (double)total;
        double percentage = (s / t) * 100;
        System.out.println("IN SERVICE: ---"+percentage);
        if (percentage >= 80) {
            return "A";
        }
        else {
            if (percentage >= 70 && percentage <= 79) {
                return "B";
            }
            else {
                if (percentage >= 60 && percentage <= 69) {
                    return "C";
                }
                else {
                        return "D";
                }
            }
        }
    }

    public Map<String, Float> getPercentCGPA(List <Subject> list){
        double score=0.0;
        double total=0.0;
        float percentage=0.0f;
        float cgpa= 0.0f;
        Map<String,Float> data= new HashMap();
        if (list.size()==0){
            data.put("cgpa",(float)0.00);
            data.put("percentage",(float)0.00);
        }
        else{
            for(Subject s :list){
                score = score +s.getScore();
                total= total+s.getTotal();
            }
            percentage = (float)(score / total) * 100;
            //percentage = 7.1*cgpa +11
            cgpa = (float)(percentage -11)/(float)7.1;
            if(cgpa>10){
                cgpa=10.00f;
            }
            data.put("cgpa",cgpa);
            data.put("percentage",percentage);
        }
        System.out.println("MAP FORMED:");
        System.out.println("PERC:"+data.get("percentage"));
        System.out.println("CGPA:"+data.get("cgpa"));
        return data;
    }

    public boolean checkNameValid(String subjName) {
        return (!subjName.equals("")) && (subjName != null) && (subjName.matches("^[a-zA-Z]*$"));
    }


    public boolean checkMarksValid(String subjScore, String subjTotal) {
        boolean marksInValid=false;
        int score=0;
        int total=0;
        try{
            score= Integer.parseInt(subjScore);
            total = Integer.parseInt(subjTotal);
        }
        catch(NumberFormatException e){
            marksInValid=true;
            System.out.println("executed number format");
        }
        if(score>total){
            marksInValid=true;
        }
        return marksInValid;
    }
}
