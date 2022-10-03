package gov.sir.core.negocio.acciones.fotocopia;

import java.util.Iterator;
import java.util.List;
import java.util.Hashtable;
import gov.sir.core.eventos.fotocopia.EvnVerificarDocumentosFotocopia;
import gov.sir.core.negocio.modelo.Fase;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.eventos.fotocopia.EvnFotocopia;
import gov.sir.core.eventos.fotocopia.EvnRespFotocopia;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.PagoInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.InicioProcesos;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoFotocopia;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFotocopia;
import gov.sir.core.negocio.modelo.TextoImprimible;
import gov.sir.core.negocio.modelo.TextoImprimiblePk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTextoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.print.common.Bundle;
import java.util.Calendar;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.eventos.fotocopia.EvnFotocopiaCrear;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaCrearSolicitud;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaLiquidarSolicitud;
import gov.sir.core.eventos.fotocopia.EvnLiquidarFotocopiaNegar;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import java.util.Vector;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFotocopiaNoExpedirSolicitudFotocopiasAlLiquidar;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.util.DateFormatUtil;

/**
 * @author dlopez, ahurtado
 */
public class ANFotocopia extends SoporteAccionNegocio{


    /**
     * PrintService
     * @since 2005/05/25
     */
          private PrintJobsInterface printJobs;



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
	 *Constructor de la clase ANFotocopia
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANFotocopia() throws EventoException {

		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null){
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		}
		catch (ServiceLocatorException e){
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

		if (hermod == null){
			throw new ServicioNoEncontradoException(
				"Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null){
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
		} catch (ServiceLocatorException e){
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
		}

		if (forseti == null){
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}


                // ------------- PrintingService Activation
                try {
                    printJobs = (PrintJobsInterface) service.getServicio(
                            "gov.sir.print");

                    if (printJobs == null) {
                        throw new ServicioNoEncontradoException(
                            "Servicio PrintJobs no encontrado");
                    }
                } catch (ServiceLocatorException e) {
                    throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJobs",
                        e);
                }

                if (printJobs == null) {
                    throw new ServicioNoEncontradoException(
                        "Servicio PrintJobs no encontrado");
                }
              // ------------- ----------------------------------

	}




	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnFotocopia evento = (EvnFotocopia) e;

		// forward
		if( ( null== evento )
      ||  ( null == evento.getTipoEvento() ) ) {
                   return null;
		}
		if( EvnFotocopia.CREAR_SOLICITUD.equals( evento.getTipoEvento() ) ){
                   return crearSolicitudFotocopia( evento );
		}
		if( EvnFotocopia.VERIFICAR_DOCUMENTOS_ASOCIADOS.equals( evento.getTipoEvento() ) ){
			  return actualizarDocumentosAsociados( evento );
		}
		if( EvnFotocopia.LIQUIDACION_FOTOCOPIAS.equals( evento.getTipoEvento() ) ){
		// modificado, manejado en ANPagofotocopia
			return liquidarSolicitudFotocopia( evento );
		}
		if( EvnFotocopia.NOEXPEDIRSOLICITUDFOTOCOPIAS_ALLIQUIDAR_EVENT.equals( evento.getTipoEvento() ) ) {
			return doNoExpedirSolicitudFotocopiasAlLiquidar( evento );
		}
		if( EvnFotocopia.DOCUMENTOSASOCIADOSADD_EVENT.equals( evento.getTipoEvento() ) ){
				return doAddDocumentoAsociado( evento  );
		}
		if( EvnFotocopia.DOCUMENTOSASOCIADOSDEL_EVENT.equals( evento.getTipoEvento() ) ){
				return doDelDocumentoAsociado( evento );
		}

