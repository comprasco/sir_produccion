/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Tipo Franquicia del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo.jdogenie;
import gov.sir.core.negocio.modelo.TipoFranquiciaPk;

public class TipoFranquiciaEnhancedPk implements java.io.Serializable {

    public int idTipoFranquicia; //pk

    public TipoFranquiciaEnhancedPk() {
    }

    public TipoFranquiciaEnhancedPk(TipoFranquiciaPk tipoFranquiciaPk) {
        this.idTipoFranquicia = tipoFranquiciaPk.idTipoFranquicia;
    }
    
    public TipoFranquiciaEnhancedPk(String s) {
        int i, p = 0;
        idTipoFranquicia = Integer.parseInt(s.substring(p));
    }

    public TipoFranquiciaPk getTipoFranquiciaPk() {
        TipoFranquiciaPk tipoFranquiciaPk = new TipoFranquiciaPk();
        tipoFranquiciaPk.idTipoFranquicia = idTipoFranquicia;
        return tipoFranquiciaPk;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoFranquiciaEnhancedPk other = (TipoFranquiciaEnhancedPk) obj;
        if (this.idTipoFranquicia != other.idTipoFranquicia) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.idTipoFranquicia;
        return hash;
    }

    @Override
    public String toString() {
        return "TipoFranquiciaEnhancedPk{" + "idTipoFranquicia=" + idTipoFranquicia + '}';
    }
}
