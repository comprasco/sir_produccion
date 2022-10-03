package gov.sir.core.eventos.certificado;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Turno;

/**
 * Envio de solicitudes con respecto a certificados, a la capa de negocio.
 * Cada constante explica a que fase y la accion que es.
 * @author mmunoz
 */
public class EvnCertificado extends EvnSIR {

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_CREACION_FOLIO_REANALIZAR = "TIPO_ANT_SIST_CREACION_FOLIO_REANALIZAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ENTREGA_OK = "TIPO_ENTREGA_OK";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ENTREGA_REIMPRESION_ESPECIAL = "TIPO_ENTREGA_REIMPRESION_ESPECIAL";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ENTREGA_REIMPRESION_CORRIENTE = "TIPO_ENTREGA_REIMPRESION_CORRIENTE";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_MAYOR_EXT_CONFIRMAR = "TIPO_MAYOR_EXT_CONFIRMAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_MAYOR_EXT_NEGAR = "TIPO_MAYOR_EXT_NEGAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_IMPRESION_CONFIRMAR = "TIPO_IMPRESION_CONFIRMAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_REIMP_ESP_CONFIRMAR = "TIPO_REIMP_ESP_CONFIRMAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_REIMP_ESP_NEGAR = "TIPO_REIMP_ESP_NEGAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_REV_INI_CONFIRMAR = "TIPO_ANT_SIST_REV_INI_CONFIRMAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_REV_INI_NEGAR = "TIPO_ANT_SIST_REV_INI_NEGAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_ANEXAR_INFORMACION_EXITO = "TIPO_ANT_SIST_ANEXAR_INFORMACION_EXITO";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_ANEXAR_INFORMACION_FRACASO = "TIPO_ANT_SIST_ANEXAR_INFORMACION_FRACASO";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_CREACION_FOLIO_CREADO = "TIPO_ANT_SIST_CREACION_FOLIO_CREADO";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_CREACION_FOLIO_EXISTE = "TIPO_ANT_SIST_CREACION_FOLIO_EXISTE";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_CREACION_FOLIO_MAS_DOCS = "TIPO_ANT_SIST_CREACION_FOLIO_MAS_DOCS";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_CREACION_FOLIO_HACER_DEFINITIVO_FOLIO = "TIPO_ANT_SIST_CREACION_FOLIO_HACER_DEFINITIVO_FOLIO";

	/**Esta constante define el tipo evento que se envia a la accion de negocio, cuando se quiere devolver un turno de creación de folio a digitar hoja de ruta */
	public static final String TIPO_ANT_SIST_CREACION_FOLIO_HACER_DEFINITIVO_REANALIZAR = "TIPO_ANT_SIST_CREACION_FOLIO_HACER_DEFINITIVO_REANALIZAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_CREACION_FOLIO_RECHAZADO = "TIPO_ANT_SIST_CREACION_FOLIO_RECHAZADO";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String ANT_SIST_CREACION_FOLIO_REANALIZAR = "ANT_SIST_CREACION_FOLIO_REANALIZAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_MICROFILM_CONFIRMAR = "TIPO_ANT_SIST_MICROFILM_CONFIRMAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_MICROFILM_NEGAR = "TIPO_ANT_SIST_MICROFILM_NEGAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_NOTIFICAR_MAS_DOCS_CONFIRMAR = "TIPO_NOTIFICAR_MAS_DOCS_CONFIRMAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_REVISION_CREADO = "TIPO_ANT_SIST_REVISION_CREADO";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_GRABAR_TEMPORAL = "TIPO_ANT_SIST_GRABAR_TEMPORAL";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_REVISION_EXISTE = "TIPO_ANT_SIST_REVISION_EXISTE";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_REVISION_MAS_DOCS = "TIPO_ANT_SIST_REVISION_MAS_DOCS";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_REVISION_RECHAZADO = "TIPO_ANT_SIST_REVISION_RECHAZADO";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_ANT_SIST_REVISION_REANALIZAR = "TIPO_ANT_SIST_REVISION_REANALIZAR";

