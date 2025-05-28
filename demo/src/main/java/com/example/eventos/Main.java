package com.example.eventos;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.eventos.exceptions.EventoNoEncontradoException;
import com.example.eventos.exceptions.MunicipioNoEncontradoException;
import com.example.eventos.exceptions.OrganizadorNoEncontradoException;
import com.example.eventos.exceptions.RolNoEncontradoException;
import com.example.eventos.exceptions.UbicacionNoEncontradaException;
import com.example.eventos.exceptions.UsuarioNoEncontradoException;
import com.example.eventos.exceptions.ZonaPrecioNoEncontradoException;
import com.example.eventos.models.Cliente;
import com.example.eventos.models.Empleado;
import com.example.eventos.models.Estado;
import com.example.eventos.models.Evento;
import com.example.eventos.models.Municipio;
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
import com.example.eventos.repositories.RolRepository;
import com.example.eventos.repositories.UbicacionRepository;
import com.example.eventos.repositories.UsuarioRepository;
import com.example.eventos.repositories.ZonaPrecioRepository;
import com.example.eventos.services.EventoServices;


@SpringBootApplication
public class Main
{
	LocalDateTime fechaHora = null;
	Ubicacion ubicacionSeleccionada = null;
	Organizador organizadorSeleccionado = null;
	ZonaPrecio zonaPrecioSeleccionada = null;
	Evento eventoSeleccionado = null;
	Municipio municipioSeleccionado = null;

	Set<String> conjuntoEventos = new HashSet<>();

	@Autowired
	private EventoServices eventoServices;

	@Autowired
    private ApplicationContext appContext;

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
		catch (IOException | InterruptedException e)
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

