package gov.sir.core.negocio.acciones.comun;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionImprimibleSIR;
import com.lowagie.text.Image;
import com.lowagie.text.Watermark;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.eventos.comun.EvnNotas;
import gov.sir.core.eventos.comun.EvnRespNotas;
import gov.sir.core.negocio.acciones.excepciones.ANTurnoException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionException;
import gov.sir.core.negocio.acciones.excepciones.NotaNoAgregadaException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.Banco;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Consignacion;
import gov.sir.core.negocio.modelo.DocPagoCheque;
import gov.sir.core.negocio.modelo.DocPagoConsignacion;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Nota;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAsociada;
import gov.sir.core.negocio.modelo.SolicitudDevolucion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CSolicitudFolio;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoRevisionCalificacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacionA;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaDevolutivaCalificacionB;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleNotaInformativa;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.PdfServlet;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mmunoz, ppabon, jvelez
 */
public class ANNotas extends SoporteAccionNegocio {

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;
  /** Constatne que define el tamaño de pulgada */
    private static final int INCH = 72;

    /** Constante que define el ancho de una hoja carta */
    private static final double LETTER_WIDTH = 8.5 * INCH;

    /** Constante que define el alto de una hoja carta */
    private static final double LETTER_HEIGHT = 11 * INCH;
	/**
	 * Instancia de PrintJobs
	 */
	private PrintJobsInterface printJobs;

