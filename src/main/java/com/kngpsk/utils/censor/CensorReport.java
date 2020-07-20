package com.kngpsk.utils.censor;

import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class CensorReport {

    private static String getFullErrorReport(Map<String,Integer>errors){

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

    private static String getSmallErrorReport(Map<String,Integer>errors){

        if (errors.size()==0)return "";

        StringBuilder report = new StringBuilder();

        //выкидываем все записи с нулевым совпадением
        Map<String,Integer> bufMap =  errors.entrySet().stream().
                filter(entry->entry.getValue()>0).
                collect(Collectors.toMap(entry->entry.getKey(),entry->entry.getValue()));

        Iterator<Map.Entry<String,Integer> >errIt =  bufMap.entrySet().iterator();
        while(errIt.hasNext()){
            Map.Entry<String,Integer> entry = errIt.next();
            if(entry.getValue()>0){
                report.append(entry.getKey());
                String s = errIt.hasNext()?", ":".";
                report.append(s);
            }
        }

        if(!StringUtils.isEmpty(report.toString()))
            report.insert(0,"Bad words: ");

        return report.toString();
    }

    public static String getReport(Map<String,Integer>errors,ReportSize size){
        String ret = null;
        switch (size){
            case Full:{
                ret =  getFullErrorReport(errors);
                break;
            }
            case Small:{
                ret =  getSmallErrorReport(errors);
                break;
            }
        }
        return ret;
    }
}
