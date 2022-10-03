/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Tarjeta Credito del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import gov.sir.core.negocio.modelo.DocPagoTarjetaCreditoCorreccion;

public class DocPagoTarjetaCreditoCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced {
    private BancoFranquiciaEnhanced bancoFranquicia;
    private String numeroTarjeta;
    private String fecha = "";
    //Edgar Lora: Mantis: 0012422
    private String numeroAprobacion = "";

    public DocPagoTarjetaCreditoCorreccionEnhanced() {
    }
    
    //Edgar Lora: Mantis: 0012422
    public DocPagoTarjetaCreditoCorreccionEnhanced(BancoFranquiciaEnhanced bancoFranquicia, String numeroTarjeta, String fecha, String numAprovacion) {
        this.tipoPago = DocumentoPagoCorreccionEnhanced.PAGO_TARJETA_CREDITO;
        this.bancoFranquicia = bancoFranquicia;
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
        this.numeroAprobacion = numAprovacion;
        
    }

    public static DocPagoTarjetaCreditoCorreccionEnhanced enhance(DocPagoTarjetaCreditoCorreccion docPagoTarjetaCredito){
	   return (DocPagoTarjetaCreditoCorreccionEnhanced)Enhanced.enhance(docPagoTarjetaCredito);
    }

    public BancoFranquiciaEnhanced getBancoFranquicia() {
        return bancoFranquicia;
    }

    public void setBancoFranquicia(BancoFranquiciaEnhanced bancoFranquicia) {
        this.bancoFranquicia = bancoFranquicia;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    //Edgar Lora: Mantis: 0012422
    public String getNumeroAprobacion() {
        return numeroAprobacion;
    }

    public void setNumeroAprobacion(String numeroAprobacion) {
        this.numeroAprobacion = numeroAprobacion;
    }
    
    public String getAnio() {
        return this.fecha.split("/")[2];
    }
}
