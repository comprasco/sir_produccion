package gov.sir.print.server;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.negocio.modelo.Imprimible;
import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
/*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se agrega instruccion para referenciar ImprimibleFormularioTestamento
                    *  @mantis 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
*/
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormularioTestamento;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConsulta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFormulario;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleResolucion;
import gov.sir.core.negocio.modelo.util.InfoLog;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.util.PdfCreator;

import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.dao.DAOException;
import gov.sir.print.server.dao.FactoryException;
import gov.sir.print.server.dao.PrintFactory;

import org.auriga.core.modelo.Servicio;


/**
 *
 * @author  dcantor
 */
public class PrintJobs implements Servicio, PrintJobsInterface {
    /** Instancia del servidor de impresión */
    private static Server server = null;
    
    
	private PrintFactory factory;    

    /** Única instancia de PrintJobs */
    private static PrintJobs INSTANCE = null;

    /** Creates a new instance of PrintJobs
     * @throws PrintJobsException */
    private PrintJobs() throws PrintJobsException {
		server = Server.getInstance();
		try {
			factory = PrintFactory.getFactory();
		}catch (FactoryException e) {
			PrintJobsException fe = new PrintJobsException("No fue posible obtener la fábrica concreta", e);
			Log.getInstance().error(this.getClass(),fe.getMessage(), e);
			throw fe;
		}
	
    }

    /**
     * Obtener la instancia de la clase PrintJobs
     * @return instancia de la clase PrintJobs
     * @throws PrintJobsException
     */
    public static synchronized PrintJobs getInstance()
        throws PrintJobsException {
        if (INSTANCE == null) {
            INSTANCE = new PrintJobs();
        }

        return INSTANCE;
    }
    
    

    /* (non-Javadoc)
     * @see gov.sir.print.interfaz.PrintJobsInterface#start()
     */
    public void start() throws PrintJobsException {
        synchronized (server) {
            while (!server.esActivo()) {
                try {
                    server.wait();
                } catch (InterruptedException ie) {
                }
            }
        }

        Log.getInstance().info(this.getClass(),"Servidor PrintJobs Iniciado");
    }

    /* (non-Javadoc)
     * @see gov.sir.print.interfaz.PrintJobsInterface#enviar(java.lang.String, gov.sir.print.common.Bundle,int,int)
     */
    public void enviar(String UID, Bundle b,  int intentos, int tiempoespera) throws PrintJobsException 
    {
        if (b.getImprimible() != null) {
        	Log.getInstance().debug(this.getClass(),"Enviando el documento " +
                    b.getImprimible().getClass().getName() + " al UID " + UID);
        }
			server.enviarDocumento(UID, b, intentos, tiempoespera);
    }
    
    /* (non-Javadoc)
     * @see gov.sir.print.interfaz.PrintJobsInterface#enviar(java.lang.String, gov.sir.print.common.Bundle,int,int,OrdenImpresion,int)
     */
    public void enviar(String UID, Bundle b,  int intentos, int tiempoespera, OrdenImpresion ordenImpresion, int ordenActual) throws PrintJobsException 
    {
		InfoLog.informarLogs("*PRINT*","",this,"enviar()",InfoLog.INICIA,InfoLog.TERMINA,"ANTES DE LLAMAR A ENVIAR","");	
			server.enviarDocumento(UID, b, intentos, tiempoespera, ordenImpresion, ordenActual);
		InfoLog.informarLogs("*PRINT*","",this,"enviar()",InfoLog.INICIA,InfoLog.TERMINA,"DESPUES DE LLAMAR A ENVIAR","");			
    }    

    /* (non-Javadoc)
     * @see gov.sir.print.interfaz.PrintJobsInterface#stop()
     */
    public void stop() {
        Log.getInstance().debug(this.getClass(),"Solicitando detener el servicio PrintJobs...");
        synchronized (server) {
            Log.getInstance().debug(this.getClass(),"Deteniendo el servicio PrintJobs...");
            server.stop();
        }
    }
    