	public void listarEventosProximosCliente() 
	{
		limpiar_pantalla();
		System.out.println("----------- EVENTOS PRÓXIMOS -----------");
		
		List<Evento> eventosProximos = eventoRepository.findAllByFechaEventoAfterOrderByFechaEventoAsc(LocalDateTime.now());

		if (eventosProximos.isEmpty()) {
			System.out.println("No hay eventos próximos disponibles en este momento.");
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			System.out.println("ID | Evento                 | Fecha y Hora        | Zona                   | Precio   | Lugar");
			System.out.println("----------------------------------------------------------------------------------------------------");
			for (Evento evento : eventosProximos) {
				String nombreLugar = evento.getUbicacion() != null ? evento.getUbicacion().getNombre() : "N/A"; // Asumiendo que Ubicacion tiene getNombre()
				String nombreZona = evento.getZonaPrecio() != null ? evento.getZonaPrecio().getNombre() : "N/A";
				BigDecimal precioZona = evento.getZonaPrecio() != null ? evento.getZonaPrecio().getPrecio() : BigDecimal.ZERO;

				System.out.printf("%-3d| %-22s | %-19s | %-22s | $%-7.2f | %s\n",
						evento.getIdEvento(),
						evento.getNombre(),
						evento.getFechaEvento().format(formatter),
						nombreZona,
						precioZona,
						nombreLugar);
			}
			System.out.println("----------------------------------------------------------------------------------------------------");
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
					String nombre_usuario, contrasenia, estado, municipio = "";
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

					Optional<Estado> estadoOptional = estadoRepository.findByNombre(estado);
					Estado estadoObtenido = estadoOptional.get();

					do 
					{ 
						limpiar_pantalla();

						try 
						{
							System.out.println("Escriba el municipio de residencia: ");
	
							municipio = System.console().readLine();
	
							// Validar que exista en la base de datos usando findByNombre()
							
							municipioSeleccionado = municipioRepository.findByNombreAndEstado(municipio, estadoObtenido).orElseThrow(() -> new MunicipioNoEncontradoException("Municipio no encontrado"));

						} 
						catch (MunicipioNoEncontradoException e) 
						{
							System.out.println(e.getMessage());
							pausar_terminal();
						}

					} while (municipioSeleccionado == null);

					
					if( tipo_usuario == 1 )
					{
						Empleado empleado = new Empleado();
						empleado.setNombre(nombre_empleado_cliente);
						empleado.setApellido(apellido);
						empleado.setSegundoApellido(segundo_apellido);
						empleado.setCalle(calle);
						empleado.setNumeroDomicilio(numero_domicilio);
						empleado.setColonia(colonia);
						empleado.setMunicipio(municipioSeleccionado);
						empleado.setEstado(estadoObtenido);

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
						cliente.setMunicipio(municipioSeleccionado);
						cliente.setEstado(estadoObtenido);

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
						if (usuario.getRol().getNombre().equals("Empleado")) 
						{
							Empleado empleado_eliminar = usuario.getEmpleado();
							empleadoRepository.delete(empleado_eliminar);
						}

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

							conjuntoEventos.add(nombre_evento);
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
					{ // Actualizar Evento
						int opcion_actualizacion;
						String nombre_evento_base; // Renombrado para claridad
						Evento eventoParaActualizar = null; // El evento específico que se modificará

						limpiar_pantalla();
						System.out.println("--- Actualizar Evento ---");

						// Paso 1: Pedir el nombre base del evento
						System.out.println("Eventos existentes (nombres base):");
						
						conjuntoEventos.clear(); // Limpiar para obtener la lista más actualizada
						eventoRepository.findAll().forEach(evento -> conjuntoEventos.add(evento.getNombre()));
						if (conjuntoEventos.isEmpty()) {
							System.out.println("No hay eventos para actualizar.");
							pausar_terminal();
							break; // Salir del case 3
						}
						for (String nombreUnico : conjuntoEventos) {
							System.out.println("- " + nombreUnico);
						}
						System.out.print("Ingrese el nombre base del evento que desea actualizar: ");
						nombre_evento_base = System.console().readLine();

						// Paso 2: Encontrar todas las presentaciones de ese evento
						List<Evento> eventosConEseNombre = eventoRepository.findAllByNombre(nombre_evento_base);

						if (eventosConEseNombre.isEmpty()) {
							System.out.println("No se encontró ningún evento con el nombre: " + nombre_evento_base);
						} else if (eventosConEseNombre.size() == 1) {
							eventoParaActualizar = eventosConEseNombre.get(0);
							System.out.println("Se actualizará el evento: " + eventoParaActualizar.getNombre() + " (ID: " + eventoParaActualizar.getIdEvento() + ")");
						} else {
							System.out.println("Se encontraron múltiples presentaciones para '" + nombre_evento_base + "'. Por favor, seleccione una por ID:");
							DateTimeFormatter formatterLista = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
							for (Evento ev : eventosConEseNombre) {
								System.out.printf("ID: %-5d | Nombre: %-20s | Fecha: %-16s | Zona: %-15s | Ubicación: %s\n",
										ev.getIdEvento(),
										ev.getNombre(),
										ev.getFechaEvento() != null ? ev.getFechaEvento().format(formatterLista) : "N/A",
										ev.getZonaPrecio() != null ? ev.getZonaPrecio().getNombre() : "N/A",
										ev.getUbicacion() != null ? ev.getUbicacion().getNombre() : "N/A");
							}
							System.out.print("Ingrese el ID del evento específico que desea actualizar: ");
							try {
								Integer idSeleccionado = Integer.parseInt(System.console().readLine());
								Optional<Evento> eventoOpt = eventosConEseNombre.stream()
																.filter(ev -> ev.getIdEvento().equals(idSeleccionado))
																.findFirst();
								if (eventoOpt.isPresent()) {
									eventoParaActualizar = eventoOpt.get();
								} else {
									System.out.println("ID no válido o no corresponde a los eventos listados.");
								}
							} catch (NumberFormatException e) {
								System.out.println("Entrada no válida para el ID.");
							}
						}

						// Paso 3: Si se seleccionó un evento específico, proceder con el menú de actualización
						if (eventoParaActualizar != null) {
							
							final Evento eventoFinalParaActualizar = eventoParaActualizar; // Necesario para lambdas/clases anónimas si las usas

							do {
								limpiar_pantalla();
								System.out.println("Actualizando Evento: " + eventoFinalParaActualizar.getNombre() + " (ID: " + eventoFinalParaActualizar.getIdEvento() + ")");
								System.out.println("Zona: " + (eventoFinalParaActualizar.getZonaPrecio() != null ? eventoFinalParaActualizar.getZonaPrecio().getNombre() : "N/A"));
								System.out.println("1. Actualizar Nombre de esta presentación del evento"); // Cambiado para reflejar que es una presentación
								System.out.println("2. Actualizar Fecha de esta presentación");
								System.out.println("3. Actualizar Lugar de esta presentación");
								System.out.println("4. Actualizar Organizador de esta presentación");
								
								System.out.println("5. Regresar al menú previo");
								System.out.print("Seleccione una opción: ");
								
								opcion_actualizacion = 0; // Resetear
								try {
									opcion_actualizacion = Integer.parseInt(System.console().readLine());
								} catch (NumberFormatException e) {
									System.out.println("Opción inválida. Intente de nuevo.");
									pausar_terminal();
									continue;
								}


								switch (opcion_actualizacion) 
								{
									case 1 -> 
									{
										// Actualizar Nombre de esta presentación
										limpiar_pantalla();
										System.out.print("Ingrese el nuevo nombre para esta presentación del evento: ");
										String nombre_evento_nuevo = System.console().readLine();
										eventoFinalParaActualizar.setNombre(nombre_evento_nuevo);
										eventoRepository.save(eventoFinalParaActualizar);
										System.out.println("Nombre de la presentación del evento actualizado exitosamente!");
									}
									case 2 -> 
									{
										DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
										LocalDateTime nuevaFechaHora = null;
										do {
											limpiar_pantalla();
											System.out.print("Ingrese la nueva fecha del evento (dd-MM-yyyy HH:mm): ");
											String fecha_evento_str = System.console().readLine();
											try {
												nuevaFechaHora = LocalDateTime.parse(fecha_evento_str, formatter);
											} catch (Exception e) {
												System.out.println("Formato inválido. Por favor use el formato dd-MM-yyyy HH:mm");
											}
										} while (nuevaFechaHora == null);
										eventoFinalParaActualizar.setFechaEvento(nuevaFechaHora);
										eventoRepository.save(eventoFinalParaActualizar);
										System.out.println("Fecha del evento actualizada exitosamente!");
									}
									case 3 -> 
									{
										// Actualizar Lugar
			
										Ubicacion nuevaUbicacionSeleccionada = null;
										do {
											limpiar_pantalla();
											System.out.println("Seleccione el ID de la nueva ubicación del evento:");
											ubicacionRepository.findAll().forEach(ubicacion -> System.out.printf("ID: %d, Nombre: %s, Dirección: %s %s, %s\n", ubicacion.getIdUbicacion(), ubicacion.getNombre(), ubicacion.getCalle(), ubicacion.getNumero(), ubicacion.getColonia()));
											System.out.print("ID Ubicación: ");
											try {
												Integer id_ubicacion_ingresada = Integer.parseInt(System.console().readLine());
												nuevaUbicacionSeleccionada = ubicacionRepository.findById(id_ubicacion_ingresada)
														.orElseThrow(() -> new UbicacionNoEncontradaException("Ubicación no disponible para ese ID."));
											} catch (NumberFormatException e) {
												System.out.println("ID de ubicación inválido.");
											} catch (UbicacionNoEncontradaException e) {
												System.out.println(e.getMessage());
												pausar_terminal();
											}
										} while (nuevaUbicacionSeleccionada == null);
										eventoFinalParaActualizar.setUbicacion(nuevaUbicacionSeleccionada);
										eventoRepository.save(eventoFinalParaActualizar);
										System.out.println("Lugar del evento actualizado exitosamente!");
									}
									case 4 -> 
									{
										// Actualizar Organizador
										Organizador nuevoOrganizadorSeleccionado = null;
										do {
											limpiar_pantalla();
											System.out.println("Estos son los organizadores disponibles, selecciona al nuevo escribiendo su EMPRESA:");
											organizadorRepository.findAll().forEach(organizador -> System.out.printf("Empresa: %s, Nombre: %s %s\n", organizador.getEmpresa(), organizador.getNombre(), organizador.getApellidos()));
											System.out.print("Empresa del organizador: ");
											try {
												String empresa_organizador = System.console().readLine();
												nuevoOrganizadorSeleccionado = organizadorRepository.findByEmpresa(empresa_organizador)
														.orElseThrow(() -> new OrganizadorNoEncontradoException("Organizador no disponible con esa empresa."));
											} catch (OrganizadorNoEncontradoException e) {
												System.out.println(e.getMessage());
												pausar_terminal();
											}
										} while (nuevoOrganizadorSeleccionado == null);
										eventoFinalParaActualizar.setOrganizador(nuevoOrganizadorSeleccionado);
										eventoRepository.save(eventoFinalParaActualizar);
										System.out.println("Organizador actualizado exitosamente!");
									}
									case 5 -> 
									{
										limpiar_pantalla();
										System.out.println("Regresando al menú previo...");
									}
									default -> System.out.println("Opción no válida, intente nuevamente.");
								}

								if (opcion_actualizacion >= 1 && opcion_actualizacion <= 4) { // Solo pausar si se hizo una actualización
									pausar_terminal();
								}

							} while (opcion_actualizacion != 5);
							// --- Fin de tu lógica de submenú de actualización ---
						} else {
							// No se seleccionó un evento para actualizar (o no se encontró)
							System.out.println("No se ha seleccionado un evento para actualizar.");
						}
						if (eventoParaActualizar == null) { // Si no se actualizó nada o no se encontró evento
							pausar_terminal();
						}
					} // Fin del case 3

					case 4 -> 
					{
						limpiar_pantalla(); // Moví limpiar_pantalla aquí para que se vea el menú antes.
						String nombre_evento_a_eliminar = ""; // Renombrado para claridad

						// Lógica para obtener el nombre del evento a eliminar (la tenías un poco mezclada con la de actualizar)
						// Simplificando para el ejemplo de borrado:
						System.out.println("¿Qué evento (nombre base) quieres eliminar todas sus presentaciones?");
						if (conjuntoEventos.isEmpty()) {
							eventoRepository.findAll().forEach(ev -> conjuntoEventos.add(ev.getNombre()));
						}
						conjuntoEventos.forEach(nombre -> System.out.println("- " + nombre));
						System.out.print("Ingresa el nombre base del evento a eliminar: ");
						nombre_evento_a_eliminar = System.console().readLine();

						// Confirmación
						List<Evento> eventosAEliminar = eventoRepository.findAllByNombre(nombre_evento_a_eliminar);
						if (eventosAEliminar.isEmpty()) {
							System.out.println("No se encontraron eventos con el nombre: " + nombre_evento_a_eliminar);
						} 
						else 
						{
							System.out.println("Se encontraron " + eventosAEliminar.size() + " presentaciones para el evento '" + nombre_evento_a_eliminar + "'.");
							System.out.print("¿Está seguro de que desea eliminar TODAS estas presentaciones? (Sí/No): ");
							String confirmacion = System.console().readLine();

							if (confirmacion.equalsIgnoreCase("Sí") || confirmacion.equalsIgnoreCase("Si")) {
								try {
									// ANTES (causaba error de transacción):
									// eventoRepository.deleteAllByNombre(nombre_evento_a_eliminar);

									// AHORA: Llama al método del servicio
									eventoServices.borrarEventosPorNombre(nombre_evento_a_eliminar);

									conjuntoEventos.remove(nombre_evento_a_eliminar); // Actualiza tu Set local
									System.out.println("Todas las presentaciones del evento '" + nombre_evento_a_eliminar + "' han sido eliminadas.");
								} catch (Exception e) { // Captura excepciones más generales por si acaso
									System.out.println("Ocurrió un error al eliminar el evento: " + e.getMessage());
									e.printStackTrace(); // Útil para depuración
								}
							} else {
								System.out.println("Operación de eliminación cancelada.");
							}
						}
					}
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
							listarEventosProximosCliente();
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

				} while (opcion_menu_eventos != 4);
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
					System.out.println("5. Cerrar Sesion");

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
						System.out.println("4. Cerrar Sesion");

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
							System.out.println("2. Cerrar Sesion");

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

						int exitcode = SpringApplication.exit(ctx, () -> 0);

						System.exit(exitcode);
						
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