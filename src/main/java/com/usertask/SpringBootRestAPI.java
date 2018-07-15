package com.usertask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages={"com.usertask"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableScheduling
public class SpringBootRestAPI {
	 public static void main(String[] args) {
		   SpringApplication.run(SpringBootRestAPI.class, args);
	    }
}
