package com.sriram.themevest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
		exclude = {DataSourceAutoConfiguration.class}
)
public class ThemeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThemeServiceApplication.class, args);
	}

}
