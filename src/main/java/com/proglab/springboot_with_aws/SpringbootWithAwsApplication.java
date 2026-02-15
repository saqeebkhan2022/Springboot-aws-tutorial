package com.proglab.springboot_with_aws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootWithAwsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWithAwsApplication.class, args);
	}

	@GetMapping("/")
	public String hello(){
		return "Your Springboot App is Deployed Successfully";
	}

}
