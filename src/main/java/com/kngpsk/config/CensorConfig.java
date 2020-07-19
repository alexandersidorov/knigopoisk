package com.kngpsk.config;

import com.kngpsk.utils.FileToList;
import com.kngpsk.utils.censor.Censor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
public class CensorConfig {

    @Value("${pathToCensor}")
    private String path;

    @Bean
    public Censor getCensor(){
        Censor censor = new Censor();

        List<String> stopList;
        try{
           stopList = FileToList.getFileToList(path);
        }catch(IOException exception){
            stopList = null;
        }

        censor.setBadWords(stopList);

        return censor;
    }
}
