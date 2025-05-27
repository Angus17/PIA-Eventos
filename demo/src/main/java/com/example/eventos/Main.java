package com.example.eventos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.eventos.models.Usuario;
import com.example.eventos.repositories.RolRepository;
import com.example.eventos.repositories.UsuarioRepository;
import com.example.eventos.exceptions.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Main
{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	public static void main(String[] args) 
	{
		SpringApplication.run(Main.class, args);
	}

	public static void limpiar_pantalla()
	{
		try
		{
			if (System.getProperty("os.name").toLowerCase().contains("win"))
			{
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}
			else
			{
				new ProcessBuilder("clear").inheritIO().start().waitFor();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void pausar_terminal()
	{
		try
		{
			System.out.println("Presione Enter para continuar...");
			System.console().readLine();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void gestionarUsuarios()
	{

	}

	public static void gestionarEventos()
	{

	}

	public static void gestionarClientes()
	{

	}

	public static void gestionarVentas()
	{

	}

	public static void sistemaEventos()
	{
		int opcion_menu;

		do
		{
			limpiar_pantalla();
			System.out.println("********************** Bienvenido al sistema, SELECCIONA UNA OPCION: **********************");

			System.out.println("1. Gestion de Usuarios");
			System.out.println("2. Gestion de Eventos");
			System.out.println("3. Gestion de Clientes");
			System.out.println("4. Gestion de Ventas");
			System.out.println("5. Salir");
			System.out.print("Seleccione una opción: ");
			
			opcion_menu = Integer.parseInt(System.console().readLine());

			switch (opcion_menu)
			{
				case 1 -> gestionarUsuarios();
				case 2 -> gestionarEventos();
				case 3 -> gestionarClientes();
				case 4 -> gestionarVentas();
				case 5 -> System.out.println("Saliendo del sistema...");
				default -> System.out.println("Opción no válida, intente nuevamente.");
			}

			if(opcion_menu != 5)
			{
				pausar_terminal();
			}

		} while (opcion_menu != 5);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx)
	{
		return args -> 
		{
			// Usuario Semilla
			// User:admineventos
			// Password:administradoreventos
			
			int opcion;
			boolean contrasenia_aceptada;
			
			do
			{
				limpiar_pantalla();

				System.out.println("Bienvenido al sistema de eventos");
				System.out.println("1. Iniciar Sesión");
				System.out.println("2. Salir");
				System.out.print("Seleccione una opción: ");
				
				opcion = Integer.parseInt(System.console().readLine());

				limpiar_pantalla();
				
				switch (opcion)
				{
					case 1 -> 
					{

						BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

						// Inserta el nombre de usuario

						System.out.print("Ingrese su nombre de usuario: ");
						String nombreUsuario = System.console().readLine();

						// Inserta la contraseña
						System.out.print("Ingrese su contraseña: ");
						String contrasena = System.console().readLine();
						
						try
						{
							// Verifica si el usuario existe
							if (!usuarioRepository.findByNombreUsuario(nombreUsuario).isPresent()) 
							{
								throw new UsuarioNoEncontradoException("Usuario no encontrado");
							}
						} 
						catch (UsuarioNoEncontradoException e) 
						{
							System.out.println(e.getMessage());
							pausar_terminal();
							continue;
						}
						
						Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario).orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
						// Verifica la contraseña
						contrasenia_aceptada = encoder.matches(contrasena, usuario.getContrasenia());


						if ( !usuario.getNombreUsuario().equals(nombreUsuario) || !contrasenia_aceptada) 
						{
							System.out.println("Credenciales inválidas. Intente nuevamente.");
							pausar_terminal();
						}
						else
						{
							System.out.println("Bienvenido " + usuario.getNombreUsuario() + "!\n\n");
							sistemaEventos();
						}

                    }
					case 2 -> 
					{
						System.out.println("Saliendo del sistema...");
						return;
                    }
					
					default -> System.out.println("Opción no válida, intente nuevamente.");
				}
				
				if(opcion != 2)
				{
					pausar_terminal();
				}

            } while (opcion != 2);
		};
	}
	
}
