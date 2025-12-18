package com.project.letmenotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
//@EnableCaching
public class LetMeNotifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetMeNotifyApplication.class, args);
    }

}
