package com.cognizant.tweeterapp.tweeterapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableMongoRepositories("com.cognizant.tweeterapp.tweeterapp.repository")
@EnableAspectJAutoProxy
public class TweeterappApplication {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(TweeterappApplication.class);

		SpringApplication.run(TweeterappApplication.class, args);
	}

}
