package com.example.eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.eventos.models.Rol;
import com.example.eventos.repositories.RolRepository;


@SpringBootApplication
public class Main implements CommandLineRunner
{
	@Autowired
	private RolRepository rRoles;

	public static void main(String[] args) 
	{
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String ... args)
	{
		Rol nuevo_rol = new Rol();
		
		nuevo_rol.setNombre("Admin");
		rRoles.save(nuevo_rol);
	}
}
