/*
 * Server.java
 *
 * Created on 27 de agosto de 2004, 11:36
 */
package gov.sir.print.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Impresion;
import gov.sir.core.negocio.modelo.ImpresionPk;
import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConsulta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaCrearSolicitud;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleResolucion;
        /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se avilita el uso de la clase gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormularioTestamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormularioTestamento;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleInscripcionTestamento;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleOficioPertenencia;
import gov.sir.core.negocio.modelo.util.InfoLog;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.dao.ForsetiFactory;
import gov.sir.print.common.Bundle;
import gov.sir.print.server.dao.DAOException;
import gov.sir.print.server.dao.FactoryException;
import gov.sir.print.server.dao.PrintFactory;

/**
 *
 * @author  dcantor
 */
class Server implements Runnable {
    private int localPortHigh;
    private int listeningPort;
    private int localPortLow;
    private int reintentos;
    private static Server INSTANCE;
    private static boolean activo = false;
    private Map clients;
    private volatile boolean stopRequested;
    private transient ServerSocket sockListen;
    private transient Socket sock;
    private transient PrintJobsProperties prop;
    private transient Hashtable impresionesExtensas;
	private PrintFactory impresion;
	private ForsetiFactory forseti;
	private static final String DEFAULT_TIME_OUT = "120000";

    private Server() throws PrintJobsException {
        prop = PrintJobsProperties.getInstancia();
        localPortLow = Integer.parseInt(prop.getProperty(PrintJobsProperties.P_SERVER_PORT_LOW));
        localPortHigh = Integer.parseInt(prop.getProperty(PrintJobsProperties.P_SERVER_PORT_HIGH));
        reintentos = Integer.parseInt(prop.getProperty(PrintJobsProperties.P_SERVER_REINTENTOS));
        listeningPort = localPortLow;
        clients = Collections.synchronizedMap(new HashMap());
        sockListen = null;
        sock = null;
        new Thread(this, "Print Jobs Server").start();
		
		try {
			impresion = PrintFactory.getFactory();
			ImpresionPk idImpresion = new ImpresionPk();
			idImpresion.idSesion="";
			Impresion imp = impresion.getImpresionDAO().getSesionImpresion(idImpresion);
			Log.getInstance().debug(this.getClass(),"Se ha iniciado el persistence manager de impresión :)");			
		}
		catch (FactoryException e) {
			PrintJobsException fe = new PrintJobsException("No fue posible obtener la fábrica concreta", e);
			Log.getInstance().error(this.getClass(),fe.getMessage(), e);
			throw fe;
		}
		catch(DAOException de){
			PrintJobsException fe = new PrintJobsException("No fue posible inicializar el Persistance Manager", de);
			Log.getInstance().error(this.getClass(),fe.getMessage(), de);
			throw fe;
		}
		
		try {
			forseti = ForsetiFactory.getFactory();
			this.impresionesExtensas = new Hashtable();
			List circulos = forseti.getZonaRegistralDAO().getCirculos();
			if(circulos!=null){
				Iterator it = circulos.iterator();
				while(it.hasNext()){
					Circulo circulo = (Circulo)it.next();
					impresionesExtensas.put(circulo.getIdCirculo() , new Integer(circulo.getNumeroBytesExtenso()));
				}
			}
			Log.getInstance().debug(this.getClass(),"Se ha iniciado el persistence manager de forseti");			
		}
		catch (gov.sir.forseti.dao.FactoryException e) {
			PrintJobsException fe = new PrintJobsException("No fue posible obtener la fábrica concreta", e);
			Log.getInstance().error(this.getClass(),fe.getMessage(), e);
			throw fe;
		}
		catch(gov.sir.forseti.dao.DAOException de){
			PrintJobsException fe = new PrintJobsException("No fue posible inicializar el Persistance Manager", de);
			Log.getInstance().error(this.getClass(),fe.getMessage(), de);
			throw fe;
		}
		
    }

    public static synchronized Server getInstance() throws PrintJobsException {
        if (INSTANCE == null) {
            INSTANCE = new Server();
        }

        return INSTANCE;
    }

