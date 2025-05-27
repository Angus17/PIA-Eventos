package com.example.eventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.eventos.exceptions.EventoNoEncontradoException;
import com.example.eventos.exceptions.OrganizadorNoEncontradoException;
import com.example.eventos.exceptions.UbicacionNoEncontradaException;
import com.example.eventos.exceptions.UsuarioNoEncontradoException;
import com.example.eventos.exceptions.ZonaPrecioNoEncontradoException;
import com.example.eventos.models.Cliente;
import com.example.eventos.models.Empleado;
import com.example.eventos.models.Evento;
import com.example.eventos.models.Organizador;
import com.example.eventos.models.Rol;
import com.example.eventos.models.Ubicacion;
import com.example.eventos.models.Usuario;
import com.example.eventos.models.ZonaPrecio;
import com.example.eventos.repositories.ClienteRepository;
import com.example.eventos.repositories.EmpleadoRepository;
import com.example.eventos.repositories.EstadoRepository;
import com.example.eventos.repositories.EventoRepository;
import com.example.eventos.repositories.MunicipioRepository;
import com.example.eventos.repositories.OrganizadorRepository;
import com.example.eventos.repositories.UbicacionRepository;
import com.example.eventos.repositories.UsuarioRepository;
import com.example.eventos.repositories.ZonaPrecioRepository;


@SpringBootApplication
public class Main
{
	LocalDateTime fechaHora = null;
	Ubicacion ubicacionSeleccionada = null;
	Organizador organizadorSeleccionado = null;
	ZonaPrecio zonaPrecioSeleccionada = null;
	Evento eventoSeleccionado = null;

	@Autowired
    private EstadoRepository estadoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UbicacionRepository ubicacionRepository;

	@Autowired
	private OrganizadorRepository organizadorRepository;

	@Autowired
	private ZonaPrecioRepository zonaPrecioRepository;

	@Autowired
	private EventoRepository eventoRepository;
	

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

