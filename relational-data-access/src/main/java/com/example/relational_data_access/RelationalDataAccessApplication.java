package com.example.relational_data_access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RelationalDataAccessApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {
		log.info("Creating tables");

		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE customers(" +
		"id SERIAL, firstname VARCHAR(255), lastname VARCHAR(255))");

		List<Object[]> splitUpNames = Arrays.asList("Brás Cúbas", "Quincas Borba", "Gaio Tito").stream()
				.map(s -> s.split(" ")).collect(Collectors.toList());

		splitUpNames.forEach(name -> log.info("Inserting customer record for {} {}", name[0], name[1]));

		jdbcTemplate.batchUpdate("INSERT INTO customers(firstname, lastname) VALUES (?,?)", splitUpNames);

		log.info("Querying for customer records where firstname = 'Quincas'");
		jdbcTemplate.query("SELECT id, firstname, lastname FROM customers WHERE firstname = ?",
				 (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("firstname"), rs.getString("lastname")), "Quincas")
				.forEach(customer -> log.info(customer.toString()));
	}
}
