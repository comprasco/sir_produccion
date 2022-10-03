/*
 * Receiver.java
 *
 * Created on 27 de agosto de 2004, 12:01
 */
package gov.sir.print.client;

import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWImpresion;
import gov.sir.print.common.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * 
 * @author dcantor, ppabon
 */
public class Receiver extends Observable {
	
	public static final String BUSY = "BUSY";
	public static final String READY = "READY";
	public static final String WAIT = "WAIT";
	private static final int WAIT_INTERVAL = 500;
	
	private String localHost;
	private int listeningPort;
	private int localPortLow;
	private int localPortHigh;
	private String remoteHost;
	private String idUsuario = "";
	private int remotePort;
	private String UID;
	private Processor processor;
	private Log logger;
	private int maxReintentos;
	private boolean forEver = true;
	private ServerListener listener = null;
	private String ipsCliente = "";
	private URL urlServidor = null;
	private boolean hayMasPeticiones = false;
	private boolean imprimiendo = false;
	private String contextoWeb = "";
	ImpresionUtilidades utilidades = null;

	/**
	 * Crea una nueva instancia del receiver
	 */
	public Receiver(String remoteHost, int remotePort, Processor p, String UID,
			int localPortLow, int localPortHigh, URL urlServidor, String idUsuario) {

		logger = new Log(Receiver.class);
		logger.print("Receiver Constructor");

		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.idUsuario = idUsuario;
		this.UID = UID;
		this.processor = p;
		this.localPortLow = localPortLow;
		this.listeningPort = this.localPortLow;
		this.listeningPort = localPortLow;
		this.localPortHigh = localPortHigh;
		this.urlServidor = urlServidor;

		logger.print("RemoteHost    : " + remoteHost);
		logger.print("RemotePort    : " + remotePort);
		logger.print("UID           : " + UID);
		logger.print("localPortLow  : " + localPortLow);
		logger.print("localPortHigh : " + localPortHigh);
		logger.print("Usuario : " + idUsuario);

		try {
			//SE DETERMINA LA DIRECCIÓN LOCAL DEL CLIENTE
			String ipLocalhost = "127.0.0.1";
			this.localHost = InetAddress.getLocalHost().getHostAddress();
			
			//SI EL MÉTODO DE INETADDRESS DEVUELVE LA 127.0.0.1 SE INTENTA CON   
			//CON EL MÉTODO getLocalHostInetAddress DETERMINAR LA IP REAL.
			if (this.localHost.equals(ipLocalhost)) {
				this.localHost = this.getLocalHostInetAddress();
			}
			logger.print("HostAddress(): " + this.localHost);
			
			this.contextoWeb = urlServidor.getPath().substring(0, urlServidor.getPath().indexOf("/", 1));
			utilidades = new ImpresionUtilidades(this.urlServidor, this.contextoWeb, this.listeningPort);
			
		} catch (UnknownHostException uhe) {
			logger.print("No se pudo obtener la IP local: " + uhe);
			uhe.printStackTrace();
			throw new RuntimeException("No se pudo obtener la IP local", uhe);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException("Error al construir Receiver ");
		}
	}

	/**
	 * Método que crea un ServerSocket 
	 * Hace varios intentos si el puerto inferior esta ocupado
	 */
	public void registrar() {
		logger.print("Registrando cliente...");

		boolean serverUP = false;
		boolean conectado = false;

		ServerSocket server = null;

		// 1. CREA EL SERVIDOR LOCAL
		while (!serverUP) {
			try {
				server = new ServerSocket(listeningPort, 20);
				logger.print("Servidor creado en el puerto: " + listeningPort);
				serverUP = true;
			} catch (IOException e) {
				if (listeningPort < localPortHigh) {
					listeningPort++;
					logger.print("Reintentando crear el servidor de sockets " + "por el puerto " + listeningPort);
				} else {
					
					logger.print("Se completo el rango de puertos y no fue posible crear "
							+ "un servidor de sockets. El cliente no funcionara correctamente");
					
					throw new RuntimeException("Se completo el rango de puertos y no fue posible crear "
							+ "un servidor de sockets. El cliente no funcionara correctamente");
					
				}
			}
		}

		//SE ARRANCA EL SERVIDOR LOCAL
		int reintentos = 0;
		setEstado(Receiver.WAIT);		
		while (!conectado && forEver) {
			try {
				logger.print("RemoteHost: " + remoteHost);
				logger.print("RemotePort: " + remotePort);
				logger.print("UID: " + UID);
				logger.print("localHost: " + localHost);
				logger.print("listeningPort: " + listeningPort);

				if (listener == null) {
					// 3. ARRANCA EL SERVIDOR LOCAL
					listener = new ServerListener(server);
					new Thread(listener).start();
				}

				conectado = true;
				setEstado(Receiver.READY);
				
			} catch (Exception e) {
				reintentos++;

				if ((reintentos == maxReintentos) && !forEver) {
					throw new RuntimeException("Se alcanzo el maximo numero de renitentos."
									+ "No fue posible conectarse al servidor");
				}

				logger.print("fallo enviando info al servidor: ["+ e.getMessage() + " - " 
						+ e.getClass().getName()+ "] reintento: " + reintentos);
				
				logger.print("Excepcion es : [" + e + "] ");
				
				StackTraceElement temp[] = e.getStackTrace();
				for (int a = 0; a < temp.length; a++) {
					StackTraceElement element = temp[a];
					logger.print("STACK : " + element.toString());
				}

				try {
					Thread.sleep(Receiver.WAIT_INTERVAL);
				} catch (InterruptedException ie) {
				}
			}
		}
	}
	