    public int getPort() {
        if (sockListen != null) {
            return sockListen.getLocalPort();
        }
        return -1;

    }

    public String getHost() {
        if (sockListen != null) {
            return sockListen.getInetAddress().getHostAddress();
        }
        return null;
    }

	public void enviarDocumento(String UID, Bundle bundle, int maxIntentos, int tiempoespera) throws PrintJobsException {
		
		InfoLog.informarLogs("ITEM_IMPRESION_METODO","",this,"enviarDocumento",InfoLog.INICIA,InfoLog.LLAMADO_PROPIO,"enviarDocumento","");
		new Thread(new EnviaImprimibleID(UID,bundle,maxIntentos,tiempoespera,impresion,null,0,impresionesExtensas)).start() ;
		InfoLog.informarLogs("ITEM_IMPRESION_METODO","",this,"enviarDocumento",InfoLog.TERMINA,InfoLog.LLAMADO_PROPIO,"enviarDocumento","");
		
	}
	
	public void enviarDocumento(String UID, Bundle bundle, int maxIntentos, int tiempoespera, OrdenImpresion ordenImpresion , int ordenActual) throws PrintJobsException {
		
		InfoLog.informarLogs("ITEM_IMPRESION_METODO","",this,"enviarDocumento",InfoLog.INICIA,InfoLog.LLAMADO_PROPIO,"enviarDocumento","");
		new Thread(new EnviaImprimibleID(UID,bundle,maxIntentos,tiempoespera,impresion,ordenImpresion,ordenActual,impresionesExtensas)).start() ;
		InfoLog.informarLogs("ITEM_IMPRESION_METODO","",this,"enviarDocumento",InfoLog.TERMINA,InfoLog.LLAMADO_PROPIO,"enviarDocumento","");
		
	}
	

    public void run() {
        ThreadDeath threaddeath = null;
        int r = 0;
        while (r<reintentos) {
            try {
                doBind();
                Log.getInstance().debug(this.getClass(),"Server ONLINE");
                while (!stopRequested) {
                    doReceive();
                    doProcess();
                }
            }
            catch (Exception e) {
                Log.getInstance().error(this.getClass(),"Ocurrio un error", e);
                Log.getInstance().debug(this.getClass(),"reintentando establecer el servicio ...");
                r++;
				Log.getInstance().debug(this.getClass(),"reintentando establecer el servicio ...(Reintento "+r+" de "+reintentos+")");
            }
            catch (ThreadDeath threaddeath1) {
                Log.getInstance().debug(this.getClass(),"Ocurrio un ThreadDeath");
                threaddeath = threaddeath1;
            }
            finally {
                doUnbind();
                Log.getInstance().debug(this.getClass(),"Server OFFLINE");
                if (threaddeath != null) {
                    throw threaddeath;
                }
            }
        }
    }

    public synchronized void stop() {
		try {
			impresion.getImpresionDAO().finalizar();
		} catch (DAOException e) {
			Log.getInstance().error(this.getClass(),e);
		}
        stopRequested = true;
        Log.getInstance().debug(this.getClass(),"se ha solicitado detener el servidor...");
    }

    public boolean esActivo() {
        return activo;
    }

    protected void doBind() throws CommunicationException {
        while (!activo) {
            Log.getInstance().debug(this.getClass(),"Publicando el Servidor de Impresion de SIR en el puerto " + listeningPort );
            try {
            	InetAddress address = InetAddress.getLocalHost();
				Log.getInstance().debug(this.getClass(),"El host del Server es: " + address.getHostAddress() + " - " + address.getHostName());            	
                sockListen = new ServerSocket(listeningPort, 20,address);
                               
                prop.setProperty(PrintJobsProperties.P_SERVER_PORT, String.valueOf(listeningPort));
                Log.getInstance().debug(this.getClass(),"Servidor de Impresion de SIR Publicado en  [Address=" + sockListen.getInetAddress() + ", Port=" + sockListen.getLocalPort() + "]");
                synchronized (this) {
                    activo = true;
                    notifyAll();
                }
            }
            catch (Throwable t) {
                if (listeningPort < localPortHigh) {
                    Log.getInstance().warn(this.getClass(),"No se pudo publicar el servidor de impresion por el puerto " + listeningPort);
                    listeningPort++;
                }
                else{
					throw new RuntimeException("Se completo el rango de puertos y no fue " +
						"posible crear un servidor de sockets. El servidor no funcionara " +
						"correctamente");
                }
                
            }
        }
    }

