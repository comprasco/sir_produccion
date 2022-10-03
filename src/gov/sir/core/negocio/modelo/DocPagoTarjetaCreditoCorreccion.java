/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo Banco Franquica del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo;
import org.auriga.core.modelo.TransferObject;

public class DocPagoTarjetaCreditoCorreccion extends DocumentoPagoCorreccion implements TransferObject {
    private BancoFranquicia bancoFranquicia;
    private String numeroTarjeta = "";
    private String fecha = "";
    //Edgar Lora: Mantis: 0012422
    private String numeroAprobacion = "";
    private static final long serialVersionUID = 1L;
    //ZONA LOGICA NEGOCIO
    /**
     * Constructor por defecto
     */
    public DocPagoTarjetaCreditoCorreccion() {
    }

    public DocPagoTarjetaCreditoCorreccion(BancoFranquicia bancoFranquicia, String numeroTarjeta, String fecha, String numAprobacion) {
        this.tipoPago = DocumentoPagoCorreccion.PAGO_TARJETA_CREDITO;
        this.bancoFranquicia = bancoFranquicia;
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
        this.numeroAprobacion = numAprobacion;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocPagoTarjetaCreditoCorreccion other = (DocPagoTarjetaCreditoCorreccion) obj;
        if (this.bancoFranquicia != other.bancoFranquicia && (this.bancoFranquicia == null || !this.bancoFranquicia.equals(other.bancoFranquicia))) {
            return false;
        }
        if ((this.numeroTarjeta == null) ? (other.numeroTarjeta != null) : !this.numeroTarjeta.equals(other.numeroTarjeta)) {
            return false;
        }
        if ((this.fecha == null) ? (other.fecha != null) : !this.fecha.equals(other.fecha)) {
            return false;
        }
        return true;
    }

    public BancoFranquicia getBancoFranquicia() {
        return bancoFranquicia;
    }

    public void setBancoFranquicia(BancoFranquicia bancoFranquicia) {
        this.bancoFranquicia = bancoFranquicia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    
    //Edgar Lora: Mantis: 0012422
    public String getNumeroAprobacion() {
        return numeroAprobacion;
    }

    public void setNumeroAprobacion(String numeroAprobacion) {
        this.numeroAprobacion = numeroAprobacion;
    }
}
