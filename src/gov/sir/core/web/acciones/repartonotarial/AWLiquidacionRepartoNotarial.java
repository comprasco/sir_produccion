package gov.sir.core.web.acciones.repartonotarial;

import co.com.iridium.generalSIR.comun.modelo.Transaccion;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import gov.sir.core.eventos.repartonotarial.EvnRepartoNotarial;
import gov.sir.core.eventos.repartonotarial.EvnRespRepartoNotarial;
import gov.sir.core.negocio.modelo.AccionNotarial;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoNotarial;
import gov.sir.core.negocio.modelo.EntidadPublica;
import gov.sir.core.negocio.modelo.Minuta;
import gov.sir.core.negocio.modelo.MinutaAccionNotarial;
import gov.sir.core.negocio.modelo.MinutaEntidadPublica;
import gov.sir.core.negocio.modelo.OtorganteNatural;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.SolicitudRepartoNotarial;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CAccionNotarial;
import gov.sir.core.negocio.modelo.constantes.CCategoria;
import gov.sir.core.negocio.modelo.constantes.CMinuta;
import gov.sir.core.negocio.modelo.constantes.CSolicitudRepartoNotarial;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionRepartoNotarialException;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Esta accion web se encarga de generar eventos de negocio relacionados con el
 * reparto notarial de minutas. Se encarga de realizar las validaciones primarias a nivel web y
 * por medio de eventos hace llamados a la capa de negocio para que su vez llame los servicios 
 * que se requieren.
 * @author ppabon
 */
public class AWLiquidacionRepartoNotarial extends SoporteAccionWeb {

	/**CONSTANTE PARA AGREGAR UN ACTO*/
	public final static String AGREGAR_ACTO = "AGREGAR_ACTO"; 
	
	/**CONSTANTE PARA AGREGAR UNA ENTIDAD PÚBLICA	 */
	public final static String AGREGAR_ENTIDAD_PUBLICA = "AGREGAR_ENTIDAD_PUBLICA";

	/**CONSTANTE PARA ELIMINAR UNA ENTIDAD PÚBLICA	 */
	public final static String ELIMINAR_ENTIDAD_PUBLICA = "ELIMINAR_ENTIDAD_PUBLICA";

	/**CONSTANTE PARA AGREGAR UN OTORGANTE NATURAL	 */
	public final static String AGREGAR_OTORGANTE_NATURAL = "AGREGAR_OTORGANTE_NATURAL";

	/**CONSTANTE PARA ELIMINAR UN OTORGANTE NATURAL	 */
	public final static String ELIMINAR_OTORGANTE_NATURAL = "ELIMINAR_OTORGANTE_NATURAL";
	
	/**CONSTANTE PARA CARGAR LOS CIRCULOS NOTARIALES DE UN CIRCULO REGISTRAL	 */
	public final static String CARGAR_CIRCULOS_NOTARIALES = "CARGAR_CIRCULOS_NOTARIALES";
	
	/**CONSTANTE PARA PRESERVAR LA INFORMACIÓN DEL FORMULARIO	 */
	public final static String PRESERVAR_INFO = "PRESERVAR_INFO";	
	
	/** CONSTANTE PARA LA ELIMINACION DE LAS ACCIONES NOTARIALES */
	public final static String ELIMINAR_ACCION_NOTARIAL = "ELIMINAR_ACCION_NOTARIAL";
	

	/**
	 * Constante que identifica que se desea liquidar la
	 * solicitud de una corrección
	 */
	public final static String LIQUIDAR = "LIQUIDAR";

	/**
	 * Constante que identifica que se desea obtener la categoria de la minuta
	 */
	public final static String OBTENER_CATEGORIA = "OBTENER_CATEGORIA";
	
	public final static String VALIDAR_INFORMACION = "VALIDAR_INFORMACION";
	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;

