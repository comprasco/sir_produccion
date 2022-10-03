/*
 * Created on 29-sep-2004
*/
package gov.sir.core.negocio.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnRespTurnoManualRegistro;
import gov.sir.core.eventos.administracion.EvnTurnoManualRegistro;
import gov.sir.core.eventos.comun.EvnLiquidacion;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.negocio.acciones.excepciones.CrearTurnoException;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaAsociadaInvalidaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.RegistroLiquidacionSolicitudVinculadaInvalidParameterException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.SolicitudInvalidaRegistroException;
import gov.sir.core.negocio.acciones.excepciones.TurnoAnteriorInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionRegistroNoProcesadoException;
import gov.sir.core.negocio.modelo.Acto;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.CheckItem;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoProceso;
import gov.sir.core.negocio.modelo.CirculoProcesoPk;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.EstacionReciboPk;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoRegistro;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudPk;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.SubtipoSolicitud;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CRoles;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaLiquidacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.dao.impl.jdogenie.JDOLiquidacionesDAO;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author jfrias
*/
public class ANTurnoManualRegistro extends SoporteAccionNegocio {

	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * Instancia de Fenrir
	 */
	private FenrirServiceInterface fenrir;

	/**
	 * Instancia de la intefaz de PrntJobs
	 */
	private PrintJobsInterface printJobs;

