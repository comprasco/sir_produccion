package gov.sir.core.web.acciones.certificado;

import co.com.iridium.generalSIR.comun.modelo.Transaccion;
import co.com.iridium.generalSIR.transacciones.TransaccionSIR;
import gov.sir.core.eventos.certificado.EvnCambioMatricula;
import gov.sir.core.eventos.certificado.EvnCertificado;
import gov.sir.core.eventos.certificado.EvnRespCambioMatricula;
import gov.sir.core.eventos.certificado.EvnRespCertificado;
import gov.sir.core.eventos.certificado.EvnRespValidacionMatricula;
import gov.sir.core.eventos.certificado.EvnValidacionMatricula;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.LiquidacionTurnoCertificado;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoCertificado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CDatosAntiguoSistema;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.MatriculaNoDadaException;
import gov.sir.core.web.acciones.excepciones.OficioInvalidoException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
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
 * Esta accion web se encarga de generar eventos de negocio relacionados con
 * la reimpresion de un certificados
 * @author mmunoz
 */
public class AWCertificado extends SoporteAccionWeb {

	public static final String OFICIO_PERTENENCIA = "OFICIO_PERTENENCIA";

	/**Esta constante identifica la accion de entregar el certificado */
	public static final String ENTREGAR = "ENTREGAR";

	/**Esta constante identifica la accion de imprimir el certificado */
	public static final String IMPRIMIR = "IMPRIMIR";

	/**Esta constante identifica la accion de enviar a imprimir de manera especial el certificado */
	public static final String IMPRIMIR_ESPECIAL = "IMPRIMIR_ESPECIAL";

	/**Esta constante identifica la accion de confirmar al imprimir*/
	public static final String IMPRIMIR_CONFIRMAR = "IMPRIMIR_CONFIRMAR";

	/**Esta constante identifica la accion de enviar por impresion especial el certificado */
	public static final String PRINTESPECIAL = "PRINTESPECIAL";

	/**Esta constante identifica la accion de confirmar la impresion por mayor extencion del certificado */
	public static final String CONFIRMAR_MAYOR_EXT = "CONFIRMAR_MAYOR_EXT";

	/**Esta constante identifica la accion de negar la impresion por mayor extencion del certificado */
	public static final String NEGAR_MAYOR_EXT = "NEGAR_MAYOR_EXT";

	/**Esta constante identifica la accion de confirmar la impresion por impresion especial del certificado */
	public static final String CONFIRMAR_PRINTESPECIAL = "CONFIRMAR_PRINTESPECIAL";

	/**Esta constante identifica la accion de negar la impresion por impresion especial del certificado */
	public static final String NEGAR_PRINTESPECIAL = "NEGAR_PRINTESPECIAL";

	/**Esta constante identifica la accion de negar la impresion por impresion especial del certificado */
	public static final String ENVIAR_PRINTESPECIAL = "ENVIAR_PRINTESPECIAL";

	/**Esta constante identifica la accion de confirmar la revision inicial en antiguo sistema */
	public static final String ANT_SISTEMA_REVISION_INICIAL_CONFIRMAR = "ANT_SISTEMA_REVISION_INICIAL_CONFIRMAR";

	/**Esta constate identifica la accion de negar la revision inicial en antiguo sistema*/
	public static final String ANT_SISTEMA_REVISION_INICIAL_NEGAR = "ANT_SISTEMA_REVISION_INICIAL_NEGAR";

	/**Esta constante identifica la accion de exitpo para anexar informacion */
	public static final String ANT_SISTEMA_ANEXAR_INFORMACION_EXITO = "ANT_SISTEMA_ANEXAR_INFORMACION_EXITO";

	/**Esta constante identifica la accion fracaso para anexar mas informacion*/
	public static final String ANT_SISTEMA_ANEXAR_INFORMACION_FRACASO = "ANT_SISTEMA_ANEXAR_INFORMACION_FRACASO";

	/**Esta constante identifica la accion que el folio existe en creacion de folio */
	public static final String ANT_SISTEMA_CREACION_FOLIO_EXISTE = "ANT_SISTEMA_CREACION_FOLIO_EXISTE";

	/**Esta constante identific a la accion que el folio fue creado*/
	public static final String ANT_SISTEMA_CREACION_FOLIO_CREADO = "ANT_SISTEMA_CREACION_FOLIO_CREADO";

	/**Esta constante identifica la accion de enviar a traer mas documentos en la creacion de folios */
	public static final String ANT_SISTEMA_CREACION_FOLIO_MAS_DOCS = "ANT_SISTEMA_CREACION_FOLIO_MAS_DOCS";

	/**Esta constante identifica la accion de enviar a guardar definitivo el folio que se realizó en */
	public static final String ANT_SISTEMA_CREACION_HACER_DEFINITIVO_FOLIO = "ANT_SISTEMA_CREACION_HACER_DEFINITIVO_FOLIO";

	/**Esta constante identifica la accion de devolver un folio de creación de folio a digitar la hoja de ruta,
	 * porque al buscador de antiguo sistema, no hizó bien la hoja de ruta*/
	public static final String ANT_SISTEMA_CREACION_REANALIZAR = "ANT_SISTEMA_CREACION_REANALIZAR";

	/**Esta constante identifica la accion rechazar en creacion de folio */
	public static final String ANT_SISTEMA_CREACION_FOLIO_RECHAZADO = "ANT_SISTEMA_CREACION_FOLIO_RECHAZADO";

	/**Esta constnte identifica la accion de reanalizar en creacion de folio */
	public static final String ANT_SISTEMA_CREACION_FOLIO_REANALIZAR = "ANT_SISTEMA_CREACION_FOLIO_REANALIZAR";

	/**Esta constante identifica la accion de confirmar en micro filmado */
	public static final String ANT_SISTEMA_MICROFILMADO_CONFIRMAR = "ANT_SISTEMA_MICROFILMADO_CONFIRMAR";

	/**Esta constante identifica la accion de negar en microfilmado */
	public static final String ANT_SISTEMA_MICROFILMADO_NEGAR = "ANT_SISTEMA_MICROFILMADO_NEGAR";

	/**Esta constante identifica la accion de confirmar en la fase de notificar traer mas documentos*/
	public static final String NOTIFICAR_MAS_DOCS_CONFIRMAR = "NOTIFICAR_MAS_DOCS_CONFIRMAR";

	/**Esta constante identifica la accion de agregar un folio existente a un turno de antiguo sistema*/
	public static final String ANT_SISTEMA_REVISION_EXISTE = "ANT_SISTEMA_CREACION_EXISTE";

	/**Esta constane identifica la accion de enviar el turno creado*/
	public static final String ANT_SISTEMA_REVISION_CREADO = "ANT_SISTEMA_REVISION_CREADO";

	/**Esta constane identifica la accion de enviar el turno creado*/
	public static final String ANT_SISTEMA_REVISION_REANALIZAR = "ANT_SISTEMA_REVISION_REANALIZAR";

