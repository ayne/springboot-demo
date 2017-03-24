package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@Slf4j
public class DemoApplication implements CommandLineRunner { //implementing CommandLineRunner is not needed if you don't want to execute jdbc command on application startup

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {
		//auto insert user in DB. for demo purposed only
		try {
			String encryptedPassword = new BCryptPasswordEncoder().encode("pHiLippE");
			jdbcTemplate.execute("INSERT INTO users (id, email, password) VALUES (1, \'charmane.santiago@gmail.com\'," +
					" \'" + encryptedPassword + "\')");
		} catch (DataIntegrityViolationException e) {
			log.warn("DataIntegrityException during insertion of user ", e);
		}
	}

}