		return null;
	}


	/**
	 * Simple evento de propagacion a la capa de negocio.
	 * Más adelante se puede usar para valizaciones en la capa de negocio.
	 */
	private EvnRespFotocopia
	doWrapEvent( EvnFotocopia evento, String eventType )
	throws EventoException {

		EvnRespFotocopia eventoRespuesta;
		eventoRespuesta = new EvnRespFotocopia( evento.getUsuario(), evento.getDocumentoFotocopia(), eventType );
		return eventoRespuesta;

	}

	/**
	 * Llamado al insertar un documento dentro de la
	 * creacion de una solicitud.
	 */
	private EvnRespFotocopia
	doAddDocumentoAsociado( EvnFotocopia evento )
	throws EventoException {
		// TODO: validate ?
		return doWrapEvent( evento, EvnRespFotocopia.DOCUMENTOSASOCIADOSADD_EVENTRESP_OK );
	}

	/**
	 * Llamado al eliminar un documento dentro de la
	 * creacion de una solicitud.
	 */
	private EvnRespFotocopia
	doDelDocumentoAsociado( EvnFotocopia evento )
	throws EventoException {
		// TODO: validate ?
		return doWrapEvent( evento, EvnRespFotocopia.DOCUMENTOSASOCIADOSDEL_EVENTRESP_OK );
	}

	/**
	 * @deprecated, ver actualizarDocumentosAsociados.
	 */
	private EvnRespFotocopia
	liquidarSolicitudFotocopia( EvnFotocopia evento )
	throws EventoException {
		return null;
	}


	/**
	 * Creacion e inscripcion de los documentos
	 * asociados en una solicitud de fotocopias.
	 */
	private EvnRespFotocopia
	crearSolicitudFotocopia( EvnFotocopia evnt )
	throws EventoException {

		 EvnFotocopiaCrear evento =(EvnFotocopiaCrear)evnt;
		 String estacionAsignada=null;

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Solicitud solicitud = null;
		Turno turno = null;


		try {

			solicitud = hermod.crearSolicitud(evento.getSolicitudFotocopia());

			if (solicitud == null) {
                          // TODO: usar otra excepcion
				throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
			}

			SolicitudFotocopia solicitudFotocopia = (SolicitudFotocopia) solicitud;
                        gov.sir.core.negocio.modelo.Usuario usuarioSir = evento.getUsuarioSIR();

			//1. Crear turno modelo operativo.
            turno = hermod.crearTurnoFotocopias( solicitudFotocopia, usuarioSir );

            InicioProcesos inicioProcesos = hermod.obtenerFaseInicial(CProceso.PROCESO_FOTOCOPIAS);
			
			Hashtable parametrosInicio = new Hashtable();
			parametrosInicio.put(Processor.ROL, inicioProcesos.getIdRol());
			parametrosInicio.put(Processor.ITEM_KEY, turno.getIdWorkflow());
			parametrosInicio.put(Processor.ACTIVITY, inicioProcesos.getIdFase());
			parametrosInicio.put(Processor.NOT_ID, "1");
			
            //	3. Obtener estación a la que se asocia el turno. 
			estacionAsignada = hermod.obtenerEstacionTurno(parametrosInicio, turno.getIdCirculo());
			
			//4. Guardar información del turno: ITEMKEY, NOTIFICATION ID, ESTACION ASIGNADA, etc, 
			hermod.guardarInfoTurnoEjecucion (parametrosInicio,estacionAsignada,turno,usuarioSir);
			
			
			// accion ------- --------------------------------------------
			// realizar la impresion TIPO1: comprobante de solicitud
			//
			//

			managePrintJob: {

			  gov.sir.core.negocio.modelo.Usuario usuario = usuarioSir;

			  gov.sir.core.negocio.modelo.Circulo circulo = evento.getSolicitudFotocopia().getCirculo();
			  String sessionId = evento.getSessionId();

			  try {
				 
				 // Si el objeto no fue transmitido, para que no genere error en la impresion

				 // if( null == liquidacion.getSolicitud().getTurno() ){
				 //  liquidacion.getSolicitud().setTurno( evento.getTurno() );
				 //}

				 creacionFocotopia_ImprimirRecibo( circulo, turno, solicitudFotocopia, usuario, sessionId );

				 // liquidacionFocotopia_ImprimirRecibo( evnt, liquidacion , circulo, usuario, sessionId );

			  }
			  catch(Throwable ex ) {
				 Log.getInstance().error(ANFotocopia.class, " # Error En la impresion " );
				 ex.printStackTrace();
				 throw new EventoException( "Errores en la impresion del archivo" );
			  }
			}

		}
		// catch (RuntimeException e1) {
		//	    throw new PagoNoProcesadoException(e1.getMessage());
		//}
		// catch (HermodException e) {
		//    e.printStackTrace(System.out);
	  	//    throw new EventoException(e.getMessage());
	        //}
		catch( Throwable e ) {
			// throw new PagoNoProcesadoException(e1.getMessage());
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANFotocopia.class,"Ha ocurrido una excepcion, al crear turno de fotocopia " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}

		EvnRespFotocopia eventoRespuesta = new EvnRespFotocopia(evento.getUsuario(), turno, EvnRespFotocopia.CREAR_SOLICITUD);

		return eventoRespuesta;
	}


		/**
		 * Realizar la actualizacion cuando llega al operario;
		 * se actualiza el tipo de fotocopia y el numero de hojas.
		 *
		 * @version 1.0
		 */
		private EvnRespFotocopia
		actualizarDocumentosAsociados( EvnFotocopia evento )
		throws EventoException {
                EvnVerificarDocumentosFotocopia evnt = (EvnVerificarDocumentosFotocopia) evento;


                if (hermod == null) {
                        throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
                }

                Solicitud solicitud = null;
                Turno turno = null;


			//Se valida que no sea necesario exigir una nota informativa para permitir el avance
			//del turno desde esta fase. 
			try
			{
			   Hashtable tablaAvance = new Hashtable(2);
			   tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
			   tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			   hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,evento.getTurno());
			}
			catch(Throwable t)
			{
				throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
        



                try {

                  // accion ------- --------------------------------------------
                  // actualizar c/u de los documentos de fotocopia con
                  // los datos de medio y numero de paginas

                  SolicitudFotocopia solicitudFotocopia = evnt.getSolicitudFotocopia();
                  if( solicitudFotocopia == null) {
                    // TODO: usar otra excepcion
                     throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
                  }
                  if( null == solicitudFotocopia.getDocumentoFotocopia() ) {
                      // TODO: usar otra excepcion
                    throw new LiquidacionNoEfectuadaException( "No existen documentos relacionados en la solicitud de fotocopias" );
                  }

                  solicitudFotocopia = hermod.updateDocumentosFotocopia( solicitudFotocopia );
                  // turno = hermod.crearTurnoFotocopias( solicitudFotocopia );

                  turno = evnt.getTurno();

                  // liquidacion: ----------------------------------------------
                  // genrar la liquidacion para poder pintar el conjunto
                  // de valores sobre la misma pagina, con los datos
                  // de la liquidacion (y poderle decir liquidar)
                  // -----------------------------------------------------------
                  LiquidacionTurnoFotocopia liquidacion = null;
                  liquidar: {

                liquidacion = new LiquidacionTurnoFotocopia();
                liquidacion.setSolicitud(solicitudFotocopia);
                liquidacion.setUsuario(evento.getUsuarioSIR());
                
                /** @author : HGOMEZ
                *** @change : Se valida que la solicitud de fotocopia sea exenta.
                *** Caso Mantis : 12288
                */
                if(evento.getEsExento().equals("EXENTO"))
                {
                    liquidacion.setEsExento(true);
                }
                else
                {
                    liquidacion.setEsExento(false);
                }
                               
                // call hermod service
                try {

                      liquidacion = (LiquidacionTurnoFotocopia) hermod.liquidar( liquidacion );
                    }
                    catch (HermodException e) {
                            Log.getInstance().error(ANFotocopia.class,e.getMessage(), e);
                            throw new EventoException(e.getMessage(), e);
                    }
                    catch (Throwable e) {
                            Log.getInstance().error(ANFotocopia.class,e.getMessage(), e);
                            throw new EventoException(e.getMessage(), e);
                    }

                  }



                  // accion ------- --------------------------------------------
                  // realizar la impresion TIPO1: comprobante de solicitud
                  //
                  //

                  managePrintJob: {

                    gov.sir.core.negocio.modelo.Usuario usuario = evnt.getUsuarioSIR();

                    gov.sir.core.negocio.modelo.Circulo circulo = evnt.getSolicitudFotocopia().getCirculo();
                    String sessionId = evnt.getSessionId();

                    try {
                    	Log.getInstance().info(ANFotocopia.class, "# Solicitud" +  liquidacion.getSolicitud() );
                    	Log.getInstance().info(ANFotocopia.class, "# Turno" +  turno );

                      // Si el objeto no fue transmitido, para que no genere error en la impresion

                      // if( null == liquidacion.getSolicitud().getTurno() ){
                      //  liquidacion.getSolicitud().setTurno( evento.getTurno() );
                      //}

                      liquidacionFocotopia_ImprimirRecibo( circulo, turno, liquidacion , solicitudFotocopia, usuario, sessionId );

                    }
                    catch(Throwable ex ) {
                      Log.getInstance().error(ANFotocopia.class, " # Error En la impresion " );
                      ex.printStackTrace();
                      throw new EventoException( "Errores en la impresion del archivo" );
                    }
                  }


                  // avanzar el turno ------------------------------------------

                  

                /***********************************************************************************/
      			/*                    ACTUALIZACION ELIMINAR SAS                                   */
      			/***********************************************************************************/
      		     
      			try {
      					Hashtable parametros = new Hashtable();
      					Turno turnoAvance       = evento.getTurno();
      					Fase fase        = evento.getFase();
      					String respuestaWf = evento.getRespuestaWF();
      					parametros.put( Processor.RESULT ,respuestaWf );
      					
      					hermod.avanzarTurnoNuevoNormal(turnoAvance,fase,parametros,evento.getUsuarioSIR());
                    }
                    catch (HermodException e) {
                            Log.getInstance().error(ANFotocopia.class,e.getMessage(), e);
                            throw new EventoException(e.getMessage(), e);
                    }
                    catch (Throwable e) {
                            Log.getInstance().error(ANFotocopia.class,e.getMessage(), e);
                            throw new EventoException(e.getMessage(), e);
                    }


                   

                }
                catch (Throwable e) {
                    // throw new PagoNoProcesadoException(e1.getMessage());
                    ExceptionPrinter printer = new ExceptionPrinter(e);
                    Log.getInstance().error(ANFotocopia.class,"Ha ocurrido una excepcion, al efectura paso en turno de fotocopia " + printer.toString());
                    throw new EventoException(e.getMessage(),e);
                }

                EvnRespFotocopia eventoRespuesta = new EvnRespFotocopia(evento.getUsuario(), turno, EvnRespFotocopia.VERIFICAR_DOCUMENTOS_ASOCIADOS_OK );

                return eventoRespuesta;
        }
	// ----------------------------------------------------------------------------------------------



	/**
	 * Cuando se niega la solicitud de fotocopias
	 * al realizar la liquidacion.
	 *
	 * Esto se debe a que no se encontro el set de documentos
	 * relacionados o por otra causa encontrada.
	 * No se realiza pago; simplemente se avanza el turno.
	 *
	 * Se genera <u>imprimible</u>.
	 *
	 * @version 1.0
	 */
	protected EvnRespFotocopia
	doNoExpedirSolicitudFotocopiasAlLiquidar( EvnFotocopia evnt )
   throws EventoException {

		EvnLiquidarFotocopiaNegar evento = (EvnLiquidarFotocopiaNegar)evnt;

		if( null == hermod ) {
		  throw new ServicioNoEncontradoException( "Servicio Hermod no encontrado" );
		}

		Fase fase   = null;
		Turno turno = null;
		String respuestaWf=null;

		try {

		  // avanzar el turno ------------------------------------------

		  avanzarTurno: {


			//Se valida que no sea necesario exigir una nota informativa para permitir el avance
			//del turno desde esta fase. 
			try
			{
			   Hashtable tablaAvance = new Hashtable(2);
			   tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
			   tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			   hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,evento.getTurno());
			}
			catch(Throwable t)
			{
				throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
        

            /***********************************************************************************/
			/*                    ACTUALIZACION ELIMINAR SAS                                   */
			/***********************************************************************************/
		     
				try {
					Hashtable parametros = new Hashtable();
					turno       = evento.getTurno();
					fase        = evento.getFase();
					respuestaWf = evento.getRespuestaWF();
					parametros.put( Processor.RESULT ,respuestaWf );
					
					hermod.avanzarTurnoNuevoNormal(turno,fase,parametros,evento.getUsuarioSIR());
				}
				catch (HermodException e) {
					Log.getInstance().error(ANFotocopia.class,e.getMessage(), e);
					throw new EventoException(e.getMessage(), e);
				}
				catch (Throwable e) {
					Log.getInstance().error(ANFotocopia.class,e.getMessage(), e);
					throw new EventoException(e.getMessage(), e);
				}

			} // :avanzarTurno

			// realizar imprimible ------------------------------------------
			printAction: {

				Vector notas = null;
				String nombreCirculo;
				String turnoNombre;
				String matriculaNombre;
				String sessionId;

				notas = new Vector();
				Iterator listOfNotesIterator = turno.getNotas().iterator();
				for (; listOfNotesIterator.hasNext(); ) {
					notas.add(listOfNotesIterator.next());
				}

				nombreCirculo = evento.getCirculo().getNombre();
				turnoNombre = evento.getTurno().getIdWorkflow();
				matriculaNombre = "";
				sessionId = evento.getSessionId();

				doNoExpedirSolicitudFotocopiasAlLiquidar_Print(notas, nombreCirculo,
					turnoNombre, matriculaNombre, sessionId);
			} // :printAction

		}
		catch (Throwable e) {
		  // throw new PagoNoProcesadoException(e1.getMessage());
		  ExceptionPrinter printer = new ExceptionPrinter(e);
		  Log.getInstance().error(ANFotocopia.class,
				"Ha ocurrido una excepcion, al efectura paso en turno de fotocopia " +
				printer.toString());
		  throw new EventoException(e.getMessage(), e);
		}

      // wrap the evento-respuesta
		EvnRespFotocopia eventoRespuesta
			= new EvnRespFotocopia( evento.getUsuario(), turno, fase, EvnRespFotocopia.PAGOFOTOCOPIASZEROVALUE_EVENTRESP_OK );

		return eventoRespuesta;

	}





	/**
	 * Realiza la impresion de notas informativas
	 * cuando se niega la solicitud al realizar la liquidacion.
	 */
	private void
	doNoExpedirSolicitudFotocopiasAlLiquidar_Print (
			Vector notas
		 , String nombreCirculo
		 , String turnoNombre
		 , String matriculaNombre
		 , String sessionId
	)
	throws Throwable {

		String fechaImpresion = this.getFechaImpresion();

		ImprimibleFotocopiaNoExpedirSolicitudFotocopiasAlLiquidar impRec;
		impRec = new ImprimibleFotocopiaNoExpedirSolicitudFotocopiasAlLiquidar( notas, nombreCirculo, turnoNombre, matriculaNombre, fechaImpresion,CTipoImprimible.RECIBO );

		String uid = sessionId;

		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try {
				  String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
				  if( intentosImpresion != null ) {
							  Integer intentos = new Integer (intentosImpresion);
							  maxIntentos = intentos.intValue();
				  }
				  else {
							  Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
							  maxIntentos = intentosDefault.intValue();
				  }

				  //INGRESO TIEMPO DE ESPERA IMPRESION
				  String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
				  if (intentosImpresion != null) {
							  Integer esperaInt = new Integer(esperaImpresion);
							  espera = esperaInt.intValue();
				  }
				  else {
							 Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
							 espera = esperaDefault.intValue();
				  }
		}
		catch( Throwable t ) {

		  Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
		  maxIntentos = intentosDefault.intValue();

		  Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
		  espera = esperaDefault.intValue();

		}

		// crear bundle para impresion
		Bundle bundle = new Bundle(impRec);

		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(uid, bundle, maxIntentos, espera);
		}
		catch (Throwable t) {
			t.printStackTrace();
		} // end try

	 } // end method

	// ----------------------------------------------------------------------------------------------


	/**
	 * realiza el imprimible al
	 * crear la solicitud.
	 */
	private void
   creacionFocotopia_ImprimirRecibo(
            gov.sir.core.negocio.modelo.Circulo circulo
          , gov.sir.core.negocio.modelo.Turno turno
          , gov.sir.core.negocio.modelo.SolicitudFotocopia solicitud
          , gov.sir.core.negocio.modelo.Usuario user
          , String sessionId
   )
   throws Throwable {

          String fechaImpresion = this.getFechaImpresion();

          ImprimibleFotocopiaCrearSolicitud imprimible
              = new ImprimibleFotocopiaCrearSolicitud( turno, solicitud, circulo ,CTipoImprimible.RECIBO);
          imprimible.setTamanoCarta(false);
          imprimible.setFechaImpresionServidorFormatted( fechaImpresion );
          imprimible.setUsuario( user );
          String uid = sessionId;


		// Obtener los Legal's de la base de datos?? si
		   /*String[] legals =    {
													"El valor a pagar se informará una vez "                         +
													"se determine el número total de copias a expedir. "
										};*/

   		
        //IMPRIMIR EL RECIBO

                //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
                int maxIntentos;
                int espera;

                //INGRESO DE INTENTOS DE IMPRESION
                try
                {

                	TextoImprimiblePk idTexto=new TextoImprimiblePk();
                	idTexto.idTexto=CTextoImprimible.PIE_PAGINA_FOTOC_CREAR_SOLICITUD;
                	TextoImprimible texto = hermod.getTextoImprimible(idTexto);
                	String[] legals  = {texto.getValor()};
                	imprimible.setFootLegendText(legals);
                        String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
                        if (intentosImpresion != null)
                        {
                                 Integer intentos = new Integer (intentosImpresion);
                                 maxIntentos = intentos.intValue();
                        }
                        else
                        {
                                 Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
                                 maxIntentos = intentosDefault.intValue();
                        }

                        //INGRESO TIEMPO DE ESPERA IMPRESION
                        String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
                        if (intentosImpresion != null)
                        {
                                 Integer esperaInt = new Integer(esperaImpresion);
                                 espera = esperaInt.intValue();
                        }
                        else
                        {
                                Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
                                espera = esperaDefault.intValue();
                        }
                }
                catch (Throwable t)
                {
                        Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
                        maxIntentos = intentosDefault.intValue();

                        Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
                        espera = esperaDefault.intValue();


                }

                Bundle bundle = new Bundle( imprimible );

              try {
                printJobs.enviar(uid, bundle, maxIntentos, espera);
              }
              catch (Throwable t) {
                t.printStackTrace();
              }
        }
      // block:eof



		/**
		 * imprime el recibo al liquidar
		 */

        private void
        liquidacionFocotopia_ImprimirRecibo(
            gov.sir.core.negocio.modelo.Circulo circulo
          , gov.sir.core.negocio.modelo.Turno turno
          , gov.sir.core.negocio.modelo.Liquidacion liquidacion
          , gov.sir.core.negocio.modelo.SolicitudFotocopia solicitud
          , gov.sir.core.negocio.modelo.Usuario user
          , String sessionId
        )
        throws Throwable {


          String fechaImpresion = this.getFechaImpresion();
          /*

          Pago pago = new gov.sir.core.negocio.modelo.imprimibles.util.DefaultPago( liquidacion, turno );

          ImprimibleRecibo impRec;
          impRec = new ImprimibleFotocopiaComprobanteSolicitud( pago, circulo, fechaImpresion );
          // impRec = new ImprimibleRecibo( evento.getTurno(), circulo, fechaImpresion );
          */

          ImprimibleFotocopiaLiquidarSolicitud impRec;
          impRec = new ImprimibleFotocopiaLiquidarSolicitud( turno, solicitud, circulo, liquidacion ,CTipoImprimible.RECIBO);
			 impRec.setFechaImpresionServidorFormatted( fechaImpresion );


           String uid = sessionId;

        //IMPRIMIR EL RECIBO

			// Obtener los Legal's de la base de datos?? SI
                    /*String[] legals  = {
                                             "De acuerdo con el Decreto 1428 de 2000 (julio 26),  " +
                                             "se cobran las siguientes tarifas: "              +
                                             "<br /> - Documentos almacenados en medio óptico o microfilmado: $500 "   +
                                             "por cada página reproducida. "                        +
                                       	     "<br /> - Documentos que reposen en los archivos físicos de "             +
                                             "la respectiva oficina de registro: $200 "                       +
                                             "por cada pagina reproducida. "
                                      ,      "<br /> "
                                      ,      "De acuerdo con la Resolución 5123 de 2000 (Artículo 1, "        +
                                             "parágrafo 1),"                                                  +
                                             " \"Se entenderá que el interesado desiste de su petición "      +
                                             "cuando transcurridos dos meses contados a partir de la "        +
                                             "solicitud no acredite el pago respectivo, de lo cual "          +
                                             "el Registrador dejará expresa constancia en memorial "          +
                                             "petitorio de las copias\"."
                                                              };*/
           
           

   		

                //OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
                int maxIntentos;
                int espera;

                //INGRESO DE INTENTOS DE IMPRESION
                try
                {
                	TextoImprimiblePk idTexto=new TextoImprimiblePk();
                	idTexto.idTexto=CTextoImprimible.PIE_PAGINA_FOTOC_LIQUIDACION_SOLICITUD;
                	TextoImprimible texto = hermod.getTextoImprimible(idTexto);
                	String[] legals  = {texto.getValor()};
                	impRec.setFootLegendText( legals );

                        String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
                        if (intentosImpresion != null)
                        {
                                 Integer intentos = new Integer (intentosImpresion);
                                 maxIntentos = intentos.intValue();
                        }
                        else
                        {
                                 Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
                                 maxIntentos = intentosDefault.intValue();
                        }

                        //INGRESO TIEMPO DE ESPERA IMPRESION
                        String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
                        if (intentosImpresion != null)
                        {
                                 Integer esperaInt = new Integer(esperaImpresion);
                                 espera = esperaInt.intValue();
                        }
                        else
                        {
                                Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
                                espera = esperaDefault.intValue();
                        }
                }
                catch (Throwable t)
                {
                        Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
                        maxIntentos = intentosDefault.intValue();

                        Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
                        espera = esperaDefault.intValue();


                }

                Bundle bundle = new Bundle(impRec);
                String copyActive = hermod.getCopiaImp(circulo.getIdCirculo());
                if(copyActive.equals(CHermod.ACTIVE)){
                     bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                }

              try {
                //se manda a imprimir el recibo por el identificador unico de usuario
                printJobs.enviar(uid, bundle, maxIntentos, espera);
              }
              catch (Throwable t) {
                t.printStackTrace();
              }
        }
      // block:eof


		/**
		 * Obtiene la fecha de impresion del servidor.
		 */
      protected String getFechaImpresion() {
          Calendar c = Calendar.getInstance();
          int dia;
          int ano;
          int hora;
          String min;
          String seg;
          String mes;

          dia = c.get(Calendar.DAY_OF_MONTH);
          mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
          ano = c.get(Calendar.YEAR);

          hora = c.get(Calendar.HOUR_OF_DAY);

          if (hora > 12) {
              hora -= 12;
          }

          min = formato(c.get(Calendar.MINUTE));
          seg = formato(c.get(Calendar.SECOND));

          String fechaImp = "Impreso el " + dia + " de " + mes + " de " + ano +
              " a las " + formato(hora) + ":" + min + ":" + seg + " " +
              DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

          return fechaImp;
  }
  /**
	* Usado al establecer la fecha de impresion
	*/
  protected String formato(int i) {
      if (i < 10) {
          return "0" + (new Integer(i)).toString();
      }

      return (new Integer(i)).toString();
  }

      // block: eof



	/**
	 * Valida que todas las aplicaciones de pago tengan saldo válido
	 * @param pago EL pago que se va a validar
	 * @param precision El rango de tolerancia
	 * @return boolean true si el pago es válido
	 * @throws PagoInvalidoException si el pago no es valido
	 */
	private boolean validarPago(Pago pago, double precision) throws PagoInvalidoException {

		List pagos = pago.getAplicacionPagos();
		Liquidacion liquidacion = pago.getLiquidacion();
		double valorLiquidado = liquidacion.getValor();
		double valorPagado = 0;

		Iterator it = pagos.iterator();

		while (it.hasNext()) {
			AplicacionPago apl = (AplicacionPago) it.next();
			valorPagado += apl.getValorAplicado();
		}

		if ((valorPagado + precision) < valorLiquidado) {
			throw new PagoInvalidoException("El valor a pagar no coincide con el valor Liquidado");
		}

		return true;
	}

}
