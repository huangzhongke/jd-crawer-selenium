package com.example.jdselenium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication
public class JdSeleniumApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdSeleniumApplication.class, args);
    }

}