	/**
	 * @throws EventoException
	 *
	 */
	public ANTurnoManualRegistro() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
		}

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnTurnoManualRegistro evento = (EvnTurnoManualRegistro) e;

		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.VALIDAR)) {
			return validar(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.REGISTRO_VINCULARTURNO_ADDITEM_EVENT)) {
			return validarSolicitudVinculada( evento );
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.VALIDAR_TURNO)) {
			return validarTurno(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.VALIDAR_MATRICULA)) {
			return validarMatricula(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.AGREGAR_ACTO)) {
			return agregarActo(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.VALIDAR_MATRICULA_ASOCIADA)) {
			return validarMatriculaAsociada(evento);
		} else if (evento.getTipoEvento().equals(EvnLiquidacion.LIQUIDAR)) {
			return liquidar(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.EDITAR_LIQUIDACION)) {
			return editarLiquidacion(evento);
		}  else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.BUSCAR_SOLICITUD)) {
			return buscarSolicitud(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION )) {
			return buscarSolicitudEdicion(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.VALIDAR_SOLICITUD)) {
			return validarSolicitud(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.INCREMENTAR_SECUENCIAL_RECIBO)) {
			return incrementarSecuencial(evento);
		} else if (evento.getTipoEvento().equals(EvnTurnoManualRegistro.CREAR_TURNO)) {
			return crearTurnoRegistro(evento);
		}

		return null;
	}

	/**
	 * Este metodo hace el llamado al negocio para que se haga la liquidacion de la solicitud
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnLiquidacion</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLiquidacion</CODE>
	 * @throws EventoException
	 */
	private EventoRespuesta liquidar(EvnTurnoManualRegistro evento) throws EventoException {
		EvnRespLiquidacion evRespuesta = null;

		Liquidacion liquidacion = evento.getLiquidacion();
		ImprimibleBase imprimible = null;
		String UID = "";

		if (liquidacion == null) {
			throw new LiquidacionNoEfectuadaException("No existe liquidación asociada");
		}

		Solicitud solicitud = liquidacion.getSolicitud();

		if (solicitud == null) {
			throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
		}

		try {
			List solFolio = solicitud.getSolicitudFolios();
			List matriculas = new Vector();
			Iterator itSolFolio = solFolio.iterator();
			
			LiquidacionTurnoRegistro liqReg = (LiquidacionTurnoRegistro)liquidacion;
			
			// La solicitud aún no ha sido creada en la base de datos, así que debe ser creada
			liqReg.setUsuario(evento.getUsuarioSIR());
			solicitud = hermod.crearSolicitud(solicitud);
			if (solicitud.getLiquidaciones()!= null && solicitud.getLiquidaciones().size()>0){
				liqReg = (LiquidacionTurnoRegistro) solicitud.getLiquidaciones().get(0);
//				Se setea la fecha de la solicitud con la fecha de Radicacion
				liqReg.setFecha(solicitud.getFecha());
			}

			while (itSolFolio.hasNext()) {
				SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
				matriculas.add(sol.getFolio().getIdMatricula());
			}

			forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud), matriculas);
			
			double valorDerechosLiquidacion = 0, valorImpuestosLiquidacion = 0, valorMora = 0, valorTotal = 0 , valorConservacionLiquidacion=0;
                        /* JAlcazar caso Mantis 05648: Acta - Requerimiento No 006 -Reespecificacion _Tarifas Resolucion_81_2010-imm.doc
                         * Cambio de tipo de dato
                         */
			double valorOtroImpuesto = 0;
			
			//liquidacion = hermod.liquidar(liquidacion);
			
			// Al no pasar por un liquidador, los totales deben calcularse sin la intervención
			// de los mismos
			List actos = liqReg.getActos();
                        JDOLiquidacionesDAO jdoLiquidacionesDAO = new JDOLiquidacionesDAO();
			for(Iterator itActos = actos.iterator(); itActos.hasNext();) {
				Acto acto = (Acto)itActos.next();
				valorDerechosLiquidacion += acto.getValor();
                                valorConservacionLiquidacion += (acto.getValor()* jdoLiquidacionesDAO.TraerConservacionActo(acto));
				valorImpuestosLiquidacion += acto.getValorImpuestos();
			}
			
			valorOtroImpuesto = liqReg.getValorOtroImp();
			valorMora = liqReg.getValorMora();
			
			liqReg.setValorDerechos(valorDerechosLiquidacion);
			liqReg.setValorImpuestos(valorImpuestosLiquidacion);
			
                        System.out.println("Desarollo1:: Valor derechos AN:"+String.valueOf(valorDerechosLiquidacion));
                        System.out.println("Desarollo1:: Valor impuestos AN:"+String.valueOf(valorImpuestosLiquidacion));
                        System.out.println("Desarollo1:: Valor Conservacion AN:"+String.valueOf(valorConservacionLiquidacion));
                        
			valorTotal = valorDerechosLiquidacion + valorImpuestosLiquidacion + valorOtroImpuesto + valorMora + valorConservacionLiquidacion;
			
			liqReg.setValor(valorTotal);

			//Se debe generar un pago con valor 0, para indicar que la solicitud ya fue paga
			if ((liqReg.getValor() == 0) && !(solicitud instanceof SolicitudRegistro)) {
				Estacion estacion = evento.getEstacion();
				Pago pago = new Pago(liquidacion, null);
				
//				Se setea la fecha de la solicitud con la fecha de Radicacion
				pago.setFecha(solicitud.getFecha());
				
				DocumentoPago documentoEfectivo = new DocPagoEfectivo(0);
				AplicacionPago appEfectivo = new AplicacionPago(documentoEfectivo, 0);

				pago.addAplicacionPago(appEfectivo);
				pago.setUsuario(evento.getUsuarioSIR());

				pago = hermod.registrarPago(pago, estacion.getEstacionId());
			}

			//SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO LA PANTALLA DE REGISTRO DE PAGO.
			Boolean esCajero = new Boolean(false);
			try{
				List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

				Iterator it = roles.iterator();
				while(it.hasNext()){
					Rol rol = (Rol)it.next();
					if(rol.getRolId().equals(CRoles.CAJERO)){
						esCajero = new Boolean(true);
						break;
					}
				}


			}catch(Exception fe){
				ExceptionPrinter printer = new ExceptionPrinter(fe);
				Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
			}
			
			//SE VERIFICA SI EL USUARIO ES CAJERO DE REGISTRO PARA MOSTRAR O NO EL BOTON DE SIGUIENTE
			//QUE CONDUCE A LA RADICACIÓN DEL TURNO.
			Boolean esCajeroRegistro = new Boolean(false);
			try{
				List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

				Iterator it = roles.iterator();
				while(it.hasNext()){
					Rol rol = (Rol)it.next();
					if(rol.getRolId().equals(CRoles.CAJERO_REGISTRO)){
						esCajeroRegistro = new Boolean(true);
						break;
					}
				}


			}catch(Exception fe){
				ExceptionPrinter printer = new ExceptionPrinter(fe);
				Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
			}			


			//Imprime la liquidación, sólo si se se desea imprimir
			if(evento.isImprimirConstancia()){
				UID = evento.getUID();
				String fechaImpresion= this.getFechaImpresion();
				imprimible = new ImprimibleConstanciaLiquidacion(liquidacion, fechaImpresion,CTipoImprimible.RECIBO);
				this.imprimir(imprimible, UID);
			}

			//Genera la respuesta
			evRespuesta = new EvnRespLiquidacion(liqReg, EvnRespLiquidacion.LIQUIDACION);
		    evRespuesta.setEsCajero(esCajero);
			evRespuesta.setEsCajeroRegistro(esCajeroRegistro);

		} catch (HermodException e) {
			throw new LiquidacionNoEfectuadaException(e.getMessage(), e);
		} catch (ForsetiException e) {
			throw new ValidacionParametrosHTException(e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		return evRespuesta;
	}

	/**
	 * Este metodo hace el llamado al negocio para que modifique una liquidación existente.
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnLiquidacion</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespLiquidacion</CODE>
	 * @throws EventoException
	 */
	private EventoRespuesta editarLiquidacion(EvnTurnoManualRegistro evento) throws EventoException {
		EvnRespLiquidacion evRespuesta = null;

		Liquidacion liquidacion = evento.getLiquidacion();
		ImprimibleBase imprimible = null;
		String UID = "";

		if (liquidacion == null) {
			throw new LiquidacionNoEfectuadaException("No existe liquidación asociada");
		}

		Solicitud solicitud = liquidacion.getSolicitud();

		if (solicitud == null) {
			throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
		}

		try {
			List solFolio = solicitud.getSolicitudFolios();
			List matriculas = new Vector();
			Iterator itSolFolio = solFolio.iterator();

			while (itSolFolio.hasNext()) {
				SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
				matriculas.add(sol.getFolio().getIdMatricula());
			}

			forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud), matriculas);
			liquidacion.setUsuario(evento.getUsuarioSIR());
			liquidacion = hermod.modificarLiquidacionRegistro(liquidacion);

			//Se debe generar un pago con valor 0, para indicar que la solicitud ya fue paga
			if (liquidacion.getValor() == 0) {
				Estacion estacion = evento.getEstacion();
				Pago pago = new Pago(liquidacion, null);
				DocumentoPago documentoEfectivo = new DocPagoEfectivo(0);
				AplicacionPago appEfectivo = new AplicacionPago(documentoEfectivo, 0);

				pago.addAplicacionPago(appEfectivo);
				pago.setUsuario(evento.getUsuarioSIR());

				pago = hermod.registrarPago(pago, estacion.getEstacionId());
			}

			//SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO LA PANTALLA DE REGISTRO DE PAGO.
			Boolean esCajero = new Boolean(false);
			try{
				List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

				Iterator it = roles.iterator();
				while(it.hasNext()){
					Rol rol = (Rol)it.next();
					if(rol.getRolId().equals(CRoles.CAJERO)){
						esCajero = new Boolean(true);
					}
				}


			}catch(Exception fe){
				ExceptionPrinter printer = new ExceptionPrinter(fe);
				Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
			}


			//Imprime la liquidación
			UID = evento.getUID();
			String fechaImpresion= this.getFechaImpresion();
			imprimible = new ImprimibleConstanciaLiquidacion(liquidacion, fechaImpresion,CTipoImprimible.RECIBO);
			this.imprimir(imprimible, UID);

			//Genera la respuesta
			evRespuesta = new EvnRespLiquidacion(liquidacion, EvnRespLiquidacion.LIQUIDACION);
			evRespuesta.setEsCajero(esCajero);

		} catch (HermodException e) {
			throw new LiquidacionNoEfectuadaException(e.getMessage(), e);
		} catch (ForsetiException e) {
			throw new ValidacionParametrosHTException(e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		return evRespuesta;
	}


	/**
	 * @param evento
	 * @return evento respuesta
	 */
	private EventoRespuesta agregarActo(EvnTurnoManualRegistro evento) throws EventoException {
		Acto acto = null;

		try {
			acto = hermod.getLiquidacionActo(evento.getActo());

			if (acto == null) {
				throw new ValidacionParametrosException("El acto no pudo ser agregado.");
			}
		} catch (ServiceLocatorException e) {
			Log.getInstance().error(ANTurnoManualRegistro.class,e);
			throw new ValidacionParametrosException("No se pudo encontrar el servicio de hermod.");
		} catch (HermodException e) {
			Log.getInstance().error(ANTurnoManualRegistro.class,e);

			ValidacionParametrosException e1 = new ValidacionParametrosException("El acto no pudo ser agregado.");
			e1.addError("El acto no pudo ser agregado.");
			throw e1;
		} catch (Throwable e) {
			Log.getInstance().error(ANTurnoManualRegistro.class,e);
		}

		return new EvnRespTurnoManualRegistro(acto, EvnRespTurnoManualRegistro.AGREGAR_ACTO);
	}

	/**
	 * @param evento
	 * @return evento respuesta
	 * @throws EventoException
	 */
	private EventoRespuesta validarMatricula(EvnTurnoManualRegistro evento) throws EventoException {
		String matricula = evento.getMatricula();

		try {
			List matriculas = new Vector();
			matriculas.add(matricula);

			//bug 4979
			List validaciones = hermod.getValidacionesRegistroTurnoManual();

			if (forseti.existeFolio(matricula)) {
				forseti.validarMatriculas(validaciones, matriculas);

				return new EvnRespLiquidacion(matricula, EvnRespLiquidacion.VALIDACION_MATRICULA);
			}

			throw new MatriculaInvalidaRegistroException("La matrícula no existe");
		} catch (MatriculaInvalidaRegistroException e) {
			throw e;
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"Error en el servicio para validar la matrícula:" + ep.toString());

			String errores = "";
			Iterator it = ((List) e.getHashErrores().get(matricula)).iterator();

			while (it.hasNext()) {
				errores = errores + "\n" + it.next();
			}

			throw new MatriculaInvalidaRegistroException("Resultado de la validación de la matrícula " + matricula + ": " + errores);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new MatriculaInvalidaRegistroException("Se produjo un error validando la matrícula", e);
		}
	}

	/**
	 * @param evento
	 * @return envento respuesta
	 * @throws EventoException
	 */
	private EventoRespuesta validarMatriculaAsociada(EvnTurnoManualRegistro evento) throws EventoException {
		String matricula = evento.getMatricula();

		try {
			if (forseti.existeFolio(matricula)) {
				return new EvnRespLiquidacion(matricula, EvnRespLiquidacion.VALIDACION_MATRICULA_ASOCIADA);
			}

			throw new MatriculaAsociadaInvalidaRegistroException("La matrícula no existe");
		} catch (MatriculaInvalidaRegistroException e) {
			throw e;
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"Error en el servicio para validar la matrícula:" + ep.toString());
			throw new MatriculaAsociadaInvalidaRegistroException("Error en el servicio para validar la matrícula", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new MatriculaAsociadaInvalidaRegistroException("Se produjo un error validando la matrícula", e);
		}
	}