	//Solo tiene acceso el usuario Administrador
	public void gestionarUsuarios( Usuario usuario_login )
	{
		int opcion_menu_usuarios;

		do
		{
			limpiar_pantalla();
			System.out.println("********************** GESTION DE USUARIOS **********************");
			System.out.println("1. Crear Usuario");
			System.out.println("2. Listar Usuarios");
			System.out.println("3. Actualizar Usuario");
			System.out.println("4. Eliminar Usuario");
			System.out.println("5. Volver al menú principal");
			System.out.print("Seleccione una opción: ");
			
			opcion_menu_usuarios = Integer.parseInt(System.console().readLine());

			switch (opcion_menu_usuarios)
			{
				case 1 -> 
				{
					int tipo_usuario;
					String nombre_usuario, contrasenia, estado, municipio;
					String nombre_empleado_cliente;
					String apellido, segundo_apellido;
					String calle, colonia;
					Integer numero_domicilio;

					do 
					{ 
						limpiar_pantalla();
						
						System.out.println("1.Empleado");
						System.out.println("2.Cliente");

						System.out.println("Seleccione el tipo de usuario a crear:");
						tipo_usuario = Integer.parseInt(System.console().readLine());
						if (tipo_usuario < 1 || tipo_usuario > 2) 
						{
							System.out.println("Opción no válida, intente nuevamente.");
						}
						
					} while (  tipo_usuario < 1 || tipo_usuario > 2 );

					limpiar_pantalla();

					System.out.print("Ingrese el nombre del usuario: ");
					nombre_usuario = System.console().readLine();

					System.out.print("Ingrese la contraseña del usuario: ");
					contrasenia = System.console().readLine();

					// Encriptar la contraseña
					BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
					String contrasenia_encriptada = encoder.encode(contrasenia);

					
					if( tipo_usuario == 1 )
					{
						limpiar_pantalla();
						System.out.print("Ingrese el nombre del empleado: ");
						nombre_empleado_cliente = System.console().readLine();
						
						System.out.print("Ingrese el apellido del empleado: ");
						apellido = System.console().readLine();
						
						System.out.print("Ingrese el segundo apellido del empleado: ");
						segundo_apellido = System.console().readLine();
						
						System.out.print("Ingrese la calle del domicilio del empleado: ");
						calle = System.console().readLine();
						
						System.out.print("Ingrese el número de domicilio del empleado: ");
						numero_domicilio = Integer.parseInt(System.console().readLine());
						
						System.out.print("Ingrese la colonia del domicilio del empleado: ");
						colonia = System.console().readLine();

					}
					else
					{
						limpiar_pantalla();
						System.out.print("Ingrese el nombre del cliente: ");
						nombre_empleado_cliente = System.console().readLine();

						System.out.print("Ingrese el apellido del cliente: ");
						apellido = System.console().readLine();

						System.out.print("Ingrese el segundo apellido del cliente: ");
						segundo_apellido = System.console().readLine();

						System.out.print("Ingrese la calle del domicilio del cliente: ");
						calle = System.console().readLine();

						System.out.print("Ingrese el número de domicilio del cliente: ");
						numero_domicilio = Integer.parseInt(System.console().readLine());

						System.out.print("Ingrese la colonia del domicilio del cliente: ");
						colonia = System.console().readLine();
						
					}
					
					do 
					{ 
						limpiar_pantalla();

						System.out.println("Escriba el estado de residencia: ");

						estado = System.console().readLine();

						// Validar que exista en la base de datos usando findByNombre()

						if (!estadoRepository.findByNombre(estado).isPresent()) 
						{
							System.out.println("Estado no encontrado, intente nuevamente.");
						}
					} while (!estadoRepository.findByNombre(estado).isPresent());

					do 
					{ 
						limpiar_pantalla();

						System.out.println("Escriba el municipio de residencia: ");

						municipio = System.console().readLine();

						// Validar que exista en la base de datos usando findByNombre()

						if (!municipioRepository.findByNombre(municipio).isPresent()) 
						{
							System.out.println("Municipio no encontrado, intente nuevamente.");
						}
					} while (!municipioRepository.findByNombre(municipio).isPresent());

					
					if( tipo_usuario == 1 )
					{
						Empleado empleado = new Empleado();
						empleado.setNombre(nombre_empleado_cliente);
						empleado.setApellido(apellido);
						empleado.setSegundoApellido(segundo_apellido);
						empleado.setCalle(calle);
						empleado.setNumeroDomicilio(numero_domicilio);
						empleado.setColonia(colonia);
						empleado.setMunicipio(municipioRepository.findByNombre(municipio).orElse(null));
						empleado.setEstado(estadoRepository.findByNombre(estado).orElse(null));

						empleadoRepository.save(empleado);

						Rol rol_empleado = new Rol();
						rol_empleado.setIdRol(2); // Asignar ID de rol de Empleado
						rol_empleado.setNombre("Empleado");

						Usuario usuario = new Usuario();
						usuario.setNombreUsuario(nombre_usuario);
						usuario.setContrasenia(contrasenia_encriptada);
						usuario.setRol(rol_empleado);
						usuario.setEmpleado(empleado);
						usuarioRepository.save(usuario);

						limpiar_pantalla();
						System.out.println("Usuario Empleado creado exitosamente.");
						pausar_terminal();
					}
					else
					{
						Cliente cliente = new Cliente();
						cliente.setNombre(nombre_empleado_cliente);
						cliente.setApellido(apellido);
						cliente.setSegundoApellido(segundo_apellido);
						cliente.setCalle(calle);
						cliente.setNumeroDomicilio(numero_domicilio);
						cliente.setColonia(colonia);
						cliente.setMunicipio(municipioRepository.findByNombre(municipio).orElse(null));
						cliente.setEstado(estadoRepository.findByNombre(estado).orElse(null));

						clienteRepository.save(cliente);

						Rol rol_cliente = new Rol();
						rol_cliente.setIdRol(3); // Asignar ID de rol de Cliente
						rol_cliente.setNombre("Cliente");

						Usuario usuario = new Usuario();
						usuario.setNombreUsuario(nombre_usuario);
						usuario.setContrasenia(contrasenia_encriptada);
						usuario.setRol(rol_cliente);
						usuario.setEmpleado(null);
						usuarioRepository.save(usuario);



						limpiar_pantalla();
						System.out.println("Usuario Cliente creado exitosamente.");
						pausar_terminal();
					}

				}
				case 2 ->
				{
					usuarioRepository.findAll().forEach(usuario -> 
					{
						System.out.println("Nombre de Usuario: " + usuario.getNombreUsuario());
						System.out.println("Rol: " + usuario.getRol().getNombre());
						if (usuario.getEmpleado() != null) 
						{
							System.out.println("Empleado: " + usuario.getEmpleado().getNombre() + " " + usuario.getEmpleado().getApellido());
						}
						System.out.println("-----------------------------");
					});
				}
				case 3 -> 
				{
					limpiar_pantalla();
					System.out.print("Ingrese el nombre de usuario a actualizar: ");
					String nombre_usuario_actualizar = System.console().readLine();

					Usuario usuario = usuarioRepository.findByNombreUsuario(nombre_usuario_actualizar).orElse(null);

					if (usuario != null) 
					{
						System.out.print("Ingrese el nuevo nombre de usuario: ");
						String nuevo_nombre_usuario = System.console().readLine();

						System.out.print("Ingrese la nueva contraseña: ");
						String nueva_contrasenia = System.console().readLine();

						// Encriptar la nueva contraseña
						BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
						String nueva_contrasenia_encriptada = encoder.encode(nueva_contrasenia);

						usuario.setNombreUsuario(nuevo_nombre_usuario);
						usuario.setContrasenia(nueva_contrasenia_encriptada);

						usuarioRepository.save(usuario);

						System.out.println("Usuario actualizado exitosamente.");
					} 
					else 
					{
						System.out.println("Usuario no encontrado.");
					}

				}
				case 4 -> 
				{
					//Eliminar un usuario

					limpiar_pantalla();

					System.out.print("Ingrese el nombre de usuario a eliminar: ");
					String nombre_usuario_eliminar = System.console().readLine();

					Usuario usuario = usuarioRepository.findByNombreUsuario(nombre_usuario_eliminar).orElse(null);
					if (usuario != null) 
					{
						usuarioRepository.delete(usuario);
						System.out.println("Usuario eliminado exitosamente.");
					} 
					else 
					{
						System.out.println("Usuario no encontrado.");
					}

				}
				case 5 -> System.out.println("Volviendo al menú principal...");
				default -> System.out.println("Opción no válida, intente nuevamente.");
			}

			if(opcion_menu_usuarios != 5)
			{
				pausar_terminal();
			}

		} while (opcion_menu_usuarios != 5);

	}

