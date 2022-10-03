package gov.sir.core.web.acciones.certificado;

import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.comun.modelo.Transaccion;
/**
* @Autor: Edgar Lora
* @Mantis: 11309                                * 
*/
import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import gov.sir.core.eventos.certificado.EvnCertificado;
import gov.sir.core.eventos.certificado.EvnRespCertificado;
import gov.sir.core.eventos.certificado.EvnRespValidacionMatricula;
import gov.sir.core.eventos.certificado.EvnValidacionMatricula;
import gov.sir.core.eventos.comun.EvnLiquidacion;
import gov.sir.core.eventos.comun.EvnRespLiquidacion;
import gov.sir.core.eventos.comun.EvnRespPago;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Departamento;
import gov.sir.core.negocio.modelo.DocPagoEfectivo;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.DocumentoPago;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Liquidacion;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.constantes.CCertificado;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CTipoTarifa;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.ValidacionLiquidacionException;
import gov.sir.core.web.acciones.excepciones.ValidacionMatriculaRegistroException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.forseti.ForsetiException;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.HermodService;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

/**
 * Esta clase realiza la liquidacion de la solicitud de certificados.
 * @author jfrias,mmunoz,dcantor
 */
public class AWCertificadoEspecial extends SoporteAccionWeb {
	
	/**Constante que identifica el campo donde se escribe la fecha de la escritura*/
	//public final static String CALENDAR = "CALENDAR";    

	/**Constante que identifica el campo donde se escribe el comentario de la escritura **/
	//public final static String COMENTARIO_ESCRITURA = "COMENTARIO_ESCRITURA";  

	/**Constante que identifica el campo donde se escribe el numero de escritura **/
	//public final static String NUMERO_ESCRITURA="NUMERO_ESCRITURA";

	/**Constante que identifica el campo donde se escribe la matricula de antiguo sistema **/
	//public final static String MATRICULA_ANT_SISTEMA ="MATRICULA_ANT_SISTEMA";    

	/**Constante que identifica el campo donde se escribe el comentario*/
	//public final static String COMENTARIO = "COMENTARIO";

	/** Constante que identifica el campo del jsp donde se solicita una nueva entrada*/
	public final static String NUEVA_ENTRADA = "NUEVA_ENTRADA";

	/** Constante que identifica el campo del jsp donde se solicita el nombre del ciudadano*/
	public final static String NOMBRE = "NOMBRE";

	/** Constante que identifica el campo del jsp donde se solicita el primer apellido del ciudadano*/
	public final static String APELLIDO2 = "APELLIDO2";

	/** Constante que identifica el campo del jsp donde se solicita el segundo apellido del ciudadano*/
	public final static String APELLIDO1 = "APELLIDO1";

	/** Constante que identifica el campo del jsp donde se solicita el id del ciudadano*/
	public final static String ID_CIUDADANO = "ID_CIUDADANO";

	/** Constante que identifica el campo del jsp donde se solicita el telefono del ciudadano*/
	public final static String TELEFONO = "TELEFONO";

	/** Constante que identifica el campo del jsp donde se solicita el tipo del documento del ciudadano*/
	public final static String TIPO_ID_CIUDADANO = "TIPO_ID_CIUDADANO";

	/** Constante que identifica el campo del jsp donde se solicita el numero de algun turno anterior*/
	public final static String TURNO_ANTERIOR = "TURNO_ANTERIOR";

	/** Constante que identifica el campo del jsp donde se solicita el tipo del certificado a solicitar*/
	public final static String TIPO_CERTIFICADO = "TIPO_CERTIFICADO";

	/** Constante que identifica el campo del jsp donde se solicita el tipo del tarifa del certificado*/
	public final static String TIPO_TARIFA_CERTIFICADOS = "TIPO_TARIFA_CERTIFICADOS";

	/** Constante que identifica el campo del jsp donde se solicita el numero de copias del certificado*/
	public final static String COPIAS = "COPIAS";
	
	/**Constante que identifica la impresora selecionada*/
	//public final static String IMPRESORA ="IMPRESORA";

	/** Constante que identifica el campo del jsp donde se solicita el numero de la matricula*/
	public final static String MATRICULA = CFolio.ID_MATRICULA;

	/** Constante que identifica el campo del jsp donde se solicita el numero total de matriculas*/
	public final static String NUM_MATRICULAS = CFolio.NUM_MATRICULAS;

	/** Constante que identifica el numero de anotaciones de un folio*/
	public final static String NUM_ANOTACIONES_FOLIO = CFolio.NUMERO_ANOTACIONES;

	/** Constante que identifica si el folio es de mayro extension*/
	public final static String MAYOR_EXTENSION_FOLIO = CFolio.MAYOR_EXTENSION;
	
	public final static String TIPO_OFICINA_ORIGEN = "TIPO_OFICINA_ORIGEN";

	/**
	 * Constante que identifica que se desea liquidar la
	 * solicitud de un certificado
	 */
	public final static String LIQUIDAR = "LIQUIDAR";

	/**
	 * Constante que identifica que se desea liquidar, pagar e imprimir la
	 * solicitud de un certificado
	 */
	public final static String RADICAR = "RADICAR";
	
	
	/**
	 * Constante que identifica que se desea agregar una
	 * nueva matrícula a la solicitud
	 */
	public final static String AGREGAR = "AGREGAR";

	/**
	 * Constante que identifica que se desea agregar un
	 * turno anterior a la solicitud
	 */
	public final static String AGREGAR_TURNO = "AGREGAR_TURNO";

	/**
	 * Constante que identifica que se desea eliminar
	 * una matrícula de la solicitud
	 */
	public final static String ELIMINAR = "ELIMINAR";

	/**
	 * Constante que identifica que se desea eliminar
	 * toda la informacion de la sesion
	 */
	public static final String REMOVER_INFO = "REMOVER_INFO";

	/**
	 *
	 */
	public static final String RECARGAR = "RECARGAR";
	
        /**
         * Contante que identifica que se desea generar un certificado especial.
         */
        public static final String CERTIFICADO_ESPECIAL = "CERTIFICADO_ESPECIAL";
    /**
     * Indicador de la accion web agregar un folio que aún no se han migrado.
     */
    public final static String AGREGAR_MIG_INC = "AGREGAR_MIG_INC";    

    /**
     * Indicador de la accion web eliminar, un folio asociado, este folio es de los que no se han migrado.
     */
    public final static String ELIMINAR_MIG_INC = "ELIMINAR_MIG_INC";
    
    /***/
    public final static String AGREGAR_MATRICULA_REGISTRO_MIG_INC = "AGREGAR_MATRICULA_REGISTRO_MIG_INC";
    
    public final static String CAMBIAR_TIPO_OFICINA_ORIGEN = "CAMBIAR_TIPO_OFICINA_ORIGEN";

	public static final String CIRCULO_CERTIFICADO_NACIONAL = "CIRCULO_CERTIFICADO_NACIONAL"; 



	/**
	 * Variable donde se guarda la accion enviada en el request
	 */
	private String accion;

	private HttpSession session;

