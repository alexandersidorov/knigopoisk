package com.kngpsk.services;

import com.kngpsk.utils.StringToList;
import com.kngpsk.utils.censor.Censor;
import com.kngpsk.utils.censor.CensorReport;
import com.kngpsk.utils.censor.ReportSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CensorService {

    @Autowired
    private Censor censor;

    private int addErrors(Map<String,Integer> commonErrors,String str){

        Map<String,Integer> errors = censor.getErrors(StringToList.getListByStr(str));
        errors.forEach(
                (key,value)->commonErrors.merge(key,value,(v1, v2) -> (v1.intValue()+v2.intValue())  ) );
        return commonErrors.size();
    }

    private Map<String,Integer> getAllErrors(String ... strs){
        Map<String,Integer> commonErrors = new HashMap<>();

        for (String str:strs)
            addErrors(commonErrors,str);

        return commonErrors;
    }

    public String censor(String ... strs){

        Map<String,Integer> errors = getAllErrors(strs);
        return CensorReport.getReport(errors, ReportSize.Small);
    }
}