	// Solo tiene acceso el usuario Administrador, Empleado y Cliente
	public void gestionarEventos( Usuario usuario_login )
	{
		int opcion_menu_eventos;
		boolean usuario_admin = false;
		boolean usuario_empleado = false;
		boolean usuario_cliente = false;

		Rol rol = usuario_login.getRol();
		
		switch (rol.getNombre()) 
		{
			case "Administrador" -> usuario_admin = true;
			case "Empleado" -> usuario_empleado = true;
			case "Cliente" -> usuario_cliente = true;
			default -> 
			{

			}
		}
		
		if (usuario_admin || usuario_empleado) 
		{

			System.out.println("********************** GESTION DE EVENTOS **********************");
			
			do 
			{ 
				limpiar_pantalla();
				System.out.println("1. Crear Evento");
				System.out.println("2. Listar Eventos");
				System.out.println("3. Actualizar Evento");
				System.out.println("4. Eliminar Evento");
				System.out.println("5. Volver al menú principal");
				System.out.print("Seleccione una opción: ");
				
				opcion_menu_eventos = Integer.parseInt(System.console().readLine());
	
				switch (opcion_menu_eventos)
				{
					case 1 -> 
					{
						limpiar_pantalla();


						System.out.print("Ingrese el nombre del evento: ");
						String nombre_evento = System.console().readLine();
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");						
						
						do 
						{ 
							limpiar_pantalla();
							
							System.out.print("Ingrese la fecha y hora (formato dd-MM-yyyy HH:mm): ");
							String fecha_evento = System.console().readLine();
							
							fechaHora = null;

							try 
							{
								fechaHora = LocalDateTime.parse(fecha_evento, formatter);
								System.out.println("Fecha y hora ingresada: " + fechaHora);
							} 
							catch (Exception e) 
							{
								System.out.println("Formato inválido. Por favor use el formato dd-MM-yyyy HH:mm");
							}
							
						} while ( fechaHora == null );

						do 
						{ 
							limpiar_pantalla();

							try 
							{
								System.out.println("Seleccione el ID de la ubicacion del evento: ");
		
								ubicacionRepository.findAll().forEach(ubicacion -> 
								{
									System.out.println("ID: " + ubicacion.getIdUbicacion());
									System.out.println("Direccion: " + ubicacion.getCalle() + " " + ubicacion.getNumero() + " " + ubicacion.getColonia());
									System.out.println("Asientos disponibles: " + ubicacion.getAsientosDisponibles());
									System.out.println("-----------------------------");
								});
		
								Integer id_ubicacion_ingresada = Integer.parseInt(System.console().readLine());
	
								ubicacionSeleccionada = ubicacionRepository.findById(id_ubicacion_ingresada).orElseThrow(() -> new UbicacionNoEncontradaException("Ubicación no disponible para ese ID."));
								
							} 
							catch (UbicacionNoEncontradaException e)
							{
								System.out.println(e.getMessage());
								pausar_terminal();
							}

						} while ( ubicacionSeleccionada == null );
						
						do 
						{ 
							limpiar_pantalla();

							try 
							{
								System.out.println("Estos son los organizadores disponibles, selecciona uno escribiendo su EMPRESA: ");
								
								organizadorRepository.findAll().forEach( organizador ->
								{
									System.out.println("Nombre de Organizador: " + organizador.getNombre() + " " + organizador.getApellidos() + " " + organizador.getSegundoApellido());
									System.out.println("Empresa: " + organizador.getEmpresa());
	
								});

								String empresa_organizador = System.console().readLine();
								
								organizadorSeleccionado = organizadorRepository.findByEmpresa(empresa_organizador).orElseThrow(() -> new OrganizadorNoEncontradoException("Organizador no disponible"));

							} 
							catch (OrganizadorNoEncontradoException e) 
							{
								System.out.println(e.getMessage());
								pausar_terminal();
							}

						} while (organizadorSeleccionado == null);
						
						List<String> zonas = new ArrayList<>();
						int contador = 0, respuesta = 0;

						do 
						{ 
							System.out.println("Estos son las zonas disponibles, selecciona hasta 3: ");
							limpiar_pantalla();

							try 
							{
								zonaPrecioRepository.findAll().forEach(zonaprecio ->
								{
									if( !zonas.contains(zonaprecio.getNombre()) )
									{
										System.out.println("Zona: " + zonaprecio.getNombre());
									}
								});

								String nombre_zona = System.console().readLine();

								zonaPrecioSeleccionada = zonaPrecioRepository.findByNombre(nombre_zona).orElseThrow(() -> new ZonaPrecioNoEncontradoException("Zona no encontrada"));

								zonas.add(nombre_zona);
								contador++;
								
								do 
								{
									System.out.print("¿Desea ingresar otro campo? (1 = Sí, 0 = No): ");
									
									try 
									{
										respuesta = Integer.parseInt(System.console().readLine());
										
										if (respuesta != 0 && respuesta != 1) {
											System.out.println("Opción inválida. Ingrese 1 para Sí o 0 para No.");
											respuesta = -1; // Para que vuelva a pedir
										}
									} 
									catch (NumberFormatException e) 
									{
										System.out.println("Entrada inválida. Por favor, ingrese un número (1 o 0).");
										respuesta = -1; // Para que vuelva a pedir
									}

								} while (respuesta == -1);

							} 
							catch (ZonaPrecioNoEncontradoException e) 
							{
								System.out.println(e.getMessage());
								pausar_terminal();
							}
						} while ( contador < 3 && respuesta != 0);

						
						zonas.forEach(zona ->
						{
							ZonaPrecio nueva_zona = zonaPrecioRepository.findByNombre(zona).orElseThrow();
							Evento nuevo_evento = new Evento();

							nuevo_evento.setNombre(nombre_evento);
							nuevo_evento.setFechaEvento(fechaHora);
							nuevo_evento.setUbicacion(ubicacionSeleccionada);
							nuevo_evento.setOrganizador(organizadorSeleccionado);
							nuevo_evento.setZonaPrecio(nueva_zona);
							eventoRepository.save(nuevo_evento);
						});
					}
					case 2 ->
					{
						limpiar_pantalla();

						eventoRepository.findAll().forEach(evento ->
						{
							System.out.println("- - - - - - - - - - - - - - - - - - - - - - - ");
							
							System.out.println("Evento: " + evento.getNombre() );
							System.out.println("Fecha del evento: " + evento.getFechaEvento());
							System.out.println("Lugar del evento: " + "Calle " + evento.getUbicacion().getCalle() + evento.getUbicacion().getNombre() + " Colonia " + evento.getUbicacion().getColonia() + ", " + evento.getUbicacion().getMunicipio().getNombre() + ", " + evento.getUbicacion().getEstado().getNombre() );
							System.out.println("Zonas disponibles: " + evento.getZonaPrecio().getNombre());

							
							System.out.println("- - - - - - - - - - - - - - - - - - - - - - - \n\n");
						});
					}
					case 3 -> 
					{
						int opcion_actualizacion;
						String nombre_evento = "";

						do 
						{ 
							limpiar_pantalla();

							try 
							{
								System.out.println("Que evento quieres actualizar? Ingresa su nombre: ");
	
								eventoRepository.findAll().forEach(evento ->
								{
									System.out.println("Evento: " + evento.getNombre() );
								});
	
								nombre_evento = System.console().readLine();
	
								eventoSeleccionado = eventoRepository.findByNombre(nombre_evento).orElseThrow(() -> new EventoNoEncontradoException("Evento no encontrado"));
								
							} 
							catch (EventoNoEncontradoException e) 
							{
								System.out.println(e.getMessage());
								pausar_terminal();
							}

						} while (eventoSeleccionado == null);

						do 
						{ 
							limpiar_pantalla();

							System.out.println("1.Actualizar Nombre de evento");
							System.out.println("2.Actualizar Fecha de evento");
							System.out.println("3.Actualizar Lugar del evento");
							System.out.println("4.Actualizar Organizador");
							System.out.println("5.Actualizar Zonas de evento");
							System.out.println("6. Regresar al menu previo");

							opcion_actualizacion = Integer.parseInt(System.console().readLine());

							switch(opcion_actualizacion)
							{
								case 1 ->
								{
									limpiar_pantalla();
									
									
										Optional<Evento> eventoOptional = eventoRepository.findByNombre(nombre_evento);
										System.out.print("Ingrese el nuevo nombre del evento: ");
										String nombre_evento_nuevo = System.console().readLine();

										Evento nuevo_evento = eventoOptional.get();
										nuevo_evento.setNombre(nombre_evento_nuevo);
										eventoRepository.save(nuevo_evento);

										System.out.println("Nombre de Evento actualizado exitosamente!");
								}
								case 2 ->
								{
									
								}
								case 3 ->
								{

								}
								case 4 ->
								{

								}
								case 5 ->
								{

								}
								case 6 ->
								{

								}
							}

							if( opcion_actualizacion != 6 )
							{
								pausar_terminal();
							}

						} while ( opcion_actualizacion != 6 );
					}
					case 4 -> System.out.println("Función para eliminar evento no implementada.");
					case 5 -> System.out.println("Volviendo al menú principal...");
					default -> System.out.println("Opción no válida, intente nuevamente.");
				}



				if(opcion_menu_eventos != 5)
				{
					pausar_terminal();
				}
				
			} while (  opcion_menu_eventos != 5 );
			
		}
		else if (usuario_cliente)
			{
				do
				{
					limpiar_pantalla();
					System.out.println("********************** GESTION DE EVENTOS **********************");
					System.out.println("1. Listar Eventos Proximos");
					System.out.println("2. Comprar Entradas");
					System.out.println("3. Ver Eventos Comprados");
					System.out.println("4. Volver al menú principal");
					System.out.print("Seleccione una opción: ");
					
					opcion_menu_eventos = Integer.parseInt(System.console().readLine());

					switch (opcion_menu_eventos)
					{
						case 1 -> 
						{
							
						}
						case 2 -> System.out.println("Función para comprar entradas no implementada.");
						case 3 -> System.out.println("Función para ver eventos comprados no implementada.");
						case 4 -> System.out.println("Volviendo al menú principal...");
						default -> System.out.println("Opción no válida, intente nuevamente.");
					}

					if(opcion_menu_eventos != 4)
					{
						pausar_terminal();
					}

				} while (opcion_menu_eventos != 2);
			}
	}

// Solo tiene acceso el usuario Administrador y Empleado
	public void gestionarClientes( Usuario usuario_login )
	{
		int opcion_menu_clientes;
		boolean usuario_admin = false;
		boolean usuario_empleado = false;

		Rol rol = usuario_login.getRol();
		
		switch (rol.getNombre()) 
		{
			case "Administrador" -> usuario_admin = true;
			case "Empleado" -> usuario_empleado = true;
			default -> 
			{

			}
		}

		do
		{
			limpiar_pantalla();
			System.out.println("********************** GESTION DE CLIENTES **********************");
			System.out.println("1. Crear Cliente");
			System.out.println("2. Listar Clientes");
			System.out.println("3. Actualizar Cliente");
			System.out.println("4. Eliminar Cliente");
			System.out.println("5. Volver al menú principal");
			System.out.print("Seleccione una opción: ");
			
			opcion_menu_clientes = Integer.parseInt(System.console().readLine());

			switch (opcion_menu_clientes)
			{
				case 1 -> System.out.println("Función para crear cliente no implementada.");
				case 2 -> System.out.println("Función para listar clientes no implementada.");
				case 3 -> System.out.println("Función para actualizar cliente no implementada.");
				case 4 -> System.out.println("Función para eliminar cliente no implementada.");
				case 5 -> System.out.println("Volviendo al menú principal...");
				default -> System.out.println("Opción no válida, intente nuevamente.");
			}

			if(opcion_menu_clientes != 5)
			{
				pausar_terminal();
			}

		} while (opcion_menu_clientes != 5);

	}

