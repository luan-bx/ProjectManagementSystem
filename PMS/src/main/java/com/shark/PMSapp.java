package com.shark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.shark.users.mapper", "com.shark.project.mapper", "com.shark.base.mapper"})
public class PMSapp {

	public static void main(String[] args) {
		SpringApplication.run(PMSapp.class, args);
	}
}
