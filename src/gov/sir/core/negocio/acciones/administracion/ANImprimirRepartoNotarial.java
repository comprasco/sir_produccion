package gov.sir.core.negocio.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnImprimirRepartoNotarial;
import gov.sir.core.eventos.administracion.EvnRespImprimirRepartoNotarial;
import gov.sir.core.negocio.acciones.excepciones.ImpresionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CReparto;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTurno;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleActaRepartoMinutas;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleCertificado;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaRepartoMinuta;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleFolioSimple;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.FenrirException;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.util.DateFormatUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 * Acción de negocio encargada de manejar los eventos de tipo
 * <code>EvnTrasladoTurno</code> destinados a manejar
 * el traslado de turnos entre usuarios
 * @author jmendez
 */
public class ANImprimirRepartoNotarial extends SoporteAccionNegocio {

	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;

	/** Instancia de la intefaz de PrntJobs  */
	private PrintJobsInterface printJobs;

	/**
	 * Constructor encargado de inicializar los servicios a ser utilizados por la
	 * acción de Negocio
	 * @throws EventoException
	 */
	public ANImprimirRepartoNotarial() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Forseti");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

		try {
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

			if (fenrir == null) {
				throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Fenrir");
		}

		try {
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

			if (printJobs == null) {
				throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio PrintJobs", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
		}
	}

	/**
	 * Manejador de eventos de tipo <code>EvnTrasladoTurno</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento
	 * que llega como parámetro.
	 *
	 * @param evento <code>EvnRespTrasladoTurno</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 *
	 * @return <code>EventoRespuesta</code> con la información resultante de la
	 * ejecución de la acción sobre los servicios
	 *
	 * @throws <code>EventoException</code>
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnImprimirRepartoNotarial evento = (EvnImprimirRepartoNotarial) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}
		String tipoEvento = evento.getTipoEvento();

		if (tipoEvento.equals(EvnImprimirRepartoNotarial.IMPRIMIR_ACTA)) {
			return imprimirActa(evento);
		} else if (tipoEvento.equals(EvnImprimirRepartoNotarial.IMPRIMIR_CARATULA)) {
			return imprimirCaratula(evento);
		} else if (tipoEvento.equals(EvnImprimirRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO)) {
			return obtenerImpresorasCirculo(evento);
		} else if (tipoEvento.equals(EvnImprimirRepartoNotarial.OBTENER_ULTIMO_TURNO_IMPRESO)) {
            return obtenerUltimoTurnoImpreso(evento);
        } else if (tipoEvento.equals(EvnImprimirRepartoNotarial.OBTENER_ULTIMO_TURNO_IMPRESO_PROCESO)) {
            return obtenerUltimoTurnoImpresoProceso(evento);
		}

		return null;
	}

   // -----------------------------------------------------------------------------
   private String
   print_FootUtils_BuildUserName( long userId ) {
      return "" + userId;
   } // end method

   private String
   print_FootUtils_BuildUserName( Long userId ) {
      if( null == userId ) {
         return getNullableString( true );
      }
      return print_FootUtils_BuildUserName( userId.longValue() ) ;
   } // end method

   private String
   print_FootUtils_BuildUserName( gov.sir.core.negocio.modelo.Usuario user ) {
      if( null == user ) {
         return getNullableString( true );
      }
      return print_FootUtils_BuildUserName( user.getIdUsuario() ) ;
   } // end method


   public static String
   Nvl( String string , String replaceIfNull ) {
      return ( null == string )?( replaceIfNull ):( string );
   } // end-method: Nvl

   public static String
   getNullableString( boolean treatBlankAsNull ) {
      return( ( treatBlankAsNull )?( "" ):( null ) );
   } // end-method: Nvl

// -----------------------------------------------------------------------------

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta obtenerUltimoTurnoImpresoProceso(EvnImprimirRepartoNotarial evento) throws EventoException{
		org.auriga.core.modelo.transferObjects.Usuario usuario = evento.getUsuario();
		long proceso = evento.getProceso();
		try {
			long idUsuario = fenrir.darIdUsuario(usuario.getUsuarioId());
			UsuarioPk oidUsu=new UsuarioPk();
			oidUsu.idUsuario=idUsuario;
			ProcesoPk oidProc=new ProcesoPk();
			oidProc.idProceso = proceso;
			CirculoPk oidCirc=new CirculoPk();
			oidCirc.idCirculo=evento.getCirculo().getIdCirculo();

			Turno turno = hermod.getUltimoTurnoProcesoUsuario(oidUsu,oidProc,oidCirc);
			List turnosSolicitudesAsociadas = new ArrayList();
			if (turno!=null){
				if (proceso==Long.parseLong(CProceso.PROCESO_CERTIFICADOS_MASIVOS)||proceso==Long.parseLong(CProceso.PROCESO_REGISTRO)){
					Solicitud solicitud=turno.getSolicitud();
					if (solicitud instanceof SolicitudCertificadoMasivo){
						turnosSolicitudesAsociadas = new ArrayList();
						SolicitudCertificadoMasivo solCert = (SolicitudCertificadoMasivo)solicitud;
						List solHijos = solCert.getSolicitudesHijas();
						if(!solHijos.isEmpty()){
							for(Iterator iter = solHijos.iterator(); iter.hasNext();){
								SolicitudAsociada solAsoc = (SolicitudAsociada)iter.next();
								SolicitudPk solid = new SolicitudPk();
								solid.idSolicitud = solAsoc.getIdSolicitud1();
								Turno turnoAsoc = forseti.getTurnoBySolicitud(solid);
								if(turnoAsoc != null){
									turnosSolicitudesAsociadas.add(turnoAsoc.getIdWorkflow());
								}
							}
						}
					}else if (solicitud instanceof SolicitudRegistro){
						turnosSolicitudesAsociadas = new ArrayList();
						SolicitudRegistro solCert = (SolicitudRegistro)solicitud;
						List solHijos = solCert.getSolicitudesHijas();
						if(!solHijos.isEmpty()){
							for(Iterator iter = solHijos.iterator(); iter.hasNext();){
								SolicitudAsociada solAsoc = (SolicitudAsociada)iter.next();
								SolicitudPk solid = new SolicitudPk();
								solid.idSolicitud = solAsoc.getIdSolicitud1();
								Turno turnoAsoc = forseti.getTurnoBySolicitud(solid);
								if(turnoAsoc != null){
									turnosSolicitudesAsociadas.add(turnoAsoc.getIdWorkflow());
								}
							}
						}
					}
				}
			}else{
				throw new ImpresionException("No hay recibos para reimprimir de ese proceso");
			}

			EvnRespImprimirRepartoNotarial resp = new EvnRespImprimirRepartoNotarial(turno.getIdWorkflow(),EvnRespImprimirRepartoNotarial.ULTIMO_TURNO_PROCESO);
			resp.setTurnosAsociados(turnosSolicitudesAsociadas);
			return resp;
		} catch (Throwable e) {
			Log.getInstance().error(ANImprimirRepartoNotarial.class,e);
            throw new EventoException("Error al obtener el último turno impreso: " + e.getMessage());
		}
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * impresión de un folio específico
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespImprimirRepartoNotarial imprimirFolio(EvnImprimirRepartoNotarial evento) throws   EventoException {

		List usuarios = null;
		ImprimibleFolioSimple impFolioSimple = null;
		Usuario usuarioBloqueo = null;
		try {
			Folio folio = evento.getFolio();

			String matricula = folio.getIdMatricula();
			String zonaReg = forseti.getZonaRegistral(matricula);
			FolioPk fid = new FolioPk();
			fid.idMatricula = matricula;
			Folio folioSimple = forseti.getFolioByID(fid);
			
			usuarioBloqueo = forseti.getBloqueoFolio(fid);
			
			if(usuarioBloqueo != null)
			{
				if(evento.getUsuarioNeg() != null)
				{
					if ( evento.getUsuarioNeg().getIdUsuario() != usuarioBloqueo.getIdUsuario() )
					{
						throw new EventoException("Sólo el usuario dueño del bloqueo puede imprimir el folio.");
					}
				}
			}
			

			//se validar si el folio es temporal o no
			if (folioSimple == null) 
			{
				throw new EventoException("El folio " + folio.getIdMatricula() +  " es Temporal.");
			}
			
			if (folioSimple == null) 
			{
				throw new EventoException("El folio " + folio.getIdMatricula() +  " es Temporal.");
			}
			
			
			
			List padres=forseti.getFoliosPadre(fid);
			List hijos=forseti.getFolioHijosEnAnotacionesConDireccionOrdenadoAnotaciones(fid);
                        /**
                        * @author: David Panesso
                        * @change: 1155.MODIFICAR.IMPRIMIBLE.CALIFICACION.CORRECCION
                        * Nuevo listado de folios derivados
                        **/
                        List<FolioDerivado> foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(fid);

			String fechaImpresion = this.getFechaImpresion();
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
			impFolioSimple = new ImprimibleFolioSimple(folioSimple, padres, hijos, foliosDerivadoHijos, fechaImpresion, CTipoImprimible.FOLIO_SIMPLE, tbase1, tbase2);
                        /* @autor          : JATENCIA 
                         * @mantis         : 0014985 
                         * @Requerimiento  : 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas  
                         * @descripcion    : Llamado de la función que organiza y visualiza la
                         *                   información de traslado del folio.
                         */
                        Traslado traslado = hermod.getFolioDestinoTraslado(matricula);
                        impFolioSimple.setInfoTraslado(traslado);
                        /* Fin del bloque */

			//se manda a imprimir el recibo por el identificador unico de usuario
		} catch (Throwable t) {
			t.printStackTrace();
			throw new EventoException("Error al imprimir el folio: " + t.getMessage());

		}