	/**
	 *Constructor de la clase ANSeguridad
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANNotas() throws EventoException {
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
	 * Recibe un evento para escoger la locacion y devuelve un evento de respuesta
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnNotas</CODE>
	 * @throws EventoException cuando ocurre un problema que no se pueda manejar
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespNotas</CODE>
	 * @see gov.sir.core.eventos.comun.EvnNotas
	 * @see gov.sir.core.eventos.comun.EvnRespNotas
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnNotas evento = (EvnNotas) e;
		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}
		if (evento.getTipoEvento().equals(EvnNotas.AGREGAR_NOTA)) {
			return agregarNota(evento);
		} else if (evento.getTipoEvento().equals(EvnNotas.IMPRIMIR_NOTA_INFORMATIVA)) {
			return imprimirNotaInformativa(evento);
		} else if (evento.getTipoEvento().equals(EvnNotas.GUARDAR_NOTAS_DEVOLUTIVAS)) {
			return guardarNotaDevolutiva(evento);
		}else if(evento.getTipoEvento().equals(EvnNotas.REGISTRAR_CALIFICACION_PARCIAL)){
			return registrarCalificacionParcial(evento);
		}else if(evento.getTipoEvento().equals(EvnNotas.ELIMINAR_NOTAS_INFORMATIVAS_ULTIMA_FASE_TURNO)){
			return eliminarNotasInformativasFaseActualTurno(evento);
		}else if(evento.getTipoEvento().equals(EvnNotas.GUARDA_NOTA_DEVOLUTIVA_ADD)){
			return guardaNotaDevolutivaAdd(evento);
		}else if(evento.getTipoEvento().equals(EvnNotas.ELIMINA_NOTA_DEVOLUTIVA_ADD)){
			return eliminaNotaDevolutivaAdd(evento);
		}
		return null;
	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta agregarNota(EvnNotas evento) throws EventoException {
		TurnoPk id = new TurnoPk();
		Turno nTurno = new Turno();
		Turno turno = evento.getTurno();
		id.anio = turno.getAnio();
		id.idCirculo = turno.getIdCirculo();
		id.idProceso = turno.getIdProceso();
		id.idTurno = turno.getIdTurno();

		try {
			hermod.addNotaToTurno(evento.getNota(), id);
		} catch (HermodException e) {
			new NotaNoAgregadaException("La nota no pudo ser agregada", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANNotas.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			nTurno = hermod.getTurno(id );
			if(nTurno.getIdProceso()==Long.parseLong(CProceso.PROCESO_DEVOLUCIONES)){
	        	
		        if(nTurno.getSolicitud().getTurnoAnterior()==null){
		        	List neoConsignaciones = new ArrayList();
	    			List consignaciones = ((SolicitudDevolucion)nTurno.getSolicitud()).getConsignaciones();
	    			Iterator iter = consignaciones.iterator();
	    			while(iter.hasNext()){
	    				try{
	    					Consignacion consignacion = ((Consignacion)iter.next());
	    					DocumentoPago aux = null;
	    					Banco banco = null;
	    					DocumentoPago docPago  = consignacion.getDocPago();
	    					if(docPago instanceof DocPagoCheque){
	    						aux = hermod.getDocPagoCheque(docPago);
	    						banco = hermod.getBancoByID(((DocPagoCheque)aux).getBanco().getIdBanco());
	    						((DocPagoCheque)docPago).setBanco(banco);
	    					}else if(docPago instanceof DocPagoConsignacion){
	    						aux = hermod.getDocPagoConsignacion(docPago);
	    						banco = hermod.getBancoByID(((DocPagoConsignacion)aux).getBanco().getIdBanco());
	    						((DocPagoConsignacion)docPago).setBanco(banco);
	    					}
	    					consignacion.setDocPago(docPago);
	    					neoConsignaciones.add(consignacion);	
	    				}catch (HermodException e) {
	    		            throw new ANTurnoException(
	    		                    "Error obteniendo consignaciones",e);
	    		        } catch (Throwable e) {
	    		                ExceptionPrinter printer = new ExceptionPrinter(e);
	    		                Log.getInstance().error(ANNotas.class,"Ha ocurrido una excepcion inesperada " +
	    		                    printer.toString());
	    		                throw new EventoException(e.getMessage(),e);
	    		        }
	    		        ((SolicitudDevolucion)nTurno.getSolicitud()).setConsignaciones(neoConsignaciones);
	    			}
		    	}else{
		    		Turno turnoAnterior = nTurno.getSolicitud().getTurnoAnterior();
	        		Solicitud solAnt = turnoAnterior.getSolicitud();
	            	
	            	if(turnoAnterior.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO)){
	            		try{              		
	            			while(turnoAnterior!=null){
	            				solAnt = turnoAnterior.getSolicitud();
	            				List solAsociadas = solAnt.getSolicitudesHijas();
	            				for(int i=0;i<solAsociadas.size();i++){
	            					Solicitud sol = hermod.getSolicitudById(((SolicitudAsociada)solAsociadas.get(i)).getSolicitudHija().getIdSolicitud());
	            					((SolicitudAsociada)turnoAnterior.getSolicitud().getSolicitudesHijas().get(i)).setSolicitudHija(sol);
	            				}
	            				turnoAnterior = solAnt.getTurnoAnterior();
	            			}
	                	}catch(Throwable t){
	                		new ANTurnoException("No existe solicitud de registro");
	                	}
	            	}
		    	}        	
	        }
		} catch (HermodException e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		return new EvnRespNotas(nTurno);
	}
	
	/**
	 * Elimina las notas informativas asociadas el turno en su fase actual
	 * @param evento
	 * @return EventoRespuesta
	 * @throws EventoException
	 */
    private EventoRespuesta eliminarNotasInformativasFaseActualTurno(EvnNotas evento)
            throws EventoException {
        TurnoPk id = new TurnoPk();
        Turno nTurno = new Turno();
        Turno turno = evento.getTurno();
        if (turno != null) {
            id.anio = turno.getAnio();
            id.idCirculo = turno.getIdCirculo();
            id.idProceso = turno.getIdProceso();
            id.idTurno = turno.getIdTurno();

            try {
                hermod.eliminarNotasInformativasUltimaFaseTurno(id);
                nTurno = hermod.getTurno(id);
            } catch (HermodException e) {
                new NotaNoAgregadaException("La nota no pudo ser agregada", e);
            } catch (Throwable e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(ANNotas.class, "Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(e.getMessage(), e);
            }
        } else {
            nTurno = turno;
        }
        return new EvnRespNotas(nTurno, evento.getTipoEvento());
    }

	/**
	 * Accion que imprime una nota informativa cuando se solicita.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta imprimirNotaInformativa(EvnNotas evento) throws EventoException {
		Hashtable tabla = new Hashtable();

		Turno turno = evento.getTurno();
		Turno turnoDatos = new Turno();
		Vector notas = (Vector) evento.getNotas();
		Circulo circulo = evento.getCirculo();
		String matricula = null;
		String idWorkflow = null;
		
		String INTENTOS = "INTENTOS";
		String ESPERA = "ESPERA";
		
		int maxIntentos;
		int espera;

		
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

		
		
		
		Hashtable ht = new Hashtable();		
		if (turno != null) {

			idWorkflow = turno.getIdWorkflow();
			
			List solicitudesFolio = turno.getSolicitud().getSolicitudFolios();
			if (solicitudesFolio != null && !solicitudesFolio.isEmpty()) {
				SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudesFolio.get(0);
				matricula = solicitudFolio.getIdMatricula();
			}
			
			List historial = turno.getHistorials();
			Iterator it = historial.iterator();
			String automatica = "";
			while (it.hasNext()) {
				TurnoHistoria th = (TurnoHistoria) it.next();
				try {
					automatica = hermod.getTipoFase(th.getFase());
					ht.put(th.getFase(), automatica);
				} catch (Throwable t) {
					Log.getInstance().info(ANNotas.class,"ERROR CONSULTANDO SI LA FASE" + th.getFase() + " ES O NO AUTOMATICA. EL ERROR ES :" + t.getMessage());
				}
			}	
			
			//Voy a trar los datos del Turno
			try {
				turnoDatos = hermod.getTurnobyWF(turno.getIdWorkflow());
			} catch (Throwable er) {
				Log.getInstance().info(ANNotas.class,"NO SE PUDO RECUPARAR LOS DATOS DEL TURNO: " + turno.getIdWorkflow() + ", " + er.getMessage());
			}

		} 

		boolean isTurnoRegistroYDevolutiva= false;
		Nota nota = (Nota)notas.get(0);
		if(nota.getIdProceso()==Long.parseLong(CProceso.PROCESO_REGISTRO)){
			if(nota.getTiponota().isDevolutiva()){
				isTurnoRegistroYDevolutiva = true;
			}
		}
		
		String fechaImpresion = this.getFechaImpresion();
		ImprimibleBase impNota=null;
		if(!isTurnoRegistroYDevolutiva){
			impNota = new ImprimibleNotaInformativa(notas, circulo.getNombre(), idWorkflow, matricula, ht, evento.getUsuarioSIR(), fechaImpresion,CTipoImprimible.NOTA_INFORMATIVA);
		}else if(nota.getIdFase()!="Impresion_Temporal"){
			TurnoPk tid = new TurnoPk();
			tid.anio = nota.getAnio();
			tid.idCirculo = nota.getIdCirculo();
			tid.idProceso = nota.getIdProceso();
			tid.idTurno = nota.getIdTurno();

			try {
				turno = hermod.getTurno(tid);
			} catch (Throwable e) {
				Log.getInstance().error(ANNotas.class,e);
			}
			SolicitudRegistro solr = (SolicitudRegistro) turno.getSolicitud();
			String certAsociados = this.obtenerTextoCertificadosAsociados(turno);
			List solicitudFolios = turno.getSolicitud().getSolicitudFolios();
			ArrayList matriculasNoInscritas = new ArrayList();
			ArrayList matriculasInscritasParcialmente = new ArrayList();
			for(int i=0; i<solicitudFolios.size(); i++){
				SolicitudFolio solFolio = (SolicitudFolio) solicitudFolios.get(i);
				if(solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIALMENTE ||
						solFolio.getEstado()==CSolicitudFolio.ESTADO_INSCRITO_PARCIAL_NO_TMP){
					matriculasInscritasParcialmente.add(solFolio.getIdMatricula());
				}else {
					matriculasNoInscritas.add(solFolio.getIdMatricula());
				}
			}
			evento.setMatriculasInscritasParcialmente(matriculasInscritasParcialmente);
			evento.setMatriculasNoInscritas(matriculasNoInscritas);
			//impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solr.getDocumento(), evento.getUsuarioSIR(), certAsociados, fechaImpresion, CTipoImprimible.NOTAS_DEVOLUTIVAS);
		}else{
			impNota = new ImprimibleNotaInformativa(notas, circulo.getNombre(), idWorkflow, matricula, ht, evento.getUsuarioSIR(), fechaImpresion,CTipoImprimible.NOTA_INFORMATIVA);
		}
			
		long procesoTurno = 0;
		
		try {
			procesoTurno = turno.getIdProceso();
		} catch (Exception ee) {
			Log.getInstance().error(ANNotas.class,"No se recupero turno con dato que viene");
		}
		
		try {
			procesoTurno = turnoDatos.getIdProceso();
		} catch (Exception ee) {
			Log.getInstance().error(ANNotas.class,"No se recupero turno con dato de hermod");
		}
		
		if(isTurnoRegistroYDevolutiva ){
			int idImprimible = this.imprimirNotaDevolutiva(evento);//this.imprimir(impNota, tabla, circulo.getIdCirculo(), evento.getNumCopiasImpresion(), true);
			if(idImprimible!=0){
				throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
			}
		}else{
			if( (procesoTurno == Long.parseLong(CProceso.PROCESO_CORRECCIONES )) ){
				int idImprimible = this.imprimir(impNota, tabla, circulo.getIdCirculo(), evento.getImprimirYN(), true);
				if(idImprimible!=0){
					throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
				}
			}else{
				int idImprimible = this.imprimir(impNota, tabla, evento.getUID(), evento.getImprimirYN(), true);
				if(idImprimible!=0){
					throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
				}
			}
		}
		return null;

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
				throw new EventoException("Se genero una excepción al imprimir la Nota. " + t.getMessage());
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
	 * @param evento
	 * @return
	 */
	private EventoRespuesta guardarNotaDevolutiva(EvnNotas evento) throws EventoException {
		TurnoPk id = new TurnoPk();
		Turno nTurno = new Turno();
		Turno turno = evento.getTurno();
		id.anio = turno.getAnio();
		id.idCirculo = turno.getIdCirculo();
		id.idProceso = turno.getIdProceso();
		id.idTurno = turno.getIdTurno();
		Fase fase = evento.getFase();

		TurnoPk tid = new TurnoPk();
		tid.anio = turno.getAnio();
		tid.idCirculo = turno.getIdCirculo();
		tid.idProceso = turno.getIdProceso();
		tid.idTurno = turno.getIdTurno();
		try {
			
			//Caso especial inscripcionParcial
			if(evento.isInscripcionParcial()){
				//Se valida que no hayan anotaciones inscritas
				boolean ok=false;
				try {
					ok=forseti.validarTerminacionCalificacion(tid,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);
				
					if(!ok){
						throw new ForsetiException("El turno debe tener anotaciones temporal en alguno de los folios");
					}
				} 
				catch (Throwable e3) {
					ValidacionParametrosException e4 = new ValidacionParametrosException();
					e4.addError("No se pudo realizar la inscripcion parcial. El turno debe tener anotaciones temporal en alguno de los folios");
					throw e4;
				}
			}
			
			
			/*if (fase != null && fase.getID().equals(CFase.CAL_CALIFICACION)) {
				hermod.removeDevolutivasFromTurno(tid);
			}*/
			
			if(fase!=null && !fase.getID().equals(CFase.CAL_CALIFICACION)){
				List notas = evento.getNotas();
				Iterator itNotas = notas.iterator();
				while(itNotas.hasNext()){
					Nota nota = (Nota)itNotas.next();
					hermod.addNotaToTurno(nota, id);	
				}
			}
			
		} catch (HermodException e) {
			new NotaNoAgregadaException("La nota no pudo ser agregada", e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANNotas.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			ValidacionParametrosException e5 = new ValidacionParametrosException();
			e5.addError("No se pudo realizar la inscripcion parcial. El turno debe tener anotaciones temporal en alguno de los folios");
			throw e5;
		}

		try {
			nTurno = hermod.getTurno(id);
		} catch (HermodException e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		
		int idImprimible = this.imprimirNotaDevolutiva(evento);
		boolean impresa = true;
		//SE COMENTAREA PORQUE A PESAR DE QUE NO SE IMPRIMIO, EL INTENTO DE REIMPRESIÓN LO HARÁ EL ADMINISTRADOR DE IMPRESORAS 
		/*if(idImprimible != 0){
			impresa = false;
		}*/
		
		return new EvnRespNotas(nTurno, EvnRespNotas.GUARDAR_NOTAS_DEVOLUTIVAS, impresa, idImprimible);
	}	
	public void VisualizarpdfNota(EvnNotas evento,HttpServletRequest request, HttpServletResponse response){
            Hashtable tabla = new Hashtable();
                int rta = -1;

		
		Vector notas = new Vector(evento.getNotas());
		Nota nota = (Nota) notas.get(0);
		
		Turno turno = evento.getTurno();
		if(turno==null){
			try{
				TurnoPk tid = new TurnoPk();
				tid.anio = nota.getAnio();
				tid.idCirculo = nota.getIdCirculo();
				tid.idProceso = nota.getIdProceso();
				tid.idTurno = nota.getIdTurno();
				
				turno = hermod.getTurno(tid);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				Log.getInstance().error(ANNotas.class,e);
			}
			
			
		}
		
		if(turno != null){
			for(int i=0; i<notas.size();i++){
				((Nota)notas.get(i)).setTurno(turno);
			}
		}
		
		Circulo circulo = evento.getCirculo();
		String matricula = null;
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
			
			List solicitudesFolio = turno.getSolicitud().getSolicitudFolios();
			if (solicitudesFolio != null && !solicitudesFolio.isEmpty()) {
				SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudesFolio.get(0);
				matricula = solicitudFolio.getIdMatricula();
			}

		}

		SolicitudRegistro  solRegistro = (SolicitudRegistro)turno.getSolicitud();
		String certAsociados = this.obtenerTextoCertificadosAsociados(turno);
		String fechaImpresion = this.getFechaImpresion();
                
                ValidacionImprimibleSIR iR = new ValidacionImprimibleSIR();
                String clase = null;
                try {
                    clase = iR.buscarVersionImprimible("NOTA_DEVOLUTIVA",nota.getTime());
                } catch (GeneralSIRException ex) {
                    Logger.getLogger(ANNotas.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if("ImprimibleNotaDevolutivaCalificacionB".equals(clase)){
                try {
                    VimprimibleNotaDevolutivaCalificacionB(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla,request,response);
                } catch (EventoException ex) {
                    Logger.getLogger(ANNotas.class.getName()).log(Level.SEVERE, null, ex);
                }
                } else if("ImprimibleNotaDevolutivaCalificacionA".equals(clase)){
                try {
                    VimprimibleNotaDevolutivaCalificacionA(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla,request,response);
                } catch (EventoException ex) {
                    Logger.getLogger(ANNotas.class.getName()).log(Level.SEVERE, null, ex);
                }
                } else if("ImprimibleNotaDevolutivaCalificacion".equals(clase)){
                    try {
                        VimprimibleNotaDevolutivaCalificacion(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla,request,response);
                    } catch (EventoException ex) {
                        Logger.getLogger(ANNotas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
             
        }
         private int VimprimibleNotaDevolutivaCalificacionB(EvnNotas evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla,HttpServletRequest request, HttpServletResponse response) throws EventoException{
                    
                    ImprimibleNotaDevolutivaCalificacionB impNota = new ImprimibleNotaDevolutivaCalificacionB(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    impNota.setMatriculasNoInscritas(evento.getMatriculasNoInscritas());
                    impNota.setMatriculasInscritasParcialmente(evento.getMatriculasInscritasParcialmente());
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
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
		
		
		impNota.setCargoRegistrador(cargoToPrint);  
		impNota.setNombreRegistrador(nombre);
		impNota.setPrintWatermarkEnabled(true);
	Turno rta1 = null;
                   try {
                            rta1 = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                    } catch (HermodException e) {
                
                    } catch (Throwable ex) {
                     }
                    List historia1 = rta1.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
                                        isFirma = true;
                                }
                        }
                    }
		
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
		}		
		
		
                        PrinterJob printJob = PrinterJob.getPrinterJob();
                        PageFormat pf = printJob.defaultPage();
                        Paper papel = new Paper();
                        double mWidth = LETTER_WIDTH;
                        double mHeight = LETTER_HEIGHT;

                        papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                            mHeight - (0.5 * INCH));
                        pf.setPaper(papel);
                        impNota.generate(pf);
                        this.generarVisualizacion(impNota, request, response);

                    return 1;
        }
        private int VimprimibleNotaDevolutivaCalificacionA(EvnNotas evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla,HttpServletRequest request, HttpServletResponse response) throws EventoException{
                    ImprimibleNotaDevolutivaCalificacionA impNota = new ImprimibleNotaDevolutivaCalificacionA(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    impNota.setMatriculasNoInscritas(evento.getMatriculasNoInscritas());
                    impNota.setMatriculasInscritasParcialmente(evento.getMatriculasInscritasParcialmente());
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
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
		
		
		impNota.setCargoRegistrador(cargoToPrint);  
		impNota.setNombreRegistrador(nombre);
		impNota.setPrintWatermarkEnabled(true);
	Turno rta1 = null;
                   try {
                            rta1 = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                    } catch (HermodException e) {
                
                    } catch (Throwable ex) {
                     }
                    List historia1 = rta1.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
                                        isFirma = true;
                                }
                        }
                    }
		
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
			
		}		
		
		
                   PrinterJob printJob = PrinterJob.getPrinterJob();
                        PageFormat pf = printJob.defaultPage();
                        Paper papel = new Paper();
                        double mWidth = LETTER_WIDTH;
                        double mHeight = LETTER_HEIGHT;

                        papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                            mHeight - (0.5 * INCH));
                        pf.setPaper(papel);
                        impNota.generate(pf);
                        this.generarVisualizacion(impNota, request, response);

