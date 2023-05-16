package com.pj.alfresco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PiecesJointesAlfrescoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PiecesJointesAlfrescoApplication.class, args);
	}

	// to deploy application add extends SpringBootServletInitializer
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PiecesJointesAlfrescoApplication.class);
	}

}
