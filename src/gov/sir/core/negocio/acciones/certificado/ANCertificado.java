package gov.sir.core.negocio.acciones.certificado;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.dao.ValidacionesSIRDAO;
import co.com.iridium.generalSIR.dao.util.Utilidades;
import gov.sir.core.eventos.certificado.EvnCambioMatricula;
import gov.sir.core.eventos.certificado.EvnCertificado;
import gov.sir.core.eventos.certificado.EvnRespCambioMatricula;
import gov.sir.core.eventos.certificado.EvnRespCertificado;
import gov.sir.core.eventos.certificado.EvnRespValidacionMatricula;
import gov.sir.core.eventos.certificado.EvnValidacionMatricula;
import gov.sir.core.is21.Encriptador;
import gov.sir.core.negocio.acciones.excepciones.ANSeguridadException;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaDepartamentosNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.FolioNoEcontradoException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionEspecialNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionMatriculaCertificadoAntSisHTException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionMatriculaCertificadoHTException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.CNota_Visibilidad;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleHojaDeRuta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutiva;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleOficioPertenencia;
import gov.sir.core.negocio.modelo.imprimibles.base.IGlosarioImprimibles;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleHelper;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.gdocumental.integracion.PublisherIntegracion;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsProperties;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author mmunoz
 */
public class ANCertificado extends SoporteAccionNegocio {

	/**
	 * Mensaje que indica que un folio es de mayor extensión.
	 */
	private static final String MSG_MAYOR_EXTENSION = "EL FOLIO ES DE MAYOR EXTENSION";

	/**
	 * Mensaje que indica que un folio es de menor extensión.
	 */
	private static final String MSG_MENOR_EXTENSION = "NO";

	/**
	 * Instancia del ServiceLocator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de la interfaz de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * Instancia de la intefaz de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia de PrintJobs
	 */
	private PrintJobsInterface printJobs;

	/**
	* Constructor de la clase ANCertificado.
	* Es el encargado de invocar al Service Locator y pedirle una instancia
	* del servicio que necesita
	*/
	public ANCertificado() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

