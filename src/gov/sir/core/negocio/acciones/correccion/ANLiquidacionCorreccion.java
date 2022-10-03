package gov.sir.core.negocio.acciones.correccion;

import gov.sir.core.eventos.correccion.EvnCorreccion;
import gov.sir.core.eventos.correccion.EvnRespCorreccion;
import gov.sir.core.negocio.acciones.excepciones.ErrorImpresionException;
import gov.sir.core.negocio.acciones.excepciones.LiquidacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.MatriculaInvalidaCorreccionException;
import gov.sir.core.negocio.acciones.excepciones.PagoInvalidoException;
import gov.sir.core.negocio.acciones.excepciones.PagoNoProcesadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCorreccion;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleRecibo;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;
         /*
        * @author Carlos Torres Urina
        * @caso matis: 12291: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        * @changed: permite la utilizacion de GeneralSIR
        */
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;

/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnCorreccion</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespCorreccion</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas
 * con el avance del workflow del proceso de correcciones y llamar a los servicios
 * que se requieren en cada fase del proceso.
 * @author ppabon, jvelez
 */
public class ANLiquidacionCorreccion extends SoporteAccionNegocio {

	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * Instancia de Hermod
	 */
	private PrintJobsInterface printJobs;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANCorrección.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANLiquidacionCorreccion() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod =
				(HermodServiceInterface) service.getServicio("gov.sir.hermod");

			forseti =
				(ForsetiServiceInterface) service.getServicio(
					"gov.sir.forseti");

			printJobs =
				(PrintJobsInterface) service.getServicio("gov.sir.print");

		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException(
				"Error instanciando el servicio Hermod",
				e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}