	/**
	 * Método para determinar si la sesión actual se encuentra activa en el servidor
	 */
	public boolean verificarSesionUsuario() throws Exception {
		boolean respuesta = false;

		Map parametros = new HashMap();
		parametros.put(WebKeys.ACCION, AWImpresion.VERIFICAR_SESION);
		parametros.put(CImpresion.UID, UID);
		parametros.put(CImpresion.LISTENINGPORT,new Integer(this.listeningPort));

		Object tmp = this.utilidades.realizarPeticion(parametros);

		if (tmp != null && tmp instanceof Boolean) {
			logger.print(" Respuesta verificarSesionUsuario: " + tmp);
			respuesta = ((Boolean) tmp).booleanValue();
		}

		return respuesta;
	}

	
	
	/**
	 * Método que consulta las impresiones pendientes en el servidor para la actual ip
	 * y las intenta imprimir
	 * @throws Exception
	 */
	public void imprimirPendientes() throws Exception {

		logger.print(" ENTRO EN EL METODO IMPRIMIR PENDIENTES.");

		setEstado(Receiver.BUSY);

		String tipoCliente = "";
		if (this.UID.length() <= 3) {
			tipoCliente = CImpresion.CLIENTE_ADMINISTRADOR;
		} else {
			tipoCliente = CImpresion.CLIENTE_LOCAL;
		}

		try {
			List impresionesFallidas = null;
			
			//SE CONSTRUYE LA CADENA PARA CONSULTAR IMPRESIONES FALLIDAS
			String consultarImpresionesFallidas = "http://"
					+ urlServidor.getHost() + ":" + urlServidor.getPort()
					+ contextoWeb + "/infoImpresion.do?" + WebKeys.ACCION + "="
					+ AWImpresion.CONSULTAR_IMPRESIONES_FALLIDAS + "&"
					+ CImpresion.UID + "=" + UID + "&" + CImpresion.LOCALHOST
					+ "=" + localHost + "&" + CImpresion.TIPO_CLIENTE + "="
					+ tipoCliente;
			
			logger.print("CADENA CONSULTAR IMPRESIONES FALLIDAS : "+ consultarImpresionesFallidas);

			URL urlImpresionesFallidas = new URL(consultarImpresionesFallidas);
			URLConnection connectionDescarga = urlImpresionesFallidas.openConnection();
			connectionDescarga.setUseCaches(false);
			ObjectInputStream ois = new ObjectInputStream(connectionDescarga.getInputStream());
			List idImpresionesFallidas = (List) ois.readObject();
			ois.close();

			if (idImpresionesFallidas != null) {
				logger.print("IMPRESIONES LISTAS: "+ (new Date()).toString());
				logger.print("CANTIDAD IMPRESIONES PENDIENTES:"+idImpresionesFallidas.size());

				Iterator it = idImpresionesFallidas.iterator();
				while (it.hasNext()) {
					String id = (String) it.next();
					try {
						logger.print("SE ENVIA A IMPRIMIR EL IDENTIFICADOR: "+id);
						this.processPorIdentificador(id);
					} catch (Exception e) {
					}
				}
			}

		} catch (StreamCorruptedException sce) {
			logger.print((new Date()).toString());
			logger.print("ERROR (StreamCorruptedException):" + sce);
			sce.printStackTrace();
		} catch (MalformedURLException e1) {
			logger.print((new Date()).toString());
			logger.print("ERROR: (MalformedURLException)" + e1);
			e1.printStackTrace();
		} catch (IOException e2) {
			logger.print((new Date()).toString());
			logger.print("ERROR (IOException):" + e2);
			e2.printStackTrace();
		} catch (Exception ex) {
			logger.print((new Date()).toString());
			logger.print("ERROR (Exception):" + ex);
			ex.printStackTrace();
		}

		logger.print(" SALIO DEL METODO IMPRIMIR PENDIENTES ");

		setEstado(Receiver.READY);
		logger.print("FIN");
	}	
	
	
	
