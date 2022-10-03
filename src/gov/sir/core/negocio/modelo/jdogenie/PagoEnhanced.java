/*
 * Created on 04-ago-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.sir.core.negocio.modelo.jdogenie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import gov.sir.core.negocio.modelo.Pago;

/**
 * @author fceballos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PagoEnhanced extends Enhanced {
    
    public static final String FORMA_PAGO_EFECTIVO = "EFECTIVO";
    public static final String FORMA_PAGO_EFECTIVO_Y_CHEQUES = "EFECTIVO_Y_CHEQUES";
    public static final String FORMA_PAGO_CONSIGNACION = "CONSIGNACION";
    
    private Date fecha;
    private Date fechaImpresion;
    private String idLiquidacion;//pk
    private String idSolicitud;//pk
    private String numRecibo;
    private String concepto;
    private List aplicacionesPago = new ArrayList(); // contains AplicacionPago  inverse Aplicacion.pago
    private LiquidacionEnhanced liquidacion = null;
	private UsuarioEnhanced usuario;
	
	/** Ultimo numero de recibo generado */
    private String lastNumRecibo;
    private String circulo;

 
    
	public PagoEnhanced() {
	}
    /**
     * Constructor que inicializa la lista de aplicaciones.
     * @param aplicacionesPago Lista de objetos de tipo <CODE>gov.sir.core.negocio.modelo.AplicacionPago</CODE>
     */
    public PagoEnhanced(LiquidacionEnhanced liquidacion, List aplicacionesPago) {
        super();
        this.liquidacion = liquidacion;
        if (aplicacionesPago != null) {
            this.aplicacionesPago = aplicacionesPago;
        }
    }
    
    /**
     * @return
     */
    public Date getFecha() {
        return fecha;
    }
    
    /**
     * @param date
     */
    public void setFecha(Date date) {
        fecha = date;
    }
    
    /**
     * Obtener el atributo fechaImpresion
     * 
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
    
    public List getAplicacionPagos() {
        return Collections.unmodifiableList(aplicacionesPago);
    }
    
    public void setAplicacionesPagos(List aplicacionesPago) {
        this.aplicacionesPago = aplicacionesPago;
    }
    
    public boolean addAplicacionPago(AplicacionPagoEnhanced newAplicacion) {
        return aplicacionesPago.add(newAplicacion);
    }
    
    public boolean removeAplicacionPago(AplicacionPagoEnhanced oldAplicacion) {
        return aplicacionesPago.remove(oldAplicacion);
    }
    
    /**
     * @param documentosPago
     */
    public int getCantidadAplicaciones(){
        return aplicacionesPago.size();
    }
    
    public static PagoEnhanced enhance(Pago x){
        return (PagoEnhanced)Enhanced.enhance(x);
    }
    
  
    
    /**
     * @return
     */
    public LiquidacionEnhanced getLiquidacion() {
        return liquidacion;
    }
    
    /**
     * @param liquidacion
     */
    public void setLiquidacion(LiquidacionEnhanced liquidacion) {
        this.liquidacion = liquidacion;
        this.setIdLiquidacion(liquidacion.getIdLiquidacion());
        this.setIdSolicitud(liquidacion.getIdSolicitud());
    }
    
    /**
     * @return
     */
    public String getIdLiquidacion() {
        return idLiquidacion;
    }
    
    /**
     * @return
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }
    
    /**
     * @param string
     */
    public void setIdLiquidacion(String string) {
        idLiquidacion = string;
    }
    
    /**
     * @param string
     */
    public void setIdSolicitud(String string) {
        idSolicitud = string;
    }
    
    
    
    
    /**
     * @return
     */
    public String getNumRecibo() {
        return numRecibo;
    }
    
    /**
     * @param string
     */
    public void setNumRecibo(String string) {
        numRecibo = string;
    }
    
	/**
	 * @return
	 */
	public UsuarioEnhanced getUsuario() {
		return usuario;
	}

	/**
	 * @param enhanced
	 */
	public void setUsuario(UsuarioEnhanced enhanced) {
		usuario = enhanced;
	}
	
    public String getLastNumRecibo() {
		return lastNumRecibo;
	}

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