	/**Esta constante define el tipo evento que se envia a la accion de negocio */
	public static final String TIPO_OFICIO_PERTENENCIA_CONFIRMAR = "TIPO_OFICIO_PERTENENCIA_CONFIRMAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ENTREGA_OK = "OK";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ENTREGA_REIMPRESION_ESPECIAL = "REIMPRESION_ESPECIAL";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ENTREGA_REIMPRESION_CORRIENTE = "REIMPRESION_CORRIENTE";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String MAYOR_EXT_CONFIRMAR = "CONFIRMAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String MAYOR_EXT_NEGAR = "NEGAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String IMPRESION_CONFIRMAR = "CONFIRMAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String REIMP_ESP_CONFIRMAR = "CONFIRMAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String REIMP_ESP_NEGAR = "NEGAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_REV_INI_CONFIRMAR = "CONFIRMAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_REV_INI_NEGAR = "NEGAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_ANEXAR_INFO_EXITO = "EXITO";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_ANEXAR_INFO_FRACASO = "FRACASO";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_CREACION_FOLIO_CREADO = "CREADO";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_CREACION_FOLIO_EXISTE = "EXISTE";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_CREACION_FOLIO_MAS_DOCS = "MAS_DOCS";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_CREACION_FOLIO_RECHAZADO = "RECHAZADO";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_CREACION_FOLIO_REANALIZA = "REANALIZAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_MICROFILM_CONFIRMAR = "CONFIRMAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_MICROFILM_NEGAR = "NEGAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String NOTIFICAR_MAS_DOCS_CONFIRMAR = "CONFIRMAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_REVISION_EXISTE = "EXISTE";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_REVISION_MAS_DOCS = "MAS_DOCS";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_REVISION_RECHAZADO = "RECHAZADO";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_REVISION_CREADO = "CREADO";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String ANT_SIST_REVISION_REANALIZAR = "REANALIZAR";

	/**Esta constante define la respuesta que se le da al workflow*/
	public static final String OFICIO_PERTENENCIA_CONFIRMAR = "CONFIRMAR";

	/**
	* Esta constante se utiliza para consultar el secuencial de recibo de la estación.
	*/
	public static final String CONSULTAR_SECUENCIAL_RECIBO = "CONSULTAR_SECUENCIAL_RECIBO";

	/**
	* Esta constante se utiliza para incrementar el secuencial de recibo de la estación.
	*/
	public static final String INCREMENTAR_SECUENCIAL_RECIBO = "INCREMENTAR_SECUENCIAL_RECIBO";

	/**Esta constante define el tipo evento que se envia a la accion de negocio, cuando imprimió la huja de ruta. */
	public static final String IMPRIMIR_HOJA_RUTA = "IMPRIMIR_HOJA_RUTA";

	/**Esta constante define el tipo evento que se envia a la accion de negocio, cuando se quiere consultar una plantilla
	 * para el oficio de pertenencia. */
	public static final String CONSULTAR_PLANTILLA_PERTENENCIA= "CONSULTAR_PLANTILLA_PERTENENCIA";

	/**Esta constante define el tipo evento que se envia a la accion de negocio, cuando se quiere consultar un folio para copiar su complementación. */
	public static final String CONSULTA_FOLIO_COMPLEMENTACION = "CONSULTA_FOLIO_COMPLEMENTACION";
	
	/**
	* Esta constante se utiliza para obtencion del folio en reimpresion especial.
	*/
	public static final String OBTENER_FOLIO_REIMPRESION_ESPECIAL= "OBTENER_FOLIO_REIMPRESION_ESPECIAL";
	
	
	public static final String 	VALIDAR_MATRICULA_MIG = "VALIDAR_MATRICULA_MIG";

	/**Esta constante define la respuesta que se le da al workflow que se
	 * guarda en el evento*/
	private String respuestaWF = "";

	/**Esta constante guarda el oficio de pertenencia que se va a guardar*/
	private String oficioPertenencia = "";

	/**Esta constante define el id de la matricula que se asosia un turno de
	 * antiguo sistema o pertenencia*/
	private String idMatricula = "";

	/**Esta constante define el turno con el cual se esta trabajado*/
	private Turno turno = null;

	/**Esta constante define la fase en la cual se esta trabajando*/
	private Fase fase = null;

	/**Estacion donde el usuario esta trabajando*/
	private Estacion estacion = null;

	/** Identificador unico de usuario*/
	private String UID;

	/**Informacion del usuario que quiere trabajar sobre el workflow*/
	private gov.sir.core.negocio.modelo.Usuario usuarioNec;

	/** **/
	private Folio folio;

	/**
	 * Almacena el rol del usuario actual
	 */
	private Rol rol;

	/**
	 * Almacena el proceso actual
	 */
	private Proceso proceso;

	/**
	* Almacena el motivo por el cual se debe incrementar el secuencial del recibo.
	*/
	private String motivo;

	private String pathFirmas;
	
	private String matricula;
	
