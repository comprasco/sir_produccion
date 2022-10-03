/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo Banco Franquica del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import gov.sir.core.negocio.modelo.BancoFranquicia;

public class BancoFranquiciaEnhanced extends Enhanced {
    private String idBanco;         // pk
    private int idTipoFranquicia;   // pk

    public BancoFranquiciaEnhanced() {
    }

    public BancoFranquiciaEnhanced(String idBanco, int idTipoFranquicia) {
        this.idBanco = idBanco;
        this.idTipoFranquicia = idTipoFranquicia;
    }

    public String getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(String idBanco) {
        this.idBanco = idBanco;
    }

    public int getIdTipoFranquicia() {
        return idTipoFranquicia;
    }

    public void setIdTipoFranquicia(int idTipoFranquicia) {
        this.idTipoFranquicia = idTipoFranquicia;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BancoFranquiciaEnhanced other = (BancoFranquiciaEnhanced) obj;
        if ((this.idBanco == null) ? (other.idBanco != null) : !this.idBanco.equals(other.idBanco)) {
            return false;
        }
        if (this.idTipoFranquicia != other.idTipoFranquicia) {
            return false;
        }
        return true;
    }
    
    public static BancoFranquiciaEnhanced enhance(BancoFranquicia bancoFranquicia) {
        return (BancoFranquiciaEnhanced) Enhanced.enhance(bancoFranquicia);
    }
}
