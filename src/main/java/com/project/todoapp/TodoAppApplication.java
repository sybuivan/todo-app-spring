package com.project.todoapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(
		info = @Info(
				title = "API todo app",
				version = "1.0.0",
				description = "This project for learning",
				contact = @Contact(
						name = "Bui Van Sy",
						email = "sybuivan1429@gmail.com"
				),
				license = @License(
						name = "syBui",
						url = "sybuivan1429@gmail.com"
				)
		)
)
public class TodoAppApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TodoAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}
}
