package gov.sir.core.web.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnAdministracionCiudadano;
import gov.sir.core.eventos.administracion.EvnAdministracionForseti;
import gov.sir.core.eventos.administracion.EvnRespAdministracionCiudadano;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoProhibicion;
import gov.sir.core.negocio.modelo.Prohibicion;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCiudadanoProhibicion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CProhibicion;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionConsultarCiudadanoException;
import gov.sir.core.web.acciones.excepciones.ValidacionEditarRestriccionesCiudadanoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.acciones.excepciones.ValidacionProhibicionesException;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Usuario;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Acción Web encargada de manejar solicitudes para generar eventos
 * de administración de ciudadanos provenientes de solicitudes 
 * a través del protocolo HTTP
 * @author jmendez
 *
 */
public class AWAdministracionCiudadano extends SoporteAccionWeb {

	/** Constante que identifica la acción de adicionar una prohibición */
	public static final String ADICIONA_PROHIBICION = "ADICIONA_PROHIBICION";
	
	/** Constante que identifica la acción de editar una prohibición */
	public static final String EDITA_PROHIBICION = "EDITA_PROHIBICION";
	
	/** Constante que identifica la acción de editar los atributos de una prohibición */
    public static final String EDITA_DETALLES_PROHIBICION = "EDITA_DETALLES_PROHIBICION";
    
	/** Constante que identifica la acción de terminar la edición de los atributos de una prohibición */
	public static final String TERMINA_EDICION_PROHIBICION = "TERMINA_EDICION_PROHIBICION";

	/** Constante que identifica la acción de eliminar una prohibición */
	public static final String ELIMINA_PROHIBICION = "ELIMINA_PROHIBICION";

	/** Constante que identifica la acción de consultar un ciudadano */
	public static final String CIUDADANO_CONSULTAR = "CIUDADANO_CONSULTAR";

	/** Constante que identifica la acción de seleccionar  un ciudadano */
	public static final String CIUDADANO_SELECCIONAR = "CIUDADANO_SELECCIONAR";

	/** Constante que identifica la acción de cancelar la edición de  un ciudadano */
	public static final String CIUDADANO_CANCELAR_EDICION = "CIUDADANO_CANCELAR_EDICION";

	/** Constante que identifica la acción de adicionar una prohibición */
	public static final String CIUDADANO_ADICIONAR_PROHIBICION = "CIUDADANO_ADICIONAR_PROHIBICION";

	/** Constante que identifica las acción de eliminar una prohibición */
	public static final String CIUDADANO_ELIMINAR_PROHIBICION = "CIUDADANO_ELIMINAR_PROHIBICION";

	/** Constante que identifica las acción de terminar la utilización de los servicios 
	 * de la acción WEB (Para limpiar la sesión y redirigir a la página principal de páginas
	 * administrativas */
	public static final String TERMINA = "TERMINA";

	///////////////////////

	/** Constante que identifica la variable del HttpSession donde se almacena un ciudadano seleccionado */
	public static final String CIUDADANO_SELECCIONADO = "CIUDADANO_SELECCIONADO";

	/** Constante que identifica la variable del HttpSession donde se almacena la lista elementos de Prohibicion 
	 * para ser desplegados en un Helper de tipò ListaElementoHelper 
	 * */
	public static final String LISTA_ELEMENTO_PROHIBICIONES = "LISTA_ELEMENTO_PROHIBICIONES";

	public static final String CIUDADANO_CREAR = "CIUDADANO_CREAR";

	public static final String CIUDADANO_CREADO = "CIUDADANO_CREADO";

