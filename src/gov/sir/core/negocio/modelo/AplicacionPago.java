package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;


/**
 * Clase que modela las aplicaciones de pago de los distintos documentos
 */
public class AplicacionPago implements TransferObject {
    /**
     * @Autor: Santiago Vásquez
     * @Change: 1242.VER DETALLES DE TURNO VISUALIZACION FORMAS PAGO
     */
    private double valorAplicadoAnulacion;
    private double valorAplicado;
    private DocumentoPago documentoPago;
    private Pago pago;
    private static final long serialVersionUID = 1L;
    /** Identificador del documento de pago */
    private String idDocumentoPago; //pk
    
    /** Identificador de la liquidación */
    private String idLiquidacion; //pk
    
    /** Identificador de la solicitud */
    private String idSolicitud; //pk
    
    private String circulo;

	/**  Constuctor por defecto */
	public AplicacionPago() {
	}

    /**
     * Constructor que inicializa las referencias al Documento de Pago empleado
     * en esta aplicación y el valor aplicado de dicho Documento.
     * @param documentoPago 
     * @param valorAplicado 
     */
    public AplicacionPago(DocumentoPago documentoPago, double valorAplicado) {
        this.documentoPago = documentoPago;
        this.valorAplicado = valorAplicado;
    }

    /**
     * Este método sobreescribe el método en Object y retorna true
     * solo si el valor pagado y el documento de pago son iguales.
     */
    public boolean equals(Object other) {
        if ((other == null) || !(other instanceof AplicacionPago)) {
            return false;
        }

        AplicacionPago p = (AplicacionPago) other;

        return documentoPago.equals(p.documentoPago);
    }


    /**
     * Obtener el atributo documentoPago
     * @return Retorna el atributo documentoPago.
     */
    public DocumentoPago getDocumentoPago() {
        return documentoPago;
    }
    /**
     * Actualizar el valor del atributo documentoPago
     * @param documentoPago El nuevo valor del atributo documentoPago.
     */
    public void setDocumentoPago(DocumentoPago documentoPago) {
        this.documentoPago = documentoPago;
    }
    /**
     * Obtener el atributo idDocumentoPago
     * @return Retorna el atributo idDocumentoPago.
     */
    public String getIdDocumentoPago() {
        return idDocumentoPago;
    }
    /**
     * Actualizar el valor del atributo idDocumentoPago
     * @param idDocumentoPago El nuevo valor del atributo idDocumentoPago.
     */
    public void setIdDocumentoPago(String idDocumentoPago) {
        this.idDocumentoPago = idDocumentoPago;
    }
    /**
     * Obtener el atributo idLiquidacion
     * @return Retorna el atributo idLiquidacion.
     */
    public String getIdLiquidacion() {
        return idLiquidacion;
    }
    /**
     * Actualizar el valor del atributo idLiquidacion
     * @param idLiquidacion El nuevo valor del atributo idLiquidacion.
     */
    public void setIdLiquidacion(String idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }
    /**
     * Obtener el atributo idSolicitud
     * @return Retorna el atributo idSolicitud.
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }
    /**
     * Actualizar el valor del atributo idSolicitud
     * @param idSolicitud El nuevo valor del atributo idSolicitud.
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    /**
     * Obtener el atributo pago
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
     * Obtener el atributo valorAplicado
     * @return Retorna el atributo valorAplicado.
     */
    public double getValorAplicado() {
        return valorAplicado;
    }
    /**
     * Actualizar el valor del atributo valorAplicado
     * @param valorAplicado El nuevo valor del atributo valorAplicado.
     */
    public void setValorAplicado(double valorAplicado) {
        this.valorAplicado = valorAplicado;
    }
    
	public String getCirculo() {
		return circulo;
	}

	public void setCirculo(String circulo) {
		this.circulo = circulo;
	}

    /**
     * @Autor: Santiago Vásquez
     * @Change: 1242.VER DETALLES DE TURNO VISUALIZACION FORMAS PAGO
     */
    public double getValorAplicadoAnulacion() {
        return valorAplicadoAnulacion;
    }

    public void setValorAplicadoAnulacion(double valorAplicadoAnulacion) {
        this.valorAplicadoAnulacion = valorAplicadoAnulacion;
    }

    
}