    protected void doReceive() throws CommunicationException {
        Log.getInstance().debug(this.getClass(),"Escuchando por conexiones por el socket [" + sockListen + "]");
        try {
            sock = sockListen.accept();
            Log.getInstance().debug(this.getClass(),"Conexion aceptada por el socket " + sock);
        }
        catch (Throwable t) {
            throw new CommunicationException(t);
        }
    }

    protected void doProcess() throws IOException, ClassNotFoundException, PrintJobsException {
        ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
        InformacionCliente nuevoCliente = (InformacionCliente)ois.readObject();
        Log.getInstance().debug(this.getClass(),"Adicionando el suscriptor: " + nuevoCliente.getUID());
        clients.put(nuevoCliente.getUID(), nuevoCliente);
        
        Impresion imp = new Impresion();
		imp.setDireccionIP(nuevoCliente.getHost());
		imp.setPuerto(""+nuevoCliente.getListeningPort());
		imp.setIdSesion(nuevoCliente.getUID());
		imp.setAdministrador(false);
		
		final int MAX_INTENTOS = 5;
        
		// Se determina si el cliente tiene bien configurado la aplicación de impresión.
		// Se hacen 5 intentos. Si dentro de estos intentos no se puede establecer una
		// conexión, se asume el puerto como "INVISIBLE"
		for(int i = 0; i < MAX_INTENTOS; i++) {
			try{
				// Verificar que una conexión puede ser establecida con el cliente
				Socket sCliente = new Socket(nuevoCliente.getHost(), nuevoCliente.getListeningPort());
				sCliente.close();
				break;
			} catch(UnknownHostException uhe) {
				imp.setPuerto("INVISIBLE");
			} catch(IOException ioe) {
				imp.setPuerto("INVISIBLE");
			}
			
			// Se espera 1 segundo entre intentos de conexión
			try {
				Thread.sleep(1000); 
			} catch(InterruptedException ie) {
				throw new PrintJobsException("Error de ejecución");
			}
		}
		
        try {
			// Adicionar la sesión de impresión
			impresion.getImpresionDAO().addSesionImpresion(imp);
        } catch (DAOException e) {
			PrintJobsException pje = new PrintJobsException("No se pudo ingresar el cliente de impresión en la base de datos", e);
			throw pje;
        } 
        
        Log.getInstance().debug(this.getClass(),"Cliente: " + nuevoCliente.getUID());
        Log.getInstance().debug(this.getClass(),"Cliente: " + nuevoCliente.getHost());
        Log.getInstance().debug(this.getClass(),"Cliente: " + nuevoCliente.getListeningPort());

        Log.getInstance().debug(this.getClass(),"Hay " + clients.size() + " suscriptores");
        sock = null;
    }

    protected void doUnbind() {
        if (sockListen == null)
            return;
        Log.getInstance().debug(this.getClass(),"Cerrando el socket listener=" + sockListen);
        try {
            sockListen.close();
        }
        catch (Throwable t) {
            Log.getInstance().warn(this.getClass(),t);
        }
    }

    protected Socket obtenerNuevoSocket(InformacionCliente cliente, int timeOut) throws UnknownHostException, IOException{
		Log.getInstance().debug(this.getClass(),"\n\nCREANDO NUEVO SOCKET");
		Log.getInstance().debug(this.getClass(),"\nUID: "+cliente.getUID() + "\nHOST: "+cliente.getHost() +"\nPUERTO: "+cliente.getListeningPort());		
    	
        Socket s = new Socket(cliente.getHost(), cliente.getListeningPort());
		String imp = "";
        s.setSoTimeout(timeOut);        
        return s;
    }
    
