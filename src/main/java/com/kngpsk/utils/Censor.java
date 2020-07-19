package com.kngpsk.utils;;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Censor {
    private String pathToFile;
    private List<String> controlStrings;

    private Map<String, Integer> errors = new HashMap<>();
    private List<String> badWords;

    //конструкторы
    public Censor() {}

    public Censor(String pathToFile, List<String> controlStrings) {
        this.pathToFile = pathToFile;
        this.controlStrings = controlStrings;
    }

    //геттеры и сеттеры
    public String getPathToFile() {return pathToFile;}
    public void setPathToFile(String pathToFile) {this.pathToFile = pathToFile;}

    public List<String> getControlStrings() {return controlStrings;}
    public void setControlStrings(List<String> controlStrings) {this.controlStrings = controlStrings;}



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
    private void haveBad(String str){

        for(String word:badWords){

            if(str.contains(word) && errors.containsKey(word)){
                int count = countInStr(str,word);
                errors.put(word, errors.get(word)+count);
            }
        }
    }

    //ленивая загрузка badWords
    private void loadBadWords() throws IOException{

        if(badWords==null){
            badWords = Files.lines(Paths.get(pathToFile)).collect(Collectors.toList());
        }
    }

    //очищает список запрещенных слов
    //после этого метода необходимо будет заново загрузить запрещенные слова
    public void clearBadWords(){
        badWords.clear();
        badWords = null;
    }

    //формирует map с ошибками
    public boolean getErrors(){
        errors.clear();

        try {
            loadBadWords();
        }catch (IOException exception){
            System.out.println("Error with load file.");
        }

        for(String word:badWords){
            errors.put(word,0);
        }

        controlStrings.stream().forEach(this::haveBad);

        boolean ret = false;
        if(errors.size()>0)ret = true;

        return ret;
    }

    //возврат отчета в текстовом виде
    public String getErrorReport(){

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
