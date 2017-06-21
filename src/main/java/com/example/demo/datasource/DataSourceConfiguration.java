package com.example.demo.datasource;

import com.example.demo.DBSetting;
import com.example.demo.DBSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {
//    @Autowired
//    DBSettingRepository repository;

    @Bean(name = "routingDataSource")
    public DataSource routingDataSource() {
        CustomRoutingDataSource routingDataSource = new CustomRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources());

        return routingDataSource;
    }

    @Bean
    Map<Object, Object> targetDataSources() {
        HashMap<Object, Object> dataSourceHashMap = new HashMap<>();

        List<DBSetting> all = new ArrayList<>();
        all.add(new DBSetting("ONE", "jdbc:mysql://localhost:3306/db_01", "root", ""));
        all.add(new DBSetting("TWO", "jdbc:mysql://localhost:3306/db_02", "root", ""));

//        Iterable<DBSetting> all = repository.findAll();

        all.forEach(s -> dataSourceHashMap.put(s.getLookupKey(), createDataSource(s.getUrl(), s.getUsername(), s.getPassword())));

        return dataSourceHashMap;
    }

    private DataSource createDataSource(String url, String username, String password) {
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }
}
