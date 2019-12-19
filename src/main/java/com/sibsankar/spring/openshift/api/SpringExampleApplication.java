package com.sibsankar.spring.openshift.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@RestController
public class SpringExampleApplication extends SpringBootServletInitializer {
	@GetMapping("/info")
	public String welcome() {
		return "++++++++++++++++++++++++++++  -: Welcome to Redhat Experimental Squard :- +++++++++++++++++++++++++++++++++ ";
	}

	@GetMapping("/{input}")
	public String congrats(@PathVariable String input) {
		return "++++++++++++++++++++++++++++    Hello " + input + ",  Your application deployed successfully.  Cheers...!!!"+ "    ++++++++++++++++++++++++++++";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringExampleApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(SpringExampleApplication.class);
	}
	
}