		if (printJobs == null) {
			throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
		}


	}

	/**
	 * Recibe un evento de certificado y devuelve un evento de respuesta
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @throws ANSeguridadException cuando ocurre un problema que no se pueda manejar
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 * @see gov.sir.core.eventos.comun.EvnCertificado
	 * @see gov.sir.core.eventos.comun.EvnRespCertificado
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {

		if (e == null)
			return null;

		if (e instanceof EvnCertificado) {
			EvnCertificado evento = (EvnCertificado) e;
			if (evento.getTipoEvento() == null)
				return null;

			/*ANTIGUO SISTEMA*/
			if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ANT_SIST_REV_INI_CONFIRMAR)) {
				return avance_revision_inicial(evento);
				//return avanzarTurno(evento);
			}
			if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ANT_SIST_REVISION_CREADO)) {
				return revisionOk(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_EXISTE)) {
				return existeFolio(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_CREADO)) {
				return avanzarTurnoDelegandoBloqueo(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_HACER_DEFINITIVO_FOLIO)) {
				return hacerDefinitivo(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_HACER_DEFINITIVO_REANALIZAR)) {
				return regresarAHojaRuta(evento);
			} else if (evento.getTipoEvento().equals("CONSULTAR")) {
				return validarTurno(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ANT_SIST_REV_INI_NEGAR)) {
				return imprimirNotaDevolutiva(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.IMPRIMIR_HOJA_RUTA)) {
				return imprimirHojaRuta(evento);
			}else if (evento.getTipoEvento().equals(EvnCertificado.CONSULTA_FOLIO_COMPLEMENTACION)) {
				return consultarFolio(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.CONSULTAR_PLANTILLA_PERTENENCIA)) {
				return consultarPlantillaPertenencia(evento);
			}else if (evento.getTipoEvento().equals(EvnCertificado.CER_AMPLIACION_TRADICION)) {
				return consultarPlantillaAmpliacion(evento);
			}

			/*FIN ANTIGUO SISTEMA*/

			/*OFICIO DE PERTENENCIA*/
			else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_OFICIO_PERTENENCIA_CONFIRMAR)) {
				return oficioPertenencia(evento);
			}
			/*FIN OFICIO DE PERTENENCIA*/

			/* INCREMENTAR SECUENCIAL DE RECIBOS */
			else if (evento.getTipoEvento().equals(EvnCertificado.INCREMENTAR_SECUENCIAL_RECIBO)) {
				return incrementarSecuencial(evento);

			}

			/* CONSULTAR SECUENCIAL DE RECIBOS */
			else if (evento.getTipoEvento().equals(EvnCertificado.CONSULTAR_SECUENCIAL_RECIBO)) {
				return consultarSecuencial(evento);

			}

			/*REIMPRESION ESPECIAL*/
			else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ENTREGA_REIMPRESION_CORRIENTE)) {
				return imprimirEntrega(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_REIMP_ESP_CONFIRMAR)) {
				return reimpresionEspecial(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_REIMP_ESP_NEGAR)) {
				return imprimirNotaDevolutiva(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.TIPO_ENTREGA_REIMPRESION_ESPECIAL)) {
				return entrega_reimpresion_especial(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.OBTENER_FOLIO_REIMPRESION_ESPECIAL)) {
				return obtenerFolioReimpresionEspecial(evento);
			}else if(evento.getTipoEvento().equals(EvnCertificado.AMPLIACION_TRADICION_CONFIRMAR)){
				hacerDefinitivaComplementacion(evento);//actualiza complementacion
				return avanzarTurno(evento);
			}else if(evento.getTipoEvento().equals(EvnCertificado.AMPLIACION_TRADICION_GUARDAR)){
				return actualizarFolioComplementacion(evento);//actualiza complementacion
			}else if(evento.getTipoEvento().equals(EvnCertificado.AMPLIACION_TRADICION_NEGAR)){
				deshacerCambiosAmpliacionTradicion(evento);
				return avanzarTurno(evento);
			} 

			/*REIMPRESION ESPECIAL*/

			/*Default*/
			else if (!evento.getTipoEvento().equals("") && evento.getTipoEvento().startsWith("TIPO_")) {
				return avanzarTurno(evento);
			} else if (evento.getTipoEvento().equals(EvnCertificado.VALIDAR_MATRICULA_MIG)) {
				return validarMatriculaMig(evento);
			}

		} else if (e instanceof EvnCambioMatricula)
			return cambiarMatricula((EvnCambioMatricula) e);
		else if (e instanceof EvnValidacionMatricula) {
			EvnValidacionMatricula evValidacion = (EvnValidacionMatricula) e;
			if (evValidacion.getTipoEvento().equals("AGREGAR_NATRICULA_ANT_SIS")) {
				return validarMatriculaAntSistema(evValidacion);
			} else if (evValidacion.getTipoEvento().equals(EvnValidacionMatricula.CERTIFICADO_ESPECIAL)){
                                return validarMatriculaCE(evValidacion);
                        }
            return validarMatricula(evValidacion);
		} 
		return null;
	}





   //Depurado Enero 11/2005
   /**
   * Método que se encarga de realizar la lógica de negocio cuando se delega un turno desde la fase de entrega
   * y hacia reimpresión especial.
   * @param evento Evento de Certificado que contiene la información necesaria para realizar la lógica de
   * negocio. 
   * @throws EventoException Genera una excepción si se presenta alguna de las siguientes condiciones:
   * 1. El turno no puede ser avanzado a esta fase sin una nota informativa.
   * 2. Se superó el número de intentos de impresión para el rol.
   * 3. El certificado que se intenta reimprimir es de tipo exento. 
   * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
   * @see gov.sir.core.eventos.comun.EvnCertificado
   * @see gov.sir.core.eventos.comun.EvnRespCertificado
   */
   private EventoRespuesta entrega_reimpresion_especial(EvnCertificado evento) throws EventoException {
		
		//1. Se valida que no se haya superado el número máximo de reimpresiones permitidas
		//para el rol
		Turno turno = evento.getTurno();
		Rol rol = new Rol();
		rol.setRolId(CRoles.REIMPRESION_ESPECIAL);
		Proceso proceso = evento.getProceso();
		SolicitudCertificado solCerti = (SolicitudCertificado) turno.getSolicitud();
		int num = solCerti.getNumImpresiones();
		int maxNumImpresiones = getNumMaxImpresiones(rol, proceso);

          //1.1 Si se superó el número de reimpresiones permitidas, se genera una excepción. 
		  if (num > maxNumImpresiones)
			  throw new ImpresionNoEfectuadaException("No es posible enviar el turno a reimpresión especial: ya se ha excedido el número de intentos de reimpresión.");

		
		
		//2. Se valida que el tipo de certificado que se va a reimprir no sea de tipo exento.
		List listaFolios = solCerti.getSolicitudFolios();
		SolicitudFolio solFolio = (SolicitudFolio)listaFolios.get(listaFolios.size()-1);
		Folio folio=null;
		List liquidaciones = solCerti.getLiquidaciones();
		LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado)liquidaciones.get(liquidaciones.size()-1);
		String tipoTarifa =liquidacion.getTipoTarifa();

           //1.2 Si el tipo de tarifa es exento no se permite la reimpresión del certificado
           //y se genera una excepción. 
		   if (tipoTarifa.equals("EXENTO"))
		   {
			   throw new TurnoNoAvanzadoException("No es posible enviar el turno a reimpresión especial: el certificado es de tipo EXENTO.");
		   }

        
        //3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
        //del turno desde esta fase. 
	    try
	    {
			Hashtable tabla = new Hashtable(2);
			tabla.put(Processor.RESULT, evento.getRespuestaWF());
			tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tabla,turno);
	    }
	    catch(Throwable t)
	    {
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
	    }
	    

		return avanzarTurno(evento);
	}








	private EventoRespuesta obtenerFolioReimpresionEspecial(EvnCertificado evento) throws EventoException {
		Folio f = evento.getFolio();
		FolioPk fid= new FolioPk();
		fid.idMatricula=f.getIdMatricula();

		try{
			f= forseti.getFolioByID(fid);
			//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
			if(f.getAnotaciones()==null || f.getAnotaciones().isEmpty()){
						f.setAnotaciones(forseti.getAnotacionesFolio(fid));
					}
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo completar la entrega", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespCertificado evRespuesta = new EvnRespCertificado(EvnRespCertificado.OBTENER_FOLIO_REIMPRESION_ESPECIAL, f);

		return evRespuesta;
	}








	//Depurado Enero 11/2005
	/**
	* Método que se encarga de realizar la lógica de negocio cuando se delega un turno 
    * hacia la fase de Revisión Inicial, en el proceso de antiguo sistema. 
	* @param evento Evento de Certificado que contiene la información necesaria para realizar la lógica de
	* negocio. 
	* @throws EventoException Genera una excepción si se presenta alguna de las siguientes condiciones:
	* 1. El turno no puede ser avanzado a esta fase sin una nota informativa.
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	* @see gov.sir.core.eventos.comun.EvnCertificado
	* @see gov.sir.core.eventos.comun.EvnRespCertificado
	*/
	private EventoRespuesta avance_revision_inicial(EvnCertificado evento) throws EventoException {
		
		
		//1. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase. 
		try
		{
			Hashtable tabla = new Hashtable(2);
			tabla.put(Processor.RESULT, evento.getRespuestaWF());
			tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			Turno turno = evento.getTurno();
			//1.1 Para el flujo normal del turno.
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tabla,turno);
			//1.2 Para la respuesta EXISTE.
			tabla.put(Processor.RESULT, "EXISTE"); 
			
		}
		catch(Throwable t)
		{
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}
		
		Turno turno = evento.getTurno();
		
		
		//2. Si el turno es de los procesos de Correcciones o de Registro, se realiza el avance 
		//directo del turno. 
		
		if( turno.getSolicitud() instanceof SolicitudCorreccion ) {
			// ninguna condicion sobre numero de folios;
			// siempre se va por --> CONFIRMAR
			return avanzarTurno( evento );
		}
		
		if( turno.getSolicitud() instanceof SolicitudRegistro ) {
			// ninguna condicion sobre numero de folios;
			// siempre se va por --> CONFIRMAR
			return avanzarTurno( evento );
	    }
		
		
		
		//3. Flujo Normal
		List listaFolios = turno.getSolicitud().getSolicitudFolios();
		int numFolios = listaFolios.size();

        
        //Si el turno tiene al menos un folio asociado, se avanza con la respuesta EXISTE. 
        if( numFolios > 0 ){

         Usuario usuarioAuriga = evento.getUsuario();
         String tipoEvento = evento.getTipoEvento();
         Fase fase = evento.getFase();
         Estacion estacion = evento.getEstacion();
         String respuestaWF = "EXISTE";
         gov.sir.core.negocio.modelo.Usuario usuario = evento.getUsuarioNec();
         Rol rol = evento.getRol();
         Proceso proceso = evento.getProceso();
         EvnCertificado evCert = new EvnCertificado(usuarioAuriga, tipoEvento, turno, fase,
                                                    estacion, respuestaWF, usuario, rol, proceso);
         return avanzarTurno( evCert );
		}

		return avanzarTurno(evento);
	}







	/**
	 * Este método llama los servicios necesarios para hacer definitivo un folio una vez
	 * fue revisado.
	 * @param evento EvnCertificado
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 * @throws EventoException
	 */
	private EventoRespuesta revisionOk(EvnCertificado evento) throws EventoException {
		return null;
	}

	/**
	 * Este método llama los servicios necesarios para hacer definitivo un folio una vez
	 * fue revisado.
	 * @param evento EvnCertificado
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 * @throws EventoException
	 */
	private EventoRespuesta hacerDefinitivo(EvnCertificado evento) throws EventoException {
		return null;
	}

	/**
	 * Este método llama los servicios necesarios para regresar el turno desde la fase de creación
	 * de folio hasta la fase de hoja de ruta en el proceso de antiguo sistema en certificados,
	 * en caso de que hayan existido errores en la digitación de la hoja de ruta
	 * fue revisado.
	 * @param evento EvnCertificado
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 * @throws EventoException
	 */
	private EventoRespuesta regresarAHojaRuta(EvnCertificado evento) throws EventoException {
		return null;
	}

	/**
	* Este metodo hace el llamado al negocio para que se incremente el secuencial de recibos.
	* @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	*/
	private EventoRespuesta incrementarSecuencial(EvnCertificado evento) throws EventoException {

		EvnRespCertificado evRespuesta = null;
		long valorSecuencial;
		long idProceso = Long.valueOf(CProceso.PROCESO_CERTIFICADOS).longValue();
		try {
			gov.sir.core.negocio.modelo.Usuario user = null;
			try {
				user = evento.getUsuarioNec();
			} catch (Exception ex) {
				Log.getInstance().error(ANCertificado.class,"No se encontro usuario del evento");
			}
			Estacion estacion = evento.getEstacion();
			EstacionReciboPk estacionRecibo = new EstacionReciboPk();
			estacionRecibo.idEstacion = estacion.getEstacionId();
			valorSecuencial = hermod.avanzarNumeroRecibo(estacionRecibo, 1, idProceso);
			valorSecuencial = hermod.getNextNumeroReciboSinAvanzar(estacionRecibo,user, idProceso);

			boolean motivo = hermod.almacenarMotivoIncrementoSecuencial(evento.getUsuarioNec(),valorSecuencial,evento.getMotivo());

		} catch (HermodException e) {
			throw new EventoException("No se pudo obtener el secuencial de recibos.", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespCertificado(valorSecuencial,null);
		return evRespuesta;
	}


	/**
	* Este metodo hace el llamado al negocio para que agrege el oficio de pertenencia
	* al turno
	* @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	*/
	private EventoRespuesta oficioPertenencia(EvnCertificado evento) throws TurnoNoAvanzadoException, EventoException {
		Turno turno = evento.getTurno();
		String oficio = evento.getOficioPertenencia();
		//forseti.setOficioPertenencia(turno, oficio);
		//TODO:	INSERTAR EL OFICIO DE PERTENENCIA
		Hashtable tabla = new Hashtable();

		Fase fase = evento.getFase();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
		//tabla.put(CImpresion.OFICIO_PERTENENCIA,evento.getOficioPertenencia());

		String textoOficioPertenencia = evento.getOficioPertenencia();
		ImprimibleOficioPertenencia imprimible = new ImprimibleOficioPertenencia(textoOficioPertenencia,CTipoImprimible.OFICIO);
                tabla.put(Processor.IMPRIMIBLE, imprimible);

		Oficio of = new Oficio();
		of.setTextoOficio(oficio);
		TurnoHistoria th = obtenerTurnoHistoriaActual(evento);
		of.setTurnoHistoria(th);
		of.setFirmado(false);

		try {
			hermod.crearOficio(of);
			avanzarTurno(evento);
			
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null)
			{
				tabla.put(Processor.INTENTOS, intentosImpresion);
			}
			else
			{
				tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null)
			{
				tabla.put(Processor.ESPERA, esperaImpresion);
			}
			else
			{
				tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
			}
                        //OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
                    
                    String nombre = "";
                    String archivo = "";
                    String cargoToPrint = "";
                    String cargo = CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
                    FirmaRegistrador firmaRegistrador = null;
                    CirculoPk cid = new CirculoPk();
                    cid.idCirculo = turno.getIdCirculo();
                    String rutaFisica = null;
                    String sNombre = "";
                    try {
                        firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);

                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }
                        if (firmaRegistrador == null) {
                            cargo = CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
                            firmaRegistrador = forseti.getFirmaRegistradorActiva(cid, cargo);
                        }

                        rutaFisica = hermod.getPathFirmasRegistradores();

                        sNombre = firmaRegistrador.getNombreRegistrador();
                        archivo = firmaRegistrador.getIdArchivo();

                        if (turno.isNacional()) {
                            if (firmaRegistrador == null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
                                    || archivo == null || archivo.equals("")) {
                                throw new Exception("No se permiten certificados de orden nacional sin firma");
                            }
                        }

                        //Se recupera el verdadero cargo para definir si es ENCARGADO o
                        //no lo es.
                        cargo = firmaRegistrador.getCargoRegistrador();

                        //Se saca el valor del cargo para imprimirlo en el certificado
                        List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

                        cargoToPrint = "";
                        OPLookupCodes lookUp;
                        for (Iterator it = cargos.iterator(); it.hasNext();) {
                            lookUp = (OPLookupCodes) it.next();
                            if (lookUp.getCodigo().equals(cargo)) {
                                cargoToPrint = lookUp.getValor();
                            }
                        }
                    } catch (Exception e) {
                        
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                    if (sNombre == null) {
                        sNombre = "";
                    }

                    //+++
                    imprimible.setCargoRegistrador(cargoToPrint);
                    imprimible.setNombreRegistrador(sNombre);

                         if (rutaFisica != null) {
                               BufferedImage imagenFirmaRegistrador = getImage(rutaFisica, archivo);
                                byte pixeles[] = null;
                               try {
                                   pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
                               } catch (Throwable e1) {

                                   e1.printStackTrace();
                               }
                                   imprimible.setPixelesImagenFirmaRegistrador(pixeles);

                              
                           }
			imprimir(imprimible,tabla,turno.getIdCirculo());
			//hermod.avanzarTurno(turno, fase, tabla,CAvanzarTurno.AVANZAR_NORMAL);

		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		return null;
	}

	private TurnoHistoria obtenerTurnoHistoriaActual(EvnCertificado evento) throws EventoException {
		Turno turno = evento.getTurno();

		List tHistorias = turno.getHistorials();
		ListIterator iter = tHistorias.listIterator();

		String faseActual = turno.getIdFase();

		TurnoHistoria activo = null;
		while (iter.hasNext()) {
			TurnoHistoria tH = (TurnoHistoria) iter.next();
			if (tH.isActivo() && tH.getFase().equals(faseActual)) {
				activo = tH;
				break;
			}
		}
		if (activo == null){
			throw new EventoException("El turno historia actual es nulo");
		}

		return activo;
	}

	/**
	 * Este metodo hace el llamado al negocio para que agrege el oficio a un turno de
	 * solicitud de antiguo sistema
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 */
	private EvnRespCertificado existeFolio(EvnCertificado evento) throws EventoException {
		Hashtable tabla = new Hashtable();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		Turno turno = evento.getTurno();
		TurnoPk idTurno = new TurnoPk();
		idTurno.anio = turno.getAnio();
		idTurno.idCirculo = turno.getIdCirculo();
		idTurno.idProceso = turno.getIdProceso();
		idTurno.idTurno = turno.getIdTurno();

		Folio folio = new Folio();
		try {

			String matricula = evento.getIdMatricula();
			try {
				folio = forseti.getFolioByMatricula(matricula);
// no necesita anotaciones debido a que se redirecciona a la pantalla 
// de confirmación de creación de turno
			} catch (ForsetiException e) {
				throw new FolioNoEcontradoException(e.getMessage(), e);
			}
			if (folio == null) {
				throw new FolioNoEcontradoException("El folio correspondiente a la matricula no fue encontrado");
			}

			boolean existe = false;
			Solicitud solicitud = turno.getSolicitud();
			Iterator it = solicitud.getSolicitudFolios().iterator();
			while(it.hasNext()){
				SolicitudFolio solFolio = (SolicitudFolio)it.next();
				Folio folioTemp = solFolio.getFolio();
				if(folioTemp!=null && folioTemp.getIdMatricula().equals(folio.getIdMatricula())){
					existe = true;
				}
			}

			if(!existe){
				try {
					SolicitudFolioPk sfId = hermod.addFolioToTurno(matricula, idTurno, evento.getUsuarioNec());
				} catch (ForsetiException e) {
					throw new FolioNoEcontradoException("No se relacionó el folio al turno", e);
				}
			}

			SolicitudFolio sol = new SolicitudFolio();
			sol.setFolio(folio);
			turno.getSolicitud().addSolicitudFolio(sol);
			
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		FolioPk fID = new FolioPk();
		fID.idMatricula = folio.getIdMatricula();

		try {
			forseti.getCountAnotacionesFolio(fID, CCriterio.TODAS_LAS_ANOTACIONES, null);
		} catch (Throwable e1) {
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + e1.getMessage());
			e1.printStackTrace();
		}
		return new EvnRespCertificado(folio);
	}

	/**
	 * Este metodo hace el llamado al negocio para que agrege imprima un certificado
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 */
	private EventoRespuesta imprimirEntrega(EvnCertificado evento) throws ServicioNoEncontradoException, ImpresionNoEfectuadaException, EventoException {

		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();
		
		//TFS 5877  se validan las notas informativas
		java.util.List notasTurno=turno.getNotas();
		if(notasTurno!=null)
		{
			Iterator it=notasTurno.iterator(); 
			boolean creadaencertentregados=false;
			while(it.hasNext())
			{
				Nota not=(Nota)it.next();
				if(not.getIdFase().equals(CFase.CER_ENTREGAR) && not.getIdProceso()==1)
				{
					//la nota se creo en la fase de certificados a entregar de un turno de certificados 
					creadaencertentregados=true;
				}
			}
			if(!creadaencertentregados)
			{
				throw new ImpresionNoEfectuadaException("No es posible reimprimir, no existe una Nota Informativa Creada en la Fase que justifique la Reimpresion");	
			}
		}
		else
		{
		  throw new ImpresionNoEfectuadaException("No es posible reimprimir, no existe una Nota Informativa  Creada en la Fase que justifique la Reimpresion");	
		}
		
		//fin de cambios TFS 5877

		Rol rol = evento.getRol();
		Proceso proceso = evento.getProceso();

		SolicitudCertificado solCerti = (SolicitudCertificado) turno.getSolicitud();
		int num = solCerti.getNumImpresiones();
				
	    int maxNumImpresiones = getNumMaxImpresiones(rol, proceso);
	    	if (num > maxNumImpresiones) {
	    		   throw new ImpresionNoEfectuadaException("No es posible reimprimir, ya se ha excedido el número de intentos de reimpresión.");  
	    	   }
	    
		List listaFolios = solCerti.getSolicitudFolios();
		if (!listaFolios.isEmpty()){
			SolicitudFolio solFolio = (SolicitudFolio)listaFolios.get(listaFolios.size()-1);
			Folio folio=null;


			List liquidaciones = solCerti.getLiquidaciones();
			LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado)liquidaciones.get(liquidaciones.size()-1);
			String tipoTarifa =liquidacion.getTipoTarifa();


			if (tipoTarifa.equals("EXENTO"))
			{
				throw new ImpresionNoEfectuadaException("No es posible reimprimir porque el certificado es de tipo EXENTO.");
			}



			Fase fase = evento.getFase();
			Estacion estacion = evento.getEstacion();
			String idCirculo = turno.getIdCirculo();
			tabla.put(Processor.ESTACION, estacion.getEstacionId());
			tabla.put(Processor.CIRCULO, idCirculo);
			tabla.put(Processor.FIRMA_PATH, evento.getPathFirmas());
			tabla.put(Processor.RESULT, evento.getRespuestaWF());
			tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

			ImprimibleBase imprimible = null;

			Folio fol = null;

			try {
				fol = ((SolicitudFolio) turno.getSolicitud().getSolicitudFolios().get(0)).getFolio();
			} catch (Exception e) {
				Log.getInstance().error(ANCertificado.class,e);
			}

			//imprimir nota devolutiva
			if (fol == null) {
				Vector notas = this.getNotasPublicas(turno);
				Vector notasPublicas = this.getNotasPublicas(turno);

				String nombreCirculo="";
				String turnoId = turno.getIdWorkflow();

				String matricula = "";
				matricula= fol.getIdMatricula();

				nombreCirculo = (String)ImprimibleHelper.getDatoFromFolio(fol,IGlosarioImprimibles.circuloRegistral);
				tabla.put(Processor.IMPRIMIBLE, imprimible);
			}

			try
			{

				//INGRESO DE INTENTOS DE IMPRESION
				String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
				if (intentosImpresion != null)
				{
					tabla.put(Processor.INTENTOS, intentosImpresion);
				}
				else
				{
					tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
				}

				//INGRESO TIEMPO DE ESPERA IMPRESION
				String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
				if (intentosImpresion != null)
				{
					tabla.put(Processor.ESPERA, esperaImpresion);
				}
				else
				{
					tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
				}

				//AVANZAR EL TURNO
				if (turno.getSolicitud() instanceof SolicitudCertificado){
					SolicitudCertificado solCert = (SolicitudCertificado)turno.getSolicitud();
					LiquidacionTurnoCertificado liq = (LiquidacionTurnoCertificado)solCert.getLiquidaciones().get(0);
		    		
					if(!(liq.getTipoCertificado().getIdTipoCertificado().equals(CTipoCertificado.TIPO_PERTENENCIA_ID))){
						imprimirCertificado(turno,evento.getUsuarioNec(),turno.getIdCirculo(), true);
						
					}
				}
				

			} catch (HermodException e) {
				throw new ImpresionNoEfectuadaException("No se pudo avanzar el turno", e);
			} catch (ForsetiException e) {
				throw new ImpresionNoEfectuadaException("No se pudo avanzar el turno", e);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), e);
			}
		}
		
		return null;
	}

	private int getNumMaxImpresiones(Rol rol, Proceso proceso) throws EventoException {
		int maxImpresiones = 0;
		try {
			maxImpresiones = hermod.getNumeroMaximoImpresiones(rol, proceso);
		} catch (HermodException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		return maxImpresiones;
	}

	/**
	 * Este metodo hace el llamado al negocio para que agrege imprima un certificado
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 */
	private EventoRespuesta reimpresionEspecial(EvnCertificado evento) throws ServicioNoEncontradoException, ImpresionNoEfectuadaException, EventoException {

		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();

		Rol rol = evento.getRol();
		Proceso proceso = evento.getProceso();
		
		java.util.List notasTurno=turno.getNotas();
		if(notasTurno!=null)
		{
			Iterator it=notasTurno.iterator(); 
			boolean creadaencertentregados=false;
			while(it.hasNext())
			{
				Nota not=(Nota)it.next();
				if(not.getIdFase().equals(CFase.CER_REIMPRESION_ESPECIAL) && not.getIdProceso()==1)
				{
					//la nota se creo en la fase de certificados a entregar de un turno de certificados 
					creadaencertentregados=true;
				}
			}
			if(!creadaencertentregados)
			{
				throw new ImpresionNoEfectuadaException("No es posible reimprimir, no existe una Nota Informativa Creada en la Fase que justifique la Reimpresion");	
			}
		}
		else
		{
		  throw new ImpresionNoEfectuadaException("No es posible reimprimir, no existe una Nota Informativa  Creada en la Fase que justifique la Reimpresion");	
		}

		SolicitudCertificado solCerti = (SolicitudCertificado) turno.getSolicitud();
		int num = solCerti.getNumImpresiones();
		int maxNumImpresiones = getNumMaxImpresiones(rol, proceso);

		if (num > maxNumImpresiones)
			throw new ImpresionEspecialNoEfectuadaException("No es posible reimprimir, ya se ha excedido el número de intentos de reimpresión.");

		Fase fase = evento.getFase();
		Estacion estacion = evento.getEstacion();
		String idCirculo = turno.getIdCirculo();
		//tabla.put(Processor.ESTACION, estacion.getEstacionId());
		tabla.put(Processor.CIRCULO, idCirculo);
		Folio folio = null;

		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		ImprimibleBase imprimible = null;

		Folio fol = null;

		try {
			fol = ((SolicitudFolio) turno.getSolicitud().getSolicitudFolios().get(0)).getFolio();
		} catch (Exception e) {
			Log.getInstance().error(ANCertificado.class,e);
		}

		//imprimir nota devolutiva
		if (fol == null) {
			Vector notas = this.getNotasPublicas(turno);
			Vector notasPublicas = this.getNotasPublicas(turno);

			String nombreCirculo="";
			String turnoId = turno.getIdWorkflow();

			String matricula = "";
			matricula= fol.getIdMatricula();
	        nombreCirculo = (String)ImprimibleHelper.getDatoFromFolio(fol,IGlosarioImprimibles.circuloRegistral);

			tabla.put(Processor.IMPRIMIBLE, imprimible);
		}

		try
		{

             //INGRESO DE INTENTOS DE IMPRESION
			 String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			 if (intentosImpresion != null)
			 {
				  tabla.put(Processor.INTENTOS, intentosImpresion);
			 }
			 else
			 {
				  tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			 }

			 //INGRESO TIEMPO DE ESPERA IMPRESION
			 String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			 if (intentosImpresion != null)
			 {
			      tabla.put(Processor.ESPERA, esperaImpresion);
			 }
			 else
			 {
				  tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
			 }

			//AVANZAR EL TURNO
			 if (turno.getSolicitud() instanceof SolicitudCertificado){
				 imprimirCertificado(turno,evento.getUsuarioNec(),turno.getIdCirculo(),true);
				 hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,evento.getUsuarioNec());
			 }

		} catch (HermodException e) {
			throw new ImpresionEspecialNoEfectuadaException("No se pudo avanzar el turno", e);
		} catch (ForsetiException e) {
			throw new ImpresionEspecialNoEfectuadaException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Este metodo hace el llamado al negocio para que agrege imprima un certificado
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 */
	/*private EventoRespuesta imprimir(EvnCertificado evento) throws ServicioNoEncontradoException, ImpresionNoEfectuadaException, EventoException {

		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		Estacion estacion = evento.getEstacion();
		String idCirculo = turno.getIdCirculo();
		tabla.put(Processor.ESTACION, estacion.getEstacionId());
		tabla.put(Processor.CIRCULO, idCirculo);
		Folio folio = null;

		Folio fol = ((SolicitudFolio) turno.getSolicitud().getSolicitudFolios().get(0)).getFolio();

		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, ((Usuario) evento.getUsuario()).getUsuarioId());

		try {
			folio = forseti.getFolioByMatricula(fol.getIdMatricula());
			Folio.ID fid = new Folio.ID();
			fid.idMatricula = folio.getIdMatricula();
			fid.idZonaRegistral = folio.getIdZonaRegistral();

			List padres = forseti.getFoliosPadre(fid);
			List hijos = forseti.getFoliosHijos(fid);

            //gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNec();
			ImprimibleCertificado iCert = new ImprimibleCertificado(folio, turno, padres, hijos);
			tabla.put(Processor.IMPRIMIBLE, iCert);

			Folio.ID oid = new Folio.ID();
			oid.idMatricula = folio.getIdMatricula();
			oid.idZonaRegistral = folio.getIdZonaRegistral();

			long numAnotaciones = forseti.getCountAnotacionesFolio(oid, CCriterio.TODAS_LAS_ANOTACIONES, null);
			hermod.setAnotacionestoSolicitud(numAnotaciones, turno.getSolicitud());
			hermod.avanzarTurno(turno, fase, tabla);
		} catch (HermodException e) {
			throw new ImpresionNoEfectuadaException("No se pudo avanzar el turno", e);
		} catch (ForsetiException e) {
			throw new ImpresionNoEfectuadaException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return null;
	}*/

	/**
	 * Este metodo hace el llamado al negocio para que avance el turno delegando el Bloqueo del folio
	 * para que otro usuario lo pueda hacer definitivo posteriormente.
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 */
	private EvnRespCertificado avanzarTurnoDelegandoBloqueo(EvnCertificado evento) throws ServicioNoEncontradoException, TurnoNoAvanzadoException, EventoException {
		EvnRespCertificado evRespuesta = null;
		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
		Folio folio = evento.getFolio();
		CirculoPk idCirc = new CirculoPk();
		idCirc.idCirculo = folio.getZonaRegistral().getCirculo().getIdCirculo();

		evento.setFolio(folio);
		this.imprimirHojaRuta(evento);
		
		return evRespuesta;
	}

	/**
	 * Este metodo hace el llamado al negocio para que avance el turno
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 */
	private EvnRespCertificado avanzarTurno(EvnCertificado evento) throws ServicioNoEncontradoException, TurnoNoAvanzadoException, EventoException {
		EvnRespCertificado evRespuesta = null;
		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

                /**
                * @author Fernando Padilla Velez
                * @change Acta - Requerimiento No 178 - Integración Gestión Documental y SIR,
                *         Se agrega esta fragmento de codigo para la publicacion del mensaje del turno creado.
                */
                if(turno.getSolicitud().getLiquidaciones() != null){
                    Liquidacion liquidacion = (Liquidacion) turno.getSolicitud().
                            getLiquidaciones().get(turno.getSolicitud().getLiquidaciones().size() - 1);

                    if (liquidacion instanceof LiquidacionTurnoCertificado) {
                            LiquidacionTurnoCertificado liquidaCert = (LiquidacionTurnoCertificado) liquidacion;
                            /**
                             * @author Fernando Padilla Velez
                             * @change 6760: Acta - Requerimiento No 191 - Pantalla Administrativa SGD,
                             *         Se realizó refactoring al proceso y ya no son necesarias.
                             */

                    }
                }

                try{
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tabla, evento.getTurno());
		}catch (Throwable t) {
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		try {
			
			if (evento.getTipoEvento().equals(EvnCertificado.TIPO_MAYOR_EXT_CONFIRMAR)){
				imprimirCertificado(turno,evento.getUsuarioNec(),turno.getIdCirculo(),false);
			}
			
			if (evento.getTipoEvento().equals(EvnCertificado.TIPO_OFICIO_PERTENENCIA_CONFIRMAR)){
				imprimirCertificado(turno,evento.getUsuarioNec(),turno.getIdCirculo(),false);
			}
			
			if(evento.getTipoEvento().equals(EvnCertificado.AMPLIACION_TRADICION_CONFIRMAR)){

				imprimirCertificado(turno, evento.getUsuarioNec(),turno.getIdCirculo() , false);
			}
			
			if (turno.getSolicitud() instanceof SolicitudCertificado){
				hermod.avanzarTurnoNuevoNormal(turno, fase, tabla,evento.getUsuarioNec());	
			}
		
		} catch (HermodException e) {
			throw new TurnoNoAvanzadoException("No se pudo completar la entrega", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return evRespuesta;
	}

	/**
	 * Valida una matrícula
	 * @param evento evento que tiene la información de la matrícula a validar
	 */
	private EvnRespValidacionMatricula validarMatricula(EvnValidacionMatricula evento) throws EventoException {

		TipoCertificadoPk tipo = new TipoCertificadoPk(evento.getTipoCertificado());
		List validaciones = null;
		List matriculas = new ArrayList();
		String matricula = evento.getMatricula();
		matriculas.add(matricula);

		try {
			validaciones = hermod.getValidacionesCertificado(tipo);
		} catch (Throwable t) {
			throw new EventoException("Error recuperando la lista de validaciones para " + "tipo de certificado " + evento.getTipoCertificado(), t);
		}
		
		try
		{
			hermod.validarMatriculas(validaciones,matriculas);
				
				
			boolean validaForseti = false;
			try {
				validaForseti = forseti.validarMatriculas(validaciones, matriculas);
			} catch (ForsetiException e)
			{
				Hashtable ht = e.getHashErrores();
				boolean lanzaError = true;
				if (ht != null && ht.size() == 1)
				{
					List errores = (List)ht.get(matricula);
					if (errores != null && errores.size() == 1)
					{
						if (errores.get(0).equals(CValidacion.FIRMA_REG_EXISTE_MSG) && !evento.isTurnoNacional())
						{
							lanzaError = false;
							validaForseti = true;
						}
					}
				}
				if (lanzaError)
				{
					throw e;
				}
			}
			
			if (validaForseti) {
				long numAnota = this.getNumeroAnotacionesFolio(matricula);

				boolean esMayorExt = false;
				esMayorExt = forseti.mayorExtensionFolio(matricula);

				 //this.esMayorExtension(matricula);
				/*
				if (numAnota > 150)
					esMayorExt = true;
				*/

				if (esMayorExt)
					Log.getInstance().debug(ANCertificado.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado]::validarMatricula: el folio es de mayor extension");
				else
					Log.getInstance().debug(ANCertificado.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado]::validarMatricula: el folio NO es de mayor extension");

				EvnRespValidacionMatricula eventoRespuesta = new EvnRespValidacionMatricula(matricula);

				if (esMayorExt)
					eventoRespuesta.setMayorExtension(CFolio.MAYOR_EXTENSION);
				else
					eventoRespuesta.setMayorExtension(CFolio.NO_MAYOR_EXTENSION);

				eventoRespuesta.setNumeroAnotaciones(String.valueOf(numAnota));

				return eventoRespuesta;
			}
		} catch (Throwable t) {
			throw new ValidacionMatriculaCertificadoHTException("Error validando la matricula " + evento.getMatricula(), t);
		}
		return null;
	}
        
        /**
         * Retorna la validación de una matricula de un certificado especial.
         * @param evento
         * @return
         * @throws EventoException 
         */
        private EvnRespValidacionMatricula validarMatriculaCE(EvnValidacionMatricula evento) throws EventoException {

		TipoCertificadoPk tipo = new TipoCertificadoPk((evento.getTipoCertificado()!=null?evento.getTipoCertificado():"1"));
		List validaciones = null;
		List matriculas = new ArrayList();
		String matricula = evento.getMatricula();
		matriculas.add(matricula);
			try {
				long numAnota = this.getNumeroAnotacionesFolio(matricula);

				boolean esMayorExt = false;
				esMayorExt = forseti.mayorExtensionFolio(matricula);

				 //this.esMayorExtension(matricula);
				/*
				if (numAnota > 150)
					esMayorExt = true;
				*/

				if (esMayorExt)
					Log.getInstance().debug(ANCertificado.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado]::validarMatricula: el folio es de mayor extension");
				else
					Log.getInstance().debug(ANCertificado.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado]::validarMatricula: el folio NO es de mayor extension");

				EvnRespValidacionMatricula eventoRespuesta = new EvnRespValidacionMatricula(matricula);

				if (esMayorExt)
					eventoRespuesta.setMayorExtension(CFolio.MAYOR_EXTENSION);
				else
					eventoRespuesta.setMayorExtension(CFolio.NO_MAYOR_EXTENSION);

				eventoRespuesta.setNumeroAnotaciones(String.valueOf(numAnota));

				return eventoRespuesta;
			
		} catch (Throwable t) {
			throw new ValidacionMatriculaCertificadoHTException("Error validando la matricula " + evento.getMatricula(), t);
		}
	}
        
	/**
	 * Cambia una matrícula
	 * @param evento evento que tiene la información de la matrícula a validar y cambiar.
	 */
	private EvnRespCambioMatricula cambiarMatricula(EvnCambioMatricula evento) throws EventoException {


		String matriculaActual = evento.getMatriculaActual();
		String matriculaNueva = evento.getMatriculaNueva();

		Turno turno;
		//validar la matricula
		try {
			EvnValidacionMatricula evnVal = new EvnValidacionMatricula(evento.getUsuario(), matriculaNueva, evento.getTipoCertificado());
			evnVal.setTurnoNacional(evento.isNacional());
			EvnRespValidacionMatricula evnRespVal = this.validarMatricula(evnVal);
		}catch (ValidacionMatriculaCertificadoHTException val) {
			throw val;
		} catch (EventoException ee) {
			throw new EventoException("Error validando la matrícula " + matriculaNueva + ee.getMessage(), ee);
		}

		TurnoPk tid = evento.getTurno_id();
                /*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   se asigna a idWorkFlow los datos correspondientes desde tid
                 * Caso Mantis  :   02359
                 */
                 String idWorkFlow = tid.anio + "-" + tid.idCirculo + "-" + tid.idProceso + "-" + tid.idTurno;

		try {
			/*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   se realizan las validaciones para cambio de matricula en isCambioMatriculaCertRegistro(idWorkFlow, matriculaNueva)
                         * Caso Mantis  :   02359
                         */
                        turno=hermod.getTurno(tid);
                        if(turno.getSolicitud().getSolicitudesPadres() != null){
                            ValidacionesSIRDAO validacion = new ValidacionesSIRDAO();
                            if(!validacion.isCambioMatriculaCertRegistro(idWorkFlow, matriculaNueva)){
                                throw new EventoException();
                            }
                        }
                /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged
                */
                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoriaSir = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                java.util.Map infoUsuario = new java.util.HashMap();
                if(evento.getInfoUsuario() !=null){
                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                } 
                try {
                    auditoriaSir.guardarDatosTerminal(infoUsuario);
                } catch (GeneralSIRException ex) {
                    Log.getInstance().error(ANCertificado.class,ex);
                }
                        //cambiar la matricula
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   se verifica matriculaActual sea diferente a null
                         * Caso Mantis  :   02359
                         */
                        if (matriculaActual != null){
                            hermod.removeFolioFromTurno(matriculaActual, tid);
                        }else{
                            matriculaActual = matriculaNueva;
                        }
			hermod.addFolioToTurno(matriculaNueva, tid, evento.getUsuarioNeg());
			//Se ingresa la auditoria del Cambio
			hermod.addCambioMatriculaAuditoria(matriculaActual, matriculaNueva, tid, evento.getUsuarioNeg());

			//obtener de nuevo el turno para subirlo actualizado
			turno=hermod.getTurno(tid);
                /**
                * @Author Carlos Torres
                * @Mantis 13176
                * @Chaged
                */
                try {
                    auditoriaSir.borrarDatosTerminal(idWorkFlow);
                } catch (GeneralSIRException ex) {
                    Log.getInstance().error(ANCertificado.class,ex);
                }
		/*
                 * @author      :   Julio Alcázar Rivas
                 * @change      :   Se ajustan las excepciones
                 * Caso Mantis  :   02359
                 */
                }catch (SQLException e) {
                       Log.getInstance().error(ANCertificado.class,e);
			throw new ValidacionMatriculaCertificadoHTException("Error cambiando la matricula " + matriculaActual + " por la matrícula " + matriculaNueva +" : "+Utilidades.mensajeErrorOracle(e.getMessage(), "ORA-20001"));
                }
                catch(EventoException e){
                       throw new ValidacionMatriculaCertificadoHTException("Error cambiando la matricula " + matriculaActual + " por la matrícula " + matriculaNueva +": No cumple las condiciones de cambio de matricula para turno de certificado asociado a un turno de registro",e);
                }
                catch (Throwable e) {

			Log.getInstance().error(ANCertificado.class,e);
			throw new ValidacionMatriculaCertificadoHTException("Error cambiando la matricula " + matriculaActual + " por la matrícula " + matriculaNueva, e);
		}
		Folio folio = null;
		try {
			folio = forseti.getFolioByMatricula(matriculaNueva);
		} catch (Throwable e1) {

			e1.printStackTrace();
			throw new ValidacionMatriculaCertificadoHTException("Error obteniendo el nuevo folio con matricula " + matriculaNueva, e1);

		}

		return new EvnRespCambioMatricula(matriculaNueva, folio, turno);
	}

	/**
	 * Valida una matrícula a un turno de certificado de Antiguo sistema
	 * @param evento evento que tiene la información de la matrícula a validar
	 */
	private EvnRespValidacionMatricula validarMatriculaAntSistema(EvnValidacionMatricula evento) throws EventoException {
		TipoCertificadoPk tipo = new TipoCertificadoPk(evento.getTipoCertificado());
		List validaciones = new ArrayList();
		Validacion val = new Validacion();
		val.setIdValidacion("FOLIO_EXISTE");
		val.setNombre("Folio Existe");
		List matriculas = new ArrayList();
		String matricula = evento.getMatricula();
		matriculas.add(matricula);

		validaciones.add(val);

		try {
			if (forseti.validarMatriculas(validaciones, matriculas)) {

				long numAnota = this.getNumeroAnotacionesFolio(matricula);
				boolean esMayorExtension = forseti.mayorExtensionFolio(matricula);

				EvnRespValidacionMatricula eventoRespuesta = new EvnRespValidacionMatricula(matricula);

				eventoRespuesta.setNumeroAnotaciones(String.valueOf(numAnota));
				if (esMayorExtension) {
					eventoRespuesta.setMayorExtension(CFolio.MAYOR_EXTENSION);
				} else {
					eventoRespuesta.setMayorExtension(CFolio.NO_MAYOR_EXTENSION);
				}

				return eventoRespuesta;
			}
		} catch (Throwable t) {
			throw new ValidacionMatriculaCertificadoAntSisHTException("Error validando la matricula " + evento.getMatricula(), t);
		}
		return null;
	}

	private long getNumeroAnotacionesFolio(String matricula) throws EventoException {

		Folio folio;
		long numAnota = -1;

		try {
			//folio = forseti.getFolioByMatricula(matricula);
			//String zonaRegistral = folio.getZonaRegistral().getIdZonaRegistral();

			Log.getInstance().debug(ANCertificado.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado::getNumeroAnotacionesFolio]: matricula=" + matricula);
			//Log.getInstance().debug(ANCertificado.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado::getNumeroAnotacionesFolio]: zonaRegistral="+zonaRegistral);

			/*
			 Folio.ID fid = new Folio.ID();
			 fid.idMatricula = matricula;
			 fid.idZonaRegistral = zonaRegistral;
			*/
			numAnota = forseti.getCountAnotacionesFolio(matricula);
			Log.getInstance().debug(ANCertificado.class,"[gov.sir.core.negocio.acciones.certificado.ANCertificado]::getNumeroAnotacionesFolio: numero de anotaciones = " + numAnota);

		} catch (Throwable e) {
			Log.getInstance().error(ANCertificado.class,e);
			throw new EventoException(e.getMessage(), e);
		}

		return numAnota;

	}

	/**
	 * Retorna el ID de la primera matrícula
	 * asociada a la solicitud. Si es un certificado solo
	 * hay una matrícula.
	 * @param solicitud
	 * @return
	 */
	private FolioPk getFolio_ID(Solicitud solicitud) {
		List solFolio = solicitud.getSolicitudFolios();
		List matriculas = new Vector();
		Iterator itSolFolio = solFolio.iterator();

		FolioPk fid;
		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			fid = new FolioPk();
			fid.idMatricula = sol.getFolio().getIdMatricula();
			matriculas.add(fid);

		}
		//String idMatricula = (String)matriculas.get(0);
		fid = (FolioPk) matriculas.get(0);

		return fid;
	}

	/**
	 * Valida un turno
	 * @param evento evento que tiene la información de la matrícula a validar
	 */
	private EventoRespuesta validarTurno(EvnCertificado evento) throws EventoException {


		EvnRespCertificado evRespuesta = null;
		Turno turno = evento.getTurno();
		TurnoPk id = new TurnoPk();
		id.anio = turno.getAnio();
		id.idCirculo = turno.getIdCirculo();
		id.idProceso = turno.getIdProceso();
		id.idTurno = turno.getIdTurno();
		TipoCertificadoPk tipo = null;
		long numAnota =0;
		boolean esMayorExtension = false;
		List turnosValidacion = new ArrayList();

		Turno nTurno = new Turno();
		Folio folio = null;

		try {
			nTurno = hermod.getTurno(id);
			if (nTurno == null) {
				throw new ValidacionParametrosException("No se pudo obtener la informacion del turno");
			}

		} catch (HermodException e) {
			throw new ValidacionMatriculaCertificadoHTException("No se pudo obtener la informacion del turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new ValidacionParametrosException(e.getMessage(), e);
		}



       // REALIZAR LAS VALIDACIONES SOBRE LAS MATRICULAS ASOCIADAS CON EL TURNO ANTERIOR.

		if (nTurno != null)
		{
			if( nTurno.getAnulado() != null && nTurno.getAnulado().equals(CTurno.TURNO_ANULADO)){
				throw new ValidacionParametrosException(" El turno anterior con el ID: " + turno.getIdWorkflow() + " no puede ser ingresado como turno anterior.  El turno se encuentra ANULADO");
			}
			
			if (nTurno.getSolicitud()!=null)
			{
				List auxFolios = nTurno.getSolicitud().getSolicitudFolios();
				List auxMatriculas = new ArrayList();

                 //Determinar el tipo de Certificado.
				 List liquidaciones = nTurno.getSolicitud().getLiquidaciones();
				 if (liquidaciones ==null)
				 {
				 	throw new ValidacionParametrosException("El turno anterior no tiene liquidaciones asociadas");
				 }

				if (liquidaciones.size()==0)
				{
					throw new ValidacionParametrosException("El turno anterior no tiene liquidaciones asociadas");
				}

				 int size = liquidaciones.size();
				 Liquidacion auxLiquidacion = (Liquidacion)liquidaciones.get(size-1);

				 if (auxLiquidacion instanceof LiquidacionTurnoCertificado){
				 	LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado) auxLiquidacion;
				 	TipoCertificado tipoCert = liqCert.getTipoCertificado();
					tipo = new TipoCertificadoPk(tipoCert.getIdTipoCertificado());

				 }
				 else
				 {
					throw new ValidacionParametrosException("El turno anterior no es una solicitud de certificados");
				 }


				if (auxFolios !=null)
				{




					List validaciones = null;

					try {
						   validaciones = hermod.getValidacionesCertificado(tipo);
					}
					catch (Throwable t) {
						throw new ValidacionParametrosException("Error recuperando la lista de validaciones para " + "tipo de certificado " + tipo, t);
					}


					String matriculaId	= null;
					//Se crea una lista con todas las matrículas asociadas con el Turno.
					for (int j=0; j<auxFolios.size();j++)
					{
						SolicitudFolio solFolio = (SolicitudFolio) auxFolios.get(j);
						matriculaId = solFolio.getIdMatricula();
						if (matriculaId !=null)
						{
							auxMatriculas.add(matriculaId);
						}
					}


					//Se validan las matrículas
					try
					{
						 boolean resultadoValidacion = this.forseti.validarMatriculas(validaciones, auxMatriculas);
						 numAnota = this.getNumeroAnotacionesFolio(matriculaId);
						 esMayorExtension = forseti.mayorExtensionFolio(matriculaId);

					}
					catch (Throwable t) {
						throw new ValidacionMatriculaCertificadoHTException("Error validando la matricula ", t);


					}
				}

			}
		}


		//REALIZAR LAS VALIDACIONES SOBRE EL TURNO ANTERIOR RESPECTO A PROCESOS Y FASES.
		if (nTurno != null)
		{
			if( nTurno.getAnulado() != null && nTurno.getAnulado().equals(CTurno.TURNO_ANULADO)){
				throw new ValidacionParametrosException(" El turno anterior con el ID: " + turno.getIdWorkflow() + " no puede ser ingresado como turno anterior.  El turno se encuentra ANULADO");
			}
			
			if (nTurno.getSolicitud()!=null)
			{
                //1. SE INGRESO TURNO CON ID NO VALIDO.
				if (nTurno == null) {
					ValidacionParametrosException exception = new ValidacionParametrosException("No Se Encontró El Turno Con el ID: " + nTurno.getIdWorkflow());
					throw new ValidacionParametrosException("No Se Encontró El Turno Con el ID: " + nTurno.getIdWorkflow(), exception);

				}

				//2. SE INGRESO TURNO QUE NO CORRESPONDE AL PROCESO DE CERTIFICADOS.
				String proceso = String.valueOf(nTurno.getIdProceso());
				if(proceso.equals(CProceso.PROCESO_CERTIFICADOS))
				{
				}
				else
				{
					ValidacionParametrosException exception = new ValidacionParametrosException("El turno anterior no es del proceso de certificados");
					throw new ValidacionParametrosException("El turno anterior no es del proceso de certificados", exception);
				}


				//3. SE VALIDA QUE EL TURNO ANTERIOR NO SEA EXENTO
				SolicitudCertificado solAnt = (SolicitudCertificado)nTurno.getSolicitud();
				if(solAnt != null)
				{
					int lastLiq = (int)solAnt.getLastIdLiquidacion();
					LiquidacionTurnoCertificado liqAnt = (LiquidacionTurnoCertificado)solAnt.getLiquidaciones().get(lastLiq - 1);
					if (liqAnt !=null)
					{

						if (liqAnt.getTipoTarifa()!=null)
						{
							if(liqAnt.getTipoTarifa().equals(CTipoCertificado.TIPO_EXENTO_NOMBRE))
							{
								ValidacionParametrosException exception  = new ValidacionParametrosException("El turno anterior con el ID: " + nTurno.getIdWorkflow() + " es de tipo exento.");
								throw new ValidacionParametrosException("El turno anterior con el ID: " + nTurno.getIdWorkflow() + " es de tipo exento.", exception);
							}
						}

					}



				}

				//4. SE REALIZAN LAS VALIDACIONES SOBRE LAS FASES POR LAS CUALES DEBIO PASAR EL TURNO
				List turnoHists = nTurno.getHistorials();
				
				// 4.1. Se determina se es un turno manual. Los turnos manuales no
				// tienen historial, ya que son ingresados directamente como
				// finalizados
				//Se valida que no sea manual
				
				boolean turnoManual = false;
				Iterator itHis1 = turnoHists.iterator();
				while(itHis1.hasNext())
				{
					TurnoHistoria turHis = (TurnoHistoria) itHis1.next();
					if(turHis.getFase().equals(CFase.CER_TMAN_CIERRE)) {
						turnoManual = true;
					}
				}
					
				if(!turnoManual) {
					Iterator itHis = turnoHists.iterator();
					boolean devuelto = false;
                    boolean entrega = false;
	
					while(itHis.hasNext())
					{
						TurnoHistoria turHis = (TurnoHistoria) itHis.next();
                        
                        if(turHis.getFase().equals(CFase.CER_ENTREGAR)) {
                            entrega = true;
                            if(devuelto)
                                break;
                        }
	
						//4.1. EL TURNO PASO POR REVISION Y FUE RECHAZADO
						if (turHis.getFase().equals("ANT_REVISION_INICIAL") && 	turHis.getRespuesta().equals("NEGAR")) {
							devuelto = true;
                            if(entrega)
                                break;
						}
	
						//4.2. EL TURNO PASO POR REIMPRESION ESPECIAL Y FUE NEGADO
						//SEGUN NUEVO REQUERMIENTO SE ELIMINA ESTA VALIDACION
						/*
						else if (turHis.getFase().equals("CER_REIMPRESION_ESPECIAL") && turHis.getRespuesta().equals("NEGAR"))
						{
							devuelto = true;
						}
						*/
	
						//4.3. EL TURNO PASO POR NOTIFICAR MAS DOCUMENTOS Y LA RESPUESTA CONFIRMAR
						else if(turHis.getFase().equals("CER_NOTIFICAR_MAS_DOCS") && turHis.getRespuesta().equals("CONFIRMAR")) {
							devuelto = true;
                            if(entrega)
                                break;
						}
	
					}
	
					if(!devuelto || !entrega) {
						//throw new ValidacionParametrosException("El turno anterior con el ID: " + nTurno.getIdWorkflow() + " no fue devuelto.");
                        throw new ValidacionParametrosException(" El turno anterior con el ID: " + turno.getIdWorkflow() + " no puede ser ingresado como turno anterior.  Para que un turno pueda ser ingresado como turno anterior se requiere que haya sido entregado al público, y que, haya pasado por revisión y haya sido negado, o bien, haya pasado por notificar más documentos y se haya confirmado.");
					}
				}
			}


		//REALIZAR LAS VALIDACIONES PARA DETERMINAR SI EL TURNO ANTERIOR INGRESADO, YA ES TURNO ANTERIOR
		//DE OTRO TURNO.
		try {
			turnosValidacion = hermod.getTurnosSiguientes(nTurno.getIdWorkflow());
		}
		catch (Throwable e1) {
			throw new ValidacionParametrosException("Error obteniendo turnos siguientes del turno: " + nTurno.getIdWorkflow(),e1);

		}
		if (turnosValidacion == null)
		{
		}
		else if (turnosValidacion.size()==0)
		{
		}
		//El turno anterior tiene turnos siguientes.
		else
		{
			throw new ValidacionParametrosException("El turno: " + nTurno.getIdWorkflow() + " ya fue utilizado como turno anterior");
		}



		}


		if (String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_CERTIFICADOS)) {
		Solicitud solicitud = nTurno.getSolicitud();
		Ciudadano ciu = solicitud.getCiudadano();
		solicitud.setIdSolicitud(null);
		solicitud.setCiudadano(ciu);

		Liquidacion liq = (Liquidacion) solicitud.getLiquidaciones().get((int) solicitud.getLastIdLiquidacion() - 1);
		liq.setPago(null);
		liq.setSolicitud(solicitud);
		//liq.setSolicitud(null);

		evRespuesta = new EvnRespCertificado(liq);
		if (esMayorExtension) {
			evRespuesta.setMayorExtension(CFolio.MAYOR_EXTENSION);
		}
		else {
			evRespuesta.setMayorExtension(CFolio.NO_MAYOR_EXTENSION);
		}

		evRespuesta.setNumeroAnotaciones(String.valueOf(numAnota));


		}
		return evRespuesta;
	}

	/**
	 * Accion que se realiza cundo se niega la revision inicial en Antiguo Sistema.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta imprimirNotaDevolutiva(EvnCertificado evento) throws EventoException {
		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();

		Fase fase = evento.getFase();
		Estacion estacion = evento.getEstacion();
		String idCirculo = turno.getIdCirculo();
		//tabla.put(Processor.ESTACION, estacion.getEstacionId());
		tabla.put(Processor.CIRCULO, idCirculo);
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());

		Vector notasPublicas = this.getNotasPublicas(turno);

		String nombreCirculo="";
		String turnoId = turno.getIdWorkflow();

		String matricula = "";
		try
		{
			nombreCirculo = turno.getSolicitud().getCirculo().getNombre();

			List solicitudesFolio = turno.getSolicitud().getSolicitudFolios();
			SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudesFolio.get(solicitudesFolio.size()-1);
			matricula=solicitudFolio.getIdMatricula();


		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}

		String fechaImpresion= this.getFechaImpresion();
		ImprimibleNotaDevolutiva impNota = new ImprimibleNotaDevolutiva(notasPublicas,nombreCirculo,turnoId,matricula, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
		impNota.setPrintWatermarkEnabled(true);
		tabla.put(Processor.IMPRIMIBLE, impNota);
		try
		{

             //INGRESO DE INTENTOS DE IMPRESION
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null)
			{
			     tabla.put(Processor.INTENTOS, intentosImpresion);
			}
			else
			{
			    tabla.put(Processor.INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null)
			{
			     tabla.put(Processor.ESPERA, esperaImpresion);
			}
			else
			{
				tabla.put(Processor.ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
			}

			if (turno.getSolicitud() instanceof SolicitudCertificado){
				 hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,evento.getUsuarioNec());
				 imprimir(impNota,tabla,turno.getIdCirculo());
			 }
			
		} catch (HermodException e) {
			throw new ImpresionNoEfectuadaException("No se pudo avanzar el turno", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return null;

	}

	/**
	 * Retorna un vectos con la lista de notas publicas
	 * asociadas al turno.
	 * @param turno
	 * @return
	 */
	private Vector getNotasPublicas(Turno turno) {
		Vector notasPublicas = new Vector();
		List notas = turno.getNotas();

		for (int i = 0; i < notas.size(); i++) {
			Nota nota = (Nota) notas.get(i);
			String visibilidad = nota.getTiponota().getVisibilidad();
			if (visibilidad.equalsIgnoreCase(CNota_Visibilidad.PUBLICO)) {
				notasPublicas.add(nota);
			}

		}
		return notasPublicas;
	}

	/**
	 * Método que permite imprimir la hoja de ruta luego que se ha creado.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta imprimirHojaRuta(EvnCertificado evento) throws EventoException {

		Folio folio = evento.getFolio();
		CirculoPk idCirc = new CirculoPk();
		idCirc.idCirculo = folio.getZonaRegistral().getCirculo().getIdCirculo();
		List deptos = new ArrayList();

		try {
			deptos = forseti.getDepartamentos(idCirc);
		} catch (ForsetiException e) {
			throw new ConsultaDepartamentosNoEfectuadaException(e.getMessage());
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		Iterator iter = deptos.iterator();
		while (iter.hasNext()) {
			Departamento depto = (Departamento) iter.next();
			if(depto.getIdDepartamento().equals(folio.getZonaRegistral().getVereda().getIdDepartamento())){
				folio.getZonaRegistral().getVereda().getMunicipio().getDepartamento().setNombre(depto.getNombre());
				Iterator iterMunc = depto.getMunicipios().iterator();
				while(iterMunc.hasNext()){
					Municipio munic = (Municipio)iterMunc.next();
					if(munic.getIdMunicipio().equals(folio.getZonaRegistral().getVereda().getIdMunicipio())){
						folio.getZonaRegistral().getVereda().getMunicipio().setNombre(munic.getNombre());
						Iterator iterVereda = munic.getVeredas().iterator();
						while(iterVereda.hasNext()){
							Vereda vereda = (Vereda)iterVereda.next();
							if(vereda.getIdVereda().equals(folio.getZonaRegistral().getVereda().getIdVereda())){
								folio.getZonaRegistral().getVereda().setNombre(vereda.getNombre());
							}

						}
					}
				}
			}
		}

		//CONSTANTES PARA LA IMPRESIÓN.
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";

		//COLOCAR LOS PARAMETROS INICIALES
		Hashtable parametros = new Hashtable();

		//OBTENER INFORMACIÓN A PARTIR DEL TURNO
		Turno turno = evento.getTurno();
		String UID = evento.getUID();


		//IMPRIMIR LOS CERTIFICADOS SOLICITADOS
		try {

                //INGRESO DE INTENTOS DE IMPRESION
				String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
				if (intentosImpresion != null)
				{
				    parametros.put(INTENTOS, intentosImpresion);
				}
				else
				{
					intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
					parametros.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
				}

				//INGRESO TIEMPO DE ESPERA IMPRESION
				String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
				if (intentosImpresion != null)
				{
					parametros.put(ESPERA, esperaImpresion);
				}
				else
				{
					esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
					parametros.put(ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
				}

			if ((UID == null) || (UID.length() == 0) || (intentosImpresion == null) || (intentosImpresion.length() == 0) || (esperaImpresion == null) || (esperaImpresion.length() == 0)) {
				throw new EventoException("Se requiere especificar el circulo, el número de intentos y la espera.");
			}

			if (folio == null) {
				throw new EventoException("Se requiere especificar la huja de ruta a imprimir");
			}

			if (folio == null) {
				throw new EventoException("Se requiere especificar el turno");
			}
			gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioNec();
			String fechaImpresion= this.getFechaImpresion();
			//obtener textos base de los separadores
			String tbase1 ="";
			String tbase2 = "";
			List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
			for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
				OPLookupCodes op = (OPLookupCodes) it.next();
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
					tbase1= op.getValor();
				}
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
					tbase2 = op.getValor();
				}
			}
			ImprimibleHojaDeRuta imprimibleHojaRuta = new ImprimibleHojaDeRuta(folio, turno, usuarioNeg, fechaImpresion,CTipoImprimible.HOJA_RUTA,tbase1, tbase2);

			parametros = this.imprimir(imprimibleHojaRuta, parametros, UID);

		} catch (Throwable t2) {
			t2.printStackTrace();
			throw new EventoException(t2.getMessage());
		}

		return new EvnRespCertificado(EvnRespCertificado.IMPRIMIR_HOJA_RUTA);
	}

	/**
	 * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	 * con los parametros asignados.
	 * @param turno
	 * @param imprimible
	 * @param parametros
	 * @return
	 * @throws EventoException
	 */
	private Hashtable imprimir(ImprimibleBase imprimible, Hashtable parametros, String UID) {

		//CONSTANTES PARA LA IMPRESIÓN.
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";

		Bundle b = new Bundle(imprimible);

		String numIntentos = (String) parametros.get(INTENTOS);
		String espera = (String) parametros.get(ESPERA);

		Integer intentosInt = new Integer(numIntentos);
		int intentos = intentosInt.intValue();
		Integer esperaInt = new Integer(espera);
		int esperado = esperaInt.intValue();

		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(UID, b, intentos, esperado);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return parametros;
	}


	/**
	 * Método que va a llamar los servicios de forseti necesarios para
	 * hacer la consulta de un folio por el idMatricula. Se ejecuta cuándo se quiere consultar un folio
	 * para copiar su complementación.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespCertificado consultarFolio(EvnCertificado evento) throws EventoException	{
		Folio folio = evento.getFolio();

		try {
			if(folio!=null && folio.getIdMatricula()!=null){
				folio = this.forseti.getFolioByMatricula(folio.getIdMatricula());
			}
		}catch(ForsetiException fe) {
			List l = fe.getErrores();
			throw new EventoException(fe.getMessage(),fe);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		return new EvnRespCertificado(EvnRespCertificado.CONSULTA_FOLIO_COMPLEMENTACION, folio);

	}

	/**
	 * Método que va a llamar los servicios de forseti necesarios para
	 * hacer la consulta de un folio por el idMatricula. Se ejecuta cuándo se quiere consultar un folio
	 * para copiar su complementación.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespCertificado consultarPlantillaPertenencia(EvnCertificado evento) throws EventoException	{

		PlantillaPertenencia plantilla = null;
		Folio folio = null;
		try {
			plantilla = this.forseti.getPlantillaPertenenciaByRespuesta(evento.getRespuestaWF());
			Folio folioTemp= evento.getFolio();
			if(folioTemp!=null && folioTemp.getIdMatricula()!=null){
				FolioPk idFolio = new FolioPk();
				idFolio.idMatricula = folioTemp.getIdMatricula();
				folio = this.forseti.getFolioByID(idFolio);
				//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
				if(folio!= null && (folio.getAnotaciones()==null || folio.getAnotaciones().isEmpty())){
					folio.setAnotaciones(forseti.getAnotacionesFolio(idFolio));
				}
			}
			
		}catch(ForsetiException fe) {
			List l = fe.getErrores();
			throw new EventoException(fe.getMessage(),fe);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		EvnRespCertificado resp = new EvnRespCertificado(EvnRespCertificado.CONSULTAR_PLANTILLA_PERTENENCIA, plantilla);
		if(folio!=null){
			resp.setFolio(folio);
		}
		
		return resp;		

	}



	/**
	* Este metodo hace el llamado al negocio para que se consulte el secuencial de recibos.
	* @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	*/
	private EventoRespuesta consultarSecuencial(EvnCertificado evento) throws EventoException {

		EvnRespCertificado evRespuesta = null;
		List listaNotasInformativas;
		long valorSecuencial;
		
		long idProceso = Long.valueOf(CProceso.PROCESO_CERTIFICADOS).longValue();
		try {
			gov.sir.core.negocio.modelo.Usuario user = null;
			try {
				user = evento.getUsuarioNec();
			} catch (Exception ex) {
				Log.getInstance().error(ANCertificado.class,"No se encontro usuario del evento");
			}
			Estacion estacion = evento.getEstacion();
			EstacionReciboPk estacionRecibo = new EstacionReciboPk();
			estacionRecibo.idEstacion = estacion.getEstacionId();
			valorSecuencial = hermod.getNextNumeroReciboSinAvanzar(estacionRecibo,user, idProceso);

			//Consultar el listado de Notas Informativas asociadas con el proceso.
			Proceso proceso = new Proceso();
			proceso.setIdProceso(1);
			ProcesoPk procesoId = new ProcesoPk();
			procesoId.idProceso = proceso.getIdProceso();
			Fase fase = evento.getFase();
			String nombreFase = null;

			if (fase != null)
			{
				nombreFase = fase.getNombre();
			}
			listaNotasInformativas = hermod.getTiposNotaProceso(procesoId,nombreFase);

		} catch (HermodException e) {
			throw new EventoException("No se pudo obtener el secuencial de recibos.", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespCertificado(valorSecuencial,listaNotasInformativas);
		return evRespuesta;
	}
	
	/**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return
	 */
	protected String getFechaImpresion()
	{

		Calendar c = Calendar.getInstance();
		int dia, ano, hora;
		String min, seg, mes;

		dia = c.get(Calendar.DAY_OF_MONTH);
		mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
		ano = c.get(Calendar.YEAR);

		hora = c.get(Calendar.HOUR_OF_DAY);
		if (hora > 12)
			hora -= 12;

		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));

		String fechaImp =
			"Impreso el "
				+ dia
				+ " de "
				+ mes
				+ " de "
				+ ano
				+ " a las "
				+ formato(hora)
				+ ":"
				+ min
				+ ":"
				+ seg
				+ " "
				+ DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

		return fechaImp;
	}
	
	/**
	 * Metodo que retorna un numero con un "0" antes en caso de ser menor
	 * que 10.
	 * @param i el numero.
	 * @return
	 */
	protected String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}
		return (new Integer(i)).toString();
	}
	
	/**
     * Imprimir el certificado asociado al turno.
     * @param turno el turno
     * @param parametros tabla de Hashing con los parametros de impresion (además de los del WorkFlow)
     * @return la tabla de hashing de parametros adicionando un registro dependiendo de si la impresion fue
     * o no exitosa.
     * @throws Throwable
     */
	private Hashtable imprimirCertificado(Turno turno, gov.sir.core.negocio.modelo.Usuario usuarioSIR, String UID, boolean IngresarTurnoHistoria ) throws Throwable
	{
		SolicitudCertificado solCerti = (SolicitudCertificado)turno.getSolicitud();
		List listaFolios = solCerti.getSolicitudFolios();
		int numCopias=solCerti.getNumeroCertificados();
		Hashtable parametros = new Hashtable();
		
		parametros.put("USUARIO_SIR", usuarioSIR.getUsername()  );
		parametros.put("CIRCULO_O_UID", UID  );
		
		Log.getInstance().debug(ANCertificado.class,"\n*******************************************************");
		Log.getInstance().debug(ANCertificado.class,"(ANTES METODO IMPRESION CERTIFICADO)");
		Log.getInstance().debug(ANCertificado.class,"\n*******************************************************\n");
		
	    //Obtener los parámetros.
        //String notificationId = (String) parametros.get(Processor.NOT_ID);
        String intentosImpresion;
        String esperaImpresion;

		//INGRESO DE INTENTOS DE IMPRESION
		try{

			intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null){
				 parametros.put(Processor.INTENTOS, intentosImpresion);
			}
			else{
				 intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
				 parametros.put(Processor.INTENTOS, intentosImpresion);
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (esperaImpresion != null){
				parametros.put(Processor.ESPERA, esperaImpresion);
			}
			else{
				esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
				parametros.put(Processor.ESPERA, esperaImpresion);
			}
		}
		catch (Throwable t){
			intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
			parametros.put(Processor.INTENTOS, intentosImpresion);
			esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
			parametros.put(Processor.ESPERA, esperaImpresion);
		}		
		
               
		/*
		 * No hay un folio asociado y no hay nada que imprimir
		 * (Por ejemplo certificado de pertenencia sin matricula asociada).
		 */
		if (listaFolios.size()<1)
		  return parametros;

		SolicitudFolio solFolio = (SolicitudFolio)listaFolios.get(listaFolios.size()-1);
		//List foliosDerivados = new Vector();
		Folio folio=null;

		List liquidaciones = solCerti.getLiquidaciones();
		LiquidacionTurnoCertificado liquidacion = (LiquidacionTurnoCertificado)liquidaciones.get(liquidaciones.size()-1);
		String tipoTarifa =liquidacion.getTipoTarifa();

		folio=forseti.getFolioByMatricula( solFolio.getIdMatricula() );
// se debe consultar todas las anotaciones; el folio se usa para certificado
		CirculoPk cid = new CirculoPk();
		cid.idCirculo = turno.getIdCirculo();

		FolioPk fid = new FolioPk();
		fid.idMatricula = folio.getIdMatricula();

		List anotaciones = folio.getAnotaciones();
		if (anotaciones == null || anotaciones.isEmpty())
		{
			anotaciones = forseti.getAnotacionesFolio(fid);
			folio.setAnotaciones(anotaciones);
		}
		
		List padres=forseti.getFoliosPadre(fid);
		List hijos=forseti.getFolioHijosEnAnotacionesConDireccion(fid);
                /**
                * @author: David Panesso
                * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                * Nuevo listado de folios derivados
                **/
                List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

		String usuario = (String)parametros.get("USUARIO_SIR");
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = new gov.sir.core.negocio.modelo.Usuario();
		usuarioNeg.setUsername(usuario);
		String fechaImpresion= this.getFechaImpresion();
		String tipoImprimible=CTipoImprimible.CERTIFICADO_INMEDIATO;
                //--------*-*-*-*-*-*-
                
                if (tipoTarifa != null) {
                    if (tipoTarifa.equals(CHermod.EXENTO)) {
                        tipoImprimible = CTipoImprimible.CERTIFICADO_EXENTO;
                    }
                }
                
		if (!solCerti.getSolicitudesPadres().isEmpty()){
			if (((SolicitudAsociada)solCerti.getSolicitudesPadres().get(0)).getSolicitudPadre() instanceof SolicitudCertificadoMasivo){
				tipoImprimible=CTipoImprimible.CERTIFICADOS_MASIVOS;
			}else{
				tipoImprimible=CTipoImprimible.CERTIFICADO_ASOCIADO;	
			}
			
		}
		
		if (forseti.mayorExtensionFolio(folio.getIdMatricula())){
			tipoImprimible=CTipoImprimible.CERTIFICADO_EXTENSO;
		}
		
		//obtener textos base de los separadores
		String tbase1 ="";
		String tbase2 = "";
		List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
		for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
			OPLookupCodes op = (OPLookupCodes) it.next();
			if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
				tbase1= op.getValor();
			}
			if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
				tbase2 = op.getValor();
			}
		}
                
                List turnoTramite = null;
                boolean isCertificadoTramite = false;
                boolean isCertificadoEspecial = false;
		boolean isCertificadoActuacion = false;
                
                if (forseti.enTramiteFolio(folio.getIdMatricula())) {
                        turnoTramite = forseti.getTurnosTramiteFolio((folio.getIdMatricula()));
                        isCertificadoTramite = true;
                        isCertificadoEspecial = true;
                }
                if (forseti.bloqueadoFolio(folio.getIdMatricula())) {
                        turnoTramite = forseti.getTurnosTramiteFolio((folio.getIdMatricula()));
                        isCertificadoTramite = true;
                        isCertificadoEspecial = true;
                }
                if (forseti.isActuacionAdministrativa(folio.getIdMatricula())){
                        isCertificadoActuacion = true;
                        isCertificadoEspecial = true;
                }
                
                

                
		ImprimibleCertificado imprimibleCertificado = new ImprimibleCertificado(folio,turno,padres,hijos, foliosDerivadoHijos, fechaImpresion,tipoImprimible, tbase1, tbase2);
                /**
                 * @author     : Carlos Torres
                 * @change     : Set propiedad NIS en el imprimible.
                 * Caso Mantis : 0006493: Acta - Requerimiento No 027 - Caracteristicas Impresión certificados
                 */
                String text = turno.getIdWorkflow() +"/"+ turno.getSolicitud().getIdSolicitud();
                byte [] key  = new byte [8];
                key[0] = 5;
                imprimibleCertificado.setNis(Encriptador.encriptar(text, key, "DES"));
                PrintJobsProperties prop = PrintJobsProperties.getInstancia();
                imprimibleCertificado.setIpverificacion(prop.getProperty(PrintJobsProperties.VERIFICACION_CERTIFICADO_IP));
		imprimibleCertificado.setPrintWatermarkEnabled(true);
		imprimibleCertificado.setUsuario(usuarioSIR);
                imprimibleCertificado.setIsCertificadoEspecial(isCertificadoEspecial);
                imprimibleCertificado.setIsCertificadoTramite(isCertificadoTramite);
                imprimibleCertificado.setCertificadoActuacion(isCertificadoActuacion);
                imprimibleCertificado.setTurnoTramite(turnoTramite);
		 /**
                 * @author     : Carlos Torres
                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                 */
                Traslado traslado = hermod.getFolioDestinoTraslado(folio.getIdMatricula());
                imprimibleCertificado.setInfoTraslado(traslado);
		
		if (IngresarTurnoHistoria) {
			hermod.updateTurnoReimpresionCertificado(turno, usuarioSIR, folio);
		}

		String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

		FirmaRegistrador firmaRegistrador = null;

		String sNombre = "";
		String archivo = "";
		String cargoToPrint = "";
		String rutaFisica = null;

	    try{
			firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);

			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}
			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}
			if(firmaRegistrador==null){
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}

			rutaFisica =  hermod.getPathFirmasRegistradores();

			sNombre = firmaRegistrador.getNombreRegistrador();
			archivo = firmaRegistrador.getIdArchivo();

			//Se recupera el verdadero cargo para definir si es ENCARGADO o
			//no lo es.
			cargo = firmaRegistrador.getCargoRegistrador();

			//Se saca el valor del cargo para imprimirlo en el certificado
			List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

			cargoToPrint = "";
			OPLookupCodes lookUp;
			for(Iterator it = cargos.iterator(); it.hasNext();){
				lookUp = (OPLookupCodes) it.next();
				if(lookUp.getCodigo().equals(cargo)){
					cargoToPrint = lookUp.getValor();
				}
			}
	    }
	    catch(Throwable t){
	    	t.printStackTrace();
	    }

		if (sNombre==null)
		  sNombre="";


		imprimibleCertificado.setCargoRegistrador(cargoToPrint);
		imprimibleCertificado.setNombreRegistrador(sNombre);


		if(rutaFisica!=null){
			BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);

			byte pixeles[]=null;
			try{
				pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
			}
			catch (Throwable e1) {
				e1.printStackTrace();
			}
		    imprimibleCertificado.setPixelesImagenFirmaRegistrador(pixeles);
		}

		Log.getInstance().debug(ANCertificado.class,"Tipo de Tarifa = "+tipoTarifa);
                if (tipoTarifa != null) {
                    if (tipoTarifa.equals(CHermod.EXENTO)) {
                        imprimibleCertificado.setExento(true);
                        String textoExento = hermod.getTextoExento();
                        imprimibleCertificado.setTextoExento(textoExento);
                    } else {
                        imprimibleCertificado.setExento(false);
                    }
                }
            imprimibleCertificado.setChangetextforregistrador(true);
   	    parametros = this.imprimir(turno,imprimibleCertificado,parametros,numCopias);

   	    String resultado =(String)parametros.get(Processor.RESULT);
   	    boolean okImpresion = true;
   	    if (resultado!=null){
   	    	if ( resultado.equals("ERROR")){
				okImpresion = false;
   	    	}
   	    }

   	    if (okImpresion){
   	    	//actualizar el número de impresiones en el turno
			int numImpresiones = solCerti.getNumImpresiones();
   	    	solCerti.setNumImpresiones(numImpresiones+1);
   	    	hermod.updateSolicitudCertificado(solCerti);
   	    }
   	    
		Log.getInstance().debug(ANCertificado.class,"\n*******************************************************");
		Log.getInstance().debug(ANCertificado.class,"(DESPUES METODO IMPRESION CERTIFICADO)");
		Log.getInstance().debug(ANCertificado.class,"\n*******************************************************\n");
   	    
		return parametros;
	}


	/**
	 * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	 * con los parametros asignados.
	 * @param turno es el Turno con el que se creo la solicitud.
	 * @param imprimible es la representacion del documento que se desea imprimir
	 * (Certificado, Oficio de Pertenencia, Nota Devolutiva, Formulario de Calificacion,
	 *  Formulario de Correccion, etc).
	 * @param parametros tabla de Hashing con los parametros de impresión (además de los parametros asociados al
	 *  WorkFlow).
	 * @param numCopias es el número de copias que se desea imprimir.
	 * @return
	 */
	private Hashtable imprimir(Turno turno, ImprimibleBase imprimible, Hashtable parametros, int numCopias) {
		String circulo = turno.getIdCirculo();
		//String impresora = (String)parametros.get(IMPRESORA);
		String imp = (String) parametros.get("CIRCULO_O_UID");

		//Opción para imprimir en local o en el applet administrativo de impresión
		if (imp != null) {
			circulo = imp;
		}

		//Bundle b = new Bundle(imprimible,impresora);
		Bundle b = new Bundle(imprimible);

		String numIntentos = (String) parametros.get(Processor.INTENTOS);
		String espera = (String) parametros.get(Processor.ESPERA);

		Integer intentosInt = new Integer(numIntentos);
		int intentos = intentosInt.intValue();
		Integer esperaInt = new Integer(espera);
		int esperado = esperaInt.intValue();
		boolean resultadoImpresion = false;

		//settear el numero de impresiones
		b.setNumeroCopias(numCopias);

		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos (Imprime 1 copia)
		try {
			printJobs.enviar(circulo, b, intentos, esperado);
			//si imprime exitosamente sale del ciclo.
			resultadoImpresion = true;
		} catch (Throwable t) {
			t.printStackTrace();
			resultadoImpresion = false;
		}

		if (resultadoImpresion) {
			parametros.put(Processor.RESULT, "CONFIRMAR");
		} else {
			parametros.put(Processor.RESULT, "ERROR");
		}
		return parametros;
	}
	
	public static BufferedImage getImage(String path, String nombreArchivo){
		String nombreCompleto = getNombreCompleto(path,nombreArchivo);
		BufferedImage buf = null;

		try{
			File file = new File(nombreCompleto);
			buf = ImageIO.read(file);
		}
		catch (IOException e){
			Log.getInstance().error(ANCertificado.class,e);
			Log.getInstance().error(ANCertificado.class,"Error imprimiendo el gráfico en la ruta: "+nombreCompleto);
		}

		return buf;
	}
	
	public static String getNombreCompleto(String path, String nombreArchivo){
		String nombreCompleto=null;

		if (!path.trim().equals(CHermod.CADENA_VACIA))
		  nombreCompleto = path + nombreArchivo;
		else
		  nombreCompleto = nombreArchivo;

	  return nombreCompleto;
	}
	
	/*public TurnoHistoria getTurnoHistoriaMayor(List turnoHists, long lastIdHistoria) {
		TurnoHistoria rta = null;
		for (int i = 0; i < turnoHists.size(); i ++) {
			TurnoHistoria temp = (TurnoHistoria) turnoHists.get(i);
			if (temp.getIdTurnoHistoria().equals(String.valueOf(lastIdHistoria))) {
				rta = temp;
			}
		}
		return rta;
	}*/
	
	/**
	 * @param evento
	 * @return evento respuesta
	 * @throws EventoException
	 */
	private EvnRespCertificado validarMatriculaMig(EvnCertificado evento) throws EventoException {
		String matricula = evento.getMatricula();

		try {
			Folio folio = new Folio();
			folio.setIdMatricula(matricula);
			
			//List validaciones = hermod.getValidacionesRegistro();
			if (hermod.isFolioValidoSirMig(folio)){
				EvnRespCertificado evn = new EvnRespCertificado(matricula, EvnRespCertificado.VALIDACION_MATRICULA_MIG);
				evn.setTipoEvento(EvnRespCertificado.VALIDACION_MATRICULA_MIG);
				return evn; 	
			} 
			
			throw new MatriculaInvalidaRegistroException("La matrícula no es valida para el proceso de Migración");
		} catch (MatriculaInvalidaRegistroException e) {
			throw e;
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Error en el servicio para validar la matrícula:" + ep.toString());

			String errores = "";
			Iterator it = ((List) e.getHashErrores().get(matricula)).iterator();

			while (it.hasNext()) {
				errores = errores + "\n" + it.next();
			}

			throw new MatriculaInvalidaRegistroException("Resultado de la validación de la matrícula " + matricula + ": " + errores);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new MatriculaInvalidaRegistroException("Se produjo un error validando la matrícula", e);
		}
	}
	
	/** Graba la complementacion de un folio  
	 * @param evento
	 * @return EvnRespuesta
	 */
	private EventoRespuesta actualizarFolioComplementacion(EvnCertificado evento) throws EventoException {
		gov.sir.core.negocio.modelo.Usuario usuarioNeg=evento.getUsuarioNec();
		Folio nuevo=new Folio();
		
		Turno turno = evento.getTurno();
		TurnoPk turnoPk = new TurnoPk();
		turnoPk.anio = turno.getAnio();
		turnoPk.idCirculo = turno.getIdCirculo();
		turnoPk.idProceso = turno.getIdProceso();
		turnoPk.idTurno = turno.getIdTurno();
		
		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		nuevo.setComplementacion(evento.getFolio().getComplementacion());
		try {
			forseti.updateFolio(nuevo, usuarioNeg,null,false);
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCertificado.class, "No se pudieron grabar las direcciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
		}
		return new EvnRespCertificado(EvnCertificado.ACTUALIZAR_TRADICION_FOLIO,nuevo);
	}
	
	/** Graba la complementacion de un folio  
	 * @param evento
	 * @return EvnRespuesta
	 */
	private EventoRespuesta hacerDefinitivaComplementacion(EvnCertificado evento) throws EventoException {
		gov.sir.core.negocio.modelo.Usuario usuarioNeg=evento.getUsuarioNec();
		Folio nuevo=new Folio();
		
		Turno turno = evento.getTurno();
		TurnoPk turnoPk = new TurnoPk();
		turnoPk.anio = turno.getAnio();
		turnoPk.idCirculo = turno.getIdCirculo();
		turnoPk.idProceso = turno.getIdProceso();
		turnoPk.idTurno = turno.getIdTurno();
		
		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		nuevo.setComplementacion(evento.getFolio().getComplementacion());
		try {
                        /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                java.util.Map infoUsuario = new java.util.HashMap();
                                if(evento.getInfoUsuario() !=null){
                                    infoUsuario.put("user",evento.getInfoUsuario().getUser());
                                    infoUsuario.put("host",evento.getInfoUsuario().getHost());
                                    infoUsuario.put("logonTime",evento.getInfoUsuario().getLogonTime());
                                    infoUsuario.put("address",evento.getInfoUsuario().getAddress());
                                    infoUsuario.put("idTurno",turno.getIdWorkflow());
                                }
                                co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR auditoria = new co.com.iridium.generalSIR.negocio.auditoria.AuditoriaSIR();
                                auditoria.guardarDatosTerminal(infoUsuario);
                                
			forseti.updateFolio(nuevo, usuarioNeg,null,false);
			forseti.hacerDefinitivoFolio(nuevo, usuarioNeg, turnoPk, true);
			forseti.desbloquearFolio(nuevo);
                        /**
                                * @Author Carlos Torres
                                * @Mantis 13176
                                * @Chaged 
                                */
                                auditoria.borrarDatosTerminal(turno.getIdWorkflow());
                        
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCertificado.class, "No se pudieron hacer definitivos los cambios:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron hacer definitivos los cambios", e1);
		}
		return new EvnRespCertificado(EvnCertificado.ACTUALIZAR_TRADICION_FOLIO,nuevo);
	}
	
	private EvnRespCertificado consultarPlantillaAmpliacion(EvnCertificado evento) throws EventoException	{

		PlantillaPertenencia plantilla = null;
		Folio folio = null;
		try {
			plantilla = this.forseti.getPlantillaPertenenciaByRespuesta(evento.getRespuestaWF());
			Folio folioTemp= evento.getFolio();
			if(folioTemp!=null && folioTemp.getIdMatricula()!=null){
				FolioPk idFolio = new FolioPk();
				idFolio.idMatricula = folioTemp.getIdMatricula();
				folio = this.forseti.getFolioByID(idFolio);
				//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
				if(folio!= null && (folio.getAnotaciones()==null || folio.getAnotaciones().isEmpty())){
					folio.setAnotaciones(forseti.getAnotacionesFolio(idFolio));
				}
			}
			
		}catch(ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new EventoException(fe.getMessage(),fe);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCertificado.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		EvnRespCertificado resp = new EvnRespCertificado(EvnRespCertificado.CER_AMPLIACION_TRADICION, plantilla);
		if(folio!=null){
			resp.setFolio(folio);
		}
		
		return resp;		

	}
	
	private void deshacerCambiosAmpliacionTradicion(EvnCertificado evento) throws EventoException{
		Turno turno = evento.getTurno();
		
		Hashtable tabla = new Hashtable();

		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
		
		try{
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tabla, evento.getTurno());
		}catch (Throwable t) {
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}
		
		Folio folio = ((SolicitudFolio)turno.getSolicitud().getSolicitudFolios().get(0)).getFolio();
		gov.sir.core.negocio.modelo.Usuario usuario = evento.getUsuarioNec();
		try{
			forseti.deshacerCambiosFolio( folio, usuario);
		}catch(ForsetiException e){
			Log.getInstance().error(ANCertificado.class, "Error deshaciendo la ampliacion de la tradicion" + e.getMessage());
			throw new EventoException("Error deshaciendo la ampliacion de la tradicion",e);
		}catch(Throwable t){
			Log.getInstance().error(ANCertificado.class, "Error deshaciendo la ampliacion de la tradicion" + t.getMessage());
			throw new EventoException("Error deshaciendo la ampliacion de la tradicion",t);
		}
	}
       
}
