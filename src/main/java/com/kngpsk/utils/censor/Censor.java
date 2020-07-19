package com.kngpsk.utils.censor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Censor {

    private List<String> badWords = new ArrayList<>();

    //конструкторы
    public Censor() {}

    //геттеры и сеттеры
    public List<String> getBadWords() {return badWords;}
    public void setBadWords(List<String> badWords) {this.badWords = badWords;}

    //кол-во вхождений подстроки в строку
    private int countInStr(String str,String strIn){

        int count = 0;
        int pos = 0;
        do{
            pos = str.indexOf(strIn,pos);

            if(pos!=-1)++count;
            else break;

            if(pos!=str.length()-1)++pos;
            else break;

        }while (true);

        return count;
    }

    //метод проверки строки на наличие запрещенных слов
    //используется в стриме
    private void haveBad(String str,Map<String,Integer>errors){

        for(String word:badWords){

            if(str.contains(word) && errors.containsKey(word)){
                int count = countInStr(str,word);
                errors.put(word, errors.get(word)+count);
            }
        }
    }

    //формирует map с ошибками
    public Map<String,Integer> getErrors(List<String> controlStrings){

        Map<String,Integer> errors = new HashMap<>();

        for(String word:badWords){
            errors.put(word,0);
        }

        controlStrings.stream().forEach(str -> haveBad(str,errors));

        return errors;
    }

}