	/**Esta constante identifica la accion de enviar a mas docuementos la revision en antiguo sistema*/
	public static final String ANT_SISTEMA_REVISION_MAS_DOCS = "ANT_SISTEMA_REVISION_MAS_DOCS";

	/**Esta constante identifica la accion de rechazar en la revision de antiguo sistema */
	public static final String ANT_SISTEMA_REVISION_RECHAZADO = "ANT_SISTEMA_REVISION_RECHAZADO";

	/**Esta constante identifica la accion de confirmar al incluir el oficio de pertenencia*/
	public static final String OFICIO_PERTENENCIA_CONFIRMAR = "OFICIO_PERTENENCIA_CONFIRMAR";

	/**Esta constante identifica la accion de confirmar al incluir el oficio de pertenencia*/
	public static final String ANT_SISTEMA_REVISION_AGREGAR = "AGREGAR";

	/**Esta constante identifica la accion de eliminar una matrícula en la creación de la hoja de ruta.*/
	public static final String HOJA_RUTA_ELIMINAR_MATRICULA = "HOJA_RUTA_ELIMINAR_MATRICULA";

	/**Esta constante identifica la accion de imprimir la hoja de ruta.*/
	public static final String IMPRIMIR_HOJA_RUTA = "IMPRIMIR_HOJA_RUTA";

	/**Esta constante identifica la accion de consultar un folio para cpiar su complementación.*/
	public static final String CONSULTA_FOLIO_COMPLEMENTACION = "CONSULTA_FOLIO_COMPLEMENTACION";

	/**Esta constante identifica la accion de cambiar la matricula del folio. **/
	public static final String CAMBIAR_FOLIO_MATRICULA_ID = "CAMBIAR_FOLIO_MATRICULA_ID";

	/**Esta constante identifica el campo Folio.IdMatricula, la matricula de un folio **/
	public static final String FOLIO_MATRICULA_ID = "FOLIO_MATRICULA_ID";

	/**
	* Esta constante se utiliza para consultar el secuencial de recibo de la estación.
	*/
	public static final String CONSULTAR_SECUENCIAL_RECIBO = "CONSULTAR_SECUENCIAL_RECIBO";
	
	/**
	* Esta constante se utiliza para consultar el secuencial de recibo de la estación.
	*/
	public static final String CONSULTAR_SECUENCIAL_RECIBO_SIMPLIFICADO = "CONSULTAR_SECUENCIAL_RECIBO_SIMPLIFICADO";

	/**Esta constante identifica la accion de consultar una plantilla, para generar oficio de pertenencia.*/
	public static final String CONSULTAR_PLANTILLA_PERTENENCIA = "CONSULTAR_PLANTILLA_PERTENENCIA";

	/**
	* Esta constante se utiliza para incrementar el secuencial de recibo de la estación.
	*/
	public static final String INCREMENTAR_SECUENCIAL_RECIBO = "INCREMENTAR_SECUENCIAL_RECIBO";
	
	/**
	* Esta constante se utiliza para incrementar el secuencial de recibo de la estación.
	*/
	public static final String INCREMENTAR_SECUENCIAL_RECIBO_SIMPLIFICADO = "INCREMENTAR_SECUENCIAL_RECIBO_SIMPLIFICADO";
	
	/**
	* Esta constante se utiliza para obtencion del folio en reimpresion especial.
	*/
	public static final String OBTENER_FOLIO_REIMPRESION_ESPECIAL= "OBTENER_FOLIO_REIMPRESION_ESPECIAL";
	
	public static final String AMPLIACION_TRADICION_CONFIRMAR = "AMPLIACION_TRADICION_CONFIRMAR";
	public static final String AMPLIACION_TRADICION_NEGAR = "AMPLIACION_TRADICION_NEGAR";
	public static final String AMPLIACION_TRADICION_GUARDAR = "AMPLIACION_TRADICION_GUARDAR";
	public static final String ACTUALIZAR_TRADICION_FOLIO = "ACTUALIZAR_TRADICION_FOLIO";
	public static final String CER_AMPLIACION_TRADICION="CER_AMPLIACION_TRADICION";
	public static final String AMPLIACION_DE_TRADICION = "AMPLIACION DE TRADICION:";
	
	

	/**Esta constante identifica la accion de cambiar la lista tipo de nota, por la lista de causales de reimpresion.**/
	//public static final String FOLIO_MATRICULA_ID = "FOLIO_MATRICULA_ID";

	/**Accion que llega de la interfaz*/
	private String accion;

	/**
	* Método principal de esta acción web. Aqui se realiza
	* toda la lógica requerida de validación y de generación
	* de eventos de negocio.
	* @param request trae la informacion del formulario
	* @throws AccionWebException cuando ocurre un error
	* @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {
		accion = request.getParameter("ACCION");

		if ((accion == null) || (accion.length() == 0)) {
			throw new AccionWebException("Debe indicar una accion");
		}
                /*AHERRENO
                 14/05/2012
                 REQ 076_151 TRANSACCION*/
                request.getSession().setAttribute("TIEMPO_INICIO_TRANSACCION", new Date());

		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String idCirculo = circulo.getIdCirculo();
		gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
		ServletContext context = session.getServletContext();

		String pathFirmas=null;


		File file = (File) context.getAttribute(WebKeys.FIRMAS_DIRECTORY);

