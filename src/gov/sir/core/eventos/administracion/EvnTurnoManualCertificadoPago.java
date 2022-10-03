/*
 * Created on 22-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.eventos.administracion;


import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.core.modelo.transferObjects.Rol;
import org.auriga.core.modelo.transferObjects.Usuario;

import gov.sir.core.eventos.comun.EvnSIR;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EvnTurnoManualCertificadoPago extends EvnSIR {
	
	/**
	 * Solicitud para validar el pago realizado a una solicitud
	 */
	public static final String VALIDAR = "VALIDAR";

	/**
	 * Solicitud de Procesar una solicitud
	 */
	public static final String PROCESAR = "PROCESAR";

	/**
	 * Solicitud de Procesar una solicitud
	 */
	public static final String VALIDAR_PROCESAR = "VALIDAR_PROCESAR";


	public static final String VERIFICAR_APLICACION = "VERIFICAR_APLICACION";

	/**
	 * Pago realizado por el ciudadano
	 */
	private Pago pago;


	/**
	 *
	 */
	private Turno turno;
	private Solicitud solicitud;


	/** Proceso en el cual esta el usuario*/
	private Proceso proceso;

	/** Estacion en el cual esta el usuario*/
	private Estacion estacion;

	/** Identificador unico de usuario*/
	private String UID;

	/** Identificador del Circulo Registral*/
	private String idCirculo;

	/** Circulo Registral*/
	private Circulo circulo;

	/** Impresora seleccionada */
	private String impresora;

	/** Impresora seleccionada */
	private String sessionId;

	/** Variable para guardar el usuario del sistema*/
	private gov.sir.core.negocio.modelo.Usuario usuarioSIR;

	/** Variable para guardar el rol con el que esta trabajando el usuario*/
	private Rol rol;

	/** Variable para guardar las validaciones que se realizaron a las matriculas cuando se realiza
	 * una solicitud masiva de certificados*/
	private Hashtable validacionesMasivos;

	/** Indica si el pago realizado es de registro */
	private boolean esPagoRegistro = false;

	/** Indica si el pago realizado es de fotocopias */
	private boolean esPagoFotocopias = false;

	/** Indica si el pago realizado es de fotocopias */
	private boolean esPagoCorreccion= false;

	/** Indica si el pago realizado es de certificados manuales */
	private boolean esPagoTurnoManualCertificado = false;

	/** Indica si el pago realizado es de consulta simple */
	private boolean asignarEstacion = false;

	private AplicacionPago aplicacionPago;

	/**
	* Lista que almacena las notas informativas declaradas antes de la
	* creación del turno.
	*/
	private List listaNotasSinTurno;

	private boolean omitirRecibo;

	/** 
	 * Cuando el turno es manual, se necesita del ID del turno, 
	 * y el ID del recibo
	 */
	private String anio;
	private String idTurno;
	private String idRecibo;
	private Date fechaInicial;
	
	private String respuestaCalificacion;

	/**
	 * Constructor por paramentros.
	 * @param usuario org.auriga.core.modelo.transferObjects.Usuario
	 * @param pago Pago
	 * @param proceso Proceso
	 * @param estacion Estacion
	 * @param tipoEvento String
	 * @param usuarioSIR
	 */
	public EvnTurnoManualCertificadoPago(Usuario usuario, Pago pago, Proceso proceso,
		Estacion estacion, String tipoEvento,
		gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		super(usuario, tipoEvento);
		this.pago = pago;
		this.proceso = proceso;
		this.estacion = estacion;
		this.usuarioSIR = usuarioSIR;
	}

	/**
	* Asocia al evento la lista de notas informativas.
	* @param notasInformativas Lista que va a ser asociada con el atributo listaNotasSinTurno.
	* <p>Contiene las notas informativas definidas antes de la creación de un turno.
	*/
	public void setListaNotasSinTurno(List notasInformativas) {
		this.listaNotasSinTurno = notasInformativas;
	}

	/**
	* Retorna la lista de notas informativas asociadas con el evento.
	* @return Lista con las notas informativas
	*/
	public List getListaNotasSinTurno() {
		return this.listaNotasSinTurno;
	}

	/**
	 * Obtener el atributo circulo
	 *
	 * @return Retorna el atributo circulo.
	 */
	public Circulo getCirculo() {
		return circulo;
	}

	/**
	 * Actualizar el valor del atributo circulo
	 * @param circulo El nuevo valor del atributo circulo.
	 */
	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	/**
	 * Obtener el atributo esPagoRegistro
	 *
	 * @return Retorna el atributo esPagoRegistro.
	 */
	public boolean isEsPagoRegistro() {
		return esPagoRegistro;
	}

	/**
	 * Obtener el atributo esPagoConsultaSimple
	 *
	 * @return Retorna el atributo esPagoConsultaSimple.
	 */
	public boolean isAsignarEstacion() {
		return asignarEstacion;
	}

	/**
	 * Obtener el atributo esPagoTurnoManualCertificado
	 * 
	 * @return Retorna el atributo esPagoTurnoManualCertificado
	 */
	public boolean isEsPagoTurnoManualCertificado() {
		return esPagoTurnoManualCertificado;
	}

	/**
	 * Actualizar el valor del atributo esPagoRegistro
	 * @param esPagoRegistro El nuevo valor del atributo esPagoRegistro.
	 */
	public void setEsPagoRegistro(boolean esPagoRegistro) {
		this.esPagoRegistro = esPagoRegistro;
	}

	/**
	 * Actualizar el valor del atributo esPagoConsultaSimple
	 * @param esPagoRegistro El nuevo valor del atributo esPagoConsultaSimple.
	 */
	public void setAsignarEstacion(boolean asignarEstacion) {
		this.asignarEstacion = asignarEstacion;
	}

	/**
	 * Actualizar el valor del atributo esPagoTurnoManualCertificado
	 * @param esPagoTurnoManualCertificado El nuevo valor del atributo esPagoTurnoManualCertificado 
	 */
	public void setEsPagoTurnoManualCertificado(boolean esPagoTurnoManualCertificado) {
		this.esPagoTurnoManualCertificado = esPagoTurnoManualCertificado;
	}

	/**
	 * Obtener el atributo estacion
	 *
	 * @return Retorna el atributo estacion.
	 */
	public Estacion getEstacion() {
		return estacion;
	}

	/**
	 * Actualizar el valor del atributo estacion
	 * @param estacion El nuevo valor del atributo estacion.
	 */
	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

	/**
	 * Obtener el atributo idCirculo
	 *
	 * @return Retorna el atributo idCirculo.
	 */
	public String getIdCirculo() {
		return idCirculo;
	}

	/**
	 * Actualizar el valor del atributo idCirculo
	 * @param idCirculo El nuevo valor del atributo idCirculo.
	 */
	public void setIdCirculo(String idCirculo) {
		this.idCirculo = idCirculo;
	}

	/**
	 * Obtener el atributo impresora
	 *
	 * @return Retorna el atributo impresora.
	 */
	public String getImpresora() {
		return impresora;
	}

	/**
	 * Actualizar el valor del atributo impresora
	 * @param impresora El nuevo valor del atributo impresora.
	 */
	public void setImpresora(String impresora) {
		this.impresora = impresora;
	}

	/**
	 * Obtener el atributo pago
	 *
	 * @return Retorna el atributo pago.
	 */
	public Pago getPago() {
		return pago;
	}

	/**
	 * Actualizar el valor del atributo pago
	 * @param pago El nuevo valor del atributo pago.
	 */
	public void setPago(Pago pago) {
		this.pago = pago;
	}

	/**
	 * Obtener el atributo proceso
	 *
	 * @return Retorna el atributo proceso.
	 */
	public Proceso getProceso() {
		return proceso;
	}

	/**
	 * Actualizar el valor del atributo proceso
	 * @param proceso El nuevo valor del atributo proceso.
	 */
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	/**
	 * Obtener el atributo rol
	 *
	 * @return Retorna el atributo rol.
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * Actualizar el valor del atributo rol
	 * @param rol El nuevo valor del atributo rol.
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * Obtener el atributo uID
	 *
	 * @return Retorna el atributo uID.
	 */
	public String getUID() {
		return UID;
	}

	/**
	 * Actualizar el valor del atributo uID
	 * @param uid El nuevo valor del atributo uID.
	 */
	public void setUID(String uid) {
		UID = uid;
	}

	/**
	 * Obtener el atributo usuarioSIR
	 *
	 * @return Retorna el atributo usuarioSIR.
	 */
	public gov.sir.core.negocio.modelo.Usuario getUsuarioSIR() {
		return usuarioSIR;
	}

	/**
	 * Actualizar el valor del atributo usuarioSIR
	 * @param usuarioSIR El nuevo valor del atributo usuarioSIR.
	 */
	public void setUsuarioSIR(gov.sir.core.negocio.modelo.Usuario usuarioSIR) {
		this.usuarioSIR = usuarioSIR;
	}

	/**
	 * @param usuario
	 * @param aplicacionPago
	 */
	public EvnTurnoManualCertificadoPago(Usuario usuario, AplicacionPago aplicacionPago) {
		super(usuario,EvnTurnoManualCertificadoPago.VERIFICAR_APLICACION);
		this.aplicacionPago = aplicacionPago;
	}



	/**
	 * Obtener el atributo validacionesMasivos
	 *
	 * @return Retorna el atributo validacionesMasivos.
	 */
	public Hashtable getValidacionesMasivos() {
		return validacionesMasivos;
	}

	public boolean isEsPagoFotocopias() {
		return esPagoFotocopias;
	}

	public Fase getFase() {
		return fase;
	}

	public String getRespuestaWF() {
		return respuestaWF;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Turno getTurno() {
		return turno;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	protected Fase fase;
	protected String respuestaWF = "";



	/**
	 * Actualizar el valor del atributo validacionesMasivos
	 * @param validacionesMasivos El nuevo valor del atributo validacionesMasivos.
	 */
	public void setValidacionesMasivos(Hashtable validacionesMasivos) {
		this.validacionesMasivos = validacionesMasivos;
	}

  	public void setEsPagoFotocopias(boolean esPagoFotocopias) {
		this.esPagoFotocopias = esPagoFotocopias;
  	}

  	public void setFase(Fase fase) {
		this.fase = fase;
  	}

  	public void setRespuestaWF(String respuestaWF) {
		this.respuestaWF = respuestaWF;
  	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public boolean isEsPagoCorreccion() {
		return esPagoCorreccion;
	}

	public void setEsPagoCorreccion(boolean esPagoCorreccion) {
		this.esPagoCorreccion = esPagoCorreccion;
	}
	/**
	 * @return
	 */
	public AplicacionPago getAplicacionPago() {
		return aplicacionPago;
	}


	/**
	 * @return
	 */
	public boolean isOmitirRecibo() {
		return omitirRecibo;
	}

	/**
	 * @param b
	 */
	public void setOmitirRecibo(boolean b) {
		omitirRecibo = b;
	}

	/**
	 * Obtiene el ID del turno asociado a este pago
	 * @return ID del turno asociado a este pago
	 */
	public String getIdTurno() {
		return idTurno;
	}

	/**
	 * Establece el ID del turno asociado a este pago
	 * @param idTurno ID del turno asociado a este pago
	 */
	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}

	/**
	 * Obtiene el ID del recibo asociado a este pago
	 * @return ID del recibo asociado a este pago 
	 */
	public String getIdRecibo() {
		return idRecibo;
	}

	/**
	 * Establece el ID del recibo asociado a este pago
	 * @param idRecibo ID del recibo asociado a este pago
	 */
	public void setIdRecibo(String idRecibo) {
		this.idRecibo = idRecibo;
	}

	/**
	 * Obtiene el ID del recibo asociado a este pago
	 * @return ID del recibo asociado a este pago 
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * Establece el ID del recibo asociado a este pago
	 * @param idRecibo ID del recibo asociado a este pago
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}
	
	/**
	 * Obtiene el ID del recibo asociado a este pago
	 * @return ID del recibo asociado a este pago 
	 */
	public Date getFechaInicio() {
		return fechaInicial;
	}

	/**
	 * Establece el ID del recibo asociado a este pago
	 * @param idRecibo ID del recibo asociado a este pago
	 */
	public void setFechaInicio(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	
	Turno turnoAnterior;

	public void setTurnoAnterior(Turno turnoAnterior) {
		this.turnoAnterior = turnoAnterior;
	}
	
	public Turno getTurnoAnterior() {
		return turnoAnterior;
	}

	public String getRespuestaCalificacion() {
		return respuestaCalificacion;
	}

	public void setRespuestaCalificacion(String respuestaCalificacion) {
		this.respuestaCalificacion = respuestaCalificacion;
	}

}