	// Solo tiene acceso el usuario Administrador y Empleado
	public void gestionarVentas( Usuario usuario_login )
	{
		int opcion_menu_ventas;
		boolean usuario_admin = false;
		boolean usuario_empleado = false;
		
		Rol rol = usuario_login.getRol();
		
		switch (rol.getNombre()) 
		{
			case "Administrador" -> usuario_admin = true;
			case "Empleado" -> usuario_empleado = true;
			default -> 
			{

			}
		}

		do
		{
			limpiar_pantalla();
			System.out.println("********************** GESTION DE VENTAS **********************");
			System.out.println("1. Listar Ventas");
			System.out.println("2. Actualizar Venta");
			System.out.println("3. Eliminar Venta");
			System.out.println("4. Volver al menú principal");
			System.out.print("Seleccione una opción: ");
			
			opcion_menu_ventas = Integer.parseInt(System.console().readLine());

			switch (opcion_menu_ventas)
			{
				case 1 -> System.out.println("Función para crear venta no implementada.");
				case 2 -> System.out.println("Función para listar ventas no implementada.");
				case 3 -> System.out.println("Función para actualizar venta no implementada.");
				case 4 -> System.out.println("Volviendo al menú principal...");
				default -> System.out.println("Opción no válida, intente nuevamente.");
			}

			if(opcion_menu_ventas != 4)
			{
				pausar_terminal();
			}

		} while (opcion_menu_ventas != 4);

	}

