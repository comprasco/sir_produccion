/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Tipo Franquicia del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import gov.sir.core.negocio.modelo.TipoFranquicia;

public class TipoFranquiciaEnhanced extends Enhanced {
    private int idTipoFranquicia;
    private String nombreFranquicia;

    public TipoFranquiciaEnhanced() {
    }

//    public TipoFranquiciaEnhanced(int idTipoFranquicia, String nombreFranquicia) {
//        this.idTipoFranquicia = idTipoFranquicia;
//        this.nombreFranquicia = nombreFranquicia;
//    }

    public int getIdTipoFranquicia() {
        return idTipoFranquicia;
    }

    public void setIdTipoFranquicia(int idTipoFranquicia) {
        this.idTipoFranquicia = idTipoFranquicia;
    }

    public String getNombreFranquicia() {
        return nombreFranquicia;
    }

    public void setNombreFranquicia(String nombreFranquicia) {
        this.nombreFranquicia = nombreFranquicia;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoFranquiciaEnhanced other = (TipoFranquiciaEnhanced) obj;
        if (this.idTipoFranquicia != other.idTipoFranquicia) {
            return false;
        }
        if ((this.nombreFranquicia == null) ? (other.nombreFranquicia != null) : !this.nombreFranquicia.equals(other.nombreFranquicia)) {
            return false;
        }
        return true;
    }
    
    public static TipoFranquiciaEnhanced enhance (TipoFranquicia tipoFranquicia){
        return (TipoFranquiciaEnhanced) Enhanced.enhance(tipoFranquicia);
    }
}
