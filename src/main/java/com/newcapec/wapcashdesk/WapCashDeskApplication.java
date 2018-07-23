package com.newcapec.wapcashdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={"com.newcapec"})
@SpringBootApplication
public class WapCashDeskApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WapCashDeskApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WapCashDeskApplication.class, args);
	}
}
