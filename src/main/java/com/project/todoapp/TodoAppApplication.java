package com.project.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@PropertySource("classpath:custom-properties.properties")
@EnableJpaAuditing
//@ComponentScan(basePackages = "com.project.todoapp")
public class TodoAppApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TodoAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}
}
