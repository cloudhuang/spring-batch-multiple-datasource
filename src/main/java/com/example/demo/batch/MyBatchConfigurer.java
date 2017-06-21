package com.example.demo.batch;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class MyBatchConfigurer extends SimpleBatchConfiguration {
    @Bean
    BatchConfigurer configurer(@Qualifier("dataSource") DataSource dataSource) {
        return new DefaultBatchConfigurer(dataSource);
    }
}