    public static class EnviaImprimibleID implements Runnable {
    	String UID=null;
    	Bundle bundle=null;
    	int maxIntentos=0;
    	int tiempoespera=0;
    	private PrintFactory impresion;
    	private OrdenImpresion ordenImpresion = null;
    	private int ordenActual = 0;
    	private int numeroBytesExtenso = 0;
    	private Hashtable impresionesExtensas = null;
    	
    	public EnviaImprimibleID(String UID, Bundle bundle, int maxIntentos, int tiempoespera, PrintFactory impresion , OrdenImpresion ordenImpresion , int ordenActual, Hashtable impresionesExtensas){
    		this.UID = UID;
    		this.bundle = bundle;
    		this.maxIntentos = maxIntentos;
    		this.tiempoespera = tiempoespera;
    		this.impresion = impresion;
    		this.ordenImpresion = ordenImpresion;
    		this.ordenActual = ordenActual;
    		this.impresionesExtensas = impresionesExtensas;
    	}
    	
		public void run() {
			
			if(ordenImpresion!=null  ){
				while( this.ordenImpresion.getOrdenImpresion() < this.ordenActual){
					try{
						Thread.sleep(100);
					}catch(Exception e){						
					}
				}
			}
			
			InfoLog.informarLogs("ITEM_IMPRESION", "", this, "enviarDocumento", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "enviarDocumento1", "");

			// InformacionCliente cliente = (InformacionCliente)
			// clients.get(UID);
			InformacionCliente cliente = null;

			Impresion impr = null;
			try {
				ImpresionPk idImpresion = new ImpresionPk();
				idImpresion.idSesion = UID;
				impr = impresion.getImpresionDAO().getSesionImpresion(idImpresion);
			} catch (DAOException e) {
				PrintJobsException pje = new PrintJobsException("No se pudo ingresar el cliente de impresión en la base de datos", e);
				// throw pje;
			}

			if (impr != null) {
				// Verificar si el cliente es invisible
				
				Integer numBytesExtenso = (Integer)this.impresionesExtensas.get(impr.getCirculo());
				this.numeroBytesExtenso = numBytesExtenso.intValue();
				
				if (impr.getPuerto() != null && impr.getPuerto().equals("INVISIBLE")) {
					Log.getInstance().error(this.getClass(),"El cliente " + UID + " es inasequible desde el sistema. No es " + "posible enviar el documento");
					// throw new EstacionNoEncontradaException("El cliente " +
					// UID + " es inasequible desde el sistema. No es " +
					// "posible enviar el documento");
				}

				Log.getInstance().debug(this.getClass(),"SE ENCONTRÓ EL CLIENTE EN LA BASE DE DATOS");
				cliente = new InformacionCliente(impr.getIdSesion(), impr.getDireccionIP(), new Integer(impr.getPuerto()).intValue(), null);
				// clients.put(cliente.getUID(), cliente);

				InfoLog.informarLogs("ITEM_IMPRESION", "", this, "enviarDocumento", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "enviarDocumento2", "");

				// SE OBTIENE EL TIEMPO MAXIMO DE VIDA DEL SOCKET CUANDO NO HAY
				// COMUNICACIÓN.
				String timeOut = "";
				try {
					timeOut = impresion.getImpresionDAO().getValor(COPLookupTypes.CONFIGURACION_IMPRESION, CImpresion.TIME_OUT_SOCKET);
				} catch (DAOException de) {
					timeOut = DEFAULT_TIME_OUT;
				}
				Log.getInstance().debug(this.getClass(),"(SOCKET ENVIO) : " + timeOut);

				InfoLog.informarLogs("ITEM_IMPRESION", "", this, "enviarDocumento", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "enviarDocumento3", "");

				Socket s = null;
				long tiempoInicial = 0;
				int idImprimible = 0;
				String tipo = "";

				// SE GUARDA EL IMPRIMIBLE PARA QUE LUEGO SEA RECUPERADO Y
				// ENVIANDO
				// POR HTTP.
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					oos.writeObject(bundle);
					
					Imprimible imp = new Imprimible();
					imp.setDatosImprimible(baos.toByteArray());
					imp.setIP(cliente.getHost());
					imp.setUID(cliente.getUID());
					if (bundle != null && bundle.getImprimible() != null) {
						tipo = bundle.getImprimible().getClass().getName();
						imp.setTipoImprimible(tipo);
                                                gov.sir.print.common.Imprimible imprimibleTemp = bundle.getImprimible();
						if (imprimibleTemp instanceof ImprimibleRecibo) {
							ImprimibleRecibo impRecibo = (ImprimibleRecibo) imprimibleTemp;
							if (impRecibo != null) {
								if (impRecibo.getTurno() != null) {
									imp.setTurno(impRecibo.getTurno().getIdWorkflow());
								}
								if (impRecibo.getPago() != null && impRecibo.getPago().getLiquidacion() != null && impRecibo.getPago().getLiquidacion().getSolicitud() != null && impRecibo.getPago().getLiquidacion().getSolicitud().getTurno() != null) {
									imp.setTurno(impRecibo.getPago().getLiquidacion().getSolicitud().getTurno().getIdWorkflow());
								}
							}
						}
						if (imprimibleTemp instanceof ImprimibleCertificado) {
							ImprimibleCertificado impCertificado = (ImprimibleCertificado) imprimibleTemp;
							if (impCertificado != null && impCertificado.getTurno() != null) {
								imp.setTurno(impCertificado.getTurno().getIdWorkflow());
							}
						}
						if (imprimibleTemp instanceof ImprimibleFormulario) {
							ImprimibleFormulario impFormulario = (ImprimibleFormulario) imprimibleTemp;
							if (impFormulario != null && impFormulario.getTurno() != null) {
								imp.setTurno(impFormulario.getTurno().getIdWorkflow());
							}
						}
						if (imprimibleTemp instanceof ImprimibleNotaDevolutivaCalificacion) {
							ImprimibleNotaDevolutivaCalificacion impNotaDev = (ImprimibleNotaDevolutivaCalificacion) imprimibleTemp;
							if (impNotaDev != null && impNotaDev.getTurno() != null) {
								imp.setTurno(impNotaDev.getTurno());
							}
						}
						if (imprimibleTemp instanceof ImprimibleConsulta) {
							ImprimibleConsulta impConsulta = (ImprimibleConsulta) imprimibleTemp;
							if (impConsulta != null && impConsulta.getTurno() != null && impConsulta.getTurno().getIdWorkflow() != null) {
								imp.setTurno(impConsulta.getTurno().getIdWorkflow());
							}
						}
						if (imprimibleTemp instanceof ImprimibleResolucion) {
							ImprimibleResolucion impResolucion = (ImprimibleResolucion) imprimibleTemp;
							if (impResolucion != null && impResolucion.getTurno() != null && impResolucion.getTurno().getIdWorkflow() != null) {
								imp.setTurno(impResolucion.getTurno().getIdWorkflow());
							}
						}

                                                /** Ellery Robles caso Mantis 07114 Acta - Requerimiento No 218 - Reimpresion de consultas
                                                 * set del turno al imprimible
                                                 * imp.setTurno(impfotosol.getTurno().getIdWorkflow());
                                                 */
                                                  if (imprimibleTemp instanceof ImprimibleFotocopiaCrearSolicitud ) {
                                                        ImprimibleFotocopiaCrearSolicitud impfotosol = (ImprimibleFotocopiaCrearSolicitud) imprimibleTemp;
                                                        if (impfotosol != null && impfotosol.getTurno() != null && impfotosol.getTurno().getIdWorkflow() != null) {
                                                                imp.setTurno(impfotosol.getTurno().getIdWorkflow());
                                                        }
                                                 }
                                                  /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se asigana el turno y el circulo al imprimible
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
                                                  if(imprimibleTemp instanceof ImprimibleFormularioTestamento)
                                                  {
                                                      ImprimibleFormularioTestamento impformtest = (ImprimibleFormularioTestamento)imprimibleTemp;
                                                      if(impformtest !=null && impformtest.getTurno()!=null && !impformtest.getTurno().isEmpty())
                                                      {
                                                          imp.setTurno(impformtest.getTurno());
                                                          imp.setCirculo(impformtest.getIdCirculo());
                                                      }
                                                  }
                                                  if(imprimibleTemp instanceof ImprimibleInscripcionTestamento)
                                                  {
                                                      ImprimibleInscripcionTestamento impformtest = (ImprimibleInscripcionTestamento)imprimibleTemp;
                                                      if(impformtest !=null && impformtest.getTurno() !=null && !impformtest.getTurno().isEmpty())
                                                      {
                                                          imp.setTurno(impformtest.getTurno());
                                                      }
                                                  }
						
					}
					imp.setNumeroBytes(imp.getDatosImprimible().length);
					imp.setNumeroImpresiones(0);
					imp.setFechaCreacion(new Date());
					
					oos.close();
					baos.close();
					
					//SI LOS DATOS DEL IMPRIMIBLE SON MAYORES A LOS CONFIGURADOS PARA EL CIRCULO
					//SE COMPRIME EL IMPRIMIBLE Y SE GUARDA EN EL BUNDLE COMO UN OBJETO ZIPEADO.
					if(imp.getDatosImprimible().length > numeroBytesExtenso){
						imp.setImprimibleExtenso(true);
						
						//Se obtiene un nombre único para colocarle al archivo
						//que estara compuesto por el inicio de la UID + últimos cinco digitos del millisegundo
						//actual + un número aleatorio entre 0 y 10000
						String inicioUID  = (UID!=null&&UID.length()>=3)?UID.substring(0,3):""; 
						long time = System.currentTimeMillis();
						String timeString = String.valueOf(time);
						int temp = (int)Math.ceil(Math.random()*10000);
						String nombreArchivo = inicioUID + timeString.substring((timeString.length()- 5), timeString.length()) + temp;
						
						byte[] bytesComprimidos =  this.procesarImprimibleExtenso(bundle , nombreArchivo);

						bundle.setDatosComprimidos(bytesComprimidos);
						bundle.setImprimible(null);

						baos = new ByteArrayOutputStream(1024);
						oos = new ObjectOutputStream(baos);
						oos.writeObject(bundle);
						oos.close();
						baos.close();
						
						imp.setDatosImprimible(baos.toByteArray());
					}
					
					idImprimible = impresion.getImpresionDAO().guardarImprimible(imp);

				} catch (Exception e) {
					e.printStackTrace();
					// throw new PrintJobsException("Error al guardar la
					// impresión.
					// ",e);
				}

				if(ordenImpresion!=null ){
					ordenImpresion.setOrdenImpresion(ordenActual + 1);
				}				
				
				
				InfoLog.informarLogs("ITEM_IMPRESION", "", this, "enviarDocumento", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "enviarDocumento4", "");

				// SE HACEN LOS INTENTOS DE IMPRESIÓN.
				for (int i = 0; i < maxIntentos; i++) {
                                    
					try {
						s = cliente.getCurrentConnection();
						tiempoInicial = System.currentTimeMillis();

						if (s == null || s.isClosed()) {
							s = obtenerNuevoSocket(cliente, new Integer(timeOut).intValue());
							// clients.remove(cliente.getUID());
							cliente.setCurrentConnection(s);
							// clients.put(cliente.getUID(), cliente);
							Log.getInstance().debug(this.getClass(),"SE ACTUALIZÓ EL SOCKET EN EL HASHTABLE, CON EL NUEVO SOCKET");
						}

						System.out.println("EL IDENTIFICADOR CREADO ES:" + idImprimible);

						// if(true){
						// throw new Exception("HOLA :(");
						// }

						InfoLog.informarLogs("ITEM_IMPRESION", "", this, "enviarDocumento", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "enviarDocumento5", "");
						// if (idImprimible != 0) {
						// new Sender(s).enviarIdentificador("" + idImprimible);
						// }
						InfoLog.informarLogs("ITEM_IMPRESION", "", this, "enviarDocumento", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "enviarDocumento6", "");
						i = maxIntentos + 1;

						Log.getInstance().debug(this.getClass(),"(IMPRESION EXITOSA): " + cliente.getHost());
						Log.getInstance().debug(this.getClass(),"(IMPRESION EXITOSA): " + cliente.getUID());
						Log.getInstance().debug(this.getClass(),"(IMPRESION EXITOSA) (TIPO ES): " + tipo);
						
						break;

					} catch (Throwable t) {
						Log.getInstance().error(this.getClass(),t.getMessage());
						Log.getInstance().error(this.getClass(),"(FALLO SOCKET): " + cliente.getHost());
						Log.getInstance().error(this.getClass(),"(FALLO SOCKET): " + cliente.getUID());
						Log.getInstance().error(this.getClass(),"(FALLO SOCKET) (TIPO ES): " + tipo);

						try {
							if (i < (maxIntentos - 1)) {
								Thread.sleep(tiempoespera);
							}
						} catch (Exception e) {
						}

						if (i == (maxIntentos - 1)) {
							// throw new PrintJobsException("Error enviando el
							// documento. " + (idImprimible != 0 ? "" +
							// idImprimible
							// : ""), t, idImprimible);
						}

					} finally {
						if (s != null) {
							try {
								s.close();
								Log.getInstance().debug(this.getClass(),"(SE CERRO EL SOCKET): " + cliente.getHost());
							} catch (IOException e) {
								Log.getInstance().error(this.getClass(),"(FALLO CERRANDO SOCKET): " + cliente.getHost());
								e.printStackTrace();
							}
						}
						s = null;

						long tiempoFin = System.currentTimeMillis();
						long resta = tiempoFin - tiempoInicial;
						resta = resta - tiempoespera;

						if (i == (maxIntentos - 1)) {
							resta = resta + tiempoespera;
						}

						long segs = resta / 1000;
						long mils = resta % 1000;

						Log.getInstance().debug(this.getClass(),"(TIEMPO SOCKET): " + cliente.getHost());
						Log.getInstance().debug(this.getClass(),"(TIEMPO SOCKET): " + segs + "." + mils);
						Log.getInstance().debug(this.getClass(),"(TIEMPO SOCKET): " + cliente.getUID());

					}

				}
			} else {
				Log.getInstance().error(this.getClass(),"El cliente " + UID + " no se ha registrado. " + "No se puede enviar" + " el documento");

				// throw new EstacionNoEncontradaException("El cliente " + UID +
				// " no se ha registrado. " + "No se puede enviar" + " el
				// documento");
			}

