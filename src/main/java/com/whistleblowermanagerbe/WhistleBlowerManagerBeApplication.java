package com.whistleblowermanagerbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WhistleBlowerManagerBeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WhistleBlowerManagerBeApplication.class, args);
    }

}
