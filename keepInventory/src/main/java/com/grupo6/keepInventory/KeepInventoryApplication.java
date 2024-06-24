package com.grupo6.keepInventory;

import com.grupo6.keepInventory.Model.Usuario;
import com.grupo6.keepInventory.Repository.UsuarioRepository;
import com.grupo6.keepInventory.Service.PasswordHashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class KeepInventoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(KeepInventoryApplication.class, args);
	}
}