			InfoLog.informarLogs("ITEM_IMPRESION", "", this, "enviarDocumento", InfoLog.INICIA, InfoLog.LLAMADO_PROPIO, "enviarDocumento7", "");

		}
		
	    protected Socket obtenerNuevoSocket(InformacionCliente cliente, int timeOut) throws UnknownHostException, IOException{
			Log.getInstance().debug(this.getClass(),"\n\nCREANDO NUEVO SOCKET");
			Log.getInstance().debug(this.getClass(),"\nUID: "+cliente.getUID());		
	    	Log.getInstance().debug(this.getClass(),"\nHOST: "+cliente.getHost());
			Log.getInstance().debug(this.getClass(),"\nPUERTO: "+cliente.getListeningPort());
	    	
	        Socket s = new Socket(cliente.getHost(), cliente.getListeningPort());
			String imp = "";
	        s.setSoTimeout(timeOut);        
	        return s;
	    }	
	    
	    /**
	     * Devuelve los bytes comprimidos a partir del bundle que se le pasa
	     * @param bundle
	     * @param nombreArchivo
	     * @return
	     * @throws UnknownHostException
	     * @throws IOException
	     */
	    protected byte [] procesarImprimibleExtenso( Bundle bundle, String nombreArchivo) throws UnknownHostException, IOException{
	    	
	    	//SE GUARDA UN COPIA EN DISCO DURO DEL OBJETO BUNDLE
			FileOutputStream fout = new FileOutputStream(nombreArchivo);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(bundle.getImprimible());
			oos.close();
			
			//SE COMPRIME EL OBJETO EN DISCO DURO
			byte[] buffer = new byte[1024];
	        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(nombreArchivo+".zip"));
	    
