package gov.sir.core.negocio.acciones.registro;

import gov.sir.core.eventos.registro.EvnCrearFolio;
import gov.sir.core.eventos.registro.EvnRespCrearFolio;
import gov.sir.core.negocio.acciones.excepciones.FolioNoCreadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author ppabon
 * Clase que permite la creación de un nuevo folio
 */
public class ANCrearFolio extends SoporteAccionNegocio {
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


    public ANCrearFolio() throws EventoException{
        super();
        service = ServiceLocator.getInstancia();
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
        }

    }

    /* (non-Javadoc)
     * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
     */
    public EventoRespuesta perform(Evento ev) throws EventoException {
        if (ev instanceof EvnCrearFolio){
			EvnCrearFolio evento=(EvnCrearFolio)ev;

			if (evento.getTipoEvento().equals(EvnCrearFolio.CREACION_FOLIO)){
				return registrarCreacion(evento);
			}else if(evento.getTipoEvento().equals(EvnCrearFolio.ELIMINAR_FOLIO_TEMPORAL)){
				return eliminarFolioCreado(evento);
			}else if(evento.getTipoEvento().equals(EvnCrearFolio.VALIDAR_ANOTACION_CIUDADANO)){
				return validarAnotacionCiudadano(evento);
			}
        }

        
        return null;
    }


	/**
	 * Método que se encaga de asociar el nuevo folio al turno luego de su creación.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta registrarCreacion(EvnCrearFolio evento) throws EventoException {

		EvnRespCrearFolio eventoResp = null;
		Usuario user = evento.getUsuarioSir();
		//Hashtable tabla = new Hashtable();

		Folio folioResp = crearFolio(evento);
		eventoResp = new EvnRespCrearFolio(folioResp, EvnRespCrearFolio.CREACION_FOLIO);
        
		String matricula = folioResp.getIdMatricula();

		Turno turno = evento.getTurno();
		TurnoPk tID = new TurnoPk();
		tID.anio = turno.getAnio();
		tID.idCirculo = turno.getIdCirculo();
		tID.idProceso = turno.getIdProceso();
		tID.idTurno = turno.getIdTurno();

		FolioPk fid=new FolioPk();
		fid.idMatricula=folioResp.getIdMatricula();
		SolicitudFolioPk sfID = null;
		try {
			sfID = hermod.addFolioToTurno(matricula, tID, evento.getUsuarioSir());
			hermod.marcarFolioRecienCreadoASInTurno(tID, fid);
			hermod.marcarFolioInTurno(tID, fid);
			turno = hermod.getTurno(tID);
			/**
			 * @author David Panesso
			 * @change 1253.CALIFICACION.FOLIOS.CERRADOS
			 * Se toma el estado del folio para manejar la excepción null para que guarde si es el folio está activo o cerrado.
			 **/
			try {
				Solicitud sol = turno.getSolicitud();
				List folios = sol.getSolicitudFolios();
				Iterator ifol = folios.iterator();
				while (ifol.hasNext()) {
					SolicitudFolio sfol = (SolicitudFolio) ifol.next();
					Folio fol = sfol.getFolio();
					FolioPk fid1 = new FolioPk();
					fid1.idMatricula = fol.getIdMatricula();
					EstadoFolio est = new EstadoFolio();
					if (forseti.cerradoFolio(fid1.idMatricula, null)) {
						est.setIdEstado(CEstadoFolio.CERRADO);
					} else {
						est.setIdEstado(CEstadoFolio.ACTIVO);
					}
					fol.setEstado(est);
					sfol.setFolio(fol);
				}
			} catch (Throwable e1) {
				e1.printStackTrace();
				throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti", e1);
			}

		} catch (Throwable e) {
			String msg = "No se pudo asociar el folio con matricula " + matricula + " al turno: " + turno.getIdWorkflow();
			Log.getInstance().error(ANCrearFolio.class,msg);
			Log.getInstance().error(ANCrearFolio.class,e);
			throw new EventoException(msg, e);
		}
		
		eventoResp.setTurno(turno);	
		return eventoResp;		

	}
	
	


	/**
	 * Método que se encarga de la creación del nuevo folio
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private Folio crearFolio(EvnCrearFolio evento) throws EventoException {

		Folio folio = evento.getFolio();
		Turno t = evento.getTurno();
		//Turno.ID tid = new Turno
		
		TurnoPk oidTurno = new TurnoPk();
		
		oidTurno.anio = t.getAnio();
		oidTurno.idCirculo = t.getIdCirculo();
		oidTurno.idProceso = t.getIdProceso();
		oidTurno.idTurno = t.getIdTurno();
		
		if (folio == null) {
			List l = new ArrayList();
			l.add("El folio asociado es inválido");
			throw new FolioNoCreadoException(l);
		}
		Folio auxFolio = null;
		
		//Se valida que toda la información del folio sea correcta.
		try {
			this.forseti.validarCrearFolio(folio, true);

		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"No se ha podido validar el nuevo folio " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		//Si la información del folio es válida se crea el folio.
		Usuario usuarioSIR = evento.getUsuarioSir();

		try {
			auxFolio = this.forseti.crearFolio(folio, usuarioSIR, oidTurno, true);
		} catch (ForsetiException fe) {
			List l = null;
			l = fe.getErrores();
			if(l.size() > 0){
				throw new FolioNoCreadoException(l);
			}
			if(fe.getHashErrores() != null && !fe.getHashErrores().isEmpty()){
				throw new ValidacionParametrosHTException(fe.getHashErrores());
			}
			if(fe.getMessage() != null){
				FolioNoCreadoException fne = new FolioNoCreadoException(l);
				fne.addError(fe.getMessage() + ", " +  fe.getCause().getMessage());
				throw fne;
			}
			
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"No se ha podido crear el folio " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return auxFolio;

	}
        
	/**
	 * Método que se encaga de asociar el nuevo folio al turno luego de su creación.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta eliminarFolioCreado(EvnCrearFolio evento) throws EventoException {
		
		EvnRespCrearFolio eventoResp = null;
		Usuario user = evento.getUsuarioSir();
		//Hashtable tabla = new Hashtable();
		Folio folio = evento.getFolio(); 
        
		String matricula = folio.getIdMatricula();

		FolioPk fid=new FolioPk();
		fid.idMatricula=folio.getIdMatricula();
		
		Turno turno = evento.getTurno();
		TurnoPk tID = new TurnoPk();
		tID.anio = turno.getAnio();
		tID.idCirculo = turno.getIdCirculo();
		tID.idProceso = turno.getIdProceso();
		tID.idTurno = turno.getIdTurno();
				
		try {
			this.forseti.deleteFolio(fid, user);
			turno = hermod.getTurno(tID);
		} catch (Throwable e) {
			throw new EventoException("No se pudo eliminar el folio " + matricula, e);
		}
		
		return new EvnRespCrearFolio( turno, EvnRespCrearFolio.ELIMINAR_FOLIO_TEMPORAL);

	}
		
	/**
	 * Método que se encaga de asociar el nuevo folio al turno luego de su creación.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta validarAnotacionCiudadano(EvnCrearFolio evento) throws EventoException {
		
		Usuario user = evento.getUsuarioSir();
		Anotacion anotacion = evento.getAnotacion(); 
        List anotacionesCiudadanos = anotacion.getAnotacionesCiudadanos();				
		try {
			this.forseti.validarAnotacionesCiudadano(anotacionesCiudadanos);
		} catch (Throwable e) {
			throw new EventoException("Validación de Enajenación. ", e);
		}
		EvnRespCrearFolio evn = new EvnRespCrearFolio(user, EvnRespCrearFolio.VALIDAR_ANOTACION_CIUDADANO);
		if(evento.isGenerarNextOrden()){
			String norder = anotacion.getOrden();
			int torder=Integer.parseInt(anotacion.getOrden());
			torder++;
			norder=Integer.toString(torder);
			evn.setNextOrden(norder);
		}
		evn.setAnotacion(anotacion);
		evn.setAnotacionesAgregadas(evento.getAnotacionesAgregadas());
		return evn;
	}


}
