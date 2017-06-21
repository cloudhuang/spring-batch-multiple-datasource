package com.example.demo.datasource;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("-------" + LocaleContextHolder.getLocale().getLanguage());
        return "ONE";
    }
}