		if (printJobs == null) {
			throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
		}

	}

	/**
	* Recibe un evento de correciones y devuelve un evento de respuesta
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCorreccion</CODE>
	* @throws EventoException cuando ocurre un problema que no se pueda manejar
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCorreccion</CODE>
	* @see gov.sir.core.eventos.comun.EvnCorreccion
	* @see gov.sir.core.eventos.comun.EvnRespCorreccion
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnCorreccion evento = (EvnCorreccion) e;

		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnCorreccion.VALIDAR_MATRICULA)) {
			return validarMatricula(evento);
		}
                else if (evento.getTipoEvento().equals(EvnCorreccion.CREAR_SOLICITUD)) {
			return crearSolicitud(evento);
		}

		return null;
	}


	/**
	 * Método que válida que una matrícula exista en la base de datos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta validarMatricula(EvnCorreccion evento) throws EventoException {
		String matricula = evento.getFolio().getIdMatricula();

		try {
			if (forseti.existeFolio(matricula.trim())) {
				return new EvnRespCorreccion(evento.getUsuario(), matricula, EvnRespCorreccion.VALIDAR_MATRICULA);
			}
			throw new MatriculaInvalidaCorreccionException("La matrícula " + matricula + " no existe");
		} catch (MatriculaInvalidaCorreccionException e) {
			throw e;
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANLiquidacionCorreccion.class,"No se pudo encontrar el servicio:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Servicio no encontrado", e);
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANLiquidacionCorreccion.class,"Error en el servicio para validar la matrícula:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Error en el servicio para validar la matrícula", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANLiquidacionCorreccion.class,"Se produjo un error validando la matrícula:" + ep.toString());
			throw new MatriculaInvalidaCorreccionException("Se produjo un error validando la matrícula", e);
		}
	}
        
	/**
	 * Método que crea una instancia del proceso de correcciones.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EvnRespCorreccion crearSolicitud(EvnCorreccion evento)
		throws EventoException {


		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		Solicitud solicitud = null;
		Pago pago = null;
		Turno turno = null;
		LiquidacionTurnoCorreccion liquidacion = new LiquidacionTurnoCorreccion();

		try {
			solicitud = hermod.crearSolicitud(evento.getSolicitudCorreccion());

			if (solicitud == null) {
				throw new LiquidacionNoEfectuadaException("No existe solicitud asociada");
			}

			List solFolio = solicitud.getSolicitudFolios();
			List matriculas = new Vector();
			Iterator itSolFolio = solFolio.iterator();

			while (itSolFolio.hasNext()) {
				SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
				matriculas.add(sol.getFolio().getIdMatricula());
			}

			forseti.validarMatriculas(
				hermod.getValidacionesSolicitud(solicitud),
				matriculas);
			liquidacion.setSolicitud(solicitud);
			liquidacion.setUsuario(evento.getUsuarioSIR());
			LiquidacionTurnoCorreccion auxLiquidacion =
				(LiquidacionTurnoCorreccion) hermod.liquidar(liquidacion);

			DocPagoEfectivo docPago =
				new DocPagoEfectivo(liquidacion.getValor());

			AplicacionPago appEfectivo = new AplicacionPago();
			appEfectivo.setIdLiquidacion(liquidacion.getIdLiquidacion());
			appEfectivo.setIdSolicitud(solicitud.getIdSolicitud());
			appEfectivo.setValorAplicado(liquidacion.getValor());
			appEfectivo.setDocumentoPago(docPago);

			pago = new Pago(auxLiquidacion, null);
			pago.addAplicacionPago(appEfectivo);
			pago.setIdLiquidacion(liquidacion.getIdLiquidacion());
			pago.setIdSolicitud(solicitud.getIdSolicitud());
			pago.setLiquidacion(auxLiquidacion);
			pago.setUsuario(evento.getUsuarioSIR());

		} catch (HermodException e) {
			e.printStackTrace(System.out);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANLiquidacionCorreccion.class,
				"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			if (validarPago(pago,
				hermod.getRangoAceptacionPago(
					solicitud.getProceso().getNombre()))) {
				pago = hermod.validarPago(pago);
			}
		} catch (HermodException e) {
			throw new PagoInvalidoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANLiquidacionCorreccion.class,
				"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			Estacion estacion = evento.getEstacion();
			turno = hermod.procesarPago(pago, estacion.getEstacionId(), "imp", pago.getUsuario(), null, false);

			/*
			pago.getLiquidacion().getSolicitud().setTurno(turno);

			//List liquidaciones = turno.getSolicitud().getLiquidaciones();
			String numRecibo = ((Liquidacion) liquidaciones.get(liquidaciones.size() - 1)).getPago().getNumRecibo();
			pago.setNumRecibo(numRecibo);
			String fechaImp = this.getFechaImpresion();
			ImprimibleRecibo iRec = new ImprimibleRecibo(pago,fechaImp);
			Bundle bundle = new Bundle(iRec);
			printJobs.enviar(evento.getUID(), bundle);
			*/

			List liquidaciones = turno.getSolicitud().getLiquidaciones();
			/*String numRecibo =
				((Liquidacion) liquidaciones.get(liquidaciones.size() - 1))
					.getPago()
					.getNumRecibo();*/
            //EstacionRecibo.ID estacionReciboID = new EstacionRecibo.ID();
            //estacionReciboID.idEstacion = estacion.getEstacionId();
            //String numRecibo = String.valueOf(hermod.getNextNumeroReciboSinAvanzar(estacionReciboID));
			//pago.setNumRecibo(numRecibo);

            //Pago.ID pagoID = new Pago.ID();
            //pagoID.idLiquidacion = pago.getIdLiquidacion();
            //pagoID.idSolicitud = pago.getIdSolicitud();
            //hermod.setNumeroReciboPago(pagoID, numRecibo);

			pago.getLiquidacion().getSolicitud().setTurno(turno);
			Circulo circulo = evento.getCirculo();
			String fechaImpresion = this.getFechaImpresion();
			ImprimibleRecibo impRec =
				new ImprimibleRecibo(pago, circulo, fechaImpresion,CTipoImprimible.RECIBO);

			String uid = evento.getUID();

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


			Bundle bundle = new Bundle(impRec);
                        String copyActive = hermod.getCopiaImp(impRec.getCirculo().getIdCirculo());
                        if(copyActive.equals(CHermod.ACTIVE)){
                             bundle.setNumeroCopias(Integer.parseInt(hermod.getValorLookupCodes(COPLookupTypes.IMPRESION_RECIBO, COPLookupCodes.IMPRESION_RECIBO)));
                        }
			try {
				//se manda a imprimir el recibo por el identificador unico de usuario
				printJobs.enviar(uid, bundle, maxIntentos, espera);
			} catch (Throwable t) {
				t.printStackTrace();
			}

		} catch (HermodException e1) {
			throw new PagoNoProcesadoException(e1.getMessage(), e1);
		} catch (PrintJobsException e2) {
			if (turno != null) {
				throw new ErrorImpresionException(
					"No se pudo imprimir el recibo:" + e2.getMessage(),
					turno.getIdWorkflow());
			}
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANLiquidacionCorreccion.class,
				"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespCorreccion eventoRespuesta =
			new EvnRespCorreccion(
				evento.getUsuario(),
				turno,
				EvnRespCorreccion.CREAR_SOLICITUD);

		return eventoRespuesta;
	}

	/**
	 * Este método valida que todas las aplicaciones de pago tengan saldo válido
	 * @param pago EL pago que se va a validar
	 * @param precision El rango de tolerancia
	 * @return boolean true si el pago es válido
	 * @throws PagoInvalidoException si el pago no es valido
	 */
	private boolean validarPago(Pago pago, double precision)
		throws PagoInvalidoException {
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
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return
	 */
	protected String getFechaImpresion() {

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

}
