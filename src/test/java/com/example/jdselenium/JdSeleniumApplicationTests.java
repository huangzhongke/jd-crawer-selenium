package com.example.jdselenium;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JdSeleniumApplicationTests {

    @Value("${indexUrl}")
    private String indexUrl;
    @Test
    void contextLoads() {
        System.out.println(indexUrl);
    }

}