	/**
	* Obtiene el valor del tiempo de vida de un Socket para la impresión.
	* @param tipo El LookupType de la variable. 
	* @param codigo El LookupCode de la variable. 
	* @return El valor de la variable.
	* @throws Throwable
	*/
	public String getTimeOutSocket (String proceso) throws PrintJobsException {

	  try{
		  return factory.getImpresionDAO().getValor(COPLookupTypes.CONFIGURACION_IMPRESION, CImpresion.TIME_OUT_SOCKET);					
	  }catch (DAOException e) {
			PrintJobsException fe = new PrintJobsException("No fue posible inicializar el Persistance Manager", e);	  	
	  }
	  		
	  return null;
		
	}    
	

	/**
	 * Guarda el bundle en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public int agregarImprimible (Imprimible imprimible) throws PrintJobsException {

		Log.getInstance().debug(this.getClass(),"ENTRO A METODO AGREGAR IMPRIMIBLE PrintJobs");
		int temp = 0;
		
	  try{
			temp = factory.getImpresionDAO().guardarImprimible(imprimible);	  	
	  }catch (Exception e) {
			PrintJobsException fe = new PrintJobsException("No fue colocar el imprimible en la Cola de impresión", e);	  	
	  }
	  Log.getInstance().debug(this.getClass(),"FIN METODO AGREGAR IMPRIMIBLE PrintJobs");
		return temp;  		
		
	}    	
	
	/**
	 * Guarda el bundle en la base de datos.
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public int agregarImprimiblePDF (Bundle bundle,  boolean guardarPDF) throws PrintJobsException {

		Log.getInstance().debug(this.getClass(),"ENTRO A METODO AGREGAR IMPRIMIBLE PDF PrintJobs");
		int temp = 0;
		Imprimible imp = new Imprimible();
		String tipo = "";
		byte[] bytesFormulario = null;
		gov.sir.print.common.Imprimible imprimibleTempTemp = null;
		
	  try{
		  try {
			  		
			  Log.getInstance().debug(this.getClass(),"AGREGAR IMPRIMIBLE PDF PrintJobs proceso de impresion con el writeObjetcs");
				ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(bundle);

				
				imp.setDatosImprimible(baos.toByteArray());
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
                                        /*
                    *  @fecha 23/11/2012
                    *  @author Carlos Torres
                    *  @chage  se asigna el turno y el circulo al imprimible
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
					imprimibleTempTemp = imprimibleTemp;
					/*PdfCreator pdf = new PdfCreator();
					//ByteArrayOutputStream pdfFormulario = pdf.generarImprimibleAPIPDDocument(imprimibleTemp, imp.getTurno(), imp.getTipoImprimible());
					ByteArrayOutputStream pdfFormulario = pdf.generar(imprimibleTemp);
				    bytesFormulario = pdfFormulario.toByteArray();*/
				}
				imp.setNumeroBytes(imp.getDatosImprimible().length);
				imp.setNumeroImpresiones(0);
				imp.setFechaCreacion(new Date());
				
				

				//idImprimible = impresion.getImpresionDAO().guardarImprimible(imp);

			} catch (Exception e) {
				Log.getInstance().error(this.getClass(),e);
			}
			if (guardarPDF) {
				Log.getInstance().debug(this.getClass(),"AGREGAR IMPRIMIBLE PDF PrintJobs proceso de impresion se va a guardar el pdf metodo guardarImprimiblePDF");
				temp = factory.getImpresionDAO().guardarImprimiblePDF(imp,imprimibleTempTemp, guardarPDF, bytesFormulario);
			} else {
				temp = 0;
			}
			Log.getInstance().debug(this.getClass(),"AGREGAR IMPRIMIBLE PDF PrintJobs proceso de impresion se va a guardar el pdf metodo guardarImprimiblePDF guardado resultado " + temp);
	  }catch (Exception e) {
		  Log.getInstance().error(this.getClass(),e);
		  PrintJobsException fe = new PrintJobsException("No fue posible guardar el PDF del imprimible", e);	  	
	  }
	  Log.getInstance().debug(this.getClass(),"FIN A METODO AGREGAR IMPRIMIBLE PDF PrintJobs");
		return temp;  		
		
	}    
	
	/**
	 * Genera el imprimible en formato PDF
	 * @param imprimible, que contiene el imprimible y el número de impresiones.
	 * @return
	 * @throws DAOException
	 */
	public void generarImprimiblePDF (Bundle bundle, boolean guardarPDF) throws PrintJobsException {

		Log.getInstance().debug(this.getClass(),"ENTRO A METODO GENERAR IMPRIMIBLE PDF PrintJobs");
		int temp = 0;
		Imprimible imp = new Imprimible();
		String tipo = "";
		byte[] bytesFormulario = null;
		gov.sir.print.common.Imprimible imprimibleTempTemp = null;
		
	  try{
		  try {
			  		
			  Log.getInstance().debug(this.getClass(),"AGREGAR IMPRIMIBLE PDF PrintJobs proceso de impresion con el writeObjetcs");
				ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(bundle);

				
				imp.setDatosImprimible(baos.toByteArray());
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
					
					imprimibleTempTemp = imprimibleTemp;
				}
				imp.setNumeroBytes(imp.getDatosImprimible().length);
				imp.setNumeroImpresiones(0);
				imp.setFechaCreacion(new Date());
				
			} catch (Exception e) {
				Log.getInstance().error(this.getClass(),e);
			}
			
			Log.getInstance().debug(this.getClass(),"AGREGAR IMPRIMIBLE PDF PrintJobs proceso de impresion se va a guardar el pdf metodo guardarImprimiblePDF");
			factory.getImpresionDAO().guardarImprimiblePDF(imp,imprimibleTempTemp, guardarPDF, bytesFormulario);
			
			PdfCreator pdf = new PdfCreator();
			ByteArrayOutputStream pdfFormulario = pdf.generar(imprimibleTempTemp);
		    bytesFormulario = pdfFormulario.toByteArray();
		    
			
			
			Log.getInstance().debug(this.getClass(),"AGREGAR IMPRIMIBLE PDF PrintJobs proceso de impresion se va a guardar el pdf metodo guardarImprimiblePDF guardado resultado " + temp);
	  }catch (Exception e) {
		  Log.getInstance().error(this.getClass(),e);
		  PrintJobsException fe = new PrintJobsException("No fue posible guardar el PDF del imprimible", e);	  	
	  }
	  Log.getInstance().debug(this.getClass(),"FIN A METODO AGREGAR IMPRIMIBLE PDF PrintJobs");  		
		
	}   
	
	/**
	 * Elimina el bundle de la base de datos
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public boolean eliminarImprimible (int id) throws PrintJobsException {

	  boolean retorno = false;
	  try{
		retorno = factory.getImpresionDAO().eliminarImprimible(id);					
	  }catch (Exception e) {
			PrintJobsException fe = new PrintJobsException("No fue eliminar el imprimible en la Cola de impresión", e);	  	
	  }
	  return retorno;
	  		
	}    		
	
	/**
	 * Obtiene el bundle de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Imprimible getImprimible(int id) throws PrintJobsException {

		Imprimible imprimible = null;
		try {
			gov.sir.core.negocio.modelo.ImprimiblePk imp = new gov.sir.core.negocio.modelo.ImprimiblePk();
			imp.idImprimible = id;
			imprimible = factory.getImpresionDAO().getImprimible(imp);
		} catch (Exception e) {
			PrintJobsException fe = new PrintJobsException("No fue colocar el imprimible en la Cola de impresión", e);
		}

		return imprimible;
	}
	
	/**
	 * Obtiene el imprimible si este no ha sido impreso previamente
	 */
	public Imprimible getImprimibleNoImpreso(int id) throws PrintJobsException {
		Imprimible imprimible = null;
		try {
			gov.sir.core.negocio.modelo.ImprimiblePk imp = new gov.sir.core.negocio.modelo.ImprimiblePk();
			imp.idImprimible = id;
			imprimible = factory.getImpresionDAO().getImprimibleNoImpreso(imp);
		} catch (Exception e) {
			PrintJobsException fe = new PrintJobsException("No fue colocar el imprimible en la Cola de impresión", e);
		}

		return imprimible;
	}
	
	/**
	 * Obtiene el bundle de la base de datos a partir del id.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public ImprimiblePdf getImprimiblePdf(String idWorkflow) throws PrintJobsException {

		ImprimiblePdf imprimible = null;
		try {
			imprimible = factory.getImpresionDAO().getImprimiblePdf(idWorkflow);
		} catch (Exception e) {
			PrintJobsException fe = new PrintJobsException("No fue colocar el imprimible en la Cola de impresión", e);
		}

		return imprimible;
	}  	
	
	/**
	 * Actualiza el imprimible en la base de datos.
	 * @param imprimible, que contiene los cambios que quieren actualizarse.
	 * @return
	 * @throws DAOException
	 */
	public boolean updateImprimible (Imprimible imprimible) throws PrintJobsException {

	  boolean temp = false;
		
	  try{
			temp = factory.getImpresionDAO().updateImprimible(imprimible);	  	
	  }catch (Exception e) {
			PrintJobsException fe = new PrintJobsException("No fue actualizar el imprimible en la Cola de impresión", e);	  	
	  }
		
		return temp;  		
		
	}  	


	/**
	 * Obtiene la Lista con los Identificadores de las impresiones fallidas para un UID.
	 * @param List. Lista con los Identificadores de las impresiones fallidas
	 * @return
	 * @throws DAOException
	 */
	public List getImpresionesFallidas(Imprimible imprimible, boolean isAdministrador) throws PrintJobsException {

		List idImpresionesFallidas = null;
		try {
			idImpresionesFallidas = factory.getImpresionDAO().getImpresionesFallidas(imprimible, isAdministrador);
		} catch (Exception e) {
			PrintJobsException fe = new PrintJobsException("No fue colocar consultar las impresiones que han fallado en el UID : " + imprimible.getUID(), e);
		}

		return idImpresionesFallidas;
	}  	


	/**
	* Obtiene el valor del tiempo de retardo de la consulta de reimpresión de fallidos
	* por parte del administrador de impresión
	* @param tipo El LookupType de la variable. 
	* @param codigo El LookupCode de la variable. 
	* @return El valor de la variable.
	* @throws Throwable
	*/
	public int getRetardoConsultaReimpresion () throws PrintJobsException {

		int retardo = 0;
		try{
			String retardoStr =factory.getImpresionDAO().getValor(COPLookupTypes.CONFIGURACION_IMPRESION, CImpresion.RETARDO_CONSULTA_REIMPRESION); 
			retardo = Integer.parseInt(retardoStr);
		}catch (DAOException e) {
			PrintJobsException fe = new PrintJobsException("No fue posible inicializar el Persistance Manager", e);	  	
		} catch (Exception e)
		{
			PrintJobsException fe = new PrintJobsException("No fue posible consultar RETARDO_CONSULTA_REIMPRESION", e);
		}
		return retardo;
		
	}

	/**
	 * Verifica si el administrado de impresión identificado con UID
	 * está activo. Esta verificación se hace comparando la fecha actual
	 * contra la última consulta de impresiones fallidas por parte del
	 * administrador de impresion
	 * @param UID
	 * @return
	 * @throws PrintJobsException
	 */
	public boolean estaActivoAdministrador (String UID) throws PrintJobsException
	{
		boolean rta = false;
		
		try{
			int esperaReimpresion = 0;
			int esperaReimpresionPrimeraVez = 0;
			String esperaReimpresionStr =factory.getImpresionDAO().getValor(COPLookupTypes.CONFIGURACION_IMPRESION_CLIENTE, COPLookupCodes.TIEMPO_ESPERA_REIMPRESION_FALLIDOS);
			String esperaReimpresionPVStr =factory.getImpresionDAO().getValor(COPLookupTypes.CONFIGURACION_IMPRESION_CLIENTE, COPLookupCodes.TIEMPO_ESPERA_REIMPRESION_FALLIDOS_PRIMERA_VEZ);
			esperaReimpresion = Integer.parseInt(esperaReimpresionStr);
			esperaReimpresionPrimeraVez = Integer.parseInt(esperaReimpresionPVStr);
			
			int retardo = this.getRetardoConsultaReimpresion();
			Calendar cal1 = Calendar.getInstance();
			cal1.add(Calendar.MILLISECOND, -(esperaReimpresion+retardo));
			Date fecha1 = cal1.getTime();
			
			Calendar cal2 = Calendar.getInstance();
			cal2.add(Calendar.MILLISECOND, -(esperaReimpresionPrimeraVez+retardo));
			Date fecha2 = cal1.getTime();
			
			Date fechaUltimaConsulta = this.getUltimaConsultaReimpresión(UID);
			
			rta = fechaUltimaConsulta.after(fecha1) || fechaUltimaConsulta.after(fecha2); 
		}catch (DAOException e) {
			PrintJobsException fe = new PrintJobsException("No fue posible inicializar el Persistance Manager", e);
			throw fe;
		} catch (Exception e)
		{
			PrintJobsException fe = new PrintJobsException("No fue posible consultar RETARDO_CONSULTA_REIMPRESION", e);
			throw fe;
		}
		return rta;
	}
	
	/**
	 * Consulta fecha de la última verificación del administrador de impresiones
	 * identificado con UID de impresiones fallidas
	 * @param UID
	 * @return
	 * @throws PrintJobsException
	 */
	public Date getUltimaConsultaReimpresión(String UID) throws PrintJobsException
	{
		Date ultimaConsulta = null;
		try{
			ultimaConsulta =  factory.getImpresionDAO().getUltimaConsultaReimpresión(UID); 
		}catch (DAOException e) {
			PrintJobsException fe = new PrintJobsException("No fue posible consultar la fecha de la última reimpresion", e);
			throw fe;
		} catch (Exception e)
		{
			PrintJobsException fe = new PrintJobsException("No fue posible consultar la fecha de la última reimpresion", e);
			throw fe;
		}
		return ultimaConsulta;
	}
	
	/**
	* Obtiene la lista de roles que verán la alerta de administrado de impresión caido
	* @return Lista de Objetos String con el id del rol
	* @throws PrintJobsException
	*/
	public List getRolesAlertaAdmImpInactivo () throws PrintJobsException {

		List roles = null;
		try{
			List lookRoles =factory.getImpresionDAO().getValor(COPLookupTypes.ROLES_ALERTA_ADM_IMP_CAIDO);
			for (Iterator lookItera = lookRoles.iterator(); lookItera.hasNext();)
			{
				if (roles == null)
				{
					roles = new ArrayList();
				}
				OPLookupCodes lookCod = (OPLookupCodes)lookItera.next();
				roles.add(lookCod.getValor());
			}
		}catch (DAOException e) {
			PrintJobsException fe = new PrintJobsException("No fue posible inicializar el Persistance Manager", e);
			throw fe;
		} catch (Exception e)
		{
			PrintJobsException fe = new PrintJobsException("No fue posible consultar ROLES_ALERTA_ADM_IMP_CAIDO", e);
			throw fe;
		}
		return roles;
		
	}

	
}