	/**
	 * Este método se encarga de procesar la solicitud del  <code>HttpServletRequest</code>
	 * de acuerdo al tipo de accion que tenga como parámetro
	 */
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		String accion = request.getParameter(WebKeys.ACCION).trim();
		HttpSession session = request.getSession();

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		} else if (accion.equals(AWAdministracionCiudadano.ADICIONA_PROHIBICION)) {
			return adicionaProhibicion(request);
		} else if (accion.equals(AWAdministracionCiudadano.ELIMINA_PROHIBICION)) {
			return eliminaProhibicion(request);
		} else if (accion.equals(AWAdministracionCiudadano.EDITA_PROHIBICION)) {
			return editarProhibicion(request);
		} 
		
		//Editar los atributos de una prohibición existente.
		else if (accion.equals(AWAdministracionCiudadano.EDITA_DETALLES_PROHIBICION)) {
			return editarDetallesProhibicion(request);
		} 
		
		//Terminar o cancelar edición de prohibición.
		else if (accion.equals(AWAdministracionCiudadano.TERMINA_EDICION_PROHIBICION)) {
			return terminarEdicionProhibicion(request);
		}
		
		else if (accion.equals(AWAdministracionCiudadano.TERMINA)) {
			return limpiarSesion(request);
		} else if (accion.equals(AWAdministracionCiudadano.CIUDADANO_CONSULTAR)) {
			return consultarCiudadano(request);
		} else if (accion.equals(AWAdministracionCiudadano.CIUDADANO_CREAR)) {
			return crearCiudadano(request);
		} else if (accion.equals(AWAdministracionCiudadano.CIUDADANO_CANCELAR_EDICION)) {
			session.removeAttribute(CIUDADANO_SELECCIONADO);
			return null;
		} else if (accion.equals(AWAdministracionCiudadano.CIUDADANO_ADICIONAR_PROHIBICION)) {
			return adicionarProhibicionCiudadano(request);
		} else if (accion.equals(AWAdministracionCiudadano.CIUDADANO_ELIMINAR_PROHIBICION)) {
			return eliminarProhibicionCiudadano(request);
		}

		return null;
	}

	private Evento crearCiudadano(HttpServletRequest request) throws AccionWebException {
		
		ValidacionParametrosException exception = new ValidacionParametrosException();
		
		Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		Usuario usuario = (Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		String tipoDocumento = request.getParameter(CCiudadano.TIPODOC + "1");
		String numero = request.getParameter(CCiudadano.IDCIUDADANO);
		String primerApellido = request.getParameter(CCiudadano.APELLIDO1);
		String segundoApellido = request.getParameter(CCiudadano.APELLIDO2);
		String nombre = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);
		
		if(tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
		} else if(tipoDocumento.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
			if (primerApellido == null || primerApellido.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else if(tipoDocumento.equals(COPLookupCodes.NIT)) {
			if (primerApellido == null || primerApellido.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} /*AHERRENO 17/01/2013
                    REQUERIMIENTO  022_453. Se realiza configuración en la pantalla administrativa
                    VALIDACIONES Y RESTRICCIONES - Administración de Prohibiciones por Ciudadanos */
                else if(tipoDocumento.equals("PS")) {
                        String regexSL = "^[a-zA-Z]+$";
                        String regexSN = "^[0-9]+$";
                        String regexLN = "^[a-zA0-Z9]+$";
                        java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                        java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                        java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                        boolean esC = false;
                        if(patternSL.matcher(numero).matches()) esC = true;
                        else if(patternSN.matcher(numero).matches()) esC = true;
                        else if(patternLN.matcher(numero).matches()) esC = true;
                        else exception.addError("El número de identificación de la persona es inválido. Debe ser alfanumérico");
                    }else {
			double valorId = 0.0d;
			if(numero == null || numero.trim().equals("")) {
				exception.addError("Debe ingresar el número de identificación del Ciudadano");
			} else {
				if (!tipoDocumento.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
					try {
						valorId = Double.parseDouble(numero);
						if (valorId <= 0) {
							exception.addError("El valor del documento no puede ser negativo o cero");
						}
					} catch (NumberFormatException e) {
						exception.addError("El número de identificación de la persona es inválido. No puede ser alfanumérico");
					}
				}
			}
			if (nombre == null || nombre.trim().equals("")) {
				exception.addError("Debe ingresar el nombre del Ciudadano");
			}
			if (primerApellido == null || primerApellido.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		}
		
		if(exception.getErrores().size() > 0) {
			throw exception;
		}
		
		Ciudadano ciudadano = null;

		if (telefono != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setTelefono(telefono);
		}

		if(tipoDocumento != null) {
			if(ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setTipoDoc(tipoDocumento);
		}

		if(numero != null) {
			if(ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setDocumento(numero);
		}

		if(nombre != null) {
			if(ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setNombre(nombre);
		}

		if(primerApellido != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setApellido1(primerApellido);
		}

		if(segundoApellido != null) {
			if(ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setApellido2(segundoApellido);
		}
		
		ciudadano.setIdCirculo(circulo != null ? circulo.getIdCirculo() : null);
		
		EvnAdministracionCiudadano evento =
			new EvnAdministracionCiudadano(usuario, EvnAdministracionCiudadano.CIUDADANO_CREAR);
		evento.setCiudadano(ciudadano);
		
		return evento;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la creación de una prohibición
	 * @param request
	 * @return evento <code>EvnAdministracionCiudadano</code> con la información de la prohibición
	 * a crear.
	 * @throws AccionWebException
	 */
	private EvnAdministracionCiudadano adicionaProhibicion(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionProhibicionesException exception = new ValidacionProhibicionesException();

		/*String id = request.getParameter(CProhibicion.PROHIBICION_ID);
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un identificador para la prohibición.");
		} else {
			
         //Se convierten a mayúsculas los parámetros recibidos, para ser almacenados de esta
		 //manera en la Base de Datos. 
		 session.setAttribute(CProhibicion.PROHIBICION_ID, id);
		}*/

		String nombre = request.getParameter(CProhibicion.PROHIBICION_NOMBRE);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un nombre para la Prohibición.");
		} else {
			session.setAttribute(CProhibicion.PROHIBICION_NOMBRE, nombre);
		}

		String descripcion = request.getParameter(CProhibicion.PROHIBICION_DESCRIPCION);
		if ((descripcion == null) || (descripcion.trim().length() == 0)) {
			exception.addError("Debe Proporcionar una descripción para la Prohibición.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Prohibicion dato = new Prohibicion();
		
		
		//dato.setIdProhibicion(id);
		dato.setNombre(nombre);
		dato.setDescripcion(descripcion);

		EvnAdministracionCiudadano evento =
			new EvnAdministracionCiudadano(usuario, EvnAdministracionCiudadano.PROHIBICION_CREAR);
		evento.setProhibicion(dato);
		return evento;
	}

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code>
	 * para la eliminación de una prohibición
	 * @param request
	 * @return evento <code>EvnAdministracionCiudadano</code> con la información de la prohibición
	 * a eliminar.
	 * @throws AccionWebException
	 */
	private EvnAdministracionCiudadano eliminaProhibicion(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionProhibicionesException exception = new ValidacionProhibicionesException();

		String id = request.getParameter(CProhibicion.PROHIBICION_ID);
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un identificador para la prohibición.");
		} else {
			session.setAttribute(CProhibicion.PROHIBICION_ID, id);
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Prohibicion dato = new Prohibicion();
		dato.setIdProhibicion(id);

		EvnAdministracionCiudadano evento =
			new EvnAdministracionCiudadano(usuario, EvnAdministracionCiudadano.PROHIBICION_ELIMINAR);
		evento.setProhibicion(dato);
		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de consulta de ciudadanos por tipo de documento y número de documento
	 * @param request
	 * @return evento <code>EvnAdministracionCiudadano</code> con la información del <code>Ciudadano</code> a consultar.
	 * @throws AccionWebException
	 */
	private EvnAdministracionCiudadano consultarCiudadano(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		session.removeAttribute(CIUDADANO_SELECCIONADO);
		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);

		ValidacionConsultarCiudadanoException exception = new ValidacionConsultarCiudadanoException();

		String tipoDocumento = request.getParameter(CCiudadano.TIPODOC);
		if (tipoDocumento == null
			|| tipoDocumento.trim().length() == 0
			|| tipoDocumento.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar un tipo de Documento");
		} else {
			session.setAttribute(CCiudadano.TIPODOC, tipoDocumento);
		}

		String numeroIdent = request.getParameter(CCiudadano.DOCUMENTO);
		if ((numeroIdent == null) || (numeroIdent.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un número de identificación");
		} else {
			session.setAttribute(CCiudadano.DOCUMENTO, numeroIdent);
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Ciudadano dato = new Ciudadano();
		dato.setTipoDoc(tipoDocumento);
		dato.setDocumento(numeroIdent);
		
		//Se setea el circulo del ciudadano
		dato.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

		EvnAdministracionCiudadano evento =
			new EvnAdministracionCiudadano(usuario, EvnAdministracionCiudadano.CIUDADANO_CONSULTAR);
		evento.setCiudadano(dato);
		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de creación de una prohibición para un ciudadano.
	 * @param request
	 * @return evento <code>EvnAdministracionCiudadano</code> con la información del 
	 * <code>CiudadanoProhibicion</code> a crear.
	 * @throws AccionWebException
	 */
	private EvnAdministracionCiudadano adicionarProhibicionCiudadano(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionEditarRestriccionesCiudadanoException exception =
			new ValidacionEditarRestriccionesCiudadanoException();
		Circulo circulo = (Circulo)request.getSession().getAttribute(WebKeys.CIRCULO);
		String tipoProhibicion = request.getParameter(CProhibicion.PROHIBICION_ID);
		if (tipoProhibicion == null
			|| tipoProhibicion.trim().length() == 0
			|| tipoProhibicion.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar un tipo de Prohibición");
		} else {
			tipoProhibicion = tipoProhibicion.toUpperCase();
			session.setAttribute(CProhibicion.PROHIBICION_ID, tipoProhibicion);
		}

		String comentario = request.getParameter(CCiudadanoProhibicion.CIUDADANO_PROHIBICION_COMENTARIO);
		if ((comentario == null) || (comentario.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un Comentario para la prohibición");
		} else {
			session.setAttribute(CCiudadanoProhibicion.CIUDADANO_PROHIBICION_COMENTARIO, comentario);
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Ciudadano ciudadano =
			(Ciudadano) session.getAttribute(AWAdministracionCiudadano.CIUDADANO_SELECCIONADO);
		Prohibicion prohibicion = new Prohibicion();
		prohibicion.setIdProhibicion(tipoProhibicion);

		CiudadanoProhibicion dato = new CiudadanoProhibicion();
		dato.setIdProhibicion(tipoProhibicion);
		dato.setCiudadano(ciudadano);
		dato.setIdCiudadano(ciudadano.getIdCiudadano());
		dato.setProhibicion(prohibicion);
		dato.setFechaInicial(new Date());
		dato.setComentario(comentario);
		dato.setIdCirculo(circulo.getIdCirculo());

		EvnAdministracionCiudadano evento =
			new EvnAdministracionCiudadano(
				usuario,
				EvnAdministracionCiudadano.CIUDADANO_PROHIBICION_ADICIONAR);
		evento.setCiudadanoProhibicion(dato);
		evento.setCiudadano(ciudadano);
		return evento;
	}

	//	//////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento de eliminación de una prohibición para un ciudadano.
	 * @param request
	 * @return evento <code>EvnAdministracionCiudadano</code> con la información del 
	 * <code>CiudadanoProhibicion</code> a eliminar.
	 * @throws AccionWebException
	 */
	private EvnAdministracionCiudadano eliminarProhibicionCiudadano(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionEditarRestriccionesCiudadanoException exception =
			new ValidacionEditarRestriccionesCiudadanoException();

		String tipoProhibicion = request.getParameter(CProhibicion.PROHIBICION_ID);
		if (tipoProhibicion == null || tipoProhibicion.trim().length() == 0) {
			exception.addError("Debe proporcionar un tipo de Prohibición");
		}
		
		String comentarioAnulacion = request.getParameter(CProhibicion.COMENTARIO_ANULACION);
		if (comentarioAnulacion == null || comentarioAnulacion.trim().length() == 0) {
			exception.addError("Debe proporcionar un comentario de Prohibición");
		}

		String fechaStr = request.getParameter(CCiudadanoProhibicion.CIUDADANO_PROHIBICION_FECHA_ID);
		if ((fechaStr == null) || (fechaStr.trim().length() == 0)) {
			exception.addError("Debe Proporcionar una Fecha");
		}

		Date fecha = null;
		try {
			fecha = DateFormatUtil.parse("dd/MM/yyyy HH:mm:ss", fechaStr);
		} catch (java.text.ParseException e) {
			exception.addError("El formato de fecha no es válido.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Ciudadano ciudadano =
			(Ciudadano) session.getAttribute(AWAdministracionCiudadano.CIUDADANO_SELECCIONADO);

		Prohibicion prohibicion = new Prohibicion();
		prohibicion.setIdProhibicion(tipoProhibicion);

		CiudadanoProhibicion dato = new CiudadanoProhibicion();
		dato.setIdProhibicion(tipoProhibicion);
		dato.setCiudadano(ciudadano);
		dato.setIdCiudadano(ciudadano.getIdCiudadano());
		dato.setProhibicion(prohibicion);
		dato.setFechaInicial(fecha);
		dato.setComentarioAnulacion(comentarioAnulacion);

		EvnAdministracionCiudadano evento =
			new EvnAdministracionCiudadano(
				usuario,
				EvnAdministracionCiudadano.CIUDADANO_PROHIBICION_ELIMINAR);
		evento.setCiudadanoProhibicion(dato);
		evento.setCiudadano(ciudadano);
		return evento;
	}

	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de limpiar la sesión luego que el usuario ha terminado de 
	 * usar las pantallas administrativas relacionadas con Ciudadanos
	 */
	private EvnAdministracionForseti limpiarSesion(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();
		session.removeAttribute(AWAdministracionCiudadano.CIUDADANO_SELECCIONADO);
		session.removeAttribute(AWAdministracionCiudadano.LISTA_ELEMENTO_PROHIBICIONES);
		session.removeAttribute(CCiudadano.IDCIUDADANO);
		session.removeAttribute(CCiudadano.DOCUMENTO);
		session.removeAttribute(CProhibicion.PROHIBICION_DESCRIPCION);
		session.removeAttribute(CProhibicion.PROHIBICION_ID);
		session.removeAttribute(CProhibicion.PROHIBICION_NOMBRE);
		return null;
	}

	
	/**
		 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
		 * procesar un evento de edición de un tipo de prohibición.
		 * @param request
		 * @return evento <code>EvnAdministracionHermod</code> con los datos del tipo de acto
		 * @throws AccionWebException
		 */
		private EvnAdministracionCiudadano editarProhibicion(HttpServletRequest request)
			throws AccionWebException {
				
			HttpSession session = request.getSession();

			ValidacionProhibicionesException exception = new ValidacionProhibicionesException();

			//Obtener el identificador de la prohibición y subirlo a la sesión.
			String id = request.getParameter(CProhibicion.PROHIBICION_ID);
			if ((id == null) || (id.trim().length() == 0)) {
				exception.addError("Debe Proporcionar un identificador para la prohibición.");
			} else {
				session.setAttribute(CProhibicion.PROHIBICION_ID, id);
			}
			
            //Obtener el nombre de la prohibición y subirlo a la sesión.
			String nombre = request.getParameter(CProhibicion.PROHIBICION_NOMBRE);
			if ((nombre == null) || (nombre.trim().length() == 0)) {
				exception.addError("Debe Proporcionar un nombre para la prohibición.");
			} else {
				session.setAttribute(CProhibicion.PROHIBICION_NOMBRE, nombre);
			}

			//Obtener la descripción de la prohibición y subirla a la sesión.
			String descripcion = request.getParameter(CProhibicion.PROHIBICION_DESCRIPCION);
			if ((descripcion == null) || (descripcion.trim().length() == 0)) {
				exception.addError("Debe Proporcionar una descripción para la prohibición.");
			} else {
				session.setAttribute(CProhibicion.PROHIBICION_DESCRIPCION, descripcion);
			}

					
		return null;
		}
	
	
	
	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * procesar un evento de edición de un tipo de prohibición.
	 * @param request
	 * @return evento <code>EvnAdministracionHermod</code> con los datos del tipo de acto
	 * @throws AccionWebException
	 */
	private EvnAdministracionCiudadano terminarEdicionProhibicion(HttpServletRequest request)
		throws AccionWebException {
					
		return null;
	}
	
	
	
	
	
	//	//////////////////////////////////////////////////////////////////////
	 ////////////////////////////////////////////////////////////////////////
	 ////////////////////////////////////////////////////////////////////////
	/**
	 * Este método se encarga de manejar el evento de respuesta proveniente 
	 * de la acción de negocio. 
	 * Sube datos a sesión de acuerdo al tipo de respuesta proveniente en el evento
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		EvnRespAdministracionCiudadano respuesta = (EvnRespAdministracionCiudadano) evento;

		HttpSession session = request.getSession();
		context = session.getServletContext();

		if (respuesta != null) {
			String tipoEvento = respuesta.getTipoEvento();
			if (tipoEvento.equals(EvnRespAdministracionCiudadano.PROHIBICION_CREAR_OK)
				|| tipoEvento.equals(EvnRespAdministracionCiudadano.PROHIBICION_ELIMINAR_OK)) {
				List elementos = (List) respuesta.getPayload();
				context.setAttribute(WebKeys.LISTA_PROHIBICIONES, elementos);
				session.removeAttribute(CProhibicion.PROHIBICION_DESCRIPCION);
				session.removeAttribute(CProhibicion.PROHIBICION_ID);
				session.removeAttribute(CProhibicion.PROHIBICION_NOMBRE);
				return;
			} else if (
				tipoEvento.equals(EvnRespAdministracionCiudadano.CIUDADANO_CONSULTAR_OK)
					|| tipoEvento.equals(EvnRespAdministracionCiudadano.CIUDADANO_ADICIONAR_OK)
					|| tipoEvento.equals(EvnRespAdministracionCiudadano.CIUDADANO_ELIMINAR_OK)) {
				Ciudadano dato = (Ciudadano) respuesta.getPayload();
				if (dato != null) {
					session.setAttribute(AWAdministracionCiudadano.CIUDADANO_SELECCIONADO, dato);
				}
				session.removeAttribute(CCiudadanoProhibicion.CIUDADANO_PROHIBICION_COMENTARIO);
				return;
			} else if (tipoEvento.equals(EvnRespAdministracionCiudadano.CIUDADANO_CREAR_OK)) {
				Ciudadano dato = (Ciudadano) respuesta.getPayload();
				if(dato != null) {
					session.setAttribute(AWAdministracionCiudadano.CIUDADANO_CREADO, dato);
				}
				return;
			}
		}
	}
	
	
	

	/**
	 * Este método se encarga de tomar los valores del <code>HttpServletRequest</code> para
	 * generar un evento para la edición de una prohibición
	 * @param request
	 * @return evento <code>EvnAdministracionCiudadano</code> con la información de la prohibición
	 * que se va  a editar.
	 * @throws AccionWebException
	 */
	private EvnAdministracionCiudadano editarDetallesProhibicion(HttpServletRequest request)
		throws AccionWebException {
		HttpSession session = request.getSession();

		ValidacionProhibicionesException exception = new ValidacionProhibicionesException();

		String id = request.getParameter(CProhibicion.PROHIBICION_ID);
		if ((id == null) || (id.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un identificador para la prohibición.");
		} else {
		 session.setAttribute(CProhibicion.PROHIBICION_ID, id);
		}

		String nombre = request.getParameter(CProhibicion.PROHIBICION_NOMBRE);
		if ((nombre == null) || (nombre.trim().length() == 0)) {
			exception.addError("Debe Proporcionar un nombre para la Prohibición.");
		} else {
			session.setAttribute(CProhibicion.PROHIBICION_NOMBRE, nombre);
		}

		String descripcion = request.getParameter(CProhibicion.PROHIBICION_DESCRIPCION);
		if ((descripcion == null) || (descripcion.trim().length() == 0)) {
			exception.addError("Debe Proporcionar una descripción para la Prohibición.");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		Usuario usuario = (Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Prohibicion dato = new Prohibicion();
		
		
		dato.setIdProhibicion(id);
		dato.setNombre(nombre);
		dato.setDescripcion(descripcion);

		EvnAdministracionCiudadano evento =
			new EvnAdministracionCiudadano(usuario, EvnAdministracionCiudadano.PROHIBICION_EDITAR);
		evento.setProhibicion(dato);
		return evento;
	}

	

}