	/**
	 * Crea una nueva instancia de AWLiquidacion
	 */
	public AWCertificadoEspecial() {
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
		session = request.getSession();
                /*AHERRENO
                 14/05/2012
                 REQ 076_151 TRANSACCION*/
                request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());
		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		}

		if (accion.equals(CERTIFICADO_ESPECIAL)){
                        session.setAttribute("TIPO_CERTIFICADO","INMEDIATO");
                        return null;
                } else if (accion.equals(LIQUIDAR)) {
			return liquidar(request);
		} else if(accion.equals(RADICAR)) {
			return radicar(request);
		} else if (accion.equals(AGREGAR)) {
			return adicionarMatricula(request);
		} else if (accion.equals(AGREGAR_TURNO)) {
			return adicionarTurno(request);
		} else if (accion.equals(ELIMINAR)) {
			return eliminarMatricula(request);
		} else if (accion.equals(REMOVER_INFO)) {
			return removerInfo(request);
		} else if (accion.equals(RECARGAR)) {
			return recargar(request);
		} else if (accion.equals(AGREGAR_MIG_INC)) {
            return agregarMatriculaSirMig(request);
        } else if (accion.equals(ELIMINAR_MIG_INC)) {
            return eliminarMatriculaSirMig(request);
        } else if (accion.equals(CAMBIAR_TIPO_OFICINA_ORIGEN)) {
        	return null;
    	} else {
			throw new AccionInvalidaException("La accion " + accion + " no es valida.");
		}
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento recargar(HttpServletRequest request) {
		
		preservarInfo(request);
		session.setAttribute(WebKeys.OCULTAR, request.getParameter(WebKeys.OCULTAR));

		return null;
	}

	/**
	 * Elimina una matricula de la lista de las matriculas ingresadas.
	 * @param request HttpServletRequest
	 * @return EvnLiquidacion nulo ya que no requiere viajar al negocio
	 */
	private EvnLiquidacion eliminarMatricula(HttpServletRequest request) {
		eliminarMatriculaSeleccionada(request);
		return null;
	}

	/**
	 * Este método se declaró publico para que pueda ser utilizado por otras acciones web
	 * que declaren un objeto de tipo AWLiquidacionCertificado
	 * (Para evitar duplicación de código)
	 *
	 * @param request
	 */
	public void eliminarMatriculaSeleccionada(HttpServletRequest request) {
		Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
		int val = num.intValue();
		String item = request.getParameter("ITEM");

		boolean antes = true;

		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);

			if (antes) {
				String id = CFolio.ID_MATRICULA + actual.toString();

				if (id.equals(item)) {
					antes = false;
					session.removeAttribute(item);
				}
			} else {
				Integer mover = new Integer(i - 1);
				String itemActual = (String) session.getAttribute(CFolio.ID_MATRICULA + actual.toString());
				session.setAttribute(CFolio.ID_MATRICULA + mover.toString(), itemActual);
				session.removeAttribute(CFolio.ID_MATRICULA + actual.toString());
			}
		}

		val--;
		session.setAttribute(AWCertificadoEspecial.NUM_MATRICULAS, new Integer(val));
		preservarInfo(request);
	}

	/**
	 * Agrega una matricula de la lista de las matriculas ingresadas.
	 * @param request HttpServletRequest
	 * @return EvnLiquidacion nulo ya que no requiere viajar al negocio
	 */

	private EvnValidacionMatricula adicionarMatricula(HttpServletRequest request) throws AccionWebException{
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		this.preservarInfo(request);
		boolean isNacional = false;
		isNacional = session.getAttribute(TIPO_OFICINA_ORIGEN) != null && session.getAttribute(TIPO_OFICINA_ORIGEN).equals(CCertificado.OFICINA_ORIGEN_NACIONAL);
		Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
		String nombreCampo = CFolio.ID_MATRICULA + num.toString();
		nombreCampo = nombreCampo.toUpperCase();
		Circulo circ= null;
		String matricula = null;
		
		ValidacionLiquidacionException exception = new ValidacionLiquidacionException();
		
		if (!isNacional)
		{
			circ = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		} else
		{
			String idCirculo = (String)session.getAttribute(CIRCULO_CERTIFICADO_NACIONAL);
			
			if(idCirculo == null || idCirculo.length() == 0 || idCirculo.equals(WebKeys.SIN_SELECCIONAR)) { 
				if (request.getParameter(nombreCampo)!=null && !((String)request.getParameter(nombreCampo)).equals("")) { session.setAttribute(nombreCampo,request.getParameter(nombreCampo)); }
				exception.addError("Debe ingresar una circulo valido");
				throw exception;
			}			
			
			List circulos = (List)request.getSession().getServletContext().getAttribute(WebKeys.LISTA_CIRCULOS_REGISTRALES_FECHA);
            for( java.util.Iterator iter = circulos.iterator(); iter.hasNext(); ) {
                    gov.sir.core.negocio.modelo.Circulo circuloAux = (gov.sir.core.negocio.modelo.Circulo) iter.next();
                    if (circuloAux.getIdCirculo().equals(idCirculo))
                    {
                    	circ = circuloAux;
                    	break;
                    }
            }
		}

		matricula = circ.getIdCirculo()+ "-";
		
		if(request.getParameter(nombreCampo)==null){
			exception.addError("Debe ingresar una matrícula valida");
			throw exception;
		}
		if(((String)request.getParameter(nombreCampo)).equals("")){
			exception.addError("Debe ingresar una matrícula valida");
			throw exception;
		}                
		matricula += request.getParameter(nombreCampo);
                /**
                 * @autor Edgar Lora
                 * @mantis 11987
                 */
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                try {
                    if(validacionesSIR.isEstadoFolioBloqueado(matricula)){
                        exception.addError("La matricula que desea agregar tiene como estado 'Bloqueado'.");
			throw exception;
                    }
                } catch (Exception ex) {
                    if(ex.getMessage() != null){
                        exception.addError(ex.getMessage());
                    }
                    throw exception;
                }
                /**
                * @Autor: Edgar Lora
                * @Mantis: 11309
                */
                TrasladoSIR trasladoSir = new TrasladoSIR();
                try {
                    if(trasladoSir.isBloqueDeTraslado(matricula)){
                        exception.addError("El folio " + matricula + " esta pendiente por confirmar traslado.");
                        throw exception;
                            /**
                            * @autor Carlos Torres
                            * @mantis 11309
                            */
                    }else if(trasladoSir.trasladadoFolio(matricula))
                    {
                        exception.addError("El folio " + matricula + " esta en estado trasladado");
                        throw exception;
                    }
                } catch (GeneralSIRException ex) {
                    exception.addError(ex.getMessage());
                    throw exception;
                }
		String tipoCertificado = isNacional ? CTipoCertificado.TIPO_INMEDIATO_ID : request.getParameter(AWCertificadoEspecial.TIPO_CERTIFICADO);
		EvnValidacionMatricula evento = null;
		if ((matricula != null) && !matricula.equals("")) {
			evento = new EvnValidacionMatricula(usuarioAuriga, matricula, tipoCertificado);
		}
		evento.setTurnoNacional(isNacional);
                evento.setTipoEvento(EvnValidacionMatricula.CERTIFICADO_ESPECIAL);
		preservarInfo(request);
		
		return evento;
	}

	/**
	 * Agrega un turno anterior a la solicitud.
	 * @param request HttpServletRequest
	 * @return EvnLiquidacion nulo ya que no requiere viajar al negocio
	 */

	private EvnCertificado adicionarTurno(HttpServletRequest request) throws AccionWebException {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		ValidacionLiquidacionException exception = new ValidacionLiquidacionException();
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);

		String idWfTurno = request.getParameter(AWCertificadoEspecial.TURNO_ANTERIOR);
		idWfTurno = idWfTurno.toUpperCase();
		String anno = null;
		String circ = null;
		String proceso = null;
		String turnoId = null;

		int pos1 = 0;
		int pos2 = idWfTurno.indexOf("-", 1);

		if (pos2 != -1) {
			anno = idWfTurno.substring(pos1, pos2);
			pos1 = pos2 + 1;
			pos2 = idWfTurno.indexOf("-", pos1);
			if (pos1 != -1 && pos2 != -1) {
				circ = idWfTurno.substring(pos1, pos2);
				pos1 = pos2 + 1;
				pos2 = idWfTurno.indexOf("-", pos1);
				if (pos1 != -1 && pos2 != -1) {
					proceso = idWfTurno.substring(pos1, pos2);
					pos1 = pos2 + 1;
					if (pos1 != -1 && pos2 != -1) {
						turnoId = idWfTurno.substring(pos1);
					}
				}
			}
		}

		Turno turno = new Turno();
		EvnCertificado evento = null;

		if (TURNO_ANTERIOR != null && anno != null && circ != null && proceso != null && turnoId != null) {
			turno.setIdWorkflow(idWfTurno);
			turno.setAnio(anno);
			turno.setIdCirculo(circ);
			turno.setIdTurno(turnoId);
			turno.setIdProceso(Long.parseLong(proceso));

			if (((Proceso) request.getSession().getAttribute(WebKeys.PROCESO)).getIdProceso() != Long.parseLong(proceso)) {
				exception.addError("El turno no es del mismo proceso");
			} else {
				evento = new EvnCertificado(usuarioAuriga, "CONSULTAR", turno, usuario);
			}

		} else {
			exception.addError("El codigo del turno no es completo");
		}

		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		//preservarInfo(request);
		return evento;
	}

	/**
	 *  Recibe la solicitud de turno de certificado que ha llenado el cajero
	 * a partir de estos datos, se debe generar una vista en la que se
	 * muestran los mismos datos y ademas, el valor liquidado y además, las
	 * opciones para que registre el pago
	 *
	 * Entonces, esta accion web recibe los siguientes datos:
	 * -si (Nueva entrada) then
	 * -Turno anterior
	 * -Tipo de certificado (cargados según tabla)
	 * -Número de matrícula inmobiliaria
	 * -Número de copias
	 * -Datos del solicitante
	 * -Tipo ID
	 * -Num ID
	 * -Apellido1
	 * -Apellido2
	 * -Nombres
	 *
	 * Validaciones que se deben realizar
	 * -Si es nueva entrada, que la anterior entrada (o sea el turno
	 * anterior) esté en el estado adecuado (esperando nueva entrada)
	 * (esto lo resuelve la capa de negocio)
	 * -El número de matrícula debe ser válida dependiendo del tipo
	 * de certificado escogido.  Algunos tipos de certificado no exigen
	 * esto.  Otros tipos de certificado tienen matrícula opcional, en este
	 * caso debe dejarse lo que ingrese el usuario.  Si el tipo de certificado
	 * exige que NO haya número de matrícula, debe borrarse lo que haya co-
	 * locado el usuario
	 * -Datos de solicitante, si existe en la base de datos según tipo id y
	 * num id, deben ignorarse los otros datos ingresados.  El cajero debe
	 * preguntar al ciudadano si es ese que aparece ahi. Si se equivoco,
	 * el cajero podrá cambiar el tipo id y el num id y hacer submit de
	 * nuevo.  Si el tipo id y num id no existen en la base de datos, se
	 * ingresan
	 *
	 * Resultados mostrados en la respuesta:
	 * -Datos de entrada
	 * -Valor del certificado (depende del tipo de certificado) (no
	 * editable)
	 * -Opciones y cajas de texto para registrar el pago
	 * -cheques y efectivo o
	 * -consignacion
	 * -etc. como se define el esquema de registro de pagos en los
	 * requerimientos
	 * @param request contenedor de tipo HttpServletRequest donde deben encontrarse los parametros arriba mencionados
	 * @return Objeto de tipo EvnTurnoCertificado que contiene el usuario que esta haciendo la transaccion, el tipo
	 * de certificado, el número de matrícula, el número de copias solicitadas y el ciudadano que está haciendo la solicitud.
	 * @throws AccionWebException Si alguno de los parametros no pasa la verificacion.
	 */
	private EvnLiquidacion liquidar(HttpServletRequest request) throws AccionWebException {
            
                List listaAplicaciones = (List) session.getAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS);
                listaAplicaciones = null;
                session.setAttribute(WebKeys.LISTA_PAGOS_REGISTRADOS, listaAplicaciones);
            
		preservarInfo(request);
                
                HermodService hs;
                List canalesXCirculo = new ArrayList() ;
                Circulo circulo = (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
                
                try {
                    hs = HermodService.getInstance();
                   canalesXCirculo = hs.getCanalesRecaudoXCirculo(circulo);
                } catch (HermodException e) {
                }
                request.getSession().setAttribute(WebKeys.LISTA_CANALES_RECAUDO_CIRCULO, canalesXCirculo);
        
		// Obtención del usuario
		session.removeAttribute(WebKeys.PAGO);
		session.removeAttribute(WebKeys.LIQUIDACION);
		session.removeAttribute(WebKeys.LISTA_CHEQUES);
		session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES);
		session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
		
		String impresora = request.getParameter(CTipoImprimible.CERTIFICADO_INMEDIATO);
		if (impresora==null||impresora.equals(WebKeys.SIN_SELECCIONAR)){
			impresora=request.getParameter(CTipoImprimible.CERTIFICADO_EXTENSO);
			if (impresora==null||impresora.equals(WebKeys.SIN_SELECCIONAR)){
				impresora=request.getParameter(CTipoImprimible.CERTIFICADO_EXENTO);
			}
		}
		
		
		session.setAttribute(WebKeys.IMPRESORA,impresora); 
		
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		boolean esNuevaEntrada = Boolean.valueOf((String) request.getParameter(AWCertificadoEspecial.NUEVA_ENTRADA)).booleanValue();


		ValidacionLiquidacionException exception = new ValidacionLiquidacionException();

		//		Obtención del tipo de Certificado
		String tipoCertificado = "1";
		if ((tipoCertificado == null) || tipoCertificado.equals("SIN_SELECCIONAR")) {
			exception.addError("El certificado es inválido");
		}

		//		Obtención del tipo de Tarifa
		String tipoTarifa = request.getParameter(AWCertificadoEspecial.TIPO_TARIFA_CERTIFICADOS);
		if ((tipoTarifa == null) || tipoTarifa.equals("SIN_SELECCIONAR")) {
			exception.addError("El tipo de tarifa es inválido");
		}

		SolicitudCertificado sol = getSolicitudCertificadoValidada(request, exception);
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();

		TipoCertificado tipoCert = new TipoCertificado();
		tipoCert.setIdTipoCertificado(tipoCertificado);

		liquidacion.setTipoTarifa(tipoTarifa);

		List listaCer = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS);
		Iterator itlc = listaCer.iterator();
		while (itlc.hasNext()) {
			ElementoLista el = (ElementoLista) itlc.next();
			if (el.getId().equals(tipoCertificado)) {
				tipoCert.setNombre(el.getValor());
			}
		}
		/**Variable de sesion para identficar el tipo de certificado. Se remueve en el 
		 * control de navegacion de registrar pago*/
		session.setAttribute(WebKeys.CERTIFICADO_TIPO, tipoCertificado);
		liquidacion.setTipoCertificado(tipoCert);
		sol.addLiquidacion(liquidacion);
		
		//Incidencia 10512. Se le pasa directamente el Id de Proceso para turnos de certificados.
		Proceso proceso = new Proceso ();
		proceso.setIdProceso(new Long(CProceso.PROCESO_CERTIFICADOS).longValue());
		//Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		sol.setProceso(proceso);
		
		liquidacion.setSolicitud(sol);

		EvnLiquidacion evento = new EvnLiquidacion(usuarioAuriga, liquidacion, proceso, (Estacion) session.getAttribute(WebKeys.ESTACION), true, usuario);
		evento.setUID(request.getSession().getId());

