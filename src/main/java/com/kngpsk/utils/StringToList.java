package com.kngpsk.utils;

import java.util.Arrays;
import java.util.List;

public class StringToList {
    public static List<String> getStringList(String str){
        return Arrays.asList(str.split("\n"));
    }
}
