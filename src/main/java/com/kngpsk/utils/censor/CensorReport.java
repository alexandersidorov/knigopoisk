package com.kngpsk.utils.censor;

import java.util.Map;

public class CensorReport {

    public static String getErrorReport(Map<String,Integer>errors){

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
}
