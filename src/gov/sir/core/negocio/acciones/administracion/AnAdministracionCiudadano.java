package gov.sir.core.negocio.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnAdministracionCiudadano;
import gov.sir.core.eventos.administracion.EvnRespAdministracionCiudadano;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.CiudadanoProhibicion;
import gov.sir.core.negocio.modelo.CiudadanoProhibicionPk;
import gov.sir.core.negocio.modelo.Prohibicion;
import gov.sir.core.negocio.modelo.ProhibicionPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import java.util.Iterator;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

/**
 * Acción de negocio encargada de manejar los eventos de tipo 
 * <code>EvnAdministracionFenrir</code> destinados a manejar 
 * la administración de los objetos del servicio Forseti  
 * 
 * @author jmendez
 *
 */
public class AnAdministracionCiudadano extends SoporteAccionNegocio {

	/** Instancia del ServiceLocator 	 */
	private ServiceLocator service = null;

	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Forseti  */
	private ForsetiServiceInterface forseti;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;

	/**
	 * Constructor encargado de inicializar los servicios a ser utilizados por la
	 * acción de Negocio
	 * @throws EventoException
	 */
	public AnAdministracionCiudadano() throws EventoException {
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

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio Fenrir no encontrado");
		}
	}

	/**
	 * Manejador de eventos de tipo <code>EvnAdministracionFenrir</code>.
	 * Se encarga de procesar las acciones solicitadas por la capa Web de acuerdo
	 * al tipo de evento que llegue a la acción de negocio.  Este método redirige
	 * la acción a otros métodos en la clase de acuerdo al tipo de evento 
	 * que llega como parámetro.
	 * 
	 * @param evento <code>EvnRespAdministracionCiudadano</code> evento con los parámetros
	 * de la acción a realizar utilizando los servicios disponibles en la clase.
	 * 
	 * @return <code>EventoRespuesta</code> con la información resultante de la 
	 * ejecución de la acción sobre los servicios
	 * 
	 * @throws <code>EventoException</code> 
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnAdministracionCiudadano evento = (EvnAdministracionCiudadano) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}
		String tipoEvento = evento.getTipoEvento();

		if (tipoEvento.equals(EvnAdministracionCiudadano.PROHIBICION_CREAR)) {
			return crearProhibicion(evento);
		} else if (tipoEvento.equals(EvnAdministracionCiudadano.PROHIBICION_ELIMINAR)) {
			return eliminarProhibicion(evento);
		} else if (tipoEvento.equals(EvnAdministracionCiudadano.CIUDADANO_CONSULTAR)) {
			return consultarCiudadano(evento);
		} else if (tipoEvento.equals(EvnAdministracionCiudadano.CIUDADANO_PROHIBICION_ADICIONAR)) {
			return adicionarCiudadanoProhibicion(evento);
		} else if (tipoEvento.equals(EvnAdministracionCiudadano.CIUDADANO_PROHIBICION_ELIMINAR) ) { 
			return eliminarCiudadanoProhibicion(evento);
		} else if (tipoEvento.equals(EvnAdministracionCiudadano.CIUDADANO_CREAR)) {
			return crearCiudadano(evento);
		}
		
		// Editar los valores almacenados de una prohibición. 
		else if (tipoEvento.equals(EvnAdministracionCiudadano.PROHIBICION_EDITAR) ) { 
			return editarProhibicion(evento);
		}

		return null;
	}

	private EventoRespuesta crearCiudadano(EvnAdministracionCiudadano evento) throws EventoException {

		Ciudadano ciudadano;
		
		try {
			ciudadano = forseti.crearCiudadano(evento.getCiudadano());
		} catch (ForsetiException e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"Ocurrió un error al crear el ciudadano en el sistema", e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"Ocurrió un error al crear el ciudadano en el sistema", e);
			throw new EventoException(e.getMessage(), e);
		}
		
		EvnRespAdministracionCiudadano evRespuesta = new EvnRespAdministracionCiudadano(ciudadano,
				EvnRespAdministracionCiudadano.CIUDADANO_CREAR_OK);
		
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar el servicio para la creación de una <code>Prohibicion</code>
	 * @param evento de tipo <code>EvnAdministracionFenrir</code> con la información
	 * de la <code>Prohibicion</code> a ser creada
	 * @return <code>EvnRespAdministracionCiudadano</code> con la lista  los objetos <code>Prohibicion</code>  
	 * existentes en el sistema. 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespAdministracionCiudadano crearProhibicion(EvnAdministracionCiudadano evento)
		throws EventoException {

		List datos = null;

		try {
			
			int lastIdProhibicion = 0;
			List prohibiciones = forseti.getProhibiciones();
			if(prohibiciones!=null){
				Iterator it = prohibiciones.iterator();
				while(it.hasNext()){					
					Prohibicion prohibicion = (Prohibicion)it.next();
					
					try{
						Integer integer = new Integer(prohibicion.getIdProhibicion());
						
						if(integer.intValue()>lastIdProhibicion){
							lastIdProhibicion = integer.intValue(); 
						}
							
					}catch(Exception e){
						
					}
					
				}
			}
			
			lastIdProhibicion++;
			
			Prohibicion prohibicion = evento.getProhibicion();
			prohibicion.setIdProhibicion(""+lastIdProhibicion);
			
			if (forseti.addProhibicion(prohibicion) != null) {
				datos = forseti.getProhibiciones();
			}
			if (datos == null) {
				throw new EventoException("Ocurrió un error al consultar las prohibiciones.", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"Ocurrió un error al crear la prohibición en el sistema", e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"Ocurrió un error al crear la prohibición en el sistema", e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionCiudadano evRespuesta =
			new EvnRespAdministracionCiudadano(
				datos,
				EvnRespAdministracionCiudadano.PROHIBICION_CREAR_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar el servicio para la eliminación de una <code>Prohibicion</code>
	 * @param evento de tipo <code>EvnAdministracionFenrir</code> con la información
	 * de la <code>Prohibicion</code> a ser eliminada
	 * @return <code>EvnRespAdministracionCiudadano</code> con la lista  los objetos <code>Prohibicion</code>  
	 * existentes en el sistema. 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespAdministracionCiudadano eliminarProhibicion(EvnAdministracionCiudadano evento)
		throws EventoException {

		List datos = null;

		try {
			forseti.eliminarProhibicion(evento.getProhibicion());
			datos = forseti.getProhibiciones();
			if (datos == null) {
				throw new EventoException("Ocurrió un error al eliminar las prohibición.", null);
			}

		} catch (ForsetiException e) {
			
			throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Prohibición", e);
		} catch (Throwable e) {
			
			throw new EventoException("Debido a restricciones de integridad de datos no se pudo eliminar el Tipo de Prohibición", e);
		}

		EvnRespAdministracionCiudadano evRespuesta =
			new EvnRespAdministracionCiudadano(
				datos,
				EvnRespAdministracionCiudadano.PROHIBICION_ELIMINAR_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar el servicio para consultar la existencia
	 * de un <code>Ciudadano</code> en el sistema
	 * @param evento de tipo <code>EvnAdministracionFenrir</code> con la información
	 * de la <code>Ciudadano</code> a ser eliminada
	 * @return <code>EvnRespAdministracionCiudadano</code> con el <code>Ciudadano</code> encontrado 
	 * o null en el caso que no exista ningún registro que cuimpla la condición de TipoDocumento /
	 * Número de Documento   
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespAdministracionCiudadano consultarCiudadano(EvnAdministracionCiudadano evento)
		throws EventoException {

		Ciudadano dato = null;
		try {
			Ciudadano ciudadano = evento.getCiudadano();
			dato = forseti.getCiudadanoByDocumento(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
		} catch (ForsetiException e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"Ocurrió un error al consultar el usuario sistema.", e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"Ocurrió un error al consultar el usuario sistema.", e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionCiudadano evRespuesta =
			new EvnRespAdministracionCiudadano(
				dato,
				EvnRespAdministracionCiudadano.CIUDADANO_CONSULTAR_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar el servicio para la eliminación de un objeto <code>CiudadanoProhibicion</code>
	 * @param evento de tipo <code>EvnAdministracionFenrir</code> con la información
	 * del  <code>CiudadanoProhibicion</code> a ser eliminado
	 * @return <code>EvnRespAdministracionCiudadano</code> con la lista  los objetos <code>Prohibicion</code>  
	 * existentes en el sistema. 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespAdministracionCiudadano eliminarCiudadanoProhibicion(EvnAdministracionCiudadano evento)
		throws EventoException {

		Ciudadano datos = null;
		try {
			Ciudadano ciudadano = evento.getCiudadano();
			CiudadanoProhibicion ciudProh = evento.getCiudadanoProhibicion();
			CiudadanoProhibicionPk cid = new CiudadanoProhibicionPk();
			cid.fechaInicial = ciudProh.getFechaInicial();
			cid.idCiudadano = ciudProh.getIdCiudadano();
			cid.idProhibicion = ciudProh.getIdProhibicion();
			
			Usuario usuario = fenrir.getUsuario(evento.getUsuario());
			
			forseti.desactivarProhibicionToCiudadano(cid, usuario,ciudProh.getComentarioAnulacion());
			datos = forseti.getCiudadanoByDocumento(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
			if (datos == null) {
				throw new EventoException("No se pudo eliminar el Ciudadano.", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"No se pudo eliminar el Ciudadano.", e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"No se pudo eliminar el Ciudadano.", e); 
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionCiudadano evRespuesta =
			new EvnRespAdministracionCiudadano(
				datos,
				EvnRespAdministracionCiudadano.CIUDADANO_ELIMINAR_OK);
		return evRespuesta;
	}

	//	////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de invocar el servicio para la creación un objeto <code>CiudadanoProhibicion</code>
	 * @param evento de tipo <code>EvnAdministracionFenrir</code> con la información
	 * del  <code>CiudadanoProhibicion</code> a ser eliminado
	 * @return <code>EvnRespAdministracionCiudadano</code> con la lista  los objetos <code>Prohibicion</code>  
	 * existentes en el sistema. 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespAdministracionCiudadano adicionarCiudadanoProhibicion(EvnAdministracionCiudadano evento)
		throws EventoException {

		Ciudadano datos = null;
		try {
			Ciudadano ciudadano = evento.getCiudadano();
			CiudadanoProhibicion ciudProd = evento.getCiudadanoProhibicion();

			CiudadanoPk cid = new CiudadanoPk();
			cid.idCiudadano = ciudadano.getIdCiudadano();

			ProhibicionPk pid = new ProhibicionPk();
			pid.idProhibicion = ciudProd.getIdProhibicion();
			
			CirculoPk cirid= new CirculoPk();
			cirid.idCirculo=ciudProd.getIdCirculo();
			
			Usuario usuario = fenrir.getUsuario(evento.getUsuario());
			
			forseti.addProhibicionToCiudadano(cid, pid, cirid, ciudProd.getComentario(), usuario);
			datos = forseti.getCiudadanoByDocumento(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
			if (datos == null) {
				throw new EventoException("No se pudo obtener el Ciudadano.", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"No se pudo obtener el Ciudadano.", e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"No se pudo obtener el Ciudadano.", e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionCiudadano evRespuesta =
			new EvnRespAdministracionCiudadano(
				datos,
				EvnRespAdministracionCiudadano.CIUDADANO_ELIMINAR_OK);
		return evRespuesta;
	}



	/**
	 * Este método se encarga de invocar el servicio para la creación de una <code>Prohibicion</code>
	 * @param evento de tipo <code>EvnAdministracionFenrir</code> con la información
	 * de la <code>Prohibicion</code> a ser creada
	 * @return <code>EvnRespAdministracionCiudadano</code> con la lista  los objetos <code>Prohibicion</code>  
	 * existentes en el sistema. 
	 * @throws <code>EventoException</code> 
	 */
	private EvnRespAdministracionCiudadano editarProhibicion(EvnAdministracionCiudadano evento)
		throws EventoException {

		List datos = null;

		try {
			if (forseti.editarProhibicion(evento.getProhibicion()) != null) {
				datos = forseti.getProhibiciones();
			}
			if (datos == null) {
				throw new EventoException("Ocurrió un error al consultar las prohibiciones.", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"Ocurrió un error al crear la prohibición en el sistema", e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(AnAdministracionCiudadano.class,"Ocurrió un error al crear la prohibición en el sistema", e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespAdministracionCiudadano evRespuesta =
			new EvnRespAdministracionCiudadano(
				datos,
				EvnRespAdministracionCiudadano.PROHIBICION_CREAR_OK);
		return evRespuesta;
	}

}