            FileInputStream in = new FileInputStream(nombreArchivo);
            out.putNextEntry(new ZipEntry(nombreArchivo));
	    
            int len;
            while ((len = in.read(buffer)) > 0){
                out.write(buffer, 0, len);
            }
	    
            out.closeEntry();
	        in.close();
	        out.close();
	        
	        //SE OBTIENEN LOS BYTES COMPRIMIDOS
	        FileInputStream fis = new FileInputStream(nombreArchivo+".zip");
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        
	        byte[] bufTemp = new byte[1024];
	        int lenTemp;
	        while ((lenTemp = fis.read(bufTemp)) > 0) {
	            baos.write(bufTemp, 0, lenTemp);
	        }
	    
	        baos.close();
	        fis.close();
			
	        //SE ELIMINA EL ARCHIVO TEMPORAL
			try{	
				java.io.File file = new File (nombreArchivo);
	        	if(file.exists ()){
	        		file.delete ();
	        	}
			}catch(Exception e){			
			}

	        try{	
				java.io.File file = new File (nombreArchivo+".zip");
	        	if(file.exists ()){
	        		file.delete ();
	        	}
			}catch(Exception e){			
			}
	        
			Log.getInstance().debug(this.getClass(),"NOMBRE ARCHIVO : "  + nombreArchivo+".zip");
			
