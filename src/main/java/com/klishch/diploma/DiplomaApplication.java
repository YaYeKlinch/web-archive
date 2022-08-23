package com.klishch.diploma;

import com.klishch.diploma.constants.StorageProperties;
import com.klishch.diploma.services.FileSystemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DiplomaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiplomaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileSystemService fileSystemService) {
		return (args) -> {
			fileSystemService.init();
		};
	}

}
