package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "dev");
        System.setProperty("spring.data.rest.base-path", "/api");
        System.setProperty("spring.mvc.pathmatch.matching-strategy", "ANT_PATH_MATCHER");
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.MySQL8Dialect");
        System.setProperty("spring.jpa.hibernate.ddl-auto", "none");
        System.setProperty("mybatis.mapper-locations", "classpath:mappers/*.xml");
        System.setProperty("springdoc.swagger-ui.path", "/swagger-ui.html");

        SpringApplication.run(DemoApplication.class, args);
    }

}