	/**
	 * Método que permite la impresión de un imprimible que no fue impreso
	 * @param id
	 * @throws Exception
	 */
	public void processPorIdentificador(String id) throws Exception {

		//SE ACTUALIZAR EL ESTADO A LOS OBSERVADORES
		setEstado(Receiver.BUSY);

		//CADENA PARA REGISTRAR FALLO
		String registrarFallo = "http://" + urlServidor.getHost() + ":"
				+ urlServidor.getPort() + contextoWeb + "/infoImpresion.do?"
				+ WebKeys.ACCION + "=" + AWImpresion.REGISTRAR_FALLO_IMPRESION
				+ "&" + CImpresion.UID + "=" + UID;

		// SE OBTIENE EL IDENTIFICADOR DEL IMPRIMIBLE Y SE SOLICITA AL SERVIDOR
		try {
			Bundle bundle = null;

			logger.print("PROCESAR POR IDENTIFICADOR (IDENTIFICADOR ES ): " + id);
			registrarFallo = registrarFallo + "&" + CImpresion.ID_IMPRIMIBLE + "=" + id;

			String descargarImprimible = "http://" + urlServidor.getHost()
					+ ":" + urlServidor.getPort() + contextoWeb
					+ "/infoImpresion.do?" + WebKeys.ACCION + "="
					+ AWImpresion.DESCARGAR_IMPRIMIBLE + "&" + CImpresion.UID
					+ "=" + UID + "&" + CImpresion.ID_IMPRIMIBLE + "=" + id;
			
			logger.print(" PROCESAR POR IDENTIFICADOR (CADENA DESCARGAR IMPRIMIBLE): "+ descargarImprimible);

			// SE SOLICITA AL SERVIDOR EL ENVIO DEL IMPRIMIBLE, POR EL PROTOCOLO
			// HTTP.  CUANDO RETORNE EL OBJETO SE INTENTA IMPRIMIR
			URL urlDescargaImprimible = new URL(descargarImprimible);
			URLConnection connectionDescarga = urlDescargaImprimible.openConnection();

			ObjectInputStream ois = new ObjectInputStream(connectionDescarga.getInputStream());
            Object obj = ois.readObject();
			ois.close();

			if (obj != null && obj instanceof Bundle) {
				bundle = (Bundle) obj;

				String mensajeError = "";
				boolean procesado = false;
				int intentos = 0;
				while ((!procesado) && (intentos < 3)) {
					try {
						processor.procesar(bundle);
						procesado = true;
					}  catch (ImpNoConfiguradaException e) {
						logger.print("***********************************");
						logger.print("ERROR DE IMPRESORA CONTROLADA: " + e.getMessage());
						logger.print("***********************************");
						e.printStackTrace();
						procesado = true;
					}  catch (Exception e) {
						intentos++;
						mensajeError = e.getMessage();
						logger.print("***********************************");
						logger.print("ERROR DE IMPRESORA: " + e.getMessage());
						logger.print("***********************************");
						e.printStackTrace();
					}
				}

				if (!procesado) {
					try {
						registrarFallo = registrarFallo + "&"+ CImpresion.MENSAJE_ERROR
								+ "=ERROR IMPRESION LOCAL (NO TRANSPORTE):"+ mensajeError;
						
						registrarFallo = quitarEspacios(registrarFallo);
						logger.print(" PROCESAR POR IDENTIFICADOR (CADENA REGISTRAR FALLO) : "+ registrarFallo);
						
						URL urlFallo = new URL(registrarFallo);
						URLConnection connectionError = urlFallo.openConnection();
						BufferedReader verificacion = 
								new BufferedReader(new InputStreamReader(connectionError.getInputStream()));
					} catch (Exception e) {
						logger.print(" PROCESAR POR IDENTIFICADOR. ERROR AL REGISTRAR EL FALLO "+ e.getMessage());
						e.printStackTrace();
					}

				}
			} else {
				logger.print("NO HAY IMPRIMIBLE : SEGURAMENTE EL IMPRIMIBLE ( "+ id + " ) YA FUE IMPRESO.");
				if (obj != null) {
					logger.print("OBJETO ERA DE TIPO."+ obj.getClass().getName());
				} else {
					logger.print("OBJETO ERA NULO.");
				}

			}

		} catch (MalformedURLException e1) {
			logger.print("ERROR (MalformedURLException):" + e1);

			try {
				registrarFallo = registrarFallo + "&"+ CImpresion.MENSAJE_ERROR + "=" + e1.getMessage();
				registrarFallo = quitarEspacios(registrarFallo);
				URL urlFallo = new URL(registrarFallo);
				URLConnection connectionDescarga = urlFallo.openConnection();
				BufferedReader verificacion = new BufferedReader(
						new InputStreamReader(connectionDescarga.getInputStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e2) {
			logger.print("ERROR (IOException):" + e2);
			e2.printStackTrace();
			try {
				registrarFallo = registrarFallo + "&"+ CImpresion.MENSAJE_ERROR + "=" + e2.getMessage();
				registrarFallo = quitarEspacios(registrarFallo);
				URL urlFallo = new URL(registrarFallo);
				URLConnection connectionDescarga = urlFallo.openConnection();
				BufferedReader verificacion = new BufferedReader(
						new InputStreamReader(connectionDescarga.getInputStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			logger.print("ERROR (Exception):" + ex);
			ex.printStackTrace();

			try {
				registrarFallo = registrarFallo + "&"+ CImpresion.MENSAJE_ERROR + "=" + ex.getMessage();
				registrarFallo = quitarEspacios(registrarFallo);
				URL urlFallo = new URL(registrarFallo);
				URLConnection connectionDescarga = urlFallo.openConnection();
				BufferedReader verificacion = new BufferedReader(
						new InputStreamReader(connectionDescarga.getInputStream()));
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {
			logger.print("CADENA REGISTRAR FALLO : " + registrarFallo);
		}

		setEstado(Receiver.READY);
		logger.print("FIN PROCESAR POR IDENTIFICADOR");
	}


	//CLASE QUE SE EJECUTA COMO UN HILO CADA
	//VEZ QUE SE REQUIERE IMPRIMIR ALGO
	private class ServerListener implements Runnable {
		ServerSocket server;

		public ServerListener(ServerSocket server) {
			this.server = server;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			logger.print("Iniciando Servidor Local...");
			while (true) {
				try {
					final Socket peticion = server.accept();				
					
					new Thread(new Runnable() {
						public void run() {
							try {
								logger.print("+++ SE CREA NUEVO THREAD:" + this.toString());
								process(peticion);
							} catch (Exception e) {
								logger.print(e.getMessage());
							}
						}
					}).start();
					if (peticion != null) {
						try {
							logger.print("+++ Cerrando el socket abierto:" + new Date().toString() + ", " + peticion.toString());
							peticion.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Este método procesa una solicitud de impresión. Lo primero que hace
		 * es cerrar el socket y luego delega la responsabilidad de la impresión
		 * al método procesar solicitud
		 * 
		 * @param peticion
		 * @throws Exception
		 */
		public void process(Socket peticion) throws Exception {

			if (peticion != null) {
				try {
					peticion.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			procesarSolicitud();

		}

		/**
		 * Este método controla que si no hay impresiones en curso, se imprima
		 * la solicitud actual. Si hay impresiones en curso notifica para que la
		 * impresión en curso imprima todo lo pendiente
		 * 
		 * @param peticion
		 */
		public void procesarSolicitud() {

			if (imprimiendo) {
				logger.print("ESTAN IMPRIMIENDO");
				hayMasPeticiones = true;
				return;
			} else {
				logger.print("NO ESTAN IMPRIMIENDO");
				imprimiendo = true;
			}

			imprimirFaltantes();
			logger.print("SE IMPRIMEN FALTANTES");
			logger.print("HAY MAS PETICIONES" + hayMasPeticiones);

			while (hayMasPeticiones) {
				hayMasPeticiones = false;
				imprimirFaltantes();
				logger.print("SE IMPRIMIERON FALTANTES, HAY MAS PETICIONES :"+ hayMasPeticiones);
			}
			imprimiendo = false;
			logger.print("IMPRIMIENDO :" + imprimiendo);
			logger.print("HAY MAS PETICIONES :" + hayMasPeticiones);

		}

		/**
		 * @param peticion
		 */
		public void imprimirFaltantes() {

			logger.print(" ENTRO NUEVO PROCESS ");

			setEstado(Receiver.BUSY);

			//SE DETERMINA CUÁL ES EL TIPO DE IMPRESIÓN
			String tipoCliente = "";
			if (UID.length() <= 3) {
				tipoCliente = CImpresion.CLIENTE_ADMINISTRADOR;
			} else {
				tipoCliente = CImpresion.CLIENTE_LOCAL;
			}

			try {
				List impresionesFallidas = null;
				
				String consultarImpresionesFallidas = "http://"
						+ urlServidor.getHost() + ":" + urlServidor.getPort()
						+ contextoWeb + "/infoImpresion.do?" + WebKeys.ACCION
						+ "=" + AWImpresion.CONSULTAR_IMPRESIONES_FALLIDAS
						+ "&" + CImpresion.UID + "=" + UID + "&"
						+ CImpresion.LOCALHOST + "=" + localHost + "&"
						+ CImpresion.TIPO_CLIENTE + "=" + tipoCliente;
				
				logger.print("CADENA CONSULTAR IMPRESIONES FALLIDAS : "+ consultarImpresionesFallidas);

				//SE DETERMINA LA LISTA DE IMPRESIONES PENDIENTES DE IMPRESIÓN
				URL urlImpresionesFallidas = new URL(consultarImpresionesFallidas);
				URLConnection connectionDescarga = urlImpresionesFallidas.openConnection();
				connectionDescarga.setUseCaches(false);
				ObjectInputStream ois = new ObjectInputStream(connectionDescarga.getInputStream());
				List idImpresionesFallidas = (List) ois.readObject();
				ois.close();

				if (idImpresionesFallidas != null) {
					logger.print("IMPRESIONES LISTAS: "+ (new Date()).toString());
					logger.print("CANTIDAD DE IMPRESIONES : "+idImpresionesFallidas.size());

					//POR CADA IMPRESIÓN PENDIENTE SE ENVIA UNA SOLICITUD PARA RECIBIR EL IMPRIMIBLE E IMPRIMIRLO
					Iterator it = idImpresionesFallidas.iterator();
					while (it.hasNext()) {
						String id = (String) it.next();
						
						logger.print("***SE QUIERE IMPRIMIR *** : " + id);
						
						try {
							this.processPorIdentificador(id);
						} catch (Exception e) {
						}
					}
				}

			} catch (StreamCorruptedException sce) {
				logger.print((new Date()).toString());
				logger.print("ERROR (StreamCorruptedException):" + sce);
				sce.printStackTrace();
			} catch (MalformedURLException e1) {
				logger.print((new Date()).toString());
				logger.print("ERROR: (MalformedURLException)" + e1);
				e1.printStackTrace();
			} catch (IOException e2) {
				logger.print((new Date()).toString());
				logger.print("ERROR (IOException):" + e2);
				e2.printStackTrace();
			} catch (Exception ex) {
				logger.print((new Date()).toString());
				logger.print("ERROR (Exception):" + ex);
				ex.printStackTrace();
			}

			logger.print(" SALIO NUEVO PROCESS ");

			setEstado(Receiver.READY);
			logger.print("FIN");

		}

		/**
		 * @param id
		 * @throws Exception
		 */
		public void processPorIdentificador(String id) throws Exception {

			setEstado(Receiver.BUSY);

			//SE DEFINE LA CADENA PARA ENVIAR SI SE PRESENTA UN FALLO
			String registrarFallo = "http://" + urlServidor.getHost() + ":"
					+ urlServidor.getPort() + contextoWeb
					+ "/infoImpresion.do?" + WebKeys.ACCION + "="
					+ AWImpresion.REGISTRAR_FALLO_IMPRESION + "&"
					+ CImpresion.UID + "=" + UID;

			// SE OBTIENE EL IDENTIFICADOR DEL IMPRIMIBLE Y SE SOLICITA AL
			// SERVIDOR
			try {
				Bundle bundle = null;

				logger.print("IDENTIFICADOR ES : " + id);
				registrarFallo = registrarFallo + "&"+ CImpresion.ID_IMPRIMIBLE + "=" + id;

				// SE SOLICITA AL SERVIDOR EL ENVIO DEL IMPRIMIBLE, POR EL
				// PROTOCOLO HTTP.
				String descargarImprimible = "http://" + urlServidor.getHost()
						+ ":" + urlServidor.getPort() + contextoWeb
						+ "/infoImpresion.do?" + WebKeys.ACCION + "="
						+ AWImpresion.DESCARGAR_IMPRIMIBLE + "&"
						+ CImpresion.UID + "=" + UID + "&"
						+ CImpresion.ID_IMPRIMIBLE + "=" + id;
				
				logger.print("CADENA DESCARGAR IMPRIMIBLE : "+ descargarImprimible);
				
				//SE DESCARGA EL IMPRIMIBLE Y SE INTENTA IMPRIMIR
				URL urlDescargaImprimible = new URL(descargarImprimible);
				URLConnection connectionDescarga = urlDescargaImprimible.openConnection();
				ObjectInputStream ois = new ObjectInputStream(connectionDescarga.getInputStream());
				Object obj = ois.readObject();
				ois.close();

				if (obj != null && obj instanceof Bundle) {
					bundle = (Bundle) obj;

					String mensajeError = "";
					boolean procesado = false;
					int intentos = 0;
					while ((!procesado) && (intentos < 3)) {
						try {
							processor.procesar(bundle);
							procesado = true;
						} catch (ImpNoConfiguradaException e) {
							logger.print("***********************************");
							logger.print("ERROR DE IMPRESORA CONTROLADA: " + e.getMessage());
							logger.print("***********************************");
							e.printStackTrace();
							procesado = true;
						}  catch (Throwable e) {
							intentos++;
							mensajeError = e.getMessage();
							logger.print("***********************************");
							logger.print("ERROR DE IMPRESORA: "+ e.getMessage());
							logger.print("***********************************");
							e.printStackTrace();
						}
					}
					
					//SI NO SE IMPRIME SE ENVIA EL ERROR AL SERVIFOR 
					if (!procesado) {
						try {
							registrarFallo = registrarFallo + "&"+ CImpresion.MENSAJE_ERROR
									+ "=ERROR IMPRESION LOCAL (NO TRANSPORTE):"+ mensajeError;
							registrarFallo = quitarEspacios(registrarFallo);
							
							logger.print("CADENA REGISTRAR FALLO : "+ registrarFallo);
							URL urlFallo = new URL(registrarFallo);
							URLConnection connectionError = urlFallo.openConnection();
							BufferedReader verificacion = new BufferedReader(
									new InputStreamReader(connectionError.getInputStream()));
						} catch (Exception e) {
							logger.print("ERROR AL REGISTRAR EL FALLO "+ e.getMessage());
							e.printStackTrace();
						}

					}
				} else {
					logger.print("NO HAY IMPRIMIBLE PARA IMPRIMIR: SEGURAMENTE EL IMPRIMIBLE ( "+ id 
							+ " ) YA FUE IMPRESO.");
					if (obj != null) {
						logger.print("OBJETO ERA DE TIPO."+ obj.getClass().getName());
					} else {
						logger.print("OBJETO ERA NULO.");
					}

				}

			} catch (MalformedURLException e1) {
				logger.print("ERROR (MalformedURLException):" + e1);

				try {
					registrarFallo = registrarFallo + "&"+ CImpresion.MENSAJE_ERROR + "=" + e1.getMessage();
					registrarFallo = quitarEspacios(registrarFallo);
					URL urlFallo = new URL(registrarFallo);
					URLConnection connectionDescarga = urlFallo.openConnection();
					BufferedReader verificacion = new BufferedReader(
							new InputStreamReader(connectionDescarga.getInputStream()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (IOException e2) {
				logger.print("ERROR (IOException):" + e2);
				e2.printStackTrace();
				try {
					registrarFallo = registrarFallo + "&"+ CImpresion.MENSAJE_ERROR + "=" + e2.getMessage();
					registrarFallo = quitarEspacios(registrarFallo);
					URL urlFallo = new URL(registrarFallo);
					URLConnection connectionDescarga = urlFallo.openConnection();
					BufferedReader verificacion = new BufferedReader(
							new InputStreamReader(connectionDescarga.getInputStream()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception ex) {
				logger.print("ERROR (Exception):" + ex);
				ex.printStackTrace();

				try {
					registrarFallo = registrarFallo + "&"+ CImpresion.MENSAJE_ERROR + "=" + ex.getMessage();
					registrarFallo = quitarEspacios(registrarFallo);
					URL urlFallo = new URL(registrarFallo);
					URLConnection connectionDescarga = urlFallo.openConnection();
					BufferedReader verificacion = new BufferedReader(
							new InputStreamReader(connectionDescarga.getInputStream()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} finally {
				logger.print("CADENA REGISTRAR FALLO : " + registrarFallo);
			}

			setEstado(Receiver.READY);
			logger.print("FIN");
		}

	}

	/**
	 * Getter for property host.
	 * 
	 * @return Value of property host.
	 */
	public String getRemoteHost() {
		return this.remoteHost;
	}

	/**
	 * Getter for property port.
	 * 
	 * @return Value of property port.
	 */
	public int getRemotePort() {
		return this.remotePort;
	}

	/**
	 * Getter for property reintentos.
	 * 
	 * @return Value of property reintentos.
	 */
	public int getMaxReintentos() {
		return this.maxReintentos;
	}

	/**
	 * Setter for property reintentos.
	 * 
	 * @param reintentos
	 *            New value of property reintentos.
	 */
	public void setMaxReintentos(int reintentos) {
		this.maxReintentos = reintentos;
		forEver = false;
	}

	private void setEstado(String estado) {
		setChanged();
		notifyObservers(estado);
	}
	
	/**
	 * @return
	 */
	public int getListeningPort() {
		return listeningPort;
	}

	/**
	 * @return
	 */
	public String quitarEspacios(String original) {

		String rta = null;
		if (original != null) {
			rta = original.replaceAll(" ", "_");
		}
		return rta;

	}

	/**
	 * @return
	 */
	public String getLocalHost() {
		return localHost;
	}

	/**
	 * @return
	 */
	public String getUID() {
		return UID;
	}
	

	/**
	 * @return
	 */
	public String getIpsCliente() {
		return this.ipsCliente;
	}	

	/**
	 * Método que determina la dirección ip del equipo local.
	 * @return
	 * @throws java.net.UnknownHostException
	 */
	public String getLocalHostInetAddress()
			throws java.net.UnknownHostException {
		InetAddress tempIP = null;
		InetAddress localhostIP = null;
		String ip = InetAddress.getLocalHost().getHostAddress();
		String IPLOCALHOST = "127.0.0.1";

		try {
			Enumeration enumNetworkInterface = NetworkInterface
					.getNetworkInterfaces();
			Enumeration enumInetAddresses = null;
			NetworkInterface networkInterface = null;

			int numeroIps = 0;
			String ips = "";
			for (; enumNetworkInterface.hasMoreElements();) {
				networkInterface = (NetworkInterface) enumNetworkInterface
						.nextElement();
				enumInetAddresses = networkInterface.getInetAddresses();
				for (; enumInetAddresses.hasMoreElements();) {
					tempIP = (InetAddress) enumInetAddresses.nextElement();
					this.ipsCliente = this.ipsCliente + tempIP.getHostAddress();
					this.ipsCliente = this.ipsCliente.trim();
					this.ipsCliente = this.ipsCliente + "/";
					if (!tempIP.getHostAddress().equals(IPLOCALHOST)) {
						logger
								.print("VERIFICANDO : "
										+ tempIP.getHostAddress());
						if (!tempIP.isLoopbackAddress()
								&& tempIP.getHostAddress().indexOf(":") == -1) {
							logger
									.print("SE ELIGE: "
											+ tempIP.getHostAddress());
							localhostIP = tempIP;
							numeroIps++;
							ips = ips + tempIP.getHostAddress();
						}
					}
				}
			}

			if (numeroIps > 2) {
				logger.print("SE ENCONTRO UNA ESTACION CON VARIAS IPS: " + ips
						+ ", FAVOR VERIFICAR.");
			}

		} catch (Exception e) {
			logger.print("ERROR OBTENIENDO LA DIRECCIÓN IP");
		}

		if (localhostIP != null) {
			ip = localhostIP.getHostAddress();
		}

		return ip;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

}
