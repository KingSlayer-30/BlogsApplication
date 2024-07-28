package com.blogapp.BlogApp;

import com.blogapp.BlogApp.config.AppConstants;
import com.blogapp.BlogApp.entities.Role;
import com.blogapp.BlogApp.repository.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("abc"));

		try{
			Role role= new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");

			Role role1= new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");

			List<Role> roles=List.of(role,role1);
			List<Role> results = this.roleRepo.saveAll(roles);

			results.forEach(r->{System.out.println(r.getName());
			});


		}
		catch(Exception e){
				e.printStackTrace();
		}

	}
}
