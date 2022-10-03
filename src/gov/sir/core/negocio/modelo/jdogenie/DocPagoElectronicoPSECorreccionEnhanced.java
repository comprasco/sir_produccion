/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Pago Electrónico PSE del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import gov.sir.core.negocio.modelo.DocPagoElectronicoPSECorreccion;

public class DocPagoElectronicoPSECorreccionEnhanced extends DocumentoPagoCorreccionEnhanced{
    private String numeroAprobacion;
    private String fecha = "";       
    //Edgar Lora: Mantis: 0012422
    private BancoFranquiciaEnhanced bancoFranquicia;
	public DocPagoElectronicoPSECorreccionEnhanced(){
	}
	/**
	 * Constructor que especifica los atributos de un pago realizado en efectivo.
	 * @param valor
	 */
        //Edgar Lora: Mantis: 0012422
	public DocPagoElectronicoPSECorreccionEnhanced(double valor, BancoFranquiciaEnhanced bancoFranquicia, String numeroAprobacion, String fecha){
		this.tipoPago = DocumentoPagoCorreccionEnhanced.PAGO_PSE;
		this.valorDocumento = valor;
                this.numeroAprobacion = numeroAprobacion;
                this.fecha = fecha; 
                //Edgar Lora: Mantis: 0012422
                this.bancoFranquicia = bancoFranquicia;                
	}
	
	public static DocPagoElectronicoPSECorreccionEnhanced enhance(DocPagoElectronicoPSECorreccion docPagoElectronicoPSE){
	   return (DocPagoElectronicoPSECorreccionEnhanced)Enhanced.enhance(docPagoElectronicoPSE);
	}

    public String getNumeroAprobacion() {
        return numeroAprobacion;
    }

    public void setNumeroAprobacion(String numeroAprobacion) {
        this.numeroAprobacion = numeroAprobacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    //Edgar Lora: Mantis: 0012422
    public BancoFranquiciaEnhanced getBancoFranquicia() {
        return bancoFranquicia;
    }

    public void setBancoFranquicia(BancoFranquiciaEnhanced bancoFranquicia) {
        this.bancoFranquicia = bancoFranquicia;
    }

    public String getAnio() {
        return this.fecha.split("/")[2];
    }
}
