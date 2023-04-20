package com.example.Student_Library_Management_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class StudentLibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentLibraryManagementSystemApplication.class, args);
	}

}
