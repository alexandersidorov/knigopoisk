package com.kngpsk.utils.censor;

import java.util.Iterator;
import java.util.Map;

public class CensorReport {

    public static String getFullErrorReport(Map<String,Integer>errors){

        if (errors.size()==0)return "";

        StringBuilder report = new StringBuilder();
        report.append("Report of Errors:\n");

        for(Map.Entry<String,Integer> entry:errors.entrySet()){

            if(entry.getValue()>0){
                report.append("Found bad word "+entry.getKey()+":"+entry.getValue()+"\n");
            }
        }

        return report.toString();
    }

    public static String getSmallErrorReport(Map<String,Integer>errors){

        if (errors.size()==0)return "";

        StringBuilder report = new StringBuilder();
        report.append("Bad words: ");

        Iterator<Map.Entry<String,Integer> >errIt =  errors.entrySet().iterator();
        while(errIt.hasNext()){
            Map.Entry<String,Integer> entry = errIt.next();
            if(entry.getValue()>0){
                report.append(entry.getKey());
                String s = errIt.hasNext()?", ":".";
                report.append(s);
            }
        }
//        for(Map.Entry<String,Integer> entry:errors.entrySet()){
//
//            if(entry.getValue()>0){
//                report.append(entry.getKey()+", ");
//            }
//        }
//        report.append(".");

        return report.toString();
    }
}
