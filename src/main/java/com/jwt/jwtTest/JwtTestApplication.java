package com.jwt.jwtTest;

import com.jwt.jwtTest.domain.Role;
import com.jwt.jwtTest.domain.Userr;
import com.jwt.jwtTest.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;


@SpringBootApplication
public class JwtTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtTestApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new Userr(null, "John Travolta", "john", "1234", new ArrayList<>()));
			userService.saveUser(new Userr(null, "Will Smith", "will", "1234", new ArrayList<>()));
			userService.saveUser(new Userr(null, "Jim Carry", "jim", "1234", new ArrayList<>()));
			userService.saveUser(new Userr(null, "Arnoldd", "arnold", "1234", new ArrayList<>()));

			userService.addRoleToUser("john", "ROLE_USER");
			userService.addRoleToUser("john", "ROLE_MANAGER");
			userService.addRoleToUser("jim", "ROLE_MANAGER");
			userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("arnold", "ROLE_ADMIN");
			userService.addRoleToUser("arnold", "ROLE_USER");

		};
	}

}
