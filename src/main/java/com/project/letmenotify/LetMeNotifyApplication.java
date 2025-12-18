package com.project.letmenotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class LetMeNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetMeNotifyApplication.class, args);
    }

}