//		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String idCirculo = circulo.getIdCirculo();
		

		if(impresora!=null){
			evento.setImpresoraPredeterminada(impresora);	
		}		
		evento.setIdCirculo(idCirculo);
		evento.setCirculo(circulo);
                evento.setEspecial(true);
		
		return evento;
	}

	/**
	 *  Recibe la solicitud de turno de certificado que ha llenado el cajero
	 * a partir de estos datos, se debe generar la liquidacion, la creacion del turno
	 * el pago y la impresion del certificado
	 * @param request contenedor de tipo HttpServletRequest donde deben encontrarse los parametros arriba mencionados
	 * @return Objeto de tipo EvnLiquidacion que contiene el usuario que esta haciendo la transaccion, el tipo
	 * de certificado.
	 * @throws AccionWebException Si alguno de los parametros no pasa la verificacion.
	 */
	private EvnLiquidacion radicar(HttpServletRequest request) throws AccionWebException {
		preservarInfo(request);
		// Obtención del usuario
		session.removeAttribute(WebKeys.PAGO);
		session.removeAttribute(WebKeys.LIQUIDACION);
		session.removeAttribute(WebKeys.LISTA_CHEQUES);
		session.removeAttribute(WebKeys.LISTA_CONSIGNACIONES);
		session.removeAttribute(WebKeys.APLICACION_EFECTIVO);
		
		String impresora = WebKeys.SIN_SELECCIONAR;
		this.preservarInfo(request);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		
		ValidacionLiquidacionException exception = new ValidacionLiquidacionException();

		String tipoCertificado = CTipoCertificado.TIPO_INMEDIATO_ID;
	
		String tipoTarifa = CTipoTarifa.NORMAL;
		
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String idCirculo = circulo.getIdCirculo();
		
		Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
		
		String nombreCampo = CFolio.ID_MATRICULA + num.toString();
		nombreCampo = nombreCampo.toUpperCase();
		Circulo circ= (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String matricula = circ.getIdCirculo()+ "-";
		matricula += request.getParameter(nombreCampo);                
                
                /**
                 * @autor AHERRENO
                 * REQUERIMIENTO 039_453
                 */
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                try {
                    if(validacionesSIR.isEstadoFolioBloqueado(matricula)){
                        exception.addError("La matricula que desea agregar tiene como estado 'Bloqueado'.");
			throw exception;
                    }
                } catch (Exception ex) {
                    if(ex.getMessage() != null){
                        exception.addError(ex.getMessage());
                    }
                    throw exception;
                }                

		SolicitudCertificado sol = getSolicitudCertificadoValidada(request, tipoCertificado,tipoTarifa, exception);
		if (exception.getErrores().size() > 0) {
			throw exception;
		}

		LiquidacionTurnoCertificado liquidacion = new LiquidacionTurnoCertificado();

		TipoCertificado tipoCert = new TipoCertificado();
		tipoCert.setIdTipoCertificado(tipoCertificado);

		liquidacion.setTipoTarifa(tipoTarifa);

		List listaCer = (List) session.getServletContext().getAttribute(WebKeys.LISTA_TIPOS_CERTIFICADOS);
		Iterator itlc = listaCer.iterator();
		while (itlc.hasNext()) {
			ElementoLista el = (ElementoLista) itlc.next();
			if (el.getId().equals(tipoCertificado)) {
				tipoCert.setNombre(el.getValor());
			}
		}
		liquidacion.setTipoCertificado(tipoCert);
		sol.addLiquidacion(liquidacion);
		
		Proceso proceso = new Proceso ();
		proceso.setIdProceso(new Long(CProceso.PROCESO_CERTIFICADOS).longValue());
		sol.setProceso(proceso);
		
		liquidacion.setSolicitud(sol);

		EvnLiquidacion evento = new EvnLiquidacion(usuarioAuriga, liquidacion, proceso, (Estacion) session.getAttribute(WebKeys.ESTACION), true, usuarioSIR);
		evento.setUID(request.getSession().getId());
		
		if(impresora!=null){
			evento.setImpresoraPredeterminada(impresora);	
		}		
		evento.setIdCirculo(idCirculo);
		evento.setCirculo(circulo);
		evento.setMatricula(matricula);
		evento.setTipoCertificado(tipoCert.getIdTipoCertificado());
		/**********************************/
					/**DATOS DEL PAGO*/
		Rol rol = (Rol) request.getSession().getAttribute(WebKeys.ROL);

        if ((rol != null) && (evento != null)) {
        	evento.setRol(rol);
        }

       evento.setTipoEvento(EvnLiquidacion.LIQUIDAR_PAGO);
       return evento;
	}
	
	

	/**
	 * Este método se declaró publico para que pueda ser utilizado por otras acciones web
	 * que declaren un objeto de tipo AWLiquidacionCertificado
	 * (Para evitar duplicación de código)
	 *
	 * @param request
	 * @param exception
	 * @return
	 */
	public SolicitudCertificado getSolicitudCertificadoValidada(HttpServletRequest request, ValidacionParametrosException exception) {

		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Ciudadano ciudadano = null;
		
		boolean isNacional = false;
		isNacional = request.getParameter(TIPO_OFICINA_ORIGEN) != null && request.getParameter(TIPO_OFICINA_ORIGEN).equals(CCertificado.OFICINA_ORIGEN_NACIONAL);

		String tipoCert = "1";
		String tipoTarifa = request.getParameter(AWCertificadoEspecial.TIPO_TARIFA_CERTIFICADOS);

		int copias = 0;
		int maxCopias = 1;

		boolean exp = false;

		//SI NO ES EXENTO EL NUMERO MAXIMO DE COPIAS ES EL CONFIGURADO
		//SI NO EL MAXIMO NUMERO DE COPIAS ES 1.   
		if (!tipoTarifa.equals("EXENTO")) {
			try {
				String numMaxCopias = (String) request.getSession().getServletContext().getAttribute(WebKeys.MAXIMO_COPIAS_CERTIFICADO);
				
				if(numMaxCopias == null || numMaxCopias.equals("")){
					numMaxCopias = "100";
				}

				numMaxCopias = numMaxCopias.trim();

				maxCopias = Integer.parseInt(numMaxCopias);

			} catch (NumberFormatException e) {
				Log.getInstance().error(AWCertificadoEspecial.class, e);
				exception.addError("Número máximo de copias inválido, no se puede ingresar letras o un numeros de mayores a 2147483647");
			}

		}

		try {
			String cadCopias = request.getParameter(AWCertificadoEspecial.COPIAS);
			copias = Integer.parseInt(cadCopias);
		} catch (NumberFormatException e) {
			exp = true;
			exception.addError("Número de copias inválido, no se puede ingresar letras o un numeros de mayores a 2147483647");
		}

		if (copias > maxCopias)
			exception.addError("El Número máximo de copias es " + maxCopias);

		if (copias <= 0 && !exp) {
			exception.addError("El numero de copias no puede ser negativo o cero.");
		}

		Integer cantidadMatriculas = (Integer) session.getAttribute(AWCertificadoEspecial.NUM_MATRICULAS);

		//Se debe validar que si ingresa una migrada no puede ingresar una matricula
		List matriculasAsociadasMig = (List)session.getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);
		
		boolean tieneMigrados = false;
		
		if (matriculasAsociadasMig!=null && !matriculasAsociadasMig.isEmpty()) {
			tieneMigrados = true;
		}
		
		if ( cantidadMatriculas.intValue() > 0 && tieneMigrados) {
			exception.addError("Debe ingresar una matricula valida o una Sin Migrar");
		}
		
		
		if (tipoCert != null && tipoCert.equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID) && cantidadMatriculas.intValue() > 0) {
			exception.addError("Para los cetificados de antiguo sistema no se deben incluir matriculas inmobiliarias");
		}

		DatosAntiguoSistema datosAntSistema = null;
		Documento documento = null;

		if (tipoCert != null && (tipoCert.equals(CTipoCertificado.TIPO_INMEDIATO_ID) || tipoCert.equals(CTipoCertificado.TIPO_EXENTO_ID)) || tipoCert.equals(CTipoCertificado.TIPO_NACIONAL_ID)) {
			if (cantidadMatriculas.intValue() <= 0) {
				exception.addError("Debe ingresar una matricula para ese tipo de certificado");
			}

		} else if (tipoCert != null && (tipoCert.equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID) || tipoCert.equals(CTipoCertificado.TIPO_PERTENENCIA_ID))) {
			datosAntSistema = this.getDatosAntiguoSistema(request);
			documento = this.getDocumento(request);
			String cadFecha = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS);
			if (cadFecha != null) {
				if (cadFecha.trim().length() > 0) {
					Calendar calendar = this.darFecha(cadFecha);
					if (calendar == null)
						exception.addError("La fecha del documento NO es válida");
				}
			}

		}

		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.IDCIUDADANO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);

		if (tipoId.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar un tipo de identificación para el ciudadano");
		} else if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else if (tipoId.equals(COPLookupCodes.NIT)) {
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		} else {
			double valorId = 0.0d;
			if (numId == null || numId.trim().equals("")) {
				exception.addError("Debe ingresar el número de identificación del Ciudadano");
			} else {
                            /*
             * @author : CTORRES
             * @change : Se implemento la validación para solo permitir caracteres alfanuméricos en 
             *           numId cuando el tipo de identificación es PASAPORTE.
             * Caso Mantis : 11056
             * No Requerimiento: 073_151
             */

                          if(tipoId.contains("PS"))
                                    {
                                        String regexSL = "^[a-zA-Z]+$";
                                        String regexSN = "^[0-9]+$";
                                        String regexLN = "^[a-zA0-Z9]+$";
                                        java.util.regex.Pattern patternSL = java.util.regex.Pattern.compile(regexSL);
                                        java.util.regex.Pattern patternSN = java.util.regex.Pattern.compile(regexSN);
                                        java.util.regex.Pattern patternLN = java.util.regex.Pattern.compile(regexLN);
                                        boolean esC = false;
                                        if(patternSL.matcher(numId).matches()) esC = true;
                                        else if(patternSN.matcher(numId).matches()) esC = true;
                                        else if(patternLN.matcher(numId).matches()) esC = true;
                                        else exception.addError("El número de identificación de la persona es inválido. Debe ser alfanumérico");
                                        
                                    }
                           else{
  
				if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
					try {
						valorId = Double.parseDouble(numId);
						if (valorId <= 0) {
							exception.addError("El valor del documento no puede ser negativo o cero");
						}
					} catch (NumberFormatException e) {
						exception.addError("El número de identificación de la persona es inválido. No puede ser alfanumérico");
					}
				}
                          }
			}
			if (nombres == null || nombres.trim().equals("")) {
				exception.addError("Debe ingresar el nombre del Ciudadano");
			}
			if (apellido1 == null || apellido1.trim().equals("")) {
				exception.addError("Debe ingresar el primer apellido del Ciudadano");
			}
		}

		if (telefono != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setTelefono(telefono);
		}

		if (tipoId != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setTipoDoc(tipoId);
		}

		if (numId != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setDocumento(numId);
		}

		if (nombres != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setNombre(nombres);
		}

		if (apellido1 != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setApellido1(apellido1);
		}

		if (apellido2 != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setApellido2(apellido2);
		}
		
		//Se setea el circulo del ciudadano
		ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

		SolicitudCertificado sol = new SolicitudCertificado();

		sol.setCirculo(circulo);
		sol.setCiudadano(ciudadano);
		sol.setUsuario(usuario);
		sol.setNumeroCertificados(copias);
		sol.setProceso(proceso);

		boolean tieneDatosAntSistema = false;
		if (datosAntSistema != null) {
			tieneDatosAntSistema = true;
			sol.setDatosAntiguoSistema(datosAntSistema);
			if (documento != null) {
				sol.setDocumento(documento);
			}
		}

		if (!isNacional){
			String turnoAnterior = request.getParameter(AWCertificadoEspecial.TURNO_ANTERIOR);
			turnoAnterior = turnoAnterior.toUpperCase();
			if ((turnoAnterior != null) && !turnoAnterior.equals("")) {
				Turno turno = new Turno();
				turno.setIdWorkflow(turnoAnterior);
				sol.setTurnoAnterior(turno);
			}
		}
		int val = cantidadMatriculas.intValue();
		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);
			String id = CFolio.ID_MATRICULA + actual.toString();
			String matricula = (String) session.getAttribute(id);
			SolicitudFolio solFolio = new SolicitudFolio();
			Folio folio = new Folio();
			folio.setIdMatricula(matricula);
			solFolio.setFolio(folio);
			sol.addSolicitudFolio(solFolio);
		}

		
		
		String tipoCertificado = request.getParameter(AWCertificadoEspecial.TIPO_CERTIFICADO);
		if (tipoCertificado != null) {
			if (tipoCertificado.equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID)) {
				if (!tieneDatosAntSistema) {
					exception.addError("No tiene ningun dato de antiguo sistema");
				}
				if (datosAntSistema != null) {
					sol.setDatosAntiguoSistema(datosAntSistema);
				}
				if (tieneMigrados) {
					exception.addError("Para tipos de certificado inmediato no puede tener asociados folios Sin Migrar.");
				}
			} else if (tipoCertificado.equals(CTipoCertificado.TIPO_PERTENENCIA_ID)) {
				if (cantidadMatriculas.equals(new Integer(0))) {
					if (!tieneDatosAntSistema && !tieneMigrados) {
						exception.addError("Para tipos de certificado pertenencia debe ingresar o una matricula o datos de antiguo sistema");
					}
				}
			} else if (tipoCertificado.equals(CTipoCertificado.TIPO_INMEDIATO_ID)) {
				if (tieneMigrados) {
					exception.addError("Para tipos de certificado inmediato no puede tener asociados folios Sin Migrar.");
				}
			}
		}

		return sol;
	}

	/**
	 * Crea una solicitud de certificado con los datos en sesion. Este metodo es usado para
	 * la pantalla simplificada de certificados, lo que hace que no tenga en cuenta varia
	 * informacion que usa la pantalla tradicional. Solo se tiene en cuenta el primer
	 * apellido del ciudadano. La matricula no se agrega, solo se escribe en el campo de texto
	 *
	 * @param request
	 * @param exception
	 * @return
	 */
	private SolicitudCertificado getSolicitudCertificadoValidada(HttpServletRequest request, String tipoCert, String tipoTarifa, 
			ValidacionParametrosException exception) {

		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Usuario usuario = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Ciudadano ciudadano = null;


		int copias = 1;
		int maxCopias = 1;

		boolean exp = false;

		//SI NO ES EXENTO EL NUMERO MAXIMO DE COPIAS ES EL CONFIGURADO
		//SI NO EL MAXIMO NUMERO DE COPIAS ES 1.   
		if (!tipoTarifa.equals("EXENTO")) {
			try {
				String numMaxCopias = (String) request.getSession().getServletContext().getAttribute(WebKeys.MAXIMO_COPIAS_CERTIFICADO);
				
				if(numMaxCopias == null || numMaxCopias.equals("")){
					numMaxCopias = "100";
				}

				numMaxCopias = numMaxCopias.trim();

				maxCopias = Integer.parseInt(numMaxCopias);

			} catch (NumberFormatException e) {
				Log.getInstance().error(AWCertificadoEspecial.class, e);
				exception.addError("Número máximo de copias inválido, no se puede ingresar letras o un numeros de mayores a 2147483647");
			}

		}
		Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
		Integer cantidadMatriculas = null;
		if(num!=null){
			cantidadMatriculas = new Integer(1); 
		}

		String tipoId = COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD;
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		if(apellido1==null||apellido1.equals("")){
			exception.addError("Debe ingresar el solicitante");
		}
		
		if (tipoId != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setTipoDoc(tipoId);
		}

		if (apellido1 != null) {
			if (ciudadano == null) {
				ciudadano = new Ciudadano();
			}
			ciudadano.setApellido1(apellido1);
		}

		//Se setea el circulo del ciudadano
		ciudadano.setIdCirculo(circulo!=null?circulo.getIdCirculo():null);

		SolicitudCertificado sol = new SolicitudCertificado();

		sol.setCirculo(circulo);
		sol.setCiudadano(ciudadano);
		sol.setUsuario(usuario);
		sol.setNumeroCertificados(copias);
		sol.setProceso(proceso);

		
		int val = cantidadMatriculas.intValue();
		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);
			String id = CFolio.ID_MATRICULA + actual.toString();
			id = id.toUpperCase();
			String matricula = circulo.getIdCirculo()+ "-";
			if((request.getParameter(id)==null)||(((String)request.getParameter(id)).equals(""))){
				exception.addError("Debe ingresar una matrícula");
			}
			matricula += request.getParameter(id);
			SolicitudFolio solFolio = new SolicitudFolio();
			Folio folio = new Folio();
			folio.setIdMatricula(matricula);
			solFolio.setFolio(folio);
			sol.addSolicitudFolio(solFolio);
		}
		return sol;
	}
	/**
	 * Este método se declaró publico para que pueda ser utilizado por otras acciones web
	 * que declaren un objeto de tipo AWLiquidacionCertificado
	 * (Para evitar duplicación de código)
	 *
	 * @param request
	 * @param exception
	 * @return
	 */
	private ValidacionParametrosException validacionCertificadoTestamento(String tipoId, String numId,
			String apellido1, String apellido2, String nombres, String tomo, String anio) {
		
		ValidacionParametrosException exception = new ValidacionParametrosException ();
//		VALIDACIONES PARA EL TESTADOR (CIUDADANO)
		/*if(tipoId == null){
			return(exception);
		}*/
		if (!(tipoId == null||tipoId.equals(WebKeys.SIN_SELECCIONAR))) {
			//exception.addError("Debe seleccionar un tipo de identificación para el Testador");
		/*}*/ 
			if (tipoId.equals(COPLookupCodes.SECUENCIAL_DOCUMENTO_IDENTIDAD)) {
				if (apellido1 == null || apellido1.trim().equals("")) {
					exception.addError("DATOS PARA TESTAMENTOS: Debe ingresar el primer apellido del Testador");
				}
			} else if (tipoId.equals(COPLookupCodes.NIT)) {
				if (apellido1 == null || apellido1.trim().equals("")) {
					exception.addError("DATOS PARA TESTAMENTOS: Debe ingresar el primer apellido del Testador");
				}
			} else {
				double valorId = 0.0d;
				if (numId == null || numId.trim().equals("")) {
					exception.addError("DATOS PARA TESTAMENTOS: Debe ingresar el número de identificacion del Testador");
				} else {
					if (!tipoId.equals(CCiudadano.TIPO_DOC_ID_TARJETA)) {
						try {
							valorId = Double.parseDouble(numId);
							if (valorId <= 0) {
								exception.addError("DATOS PARA TESTAMENTOS: El valor del documento del Tetsador no puede ser negativo o cero");
							}
						} catch (NumberFormatException e) {
							exception.addError("DATOS PARA TESTAMENTOS: El número de identificación del Testador es inválido. No puede ser alfanumérico");
						}
					}
				}
				if (nombres == null || nombres.trim().equals("")) {
					exception.addError("DATOS PARA TESTAMENTOS: Debe ingresar el nombre del Testador");
				}
				if (apellido1 == null || apellido1.trim().equals("")) {
					exception.addError("DATOS PARA TESTAMENTOS: Debe ingresar el primer apellido del Testador");
				}
			}
		}
			/****/
		
		if (tomo == null || tomo.trim().equals("")){  
			exception.addError("DATOS PARA TESTAMENTOS: Debe ingresar el Tomo");
		}
		if (anio == null || anio.trim().equals("")){
			exception.addError("DATOS PARA TESTAMENTOS: Debe ingresar el Año");
		}
		return exception;
	}
	/**
	Datos adicionales de antiguo sistema:
	* Libro
	  * Tipo
	  * Número - Letra
	  * Página
	  * Año
	* Tomo
	  * Número
	  * Página
	  * Municipio
	  * Año
	* Datos del documento (Opcionales)
	  * Número de documento
	  * Tipo de documento
	  * Fecha del documento
	  * Comentario
	  * Oficina origen (opcional)
	    * Tipo
	    * Número
	    * Departamento
	    * Municipio
	* Comentario 	 
	* @param request
	* @return un objeto que representa los datos de antiguo sistema ingresados por el usuario.
	*/
	private DatosAntiguoSistema getDatosAntiguoSistema(HttpServletRequest request) {
		String listaKeysAntSistema[] = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;
		Vector keysLlenas = new Vector();
		String key, valor;
		for (int i = 0; i < listaKeysAntSistema.length; i++) {
			key = listaKeysAntSistema[i];
			valor = request.getParameter(key);
			if (valor != null) {
				if (valor.trim().length() > 0) {
					keysLlenas.add(key);
				}
			}
		}

		DatosAntiguoSistema datosAntSistema = null;

		if (keysLlenas.size() > 0) {
			datosAntSistema = new DatosAntiguoSistema();
			for (int i = 0; i < keysLlenas.size(); i++) {
				key = (String) keysLlenas.get(i);
				datosAntSistema = this.setDatoAntiguoSistema(request, key, datosAntSistema);

			}
		}

		return datosAntSistema;
	}

	/**
	* Datos del documento (Opcionales)
	  * Número de documento
	  * Tipo de documento
	  * Fecha del documento
	  * Comentario
	  * Oficina origen (opcional)
		* Tipo
		* Número
		* Departamento
		* Municipio
	* @param request
	* @return un objeto que representa los datos de antiguo sistema ingresados por el usuario.
	*/
	private Documento getDocumento(HttpServletRequest request) {
		String listaKeysDocumento[] = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;
		Vector keysLlenas = new Vector();
		String key, valor;
		for (int i = 0; i < listaKeysDocumento.length; i++) {
			key = listaKeysDocumento[i];
			valor = request.getParameter(key);
			if (valor != null) {
				if (valor.trim().length() > 0) {
					keysLlenas.add(key);
				}
			}
		}

		Documento documento = null;

		if (keysLlenas.size() > 0) {
			documento = new Documento();
			for (int i = 0; i < keysLlenas.size(); i++) {
				key = (String) keysLlenas.get(i);
				documento = this.setDatoDocumento(request, key, documento);

			}
		}

		return documento;
	}

	/**
	 * 		
	 * DOCUMENTO_COMENTARIO_AS,
	 * DOCUMENTO_FECHA_AS,
	 * DOCUMENTO_NUMERO_AS,
	 * DOCUMENTO_OFICINA_DEPARTAMENTO_AS,
	 * DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS,
	 * DOCUMENTO_OFICINA_MUNICIPIO_AS,
	 * DOCUMENTO_OFICINA_MUNICIPIO_ID_AS,
	 * DOCUMENTO_OFICINA_NUMERO_AS,
	 * DOCUMENTO_OFICINA_NUMERO_ID_AS,
	 * DOCUMENTO_OFICINA_TIPO_AS,
	 * DOCUMENTO_TIPO_AS
	 * @param request
	 * @param key
	 * @param documento
	 * @return
	 */
	private Documento setDatoDocumento(HttpServletRequest request, String key, Documento documento) {
		String valor = request.getParameter(key);
                  /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
                String oficinaVersion = request.getParameter(CDatosAntiguoSistema.DOCUMENTO_OFICINA_AS_VERSION);
		/*
		 * DATOS DEL DOCUMENTO
		 Datos del documento (Opcionales)
		  * Número de documento
		  * Tipo de documento
		  * Fecha del documento
		  * Comentario
		  * Oficina origen (opcional)
		    * Tipo
		    * Número
		    * Departamento
		    * Municipio
			*/
		if (key.equals(CDatosAntiguoSistema.DOCUMENTO_NUMERO_AS)) {
			documento.setNumero(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_TIPO_AS)) {
			TipoDocumento tipoDoc = documento.getTipoDocumento();

			if (tipoDoc == null)
				tipoDoc = new TipoDocumento();

			tipoDoc.setIdTipoDocumento(valor);
			documento.setTipoDocumento(tipoDoc);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_FECHA_AS)) {
			Calendar calendar = darFecha(valor);
			if (calendar != null) {
				Date fecha = calendar.getTime();
				documento.setFecha(fecha);
			}
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS)) {
			documento.setComentario(valor.toUpperCase());
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			Vereda vereda = oficina.getVereda();

			if (vereda == null)
				vereda = new Vereda();

			Municipio municipio = vereda.getMunicipio();

			if (municipio == null)
				municipio = new Municipio();

			Departamento depto = municipio.getDepartamento();
			if (depto == null)
				depto = new Departamento();

			depto.setNombre(valor);

			municipio.setDepartamento(depto);
			vereda.setMunicipio(municipio);
			oficina.setVereda(vereda);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_ID_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			Vereda vereda = oficina.getVereda();

			if (vereda == null)
				vereda = new Vereda();

			Municipio municipio = vereda.getMunicipio();

			if (municipio == null)
				municipio = new Municipio();

			Departamento depto = municipio.getDepartamento();
			if (depto == null)
				depto = new Departamento();

			depto.setIdDepartamento(valor);

			municipio.setDepartamento(depto);
			vereda.setMunicipio(municipio);
			oficina.setVereda(vereda);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			Vereda vereda = oficina.getVereda();

			if (vereda == null)
				vereda = new Vereda();

			Municipio municipio = vereda.getMunicipio();

			if (municipio == null)
				municipio = new Municipio();

			municipio.setNombre(valor);
			vereda.setMunicipio(municipio);
			oficina.setVereda(vereda);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_ID_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			Vereda vereda = oficina.getVereda();

			if (vereda == null)
				vereda = new Vereda();

			Municipio municipio = vereda.getMunicipio();

			if (municipio == null)
				municipio = new Municipio();

			municipio.setIdMunicipio(valor);
			vereda.setMunicipio(municipio);
			oficina.setVereda(vereda);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			oficina.setNumero(valor);

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_ID_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			oficina.setIdOficinaOrigen(valor);
                        /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                         */
                        oficina.setVersion(Long.parseLong(oficinaVersion));

			documento.setOficinaOrigen(oficina);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS)) {
			OficinaOrigen oficina = documento.getOficinaOrigen();

			if (oficina == null)
				oficina = new OficinaOrigen();

			TipoOficina tipoOfic = oficina.getTipoOficina();
			if (tipoOfic == null)
				tipoOfic = new TipoOficina();

			tipoOfic.setIdTipoOficina(valor);
			oficina.setTipoOficina(tipoOfic);

			documento.setOficinaOrigen(oficina);
		}

		return documento;
	}

	/**
	 * Retorna los datos de antiguo sistema despues de settear la propiedad correspondiente a la llave "key"
	 * en le objeto "datosAntSistema".
	  Datos adicionales de antiguo sistema:
	* Libro
	  * Tipo
	  * Número - Letra
	  * Página
	  * Año
	* Tomo
	  * Número
	  * Página
	  * Municipio
	  * Año
	* Datos del documento (Opcionales)
	  * Número de documento
	  * Tipo de documento
	  * Fecha del documento
	  * Comentario
	  * Oficina origen (opcional)
	    * Tipo
	    * Número
	    * Departamento
	    * Municipio
	 * Comentario 
	 * @param request
	 * @param key
	 * @param datosAntSistema
	 * @return
	 */
	private DatosAntiguoSistema setDatoAntiguoSistema(HttpServletRequest request, String key, DatosAntiguoSistema datosAntSistema) {
		String valor = request.getParameter(key);

		/*
		DATOS DEL LIBRO:
		Libro
		  * Tipo
		  * Número - Letra
		  * Página
		  * Año
		*/
		//Libro.Tipo
		if (key.equals(CDatosAntiguoSistema.LIBRO_TIPO_AS)) {
			datosAntSistema.setLibroTipo(valor);
		}
		//Libro.Número - Letra
		else if (key.equals(CDatosAntiguoSistema.LIBRO_NUMERO_AS)) {
			datosAntSistema.setLibroNumero(valor);
		}
		//Libro.Página
		else if (key.equals(CDatosAntiguoSistema.LIBRO_PAGINA_AS)) {
			datosAntSistema.setLibroPagina(valor);
		}
		//Libro.Año
		else if (key.equals(CDatosAntiguoSistema.LIBRO_ANO_AS)) {
			datosAntSistema.setLibroAnio(valor);
		}

		/*
		 * DATOS DEL TOMO	 
		 Tomo
		  * Número
		  * Página
		  * Municipio
		  * Año
		 */
		else if (key.equals(CDatosAntiguoSistema.TOMO_NUMERO_AS)) {
			datosAntSistema.setTomoNumero(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_PAGINA_AS)) {
			datosAntSistema.setTomoPagina(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_MUNICIPIO_AS)) {
			datosAntSistema.setTomoMunicipio(valor);
		} else if (key.equals(CDatosAntiguoSistema.TOMO_ANO_AS)) {
			datosAntSistema.setTomoAnio(valor);
		}

		/*
		 * DATOS DE LA OFICINA DE ORIGEN
		 Oficina origen (opcional)
		  * Tipo
		  * Número
		  * Departamento
		  * Municipio
		 */
		else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_TIPO_AS)) {
			//datosAntSistema.setDocumentoOficinaTipo(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_NUMERO_AS)) {
			//datosAntSistema.setDocumentoOficinaNumero(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_DEPARTAMENTO_AS)) {
			//datosAntSistema.setDocumentoOficinaDepartamento(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_OFICINA_MUNICIPIO_AS)) {
			//datosAntSistema.setDocumentoOficinaMunicipio(valor);
		} else if (key.equals(CDatosAntiguoSistema.DOCUMENTO_COMENTARIO_AS)) {
			//datosAntSistema.setDocumentoOficinaComentario(valor);
		}
		/*
		 * DATOS DEL COMENTARIO
		 */
		else if (key.equals(CDatosAntiguoSistema.COMENTARIO_AS)) {
			datosAntSistema.setComentario(valor.toUpperCase());
		}

		return datosAntSistema;
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {
		if (evento instanceof EvnRespLiquidacion) {
			EvnRespLiquidacion respuesta = (EvnRespLiquidacion) evento;

			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespLiquidacion.LIQUIDACION)) {

					Liquidacion liquidacion = respuesta.getLiquidacion();
					session.setAttribute(WebKeys.LIQUIDACION, liquidacion);
                                        session.setAttribute(WebKeys.IS_CERTIFICADO_ESPECIAL, respuesta.isCertificadoEspecial());
                                        session.setAttribute(WebKeys.IS_CERTIFICADO_TRAMITE, respuesta.isCertificadoTramite());
                                        session.setAttribute(WebKeys.IS_CERTIFICADO_ACTUACION, respuesta.isCertificadoActuacion());
                                        session.setAttribute(WebKeys.TURNO_TRAMITE_FOLIO, respuesta.getTurnoTramite());
					session.setAttribute(WebKeys.VALOR_LIQUIDACION, String.valueOf(liquidacion.getValor()));

					removerInfo(request);
					DocumentoPago documentoEfectivo = new DocPagoEfectivo(liquidacion.getValor());
					AplicacionPago aplicacionEfectivo = new AplicacionPago(documentoEfectivo, liquidacion.getValor());
					session.setAttribute(WebKeys.APLICACION_EFECTIVO, aplicacionEfectivo);

				}
				if(respuesta.getTipoEvento().equals(EvnRespLiquidacion.RADICAR)){
					Turno turno = respuesta.getTurno();
					if (respuesta.getValorSecuencial()!=0){
						if(turno != null && turno.getIdProceso()==Long.parseLong(CProceso.PROCESO_CERTIFICADOS)){
							long secuencial = (long) respuesta.getValorSecuencial();
							session.setAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION, "" + secuencial);
						}
					}
					removerInfo(request);
				}
			}
		} else if (evento instanceof EvnRespPago) {
			EvnRespPago respuesta = (EvnRespPago) evento;
			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespPago.PROCESAMIENTO_PAGO)) {
					Turno turno = respuesta.getTurno();
					session.setAttribute(WebKeys.TURNO, turno);
					this.removerInfo(request);
				}
			}
		} else if (evento instanceof EvnRespValidacionMatricula) {
			EvnRespValidacionMatricula respuesta = (EvnRespValidacionMatricula) evento;
			Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
			String nombreCampo = CFolio.ID_MATRICULA + num.toString();
			nombreCampo = nombreCampo.toUpperCase();
			String matricula = respuesta.getMatricula();
			if ((matricula != null) && !matricula.equals("")) {
				session.setAttribute(nombreCampo, matricula);
				int val = num.intValue();
				val++;
				Integer nuevo = new Integer(val);
				session.setAttribute(AWCertificadoEspecial.NUM_MATRICULAS, nuevo);
				String numeroAnota = respuesta.getNumeroAnotaciones();
				String mayorExtension = respuesta.getMayorExtension();
				session.setAttribute(AWCertificadoEspecial.MAYOR_EXTENSION_FOLIO, mayorExtension);
				
				session.setAttribute(AWCertificadoEspecial.NUM_ANOTACIONES_FOLIO, numeroAnota);

			}
			preservarInfo(request);
		} else if (evento instanceof EvnRespCertificado) {
			EvnRespCertificado respuesta = (EvnRespCertificado) evento;
			request.getSession().setAttribute(WebKeys.LIQUIDACION, respuesta.getLiquidacion());
			
			if (respuesta.getTipoEvento().equalsIgnoreCase(EvnRespLiquidacion.VALIDACION_MATRICULA_MIG)) {
                String matricula = (String) respuesta.getMatricula();

                if (matricula != null) {
                	 List matriculasSirMig = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);

                    if (matriculasSirMig == null) {
                    	matriculasSirMig = new ArrayList();
                    }
					matriculasSirMig.add(matricula);
					request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO, matriculasSirMig);      
                }
                
             }
              
              
              // PRESERVAR INFORMACION
              
              this.removerInformacion(request);
              Liquidacion liq = respuesta.getLiquidacion();
              String matriculaAsociada = null;
              if ( liq != null)
              {
              	Solicitud solicitud = liq.getSolicitud();
				if( solicitud!= null){
					List solFolios = solicitud.getSolicitudFolios();
					Iterator itSolFolios = solFolios.iterator();
					if(!solFolios.isEmpty()){
					
					  while(itSolFolios.hasNext()){
				         SolicitudFolio solFolio = (SolicitudFolio)itSolFolios.next();	
				         matriculaAsociada = solFolio.getIdMatricula();
				         
					  }
					
                     }
                     if (liq!=null)
                     {
                     	LiquidacionTurnoCertificado liqCert = (LiquidacionTurnoCertificado)liq;
                     	TipoCertificado kind = liqCert.getTipoCertificado();
                     	
                     	//Si el certificado es de antiguo sistema se coloca en false el atributo
                     	//ocultar antiguo sistema de la sesión.
                     	if (kind != null)
                     	{
							if (kind.getIdTipoCertificado().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID)|| kind.getNombre().equals(CTipoCertificado.TIPO_ANTIGUO_SISTEMA_NOMBRE))
							{
								request.getSession().setAttribute(WebKeys.OCULTAR, "FALSE");
                     		}
                     	}
                     	
                     }
			  
				}
			  }	  
					  
					  Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
					  String nombreCampo = CFolio.ID_MATRICULA + num.toString();
					  nombreCampo = nombreCampo.toUpperCase();
					  String matricula = matriculaAsociada;
					  if ((matricula != null) && !matricula.equals("")) {
						  session.setAttribute(nombreCampo, matricula);
						  int val = num.intValue();
						  val++;
						  Integer nuevo = new Integer(val);
						  session.setAttribute(AWCertificadoEspecial.NUM_MATRICULAS, nuevo);
						  String numeroAnota = respuesta.getNumeroAnotaciones();
						  String mayorExtension = respuesta.getMayorExtension();
						  session.setAttribute(AWCertificadoEspecial.MAYOR_EXTENSION_FOLIO, mayorExtension);
						  session.setAttribute(AWCertificadoEspecial.NUM_ANOTACIONES_FOLIO, numeroAnota);

					  }
					  preservarInfo(request);
		}
		
		if (evento == null)
		{
			if (accion.equals(CAMBIAR_TIPO_OFICINA_ORIGEN))
			{
				preservarInfo(request);
				Integer nume = (Integer) request.getSession().getAttribute(CFolio.NUM_MATRICULAS);

				int num = 0;
				if (nume != null) {
					num = nume.intValue();
				}
				for (int i = 0; i < num; i++) {
					Integer camb = new Integer(i);
					HttpSession session = request.getSession();

					session.removeAttribute(CFolio.ID_MATRICULA + camb.toString());
					session.removeAttribute(CFolio.NUMERO_ANOTACIONES);
					session.removeAttribute(CFolio.MAYOR_EXTENSION);
				}
				request.getSession().removeAttribute(CFolio.NUM_MATRICULAS);
			}
		}
                /* AHERRENO
                14/05/2012
                REQ 076_151 TRANSACCION*/
                Date fechaIni =  (Date) request.getSession().getAttribute("TIEMPO_INICIO_TRANSACCION");
                Date fechaFin =  new Date();
                TransaccionSIR transaccion = new TransaccionSIR();
                List <Transaccion> acciones = (List <Transaccion>) request.getSession().getAttribute("LISTA_TRANSACCION");
                double tiempoSession =  (Double) request.getSession().getAttribute("TIEMPO_TRANSACCION");    
                long calTiempo = 0;
                try {
                    calTiempo = transaccion.calculoTransaccion(fechaIni, fechaFin);
                } catch (Exception ex) {
                    Logger.getLogger(AWCertificadoEspecial.class.getName()).log(Level.SEVERE, null, ex);
                }
                DecimalFormat df = new DecimalFormat("0.000"); 
                double calculo = Double.valueOf(df.format(tiempoSession+((double)calTiempo/1000)).replace(',', '.'));
                System.out.println("El tiempo de la accion "+accion+" en milisegundos " +calTiempo );          
                request.getSession().setAttribute("TIEMPO_TRANSACCION",calculo);
                Transaccion transaccionReg = new Transaccion();
                transaccionReg.setFechaTransaccion(fechaFin);
                transaccionReg.setAccionWeb(accion);
                transaccionReg.setTiempo(calTiempo);
                acciones.add(transaccionReg);
                request.getSession().setAttribute("LISTA_TRANSACCION",acciones);    
                
                if(accion.equals(RADICAR) || accion.equals(LIQUIDAR)){
                    if (evento instanceof EvnRespPago){
                        EvnRespPago turno = (EvnRespPago) evento;
                    /*Se recorre la lista para almacenar la informacion del turno*/
                        if(turno.getTurno() != null){
                            for (Transaccion transacion: acciones) {
                                transacion.setAnio(turno.getTurno().getAnio());
                                transacion.setIdCirculo(turno.getTurno().getIdCirculo());
                                transacion.setIdProceso(turno.getTurno().getIdProceso());
                                transacion.setIdTurno(turno.getTurno().getIdTurno());                    
                            }
                            transaccion.guardarTransaccion(acciones);
                            acciones.clear();
                            request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
                            request.getSession().setAttribute("TIEMPO_TRANSACCION",Double.valueOf(0));                              
                        }                           
                    }else if (evento instanceof EvnRespLiquidacion){
                        EvnRespLiquidacion turno = (EvnRespLiquidacion) evento;
                    /*Se recorre la lista para almacenar la informacion del turno*/
                        if(turno.getTurno() != null){
                            for (Transaccion transacion: acciones) {
                                transacion.setAnio(turno.getTurno().getAnio());
                                transacion.setIdCirculo(turno.getTurno().getIdCirculo());
                                transacion.setIdProceso(turno.getTurno().getIdProceso());
                                transacion.setIdTurno(turno.getTurno().getIdTurno());                    
                            }
                            transaccion.guardarTransaccion(acciones);
                            acciones.clear();
                            request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
                            request.getSession().setAttribute("TIEMPO_TRANSACCION",Double.valueOf(0));                              
                        }                        
                    }
                                      
                }
	}
	
	
    /**
     * Asociar una matrícula que aún no se ha migrado a la solicitud
     *
     * @param request
     *            La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> vacio
     *         String de la matrícula sin migrar que se quiere asociar para que sea
     * @throws AccionWebException
     */
    private EvnCertificado agregarMatriculaSirMig(HttpServletRequest request)
        throws AccionWebException {
        List matriculasSirMig = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);

        if (matriculasSirMig == null) {
        	matriculasSirMig = new ArrayList();
        }

        Circulo circ= (Circulo) request.getSession().getAttribute(WebKeys.CIRCULO);
		String matricula = circ.getIdCirculo()+ "-";
		matricula += request.getParameter(AGREGAR_MATRICULA_REGISTRO_MIG_INC);
        preservarInfo(request);

        if ((matricula == null) || matricula.trim().equals("")) {
            throw new ValidacionMatriculaRegistroException("Matrícula inválida");
        }

        if (matriculasSirMig.contains(matricula)) {
            throw new ValidacionMatriculaRegistroException(
                "La matrícula que quiere ingresar, ya fue ingresada");
        }
        
        org.auriga.core.modelo.transferObjects.Usuario usuario = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession()
        .getAttribute(SMARTKeys.USUARIO_EN_SESION);

        EvnCertificado evn = new EvnCertificado(usuario,EvnCertificado.VALIDAR_MATRICULA_MIG);
        evn.setMatricula(matricula.toUpperCase());
        return evn ;
        //request.getSession().setAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO,matriculasSirMig);

        //return null;
    }

    /**
     * Desasociar una matrícula de la solicitud
     *
     * @param request
     *            La información del formulario
     * @return Un <CODE>EvnLiquidacionRegistro</CODE> nulo, ya que no se
     *         necesita generar un evento
     */
    private EvnCertificado eliminarMatriculaSirMig(HttpServletRequest request) {
        List matriculas = (List) request.getSession().getAttribute(WebKeys.LISTA_MATRICULAS_SIR_MIG_SOLICITUD_REGISTRO);

        if (matriculas == null) {
            matriculas = new ArrayList();
        }

        String[] matriculasElim = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR_SIR_MIG);

        if (matriculasElim != null) {
            for (int i = 0; i < matriculasElim.length; i++) {
                String actual = matriculasElim[i];
                matriculas.remove(actual);
            }
        }

        preservarInfo(request);

        return null;
    }    


	/**
	 * Este metodo borra de la sesion la informacion que se puso en los campos,
	 * ademas de la informacion de las matriculas que se pone en sesion.
	 * @param request HttpServletRequest
	 * @return Evento nulo ya que no se requiere que viaje hasta el negocio
	 */
	private Evento removerInfo(HttpServletRequest request) {
		removerInformacion(request);
		return null;
	}

	/**
	 * Este método se declaró publico para que pueda ser utilizado por otras acciones web
	 * que declaren un objeto de tipo AWLiquidacionCertificado
	 * (Para evitar duplicación de código)
	 *
	 * @param request
	 */
	public void removerInformacion(HttpServletRequest request) {
		int val =0;
		Integer cantidadMatriculas = (Integer) session.getAttribute(AWCertificadoEspecial.NUM_MATRICULAS);
		if (cantidadMatriculas !=null)
		{
			val = cantidadMatriculas.intValue();
		}
		
		 

		session.removeAttribute(AWCertificadoEspecial.COPIAS);
		session.removeAttribute(AWCertificadoEspecial.TIPO_CERTIFICADO);
		session.removeAttribute(AWCertificadoEspecial.TIPO_TARIFA_CERTIFICADOS);
		session.removeAttribute(AWCertificadoEspecial.TURNO_ANTERIOR);
		session.removeAttribute(AWCertificadoEspecial.TIPO_ID_CIUDADANO);
		session.removeAttribute(AWCertificadoEspecial.ID_CIUDADANO);
		session.removeAttribute(AWCertificadoEspecial.APELLIDO1);
		session.removeAttribute(AWCertificadoEspecial.APELLIDO2);
		session.removeAttribute(AWCertificadoEspecial.NOMBRE);
		session.removeAttribute(AWCertificadoEspecial.TELEFONO);

		session.removeAttribute(AWCertificadoEspecial.MAYOR_EXTENSION_FOLIO);
		session.removeAttribute(AWCertificadoEspecial.NUM_ANOTACIONES_FOLIO);

		String keyAntSistema[] = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;
		String key;
		for (int i = 0; i < keyAntSistema.length; i++) {
			key = keyAntSistema[i];
			session.removeAttribute(key);
		}

		String keyDocumento[] = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;
		for (int i = 0; i < keyDocumento.length; i++) {
			key = keyDocumento[i];
			session.removeAttribute(key);
		}

		for (int i = 0; i < val; i++) {
			Integer actual = new Integer(i);
			String id = AWCertificadoEspecial.MATRICULA + actual.toString();
			session.removeAttribute(id);
		}
		session.setAttribute(AWCertificadoEspecial.NUM_MATRICULAS, new Integer(0));
	}

	/**
	 * Este metodo pone en la sesion la informacion que se puso en los campos,
	 * @param request HttpServletRequest
	 * @return Evento nulo ya que no se requiere que viaje hasta el negocio
	 */
	public void preservarInfo(HttpServletRequest request) {
		String copias = request.getParameter(AWCertificadoEspecial.COPIAS);
		session.setAttribute(CTipoImprimible.CERTIFICADO_INMEDIATO,request.getParameter(CTipoImprimible.CERTIFICADO_INMEDIATO));
		session.setAttribute(CTipoImprimible.CERTIFICADO_EXTENSO,request.getParameter(CTipoImprimible.CERTIFICADO_EXTENSO));
		session.setAttribute(CTipoImprimible.CERTIFICADO_EXENTO,request.getParameter(CTipoImprimible.CERTIFICADO_EXENTO));
				
		String tipoCert = request.getParameter(AWCertificadoEspecial.TIPO_CERTIFICADO);
		String tipoTarifa = request.getParameter(AWCertificadoEspecial.TIPO_TARIFA_CERTIFICADOS);

		String turnoAnterior = request.getParameter(AWCertificadoEspecial.TURNO_ANTERIOR);
		
		String circuloCertNal = request.getParameter(CIRCULO_CERTIFICADO_NACIONAL);

		String keyAntSistema[] = CDatosAntiguoSistema.LISTA_KEYS_DATOS_ANTIGUO_SISTEMA;
		String key, valor;
		for (int i = 0; i < keyAntSistema.length; i++) {
			key = keyAntSistema[i];
			valor = request.getParameter(key);
			if (valor != null && !valor.trim().equals("")) {
				session.setAttribute(key, valor);
			}
		}

		String keyDocumentos[] = CDatosAntiguoSistema.LISTA_KEYS_DOCUMENTO;
		for (int i = 0; i < keyDocumentos.length; i++) {
			key = keyDocumentos[i];
			valor = request.getParameter(key);
			if (valor != null && !valor.trim().equals("")) {
				session.setAttribute(key, valor);
			}
		}

		String tipoId = request.getParameter(CCiudadano.TIPODOC);
		String numId = request.getParameter(CCiudadano.IDCIUDADANO);
		String apellido1 = request.getParameter(CCiudadano.APELLIDO1);
		String apellido2 = request.getParameter(CCiudadano.APELLIDO2);
		String nombres = request.getParameter(CCiudadano.NOMBRE);
		String telefono = request.getParameter(CCiudadano.TELEFONO);
		String tipoOficinaOrigen = request.getParameter(TIPO_OFICINA_ORIGEN);

		if ((copias != null)) {
			session.setAttribute(AWCertificadoEspecial.COPIAS, copias);
		}

		if ((tipoCert != null)) {
			session.setAttribute(AWCertificadoEspecial.TIPO_CERTIFICADO, tipoCert);
		}

		if ((tipoTarifa != null)) {
			session.setAttribute(AWCertificadoEspecial.TIPO_TARIFA_CERTIFICADOS, tipoTarifa);
		}



		
		if ((turnoAnterior != null)) {
			session.setAttribute(AWCertificadoEspecial.TURNO_ANTERIOR, turnoAnterior);
		}


		request.getSession().setAttribute(CCiudadano.TIPODOC, tipoId);
		request.getSession().setAttribute(CCiudadano.IDCIUDADANO, numId);
		request.getSession().setAttribute(CCiudadano.APELLIDO1, apellido1);
		request.getSession().setAttribute(CCiudadano.NOMBRE, nombres);
		request.getSession().setAttribute(CCiudadano.APELLIDO2, apellido2);
		request.getSession().setAttribute(CCiudadano.TELEFONO, telefono);
		request.getSession().setAttribute(TIPO_OFICINA_ORIGEN,tipoOficinaOrigen);
		request.getSession().setAttribute(CIRCULO_CERTIFICADO_NACIONAL,circuloCertNal);
	}

	private Calendar darFecha(String fechaInterfaz) {
		Calendar calendar = Calendar.getInstance();
		String[] partido = fechaInterfaz.split("/");

		if (partido.length == 3) {
			int dia = Integer.parseInt(partido[0]);
			int mes = Integer.parseInt(partido[1]) - 1;
			int año = Integer.parseInt(partido[2]);
			calendar.set(año, mes, dia);

			if ((calendar.get(Calendar.YEAR) == año) && (calendar.get(Calendar.MONTH) == mes) && (calendar.get(Calendar.DAY_OF_MONTH) == dia)) {
				if (!calendar.after(Calendar.getInstance())) {
					return calendar;
				}
			}
		}

		return null;
	}

	public static String getFecha(Date fecha) {
		Calendar c = Calendar.getInstance();
		String fechaString = " ";
		if (fecha != null) {

			c.setTime(fecha);
			fechaString = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
		}
		return fechaString;
	}

	public static String getValor(HttpServletRequest request, String key) {
		String tmp = request.getParameter(key);
		String valor = "&nbsp;";
		if (tmp != null) {
			if (tmp.trim().length() > 0) {
				valor = tmp;
			}
		}
		return valor;
	}

}
