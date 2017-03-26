package org.launchcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ="org.launchcode.pyw.controllers")
public class PaintYourWorldApplication {

	public static void main(String[] args) {
		System.out.println(args.length);
		SpringApplication.run(PaintYourWorldApplication.class, args);
	}
}
