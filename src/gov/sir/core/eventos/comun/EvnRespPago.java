package gov.sir.core.eventos.comun;

import gov.sir.core.negocio.modelo.AplicacionPago;
import gov.sir.core.negocio.modelo.ImprimiblePdf;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.Turno;

/**
 * @author eacosta,mmunoz
 */
public class EvnRespPago extends EvnSIRRespuesta {


	/**
     * Tipo de respuesta empleado tras la solicitud de validación de un Pago
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
	 * Valor del secuencial para la impresión del recibo de pago.
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
	
	private int idImprimible = 0;

	private ImprimiblePdf imprimiblePdf;
	    

    /**
     * Constructor público que inicializa los atributos pago, rangoAceptablePago y bancos
     * @param pago
     * @param rangoAceptablePago
     */
    public EvnRespPago(Pago pago, double rangoAceptablePago) {
        super(pago, VALIDACION_PAGO);
        this.pago = pago;
        this.rangoAceptablePago = rangoAceptablePago;
    }

    /**
     * Constructor por parametros
     * @param turno
     */
    public EvnRespPago(Turno turno) {
        super(turno, PROCESAMIENTO_PAGO);
        this.turno = turno;
    }

    /**
     * Constructor que envia como respuesta el pago que fue registrado, como
     * consecuencia del pago en Registro
     * 
     *  @param pago 
     */
    public EvnRespPago(Pago pago) {
        super(pago, PROCESAMIENTO_PAGO);
        this.pago = pago;
    }
    
	/**
	 * @param aplicacionPago
	 */
	public EvnRespPago(AplicacionPago aplicacionPago, boolean esNueva) {
		super(aplicacionPago,EvnRespPago.VERIFICACION_APLICACION_PAGO);
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

	/**
	 * @return
	 */
	public int getIdImprimible() {
		return idImprimible;
	}

	/**
	 * @param i
	 */
	public void setIdImprimible(int i) {
		idImprimible = i;
	}

	public void setImprimiblePdf(ImprimiblePdf imprimiblePdf) {
		this.imprimiblePdf = imprimiblePdf;
	}
	
	public ImprimiblePdf getImprimiblePdf() {
		return this.imprimiblePdf;
	}

}