	public void sistemaEventos( Usuario usuario_login )
	{
		int opcion_menu;
		boolean usuario_admin = false;
		boolean usuario_empleado = false;
		boolean usuario_cliente = false;

		Rol rol = usuario_login.getRol();
		
            switch (rol.getNombre()) 
			{
                case "Administrador" -> usuario_admin = true;
                case "Empleado" -> usuario_empleado = true;
                case "Cliente" -> usuario_cliente = true;
                default -> 
				{

                }
            }

		
			limpiar_pantalla();
			System.out.println("********************** Bienvenido al sistema de EVENTOS: **********************");
			
			if( usuario_admin )
			{
				do 
				{ 
					limpiar_pantalla();

					System.out.println("1. Gestion de Usuarios");
					System.out.println("2. Gestion de Eventos");
					System.out.println("3. Gestion de Clientes");
					System.out.println("4. Gestion de Ventas");
					System.out.println("5. Salir");

					System.out.print("Seleccione una opción: ");
					opcion_menu = Integer.parseInt(System.console().readLine());

					switch (opcion_menu)
					{
						case 1 -> gestionarUsuarios(usuario_login);
						case 2 -> gestionarEventos(usuario_login);
						case 3 -> gestionarClientes(usuario_login);
						case 4 -> gestionarVentas(usuario_login);
						case 5 -> System.out.println("Saliendo del sistema...");
						default -> System.out.println("Opción no válida, intente nuevamente.");
					}

					if(opcion_menu != 5)
					{
						pausar_terminal();
					}

				} while (  opcion_menu != 5 );
			}
			else if( usuario_empleado )
				{
					do 
					{ 
						System.out.println("1. Gestion de Eventos");
						System.out.println("2. Gestion de Clientes");
						System.out.println("3. Gestion de Ventas");
						System.out.println("4. Salir");

						System.out.print("Seleccione una opción: ");
						opcion_menu = Integer.parseInt(System.console().readLine());

						switch (opcion_menu)
						{
							case 1 -> gestionarEventos(usuario_login);
							case 2 -> gestionarClientes(usuario_login);
							case 3 -> gestionarVentas(usuario_login);
							case 4 -> System.out.println("Saliendo del sistema...");
							default -> System.out.println("Opción no válida, intente nuevamente.");
						}

						if(opcion_menu != 4)
						{
							pausar_terminal();
						}
						
					} while (  opcion_menu != 4 );
				}
				else if( usuario_cliente )
					{
						do 
						{ 
							System.out.println("1. Gestion de Eventos");
							System.out.println("2. Salir");

							System.out.print("Seleccione una opción: ");
							opcion_menu = Integer.parseInt(System.console().readLine());

							switch (opcion_menu)
							{
								case 1 -> gestionarEventos(usuario_login);
								case 2 -> System.out.println("Saliendo del sistema...");
								default -> System.out.println("Opción no válida, intente nuevamente.");
							}

							if(opcion_menu != 2)
							{
								pausar_terminal();
							}
						
						} while (  opcion_menu != 2 );
					}
					else
					{
						System.out.println("No tienes permisos para acceder al sistema.");
					}
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
							sistemaEventos( usuario );
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
