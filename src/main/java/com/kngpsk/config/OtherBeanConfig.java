package com.kngpsk.config;

import com.kngpsk.services.FileSaver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtherBeanConfig {

    @Value("${upload.path}")
    private String uploadPath;

    @Bean
    public FileSaver getFileSaver(){
        FileSaver fileSaver = new FileSaver();
        fileSaver.setUploadPath(uploadPath);
        return fileSaver;
    }
}