                    return 1;
        }
        private int VimprimibleNotaDevolutivaCalificacion(EvnNotas evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla,HttpServletRequest request, HttpServletResponse response) throws EventoException{
            ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    impNota.setMatriculasNoInscritas(evento.getMatriculasNoInscritas());
                    impNota.setMatriculasInscritasParcialmente(evento.getMatriculasInscritasParcialmente());
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
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


                    impNota.setCargoRegistrador(cargoToPrint);  
                    impNota.setNombreRegistrador(nombre);
                    impNota.setPrintWatermarkEnabled(true);

                     Turno rta1 = null;
                   try {
                            rta1 = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                    } catch (HermodException e) {
                
                    } catch (Throwable ex) {
                     }
                    List historia1 = rta1.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
                                        isFirma = true;
                                }
                        }
                    }
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
                           
                           
                    }		

                         PrinterJob printJob = PrinterJob.getPrinterJob();
                        PageFormat pf = printJob.defaultPage();
                        Paper papel = new Paper();
                        double mWidth = LETTER_WIDTH;
                        double mHeight = LETTER_HEIGHT;

                        papel.setImageableArea(INCH / 4, INCH / 4, mWidth - (0.5 * INCH),
                            mHeight - (0.5 * INCH));
                        pf.setPaper(papel);
                        impNota.generate(pf);
                        this.generarVisualizacion(impNota, request, response);
               
                    return 1;
        }
                      /**
                * Generar un pdf
                * @param imprimible
                * @param request
                * @param response
                */
               private void generarVisualizacion(Pageable imprimible, HttpServletRequest request,
                   HttpServletResponse response) {
                   try {
                       
                       com.lowagie.text.Document pdfDocument = new com.lowagie.text.Document();
                       response.setContentType("application/pdf");
                       // Se agrega condicion para 
                       // Visualizar Formulario Imprimible en SIR
                       // add by DNilson Olaya Gómez --- desarrollo3@tsg.net.co
                       ByteArrayOutputStream baos = new ByteArrayOutputStream();
                       response.addHeader("Content-Disposition", "inline; filename=FormularioImprimible.pdf");
                       PdfWriter writer = PdfWriter.getInstance(pdfDocument,
                               response.getOutputStream());
                       writer.setViewerPreferences(PdfWriter.HideMenubar |
                           PdfWriter.HideToolbar);
                       writer.setEncryption(PdfWriter.STRENGTH128BITS, null, null, 0);

                       InputStream is =  PdfServlet.class.getResourceAsStream( "watermark.jpg" );
                       byte[] bytes =this.inputStreamToBytes(is);

                                   /**
                                   * @author: Fernando Padilla Velez, 22.06.2015
                                   * @change: Incidencia No 180870 Generación PDF- Resultado de la consulta
                                   * 		   No permitia visulizar el pdf, se intercambian las lineas de codigo
                                   *          para que primero se abra el documento y luego si se agrega la imagen.
                                   */
                       pdfDocument.open();

                                   Image img = Image.getInstance(  bytes );
                       pdfDocument.add(new Watermark(img, 50, 320));

                       PdfContentByte content = writer.getDirectContent();

                       int num_pages = imprimible.getNumberOfPages();

                       for (int i = 0; i < num_pages; i++) {
                           Printable page = imprimible.getPrintable(i);

                           // we create a template and a Graphics2D object that
                           // corresponds with it
                           PageFormat pageformat = imprimible.getPageFormat(i);
                           int pageWidth = (int) pageformat.getWidth();
                           int pageHeight = (int) pageformat.getHeight();
                           PdfTemplate template = content.createTemplate(pageWidth,
                                   pageHeight);
                           template.setWidth(pageWidth);
                           template.setHeight(pageHeight);

                           Graphics2D g2d = template.createGraphics(pageWidth, pageHeight);
                           page.print(g2d, pageformat, 0);

                           if (pageformat.getOrientation() == PageFormat.LANDSCAPE) {
                               content.addTemplate(template, 0, 1, -1, 0, pageHeight, 0);
                           } else {
                               content.addTemplate(template, 0, 0);
                           }

                           pdfDocument.newPage();
                       }

                       pdfDocument.close();
                   } catch (Exception e) {
                           Log.getInstance().error(PdfServlet.class,e);
                   }
               }
                     /**
            * @param in
            * @return
            * @throws IOException
            */
           public byte[] inputStreamToBytes(InputStream in) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);

		in.close();
		out.close();
		return out.toByteArray();
    	}
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta registrarCalificacionParcial(EvnNotas evento) throws EventoException {
		boolean ok=false;
		//Usuario usuario = evento.getUsuario();
		TurnoPk oid = new TurnoPk();
		oid.anio = evento.getTurno().getAnio();
		oid.idCirculo = evento.getTurno().getIdCirculo();
		oid.idProceso = evento.getTurno().getIdProceso();
		oid.idTurno = evento.getTurno().getIdTurno();
		Hashtable ht = new Hashtable();

		try {
			ok=forseti.validarTerminacionCalificacion(oid,CTipoRevisionCalificacion.TODOS_FOLIOS_UNA_ANOTACION);
			ht = forseti.validarNuevasAnotacionesTurno(oid);

		} catch (Throwable e1) {

			e1.printStackTrace();
			throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti",e1);
		}
//		SE CREA EL EVENTO DE RESPUESTA
		//EvnNotas respuesta = new EvnNotas(null, null, evento.getTurno());
		EvnRespNotas respuesta = new EvnRespNotas(evento.getTurno(),EvnRespNotas.REGISTRAR_CALIFICACION_PARCIAL, false, 0);
		//respuesta = new EventoRespuesta();
		respuesta.setValidacionAnotacionesTemporales(ht);
		return respuesta;
		
	}
	
	
	/**
	 * Accion que imprime una nota informativa cuando se solicita.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private int imprimirNotaDevolutiva(EvnNotas evento) throws EventoException {
                Hashtable tabla = new Hashtable();
                int rta = -1;


                Vector notas = new Vector(evento.getNotas());
                Nota nota = (Nota) notas.get(0);

                Turno turno = evento.getTurno();
                if(turno==null){
                        try{
                                TurnoPk tid = new TurnoPk();
                                tid.anio = nota.getAnio();
                                tid.idCirculo = nota.getIdCirculo();
                                tid.idProceso = nota.getIdProceso();
                                tid.idTurno = nota.getIdTurno();

                                turno = hermod.getTurno(tid);
                        } catch (Throwable e) {
                                // TODO Auto-generated catch block
                                Log.getInstance().error(ANNotas.class,e);
                        }


                }

                if(turno != null){
                        for(int i=0; i<notas.size();i++){
                                ((Nota)notas.get(i)).setTurno(turno);
                        }
                }

                Circulo circulo = evento.getCirculo();
                String matricula = null;
                String idWorkflow = null;
                List historia = turno.getHistorials();
                int i1 = 0;
                String impresion = "";
                Usuario usuarioSIR = null;
                TurnoHistoria Histo = null;
                while (i1 < historia.size()) {
                    TurnoHistoria historial = (TurnoHistoria) historia.get(i1);
                    if (historial.getFase().equals(CFase.REG_TESTAMENTO)) {
                        if (i1 != historia.size() - 1) {
                            usuarioSIR = historial.getUsuarioAtiende();
                            impresion = historial.getNcopias();
                        }
                    }
                    i1++;
                }
                if(usuarioSIR == null){
                    int i = 0;
                    String impresion1 = "";
                    while (i < historia.size()) {
                        TurnoHistoria historial = (TurnoHistoria) historia.get(i);
                        if (historial.getFase().equals(CFase.CAL_CALIFICACION)) {
                            if (i != historia.size() - 1) {
                                usuarioSIR = historial.getUsuarioAtiende();
                                impresion1 =  historial.getNcopias();
                            }

                        }
                        i++;
                    }
                    
                }
                if(evento.getFase() != null){
                    if(evento.getFase().getID().equals(CFase.CAL_CALIFICACION)){
                              usuarioSIR = evento.getUsuarioSIR();
                            
                    }
                }
                
                
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

                        List solicitudesFolio = turno.getSolicitud().getSolicitudFolios();
                        if (solicitudesFolio != null && !solicitudesFolio.isEmpty()) {
                                SolicitudFolio solicitudFolio = (SolicitudFolio) solicitudesFolio.get(0);
                                matricula = solicitudFolio.getIdMatricula();
                        }

                }

                SolicitudRegistro  solRegistro = (SolicitudRegistro)turno.getSolicitud();
                String certAsociados = this.obtenerTextoCertificadosAsociados(turno);
                String fechaImpresion = this.getFechaImpresion();

                ValidacionImprimibleSIR iR = new ValidacionImprimibleSIR();
                String clase = null;
                try {
                    clase = iR.buscarVersionImprimible("NOTA_DEVOLUTIVA",nota.getTime());
                } catch (GeneralSIRException ex) {
                    Logger.getLogger(ANNotas.class.getName()).log(Level.SEVERE, null, ex);
                }

                if("ImprimibleNotaDevolutivaCalificacionB".equals(clase)){
                    rta = imprimibleNotaDevolutivaCalificacionB(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla);
                } else if("ImprimibleNotaDevolutivaCalificacionA".equals(clase)){
                    rta = imprimibleNotaDevolutivaCalificacionA(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla);
                } else if("ImprimibleNotaDevolutivaCalificacion".equals(clase)){
                    rta = imprimibleNotaDevolutivaCalificacion(evento, turno, notas, circulo, idWorkflow, matricula, solRegistro, usuarioSIR, certAsociados, fechaImpresion, tabla);
                }

                return rta;
	}	
        
        
        private int imprimibleNotaDevolutivaCalificacionB(EvnNotas evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla) throws EventoException{
                    
                    ImprimibleNotaDevolutivaCalificacionB impNota = new ImprimibleNotaDevolutivaCalificacionB(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    impNota.setMatriculasNoInscritas(evento.getMatriculasNoInscritas());
                    impNota.setMatriculasInscritasParcialmente(evento.getMatriculasInscritasParcialmente());
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
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
		
		
		impNota.setCargoRegistrador(cargoToPrint);  
		impNota.setNombreRegistrador(nombre);
		impNota.setPrintWatermarkEnabled(true);
	Turno rta1 = null;
                   try {
                            rta1 = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                    } catch (HermodException e) {
                
                    } catch (Throwable ex) {
                     }
                    List historia1 = rta1.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
                                        isFirma = true;
                                }
                        }
                    }
		
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
			 if(isFirma){
                                 impNota.setPixelesImagenFirmaRegistrador(pixeles);
                                 impNota.setChangetextforregistrador(true);
                            }
		}		
		
		
                int imprimirques = evento.getImprimirYN();
		int impresa =  this.imprimir(impNota, tabla, circulo.getIdCirculo(),imprimirques , false);

                    return impresa;
        }

        private int imprimibleNotaDevolutivaCalificacionA(EvnNotas evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla) throws EventoException{
                    ImprimibleNotaDevolutivaCalificacionA impNota = new ImprimibleNotaDevolutivaCalificacionA(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    impNota.setMatriculasNoInscritas(evento.getMatriculasNoInscritas());
                    impNota.setMatriculasInscritasParcialmente(evento.getMatriculasInscritasParcialmente());
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
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
		
		
		impNota.setCargoRegistrador(cargoToPrint);  
		impNota.setNombreRegistrador(nombre);
		impNota.setPrintWatermarkEnabled(true);
	Turno rta1 = null;
                   try {
                            rta1 = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                    } catch (HermodException e) {
                
                    } catch (Throwable ex) {
                     }
                    List historia1 = rta1.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)){
                                        isFirma = true;
                                }
                        }
                    }
		
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
			 if(isFirma){
                                 impNota.setPixelesImagenFirmaRegistrador(pixeles);
                                 impNota.setChangetextforregistrador(true);
                            }
		}		
		
		
                int imprimirques = evento.getImprimirYN();
		int impresa =  this.imprimir(impNota, tabla, circulo.getIdCirculo(), imprimirques, false);

                    return impresa;
        }
        
        private int imprimibleNotaDevolutivaCalificacion(EvnNotas evento, Turno turno, Vector notas, Circulo circulo, String idWorkflow, String matricula, SolicitudRegistro solRegistro, Usuario usuarioSIR, String certAsociados, String fechaImpresion, Hashtable tabla) throws EventoException{
            ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    //ImprimibleNotaDevolutivaCalificacion impNota = new ImprimibleNotaDevolutivaCalificacion(notas, circulo.getNombre(), idWorkflow, matricula, solRegistro.getDocumento(), usuarioSIR, certAsociados, fechaImpresion,CTipoImprimible.NOTAS_DEVOLUTIVAS);
                    impNota.setPrintWatermarkEnabled(true);
                    impNota.setMatriculasNoInscritas(evento.getMatriculasNoInscritas());
                    impNota.setMatriculasInscritasParcialmente(evento.getMatriculasInscritasParcialmente());
                    /**
                    * @author Fernado Padila
                    * @change mantis 5423: Acta - Requerimiento No 206 - FORMATO NOTA DEVOLUTIVA,
                    *         Se setea el nombre del departamento de la oficina que trabaja el turno.
                    */
                    impNota.setNombreDepartamento(solRegistro.getCirculo().getOficinaOrigen().
                            getVereda().getMunicipio().getDepartamento().getNombre());
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


                    impNota.setCargoRegistrador(cargoToPrint);  
                    impNota.setNombreRegistrador(nombre);
                    impNota.setPrintWatermarkEnabled(true);

                     Turno rta1 = null;
                   try {
                            rta1 = hermod.getTurnobyWF(evento.getTurno().getIdWorkflow());
                    } catch (HermodException e) {
                
                    } catch (Throwable ex) {
                     }
                    List historia1 = rta1.getHistorials();
                    boolean isFirma= false;
                    Iterator iti = historia1.iterator();
                    String resp1 = "";
                    if(historia1==null || historia1.size()==0){
                    
                    }else{
                        Boolean control =true;
                        while(iti.hasNext()){
                                TurnoHistoria th = (TurnoHistoria)iti.next();
                                if(th.getFase().equals(CFase.REG_CERTIFICADOS_ASOCIADOS)
                                        || th.getFase().equals(CFase.NOT_NOTA_DEVOLUTIVA)){
                                        isFirma = true;
                                }
                        }
                    }
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
                            if(isFirma){
                                 impNota.setPixelesImagenFirmaRegistrador(pixeles);
                                 impNota.setChangetextforregistrador(true);
                            }
                           
                    }		


                    int imprimirques = evento.getImprimirYN();
                    int impresa =  this.imprimir(impNota, tabla, circulo.getIdCirculo(), imprimirques, false);

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
			Log.getInstance().error(ANNotas.class,e);
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
	
	
	
	private EventoRespuesta guardaNotaDevolutivaAdd(EvnNotas evento) throws EventoException {
		TurnoPk id = new TurnoPk();
		Turno nTurno = new Turno();
		Turno turno = evento.getTurno();
		id.anio = turno.getAnio();
		id.idCirculo = turno.getIdCirculo();
		id.idProceso = turno.getIdProceso();
		id.idTurno = turno.getIdTurno();
		TurnoPk tid = new TurnoPk();
		tid.anio = turno.getAnio();
		tid.idCirculo = turno.getIdCirculo();
		tid.idProceso = turno.getIdProceso();
		tid.idTurno = turno.getIdTurno();
		try {
			if(evento.isInscripcionParcial()){
				boolean ok=false;
				try {
					ok=forseti.validarTerminacionCalificacion(tid,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);
				
					if(!ok){
						throw new ForsetiException("El turno debe tener anotaciones temporal en alguno de los folios");
					}
				}catch (Throwable e3) {
					ValidacionParametrosException e4 = new ValidacionParametrosException();
					e4.addError("No se pudo realizar la inscripcion parcial. El turno debe tener anotaciones temporal en alguno de los folios");
					throw e4;
				}
			}
			Nota nota = evento.getNota();
			/*if(evento.getNotaToBorrar()!=null)
				hermod.removeNotaDevolutivaFromTurno(id,evento.getNotaToBorrar());
			hermod.addNotaToTurno(nota, id);*/
			hermod.actualizaNotaDevolutiva(id, nota,evento.getNotaToBorrar());
		}catch (HermodException e) {
			new NotaNoAgregadaException("La nota no pudo ser agregada", e);
		}catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANNotas.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			ValidacionParametrosException e5 = new ValidacionParametrosException();
			e5.addError("No se pudo realizar la inscripcion parcial. El turno debe tener anotaciones temporal en alguno de los folios");
			throw e5;
		}
		try {
			nTurno = hermod.getTurno(id);
		} catch (HermodException e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		return new EvnRespNotas(nTurno,EvnNotas.GUARDA_NOTA_DEVOLUTIVA_ADD);
	}	
	
	private EventoRespuesta eliminaNotaDevolutivaAdd(EvnNotas evento) throws EventoException {
		TurnoPk id = new TurnoPk();
		Turno nTurno = new Turno();
		Turno turno = evento.getTurno();
		id.anio = turno.getAnio();
		id.idCirculo = turno.getIdCirculo();
		id.idProceso = turno.getIdProceso();
		id.idTurno = turno.getIdTurno();
		TurnoPk tid = new TurnoPk();
		tid.anio = turno.getAnio();
		tid.idCirculo = turno.getIdCirculo();
		tid.idProceso = turno.getIdProceso();
		tid.idTurno = turno.getIdTurno();
		try {
			if(evento.isInscripcionParcial()){
				boolean ok=false;
				try {
					ok=forseti.validarTerminacionCalificacion(tid,CTipoRevisionCalificacion.UN_FOLIO_UNA_ANOTACION);
				
					if(!ok){
						throw new ForsetiException("El turno debe tener anotaciones temporal en alguno de los folios");
					}
				}catch (Throwable e3) {
					ValidacionParametrosException e4 = new ValidacionParametrosException();
					e4.addError("No se pudo realizar la inscripcion parcial. El turno debe tener anotaciones temporal en alguno de los folios");
					throw e4;
				}
			}
			Nota nota = evento.getNota();
			hermod.removeNotaDevolutivaFromTurno(id,nota);
		}catch (HermodException e) {
			new NotaNoAgregadaException("La nota no pudo ser agregada", e);
		}catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANNotas.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			ValidacionParametrosException e5 = new ValidacionParametrosException();
			e5.addError("No se pudo realizar la inscripcion parcial. El turno debe tener anotaciones temporal en alguno de los folios");
			throw e5;
		}
		try {
			nTurno = hermod.getTurno(id);
		} catch (HermodException e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		return new EvnRespNotas(nTurno,EvnNotas.GUARDA_NOTA_DEVOLUTIVA_ADD);
	}		
}