	/**
	 * Constructor de la clase AWTurnoCorreccion
	 */
	public AWLiquidacionRepartoNotarial() {
		super();
	}

	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}
                /* AHERRENO
                 29/05/2012
                 REQ 076_151 TRANSACCION*/
                request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());                   
		if (accion.equals(CARGAR_CIRCULOS_NOTARIALES)) {
			return cargarCirculosNotariales(request);
		} else if (accion.equals(AGREGAR_ENTIDAD_PUBLICA)) {
			return agregarEntidadPublica(request);
		} else if (accion.equals(ELIMINAR_ENTIDAD_PUBLICA)) {
			return eliminarEntidadPublica(request);
		} else if (accion.equals(ELIMINAR_ACCION_NOTARIAL)){
			return eliminarAccionNotarial(request);
		} else if (accion.equals(AGREGAR_OTORGANTE_NATURAL)) {
			return agregarOtorganteNatural(request);
		} else if (accion.equals(ELIMINAR_OTORGANTE_NATURAL)) {
			return eliminarOtorganteNatural(request);
		} else if (accion.equals(LIQUIDAR)) {
			return liquidar(request);
		} else if (accion.equals(OBTENER_CATEGORIA)) {
			return obtenerCategoria(request);
		} else if (accion.equals(PRESERVAR_INFO)) {
			this.preservarInfoLiquidacion(request);
			return null;
		} else if (accion.equals(AGREGAR_ACTO)){
			return agregarActo(request);
		} else if (accion.equals(VALIDAR_INFORMACION)){
			return validarInformacion(request);
		}
			else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}
	}

	/**
	 * Método que agrega una nueva entidad pública a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial cargarCirculosNotariales(HttpServletRequest request) throws AccionWebException {
		
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);

		return new EvnRepartoNotarial(usuarioAuriga, circulo, EvnRepartoNotarial.CARGAR_CIRCULOS_NOTARIALES);
	}
	
	/**
	 * Método que permite agregar un acto a la solicitud del reparto notarial
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial agregarActo(HttpServletRequest request) throws AccionWebException {
		//GUARDAR LA INFO DEL FORMULARIO
		this.preservarInfoLiquidacion(request);
		
		String cuantia = request.getParameter(CMinuta.CUANTIA_ACTO);
		String unidades = request.getParameter(CMinuta.UNIDADES_ACTO);
		String conCuantia = CAccionNotarial.SIN_CUANTIA;
		
		if (request.getParameter(CMinuta.ACCION_NOTARIAL_CON_CUANTIA)!=null){
			conCuantia = CAccionNotarial.CON_CUANTIA;
		}
		
		String tipoAccionNotarial = request.getParameter(CMinuta.TIPO_ACCION_NOTARIAL);
		
		//ActoMinuta actoMinuta = new ActoMinuta();
		AccionNotarial accionNotarial = new AccionNotarial();
		
		//Se detectan las excepciones de validación
		ValidacionLiquidacionRepartoNotarialException exception = new ValidacionLiquidacionRepartoNotarialException();
		
		if ((tipoAccionNotarial == null) || tipoAccionNotarial.equals("SIN_SELECCIONAR")) {
			exception.addError("El Tipo de Contrato Proporcionado es inválido");
			if(exception.getErrores().size() >0) {
			  throw exception;	
			}
		} 
		else {
			List actos = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ACCIONES_NOTARIALES);
			Iterator it = actos.iterator();
			while (it.hasNext()) {
				AccionNotarial am = (AccionNotarial) it.next();
				if (tipoAccionNotarial.equals(am.getIdAccionNotarial())) {
					accionNotarial = am;
				}
			}
					
			MinutaAccionNotarial actoMinuta = new MinutaAccionNotarial();
			actoMinuta.setAccionNotarial(accionNotarial);
			boolean cuantiainvalida=false;
			try {
					if (conCuantia.equals(CAccionNotarial.CON_CUANTIA)) {
						actoMinuta.setValor(cuantia!=null && !cuantia.equals("") ? Double.parseDouble(cuantia) : 0);
					}
			}
			catch(NumberFormatException ee) {
				cuantiainvalida=true;			
				exception.addError("La Cuantia Debe ser valida");		
			 }
			
			try {
				actoMinuta.setUnidades(unidades!=null && !unidades.equals("") ? Long.parseLong(unidades) : 0);
			}
			catch(NumberFormatException ee) {
				exception.addError("Las Unidades Inmobiliarias Deben ser validas");		
			}
			actoMinuta.setConCuantia(Long.parseLong(conCuantia));
			
			/*// SI NO ES PROPIEDAD HORIZONTAL NI PARCELACIONES, SE DEBE INGRESAR EL VALOR DE LA CUANTÍA SINO EL NÚMERO DE LAS UNIDADES  
			if(!(tipoAccionNotarial.equals(CAccionNotarial.REGLAMENTO_PROPIEDAD_HORIZONTAL) 
					|| tipoAccionNotarial.equals(CAccionNotarial.URBANIZACIONES_PARCELACIONES)
					|| tipoAccionNotarial.equals(CAccionNotarial.PARCELACIONES))){
				//minuta.setValor(new Long(cuantia).longValue());
				actoMinuta.setUnidades(0);
				if ()
			}else{
				if ((unidades != null && unidades.length() > 0)) {
					//minuta.setUnidades(new Long(unidades).longValue());
					actoMinuta.setValor(0);
				}
			}*/
			
			long unidadesfinales=-1;
			
			//Se valida que para ciertos tipos de Acciones Notariales son obligatorias las unidades
			if (tipoAccionNotarial.equals(CAccionNotarial.REGLAMENTO_PROPIEDAD_HORIZONTAL) 
				|| tipoAccionNotarial.equals(CAccionNotarial.URBANIZACIONES_PARCELACIONES)) {
				if (unidades!=null) {
					try{
						long unidadesValor = new Long(unidades).longValue();
						if (unidadesValor <= 0) {
							exception.addError("Las Unidades Inmobiliarias deben ser mayores a 0.");
							unidadesfinales=-1;
						}
						else {
							unidadesfinales=unidadesValor;
						}
					} catch (NumberFormatException ee) {
						//exception.addError("Las Unidades Inmobiliarias deben ser validas.");
					}
				} else {
					exception.addError("El tipo de acción notarial debe tener Unidades Inmobiliarias.");
				}
			  }
			  else {
				  
				  try {
					  if(!unidades.equals("")) {
						  unidadesfinales=new Long(unidades).longValue();
						  if(unidadesfinales<0)
						  {
							  exception.addError("El Numero de Unidades Inmobiliarias para el Tipo de Accion Notarial es Opcional, SIn Embargo si lo proporciona debe ser mayor o igual a cero ");	 
						  }
					  }
				  }
				catch(NumberFormatException ee) {
					//error, la accion notarial no tiene unidades inmobiliarias pero esto no es necesario
					//debido al tipo de accion notarial es opcional
					unidadesfinales=-1;
				 }
			   }
		
			
		  //Si seleciona el flag de cuantia este valor debe ser mayor a 0.
			if (conCuantia.equals(CAccionNotarial.CON_CUANTIA)) {
				if (cuantia!=null) {
					try{
						  double cuantiaValor = new Double(cuantia).doubleValue();
						  if (cuantiaValor == 0) {	
							if(unidadesfinales!=0) { 
								exception.addError("La Cuantía Debe Ser Mayor a Cero");
					      }
						}
						else {
						   if(cuantiaValor<0 ) {
						     exception.addError("La Cuantia Debe Ser Mayor a Cero");  				
							}
						}
					 }  catch (NumberFormatException ee)  {
						 if(!cuantiainvalida) {
						    exception.addError("La Cuantía debe ser valida");
				           }
					     }
				}
			}

			if (exception.getErrores().size() > 0) {
				throw exception;
			}
			
			//Se agregan los actos
			List actosMinuta = (List) request.getSession().getAttribute(WebKeys.LISTA_ACTOS_MINUTA);
			if (actosMinuta == null) {
				actosMinuta = new ArrayList();
			}

			if (!this.estaAsociadaActo(actoMinuta, actosMinuta)) {
				actosMinuta.add(actoMinuta);
			} else {
				exception.addError("La accion notarial " + accionNotarial.getNombre() + " ya ha sido agregada.");
				throw exception;
			}

			
				
			request.getSession().setAttribute(WebKeys.LISTA_ACTOS_MINUTA, actosMinuta);
			
			
		}
		
		return null;
	}

	/**
	 * Método que agrega una nueva entidad pública a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial agregarEntidadPublica(HttpServletRequest request) throws AccionWebException {

		this.preservarInfoLiquidacion(request);
		//Se recibe la información que viene del formulario.
		String entPublica = request.getParameter(CMinuta.ENTIDAD_PUBLICA);
		String exento = request.getParameter(CMinuta.ENTIDAD_PUBLICA_EXENTO);
		boolean bExento=false;

		EntidadPublica entidadPublica = new EntidadPublica();

		//Se detectan las excepciones de validación
		ValidacionLiquidacionRepartoNotarialException exception = new ValidacionLiquidacionRepartoNotarialException();

		// Si es obligatorio que se deba seleccionar una entidad publica lanza el error
		if (entPublica == null || entPublica.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar una entidad pública.");
			throw exception;
		}
		
		if (exento != null && exento.equals(CMinuta.EXENTO)) {
			bExento=true;
		}

		List entidades = (List) request.getSession().getServletContext().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS_ACTIVAS);
		Iterator it = entidades.iterator();
		while (it.hasNext()) {
			EntidadPublica ep = (EntidadPublica) it.next();
			if (entPublica.equals(ep.getIdEntidadPublica())) {
				entidadPublica = ep;
			}
		}

		List entidadespublicas = (List) request.getSession().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
		if (entidadespublicas == null) {
			entidadespublicas = new ArrayList();
		}

		if (!this.estaAsociadaEntidadPublica(entidadPublica, entidadespublicas)) {
			entidadPublica.setExento(bExento);
			entidadespublicas.add(entidadPublica);
		} else {
			exception.addError("La entidad pública " + entidadPublica.getNombre() + ", ya ha sido agregada.");
			throw exception;
		}

		request.getSession().setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, entidadespublicas);

		return null;
	}

	/**
	 * Método que agrega una nueva entidad pública a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private boolean estaAsociadaEntidadPublica(EntidadPublica entidadPublica, List entidades) throws AccionWebException {

		boolean asociada = false;

		Iterator it = entidades.iterator();
		while (it.hasNext()) {
			EntidadPublica ep = (EntidadPublica) it.next();
			if (entidadPublica.getIdEntidadPublica().equals(ep.getIdEntidadPublica())) {
				asociada = true;
				break;
			}
		}

		return asociada;
	}
	
	/**
	 * Verifica si un acto ya esta asociado a la solicitud de la minuta
	 * @param accionNotarial
	 * @param actosMinuta
	 * @return booleano que informa si existe la entidad
	 * @throws AccionWebException
	 */
	private boolean estaAsociadaActo(MinutaAccionNotarial actoMinuta, List actosMinuta) throws AccionWebException
	{
		boolean asociada = false;
		AccionNotarial accionNotarial = actoMinuta.getAccionNotarial();
		
		Iterator it = actosMinuta.iterator();
		while(it.hasNext())
		{
			//AccionNotarial an = (AccionNotarial)it.next();
			MinutaAccionNotarial actoMinutaTmp = (MinutaAccionNotarial) it.next();
			AccionNotarial an = actoMinutaTmp.getAccionNotarial();
			if(accionNotarial.getIdAccionNotarial().equals(an.getIdAccionNotarial())){
				asociada = true;
				break;
			}
		}
		
		return asociada;
	}
	
	
	private EvnRepartoNotarial eliminarAccionNotarial(HttpServletRequest request) throws AccionWebException {

		this.preservarInfoLiquidacion(request);
		//Se recibe la información que viene del formulario.
		String posicion = request.getParameter(WebKeys.POSICION);
		int iPosicion = 0;

		//Se detectan las excepciones de validación
		ValidacionLiquidacionRepartoNotarialException exception = new ValidacionLiquidacionRepartoNotarialException();

		if (posicion == null || posicion.length() == 0) {
			exception.addError("Debe seleccionar la accion notarial a eliminar.");
			throw exception;
		} else {
			try {
				iPosicion = new BigDecimal(posicion).intValue();
			} catch (Throwable t) {
				exception.addError("Debe seleccionar la accion notarial a eliminar.");
			}
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		List accionesNotariales = (List) request.getSession().getAttribute(WebKeys.LISTA_ACTOS_MINUTA);
		if (accionesNotariales == null) {
			accionesNotariales = new ArrayList();
		}

		if (accionesNotariales.size() >= (iPosicion + 1)) {			
			accionesNotariales.remove(iPosicion);
		}

		request.getSession().setAttribute(WebKeys.LISTA_ACTOS_MINUTA, accionesNotariales);

		return null;
	}

	/**
	 * Método que elimina una nueva entidad pública a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial eliminarEntidadPublica(HttpServletRequest request) throws AccionWebException {

		this.preservarInfoLiquidacion(request);
		//Se recibe la información que viene del formulario.
		String posicion = request.getParameter(WebKeys.POSICION);
		int iPosicion = 0;

		//Se detectan las excepciones de validación
		ValidacionLiquidacionRepartoNotarialException exception = new ValidacionLiquidacionRepartoNotarialException();

		if (posicion == null || posicion.length() == 0) {
			exception.addError("Debe seleccionar la entidad pública a eliminar.");
			throw exception;
		} else {
			try {
				iPosicion = new BigDecimal(posicion).intValue();
			} catch (Throwable t) {
				exception.addError("Debe seleccionar la entidad pública a eliminar.");
			}
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		List entidadespublicas = (List) request.getSession().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
		if (entidadespublicas == null) {
			entidadespublicas = new ArrayList();
		}

		if (entidadespublicas.size() >= (iPosicion + 1)) {
			entidadespublicas.remove(iPosicion);
		}

		request.getSession().setAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS, entidadespublicas);

		return null;
	}

	/**
	 * Método que agrega un nuevo otorgante natural a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial agregarOtorganteNatural(HttpServletRequest request) throws AccionWebException {

		this.preservarInfoLiquidacion(request);
		//Se recibe la información que viene del formulario.
		String otorgante = request.getParameter(CMinuta.OTORGANTE_NATURAL);
		String exento = request.getParameter(CMinuta.OTORGANTE_NATURAL_EXENTO);
		boolean bExento=false;

		//Se detectan las excepciones de validación
		ValidacionLiquidacionRepartoNotarialException exception = new ValidacionLiquidacionRepartoNotarialException();

		if (otorgante == null || otorgante.length() == 0) {
			exception.addError("Debe ingresar un otorgante natural.");
			throw exception;
		}
		
		if (exento != null && exento.equals(CMinuta.EXENTO)) {
			bExento=true;
		}

		OtorganteNatural otorganteNatural = new OtorganteNatural();
		otorganteNatural.setNombre(otorgante);
		otorganteNatural.setExento(bExento);

		List otorgantes = (List) request.getSession().getAttribute(WebKeys.LISTA_OTORGANTES);
		if (otorgantes == null) {
			otorgantes = new ArrayList();
		}

		otorgantes.add(otorganteNatural);
		request.getSession().setAttribute(WebKeys.LISTA_OTORGANTES, otorgantes);

		return null;
	}

	/**
	 * Método que elimina un otorgante natural a la solicitud de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial eliminarOtorganteNatural(HttpServletRequest request) throws AccionWebException {

		this.preservarInfoLiquidacion(request);
		//Se recibe la información que viene del formulario.		
		String posicion = request.getParameter(WebKeys.POSICION);
		int iPosicion = 0;

		//Se detectan las excepciones de validación
		ValidacionLiquidacionRepartoNotarialException exception = new ValidacionLiquidacionRepartoNotarialException();

		if (posicion == null || posicion.length() == 0) {
			exception.addError("Debe seleccionar el otorgante a eliminar.");
			throw exception;
		} else {
			try {
				iPosicion = new BigDecimal(posicion).intValue();
			} catch (Throwable t) {
				exception.addError("Debe seleccionar el otorgante a eliminar.");
			}
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		List otorgantes = (List) request.getSession().getAttribute(WebKeys.LISTA_OTORGANTES);
		if (otorgantes == null) {
			otorgantes = new ArrayList();
		}

		if (otorgantes.size() >= (iPosicion + 1)) {
			otorgantes.remove(iPosicion);
		}

		request.getSession().setAttribute(WebKeys.LISTA_OTORGANTES, otorgantes);

		return null;
	}
	
	/**
	 * Metodo que determina si las acciones notariales agregadas en la liquidacion son de tipo PROPIEDAD HORIZONTAL o PARCELACIONES
	 * @param accionesNotariales Lista con las acciones notariales agregadas
	 * @return boolean
	 */
	private boolean tieneAccionesNotarialesPropiedadHorizontalOParcelaciones(List accionesNotariales)
	{
		boolean esPropiedadHorizontalOParcelaciones = false;
		Iterator it = accionesNotariales.iterator();
		while (it.hasNext())
		{
			//AccionNotarial accionNotarial = (AccionNotarial)it.next();
			MinutaAccionNotarial actoMinutaTmp = (MinutaAccionNotarial) it.next();
			AccionNotarial accionNotarial = actoMinutaTmp.getAccionNotarial();
			if(accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.REGLAMENTO_PROPIEDAD_HORIZONTAL) 
				|| accionNotarial.getIdAccionNotarial().equals(CAccionNotarial.URBANIZACIONES_PARCELACIONES))
			{
				esPropiedadHorizontalOParcelaciones = true;
				break;
			}
		}
		
		return esPropiedadHorizontalOParcelaciones;
	}

	/**
	 * Método que liquida válida los parametros para la liquidación y creación de un turno de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial liquidar(HttpServletRequest request) throws AccionWebException {
		
//		Se detectan las excepciones de validación
		ValidacionLiquidacionRepartoNotarialException exception = new ValidacionLiquidacionRepartoNotarialException();
		
//		Datos de la sesión.		
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		String UID = request.getSession().getId();
		Estacion estacion = (Estacion) request.getSession().getAttribute(WebKeys.ESTACION);
		Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);
		
		boolean imprimirConstancia = false;
		
		if (request.getSession().getAttribute(CMinuta.IMPRIMIR_CONSTANCIA)!=null){
			imprimirConstancia = true;
		}
		Minuta minuta = (Minuta)request.getSession().getAttribute(CMinuta.MINUTA);
		SolicitudRepartoNotarial solicitudRepartoNotarial = (SolicitudRepartoNotarial) request.getSession().getAttribute(CSolicitudRepartoNotarial.SOLICITUD_REPARTO_NOTARIAL);
		
		List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);

		EvnRepartoNotarial evnt = new EvnRepartoNotarial(usuarioAuriga, (Usuario) request.getSession().getAttribute(WebKeys.USUARIO), solicitudRepartoNotarial, minuta, EvnRepartoNotarial.CREAR_SOLICITUD, UID, rol, estacion, circulo, listaNotas);
		evnt.setImprimirConstancia(imprimirConstancia);
		return evnt;
	}

	/**
	 * Método que liquida válida los parametros para la liquidación y creación de un turno de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial obtenerCategoria(HttpServletRequest request) throws AccionWebException {
	
		//Se recibe la información que viene del formulario.
		String tipoReparto = request.getParameter(CMinuta.TIPO_REPARTO);
		String circuloNotarial = request.getParameter(CMinuta.CIRCULO_NOTARIAL);		
		String nroFolios = request.getParameter(CMinuta.NRO_FOLIOS_MINUTA);
		String observaciones = request.getParameter(CMinuta.OBSERVACIONES_REPARTO);
		List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
		List entidadesPublicas = (List) request.getSession().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
		List otorgantes = (List) request.getSession().getAttribute(WebKeys.LISTA_OTORGANTES);
		List accionesNotariales = (List) request.getSession().getAttribute(WebKeys.LISTA_ACTOS_MINUTA);

                /**
                 * @author : Diana Lora
                 * @change : Mantis 0010028: Acta - Requerimiento No 058_151 - Certificados de otros circulos
                 */
                double unidades = Double.parseDouble( ((String)request.getSession().getAttribute(CMinuta.UNIDAD_SELECCIONADA)).replace(".", "").replace(",", "") );
                double cuantia = Double.parseDouble( ((String)request.getSession().getAttribute(CMinuta.CUANTIA_SELECCIONADA)).replace(".", "").replace(",", "") );

		this.preservarInfoLiquidacion(request);

		//Datos de la sesión.		
		Usuario usuario = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);

		
		//Se llena la minuta		
		Minuta minuta = new Minuta();

		if (tipoReparto.equals(CMinuta.ORDINARIO)) {
			minuta.setNormal(true);
		} else {
			minuta.setNormal(false);
		}
		
		
		CirculoNotarial cn = new CirculoNotarial();
		cn.setIdCirculo(circuloNotarial);
		
		minuta.setCirculoNotarial(cn);
		
		//minuta.setAccionNotarial(accionNotarial);
		if ((nroFolios != null && nroFolios.length() > 0)) {
			minuta.setNumeroFolios(new Long(nroFolios).longValue());
		}
		 
		if(observaciones!=null){
			minuta.setComentario(observaciones.toUpperCase());	
		}
		
