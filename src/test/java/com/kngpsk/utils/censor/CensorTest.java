package com.kngpsk.utils.censor;

import com.kngpsk.utils.StringToList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CensorTest {

    private static List<String> badWords;
    private static Censor censor;

    private final String str1 = "стоп стоп стоп \nЗАПРЕТ ЗАПРЕТ";
    private final String str2 = "один один один \nдва два два\nтри три три";
    private final String str3 = "";

    @BeforeClass
    public static void setUp(){
        String badStr = "стоп запрет Стоп Запрет СТОП ЗАПРЕТ";
        badWords = StringToList.getListBySpace(badStr);

        censor = new Censor();
    }

    @Before
    public void setBadWords(){
        censor.setBadWords(badWords);
    }

    @Test
    public void haveErrors() {
        Map<String,Integer> errs = censor.getErrors(StringToList.getListByStr(str1));
        int actual1 = errs.get("стоп");
        int actual2 = errs.get("ЗАПРЕТ");

        Assert.assertEquals(3,actual1);
        Assert.assertEquals(2,actual2);
    }

    @Test
    public void haveNoErrors(){
        Map<String,Integer> errs = censor.getErrors(StringToList.getListByStr(str2));

        for(String word:badWords) {
            int actual = errs.get(word);
            Assert.assertEquals(0,actual);
        }
    }

    @Test
    public void emptyString(){
        Map<String,Integer> errs = censor.getErrors(StringToList.getListByStr(str3));

        for(String word:badWords) {
            int actual = errs.get(word);
            Assert.assertEquals(0,actual);
        }
    }

    @Test
    public void emptyBadWords(){

        List<String> emptyBadWords = StringToList.getListByStr("");
        censor.setBadWords(emptyBadWords);

        Map<String,Integer> errs = censor.getErrors(StringToList.getListByStr(str1));

        int actual = errs.size();
        Assert.assertEquals(0,actual);
    }
}