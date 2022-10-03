package gov.sir.core.negocio.acciones.registro;

import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import gov.sir.core.eventos.registro.EvnRespTestamentos;
import gov.sir.core.eventos.registro.EvnTestamentos;
import gov.sir.core.negocio.acciones.excepciones.RegistroTestamentosException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Testamento;
import gov.sir.core.negocio.modelo.TestamentoCiudadanoPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAvanzarTurno;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleInscripcionTestamento;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutiva;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import gov.sir.print.common.Bundle;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Acción de negocio encargada de manejar los eventos de tipo 
 * <code>EvnTestamentos</code> destinados a manejar 
 * las inscripciones relacionadas con la inscripción de testamentos.
 * 
 * @author ppabon
 *
 */
public class ANTestamentos extends SoporteAccionNegocio {

	//SIR 57 R
	
	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;

	/** Instancia de PrintJobs */
	private PrintJobsInterface printJobs;	


	/**
	 *Constructor de la clase <code>ANTestamentos</code>.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANTestamentos() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");			
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
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
	 * Manejador de eventos de tipo <code>EvnTestamentos</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento 
	 * que llega como parámetro.
	 * 
	 * @param evento <code>EvnTestamentos</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 * 
	 * @return <code>EventoRespuesta</code> con la información resultante de la 
	 * ejecución de la acción sobre los servicios
	 * 
	 * @throws <code>EventoException</code> 
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnTestamentos evento = (EvnTestamentos) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}

		String tipoEvento = evento.getTipoEvento();

		if (tipoEvento.equals(EvnTestamentos.REGISTRAR)) {
			return registrarNuevo(evento);
		} else if (tipoEvento.equals(EvnTestamentos.DEVOLVER)) {
			return devolverNuevo(evento);
		}/** Se envía el turno a corrección de encabezado*/
		else if(tipoEvento.equals(EvnTestamentos.CORRECCION)){
			return delegarCorreccionEncabezado(evento); 
		}
		/** Agregar testador*/
		else if(tipoEvento.equals(EvnTestamentos.AGREGAR_TESTADOR)){
			return agregarTestador(evento);
		}
		else if(tipoEvento.equals(EvnTestamentos.ELIMINAR_TESTADOR)){
			return eliminarTestador(evento);
		}else if(tipoEvento.equals(EvnTestamentos.DEVOLVER_A_CONFRONTACION)){
			return devolverAConfrontacion(evento);
		}
		return null;
	}

	/**
	 * Este método se encarga de inscribir un testamento.
	 * 
	 * @param evento de tipo <code>EvnTestamentos</code> 
	 * 
	 * @return <code>EvnRespTestamentos</code> con la información concertiente 
	 * a si se pudo registrar la inscripción del testamento.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespTestamentos registrar(EvnTestamentos evento) throws EventoException {
		Turno turno = evento.getTurno();
		Testamento testamento = evento.getTestamento();
		int idImprimible = 0;
                int tem = 0;
		try {
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
			hermod.addTestamentoToSolicitudRegistro(tid, testamento);
                        tem= evento.getCopiasImprimir();
                        evento.setCopiasImprimir(1);
			idImprimible = this.imprimirTestamento(evento);
                        evento.setCopiasImprimir(tem);
			this.avanzarTurno(evento , CAvanzarTurno.AVANZAR_NORMAL);
			turno = hermod.getTurno(tid);
		} catch (HermodException e) {
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", e);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", t);
		}

		EvnRespTestamentos rta = new EvnRespTestamentos(turno, EvnRespTestamentos.REGISTRAR, idImprimible);
		return rta;

	}

	/**
	 * Este método se encarga de devolver por alguna razón la inscripción de un testamento.
	 * 
	 * @param evento de tipo <code>EvnTestamentos</code> 
	 * 
	 * @return <code>EvnRespTestamentos</code> con la información concertiente 
	 * a si se pudo devolver la solicitud de inscripción de un testamento.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespTestamentos devolver(EvnTestamentos evento) throws EventoException {
		Turno turno = evento.getTurno();
		int idImprimible = 0;

		try {
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
			hermod.addNotaToTurno(evento.getNota(), tid);
			idImprimible = this.imprimirNotaDevolutivaTestamentos(evento);
			this.avanzarTurno(evento , CAvanzarTurno.AVANZAR_NORMAL);
			turno = hermod.getTurno(tid);
		} catch (HermodException e) {
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", e);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", t);
		}

		EvnRespTestamentos rta = new EvnRespTestamentos(turno, EvnRespTestamentos.DEVOLVER ,idImprimible);
		return rta;

	}


	/**
	 * Este metodo llama el servicio Hermod para poder hacer el avance del turno.
	 * @param evento EvnDevolucion
	 * @return EventoRespuesta
	 * @throws EventoException
	 */
	public void avanzarTurno(EvnTestamentos evento, int tipoAvance) throws EventoException {
		Hashtable tabla = new Hashtable();

		//Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase. 
		try {
			Hashtable tablaAvance = new Hashtable(2);
			tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
                        tablaAvance.put(Processor.NCOPIAS, String.valueOf(evento.getCopiasImprimir()));
			tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
			hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, evento.getTurno());
		} catch (Throwable t) {
			throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}

		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		tabla.put(Processor.RESULT, evento.getRespuestaWF());
		tabla.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioSIR().getUsername());
                tabla.put(Processor.NCOPIAS, String.valueOf(evento.getCopiasImprimir()));
			
		
	}
	
	/**
	 * Accion que imprime una constancia de testamentos.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private int imprimirTestamento(EvnTestamentos evento) throws EventoException {
		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();
		Circulo circulo = evento.getCirculo();
		String idWorkflow = null;
		
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";
		
		int maxIntentos;
		int espera;
		
		SolicitudRegistro solicitud =  (SolicitudRegistro)turno.getSolicitud();  

		
		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS. 

		//INGRESO DE INTENTOS DE IMPRESION
		try
		{
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
			if (intentosImpresion != null)
			{
				 tabla.put(INTENTOS,intentosImpresion);
			}
			else
			{
				tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			}
			
			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null)
			{
				tabla.put(ESPERA,esperaImpresion);
			}
			else
			{
				tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			}
		}
		catch (Throwable t)
		{
			
			tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			
		}
		
		String fechaImpresion = this.getFechaImpresion();
		Date fechaRadicacion = null;
		if(solicitud.getFecha()!=null){
			fechaRadicacion = solicitud.getFecha(); 
		}
		ImprimibleInscripcionTestamento impTestamento = new ImprimibleInscripcionTestamento(evento.getTestamento(), solicitud.getDocumento(), turno.getIdWorkflow() , circulo.getNombre() , fechaImpresion , evento.getUsuarioSIR() , fechaRadicacion , CTipoImprimible.INSCRIPCION_TESTAMENTOS );
		
		
		//OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
		String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
		CirculoPk cid = new CirculoPk();
		cid.idCirculo = turno.getIdCirculo();		
		
		FirmaRegistrador firmaRegistrador = null;

		String nombre = "";
		String archivo = "";
		String cargoToPrint = "";
		String rutaFisica = null;		
		
		try
		{
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
			
			nombre = firmaRegistrador.getNombreRegistrador();
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
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		
		if (nombre==null)
		  nombre="";
		
		
		impTestamento.setCargoRegistrador(cargoToPrint);  
		impTestamento.setNombreRegistrador(nombre);
	
		int idImprimible =0;
		for(int i=0;i<evento.getCopiasImprimir();i++){
			idImprimible= this.imprimir(impTestamento, tabla, circulo.getIdCirculo(), 1, false);
		}
		
		return idImprimible;

	}
	
	

	/**
	 * Accion que imprime una nota devolutiva de testamentos cuando se solicita.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private int imprimirNotaDevolutivaTestamentos(EvnTestamentos evento) throws EventoException {
		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();
		Vector notas = (Vector)evento.getNotas();
		Circulo circulo = evento.getCirculo();
                /**
                 * @Autor: Elora
                 * @Mantis: 0012621
                 * @Requerimiento: 055_453
                 */
		String matricula = turno.getIdMatriculaUltima();
                if(matricula == null){
                    matricula = "";
                }
		String idWorkflow = null;
		
		Usuario usuarioSIR = evento.getUsuarioSIR();
		
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";
		
		int maxIntentos;
		int espera;


		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS. 

		//INGRESO DE INTENTOS DE IMPRESION
		try{
		
			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_FOLIO);
			if (intentosImpresion != null){
				 tabla.put(INTENTOS,intentosImpresion);
			}else{
				tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			}
			
			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
			if (intentosImpresion != null){
				tabla.put(ESPERA,esperaImpresion);
			}
			else{
				tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);
			}
		}catch (Throwable t){			
			tabla.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
			tabla.put(ESPERA,CImpresion.DEFAULT_ESPERA_IMPRESION);			
		}

		if (turno != null) {

			idWorkflow = turno.getIdWorkflow();
			

		}

		SolicitudRegistro  solRegistro = (SolicitudRegistro)turno.getSolicitud();
		String certAsociados = this.obtenerTextoCertificadosAsociados(turno);
		String fechaImpresion = this.getFechaImpresion();
		//ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
		ImprimibleNotaDevolutiva impNota = new ImprimibleNotaDevolutiva(notas, circulo.getNombre(), idWorkflow, matricula, fechaImpresion, CTipoImprimible.NOTAS_DEVOLUTIVAS);
		
		//OPCIONES PARA COLOCAR LA FIRMA DEL REGISTRADOR
		String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;
		CirculoPk cid = new CirculoPk();
		cid.idCirculo = turno.getIdCirculo();		
		
		//HABILITAR SI LA DEVOLUCIÓN ES  SIMILAR A LAS NOTAS DE CALIFICACIÓN
		/*
		FirmaRegistrador firmaRegistrador = null;

		String nombre = "";
		String archivo = "";
		String cargoToPrint = "";
		String rutaFisica = null;		
		
		try
		{
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
			
			nombre = firmaRegistrador.getNombreRegistrador();
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
		catch(Throwable t)
		{
			t.printStackTrace();
		}
		
		if (nombre==null)
		  nombre="";
		
		
		impNota.setCargoRegistrador(cargoToPrint);  
		impNota.setNombreRegistrador(nombre);
	
		
		if(rutaFisica!=null)
		{
			BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);
			
			byte pixeles[]=null;
			try 
			{
				pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
			} 
			catch (Throwable e1) {
				
				e1.printStackTrace();
			}
			impNota.setPixelesImagenFirmaRegistrador(pixeles);
		}	*/	
		
		

		int impresa =  this.imprimir(impNota, tabla, circulo.getIdCirculo(), 1, false);

		return impresa;

	}	
	
	/**
	 * @param string
	 */
	private String obtenerTextoCertificadosAsociados(Turno turno) {

		String retorno = " Y CERTIFICADO ASOCIADO: 0";
		String numeroCertificados = "";
		
		SolicitudAsociada solicitudAsociada = null;
		Solicitud solicitudHija = null;
		Turno turnoHijo = null; 							
		
		if(turno!=null){
			Solicitud solicitud = turno.getSolicitud();
			if(solicitud!=null){
				List solicitudesHijas = solicitud.getSolicitudesHijas();
				if(solicitudesHijas !=null && solicitudesHijas.size()>0){
					Iterator itSolHija = solicitudesHijas.iterator();
					while (itSolHija.hasNext()){
						solicitudAsociada = (SolicitudAsociada)itSolHija.next();
						solicitudHija = solicitudAsociada.getSolicitudHija();
						turnoHijo = solicitudHija.getTurno(); 		
						numeroCertificados = numeroCertificados + turnoHijo.getIdWorkflow() + ", ";					
					}					 
				}
			}			 
		}
		if(numeroCertificados.length()>0){
			numeroCertificados = numeroCertificados.substring(0,(numeroCertificados.length()-2));
			retorno = " Y CERTIFICADOS ASOCIADOS: " + numeroCertificados;
		}
		return retorno;
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
	private int imprimir(ImprimibleBase imprimible, Hashtable parametros, String ID, int numCopias, boolean lanzarExcepcion) throws EventoException {

		boolean impresion_ok = false;
		String mensaje_error = "";

		//CONSTANTES PARA LA IMPRESIÓN.
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";

		Bundle b = new Bundle(imprimible);
		b.setNumeroCopias(numCopias);		

		String numIntentos = (String) parametros.get(INTENTOS);
		String espera = (String) parametros.get(ESPERA);

		Integer intentosInt = new Integer(numIntentos);
		int intentos = intentosInt.intValue();
		Integer esperaInt = new Integer(espera);
		int esperado = esperaInt.intValue();
		int idImprimible = 0;

		//Ciclo que se ejecuta de acuerdo al valor recibido en intentos
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario o id del circulo (caso especial CORRECCIONES)
			printJobs.enviar(ID, b, intentos, esperado);
			impresion_ok = true;
		}
		catch (PrintJobsException t) {
			idImprimible = t.getIdImpresion();
			if(idImprimible == 0){
				throw new EventoException("Se genero una excepción al imprimir la Constancia de testamentos. " + t.getMessage());
			}
		}
		catch (Throwable t) {
			t.printStackTrace();
			mensaje_error = t.getMessage();
			
			if (lanzarExcepcion && !impresion_ok) {
				throw new EventoException(mensaje_error);
			}
			
		}


		return idImprimible;
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
	 * @param path
	 * @param nombreArchivo
	 * @return
	 */
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
			Log.getInstance().error(ANTestamentos.class,e);
		}
		
		return buf;
	}	
	
	
	/**
	 * @param path
	 * @param nombreArchivo
	 * @return
	 */
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
	 * Este método se encarga de devolver por alguna razón la inscripción de un testamento.
	 * 
	 * @param evento de tipo <code>EvnTestamentos</code> 
	 * 
	 * @return <code>EvnRespTestamentos</code> con la información concertiente 
	 * a si se pudo devolver la solicitud de inscripción de un testamento.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespTestamentos devolverNuevo(EvnTestamentos evento) throws EventoException {
		Turno turno = evento.getTurno();
		int idImprimible = 0;

		try {
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
			hermod.addNotaToTurno(evento.getNota(), tid);
			idImprimible = this.imprimirNotaDevolutivaTestamentos(evento);
			Usuario u = evento.getUsuarioSIR();
			
			
			/*****************************************/
			/*        ELIMINAR SASS                  */
			/*****************************************/
			
			
			try {
				Hashtable tablaAvance = new Hashtable(2);
				tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, evento.getTurno());
			} catch (Throwable t) {
				throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
			
			
			Fase faseAvance = evento.getFase();    
		    Hashtable parametrosAvance = new Hashtable();
		  	parametrosAvance.put(Processor.RESULT,CRespuesta.NEGAR);
			             
			try 
			{
				hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
			
			}
		    catch (Throwable exception) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
		    }
		    
			
			
			
			turno = hermod.getTurno(tid);
		} catch (HermodException e) {
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", e);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", t);
		}

		EvnRespTestamentos rta = new EvnRespTestamentos(turno, EvnRespTestamentos.DEVOLVER ,idImprimible);
		return rta;

	}

	
	
	
	/**
	 * Este método se encarga de inscribir un testamento.
	 * 
	 * @param evento de tipo <code>EvnTestamentos</code> 
	 * 
	 * @return <code>EvnRespTestamentos</code> con la información concertiente 
	 * a si se pudo registrar la inscripción del testamento.
	 * 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespTestamentos registrarNuevo(EvnTestamentos evento) throws EventoException {
		Turno turno = evento.getTurno();
		Testamento testamento = evento.getTestamento();
		int idImprimible = 0;

		try {
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
			hermod.addTestamentoToSolicitudRegistro(tid, testamento);
                        int copias = evento.getCopiasImprimir();
                        evento.setCopiasImprimir(1);
			idImprimible = this.imprimirTestamento(evento);
			Usuario u = evento.getUsuarioSIR();
			
			
			/*****************************************/
			/*        ELIMINAR SASS                  */
			/*****************************************/
			
			
			try {
				Hashtable tablaAvance = new Hashtable(2);
				tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
                                
				hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, evento.getTurno());
			} catch (Throwable t) {
				throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
			
			
			Fase faseAvance = evento.getFase();    
                        Hashtable parametrosAvance = new Hashtable();
		  	parametrosAvance.put(Processor.RESULT,CRespuesta.CONFIRMAR);
			parametrosAvance.put(Processor.NCOPIAS, String.valueOf(copias));
                                         
			try 
			{
				hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
			
			}
		    catch (Throwable exception) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
		    }
			
			
			
			turno = hermod.getTurno(tid);
		} catch (HermodException e) {
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", e);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", t);
		}

		EvnRespTestamentos rta = new EvnRespTestamentos(turno, EvnRespTestamentos.REGISTRAR, idImprimible);
		return rta;

	}
	
	/** Modifica Pablo Quintana Junio 16 2008
	* @param evento
	* @return
	*/
	private EvnRespTestamentos delegarCorreccionEncabezado(EvnTestamentos evento) throws EventoException {
		Turno turno = evento.getTurno();
		//Testamento testamento = evento.getTestamento();
		int idImprimible = 0;
		try {
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
			//hermod.addTestamentoToSolicitudRegistro(tid, testamento);
			//idImprimible = this.imprimirTestamento(evento);
			Usuario u = evento.getUsuarioSIR();
			/*
			 * Se valida que se ingrese nota informativa
			 */
			try {
				Hashtable tablaAvance = new Hashtable(2);
				tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, evento.getTurno());
			} catch (Throwable t) {
				throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
			
			
			Fase faseAvance = evento.getFase();    
		    Hashtable parametrosAvance = new Hashtable();
		  	parametrosAvance.put(Processor.RESULT,CRespuesta.ERROR_ENCABEZADO);
			             
			try 
			{
				hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
			
			}
		    catch (Throwable exception) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
		    }
			
			
			
			turno = hermod.getTurno(tid);
		} catch (HermodException e) {
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", e);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RegistroTestamentosException("No se pudo registrar el testamento.", t);
		}

		EvnRespTestamentos rta = new EvnRespTestamentos(turno, EvnRespTestamentos.CORRECCION_ENCABEZADO, idImprimible);
		
		return rta;
	}

	/** Agregar testador a testamento
	* @param evento
	* @return*/
	private EvnRespTestamentos agregarTestador(EvnTestamentos evento) throws EventoException {
		return null;
	}
	
	private EvnRespTestamentos eliminarTestador(EvnTestamentos evento) throws EventoException {
		if(evento.getTestamentoCiudadano()!=null){
			TestamentoCiudadanoPk testamentoCiudadanoID;
			try {
				testamentoCiudadanoID=new TestamentoCiudadanoPk();
				testamentoCiudadanoID.idTestamento=evento.getTestamento().getIdTestamento();
				testamentoCiudadanoID.idCiudadano=evento.getTestamentoCiudadano().getCiudadano().getIdCiudadano();
				hermod.removeTestadorFromTestamento(testamentoCiudadanoID);
			} catch (Throwable e) {
				throw new RegistroTestamentosException("No se pudo eliminar el testador.", e);
			}
		}
		return null;
	}
	
	/** Devuelve un turno de testamentos a confrontacion
	* @param evento
	* @return
	*/
	private EvnRespTestamentos devolverAConfrontacion(EvnTestamentos evento) throws EventoException {
		Turno turno = evento.getTurno();
		int idImprimible = 0;
		try {
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();
			Usuario u = evento.getUsuarioSIR();
			/*
			 * Se valida que se ingrese nota informativa
			 */
			try {
				Hashtable tablaAvance = new Hashtable(2);
				tablaAvance.put(Processor.RESULT, evento.getRespuestaWF());
				tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuario().getUsuarioId());
				hermod.validarNotaInformativaAvanceTurno(evento.getFase(), tablaAvance, evento.getTurno());
			} catch (Throwable t) {
				throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			}
			Fase faseAvance = evento.getFase();    
		    Hashtable parametrosAvance = new Hashtable();
		  	parametrosAvance.put(Processor.RESULT,CRespuesta.DEVOLVER_A_CONFRONTACION);
			try{
				hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
			}catch (Throwable exception) {
			    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
		    }
			turno = hermod.getTurno(tid);
		} catch (HermodException e) {
			throw new RegistroTestamentosException("No se pudo regresar el testamento a confrontación.", e);
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RegistroTestamentosException("No se pudo regresar el testamento a confrontación.", t);
		}
		return new EvnRespTestamentos(turno, EvnRespTestamentos.DEVOLVER_A_CONFRONTACION, idImprimible);
	}
}