protected EventoRespuesta
validarSolicitudVinculada( EvnTurnoManualRegistro evento )
throws EventoException {

    // pipe + "wrap" the event

    gov.sir.core.negocio.modelo.SolicitudVinculada param_t0_SolicitudVinculada = null;
    param_t0_SolicitudVinculada = evento.getSolicitudVinculada();


    gov.sir.core.negocio.modelo.SolicitudVinculada param_t1_SolicitudVinculada = null;

    try {

        gov.sir.core.negocio.modelo.SolicitudPk solicitudId
           = new gov.sir.core.negocio.modelo.SolicitudPk();
        solicitudId.idSolicitud = param_t0_SolicitudVinculada.getIdSolicitud();

        //boolean validationResult = hermod.validarSolicitudVinculada( solicitudId );
        hermod.validarSolicitudVinculada( solicitudId );


        gov.sir.core.negocio.modelo.Solicitud solicitudVinculada
          = hermod.getSolicitudById( solicitudId.idSolicitud );

        // set solicitud1
        param_t1_SolicitudVinculada = new gov.sir.core.negocio.modelo.SolicitudVinculada();
        param_t1_SolicitudVinculada.setSolicitudPadre( solicitudVinculada );



        // observar si el turno vinculado esta
        // vinculado a otro turno ?

        // List<Turno> ::
        // hermod.getTurnosVinculados( param_t1_TurnoVinculado.getIdWorkFlow() )
        // si tiene items,
        // throw new Exception("El turno: " + turno.getIdWorkflow() + " ya fue utilizado como turno vinculado ");
        // foreach, validate ?




    }
    catch (HermodException h) {
       throw new RegistroLiquidacionSolicitudVinculadaInvalidParameterException( "solicitud-vinculada; error: ", h );
    }
    catch (Throwable e) {
       throw new RegistroLiquidacionSolicitudVinculadaInvalidParameterException( "Imposible validar el turno", e );
    }


    EvnRespTurnoManualRegistro processedEvent = null;
    processedEvent = new EvnRespTurnoManualRegistro( null );

    processedEvent.setTipoEvento( EvnRespTurnoManualRegistro.REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK );
    processedEvent.setSolicitudVinculada( param_t1_SolicitudVinculada );

    return processedEvent;
}

	/**
	 * @param evento
	 * @return  envento respuesta
	 * @throws EventoException
	 */
	private EventoRespuesta validarTurno(EvnTurnoManualRegistro evento) throws EventoException {
		Turno t = evento.getTurnoAnterior();
		Turno turno = null;
		List turnosValidacion = new ArrayList();

		try {
			turno = hermod.getTurnobyWF(t.getIdWorkflow());

			//REALIZAR LAS VALIDACIONES PARA DETERMINAR SI EL TURNO ANTERIOR INGRESADO, YA ES TURNO ANTERIOR
			//DE OTRO TURNO.
			try {
				turnosValidacion = hermod.getTurnosSiguientes(turno.getIdWorkflow());
			} catch (Throwable e1) {
				throw new TurnoAnteriorInvalidoException("Error obteniendo turnos siguientes del turno: " + turno.getIdWorkflow(), e1);
			}

			if ((turnosValidacion != null) && (turnosValidacion.size() != 0)) {
				throw new TurnoAnteriorInvalidoException("El turno: " + turno.getIdWorkflow() + " ya fue utilizado como turno anterior");
			}

			//BUGID: 0002018
			validarTurnoHistorialFases(turno);

			//
			SolicitudRegistro solicitudReg = (SolicitudRegistro) turno.getSolicitud();
			long idLiq = solicitudReg.getLastIdLiquidacion() - 1;
			List liquidaciones = solicitudReg.getLiquidaciones();
			Liquidacion liq = (Liquidacion) liquidaciones.get((int) idLiq);
			LiquidacionTurnoRegistro liqReq = (LiquidacionTurnoRegistro) liq;
			liqReq.setSolicitud(solicitudReg);

			List actos = liqReq.getActos();
			List listaTemp = new Vector();
			List listaActosTemp = new Vector();

			for (Iterator iter = actos.iterator(); iter.hasNext();) {
				Object element = iter.next();
				listaTemp.add(element);
			}

			for (Iterator iterActos = listaTemp.iterator(); iterActos.hasNext();) {
				Acto actoTemp = (Acto) iterActos.next();
				//Acto nuevoActo = new Acto();

				liqReq.removeActo(actoTemp);
				actoTemp.setLiquidacion(liqReq);

				actoTemp = hermod.getLiquidacionActo(actoTemp);
				listaActosTemp.add(actoTemp);
			}

			for (Iterator iter = listaActosTemp.iterator(); iter.hasNext();) {
				Acto element = (Acto) iter.next();
				liqReq.addActo(element);
			}
		} catch (TurnoAnteriorInvalidoException e) {
			throw e;
		} catch (HermodException h) {
			throw new TurnoAnteriorInvalidoException("El turno anterior ingresado es inválido", h);
		} catch (Throwable e) {
			throw new TurnoAnteriorInvalidoException("Imposible validar el turno", e);
		}

		return new EvnRespLiquidacion(turno);
	}

	/**
	 * @param turno
	 * @throws TurnoAnteriorInvalidoException
	 */
	private void validarTurnoHistorialFases(Turno turno) throws TurnoAnteriorInvalidoException {
		List turnoHists = turno.getHistorials();
		Iterator itHis = turnoHists.iterator();
		boolean devuelto = false;
		boolean entrega = false;

		while (itHis.hasNext()) {
			TurnoHistoria turHis = (TurnoHistoria) itHis.next();

			// EL TURNO PASO POR ENTREGA REGISTRO ó ENTREGA CORRESPONDENCIA
			if (turHis.getFase().equalsIgnoreCase(CFase.REG_ENTREGA) || turHis.getFase().equalsIgnoreCase(CFase.REG_ENTREGA_EXTERNO)) {
				entrega = true;
			}

			// EL TURNO PASO POR UNA RESPUESTA DEVOLUCION
			if ((turHis.getRespuesta() != null) && turHis.getRespuesta().equals(CRespuesta.DEVOLUCION)) {
				devuelto = true;
			}
		}

		//EL turno es válido
		if (devuelto && entrega) {
			return;
		}

		throw new TurnoAnteriorInvalidoException(" El turno anterior con el ID: " + turno.getIdWorkflow() + " no ha pasado por la fase de Entrega y no fué Devuelto");
	}

	/**
	 * @param evento
	 * @return envento respuesta
	 * @throws EventoException
	 */
	private EventoRespuesta validar(EvnTurnoManualRegistro evento) throws EventoException {
		SolicitudRegistro solicitud = evento.getSolicitud();

		Boolean esCajero = new Boolean(false);
		
		try {
			TurnoPk turnoId = new TurnoPk();
            turnoId.anio = evento.getAnio();
            turnoId.idCirculo = evento.getIdCirculo();
            turnoId.idProceso = evento.getProceso().getIdProceso();
            turnoId.idTurno = evento.getIdTurno();

            Calendar cal = Calendar.getInstance();
            int iAnio = cal.get(Calendar.YEAR);

            if (iAnio == Integer.parseInt(evento.getAnio())) {
                CirculoProcesoPk cpId = new CirculoProcesoPk();
                cpId.anio = evento.getAnio();
                cpId.idCirculo = evento.getIdCirculo();
                cpId.idProceso = evento.getProceso().getIdProceso();

                CirculoProceso cp = hermod.getCirculoProceso(cpId);

                if (cp.getLastIdTurno() <= Long.parseLong(evento.getIdTurno())) {
                    throw new LiquidacionNoEfectuadaException(
                        "El consecutivo del turno no puede ser mayor al valor del consecutivo actual");
                }
            }

            Turno t = hermod.getTurno(turnoId);

            if (t != null) {
                throw new LiquidacionNoEfectuadaException("El turno " + t.getIdWorkflow() + " ya existe");
            }
			
			List solFolio = solicitud.getSolicitudFolios();
			List matriculas = new Vector();
			Iterator itSolFolio = solFolio.iterator();

			while (itSolFolio.hasNext()) {
				SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
				matriculas.add(sol.getFolio().getIdMatricula());
			}
			
			forseti.validarMatriculas(hermod.getValidacionesSolicitud(solicitud), matriculas);
			
			//SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO EL BOTON SIGUIENTE PARA REGISTRAR EL PAGO.
			try{
				List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

				Iterator it = roles.iterator();
				while(it.hasNext()){
					Rol rol = (Rol)it.next();
					if(rol.getRolId().equals(CRoles.CAJERO)){
						esCajero = new Boolean(true);
						break;
					}
				}

			}catch(Exception fe){
				ExceptionPrinter printer = new ExceptionPrinter(fe);
				Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
			}
			
			
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"No se pudieron validar las matrículas:" + ep.toString());
			throw new ValidacionRegistroNoProcesadoException("Error en la validación de matrículas", e);
		} catch (HermodException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"No se pudieron obtener las validaciones de las matrículas:" + ep.toString());
			throw new ValidacionRegistroNoProcesadoException("Error obteniendo las validaciones de las matrículas", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"No se pudo validar:" + ep.toString());
			throw new ValidacionRegistroNoProcesadoException("No se pudo validar", e);
		}

		EvnRespLiquidacion evnLiq = new EvnRespLiquidacion(solicitud);
		evnLiq.setEsCajero(esCajero); 
		return evnLiq;
	}

	/**
	 * @param evento
	 * @return envento respuesta
	 * @throws EventoException
	 */
	private EventoRespuesta buscarSolicitud(EvnTurnoManualRegistro evento) throws EventoException {
		String idSolicitud = evento.getIdSolicitud();

		Solicitud solicitud = null;
		Boolean esCajeroRegistro = new Boolean(false);		

		try {
			solicitud = hermod.getSolicitudById(idSolicitud);

			List listaLiquidaciones = solicitud.getLiquidaciones();

			if (listaLiquidaciones.size() >= 0) {
				Liquidacion liq = (Liquidacion) listaLiquidaciones.get(0);

				if (liq.getPago() != null) {
					throw new SolicitudInvalidaRegistroException("La solicitud ya fue pagada");
				}
			} else {
				throw new SolicitudInvalidaRegistroException("La solicitud no tiene liquidaciones asociadas");
			}
			
			//SE VERIFICA SI EL USUARIO ES CAJERO PARA MOSTRAR O NO LA PANTALLA DE REGISTRO DE PAGO.
			try{
				List roles = fenrir.darRolUsuario(evento.getUsuarioSIR().getIdUsuario());

				Iterator it = roles.iterator();
				while(it.hasNext()){
					Rol rol = (Rol)it.next();
					if(rol.getRolId().equals(CRoles.CAJERO_REGISTRO)){
						esCajeroRegistro = new Boolean(true);
					}

				}

			}catch(Exception fe){
				ExceptionPrinter printer = new ExceptionPrinter(fe);
				Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion al consultar los reles del usuario " + printer.toString());
			}
			
			
		} catch (HermodException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"La solicitud es invalida o no existe:" + ep.toString());
			throw new SolicitudInvalidaRegistroException("La solicitud es invalida o no existe", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"No se pudo validar:" + ep.toString());
			throw new SolicitudInvalidaRegistroException("No se pudo validar", e);
		}

		EvnRespTurnoManualRegistro respuesta = new EvnRespTurnoManualRegistro(solicitud, EvnRespTurnoManualRegistro.BUSCAR_SOLICITUD);
		respuesta.setEsCajeroRegistro(esCajeroRegistro);
		return respuesta;
	}

	/**
	 * Método que se encarga de consultar una solicitud para cargar sus liquidaciones y poder modificarlas.
	 * Es usado para poder editar las liquidaciones antes de que sean pagadas.
	 * @param evento
	 * @return envento respuesta
	 * @throws EventoException
	 */
	private EventoRespuesta buscarSolicitudEdicion(EvnTurnoManualRegistro evento) throws EventoException {
		String idSolicitud = evento.getIdSolicitud();

		Solicitud solicitud = null;

		try {
			solicitud = hermod.getSolicitudById(idSolicitud);

			List listaLiquidaciones = solicitud.getLiquidaciones();

			if (listaLiquidaciones.size() >= 0) {
				Liquidacion liq = (Liquidacion) listaLiquidaciones.get(0);

				if (liq.getPago() != null) {
					throw new SolicitudInvalidaRegistroException("La solicitud ya fue pagada");
				}
			} else {
				throw new SolicitudInvalidaRegistroException("La solicitud no tiene liquidaciones asociadas");
			}

		} catch (HermodException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"La solicitud es inválida o no existe:" + ep.toString());
			throw new SolicitudInvalidaRegistroException("La solicitud es invalida o no existe", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"No se pudo validar:" + ep.toString());
			throw new SolicitudInvalidaRegistroException("No se pudo validar", e);
		}

		EvnRespTurnoManualRegistro respuesta = new EvnRespTurnoManualRegistro(solicitud, EvnRespTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION);

		return respuesta;
	}

	/**
	 * @param evento
	 * @return envento respuesta
	 * @throws EventoException
	 */
	private EventoRespuesta validarSolicitud(EvnTurnoManualRegistro evento) throws EventoException {
		String idSolicitud = evento.getIdSolicitud();
		SolicitudPk solId = new SolicitudPk();
		solId.idSolicitud = idSolicitud;

		Estacion estacion = evento.getEstacion();
		EstacionReciboPk estacionRecibo = new EstacionReciboPk();
		estacionRecibo.idEstacion = estacion.getEstacionId();

		SolicitudRegistro solicitud = null;
		long valorSecuencial;
		long idProceso = Long.valueOf(CProceso.PROCESO_REGISTRO).longValue();

		try {
			Usuario user = null;
			try {
				user = evento.getUsuarioNec();
			} catch (Exception ex) {
				//logger.debug("No se encontro usuario del evento");
			}
			//Se manda recibo por default el proceso de registro
			valorSecuencial = hermod.getNextNumeroReciboSinAvanzar(estacionRecibo, user, idProceso);
			solicitud = (SolicitudRegistro) hermod.getSolicitudById(idSolicitud);

			//validar si la solicitud tiene un pago
			Pago pago;
			List liquidaciones = solicitud.getLiquidaciones();

			if (liquidaciones != null) {
				Liquidacion liq = (Liquidacion) liquidaciones.get(0);

				if (liq != null) {
					pago = liq.getPago();

					if (pago == null) {
						throw new Exception("La solicitud no tiene un pago asociado.");
					}
				} else {
					throw new Exception("La solicitud no tiene una liquidacion asociada.");
				}
			} else {
				throw new Exception("La solicitud no tiene una lista de liquidaciones asociada.");
			}

			//mirar si ya tiene un turno asociado
			Turno turno = hermod.getTurnoBySolicitud(solId);

			if (turno != null) {
				throw new Exception("La solicitud ya tiene un turno asociado.");
			}

			//obtener checkitmes y agregarlos a la solicitud.
			SubtipoSolicitud subsol = solicitud.getSubtipoSolicitud();
			List checkitems = hermod.getCheckItemsBySubtipoSolicitud(subsol);
			Iterator ic = checkitems.iterator();

			for (; ic.hasNext();) {
				CheckItem check = (CheckItem) ic.next();
				subsol.addCheckItem(check);
			}

			solicitud.setSubtipoSolicitud(subsol);
		} catch (HermodException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"La solicitud es invalida o no existe:" + ep.toString());
			throw new SolicitudInvalidaRegistroException("La solicitud es invalida o no existe", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"No se pudo validar:" + ep.toString());
			throw new SolicitudInvalidaRegistroException("No se pudo validar", e);
		}

		EvnRespTurnoManualRegistro respuesta = new EvnRespTurnoManualRegistro(solicitud, EvnRespTurnoManualRegistro.VALIDAR_SOLICITUD);
		respuesta.setValorSecuencial(valorSecuencial);

		return respuesta;
	}

	/**
	    * Este metodo hace el llamado al negocio para que se incremente el secuencial de recibos.
	    * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	    * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 * @throws EventoException
	    */
	private EventoRespuesta incrementarSecuencial(EvnTurnoManualRegistro evento) throws EventoException {
		EvnRespTurnoManualRegistro evRespuesta = null;
		long valorSecuencial;

		long idProceso = Long.valueOf(CProceso.PROCESO_REGISTRO).longValue();
		
		try {
			Usuario user = evento.getUsuarioNec();
			Estacion estacion = evento.getEstacion();
			EstacionReciboPk estacionRecibo = new EstacionReciboPk();
			estacionRecibo.idEstacion = estacion.getEstacionId();
			valorSecuencial = hermod.avanzarNumeroRecibo(estacionRecibo, 1, idProceso);
			valorSecuencial = hermod.getNextNumeroReciboSinAvanzar(estacionRecibo,user, idProceso);
			
			//boolean motivo = hermod.almacenarMotivoIncrementoSecuencial(evento.getUsuarioNec(), valorSecuencial, evento.getMotivo());
			hermod.almacenarMotivoIncrementoSecuencial(evento.getUsuarioNec(), valorSecuencial, evento.getMotivo());
		} catch (HermodException e) {
			throw new EventoException("No se pudo obtener el secuencial de recibos.", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespTurnoManualRegistro(new Long(valorSecuencial), EvnRespTurnoManualRegistro.INCREMENTAR_SECUENCIAL_RECIBO);

		return evRespuesta;
	}

	/**
	* Este metodo hace el llamado al negocio para que se incremente el secuencial de recibos.
	* @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	* @throws EventoException
	*/
	private EventoRespuesta crearTurnoRegistro(EvnTurnoManualRegistro evento) throws EventoException {
        
		EvnRespTurnoManualRegistro evRespuesta = null;
		SolicitudRegistro sol = evento.getSolicitudRegistro();
		Usuario usuario = evento.getUsuarioSIR();
		Turno turno = null;
		String fechaImp = evento.getFechaImpresion();
		Circulo circulo = evento.getCirculo();
		LiquidacionTurnoRegistro liq = null;
		String uid;

		//obtener el pago de la solicitud
		Pago pago = null;
		List liquidaciones = sol.getLiquidaciones();

		if ((liquidaciones != null) && (liquidaciones.size() > 0)) {
			liq = (LiquidacionTurnoRegistro) liquidaciones.get(0);
			pago = liq.getPago();
		}

		if (pago == null) {
			throw new CrearTurnoException("el pago de la solicitud es null");
		}

		//inicializar el UID
		uid = evento.getUID();

		try {
			/*SECCION CREACION del turno registro (este tambien crea los turnos de los certificados asociados)*/
			turno = hermod.crearTurnoRegistro(sol, usuario);

			/*FIN DE SECCION*/
			/*SECCION IMPRESION del recibo de solicitud*/

			//inicilizar pago para el imprimible de recibo
			if (liq != null) {
				SolicitudRegistro soli = (SolicitudRegistro) hermod.getSolicitudById(sol.getIdSolicitud());
				soli.setTurno(turno);
				liq.setSolicitud(soli);
				pago.setLiquidacion(liq);
			}

			//crear imprimible
			ImprimibleRecibo imprimible = new ImprimibleRecibo(pago, circulo, fechaImp,CTipoImprimible.RECIBO);

			//mandar a imprimir dandole el uid
			this.imprimir(imprimible, uid);

			/*FIN DE SECCION*/
		} catch (HermodException e) {
			throw new EventoException("No se pudo obtener el secuencial de recibos.", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANTurnoManualRegistro.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespTurnoManualRegistro(turno, EvnRespTurnoManualRegistro.CREAR_TURNO);

		return evRespuesta;
	}

	/**
	 * @param imprimible
	 * @param uid
	 */
	private void imprimir(ImprimibleBase imprimible, String uid) {
		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;

		//INGRESO DE INTENTOS DE IMPRESION
		try {
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);

			if (intentosImpresion != null) {
				Integer intentos = new Integer(intentosImpresion);
				maxIntentos = intentos.intValue();
			} else {
				Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
				maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);

			if (intentosImpresion != null) {
				Integer esperaInt = new Integer(esperaImpresion);
				espera = esperaInt.intValue();
			} else {
				Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		} catch (Throwable t) {
			Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();
		}
                
		Bundle bundle = new Bundle(imprimible);
                if(imprimible.getTipoImprimible().equals(CTipoImprimible.RECIBO)){
                    try{
                        ImprimibleRecibo imprimibleR = (ImprimibleRecibo) imprimible;
                        String copyActive = hermod.getCopiaImp(imprimibleR.getCirculo().getIdCirculo());
                        if(copyActive.equals(CHermod.ACTIVE)){
                             bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                        }
                    } catch (Throwable ex) {
                        Logger.getLogger(ANTurnoManualRegistro.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                }
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(uid, bundle, maxIntentos, espera);

		} catch (Throwable t) {
			t.printStackTrace();
		}
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
}
