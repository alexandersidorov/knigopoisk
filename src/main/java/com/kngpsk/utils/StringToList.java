package com.kngpsk.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringToList {
    public static List<String> getListByStr(String str){
        if(StringUtils.isEmpty(str))
            return new ArrayList<>();
        else
            return Arrays.asList(str.split("\n"));
    }
    public static List<String> getListBySpace(String str){
        if(StringUtils.isEmpty(str))
            return new ArrayList<>();
        else
            return Arrays.asList(str.split(" "));
    }
}
