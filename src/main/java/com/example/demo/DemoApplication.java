package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Component
class DataSourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "dataSource")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}

@RestController
class WelcomeController {
    @GetMapping("/")
    public String welcome() {
        return "Welcome";
    }
}