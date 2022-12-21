package com.hubilon.dj_etri.fileloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileLoaderApplication {

    public static void main(String[] args) {
        SpringApplication toolsApplication = new SpringApplication(FileLoaderApplication.class);
        toolsApplication.setWebApplicationType(WebApplicationType.NONE);
        toolsApplication.run(args);
    }

}
