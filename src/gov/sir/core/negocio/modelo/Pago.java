/*
 * Created on 04-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo;

import org.auriga.core.modelo.TransferObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/** Clase que modela los datos de pagos de liquidaciones
 * @author fceballos
 *
 */
public class Pago implements TransferObject {
    /** Constante para definir la forma de pago efectivo */
    public static final String FORMA_PAGO_EFECTIVO = "EFECTIVO";
    private static final long serialVersionUID = 1L;

    /** Constante para definir la forma de pago cheques y efectivo */
    public static final String FORMA_PAGO_EFECTIVO_Y_CHEQUES = "EFECTIVO_Y_CHEQUES";

    /** Constante para definir la forma de pago consignación */
    public static final String FORMA_PAGO_CONSIGNACION = "CONSIGNACION";

    /** Fecha del pago */
    private Date fecha;
    
    /** Fecha de la última impresión del recibo de pago */
    private Date fechaImpresion;

    /** Idetificador de la liquidación Parte de la llave primaria del objeto */
    private String idLiquidacion; //pk

    /** Idetificador de la solicitud Parte de la llave primaria del objeto */
    private String idSolicitud; //pk

    /** Número del recibo de pago */
    private String numRecibo;
    
    /**Concepto del pago*/
    private String concepto;

    /** Lista de aplicaciones de pago que contienen los documentos de pago */
    private List aplicacionesPago = new ArrayList(); // contains AplicacionPago  inverse Aplicacion.pago

    /** Liquidación sobre la que se realiza el pago */
    private Liquidacion liquidacion = null;
    
    /** Identificador del circulo*/
    private String circulo;

    /** Usuario que realiza el pago */
    private Usuario usuario;
    
    /** Ultimo numero de recibo generado */
    private String lastNumRecibo;


	/**  Constructor por defecto */
    public Pago() {
    }

    /**
    * Constructor que inicializa la lista de aplicaciones.
     * @param liquidacion Liquidación sobre la que se realiza el pago
    * @param aplicacionesPago Lista de objetos de tipo <CODE>gov.sir.core.negocio.modelo.AplicacionPago</CODE>
    */
    public Pago(Liquidacion liquidacion, List aplicacionesPago) {
        super();
        this.liquidacion = liquidacion;

        if (aplicacionesPago != null) {
            this.aplicacionesPago = aplicacionesPago;
        }
    }

    /**
     * Obtener la lista de aplicaciones pago
     * @return  Lista de aplciaciones pago
     */
    public List getAplicacionPagos() {
        return Collections.unmodifiableList(aplicacionesPago);
    }

    /**
     * Agregar una aplicación pago al Objeto Pago
     * @param newAplicacion
     * @return true si se pudo agregar la aplicación pago
     */
    public boolean addAplicacionPago(AplicacionPago newAplicacion) {
        return aplicacionesPago.add(newAplicacion);
    }

    /**
     * Remover una aplicación pago
     * @param oldAplicacion
     * @return true si pudo remover la aplicación
     */
    public boolean removeAplicacionPago(AplicacionPago oldAplicacion) {
        return aplicacionesPago.remove(oldAplicacion);
    }

    /**
     * Obtener el número de aplicaciones pago asociadas
    * @return número de aplicaciones pago
    */
    public int getCantidadAplicaciones() {
        return aplicacionesPago.size();
    }

    /**
     * Actualizar el valor del atributo liquidacion
     * @param liquidacion El nuevo valor del atributo liquidacion
     */
    public void setLiquidacion(Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
        this.setIdLiquidacion(liquidacion.getIdLiquidacion());
        this.setIdSolicitud(liquidacion.getIdSolicitud());
    }

    /**
     * Obtener el atributo fecha
     * @return Retorna el atributo fecha.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Actualizar el valor del atributo fecha
     * @param fecha El nuevo valor del atributo fecha.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtener el atributo fechaImpresion
     * @return Retorna el atributo fechaImpresion
     */
    public Date getFechaImpresion() {
        return fechaImpresion;
    }
    
    /**
     * Actualizar el valor del atributo fechaImpresion
     * @param fechaImpresion El nuevo valor del atributo fechaImpresion
     */
    public void setFechaImpresion(Date fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
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
     * Obtener el atributo numRecibo
     * @return Retorna el atributo numRecibo.
     */
    public String getNumRecibo() {
        return numRecibo;
    }

    /**
     * Actualizar el valor del atributo numRecibo
     * @param numRecibo El nuevo valor del atributo numRecibo.
     */
    public void setNumRecibo(String numRecibo) {
        this.numRecibo = numRecibo;
    }

    /**
     * Obtener el atributo usuario
     * @return Retorna el atributo usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Actualizar el valor del atributo usuario
     * @param usuario El nuevo valor del atributo usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtener el atributo liquidacion
     * @return Retorna el atributo liquidacion.
     */
    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    /** Obtiene el ultimo numero de recibo impreso para el pago */
    public String getLastNumRecibo() {
		return lastNumRecibo;
	}

    /** Modifica el ultimo numero de recibo impreso para el pago  */
	public void setLastNumRecibo(String lastNumRecibo) {
		this.lastNumRecibo = lastNumRecibo;
	}

	public String getCirculo() {
		return circulo;
	}

	public void setCirculo(String circulo) {
		this.circulo = circulo;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
}
