/*
 * Created on 23-sep-2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.eventos.administracion;

import gov.sir.core.eventos.comun.EvnSIRRespuesta;
import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author mnewball
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EvnRespTurnoManualCertificadoPago extends EvnSIRRespuesta {
	
	/**
	 * Tipo de respuesta empleado tras la solicitud de validaci?n de un Pago
	 */
	public static final String VALIDACION_PAGO = "VALIDACION_PAGO";

	/**
	 * Tipo de respuesta empleado tras la solicitud del procesamiento de un Pago
	 */
	public static final String PROCESAMIENTO_PAGO = "PROCESAMIENTO_PAGO";

	public static final String VERIFICACION_APLICACION_PAGO = "VERIFICACION_APLICACION_PAGO";

	/**
	 * Referencia al objeto Pago respuesta
	 */
	private Pago pago;

	/**
	 * Referencia al objeto Turno respuesta
	 */
	private Turno turno;

	/**
	 * rango en moneda legal para aceptar un pago
	 */
	private double rangoAceptablePago;

	/**
	 * Valor del secuencial para la impresi?n del recibo de pago.
	 */
	private double valorSecuencial;

	/**
	 * Solicitud relacionada con el pago.
	 */
	private Solicitud solicitud;

	/**
	 * Determina si el usuario que hace el pago es cajero de registro, para evitar hacer otro paso, 
	 * entonces el mismo usuario radica el turno de registro. 
	 */
	private Boolean esCajeroRegistro;	

	private boolean nueva;
    

	/**
	 * Constructor p?blico que inicializa los atributos pago, rangoAceptablePago y bancos
	 * @param pago
	 * @param rangoAceptablePago
	 */
	public EvnRespTurnoManualCertificadoPago(Pago pago, double rangoAceptablePago) {
		super(pago, VALIDACION_PAGO);
		this.pago = pago;
		this.rangoAceptablePago = rangoAceptablePago;
	}

	/**
	 * Constructor por parametros
	 * @param turno
	 */
	public EvnRespTurnoManualCertificadoPago(Turno turno) {
		super(turno, PROCESAMIENTO_PAGO);
		this.turno = turno;
	}

	/**
	 * Constructor que envia como respuesta el pago que fue registrado, como
	 * consecuencia del pago en Registro
	 * 
	 *  @param pago 
	 */
	public EvnRespTurnoManualCertificadoPago(Pago pago) {
		super(pago, PROCESAMIENTO_PAGO);
		this.pago = pago;
	}

	/**
	 * @param aplicacionPago
	 */
	public EvnRespTurnoManualCertificadoPago(AplicacionPago aplicacionPago, boolean esNueva) {
		super(aplicacionPago,EvnRespTurnoManualCertificadoPago.VERIFICACION_APLICACION_PAGO);
		this.nueva = esNueva;
	}

	/**
	 * Retorna el pago que viaja en el evento
	 * @return Pago
	 */
	public Pago getPago() {
		return pago;
	}

	/**
	 * Retorna el rango aceptable para validar el pago
	 * @return double
	 */
	public double getRangoAceptablePago() {
		return rangoAceptablePago;
	}

	/**
	 * Retorna el turno
	 * @return Turno
	 */
	public Turno getTurno() {
		return turno;
	}
	
	/**
	 * @return
	 */
	public Solicitud getSolicitud() {
		return solicitud;
	}

	/**
	 * @return
	 */
	public double getValorSecuencial() {
		return valorSecuencial;
	}

	/**
	 * @param solicitud
	 */
	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	/**
	 * @param d
	 */
	public void setValorSecuencial(double d) {
		valorSecuencial = d;
	}



	/**
	 * @return
	 */
	public Boolean getEsCajeroRegistro() {
		return esCajeroRegistro;
	}

	/**
	 * @param boolean1
	 */
	public void setEsCajeroRegistro(Boolean boolean1) {
		esCajeroRegistro = boolean1;
	}

	/**
	 * @return
	 */
	public boolean isNueva() {
		return nueva;
	}

}
