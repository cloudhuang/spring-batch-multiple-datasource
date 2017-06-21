package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private DBSettingRepository repository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        // execute when spring boot started

//        repository.save(new DBSetting("ONE", "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE", "root", ""));
//        repository.save(new DBSetting("TWO", "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE", "root", ""));
//        repository.save(new DBSetting("THREE", "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE", "root", ""));
//        repository.save(new DBSetting("FOUR", "jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE", "root", ""));
    }
}