//		double cuantiaTotal = 0;
//		long unidadesTotal = 0;
		
		if(accionesNotariales != null && accionesNotariales.size() > 0){
			Iterator it = accionesNotariales.iterator();
			while (it.hasNext())
			{
				MinutaAccionNotarial actoMinutaTmp = (MinutaAccionNotarial) it.next();
//				cuantiaTotal = cuantiaTotal + actoMinutaTmp.getValor();
//				unidadesTotal = unidadesTotal + actoMinutaTmp.getUnidades();
				minuta.addAccionesNotariale(actoMinutaTmp);
			}
		}
	
		/**
                 * @author : Diana Lora
                 * @change : Mantis 0010028: Acta - Requerimiento No 058_151 - Certificados de otros circulos
                 * Se coloca el valor de las unidades y cuantias seleccionada por el usuario.
                 */
		minuta.setUnidades((long) unidades);                
		minuta.setValor(cuantia);
		
		if (entidadesPublicas != null && entidadesPublicas.size() > 0) {
			Iterator it = entidadesPublicas.iterator();
			while (it.hasNext()) {
				EntidadPublica entidadPublica = (EntidadPublica) it.next();
				MinutaEntidadPublica mep = new MinutaEntidadPublica();
				mep.setEntidadPublica(entidadPublica);
				minuta.addEntidadesPublica(mep);
			}
		}

		if (otorgantes != null && otorgantes.size() > 0) {
			Iterator it = otorgantes.iterator();
			while (it.hasNext()) {
				OtorganteNatural oto = (OtorganteNatural) it.next();
				minuta.addOtorgantesNaturale(oto);
			}
		}

		//Se llena la Solicitud de Reparto Notarial
		SolicitudRepartoNotarial solicitudRepartoNotarial = new SolicitudRepartoNotarial();
		solicitudRepartoNotarial.setCirculo(circulo);
		solicitudRepartoNotarial.setProceso(proceso);
		solicitudRepartoNotarial.setTurno(null);
		solicitudRepartoNotarial.setUsuario(usuario);
		
		request.getSession().setAttribute(CMinuta.IMPRIMIR_CONSTANCIA,request.getParameter(CMinuta.IMPRIMIR_CONSTANCIA));
		request.getSession().setAttribute(CSolicitudRepartoNotarial.SOLICITUD_REPARTO_NOTARIAL,solicitudRepartoNotarial);
		request.getSession().setAttribute(CMinuta.MINUTA, minuta);
		EvnRepartoNotarial evnt = new EvnRepartoNotarial(null, (Usuario) request.getSession().getAttribute(WebKeys.USUARIO), solicitudRepartoNotarial, minuta, EvnRepartoNotarial.OBTENER_CATEGORIA, null, null, null, circulo, listaNotas);
		return evnt;
	}

	/**
	 * Método que valida la informacion de la radicacion de minuta
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnRepartoNotarial validarInformacion(HttpServletRequest request) throws AccionWebException {
		this.preservarInfoLiquidacion(request);
		//Se recibe la información que viene del formulario.
		String tipoReparto = request.getParameter(CMinuta.TIPO_REPARTO);
		String circuloNotarial = request.getParameter(CMinuta.CIRCULO_NOTARIAL);		
		String nroFolios = request.getParameter(CMinuta.NRO_FOLIOS_MINUTA);
//		String cuantia = request.getParameter(CMinuta.CUANTIA);
                String cuantia = request.getParameter(CMinuta.CUANTIA_SELECCIONADA).replace(".", "").replace(",", "");
//		String unidades = request.getParameter(CMinuta.UNIDADES);
                String unidades = request.getParameter(CMinuta.UNIDAD_SELECCIONADA);
                boolean tieneAccionesPropiedadHorizontalOParcelaciones = false;
		
		
		//Se detectan las excepciones de validación
		ValidacionLiquidacionRepartoNotarialException exception = new ValidacionLiquidacionRepartoNotarialException();

		if (tipoReparto == null || (tipoReparto.length() <= 0)) {
			exception.addError("Debe seleccionar un tipo de reparto");
		}
		
		if (circuloNotarial == null || (circuloNotarial.length() <= 0)) {
			exception.addError("Debe seleccionar el círculo notarial para el reparto de la minuta");
		}else if(circuloNotarial.equals(WebKeys.SIN_SELECCIONAR)){
			exception.addError("Debe seleccionar el círculo notarial para el reparto de la minuta");		
		}
		
		List listaNotas = (List) request.getSession().getAttribute(WebKeys.LISTA_NOTAS_INFORMATIVAS_SIN_TURNO);
		if ((listaNotas == null || listaNotas.size()==0)&& tipoReparto.equals(CMinuta.EXTRAORDINARIO))
		{
			exception.addError("La radicación de la minuta es extraordinaria. Debe ingresar una nota informativa.");
		}

		if ((nroFolios != null && nroFolios.length() > 0)) {
			try {
				Double.parseDouble(nroFolios);
			} catch (Exception e) {
				exception.addError("Debe ingresar valores correctos para el número de folios");
			}

		}

		List entidadesPublicas = (List) request.getSession().getAttribute(WebKeys.LISTA_ENTIDADES_PUBLICAS);
		List accionesNotariales = (List) request.getSession().getAttribute(WebKeys.LISTA_ACTOS_MINUTA);

		boolean esObligatorio = true;
		
		if (accionesNotariales == null || accionesNotariales.size() == 0) {
			exception.addError("Debe ingresar por lo menos una accion notarial");
		}
		
		for(int i=0; i<accionesNotariales.size();i++){
			MinutaAccionNotarial acc = (MinutaAccionNotarial)accionesNotariales.get(i);
			if(acc.getAccionNotarial().getIdAccionNotarial().equals(CAccionNotarial.CATEGORIA_DECLARATORIA_DE_PRESCRIPCION_ADQUISITIVA)
					|| acc.getAccionNotarial().getIdAccionNotarial().equals(CAccionNotarial.DECLARACION_DE_LA_POSESION_REGULAR)){
				esObligatorio = false;
			}
		}

		if ((entidadesPublicas == null || entidadesPublicas.size() == 0) && esObligatorio) {
			exception.addError("Debe ingresar por lo menos una entidad pública relacionada");
		}
		
		this.preservarInfoLiquidacion(request);
		//Se lanzan los errores si existieron en la validación.
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		tieneAccionesPropiedadHorizontalOParcelaciones = 
			this.tieneAccionesNotarialesPropiedadHorizontalOParcelaciones(accionesNotariales);
		
		if (tieneAccionesPropiedadHorizontalOParcelaciones) {
			if ((unidades == null || unidades.length() <= 0)) {
				exception.addError("Debe ingresar el número de las unidades");
			} else {
				try {
					Double.parseDouble(unidades);
                                        request.getSession().setAttribute(CMinuta.UNIDAD_SELECCIONADA, unidades);
				} catch (Exception e) {
					exception.addError("Debe ingresar valores correctos para el número de las unidades");
				}
			}
		}/* else{
                    request.getSession().setAttribute(CMinuta.UNIDAD_SELECCIONADA, "0");
                }*/
		
		if ((cuantia == null || cuantia.length() <= 0)) {
			if(!(tieneAccionesPropiedadHorizontalOParcelaciones)){
                                exception.addError("Debe seleccionar el valor de la Cuantia y/o Unidades");
			}
		} else {
			try {
				Double.parseDouble(cuantia);
                                request.getSession().setAttribute(CMinuta.CUANTIA_SELECCIONADA, cuantia);
			} catch (Exception e) {
				exception.addError("Debe ingresar valores correctos para el valor de la cuantía");
			}
		}

                if(!exception.getErrores().isEmpty()){
                    throw exception;
                }

		request.getSession().setAttribute(WebKeys.RECARGA, new Boolean (true));
		return null;
	}
	/**
	 * Método que preserva la información del formulario de creación de un turno de reparto notarial.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private void preservarInfoLiquidacion(HttpServletRequest request) {

		//Los datos se suben a la sesión para preservar la información del formulario.
		HttpSession sesion = request.getSession();
		
		sesion.setAttribute(CMinuta.TIPO_REPARTO, request.getParameter(CMinuta.TIPO_REPARTO));
                sesion.setAttribute(CMinuta.ID_ACCION_NOTARIAL_SELECCIONADA, request.getParameter(CMinuta.ID_ACCION_NOTARIAL_SELECCIONADA));
                sesion.setAttribute(CMinuta.CUANTIA_SELECCIONADA, request.getParameter(CMinuta.CUANTIA_SELECCIONADA));
                sesion.setAttribute(CMinuta.UNIDAD_SELECCIONADA, request.getParameter(CMinuta.UNIDAD_SELECCIONADA));
		sesion.setAttribute(CMinuta.TIPO_REPARTO, request.getParameter(CMinuta.TIPO_REPARTO));
		sesion.setAttribute(CMinuta.ID_TIPO_ACCION_NOTARIAL, request.getParameter(CMinuta.ID_TIPO_ACCION_NOTARIAL));
		sesion.setAttribute(CMinuta.TIPO_ACCION_NOTARIAL, request.getParameter(CMinuta.TIPO_ACCION_NOTARIAL));
		sesion.setAttribute(CMinuta.CIRCULO_NOTARIAL, request.getParameter(CMinuta.CIRCULO_NOTARIAL));		
		sesion.setAttribute(CMinuta.NRO_FOLIOS_MINUTA, request.getParameter(CMinuta.NRO_FOLIOS_MINUTA));
		sesion.setAttribute(CMinuta.CUANTIA, request.getParameter(CMinuta.CUANTIA));
		sesion.setAttribute(CMinuta.UNIDAD_SELECCIONADA, request.getParameter(CMinuta.UNIDAD_SELECCIONADA));
		sesion.setAttribute(CMinuta.OBSERVACIONES_REPARTO, request.getParameter(CMinuta.OBSERVACIONES_REPARTO));
		//sesion.setAttribute(CMinuta.LISTA_ACTOS_REPARTO, )
		
		if (request.getParameter(CMinuta.ACCION_NOTARIAL_CON_CUANTIA)==null){
			sesion.setAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA, "ACCION_NOTARIAL_SIN_CUANTIA");
		} else{
			sesion.setAttribute(CMinuta.ACCION_NOTARIAL_CON_CUANTIA, request.getParameter(CMinuta.ACCION_NOTARIAL_CON_CUANTIA));
		}
		
		if (request.getParameter(CMinuta.IMPRIMIR_CONSTANCIA)==null){
			sesion.setAttribute(CMinuta.IMPRIMIR_CONSTANCIA, "IMPRIMIR_SIN_CONSTANCIA");
		} else{
			sesion.setAttribute(CMinuta.IMPRIMIR_CONSTANCIA, request.getParameter(CMinuta.IMPRIMIR_CONSTANCIA));
		}
		
	}

	/*
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespRepartoNotarial respuesta = (EvnRespRepartoNotarial) evento;

		if (respuesta != null) 
		{
			
			if (respuesta.getTipoEvento().equals(EvnRespRepartoNotarial.CARGAR_CIRCULOS_NOTARIALES)) {
				List circulosNotariales = (List)respuesta.getPayload();				
				request.getSession().setAttribute(WebKeys.LISTA_CIRCULOS_NOTARIALES, circulosNotariales);
			}else if (respuesta.getTipoEvento().equals(EvnRespRepartoNotarial.OBTENER_CATEGORIA_OK)) {
				request.getSession().setAttribute(CCategoria.CATEGORIA, respuesta.getCategoria());
			}			
			
			if (respuesta.getTurno() != null) 
			{
				request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());
			}
		}
                /*AHERRENO
                 28/05/2012
                 REQ 076_151 TRANSACCION*/
                 Date fechaIni =  (Date) request.getSession().getAttribute("TIEMPO_INICIO_TRANSACCION");
                double tiempoSession =  (Double) request.getSession().getAttribute("TIEMPO_TRANSACCION");
                Date fechaFin =  new Date();
                TransaccionSIR transaccion = new TransaccionSIR();
                List <Transaccion> acciones = (List <Transaccion>) request.getSession().getAttribute("LISTA_TRANSACCION");
                long calTiempo = 0;
        try {
            calTiempo = transaccion.calculoTransaccion(fechaIni, fechaFin);
        } catch (Exception ex) {
            Logger.getLogger(AWLiquidacionRepartoNotarial.class.getName()).log(Level.SEVERE, null, ex);
        }                
                DecimalFormat df = new DecimalFormat("0.000"); 
                double calculo = Double.valueOf(df.format(tiempoSession+((double)calTiempo/1000)).replace(',', '.'));
                System.out.println("El tiempo de la accion "+request.getParameter("ACCION")+" en milisegundos " +calTiempo );
                request.getSession().setAttribute("TIEMPO_TRANSACCION",calculo);
                Transaccion transaccionReg = new Transaccion();
                transaccionReg.setFechaTransaccion(fechaFin);
                transaccionReg.setAccionWeb(request.getParameter("ACCION"));
                transaccionReg.setTiempo(calTiempo);
                acciones.add(transaccionReg);
                request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
                
                if(request.getParameter("ACCION").equals("LIQUIDAR")){
                EvnRespRepartoNotarial  eventoResp = (EvnRespRepartoNotarial) respuesta;
                Turno turno =  null;
                if(eventoResp instanceof EvnRespRepartoNotarial){               
                        turno = (Turno)eventoResp.getTurno();
                            /*Se recorre la lista para almacenar la informacion del turno*/
                        if(turno != null){        
                            for (Transaccion transacion: acciones) {
                                    transacion.setAnio(turno.getAnio());
                                    transacion.setIdCirculo(turno.getIdCirculo());
                                    transacion.setIdProceso(turno.getIdProceso());
                                    transacion.setIdTurno(turno.getIdTurno());                    
                                }
                            transaccion.guardarTransaccion(acciones);
                            acciones.clear();
                            request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
                            request.getSession().setAttribute("TIEMPO_TRANSACCION",Double.valueOf(0));                    
                        }
                        }    
                }                    
	}
}
