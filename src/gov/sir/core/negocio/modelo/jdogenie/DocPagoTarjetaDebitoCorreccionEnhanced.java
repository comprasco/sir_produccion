/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Tarjeta Debito del nuevo flujo
 *** de forma de pago.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import gov.sir.core.negocio.modelo.DocPagoTarjetaDebitoCorreccion;

public class DocPagoTarjetaDebitoCorreccionEnhanced extends DocumentoPagoCorreccionEnhanced {
    private BancoFranquiciaEnhanced bancoFranquicia;
    private String numeroTarjeta;
    private String fecha = "";
    //Edgar Lora: Mantis: 0012422
    private String numeroAprobacion;
    
    public DocPagoTarjetaDebitoCorreccionEnhanced() {
    }
    
    //Edgar Lora: Mantis: 0012422
    public DocPagoTarjetaDebitoCorreccionEnhanced(BancoFranquiciaEnhanced bancoFranquicia, String numeroTarjeta, String fecha, String numAprovacion) {
        this.tipoPago = DocumentoPagoCorreccionEnhanced.PAGO_TARJETA_CREDITO;
        this.bancoFranquicia = bancoFranquicia;
        this.numeroTarjeta = numeroTarjeta;
        this.fecha = fecha;
        this.numeroAprobacion = numAprovacion;
    }

    public static DocPagoTarjetaDebitoCorreccionEnhanced enhance(DocPagoTarjetaDebitoCorreccion docPagoTarjetaDebito){
	   return (DocPagoTarjetaDebitoCorreccionEnhanced)Enhanced.enhance(docPagoTarjetaDebito);
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

    public void setNumeroAprobacion(String numeroAprovacion) {
        this.numeroAprobacion = numeroAprovacion;
    }
    
    public String getAnio() {
        return this.fecha.split("/")[2];
    }
}