		String uid = evento.getUid();

		if (impFolioSimple == null)
			throw new EventoException("Error al imprimir el folio: " + "el imprimible es null");

        this.imprimir(impFolioSimple,uid);

        EvnRespImprimirRepartoNotarial evRespuesta =
			new EvnRespImprimirRepartoNotarial(usuarios, EvnRespImprimirRepartoNotarial.IMPRESION_CARATULA_OK);
		return evRespuesta;
	}

	private EvnRespImprimirRepartoNotarial obtenerUltimoTurnoImpreso(EvnImprimirRepartoNotarial evento) throws EventoException {

        Pago pago=null;
        int maxIntentos;
        int espera;
        Turno turno = null;
        Usuario usuNeg=evento.getUsuarioNeg();
        try {

            org.auriga.core.modelo.transferObjects.Usuario usuario = evento.getUsuario();

            long idUsuario = fenrir.darIdUsuario(usuario.getUsuarioId());
            List rolesUsuario = fenrir.darRolUsuario(idUsuario);

            if(rolesUsuario != null) {
                List estacionesUsuario = fenrir.darEstacionUsuario(idUsuario, (Rol)rolesUsuario.get(0));
                Estacion estacion = (Estacion)estacionesUsuario.get(0);
                EstacionReciboPk estacionID = new EstacionReciboPk();
                estacionID.idEstacion = estacion.getEstacionId();
                EstacionRecibo estacionRecibo = hermod.getEstacionRecibo(estacionID);
                long recibo = hermod.getNextNumeroReciboSinAvanzar(estacionID,usuNeg, turno.getIdProceso());
                if(recibo == estacionRecibo.getNumeroInicial())
                    recibo = estacionRecibo.getNumeroFinal();
                else
                    recibo--;
                String numRecibo = String.valueOf(recibo);
                pago = hermod.getPagoByNumeroRecibo(numRecibo);

                Solicitud solicitud = pago.getLiquidacion().getSolicitud();
                SolicitudPk solicitudId = new SolicitudPk();
                solicitudId.idSolicitud = solicitud.getIdSolicitud();
                turno = hermod.getTurnoBySolicitud(solicitudId);
            }


        } catch (Throwable t) {
            t.printStackTrace();
            throw new EventoException("Error al obtener el último turno impreso: " + t.getMessage());
        }

        EvnRespImprimirRepartoNotarial evRespuesta =
            new EvnRespImprimirRepartoNotarial(turno, EvnRespImprimirRepartoNotarial.OBTENER_ULTIMO_TURNO_IMPRESO_OK);
        return evRespuesta;
    }

	private void imprimir(ImprimibleBase imprimible, String uid)throws EventoException
	{
		int maxIntentos;
		int espera;

		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.

		//INGRESO DE INTENTOS DE IMPRESION
		try
		{

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
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
		    String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_FOLIO);
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

		Bundle bundle = new Bundle(imprimible);
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(uid, bundle, maxIntentos, espera);

		} catch (PrintJobsException t) {
			t.printStackTrace();
			if(t.getIdImpresion()!=0){
				throw new EventoException("Se presentaron problemas de comunicación al realizar la impresión del folio, coloque el número ("+t.getIdImpresion()+") en el aplicativo de impresión SIR, para realizar esta impresión",t);
			}
		}catch(Throwable t){
		}

	}

