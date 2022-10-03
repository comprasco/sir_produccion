/*
 * @autor         : HGOMEZ 
 * @mantis        : 12422 
 * @Requerimiento : 049_453 
 * @descripcion   : Clase que maneja el flujo de Tipo Franquicia del nuevo flujo
 * de forma de pago.
 */
package gov.sir.core.negocio.modelo;

public class TipoFranquiciaPk implements java.io.Serializable {
    public int idTipoFranquicia; //pk

    public TipoFranquiciaPk() {
    }

    public TipoFranquiciaPk(int idTipoFranquicia) {
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
        final TipoFranquiciaPk other = (TipoFranquiciaPk) obj;
        if (this.idTipoFranquicia != other.idTipoFranquicia) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idTipoFranquicia;
        return hash;
    }
}
