/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Pago Electr?nico PSE del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo;
import org.auriga.core.modelo.TransferObject;

public class DocPagoElectronicoPSE extends DocumentoPago implements TransferObject {
    //ZONA VARIABLE
    private String numeroAprobacion = "";
    private String fechaDocumento = "";
    //Edgar Lora: Mantis: 0012422
    private static final long serialVersionUID = 1L;
    private BancoFranquicia bancoFranquicia;
    //private double valorPSE = 0.0d;
    //ZONA LOGICA NEGOCIO
    /**
     * Constructor por defecto
     */
    public DocPagoElectronicoPSE() {
    }

    /**
     * Constructor que especifica los atributos de un pago realizado por medio
     * de un pago electr?nico PSE.
     *
     * @param numeroAprobacion
     * @param fechaDocumento
     * @param valor
     */
    //Edgar Lora: Mantis: 0012422
    public DocPagoElectronicoPSE(BancoFranquicia bancoFranquicia, String numeroAprobacion, String fechaDocumento) {
        this.tipoPago = DocumentoPago.PAGO_ELECTRONICO_PSE;
        this.numeroAprobacion = numeroAprobacion;
        this.fechaDocumento = fechaDocumento;
        //Edgar Lora: Mantis: 0012422
        this.bancoFranquicia = bancoFranquicia;
    }
 /**
     * Constructor que especifica los atributos de un pago para correccion realizado por medio
     * de un pago electr?nico PSE.
     *
     * @param numeroAprobacion
     * @param fechaDocumento
     * @param valor
     */
    //Edgar Lora: Mantis: 0012422
    public DocPagoElectronicoPSE(BancoFranquicia bancoFranquicia, String numeroAprobacion) {
        this.tipoPago = DocumentoPago.PAGO_ELECTRONICO_PSE;
        this.numeroAprobacion = numeroAprobacion;
        this.bancoFranquicia = bancoFranquicia;
    }
    /**
     * Este m?todo sobreescribe el m?todo en Object y retorna true solo si el
     * numero de numeroAprobacion y la fechaDocumento son iguales.
     */
    public boolean equals(Object other) {
        if (other == null || !(other instanceof DocPagoElectronicoPSE)) {
            return false;
        }
        DocPagoElectronicoPSE p = (DocPagoElectronicoPSE) other;
        return numeroAprobacion.equals(p.numeroAprobacion) && fechaDocumento.equals(p.fechaDocumento);
    }

    //ZONA GETTERS Y SETTERS
    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public String getNumeroAprobacion() {
        return numeroAprobacion;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public void setNumeroAprobacion(String numeroAprobacion) {
        this.numeroAprobacion = numeroAprobacion;
    }
    
    //Edgar Lora: Mantis: 0012422
    public BancoFranquicia getBancoFranquicia() {
        return bancoFranquicia;
    }

    public void setBancoFranquicia(BancoFranquicia bancoFranquicia) {
        this.bancoFranquicia = bancoFranquicia;
    }
    
}
