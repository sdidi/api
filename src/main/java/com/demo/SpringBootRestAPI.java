package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.demo"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootRestAPI {
	 public static void main(String[] args) {
		   SpringApplication.run(SpringBootRestAPI.class, args);
	    }
}