		if (file != null)
		{
			pathFirmas = file.getPath();
		}

		Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		if (accion.equals(CAMBIAR_FOLIO_MATRICULA_ID)) {
			Circulo circ= (Circulo) session.getAttribute(WebKeys.CIRCULO);
			String matriculaNueva= circ.getIdCirculo()+"-";
			matriculaNueva += (String) request.getParameter(FOLIO_MATRICULA_ID);

			
			SolicitudCertificado solCerti = (SolicitudCertificado) turno.getSolicitud();
			Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
			String matriculaActual = folio.getIdMatricula();

			List liquidaciones = solCerti.getLiquidaciones();
			LiquidacionTurnoCertificado liquida = (LiquidacionTurnoCertificado) liquidaciones.get(liquidaciones.size() - 1);
			TipoCertificado tipoCerti = liquida.getTipoCertificado();
			String tipoCertificado = tipoCerti.getIdTipoCertificado();
			turno.getAnio();
			TurnoPk tid = new TurnoPk();
			tid.anio = turno.getAnio();
			tid.idCirculo = turno.getIdCirculo();
			tid.idProceso = turno.getIdProceso();
			tid.idTurno = turno.getIdTurno();

			Usuario usuarioNeg = (Usuario) session.getAttribute(WebKeys.USUARIO);
			return new EvnCambioMatricula(usuarioAuriga, matriculaActual, matriculaNueva, tipoCertificado, tid, usuarioNeg);
		}
		if (accion.equals(AWCertificado.ENTREGAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ENTREGA_OK, turno, fase, estacion, EvnCertificado.ENTREGA_OK, usuario);
		} else if (accion.equals(AWCertificado.IMPRIMIR)) {
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(turno.getFechaInicio());
			Calendar calendarioAct = Calendar.getInstance();
			calendarioAct.setTime(new Date());

			if((calendario.get(Calendar.DAY_OF_MONTH) != calendarioAct.get(Calendar.DAY_OF_MONTH)) ||
				(calendario.get(Calendar.YEAR) != calendarioAct.get(Calendar.YEAR)) ||
				(calendario.get(Calendar.MONTH) != calendarioAct.get(Calendar.MONTH))){
				throw new AccionInvalidaException("El turno no puede imprimirse, ya que no fue creado hoy. " +
					"Para intentar imprimir debe enviarse por Reimpresion Especial");
			}
			EvnCertificado evento = new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ENTREGA_REIMPRESION_CORRIENTE, turno, fase, estacion, EvnCertificado.ENTREGA_REIMPRESION_CORRIENTE, usuario, rol, proceso);
			evento.setPathFirmas(pathFirmas);
			return evento;
		} else if (accion.equals(AWCertificado.IMPRIMIR_CONFIRMAR)) {
			EvnCertificado evento = new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_IMPRESION_CONFIRMAR, turno, fase, estacion, EvnCertificado.IMPRESION_CONFIRMAR, usuario);
			evento.setPathFirmas(pathFirmas);
			return evento;
		} else if (accion.equals(AWCertificado.IMPRIMIR_ESPECIAL)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ENTREGA_REIMPRESION_ESPECIAL, turno, fase, estacion, EvnCertificado.ENTREGA_REIMPRESION_ESPECIAL, usuario, rol, proceso);
		} else if (accion.equals(AWCertificado.CONFIRMAR_MAYOR_EXT)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_MAYOR_EXT_CONFIRMAR, turno, fase, estacion, EvnCertificado.MAYOR_EXT_CONFIRMAR, usuario);
		} else if (accion.equals(AWCertificado.NEGAR_MAYOR_EXT)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_MAYOR_EXT_NEGAR, turno, fase, estacion, EvnCertificado.MAYOR_EXT_NEGAR, usuario);
		} else if (accion.equals(AWCertificado.CONFIRMAR_PRINTESPECIAL)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_REIMP_ESP_CONFIRMAR, turno, fase, estacion, EvnCertificado.REIMP_ESP_CONFIRMAR, usuario, rol, proceso);
		} else if (accion.equals(AWCertificado.NEGAR_PRINTESPECIAL)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_REIMP_ESP_NEGAR, turno, fase, estacion, EvnCertificado.REIMP_ESP_NEGAR, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_REVISION_INICIAL_CONFIRMAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_REV_INI_CONFIRMAR, turno, fase, estacion, EvnCertificado.ANT_SIST_REV_INI_CONFIRMAR, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_REVISION_INICIAL_NEGAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_REV_INI_NEGAR, turno, fase, estacion, EvnCertificado.ANT_SIST_REV_INI_NEGAR, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_ANEXAR_INFORMACION_EXITO)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_ANEXAR_INFORMACION_EXITO, turno, fase, estacion, EvnCertificado.ANT_SIST_ANEXAR_INFO_EXITO, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_ANEXAR_INFORMACION_FRACASO)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_ANEXAR_INFORMACION_FRACASO, turno, fase, estacion, EvnCertificado.ANT_SIST_ANEXAR_INFO_FRACASO, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_CREACION_FOLIO_EXISTE)) {
			return antSistemaExiste(request);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_CREACION_FOLIO_REANALIZAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_REANALIZAR, turno, fase, estacion, EvnCertificado.ANT_SIST_CREACION_FOLIO_REANALIZA, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_CREACION_FOLIO_RECHAZADO)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_RECHAZADO, turno, fase, estacion, EvnCertificado.ANT_SIST_CREACION_FOLIO_RECHAZADO, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_CREACION_FOLIO_MAS_DOCS)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_MAS_DOCS, turno, fase, estacion, EvnCertificado.ANT_SIST_CREACION_FOLIO_MAS_DOCS, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_CREACION_HACER_DEFINITIVO_FOLIO)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_HACER_DEFINITIVO_FOLIO, turno, fase, estacion, EvnCertificado.ANT_SIST_CREACION_FOLIO_CREADO, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_CREACION_REANALIZAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_HACER_DEFINITIVO_REANALIZAR, turno, fase, estacion, EvnCertificado.ANT_SIST_CREACION_FOLIO_REANALIZA, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_CREACION_FOLIO_CREADO)) {
			return this.finalizarHojaDeRuta(request,fase);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_MICROFILMADO_CONFIRMAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_MICROFILM_CONFIRMAR, turno, fase, estacion, EvnCertificado.ANT_SIST_MICROFILM_CONFIRMAR, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_MICROFILMADO_NEGAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_MICROFILM_NEGAR, turno, fase, estacion, EvnCertificado.ANT_SIST_MICROFILM_NEGAR, usuario);
		} else if (accion.equals(AWCertificado.NOTIFICAR_MAS_DOCS_CONFIRMAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_NOTIFICAR_MAS_DOCS_CONFIRMAR, turno, fase, estacion, EvnCertificado.NOTIFICAR_MAS_DOCS_CONFIRMAR, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_REVISION_RECHAZADO)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_REVISION_RECHAZADO, turno, fase, estacion, EvnCertificado.ANT_SIST_REVISION_RECHAZADO, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_REVISION_EXISTE)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_REVISION_EXISTE, turno, fase, estacion, EvnCertificado.ANT_SIST_REVISION_EXISTE, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_REVISION_MAS_DOCS)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_REVISION_MAS_DOCS, turno, fase, estacion, EvnCertificado.ANT_SIST_REVISION_MAS_DOCS, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_REVISION_CREADO)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_REVISION_CREADO, turno, fase, estacion, EvnCertificado.ANT_SIST_REVISION_CREADO, usuario);
		} else if (accion.equals(AWCertificado.ANT_SISTEMA_REVISION_REANALIZAR)) {
			return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_REVISION_REANALIZAR, turno, fase, estacion, EvnCertificado.ANT_SIST_REVISION_REANALIZAR, usuario);
		} else if (accion.equals(AWCertificado.OFICIO_PERTENENCIA_CONFIRMAR)) {
			return oficioPertenencia(request);
		} else if (accion.equals(ANT_SISTEMA_REVISION_AGREGAR)) {
			return adicionarMatricula(request);
		} else if (accion.equals(AWCertificado.HOJA_RUTA_ELIMINAR_MATRICULA)) {
			return eliminarMatriculaHojaRuta(request);
		} else if (accion.equals(CONSULTA_FOLIO_COMPLEMENTACION)) {
			return consultaFolioComplementacion(request);
		}else if (accion.equals(AWCertificado.INCREMENTAR_SECUENCIAL_RECIBO)||accion.equals(AWCertificado.INCREMENTAR_SECUENCIAL_RECIBO_SIMPLIFICADO)) {
			preservarInfoLiquidacion(request);
			return incrementarSecuencialRecibo (request);
		} else if (accion.equals(AWCertificado.IMPRIMIR_HOJA_RUTA)) {
			return imprimirHojaRuta(request);
		} else if (accion.equals(AWCertificado.OBTENER_FOLIO_REIMPRESION_ESPECIAL)) {
			return obtenerFolioReimpresionEspecial(request);
		}else if (accion.equals(AWCertificado.CONSULTAR_PLANTILLA_PERTENENCIA)) {
			String respuesta = request.getParameter(WebKeys.PLANTILLA);
			String idZona = request.getParameter(CFolio.ID_ZONA_REGISTRAL);
			String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
			Folio folio = new Folio();
			folio.setIdMatricula(idMatricula);
			EvnCertificado eventoCert = new EvnCertificado(usuarioAuriga, usuario, EvnCertificado.CONSULTAR_PLANTILLA_PERTENENCIA,respuesta);
			eventoCert.setFolio(folio);
			return eventoCert;
		} else if (accion.equals(AWCertificado.CONSULTAR_SECUENCIAL_RECIBO)||accion.equals(AWCertificado.CONSULTAR_SECUENCIAL_RECIBO_SIMPLIFICADO)) {
			preservarInfoLiquidacion(request);
			return new EvnCertificado(usuarioAuriga, EvnCertificado.CONSULTAR_SECUENCIAL_RECIBO, turno, fase, estacion, EvnCertificado.CONSULTAR_SECUENCIAL_RECIBO, usuario);
		}else if(accion.equals(AWCertificado.AMPLIACION_TRADICION_CONFIRMAR)){
			return ampliacionTradicionConfirmar(request);
		}else if(accion.equals(AWCertificado.AMPLIACION_TRADICION_GUARDAR)){
			return ampliacionTradicionGuardar(request);
		}else if(accion.equals(AWCertificado.AMPLIACION_TRADICION_NEGAR)){
			return ampliacionTradicionNegar(request);
		}else if(accion.equals(CER_AMPLIACION_TRADICION)){
			String respuesta = request.getParameter(WebKeys.PLANTILLA);
			String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
			Folio folio = new Folio();
			folio.setIdMatricula(idMatricula);
			EvnCertificado eventoCert = new EvnCertificado(usuarioAuriga, usuario, EvnCertificado.CER_AMPLIACION_TRADICION,respuesta);
			eventoCert.setFolio(folio);
			return eventoCert;
		}
		else {
			throw new AccionInvalidaException("La acción " + accion + " no es válida.");
		}
	}

	/**
	 * Metodo que agrega un oficio de pertenencia al turno.
	 * @param request HttpServletRequest
	 * @return un <CODE>Evento</CODE> cuando es necesario o nulo en cualquier otro caso
	 * @throws AccionWebException
	 */
	private Evento oficioPertenencia(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		String oficio = request.getParameter(AWCertificado.OFICIO_PERTENENCIA);
		gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
		if (oficio == null || oficio.length() <= 0) {
			throw new OficioInvalidoException("El oficio que escribio es invalido");
		}

		Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
		/*
		if(folio == null){
			throw new AccionWebException("El folio que se encotro en sesion es invalido");
		}*/
		String oficioPertenencia = (String) request.getParameter(OFICIO_PERTENENCIA);
		EvnCertificado evento = new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_OFICIO_PERTENENCIA_CONFIRMAR, turno, fase, estacion, EvnCertificado.OFICIO_PERTENENCIA_CONFIRMAR, usuario, oficioPertenencia);
		evento.setUID(request.getSession().getId());
		return evento;
	}

	/**
	 * Método que relaciona un folio existente con el turno de antiguo sistema
	 * @param request HttpServletRequest
	 * @return un <CODE>EvnCertificado</CODE> cuando es necesario o nulo en cualquier otro caso
	 * @throws MatriculaNoDadaException
	 */
	private EvnCertificado antSistemaExiste(HttpServletRequest request) throws MatriculaNoDadaException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Integer num = (Integer) request.getSession().getAttribute(CFolio.NUM_MATRICULAS);
		String matricula = null;
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

		Solicitud solicitud = turno.getSolicitud();
		//Se válida que si la solicitud no tiene un folio en la solicitud, válide que se haya ingresado uno nuevo en la interfaz.
		if(solicitud==null || solicitud.getSolicitudFolios()==null || solicitud.getSolicitudFolios().isEmpty()){
			if (num.intValue() <= 0) {
				throw new MatriculaNoDadaException("Debe incluir un número de una matrícula");
			}
		}else{
			Iterator itSol = solicitud.getSolicitudFolios().iterator();
			SolicitudFolio solFolio = null;
			while (itSol.hasNext()){
				solFolio = (SolicitudFolio)itSol.next();
			}
			if(solFolio!=null&&solFolio.getFolio()!=null){
				matricula = solFolio.getFolio().getIdMatricula();
			}
		}

		if(matricula==null||matricula.equals("")){
			int val = num.intValue();
			for (int i = 0; i < val; i++) {
				Integer actual = new Integer(i);
				String id = CFolio.ID_MATRICULA + actual.toString();
				matricula = (String) session.getAttribute(id);
				SolicitudFolio solFolio = new SolicitudFolio();
				Folio folio = new Folio();
				folio.setIdMatricula(matricula);
				solFolio.setFolio(folio);
			}
		}



		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		return new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_EXISTE, turno, fase, usuario, EvnCertificado.ANT_SIST_CREACION_FOLIO_EXISTE, matricula);

	}

	/**
	 * Agrega una matricula de la lista de las matriculas ingresadas.
	 * @param request HttpServletRequest
	 * @return EvnLiquidacion nulo ya que no requiere viajar al negocio
	 */

	private EvnValidacionMatricula adicionarMatricula(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		Turno turno = (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		Solicitud solicitud = turno.getSolicitud();


                // validators

                // 1: conditional validators

                if( solicitud instanceof SolicitudCertificado ) {

                    // Se válida que si la solicitud no tiene un folio en la solicitud;
                    // valida que se haya ingresado uno nuevo en la interfaz.
                    if( ( null != solicitud )
                      &&( solicitud.getSolicitudFolios()!=null )
                      &&( !solicitud.getSolicitudFolios().isEmpty() ) ) {


                            ValidacionParametrosException excepcion = new ValidacionParametrosException();
                            excepcion.addError("No se puede agregar una matrícula porque el turno ya tiene un folio asociado");
                            throw excepcion;
                    }

                }



		Integer num = (Integer) session.getAttribute(CFolio.NUM_MATRICULAS);
		String nombreCampo = CFolio.ID_MATRICULA + num.toString();
		nombreCampo = nombreCampo.toUpperCase();
		String matricula = request.getParameter(nombreCampo);
		String tipoCertificado = CTipoCertificado.TIPO_ANTIGUO_SISTEMA_ID;

		EvnValidacionMatricula evento = null;
		if ((matricula != null) && !matricula.equals("")) {
			evento = new EvnValidacionMatricula(usuarioAuriga, matricula, tipoCertificado, "AGREGAR_NATRICULA_ANT_SIS");
		}
		return evento;
	}

	/**
	 * Elimina una matricula de la lista de las matriculas ingresadas.
	 * @param request HttpServletRequest
	 * @return Evento nulo ya que no requiere viajar al negocio
	 */
	public Evento eliminarMatriculaHojaRuta(HttpServletRequest request) {
		HttpSession session = request.getSession();
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
		session.setAttribute(AWLiquidacionCertificado.NUM_MATRICULAS, new Integer(val));
		return null;
	}



	/**
	 * Método que imprime la hoja de ruta creada.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnCertificado finalizarHojaDeRuta(HttpServletRequest request, Fase fase) throws AccionWebException {
		HttpSession session = request.getSession();

		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);
		Direccion direccion = (Direccion) session.getAttribute(CDireccion.DIRECCION_TEMPORAL);

		if(folio.getDirecciones()!=null && folio.getDirecciones().isEmpty() && direccion !=null){
			folio.addDireccione(direccion);
		}

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR =  (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String uid = request.getSession().getId();
		EvnCertificado evento = new EvnCertificado(usuarioAuriga, EvnCertificado.TIPO_ANT_SIST_CREACION_FOLIO_CREADO, turno, fase, estacion, EvnCertificado.ANT_SIST_CREACION_FOLIO_CREADO, usuarioSIR);
		evento.setFolio(folio);
		evento.setUID(uid);




		return evento;
	}


	/**
	 * Método que imprime la hoja de ruta creada.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnCertificado imprimirHojaRuta(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Folio folio = (Folio) session.getAttribute(WebKeys.FOLIO);

		Direccion direccion = (Direccion) session.getAttribute(CDireccion.DIRECCION_TEMPORAL);

		if(folio.getDirecciones()!=null && folio.getDirecciones().isEmpty() && direccion !=null){
			folio.addDireccione(direccion);
		}

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR =  (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		String uid = request.getSession().getId();

		EvnCertificado eventoCertificado = new EvnCertificado(usuarioAuriga , usuarioSIR, EvnCertificado.IMPRIMIR_HOJA_RUTA,  turno,  estacion , folio, uid);

		return eventoCertificado;
	}

	/**
	 * Permite consultar un folio para traer la información que puede ser copiada a otros folios.
	 * @param request
	 * @return EvnCertificado
	 */
	private EvnCertificado consultaFolioComplementacion(HttpServletRequest request) throws AccionWebException {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);

		preservarInfo(request);

		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		request.getSession().removeAttribute(WebKeys.MENSAJE);

		if (idMatricula == null) {
			idMatricula = (String) request.getSession().getAttribute(CFolio.ID_MATRICULA);
		}

		String tipoComp = request.getParameter(CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION);
		request.getSession().setAttribute(CFolio.SELECCIONAR_FOLIO  + AWModificarFolio.FOLIO_COMPLEMENTACION,tipoComp);

		Folio folio = new Folio();
		folio.setIdMatricula(idMatricula);

		return new EvnCertificado(usuarioAuriga, usuarioSIR, EvnCertificado.CONSULTA_FOLIO_COMPLEMENTACION, folio);
	}
	
	/**
	 * Método que obtiene el folio con sus anotaciones en la fase de reeimpresion especial
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private EvnCertificado obtenerFolioReimpresionEspecial(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		//obtener el folio de la solicitud
		SolicitudCertificado sc= (SolicitudCertificado) turno.getSolicitud();
		List solFolios= sc.getSolicitudFolios();
		SolicitudFolio sf= (SolicitudFolio) solFolios.get(0);
		Folio f= sf.getFolio();
		
		EvnCertificado evento= new EvnCertificado(usuarioAuriga, EvnCertificado.OBTENER_FOLIO_REIMPRESION_ESPECIAL);
		evento.setFolio(f);
		return evento;
	}


	/**
	 * @param request
	 * @return
	 */
	private Evento preservarInfo(HttpServletRequest request) {
		preservarInfoBasicaFolio(request);
		preservarInfoBasicaAnotacion(request);
		preservarInfoAntiguoSistemaAnotacion(request);
		preservarInfoBasicaCiudadano(request);
		return null;
	}



	private void preservarInfoBasicaFolio(HttpServletRequest request) {
		HttpSession session = request.getSession();
		//Se salva temporalmente la informacion sobre el folio. Esto no hace persistente el folio, ni cumple con el requerimiento de grabar de manera temporal
		if (request.getParameter(CFolio.FOLIO_NUM_RADICACION) != null) {
			session.setAttribute(CFolio.FOLIO_NUM_RADICACION,
				request.getParameter(CFolio.FOLIO_NUM_RADICACION));
		}

		if (request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO) != null) {
			session.setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO,
				request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO));
		}

		if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO) != null) {
			session.setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO,
				request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO));
		}

		if (request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC) != null) {
			session.setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC,
				request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC));
		}

		if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC) != null) {
			session.setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC,
				request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC));
		}

		if (request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA) != null) {
			session.setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA,
				request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA));
		}

		if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA) != null) {
			session.setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA,
				request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA));
		}

		if (request.getParameter(CFolio.FOLIO_ESTADO_FOLIO) != null) {
			session.setAttribute(CFolio.FOLIO_ESTADO_FOLIO,
				request.getParameter(CFolio.FOLIO_ESTADO_FOLIO));
		}

		if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
			session.setAttribute(CFolio.FOLIO_TIPO_PREDIO,
				request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
		}

		if (request.getParameter(CFolio.FOLIO_COMPLEMENTACION) != null) {
			session.setAttribute(CFolio.FOLIO_COMPLEMENTACION,
				request.getParameter(CFolio.FOLIO_COMPLEMENTACION));
		}

		if (request.getParameter(CFolio.FOLIO_LINDERO) != null) {
			session.setAttribute(CFolio.FOLIO_LINDERO,
				request.getParameter(CFolio.FOLIO_LINDERO));
		}

		if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
			session.setAttribute(CFolio.FOLIO_TIPO_PREDIO,
				request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
		}

		if (request.getParameter(CFolio.FOLIO_COD_CATASTRAL) != null) {
			session.setAttribute(CFolio.FOLIO_COD_CATASTRAL,
				request.getParameter(CFolio.FOLIO_COD_CATASTRAL));
		}

		if (request.getParameter(CFolio.FOLIO_EJE1) != null) {
			session.setAttribute(CFolio.FOLIO_EJE1,
				request.getParameter(CFolio.FOLIO_EJE1));
		}

		if (request.getParameter(CFolio.FOLIO_EJE2) != null) {
			session.setAttribute(CFolio.FOLIO_EJE2,
				request.getParameter(CFolio.FOLIO_EJE2));
		}

		if (request.getParameter(CFolio.FOLIO_VALOR1) != null) {
			session.setAttribute(CFolio.FOLIO_VALOR1,
				request.getParameter(CFolio.FOLIO_VALOR1));
		}

		if (request.getParameter(CFolio.FOLIO_VALOR2) != null) {
			session.setAttribute(CFolio.FOLIO_VALOR2,
				request.getParameter(CFolio.FOLIO_VALOR2));
		}

		if (request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR) != null) {
			session.setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR,
				request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR));
		}
	}

	private void preservarInfoBasicaAnotacion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NUM));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO,
				request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_TIPO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO,
				request.getParameter(
			CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_TIPO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA,
				request.getParameter(
			CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_OFICINA));
		}

		if (request.getParameter(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION) != null) {
			session.setAttribute(CFolio.ANOTACION_COMENTARIO_ESPECIFICACION,
				request.getParameter(
			CFolio.ANOTACION_COMENTARIO_ESPECIFICACION));
		}

		if (request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO) != null) {
			session.setAttribute(CFolio.ANOTACION_FECHA_DOCUMENTO,
				request.getParameter(CFolio.ANOTACION_FECHA_DOCUMENTO));
		}

		if (request.getParameter(CFolio.ANOTACION_FECHA_RADICACION) != null) {
			session.setAttribute(CFolio.ANOTACION_FECHA_RADICACION,
				request.getParameter(CFolio.ANOTACION_FECHA_RADICACION));
		}

		if (request.getParameter(CFolio.ANOTACION_NUM_RADICACION) != null) {
			session.setAttribute(CFolio.ANOTACION_NUM_RADICACION,
				request.getParameter(CFolio.ANOTACION_NUM_RADICACION));
		}

		if (request.getParameter(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION) != null) {
			session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION,
				request.getParameter(
			CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION));
		}

		if (request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO) != null) {
			session.setAttribute(CFolio.ANOTACION_TIPO_DOCUMENTO,
				request.getParameter(CFolio.ANOTACION_TIPO_DOCUMENTO));
		}

		if (request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION) != null) {
			session.setAttribute(CFolio.ANOTACION_VALOR_ESPECIFICACION,
				request.getParameter(CFolio.ANOTACION_VALOR_ESPECIFICACION));
		}

		if (request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO) != null) {
			session.setAttribute(CFolio.ANOTACION_NUM_DOCUMENTO,
				request.getParameter(CFolio.ANOTACION_NUM_DOCUMENTO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO,
				request.getParameter(
				CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_DEPTO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO,
				request.getParameter(
				CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_DEPTO));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC,
				request.getParameter(
				CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_MUNIC));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC,
				request.getParameter(
			CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_MUNIC));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA,
				request.getParameter(
			CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA));
		}

		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA) != null) {
			session.setAttribute(CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA,
				request.getParameter(
			CFolio.ANOTACION_OFICINA_DOCUMENTO_NOM_VEREDA));
		}


		if (request.getParameter(CFolio.ANOTACION_OFICINA_DOCUMENTO_ID_VEREDA) != null) {
			session.setAttribute("tipo_oficina",
				request.getParameter("tipo_oficina"));
			session.setAttribute("tipo_nom_oficina",
				request.getParameter("tipo_nom_oficina"));
			session.setAttribute("numero_oficina",
				request.getParameter("numero_oficina"));
			session.setAttribute("id_oficina",
				request.getParameter("id_oficina"));
		}

		if (request.getParameter(
		CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID) != null) {
			session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID,
				request.getParameter(
			CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_ID));
		}

		if (request.getParameter(
		CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM) != null) {
			session.setAttribute(CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM,
				request.getParameter(
			CFolio.ANOTACION_NAT_JURIDICA_ESPECIFICACION_NOM));
		}
	}

	private void preservarInfoBasicaCiudadano(HttpServletRequest request){
		HttpSession session = request.getSession();
		if (request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA) != null) {
			session.setAttribute(CFolio.ANOTACION_TIPO_ID_PERSONA,
				request.getParameter(CFolio.ANOTACION_TIPO_ID_PERSONA));
		}

		if (request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA) != null) {
			session.setAttribute(CFolio.ANOTACION_NUM_ID_PERSONA,
				request.getParameter(CFolio.ANOTACION_NUM_ID_PERSONA));
		}

		if (request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA) != null) {
			session.setAttribute(CFolio.ANOTACION_APELLIDO_1_PERSONA,
				request.getParameter(CFolio.ANOTACION_APELLIDO_1_PERSONA));
		}

		if (request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA) != null) {
			session.setAttribute(CFolio.ANOTACION_APELLIDO_2_PERSONA,
				request.getParameter(CFolio.ANOTACION_APELLIDO_2_PERSONA));
		}

		if (request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA) != null) {
			session.setAttribute(CFolio.ANOTACION_NOMBRES_PERSONA,
				request.getParameter(CFolio.ANOTACION_NOMBRES_PERSONA));
		}

		if (request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION) != null) {
			session.setAttribute(CFolio.ANOTACION_TIPO_INTER_ASOCIACION,
				request.getParameter(CFolio.ANOTACION_TIPO_INTER_ASOCIACION));
		}

		if (request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION) != null) {
			session.setAttribute(CFolio.ANOTACION_PORCENTAJE_ASOCIACION,
				request.getParameter(CFolio.ANOTACION_PORCENTAJE_ASOCIACION));
		}


	}

	private void preservarInfoAntiguoSistemaAnotacion(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS,
		request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_ANO_AS));

		session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS,
		request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_NUMERO_AS));

		session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS,
		request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_PAGINA_AS));

		session.setAttribute(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS,
		request.getParameter(CDatosAntiguoSistema.ANOTACION_LIBRO_TIPO_AS));

		session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS,
		request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_ANO_AS));

		session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS,
		request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_MUNICIPIO_AS));

		session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS,
		request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_NUMERO_AS));

		session.setAttribute(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS,
		request.getParameter(CDatosAntiguoSistema.ANOTACION_TOMO_PAGINA_AS));

	}

	/**
	 * Este metodo pone en la sesion la informacion que se puso en los campos,
	 * @param request HttpServletRequest
	 * @return Evento nulo ya que no se requiere que viaje hasta el negocio
	 */
	public void preservarInfoLiquidacion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String copias = request.getParameter(AWLiquidacionCertificado.COPIAS);
		String tipoCert = request.getParameter(AWLiquidacionCertificado.TIPO_CERTIFICADO);
		String tipoTarifa = request.getParameter(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS);

		String turnoAnterior = request.getParameter(AWLiquidacionCertificado.TURNO_ANTERIOR);

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

		if ((copias != null)) {
			session.setAttribute(AWLiquidacionCertificado.COPIAS, copias);
		}

		if ((tipoCert != null)) {
			session.setAttribute(AWLiquidacionCertificado.TIPO_CERTIFICADO, tipoCert);
		}

		if ((tipoTarifa != null)) {
			session.setAttribute(AWLiquidacionCertificado.TIPO_TARIFA_CERTIFICADOS, tipoTarifa);
		}

		if ((turnoAnterior != null)) {
			session.setAttribute(AWLiquidacionCertificado.TURNO_ANTERIOR, turnoAnterior);
		}

		request.getSession().setAttribute(CCiudadano.TIPODOC, tipoId);
		request.getSession().setAttribute(CCiudadano.IDCIUDADANO, numId);
		request.getSession().setAttribute(CCiudadano.APELLIDO1, apellido1);
		request.getSession().setAttribute(CCiudadano.NOMBRE, nombres);
		request.getSession().setAttribute(CCiudadano.APELLIDO2, apellido2);
		request.getSession().setAttribute(CCiudadano.TELEFONO, telefono);
	}


	/**
	 * Este método permite procesar cualquier evento de respuesta de la capa
	 * de negocio, en caso de recibir alguno.
	 * @param request la información del formulario
	 * @param eventoRespuesta el evento de respuesta de la capa de negocio, en caso
	 * de existir alguno
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

		if (evento instanceof EvnRespValidacionMatricula) {
			EvnRespValidacionMatricula respuesta = (EvnRespValidacionMatricula) evento;
			Integer num = (Integer) request.getSession().getAttribute(CFolio.NUM_MATRICULAS);
			String nombreCampo = CFolio.ID_MATRICULA + num.toString();
			nombreCampo = nombreCampo.toUpperCase();
			String matricula = respuesta.getMatricula();
			if ((matricula != null) && !matricula.equals("")) {
				request.getSession().setAttribute(nombreCampo, matricula);
				int val = num.intValue();
				val++;
				Integer nuevo = new Integer(val);
				request.getSession().setAttribute(AWLiquidacionCertificado.NUM_MATRICULAS, nuevo);
				String numeroAnota = respuesta.getNumeroAnotaciones();
				String mayorExtension = respuesta.getMayorExtension();
				request.getSession().setAttribute(AWLiquidacionCertificado.MAYOR_EXTENSION_FOLIO, mayorExtension);
				request.getSession().setAttribute(AWLiquidacionCertificado.NUM_ANOTACIONES_FOLIO, numeroAnota);
			}
		} else if (evento instanceof EvnRespCambioMatricula) {
			EvnRespCambioMatricula eventoMat = (EvnRespCambioMatricula) evento;
			String matricula = eventoMat.getMatricula();
			Folio folio = eventoMat.getFolio();
			Turno turno = eventoMat.getTurno();
			request.getSession().setAttribute(WebKeys.FOLIO, folio);
			request.getSession().setAttribute(FOLIO_MATRICULA_ID, matricula);
			request.getSession().setAttribute(WebKeys.TURNO, turno);
		} else {
			EvnRespCertificado respuesta = (EvnRespCertificado) evento;

			if (respuesta != null) {
				if (respuesta.getTipoEvento().equals(EvnRespCertificado.FOLIO_EXISTENTE)) {
					request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
				}

				if (respuesta.getTipoEvento().equals(EvnRespCertificado.ADMINISTRACION_RECIBOS)) {
					request.getSession().setAttribute(WebKeys.SECUENCIAL_RECIBO_ESTACION, "" + respuesta.getSecuencialRecibo());
					request.getSession().setAttribute(WebKeys.SECUENCIAL_RECIBO_CONSULTADO, "" + respuesta.getSecuencialRecibo());
					request.getSession().setAttribute(WebKeys.LISTA_TIPOS_NOTAS, respuesta.getListaNotasInformativas());
				}

				if (respuesta.getTipoEvento().equals(EvnRespCertificado.CONSULTA_FOLIO_COMPLEMENTACION)) {
					if(respuesta.getFolio()!=null && respuesta.getFolio().getComplementacion()!=null){
						request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getComplementacion());
						request.getSession().setAttribute(CFolio.FOLIO_ID_COMPLEMENTACION, respuesta.getFolio().getComplementacion().getIdComplementacion());
					}
				}

				if (respuesta.getTipoEvento().equals(EvnRespCertificado.CONSULTAR_PLANTILLA_PERTENENCIA)) {
					if(respuesta.getPayload()!=null){
						gov.sir.core.negocio.modelo.PlantillaPertenencia plantilla = (gov.sir.core.negocio.modelo.PlantillaPertenencia)respuesta.getPayload();
						String descPlantilla = " ";
						if(plantilla.getDescripcion()!=null && !plantilla.getDescripcion().equals("")){
							descPlantilla = plantilla.getDescripcion();
						}
						request.getSession().setAttribute(WebKeys.PLANTILLA, descPlantilla);
					}else{
						request.getSession().setAttribute(WebKeys.PLANTILLA, " ");
					}
					if(respuesta.getFolio()!=null){
						request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
					}
				}
				if (respuesta.getTipoEvento().equals(EvnRespCertificado.OBTENER_FOLIO_REIMPRESION_ESPECIAL)) {
					if(respuesta.getFolio()!=null){
						request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
					}
				}
				
				if (respuesta.getTipoEvento().equals(EvnRespCertificado.ACTUALIZAR_TRADICION_FOLIO)) {
					if(respuesta.getFolio()!=null){
						request.getSession().setAttribute(WebKeys.FOLIO, respuesta.getFolio());
					}
				}
				
			}
		}

                /* AHERRENO
                 14/05/2012
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
            Logger.getLogger(AWCertificado.class.getName()).log(Level.SEVERE, null, ex);
        }                
                DecimalFormat df = new DecimalFormat("0.000"); 
                double calculo = Double.valueOf(df.format(tiempoSession+((double)calTiempo/1000)).replace(',', '.'));
                System.out.println("El tiempo de la accion "+request.getParameter("ACCION")+" en milisegundos " +calTiempo );
                request.getSession().setAttribute("TIEMPO_TRANSACCION",calculo);
                Transaccion transaccionReg = new Transaccion();
                transaccionReg.setFechaTransaccion(fechaFin);
                transaccionReg.setAccionWeb(accion);
                transaccionReg.setTiempo(calTiempo);
                acciones.add(transaccionReg);
                request.getSession().setAttribute("LISTA_TRANSACCION",acciones);
	}


	/**
	 * Permite incrementar el secuencial de recibos.
	 * @param request
	 * @return EvnCertificado
	 */
	private EvnCertificado incrementarSecuencialRecibo (HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		String motivo = (String) request.getParameter(WebKeys.MOTIVO_INCREMENTO_SECUENCIAL);

		if (motivo == null ||  motivo.equals(""))
		{
			throw new AccionWebException("No ingresó un motivo para el incremento del secuencial del recibo");
		}


		//Obtener atributos del request.
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Circulo circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		String idCirculo = circulo.getIdCirculo();
		gov.sir.core.negocio.modelo.Usuario usuario = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);
		Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);

		EvnCertificado eventoCert = new EvnCertificado(usuarioAuriga, EvnCertificado.INCREMENTAR_SECUENCIAL_RECIBO, turno, fase, estacion, EvnCertificado.INCREMENTAR_SECUENCIAL_RECIBO, usuario);
		eventoCert.setMotivo(motivo);
		return eventoCert;

	}
	
	/**
	 * Acepta la ampliación de la tradición de un folio
	 * Pablo Quintana
	 */
	private Evento ampliacionTradicionConfirmar(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Folio folio =(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		Folio folioDef=(Folio)request.getSession().getAttribute(CFolio.FOLIO_DEF);
		String complementacionDef = "";
		String complementacion=request.getParameter(CFolio.COMPLEMENTACION_AMPLIACION);
		if(folioDef!=null && folioDef.getComplementacion()!=null && folioDef.getComplementacion().getComplementacion()!=null){
			complementacionDef = folioDef.getComplementacion().getComplementacion();
		}
		if(complementacion==null || complementacion.trim().equals("")){
			throw new ParametroInvalidoException("No se puede avanzar el turno, no hay ampliacion de la tradicion");
		}
		complementacion = complementacion.replaceAll("\n", "");
		complementacion = complementacion.replaceAll(AMPLIACION_DE_TRADICION, "");
		if(complementacion.trim().equals("")){
			throw new ParametroInvalidoException("No se puede avanzar el turno, no hay ampliacion de la tradicion");
		}
		if(folio!=null){
			if(folio.getComplementacion()!=null)
				folio.getComplementacion().setComplementacion((complementacionDef+"\n"+AMPLIACION_DE_TRADICION+"\n"+complementacion).trim());
			else{
				Complementacion aux = new Complementacion();
				aux.setComplementacion(AMPLIACION_DE_TRADICION+"\n"+complementacion);
				folio.setComplementacion(aux);
			}
		}
		
		session.removeAttribute(CFolio.COMPLEMENTACION_AMPLIACION);
		
		if(!this.huboCambiosComplementacion(folio, folioDef)){
			throw new ParametroInvalidoException("No se puede avanzar el turno, no hay ampliacion de la tradicion");
		}
		EvnCertificado evento= new EvnCertificado(usuarioAriga,EvnCertificado.AMPLIACION_TRADICION_CONFIRMAR,turno,fase,estacion,CRespuesta.AMPLIACION_TRADICION,usuarioSIR,rol,proceso);
		evento.setUsuarioNec(usuarioSIR);
		evento.setFolio(folio);
                 /**
                * @Author Carlos Torres
                * @Mantis 13176
                */
		gov.sir.core.negocio.modelo.Usuario usuarioNeg = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
                gov.sir.core.negocio.modelo.InfoUsuario infoUsuario = new gov.sir.core.negocio.modelo.InfoUsuario(request.getSession().getId(), request.getRemoteHost(), usuarioNeg.getUsername(),String.valueOf(usuarioNeg.getIdUsuario()));
                infoUsuario.setLogonTime(new Date(request.getSession().getCreationTime()));
                evento.setInfoUsuario(infoUsuario);
		return evento;
		
	}
	
	private Evento ampliacionTradicionGuardar(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Folio folio =(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		Folio folioDef=(Folio)request.getSession().getAttribute(CFolio.FOLIO_DEF);
		String complementacionDef = "";
		String complementacion=request.getParameter(CFolio.COMPLEMENTACION_AMPLIACION);
		if(folioDef!=null && folioDef.getComplementacion()!=null && folioDef.getComplementacion().getComplementacion()!=null){
			complementacionDef = folioDef.getComplementacion().getComplementacion();
		}
		if(complementacion==null || complementacion.trim().equals("")){
			throw new ParametroInvalidoException("No hay complementacion nueva para guardar");
		}
		complementacion = complementacion.replaceAll("\n", "");
		complementacion = complementacion.replaceAll(AMPLIACION_DE_TRADICION, "");
		if(complementacion.trim().equals("")){
			throw new ParametroInvalidoException("No hay complementacion nueva para guardar");
		}
		if(folio!=null){
			if(folio.getComplementacion()!=null)
				folio.getComplementacion().setComplementacion((complementacionDef+"\n"+AMPLIACION_DE_TRADICION+"\n"+complementacion).trim());
			else{
				Complementacion aux = new Complementacion();
				aux.setComplementacion(AMPLIACION_DE_TRADICION+"\n"+complementacion);
				folio.setComplementacion(aux);
			}
		}
		EvnCertificado evento= new EvnCertificado(usuarioAriga,EvnCertificado.AMPLIACION_TRADICION_GUARDAR,turno,fase,estacion,CRespuesta.AMPLIACION_TRADICION,usuarioSIR,rol,proceso);
		evento.setUsuarioNec(usuarioSIR);
		evento.setFolio(folio);
		return evento;
		
	}
	
	private Evento ampliacionTradicionNegar(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		org.auriga.core.modelo.transferObjects.Usuario usuarioAriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (Usuario) session.getAttribute(WebKeys.USUARIO);
		Fase fase = (Fase) session.getAttribute(WebKeys.FASE);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Estacion estacion = (Estacion) session.getAttribute(WebKeys.ESTACION);
		Rol rol = (Rol) session.getAttribute(WebKeys.ROL);
		Proceso proceso = (Proceso) session.getAttribute(WebKeys.PROCESO);
		Folio folio =(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		
		session.removeAttribute(CFolio.COMPLEMENTACION_AMPLIACION);
		EvnCertificado evento= new EvnCertificado(usuarioAriga, EvnCertificado.AMPLIACION_TRADICION_NEGAR,turno,fase,estacion,CRespuesta.NEGAR,usuarioSIR,rol,proceso);
		evento.setUsuarioNec(usuarioSIR);
		evento.setFolio(folio);
		return evento;
	}
	
	
	private boolean huboCambiosComplementacion(Folio folio, Folio folioDef){
		if(folio!=null && folioDef!=null){
			if(folio.getComplementacion()!=null && folio.getComplementacion().getComplementacion()!=null){
				if(folioDef.getComplementacion()==null || folioDef.getComplementacion().getComplementacion() == null)
					return true;
				if(!folio.getComplementacion().getComplementacion().equals(folioDef.getComplementacion().getComplementacion()))
					return true;
			}
		}
		return false;
	}
	
}
