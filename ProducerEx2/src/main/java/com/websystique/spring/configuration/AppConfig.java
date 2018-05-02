package com.websystique.spring.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.websystique.spring")
@Import({MessagingConfiguration.class, MessagingListnerConfiguration.class})
public class AppConfig {
	
	//Put Other Application configuration here.
}
