package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private DBSettingRepository repository;

	@Autowired
	private EntityManager em;

	@Autowired
	private ApplicationContext context;


	@Test
	@Transactional
	public void contextLoads() {
		repository.findAll().forEach(db -> {
			System.out.println(db.getLookupKey());
		});
	}

	@Test
	public void testMultipleDataSources() {
		DataSource routingDataSource = (DataSource) context.getBean("routingDataSource");
		Assert.assertNotNull(routingDataSource);
	}
}
