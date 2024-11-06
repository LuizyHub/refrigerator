package com.refrigerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class RefrigeratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefrigeratorApplication.class, args);
	}

	@Controller
	public static class WelcomeController {

		@GetMapping("/")
		public String redirectToIndexHtml() {
			return "redirect:/refrigerators";
		}
	}

}
