package com.ciandt.summit.bootcamp2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableFeignClients
@EnableWebMvc
public class SummitBootcampApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummitBootcampApplication.class, args);
	}

}