//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * impresión de un certificado específico
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespImprimirRepartoNotarial imprimirCaratula(EvnImprimirRepartoNotarial evento) throws EventoException
	{
		String idCirculoUsuario = null;
		List usuarios = null;
		Circulo circulo = evento.getCirculo();
		Turno turno = evento.getTurno();
		Turno turnoPersistente = null;
			
		Usuario usuario = null;
		try
		{
			usuario = fenrir.getUsuario(evento.getUsuario());

			if(!usuario.getUsuarioCirculos().isEmpty()){
				UsuarioCirculo usuarioCirculo = (UsuarioCirculo)usuario.getUsuarioCirculos().get(0);
				idCirculoUsuario = usuarioCirculo.getIdCirculo();
			}
			else
			{
				throw new ImpresionException("El usuario no tiene círculos asociados");
			}
	
			turnoPersistente = hermod.getTurnobyWF(turno.getIdWorkflow());
				
			//comprobar que el turno no esta anulado
			if (turnoPersistente.getAnulado()!= null && turnoPersistente.getAnulado().equals(CTurno.TURNO_ANULADO)){
				throw new EventoException("El turno esta anulado");
			}
	
			String idCirculoTurno = turnoPersistente.getCirculoproceso().getIdCirculo();
	
			//Restricciones:
			//Se debe validar que el turno ingresado, pertenece al circulo del usuario, y es un turno de certificado
			if(!idCirculoTurno.equals(idCirculoTurno)){
				throw new ImpresionException("El círculo del usuario no corresponde con el círculo del turno.");
			}
			//Restricciones:
			//debe ser un turno de certificado
			Solicitud solicitud = turnoPersistente.getSolicitud();
			int numImpresionesSolicitadas = evento.getNumeroImpresiones();
	
			if(!(solicitud instanceof SolicitudRepartoNotarial)){
				throw new ImpresionException("El turno no es de reparto notarial.");
			}
		}
		catch (Throwable e)
		{
			Log.getInstance().error(ANImprimirRepartoNotarial.class,e);
		}
				
		//TODO Mapear el numero de impresiones de caratula
		//int numImpresionesCaratula=solicitudRepNot.getNumReimpresionesRecibo();

		boolean resultado = false;
		int maxIntentos;
		int espera;

		//SE OBTIENE EL NÚMERO DE INTENTOS DE IMPRESIÓN
		String intentosImpresion;
		try 
		{
			intentosImpresion = hermod.getNumeroIntentosImpresion(gov.sir.core.negocio.modelo.constantes.CImpresion.IMPRIMIR_RECIBO);
		
			if (intentosImpresion != null)
			{
				Integer intentos = new Integer(intentosImpresion);
				maxIntentos = intentos.intValue();
			}
			else
			{
				Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
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
				Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		} 
		catch (Throwable e) 
		{
			Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();
		}

//			SE REALIZAN LAS IMPRESIONES DEL REPARTO
					try {
						String fechaImpresion = this.getFechaImpresion();

						java.util.Hashtable minutas = new Hashtable();
						List turnos = new java.util.ArrayList();
						turnos.add(turno.getIdWorkflow());
						
						Iterator it = turnos.iterator();
						while (it.hasNext())
						{
							String idWorkflow = (String) it.next();
							gov.sir.core.negocio.modelo.Minuta min = hermod.getMinutaByTurnoWF(idWorkflow);
							if (min != null) {
								minutas.put(idWorkflow, min);
							}
						}

						Minuta temp = new Minuta();
						if (minutas != null) {
							Enumeration en = minutas.keys();
							if (en.hasMoreElements()) {
								String key = (String) en.nextElement();
								temp = (Minuta) minutas.get(key);
							}
						}

						java.util.Calendar c = java.util.Calendar.getInstance();

						//IMPRIMIR EL RECIBO DEL REPARTO POR CADA MINUTA REPARTIDA. SE IMPRIMEN DOS VECES.
						Enumeration idturnos = minutas.keys();

						int veces = evento.getNumeroImpresiones();
						while (idturnos.hasMoreElements()) {

							String idWorkflow = (String) idturnos.nextElement();
							Log.getInstance().info(ANImprimirRepartoNotarial.class,"GENERANDO CONSTANCIA PARA " + idWorkflow);
							Minuta minuta = (Minuta) minutas.get(idWorkflow);

							gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaRepartoMinuta impReparto = new ImprimibleConstanciaRepartoMinuta(minuta, circulo, fechaImpresion, idWorkflow,CTipoImprimible.REPARTO);

							Bundle bundle = new Bundle(impReparto);
							bundle.setNumeroCopias(veces);

							//SE REQUIREN IMPRIMIR DOS COPIAS

							try {
								//se manda a imprimir el recibo por el identificador unico de usuario
								printJobs.enviar(circulo.getIdCirculo(), bundle, maxIntentos, espera);
							} catch (Throwable t) {
								t.printStackTrace();
							}


						}
			}
			catch(Throwable e){
			Log.getInstance().error(ANImprimirRepartoNotarial.class,e.getMessage());
			}
			try
			{
				//Se agrega la nota informativa al turno.
				String notaId = evento.getTipoNota();
				String notaDescripcion = evento.getDescripcionNota();
	
	
				TipoNota tipoNota = new TipoNota();
				tipoNota.setIdTipoNota(notaId);
	
				Nota notaAgregada = new Nota();
				notaAgregada.setTiponota(tipoNota);
				notaAgregada.setDescripcion(notaDescripcion);
				notaAgregada.setAnio(turnoPersistente.getAnio());
				notaAgregada.setIdFase("CER_REIMPRESION_CERTIFICADOS");
				notaAgregada.setIdCirculo(idCirculoUsuario);
				notaAgregada.setIdProceso(1);
				notaAgregada.setTime(new Date());
				notaAgregada.setUsuario(usuario);
	
				TurnoPk idTurno = new TurnoPk();
				idTurno.anio = turnoPersistente.getAnio();
				idTurno.idCirculo = turnoPersistente.getIdCirculo();
				idTurno.idProceso = turnoPersistente.getIdProceso();
				idTurno.idTurno = turnoPersistente.getIdTurno();
	
				hermod.addNotaToTurno(notaAgregada, idTurno);
			}
			catch(Throwable e)
			{
				Log.getInstance().error(ANImprimirRepartoNotarial.class,e.getMessage());
			}
			
			EvnRespImprimirRepartoNotarial evRespuesta =
				new EvnRespImprimirRepartoNotarial(usuarios, EvnRespImprimirRepartoNotarial.IMPRESION_CARATULA_OK);
		
			return evRespuesta;
	}
	
	/**
	 * Este método se encarga de invocar los servicios necesarios para la
	 * impresión de un certificado específico
	 * @return <code>EvnRespTrasladoTurno</code>
	 * @throws <code>EventoException</code>
	 */
	private EvnRespImprimirRepartoNotarial imprimirActa(EvnImprimirRepartoNotarial evento) throws EventoException
	{
		String idCirculoUsuario = null;
		List usuarios = null;
		Circulo circulo = evento.getCirculo();
		RepartoNotarial repartoNotarial = evento.getRepartoNotarial();
		RepartoNotarial repartoNotarialPersistente = new RepartoNotarial();
			
		Usuario usuario;
		try
		{
			usuario = fenrir.getUsuario(evento.getUsuario());

			if(!usuario.getUsuarioCirculos().isEmpty()){
				UsuarioCirculo usuarioCirculo = (UsuarioCirculo)usuario.getUsuarioCirculos().get(0);
				idCirculoUsuario = usuarioCirculo.getIdCirculo();
			}
			else
			{
				throw new ImpresionException("El usuario no tiene círculos asociados");
			}
			
			RepartoNotarialPk id = new RepartoNotarialPk();
			id.idRepartoNotarial = repartoNotarial.getIdRepartoNotarial();
			repartoNotarialPersistente = hermod.getRepartoNotarialById(id);
				
			int numImpresionesSolicitadas = evento.getNumeroImpresiones();
		}
		catch (Throwable e)
		{
			Log.getInstance().error(ANImprimirRepartoNotarial.class,e);
		}
				
		//TODO Mapear el numero de impresiones de caratula
		//int numImpresionesCaratula=solicitudRepNot.getNumReimpresionesRecibo();

		boolean resultado = false;
		int maxIntentos;
		int espera;

		//SE OBTIENE EL NÚMERO DE INTENTOS DE IMPRESIÓN
		String intentosImpresion;
		try 
		{
			intentosImpresion = hermod.getNumeroIntentosImpresion(gov.sir.core.negocio.modelo.constantes.CImpresion.IMPRIMIR_RECIBO);
		
			if (intentosImpresion != null)
			{
				Integer intentos = new Integer(intentosImpresion);
				maxIntentos = intentos.intValue();
			}
			else
			{
				Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
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
				Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		} 
		catch (Throwable e) 
		{
			Integer intentosDefault = new Integer(CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer(CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();
		}

		//SE IMPRIME EL ACTA, LOS PARAMETROS DE CIUDAD, DIRECTOR Y COORDINADOR DEBEN CAMBIARSE POR LOS DE LOS NOMBRES REALES.
		
		Hashtable minutas = new Hashtable();
		Minuta temp = new Minuta();
		
		for (Iterator iter = repartoNotarialPersistente.getMinutas().iterator(); iter.hasNext();) 
		{
			Minuta element = (Minuta) iter.next();
			if (element != null) 
			{
				temp = element;
			}
		}
		try {
			if (temp.getRepartoNotarial()==null && repartoNotarial != null) {
				temp.setRepartoNotarial(repartoNotarial);
			}
			CirculoPk cid = new CirculoPk();
			cid.idCirculo = circulo.getIdCirculo();
			
			TextoPk id = new TextoPk();
			id.idCirculo = circulo.getIdCirculo();
			id.idLlave = CReparto.TEXTO_ACTA_REP_NOTARIAL;
			Texto texto = forseti.getTexto(id);
			
			SolicitudPk solID = new SolicitudPk();
			solID.idSolicitud = temp.getIdMinuta();
			
			Turno turno = hermod.getTurnoBySolicitud(solID);
			
			temp = hermod.getMinutaByTurnoWF(turno.getIdWorkflow());
			
			ActoresRepartoNotarialPk idActores = new ActoresRepartoNotarialPk();
			idActores.idCirculo = circulo.getIdCirculo();
			ActoresRepartoNotarial actoresRepartoNotarial = forseti.getActoresRepartoNotarial(idActores);
			
			String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
			FirmaRegistrador firmaRegistrador = null;
	
			firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
	
			if(firmaRegistrador==null)
			{
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}
			
			if(firmaRegistrador==null)
			{
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}
			
			if(firmaRegistrador==null)
			{
				cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
				firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
			}
			
			String ciudad = null;
			
			Circulo circuloConsultado = forseti.getCirculo(cid);
			
			
			if(circuloConsultado.getOficinaOrigen() != null){
				if(circuloConsultado.getOficinaOrigen().getVereda() != null){
					if(circuloConsultado.getOficinaOrigen().getVereda().getMunicipio() != null){
						if(circuloConsultado.getOficinaOrigen().getVereda().getMunicipio().getNombre() != null){
							ciudad = circuloConsultado.getOficinaOrigen().getVereda().getMunicipio().getNombre();
						}
					}
				}
			}

			ImprimibleActaRepartoMinutas impActaReparto = new ImprimibleActaRepartoMinutas(temp, circulo, repartoNotarialPersistente.getFechaCreacion(), ciudad, null, null,CTipoImprimible.REPARTO);
			
			if(texto != null)
				impActaReparto.setTextoReparto(texto.getTexto());
			
			if(actoresRepartoNotarial != null)
			{
				impActaReparto.setCoordinadorReparto(actoresRepartoNotarial.getCoordinadorReparto());
				impActaReparto.setDirectorReparto(actoresRepartoNotarial.getDirectorReparto());
			}

                        /* JAlcazar caso Mantis 0009055: Acta - Requerimiento No 019_151_Caratula - Reparto Notarial
                         * Se coloca en la reimpresion del Acta de Reparto Notarial el usuario que ejecuto el proceso. SET en 
                         * el metodo setDirectorReparto(funcionarioReparto).
                         */
                        Usuario usuarioreparto = null;
                        Turno turnominuta = hermod.getTurnobyWF(temp.getIdMinuta());
                        List historial = turnominuta.getHistorials();
                        Iterator itera = historial.iterator();
                        while(itera.hasNext()){
                            TurnoHistoria th = (TurnoHistoria) itera.next();
                            if(CFase.REP_REPARTO.equals(th.getFase())){
                                usuarioreparto = th.getUsuarioAtiende();
                            }
                        }
                        if (usuarioreparto != null){
                            String funcionarioReparto = usuarioreparto.getNombre();
			    funcionarioReparto += usuarioreparto.getApellido1()!=null?(" "+ usuarioreparto.getApellido1()):"";
			    funcionarioReparto += usuarioreparto.getApellido2()!=null?(" "+ usuarioreparto.getApellido2()):"";
                            impActaReparto.setDirectorReparto(funcionarioReparto);
                        }
                        
			impActaReparto.setRegistradorReparto(firmaRegistrador.getNombreRegistrador());
			
			if(repartoNotarialPersistente.getMinutas() == null || repartoNotarialPersistente.getMinutas().size() == 0)
				impActaReparto.setTextoObservaciones("No hubo minutas para repartir para el día " + impActaReparto.getFecha() + ".");
			
			Bundle bundleActa = new Bundle(impActaReparto);
	
			int veces = new Integer(evento.getNumeroImpresiones()).intValue();
			bundleActa.setNumeroCopias(veces);
	
			
				//se manda a imprimir el recibo por el identificador unico de usuario
				printJobs.enviar(circulo.getIdCirculo(), bundleActa, maxIntentos, espera);
		} catch (Throwable t) {
			t.printStackTrace();
		}
			
		EvnRespImprimirRepartoNotarial evRespuesta =
			new EvnRespImprimirRepartoNotarial(usuarios, EvnRespImprimirRepartoNotarial.IMPRESION_ACTA_OK);
		
		return evRespuesta;
	}


	private int getNumMaxImpresiones(Rol rol, Proceso proceso) throws EventoException {
		int maxImpresiones = 0;
		try {
			maxImpresiones = hermod.getNumeroMaximoImpresiones(rol, proceso);
		} catch (HermodException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			throw new EventoException(e.getMessage(), e);
		}

		return maxImpresiones;
	}


	private  ImprimibleCertificado getImprimibleCertificadoConFirmaDigital(ImprimibleCertificado impCertificado, String idCirculo) throws Throwable
	{
	    String nombre = "";
		String archivo = "";

		FirmaRegistrador firmaRegistrador = null;
		String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

		CirculoPk cid = new CirculoPk();
		cid.idCirculo = idCirculo;

		firmaRegistrador =  this.forseti.getFirmaRegistradorActiva(cid,cargo);

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

		if (firmaRegistrador==null)
		  return impCertificado;

		String cargoReg = firmaRegistrador.getCargoRegistrador();
		String nombreReg = firmaRegistrador.getNombreRegistrador();

		nombre = firmaRegistrador.getNombreRegistrador();
		archivo = firmaRegistrador.getIdArchivo();
		String rutaFisica = null;

		try{
			rutaFisica =  hermod.getPathFirmasRegistradores();
		}catch(HermodException t){
			Log.getInstance().debug(ANImprimirRepartoNotarial.class,"No se pudo cargar la firma de los registradores");
		}

		if(rutaFisica!=null)
		{
			BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);

			byte pixeles[]=null;
			try
			{
				pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
			}
			catch(Throwable t)
			{
				t.printStackTrace();
			}
			impCertificado.setPixelesImagenFirmaRegistrador(pixeles);
			impCertificado.setCargoRegistrador(cargoReg);
			impCertificado.setNombreRegistrador(nombreReg);

		}

		return impCertificado;
	}


	public static BufferedImage getImage(String path, String nombreArchivo)
	{
		String nombreCompleto = getNombreCompleto(path,nombreArchivo);
		BufferedImage buf = null;

		try
		{
			File file = new File(nombreCompleto);
			buf = ImageIO.read(file);
		}
		catch (IOException e)
		{
			Log.getInstance().error(ANImprimirRepartoNotarial.class,e);
		}

		return buf;
	}

	public static String getNombreCompleto(String path, String nombreArchivo)
	{

		String nombreCompleto=null;

		if (!path.trim().equals(""))
		  nombreCompleto = path + nombreArchivo;
		else
		  nombreCompleto = nombreArchivo;


	  return nombreCompleto;
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
				+ DateFormatUtil.getAmPmString(c
					.get(Calendar.AM_PM));

		return fechaImp;
	}

	/**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return
	 */
	protected String getFechaImpresion(Date fecha)
	{
                if( null == fecha ) {
                   return getNullableString( true );
                } // if

		Calendar c = Calendar.getInstance();
		c.setTime(fecha);
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
	 * Retorna el ID de la primera matrícula
	 * asociada a la solicitud. Si es un certificado solo
	 * hay una matrícula.
	 * @param solicitud
	 * @return
	 */
	private FolioPk getFolio_ID(Solicitud solicitud) {
		List solFolio = solicitud.getSolicitudFolios();

		Iterator itSolFolio = solFolio.iterator();
		List matriculas = new Vector();

		FolioPk fid;
		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio)itSolFolio.next();
			fid = new FolioPk();
			fid.idMatricula = sol.getFolio().getIdMatricula();
			matriculas.add(fid);

		}
		//String idMatricula = (String)matriculas.get(0);
		try {
			fid = (FolioPk)matriculas.get(0);
		}
		catch (Throwable e) {
			Log.getInstance().error(ANImprimirRepartoNotarial.class,e);
			fid = null;
		}

		return fid;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EvnRespImprimirRepartoNotarial obtenerImpresorasCirculo(EvnImprimirRepartoNotarial evento) throws EventoException {
		Map impresoras = null;
		List notas = null;
		Circulo cir=evento.getCirculo();

		try {
            if(cir!=null){
				impresoras = forseti.getConfiguracionImpresoras(cir.getIdCirculo());
            }

            //Obtener las notas informativas.
            ProcesoPk idProceso = new ProcesoPk();
            idProceso.idProceso = 1;
            String fase = "CER_REIMPRESION_ADMINISTRATIVA";
            notas = hermod.getTiposNotaProceso(idProceso,fase);



        }
        catch (HermodException e1) {
            throw new EventoException(e1.getMessage(), e1);
        }
        catch (FenrirException e) {
            throw new EventoException(e.getMessage(), e);
        }
        catch (Throwable e) {
            Log.getInstance().error(ANImprimirRepartoNotarial.class,"Excepción obteniendo notas informativas o impresoras del círculo ", e);
            throw new EventoException(e.getMessage(), e);
        }

        EvnRespImprimirRepartoNotarial respuesta = new EvnRespImprimirRepartoNotarial(impresoras,EvnImprimirRepartoNotarial.OBTENER_IMPRESORAS_CIRCULO);
        respuesta.setCirculo(cir);
        respuesta.setListaNotasReimpresion(notas);
        return respuesta;
	}

	/**
	  * Imprime el objeto imprimible en la estacion asociada al circulo del turno dado
	  * con los parametros asignados.
	  * @param turno
	  * @param imprimible
	  * @param parametros
	  * @return
	  */
	 private boolean imprimirAdministrador(String circulo, ImprimibleBase imprimible, Hashtable parametros)
	 {


		Bundle b = new Bundle(imprimible);

		String intentosImpresion;
		String esperaImpresion;

		ServiceLocator sl = ServiceLocator.getInstancia();


		//INGRESO DE INTENTOS DE IMPRESION
		 try
		  {

			  hermod = (HermodServiceInterface) sl.getServicio("gov.sir.hermod");
			  printJobs = (PrintJobsInterface) sl.getServicio("gov.sir.print");

			  intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			  if (intentosImpresion != null)
			  {
				   parametros.put(Processor.INTENTOS, intentosImpresion);
			  }
			  else
			  {
				   intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
				   parametros.put(Processor.INTENTOS, intentosImpresion);
			  }

			  //INGRESO TIEMPO DE ESPERA IMPRESION
			  esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			  if (intentosImpresion != null)
			  {
				  parametros.put(Processor.ESPERA, esperaImpresion);
			  }
			  else
			  {
				  esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
				  parametros.put(Processor.ESPERA, esperaImpresion);
			  }
		  }
		  catch (Throwable t)
		  {
			  intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
			  parametros.put(Processor.INTENTOS, intentosImpresion);
			  esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
			  parametros.put(Processor.ESPERA, esperaImpresion);
		  }

		 Integer intIntentos = new Integer (intentosImpresion);
		 int intentos = intIntentos.intValue();

		 Long longEspera = new Long (esperaImpresion);
		 int espera = longEspera.intValue();

		 ////////////////////Número de copias
		 Integer nCopias = (Integer)parametros.get(Processor.NUM_COPIAS_IMPRIMIBLE);
		 int numCopias = 1;
		 if (nCopias != null){
			numCopias = nCopias.intValue();
		 }

		 boolean resultadoImpresion = false;
		 b.setNumeroCopias(numCopias);

       String impresoraSeleccionada = (String)parametros.get(Processor.IMPRESORA_SELECCIONADA);
       if(impresoraSeleccionada!=null){
       	b.setNombreImpresora(impresoraSeleccionada);
       }

		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos (Imprime 1 copia)
		try
		{
			printJobs.enviar(circulo, b, intentos, espera);
			//si imprime exitosamente sale del ciclo.
			resultadoImpresion = true;
			parametros=new Hashtable();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			resultadoImpresion = false;

		}
		return resultadoImpresion;
	 }
}