	        return baos.toByteArray();
	    	
	    	/* CODIGO PARA COLOCAR UN ARCHIVO A UN FTP
	    	 * try {
				String serverFtp = (String) ht.get(CImpresion.FTP_SERVER);
				String userFtp = (String) ht.get(CImpresion.FTP_USER);
				String passwordFtp = (String) ht.get(CImpresion.FTP_PASSWORD);
				String directorioFtp = (String) ht.get(CImpresion.FTP_DIRECTORIO);

				FtpClient client = new FtpClient();
				client.openServer(serverFtp);
				client.login(userFtp, passwordFtp);
				client.binary();

				client.cd(directorioFtp);

				TelnetOutputStream outTelnet = client.put(nombreArchivo);
				outTelnet.write(bytes);

				outTelnet.flush();
				outTelnet.close();

				client.closeServer();

			} catch (Exception e) {
				Log.getInstance().debug(this.getClass(),e.getMessage());
				e.printStackTrace();
				System.out.println(e.getMessage());
			}*/
	        
	        
	        /* CODIGO PARA DESCARGAR UN ARCHIVO DESDE UN FTP
			// SE CONECTA AL SERVIDOR Y A LA CARPETA ESPECÍFICADA
			FtpClient client = new FtpClient();
			client.openServer(serverFtp);
			client.login(userFtp, passwordFtp);
			client.binary();

			client.cd(directorioFtp);

			// TRAER EL ARCHIVO A LA CARPETA LOCAL
			TelnetInputStream in;
			BufferedOutputStream bos;
			int buffer = 0;

			in = client.get(nombreArchivo);
			bos = new BufferedOutputStream(new FileOutputStream(
					directorioFtpLocal + nombreArchivo));
			buffer = 0;
			while ((buffer = in.read()) != -1) {
				bos.write(buffer);
			}

			bos.flush();
			bos.close();
			in.close();

			File file = new File(directorioFtpLocal + nombreArchivo);
			InputStream is = new FileInputStream(file);

			ObjectInputStream ois = new ObjectInputStream(is);
			Object obj = ois.readObject();

			is.close();
			ois.close();

			bundle = (Bundle) obj;
			*/
	    	
	    }	
	    
	}
    
    
}
