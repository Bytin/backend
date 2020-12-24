package tech.bytin.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
        }
        
        @RequestMapping("/hello")
        public String hello(){
               return "Hi there, " + SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        }

}