	public static final String 	AMPLIACION_TRADICION_CONFIRMAR = "AMPLIACION_TRADICION_CONFIRMAR";
	public static final String ACTUALIZAR_TRADICION_FOLIO="ACTUALIZAR_TRADICION_FOLIO";
	public static final String CER_AMPLIACION_TRADICION="CER_AMPLIACION_TRADICION";
	public static final String AMPLIACION_TRADICION_GUARDAR = "AMPLIACION_TRADICION_GUARDAR";
	public static final String AMPLIACION_TRADICION_NEGAR = "AMPLIACION_TRADICION_NEGAR";

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere
	 * agregar un oficio de pertenencia al certificado.
	 * @param usuarioAuriga. org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String con el tipo de evento que se quiere crear.
	 * @param turno gov.sir.core.negocio.modelo.Turno
	 * @param fase gov.sir.core.negocio.modelo.Fase
	 * @param respuestaWF String con la respuesta que se le va a dar al WorkFlow
	 * @param oficioPertenencia String El oficio de pertenencia que se va a agregar.
	 * @param usuario gov.sir.core.negocio.modelo.Usuario usuarioNec
	 */
	public EvnCertificado(Usuario usuarioAuriga, String tipoEvento, Turno turno, Fase fase, String respuestaWF, String oficioPertenencia, gov.sir.core.negocio.modelo.Usuario usuario) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respuestaWF;
		this.oficioPertenencia = oficioPertenencia;
		this.usuarioNec = usuario;
	}

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere
	 * avanzar o imprimi un certificado.
	 * @param usuarioAuriga. org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String con el tipo de evento que se quiere crear.
	 * @param turno gov.sir.core.negocio.modelo.Turno
	 * @param fase gov.sir.core.negocio.modelo.Fase
	 * @param respuestaWF String con la respuesta que se le va a dar al WorkFlow
	 * @param usuario gov.sir.core.negocio.modelo.Usuario usuarioNec
	 * @param estacion org.auriga.core.modelo.transferObjects.Estacion
	 */
	public EvnCertificado(Usuario usuarioAuriga, String tipoEvento, Turno turno, Fase fase, Estacion estacion, String respuestaWF, gov.sir.core.negocio.modelo.Usuario usuario, Rol rol, Proceso proceso) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respuestaWF;
		this.usuarioNec = usuario;
		this.estacion = estacion;
		this.rol = rol;
		this.proceso = proceso;
	}

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere
	 * avanzar o imprimi un certificado.
	 * @param usuarioAuriga. org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String con el tipo de evento que se quiere crear.
	 * @param turno gov.sir.core.negocio.modelo.Turno
	 * @param fase gov.sir.core.negocio.modelo.Fase
	 * @param respuestaWF String con la respuesta que se le va a dar al WorkFlow
	 * @param usuario gov.sir.core.negocio.modelo.Usuario usuarioNec
	 * @param estacion org.auriga.core.modelo.transferObjects.Estacion
	 */
	public EvnCertificado(Usuario usuarioAuriga, String tipoEvento, Turno turno, Fase fase, Estacion estacion, String respuestaWF, gov.sir.core.negocio.modelo.Usuario usuario) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respuestaWF;
		this.usuarioNec = usuario;
		this.estacion = estacion;
		this.rol = null;
	}

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere
	 * avanzar o imprimi un certificado.
	 * @param usuarioAuriga. org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String con el tipo de evento que se quiere crear.
	 * @param turno gov.sir.core.negocio.modelo.Turno
	 * @param fase gov.sir.core.negocio.modelo.Fase
	 * @param respuestaWF String con la respuesta que se le va a dar al WorkFlow
	 * @param usuario gov.sir.core.negocio.modelo.Usuario usuarioNec
	 * @param estacion org.auriga.core.modelo.transferObjects.Estacion
	 */
	public EvnCertificado(Usuario usuarioAuriga, String tipoEvento, Turno turno, Fase fase, Estacion estacion, String respuestaWF, gov.sir.core.negocio.modelo.Usuario usuario, String oficioPertenencia) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respuestaWF;
		this.usuarioNec = usuario;
		this.estacion = estacion;
		this.oficioPertenencia = oficioPertenencia;
	}

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere
	 * avanzar o imprimi un certificado.
	 * @param usuarioAuriga. org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String con el tipo de evento que se quiere crear.
	 * @param turno gov.sir.core.negocio.modelo.Turno
	 * @param fase gov.sir.core.negocio.modelo.Fase
	 * @param respuestaWF String con la respuesta que se le va a dar al WorkFlow
	 * @param usuario gov.sir.core.negocio.modelo.Usuario usuarioNec
	 * @param estacion org.auriga.core.modelo.transferObjects.Estacion
	 */
	public EvnCertificado(Usuario usuarioAuriga, String tipoEvento, Turno turno, Fase fase, gov.sir.core.negocio.modelo.Usuario usuario, String respuestaWF, String matricula) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.fase = fase;
		this.respuestaWF = respuestaWF;
		this.usuarioNec = usuario;
		this.idMatricula = matricula;
	}

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere
	 * agregar un Turno Anterior a una solicitud de certificado.
	 * @param usuarioAuriga. org.auriga.core.modelo.transferObjects.Usuario
	 * @param tipoEvento String con el tipo de evento que se quiere crear.
	 * @param turno gov.sir.core.negocio.modelo.Turno
	 * @param usuario gov.sir.core.negocio.modelo.Usuario usuarioNec
	 */
	public EvnCertificado(Usuario usuarioAuriga, String tipoEvento, Turno turno, gov.sir.core.negocio.modelo.Usuario usuario) {
		super(usuarioAuriga, tipoEvento);
		this.turno = turno;
		this.usuarioNec = usuario;
	}
	

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere
	 * imprimir la hoja de ruta.
	 */
	public EvnCertificado(Usuario usuarioAuriga, gov.sir.core.negocio.modelo.Usuario usuario, String tipoEvento, Turno turno, Estacion estacion, Folio folio, String UID) {
		super(usuarioAuriga, tipoEvento);
		this.usuarioNec = usuario;
		this.turno = turno;
		this.estacion = estacion;
		this.folio = folio;
		this.UID = UID;
	}

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere consultar un folio para copiar su complementación.
	 * @param usuarioAuriga
	 * @param usuarioSIR
	 * @param string
	 * @param folio
	 */
	public EvnCertificado(Usuario usuarioAuriga, gov.sir.core.negocio.modelo.Usuario usuarioSIR, String tipoEvento, Folio folio) {
		super(usuarioAuriga, tipoEvento);
		this.usuarioNec = usuarioSIR;
		this.folio = folio;
	}

	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere consultar una plantilla para generar el oficio de pertenencia.
	 * @param usuarioAuriga
	 * @param usuarioSIR
	 * @param string
	 * @param folio
	 */
	public EvnCertificado(Usuario usuarioAuriga, gov.sir.core.negocio.modelo.Usuario usuarioSIR, String tipoEvento, String respuestaWF) {
		super(usuarioAuriga, tipoEvento);
		this.usuarioNec = usuarioSIR;
		this.respuestaWF = respuestaWF;
	}
	
	/**
	 * Constructor con parametros de EvnCertificado, este constructor es utilizado cuando se quiere consultar una plantilla para generar el oficio de pertenencia.
	 * @param usuarioAuriga
	 * @param usuarioSIR
	 * @param string
	 * @param folio
	 */
	public EvnCertificado(Usuario usuarioAuriga, String tipoEvento) {
		super(usuarioAuriga, tipoEvento);
	}




	/**
	 * Retorna la fase que viaja en el evento.
	* @return gov.sir.core.negocio.modelo.Fase
	*/
	public Fase getFase() {
		return fase;
	}

	/**
	 * Retorna el turno que viaja en el evento
	 * @return gov.sir.core.negocio.modelo.Turno
	 */
	public Turno getTurno() {
		return turno;
	}

	/**
	 * Retorna la respuesta del WorkFlow que viaja en el evento
	 * @return String
	 */
	public String getRespuestaWF() {
		return respuestaWF;
	}

	/**
	 * Retorna el oficio de pertenencia que viaja en el evento.
	 * @return String
	 */
	public String getOficioPertenencia() {
		return oficioPertenencia;
	}

	/**
	 * Retorna el identificador de la matricula que viaja en el evento.
	 * @return String
	 */
	public String getIdMatricula() {
		return idMatricula;
	}

	/**
	 * Retorna la estacion que viaja en el evento
	 * @return org.auriga.core.modelo.transferObjects.Estacion
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * Retorna el usuario SIR que viaja en el negocio
	 * @return gov.sir.core.negocio.modelo.Usuario
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioNec() {
		return usuarioNec;
	}

	/**
	 * @return
	 */
	public Folio getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 */
	public void setFolio(Folio folio) {
		this.folio = folio;
	}

	/**
	 * @return
	 */
	public String getUID() {
		return UID;
	}

	/**
	 * @param string
	 */
	public void setUID(String string) {
		UID = string;
	}

	/**
	 * @return
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @return
	 */
	public Proceso getProceso() {
		return proceso;
	}

	/**
	 * @return
	 */
	public String getPathFirmas() {
		return pathFirmas;
	}

	/**
	 * @param string
	 */
	public void setPathFirmas(String string) {
		pathFirmas = string;
	}

	/**
	 * @return
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * @param string
	 */
	public void setMotivo(String string) {
		motivo = string;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void setUsuarioNec(gov.sir.core.negocio.modelo.Usuario usuarioNec) {
		this.usuarioNec = usuarioNec;
	}

	
}
